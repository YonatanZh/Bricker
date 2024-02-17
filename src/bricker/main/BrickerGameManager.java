package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.SpecialCollisionStrategy;
import bricker.game_objects.*;
import bricker.special_behaviors.BehaviorFactory;
import bricker.special_behaviors.SpecialBehaviors;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

import static bricker.main.Constants.*;

/**
 * This class is the main class for the Bricker game. It is responsible for initializing the game
 * and updating the game state.
 */
public class BrickerGameManager extends GameManager {
    private static final SpecialBehaviors[] allBehaviors = new SpecialBehaviors[BEHAVIOR_AMOUNT];
    private ImageReader imageReader;
    private UserInputListener inputListener;
    private WindowController windowController;
    private final Vector2 windowDimensions;
    private GameObjectFactory gameObjectFactory;
    private GameObject ball;
    private GameObject paddle;
    private LifeCounter lifeCounter;
    private final Counter lives;
    private final int rowsOfBricks;
    private final int bricksPerRow;
    private Counter brickCounter;
    private Vector2 screenCenter;
    private GameObject objectTracker;


    /**
     * Constructor for the BrickerGameManager class. It initializes the game with default values.
     */
    public BrickerGameManager() {
        super(GAME_TITLE, new Vector2(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.windowDimensions = new Vector2(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.bricksPerRow = DEFAULT_BRICK_PER_ROW;
        this.rowsOfBricks = DEFAULT_ROW_OF_BRICKS;
        this.lives = new Counter(DEFAULT_LIVES);
    }

    /**
     * Constructor for the BrickerGameManager class. It initializes the game with the given values.
     *
     * @param windowDimensions the dimensions of the game window
     * @param bricksPerRow     the number of bricks per row
     * @param rowsOfBricks     the number of rows of bricks
     */
    public BrickerGameManager(Vector2 windowDimensions, int bricksPerRow, int rowsOfBricks) {
        super(GAME_TITLE, windowDimensions);
        this.windowDimensions = windowDimensions;
        this.rowsOfBricks = rowsOfBricks;
        this.bricksPerRow = bricksPerRow;
        this.lives = new Counter(DEFAULT_LIVES);
    }

    /**
     * Initializes the game with the given image reader, sound reader, input listener, and window
     * controller.
     *
     * @param imageReader      the image reader
     * @param soundReader      the sound reader
     * @param inputListener    the input listener
     * @param windowController the window controller
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        // initialization
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.brickCounter = new Counter(this.bricksPerRow * this.rowsOfBricks);
        this.screenCenter = windowDimensions.mult(0.5f);

        this.gameObjectFactory = new GameObjectFactory(imageReader, soundReader, inputListener,
                windowDimensions);


        GameObject background = gameObjectFactory.createBackground(BACKGROUND_PATH);
        gameObjects().addGameObject(background, Layer.BACKGROUND);

        GameObject[] walls = gameObjectFactory.createWalls(WALL_WIDTH);
        addWalls(walls);

        this.ball = gameObjectFactory.createBall(BALL_PATH, BALL_SOUND_PATH, BALL_RADIUS, screenCenter);
        setBallVelocity(ball);
        gameObjects().addGameObject(this.ball);

        this.objectTracker = gameObjectFactory.createObjectTracker(ball, Vector2.ZERO,
                windowDimensions.mult(1.2f), windowDimensions, this, ball);
        gameObjects().addGameObject(this.objectTracker, Layer.UI);

        Vector2 paddleSize = new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT);
        Vector2 paddlePosition = new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 50);
        this.paddle = gameObjectFactory.createPaddle(PADDLE_PATH, paddleSize, paddlePosition);
        gameObjects().addGameObject(this.paddle);

        createLifeDisplay();

        createBehaviors();

        GameObject[][] bricks = new GameObject[rowsOfBricks][bricksPerRow];
        createBricks(bricks);
        addBricks(bricks);
    }

    /**
     * Updates the game state. It checks for collisions, updates the game objects, and checks for win.
     *
     * @param deltaTime unused.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkPaddleWallCollision();
        checkWinCondition();
        if (isBallOutOfBounds()) {
            resetBallAfterLoss();
        }
        removeOutOfBoundsItems();
    }

    private void createBehaviors() {
        BehaviorFactory behaviorFactory = new BehaviorFactory(gameObjects(), gameObjectFactory,
                windowDimensions);
        allBehaviors[0] = behaviorFactory.createExtraPuck(BALL_RADIUS);
        allBehaviors[1] = behaviorFactory.createExtraPaddle(new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT));
        allBehaviors[2] = behaviorFactory.createCameraChange(this, (ObjectTracker) objectTracker);
        Vector2 lifeSize = new Vector2(DROPPING_LIFE_SIZE, DROPPING_LIFE_SIZE);
        Renderable lifeImage = imageReader.readImage(LIFE_HEART_PATH, true);
        allBehaviors[3] = behaviorFactory.createExtraLife(lifeSize, lifeImage, windowDimensions,
                gameObjects(),
                gameObjectFactory, lifeCounter);
        allBehaviors[4] = behaviorFactory.createDouble(new SpecialBehaviors[]{allBehaviors[0],
                allBehaviors[1], allBehaviors[2], allBehaviors[3]});
    }

    // todo there's a problem with the spacing whenever a life is created or lost + plus the initial
    //  spacing is off
    private void createLifeDisplay() {
        Vector2 livesTopLeftCorner = new Vector2(LIVES_INDENT_SIZE,
                windowDimensions.y() - DROPPING_LIFE_SIZE);
        Vector2 livesDimensions = new Vector2((DROPPING_LIFE_SIZE + BUFFER) * DEFAULT_LIVES,
                DROPPING_LIFE_SIZE);
        Vector2 singleLifeDimensions = new Vector2(DROPPING_LIFE_SIZE, DROPPING_LIFE_SIZE);
        float xIndentation = DROPPING_LIFE_SIZE + BUFFER;
        Vector2 indentation = new Vector2(xIndentation, 0);

        NumericLifeDisplay numericLifeCounter =
                (NumericLifeDisplay) gameObjectFactory.createNumericLifeDisplay(livesTopLeftCorner,
                        livesDimensions, lives);
        gameObjects().addGameObject(numericLifeCounter, Layer.UI);

        GraphicalLifeDisplay graphicalLives =
                (GraphicalLifeDisplay) gameObjectFactory.createGraphicalLifeDisplay(
                        livesTopLeftCorner.add(indentation),
                        singleLifeDimensions, xIndentation, lives, MAX_LIVES, gameObjects());

        this.lifeCounter = (LifeCounter) gameObjectFactory.createLifeCounter(livesTopLeftCorner,
                livesDimensions, lives, numericLifeCounter, graphicalLives);
    }

    private void createBricks(GameObject[][] listOfBricks) {
        Renderable brickImage = imageReader.readImage(BRICK_PATH, false);
        CollisionStrategy collisionStrategy = new SpecialCollisionStrategy(gameObjects(), windowDimensions,
                allBehaviors);

        int distFromWall = (WALL_WIDTH * 2) + 1;
        int allBufferSize = (bricksPerRow - 1) * BUFFER;
        float brickWidth = (windowDimensions.x() - distFromWall - allBufferSize) / bricksPerRow;
        for (int i = 1; i <= rowsOfBricks; i++) {
            listOfBricks[i - 1] = createBrickRow(brickWidth, i, brickImage, collisionStrategy);
        }
    }

    private GameObject[] createBrickRow(float brickWidth, int rowIndex, Renderable brickImage,
                                        CollisionStrategy strategy) {
        Vector2 brickDimension = new Vector2(brickWidth, BRICK_HEIGHT);
        GameObject[] row = new GameObject[bricksPerRow];
        for (int i = 0; i < bricksPerRow; i++) {

            Vector2 topLeft = new Vector2((i * (BUFFER + brickWidth)) + WALL_WIDTH,
                    rowIndex * (BRICK_HEIGHT + BUFFER) + WALL_WIDTH);
            row[i] = gameObjectFactory.createBrick(topLeft, brickDimension, brickImage, strategy,
                    brickCounter);
        }
        return row;
    }

    private void setBallVelocity(GameObject ball) {
        float velocityX = BALL_SPEED;
        float velocityY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            velocityX = -velocityX;
        }
        if (rand.nextBoolean()) {
            velocityY = -velocityY;
        }
        ball.setVelocity(new Vector2(velocityX, velocityY));
    }

    private void addWalls(GameObject[] walls) {
        for (GameObject wall : walls) {
            gameObjects().addGameObject(wall);
        }
    }

    private void addBricks(GameObject[][] bricks) {
        for (GameObject[] brick : bricks) {
            for (GameObject gameObject : brick) {
                gameObjects().addGameObject(gameObject);
            }
        }
    }

    private boolean isBallOutOfBounds() {
        return ball.getCenter().y() > windowDimensions.y();
    }

    private void resetBallAfterLoss() {
        lives.decrement();
        this.lifeCounter.loseLife();
        checkEndGame();
        ball.setCenter(screenCenter);
        setBallVelocity(ball);
    }

    private void removeOutOfBoundsItems() {
        for (GameObject obj : gameObjects().objectsInLayer(Layer.DEFAULT)) {
            if (obj.getCenter().y() > windowDimensions.y()) {
                gameObjects().removeGameObject(obj);
            }
        }
    }

    private void resetWindowDialog(String prompt) {
        if (windowController.openYesNoDialog(prompt)) {
            lives.reset();
            lives.increaseBy(DEFAULT_LIVES);
            this.windowController.resetGame();
        } else {
            windowController.closeWindow();
        }
    }

    private void checkEndGame() {
        if (lives.value() == 0) {
            resetWindowDialog(LOSE_PROMPT);
        }
    }

    private void checkWinCondition() {
        if (brickCounter.value() == 0 || inputListener.isKeyPressed('W')) {
            resetWindowDialog(WIN_PROMPT);
        }
    }

    private void checkPaddleWallCollision() {
        for (GameObject obj : gameObjects().objectsInLayer(Layer.DEFAULT)) {
            if (obj instanceof Paddle) {
                float x = obj.getTopLeftCorner().x();
                float maxX = windowDimensions.x() - obj.getDimensions().x();

                obj.setTopLeftCorner(new Vector2(Math.max(0, Math.min(x, maxX)), obj.getTopLeftCorner().y()));
            }
        }
    }


    /**
     * Main method for the Bricker game. It creates a BrickerGameManager and runs the game.
     *
     * @param args unused.
     */
    public static void main(String[] args) {
        BrickerGameManager game;
        Vector2 screen = new Vector2(SCREEN_WIDTH, SCREEN_HEIGHT);

        if (args.length == 2) {
            game = new BrickerGameManager(screen, Integer.parseInt(args[0]),
                    Integer.parseInt(args[1]));
        } else {
            game = new BrickerGameManager();
        }
        game.run();
    }
}


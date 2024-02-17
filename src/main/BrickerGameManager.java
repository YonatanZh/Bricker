package main;

import brick_strategies.CollisionStrategy;
import brick_strategies.SpecialCollisionStrategy;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.*;
import special_behaviors.BehaviorFactory;
import special_behaviors.SpecialBehaviors;

import java.util.Random;

public class BrickerGameManager extends GameManager {

    // screen dimensions
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int WALL_WIDTH = 5;
    private static final int BUFFER = 5;
    private static final int LIVES_INDENT_SIZE = 50;

    // game Strings
    private static final String TITLE = "Bricker";
    private static final String LOSE_PROMPT = "You lose! Play again?";
    private static final String WIN_PROMPT = "You win! Play again?";

    // assets paths
    private static final String BALL_PATH = "assets/ball.png";
    private static final String BALL_SOUND_PATH = "assets/blop_cut_silenced.wav";
    private static final String PADDLE_PATH = "assets/paddle.png";
    private static final String BACKGROUND_PATH = "assets/DARK_BG2_small.jpeg";
    private static final String BRICK_PATH = "assets/brick.png";

    // ball and paddle dimensions
    private static final int BALL_RADIUS = 20;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 15;
    private static final int BALL_SPEED = 200;
    private static final int LIVES_SQUARE_SIZE = 20;

    // brick dimensions
    private static final int DEFAULT_ROW_OF_BRICKS = 7;
    private static final int DEFAULT_BRICK_PER_ROW = 8;
    private static final int BRICK_HEIGHT = 15;

    // lives constants
    private static final int DEFAULT_LIVES = 3;

    // todo figure out if needs to be documented
    public static final int MAX_BEHAVIORS_PER_BRICK = 3;
    private static final int BEHAVIOR_AMOUNT = 5;
    private static final SpecialBehaviors[] allBehaviors = new SpecialBehaviors[BEHAVIOR_AMOUNT];

    private ImageReader imageReader;
    private SoundReader soundReader;
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


    public BrickerGameManager() {
        super(TITLE, new Vector2(WIDTH, HEIGHT));
        this.windowDimensions = new Vector2(WIDTH, HEIGHT);
        this.bricksPerRow = DEFAULT_BRICK_PER_ROW;
        this.rowsOfBricks = DEFAULT_ROW_OF_BRICKS;
        this.lives = new Counter(DEFAULT_LIVES);
    }

    public BrickerGameManager(Vector2 windowDimensions, int bricksPerRow, int rowsOfBricks) {
        super(TITLE, windowDimensions);
        this.windowDimensions = windowDimensions;
        this.rowsOfBricks = rowsOfBricks;
        this.bricksPerRow = bricksPerRow;
        this.lives = new Counter(DEFAULT_LIVES);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        // initialization
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.brickCounter = new Counter(this.bricksPerRow * this.rowsOfBricks);
        this.screenCenter = windowDimensions.mult(0.5f);

        this.gameObjectFactory = new GameObjectFactory(this, imageReader, soundReader, inputListener,
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



        //todo refactor this. this creation of life counter breaks encapsulation. none of the objects
        // should add themselves to the game.    once done need to handle the case of new game with more or
        // less lives than default
        Vector2 livesTopLeftCorner = new Vector2(LIVES_INDENT_SIZE, windowDimensions.y() - LIVES_SQUARE_SIZE);
        Vector2 livesDimensions = new Vector2((LIVES_SQUARE_SIZE + BUFFER) * DEFAULT_LIVES,
                LIVES_SQUARE_SIZE);

        this.lifeCounter = (LifeCounter) gameObjectFactory.createLifeCounter(livesTopLeftCorner,
                livesDimensions,
                imageReader, lives, LIVES_SQUARE_SIZE, BUFFER, gameObjects());

        createBehaviors();

        GameObject[][] bricks = new GameObject[rowsOfBricks][bricksPerRow];
        createBricks(bricks);
        addBricks(bricks);

    }

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

    private void createBehaviors(){
        BehaviorFactory behaviorFactory = new BehaviorFactory(gameObjects(), gameObjectFactory,
                windowDimensions, lifeCounter);
        allBehaviors[0] = behaviorFactory.createExtraPuck(BALL_RADIUS);
        allBehaviors[1] = behaviorFactory.createExtraPaddle(new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT));
    }

    private void createBricks(GameObject[][] listOfBricks) {
        Renderable brickImage = imageReader.readImage(BRICK_PATH, false);
        CollisionStrategy collisionStrategy = new SpecialCollisionStrategy(this, gameObjects(),
                gameObjectFactory, windowDimensions, BALL_RADIUS, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                lifeCounter, (ObjectTracker)objectTracker, allBehaviors);

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

    //todo check the problem of pressing W and the counters not resetting
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


    public static void main(String[] args) {
        BrickerGameManager game;
        Vector2 screen = new Vector2(WIDTH, HEIGHT);

        if (args.length == 2) {
            game = new BrickerGameManager(screen, Integer.parseInt(args[0]),
                    Integer.parseInt(args[1]));
        } else {
            game = new BrickerGameManager();
        }
        game.run();
    }
}


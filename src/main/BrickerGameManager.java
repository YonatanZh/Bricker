package main;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.GameObjectFactory;
import gameobjects.LifeCounter;
import gameobjects.Paddle;

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
    private static final String LIFE_HEART_PATH = "assets/heart.png";
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

    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private WindowController windowController;
    private final Vector2 windowDimensions;
    private GameObjectFactory gameObjectFactory;
    private GameObject ball;
    private GameObject paddle;
    private LifeCounter lifeCounter;
    private int lives;
    private final int rowsOfBricks;
    private final int bricksPerRow;
    private Counter brickCounter;
    private Vector2 screenCenter;


    public BrickerGameManager() {
        super(TITLE, new Vector2(WIDTH, HEIGHT));
        this.windowDimensions = new Vector2(WIDTH, HEIGHT);
        this.bricksPerRow = DEFAULT_BRICK_PER_ROW;
        this.rowsOfBricks = DEFAULT_ROW_OF_BRICKS;
        this.lives = DEFAULT_LIVES;
    }

    public BrickerGameManager(Vector2 windowDimensions, int bricksPerRow, int rowsOfBricks) {
        super(TITLE, windowDimensions);
        this.windowDimensions = windowDimensions;
        this.rowsOfBricks = rowsOfBricks;
        this.bricksPerRow = bricksPerRow;
        this.lives = DEFAULT_LIVES;
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

        this.gameObjectFactory = new GameObjectFactory(this ,imageReader, soundReader, inputListener,
                windowDimensions);



        GameObject background = gameObjectFactory.createBackground(BACKGROUND_PATH);
        gameObjects().addGameObject(background, Layer.BACKGROUND);

        GameObject[] walls = gameObjectFactory.createWalls(WALL_WIDTH);
        addWalls(walls);

        this.ball = gameObjectFactory.createBall(BALL_PATH, BALL_SOUND_PATH, BALL_RADIUS, screenCenter);
        setBallVelocity(ball);
        gameObjects().addGameObject(this.ball);

        Vector2 paddleSize = new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT);
        Vector2 paddlePosition = new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 50);
        this.paddle = gameObjectFactory.createPaddle(PADDLE_PATH, paddleSize, paddlePosition);
        gameObjects().addGameObject(this.paddle);

        GameObject[][] bricks = new GameObject[rowsOfBricks][bricksPerRow];
        bricks = gameObjectFactory.createBrick(bricks, BRICK_PATH, WALL_WIDTH, BRICK_HEIGHT, BUFFER,
                gameObjects(),
                this.bricksPerRow, this.rowsOfBricks, this.brickCounter);
        addBricks(bricks);

        Renderable ballImage = imageReader.readImage(LIFE_HEART_PATH, true);
        Vector2 livesTopLeftCorner = new Vector2(LIVES_INDENT_SIZE, windowDimensions.y() - LIVES_SQUARE_SIZE);
        Vector2 livesDimensions = new Vector2((LIVES_SQUARE_SIZE + BUFFER) * DEFAULT_LIVES, LIVES_SQUARE_SIZE);
        this.lifeCounter = (LifeCounter) gameObjectFactory.createLifeCounter(livesTopLeftCorner, livesDimensions,
                ballImage, DEFAULT_LIVES, LIVES_SQUARE_SIZE, BUFFER ,gameObjects());



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

    private void resetBallAfterLoss(){
        lives--;
        this.lifeCounter.loseLife();
        checkEndGame();
        gameObjects().removeGameObject(ball);
        this.ball = gameObjectFactory.createBall(BALL_PATH, BALL_SOUND_PATH, BALL_RADIUS, screenCenter);
        setBallVelocity(ball);
        gameObjects().addGameObject(ball);
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
            lives = DEFAULT_LIVES;
            this.windowController.resetGame();
        } else {
            windowController.closeWindow();
        }
    }

    private void checkEndGame() {
        if (lives == 0) {
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

    public Counter getBrickCounter() {
        return brickCounter;
    }
}


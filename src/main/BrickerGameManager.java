package main;

import brick_strategies.BasicCollisionStrategy;
import brick_strategies.CollisionStrategy;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameobjects.Ball;
import gameobjects.Brick;
import gameobjects.Paddle;

import java.util.Random;

public class BrickerGameManager extends GameManager {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final Vector2 WINDOW_DIMENSIONS = new Vector2(WIDTH, HEIGHT);
    private static final String TITLE = "Bricker";
    private static final Vector2 DEFAULT_BALL_SIZE = new Vector2(20, 20);
    private static final Vector2 DEFAULT_PADDLE_SIZE = new Vector2(100, 15);
    private static final int WALL_WIDTH = 5;
    private static final int BALL_SPEED = 200;
    private static final int DEFAULT_ROW_OF_BRICKS = 7;
    private static final int DEFAULT_BRICK_PER_ROW = 8;
    private static final int BRICK_HEIGHT = 15;
    private static final int BUFFER = 5;
    private static final int DEFAULT_LIFES = 3;
    private static final String PROMPT = "You lose! Play again?";

    private int lifes;
    private GameObject ball;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private int rowsOfBricks;
    private int bricksPerRow;
    private WindowController windowController;


    public BrickerGameManager() {
        super(TITLE, WINDOW_DIMENSIONS);
        this.bricksPerRow = DEFAULT_BRICK_PER_ROW;
        this.rowsOfBricks = DEFAULT_ROW_OF_BRICKS;
        this.lifes = DEFAULT_LIFES;
    }

    public BrickerGameManager(int bricksPerRow, int rowsOfBricks) {
        super(TITLE, WINDOW_DIMENSIONS);

        this.rowsOfBricks = rowsOfBricks;
        this.bricksPerRow = bricksPerRow;
        this.lifes = DEFAULT_LIFES;
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        this.windowController = windowController;
        this.imageReader = imageReader;
        this.soundReader = soundReader;

        Vector2 windowDimensions = windowController.getWindowDimensions();
        createBall(imageReader, soundReader, windowDimensions);
        createPaddle(imageReader, inputListener, windowDimensions);
        createWalls(windowDimensions);
        createBackground(imageReader, windowDimensions);
        createBricks(imageReader, windowDimensions);

    }

    private void createBall(ImageReader imageReader, SoundReader soundReader, Vector2 windowDimensions) {
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        this.ball = new Ball(Vector2.ZERO, DEFAULT_BALL_SIZE, ballImage, collisionSound);
        ball.setCenter(windowDimensions.mult(0.5f));

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

        gameObjects().addGameObject(ball);
    }

    private void createPaddle(ImageReader imageReader, UserInputListener inputListener,
                              Vector2 windowDimensions) {
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        GameObject paddle = new Paddle(Vector2.ZERO, DEFAULT_PADDLE_SIZE, paddleImage, inputListener);
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 30));
        gameObjects().addGameObject(paddle);
    }

    private void createWalls(Vector2 windowDimensions) {
        createWall(Vector2.ZERO, new Vector2(WALL_WIDTH, windowDimensions.y()));
        createWall(new Vector2(windowDimensions.x() - WALL_WIDTH, 0),
                new Vector2(WALL_WIDTH, windowDimensions.y()));
        createWall(Vector2.ZERO, new Vector2(windowDimensions.x(), WALL_WIDTH));
    }

    private void createWall(Vector2 position, Vector2 size) {
//        RectangleRenderable rec = new RectangleRenderable(Color.black);
        GameObject wall = new GameObject(position, size, null);
        gameObjects().addGameObject(wall);
    }

    private void createBackground(ImageReader imageReader, Vector2 windowDimensions) {
        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", true);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void createBricks(ImageReader imageReader, Vector2 windowDimensions) {
        Renderable brickImage = imageReader.readImage("assets/brick.png", false);
        BasicCollisionStrategy bcs = new BasicCollisionStrategy(this);

        int distFromWall = (WALL_WIDTH * 2) + 1;
        int allBufferSize = (this.bricksPerRow - 1) * BUFFER;
        float brickWidth = (windowDimensions.x() - distFromWall - allBufferSize) / this.bricksPerRow;
        for (int i = 1; i <= this.rowsOfBricks; i++) {
            createRow(brickWidth, i, brickImage, bcs);
        }
    }

    private void createRow(float brickWidth, int rowIndex, Renderable brickImage, CollisionStrategy strat) {
        Vector2 brickDimension = new Vector2(brickWidth, BRICK_HEIGHT);
        for (int i = 0; i < this.bricksPerRow; i++) {
            Vector2 topLeft = new Vector2( (i * (BUFFER + brickWidth)) + WALL_WIDTH,
                    rowIndex * (BRICK_HEIGHT + BUFFER) + WALL_WIDTH);
            GameObject brick = new Brick(topLeft, brickDimension, brickImage, strat);
            gameObjects().addGameObject(brick);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkPaddleWallCollision();
        if(isBallOutOfBounds()) {
            lifes --;
            checkEndGame();
            gameObjects().removeGameObject(ball);
            createBall(imageReader, soundReader, WINDOW_DIMENSIONS);
        }
    }

    private boolean isBallOutOfBounds() {
        return ball.getCenter().y() > WINDOW_DIMENSIONS.y();
    }

    private void checkEndGame() {
        if (lifes == 0) {
            if (windowController.openYesNoDialog(PROMPT)) {
                lifes = DEFAULT_LIFES;
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        }
    }

    //todo reformat this EDEN
    private void checkPaddleWallCollision() {
        for (GameObject obj : gameObjects().objectsInLayer(Layer.DEFAULT)) {
            if (obj instanceof Paddle) {
                if (obj.getTopLeftCorner().x() < 0) {
                    obj.setTopLeftCorner(new Vector2(0, obj.getTopLeftCorner().y()));
                } else if ((obj.getTopLeftCorner().x() >
                        (WINDOW_DIMENSIONS.x() - obj.getDimensions().x()))) {
                    obj.setTopLeftCorner(new Vector2(WINDOW_DIMENSIONS.x() - obj.getDimensions().x(),
                            obj.getTopLeftCorner().y()));
                }
            }
        }
//            for (GameObject obj : gameObjects().objectsInLayer(Layer.DEFAULT)) {
//                if (obj instanceof Paddle) {
//                    float newX = Math.max(0, Math.min(WINDOW_DIMENSIONS.x() - obj.getDimensions().x(), obj.getTopLeftCorner().x()));
//                    obj.setTopLeftCorner(new Vector2(newX, obj.getTopLeftCorner().y()));
//                }
//            }
//        }
    }

    public void deleteObject(GameObject thisObj) {
        gameObjects().removeGameObject(thisObj);
    }

    public static void main(String[] args) {
        BrickerGameManager game;

        if (args.length == 2) {
            game = new BrickerGameManager(Integer.parseInt(args[0]),
                    Integer.parseInt(args[1]));
        } else {
            game = new BrickerGameManager();
        }
        game.run();
    }
}


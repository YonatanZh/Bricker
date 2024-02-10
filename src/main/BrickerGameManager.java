package main;

import brick_strategies.BasicCollisionStrategy;
import brick_strategies.CollisionStrategy;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameobjects.Ball;
import gameobjects.Brick;
import gameobjects.GameObjectFactory;
import gameobjects.Paddle;

import java.util.Random;

public class BrickerGameManager extends GameManager {

    // screen dimensions
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int WALL_WIDTH = 5;
    private static final int BUFFER = 5;

    // game Strings
    private static final String TITLE = "Bricker";
    private static final String LOSE_PROMPT = "You lose! Play again?";

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

    // brick dimensions
    private static final int DEFAULT_ROW_OF_BRICKS = 7;
    private static final int DEFAULT_BRICK_PER_ROW = 8;
    private static final int BRICK_HEIGHT = 15;

    // lives constants
    private static final int DEFAULT_LIFES = 3;

    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private WindowController windowController;
    private final Vector2 windowDimensions;
    private GameObjectFactory gameObjectFactory;
    private GameObject ball;
    private GameObject paddle;
    private int lives;
    private final int rowsOfBricks;
    private final int bricksPerRow;


    public BrickerGameManager() {
        super(TITLE, new Vector2(WIDTH, HEIGHT));
        this.windowDimensions = new Vector2(WIDTH, HEIGHT);
        this.bricksPerRow = DEFAULT_BRICK_PER_ROW;
        this.rowsOfBricks = DEFAULT_ROW_OF_BRICKS;
        this.lives = DEFAULT_LIFES;
    }

    public BrickerGameManager(Vector2 windowDimensions, int bricksPerRow, int rowsOfBricks) {
        super(TITLE, windowDimensions);
        this.windowDimensions = windowDimensions;
        this.rowsOfBricks = rowsOfBricks;
        this.bricksPerRow = bricksPerRow;
        this.lives = DEFAULT_LIFES;
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


        this.gameObjectFactory = new GameObjectFactory(imageReader, soundReader, inputListener,
                windowDimensions, gameObjects());

        // creating game objects
        GameObject background = gameObjectFactory.createBackground(BACKGROUND_PATH);
        GameObject [] walls = gameObjectFactory.createWalls(WALL_WIDTH);
        this.ball = gameObjectFactory.createBall(BALL_PATH, BALL_SOUND_PATH, BALL_RADIUS, BALL_SPEED);
        this.paddle = gameObjectFactory.createPaddle(PADDLE_PATH, PADDLE_WIDTH, PADDLE_HEIGHT);
        GameObject [][] bricks = new GameObject[rowsOfBricks][bricksPerRow];
        bricks = gameObjectFactory.createBrick(bricks, BRICK_PATH, WALL_WIDTH, BRICK_HEIGHT, BUFFER, this,
                this.bricksPerRow, this.rowsOfBricks);

        // adding elements
        gameObjects().addGameObject(background, Layer.BACKGROUND);
        addWalls(walls);
        gameObjects().addGameObject(this.ball);
        gameObjects().addGameObject(this.paddle);
        addBricks(bricks);

    }

    public void deleteObject(GameObject thisObj) {
        gameObjects().removeGameObject(thisObj);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkPaddleWallCollision();
        if(isBallOutOfBounds()) {
            lives--;
            checkEndGame();
            gameObjects().removeGameObject(ball);
            this.ball = gameObjectFactory.createBall(BALL_PATH, BALL_SOUND_PATH, BALL_RADIUS, BALL_SPEED);
            gameObjects().addGameObject(ball);
        }
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

    private void checkEndGame() {
        if (lives == 0) {
            if (windowController.openYesNoDialog(LOSE_PROMPT)) {
                lives = DEFAULT_LIFES;
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
                float x = obj.getTopLeftCorner().x();
                float maxX = windowDimensions.x() - obj.getDimensions().x();

                obj.setTopLeftCorner(new Vector2(Math.max(0, Math.min(x, maxX)), obj.getTopLeftCorner().y()));
            }
        }

//        for (GameObject obj : gameObjects().objectsInLayer(Layer.DEFAULT)) {
//            if (obj instanceof Paddle) {
//                if (obj.getTopLeftCorner().x() < 0) {
//                    obj.setTopLeftCorner(new Vector2(0, obj.getTopLeftCorner().y()));
//                } else if ((obj.getTopLeftCorner().x() >
//                        (windowDimensions.x() - obj.getDimensions().x()))) {
//                    obj.setTopLeftCorner(new Vector2(WINDOW_DIMENSIONS.x() - obj.getDimensions().x(),
//                            obj.getTopLeftCorner().y()));
//                }
//            }
//        }
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


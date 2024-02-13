package gameobjects;

import brick_strategies.BasicCollisionStrategy;
import brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import main.BrickerGameManager;

import java.util.Random;

public class GameObjectFactory {

    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final GameObjectCollection gameObjects;
    private final Random rand;

    public GameObjectFactory(ImageReader imageReader, SoundReader soundReader,
                             UserInputListener inputListener, Vector2 windowDimensions,
                             GameObjectCollection gameObjects) {

        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjects;
        this.rand = new Random();
    }

    public GameObject createBackground(String imagePath) {
        Renderable backgroundImage = imageReader.readImage(imagePath, true);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        return background;
    }

    public GameObject[] createWalls(int wallWidth) {
        GameObject[] walls = new GameObject[3];
        walls[0] = createWall(Vector2.ZERO, new Vector2(wallWidth, windowDimensions.y()));
        walls[1] = createWall(new Vector2(windowDimensions.x() - wallWidth, 0),
                new Vector2(wallWidth, windowDimensions.y()));
        walls[2] = createWall(Vector2.ZERO, new Vector2(windowDimensions.x(), wallWidth));
        return walls;
    }

    public GameObject createWall(Vector2 position, Vector2 size) {
        return new GameObject(position, size, null);
    }

    public GameObject createBall(String ballImagePath, String ballSoundPath, int ballRadius,
                                 int ballSpeed) {
        Renderable ballImage = imageReader.readImage(ballImagePath, true);
        Sound collisionSound = soundReader.readSound(ballSoundPath);
        Vector2 ballSize = new Vector2(ballRadius, ballRadius);
        GameObject ball = new Ball(Vector2.ZERO, ballSize, ballImage, collisionSound);
        ball.setCenter(windowDimensions.mult(0.5f));

        float velocityX = ballSpeed;
        float velocityY = ballSpeed;
        if (rand.nextBoolean()) {
            velocityX = -velocityX;
        }
        if (rand.nextBoolean()) {
            velocityY = -velocityY;
        }
        ball.setVelocity(new Vector2(velocityX, velocityY));
        return ball;
    }

    public GameObject createPaddle(String paddleImagePath, int paddleWidth, int paddleHeight) {
        Renderable paddleImage = imageReader.readImage(paddleImagePath, true);
        Vector2 paddleSize = new Vector2(paddleWidth, paddleHeight);
        GameObject paddle = new Paddle(Vector2.ZERO, paddleSize, paddleImage, inputListener);
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 50));
        return paddle;
    }

    public GameObject [][] createBrick(GameObject [][] listOfBricks, String brickImagePath, int wallWidth,
                            int brickHeight, int bufferSize, BrickerGameManager gameManager,
                            int bricksPerRow, int rowsOfBricks) {
        Renderable brickImage = imageReader.readImage(brickImagePath, false);
        BasicCollisionStrategy bcs = new BasicCollisionStrategy(gameManager);
        Counter brickCounter = gameManager.getBrickCounter();

        int distFromWall = (wallWidth * 2) + 1;
        int allBufferSize = (bricksPerRow - 1) * bufferSize;
        float brickWidth = (windowDimensions.x() - distFromWall - allBufferSize) / bricksPerRow;
        for (int i = 1; i <= rowsOfBricks; i++) {
            listOfBricks[i-1] = createBrickRow(brickWidth, brickHeight, i, bricksPerRow, wallWidth,
                    bufferSize, brickImage, bcs, brickCounter);
        }
        return listOfBricks;
    }

    private GameObject[] createBrickRow(float brickWidth, int brickHeight, int rowIndex, int bricksPerRow,
                                int wallWidth, int bufferSize, Renderable brickImage,
                                CollisionStrategy strat, Counter brickCounter) {
        Vector2 brickDimension = new Vector2(brickWidth, brickHeight);
        GameObject [] row = new GameObject[bricksPerRow];
        for (int i = 0; i < bricksPerRow; i++) {

            Vector2 topLeft = new Vector2( (i * (bufferSize + brickWidth)) + wallWidth,
                    rowIndex * (brickHeight + bufferSize) + wallWidth);
            GameObject brick = new Brick(topLeft, brickDimension, brickImage, strat, brickCounter);
            row[i] = brick;
        }
        return row;
    }

    public GameObject createLifeCounter(String lifeImagePath, int lives, GameObjectCollection gameObjects) {
        Renderable ballImage = imageReader.readImage(lifeImagePath, true);
        return new LifeCounter(new Vector2(50, windowDimensions.y() - 20), new Vector2(20*lives, 20), ballImage,lives,gameObjects);
    }

}
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

import java.util.Random;

public class GameObjectFactory {

    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final Random rand;

    public GameObjectFactory(ImageReader imageReader, SoundReader soundReader,
                             UserInputListener inputListener, Vector2 windowDimensions) {

        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
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

    public GameObject createBall(String ballImagePath, String ballSoundPath, float ballRadius,
                                 Vector2 position) {
        Renderable ballImage = imageReader.readImage(ballImagePath, true);
        Sound collisionSound = soundReader.readSound(ballSoundPath);
        Vector2 ballSize = new Vector2(ballRadius, ballRadius);
        GameObject ball = new Ball(Vector2.ZERO, ballSize, ballImage, collisionSound);
        ball.setCenter(position);
        return ball;
    }

    public GameObject createPaddle(String paddleImagePath, Vector2 paddleSize, Vector2 position) {
        Renderable paddleImage = imageReader.readImage(paddleImagePath, true);
        GameObject paddle = new Paddle(position, paddleSize, paddleImage, inputListener);
        paddle.setCenter(position);
        return paddle;
    }

    public GameObject createTempPaddle(String paddleImagePath, Vector2 paddleSize, Vector2 position,
                                       GameObjectCollection gameObjects) {
        Renderable paddleImage = imageReader.readImage(paddleImagePath, true);
        CollisionStrategy basicCollisionStrategy = new BasicCollisionStrategy(gameObjects);
        GameObject paddle = new DisappearingPaddle(position, paddleSize, paddleImage, inputListener,
                basicCollisionStrategy);
        paddle.setCenter(position);
        return paddle;
    }

    public GameObject[][] createBrick(GameObject[][] listOfBricks, String brickImagePath, int wallWidth,
                                      int brickHeight, int bufferSize, GameObjectCollection gameObjects,
                                      int bricksPerRow, int rowsOfBricks, Counter brickCounter) {
        Renderable brickImage = imageReader.readImage(brickImagePath, false);
        CollisionStrategy bcs = new BasicCollisionStrategy(gameObjects);

        int distFromWall = (wallWidth * 2) + 1;
        int allBufferSize = (bricksPerRow - 1) * bufferSize;
        float brickWidth = (windowDimensions.x() - distFromWall - allBufferSize) / bricksPerRow;
        for (int i = 1; i <= rowsOfBricks; i++) {
            listOfBricks[i - 1] = createBrickRow(brickWidth, brickHeight, i, bricksPerRow, wallWidth,
                    bufferSize, brickImage, bcs, brickCounter);
        }
        return listOfBricks;
    }

    private GameObject[] createBrickRow(float brickWidth, int brickHeight, int rowIndex, int bricksPerRow,
                                        int wallWidth, int bufferSize, Renderable brickImage,
                                        CollisionStrategy strat, Counter brickCounter) {
        Vector2 brickDimension = new Vector2(brickWidth, brickHeight);
        GameObject[] row = new GameObject[bricksPerRow];
        for (int i = 0; i < bricksPerRow; i++) {

            Vector2 topLeft = new Vector2((i * (bufferSize + brickWidth)) + wallWidth,
                    rowIndex * (brickHeight + bufferSize) + wallWidth);
            GameObject brick = new Brick(topLeft, brickDimension, brickImage, strat, brickCounter);
            row[i] = brick;
        }
        return row;
    }

    //todo: create graphical functuion, create numeric function make the create life counter use them.


    public GameObject createLifeCounter(String lifeImagePath, int lives, GameObjectCollection gameObjects) {
        Renderable ballImage = imageReader.readImage(lifeImagePath, true);
        return new LifeCounter(new Vector2(50, windowDimensions.y() - 20), new Vector2(20 * lives, 20),
                ballImage, lives, gameObjects);
    }

}

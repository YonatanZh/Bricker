package gameobjects;

import brick_strategies.BasicCollisionStrategy;
import brick_strategies.CollisionStrategy;
import brick_strategies.SpecialCollisionStrategy;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * A class representing a factory for game objects.
 */
public class GameObjectFactory {

    private final GameManager owner;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;

    /**
     * Creates a new GameObjectFactory object.
     * @param owner the game manager
     * @param imageReader the image reader
     * @param soundReader the sound reader
     * @param inputListener the input listener
     * @param windowDimensions the window dimensions
     */
    public GameObjectFactory(GameManager owner, ImageReader imageReader, SoundReader soundReader,
                             UserInputListener inputListener, Vector2 windowDimensions) {
        this.owner = owner;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Creates a new background object.
     * @param imagePath the path to the image of the background
     * @return a new background object
     */
    public GameObject createBackground(String imagePath) {
        Renderable backgroundImage = imageReader.readImage(imagePath, true);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        return background;
    }

    /**
     * Creates all wall objects.
     * @param wallWidth the width of the wall
     * @return an array of all walls
     */
    public GameObject[] createWalls(int wallWidth) {
        GameObject[] walls = new GameObject[3];
        walls[0] = createWall(Vector2.ZERO, new Vector2(wallWidth, windowDimensions.y()));
        walls[1] = createWall(new Vector2(windowDimensions.x() - wallWidth, 0),
                new Vector2(wallWidth, windowDimensions.y()));
        walls[2] = createWall(Vector2.ZERO, new Vector2(windowDimensions.x(), wallWidth));
        return walls;
    }

    /**
     * Creates a new wall object.
     * @param position the position of the wall
     * @param size the size of the wall
     * @return a new wall object
     */
    public GameObject createWall(Vector2 position, Vector2 size) {
        return new GameObject(position, size, null);
    }

    /**
     * Creates a new ball object.
     * @param ballImagePath the path to the image of the ball
     * @param ballSoundPath the path to the sound of the ball
     * @param ballRadius the radius of the ball
     * @param position the position of the ball
     * @return a new ball object
     */
    public GameObject createBall(String ballImagePath, String ballSoundPath, float ballRadius,
                                 Vector2 position) {
        Renderable ballImage = imageReader.readImage(ballImagePath, true);
        Sound collisionSound = soundReader.readSound(ballSoundPath);
        Vector2 ballSize = new Vector2(ballRadius, ballRadius);
        GameObject ball = new Ball(Vector2.ZERO, ballSize, ballImage, collisionSound);
        ball.setCenter(position);
        return ball;
    }

    /**
     * Creates a new paddle object.
     * @param paddleImagePath the path to the image of the paddle
     * @param paddleSize the size of the paddle
     * @param position the position of the paddle
     * @return a new paddle object
     */
    public GameObject createPaddle(String paddleImagePath, Vector2 paddleSize, Vector2 position) {
        Renderable paddleImage = imageReader.readImage(paddleImagePath, true);
        GameObject paddle = new Paddle(position, paddleSize, paddleImage, inputListener);
        paddle.setCenter(position);
        return paddle;
    }

    /**
     * Creates a new disappearing paddle object.
     * @param paddleImagePath the path to the image of the paddle
     * @param paddleSize the size of the paddle
     * @param position the position of the paddle
     * @param gameObjects a collection of all the game objects
     * @param paddleCounter the paddle counter
     * @return a new disappearing paddle object
     */
    public GameObject createDisappearingPaddle(String paddleImagePath, Vector2 paddleSize, Vector2 position,
                                               GameObjectCollection gameObjects, Counter paddleCounter) {
        Renderable paddleImage = imageReader.readImage(paddleImagePath, true);
        CollisionStrategy basicCollisionStrategy = new BasicCollisionStrategy(gameObjects);
        GameObject paddle = new DisappearingPaddle(position, paddleSize, paddleImage, inputListener,
                basicCollisionStrategy, paddleCounter);
        paddle.setCenter(position);
        return paddle;
    }

    //todo move this logic to the game manager - yonatan
    /**
     * Creates all brick objects.
     * @param listOfBricks an array of all bricks
     * @param brickImagePath the path to the image of the brick
     * @param wallWidth the width of the wall
     * @param brickHeight the height of the brick
     * @param bufferSize the buffer size between bricks
     * @param gameObjects a collection of all the game objects
     * @param bricksPerRow the number of bricks per row
     * @param rowsOfBricks the number of rows of bricks
     * @param brickCounter the brick counter
     * @param lifeCounter the life counter
     * @return an array of all bricks
     */
    public GameObject[][] createBrick(GameObject[][] listOfBricks, String brickImagePath, int wallWidth,
                                      int brickHeight, int bufferSize, GameObjectCollection gameObjects,
                                      int bricksPerRow, int rowsOfBricks, Counter brickCounter, LifeCounter lifeCounter) {
        Renderable brickImage = imageReader.readImage(brickImagePath, false);
        // todo figure out how to get rid of the magic numbers - yonatan
        CollisionStrategy bcs = new SpecialCollisionStrategy(owner, gameObjects, this, windowDimensions, 10,
                10,
                new Vector2(100, 15), lifeCounter);

        int distFromWall = (wallWidth * 2) + 1;
        int allBufferSize = (bricksPerRow - 1) * bufferSize;
        float brickWidth = (windowDimensions.x() - distFromWall - allBufferSize) / bricksPerRow;
        for (int i = 1; i <= rowsOfBricks; i++) {
            listOfBricks[i - 1] = createBrickRow(brickWidth, brickHeight, i, bricksPerRow, wallWidth,
                    bufferSize, brickImage, bcs, brickCounter);
        }
        return listOfBricks;
    }

    /**
     * Creates all brick objects.
     * @param brickWidth the width of the brick
     * @param brickHeight the height of the brick
     * @param rowIndex the index of the row
     * @param bricksPerRow the number of bricks per row
     * @param wallWidth the width of the wall
     * @param bufferSize the buffer size between bricks
     * @param brickImage the image of the brick
     * @param strat the collision strategy of the brick
     * @param brickCounter the brick counter
     * @return an array of all bricks
     */
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

    /**
     * Creates a new numeric life display object.
     * @param topLeftCorner the position of the top left corner of the life display
     * @param dimensions the dimensions of the life display
     * @param lives the life counter
     * @param gameObjects a collection of all the game objects
     * @return a new numeric life display object
     */
    public GameObject createNumericLifeDisplay(Vector2 topLeftCorner, Vector2 dimensions, Counter lives, GameObjectCollection gameObjects) {
        return new NumericLifeDisplay(topLeftCorner, dimensions, lives, gameObjects);
    }

    /**
     * Creates a new graphical life display object.
     * @param topLeftCorner the position of the top left corner of the life display
     * @param dimensions the dimensions of the life display
     * @param renderable that presents an image of the life
     * @param indent the indent between the lives
     * @param lives the life counter
     * @param maxLives the maximum number of lives
     * @param gameObjects a collection of all the game objects
     * @return a new graphical life display object
     */
    public GameObject createGraphicalLifeDisplay(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                                                 float indent, Counter lives, int maxLives,
                                                 GameObjectCollection gameObjects) {
        return new GraphicalLifeDisplay(topLeftCorner, dimensions, indent, lives, maxLives, gameObjects, renderable);
    }

    /**
     * Creates a new life counter object.
     * @param topLeftCorner the position of the top left corner of the life counter
     * @param dimensions the dimensions of the life counter
     * @param imageReader the image reader
     * @param lives the life counter
     * @param objectSize the size of the life
     * @param buffer the buffer between the lives
     * @param gameObjects a collection of all the game objects
     * @return a new life counter object
     */
    public GameObject createLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, ImageReader imageReader, Counter lives,  float objectSize,
                                        int buffer, GameObjectCollection gameObjects) {
        return new LifeCounter(topLeftCorner, dimensions, imageReader, lives, objectSize, buffer, gameObjects, this);
    }

    /**
     * Creates a new falling life object.
     * @param topLeftCorner the position of the top left corner of the life
     * @param dimensions the dimensions of the life
     * @param renderable that presents an image of the life
     * @param windowDimensions the window dimensions
     * @param gameObjects a collection of all the game objects
     * @param lifeCounter the life counter object
     * @return a new falling life object
     */
    public GameObject createFallingLife(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                                        Vector2 windowDimensions, GameObjectCollection gameObjects, LifeCounter lifeCounter) {
        return new FallingLife(topLeftCorner, dimensions, renderable, windowDimensions, gameObjects, lifeCounter);
    }

}
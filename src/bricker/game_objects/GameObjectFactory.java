package bricker.game_objects;

import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.CollisionStrategy;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import static bricker.main.Constants.*;

/**
 * A class for creating game objects.
 */
public class GameObjectFactory {

    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;

    /**
     * Constructs a new GameObjectFactory.
     *
     * @param imageReader      the image reader for reading images.
     * @param soundReader      the sound reader for reading sounds.
     * @param inputListener    the input listener for listening to user input.
     * @param windowDimensions the dimensions of the window.
     */
    public GameObjectFactory(ImageReader imageReader, SoundReader soundReader,
                             UserInputListener inputListener, Vector2 windowDimensions) {
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Creates a background game object.
     *
     * @param imagePath the path to the image of the background.
     * @return a background game object.
     */
    public GameObject createBackground(String imagePath) {
        Renderable backgroundImage = imageReader.readImage(imagePath, true);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        return background;
    }

    /**
     * Creates a wall game object.
     *
     * @param wallWidth the width of the wall.
     * @return an array of wall game objects.
     */
    public GameObject[] createWalls(int wallWidth) {
        GameObject[] walls = new GameObject[3];
        walls[0] = createWall(Vector2.ZERO, new Vector2(wallWidth, windowDimensions.y()));
        walls[1] = createWall(new Vector2(windowDimensions.x() - wallWidth, 0),
                new Vector2(wallWidth, windowDimensions.y()));
        walls[2] = createWall(Vector2.ZERO, new Vector2(windowDimensions.x(), wallWidth));
        return walls;
    }

    private GameObject createWall(Vector2 position, Vector2 size) {
        return new GameObject(position, size, null);
    }

    /**
     * Creates a ball game object.
     *
     * @param ballImagePath the path to the image of the ball.
     * @param ballSoundPath the path to the sound of the ball.
     * @param ballRadius    the radius of the ball.
     * @param position      the position of the ball.
     * @return a ball game object.
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
     * Creates a paddle game object.
     *
     * @param paddleImagePath the path to the image of the paddle.
     * @param paddleSize      the size of the paddle.
     * @param position        the position of the paddle.
     * @return a paddle game object.
     */
    public GameObject createPaddle(String paddleImagePath, Vector2 paddleSize, Vector2 position) {
        Renderable paddleImage = imageReader.readImage(paddleImagePath, true);
        GameObject paddle = new Paddle(position, paddleSize, paddleImage, inputListener);
        paddle.setCenter(position);
        return paddle;
    }

    /**
     * Creates a disappearing paddle game object.
     *
     * @param paddleImagePath the path to the image of the paddle.
     * @param paddleSize      the size of the paddle.
     * @param position        the position of the paddle.
     * @param gameObjects     the collection of game objects.
     * @param paddleCounter   the counter for the disappearing paddle.
     * @return a disappearing paddle game object.
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

    /**
     * Creates a brick game object.
     *
     * @param topLeftCorner     the top left corner of the brick.
     * @param dimensions        the dimensions of the brick.
     * @param renderable        the renderable object that will draw the brick.
     * @param collisionStrategy the collision strategy for the brick.
     * @param brickCounter      the counter for the bricks.
     * @return a brick game object.
     */
    public GameObject createBrick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                                  CollisionStrategy collisionStrategy, Counter brickCounter) {
        return new Brick(topLeftCorner, dimensions, renderable, collisionStrategy, brickCounter);
    }

    /**
     * Creates a numeric life counter game object.
     *
     * @param topLeftCorner the top left corner of the life counter.
     * @param dimensions    the dimensions of the life counter.
     * @param lives         the life counter
     * @return a numeric life counter game object.
     */
    public GameObject createNumericLifeDisplay(Vector2 topLeftCorner, Vector2 dimensions, Counter lives) {
        return new NumericLifeDisplay(topLeftCorner, dimensions, lives);
    }

    /**
     * Creates a graphical life counter game object.
     *
     * @param topLeftCorner the top left corner of the life counter.
     * @param dimensions    the dimensions of the life counter.
     * @param indent        the indent between the lives
     * @param lives         the life counter
     * @param maxLives      the maximum number of lives
     * @param gameObjects   a collection of all the game objects
     * @return a life counter game object.
     */
    public GameObject createGraphicalLifeDisplay(Vector2 topLeftCorner, Vector2 dimensions,
                                                 float indent, Counter lives, int maxLives,
                                                 GameObjectCollection gameObjects) {
        Renderable lifeImage = imageReader.readImage(LIFE_HEART_PATH, true);
        return new GraphicalLifeDisplay(topLeftCorner, dimensions, indent, lives, gameObjects, maxLives,
                lifeImage);
    }

    /**
     * Creates a life counter game object.
     *
     * @param topLeftCorner        the top left corner of the life counter.
     * @param dimensions           the dimensions of the life counter.
     * @param lives                the counter for the lives.
     * @param numericLifeCounter   the numeric life counter.
     * @param graphicalLives       the graphical life display.
     * @return a life counter game object.
     */
    public GameObject createLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Counter lives,
                                        NumericLifeDisplay numericLifeCounter,
                                        GraphicalLifeDisplay graphicalLives) {
        return new LifeCounter(topLeftCorner, dimensions, lives, numericLifeCounter,
                graphicalLives);
    }

    /**
     * Creates a falling life game object.
     *
     * @param topLeftCorner    the top left corner of the falling life.
     * @param dimensions       the dimensions of the falling life.
     * @param renderable       the renderable object that will draw the falling life.
     * @param windowDimensions the dimensions of the window.
     * @param gameObjects      the collection of game objects.
     * @param lifeCounter      the life counter.
     * @return a falling life game object.
     */
    public GameObject createFallingLife(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                                        Vector2 windowDimensions, GameObjectCollection gameObjects,
                                        LifeCounter lifeCounter) {
        return new FallingLife(topLeftCorner, dimensions, renderable, windowDimensions, gameObjects,
                lifeCounter);
    }

    /**
     * creates a game object that follows another game object.
     *
     * @param objToFollow           the object to follow.
     * @param deltaRelativeToObject the delta between the object and the object to follow.
     * @param dimensions            the dimensions of the tracker.
     * @param windowDimensions      the dimensions of the window.
     * @param owner                 the game manager that owns the object.
     * @param object                the object to follow.
     * @return a game object that follows another game object.
     */
    public GameObject createObjectTracker(GameObject objToFollow, Vector2 deltaRelativeToObject,
                                          Vector2 dimensions,
                                          Vector2 windowDimensions, GameManager owner, GameObject object) {
        return new ObjectTracker(objToFollow, deltaRelativeToObject, dimensions, windowDimensions, owner,
                object);
    }
}
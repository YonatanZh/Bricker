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

public class GameObjectFactory {

    private final GameManager owner;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;


    public GameObjectFactory(GameManager owner, ImageReader imageReader, SoundReader soundReader,
                             UserInputListener inputListener, Vector2 windowDimensions) {
        this.owner = owner;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
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

    public GameObject createDisappearingPaddle(String paddleImagePath, Vector2 paddleSize, Vector2 position,
                                               GameObjectCollection gameObjects, Counter paddleCounter) {
        Renderable paddleImage = imageReader.readImage(paddleImagePath, true);
        CollisionStrategy basicCollisionStrategy = new BasicCollisionStrategy(gameObjects);
//        Counter paddleCounter = new Counter(0);
        GameObject paddle = new DisappearingPaddle(position, paddleSize, paddleImage, inputListener,
                basicCollisionStrategy, paddleCounter);
        paddle.setCenter(position);
        return paddle;
    }

    public GameObject createBrick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                                  CollisionStrategy collisionStrategy, Counter brickCounter) {
        return new Brick(topLeftCorner, dimensions, renderable, collisionStrategy, brickCounter);
    }

    public GameObject createNumericLifeDisplay(Vector2 topLeftCorner, Vector2 dimensions, Counter lives,
                                               GameObjectCollection gameObjects) {
        return new NumericLifeDisplay(topLeftCorner, dimensions, lives, gameObjects);
    }

    public GameObject createGraphicalLifeDisplay(Vector2 topLeftCorner, Vector2 dimensions,
                                                 Renderable renderable,
                                                 float indent, Counter lives, int maxLives,
                                                 GameObjectCollection gameObjects) {
        return new GraphicalLifeDisplay(topLeftCorner, dimensions, indent, lives, maxLives, gameObjects,
                renderable);
    }

    public GameObject createLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, ImageReader imageReader,
                                        Counter lives, float objectSize,
                                        int buffer, GameObjectCollection gameObjects) {
        return new LifeCounter(topLeftCorner, dimensions, imageReader, lives, objectSize, buffer, gameObjects,
                this);
    }

    public GameObject createFallingLife(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                                        Vector2 windowDimensions, GameObjectCollection gameObjects,
                                        LifeCounter lifeCounter) {
        return new FallingLife(topLeftCorner, dimensions, renderable, windowDimensions, gameObjects,
                lifeCounter);
    }

    public  GameObject createObjectTracker(GameObject objToFollow, Vector2 deltaRelativeToObject, Vector2 dimensions,
                                          Vector2 windowDimensions, GameManager owner, GameObject object) {
        return new ObjectTracker(objToFollow, deltaRelativeToObject, dimensions, windowDimensions, owner, object);
    }
}
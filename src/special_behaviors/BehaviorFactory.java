package special_behaviors;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.GameObjectFactory;
import gameobjects.LifeCounter;

/**
 * A class representing a factory for special behaviors.
 */
public class BehaviorFactory {

    private GameManager owner;
    private final GameObjectCollection gameObjects;
    private final GameObjectFactory gameObjectFactory;
    private final Vector2 windowDimensions;

    /**
     * Creates a new BehaviorFactory object.
     * @param owner the game manager
     * @param gameObjects a collection of all the game objects
     * @param gameObjectFactory the game object factory
     * @param windowDimensions the window dimensions
     * @param lifeCounter the life counter
     */
    public BehaviorFactory(GameManager owner, GameObjectCollection gameObjects,
                           GameObjectFactory gameObjectFactory, Vector2 windowDimensions, LifeCounter lifeCounter) {
        this.owner = owner;
        this.gameObjects = gameObjects;
        this.gameObjectFactory = gameObjectFactory;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Creates a new ExtraPaddle object.
     * @param size the size of the paddle
     * @param paddleCounter the paddle counter
     * @return a new ExtraPaddle object
     */
    public SpecialBehaviors createExtraPaddle(Vector2 size, Counter paddleCounter) {
        return new ExtraPaddle(gameObjects, gameObjectFactory, size, windowDimensions, paddleCounter);
    }

    /**
     * Creates a new ExtraPuck object.
     * @param ballRadius the radius of the puck
     * @param ballSpeed the speed of the puck
     * @param position the position of the puck
     * @return a new ExtraPuck object
     */
    public SpecialBehaviors createExtraPuck(int ballRadius, int ballSpeed, Vector2 position) {
        return new ExtraPuck(gameObjects, gameObjectFactory, ballRadius, ballSpeed, position);
    }

    /**
     * Creates a new CameraChange object.
     * @param objToFollow the object to follow
     * @return a new CameraChange object
     */
    public SpecialBehaviors createCameraChange(GameObject objToFollow, GameManager owner) {
        return new CameraChange(objToFollow, Vector2.ZERO, windowDimensions.mult(1.2f), windowDimensions, owner);
    }

    /**
     * Creates a new ExtraLife object.
     * @param topLeftCorner the position of the top left corner of the life
     * @param dimensions the dimensions of the life
     * @return a new ExtraLife object
     */
    public SpecialBehaviors createExtraLife(Vector2 topLeftCorner, Vector2 dimensions,
                                            Vector2 windowDimensions, GameObjectCollection gameObjects,
                                            GameObjectFactory gameObjectFactory, LifeCounter lifeCounter) {
        return new ExtraLife(topLeftCorner, dimensions, lifeCounter, windowDimensions, gameObjects, gameObjectFactory);
    }
}

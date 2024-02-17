package special_behaviors;

import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;
import gameobjects.GameObjectFactory;
import gameobjects.LifeCounter;

/**
 * A class representing a special behavior that adds an extra life to the game.
 */
public class ExtraLife implements SpecialBehaviors {
    private final Vector2 topLeftCorner;
    private final Vector2 dimensions;
    private static final Vector2 VELOCITY = new Vector2(0, 100);
    private final Vector2 windowDimensions;
    private final GameObjectCollection gameObjects;
    private final GameObjectFactory gameObjectFactory;
    private final LifeCounter lifeCounter;

    /**
     * Creates a new ExtraLife object.
     * @param topLeftCorner the position of the top left corner of the life
     * @param dimensions the dimensions of the life
     * @param lifeCounter the life counter
     * @param windowDimensions the window dimensions
     * @param gameObjects a collection of all the game objects
     * @param gameObjectFactory the game object factory
     */
    public ExtraLife(Vector2 topLeftCorner, Vector2 dimensions, LifeCounter lifeCounter,
                     Vector2 windowDimensions, GameObjectCollection gameObjects, GameObjectFactory gameObjectFactory) {
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjects;
        this.gameObjectFactory = gameObjectFactory;
        this.lifeCounter = lifeCounter;
    }

    /**
     * Adds an extra life to the game.
     */
    public void behave() {
        lifeCounter.createLife(gameObjectFactory, topLeftCorner, dimensions, VELOCITY, windowDimensions, gameObjects);
    }
}

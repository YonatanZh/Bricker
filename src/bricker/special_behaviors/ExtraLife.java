package bricker.special_behaviors;

import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;
import bricker.game_objects.GameObjectFactory;
import bricker.game_objects.LifeCounter;

import static bricker.main.Constants.HEART_VELOCITY;

/**
 * A SpecialBehavior that creates an extra life when the puck collides with the paddle.
 */
public class ExtraLife implements SpecialBehaviors {
    private final Vector2 dimensions;
    private final Vector2 windowDimensions;
    private final GameObjectCollection gameObjects;
    private final GameObjectFactory gameObjectFactory;
    private final LifeCounter lifeCounter;

    /**
     * Creates a new ExtraLife.
     *
     * @param dimensions         the dimensions of the life.
     * @param lifeCounter        the life counter.
     * @param windowDimensions   the dimensions of the window.
     * @param gameObjects        the collection of game objects.
     * @param gameObjectFactory the factory for creating game objects.
     */
    public ExtraLife(Vector2 dimensions, LifeCounter lifeCounter,
                     Vector2 windowDimensions, GameObjectCollection gameObjects, GameObjectFactory gameObjectFactory) {
        this.dimensions = dimensions;
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjects;
        this.gameObjectFactory = gameObjectFactory;
        this.lifeCounter = lifeCounter;
    }

    /**
     * Creates a new life at the given position.
     *
     * @param position1 the position of the puck.
     * @param position2 the position of the paddle.
     */
    @Override
    public void behave(Vector2 position1, Vector2 position2) {
        lifeCounter.createLife(gameObjectFactory, position1, dimensions, HEART_VELOCITY, windowDimensions, gameObjects);
    }
}

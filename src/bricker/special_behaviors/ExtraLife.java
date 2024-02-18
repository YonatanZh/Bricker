package bricker.special_behaviors;

import bricker.gameobjects.FallingLife;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.gameobjects.GameObjectFactory;
import bricker.gameobjects.LifeCounter;

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
    private final Renderable lifeImage;

    /**
     * Creates a new ExtraLife.
     *
     * @param dimensions         the dimensions of the life.
     * @param lifeCounter        the life counter.
     * @param windowDimensions   the dimensions of the window.
     * @param gameObjects        the collection of game objects.
     * @param gameObjectFactory the factory for creating game objects.
     */
    public ExtraLife(Vector2 dimensions, Renderable lifeImage, LifeCounter lifeCounter,
                     Vector2 windowDimensions, GameObjectCollection gameObjects,
                     GameObjectFactory gameObjectFactory) {
        this.dimensions = dimensions;
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjects;
        this.gameObjectFactory = gameObjectFactory;
        this.lifeCounter = lifeCounter;
        this.lifeImage = lifeImage;
    }

    /**
     * This method is used to define the behavior of a special object.
     *
     * @param position1 The location to activate the behavior.
     * @param position2 The location to activate the behavior.
     */
    @Override
    public void behave(Vector2 position1, Vector2 position2) {
        FallingLife life = (FallingLife) gameObjectFactory.createFallingLife(position1, dimensions, lifeImage,
                windowDimensions, gameObjects, lifeCounter);
        life.setCenter(position1);
        life.setVelocity(HEART_VELOCITY);
        gameObjects.addGameObject(life, danogl.collisions.Layer.DEFAULT);
    }
}

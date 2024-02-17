package bricker.special_behaviors;

import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.game_objects.GameObjectFactory;
import bricker.game_objects.LifeCounter;
import bricker.game_objects.ObjectTracker;

/**
 * This class is used to create the different special behaviors that can be added to the game.
 */
public class BehaviorFactory {
    private final GameObjectCollection gameObjects;
    private final GameObjectFactory gameObjectFactory;
    private final Vector2 windowDimensions;

    /**
     * Constructor for the BehaviorFactory class.
     *
     * @param gameObjects       The collection of game objects.
     * @param gameObjectFactory The factory used to create game objects.
     * @param windowDimensions  The dimensions of the window.
     */
    public BehaviorFactory(GameObjectCollection gameObjects,
                           GameObjectFactory gameObjectFactory, Vector2 windowDimensions) {
        this.gameObjects = gameObjects;
        this.gameObjectFactory = gameObjectFactory;
        this.windowDimensions = windowDimensions;
    }

    /**
     * This method is used to create an extra paddle behavior.
     *
     * @param size The size of the paddle.
     * @return The extra paddle behavior.
     */
    public SpecialBehaviors createExtraPaddle(Vector2 size) {
        return new ExtraPaddle(gameObjects, gameObjectFactory, size);
    }

    /**
     * This method is used to create an extra puck behavior.
     *
     * @param ballRadius The radius of the puck.
     * @return The extra puck behavior.
     */
    public SpecialBehaviors createExtraPuck(int ballRadius) {
        return new ExtraPuck(gameObjects, gameObjectFactory, ballRadius);
    }

    /**
     * This method is used to create a camera change behavior.
     *
     * @param owner   The game manager.
     * @param tracker The object tracker.
     * @return The camera change behavior.
     */
    public SpecialBehaviors createCameraChange(GameManager owner, ObjectTracker tracker) {
        return new CameraChange(owner, tracker);
    }

    /**
     * This method is used to create an extra life behavior.
     *
     * @param dimensions        The dimensions of the extra life.
     * @param windowDimensions  The dimensions of the window.
     * @param gameObjects       The collection of game objects.
     * @param gameObjectFactory The factory used to create game objects.
     * @param lifeCounter       The life counter of the game.
     * @return The extra life behavior.
     */
    public SpecialBehaviors createExtraLife(Vector2 dimensions, Renderable lifeImage,
                                            Vector2 windowDimensions, GameObjectCollection gameObjects,
                                            GameObjectFactory gameObjectFactory, LifeCounter lifeCounter) {
        return new ExtraLife(dimensions, lifeImage, lifeCounter, windowDimensions, gameObjects,
                gameObjectFactory);
    }

    /**
     * This method is used to create a double behavior.
     *
     * @param allBehaviors all other behaviors in the game.
     * @return The double behavior.
     */
    public SpecialBehaviors createDouble(SpecialBehaviors[] allBehaviors) {
        return new DoubleBehavior(allBehaviors);
    }

}

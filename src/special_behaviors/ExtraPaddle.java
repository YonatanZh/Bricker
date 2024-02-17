package special_behaviors;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.GameObjectFactory;

/**
 * A class representing a special behavior that adds an extra paddle to the game.
 */
public class ExtraPaddle implements SpecialBehaviors {

    private static final String PADDLE_PATH = "assets/paddle.png";

    private final GameObjectCollection gameObjects;
    private final GameObjectFactory gameObjectFactory;
    private Vector2 size;
    private Vector2 windowDimensions;
    private Counter paddleCounter;

    /**
     * Creates a new ExtraPaddle object.
     * @param gameObjects a collection of all the game objects
     * @param gameObjectFactory the game object factory
     * @param size the size of the paddle
     * @param windowDimensions the window dimensions
     * @param paddleCounter the paddle counter
     */
    public ExtraPaddle(GameObjectCollection gameObjects, GameObjectFactory gameObjectFactory, Vector2 size,
                       Vector2 windowDimensions, Counter paddleCounter) {
        this.gameObjects = gameObjects;
        this.gameObjectFactory = gameObjectFactory;
        this.size = size;
        this.windowDimensions = windowDimensions;
        this.paddleCounter = paddleCounter;
    }

    /**
     * Adds an extra paddle to the game.
     */
    public void behave() {
        Vector2 position = new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2);
        GameObject paddle = gameObjectFactory.createDisappearingPaddle(PADDLE_PATH, size, position,
                gameObjects, paddleCounter);
        gameObjects.addGameObject(paddle);
    }
}

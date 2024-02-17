package special_behaviors;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.GameObjectFactory;

public class ExtraPaddle implements SpecialBehaviors {

    private static final String PADDLE_PATH = "assets/paddle.png";

    private final GameObjectCollection gameObjects;
    private final GameObjectFactory gameObjectFactory;
    private Vector2 size;
    private Vector2 windowDimensions;
    private Counter paddleCounter;

    public ExtraPaddle(GameObjectCollection gameObjects, GameObjectFactory gameObjectFactory, Vector2 size,
                       Vector2 windowDimensions, Counter paddleCounter) {
        this.gameObjects = gameObjects;
        this.gameObjectFactory = gameObjectFactory;
        this.size = size;
        this.windowDimensions = windowDimensions;
        this.paddleCounter = paddleCounter;
    }

    public void behave(Vector2 position) {
        position = new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2);
        GameObject paddle = gameObjectFactory.createDisappearingPaddle(PADDLE_PATH, size, position,
                gameObjects, paddleCounter);
        gameObjects.addGameObject(paddle);
    }
}

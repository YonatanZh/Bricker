package special_behaviors;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;
import gameobjects.GameObjectFactory;

import java.util.Random;

public class ExtraPaddle implements SpecialBehaviors {

    private static final String PADDLE_PATH = "assets/paddle.png";

    private final GameObjectCollection gameObjects;
    private final GameObjectFactory gameObjectFactory;
    private Vector2 size;
    private Vector2 windowDimensions;

    public ExtraPaddle(GameObjectCollection gameObjects, GameObjectFactory gameObjectFactory, Vector2 size,
                       Vector2 windowDimensions) {
        this.gameObjects = gameObjects;
        this.gameObjectFactory = gameObjectFactory;
        this.size = size;
        this.windowDimensions = windowDimensions;
    }

    public void behave() {
        Vector2 position = new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2);
        GameObject paddle = gameObjectFactory.createTempPaddle(PADDLE_PATH, size, position, gameObjects);
        gameObjects.addGameObject(paddle);
    }
}

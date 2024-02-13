package special_behaviors;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.GameObjectFactory;

public class BehaviorFacory {

    private GameManager owner;
    private final GameObjectCollection gameObjects;
    private final GameObjectFactory gameObjectFactory;
    private final Vector2 windowDimensions;


    public BehaviorFacory(GameManager owner, GameObjectCollection gameObjects,
                          GameObjectFactory gameObjectFactory, Vector2 windowDimensions) {
        this.owner = owner;
        this.gameObjects = gameObjects;
        this.gameObjectFactory = gameObjectFactory;
        this.windowDimensions = windowDimensions;
    }

    public SpecialBehaviors createExtraPaddle(Vector2 size, Counter paddleCounter) {
        return new ExtraPaddle(gameObjects, gameObjectFactory, size, windowDimensions, paddleCounter);
    }

    public SpecialBehaviors createExtraPuck(int ballRadius, int ballSpeed, Vector2 position) {
        return new ExtraPuck(gameObjects, gameObjectFactory, ballRadius, ballSpeed, position);
    }

    public SpecialBehaviors createCameraChange(GameObject objToFollow, GameManager owner) {
        return new CameraChange(objToFollow, Vector2.ZERO, windowDimensions.mult(1.2f), windowDimensions, owner);
    }
}

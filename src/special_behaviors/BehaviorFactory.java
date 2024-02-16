package special_behaviors;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.GameObjectFactory;
import gameobjects.LifeCounter;

public class BehaviorFactory {

    private GameManager owner;
    private final GameObjectCollection gameObjects;
    private final GameObjectFactory gameObjectFactory;
    private final Vector2 windowDimensions;


    public BehaviorFactory(GameManager owner, GameObjectCollection gameObjects,
                           GameObjectFactory gameObjectFactory, Vector2 windowDimensions, LifeCounter lifeCounter) {
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

    public SpecialBehaviors createExtraLife(Vector2 topLeftCorner, Vector2 dimensions,
                                            Vector2 windowDimensions, GameObjectCollection gameObjects,
                                            GameObjectFactory gameObjectFactory, LifeCounter lifeCounter) {
        return new ExtraLife(topLeftCorner, dimensions, lifeCounter, windowDimensions, gameObjects, gameObjectFactory);
    }
}

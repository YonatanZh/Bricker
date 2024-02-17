package special_behaviors;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.GameObjectFactory;
import gameobjects.LifeCounter;
import gameobjects.ObjectTracker;

public class BehaviorFactory {

    private final GameObjectCollection gameObjects;
    private final GameObjectFactory gameObjectFactory;
    private final Vector2 windowDimensions;


    public BehaviorFactory(GameObjectCollection gameObjects,
                           GameObjectFactory gameObjectFactory, Vector2 windowDimensions, LifeCounter lifeCounter) {
        this.gameObjects = gameObjects;
        this.gameObjectFactory = gameObjectFactory;
        this.windowDimensions = windowDimensions;
    }

    public SpecialBehaviors createExtraPaddle(Vector2 size, Counter paddleCounter) {
        return new ExtraPaddle(gameObjects, gameObjectFactory, size, windowDimensions, paddleCounter);
    }

    public SpecialBehaviors createExtraPuck(int ballRadius) {
        return new ExtraPuck(gameObjects, gameObjectFactory, ballRadius);
    }

    public SpecialBehaviors createCameraChange(GameManager owner, ObjectTracker tracker) {
        return new CameraChange(owner, tracker);
    }

    public SpecialBehaviors createExtraLife(Vector2 topLeftCorner, Vector2 dimensions,
                                            Vector2 windowDimensions, GameObjectCollection gameObjects,
                                            GameObjectFactory gameObjectFactory, LifeCounter lifeCounter) {
        return new ExtraLife(topLeftCorner, dimensions, lifeCounter, windowDimensions, gameObjects, gameObjectFactory);
    }

    public SpecialBehaviors createDouble() {
        SpecialBehaviors [] b = new SpecialBehaviors[] {}
    }
}

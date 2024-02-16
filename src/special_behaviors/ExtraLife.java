package special_behaviors;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameobjects.FallingLife;
import gameobjects.GameObjectFactory;
import gameobjects.LifeCounter;

public class ExtraLife implements SpecialBehaviors {
    private final Vector2 topLeftCorner;
    private final Vector2 dimensions;
    private static final Vector2 VELOCITY = new Vector2(0, 100);
    private final Vector2 windowDimensions;
    private final GameObjectCollection gameObjects;
    private final GameObjectFactory gameObjectFactory;
    private final LifeCounter lifeCounter;

    public ExtraLife(Vector2 topLeftCorner, Vector2 dimensions, LifeCounter lifeCounter,
                     Vector2 windowDimensions, GameObjectCollection gameObjects, GameObjectFactory gameObjectFactory) {
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjects;
        this.gameObjectFactory = gameObjectFactory;
        this.lifeCounter = lifeCounter;
    }

    public void behave() {
        lifeCounter.createLife(gameObjectFactory, topLeftCorner, dimensions, VELOCITY, windowDimensions, gameObjects);
    }
}

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
    private final Renderable renderable;
    private final Vector2 velocity;
    private final Vector2 windowDimensions;
    private final GameObjectCollection gameObjects;
    private final LifeCounter lifeCounter;
    private final GameObjectFactory gameObjectFactory;

    public ExtraLife(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Vector2 velocity,
                     Vector2 windowDimensions, GameObjectCollection gameObjects, LifeCounter lifeCounter, GameObjectFactory gameObjectFactory) {
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        this.renderable = renderable;
        this.velocity = velocity;
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjects;
        this.lifeCounter = lifeCounter;
        this.gameObjectFactory = gameObjectFactory;
    }

    public void behave() {
        FallingLife life = (FallingLife) gameObjectFactory.createFallingLife(topLeftCorner, dimensions, renderable, velocity,
                windowDimensions, gameObjects, lifeCounter);
        life.setCenter(topLeftCorner);
        life.setVelocity(velocity);
        gameObjects.addGameObject(life);
    }
}

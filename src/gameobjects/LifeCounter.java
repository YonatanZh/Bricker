package gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class LifeCounter extends GameObject {
    private static final int MAX_LIVES = 4; //todo what is this???

    private final NumericLifeDisplay numericLifeCounter;
    private final GraphicalLifeDisplay graphicalLives;
    private final Counter lives;
    private final Vector2 topLeftCorner;
    private final float objectSize;
    private final int buffer;



    public LifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, int lives, float objectSize,
                       int buffer, GameObjectCollection gameObjects, GameObjectFactory gameObjectFactory) {
        super(topLeftCorner, dimensions, null);
        this.lives = new Counter(lives);
        this.topLeftCorner = topLeftCorner;
        this.objectSize = objectSize;
        this.buffer = buffer;
        Vector2 lifeDimensions = new Vector2(objectSize, objectSize);
        this.numericLifeCounter = (NumericLifeDisplay) gameObjectFactory.createNumericLifeDisplay(topLeftCorner,
                lifeDimensions, this.lives, gameObjects);

        Vector2 indentation = new Vector2(objectSize + buffer, 0);
        this.graphicalLives = (GraphicalLifeDisplay) gameObjectFactory.createGraphicalLifeDisplay(topLeftCorner.add(indentation),
                lifeDimensions, renderable, objectSize, buffer, this.lives, gameObjects);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (lives.value() == 0) {

        }
    }

    public void gainLife() {
    }



    public void loseLife() {
        lives.decrement();
        graphicalLives.loseLife();
        numericLifeCounter.loseLife();
    }

}

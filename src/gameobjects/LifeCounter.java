package gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class LifeCounter extends GameObject {
    private static final int MAX_LIVES = 5; //todo what is this???

    private final NumericLifeDisplay numericLifeCounter;
    private final GraphicalLifeDisplay graphicalLives;
    private final Counter lives;



    public LifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, int lives, float objectSize,
                       int buffer, GameObjectCollection gameObjects, GameObjectFactory gameObjectFactory) {
        super(topLeftCorner, dimensions, null);
        this.lives = new Counter(lives);
        Vector2 lifeDimensions = new Vector2(objectSize, objectSize);
        this.numericLifeCounter = (NumericLifeDisplay) gameObjectFactory.createNumericLifeDisplay(topLeftCorner,
                lifeDimensions, this.lives, gameObjects);

        Vector2 indentation = new Vector2(objectSize + buffer, 0);
        this.graphicalLives = (GraphicalLifeDisplay) gameObjectFactory.createGraphicalLifeDisplay(topLeftCorner.add(indentation),
                lifeDimensions, renderable, objectSize, buffer, this.lives, gameObjects);
    }


    public void loseLife() {
        lives.decrement();
        graphicalLives.loseLife();
        numericLifeCounter.loseLife();
    }

}

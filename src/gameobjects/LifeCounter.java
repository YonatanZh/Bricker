package gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class LifeCounter extends GameObject {

    private static final int SQUARE_SIZE = 20;
    private static final int MAX_LIVES = 5; //todo change this to the potential max lives

    private final NumericLifeDisplay numericLifeCounter;
    private final GraphicalLifeDisplay[] graphicalLives;
    private int lives;
    private final GameObjectCollection gameObjects;

    //TODO : maybe do lives counter as global counter like for bricks? - eden
    //TODO : make graphic counter do all the work - eden
    //todo : make the constructor use the objects from the factory - eden
    public LifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, int lives, GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, null);
        this.lives = lives;
        this.gameObjects = gameObjects;
        this.numericLifeCounter = new NumericLifeDisplay(topLeftCorner, new Vector2(SQUARE_SIZE, SQUARE_SIZE), lives);
        this.numericLifeCounter.setCenter(topLeftCorner);
        gameObjects.addGameObject(numericLifeCounter, Layer.UI);

        this.graphicalLives = new GraphicalLifeDisplay[MAX_LIVES];
        for (int i = 0; i < lives; i++) {
            float x = topLeftCorner.x() + (i + 1) * SQUARE_SIZE;
            Vector2 position = new Vector2(x, topLeftCorner.y());
            graphicalLives[i] = new GraphicalLifeDisplay(position, new Vector2(SQUARE_SIZE, SQUARE_SIZE), renderable, position);
            gameObjects.addGameObject(graphicalLives[i], Layer.UI);
        }
    }

//    todo have every object i.e the life counters, have his fucnion - eden

    public void loseLife() {
        lives--;
        gameObjects.removeGameObject(graphicalLives[lives], Layer.UI);
        numericLifeCounter.decrementLives(lives);
        numericLifeCounter.setColor(lives);
    }

}

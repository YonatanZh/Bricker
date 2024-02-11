package gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class LifeCounter extends GameObject {

    private static final int SQUARE_SIZE = 20;
    private static final int MAX_LIVES = 5; //todo change this to the potential max lives

    private final NumericLifeDisplay numericLifeCounter;
    private GraphicalLifeDisplay[] graphicalLives;
    private int lives;

    public LifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, int lives, GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, null);
        this.lives = lives;
        this.numericLifeCounter = new NumericLifeDisplay(topLeftCorner, new Vector2(SQUARE_SIZE, SQUARE_SIZE), lives);
        this.numericLifeCounter.setCenter(topLeftCorner);
        gameObjects.addGameObject(numericLifeCounter);
        this.graphicalLives = new GraphicalLifeDisplay[MAX_LIVES];
        for (int i = 0; i < lives; i++) {
            float x = topLeftCorner.x() + (i + 1) * SQUARE_SIZE;
            Vector2 position = new Vector2(x, topLeftCorner.y());
            graphicalLives[i] = new GraphicalLifeDisplay(position, new Vector2(SQUARE_SIZE, SQUARE_SIZE), renderable, position);
            gameObjects.addGameObject(graphicalLives[i]);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        numericLifeCounter.setColor(lives);

    }

    public void loseLife() {
        lives--;
//        graphicalLives[lives
    }

}

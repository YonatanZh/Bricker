package bricker.game_objects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import static bricker.main.Constants.MAX_LIVES;

/**
 * A class representing the life counter. It keeps track of the lives and updates the display when a life is lost or gained.
 */
public class LifeCounter extends GameObject {
    private final NumericLifeDisplay numericLifeCounter;
    private final GraphicalLifeDisplay graphicalLives;
    private final Counter lives;

    /**
     * Creates a new life counter.
     *
     * @param topLeftCorner       the top left corner of the object
     * @param dimensions          the dimensions of the object
     * @param lives               the counter for the lives
     * @param numericLifeCounter  the numeric life counter
     * @param graphicalLives      the graphical life counter
     */
    public LifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Counter lives,
                       NumericLifeDisplay numericLifeCounter,
                       GraphicalLifeDisplay graphicalLives) {
        super(topLeftCorner, dimensions, null);
        this.lives = lives;
        this.numericLifeCounter = numericLifeCounter;
        this.graphicalLives = graphicalLives;
    }

    /**
     * Updates the life counter.
     */
    public void gainLife() {
        if (lives.value() < MAX_LIVES) {
            lives.increment();
            graphicalLives.gainLife(lives.value() - 1);
            numericLifeCounter.updateDisplay();
        }
    }

    /**
     *
     * @param topLeftCorner
     * @param life
     * @param velocity
     * @param gameObjects
     */
    public void createLife(Vector2 topLeftCorner, FallingLife life, Vector2 velocity,
                           GameObjectCollection gameObjects) {
        life.setCenter(topLeftCorner);
        life.setVelocity(velocity);
        gameObjects.addGameObject(life, danogl.collisions.Layer.DEFAULT);
    }

    /**
     * Updates the life counter when a life is lost.
     */
    public void loseLife() {
        graphicalLives.loseLife();
        numericLifeCounter.updateDisplay();
    }

}

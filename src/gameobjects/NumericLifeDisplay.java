package gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;


/**
 * A class representing a numeric life display.
 */
public class NumericLifeDisplay extends GameObject {

    private static final int GREEN_THRESHOLD = 3;
    private static final int YELLOW_THRESHOLD = 2;

    private final Counter lives;
    private final TextRenderable lifeCounter;

    /**
     * Creates a new NumericLifeDisplay object.
     * @param topLeftCorner the position of the top left corner of the life display
     * @param dimensions the dimensions of the life display
     * @param lives the life counter
     * @param gameObjects a collection of all the game objects
     */
    public NumericLifeDisplay(Vector2 topLeftCorner, Vector2 dimensions, Counter lives,
                              GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, null);
        this.lifeCounter = new TextRenderable(Integer.toString(lives.value()));
        this.lives = lives;
        setColor();
        this.renderer().setRenderable(lifeCounter);
        this.setCenter(topLeftCorner);
        gameObjects.addGameObject(this, Layer.UI);
    }

    /**
     * represents the new life counter after a life is lost.
     */
    public void loseLife() {
        this.lifeCounter.setString(Integer.toString(lives.value()));
        setColor();
    }

    /**
     * Sets the color of the life counter based on the number of lives.
     */
    public void setColor() {
        if (lives.value() >= GREEN_THRESHOLD) {
            lifeCounter.setColor(Color.GREEN);
        } else if (lives.value() == YELLOW_THRESHOLD) {
            lifeCounter.setColor(Color.YELLOW);
        } else {
            lifeCounter.setColor(Color.RED);
        }
    }

    /**
     * represents the new life counter after a life is gained.
     */
    public void gainLife() {
        this.lifeCounter.setString(Integer.toString(lives.value()));
        setColor();
    }
}

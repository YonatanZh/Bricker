package bricker.game_objects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.awt.*;

import static bricker.main.Constants.GREEN_THRESHOLD;
import static bricker.main.Constants.YELLOW_THRESHOLD;


/**
 * A TextRenderable that represents a numeric life counter with a color that changes based
 * on the current life count.
 */
public class NumericLifeDisplay extends GameObject {
    private final Counter lives;
    private final TextRenderable lifeCounter;

    /**
     * Creates a new NumericLifeDisplay object.
     *
     * @param topLeftCorner the position of the top left corner of the life
     * @param dimensions    the dimensions of the life
     * @param lives         the life counter
     */
    public NumericLifeDisplay(Vector2 topLeftCorner, Vector2 dimensions, Counter lives) {
        super(topLeftCorner, dimensions, null);
        this.lifeCounter = new TextRenderable(Integer.toString(lives.value()));
        this.lives = lives;
        setColor();
        this.renderer().setRenderable(lifeCounter);
        this.setCenter(topLeftCorner);
    }

    /**
     * Updates the life counter when a life is lost or gained with the correct color.
     */
    public void updateDisplay() {
        this.lifeCounter.setString(Integer.toString(lives.value()));
        setColor();
    }

    private void setColor() {
        if (lives.value() >= GREEN_THRESHOLD) {
            lifeCounter.setColor(Color.GREEN);
        } else if (lives.value() == YELLOW_THRESHOLD) {
            lifeCounter.setColor(Color.YELLOW);
        } else {
            lifeCounter.setColor(Color.RED);
        }
    }
}

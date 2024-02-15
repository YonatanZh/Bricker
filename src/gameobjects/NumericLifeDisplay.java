package gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;


/**
 * A TextRenderable that represents a numeric life counter with a color that changes based
 * on the current life count.
 */
public class NumericLifeDisplay extends GameObject {

    private static final int GREEN_THRESHOLD = 3;
    private static final int YELLOW_THRESHOLD = 2;

    private final Counter lives;
    private final TextRenderable lifeCounter;

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

    public void loseLife() {
        this.lifeCounter.setString(Integer.toString(lives.value()));
        setColor();
    }


    public void setColor() {
        if (lives.value() >= GREEN_THRESHOLD) {
            lifeCounter.setColor(Color.GREEN);
        } else if (lives.value() == YELLOW_THRESHOLD) {
            lifeCounter.setColor(Color.YELLOW);
        } else {
            lifeCounter.setColor(Color.RED);
        }
    }

    public void gainLife() {
        this.lifeCounter.setString(Integer.toString(lives.value()));
        setColor();
    }
}

package gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;


/**
 * A TextRenderable that represents a numeric life counter with a color that changes based
 * on the current life count.
 */
public class NumericLifeDisplay extends GameObject {

    private static final int GREEN_THRESHOLD = 3;
    private static final int YELLOW_THRESHOLD = 2;


    private final TextRenderable lifeCounter;

    public NumericLifeDisplay(Vector2 topLeftCorner, Vector2 dimensions, int lives) {
        super(topLeftCorner, dimensions, null);
        this.lifeCounter = new TextRenderable(Integer.toString(lives));
        setColor(lives);
        this.renderer().setRenderable(lifeCounter);
    }

//    @Override
//    public void update(float deltaTime) {
//        super.update(deltaTime);
//        lifeCounter.setString(Integer.toString(counter));
//        setColor();
//    }

//    public void decreaseLife() {
//        counter--;
//        update(0);
//    }
//
//    public void increaseLife() {
//        counter++;
//        update(0);
//    }

    public void setColor(int counter) {
        if (counter >= GREEN_THRESHOLD) {
            lifeCounter.setColor(Color.GREEN);
        } else if (counter == YELLOW_THRESHOLD) {
            lifeCounter.setColor(Color.YELLOW);
        } else {
            lifeCounter.setColor(Color.RED);
        }
    }

}

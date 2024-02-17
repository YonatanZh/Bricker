package bricker.game_objects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;

import static bricker.main.Constants.PADDLE_MOVEMENT_SPEED;

/**
 * A paddle that the user can control.
 */
public class Paddle extends GameObject {

    private final UserInputListener inputListener;

    /**
     * Creates a new paddle.
     *
     * @param topLeftCorner the top left corner of the paddle.
     * @param dimensions    the dimensions of the paddle.
     * @param renderable    the renderable object that will draw the paddle.
     * @param inputListener the input listener that will control the paddle.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
    }

    /**
     * Updates the paddle's position based on the user's input.
     *
     * @param deltaTime unused.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movement = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movement = movement.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movement = movement.add(Vector2.RIGHT);
        }
        setVelocity(movement.mult(PADDLE_MOVEMENT_SPEED));
    }


}

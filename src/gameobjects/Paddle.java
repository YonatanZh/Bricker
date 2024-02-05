package gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {

    private static final float MOVEMENT_SPEED = 300;
    private final UserInputListener inputListener;

    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movement = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movement = movement.add(Vector2.LEFT);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movement = movement.add(Vector2.RIGHT);
        }
        setVelocity(movement.mult(MOVEMENT_SPEED));
    }


}

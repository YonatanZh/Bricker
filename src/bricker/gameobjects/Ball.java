package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * A class representing the ball in the game.
 */
public class Ball extends GameObject {

    private int collisionCounter;
    private Sound collisionSound;

    /**
     * Creates a new Ball object.
     *
     * @param position       The position of the ball.
     * @param size           The size of the ball.
     * @param image          The image of the ball.
     * @param collisionSound The sound to play when the ball collides with something.
     */
    public Ball(Vector2 position, Vector2 size, Renderable image, Sound collisionSound) {
        super(position, size, image);
        this.collisionCounter = 0;
        this.collisionSound = collisionSound;
    }

    /**
     * Called when the ball collides with another object. Plays the collision sound and changes the direction
     * of the ball.
     *
     * @param other     The object the ball collided with.
     * @param collision The collision parameters.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionSound.play();
        collisionCounter++;
        Vector2 newVelocity = getVelocity().flipped(collision.getNormal());
        setVelocity(newVelocity);
    }

    /**
     * Returns the number of collisions the ball has had.
     *
     * @return The number of collisions the ball has had.
     */
    public int getCollisionCounter() {
        return collisionCounter;
    }
}

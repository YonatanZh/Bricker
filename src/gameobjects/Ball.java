package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * A class representing a ball in the game.
 */
public class Ball extends GameObject {

    private int collisionCounter;
    private Sound collisionSound;


    /**
     * Creates a new Ball object.
     * @param position the position of the ball
     * @param size the size of the ball
     * @param image the image of the ball
     * @param collisionSound the sound to play when the ball collides with something
     */
    public Ball(Vector2 position, Vector2 size, Renderable image, Sound collisionSound){
        super(position, size, image);
        this.collisionCounter = 0;
        this.collisionSound = collisionSound;
    }

    /**
     * A method that is called when an object collides with the ball
     * @param other the object that the ball collided with
     * @param collision the collision that occurred
     * */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionSound.play();
        collisionCounter++;
        Vector2 newVelocity = getVelocity().flipped(collision.getNormal());
        setVelocity(newVelocity);
    }

    /**
     * @return the number of collisions the ball has had
     */
    public int getCollisionCounter() {
        return collisionCounter;
    }
}

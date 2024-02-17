package gameobjects;

import brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**`
 * A class representing a paddle that disappears after a certain number of collisions.
 */
public class DisappearingPaddle extends Paddle{

    private static final int MAX_COLLISIONS = 4;

    CollisionStrategy collisionStrategy;
    private Counter paddleCounter;
    private int collisionCount;

    /**
     * Creates a new DisappearingPaddle object.
     * @param topLeftCorner the position of the top left corner of the paddle
     * @param dimensions the dimensions of the paddle
     * @param renderable that presents an image of the paddle
     * @param inputListener the input listener for the paddle
     * @param collisionStrategy the strategy for collision with the paddle
     * @param paddleCounter the counter for the number of paddles in the game
     */
    public DisappearingPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                              UserInputListener inputListener, CollisionStrategy collisionStrategy,
                              Counter paddleCounter) {
        super(topLeftCorner, dimensions, renderable, inputListener);
        this.collisionStrategy = collisionStrategy;
        this.paddleCounter = paddleCounter;
        paddleCounter.increment();
        this.collisionCount = 0;
    }

     /**
     * A method that is called when an object collides with the paddle
     * It counts the collisions with the ball if it's equal to MAX_COLLISIONS it will be removed.
     * @param other the object that the paddle collided with
     * @param collision the collision that occurred
     * */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Ball){
            collisionCount++;
        }
        if (collisionCount == MAX_COLLISIONS) {
            paddleCounter.decrement();
            collisionStrategy.onCollision(this, other);
        }
    }

}

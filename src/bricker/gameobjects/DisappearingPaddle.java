package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import static bricker.main.Constants.DISAPPEARING_PADDLE_MAX_COLLISIONS;

/**
 * A class representing a paddle that disappears after a certain number of collisions.
 * Can only collide with the ball.
 * Can only have one on the screen at a time.
 */
public class DisappearingPaddle extends Paddle{
    private final CollisionStrategy collisionStrategy;
    private final Counter paddleCounter;
    private int collisionCount;

    /**
     * Constructs a new DisappearingPaddle.
     *
     * @param topLeftCorner      the top left corner of the paddle.
     * @param dimensions         the dimensions of the paddle.
     * @param renderable         the image of the paddle.
     * @param inputListener      the input listener for the paddle.
     * @param collisionStrategy  the collision strategy of the paddle.
     * @param paddleCounter      the counter for the number of paddles.
     */
    public DisappearingPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                              UserInputListener inputListener, CollisionStrategy collisionStrategy,
                              Counter paddleCounter) {
        super(topLeftCorner, dimensions, renderable, inputListener);
        this.collisionStrategy = collisionStrategy;
        this.paddleCounter = paddleCounter;
        this.collisionCount = 0;
    }

    /**
     * Called when the paddle collides with another object.
     * Calls the collision strategy and decrements the paddle counter.
     *
     * @param other     the object the paddle collided with.
     * @param collision the collision parameters.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Ball){
            collisionCount++;
        }
        if (collisionCount == DISAPPEARING_PADDLE_MAX_COLLISIONS) {
            paddleCounter.decrement();
            collisionStrategy.onCollision(this, other);
        }
    }

}

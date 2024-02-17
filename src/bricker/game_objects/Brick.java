package gameobjects;

import brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * A class representing a brick in the game.
 */
public class Brick extends GameObject {

    private final CollisionStrategy collisionStrategy;
    private final Counter brickCounter;

    /**
     * Constructs a new brick.
     *
     * @param topLeftCorner      the top left corner of the brick.
     * @param dimensions         the dimensions of the brick.
     * @param renderable         the image of the brick.
     * @param collisionStrategy  the collision strategy of the brick.
     * @param brickCounter       the counter for the number of bricks.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy, Counter brickCounter) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.brickCounter = brickCounter;
    }

    /**
     * Called when the brick collides with another object. Calls the collision strategy and decrements the brick counter.
     *
     * @param other     the object the brick collided with.
     * @param collision the collision parameters.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        brickCounter.decrement();
        collisionStrategy.onCollision(this, other);
    }
}

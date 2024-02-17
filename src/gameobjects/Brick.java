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
     * Creates a new Brick object.
     * @param topLeftCorner the position of the top left corner of the brick
     * @param dimensions the dimensions of the brick
     * @param renderable the image of the brick
     * @param collisionStrategy the strategy for collision with the brick
     * @param brickCounter the counter for the number of bricks in the game
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy, Counter brickCounter) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.brickCounter = brickCounter;
    }

    /**
     * A method that is called when an object collides with the brick
     * @param other the object that the brick collided with
     * @param collision the collision that occurred
     * */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        brickCounter.decrement();
        collisionStrategy.onCollision(this, other);
    }
}

package gameobjects;

import brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class DisappearingPaddle extends Paddle{

    private static final int MAX_COLLISIONS = 4;

    CollisionStrategy collisionStrategy;
    private int collisionCount;

    public DisappearingPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                              UserInputListener inputListener, CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable, inputListener);
        this.collisionStrategy = collisionStrategy;
        this.collisionCount = 0;
    }


    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionCount++;
        if (collisionCount == MAX_COLLISIONS) {
            collisionStrategy.onCollision(this, other);
        }
    }

}

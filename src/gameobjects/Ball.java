package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Ball extends GameObject {

    private int collisionCounter;


    public Ball(Vector2 position, Vector2 size, Renderable image) {
        super(position, size, image);
        collisionCounter = 0;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionCounter++;
        Vector2 newVelocity = getVelocity().flipped(collision.getNormal());
        setVelocity(newVelocity);
    }

    public int getCollisionCounter() {
        return collisionCounter;
    }
}

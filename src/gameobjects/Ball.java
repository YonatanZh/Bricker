package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Ball extends GameObject {

    private int collisionCounter;
    private Sound collisionSound;


    public Ball(Vector2 position, Vector2 size, Renderable image, Sound collisionSound){
        super(position, size, image);
        this.collisionCounter = 0;
        this.collisionSound = collisionSound;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionSound.play();
        collisionCounter++;
        Vector2 newVelocity = getVelocity().flipped(collision.getNormal());
        setVelocity(newVelocity);
    }

    public int getCollisionCounter() {
        return collisionCounter;
    }
}

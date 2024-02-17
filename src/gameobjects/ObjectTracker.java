package gameobjects;

import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;

public class ObjectTracker extends Camera {

    private GameManager owner;
    private int collisionAllowed;
    private int collisionCounter;
    private GameObject gameObject;
    private boolean flag;

    public ObjectTracker(GameObject objToFollow, Vector2 deltaRelativeToObject, Vector2 dimensions,
                        Vector2 windowDimensions, GameManager owner, GameObject ball) {
        super(objToFollow, deltaRelativeToObject, dimensions, windowDimensions);
        this.owner = owner;
        this.collisionAllowed = 0;
        this.collisionCounter = 0;
        this.gameObject = ball;
        this.flag = false;

        this.i = 0;
    }

    private int i;

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        i++;
        if (gameObject instanceof Ball && ((Ball) gameObject).getCollisionCounter() >= collisionCounter + collisionAllowed) {
            owner.setCamera(null);
            flag = false;
            collisionCounter = 0;
        }
    }

    public void trackCollision(int collisionsAllowed) {
        if (gameObject instanceof Ball && !flag) {
            collisionCounter = ((Ball) gameObject).getCollisionCounter();
            this.collisionAllowed = collisionsAllowed;
            flag = true;
        }
    }
}
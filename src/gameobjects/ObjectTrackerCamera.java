package gameobjects;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;

public class ObjectTrackerCamera extends Camera {

    private GameManager owner;
    private GameObject obj;
    private int maxCollisions;

    public ObjectTrackerCamera(GameObject objToFollow, Vector2 deltaRelativeToObject, Vector2 dimensions,
                               Vector2 windowDimensions, GameManager owner, int maxCollisions) {
        super(objToFollow, deltaRelativeToObject, dimensions, windowDimensions);
        this.owner = owner;
        this.obj = objToFollow;
        this.maxCollisions = maxCollisions;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (obj instanceof Ball && ((Ball) obj).getCollisionCounter() % maxCollisions == 0) {
            owner.setCamera(null);
        }
    }

}
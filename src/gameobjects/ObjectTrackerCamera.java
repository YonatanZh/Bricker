package gameobjects;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * A class representing a camera that follows an object and stops following it after a certain number of collisions.
 */
public class ObjectTrackerCamera extends Camera {

    private GameManager owner;
    private GameObject obj;
    private int maxCollisions;

    /**
     * Creates a new ObjectTrackerCamera object.
     * @param objToFollow the object to follow
     * @param deltaRelativeToObject the delta between the camera and the object
     * @param dimensions the dimensions of the camera
     * @param windowDimensions the window dimensions
     * @param owner the game manager
     * @param maxCollisions the maximum number of collisions before the camera stops following the object
     */
    public ObjectTrackerCamera(GameObject objToFollow, Vector2 deltaRelativeToObject, Vector2 dimensions,
                               Vector2 windowDimensions, GameManager owner, int maxCollisions) {
        super(objToFollow, deltaRelativeToObject, dimensions, windowDimensions);
        this.owner = owner;
        this.obj = objToFollow;
        this.maxCollisions = maxCollisions;
    }

    /**
     * Updates the camera.
     * @param deltaTime the time passed since the last update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (obj instanceof Ball && ((Ball) obj).getCollisionCounter() % maxCollisions == 0) {
            owner.setCamera(null);
        }
    }

}
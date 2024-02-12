package special_behaviors;

import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import gameobjects.Ball;
import gameobjects.ObjectTrackerCamera;


//todo change this class to two separate classes one for the behavior and one for the camera
public class CameraChange implements SpecialBehaviors {
    private static final int MAX_COLLISIONS = 4;
    private Camera camera;
    private GameManager owner;
    GameObject objToFollow;

    public CameraChange(GameObject objToFollow, Vector2 deltaRelativeToObject, Vector2 dimensions,
                        Vector2 windowDimensions, GameManager owner) {
        this.owner = owner;
        this.objToFollow = objToFollow;
        this.camera = new ObjectTrackerCamera(objToFollow, deltaRelativeToObject, dimensions,
                windowDimensions, owner, MAX_COLLISIONS);
    }


    public void behave() {
        if (owner.camera() == null) {
            owner.setCamera(camera);
        }
    }
}

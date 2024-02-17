package special_behaviors;

import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import gameobjects.ObjectTracker;


//todo change this class to two separate classes one for the behavior and one for the camera
public class CameraChange implements SpecialBehaviors {
    private static final int MAX_COLLISIONS = 4;
    private final ObjectTracker tracker;
    private final GameManager owner;


    public CameraChange(GameManager owner, ObjectTracker tracker) {
        this.owner = owner;
        this.tracker = tracker;
    }


    public void behave() {
        owner.setCamera(tracker);
        tracker.trackCollision(MAX_COLLISIONS);
    }
}

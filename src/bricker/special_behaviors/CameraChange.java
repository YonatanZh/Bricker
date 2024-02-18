package bricker.special_behaviors;

import danogl.GameManager;
import danogl.util.Vector2;
import bricker.gameobjects.ObjectTracker;

import static bricker.main.Constants.CAMERA_CHANGE_MAX_COLLISIONS;

/**
 * This class is used to define the behavior of a special brick that changes the camera.
 */
public class CameraChange implements SpecialBehaviors {
    private final ObjectTracker tracker;
    private final GameManager owner;

    /**
     * Constructor for the CameraChange class.
     *
     * @param owner   The game manager.
     * @param tracker The object tracker.
     */
    public CameraChange(GameManager owner, ObjectTracker tracker) {
        this.owner = owner;
        this.tracker = tracker;
    }

    /**
     * This method is used to define the behavior of a special object.
     *
     * @param position1 null
     * @param position2 null
     */
    public void behave(Vector2 position1, Vector2 position2) {
        owner.setCamera(tracker);
        tracker.trackCollision(CAMERA_CHANGE_MAX_COLLISIONS);
    }
}

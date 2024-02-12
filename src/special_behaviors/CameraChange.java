package special_behaviors;

import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import gameobjects.Ball;

public class CameraChange extends Camera implements SpecialBehaviors {
    private static final int MAX_COLLISIONS = 4;
    private final GameManager owner;
    private final GameObject obj;

    public CameraChange(GameObject objToFollow, Vector2 deltaRelativeToObject, Vector2 dimensions,
                         Vector2 windowDimensions, GameManager owner) {
        super(objToFollow, deltaRelativeToObject, dimensions, windowDimensions);
        this.owner = owner;
        this.obj = objToFollow;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        behave();
    }

    public void behave() {
        if (obj instanceof Ball && ((Ball) obj).getCollisionCounter() % MAX_COLLISIONS == 0) {
            owner.setCamera(null);
        } else {
            owner.setCamera(this);
        }
    }
}

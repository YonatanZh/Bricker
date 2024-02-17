package bricker.game_objects;

import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;

/**
 * A class representing a camera that follows a game object.
 */
public class ObjectTracker extends Camera {
    private final GameManager owner;
    private int collisionAllowed;
    private int collisionCounter;
    private final GameObject gameObject;
    private boolean flag;

    /**
     * Constructs a new ObjectTracker.
     *
     * @param objToFollow           the object to follow.
     * @param deltaRelativeToObject the relative position of the camera to the object.
     * @param dimensions            the dimensions of the camera.
     * @param windowDimensions      the dimensions of the window.
     * @param owner                 the game manager.
     * @param ball                  the ball.
     */
    public ObjectTracker(GameObject objToFollow, Vector2 deltaRelativeToObject, Vector2 dimensions,
                         Vector2 windowDimensions, GameManager owner, GameObject ball) {
        super(objToFollow, deltaRelativeToObject, dimensions, windowDimensions);
        this.owner = owner;
        this.collisionAllowed = 0;
        this.collisionCounter = 0;
        this.gameObject = ball;
        this.flag = false;
    }

    /**
     * Called once per frame. Any logic is put here, or is called upon from here.
     * Checks if the ball has collided with the paddle the allowed number of times.
     *
     * @param deltaTime unused.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (gameObject instanceof Ball &&
                ((Ball) gameObject).getCollisionCounter() >= collisionCounter + collisionAllowed) {
            owner.setCamera(null);
            flag = false;
            collisionCounter = 0;
        }
    }

    /**
     * Tracks the number of collisions of the ball.
     *
     * @param collisionsAllowed the number of collisions allowed.
     */
    public void trackCollision(int collisionsAllowed) {
        if (gameObject instanceof Ball && !flag) {
            collisionCounter = ((Ball) gameObject).getCollisionCounter();
            this.collisionAllowed = collisionsAllowed;
            flag = true;
        }
    }
}
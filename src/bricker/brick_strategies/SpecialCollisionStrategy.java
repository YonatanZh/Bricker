package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;
import bricker.special_behaviors.SpecialBehaviors;
import java.util.Random;

import static bricker.main.Constants.*;

/**
 * Special collision strategy that removes the object from the game when it collides with another object.
 * It also has a chance to trigger a special behavior.
 */
public class SpecialCollisionStrategy extends BasicCollisionStrategy implements CollisionStrategy {
    private static final Random rand = new Random();
    private final SpecialBehaviors[] allBehaviors;
    private final Vector2 windowDimensions;

    /**
     * Constructor for the special collision strategy.
     * @param gameObjects the collection of game objects.
     * @param windowDimensions the dimensions of the window.
     * @param allBehaviors all the special behaviors.
     */
    public SpecialCollisionStrategy(GameObjectCollection gameObjects, Vector2 windowDimensions,
                   SpecialBehaviors[] allBehaviors) {
        super(gameObjects);

        this.windowDimensions = windowDimensions;

        this.allBehaviors = allBehaviors;

    }

    /**
     * Removes the object from the game when it collides with another object.
     * It also has a chance to trigger a special behavior.
     * @param thisObj the object that collided with another object.
     * @param otherObj the object that collided with this object.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);

        int behavior = rand.nextInt() % SPECIAL_BEHAVIOR_ODDS;

        switch (behavior) {
            case 8:
                allBehaviors[behavior].behave(thisObj.getCenter(), null);
                break;
            case EXTRA_PADDLE:
                allBehaviors[behavior].behave(windowDimensions, null);
                break;
            case CAMERA_CHANGE:
                allBehaviors[behavior].behave(null, null);
                break;
            case 0:
                allBehaviors[3].behave(thisObj.getCenter(), null);
                break;
            case DOUBLE:
                allBehaviors[behavior].behave(thisObj.getCenter(), windowDimensions);
                break;
            default:
                break;
        }
    }
}

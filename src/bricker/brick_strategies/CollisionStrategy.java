package bricker.brick_strategies;

import danogl.GameObject;

/**
 * This interface is used to define the behavior of a brick when it collides with another object.
 */
public interface CollisionStrategy {

    /**
     * This method is used to define the behavior of a brick when it collides with another object.
     */
     void onCollision(GameObject thisObj, GameObject otherObj);
}

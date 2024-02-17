package brick_strategies;

import danogl.GameObject;

/**
 * An interface representing a strategy for collision between objects in the game
 * */
public interface CollisionStrategy {

    /**
     * A method that is called when two objects collide
     * @param thisObj the first object
     * @param otherObj the second object
     * */
    public void onCollision(GameObject thisObj, GameObject otherObj);
}

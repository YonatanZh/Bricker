package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

/**
 * Basic collision strategy that removes the object from the game when it collides with another object.
 */
public class BasicCollisionStrategy implements CollisionStrategy{

    /**
     * The collection of game objects.
     */
    protected GameObjectCollection gameObjects;

    /**
     * Constructor for the basic collision strategy.
     * @param gameObjects the collection of game objects.
     */
    public BasicCollisionStrategy(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
    }

    /**
     * Removes the object from the game when it collides with another object.
     * @param thisObj the object that collided with another object.
     * @param otherObj the object that collided with this object.
     */
    @Override
        public void onCollision(GameObject thisObj, GameObject otherObj) {
            gameObjects.removeGameObject(thisObj);
        }
}

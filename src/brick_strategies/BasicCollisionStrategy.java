package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import main.BrickerGameManager;

/**`
 * A basic strategy for collision between objects in the game
 * */
public class BasicCollisionStrategy implements CollisionStrategy{

    protected GameObjectCollection gameObjects;

    /**
     * Creates a new BasicCollisionStrategy object
     * @param gameObjects the collection of game objects
     * */
    public BasicCollisionStrategy(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
    }

    /**
     * A method that is called when the object collides with another object
     * it removes the object that has this strategy from the game
     * @param thisObj the first object
     * @param otherObj the second object
     * */
    @Override
        public void onCollision(GameObject thisObj, GameObject otherObj) {
            gameObjects.removeGameObject(thisObj);
        }
}

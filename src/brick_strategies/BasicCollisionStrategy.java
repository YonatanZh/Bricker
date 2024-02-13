package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import main.BrickerGameManager;

public class BasicCollisionStrategy implements CollisionStrategy{

    protected GameObjectCollection gameObjects;

    public BasicCollisionStrategy(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
    }

    @Override
        public void onCollision(GameObject thisObj, GameObject otherObj) {
            gameObjects.removeGameObject(thisObj);
        }
}

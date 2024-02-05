package brick_strategies;

import danogl.GameObject;
import main.BrickerGameManager;

public class BasicCollisionStrategy implements CollisionStrategy{

    private BrickerGameManager gameManager;

    public BasicCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
        public void onCollision(GameObject thisObj, GameObject otherObj) {
            gameManager.deleteObject(thisObj);
            System.out.println("collision detected");
        }
}

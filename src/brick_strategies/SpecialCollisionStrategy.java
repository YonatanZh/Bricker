package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;
import gameobjects.Paddle;
import special_behaviors.ExtraPaddle;
import special_behaviors.ExtraPuck;
import gameobjects.GameObjectFactory;

import java.util.Random;

public class SpecialCollisionStrategy extends BasicCollisionStrategy implements CollisionStrategy {

    private static final int RANDOM_FACTOR = 4;
    private final Random rand;
    private GameObjectFactory gameObjectFactory;
    private Vector2 windowDimensions;
    private int ballRadius;
    private int ballSpeed;

    public SpecialCollisionStrategy(GameObjectCollection gameObjects, GameObjectFactory gameObjectFactory,
                                    Vector2 windowDimensions, int ballRadius, int ballSpeed) {
        super(gameObjects);
        this.gameObjectFactory = gameObjectFactory;
        this.windowDimensions = windowDimensions;
        this.ballRadius = ballRadius;
        this.ballSpeed = ballSpeed;
        this.rand = new Random();
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);

        int behavior = rand.nextInt() % RANDOM_FACTOR;

        switch (behavior) {
            case 0:
                ExtraPuck puck = new ExtraPuck(gameObjects, gameObjectFactory, ballRadius, ballSpeed,
                        new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2));
                puck.behave();
                break;
            case 1:
                ExtraPaddle paddle = new ExtraPaddle(gameObjects, gameObjectFactory, new Vector2(100, 20),
                        windowDimensions);
                paddle.behave();
                break;
            default:
                super.onCollision(thisObj, otherObj);
        }
    }
}

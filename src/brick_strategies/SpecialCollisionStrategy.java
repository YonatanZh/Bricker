package brick_strategies;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.Ball;
import gameobjects.LifeCounter;
import special_behaviors.BehaviorFactory;
import gameobjects.GameObjectFactory;

import java.util.Random;

public class SpecialCollisionStrategy extends BasicCollisionStrategy implements CollisionStrategy {

    //todo chagnge theis to the actual number of special behaviors
    private static final int RANDOM_FACTOR = 1;
    private final Random rand;
    private final BehaviorFactory behaviorFactory;
    private final LifeCounter lifeCounter;
    private GameManager owner;
    private GameObjectFactory gameObjectFactory;
    private Vector2 windowDimensions;
    private int ballRadius;
    private int ballSpeed;
    private Vector2 paddleSize;
    private Counter paddleCounter;
    private Ball ball;

    public SpecialCollisionStrategy(GameManager owner, GameObjectCollection gameObjects,
                                    GameObjectFactory gameObjectFactory,
                                    Vector2 windowDimensions, int ballRadius,
                                    Vector2 paddleSize, LifeCounter lifeCounter) {
        super(gameObjects);
        this.owner = owner;
        this.gameObjectFactory = gameObjectFactory;
        this.windowDimensions = windowDimensions;
        this.ballRadius = ballRadius;
        this.paddleSize = paddleSize;
        this.rand = new Random();
        this.lifeCounter = lifeCounter;
        this.behaviorFactory = new BehaviorFactory(owner, gameObjects, gameObjectFactory, windowDimensions, lifeCounter);
        this.paddleCounter = new Counter(0);
        this.ball = getBall();
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);

        int behavior = rand.nextInt() % RANDOM_FACTOR;

        switch (behavior) {
            case 0:
                behaviorFactory.createExtraPuck(ballRadius, thisObj.getCenter()).behave();
                break;
            case 1:
                if (paddleCounter.value() == 0) {
                    behaviorFactory.createExtraPaddle(paddleSize, paddleCounter).behave();
                }
                break;
            case 2:
                behaviorFactory.createCameraChange(ball, owner).behave();
                break;
            case 3:
                behaviorFactory.createExtraLife(thisObj.getCenter(), new Vector2(20, 20), windowDimensions, gameObjects, gameObjectFactory, lifeCounter).behave();
                break;
            default:
                break;
        }
    }

    private Ball getBall() {
        for (GameObject obj : gameObjects.objectsInLayer(Layer.DEFAULT)) {
            if (obj instanceof Ball) {
                return (Ball) obj;
            }
        }
        return null;
    }
}

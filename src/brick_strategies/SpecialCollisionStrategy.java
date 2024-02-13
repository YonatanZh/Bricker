package brick_strategies;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.Ball;
import gameobjects.Paddle;
import special_behaviors.BehaviorFacory;
import special_behaviors.CameraChange;
import special_behaviors.ExtraPaddle;
import special_behaviors.ExtraPuck;
import gameobjects.GameObjectFactory;

import java.util.Random;

public class SpecialCollisionStrategy extends BasicCollisionStrategy implements CollisionStrategy {

    //todo chagnge theis to the actual number of special behaviors
    private static final int RANDOM_FACTOR = 3;
    private final Random rand;
    private final BehaviorFacory behaviorFacory;
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
                                    Vector2 windowDimensions, int ballRadius, int ballSpeed,
                                    Vector2 paddleSize) {
        super(gameObjects);
        this.owner = owner;
        this.gameObjectFactory = gameObjectFactory;
        this.windowDimensions = windowDimensions;
        this.ballRadius = ballRadius;
        this.ballSpeed = ballSpeed;
        this.paddleSize = paddleSize;
        this.rand = new Random();
        this.behaviorFacory = new BehaviorFacory(owner, gameObjects, gameObjectFactory, windowDimensions);
        this.paddleCounter = new Counter(0);
        this.ball = getBall();
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);

        int behavior = rand.nextInt() % RANDOM_FACTOR;

        switch (behavior) {
            case 0:
                behaviorFacory.createExtraPuck(ballRadius, ballSpeed, thisObj.getCenter()).behave();
                break;
            case 1:
                if (paddleCounter.value() == 0) {
                    behaviorFacory.createExtraPaddle(paddleSize, paddleCounter).behave();
                }
                break;
            case 2:
                behaviorFacory.createCameraChange(ball, owner).behave();
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

package brick_strategies;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.Ball;
import gameobjects.LifeCounter;
import gameobjects.ObjectTracker;
import special_behaviors.BehaviorFactory;
import gameobjects.GameObjectFactory;
import special_behaviors.SpecialBehaviors;

import java.util.Random;

public class SpecialCollisionStrategy extends BasicCollisionStrategy implements CollisionStrategy {

    //todo chagnge theis to the actual number of special behaviors
    private static final int RANDOM_FACTOR = 1;
    private static final Random rand = new Random();
    private final LifeCounter lifeCounter;
    private final SpecialBehaviors[] allBehaviors;
    private final GameManager owner;
    private final GameObjectFactory gameObjectFactory;
    private final Vector2 windowDimensions;
    private final int ballRadius;
    private final Vector2 paddleSize;
    private final ObjectTracker tracker;
    private final Counter paddleCounter;


    public SpecialCollisionStrategy(GameManager owner, GameObjectCollection gameObjects,
                                    GameObjectFactory gameObjectFactory,
                                    Vector2 windowDimensions, int ballRadius,
                                    Vector2 paddleSize, LifeCounter lifeCounter, ObjectTracker tracker,
                                    SpecialBehaviors[] allBehaviors) {
        super(gameObjects);
        this.owner = owner;
        this.gameObjectFactory = gameObjectFactory;
        this.windowDimensions = windowDimensions;
        this.ballRadius = ballRadius;
        this.paddleSize = paddleSize;
        this.tracker = tracker;
        this.lifeCounter = lifeCounter;
        this.allBehaviors = allBehaviors;
        this.paddleCounter = new Counter(0);
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);

        int behavior = rand.nextInt() % RANDOM_FACTOR;

        switch (behavior) {
            case 2: // extra puck
                allBehaviors[behavior].behave(thisObj.getCenter());
                break;
            case 1: // todo fix this
                if (paddleCounter.value() == 0) {
                    behaviorFactory.createExtraPaddle(paddleSize, paddleCounter).behave(windowDimensions);
                }
                break;
            case 0:
                behaviorFactory.createCameraChange(owner, tracker).behave();
                break;
            case 3:
                behaviorFactory.createExtraLife(thisObj.getCenter(), new Vector2(20, 20), windowDimensions, gameObjects, gameObjectFactory, lifeCounter).behave();
                break;
            case 4:
                behaviorFactory.createDouble();
                break;
            default:
                break;
        }
    }
}

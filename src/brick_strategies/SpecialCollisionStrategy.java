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
import special_behaviors.Behaviors;

import java.util.Random;

/**
 * This class is a collision strategy that adds special behaviors to the game when a collision occurs.
 */
public class SpecialCollisionStrategy extends BasicCollisionStrategy implements CollisionStrategy {

    //todo chagnge theis to the actual number of special behaviors
    //todo maybe use enum?
    private static final int RANDOM_FACTOR = 5;
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

    /**
     * Constructor for the SpecialCollisionStrategy class.
     *
     * @param owner             the game manager
     * @param gameObjects       the game objects
     * @param gameObjectFactory the game object factory
     * @param windowDimensions  the window dimensions
     * @param ballRadius        the ball radius
     * @param ballSpeed         the ball speed
     * @param paddleSize        the paddle size
     * @param lifeCounter       the life counter
     */
    public SpecialCollisionStrategy(GameManager owner, GameObjectCollection gameObjects,
                                    GameObjectFactory gameObjectFactory,
                                    Vector2 windowDimensions, int ballRadius, int ballSpeed,
                                    Vector2 paddleSize, LifeCounter lifeCounter) {
        super(gameObjects);
        this.owner = owner;
        this.gameObjectFactory = gameObjectFactory;
        this.windowDimensions = windowDimensions;
        this.ballRadius = ballRadius;
        this.ballSpeed = ballSpeed;
        this.paddleSize = paddleSize;
        this.rand = new Random();
        this.lifeCounter = lifeCounter;
        this.behaviorFactory = new BehaviorFactory(owner, gameObjects, gameObjectFactory, windowDimensions, lifeCounter);
        this.paddleCounter = new Counter(0);
        this.ball = getBall();
    }

    /**
     * A method that is called when an object collides with the special object.
     *
     * @param thisObj  the object that has this strategy
     * @param otherObj other object that this object collided with
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);

        int behaviorAmount = Behaviors.values().length;
        int behaviorIndex = Math.min(rand.nextInt() % RANDOM_FACTOR, behaviorAmount);
        Behaviors behavior = Behaviors.values()[behaviorIndex];

        switch (behavior) {
            case Behaviors.EXTRA_PUCK:
                // adds two extra pucks (small balls) to the game
                behaviorFactory.createExtraPuck(ballRadius, ballSpeed, thisObj.getCenter()).behave();
                break;
            case Behaviors.EXTRA_PADDLE:
                // adds an extra paddle to the game
                if (paddleCounter.value() == 0) {
                    behaviorFactory.createExtraPaddle(paddleSize, paddleCounter).behave();
                }
                break;
            case Behaviors.CAMERA_CHANGE:
                // changes the camera to follow the ball
                behaviorFactory.createCameraChange(ball, owner).behave();
                break;
            case Behaviors.EXTRA_LIFE:
                // adds an extra life to the game that the player can collect with the paddle
                behaviorFactory.createExtraLife(thisObj.getCenter(), new Vector2(20, 20), windowDimensions, gameObjects, gameObjectFactory, lifeCounter).behave();
                break;
            case Behaviors.NONE:
                break;
        }
    }

    /**
     * A method that returns the ball object.
     *
     * @return the ball object
     */
    private Ball getBall() {
        for (GameObject obj : gameObjects.objectsInLayer(Layer.DEFAULT)) {
            if (obj instanceof Ball) {
                return (Ball) obj;
            }
        }
        return null;
    }
}

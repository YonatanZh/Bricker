package bricker.special_behaviors;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;
import bricker.gameobjects.GameObjectFactory;

import java.util.Random;

import static bricker.main.Constants.*;

/**
 * This class is used to create an extra puck behavior.
 */
public class ExtraPuck implements SpecialBehaviors {
    private final GameObjectCollection gameObjects;
    private final GameObjectFactory gameObjectFactory;
    private final float puckRadius;
    private final Random rand;

    /**
     * Constructor for the ExtraPuck class.
     *
     * @param gameObjects       The collection of game objects.
     * @param gameObjectFactory The factory used to create game objects.
     * @param ballRadius        The radius of the puck.
     */
    public ExtraPuck(GameObjectCollection gameObjects, GameObjectFactory gameObjectFactory, int ballRadius) {
        this.gameObjects = gameObjects;
        this.gameObjectFactory = gameObjectFactory;
        this.puckRadius = ballRadius * PUCK_REDUCTION_FACTOR;
        rand = new Random();
    }

    /**
     * This method is used to create the extra puck behavior.
     *
     * @param position1 The position of the first paddle.
     * @param position2 The position of the second paddle.
     */
    public void behave(Vector2 position1, Vector2 position2) {

        for (int i = 0; i < PUCK_AMOUNT; i++) {
            GameObject puck = gameObjectFactory.createBall(PUCK_PATH, PUCK_SOUND_PATH,
                    puckRadius, position1);
            double angle = rand.nextDouble() * Math.PI;
            float velocityX = (float) Math.cos(angle) * PUCK_SPEED;
            float velocityY = (float) Math.sin(angle) * PUCK_SPEED;
            puck.setVelocity(new Vector2(velocityX, velocityY));
            gameObjects.addGameObject(puck);
        }
    }
}

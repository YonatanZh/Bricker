package special_behaviors;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameobjects.Ball;
import gameobjects.GameObjectFactory;
import main.BrickerGameManager;

import java.util.Random;

public class ExtraPuck implements SpecialBehaviors {

    private static final String PUCK_PATH = "assets/mockBall.png";
    private static final String SOUND_PATH = "assets/blop_cut_silenced.wav";
    private static final float REDUCTION_FACTOR = 0.75f;
    private static final int PUCK_AMOUNT = 2;
    private static final int PUCK_SPEED = 175;


    private final GameObjectCollection gameObjects;
    private final GameObjectFactory gameObjectFactory;
    private final float puckRadius;
    private final Vector2 position;
    private final Random rand;

    public ExtraPuck(GameObjectCollection gameObjects, GameObjectFactory gameObjectFactory, int ballRadius,
                     Vector2 position) {
        this.gameObjects = gameObjects;
        this.gameObjectFactory = gameObjectFactory;
        this.puckRadius = ballRadius * REDUCTION_FACTOR;
        this.position = position;
        rand = new Random();
    }

    //todo change the string literal
    public void behave() {

        for (int i = 0; i < PUCK_AMOUNT; i++) {
            GameObject puck = gameObjectFactory.createBall(PUCK_PATH, SOUND_PATH,
                    puckRadius, position);

            double angle = rand.nextDouble() * Math.PI;
            float velocityX = (float) Math.cos(angle) * PUCK_SPEED;
            float velocityY = (float) Math.sin(angle) * PUCK_SPEED;

            puck.setVelocity(new Vector2(velocityX, velocityY));

            gameObjects.addGameObject(puck);
        }
    }
}

package bricker.special_behaviors;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.game_objects.GameObjectFactory;

import static bricker.main.Constants.PADDLE_PATH;

/**
 * A SpecialBehavior that adds an extra paddle to the game.
 */
public class ExtraPaddle implements SpecialBehaviors {
    private final GameObjectCollection gameObjects;
    private final GameObjectFactory gameObjectFactory;
    private final Vector2 size;
    private final Counter paddleCounter;

    /**
     * Constructor for the ExtraPaddle class.
     *
     * @param gameObjects       The collection of game objects.
     * @param gameObjectFactory The factory used to create game objects.
     * @param size              The size of the paddle.
     */
    public ExtraPaddle(GameObjectCollection gameObjects, GameObjectFactory gameObjectFactory, Vector2 size) {
        this.gameObjects = gameObjects;
        this.gameObjectFactory = gameObjectFactory;
        this.size = size;
        this.paddleCounter = new Counter(0);
    }

    /**
     * This method is used to add an extra paddle to the game. cannot have more than one extra paddle at a
     * time.
     *
     * @param position1 The position of the first paddle.
     * @param position2 The position of the second paddle.
     */
    public void behave(Vector2 position1, Vector2 position2) {
        position1 = position1.mult(0.5f);
        if (paddleCounter.value() == 0) {
            paddleCounter.increment();
            GameObject paddle = gameObjectFactory.createDisappearingPaddle(PADDLE_PATH, size, position1,
                    gameObjects, paddleCounter);
            gameObjects.addGameObject(paddle);
        }
    }
}

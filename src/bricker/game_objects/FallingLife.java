package bricker.game_objects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.collisions.Collision;

/**
 * A class representing a falling life object.
 */
public class FallingLife extends GameObject {
    private final GameObjectCollection gameObjects;
    private final Vector2 windowDimensions;
    private final LifeCounter lifeCounter;

    /**
     * Creates a new falling life object.
     *
     * @param topLeftCorner    the top left corner of the object
     * @param dimensions       the dimensions of the object
     * @param renderable       the renderable for the object
     * @param windowDimensions the dimensions of the window
     * @param gameObjects      the collection of game objects
     * @param lifeCounter      the life counter
     */
    public FallingLife(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       Vector2 windowDimensions, GameObjectCollection gameObjects, LifeCounter lifeCounter) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjects = gameObjects;
        this.windowDimensions = windowDimensions;
        this.lifeCounter = lifeCounter;
    }

    /**
     * Updates the life object. if the falling life is out of bounds it will be removed from the game.
     *
     * @param deltaTime the time passed since the last update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (isOutOfBounds()) {
            gameObjects.removeGameObject(this);
        }
    }


    /**
     * Defines what objects it should collide with.
     *
     * @param other the object that the paddle collided with
     * @return if the object should collide with the paddle or not
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other instanceof Paddle && !(other instanceof DisappearingPaddle);
    }

    /**
     * A method that checks if the falling life is out of bounds.
     *
     * @return if the falling life is out of bounds
     */
    private boolean isOutOfBounds() {
        return getCenter().y() > windowDimensions.y();
    }

    /**
     * A method that is called when an object collides with the paddle
     * If a collision occurred with what shouldCollideWith defined it will add a life to the player
     *
     * @param other     the object that the paddle collided with
     * @param collision the collision that occurred
     */
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.lifeCounter.gainLife();
        gameObjects.removeGameObject(this);
    }
}

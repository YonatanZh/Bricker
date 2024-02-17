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
     * Creates a new FallingLife object.
     * @param topLeftCorner the position of the top left corner of the life
     * @param dimensions the dimensions of the life
     * @param renderable that presents an image of the life
     * @param windowDimensions the window dimensions
     * @param gameObjects a collection of all the game objects
     * @param lifeCounter the life counter object
     */
    public FallingLife(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                        Vector2 windowDimensions, GameObjectCollection gameObjects, LifeCounter lifeCounter) {
            super(topLeftCorner, dimensions, renderable);
            this.gameObjects = gameObjects;
            this.windowDimensions = windowDimensions;
            this.lifeCounter = lifeCounter;
        }

        /**
         * If the life is out of the window dimensions it is removed from the game.
         */
        @Override
        public void update(float deltaTime) {
            super.update(deltaTime);
            if (isOutOfBounds()) {
                gameObjects.removeGameObject(this);
            }
        }

        /**
         * Defines for the object which objects it can collide with for the onCollisionEnter method.
         */
        @Override
        public boolean shouldCollideWith(GameObject other) {
            return other instanceof Paddle && !(other instanceof DisappearingPaddle);
        }

        /**
         * @return if life is out of bounds or not.
         */
        private boolean isOutOfBounds() {
            return getCenter().y() > windowDimensions.y();
        }

        /**
        * A method that is called when an object collides with the paddle
        * If a collision occurred with what shouldCollideWith defined it will add a life to the player
        * @param other the object that the paddle collided with
        * @param collision the collision that occurred
        * */
        public void onCollisionEnter(GameObject other, Collision collision) {
            super.onCollisionEnter(other, collision);
            this.lifeCounter.gainLife();
            gameObjects.removeGameObject(this);
        }
}

package gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.collisions.Collision;

public class FallingLife extends GameObject {

    private final GameObjectCollection gameObjects;
    private final Vector2 windowDimensions;
    private final LifeCounter lifeCounter;

    public FallingLife(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Vector2 velocity,
                        Vector2 windowDimensions, GameObjectCollection gameObjects, LifeCounter lifeCounter) {
            super(topLeftCorner, dimensions, renderable);
            this.gameObjects = gameObjects;
            this.windowDimensions = windowDimensions;
            this.lifeCounter = lifeCounter;

        }

        @Override
        public void update(float deltaTime) {
            super.update(deltaTime);
            if (isOutOfBounds()) {
                gameObjects.removeGameObject(this);
            }
        }

        @Override
        public boolean shouldCollideWith(GameObject other) {
            return other instanceof Paddle && !(other instanceof DisappearingPaddle);
        }

        private boolean isOutOfBounds() {
            return getCenter().y() > windowDimensions.y();
        }

        public void onCollisionEnter(GameObject other, Collision collision) {
            super.onCollisionEnter(other, collision);
            this.lifeCounter.gainLife();
        }
}

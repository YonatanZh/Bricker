package gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * A class representing a life counter in the game.
 */
public class LifeCounter extends GameObject {
    private static final int MAX_LIVES = 4; //todo what is this???

    private static final String LIFE_HEART_PATH = "assets/heart.png";
    private final NumericLifeDisplay numericLifeCounter;
    private final GraphicalLifeDisplay graphicalLives;
    private final Counter lives;
    private final Vector2 lifeDimensions;
    Renderable lifeImage;

    /**
     * Creates a new LifeCounter object.
     * @param topLeftCorner the position of the top left corner of the life counter
     * @param dimensions the dimensions of the life counter
     * @param imageReader the image reader
     * @param lives the life counter
     * @param objectSize the size of the life
     * @param buffer the buffer between the lives
     * @param gameObjects a collection of all the game objects
     * @param gameObjectFactory the game object factory
     */
    public LifeCounter(Vector2 topLeftCorner, Vector2 dimensions, ImageReader imageReader, Counter lives, float objectSize,
                       int buffer, GameObjectCollection gameObjects, GameObjectFactory gameObjectFactory) {
        super(topLeftCorner, dimensions, null);
        this.lives = lives;
        this.lifeImage = imageReader.readImage(LIFE_HEART_PATH, true);
        this.lifeDimensions = new Vector2(objectSize, objectSize);
        this.numericLifeCounter = (NumericLifeDisplay) gameObjectFactory.createNumericLifeDisplay(topLeftCorner,
                lifeDimensions, this.lives, gameObjects);

        Vector2 indentation = new Vector2(objectSize + buffer, 0);
        this.graphicalLives = (GraphicalLifeDisplay) gameObjectFactory.createGraphicalLifeDisplay(topLeftCorner.add(indentation),
                lifeDimensions, lifeImage, objectSize + buffer, this.lives, MAX_LIVES, gameObjects);
    }

    /**
     * Updates the life counter.
     * @param deltaTime the time passed since the last update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    public void gainLife() {
        if (lives.value() < MAX_LIVES) {
            lives.increment();
            graphicalLives.gainLife(lifeDimensions, lives.value() - 1);
            numericLifeCounter.gainLife();
        }
    }

    /**
     * Creates a new life object.
     * @param gameObjectFactory the game object factory
     * @param topLeftCorner the position of the top left corner of the life
     * @param dimensions the dimensions of the life
     * @param velocity the velocity of the life
     * @param windowDimensions the window dimensions
     * @param gameObjects a collection of all the game objects
     */
    public void createLife(GameObjectFactory gameObjectFactory, Vector2 topLeftCorner, Vector2 dimensions,
                           Vector2 velocity, Vector2 windowDimensions, GameObjectCollection gameObjects) {
        FallingLife life = (FallingLife) gameObjectFactory.createFallingLife(topLeftCorner, dimensions, lifeImage,
                windowDimensions, gameObjects, this);
        life.setCenter(topLeftCorner);
        life.setVelocity(velocity);
        gameObjects.addGameObject(life, danogl.collisions.Layer.DEFAULT);
    }

    /**
     * Removes a life from the game.
     */
    public void loseLife() {
        graphicalLives.loseLife();
        numericLifeCounter.loseLife();
    }

}

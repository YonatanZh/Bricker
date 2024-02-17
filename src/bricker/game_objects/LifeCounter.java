package gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import static bricker.main.Constants.LIFE_HEART_PATH;

public class LifeCounter extends GameObject {
    private static final int MAX_LIVES = 4; //todo what is this???
    // todo: this was supposed to be the max life for the player for when add lives back. add this to the
    //  constants file

    private final NumericLifeDisplay numericLifeCounter;
    private final GraphicalLifeDisplay graphicalLives;
    private final Counter lives;
    private final Vector2 lifeDimensions;
    Renderable lifeImage;

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

    public void createLife(GameObjectFactory gameObjectFactory, Vector2 topLeftCorner, Vector2 dimensions,
                           Vector2 velocity, Vector2 windowDimensions, GameObjectCollection gameObjects) {
        FallingLife life = (FallingLife) gameObjectFactory.createFallingLife(topLeftCorner, dimensions, lifeImage,
                windowDimensions, gameObjects, this);
        life.setCenter(topLeftCorner);
        life.setVelocity(velocity);
        gameObjects.addGameObject(life, danogl.collisions.Layer.DEFAULT);
    }
    public void loseLife() {
        graphicalLives.loseLife();
        numericLifeCounter.loseLife();
    }

}

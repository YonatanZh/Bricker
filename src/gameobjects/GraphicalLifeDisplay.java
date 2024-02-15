package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class
GraphicalLifeDisplay extends GameObject {

    private final GameObjectCollection gameObjects;
    private final Counter lives;
    private final GameObject[] allGraphicalLives;

    // todo: made so we get a renderable. thought it was better practice - maybe change it in numerical too?
    // todo: it maybe also ok that graphic is images and numerical is text.
    public GraphicalLifeDisplay(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                                float objectSize, int buffer, Counter lives,
                                GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable);
        this.allGraphicalLives = new GameObject[lives.value()];
        for (int i = 0; i < lives.value(); i++) {
            float indent =  i * (objectSize + buffer);
            Vector2 indentation = new Vector2(indent, 0);
            Vector2 position = topLeftCorner.add(indentation);
            allGraphicalLives[i] = new GameObject(position, dimensions, renderable);
            allGraphicalLives[i].setCenter(position);
            gameObjects.addGameObject(allGraphicalLives[i], Layer.DEFAULT);
        }
        this.gameObjects = gameObjects;
        this.lives = lives;
    }

    public void loseLife() {
        gameObjects.removeGameObject(allGraphicalLives[lives.value()], Layer.UI);
    }

    public void gainLife(Vector2 topLeftCorner, float objectSize, int buffer) {

    }
}
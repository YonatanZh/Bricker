package bricker.game_objects;

import danogl.GameObject;
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
    private final Renderable renderable;
    private final float indent;
    private Vector2 positionForNewLife;

    // todo: made so we get a renderable. thought it was better practice - maybe change it in numerical too?
    // todo: it maybe also ok that graphic is images and numerical is text.
    public GraphicalLifeDisplay(Vector2 topLeftCorner, Vector2 dimensions,
                                float indent, Counter lives, int maxLives,
                                GameObjectCollection gameObjects, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        this.allGraphicalLives = new GameObject[maxLives];
        this.renderable = renderable;
        this.gameObjects = gameObjects;
        this.lives = lives;
        this.indent = indent;
        positionForNewLife = topLeftCorner;
        for (int i = 0; i < lives.value(); i++) {
            gainLife(dimensions, i);


            this.positionForNewLife = topLeftCorner.add(new Vector2(this.indent * (i+1), 0));
        }
    }

    public void loseLife() {
        gameObjects.removeGameObject(allGraphicalLives[lives.value()], Layer.UI);
        this.positionForNewLife = positionForNewLife.subtract(new Vector2(this.indent, 0));
    }

    public void gainLife(Vector2 objectSize, int index) {
        allGraphicalLives[index] = new GameObject(positionForNewLife, objectSize, renderable);
        allGraphicalLives[index].setCenter(positionForNewLife);
        gameObjects.addGameObject(allGraphicalLives[index], Layer.UI);
        this.positionForNewLife = positionForNewLife.add(new Vector2(this.indent, 0));
    }
}
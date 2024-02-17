package bricker.game_objects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * A class representing a graphical life display.
 */
public class
GraphicalLifeDisplay extends GameObject {
    private final GameObjectCollection gameObjects;
    private final Counter lives;
    private final GameObject[] allGraphicalLives;
    private final Renderable renderable;
    private final float indent;
    private Vector2 positionForNewLife;

    /**
     * Creates a new GraphicalLifeDisplay object.
     * @param topLeftCorner the position of the top left corner of the life display
     * @param dimensions the dimensions of the life display
     * @param indent the indent between the lives
     * @param lives the life counter
     * @param maxLives the maximum number of lives
     * @param gameObjects a collection of all the game objects
     * @param renderable that presents an image of the life
     */
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

    /**
     * Removes a graphical life from the display.
     */
    public void loseLife() {
        gameObjects.removeGameObject(allGraphicalLives[lives.value()], Layer.UI);
        this.positionForNewLife = positionForNewLife.subtract(new Vector2(this.indent, 0));
    }

    /**
     * Adds a graphical life to the display.
     * @param objectSize the size of the life
     * @param index the index of the life in the array
     */
    public void gainLife(Vector2 objectSize, int index) {
        allGraphicalLives[index] = new GameObject(positionForNewLife, objectSize, renderable);
        allGraphicalLives[index].setCenter(positionForNewLife);
        gameObjects.addGameObject(allGraphicalLives[index], Layer.UI);
        this.positionForNewLife = positionForNewLife.add(new Vector2(this.indent, 0));
    }
}
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
    private final Vector2 lifeDimensions;
    private Vector2 positionForNewLife;
    private final float indent;
    private final Counter lives;
    private final GameObject[] allGraphicalLives;
    private final GameObjectCollection gameObjects;
    private final Renderable renderable;

    /**
     * Creates a new GraphicalLifeDisplay object.
     *
     * @param topLeftCorner the position of the top left corner of the life
     * @param dimensions    the dimensions of the life
     * @param indent        the indent between the lives
     * @param lives         the life counter
     * @param gameObjects   a collection of all the game objects
     * @param maxLives      the maximum number of lives
     * @param renderable    that presents an image of the life
     */
    public GraphicalLifeDisplay(Vector2 topLeftCorner, Vector2 dimensions, float indent, Counter lives,
                                GameObjectCollection gameObjects, int maxLives, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        this.positionForNewLife = topLeftCorner;
        this.indent = indent;
        this.lives = lives;
        this.gameObjects = gameObjects;
        this.allGraphicalLives = new GameObject[maxLives];
        this.renderable = renderable;
        this.lifeDimensions = dimensions;
        setInitialLives();
    }

    private void setInitialLives() {
        for (int i = 0; i < lives.value(); i++) {
            gainLife(i);
            this.positionForNewLife = positionForNewLife.add(new Vector2(this.indent, 0));
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
     *
     * @param index the index of the life in the array
     */
    public void gainLife(int index) {
        allGraphicalLives[index] = new GameObject(positionForNewLife, lifeDimensions, renderable);
        allGraphicalLives[index].setCenter(positionForNewLife);
        gameObjects.addGameObject(allGraphicalLives[index], Layer.UI);
        this.positionForNewLife = positionForNewLife.add(new Vector2(this.indent, 0));
    }
}
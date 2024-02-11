package gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class
GraphicalLifeDisplay extends GameObject {

    public GraphicalLifeDisplay(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Vector2 position) {
        super(topLeftCorner, dimensions, renderable);
        this.setCenter(position);
    }


}
package gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class LifeCounter extends GameObject {
    public LifeCounter(Vector2 topLeftCorner, Vector2 dimensions) {
        super(topLeftCorner, dimensions, null);
    }
}

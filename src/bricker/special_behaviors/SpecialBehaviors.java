package bricker.special_behaviors;

import danogl.util.Vector2;

/**
 * This interface is used to define the behavior of a special bricks.
 */
public interface SpecialBehaviors {

    /**
     * This method is used to define the behavior of a special object.
     *
     * @param position1 The location to activate the behavior.
     * @param position2 The location to activate the behavior.
     */
    void behave(Vector2 position1, Vector2 position2);
}

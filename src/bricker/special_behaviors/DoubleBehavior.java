package bricker.special_behaviors;

import danogl.util.Counter;
import danogl.util.Vector2;
import java.util.Random;
import static bricker.main.Constants.MAX_BEHAVIORS_PER_BRICK;
import static bricker.main.Constants.MAX_PADDLES;

/**
 * This class is used to define the behavior of a special brick that doubles the power-ups.
 */
public class DoubleBehavior implements SpecialBehaviors{
    private static final Random rand = new Random();
    private final SpecialBehaviors[] allBehaviors;

    /**
     * Constructor for the DoubleBehavior class.
     *
     * @param allBehaviors The behaviors to be doubled.
     */
    public DoubleBehavior(SpecialBehaviors[] allBehaviors) {
        this.allBehaviors = new SpecialBehaviors[allBehaviors.length];
        System.arraycopy(allBehaviors, 0, this.allBehaviors, 0, allBehaviors.length);
    }

    /**
     * This method is used to define the behavior of a special object.
     *
     * @param position1 The location to activate the behavior.
     * @param position2 The location to activate the behavior.
     */
    @Override
    public void behave(Vector2 position1, Vector2 position2) {
        Counter counter = new Counter();
        behaveHelper(position1, position2, counter);
    }

    private void behave(int behavior, Vector2 position1, Vector2 position2) {
        if (behavior == MAX_PADDLES) {
            allBehaviors[behavior].behave(position2, position1);
            return;
        }
        allBehaviors[behavior].behave(position1, position2);
    }

    private void behaveHelper(Vector2 position1, Vector2 position2,Counter counter) {
        if (counter.value() < MAX_BEHAVIORS_PER_BRICK) {
            int behavior = rand.nextInt(allBehaviors.length); //first actual power-ups, no double
            behave(behavior, position1, position2);
            counter.increment();
            if (counter.value() < MAX_BEHAVIORS_PER_BRICK) {
                dealWithDouble(position1, position2, counter);
            }
        }
    }

    private void dealWithDouble(Vector2 position1, Vector2 position2,Counter counter) {
        int behavior = rand.nextInt(allBehaviors.length + 1); //+1 represents double
        if (behavior == allBehaviors.length) { //got double
            behaveHelper(position1,position2, counter);
        } else {
            behave(behavior, position1, position2);
            counter.increment();
        }
    }
}

package special_behaviors;

import danogl.util.Counter;

import java.util.Random;

import static main.BrickerGameManager.MAX_BEHAVIORS_PER_BRICK;

public class DoubleBehavior implements SpecialBehaviors{

    private static final Random rand = new Random();
    private final SpecialBehaviors[] allBehaviors;

    public DoubleBehavior(SpecialBehaviors[] allBehaviors) {
        this.allBehaviors = new SpecialBehaviors[allBehaviors.length];
        System.arraycopy(allBehaviors, 0, this.allBehaviors, 0, allBehaviors.length);
    }

    @Override
    public void behave() {
        Counter counter = new Counter();
        behaveHelper(counter);
    }

    private void behaveHelper(Counter counter) {
        if (counter.value() < MAX_BEHAVIORS_PER_BRICK) {
            int behavior = rand.nextInt(allBehaviors.length); //first actual power-ups, no double
            allBehaviors[behavior].behave();
            counter.increment();
            if (counter.value() < MAX_BEHAVIORS_PER_BRICK) {
                dealWithDouble(counter);
            }
        }
    }

    private void dealWithDouble(Counter counter) {
        int behavior = rand.nextInt(allBehaviors.length + 1); //+1 represents double
        if (behavior == allBehaviors.length) { //got double
            behaveHelper(counter);
        } else {
            allBehaviors[behavior].behave();
            counter.increment();
        }
    }
}

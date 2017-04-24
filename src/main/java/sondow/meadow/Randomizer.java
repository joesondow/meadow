package sondow.meadow;

import java.util.List;
import java.util.Random;

/**
 * Custom randomizer wrapper class allows for deterministic unit tests.
 *
 * @author joesondow
 */
public class Randomizer {

    Random random;

    public Randomizer(Random random) {
        this.random = random;
    }

    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    public <T> T oneOf(List<T> things) {
        return things.get(nextInt(things.size()));
    }
}

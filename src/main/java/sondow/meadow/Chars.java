package sondow.meadow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The cast of characters.
 *
 * @author @JoeSondow
 */
public class Chars {
    public static final List<String> GRASS_TYPE = Arrays.asList("🌱", "🌿", "☘️", "🍀");
    public static final List<String> FLOWER_TYPES = Arrays.asList("🌼", "🌻", "🌸", "🌺", "🌷",
            "🍄");
    public static final List<String> ANIMAL_TYPES = Arrays.asList("🐌", "🐇", "🐈", "🐀", "🐿️",
            "🐒", "🐁", "🐓", "🐢", "🐍", "🐝", "🦋", "🦎");

    @SuppressWarnings("serial")
    public static final Map<String, String> ANIMALS_TO_TREATS = new HashMap<String, String>() {
        {
            // Bunny, carrot
            put("🐇", "🥕");
            // Cat, bacon
            put("🐈", "🥓");
            // Rat, cheese
            put("🐀", "🧀");
            // Mouse, cheese
            put("🐁", "🧀");
            // Chicken, corn
            put("🐓", "🌽");
            // Turtle, salad
            put("🐢", "🥗");
            // Chipmunk, chestnut
            put("🐿️", "🌰");
            // Monkey, banana
            put("🐒", "🍌");
        }
    };

    public static final List<String> PATH_TYPES = Arrays.asList("🌱");
}

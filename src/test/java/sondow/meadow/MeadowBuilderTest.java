package sondow.meadow;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MeadowBuilderTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testBuildMeadowWithFlowersAndAnimal() {
        Random random = new Random(1L);
        MeadowBuilder builder = new MeadowBuilder(random);
        String meadow = builder.build();
        String expected = "" +
                "🌱🌱🌷🌱🌸🌼🌱🌱🌷\n" +
                "🌱🌿🌱🌷🌱🌱🌱🌱🌱\n" +
                "🌱🌱🌱🌱🌼🌱🌱🌷🌼\n" +
                "🌱🌱🌸🌱🌱🌱🌼🐀🌱\n" +
                "🌷🌱🌱🌼🌱🌱🌼🌱🌼\n" +
                "🌱🌷🌱🌱🌼🌱🌼🌷🌼\n" +
                "🌸🌱🌷🌱🌸🌼🌱🌿🌼";

        assertEquals(expected, meadow);
    }

    @Test
    public final void testBuildMeadowWithNoFlowersNorAnimals() {
        Random random = new Random(2L);
        MeadowBuilder builder = new MeadowBuilder(random);
        String meadow = builder.build();
        String expected = "" +
                "🌿🌿🌿🍀🍀🍀🍀🍀🌿\n" +
                "🌿🌿🌿🌿🍀🌿🌿🍀🌿\n" +
                "🌿🌿🍀🍀🍀🌿🌿🍀🍀\n" +
                "🌿🌿🌱🌿🌿🌱🌿🍀🍀\n" +
                "🍀🌿🌿🌿🌿🌿🍀🍀🌿\n" +
                "🌿🌿🍀🌿🌿🌿🌿🌿🌿\n" +
                "🍀🌿🌱🌿🍀🌿🌿🍀🍀";

        assertEquals(expected, meadow);
    }

    @Test
    public final void testBuildPathThroughTheMeadowWhereTreatTouchesEarlyPathCorner() {
        Random random = new Random(43L);
        MeadowBuilder builder = new MeadowBuilder(random);
        String meadow = builder.build();
        String expected = "" +
                "🍄🍄🍄🍄🍄🍄🍄🍄🍄\n" +
                "🍄🌱🌱🌱🌱🌱🍄🍄🍄\n" +
                "🍄🌱🍄🍄🍄🌱🌱🌱🍄\n" +
                "🍄🌱🍄🍄🍌🍄🍄🌱🍄\n" +
                "🍄🌱🍄🌱🌱🍄🍄🌱🐒\n" +
                "🍄🌱🌱🌱🍄🍄🍄🍄🍄\n" +
                "🍄🍄🍄🍄🍄🍄🍄🍄🍄";

        assertEquals(expected, meadow);
    }

    @Test
    public final void testBuildPathThroughTheMeadowMonkeyBananaNoCornerNicks() {
        Random random = new Random(74L);
        MeadowBuilder builder = new MeadowBuilder(random);
        String meadow = builder.build();
        String expected = "" +
                "🌸🌺🌸🌺🍄🌸🌺🌸🌸\n" +
                "🌺🌸🌸🌸🌸🌸🌸🌸🌸\n" +
                "🌸🌸🌱🌱🌱🌸🌸🌱🐒\n" +
                "🌸🌱🌱🌸🌱🌸🌸🌱🌸\n" +
                "🌸🌱🌸🌸🌱🌸🌸🌱🌸\n" +
                "🌸🌱🍌🌸🌱🌱🌱🌱🌸\n" +
                "🌸🌸🌸🌸🌸🌸🌸🌸🌸";

        assertEquals(expected, meadow);
    }

    @Test
    public final void testBuildPathThroughTheMeadowWhereEarlyPathNicksCornerOfLaterPath() {
        Random random = new Random(206L);
        MeadowBuilder builder = new MeadowBuilder(random);
        String meadow = builder.build();
        String expected = "" +
                "🌺🌺🌺🌺🌺🌺🌺🌺🌺\n" +
                "🌺🌱🌱🌱🌱🌱🌱🌱🌺\n" +
                "🌺🌱🌺🌺🌺🌺🌺🌱🐒\n" +
                "🌺🌱🌺🌱🌱🌱🌱🌺🌺\n" +
                "🌺🌱🌺🌱🌺🌺🌱🌱🌺\n" +
                "🌺🌱🌱🌱🌺🌺🌺🍌🌺\n" +
                "🌺🌺🌺🌺🌺🌼🌺🌺🌺";

        assertEquals(expected, meadow);
    }

}

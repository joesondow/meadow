package sondow.meadow;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for Grid.
 *
 * @author @JoeSondow
 */
public class GridTest {

    Grid grid;

    @Before
    public void setUp() throws Exception {
        grid = new Grid(3, 4, "M");
    }

    @Test
    public void testCreateGetPutAndToString() {
        assertEquals("MMMM\nMMMM\nMMMM", grid.toString());
        assertEquals("M", grid.getCellContents(1, 0));
        grid.put(1, 0, "P");
        grid.put(2, 3, "Q");
        assertEquals("P", grid.getCellContents(1, 0));
        assertEquals("M", grid.getCellContents(1, 1));
        assertEquals("MMMM\nPMMM\nMMMQ", grid.toString());
    }

}
package sondow.meadow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        grid = new Grid(5, 7, "M");
    }

    @Test
    public void testCreateGetPutAndToString() {
        assertEquals("MMMMMMM\nMMMMMMM\nMMMMMMM\nMMMMMMM\nMMMMMMM", grid.toString());
        assertEquals("M", grid.getCellContents(1, 0));
        grid.put(1, 0, "P");
        grid.put(2, 3, "Q");
        assertEquals("P", grid.getCellContents(1, 0));
        assertEquals("M", grid.getCellContents(1, 1));
        assertEquals("MMMMMMM\nPMMMMMM\nMMMQMMM\nMMMMMMM\nMMMMMMM", grid.toString());
    }

    @Test
    public void testIsEdgeCell() {
        assertEquals("MMMMMMM\nMMMMMMM\nMMMMMMM\nMMMMMMM\nMMMMMMM", grid.toString());
        grid.put(0, 0, "L");
        grid.put(0, 6, "R");
        grid.put(4, 0, "B");
        grid.put(4, 6, "G");
        assertEquals("LMMMMMR\nMMMMMMM\nMMMMMMM\nMMMMMMM\nBMMMMMG", grid.toString());
        Cell topLeft = grid.getCell(0, 0);
        Cell topRight = grid.getCell(0, 6);
        Cell bottomLeft = grid.getCell(4, 0);
        Cell bottomRight = grid.getCell(4, 6);
        Cell topMiddle = grid.getCell(0, 3);
        Cell bottomMiddle = grid.getCell(4, 3);
        Cell midLeft = grid.getCell(2, 0);
        Cell midRight = grid.getCell(2, 6);
        Cell center = grid.getCell(3, 3);
        assertTrue(grid.isEdgeCell(topLeft));
        assertTrue(grid.isEdgeCell(topRight));
        assertTrue(grid.isEdgeCell(bottomLeft));
        assertTrue(grid.isEdgeCell(bottomRight));
        assertTrue(grid.isEdgeCell(topMiddle));
        assertTrue(grid.isEdgeCell(bottomMiddle));
        assertTrue(grid.isEdgeCell(midLeft));
        assertTrue(grid.isEdgeCell(midRight));
        assertFalse(grid.isEdgeCell(center));
    }

}

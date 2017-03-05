package sondow.meadow;

import java.util.ArrayList;
import java.util.List;

/**
 * Data structure for holding a grid of strings, mostly for emoji and whitespace.
 *
 * @author @JoeSondow
 */
public class Grid {

    private String[][] table;
    private String init;

    public String getInit() {
        return init;
    }

    /**
     * Creates a grid of cells based on the specified dimensions, with a starting value in every cell.
     *
     * @param rows the number of rows the grid should have
     * @param cols the number of columns the grid should have
     * @param init the initial value to put in every cell
     */
    Grid(int rows, int cols, String init) {
        this.init = init;
        assert rows > 0;
        assert cols > 0;
        table = new String[rows][cols];
        for (int r = 0; r < table.length; r++) {
            String[] row = table[r];
            for (int c = 0; c < row.length; c++) {
                table[r][c] = init;
            }
        }
    }

    public List<Cell> getOrthogonalNeighborsOf(Cell cell) {
        int row = cell.getRow();
        int col = cell.getCol();
        assert row >= 0;
        assert col >= 0;
        assert row < table.length;
        assert col < table[0].length;

        List<Cell> neighbors = new ArrayList<Cell>();

        // Top
        if (row > 0) {
            int topNeighborRow = row - 1;
            String contents = this.getCellContents(topNeighborRow, col);
            neighbors.add(new Cell(topNeighborRow, col, contents));
        }
        // Bottom
        if (row < table.length - 1) {
            int bottomNeighborRow = row + 1;
            String contents = this.getCellContents(bottomNeighborRow, col);
            neighbors.add(new Cell(bottomNeighborRow, col, contents));
        }
        // Left
        if (col > 0) {
            int leftNeighborColumn = col - 1;
            String contents = this.getCellContents(row, leftNeighborColumn);
            neighbors.add(new Cell(row, leftNeighborColumn, contents));
        }
        // Right
        if (col < table[0].length - 1) {
            int rightNeighborColumn = col + 1;
            String contents = this.getCellContents(row, rightNeighborColumn);
            neighbors.add(new Cell(row, rightNeighborColumn, contents));
        }

        return neighbors;
    }

    public List<Cell> getAllNeighborsOf(Cell cell) {
        List<Cell> neighbors = getOrthogonalNeighborsOf(cell);
        int row = cell.getRow();
        int col = cell.getCol();

        // Top left
        if (row > 0 && col > 0) {
            int topLeftNeighborRow = row - 1;
            int topLeftNeighborCol = col - 1;
            String contents = this.getCellContents(topLeftNeighborRow, topLeftNeighborCol);
            neighbors.add(new Cell(topLeftNeighborRow, topLeftNeighborCol, contents));
        }
        // Top right
        if (row > 0 && col < table[0].length - 1) {
            int topRightNeighborRow = row - 1;
            int topRightNeighborCol = col + 1;
            String contents = this.getCellContents(topRightNeighborRow, topRightNeighborCol);
            neighbors.add(new Cell(topRightNeighborRow, topRightNeighborCol, contents));
        }
        // Bottom left
        if (row < table.length - 1 && col > 0) {
            int bottomLeftRow = row + 1;
            int bottomLeftCol = col - 1;
            String contents = this.getCellContents(bottomLeftRow, bottomLeftCol);
            neighbors.add(new Cell(bottomLeftRow, bottomLeftCol, contents));
        }
        // Bottom right
        if (row < table.length - 1 && col < table[0].length - 1) {
            int bottomRightRow = row + 1;
            int bottomRightCol = col + 1;
            String contents = this.getCellContents(bottomRightRow, bottomRightCol);
            neighbors.add(new Cell(bottomRightRow, bottomRightCol, contents));
        }
        return neighbors;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int r = 0; r < table.length; r++) {
            String[] row = table[r];
            for (int c = 0; c < row.length; c++) {
                builder.append(row[c]);
            }
            if (r < table.length - 1) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    /**
     * Puts a string in the grid at specific coordinates.
     *
     * @param row the zero-indexed row number
     * @param col the zero-indexed column number
     * @param value the string to put in the grid
     */
    public void put(Cell cell) {

        table[cell.getRow()][cell.getCol()] = cell.getContents();
    }

    /**
     * Puts a string in the grid at specific coordinates.
     *
     * @param row the zero-indexed row number
     * @param col the zero-indexed column number
     * @param value the string to put in the grid
     */
    public void put(int row, int col, String value) {

        table[row][col] = value;
    }

    /**
     * Gets a value from the grid at specific coordinates.
     *
     * @param row the zero-indexed row number
     * @param col the zero-indexed column number
     * @return the value stored at the specific grid cell
     */
    public String getCellContents(int row, int col) {
        return table[row][col];
    }

}

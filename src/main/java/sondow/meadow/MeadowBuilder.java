package sondow.meadow;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MeadowBuilder {

    /**
     * Custom randomizer wrapper class allows for deterministic unit tests.
     */
    Randomizer randomizer;

    public static final int ROW_COUNT = 7;
    public static final int COL_COUNT = 9;

    MeadowBuilder(Random random) {
        randomizer = new Randomizer(random);
    }

    public static void main(String[] args) {
        for (long i = 0L; i < 500L; i++) {
            MeadowBuilder b = new MeadowBuilder(new Random());
            String meadow = b.build();
            if (b.isPath) {
                System.out.println(meadow + "\n\n" + i + "\n\n\n\n");
            }
        }
    }

    public boolean isPath = false;

    public String build() {
        int algorithmPick = randomizer.nextInt(7);
        if (algorithmPick == 1) {
            isPath = true;
            return pathThroughTheField();
        }
        return randomGrassMaybeFlowersMaybeOneAnimal();
    }

    private Grid initializeGrid(String base) {
        Grid grid = new Grid(ROW_COUNT, COL_COUNT, base);
        return grid;
    }

    private List<Cell> generatePath(Grid grid) {

        List<Cell> path = new ArrayList<Cell>();

        // Start on the right, because emoji animals face left. Don't start on the top or
        // bottom row.
        int startCellRowIndex = randomizer.nextInt(ROW_COUNT - 2) + 1;
        int startCellColIndex = COL_COUNT - 1;

        // First cell is path start. Animal will go there after path is fully calculated.
        Cell startCell = new Cell(startCellRowIndex, startCellColIndex, "");
        path.add(startCell);

        // Next cell: Identify cells on four sides of cursor. These are candidates for next
        // cell on the path.
        Cell cursor = startCell;

        // For each candidate: If candidate has three grass cells around it, it's worthy of
        // being on the path.
        boolean lookingForEndOfPath = true;
        int iterations = 0;
        while (lookingForEndOfPath && iterations < 100) {
            iterations++; // just to be safe while I debug this shiz
            List<Cell> candidates = grid.getOrthogonalNeighborsOf(cursor);
            List<Cell> worthyCandidates = new ArrayList<Cell>();
            for (Cell candidateCell : candidates) {
                List<Cell> neighborsOfCandidate = grid.getAllNeighborsOf(candidateCell);
                List<Cell> neighborsOnPath = new ArrayList<Cell>();
                for (Cell neighbor : neighborsOfCandidate) {
                    // If candidate's neighbors are already on path, then candidate is not
                    // path-worthy.
                    if (path.contains(neighbor)) {
                        neighborsOnPath.add(neighbor);
                    }
                }
                if (!grid.isEdgeCell(candidateCell)) {
                    if (neighborsOnPath.size() <= 1) {
                        worthyCandidates.add(candidateCell);
                    } else if (neighborsOnPath.size() == 2) {
                        // If there are two neighbors on the path, then this cell is still
                        // worthy iff those neighbors are adjacent to each other, indicating
                        // that they're both earlier cells on the path.
                        Cell neighborA = neighborsOnPath.get(0);
                        Cell neighborB = neighborsOnPath.get(1);
                        if (grid.getOrthogonalNeighborsOf(neighborA).contains(neighborB)) {
                            worthyCandidates.add(candidateCell);
                        }
                    }
                }
            }
            if (worthyCandidates.size() > 0) {
                Cell nextPathCell = randomizer.oneOf(worthyCandidates);
                path.add(nextPathCell);
                cursor = nextPathCell;
            } else {
                lookingForEndOfPath = false;
            }
        }
        return path;
    }

    private String pathThroughTheField() {
        String baseFlowerType = randomizer.oneOf(Chars.FLOWER_TYPES);
        Grid grid = initializeGrid(baseFlowerType);

        List<Cell> path = new ArrayList<Cell>();
        while (path.size() < 17) {
            path = generatePath(grid);
        }
        List<String> animals = new ArrayList<String>(Chars.ANIMALS_TO_TREATS.keySet());
        String animal = randomizer.oneOf(animals);
        String pathType = randomizer.oneOf(Chars.PATH_TYPES);
        for (int i = 0; i < path.size(); i++) {
            Cell cell = path.get(i);
            if (i == path.size() - 1) {
                String treat = Chars.ANIMALS_TO_TREATS.get(animal);
                cell.setContents(treat);
            } else if (i == 0) {
                cell.setContents(animal);
            } else {
                cell.setContents(pathType);
            }
            grid.put(cell);
        }

        List<String> flowerTypes = chooseSomeRandomFlowerTypes();

        // Find all the cells that are not on or adjacent to the path.
        List<Cell> farFromPath = new ArrayList<Cell>();
        for (int r = 0; r < ROW_COUNT; r++) {
            for (int c = 0; c < COL_COUNT; c++) {
                boolean cellIsOnOrNearPath = false;
                Cell cell = new Cell(r, c, grid.getCellContents(r, c));
                if (path.contains(cell)) {
                    cellIsOnOrNearPath = true;
                } else {
                    List<Cell> neighbors = grid.getAllNeighborsOf(cell);
                    for (Cell n : neighbors) {
                        if (path.contains(n)) {
                            cellIsOnOrNearPath = true;
                            break;
                        }
                    }
                }
                if (!cellIsOnOrNearPath) {
                    farFromPath.add(cell);
                }
            }
        }

        for (String flowerType : flowerTypes) {
            int flowerCount = randomizer.nextInt(farFromPath.size() + 1);
            for (int i = 0; i < flowerCount; i++) {
                Cell cell = randomizer.oneOf(farFromPath);
                cell.setContents(flowerType);
                grid.put(cell);
            }
        }

        return grid.toString();
    }

    private String randomGrassMaybeFlowersMaybeOneAnimal() {
        String baseGrassType = randomizer.oneOf(Chars.GRASS_TYPE);
        Grid grid = initializeGrid(baseGrassType);
        List<String> otherGrassTypes = new ArrayList<String>();
        int otherGrassTypeCount = randomizer.nextInt(Chars.GRASS_TYPE.size() - 1);
        while (otherGrassTypes.size() < otherGrassTypeCount) {
            String grassType = randomizer.oneOf(Chars.GRASS_TYPE);
            if (!grassType.equals(grid.getInit()) && !otherGrassTypes.contains(grassType)) {
                otherGrassTypes.add(grassType);
            }
        }
        for (String grass : otherGrassTypes) {
            int otherGrassCount = randomizer.nextInt((int) Math.round(ROW_COUNT * COL_COUNT * 0.8));
            for (int i = 0; i < otherGrassCount; i++) {
                int row = randomizer.nextInt(ROW_COUNT);
                int col = randomizer.nextInt(COL_COUNT);
                grid.put(row, col, grass);
            }
        }

        List<String> flowerTypes = chooseSomeRandomFlowerTypes();
        for (String flowerType : flowerTypes) {
            int flowerCount = randomizer.nextInt((int) Math.round(ROW_COUNT * COL_COUNT * 0.3));
            for (int i = 0; i < flowerCount; i++) {
                int row = randomizer.nextInt(ROW_COUNT);
                int col = randomizer.nextInt(COL_COUNT);
                grid.put(row, col, flowerType);
            }
        }

        if (randomizer.nextInt(2) == 0) {
            int animalRow = randomizer.nextInt(ROW_COUNT - 2) + 1;
            int animalCol = randomizer.nextInt(COL_COUNT - 2) + 1;
            String animalType = randomizer.oneOf(Chars.ANIMAL_TYPES);
            grid.put(animalRow, animalCol, animalType);
        }

        return grid.toString();
    }

    private List<String> chooseSomeRandomFlowerTypes() {
        List<String> flowerTypes = new ArrayList<String>();
        int flowerTypeCount = randomizer.nextInt(Chars.FLOWER_TYPES.size());
        while (flowerTypes.size() < flowerTypeCount) {
            String flowerType = randomizer.oneOf(Chars.FLOWER_TYPES);
            if (!flowerTypes.contains(flowerType)) {
                flowerTypes.add(flowerType);
            }
        }
        return flowerTypes;
    }

}

package sondow.meadow;

import java.util.ArrayList;
import java.util.List;

public class MeadowBuilder {

    /**
     * Custom randomizer wrapper class allows for deterministic unit tests.
     */
    Randomizer random;

    public static final int ROW_COUNT = 7;
    public static final int COL_COUNT = 9;

    MeadowBuilder() {
        random = new Randomizer();
    }

    public static void main(String[] args) {
        MeadowBuilder b = new MeadowBuilder();
        String meadow = b.build();
        System.out.println(meadow);
    }

    public String build() {
        int algorithmPick = random.nextInt(3);
        if (algorithmPick == 1) {
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

        // Start on the right, because emoji animals face left. Don't start on the top or bottom row.
        int startCellRowIndex = random.nextInt(ROW_COUNT - 2) + 1;
        int startCellColIndex = COL_COUNT - 1;

        // First cell is path start. Animal will go there after path is fully calculated.
        Cell startCell = new Cell(startCellRowIndex, startCellColIndex, "");
        path.add(startCell);

        // Next cell: Identify cells on four sides of cursor. These are candidates for next cell on the path.
        Cell cursor = startCell;

        // For each candidate: If candidate has three grass cells around it, it's worthy of being on the path.
        boolean lookingForEndOfPath = true;
        int iterations = 0;
        while (lookingForEndOfPath && iterations < 100) {
            iterations++; // just to be safe while I debug this shiz
            List<Cell> candidates = grid.getOrthogonalNeighborsOf(cursor);
            List<Cell> worthyCandidates = new ArrayList<Cell>();
            for (Cell candidateCell : candidates) {
                int neighborsOffPath = 0;
                List<Cell> neighborsOfCandidate = grid.getOrthogonalNeighborsOf(candidateCell);
                if (neighborsOfCandidate.size() >= 3) {
                    for (Cell neighbor : neighborsOfCandidate) {
                        // If candidate's neighbors are already on path, then candidate is not path-worthy.
                        if (!path.contains(neighbor)) {
                            neighborsOffPath++;
                        }
                    }
                }
                if (neighborsOffPath >= 3) {
                    worthyCandidates.add(candidateCell);
                }
            }
            if (worthyCandidates.size() > 0) {
                Cell nextPathCell = random.oneOf(worthyCandidates);
                path.add(nextPathCell);
                cursor = nextPathCell;
            } else {
                lookingForEndOfPath = false;
            }
        }
        return path;
    }

    private String pathThroughTheField() {
        String baseFlowerType = random.oneOf(Chars.FLOWER_TYPES);
        Grid grid = initializeGrid(baseFlowerType);

        List<Cell> path = new ArrayList<Cell>();
        while (path.size() < 17) {
            path = generatePath(grid);
        }
        List<String> animals = new ArrayList<String>(Chars.ANIMALS_TO_TREATS.keySet());
        String animal = random.oneOf(animals);
        String pathType = random.oneOf(Chars.PATH_TYPES);
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
        for (String flowerType : flowerTypes) {
            int flowerCount = random.nextInt((int) Math.round(ROW_COUNT * COL_COUNT * 0.3));
            for (int i = 0; i < flowerCount; i++) {
                int row = random.nextInt(ROW_COUNT);
                int col = random.nextInt(COL_COUNT);
                Cell cell = new Cell(row, col, grid.getCellContents(row, col));
                if (!path.contains(cell)) {
                    // Are any neighbors of the cell on the path? Keep weird flowers away from the path.
                    List<Cell> neighbors = grid.getAllNeighborsOf(cell);
                    boolean neighborIsOnPath = false;
                    for (Cell neighbor : neighbors) {
                        if (path.contains(neighbor)) {
                            neighborIsOnPath = true;
                        }
                    }
                    if (neighborIsOnPath == false) {
                        grid.put(row, col, flowerType);
                    }
                }
            }
        }

        return grid.toString();
    }

    private String randomGrassMaybeFlowersMaybeOneAnimal() {
        String baseGrassType = random.oneOf(Chars.GRASS_TYPE);
        Grid grid = initializeGrid(baseGrassType);
        List<String> otherGrassTypes = new ArrayList<String>();
        int otherGrassTypeCount = random.nextInt(Chars.GRASS_TYPE.size() - 1);
        while (otherGrassTypes.size() < otherGrassTypeCount) {
            String otherGrassType = random.oneOf(Chars.GRASS_TYPE);
            if (!otherGrassType.equals(grid.getInit()) && !otherGrassTypes.contains(otherGrassType)) {
                otherGrassTypes.add(otherGrassType);
            }
        }
        for (String grassType : otherGrassTypes) {
            int otherGrassCount = random.nextInt((int) Math.round(ROW_COUNT * COL_COUNT * 0.8));
            for (int i = 0; i < otherGrassCount; i++) {
                int row = random.nextInt(ROW_COUNT);
                int col = random.nextInt(COL_COUNT);
                grid.put(row, col, grassType);
            }
        }

        List<String> flowerTypes = chooseSomeRandomFlowerTypes();
        for (String flowerType : flowerTypes) {
            int flowerCount = random.nextInt((int) Math.round(ROW_COUNT * COL_COUNT * 0.3));
            for (int i = 0; i < flowerCount; i++) {
                int row = random.nextInt(ROW_COUNT);
                int col = random.nextInt(COL_COUNT);
                grid.put(row, col, flowerType);
            }
        }

        if (random.nextInt(2) == 0) {
            int animalRow = random.nextInt(ROW_COUNT - 2) + 1;
            int animalCol = random.nextInt(COL_COUNT - 2) + 1;
            String animalType = random.oneOf(Chars.ANIMAL_TYPES);
            grid.put(animalRow, animalCol, animalType);
        }

        return grid.toString();
    }

    private List<String> chooseSomeRandomFlowerTypes() {
        List<String> flowerTypes = new ArrayList<String>();
        int flowerTypeCount = random.nextInt(Chars.FLOWER_TYPES.size());
        while (flowerTypes.size() < flowerTypeCount) {
            String flowerType = random.oneOf(Chars.FLOWER_TYPES);
            if (!flowerTypes.contains(flowerType)) {
                flowerTypes.add(flowerType);
            }
        }
        return flowerTypes;
    }

}

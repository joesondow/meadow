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
        Grid grid = initializeGrid();
        int algorithmPick = random.nextInt(10);
        if (algorithmPick == 3) {
            return pathThroughTheField(grid);
        }
        return randomGrassMaybeFlowersMaybeOneAnimal(grid);
    }

    private Grid initializeGrid() {
        String baseGrassType = random.oneOf(Chars.GRASS_TYPE);
        Grid grid = new Grid(ROW_COUNT, COL_COUNT, baseGrassType);
        return grid;
    }

    private String pathThroughTheField(Grid grid) {
        return randomGrassMaybeFlowersMaybeOneAnimal(grid);
    }

    private String randomGrassMaybeFlowersMaybeOneAnimal(Grid grid) {
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

        List<String> flowerTypes = new ArrayList<String>();
        int flowerTypeCount = random.nextInt(Chars.FLOWER_TYPES.size());
        while (flowerTypes.size() < flowerTypeCount) {
            String flowerType = random.oneOf(Chars.FLOWER_TYPES);
            if (!flowerTypes.contains(flowerType)) {
                flowerTypes.add(flowerType);
            }
        }
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

}

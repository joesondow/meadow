package sondow.meadow;

import java.util.ArrayList;
import java.util.List;

public class MeadowBuilder {

    /**
     * Custom randomizer wrapper class allows for deterministic unit tests.
     */
    Randomizer random;

    MeadowBuilder() {
        random = new Randomizer();
    }

    public static void main(String[] args) {
        MeadowBuilder b = new MeadowBuilder();
        b.build();
    }

    public String build() {
        int rowCount = 7;
        int colCount = 9;
        String baseGrassType = random.oneOf(Chars.GRASS_TYPE);
        Grid grid = new Grid(rowCount, colCount, baseGrassType);

        List<String> otherGrassTypes = new ArrayList<String>();
        int otherGrassTypeCount = random.nextInt(Chars.GRASS_TYPE.size() - 1);
        while (otherGrassTypes.size() < otherGrassTypeCount) {
            String otherGrassType = random.oneOf(Chars.GRASS_TYPE);
            if (!otherGrassType.equals(baseGrassType) && !otherGrassTypes.contains(otherGrassType)) {
                otherGrassTypes.add(otherGrassType);
            }
        }
        for (String grassType : otherGrassTypes) {
            int otherGrassCount = random.nextInt((int) Math.round(rowCount * colCount * 0.8));
            for (int i = 0; i < otherGrassCount; i++) {
                int row = random.nextInt(rowCount);
                int col = random.nextInt(colCount);
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
            int flowerCount = random.nextInt((int) Math.round(rowCount * colCount * 0.3));
            for (int i = 0; i < flowerCount; i++) {
                int row = random.nextInt(rowCount);
                int col = random.nextInt(colCount);
                grid.put(row, col, flowerType);
            }
        }

        if (random.nextInt(2) == 0) {
            int animalRow = random.nextInt(rowCount - 2) + 1;
            int animalCol = random.nextInt(colCount - 2) + 1;
            String animalType = random.oneOf(Chars.ANIMAL_TYPES);
            grid.put(animalRow, animalCol, animalType);
        }

        return grid.toString();
    }

}

package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Position;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Mines {
    private final Set<Position> minePositions;
    private final int maxMines;

    public Mines(int maxMines, int maxWorldSize) {
        this.maxMines = maxMines;
        this.minePositions = new HashSet<>();
        generateRandomMines(maxWorldSize);
    }

    private void generateRandomMines(int maxWorldSize) {
        Random random = new Random();
        int attempts = 0;

        while (minePositions.size() < maxMines && attempts < maxWorldSize * maxWorldSize) {
            int x = random.nextInt(maxWorldSize * 2 + 1) - maxWorldSize;
            int y = random.nextInt(maxWorldSize * 2 + 1) - maxWorldSize;
            Position position = new Position(x, y);

            if (!minePositions.contains(position) && !position.equals(AbstractWorld.CENTRE)) {
                minePositions.add(position);
            }

            attempts++;
        }
    }

    public boolean isMineAt(Position position) {
        return minePositions.contains(position);
    }
}

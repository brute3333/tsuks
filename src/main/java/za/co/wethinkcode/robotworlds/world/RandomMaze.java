package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomMaze {
    private List<SquareObstacle> obstList;

    public RandomMaze() {
        this.obstList = new ArrayList<>();
        createObstacles();
    }

    public void createObstacles() {
        Random rand = new Random();
        for (int i = 0; i < rand.nextInt(10); i++) {
            int x = rand.nextInt(201) - 100;
            int y = rand.nextInt(401) - 200;
            this.obstList.add(new SquareObstacle(5,5));
        }
    }

    public List<SquareObstacle> getObstacles() {
        return this.obstList;
    }

    public boolean blocksPath(Position a, Position b) {
        for (SquareObstacle s : getObstacles()) {
            if (s.blocksPath(a, b)) {
                return true;
            }
        }
        return false;
    }
}

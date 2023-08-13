package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Position;

public class SquareObstacle {
    private final int width;
    private final int height;
    private int x;
    private int y;
    private int size;
    private String robotName;
    private String name;

//    public SquareObstacle() {
//        this.size = 0;
//    }
//
//    public SquareObstacle(int size) {
//        this.size = size;
//    }

    public SquareObstacle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int getBottomLeftX() {
        return this.x;
    }

    public int getBottomLeftY() {
        return this.y;
    }

    public boolean blocksPosition(Position position) {
        int topRightX = this.x + this.width - 1;
        int topRightY = this.y + this.height - 1;

        return position.getX() >= this.x && position.getX() <= topRightX &&
                position.getY() >= this.y && position.getY() <= topRightY;
    }

    public boolean blocksPath(Position a, Position b) {
        if (a.getX() == b.getX()) {
            for (int i = Math.min(a.getY(), b.getY()); i <= Math.max(a.getY(), b.getY()); i++) {
                Position position = new Position(b.getX(), i);
                if (blocksPosition(position)) {
                    return true;
                }
            }
        }

        if (a.getY() == b.getY()) {
            for (int i = Math.min(a.getX(), b.getX()); i <= Math.max(a.getX(), b.getX()); i++) {
                Position position = new Position(i, b.getY());
                if (blocksPosition(position)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public boolean intersects(Position position) {
        return false;
    }

    public void setRobot(String robotName) {
        this.robotName = robotName;
    }

    public boolean hasRobot() {
        return robotName != null;
    }
}

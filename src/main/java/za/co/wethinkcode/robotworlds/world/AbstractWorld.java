package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.Robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.HashSet;

public class AbstractWorld {
    private final int width;
    private final int height;
    private final SquareObstacle[][] grid;
    private final Map<String, Position> robotPositions;
    // private final HashSet<Position> obstaclePositions;
    private final HashMap<String, Position> obstaclePositions;



    private final Position TOP_LEFT = new Position(-100, 200);
    private final Position BOTTOM_RIGHT = new Position(100, -200);
    public static final Position CENTRE = new Position(0, 0);
    private Direction currentDirection;
    private Position position;
    private List<Robot> obstList;
    private RandomMaze maze;

    public AbstractWorld(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new SquareObstacle[height][width];
        this.robotPositions = new HashMap<>();
        this.obstaclePositions = new HashMap<>();
        initializeGrid();

        this.maze = new RandomMaze();
        reset();
        this.obstList = new ArrayList<>();
    }

    private void initializeGrid(){
        for (int row = 0; row < height; row++){
            for (int col = 0; col < width; col++){
                grid[row][col] = new SquareObstacle(5,5);
            }
        }
    }

    public Position launchRobot(String robotName){
        if (!hasAvailableSpace()){
            throw new IllegalStateException("No more space in this world");
        }

        Random random = new Random();
        int maxAttempts = width * height;
        int attempts = 0;
        Position position;

        do{
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            position = new Position(x, y);
            attempts++;
        }while (isOccupied(position) || obstaclePositions.containsKey(position) && attempts < maxAttempts);

        if (attempts >= maxAttempts){
            throw new IllegalStateException("No available position to launch the robot.");
        }

        grid [position.getY()][position.getX()].setRobot(robotName);
        robotPositions.put(robotName, position);
        return position;
    }

    private boolean hasAvailableSpace(){
        int totalPositions = width + height;
        return robotPositions.size() + obstaclePositions.size() < totalPositions;
    }
    
    private boolean isOccupied(Position position){
        return grid[position.getY()][position.getX()].hasRobot();
    }

    public void updateRobotPosition(String robotName, Position newPosition){
        robotPositions.put(robotName, newPosition);
    }

    public Position getRobotPosition(String robotName){
        return robotPositions.get(robotName);
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public SquareObstacle getObstacleAt(Position position){
        int row = position.getY() + height / 2;
        int col = position.getX() + width / 2;
        if (isValidPosition(row, col)){
            return grid[row][col];
        }
        return null;
    }

    public void placeObstacle(Position position, int size) {
        int row = position.getY() + height / 2;
        int col = position.getX() + width / 2;
        if (isValidPosition(row, col)) {
            for (int i = row; i < row + size; i++) {
                for (int j = col; j < col + size; j++) {
                    obstaclePositions.put(null, new Position(j, i));
                }
            }
        }
    }

    public void removeObstacle(Position position){
        int row = position.getY() + height / 2;
        int col = position.getX() + width / 2;
        if (isValidPosition(row, col)){
            for (int i = col; i < col + grid[row][col].getSize(); i++){
                for (int j = col; j < col + grid[row][col].getSize(); j++){
                    obstaclePositions.remove(new Position(j, i));

                }
            }
        }
    }

    private boolean isValidPosition(int row, int col){
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    public WorldEnum updatePosition(int nrSteps) {
        int newX = this.position.getX();
        int newY = this.position.getY();

        if (Direction.NORTH.equals(this.currentDirection)) {
            newY = newY + nrSteps;
        } else if (Direction.EAST.equals(currentDirection)) {
            newX = newX + nrSteps;
        } else if (Direction.SOUTH.equals(this.currentDirection)) {
            newY = newY - nrSteps;
        } else if (Direction.WEST.equals(this.currentDirection)) {
            newX = newX - nrSteps;
        }
        Position newPosition = new Position(newX, newY);
        if (newPosition.isIn(TOP_LEFT, BOTTOM_RIGHT)) {
            if (isNewPositionAllowed(newPosition) && !maze.blocksPath(position, newPosition)) {
                this.position = newPosition;
                return WorldEnum.SUCCESS;
            }
            return WorldEnum.FAILED_OBSTRUCTED;
        }
        return WorldEnum.FAILED_OUTSIDE_WORLD;
    }

    public void updateDirection(boolean turnRight) {
        if (turnRight) {
            if (currentDirection == Direction.NORTH) {
                this.currentDirection = Direction.EAST;
            } else if (currentDirection == Direction.EAST) {
                this.currentDirection = Direction.SOUTH;
            } else if (currentDirection == Direction.SOUTH) {
                this.currentDirection = Direction.WEST;
            } else if (currentDirection == Direction.WEST) {
                this.currentDirection = Direction.NORTH;
            }
        } else {
            if (currentDirection == Direction.NORTH) {
                this.currentDirection = Direction.WEST;
            } else if (currentDirection == Direction.WEST) {
                this.currentDirection = Direction.SOUTH;
            } else if (currentDirection == Direction.SOUTH) {
                this.currentDirection = Direction.EAST;
            } else if (currentDirection == Direction.EAST) {
                this.currentDirection = Direction.NORTH;
            }
        }
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setDirection(Direction direction) {
        this.currentDirection = direction;
    }

    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    public boolean isNewPositionAllowed(Position position) {
        for (SquareObstacle obstacle : getObstacles()) {
            if (obstacle.intersects(position)) {
                return false;
            }
        }
        return position.isIn(TOP_LEFT, BOTTOM_RIGHT);
    }

    public void reset() {
        this.position = CENTRE;
        this.currentDirection = Direction.NORTH;
    }

    public void showObstacles() {
        for (SquareObstacle obstacle : getObstacles()) {
            System.out.println("- At position " + obstacle.getBottomLeftX() + "," + obstacle.getBottomLeftY() + " (to " + (obstacle.getBottomLeftX() + 5) + "," + (obstacle.getBottomLeftY() + 5) + ")");
        }
    }

    public List<SquareObstacle> getObstacles() {
        return this.maze.getObstacles();
    }

    public boolean placeRobot(Robot target) {
        Position robotPosition = target.getPosition();
        if (isNewPositionAllowed(robotPosition) && !maze.blocksPath(position, robotPosition)) {
            obstList.add(target);
            return true; // Robot successfully placed
        }
        return false; // Failed to place the robot
    }

    public boolean isRobotNameTaken(String kind) {
        for (Robot robot : obstList) {
            if (robot.getName().equalsIgnoreCase(kind)) {
                return true; //Name is taken
            }
        }
        return false; // Name is not taken
    }

    public boolean getAvailableSpace() {
        int totalPositions = (TOP_LEFT.getX() - BOTTOM_RIGHT.getX() + 1) * (TOP_LEFT.getY() - BOTTOM_RIGHT.getY() + 1);
        int occupiedPositions = getObstacles().size() + obstList.size();
        return occupiedPositions < totalPositions;
    }
}

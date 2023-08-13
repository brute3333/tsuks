package za.co.wethinkcode.robotworlds;

import org.json.JSONException;
import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.world.AbstractWorld;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.IWorld.UpdateResponse;
import za.co.wethinkcode.robotworlds.Position;
import java.util.HashMap;

public class Robot {
    public String name;
    private Position position;
    private int currentShield;
    private int currentShots;
    private Direction currentDirection = Direction.NORTH;
    public AbstractWorld world;
    private String status;

    public Robot(String name, int maximumShieldStrength, int maximumShots) {
        // Constructor for creating a robot with specified name, shield, and shots
        this.name = name;
        this.currentShield = maximumShieldStrength;
        this.currentShots = maximumShots;
        this.position = new Position(0, 0);
        this.world = new AbstractWorld(maximumShots, maximumShots); 
        this.status = "NORMAL";
    }

    public Robot(AbstractWorld world) {
        // Constructor for creating a robot with a specified world
        this.name = null; // Set the name to null or provide a default name
        this.currentShield = 5; 
        this.currentShots = 10; 
        this.position = new Position(0, 0);
        this.world = world;
        this.status = "NORMAL";
    }

    public Robot(String name, Position position) {
        // Constructor for creating a robot with a specified name and position
        this.name = name;
        this.currentShield = 5;
        this.currentShots = 10;
        this.position = position;
        this.world = null; // Set the world to null or provide a default world
        this.status = "NORMAL";
    }

    public void handleCommand(Command command) throws JSONException {
        // This method takes a Command object and executes it on the current robot instance.
        // The Command class contains the logic for handling different commands.
        command.execute(this);
    }

    public AbstractWorld getWorld() {
        return world;
    }

    public void setWorld(AbstractWorld world) {
        this.world = world;
    }

    public String getName() {
        return name;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setDirection(Direction direction) {
        this.currentDirection = direction;
    }

    public Direction getDirection() {
        return currentDirection;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public int getShield() {
        return currentShield;
    }

    public int getShots() {
        return currentShots;
    }

    public void setShield(int shield) {
        this.currentShield = shield;
    }

    public void setShots(int shots) {
        this.currentShots = shots;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<String, Object> getState() {
        // This method returns a HashMap representing the current state of the robot.
        // It includes the position, direction, shields, shots, and status.
        // This is useful for capturing the robot's state and sending it in a response to the client.
        HashMap<String, Object> state = new HashMap<>();
        state.put("position", new int[]{position.getX(), position.getY()});
        state.put("direction", currentDirection.toString());
        state.put("shields", currentShield);
        state.put("shots", currentShots);
        state.put("status", status);
        return state;
    }

    public String toString() {
        // This method returns the JSON representation of the robot's state.
        // It utilizes the getState() method to get the HashMap and converts it to a JSONObject.
        try {
            JSONObject stateJson = new JSONObject(getState());
            return stateJson.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setName(String name2) {
        name = name2;
    }

    public void turn(String direction) {
        // Method to turn the robot to a specified direction ("left" or "right")
        if (direction.equalsIgnoreCase("left")) {
            switch (currentDirection) {
                case NORTH:
                    currentDirection = Direction.WEST;
                    break;
                case WEST:
                    currentDirection = Direction.SOUTH;
                    break;
                case SOUTH:
                    currentDirection = Direction.EAST;
                    break;
                case EAST:
                    currentDirection = Direction.NORTH;
                    break;
            }
        } else if (direction.equalsIgnoreCase("right")) {
            switch (currentDirection) {
                case NORTH:
                    currentDirection = Direction.EAST;
                    break;
                case EAST:
                    currentDirection = Direction.SOUTH;
                    break;
                case SOUTH:
                    currentDirection = Direction.WEST;
                    break;
                case WEST:
                    currentDirection = Direction.NORTH;
                    break;
            }
        } else {
            System.err.println("Invalid turn direction. Please use 'left' or 'right'.");
        }
    }
    
    public void reload() {
        // Method to reload the robot's shots
        int maxShots = 10; // Maximum number of shots the robot can have
        currentShots = maxShots;
        System.out.println("Reloaded. CurreIWorldnt shots: " + currentShots);
    }
    
    public void repair() {
        // Method to repair the robot's shields
        int maxShieldStrength = 5; // Maximum shield strength the robot can have
        currentShield = maxShieldStrength;
        System.out.println("Repaired. Current shield strength: " + currentShield);
    }
    
    public void move(String commandString, int steps) {
        // Method to move the robot in a specified direction for a given number of steps
        // For simplicity, we assume the robot moves one step at a time
        System.out.println(position.getX()+"  "+position.getY());

        int newX = this.position.getX();
        int newY = this.position.getY();

        if (Direction.NORTH.equals(this.currentDirection)) {
            newY = newY + steps;
        }
        if (Direction.SOUTH.equals(this.currentDirection)) {
            newY = newY - steps;
        }
        if (Direction.EAST.equals(this.currentDirection)) {
            newX = newX + steps;
        }
        if (Direction.WEST.equals(this.currentDirection)) {
            newX = newX - steps;
        }

        Position newPosition = new Position(newX, newY);

        this.position = newPosition;


        System.out.println("Moved " + steps + " steps in the " + currentDirection + " direction. New position: " + position);
    }
    
}

package za.co.wethinkcode.robotworlds.Server;

import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.cli.*;

import za.co.wethinkcode.robotworlds.Command;
import za.co.wethinkcode.robotworlds.Commands.CommandHandler;
import za.co.wethinkcode.robotworlds.Json.ResponseHandler;
import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.Robot;
import za.co.wethinkcode.robotworlds.world.AbstractWorld;

import java.io.*;
import java.net.*;
import java.text.ParseException;
// import java.util.Random;

public class SimpleServer implements Runnable {
    public static final int DEFAULT_PORT = 5000;
    public static final int DEFAULT_SIZE = 1;
    public static final String DEFAULT_OBSTACLES = "none";

    private final BufferedReader in;
    private final BufferedWriter out;
    private final String clientMachine;

    private Robot robot;
    private String name;
    // private CommandHandler commandHandler;
    private AbstractWorld world;
    private Position position;
    private Direction direction;
    private ResponseHandler responseHandler;

    public SimpleServer(Socket socket, AbstractWorld world, Robot robot) throws IOException {
        System.out.println("Waiting for client...");
        clientMachine = socket.getInetAddress().getHostName();
        System.out.println("Connection from " + clientMachine);
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.world = world;
        this.robot = robot;
        // this.commandHandler = new CommandHandler();
        this.responseHandler = new ResponseHandler();
    }

    public void run() {
        try {
            String messageFromClient;

            while ((messageFromClient = in.readLine()) != null) {
                System.out.println("Message from client: " + messageFromClient);

                try {
                    JSONObject jsonObject = new JSONObject(messageFromClient);
                    String robotName = jsonObject.getString("robot");
                    String commandString = jsonObject.getString("command");
                    JSONObject arguments = jsonObject.getJSONObject("arguments");

                    // Update the robot's position and direction
                    robot.getWorld().setPosition(position);
                    robot.getWorld().setDirection(direction);

                    String response = handleCommand(commandString, arguments, robotName);
                    writeResponse(response);

                    // Update the robot's name, position, and direction for the next iteration
                    this.name = robotName;
                    position = robot.getWorld().getPosition();
                    direction = robot.getWorld().getCurrentDirection();
                } catch (JSONException e) {
                    System.err.println("Invalid JSON format: " + messageFromClient);
                    continue; // Skip processing the invalid message
                }
            }
        } catch (IOException e) {
            System.out.println("Shutting down single client server");
        } finally {
            closeQuietly();
        }
    }
    


    private String handleCommand(String commandString, JSONObject arguments, String robotName) {
        try {
            switch (commandString) {
                case "forward":
                case "back":
                case "right":
                case "left":
                    // Handle movement commands
                    if (arguments.has("steps")){
                        int distance = arguments.getInt("steps");
                        robot.move(commandString, distance);
                        return generateResponseWithUpdatedState();
                    }else {
                        return responseHandler.createErrorResponse("Invalid command arguments");
                    }
                case "repair":
                    robot.repair();
                    return generateResponseWithUpdatedState();
                case "reload":
                    robot.reload();
                    return generateResponseWithUpdatedState();
                case "turn":
                    String turnDirection = arguments.getString("direction");
                    robot.turn(turnDirection);
                    return generateResponseWithUpdatedState();
                case "dump":
                    return responseHandler.dumpCommand(position.toString());
                case "launch":
                    return handleLaunchCommand(robotName, arguments);
                default:
                    return responseHandler.createErrorResponse("Invalid command");
            }
        } catch (JSONException e) {
            return responseHandler.createErrorResponse("Invalid command arguments");
        }
    }
    



    private String generateResponseWithUpdatedState() throws JSONException {
        // Generate the response JSON with the updated state
        JSONObject stateJson = new JSONObject();
        stateJson.put("position", robot.getPosition().toString());
        stateJson.put("direction", robot.getDirection().toString());
        stateJson.put("shields", robot.getShield());
        stateJson.put("shots", robot.getShots());
        stateJson.put("status", "OK");

        JSONObject responseJson = new JSONObject();
        responseJson.put("result", "OK");
        responseJson.put("state", stateJson);
        return responseJson.toString();
    }

    private void writeResponse(String response) throws IOException {
        out.write(response);
        out.newLine();
        out.flush();
    }    

    private String handleLaunchCommand(String robotName, JSONObject arguments) {
        try {
            String name = arguments.getString("name");
            int shields = arguments.getInt("shields");
            int shots = arguments.getInt("shots");

            // Launch the robot with the specified name, shields, and shots
            robot.setName(name);
            robot.setShield(shields);
            robot.setShots(shots);

            // Generate the response JSON with the updated state
            JSONObject stateJson = new JSONObject();
            stateJson.put("shields", shields);
            stateJson.put("shots", shots);
            stateJson.put("status", "OK");

            JSONObject responseJson = new JSONObject();
            responseJson.put("result", "OK");
            responseJson.put("state", stateJson);

            return responseJson.toString();
        } catch (JSONException e) {
            return responseHandler.createErrorResponse("Invalid launch command arguments");
        }
    }

    private void closeQuietly() {
        try {
            in.close();
            out.close();
        } catch (IOException ignored) {
        }
    }

    public static void main(String[] args) throws IOException, org.apache.commons.cli.ParseException, ParseException{
        try {
            Options options = new Options();
            options.addOption(Option.builder("p").longOpt("port").desc("Server Port").hasArg().type(Integer.class).argName("port").build());
            options.addOption(Option.builder("s").longOpt("size").desc("Size of world (one side)").hasArg().type(Integer.class).argName("size").build());
            options.addOption(Option.builder("o").longOpt("obstacles").desc("Obstacles (position: x,y or none)").hasArg().type(String.class).argName("obstacles").build());

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            int port = cmd.hasOption("p") ? Integer.parseInt(cmd.getOptionValue("p")) : DEFAULT_PORT;
            int size = cmd.hasOption("s") ? Integer.parseInt(cmd.getOptionValue("s")) : DEFAULT_SIZE;
            String obstacles = cmd.hasOption("o") ? cmd.getOptionValue("o") : DEFAULT_OBSTACLES;

            AbstractWorld world = new AbstractWorld(size, size);
            Robot robot = new Robot(world);
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Server running & waiting for client connections.");
                System.out.println("Server Port: " + port);
                System.out.println("Size of world: " + size + "x" + size);
                System.out.println("Obstacles: " + obstacles);

                while (true) {
                    Socket socket = serverSocket.accept();
                    new Thread(new SimpleServer(socket, world, robot)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (org.apache.commons.cli.ParseException e) {
            System.err.println("Error parsing command line arguments: " + e.getMessage());
        }
    }
}
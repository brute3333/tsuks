package za.co.wethinkcode.robotworlds.Client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    private boolean hasLaunched;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.username = username;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            closeEverything();
        }
    }

    public void sendMessage() {
        try {
            System.out.println("Enter 'launch' followed by your robot type and name to start the program:");
            Scanner scanner = new Scanner(System.in);
            hasLaunched = false;

            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();

                if (messageToSend.equalsIgnoreCase("quit")) {
                    System.out.println("PROGRAM ENDED!");
                    closeEverything();
                    System.exit(0);
                }

                if (!hasLaunched) {
                    if (messageToSend.startsWith("launch")) {
                        String[] launchCommand = messageToSend.split(" ");
                        if (launchCommand.length != 3) {
                            System.err.println("Invalid launch command. Please provide robot type and name.");
                            continue;
                        }
                        String robotType = launchCommand[1];
                        String robotName = launchCommand[2];

                        String requestJson = createLaunchRequest(robotType, robotName);
                        System.out.println(requestJson);
                        sendRequest(requestJson);
                        hasLaunched = true;
                        System.out.println("Launch successful!");
                    } else {
                        System.err.println("Invalid command. Please enter the launch command first.");
                    }
                } else {
                    JSONObject commandJson = new JSONObject();
                    if (messageToSend.startsWith("forward") || messageToSend.startsWith("back")) {
                        String[] parts = messageToSend.split(" ");
                        if (parts.length == 2) {
                            String command = parts[0];
                            int steps = Integer.parseInt(parts[1]);
    
                            JSONObject argumentsJson = new JSONObject();
                            argumentsJson.put("steps", steps);
    
                            commandJson.put("robot", "hal");
                            commandJson.put("command", command);
                            commandJson.put("arguments", argumentsJson);
    
                            sendRequest(commandJson.toString());
                        }else {
                            System.err.println("Invalid command format. Please use '<command> <steps>'.");
                            continue;
                        }
                    } else if (messageToSend.startsWith("left") || messageToSend.startsWith("right")) {
                        String command = messageToSend;

                        JSONObject argumentJson = new JSONObject();

                        commandJson.put("robot", "hal");
                        commandJson.put("command", command);
                        commandJson.put("arguments", argumentJson);

                        sendRequest(commandJson.toString());
                    } else if (messageToSend.startsWith("repair") || messageToSend.startsWith("reload") || messageToSend.startsWith("dump")) {
                        String command = messageToSend.split(" ")[10];
                        commandJson.put("command", command);
                    } else if (messageToSend.startsWith("turn")){
                        String[] parts = messageToSend.split(" ");
                        if (parts.length == 2) {
                            String command = parts[0];
                            String direction = parts[1];

                            commandJson.put("command", command);
                            
                            JSONObject argumentsJson = new JSONObject();
                            argumentsJson.put("direction", direction);
                            
                            commandJson.put("command", argumentsJson);
                            sendRequest(commandJson.toString());
                        } else {
                            System.err.println("Invalid 'Turn' command format. Please use 'turn <direction>'.");
                            continue;
                        }
                    
                        } else {
                            System.err.println("Invaild command.");
                            continue;
                    }
                    // JSONObject commandJson = new JSONObject();
                    // commandJson.put("command", messageToSend);
                    // sendRequest(commandJson.toString());
                }
            }
        } catch (IOException | JSONException e) {
            closeEverything();
        }
    }

    private String createLaunchRequest(String robotType, String robotName) throws JSONException {

        JSONObject requestJson = new JSONObject();
        requestJson.put("robot", robotType);
        requestJson.put("command", "launch");

        JSONObject argumentsJson = new JSONObject();
        argumentsJson.put("name", robotName);
        argumentsJson.put("shields", 5);
        argumentsJson.put("shots", 10);

        requestJson.put("arguments", argumentsJson);
        return requestJson.toString();
    }

    public void sendRequest(String requestJson) throws IOException {
        bufferedWriter.write(requestJson);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    private void handleLaunchResponse(JSONObject responseJson) throws JSONException {
        System.out.println("Server Response: " + responseJson.toString()); // server response.
        String result = responseJson.getString("result");
        if (result.equalsIgnoreCase("OK")) {
            JSONObject stateJson = responseJson.optJSONObject("state");
            if (stateJson != null) {
                int shieldStrength = stateJson.optInt("shields");
                int shots = stateJson.optInt("shots");
                String status = stateJson.optString("status");
                String position = stateJson.optString("position");

                // System.out.println("Launch successful!");
                System.out.println("shields " + shieldStrength);
                System.out.println("shots " + shots);
                System.out.println("status " + status);

                if (!position.isEmpty()) {
                    try {
                        position = position.replace("[", "").replace("]", "");
                        String[] positionArray = position.split(",");
                        if (positionArray.length == 2) {
                            int x = Integer.parseInt(positionArray[0].trim());
                            int y = Integer.parseInt(positionArray[1].trim());
                            System.out.println("position [" + x + ", " + y + "]");
                        } else {
                            System.out.println("Invalid position format");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid position format");
                    }
                } else {
                    System.out.println("Position not available");
                }
            } else {
                System.out.println("Invalid state format");
            }
        } else {
            String errorMessage = responseJson.optString("message");
            System.out.println("Launch failed. Reason: " + errorMessage);
        }
    }

    private JSONObject readResponse() throws IOException, JSONException {
        String responseStr = bufferedReader.readLine();
        if (responseStr != null && !responseStr.isEmpty()) {
            try {
                return new JSONObject(responseStr);
            } catch (JSONException e) {
                System.err.println("Invalid response format: " + responseStr);
                return null;
            }
        } else {
            return null;
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        String responseStr = bufferedReader.readLine();
                        System.out.println("Response from server: " + responseStr); // Debugging statement
                        if (responseStr != null && !responseStr.isEmpty()) {
                            try {
                                JSONObject responseJson = new JSONObject(responseStr);
                                handleLaunchResponse(responseJson);
                            } catch (JSONException e) {
                                System.err.println("Invalid response format: " + responseStr);
                            }
                        }
                    } catch (IOException e) {
                        closeEverything();
                    }
                }
            }
        }).start();
    }


    public void closeEverything() {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, JSONException {
        Socket socket = new Socket("localhost", 5000);
        Client client = new Client(socket, "");
        client.listenForMessage();
        client.sendMessage();
    }

    public void connect(String defaultIp, int defaultPort) {
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public JSONObject getResponse() {
        try {
            return new JSONObject(bufferedReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

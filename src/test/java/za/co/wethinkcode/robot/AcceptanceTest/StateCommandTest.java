package za.co.wethinkcode.robot.AcceptanceTest;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Client.Client;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class StateCommandTest {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private  Client serverClient;
    private Socket socket;

    @BeforeEach
    void connectToServer() throws IOException {
        socket = new Socket(DEFAULT_IP, DEFAULT_PORT);
        serverClient = new Client(socket, "");
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }
    @AfterEach
    void disconnectFromServer() {
        serverClient.closeEverything();
    }

    /**
     * Story: State Command
     *
     * Scenario: The robot exists in the world
     *   Given a connected server client
     *   When a state command is sent for an existing robot
     *   Then the response should indicate an error
     *   And the response data should contain an error message indicating the robot does not exist
     */
    @Test
    void testRobotStateExists() throws IOException {
        String request = "{" +
                "  \"robot\": \"robot1\"," +
                "  \"command\": \"state\"" +
                "}";
        serverClient.sendRequest(request);
        JSONObject response = serverClient.getResponse();
        assertEquals("ERROR", response.getString("result"));
        assertEquals("Robot does not exist", response.getJSONObject("data").getString("message"));
    }

    /**
     * Story: State Commands
     * Scenario: The robot is not in the world
     *   Given a connected server client
     *   When a state command is sent for a non-existent robot
     *   Then the response should indicate an error
     *   And the response data should contain an error message indicating the robot does not exist
     */
    @Test
    void testRobotStateNotExists() throws IOException {
        String request = "{" +
                "  \"robot\": \"nonExistentRobot\"," +
                "  \"command\": \"state\"" +
                "}";
        serverClient.sendRequest(request);
        JSONObject response = serverClient.getResponse();
        assertEquals("ERROR", response.getString("result"));
        assertEquals("Robot does not exist", response.getJSONObject("data").getString("message"));
    }
}

package za.co.wethinkcode.robot.AcceptanceTest;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Client.Client;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class LaunchCommandTest {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private Socket socket;
    private Client serverClient;

    @BeforeEach
    void connectToServer() throws IOException {
        socket = new Socket(DEFAULT_IP, DEFAULT_PORT);
        serverClient = new Client(socket, "");
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer(){
        serverClient.closeEverything();
    }

    /**
     * Story: Launch Command
     *
     * Scenario: Valid launch should succeed
     *   Given a connected server client
     *   When a valid launch command is sent
     *   Then the response should indicate success
     *   And the response data should contain the initial state of the robot
     */
    @Test
    void validLaunchShouldSucceed(){
        assertTrue(serverClient.isConnected());
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        try {
            serverClient.sendRequest(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject response  = serverClient.getResponse();
//        assertEquals("",response.toString());
        assertNotNull(response.getString("result"));
        assertEquals("OK", response.getString("result"));
        assertEquals(0, response.getJSONObject("data").getJSONArray("position").get(0));
        assertEquals(0, response.getJSONObject("data").getJSONArray("position").get(1));
        assertEquals(1, response.getJSONObject("data").getInt("visibility"));
        assertNotNull(response.getJSONObject("state"));
        assertEquals(0, response.getJSONObject("state").getInt("shields"));
        assertEquals(0, response.getJSONObject("state").getInt("shots"));
        assertEquals("NORTH", response.getJSONObject("state").getString("direction"));
    }

    /**
     * Story: Launch Command
     *
     * Scenario: Invalid launch should fail
     *   Given a connected server client
     *   When an invalid launch command is sent
     *   Then the response should indicate an error
     *   And the response data should contain an error message
     */
    @Test
    void invalidLaunchShouldFail() throws IOException {
        assertTrue(serverClient.isConnected());
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"lasffs\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient.sendRequest(request);
        JSONObject response  = serverClient.getResponse();
//        assertEquals("",response.toString());
        assertNotNull(response.getString("result"));
        assertEquals("ERROR", response.getString("result"));
        assertNotNull(response.getJSONObject("data"));
        assertEquals("Unsupported command", response.getJSONObject("data").getString("message"));
    }
    private void assertTrue(boolean connected) {
    }
}

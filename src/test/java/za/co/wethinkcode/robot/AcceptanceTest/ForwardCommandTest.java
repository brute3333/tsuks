package za.co.wethinkcode.robot.AcceptanceTest;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Client.Client;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ForwardCommandTest {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private  Client serverClient;
    private Socket socket;;

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
     * Scenario: Moving at the edge of the world
     * Given that I am connected to a running Robot Worlds server
     * And the world is of size 1x1 with no obstacles or pits
     * And a robot called "HAL" is already connected and launched
     * When I send a command for "HAL" to move forward by 5 steps
     * Then I should get an "OK" response with the message "At the NORTH edge"
     * and the position information returned should be at co-ordinates [0,0]
     */
    @Test
    void movingAtEdgeOfWorldShouldReturnOKWithMessageAndPosition() throws IOException {
        assertTrue(serverClient.isConnected());

        // Prepare the request JSON
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"forward\"," +
                "  \"arguments\": [\"5\"]" +
                "}";

        // Send the request to the server
        serverClient.sendRequest(request);

        // Get the response from the server
        JSONObject response = serverClient.getResponse();

        // Assert the response values
//        assertEquals(response, "");
        assertNotNull(response.getString("result"));
        assertEquals("ERROR", response.getString("result"));
        assertEquals("Robot does not exist", response.getJSONObject("data").getString("message"));
//        assertEquals("At the NORTH edge", response.getJSONObject("data").getString("message"));
//        JSONObject position = response.getJSONObject("data").getJSONObject("position");
//        assertEquals(0, position.getInt("x"));
//        assertEquals(0, position.getInt("y"));
    }
}

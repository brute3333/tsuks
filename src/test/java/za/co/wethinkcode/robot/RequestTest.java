package za.co.wethinkcode.robot;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Json.RequestHandler;
import static org.junit.jupiter.api.Assertions.*;

public class RequestTest {
    @Test
    void testRequestType() throws JSONException {
        RequestHandler request = new RequestHandler();
        assertNotNull(request.handleRequest());
    }
}

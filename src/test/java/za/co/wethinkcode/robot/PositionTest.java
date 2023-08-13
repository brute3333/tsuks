package za.co.wethinkcode.robot;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Position;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    @Test
    public void getCoordinates() {
        Position position = new Position(20, -12);
        assertEquals(20, position.getX());
        assertEquals(-12, position.getY());
    }

    @Test
    public void insideRectangularRegion() {
        Position topLeft = new Position(-20, 20);
        Position bottomRight = new Position(20,-20);
        assertTrue((new Position(10,10)).isIn(topLeft, bottomRight), "should be inside");
        assertFalse((new Position(10,30)).isIn(topLeft, bottomRight), "should be beyond top boundary");
        assertFalse((new Position(10,-30)).isIn(topLeft, bottomRight), "should be beyond bottom boundary");
        assertFalse((new Position(30,10)).isIn(topLeft, bottomRight), "should be beyond right boundary");
        assertFalse((new Position(-30,10)).isIn(topLeft, bottomRight), "should be beyond left boundary");
    }

    @Test
    public void shouldKnowXandY(){
        Position pp = new Position(10,20);
        assertEquals(10,pp.getX());
        assertEquals(20,pp.getY());
    }

    @Test
    public void equality() {
        assertEquals(new Position(-33, 36), new Position(-33, 36));
        assertNotEquals(new Position(-33, 36), new Position(0, 36));
        assertNotEquals(new Position(-33, 36), new Position(-33, 0));
        assertNotEquals(new Position(-33, 36), new Position(0, 0));
    }


}

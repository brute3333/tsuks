package za.co.wethinkcode.robot;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Direction;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    @Test
    void testDirection(){

        assertEquals("NORTH",Direction.NORTH.name());
        assertEquals("SOUTH",Direction.SOUTH.name());
        assertNotEquals("WEST", Direction.EAST.name());
    }

    @Test
    void testDirectionType(){
        Direction direction = Direction.NORTH;
        assertTrue(direction.getClass().isEnum());
    }

}

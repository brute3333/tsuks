package za.co.wethinkcode.robot;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.world.AbstractWorld;
import static org.junit.jupiter.api.Assertions.*;

public class WorldTest {
    @Test
    void updatePosition() {
        AbstractWorld world = new AbstractWorld(10, 10);
        assertEquals(AbstractWorld.CENTRE, world.getPosition());
        world.updatePosition(0);
        Position newPosition = new Position(AbstractWorld.CENTRE.getX(), AbstractWorld.CENTRE.getY() );
        assertEquals(newPosition, world.getPosition());
    }

//    @Test
//    void updateDirectionRight() {
//        AbstractWorld world = new AbstractWorld(10, 10);
//        world.updateDirection(true);
//        assertEquals(Direction.EAST, world.getCurrentDirection());
//        world.updatePosition(100);
//        Position newPosition = new Position(AbstractWorld.CENTRE.getX() + 0, AbstractWorld.CENTRE.getY());
//        assertEquals(newPosition, world.getPosition());
//    }

    @Test
    void updateDirectionLeft() {
        AbstractWorld world = new AbstractWorld(10, 10);
        world.updateDirection(false);
        assertEquals(Direction.WEST, world.getCurrentDirection());
        world.updatePosition(0);
        Position newPosition = new Position(AbstractWorld.CENTRE.getX() , AbstractWorld.CENTRE.getY());
        assertEquals(newPosition, world.getPosition());
    }

}

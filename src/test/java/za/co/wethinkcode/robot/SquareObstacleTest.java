package za.co.wethinkcode.robot;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.world.SquareObstacle;

import static org.junit.jupiter.api.Assertions.*;

public class SquareObstacleTest {

    @Test
    void testObstacleDimensions() {
        SquareObstacle obstacle = new SquareObstacle(5, 5);
        assertEquals(0, obstacle.getBottomLeftX());
        assertEquals(0, obstacle.getBottomLeftY());
        assertEquals(0, obstacle.getSize());
    }

    @Test
    void testSize() {
        SquareObstacle obstacles = new SquareObstacle(1, 1);
//        assertEquals(response , "");
        assertEquals(0, obstacles.getBottomLeftX());
        assertEquals(0, obstacles.getBottomLeftY());
        assertEquals(0, obstacles.getSize());
    }

//    @Test
//    void testBlockPosition() {
//        SquareObstacle obstacles = new SquareObstacle(7, 7);
//        assertTrue(obstacles.blocksPosition(new Position(6, 6)));
//        assertTrue(obstacles.blocksPosition(new Position(6, 7)));
//        assertTrue(obstacles.blocksPosition(new Position(7, 6)));
//        assertTrue(obstacles.blocksPosition(new Position(7, 7)));
//        assertFalse(obstacles.blocksPosition(new Position(5, 5)));
//        assertFalse(obstacles.blocksPosition(new Position(8, 8)));
//    }

//    @Test
//    void testBlockedPath() {
//        SquareObstacle obstacles = new SquareObstacle(1, 1);
//        assertTrue(obstacles.blocksPath(new Position(1, 1), new Position(1, 5)));
//        assertFalse(obstacles.blocksPath(new Position(1, 1), new Position(2, 2)));
//        assertTrue(obstacles.blocksPath(new Position(0, 1), new Position(3, 1)));
//        assertFalse(obstacles.blocksPath(new Position(0, 0), new Position(1, 1)));
//        assertTrue(obstacles.blocksPath(new Position(1, 1), new Position(1, 10)));
//    }
}

package za.co.wethinkcode.robot;
import org.junit.jupiter.api.Test;


import za.co.wethinkcode.robot.QuitCommand;
import za.co.wethinkcode.robotworlds.*;
import za.co.wethinkcode.robotworlds.Commands.BackCommand;
import za.co.wethinkcode.robotworlds.Commands.ForwardCommand;

import static org.junit.jupiter.api.Assertions.*;


class TestCommand {

    @Test
    void getForwardCommand() {

        ForwardCommand test = new ForwardCommand("50");
        assertEquals("forward", test.getName());
        assertEquals("50", test.getArgument());
    }

    @Test
    void getBackCommand(){
        BackCommand test = new BackCommand("10");
        assertEquals("back", test.getName());
        assertEquals("10", test.getArgument());
    }


    @Test
    void getQuitCommand() {
        Command test = new QuitCommand();
        assertEquals("quit", test.getName());
    }


    @Test
    void createInvalidCommand() {
        try {
            Command forward = Command.create("say hello");                                              //<4>
            fail("Should have thrown an exception");                                                    //<5>
        } catch (IllegalArgumentException e) {
            assertEquals("Unsupported command: say hello", e.getMessage());                             //<6>
        }
    }




}


package za.co.wethinkcode.robotworlds.Server;

import za.co.wethinkcode.robotworlds.Robot;
import za.co.wethinkcode.robotworlds.world.AbstractWorld;

import java.net.*;
import java.io.*;

public class ServerHandler {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(SimpleServer.DEFAULT_PORT);
        System.out.println("Server running & waiting for client connections.");

        AbstractWorld world = new AbstractWorld(0, 0);
        Robot robot = new Robot(world);

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Connection: " + socket);

                Runnable serverTask = new SimpleServer(socket, world, robot);
                Thread serverThread = new Thread(serverTask);
                serverThread.start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

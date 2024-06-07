package Server;

import Server.Manager.Server;

import java.io.*;

public class ServerMain {
    public final static int SERVICE_PORT=50001;
    public static void main(String[] args) throws IOException {
        Server server = new Server(SERVICE_PORT);
        server.init();

    }
}

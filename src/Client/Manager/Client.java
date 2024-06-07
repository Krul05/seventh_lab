package Client.Manager;

import lib.Console;

import java.io.*;
import java.net.*;

public class Client {
    int SERVICE_PORT;
    Console console;

    DatagramSocket ds;
    InetAddress serverAddress = InetAddress.getByName("localhost");
    SocketAddress addr;

    public Client(int SERVICE_PORT, Console console) throws IOException {
        this.console = console;
        this.SERVICE_PORT = SERVICE_PORT;
        ds = new DatagramSocket();
        ds.setSoTimeout(10000);
    }


    public <T> void send(T s) throws IOException {
        ByteArrayOutputStream sending = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(sending);
        try {
            oos.writeObject(s);
        } catch (NotSerializableException ex) {
            console.println(ex.getLocalizedMessage());
        }
        byte[] bytes = sending.toByteArray();
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length, serverAddress, SERVICE_PORT);
        ds.send(dp);
    }

    public ObjectInputStream read() throws IOException {
        byte[] receivingDataBuffer = new byte[4096];
        DatagramPacket dp = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
        ds.receive(dp);
        ByteArrayInputStream receivedData = new ByteArrayInputStream(dp.getData(), 0, dp.getLength());
        ObjectInputStream ois = new ObjectInputStream(receivedData);
        return ois;
    }
}

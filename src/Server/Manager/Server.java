package Server.Manager;

import Server.Commands.Save;
import lib.Commands;
import lib.Models.User;
import lib.Request;
import lib.Response;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Selector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.*;

public class Server {
    DatagramChannel dc;
    InetSocketAddress senderAddress;
    private final ForkJoinPool threadPool = ForkJoinPool.commonPool();
    int SERVICE_PORT;

    public Server(int SERVICE_PORT) throws IOException {
        this.SERVICE_PORT = SERVICE_PORT;
    }

    public DatagramChannel getDc() {
        return dc;
    }

    public void send(Response response, InetSocketAddress senderAddress) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(response);
            byte[] responseBytes = baos.toByteArray();
            buffer.clear();
            buffer.put(responseBytes);
            buffer.flip();
            dc.send(buffer, senderAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void init() throws IOException {
        Logger LOGGER = Logger.getLogger("MyLog");
        LOGGER.setUseParentHandlers(false);
        try {
            FileHandler fh = new FileHandler("src/Server/log.config", true);
            LOGGER.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.info("Log message");
        } catch (SecurityException | IOException ex) {
            LOGGER.log(Level.SEVERE, "Произошла ошибка при работе с FileHandler.", ex);
        }
        LOGGER.log(Level.INFO,"Сервер начал работу");
        dc = DatagramChannel.open();
        dc.bind(new InetSocketAddress(SERVICE_PORT));
        LOGGER.log(Level.INFO,"Канал открыт и подключен");
        dc.configureBlocking(false);
        LOGGER.log(Level.INFO,"Канал начал работу в non-blocking режиме");
        DataBase dataBase = new DataBase();
        dataBase.setConnection("jdbc:postgresql://localhost:5432/prog", "postgres", "pass");
        CollectionManager collection = dataBase.getCollection();
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            if (console.ready()) {

            }

                try {
                    ByteBuffer buffer = ByteBuffer.allocate(4096);
                    Thread thread = new Thread(() -> {
                        try {
                            senderAddress =  (InetSocketAddress) dc.receive(buffer);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    thread.start();
                    thread.join();
                    Request receivedMessage = null;
                    User user = null;
                    Commands command = null;
                    if(senderAddress != null){
                        LOGGER.log(Level.INFO,"Сервер получил данные от клиента");
                        buffer.flip();
                        ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
                        ObjectInputStream ois = new ObjectInputStream(bais);
                        receivedMessage = (Request) ois.readObject();
                        LOGGER.log(Level.INFO,"Данные десериализированы");
                        if (receivedMessage.getType().equals("user")) {
                            Response response = new Response(userauth(receivedMessage.getUser(), dataBase));
                            threadPool.submit(() -> {

                                    send(response, senderAddress);

                            }).join();
                            ExecutorService executor = Executors.newFixedThreadPool(2);
                            executor.submit(() -> response);
                        } else {
                            command = receivedMessage.getCommands();
                            user = receivedMessage.getUser();
                        }
                    }
                    if (command!=null) {
                        ExecutorService executorService = Executors.newCachedThreadPool();
                        executorService.submit(new CommandManager(this, collection, dataBase, command, user, threadPool, senderAddress));
                    }


                } catch (ClassNotFoundException e) {
                    LOGGER.log(Level.WARNING,"Произошла ошибка");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
        }

    }
    private String userauth(User user, DataBase dataBase) {
        String response;
        if (dataBase.checkUser(user)) {
            if (DataBase.checkPassword(user)) {
                response = "Вы вошли в систему.";
            } else {
                response = "Неверный пароль.";
            }
        } else {
            if (DataBase.addUser(user)) {
                response = "Пользователя с таким именем не существует, поэтому Вы были зарегистрированы в системе.";
            } else {
                response = "Пользователя с таким именем не существует, регистрация не удалась ввиду неизвестной ошибки.";
            }
        }
        return response;
    }
}

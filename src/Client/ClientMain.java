package Client;

import Client.Manager.*;
import lib.Console;
import lib.Commands;
import lib.Models.User;
import lib.Request;
import lib.Response;

import java.io.*;
import java.net.*;
import java.util.NoSuchElementException;

public class ClientMain {
    public final static int SERVICE_PORT=50001;
    public static void main(String[] args) throws  IOException {
        Console console = new Console();
        Inputs inputs = new Inputs(console);
        Commands s;
        Client client = new Client(SERVICE_PORT, console);
        User user = userauth(client);
        boolean flag = true;
        while (flag) {
            try {
                s = inputs.commandInput();
            } catch (NullPointerException ex) {
                console.println("Программа завершена");
                return;
            }
            try {

                if (s.getName().equals("execute_script")) {
                    ExecuteScript executeScript = new ExecuteScript(client, user);
                    executeScript.action((String) s.getArgument());
                } else {
                    Request request = new Request();
                    request.setUser(user);
                    request.setCommands(s);
                    client.send(request);
                    Response response = (Response) client.read().readObject();
                    String answer = response.getResponse();
                    if (answer.equals("exit")) {
                        flag = false;
                    } else {
                        console.println(answer);
                    }
                }

            } catch (SocketException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SocketTimeoutException e) {
                System.out.println("Сервер не доступен! Попробуйте ещё раз!");
            }
        }

    }
    private static User userauth(Client client) {
        String username;
        String password;
        Inputs inputs = new Inputs(new Console());
        User user = null;
        try {
            while (true) {
                System.out.println("Введите логин:");
                username = inputs.input();
                if (username.equals("exit")) {
                    System.out.println("Завершение работы консольного приложения...");
                    System.exit(0);
                }
                System.out.println("Введите пароль:");
                password = inputs.input();
                if (password.equals("exit")) {
                    System.out.println("Завершение работы консольного приложения...");
                    System.exit(0);
                }
                String hashedPassword = Hasher.hash(password);
                if (hashedPassword.equals("Такого алгоритма хэширования не существует.")) {
                    System.out.println("Алгоритма кодирования не существует");
                    System.exit(0);
                } else {
                    user = new User(username, hashedPassword);
                }
                Request request = new Request();
                request.setUser(user);
                client.send(request);
                Response response = (Response) client.read().readObject();
                if (response.getResponse().equals("Вы вошли в систему.")) {
                    System.out.println(response.getResponse());
                    if (!response.getResponse().equals("Неверный пароль.")) {
                        break;
                    }
                } else {
                    System.out.println(response.getResponse());
                    if (response.getResponse().equals("Пользователя с таким именем не существует, поэтому Вы были зарегистрированы в системе.")) {
                        break;
                    } else {
                        System.out.println("Попробуйте снова или напишите exit для выхода.");
                    }
                }
            }
        } catch (NoSuchElementException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }
}
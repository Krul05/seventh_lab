package Server.Manager;

import Client.Manager.ExecuteScript;
import lib.Commands;
import Server.Commands.*;
import lib.Models.MovieEntr;
import lib.Models.MovieGenre;
import lib.Models.User;
import lib.Response;


import java.io.IOException;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * Класс CommandManager - обрабатывает команды
 */
public class CommandManager  implements Runnable{
    Server server;
    CollectionManager collectionManager;
    private Scanner scanner = null;
    DataBase dataBase;
    Commands commands;
    InetSocketAddress senderAddress;
    User user;
    ForkJoinPool threadPool;
    public <T> CommandManager(Server server, CollectionManager collectionManager, DataBase dataBase, Commands<T> commands, User user, ForkJoinPool threadPool, InetSocketAddress senderAddress) {
        this.server = server;
        this.collectionManager = collectionManager;
        this.dataBase = dataBase;
        this.commands = commands;
        this.user = user;
        this.threadPool = threadPool;
        this.senderAddress = senderAddress;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void run() {
        Response response = null;
        if (commands != null) {
            String name = commands.getName();
            MovieEntr movieEntr = commands.getMovie();
            if (name.equals("add")) {
                Add add = new Add(server, collectionManager, dataBase);
                if (scanner == null) {
                    try {
                        response = new Response(add.action(movieEntr, user));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (name.equals("help")) {
                Help help = new Help(server, dataBase);
                try {
                    response = new Response(help.action());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (name.equals("info")) {
                Info info = new Info(server, collectionManager, dataBase);
                try {
                    response = new Response(info.action());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (name.equals("show")) {
                Show show = new Show(server, collectionManager, dataBase);
                try {
                    response = new Response(show.action());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (name.equals("update")) {
                if (commands.getArgument() != null) {
                    Update update = new Update(server, collectionManager, dataBase);
                    if (scanner == null) {
                        try {
                            response = new Response(update.action((Integer) commands.getArgument(), movieEntr, user));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    response = new Response("Вы забыли ввести id, пожалуйста, повторите ввод");
                }
            } else if (name.equals("remove_by_id")) {
                if (commands.getArgument() != null) {
                    RemoveById removeById = new RemoveById(server, collectionManager, dataBase);
                    try {
                        response = new Response(removeById.action((Integer) commands.getArgument(), user));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    response = new Response("Вы забыли ввести id, пожалуйста, повторите ввод");
                }
            } else if (name.equals("clear")) {
                Clear clear = new Clear(server, collectionManager, dataBase);
                try {
                    response = new Response(clear.action(user));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (name.equals("exit")) {
                Exit exit = new Exit(server, collectionManager, dataBase);
                try {
                    response = new Response(exit.action());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (name.equals("remove_first")) {
                RemoveFirst removeFirst = new RemoveFirst(server, collectionManager, dataBase);
                try {
                    response = new Response(removeFirst.action(user));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (name.equals("head")) {
                Head head = new Head(server, collectionManager, dataBase);
                try {
                    response = new Response(head.action());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (name.equals("add_if_max")) {
                AddIfMax addIfMax = new AddIfMax(server, collectionManager, dataBase);
                if (scanner == null) {
                    try {
                        response = new Response(addIfMax.action(movieEntr, user));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (name.equals("remove_all_by_genre")) {
                if (commands.getArgument() != null) {
                    RemoveAllByGenre removeAllByGenre = new RemoveAllByGenre(server, collectionManager, dataBase);
                    try {
                        response = new Response(removeAllByGenre.action((MovieGenre) commands.getArgument(), user));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    response = new Response("Вы забыли указать жанр");
                }
            } else if (name.equals("filter_greater_than_oscars_count")) {
                if (commands.getArgument() != null) {
                    FilterGreaterThanOscarsCount filterGreaterThanOscarsCount = new FilterGreaterThanOscarsCount(server, collectionManager, dataBase);
                    try {
                        response = new Response(filterGreaterThanOscarsCount.action((Integer) commands.getArgument()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    response = new Response("Вы забыли ввести количество оскаров, пожалуйста, повторите ввод");
                }
            } else if (name.equals("print_field_ascending_operator")) {
                PrintFieldAscendingOperator printFieldAscendingOperator = new PrintFieldAscendingOperator(server, collectionManager, dataBase);
                try {
                    response = new Response(printFieldAscendingOperator.action());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                response = new Response("Такой команды нет, пожалуйста, повторите ввод");
            }
        }
        Response finalResponse = response;

        threadPool.submit(() -> {
                server.send(finalResponse, senderAddress);
        }).join();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> finalResponse);
    }
}

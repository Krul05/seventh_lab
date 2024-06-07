package Client.Manager;

import lib.Commands;
import lib.Models.MovieEntr;
import lib.Models.MovieGenre;
import lib.Models.User;
import lib.Request;
import lib.Response;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Scanner;


public class ExecuteScript {
    Client client;
    User user;
    public ExecuteScript(Client client, User user) {
        this.client = client;
        this.user = user;
    }

    public String action(String file) throws IOException {
        try {
            FileReader fileReader = new FileReader(file);
            Scanner scan = new Scanner(fileReader);
            String[] elements;
            while (scan.hasNextLine()) {
                elements = scan.nextLine().trim().split(" ");
                Commands commands = null;
                if (elements[0].equals("remove_by_id") || elements[0].equals("update") || elements[0].equals("execute_script") || elements[0].equals("remove_all_by_genre") || elements[0].equals("filter_greater_than_oscars_count")) {
                    if (elements.length == 2) {
                        try {
                            if (elements[0].equals("remove_by_id") || elements[0].equals("filter_greater_than_oscars_count")) {
                                commands = new Commands(elements[0], Integer.parseInt(elements[1]));
                            } else if (elements[0].equals("execute_script")) {
                                commands = new Commands(elements[0], elements[1]);
                            } else if (elements[0].equals("update")) {
                                MovieScan movieScan = new MovieScan(scan);
                                MovieEntr movie = movieScan.movieScan();
                                commands = new Commands(elements[0], Integer.parseInt(elements[1]), movie);
                            } else {
                                commands = new Commands(elements[0], MovieGenre.valueOf(elements[1]));
                            }
                        } catch (Exception ex) {
                            commands = null;
                        }
                    } else {
                        commands = null;
                    }
                } else {
                    if (elements[0].equals("add") || elements[0].equals("add_if_max")) {
                        MovieScan movieScan = new MovieScan(scan);
                        MovieEntr movie = movieScan.movieScan();
                        commands = new Commands(elements[0],  movie);
                    } else {
                        commands = new Commands(elements[0]);
                    }
                }
                Request request = new Request();
                request.setUser(user);
                request.setCommands(commands);
                client.send(request);
                Response response = (Response) client.read().readObject();
                String answer = response.getResponse();
                System.out.println(answer);
            }
            return "Команда выполнена!";
        } catch (FileNotFoundException exception) {
            return "Такого файла нет";
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}

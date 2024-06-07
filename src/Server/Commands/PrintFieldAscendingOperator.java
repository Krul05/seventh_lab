package Server.Commands;

import Server.Manager.CollectionManager;
import Server.Manager.DataBase;
import Server.Manager.Server;
import lib.Models.Movie;

import java.io.IOException;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class PrintFieldAscendingOperator extends Command{

    CollectionManager collectionManager;
    public PrintFieldAscendingOperator(Server server, CollectionManager collectionManager, DataBase dataBase) {
        super(server, "print_field_ascending_operator", "вывести значение поля operator всех элементов в порядке возрастания", dataBase);
        this.collectionManager = collectionManager;
    }


    public String action() throws IOException {
        collectionManager.setCollection(dataBase.getCollection().getCollection());
        LinkedList<Movie> collection = collectionManager.getCollection();
        String s;
        try {
            s = collection.stream().map(Movie::getOperator).sorted().map(person -> (person.toString() + "\n")).collect(Collectors.joining());
        } catch (NullPointerException ex) {
            s = "";
        }
        return s;
    }
}

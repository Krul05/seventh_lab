package Server.Commands;

import Server.Manager.CollectionManager;
import Server.Manager.DataBase;
import Server.Manager.Server;
import lib.Models.Movie;

import java.io.IOException;
import java.util.LinkedList;

public class Head extends Command{

    CollectionManager collectionManager;
    public Head(Server server, CollectionManager collectionManager, DataBase dataBase) {
        super(server, "head", "вывести первый элемент коллекции", dataBase);
        this.collectionManager = collectionManager;
    }


    public String action() throws IOException {
        collectionManager.setCollection(dataBase.getCollection().getCollection());
        LinkedList<Movie> collection = collectionManager.getCollection();
        try {
            return collection.get(0).getMovie();
        } catch (Exception ex) {
            return "Коллекция пуста";
        }

    }
}

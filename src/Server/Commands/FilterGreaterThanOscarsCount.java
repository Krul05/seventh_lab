package Server.Commands;

import Server.Manager.CollectionManager;
import Server.Manager.DataBase;
import Server.Manager.Server;
import lib.Models.Movie;

import java.io.IOException;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class FilterGreaterThanOscarsCount extends Command{

    CollectionManager collectionManager;
    public FilterGreaterThanOscarsCount(Server server, CollectionManager collectionManager, DataBase dataBase) {
        super(server, "filter_greater_than_oscars_count", "вывести элементы, значение поля oscarsCount больше заданных", dataBase);
        this.collectionManager = collectionManager;
    }


    public String action(int oscarsCount) throws IOException {
        collectionManager.setCollection(dataBase.getCollection().getCollection());
        LinkedList<Movie> collection = collectionManager.getCollection();
        String s;
        s = collection.stream().filter(movie -> movie.getOscarsCount() > oscarsCount).map(Movie::getMovie).collect(Collectors.joining());
        return s;
    }

}

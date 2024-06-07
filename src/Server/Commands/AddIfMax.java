package Server.Commands;

import Server.Manager.CollectionManager;
import Server.Manager.DataBase;
import Server.Manager.Server;
import lib.Models.Movie;
import lib.Models.MovieEntr;
import lib.Models.User;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class AddIfMax extends Command{
    private final ReentrantLock locker = new ReentrantLock();
    CollectionManager collectionManager;
    public AddIfMax(Server server, CollectionManager collectionManager, DataBase dataBase) {
        super(server, "add_if_max", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции", dataBase);
        this.collectionManager = collectionManager;
    }

    public String action(MovieEntr movieEntr, User user) throws IOException, ClassNotFoundException {
        Movie movie = new Movie(movieEntr.getName(), movieEntr.getCoordinates(), movieEntr.getOscarsCount(), movieEntr.getGenre(), movieEntr.getMpaaRating(), movieEntr.getOperator());
        locker.lock();
        LinkedList<Movie> collection = collectionManager.getCollection();
        int n = collection.size()-1;
        if (movie.compareTo(collection.get(n)) > 0) {
            if (dataBase.addMovie(movie, user)) {
                collectionManager.setCollection(dataBase.getCollection().getCollection());
            }
        }
        locker.unlock();
        return "Команда выполнена!";
    }
}

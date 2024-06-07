package Server.Commands;

import Server.Manager.CollectionManager;
import Server.Manager.DataBase;
import Server.Manager.Server;
import lib.Models.Movie;
import lib.Models.MovieEntr;
import lib.Models.User;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.locks.ReentrantLock;

public class Add extends Command{
    CollectionManager collectionManager;
    private final ReentrantLock locker = new ReentrantLock();
    public Add(Server server, CollectionManager collectionManager, DataBase dataBase) {
        super(server, "add", "добавить новый элемент в коллекцию", dataBase);
        this.collectionManager = collectionManager;
    }


    public String action(MovieEntr movieEntr, User user) throws IOException, ClassNotFoundException {
        Movie movie = new Movie(movieEntr.getName(), movieEntr.getCoordinates(), movieEntr.getOscarsCount(), movieEntr.getGenre(), movieEntr.getMpaaRating(), movieEntr.getOperator());
        locker.lock();
        if (dataBase.addMovie(movie, user)) {
            collectionManager.setCollection(dataBase.getCollection().getCollection());
        }
        locker.unlock();
        return "Команда выполнена!";
    }


}

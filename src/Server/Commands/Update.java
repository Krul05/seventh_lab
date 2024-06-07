package Server.Commands;

import Server.Manager.CollectionManager;
import Server.Manager.DataBase;
import Server.Manager.Server;
import lib.Models.Movie;
import lib.Models.MovieEntr;
import lib.Models.User;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class Update extends Command{
    private final ReentrantLock locker = new ReentrantLock();
    CollectionManager collectionManager;
    public Update(Server server, CollectionManager collectionManager, DataBase dataBase) {
        super(server, "update", "обновить значение элемента коллекции, id которого равен заданному", dataBase);
        this.collectionManager = collectionManager;

    }
    public Movie movieEntr(MovieEntr movieEntr) throws IOException, ClassNotFoundException {
        Movie movie = new Movie(movieEntr.getName(), movieEntr.getCoordinates(), movieEntr.getOscarsCount(), movieEntr.getGenre(), movieEntr.getMpaaRating(), movieEntr.getOperator());
        collectionManager.getCollection().add(movie);
        return movie;
    }

    public String action(int id, MovieEntr movieEntr, User user) throws IOException, ClassNotFoundException {
        Movie movie = movieEntr(movieEntr);
        locker.lock();
        if (dataBase.update(id, movie, user)) {
            collectionManager.setCollection(dataBase.getCollection().getCollection());
        }
        locker.unlock();
        return "Команда выполнена!";
    }

}

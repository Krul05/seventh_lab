package Server.Commands;

import Server.Manager.CollectionManager;
import Server.Manager.DataBase;
import Server.Manager.Server;
import lib.Models.Movie;
import lib.Models.MovieGenre;
import lib.Models.User;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class RemoveAllByGenre extends Command{
    private final ReentrantLock locker = new ReentrantLock();
    CollectionManager collectionManager;
    public RemoveAllByGenre(Server server, CollectionManager collectionManager, DataBase dataBase) {
        super(server, "remove_all_by_genre", "удалить из коллекции все элементы, значение поля genre которых эквивалентно заданному", dataBase);
        this.collectionManager = collectionManager;
    }


    public String action(MovieGenre genre, User user) throws IOException {
        locker.lock();
        if (dataBase.removeAllByGenre(genre, user)) {
            collectionManager.setCollection(dataBase.getCollection().getCollection());
        }
        locker.unlock();
        return "Команда выполнена!";
    }
}

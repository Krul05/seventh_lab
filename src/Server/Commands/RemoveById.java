package Server.Commands;

import Server.Manager.CollectionManager;
import Server.Manager.DataBase;
import Server.Manager.Server;
import lib.Models.Movie;
import lib.Models.User;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class RemoveById extends Command{
    private final ReentrantLock locker = new ReentrantLock();
    CollectionManager collectionManager;
    public RemoveById(Server server, CollectionManager collectionManager, DataBase dataBase) {
        super(server, "remove_by_id", "удалить элемент коллекции по его id", dataBase);
        this.collectionManager = collectionManager;
    }


    public String action(int id, User user) throws IOException {
        locker.lock();
        if (dataBase.removeById(id, user)) {
            collectionManager.setCollection(dataBase.getCollection().getCollection());
        }
        locker.unlock();
        return "Команда выполнена!";
    }
}

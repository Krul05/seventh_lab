package Server.Commands;

import Server.Manager.CollectionManager;
import Server.Manager.DataBase;
import Server.Manager.Server;
import lib.Models.User;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class RemoveFirst extends Command {
    private final ReentrantLock locker = new ReentrantLock();
    CollectionManager collectionManager;
    public RemoveFirst(Server server, CollectionManager collectionManager, DataBase dataBase) {
        super(server, "remove_first", "удалить первый элемент из коллекции", dataBase);
        this.collectionManager = collectionManager;
    }


    public String action(User user) throws IOException {
        locker.lock();
        if(dataBase.removeFirst(user)) {
            collectionManager.setCollection(dataBase.getCollection().getCollection());
        }
        locker.unlock();
        return "Команда выполнена!";

    }
}

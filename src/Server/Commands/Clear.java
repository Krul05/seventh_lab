package Server.Commands;

import Server.Manager.CollectionManager;
import Server.Manager.DataBase;
import Server.Manager.Server;
import lib.Models.User;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class Clear extends Command{
    CollectionManager collectionManager;
    private final ReentrantLock locker = new ReentrantLock();
    public Clear(Server server, CollectionManager collectionManager, DataBase dataBase) {
        super(server, "clear", "очистить коллекцию", dataBase);
        this.collectionManager = collectionManager;
    }


    public String action(User user) throws IOException {
        locker.lock();
        if (dataBase.clearCollection(user)) {
            collectionManager.setCollection(dataBase.getCollection().getCollection());
        }
        locker.unlock();
        return "Команда выполнена!";
    }
}

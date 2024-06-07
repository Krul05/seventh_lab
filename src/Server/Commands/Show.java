package Server.Commands;

import Server.Manager.CollectionManager;
import Server.Manager.DataBase;
import Server.Manager.Server;

import java.io.IOException;

public class Show extends Command{

    CollectionManager collectionManager;
    public Show(Server server, CollectionManager collectionManager, DataBase dataBase) {
        super(server, "show", "вывести все элементы коллекции", dataBase);
        this.collectionManager = collectionManager;
    }


    public String action() throws IOException {
        collectionManager.setCollection(dataBase.getCollection().getCollection());
        return collectionManager.show();
    }
}

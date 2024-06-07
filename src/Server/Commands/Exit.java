package Server.Commands;

import Server.Manager.CollectionManager;
import Server.Manager.DataBase;
import Server.Manager.Server;

import java.io.IOException;

public class Exit extends Command{
    CollectionManager collectionManager;

    public Exit(Server server, CollectionManager collectionManager, DataBase dataBase) {
        super(server, "exit", "завершить программу (без сохранения в файл)", dataBase);
        this.collectionManager = collectionManager;
    }


    public String action() throws IOException {
        return "exit";
    }
}

package Server.Commands;

import Server.Manager.DataBase;
import Server.Manager.Server;

/**
 * Базовый класс команд содержит поля <b>console</b>, <b>name</b>, <b>description</b>
 */
public abstract class Command {
    protected final Server server;
    private final String name;
    private final String description;
    protected final DataBase dataBase;
    /**
     * @param server      - выводит в консоль
     * @param name        - название команды
     * @param description - описание команды
     * @param dataBase
     */
    protected Command(Server server, String name, String description, DataBase dataBase) {
        this.server = server;
        this.name = name;
        this.description = description;
        this.dataBase = dataBase;
    }


    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}

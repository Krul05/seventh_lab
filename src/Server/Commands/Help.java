package Server.Commands;

import Server.Manager.DataBase;
import Server.Manager.Server;

import java.io.IOException;

public class Help extends Command{

    public Help(Server server, DataBase dataBase) {
        super(server, "help", "вывести справку по доступным командам", dataBase);
    }

    public String action() throws IOException {
        String s = "";
        s+=("help: вывести справку по доступным командам \n");
        s+=("info: вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д.\n");
        s+=("show: вывести все элементы коллекции\n");
        s+=("add: добавить новый элемент в коллекцию\n");
        s+=("update: обновить значение элемента коллекции, id которого равен заданному\n");
        s+=("remove_by_id: удалить элемент коллекции по его id\n");
        s+=("clear: очистить коллекцию\n");
        s+=("save: сохранить коллекцию в файл\n");
        s+=("execute_script: считать и исполнить скрипт из файла\n");
        s+=("exit: завершить программу (без сохранения в файл)\n");
        s+=("remove_first: удалить первый элемент из коллекции\n");
        s+=("head: вывести первый элемент коллекции\n");
        s+=("add_if_max: добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n");
        s+=("remove_all_by_genre: удалить из коллекции все элементы, значение поля genre которых эквивалентно заданному\n");
        s+=("filter_greater_than_oscars_count: вывести элементы, значение поля oscarsCount больше заданных\n");
        s+=("print_field_ascending_operator: вывести значение поля operator всех элементов в порядке возрастания");
        return s;
    }
}

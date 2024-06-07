package Server.Manager;

import lib.Models.Movie;

import java.io.File;
import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * Класс CollectionManager - управляет коллекцией
 */
public class CollectionManager {
    public CollectionManager(LinkedList<Movie> collection) {
        creationDate = java.time.ZonedDateTime.now();
        this.collection = collection;
    }
    LinkedList<Movie> collection;
    private java.time.ZonedDateTime creationDate;
    public LinkedList getCollection() {
        return collection;
    }


    public String show() {
        int size = collection.size();
        if (size==0) {
            return "Коллекция пуста!";
        } else {
            String s = "";
            for (int i = 0; i<size; i++) {
                s += collection.get(i).getMovie();
                s += "\n";
            }
            return s;
        }
    }
    public void setCollection(LinkedList<Movie> c) {
        collection = c;
    }
    public String getDiscription() {
        String type = "java.util.LinkedList";
        String date = creationDate.getDayOfMonth() + " " + creationDate.getMonth() + " " + creationDate.getYear() + " " + creationDate.getHour() +":" + creationDate.getMinute() + ":" + creationDate.getSecond();
        String size = String.valueOf(collection.size());
        return "тип - " + type + " \n" + "время инициализации - " + date + "\n" + "количество элементов в коллекции - " + size;
    }
}

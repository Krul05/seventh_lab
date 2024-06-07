package Server.Manager;

import lib.Models.Movie;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class XMLWorker {
    File file;
    public XMLWorker(File file) {
        this.file = file;
    }
    public LinkedList<Movie> read() {
        LinkedList<Movie> deserialized = new LinkedList<Movie>();

        try(FileInputStream fis = new FileInputStream(file);
            XMLDecoder decoder = new XMLDecoder(fis)) {
            final boolean[] e = {false};
            decoder.setExceptionListener(ex -> e[0] = true);
            deserialized = (LinkedList<Movie>) decoder.readObject();
            if(e[0]){
                System.out.print("Файл пуст");
                return new LinkedList<>();
            }
            if (deserialized.stream().filter(movie -> !movie.is_valide()).count() > 0) {
                deserialized.removeIf(movie -> !movie.is_valide());
                System.out.println("В файле были невалидные объекты");
            }
        } catch (Exception e) {
            System.out.println("Что-то пошло не так");
        }
        if (deserialized.size()!=0) {
            System.out.println("Коллекция записана");
        }
        return deserialized;
    }
    public void write(LinkedList<Movie> collection) {
        if (file.canWrite()) {
            try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)))) {
                encoder.writeObject(collection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("В файл нельзя ничего записать");
        }
    }
}

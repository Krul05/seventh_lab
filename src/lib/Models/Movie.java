package lib.Models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * Класс Movie с полями <b>id</b>, <b>name</b>, <b>coordinates</b>, <b>creationDate</b>, <b>oscarsCount</b>, <b>genre</b>, <b>mpaaRating</b>, <b>operator</b>.
 */
public class Movie implements Comparable<Movie> {
    private int id;
    private String name;
    private Coordinates coordinates;
    private Date creationDate;
    private Integer oscarsCount;
    private MovieGenre genre;
    private MpaaRating mpaaRating;
    private Person operator;
    private int userID;
    /**
     * @param id - id
     * @param name - Название фильма
     * @param coordinates - Локация
     * @param date - Дата создания
     * @param genre - Жанр
     * @param mpaaRating - рейтинг
     * @param operator - оператор
     * @param address - Address
     */
    private static final AtomicInteger ID_Generator = new AtomicInteger(1000);
    public Movie(String name, Coordinates coordinates, Integer oscarsCount, MovieGenre genre, MpaaRating mppaRating, Person operator) {
        this.id = ID_Generator.getAndIncrement();
        this.creationDate = java.sql.Timestamp.valueOf(LocalDateTime.now());
        this.name = name;
        this.coordinates = coordinates;
        this.oscarsCount = oscarsCount;
        this.genre = genre;
        this.mpaaRating = mppaRating;
        this.operator = operator;
    }

    public Movie () {

    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public Person getOperator() {
        return operator;
    }

    public Integer getOscarsCount() {
        return oscarsCount;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOperator(Person operator) {
        this.operator = operator;
    }

    public void setOscarsCount(Integer oscarsCount) {
        this.oscarsCount = oscarsCount;
    }
    public void setMovie(Movie movie) {
        name = movie.getName();
        id = movie.getId();
        coordinates = movie.getCoordinates();
        creationDate = movie.getCreationDate();
        oscarsCount = movie.getOscarsCount();
        genre = movie.getGenre();
        mpaaRating = movie.getMpaaRating();


    }

    public Date getCreationDate() {
        return creationDate;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getId() {
        return id;
    }
    public boolean is_valide() {
        if (id > 0 && name != null && !name.isEmpty() && coordinates.is_valide() && creationDate != null && oscarsCount > 0) {
            if (operator==null) {
                return true;
            } else if (operator.is_valide()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public String getMovie() {
        String s = "";
        s += "ID: " + id + "\n";
        s+= "Название фильма: " + name + "\n";
        s+= "Координаты: " + coordinates.toString() + "\n";
        s+= "Количество оскаров: ";
        if (oscarsCount != null)
            s+=oscarsCount + "\n";
        else
            s+="\n";
        if (genre != null)
            s+= "Жанр: " + genre.getName() + "\n";
        else
            s += "Жанр: \n";
        if (mpaaRating!=null)
            s+= "MPAA рейтинг: " + mpaaRating.toString() + "\n";
        else
            s+="MPAA рейтинг: \n";
        if (operator!=null)
            s += "Оператор \n" + operator.toString() + "\n";
        else
            s += "Оператор \n";
        s+= "Дата добавления: " + creationDate + "\n";
        return s;
    }

    @Override
    public int compareTo(Movie o) {
        return this.getName().compareTo(o.getName());
    }

}

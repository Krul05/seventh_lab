package lib.Models;

import java.io.Serializable;

public class MovieEntr implements Serializable {
    private String name;
    private Coordinates coordinates;
    private Integer oscarsCount;
    private MovieGenre genre;
    private MpaaRating mpaaRating;
    private Person operator;
    public MovieEntr(String name, Coordinates coordinates, Integer oscarsCount, MovieGenre genre, MpaaRating mppaRating, Person operator) {
        this.name = name;
        this.coordinates = coordinates;
        this.oscarsCount = oscarsCount;
        this.genre = genre;
        this.mpaaRating = mppaRating;
        this.operator = operator;
    }

    public MovieEntr () {

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


    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOscarsCount(Integer oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    public void setOperator(Person operator) {
        this.operator = operator;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}

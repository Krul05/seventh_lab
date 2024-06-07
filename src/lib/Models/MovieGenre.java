package lib.Models;
/**
 * Возможные жанры
 */
public enum MovieGenre {
    MUSICAL("Музыкальный фильм"),
    ADVENTURE("Приключенческий фильм"),
    HORROR("Фильм ужасов"),
    SCIENCE_FICTION("Научная фантастика");
    private String name;

    MovieGenre(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}

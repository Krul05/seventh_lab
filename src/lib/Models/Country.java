package lib.Models;

/**
 * Возможные национальности.
 */
public enum Country {
    UNITED_KINGDOM("Великобритания"),
    GERMANY("Германия"),
    CHINA("Китай"),
    SOUTH_KOREA("Южная Корея");
    private String name;
    Country(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}

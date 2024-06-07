package Client.Manager;

import lib.Console;
import Client.Manager.Inputs;
import lib.Models.*;
/**
 * Класс Builders - проверяет введённые значения
 */
public class Builders {
    Console console;
    Inputs inputs;
    public Builders(Console console, Inputs inputs) {
        this.console = console;
        this.inputs = inputs;
    }

    public Integer oscarsBuilder(String s) {
        Integer oscarsCount = null;
        if (s!=null && !s.isEmpty()) {
            boolean e = true;
            while (e) {
                try {
                    oscarsCount = Integer.parseInt(s);
                    if (!(oscarsCount > 0)) {
                        console.println("Количество оскаров должно быть положительным");
                        oscarsCount = oscarsBuilder(inputs.oscarsInput());
                    }
                    e = false;

                } catch (NumberFormatException ex) {
                    console.println("Количество оскаров - это целочисленное число");
                    s = inputs.oscarsInput();
                }
            }
        } else {
            oscarsCount = null;
        }
        return oscarsCount;
    }
    public String movieNameBuilder(String name) {
        if (name!=null && !name.isEmpty()) {
            return name;
        } else {
            console.println("Поле не должно быть пустым");
            name = inputs.movieNameInput();
        }
        return name;
    }
    public String isOperatorBuilder(String s) {
        while (!s.equals("yes") && !s.equals("no")) {
            s = inputs.isOperatorInput();
        }
        return s;
    }
    public MovieGenre genreBuilder(String s) {
        MovieGenre movieGenre = null;
        boolean e = true;
        while (e) {
            try {
                if (!s.isEmpty()) {
                    movieGenre = MovieGenre.valueOf(s);
                }
                e = false;
            } catch (Exception ex) {
                s = inputs.genreInput();
            }
        }
        return movieGenre;
    }
    public MpaaRating ratingBuilder(String s) {
        MpaaRating mpaaRating = null;
        boolean e = true;
        while (e) {
            try {
                if (!s.isEmpty()) {
                    mpaaRating = MpaaRating.valueOf(s);
                }
                e = false;
            } catch (Exception ex) {
                s = inputs.ratingInput();
            }

        }
        return mpaaRating;
    }
    public Coordinates coordinatesBuilder(String x1, String y1) {
        boolean e = true;
        double x = 0;
        while (e) {
            try {
                x = Double.parseDouble(x1);
                e = false;
            } catch (NumberFormatException ex) {
                x1 = inputs.xInput();
            }
        }
        e = true;
        int y = 0;
        while (e) {
            try {
                y = Integer.parseInt(y1);
                e = false;
            } catch (NumberFormatException ex) {
                y1 = inputs.yInput();
            }
        }
        Coordinates coordinates;
        if (y > 662) {
            console.println("y должен быть меньше 662");
            coordinates = coordinatesBuilder(" ", " ");
        } else {
            coordinates = new Coordinates(x, y);
        }
        return coordinates;
    }
    public String personNameBuilder(String name) {
        while (name == null || name.isEmpty()) {
            console.println("Имя человека не может быть пустым");
            name = inputs.personNameInput();
        }
        return name;
    }
    public String passportIDBuilder(String passportID) {
        boolean e = true;
        while (e) {
            if (passportID == null || passportID.isEmpty()) {
                passportID = null;
                return passportID;
            } else if (passportID.length() < 9 || passportID.length() > 40) {
                console.println("ПаспортID должен содержать 9-40 символов");
                passportID = inputs.passportIDInput();
            } else {
                e = false;
            }
        }
        return passportID;
    }
    public Country nationalityBuilder(String s) {
        while (s.isEmpty()) {
            console.println("Национальность не может быть пустой");
            s = inputs.nationalityInput();
        }
        boolean e = true;
        Country nationality = null;
        while (e) {
            try {
                nationality = Country.valueOf(s);
                e = false;
            } catch (Exception ex) {
                s = inputs.nationalityInput();
            }
        }
        return nationality;
    }
}

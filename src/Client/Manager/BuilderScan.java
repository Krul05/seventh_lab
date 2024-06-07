package Client.Manager;

import lib.Models.*;

public class BuilderScan {
    public Integer oscarsBuilder(String s) {
        Integer oscarsCount = null;
        if (s!=null && !s.isEmpty()) {
            try {
                oscarsCount = Integer.parseInt(s);
                if (!(oscarsCount > 0)) {
                    oscarsCount = null;
                }

            } catch (NumberFormatException ex) {
                oscarsCount = null;
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
            name = null;
        }
        return name;
    }
    public String isOperatorBuilder(String s) {
        while (!s.equals("yes") && !s.equals("no")) {
            s = "no";
        }
        return s;
    }
    public MovieGenre genreBuilder(String s) {
        MovieGenre movieGenre = null;

        try {
            if (!s.isEmpty()) {
                movieGenre = MovieGenre.valueOf(s);
            }
        } catch (Exception ex) {
        }
        return movieGenre;
    }
    public MpaaRating ratingBuilder(String s) {
        MpaaRating mpaaRating = null;
        try {
            if (!s.isEmpty()) {
                mpaaRating = MpaaRating.valueOf(s);
            }
        } catch (Exception ex) {
        }

        return mpaaRating;
    }
    public Coordinates coordinatesBuilder(String x1, String y1) {
        Coordinates coordinates = new Coordinates(0, 0);
        double x = 0;
        try {
                x = Double.parseDouble(x1);
        } catch (NumberFormatException ex) {
            coordinates = null;
        }
        int y = 0;
        try {
            y = Integer.parseInt(y1);
        } catch (NumberFormatException ex) {
            coordinates = null;
        }

        if (y > 662) {
            coordinates = null;
        } else if (coordinates!=null){
            coordinates = new Coordinates(x, y);
        }
        return coordinates;
    }
    public String personNameBuilder(String name) {
        if (name == null || name.isEmpty()) {
            name = null;
        }
        return name;
    }
    public String passportIDBuilder(String passportID) {
        if (passportID == null || passportID.isEmpty()) {
            passportID = null;
        } else if (passportID.length() < 9 || passportID.length() > 40) {
            passportID = null;
        }
        return passportID;
    }
    public Country nationalityBuilder(String s) {
        boolean e = true;
        if (s.isEmpty()) {
           e = false;
        }

        Country nationality = null;
        try {
            nationality = Country.valueOf(s);
        } catch (Exception ex) {
            e = false;
        }
        if (!e) {
            nationality = null;
        }
        return nationality;
    }
}

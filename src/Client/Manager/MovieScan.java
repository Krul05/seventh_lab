package Client.Manager;

import lib.Models.*;

import java.util.Scanner;

public class MovieScan {
    Scanner scanner;
    public MovieScan(Scanner scanner) {
        this.scanner = scanner;
    }
    public MovieEntr movieScan() {
        try {
            BuilderScan builders = new BuilderScan();
            String movieName = builders.movieNameBuilder(scanner.nextLine().trim());
            int oscars = builders.oscarsBuilder(scanner.nextLine().trim());
            Coordinates coordinates = builders.coordinatesBuilder(scanner.nextLine(), scanner.nextLine());
            String isOperator = builders.isOperatorBuilder(scanner.nextLine());
            Person person = null;
            if (isOperator.equals("yes")) {
                String personName = builders.personNameBuilder(scanner.nextLine().trim());
                String passportID = builders.passportIDBuilder(scanner.nextLine().trim());
                Country nationality = builders.nationalityBuilder(scanner.nextLine());
                if (personName!= null && nationality!=null) {
                    person = new Person(personName, passportID, nationality);
                }
            }
            MovieGenre genre = builders.genreBuilder(scanner.nextLine().trim());
            MpaaRating rating = builders.ratingBuilder(scanner.nextLine().trim());
            MovieEntr movie = null;
            if (movieName!=null && coordinates!=null) {
                movie = new MovieEntr(movieName, coordinates, oscars, genre, rating, person);
            }
            return movie;
        } catch (Exception ex) {
            return null;
        }

    }
}

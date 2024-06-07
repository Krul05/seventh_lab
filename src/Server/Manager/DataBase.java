package Server.Manager;

import Server.Commands.Update;
import lib.Models.*;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;

import static java.sql.Types.NULL;

public class DataBase {
    private static Connection connection;
    public static void setConnection(String URL, String username, String password) {
        try {
            connection = DriverManager.getConnection(URL, username, password);
            System.out.println("Соединение с базой данных установлено.");
        } catch (SQLException e) {
            System.err.println("Не удалось установить соединение с базой данных.");
            e.printStackTrace();
        }
    }

    public static boolean checkUser(User user) {
        String query = "SELECT EXISTS(SELECT 1 FROM users WHERE username = ?)";
        try (PreparedStatement p = connection.prepareStatement(query)) {
            p.setString(1, user.getUsername());
            ResultSet result = p.executeQuery();
            if (result.next()) {
                return result.getBoolean(1);
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
    public static boolean checkPassword(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String query = "SELECT userpassword FROM users WHERE username = ?";
        try (PreparedStatement p = connection.prepareStatement(query)){
            p.setString(1, username);
            ResultSet result = p.executeQuery();
            if (result.next()){
                String encryptedPassword = result.getString("userpassword");
                return encryptedPassword.equals(password);
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public static boolean addUser(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String query = "INSERT INTO users (username, userpassword) VALUES (?, ?)";
        try (PreparedStatement p = connection.prepareStatement(query)){
            p.setString(1, username);
            p.setString(2, password);
            p.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static CollectionManager getCollection() {
        String query = "SELECT * FROM movie ORDER BY moviename DESC";
        CollectionManager collection = new CollectionManager(new LinkedList<Movie>());
        try (PreparedStatement p = connection.prepareStatement(query)){
            ResultSet res = p.executeQuery();
            while (res.next()){
                try {
                    Movie movie = new Movie();
                    movie.setId(res.getInt("id"));
                    movie.setName(res.getString("moviename"));
                    movie.setCoordinates(new Coordinates(res.getDouble("x"), res.getInt("y")));
                    movie.setCreationDate(res.getDate("creationdate"));
                    movie.setOscarsCount(res.getInt("oscarscount"));
                    try {
                        movie.setMpaaRating(MpaaRating.valueOf(res.getString("mpaarating")));
                    } catch (NullPointerException e) {
                        movie.setMpaaRating(null);
                    }
                    try {
                        movie.setGenre(MovieGenre.valueOf(res.getString("genre")));
                    } catch (NullPointerException e) {
                        movie.setGenre(null);
                    }
                    try {
                        movie.setOperator(new Person(res.getString("personname"), res.getString("passportid"), Country.valueOf(res.getString("nationality"))));
                    } catch (NullPointerException ex) {
                        movie.setOperator(null);

                    }
                    movie.setUserID(res.getInt("userid"));
                    collection.getCollection().add(movie);
                } catch (IllegalArgumentException e){
                    e.printStackTrace();
                }
            }
            return collection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new CollectionManager(new LinkedList<Movie>());
    }

    public static boolean addMovie(Movie movie, User user) {
        String query = "INSERT INTO movie (moviename, creationdate, oscarscount, genre, mpaarating, x, y, personname," +
                "passportid, nationality, userid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, (SELECT id FROM users WHERE username = ?))";

        try (PreparedStatement p = connection.prepareStatement(query)){
            p.setString( 1, movie.getName());
            Date date = new Date();
            p.setDate(2, new java.sql.Date(date.getTime()));
            p.setInt(3, movie.getOscarsCount());
            if (movie.getGenre()!=null) {
                p.setString(4, movie.getGenre().name());
            } else {
                p.setNull(4, NULL);
            }
            if (movie.getMpaaRating()!=null) {
                p.setString(5, movie.getMpaaRating().name());
            } else {
                p.setNull(5, NULL);
            }
            p.setDouble(6, movie.getCoordinates().getX());
            p.setInt(7, movie.getCoordinates().getY());
            if (movie.getOperator() != null) {
                p.setString(8, movie.getOperator().getName());
                if (movie.getOperator().getPassportID()!=null) {
                    p.setString(9, movie.getOperator().getPassportID());
                } else {
                    p.setNull(9, NULL);
                }
                p.setString(10, movie.getOperator().getNationality().name());
            } else {
                p.setNull(8, NULL);
                p.setNull(9, NULL);
                p.setNull(10, NULL);
            }
            p.setString(11, user.getUsername());
            p.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;
        }


    }

    public static boolean removeById(int id, User user) {
        String query = "DELETE FROM movie WHERE (id = ? AND userid = (SELECT id FROM users WHERE username = ?))";
        try (PreparedStatement p = connection.prepareStatement(query)){
            p.setInt(1, id);
            p.setString(2, user.getUsername());
            p.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean update(int id, Movie movie, User user) {
        String query = "UPDATE movie SET moviename = ?, creationdate = ?, oscarscount = ?, genre = ?, mpaarating = ?, x = ?, y = ?, personname = ?," +
                "passportid = ?, nationality = ? WHERE (id = ? AND userid = (SELECT id FROM users WHERE username = ?))";
        try (PreparedStatement p = connection.prepareStatement(query)){
            p.setString( 1, movie.getName());
            Date date = new Date();
            p.setDate(2, new java.sql.Date(date.getTime()));
            p.setInt(3, movie.getOscarsCount());
            if (movie.getGenre()!=null) {
                p.setString(4, movie.getGenre().name());
            } else {
                p.setNull(4, NULL);
            }
            if (movie.getMpaaRating()!=null) {
                p.setString(5, movie.getMpaaRating().name());
            } else {
                p.setNull(5, NULL);
            }
            p.setDouble(6, movie.getCoordinates().getX());
            p.setInt(7, movie.getCoordinates().getY());
            if (movie.getOperator() != null) {
                p.setString(8, movie.getOperator().getName());
                if (movie.getOperator().getPassportID()!=null) {
                    p.setString(9, movie.getOperator().getPassportID());
                } else {
                    p.setNull(9, NULL);
                }
                p.setString(10, movie.getOperator().getNationality().name());
            } else {
                p.setNull(8, NULL);
                p.setNull(9, NULL);
                p.setNull(10, NULL);
            }
            p.setInt(11, id);
            p.setString(12, user.getUsername());
            p.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;
        }
    }


    public static boolean clearCollection(User user) {
        String query = "DELETE FROM movie WHERE userid = (SELECT id FROM users WHERE username = ?)";
        try (PreparedStatement p = connection.prepareStatement(query)){
            p.setString(1, user.getUsername());
            p.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public static boolean removeAllByGenre(MovieGenre genre, User user) {
        String query = "DELETE FROM movie WHERE moviegenre = ? and userid = (SELECT id FROM users WHERE username = ?)";
        try (PreparedStatement p = connection.prepareStatement(query)){
            p.setString(1, genre.name());
            p.setString(2, user.getUsername());
            p.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public static boolean removeFirst(User user) {
        String query = "DELETE FROM movie WHERE userid = (SELECT id FROM users WHERE username = ?) and id = (select id from movie where moviename = max(moviename) and userid = (select id from users where username = ?))";

        try (PreparedStatement p = connection.prepareStatement(query)){
            p.setString(1, user.getUsername());
            p.setString(2, user.getUsername());
            p.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

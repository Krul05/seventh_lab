package lib;

import lib.Models.MovieEntr;

import java.io.Serializable;

public class Commands<T> implements Serializable {
    String name;
    T argument = null;
    MovieEntr movie = null;
    public Commands(String name, T argument, MovieEntr movie) {
        this.name = name;
        this.argument = argument;
        this.movie = movie;
    }
    public Commands(String name, MovieEntr movie) {
        this.name = name;
        this.movie = movie;
    }
    public Commands(String name, T argument) {
        this.name = name;
        this.argument = argument;
    }
    public Commands(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public T getArgument() {
        return argument;
    }

    public MovieEntr getMovie() {
        return movie;
    }
}

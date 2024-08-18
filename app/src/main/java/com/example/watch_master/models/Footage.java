package com.example.watch_master.models;

import java.util.HashMap;

public class Footage {

    private String nameList = " ";
    private boolean type;
    private HashMap<String,Movie> movies = new HashMap<>();
    private HashMap<String,TvShow> tvShows = new HashMap<>();

    public Footage(){

    }


    public String getNameList() {
        return nameList;
    }

    public Footage setNameList(String nameList) {
        this.nameList = nameList;
        return this;
    }


    public HashMap<String, Movie> getMovies() {
        return movies;
    }

    public Footage setMovies(HashMap<String, Movie> movies) {
        this.movies = movies;
        return this;
    }


    public HashMap<String, TvShow> getTvShows() {
        return tvShows;
    }

    public Footage setTvShows(HashMap<String, TvShow> tvShows) {
        this.tvShows = tvShows;
        return this;
    }

}

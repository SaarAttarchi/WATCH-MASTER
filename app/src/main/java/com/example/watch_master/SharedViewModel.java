package com.example.watch_master;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.watch_master.models.Movie;
import com.example.watch_master.models.TvShow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SharedViewModel extends ViewModel {

    private final MutableLiveData<HashMap<String, TvShow>> selectedTvShows = new MutableLiveData<>(new HashMap<>());
    private final MutableLiveData<HashMap<String, Movie>> selectedMovies = new MutableLiveData<>(new HashMap<>());

    // Getter for TvShows
    public MutableLiveData<HashMap<String, TvShow>> getSelectedTvShows() {
        return selectedTvShows;
    }

    // Getter for Movies
    public MutableLiveData<HashMap<String, Movie>> getSelectedMovies() {
        return selectedMovies;
    }


    // Setter for TvShows
    public void setSelectedTvShows(HashMap<String, TvShow> tvShows) {
        selectedTvShows.setValue(tvShows);
    }

    // Setter for Movies
    public void setSelectedMovies(HashMap<String, Movie> movies) {
        selectedMovies.setValue(movies);
    }
    // add a new tv show to the list
    public void addTvShow(String key, TvShow tvShow) {
        HashMap<String, TvShow> currentShows = selectedTvShows.getValue();
        if (currentShows != null) {
            currentShows.put(key, tvShow);
            selectedTvShows.setValue(currentShows);
        }
    }

    // add a new movie to the list
    public void addMovie(String key, Movie movie) {
        HashMap<String, Movie> currentMovies = selectedMovies.getValue();
        if (currentMovies != null) {
            currentMovies.put(key, movie);
            selectedMovies.setValue(currentMovies);
        }
    }
    // remove a tv show from the list
    public void removeTvShow(String key) {
        HashMap<String, TvShow> currentShows = selectedTvShows.getValue();
        if (currentShows != null) {
            currentShows.remove(key);
            selectedTvShows.setValue(currentShows);
        }
    }
    // remove a movie from the list
    public void removeMovie(String key) {
        HashMap<String, Movie> currentMovies = selectedMovies.getValue();
        if (currentMovies != null) {
            currentMovies.remove(key);
            selectedMovies.setValue(currentMovies);
        }
    }
}

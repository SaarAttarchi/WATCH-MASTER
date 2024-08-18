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

    public MutableLiveData<HashMap<String, TvShow>> getSelectedTvShows() {
        return selectedTvShows;
    }

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

    public void addTvShow(String key, TvShow tvShow) {
        HashMap<String, TvShow> currentShows = selectedTvShows.getValue();
        if (currentShows != null) {
            currentShows.put(key, tvShow);
            selectedTvShows.setValue(currentShows);
        }
    }


    public void addMovie(String key, Movie movie) {
        HashMap<String, Movie> currentMovies = selectedMovies.getValue();
        if (currentMovies != null) {
            currentMovies.put(key, movie);
            selectedMovies.setValue(currentMovies);
        }
    }

    public void removeTvShow(String key) {
        HashMap<String, TvShow> currentShows = selectedTvShows.getValue();
        if (currentShows != null) {
            currentShows.remove(key);
            selectedTvShows.setValue(currentShows);
        }
    }

    public void removeMovie(String key) {
        HashMap<String, Movie> currentMovies = selectedMovies.getValue();
        if (currentMovies != null) {
            currentMovies.remove(key);
            selectedMovies.setValue(currentMovies);
        }
    }
}

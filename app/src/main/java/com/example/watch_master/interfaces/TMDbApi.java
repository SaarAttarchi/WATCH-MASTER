package com.example.watch_master.interfaces;

import com.example.watch_master.DataManager.EpisodeDiscoverResponse;
import com.example.watch_master.DataManager.MovieDiscoverResponse;
import com.example.watch_master.DataManager.TvShowDiscoverResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDbApi {
    // Fetch TV Shows
    @GET("/3/discover/tv")
    Call<TvShowDiscoverResponse> searchTvShow(@Query("api_key") String apiKey);

    // Fetch Episodes
    @GET("/3/discover/tv")
    Call<EpisodeDiscoverResponse> searchTvShowEpisode(@Query("api_key") String apiKey);

    // Fetch Movies
    @GET("/3/discover/movie")
    Call<MovieDiscoverResponse> searchMovies(@Query("api_key") String apiKey);

}

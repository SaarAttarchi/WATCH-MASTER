package com.example.watch_master.interfaces;

import com.example.watch_master.DataManager.EpisodeDiscoverResponse;
import com.example.watch_master.DataManager.MovieDiscoverResponse;
import com.example.watch_master.DataManager.TvShowDiscoverResponse;
import com.example.watch_master.models.TvShow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDbApi {
    // Fetch TV Shows
    @GET("/3/discover/tv")
    Call<TvShowDiscoverResponse> searchTvShow(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("/3/tv/{tv_id}")
    Call<TvShow> searchTvShowInfo(@Path("tv_id") int showId, @Query("api_key") String apiKey);


    // Fetch Episodes
    @GET("/3/tv/{tv_id}/season/{season_number}")
    Call<EpisodeDiscoverResponse> searchTvShowEpisode(
            @Path("tv_id") int showId,
            @Path("season_number") int seasonNumber,
            @Query("api_key") String apiKey
    );


    // Fetch Movies
    @GET("/3/discover/movie")
    Call<MovieDiscoverResponse> searchMovies(@Query("api_key") String apiKey, @Query("page") int page);

}

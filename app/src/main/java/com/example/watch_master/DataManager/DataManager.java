package com.example.watch_master.DataManager;

import android.util.Log;

import com.example.watch_master.interfaces.DataFetchCallback;
import com.example.watch_master.interfaces.EpisodeFetchCallback;
import com.example.watch_master.interfaces.TMDbApi;
import com.example.watch_master.interfaces.TvShowInfoCallback;
import com.example.watch_master.models.Episode;
import com.example.watch_master.models.Movie;
import com.example.watch_master.models.TvShow;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DataManager {


    public void allDta(String query, DataFetchCallback callback){
        HashMap<String, TvShow> tvShows = new HashMap<>();
        HashMap<String, Movie> movies = new HashMap<>();

        // getting all the tv shows from the data base
        fetchTvShows(callback, "", tvShows, movies, query);
        // getting all the movies from the data base
        fetchMovies("",callback, movies, tvShows, query);


    }

    // getting the right url every time and fetching the tv shows from the data base
    // using a search query when needed
    // creates a new tv show object to fetch that information
    public void fetchTvShows(DataFetchCallback callback, String apiKey, HashMap<String, TvShow> tvShows, HashMap<String, Movie> movies, String query) {
        for (int i = 0; i < 5; i++) { // set how many pages of information we want
            TMDbApi apiService = ApiClient.getRetrofitInstance().create(TMDbApi.class);
            Call<TvShowDiscoverResponse> tvShowCall;
            if(query == null){
                tvShowCall = apiService.searchTvShow(apiKey, i);
            }
            else{
                tvShowCall = apiService.searchTvShowByName(apiKey, query, i);
            }

            Log.d("TAG", "Request URL: " + tvShowCall.request().url());
            tvShowCall.enqueue(new Callback<TvShowDiscoverResponse>() {
                @Override
                public void onResponse(Call<TvShowDiscoverResponse> call, Response<TvShowDiscoverResponse> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        TvShowDiscoverResponse showResponses = response.body();

                        for (TvShow show : showResponses.getResults()) {
                            TvShow tvShow = new TvShow()
                                    .setId(show.getId())
                                    .setOriginal_name(show.getOriginal_name())
                                    .setOverview(show.getOverview())
                                    .setFirst_air_date(show.getFirst_air_date())
                                    .setPoster_path("https://image.tmdb.org/t/p/w500" + show.getPoster_path())
                                    .setVote_average(show.getVote_average());// Ensure full URL handling for the image

                            tvShows.put(String.valueOf(show.getId()), tvShow);


                            fetchInfo(tvShow, apiKey, new TvShowInfoCallback() {
                                @Override
                                public void onInfoFetched(TvShow tvShow) {

                                }
                            });


                            Log.d("TAG10", "shows onResponse:" + tvShow.getNumber_of_seasons());
                            Log.d("TAG10", "shows onResponse:" + tvShow.getNumber_of_episodes());
                            Log.d("TAG", "show: " + show.getOriginal_name());

                            HashMap<String, Episode> tvShowEpisodes = new HashMap<>();

                            fetchEpisodes(tvShow, tvShowEpisodes, apiKey, new EpisodeFetchCallback() {
                                @Override
                                public void onEpisodesFetched(HashMap<String, Episode> episodes) {
                                    tvShow.setEpisodes(tvShowEpisodes);
                                }
                            });
                        }

                        if (callback != null) {
                            callback.onDataFetched(tvShows, movies);
                        }
                    }
                }

                @Override
                public void onFailure(Call<TvShowDiscoverResponse> call, Throwable t) {
                    Log.d("TAG", "Moviesdf;sjfl;kdsjflkdsjflds: ");
                }
            });
        }
    }

    // search in different URL in order to get more information about the show
    private void fetchInfo(TvShow tvShow, String apiKey, TvShowInfoCallback tvShowInfoCallback) {
        TMDbApi apiService = ApiClient.getRetrofitInstance().create(TMDbApi.class);
        Call<TvShow> call = apiService.searchTvShowInfo(tvShow.getId(), apiKey);
        call.enqueue(new Callback<TvShow>() {
            @Override
            public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TvShow tvShowResponse = response.body();

                    // Set the number of seasons and episodes
                    tvShow.setNumber_of_seasons(tvShowResponse.getNumber_of_seasons());
                    tvShow.setNumber_of_episodes(tvShowResponse.getNumber_of_episodes());


                    if (tvShowInfoCallback != null) {
                        tvShowInfoCallback.onInfoFetched(tvShow);
                    }
                }
            }

            @Override
            public void onFailure(Call<TvShow> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void fetchEpisodes(TvShow tvShow, HashMap<String, Episode> tvShowEpisodes, String apiKey, EpisodeFetchCallback callBack) {
        // Fetch episodes for the show
        TMDbApi apiService = ApiClient.getRetrofitInstance().create(TMDbApi.class);

        for (int i = 1; i <= 2; i++) {
            Call<EpisodeDiscoverResponse> episodeCall = apiService.searchTvShowEpisode(tvShow.getId(), i, apiKey);
            Log.d("TAG", "Request URL: " + episodeCall.request().url());


            episodeCall.enqueue(new Callback<EpisodeDiscoverResponse>() {
                @Override
                public void onResponse(Call<EpisodeDiscoverResponse> call, Response<EpisodeDiscoverResponse> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        EpisodeDiscoverResponse episodeResponses = response.body();

                        // Extract episodes
                        for (Episode episodeResponse : episodeResponses.getEpisodes()) {
                            // save the episodes in an easy way we could get to
                            int episodeKey =  episodeResponse.getSeason_number() * 10000 + episodeResponse.getEpisode_number();


                            Episode episode = new Episode()
                                    .setId(episodeResponse.getId())
                                    .setName(episodeResponse.getName())
                                    .setSeason_number(episodeResponse.getSeason_number())
                                    .setEpisode_number(episodeResponse.getEpisode_number())
                                    .setOverview(episodeResponse.getOverview())
                                    .setAir_date(episodeResponse.getAir_date())
                                    .setVote_average(episodeResponse.getVote_average())
                                    .setRuntime(episodeResponse.getRuntime());
                            tvShowEpisodes.put(String.valueOf(episodeKey), episode);
                            Log.d("TAG7", "Episode: " + episode.getName());
                        }

                        Log.d("TAG", "show: " + tvShow.getOriginal_name());

                        if (callBack != null) {
                            callBack.onEpisodesFetched(tvShowEpisodes);
                        }


                    } else {
                        Log.d("TAG7", "Episode response is unsuccessful or body is null.");
                    }
                }

                @Override
                public void onFailure(Call<EpisodeDiscoverResponse> call, Throwable t) {
                    Log.d("TAG", "Failed to fetch episodes: " + t.getMessage());
                }
            });
        }
    }











    // getting the right url every time and fetching the tv shows from the data base
    // using a search query when needed
    // creates a new movie object to fetch that information
    public void fetchMovies(String apiKey, DataFetchCallback callback, HashMap<String, Movie> movies, HashMap<String, TvShow> tvShows, String query) {
        for (int i = 0; i < 10; i++) {// set how mny pages of information we want

            TMDbApi apiService = ApiClient.getRetrofitInstance().create(TMDbApi.class);
            Call<MovieDiscoverResponse> movieCall;
            if(query == null){
                movieCall = apiService.searchMovies(apiKey ,i);
            }else{
                movieCall = apiService.searchMoviesByName(apiKey, query,i);
            }

            Log.d("TAG", "Request URL for movies: " + movieCall.request().url());

            movieCall.enqueue(new Callback<MovieDiscoverResponse>() {
                @Override
                public void onResponse(Call<MovieDiscoverResponse> call, Response<MovieDiscoverResponse> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        MovieDiscoverResponse moviesResponses = response.body();

                        for (Movie movie : moviesResponses.getResults()) {
                            Movie movieItem = new Movie()
                                    .setId(movie.getId())
                                    .setTitle(movie.getTitle())
                                    .setOverview(movie.getOverview())
                                    .setRelease_date(movie.getRelease_date())
                                    .setPoster_path("https://image.tmdb.org/t/p/w500" + movie.getPoster_path())
                                    .setVote_average(movie.getVote_average());// Ensure full URL handling for the image

                            movies.put(String.valueOf(movie.getId()), movieItem);
                            Log.d("TAG", "Movie: " + movie.getTitle());
                        }
                        // using an interface to get all the all movies hashmaps
                        if (callback != null) {
                            callback.onDataFetched(tvShows, movies);
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieDiscoverResponse> call, Throwable t) {

                }
            });
        }
    }
}


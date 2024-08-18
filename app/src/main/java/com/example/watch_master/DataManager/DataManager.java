package com.example.watch_master.DataManager;

import android.util.Log;

import com.example.watch_master.interfaces.DataFetchCallback;
import com.example.watch_master.interfaces.EpisodeFetchCallback;
import com.example.watch_master.interfaces.TMDbApi;
import com.example.watch_master.interfaces.TVMazeApi;
import com.example.watch_master.models.Episode;
import com.example.watch_master.models.Movie;
import com.example.watch_master.models.Show;
import com.example.watch_master.models.TvShow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DataManager {


    public void allDta(DataFetchCallback callback){
        HashMap<String, TvShow> tvShows = new HashMap<>();
        HashMap<String, Movie> movies = new HashMap<>();

        fetchData(callback, "", tvShows, movies);

        fetchMovies("",callback, movies, tvShows);



        //fetchData(callback, "breaking bad","https://static.tvmaze.com/uploads/images/medium_portrait/501/1253519.jpg", tvShows, movies);
        //fetchData(callback, "house of the dragon","https://static.tvmaze.com/uploads/images/medium_portrait/530/1325279.jpg", tvShows, movies);
        //fetchData(callback, "The Boys","https://static.tvmaze.com/uploads/images/medium_portrait/528/1321349.jpg", tvShows, movies);
        //fetchData(callback, "Avatar: The Last Airbender ","https://static.tvmaze.com/uploads/images/medium_portrait/79/199224.jpg", tvShows, movies);






    }


    public void fetchData(DataFetchCallback callback, String apiKey, HashMap<String, TvShow> tvShows, HashMap<String, Movie> movies) {
        TMDbApi apiService2 = ApiClient.getRetrofitInstanceMovie().create(TMDbApi.class);
        Call<TvShowDiscoverResponse> tvShowCall = apiService2.searchTvShow(apiKey);
        Log.d("TAG", "Request URL: " + tvShowCall.request().url());


        tvShowCall.enqueue(new Callback<TvShowDiscoverResponse>() {
            @Override
            public void onResponse(Call<TvShowDiscoverResponse> call, Response<TvShowDiscoverResponse> response) {
                Log.d("TAG9", "shows onResponse:");


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
                        Log.d("TAG", "show: " + show.getOriginal_name());

                        HashMap<String, Episode> tvShowEpisodes = new HashMap<>();
                        fetchEpisodes(tvShow, tvShowEpisodes, apiKey);
                    }

                    if (callback != null) {
                        callback.onDataFetched(tvShows, movies);
                    }
                }
            }

            @Override
            public void onFailure(Call<TvShowDiscoverResponse> call, Throwable t) {
                Log.d("TAG", "Moviesdf;sjfl;kdsjflkdsjflds: " );
            }
        });
    }

    public void fetchEpisodes(TvShow tvShow, HashMap<String, Episode> tvShowEpisodes, String apiKey) {
        // Fetch episodes for the show
        TMDbApi apiService2 = ApiClient.getRetrofitInstanceMovie().create(TMDbApi.class);
        Log.d("TAG", "Moviesdf;sjfl;kdsjflkdsjflds: " );
        Call<EpisodeDiscoverResponse> episodeCall = apiService2.searchTvShowEpisode( apiKey);
        Log.d("TAG16", "Request URL: " + episodeCall.request().url());

        episodeCall.enqueue(new Callback<EpisodeDiscoverResponse>() {
            @Override
            public void onResponse(Call<EpisodeDiscoverResponse> call, Response<EpisodeDiscoverResponse> response) {
                Log.d("TAG17", "Episodes onResponse:");
                if (response.isSuccessful() && response.body() != null) {
                    EpisodeDiscoverResponse episodeResponses = response.body();
                    //Log.d("TAG", "Episodes fetched: " + episodeResponses);

                    // Extract episodes
                    for (Episode episodeResponse : episodeResponses.getResults()) {
                        Episode episode = new Episode()
                                .setId(episodeResponse.getId())
                                .setName(episodeResponse.getName())
                                .setSeason_number(episodeResponse.getSeason_number())
                                .setEpisode_number(episodeResponse.getEpisode_number())
                                .setOverview(episodeResponse.getOverview())
                                .setAir_date(episodeResponse.getAir_date())
                                .setVote_average(episodeResponse.getVote_average())
                                .setRuntime(episodeResponse.getRuntime());
                        tvShowEpisodes.put(String.valueOf(episodeResponse.getId()), episode);
                        Log.d("TAG7", "Episode: " + episodeResponse.getName());
                    }

                    Log.d("TAG", "show: " + tvShow.getOriginal_name());

                    
                    

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
/*

                    // Fetch movies after shows
                // Replace with actual movie endpoint and query

                Call<Movie> movieCall = apiService.getMovieDetails(500, "apikey");  // Adjust the API call method and parameters as needed
                Log.d("TAG", "Request URL: " + movieCall.request().url());

                movieCall.enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        Log.d("TAG", "Movies onResponse:");
                        HashMap<String, Movie> movies = new HashMap<>();

                        if (response.isSuccessful() && response.body() != null) {
                            Movie movie = response.body();  // Adjust this based on your MovieResponse structure

                            for (Movie tmdbMovie : movieResponses) {
                                // Create a new Movie object
                                Movie movie = new Movie()
                                        .setId(tmdbMovie.getId())
                                        .setName(tmdbMovie.getName())
                                        .setSummary(tmdbMovie.getSummary())
                                        .setReleaseDate(tmdbMovie.getReleaseDate())
                                        .setRating(tmdbMovie.getRating())
                                        .setGenre(tmdbMovie.getGenre())
                                        .setPoster_path(tmdbMovie.getPosterPath());

                                // Use the movie ID as the key
                                movies.put(String.valueOf(tmdbMovie.getId()), movie);
                                Log.d("TAG", "Movies onResponse:" + tmdbMovie.getName());
                            }
                        }

                        // Pass both TV shows and movies to the callback
                        if (callback != null) {
                            callback.onDataFetched(tvShows, movies);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {

                    }

                });

 */













    public void fetchMovies(String apiKey, DataFetchCallback callback, HashMap<String, Movie> movies, HashMap<String, TvShow> tvShows){

        TMDbApi apiService2 = ApiClient.getRetrofitInstanceMovie().create(TMDbApi.class);
        Call<MovieDiscoverResponse> movieCall = apiService2.searchMovies(apiKey);
        Log.d("TAG", "Request URL for movies: " + movieCall.request().url());

        movieCall.enqueue(new Callback<MovieDiscoverResponse>() {
            @Override
            public void onResponse(Call<MovieDiscoverResponse> call, Response<MovieDiscoverResponse> response) {
                Log.d("TAG9", "Movies onResponse:");


                if (response.isSuccessful() && response.body() != null) {
                    MovieDiscoverResponse moviesResponses = response.body();

                    for (Movie movie : moviesResponses.getResults()) {
//                        Movie movie = movieResponse.getMovie();
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

                    if (callback != null) {
                        callback.onDataFetched(tvShows, movies);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieDiscoverResponse> call, Throwable t) {
                Log.d("TAG", "Moviesdf;sjfl;kdsjflkdsjflds: " );
            }
        });
    }
}

/*
    public static Footage footageLibrary(){
        Footage footage = new Footage();

        /*
        footage.getTvShows().put("Game Of Thrones", new TvShow()
                .setName("game of thrones")
                .setEpisodeName("Home")
                .setEpisodeNumber("6x02")
                .setReleaseDate("01-05-2016")
                .setDuration("57 minutes")
                .setRating(8.6f)
                .setGenre(new ArrayList<>(Arrays.asList("Comedy", "Family", "Fantasy")))

        );





        footage.getTvShows().put("breaking bad", new TvShow()
                .setName("breaking bad")
                .setEpisodeName("pilot")
                .setEpisodeNumber("1x01")
                .setReleaseDate("24-07-2008")
                .setDuration("58 minutes")
                .setRating(9.0f)
                .setGenre(new ArrayList<>(Arrays.asList("Comedy", "Family", "Fantasy")))
        );

        footage.getTvShows().put("the clone wars", new TvShow()
                .setName("the clone wars")
                .setEpisodeName("pilot")
                .setEpisodeNumber("1x01")
                .setReleaseDate("24-07-2008")
                .setDuration("10 minutes")
                .setRating(9.0f)
                .setGenre(new ArrayList<>(Arrays.asList("Comedy", "Family", "Fantasy")))
        );
        footage.getTvShows().put("avatar the last airbender", new TvShow()
                .setName("avatar the last airbender")
                .setEpisodeName("pilot")
                .setEpisodeNumber("1x01")
                .setReleaseDate("24-07-2008")
                .setDuration("24 minutes")
                .setRating(9.0f)
                .setGenre(new ArrayList<>(Arrays.asList("Comedy", "Family", "Fantasy")))
        );

        footage.getTvShows().put("house of the dragon",new TvShow()
                .setName("house of the dragon")
                .setEpisodeName("pilot")
                .setEpisodeNumber("1x01")
                .setReleaseDate("24-07-2008")
                .setDuration("58 minutes")
                .setRating(9.0f)
                .setGenre(new ArrayList<>(Arrays.asList("Comedy", "Family", "Fantasy")))
        );

        footage.getTvShows().put("the boys",new TvShow()
                .setName("the boys")
                .setEpisodeName("pilot")
                .setEpisodeNumber("1x01")
                .setReleaseDate("24-07-2008")
                .setDuration("58 minutes")
                .setRating(9.0f)
                .setGenre(new ArrayList<>(Arrays.asList("Comedy", "Family", "Fantasy")))
        );











        footage.getMovies().put("Fight club", new Movie()
                .setName("Fight club")
                .setReleaseDate("16-11-1999")
                .setDuration("139 minutes")
                .setRating(8.8f)
                .setGenre(new ArrayList<>(Collections.singletonList("Drama")))
        );

        footage.getMovies().put("Inception", new Movie()
                .setName("Inception")
                .setReleaseDate("22-07-2010")
                .setDuration("148 minutes")
                .setRating(8.8f)
                .setGenre(new ArrayList<>(Arrays.asList("Action", "Adventure", "Sci-Fi")))
        );

        footage.getMovies().put("Forest Gump", new Movie()
                .setName("Forest Gump")
                .setReleaseDate("30-09-1994")
                .setDuration("142 minutes")
                .setRating(8.8f)
                .setGenre(new ArrayList<>(Arrays.asList("Drama", "Romance")))
        );

        footage.getMovies().put("The Matrix", new Movie()
                .setName("The Matrix")
                .setReleaseDate("24-06-1999")
                .setDuration("136 minutes")
                .setRating(8.8f)
                .setGenre(new ArrayList<>(Arrays.asList("Action", "Sci-Fi")))
        );



        Log.d("TAG", "saveToFirebase: sasasa");
        return footage;
    }
    }
 */






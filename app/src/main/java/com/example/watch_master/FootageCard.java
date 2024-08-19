package com.example.watch_master;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.watch_master.interfaces.EpisodeUpdateCallback;
import com.example.watch_master.models.Episode;
import com.example.watch_master.models.Movie;
import com.example.watch_master.models.TvShow;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FootageCard extends RecyclerView.Adapter<FootageCard.FootageViewHolder> {

    private HashMap<String,Movie> movies;
    private HashMap<String,TvShow> tvShows;
    private SharedViewModel sharedViewModel;
    private boolean location;


    public FootageCard(HashMap<String,TvShow> tvShows, HashMap<String, Movie> movies, SharedViewModel sharedViewModel, boolean location) {
        this.tvShows = tvShows;
        this.movies = movies;
        this.sharedViewModel = sharedViewModel;
        this.location = location;
    }

    public void setTvShows(HashMap<String,TvShow> tvShows) {
        this.tvShows = tvShows;
    }

    public void setMovies(HashMap<String, Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public FootageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new FootageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FootageViewHolder holder, int position) {

        List<String> tvShowKeys = new ArrayList<>(tvShows.keySet());
        List<String> movieKeys = new ArrayList<>(movies.keySet());

        if (position < tvShowKeys.size()) {// gets the tv shows first form the list
            TvShow tvShow = tvShows.get(tvShowKeys.get(position));

            int currentEpisode = tvShow.getCurrent_episode();
            int currentSeason = tvShow.getCurrent_season();

            // showing the tv show name and poster
            Log.d("ImageURLsasdsa", "URL: " + tvShow.getPoster_path());
            Glide.with(holder.itemView.getContext()).load(tvShow.getPoster_path()).placeholder(R.drawable.ic_launcher_background).centerInside().into(holder.item_picture);
            holder.item_LBL_name.setText(tvShow.getOriginal_name());


            if(!location) { // if the footage is shown on the user page



                HashMap <String,Episode> episodes = tvShow.getEpisodes();
                // gets the current episode and season number
                // if the show just got added then set the season and episode to be both 1 and not 0
                if(tvShow.getCurrent_episode() == 0 && tvShow.getCurrent_season() == 0) {
                    currentEpisode = 1 + tvShow.getCurrent_episode();
                    currentSeason = 1 + tvShow.getCurrent_season();
                }
                else{
                    currentEpisode = tvShow.getCurrent_episode();
                    currentSeason = tvShow.getCurrent_season();
                }
                final int[] finalCurrentEpisode = {currentEpisode};
                final int[] finalCurrentSeason = {currentSeason};
                // getting the episode by the way we saved him in the hashmap
                Episode episode = episodes.get(String.valueOf(currentSeason * 10000 + currentEpisode));

                // shows episode information
                holder.current_episode_name.setText(episode.getName());
                holder.current_episode_number.setText("episode: " + episode.getSeason_number() + "x" + episode.getEpisode_number());
                holder.item_release_Date.setText("release date: " +episode.getAir_date());
                holder.item_duration.setText("runtime: " +episode.getRuntime() + " minutes");
                holder.item_rating.setText("rating: " + String.valueOf(episode.getVote_average()));
                holder.item_summary.setText("overview: "+ episode.getOverview());
                holder.next_episode.setVisibility(View.VISIBLE);
                holder.next_episode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { // getting the next episode number
                        updateEpisode("next", finalCurrentEpisode, finalCurrentSeason);
                        if(finalCurrentSeason[0] > tvShow.getNumber_of_seasons()){
                            tvShow.setCurrent_episode(0);
                            tvShow.setCurrent_season(0);
                        }else{
                            tvShow.setCurrent_episode(finalCurrentEpisode[0]);
                            tvShow.setCurrent_season(finalCurrentSeason[0]);
                        }
                    }
                });
                holder.previous_episode.setVisibility(View.VISIBLE);
                holder.previous_episode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { // getting the previous episode number
                        if(finalCurrentEpisode[0] > 1 || finalCurrentEpisode[0] > 1){
                        updateEpisode("previous", finalCurrentEpisode, finalCurrentSeason);
                            tvShow.setCurrent_episode(finalCurrentEpisode[0]);
                            tvShow.setCurrent_season(finalCurrentSeason[0]);
                            }

                    }
                });

                // can press and check ro put in your list or not
                holder.add_item.setOnCheckedChangeListener(null);
                holder.add_item.setChecked(sharedViewModel.getSelectedTvShows().getValue().containsKey(tvShowKeys.get(position)));
                holder.add_item.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        // add the show to the user list if added
                        sharedViewModel.addTvShow(tvShowKeys.get(position), tvShow);
                        // remove the show from the user list if removed
                    } else {
                        sharedViewModel.removeTvShow(tvShowKeys.get(position));
                    }
                });



            }
            else {// if not in the user page and on the all footage then show the show details
                holder.next_episode.setVisibility(View.INVISIBLE);
                holder.previous_episode.setVisibility(View.INVISIBLE);
                holder.item_release_Date.setText(tvShow.getFirst_air_date());
                holder.item_duration.setText(tvShow.getDuration());
                holder.item_rating.setText(String.valueOf(tvShow.getVote_average()));
                holder.item_summary.setText(tvShow.getOverview());
                Log.d("TAG2", "saveToFirebase: check1");
                holder.add_item.setOnCheckedChangeListener(null);
                holder.add_item.setChecked(sharedViewModel.getSelectedTvShows().getValue().containsKey(tvShowKeys.get(position)));
                Log.d("TAG2", "saveToFirebase: check2");
                holder.add_item.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        // add the show to the user list if added
                        sharedViewModel.addTvShow(tvShowKeys.get(position), tvShow);
                        // remove the show from the user list if removed
                    } else {
                        sharedViewModel.removeTvShow(tvShowKeys.get(position));
                    }
                });
            }

        } else {
            // gets the movies form the list
            int moviePosition = position - tvShowKeys.size();

            // sets the movie details on the footage card
            Movie movie = movies.get(movieKeys.get(moviePosition));

            Glide.with(holder.itemView.getContext()).load(movie.getPoster_path()).placeholder(R.drawable.ic_launcher_background).centerInside().into(holder.item_picture);
            holder.item_LBL_name.setText(movie.getTitle());
            holder.item_release_Date.setText(movie.getRelease_date());
            holder.item_duration.setText(movie.getRuntime());
            holder.item_rating.setText(String.valueOf(movie.getVote_average()));
            holder.previous_episode.setVisibility(View.INVISIBLE);
            holder.next_episode.setVisibility(View.INVISIBLE);
            holder.item_summary.setText(movie.getOverview());
            holder.add_item.setOnCheckedChangeListener(null);
            holder.add_item.setChecked(sharedViewModel.getSelectedMovies().getValue().containsKey(movieKeys.get(moviePosition)));
            holder.add_item.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // add the movie to the user list if added
                    sharedViewModel.addMovie(movieKeys.get(moviePosition), movie);
                } else {
                    // remove the show from the user list if removed
                    sharedViewModel.removeMovie(movieKeys.get(moviePosition));
                }
            });
        }




    }


    // calculate what is supposed to be the next episode
    private void updateEpisode(String type, int[] finalCurrentEpisode, int[] finalCurrentSeason) {
        if(Objects.equals(type, "next")) {
            if(finalCurrentEpisode[0] >= 10){
                finalCurrentSeason[0]++;
                finalCurrentEpisode[0] = 1;
            }
            else{
                finalCurrentEpisode[0]++;
            }
        }
        else{
            if(finalCurrentEpisode[0] <= 1){
                finalCurrentSeason[0]--;
                finalCurrentEpisode[0] = 10;
            }
            else{
                finalCurrentEpisode[0]--;
            }
        }

    }


    @Override
    public int getItemCount() {
        int tvShowsCount = (tvShows != null) ? tvShows.size() : 0;
        int moviesCount = (movies != null) ? movies.size() : 0;

        return tvShowsCount + moviesCount;
    }



    public class FootageViewHolder extends RecyclerView.ViewHolder {

        private final ShapeableImageView item_picture;
        private final MaterialTextView item_LBL_name;
        private final MaterialTextView current_episode_name;
        private final MaterialTextView current_episode_number;
        private final MaterialTextView item_release_Date;
        private final MaterialTextView item_duration;
        private final MaterialTextView item_rating;
        private final MaterialTextView item_summary;
        private final CheckBox add_item;
        private final FloatingActionButton previous_episode;
        private final FloatingActionButton next_episode;


        public FootageViewHolder(@NonNull View itemView) {
            super(itemView);
            item_picture = itemView.findViewById(R.id.item_picture);
            item_LBL_name = itemView.findViewById(R.id.item_LBL_name);
            current_episode_name = itemView.findViewById(R.id.current_episode_name);
            current_episode_number = itemView.findViewById(R.id.current_episode_number);
            item_release_Date = itemView.findViewById(R.id.item_release_Date);
            item_duration = itemView.findViewById(R.id.item_duration);
            item_rating = itemView.findViewById(R.id.item_rating);
            item_summary = itemView.findViewById(R.id.item_summary);
            add_item = itemView.findViewById(R.id.add_item);
            previous_episode = itemView.findViewById(R.id.previous_episode);
            next_episode = itemView.findViewById(R.id.next_episode);

        }
    }

}

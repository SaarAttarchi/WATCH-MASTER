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
    private int location;


    public FootageCard(HashMap<String,TvShow> tvShows, HashMap<String, Movie> movies, SharedViewModel sharedViewModel, int location) {
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

        if (position < tvShowKeys.size()) {
            TvShow tvShow = tvShows.get(tvShowKeys.get(position));

            int currentEpisode = tvShow.getCurrent_episode();
            int currentSeason = tvShow.getCurrent_season();

            Log.d("ImageURLsasdsa", "URL: " + tvShow.getPoster_path());
            Glide.with(holder.itemView.getContext()).load(tvShow.getPoster_path()).placeholder(R.drawable.ic_launcher_background).centerInside().into(holder.item_picture);
            holder.item_LBL_name.setText(tvShow.getOriginal_name());
            Log.d("TAG111", "Shows onResponse: " + tvShow.getOriginal_name());
            Log.d("TAG222", "Shows onResponse: " + tvShow.getNumber_of_seasons());
            Log.d("TAG222", "Shows onResponse: " + tvShow.getNumber_of_episodes());




            if(location == 1) {

                episodeCard(tvShow, holder,currentEpisode,currentSeason, new EpisodeUpdateCallback() {
                    @Override
                    public void onEpisodeUpdate() {

                    }
                });

                HashMap <String,Episode> episodes = tvShow.getEpisodes();

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
                Episode episode = episodes.get(String.valueOf(currentSeason * 10000 + currentEpisode));


                holder.current_episode_name.setText(episode.getName());
                holder.current_episode_number.setText(episode.getSeason_number() + "x" + episode.getEpisode_number());
                holder.item_release_Date.setText(episode.getAir_date());
                holder.item_duration.setText(episode.getRuntime());
                holder.item_rating.setText(String.valueOf(episode.getVote_average()));
                holder.item_summary.setText(episode.getOverview());
                holder.next_episode.setVisibility(View.VISIBLE);

                holder.next_episode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                    public void onClick(View v) {
                        if(finalCurrentEpisode[0] > 1 || finalCurrentEpisode[0] > 1){
                        updateEpisode("previous", finalCurrentEpisode, finalCurrentSeason);
                            tvShow.setCurrent_episode(finalCurrentEpisode[0]);
                            tvShow.setCurrent_season(finalCurrentSeason[0]);
                            }

                    }
                });







                Log.d("TAG2", "saveToFirebase: check1");
                holder.add_item.setOnCheckedChangeListener(null);
                holder.add_item.setChecked(sharedViewModel.getSelectedTvShows().getValue().containsKey(tvShowKeys.get(position)));
                Log.d("TAG2", "saveToFirebase: check2");
                holder.add_item.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        Log.d("TAG2", "saveToFirebase: check3");
                        sharedViewModel.addTvShow(tvShowKeys.get(position), tvShow);
                        Log.d("TAG2", "saveToFirebase: check4");
                    } else {
                        sharedViewModel.removeTvShow(tvShowKeys.get(position));
                    }
                });



            }
            else {

                holder.item_release_Date.setText(tvShow.getFirst_air_date());
                holder.item_duration.setText(tvShow.getDuration());
                holder.item_rating.setText(String.valueOf(tvShow.getVote_average()));
//                holder.item_genres.setText(String.join(", ", tvShow.getGenre_ids()));
                holder.item_summary.setText(tvShow.getOverview());
                Log.d("TAG2", "saveToFirebase: check1");
                holder.add_item.setOnCheckedChangeListener(null);
                holder.add_item.setChecked(sharedViewModel.getSelectedTvShows().getValue().containsKey(tvShowKeys.get(position)));
                Log.d("TAG2", "saveToFirebase: check2");
                holder.add_item.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        Log.d("TAG2", "saveToFirebase: check3");
                        sharedViewModel.addTvShow(tvShowKeys.get(position), tvShow);
                        Log.d("TAG2", "saveToFirebase: check4");
                    } else {
                        sharedViewModel.removeTvShow(tvShowKeys.get(position));
                    }
                });
            }

        } else {
            int moviePosition = position - tvShowKeys.size();
            Movie movie = movies.get(movieKeys.get(moviePosition));
            Glide.with(holder.itemView.getContext()).load(movie.getPoster_path()).placeholder(R.drawable.ic_launcher_background).centerInside().into(holder.item_picture);
            holder.item_LBL_name.setText(movie.getTitle());
            holder.item_release_Date.setText(movie.getRelease_date());
            holder.item_duration.setText(movie.getRuntime());
            holder.item_rating.setText(String.valueOf(movie.getVote_average()));
//            holder.item_genres.setText(String.join(", ", movie.getGenre_ids()));
            holder.previous_episode.setVisibility(View.INVISIBLE);
            holder.next_episode.setVisibility(View.INVISIBLE);
            holder.item_summary.setText(movie.getOverview());
            holder.add_item.setOnCheckedChangeListener(null);
            holder.add_item.setChecked(sharedViewModel.getSelectedMovies().getValue().containsKey(movieKeys.get(moviePosition)));
            holder.add_item.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    sharedViewModel.addMovie(movieKeys.get(moviePosition), movie);
                } else {
                    sharedViewModel.removeMovie(movieKeys.get(moviePosition));
                }
            });
        }




    }

    private void episodeCard(TvShow tvShow, @NonNull FootageViewHolder holder,int currentEpisode,int currentSeason, EpisodeUpdateCallback episodeUpdateCallback){


    }

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
        private final MaterialButton previous_episode;
        private final MaterialButton next_episode;


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

    public Episode getWantedEpisode(HashMap<String, Episode> episodes, int wantedIndex) {
        int index = 0;
        for (Map.Entry<String, Episode> entry : episodes.entrySet()) {
            if (index == wantedIndex) { // Index 2 corresponds to the third element
                return entry.getValue(); // Return the third episode
            }
            index++;
        }
        return null; // Return null if there is no third episode
    }
}

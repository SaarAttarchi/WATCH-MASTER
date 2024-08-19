package com.example.watch_master;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.watch_master.DataManager.DataManager;
import com.example.watch_master.models.Episode;
import com.example.watch_master.models.Movie;
import com.example.watch_master.models.TvShow;
import com.example.watch_master.ui.home.HomeFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


            Log.d("ImageURLsasdsa", "URL: " + tvShow.getPoster_path());
            Glide.with(holder.itemView.getContext()).load(tvShow.getPoster_path()).placeholder(R.drawable.ic_launcher_background).centerInside().into(holder.item_picture);
            holder.item_LBL_name.setText(tvShow.getOriginal_name());
            Log.d("TAG111", "Shows onResponse: " + tvShow.getOriginal_name());



            if(location == 1) {

                HashMap <String,Episode> episodes = tvShow.getEpisodes();

                Episode episode = getWantedEpisode(episodes, 0);

                holder.current_episode_name.setText(episode.getName());
                holder.current_episode_number.setText(episode.getSeason_number() + "x" + episode.getEpisode_number());
                holder.item_release_Date.setText(episode.getAir_date());
                holder.item_duration.setText(episode.getRuntime());
                holder.item_rating.setText(String.valueOf(episode.getVote_average()));
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
            else {

                Log.d("TAG2", "saveToFirebase: sasasa");
                //Log.d("TAG", String.valueOf(tvShow));




                holder.item_release_Date.setText(tvShow.getFirst_air_date());
//                Log.d("TAG2", "saveToFirebase: sasasa");
//                Log.d("TAG", "Shows onResponse: " + tvShow.getDuration());
//                Log.d("TAG", "Shows onResponse: " + tvShow.getName());
                holder.item_duration.setText(tvShow.getDuration());
                holder.item_rating.setText(String.valueOf(tvShow.getVote_average()));
//                holder.item_genres.setText(String.join(", ", tvShow.getGenre_ids()));
                holder.item_summary.setText(tvShow.getOverview());
                holder.previous_episode.setVisibility(View.VISIBLE);
                holder.next_episode.setVisibility(View.VISIBLE);
                holder.add_item.setOnCheckedChangeListener(null);
                holder.add_item.setChecked(sharedViewModel.getSelectedTvShows().getValue().containsKey(tvShowKeys.get(position)));
                holder.add_item.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        sharedViewModel.addTvShow(tvShowKeys.get(position), tvShow);
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
        private final MaterialTextView item_genres;
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
            item_genres = itemView.findViewById(R.id.item_genres);
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

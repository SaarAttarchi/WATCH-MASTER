package com.example.watch_master.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watch_master.FootageCard;
import com.example.watch_master.R;
import com.example.watch_master.SharedViewModel;
import com.example.watch_master.databinding.FragmentHomeBinding;
import com.example.watch_master.models.Movie;
import com.example.watch_master.models.TvShow;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private SharedViewModel sharedViewModel;
    private RecyclerView main_LST_items;
    private TextView listText;
    private FootageCard footageCard;
    private boolean isTvShowsLoaded = false;
    private boolean isMoviesLoaded = false;



    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        //text_home = text_home.findViewById(R.id.text_home);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        main_LST_items = binding.mainLSTItems;
        main_LST_items.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set up the adapter with an empty list initially
        footageCard = new FootageCard(new HashMap<>(), new HashMap<>(), sharedViewModel, 1);

        main_LST_items.setAdapter(footageCard);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
            loadFirebaseData(user.getUid());


        //if(isTvShowsLoaded = false || isMoviesLoaded == false)
            //listText.setText("your watchlist is empty");
        checkDataLoaded();





        return root;
    }

    private void loadFirebaseData(String userId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("checked_footage").child(userId);

        // Load TV shows
        myRef.child("tv_shows").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, TvShow> tvShows = new HashMap<>();
                for (DataSnapshot tvShowSnapshot : snapshot.getChildren()) {
                    TvShow tvShow = tvShowSnapshot.getValue(TvShow.class);
                    if (tvShow != null) {
                        tvShows.put(tvShowSnapshot.getKey(), tvShow);
                    }
                }
                sharedViewModel.setSelectedTvShows(tvShows);
                isTvShowsLoaded = true;
                checkDataLoaded();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "Failed to load TV shows from Firebase: " + error.getMessage());
            }
        });

        // Load Movies
        myRef.child("movies").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Movie> movies = new HashMap<>();
                for (DataSnapshot movieSnapshot : snapshot.getChildren()) {
                    Movie movie = movieSnapshot.getValue(Movie.class);
                    if (movie != null) {
                        movies.put(movieSnapshot.getKey(), movie);
                    }
                }
                sharedViewModel.setSelectedMovies(movies);
                isMoviesLoaded = true;
                checkDataLoaded();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "Failed to load movies from Firebase: " + error.getMessage());
            }
        });
    }

    private void checkDataLoaded() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (isTvShowsLoaded && isMoviesLoaded) {
            // Observe changes to TV shows
            sharedViewModel.getSelectedTvShows().observe(getViewLifecycleOwner(), selectedTvShows -> {
                footageCard.setTvShows(selectedTvShows);
                footageCard.notifyDataSetChanged();
                FirebaseDatabase.getInstance().getReference("checked_footage").child(user.getUid()).child("tv_shows").setValue(selectedTvShows);
            });

            // Observe changes to Movies
            sharedViewModel.getSelectedMovies().observe(getViewLifecycleOwner(), selectedMovies -> {
                footageCard.setMovies(selectedMovies);
                footageCard.notifyDataSetChanged();
                FirebaseDatabase.getInstance().getReference("checked_footage").child(user.getUid()).child("movies").setValue(selectedMovies);
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
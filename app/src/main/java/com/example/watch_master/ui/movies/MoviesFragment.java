package com.example.watch_master.ui.movies;

import android.os.Bundle;
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
import com.example.watch_master.SharedViewModel;
import com.example.watch_master.databinding.FragmentHomeBinding;
import com.example.watch_master.databinding.FragmentMoviesBinding;

import java.util.HashMap;


public class MoviesFragment extends Fragment {

    private FragmentMoviesBinding binding;
    private SharedViewModel sharedViewModel;
    private RecyclerView main_LST_items;
    private FootageCard footageCard;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MoviesViewModel slideshowViewModel =
                new ViewModelProvider(this).get(MoviesViewModel.class);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        binding = FragmentMoviesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        main_LST_items = binding.mainLSTItems;
        main_LST_items.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set up the adapter with an empty list initially
        footageCard = new FootageCard(new HashMap<>(), sharedViewModel.getSelectedMovies().getValue(), sharedViewModel, 1); // Implement TvShowAdapter

        main_LST_items.setAdapter(footageCard);


        sharedViewModel.getSelectedMovies().observe(getViewLifecycleOwner(), selectedMovies  -> {
            // Update the RecyclerView when the data changes
            footageCard.setMovies(selectedMovies);
            footageCard.notifyDataSetChanged();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
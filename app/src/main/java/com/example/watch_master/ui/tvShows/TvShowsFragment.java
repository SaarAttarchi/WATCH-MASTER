package com.example.watch_master.ui.tvShows;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watch_master.FootageCard;
import com.example.watch_master.SharedViewModel;
import com.example.watch_master.databinding.FragmentMoviesBinding;

import java.util.HashMap;

public class TvShowsFragment extends Fragment {

    private FragmentMoviesBinding binding;
    private SharedViewModel sharedViewModel;
    private RecyclerView main_LST_items;
    private FootageCard footageCard;
    private boolean isInAllFootage = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TvShowsViewModel galleryViewModel =
                new ViewModelProvider(this).get(TvShowsViewModel.class);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        binding = FragmentMoviesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        main_LST_items = binding.mainLSTItems;
        main_LST_items.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set up the adapter with an empty list initially
        footageCard = new FootageCard(sharedViewModel.getSelectedTvShows().getValue(), new HashMap<>(), sharedViewModel, isInAllFootage); // Implement TvShowAdapter

        main_LST_items.setAdapter(footageCard);

        // sets the UI so it will show only the tv shows
        sharedViewModel.getSelectedTvShows().observe(getViewLifecycleOwner(), selectedTvShows  -> {
            // Update the RecyclerView when the data changes
            footageCard.setTvShows(selectedTvShows);
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
package com.example.watch_master;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.watch_master.DataManager.DataManager;
import com.example.watch_master.interfaces.DataFetchCallback;
import com.example.watch_master.models.Movie;
import com.example.watch_master.models.TvShow;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllFootageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllFootageFragment extends Fragment {
/*
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters



 */

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView main_LST_items;
    private SharedViewModel sharedViewModel;


    public AllFootageFragment() {
        // Required empty public constructor
    }

    public static AllFootageFragment newInstance(String param1, String param2) {
        AllFootageFragment fragment = new AllFootageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_footage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("TAG", "saveToFirebase: sasasa");
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        Log.d("TAG2", "saveToFirebase: sasasa");

        HashMap<String, TvShow> initialTvShows = new HashMap<>();
        HashMap<String, Movie> initialMovies = new HashMap<>();
        FootageCard footageCard = new FootageCard(initialTvShows, initialMovies, sharedViewModel, 0);

        //FootageCard footageCard = new FootageCard(DataManager.footageLibrary().getTvShows(), DataManager.footageLibrary().getMovies(), sharedViewModel);

        Log.d("TAG3", "saveToFirebase: sasasa");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        main_LST_items = view.findViewById(R.id.main_LST_items);
        Log.d("TAG4", "saveToFirebase: sasasa");
        main_LST_items.setLayoutManager(linearLayoutManager);

        main_LST_items.setAdapter(footageCard);

        DataManager dataFetcher = new DataManager();
        dataFetcher.allDta(new DataFetchCallback() {
            @Override
            public void onDataFetched(HashMap<String, TvShow> tvShows, HashMap<String, Movie> movies) {
                footageCard.setTvShows(tvShows);
                footageCard.setMovies(movies);
                footageCard.notifyDataSetChanged();
            }


        });


    }

    }
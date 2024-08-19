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
import android.widget.Button;

import com.example.watch_master.DataManager.DataManager;
import com.example.watch_master.interfaces.DataFetchCallback;
import com.example.watch_master.models.Movie;
import com.example.watch_master.models.TvShow;
import com.google.android.material.textfield.TextInputEditText;

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
    private boolean isInAllFootage = true;
    private String query;
    private TextInputEditText searchBar;
    private Button searchButton;
    private boolean search = false;


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



        // creates a new sharedViewModel instance
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // creates empty hashmaps for each type and put in the FootageCard constructor
        HashMap<String, TvShow> initialTvShows = new HashMap<>();
        HashMap<String, Movie> initialMovies = new HashMap<>();
        FootageCard footageCard = new FootageCard(initialTvShows, initialMovies, sharedViewModel,isInAllFootage);

        // find variables in the fragment
        main_LST_items = view.findViewById(R.id.main_LST_items);
        searchBar = view.findViewById(R.id.search_bar);
        searchButton = view.findViewById(R.id.search_button);

        //sets the footageCard adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DataManager dataFetcher = new DataManager();

        main_LST_items.setLayoutManager(linearLayoutManager);

        main_LST_items.setAdapter(footageCard);

        // get all the footage in the data base when not in search mode and show it
        if(!search) {
            dataFetcher.allDta(query, new DataFetchCallback() {
                @Override
                public void onDataFetched(HashMap<String, TvShow> tvShows, HashMap<String, Movie> movies) {
                    footageCard.setTvShows(tvShows); // sets the tv shows
                    footageCard.setMovies(movies); // sets the movies
                    footageCard.notifyDataSetChanged();
                }
            });
        }

        //gets the search bar input and use it as query
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = searchBar.getText().toString().trim();
                if (!query.isEmpty()) {
                    search = true;
                    dataFetcher.allDta(query, new DataFetchCallback() {
                        @Override
                        public void onDataFetched(HashMap<String, TvShow> tvShows, HashMap<String, Movie> movies) {
                            footageCard.setTvShows(tvShows);
                            footageCard.setMovies(movies);
                            footageCard.notifyDataSetChanged();
                        }


                    });
                }
            }
        });
//
//


    }

    }
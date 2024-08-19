package com.example.watch_master;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.watch_master.DataManager.DataManager;
import com.example.watch_master.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watch_master.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private RecyclerView main_LST_items;
    private FloatingActionButton add_new_item;
    private FloatingActionButton sign_in_button;
    private FloatingActionButton sign_out_button;
    private TextView userName;
    private TextView userMail;
    private TextView listText;
    private boolean isSignedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_tv_shows, R.id.nav_movies, R.id.all_footage, R.id.sign_in)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        findViews();
        initViews();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    private void findViews() {
        main_LST_items = findViewById(R.id.main_LST_items);
        userName = binding.navView.getHeaderView(0).findViewById(R.id.userName);
        userMail = binding.navView.getHeaderView(0).findViewById(R.id.userMail);
        sign_in_button = binding.navView.getHeaderView(0).findViewById(R.id.sign_in_button);
        sign_out_button = binding.navView.getHeaderView(0).findViewById(R.id.sign_out_button);
        add_new_item = findViewById(R.id.add_new_item);
        listText = findViewById(R.id.listText);


    }

    private void initViews() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) {
            isSignedIn = false;

            sign_in_button.setVisibility(View.VISIBLE);
            sign_out_button.setVisibility(View.INVISIBLE);

            Intent signInIntent = new Intent(this, SignInActivity.class);
            sign_in_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_content_main);
                    //navController.navigate(R.id.sign_in);

                    startActivity(signInIntent);
                }
            });
        }


        else {
            isSignedIn = true;


            sign_in_button.setVisibility(View.INVISIBLE);
            sign_out_button.setVisibility(View.VISIBLE);

            HomeFragment home = new HomeFragment();

            if(home.isMoviesLoaded || home.isTvShowsLoaded)
                listText.setVisibility(View.INVISIBLE);
            else
                listText.setVisibility(View.VISIBLE);



            Intent signInIntent = new Intent(this, SignInActivity.class);
            sign_out_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_content_main);
                    //navController.navigate(R.id.sign_in);

                    startActivity(signInIntent);
                }
            });

            if (user.getDisplayName() == null) {
                userName.setText(user.getPhoneNumber());
            } else {
                userName.setText(user.getDisplayName());
            }

            userMail.setText(user.getEmail());
        }



        binding.appBarMain.addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user == null){
                    Toast.makeText(getBaseContext(), "please sign in first", Toast.LENGTH_LONG).show();
                }
                else {
                    NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_content_main);
                    navController.navigate(R.id.all_footage);
                }
            }
        });








        /*
        FootageCard footageCard = new FootageCard(DataManager.getTvShows());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        main_LST_items.setLayoutManager(linearLayoutManager);
        main_LST_items.setAdapter(footageCard);


         */

        //add_new_item.setOnClickListener(v -> saveToFirebase());





    }


/*

    private void saveToFirebase(){
        // Write a message to the database

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("footage");

        myRef.setValue(DataManager.footageLibrary());
        Log.d("TAG", "saveToFirebase: sasasa");
    }

 */



}
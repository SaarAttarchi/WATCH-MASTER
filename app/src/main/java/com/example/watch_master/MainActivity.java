package com.example.watch_master;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
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
import com.google.android.material.textfield.TextInputEditText;
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
                R.id.nav_home, R.id.nav_tv_shows, R.id.nav_movies, R.id.all_footage)
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


    }

    private void initViews() {

        // gets the user information
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) {// if not connected
            // show the button to sign
            sign_in_button.setVisibility(View.VISIBLE);
            sign_out_button.setVisibility(View.INVISIBLE);
            // goes to the sign activity
            Intent signInIntent = new Intent(this, SignInActivity.class);
            sign_in_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(signInIntent);
                }
            });
        }
        else {// if connected
            // show the sign out botton
            sign_in_button.setVisibility(View.INVISIBLE);
            sign_out_button.setVisibility(View.VISIBLE);




            // goes to sign in activity to leave
            Intent signInIntent = new Intent(this, SignInActivity.class);
            sign_out_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(signInIntent);
                }
            });

            // shows user information
            if (user.getPhoneNumber() != null) {
                userName.setText(user.getPhoneNumber());
            } else {
                userName.setText(user.getDisplayName());
                userMail.setText(user.getEmail());
            }
        }


        // only when user sign in can show the all footage
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


    }


}
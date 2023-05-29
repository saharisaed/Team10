package com.example.myapplication00;

import android.os.Bundle;

import com.example.myapplication00.ByElderly.dialogeMessage;
import com.example.myapplication00.logic_model.UsersDataBaseManager;
import com.example.myapplication00.logic_model.UsersSQLiteDB;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication00.databinding.ActivityMainBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;


    public void onStart(){
        super.onStart();
        FirebaseApp.initializeApp(this);
    }

    public void onDestroy(){
        super.onDestroy();
        UsersDataBaseManager.getInstance().closeDataBase();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.changePassword){
            Toast.makeText(this,"changePassword", Toast.LENGTH_SHORT).show();
            ChangePassword d=new ChangePassword();
            d.show(getSupportFragmentManager(), "ChangePassword");
        }


        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        //open the database and get the data from the firebase
        UsersDataBaseManager.getInstance().openDataBase(this);
        Log.d("Welcome dp", "Successfully opened the dp");
        //UsersDataBaseManager.getInstance().removeAllUsers();
        UsersDataBaseManager.getInstance().getData();
        Log.d("Welcome firebase", "Successfully getting the data from the firebase");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
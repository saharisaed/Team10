package com.example.myapplication00;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication00.ByAdmin.Admin1;
import com.example.myapplication00.ByAdmin.AdminAddUser;
import com.example.myapplication00.ByAdmin.AdminEditUser;
import com.example.myapplication00.ByAdmin.DeleteUser;
import com.example.myapplication00.ByAdmin.EditExtraData;
import com.example.myapplication00.ByAdmin.EditExtraData2;
import com.example.myapplication00.ByAdmin.EditSpecUser;
import com.example.myapplication00.ByCaretaker.Caretakers;
import com.example.myapplication00.ByCaretaker.ChickElderly;
import com.example.myapplication00.ByElderly.Elderly;
import com.example.myapplication00.ByElderly.ElderlyHealthService;
import com.example.myapplication00.ByElderly.ElderlyMonitor;
import com.example.myapplication00.ByElderly.ElderlyOrder;
import com.example.myapplication00.ByManager.Manager;
import com.example.myapplication00.databinding.ActivityMainBinding;
import com.example.myapplication00.logic_model.UsersDataBaseManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//The main activity that include every fragment we entered
public class MainPageForUser extends AppCompatActivity {


    private AppBarConfiguration appBarConfiguration;
    FirebaseUser user;
    String getuserId;
    Toolbar toolbar;
    private ActivityMainBinding binding;
    static ConstraintLayout layoutMe;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        //hide the items for some users
        MenuItem itemSetting=menu.findItem(R.id.details);
        MenuItem item=menu.findItem(R.id.setting);
        if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admin@gmail.com")){
            item.setVisible(false);
        }
        else if(!UsersDataBaseManager.getInstance().getSelectedUser().getUserType().equals("caretaker")){
            itemSetting.setVisible(false);
        }




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.logout){
            signOut(this);
        }
        if(id==R.id.setting){
            dialoge_notification d=new dialoge_notification();
            d.show(getSupportFragmentManager(), "dialoge_notification");
        }
        if(id==R.id.details){
            ShowCaretakerDetails fragment = new ShowCaretakerDetails();
            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment).commit();

        }

        return true;
    }

    //To sign Out
    public void signOut(Context context){
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setMessage("Do you want to sign out?");
        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ConstraintLayout layoutMe;
                layoutMe=findViewById(R.id.layoutMe);
                Snackbar.make(layoutMe,"Sign Out",Snackbar.LENGTH_LONG)
                        .setAction("Close", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                Intent intent6 = new Intent(context, MainActivity.class);
                FirebaseAuth.getInstance().signOut();
                context.startActivity(intent6);
            }
        }).setNeutralButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertdialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_for_user);


        layoutMe=findViewById(R.id.layoutMe);
        toolbar=findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        layoutMe=findViewById(R.id.layoutMe);

        invalidateOptionsMenu();

        String getInput = getIntent().getStringExtra("userType");
        getuserId = getIntent().getStringExtra("userId");
        //calling the method to set the fragment to the specific user
        if(!getInput.equals(null))
            setFragment(getInput);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }



    //setting the fragment by the user type
    public void setFragment(String userType){

        if( userType.equals("manager")){//for managers
            //setting the caretaker fragment
            Manager manager= new Manager();
            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout ,manager).commit();

        }

        if( userType.equals("elderly Person")){//for elderly people
            //setting elderly Person fragment
            Elderly elderly = new Elderly();
            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout ,elderly).commit();


        }

        if( userType.equals("caretaker")){//for caretakers
            //setting the fragment to the caretaker
            Caretakers caretakers = new Caretakers();
            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout ,caretakers).commit();

        }if(userType.equals("admin")){ //for Admin
            //setting the fragment for the admin
            Admin1 admin1 = new Admin1();
            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout ,admin1).commit();
        }
        if (userType.equals("AdminAddUser")) {//for AdminAddUser Fragment
            //setting the AdminAddUser fragment
            AdminAddUser fragment = new AdminAddUser();
            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment).commit();

        }

        if (userType.equals("removeUser")) {//for DeleteUser Fragment
            //setting the DeleteUser fragment
            DeleteUser fragment = new DeleteUser();
            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment).commit();

        }

        if (userType.equals("AdminEditUser")) {//for AdminEditUser Fragment
            //setting the AdminEditUser fragment
            AdminEditUser fragment = new AdminEditUser();
            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment).commit();

        }

        if (userType.equals("healthServices")) {//for ElderlyHealthService Fragment
            //setting the ElderlyHealthService fragment
            ElderlyHealthService fragment = new ElderlyHealthService();
            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment).commit();

        }

        if (userType.equals("monitor")) {//for ElderlyMonitor Fragment
            //setting the ElderlyMonitor fragment
            ElderlyMonitor fragment = new ElderlyMonitor("yes");
            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment).commit();

        }

        if (userType.equals("order")) {//for ElderlyOrder Fragment
            //setting the ElderlyOrder fragment
            ElderlyOrder fragment = new ElderlyOrder();
            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment).commit();

        }

        if (userType.equals("EditSpecUser")) {//for EditSpecUser Fragment
            //setting the EditSpecUser fragment
            EditSpecUser fragment = new EditSpecUser();
            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment).commit();

        }
        if (userType.equals("EditExtraData")) {//for EditSpecUser Fragment
            //setting the EditExtraData fragment
            EditExtraData fragment = new EditExtraData();
            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment).commit();

        }

        if (userType.equals("EditExtraData2")) {//for EditSpecUser Fragment
            //setting the EditExtraData2 fragment
            EditExtraData2 fragment = new EditExtraData2();
            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment).commit();

        }
        if (userType.equals("ChickElderly")) {//for EditSpecUser Fragment
            //setting the CheckElderly fragment
            ChickElderly fragment = new ChickElderly();
            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment).commit();

        }

    }

}
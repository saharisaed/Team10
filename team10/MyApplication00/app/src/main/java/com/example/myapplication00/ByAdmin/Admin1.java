package com.example.myapplication00.ByAdmin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication00.MainPageForUser;
import com.example.myapplication00.R;



//The framgent that have three buttons for the admin activities
public class Admin1 extends Fragment {

    //each Button open a fregment
    Button addUser;
    Button deleteUser;
    Button editUser;
    //this is the main activity that gonna have the fragments on
    MainPageForUser main=new MainPageForUser();

    public Admin1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_admin1, container, false);

        //addUser is a button to open the adding user page
        addUser = view.findViewById(R.id.addUser);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(),MainPageForUser.class);
                in.putExtra("userType","AdminAddUser").toString();
                startActivity(in);
            }
        });


        //deleteUser is a button to open the delete user page
        deleteUser = view.findViewById(R.id.removeUser);
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(),MainPageForUser.class);
                in.putExtra("userType","removeUser").toString();
                startActivity(in);


            }
        });


        //editUser is a button to open the Edit user page
        editUser = view.findViewById(R.id.editUser);
        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(),MainPageForUser.class);
                in.putExtra("userType","AdminEditUser").toString();
                startActivity(in);


            }
        });




        return view;
    }
}
package com.example.myapplication00.ByElderly;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication00.Classes.User;
import com.example.myapplication00.MainPageForUser;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;


public class Elderly extends Fragment {

    Button healthServices;
    Button monitor;
    Button order1;

    public Elderly() {
        // Required empty public constructor
        Log.d("Im"," in the Elderly");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_elderly, container, false);

        //healthServices is a button to open the healthServices page
        healthServices = view.findViewById(R.id.healthServices);
        healthServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), MainPageForUser.class);
                in.putExtra("userType","healthServices").toString();
                startActivity(in);


            }
        });

        //monitor is a button to open the monitor page
        monitor = view.findViewById(R.id.monitor);
        monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(" monitor button","");
                User u= UsersDataBaseManager.getInstance().getSelectedUser();
                Intent in = new Intent(getActivity(),MainPageForUser.class);
                in.putExtra("userType","monitor").toString();
                startActivity(in);


            }
        });


        //order1 is a button to open the order page
        order1 = view.findViewById(R.id.order1);
        order1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(),MainPageForUser.class);
                in.putExtra("userType","order").toString();
                startActivity(in);


            }
        });

        return view;
    }
}
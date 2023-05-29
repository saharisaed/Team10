package com.example.myapplication00;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;


public class dialoge_notification extends DialogFragment {

    SwitchCompat switchCompat;

    public dialoge_notification() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dialoge_notification, container, false);
        switchCompat=view.findViewById(R.id.switch22);
        TextView text=view.findViewById(R.id.messagenoti);

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(switchCompat.isChecked()){
                    //change the data
                    text.setText("Turn the notification off");
                }else
                    text.setText(R.string.notiOn);






            }
        });

        return view;
    }
}
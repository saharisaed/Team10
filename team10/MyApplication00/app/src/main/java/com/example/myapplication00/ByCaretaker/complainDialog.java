package com.example.myapplication00.ByCaretaker;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication00.R;


public class complainDialog extends DialogFragment {

    Button send;
    public complainDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_complain_dialog, container, false);

        //get the button
        send=view.findViewById(R.id.sendcomplain);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "your complain was send", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        });
        return  view;
    }
}
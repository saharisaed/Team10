package com.example.myapplication00.ByCaretaker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication00.Classes.ElderlyClass;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;

public class ChickElderly extends Fragment {


    UsersDataBaseManager manager;
    String userId;
    ElderlyClass elderlyClass;

    public ChickElderly() {

    }

    public ChickElderly(String userId) {
        manager=UsersDataBaseManager.getInstance();
        this.userId=userId;
        //get the elderly to set his info
        for(ElderlyClass e:manager.getElderlies()){
            if(e.getId().equals(userId)){
                elderlyClass=e;
                break;
            }

        }


    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chick_elderly, container, false);
        //get the elderly to set his info

        return view;
    }
}
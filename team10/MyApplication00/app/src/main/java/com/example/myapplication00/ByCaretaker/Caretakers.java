package com.example.myapplication00.ByCaretaker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication00.ByElderly.orderingAdapter;
import com.example.myapplication00.Classes.ElderlyClass;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;

import java.util.List;


public class Caretakers extends Fragment {



    public Caretakers() {
        // Required empty public constructor
        Log.d("Im"," in the Caretaker");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_caretakers, container, false);
        //set the card view for the caretaker user
        //call the adapter to set the details for each elderly person
        //to change for later to recycler cardView
        RecyclerView recyclerView=view.findViewById(R.id.cardListCaretaker);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        caretaker_adapter ca = new caretaker_adapter(createList(), getContext());
        recyclerView.setAdapter(ca);




        return view;

    }

    //create elderly persons list for the card views
    //we will change the User data to elderly data
    private List<ElderlyClass> createList() {

        List<ElderlyClass> result = null;

        //Cursor res = myInfoDatabase.getUserdata();
        result= UsersDataBaseManager.getInstance().getElderlies();
        if( result.size()== 0){
            Toast.makeText(getContext(), "There is no users yet", Toast.LENGTH_SHORT).show();
        }

        return result;

    }
}
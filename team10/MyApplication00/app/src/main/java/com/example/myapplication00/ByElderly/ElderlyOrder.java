package com.example.myapplication00.ByElderly;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication00.Classes.Medicine_user;
import com.example.myapplication00.Classes.User;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ElderlyOrder extends Fragment {

    private List<Medicine_user> list=new ArrayList();
    UsersDataBaseManager UsersDataBaseManager;
    private orderingAdapter  ca;

    public ElderlyOrder() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_elderly_order, container, false);
        UsersDataBaseManager=UsersDataBaseManager.getInstance();
        final User user=UsersDataBaseManager.getSelectedUser();


        //getting the information from the fireBase that i trust
        Task<QuerySnapshot> querySnapshotTask =UsersDataBaseManager.df.collection("MedicansUser")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                //get the details for each document and check if it for our elderly person
                                Map<String,Object> mymap=document.getData();
                                Medicine_user m;
                                //getting the fields
                                String medicineName = (String) mymap.get("medicineName");
                                String userId = (String) mymap.get("userId");
                                String currentAmount= (String) mymap.get("currentAmount");
                                String atHour=(String) mymap.get("atHour");
                                String takeIt= (String)mymap.get("takeit");
                                //check if it for our elderly person
                                if(userId.equals(user.getId())){
                                    m=new Medicine_user(medicineName,userId,currentAmount,atHour,takeIt);
                                    list.add(m);
                                }

                            }
                            RecyclerView recyclerView=view.findViewById(R.id.orderingList);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            ca = new orderingAdapter(list, getContext());
                            recyclerView.setAdapter(ca);
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                } ) ;
        return view;
    }
}
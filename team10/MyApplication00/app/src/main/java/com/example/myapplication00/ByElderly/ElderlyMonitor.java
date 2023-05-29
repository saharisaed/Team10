package com.example.myapplication00.ByElderly;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication00.ByManager.Manager_WorkAdapter;
import com.example.myapplication00.Classes.Medicine_user;
import com.example.myapplication00.Classes.User;
import com.example.myapplication00.MainActivity;
import com.example.myapplication00.MainPageForUser;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ElderlyMonitor extends Fragment {

    private List<Medicine_user> list=new ArrayList();
    UsersDataBaseManager UsersDataBaseManager;
    private monitoringAdapter  ca;
    private String im;



    public ElderlyMonitor(String im) {
        Log.d("I am in","ElderlyMonitor");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_elderly_monitor, container, false);
//        listView =  view.findViewById(R.id.monitoringList);

        UsersDataBaseManager=UsersDataBaseManager.getInstance();
        final User user=UsersDataBaseManager.getSelectedUser();
        Log.w("hiii",user.getEmail()+" "+user.getId());
        if(user.getUserType().equals("caretaker")){
            im="no";
        }
        else
            im="yes";
        Log.w("im",im);


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
                                if(im.equals("yes")){
                                    if(userId.equals(user.getId())){
                                        m=new Medicine_user(medicineName,userId,currentAmount,atHour,takeIt);
                                        list.add(m);

                                }}else{
                                        if(userId.equals(UsersDataBaseManager.getUserIdSelected())){
                                            m=new Medicine_user(medicineName,userId,currentAmount,atHour,takeIt);
                                            list.add(m);
                                        }
                                    Log.w("hiii",UsersDataBaseManager.getUserIdSelected()+" "+user.getId());

                                }

                            }
                            RecyclerView recyclerView=view.findViewById(R.id.monitoringList);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            ca = new monitoringAdapter(list, getContext());
                            recyclerView.setAdapter(ca);
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                } ) ;

        return view;
    }
}
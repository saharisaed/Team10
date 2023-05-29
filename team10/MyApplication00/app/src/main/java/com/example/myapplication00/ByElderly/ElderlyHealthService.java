package com.example.myapplication00.ByElderly;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication00.Classes.HealthServices;
import com.example.myapplication00.R;

import java.util.ArrayList;
import java.util.List;

//Handel Health Services
public class ElderlyHealthService extends Fragment {


    private ListView listView;
    private EditText idinput;
    private ImageButton search;
    List<HealthServices> list= new ArrayList<>();
    List<HealthServices> healthServices;

    public ElderlyHealthService() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_elderly_health_service, container, false);
        listView =  view.findViewById(R.id.cardListhealth);

        //get the id's
        //getting the id and the button
        search = view.findViewById(R.id.searchHealth);
        idinput = view.findViewById(R.id.idSearchServices);


        //click on the button
        String input=idinput.getText().toString();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input;
                input=idinput.getText().toString();
                //check the validation
                if(input.equals("")){
                    //return all the numbers
                    list=getTheList();
                    healthServicesAdapter ca = new healthServicesAdapter(getContext(),R.layout.card_view_health_services, list);
                    listView.setAdapter(ca);
                }

                else{
                    //get the specific phone number
                    HealthServices h=null;
                    for(HealthServices healthServices:getTheList())
                        if(healthServices.getName().equals(input)){
                            h=healthServices;
                        }
                    if(h!=null){
                        list.clear();
                        list.add(h);
                        healthServicesAdapter ca = new healthServicesAdapter(getContext(),R.layout.card_view_health_services, list);
                        listView.setAdapter(ca);
                    }
                    else{
                        Toast.makeText(getContext(), "There is Health Service with the insert name ُ", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

        //return all the numbers
        if(input.equals("")){
            //return all the numbers
            list=getTheList();
            healthServicesAdapter ca = new healthServicesAdapter(getContext(),R.layout.card_view_health_services, list);
            listView.setAdapter(ca);
        }


        return view;
    }


    //build the hashMap of the health services
    public List<HealthServices> getTheList(){

        healthServices=new ArrayList<>();
        healthServices.add(new HealthServices("Sheba Medical Center","03-530-3030"));
        healthServices.add(new HealthServices("The Israel Medical Association","03-610-0444"));
        healthServices.add(new HealthServices("Maccabi Healthcare Services","03-512-2122"));
        healthServices.add(new HealthServices("Ministry of Health","08-624-1010"));
        healthServices.add(new HealthServices("Yossi Ambulance","03-770-0731"));
        healthServices.add(new HealthServices("Blade","052-632-7979"));
        healthServices.add(new HealthServices("Balance Center for Integrative Health & Wellness","058-567-6758"));
        healthServices.add(new HealthServices("dialogue","052-348-3853"));

        return healthServices;

    }
}
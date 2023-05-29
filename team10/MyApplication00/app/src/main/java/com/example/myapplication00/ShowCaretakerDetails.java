package com.example.myapplication00;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication00.ByCaretaker.complainDialog;
import com.example.myapplication00.ByElderly.dialogeMessage;
import com.example.myapplication00.Classes.Caretaker;
import com.example.myapplication00.Classes.HolidayUser;
import com.example.myapplication00.Classes.PaymentPerUser;
import com.example.myapplication00.Classes.User;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//extra data for caretakers
public class ShowCaretakerDetails extends Fragment {

    UsersDataBaseManager UsersDataBaseManager;
    Button save;
    TextView name,month;
    TextView date1,date2,date3,date4,price;
    int i;
    Caretaker caretaker=null;

    User user;
    DatePickerDialog dataPickerDialog;


    public ShowCaretakerDetails() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_show_caretaker_details, container, false);

        //getting the selected user from the list
        UsersDataBaseManager=UsersDataBaseManager.getInstance();
        user =UsersDataBaseManager.getSelectedUser();
        for(Caretaker c: UsersDataBaseManager.getCaretakers()){
            if(user.getEmail().equals(c.getEmail())) {
                caretaker=c;
                Log.d("found caretaker",user.getEmail());
            }
        }


        //getting the id
        name=view.findViewById(R.id.nameofCaretakerD);
        month=view.findViewById(R.id.numofmonthPa);
        date1=view.findViewById(R.id.date1);
        date2=view.findViewById(R.id.date2);
        date3=view.findViewById(R.id.date3);
        date4=view.findViewById(R.id.date4);
        price=view.findViewById(R.id.setprice);

        price.setEnabled(false);


        //btn
        save = view.findViewById(R.id.btnForDe);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open dialoge for message
                complainDialog dialog=new complainDialog();
                dialog.show(getChildFragmentManager(), "complainDialog");
            }
        });


        //set the current details
        if (user!=null){
            String theName=caretaker.getFirstName()+" "+caretaker.getLastName().toString();
            name.setText(theName);
            Calendar c=Calendar.getInstance();
            String num= String.valueOf(c.get(Calendar.MONTH)+1);
            month.setText(num);
            int i=1;
            for(String d:caretaker.getHolidays()){
                if(i==1){
                    date1.setText(d);
                }
                if(i==2){
                    date2.setText(d);
                }
                if(i==3){
                    date3.setText(d);
                }if(i==4){
                    date4.setText(d);
                }
                i++;
            }
            String theprice=caretaker.getPayment_per_month().get(String.valueOf(c.get(Calendar.MONTH)+1));
            if(theprice==null){
                theprice= String.valueOf(100*28);
            }
            price.setText(theprice);

        }




        return view;
    }



    private String getMonthFormat(int month) {
        Calendar cal=Calendar.getInstance();
        int month1=cal.get(Calendar.MONTH);
        return String.valueOf(month1+1);
    }



}
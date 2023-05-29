package com.example.myapplication00.ByManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication00.Classes.Caretaker;
import com.example.myapplication00.Classes.HolidayUser;
import com.example.myapplication00.Classes.User;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//DialogFragment that appear when the manager click on setHolidays for caretaker
public class ManagerSetHolidays extends DialogFragment {

    TextView name,date1,date2,date3,date4;
    ImageButton btn1,btn2,btn3,btn4;
    Button save,cancel;
    DatePickerDialog dataPickerDialog;
    Caretaker caretaker;
    int i;



    public ManagerSetHolidays() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_manager_set_holidays, container, false);


        initDatePicker();
        final User user = UsersDataBaseManager.getInstance().getSelectedUser();
        //getting the specefic user
        for (Caretaker selected:UsersDataBaseManager.getInstance().getCaretakers()){
            if(user.getEmail().equals(selected.getEmail())){
                caretaker=selected;
            }
        }

        //get the id's
        name=view.findViewById(R.id.caretakerNamePay);
        date1=view.findViewById(R.id.date1M);
        date2=view.findViewById(R.id.date2M);
        date3=view.findViewById(R.id.date3M);
        date4=view.findViewById(R.id.date4M);
        btn1=view.findViewById(R.id.cal1M);
        btn2=view.findViewById(R.id.cal2M);
        btn3=view.findViewById(R.id.cal3M);
        btn4=view.findViewById(R.id.cal4M);
        save=view.findViewById(R.id.saveForHoli);
        cancel=view.findViewById(R.id.cancelForHoli);


        //set the current details
        if (user!=null){
            String theName=caretaker.getFirstName()+" "+caretaker.getLastName();
            name.setText(theName);
            Calendar c=Calendar.getInstance();
            String num= String.valueOf(c.get(Calendar.MONTH)+1);
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

        }

        //By clicking on the calenders
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=1;
                dataPickerDialog.show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=2;
                dataPickerDialog.show();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=3;
                dataPickerDialog.show();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=4;
                dataPickerDialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alritMess();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;

    }


    //for setting tha date selected
    private void initDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date=dayOfMonth+"."+String.valueOf(month+1)+"."+year;
                if(i==1)
                    date1.setText(date);
                if(i==2)
                    date2.setText(date);
                if(i==3)
                    date3.setText(date);
                if(i==4)
                    date4.setText(date);
            }


        };

        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);

        int style= AlertDialog.THEME_TRADITIONAL;
        dataPickerDialog=new DatePickerDialog(getContext(),style,dateSetListener,year,month,day);

    }


    //Ask the admin to conform
    public  void alritMess() {

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
        alertdialog.setMessage("Are you sure that you want to Set the changes?");
        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //call the method
                if(addTheDetails())
                    Toast.makeText(getContext(), "The details was updates successfully", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getContext(), "Please insert 4 different dates", Toast.LENGTH_LONG).show();


            }
        });

        alertdialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "Cancel", Toast.LENGTH_LONG).show();
            }
        });

        alertdialog.show();
    }

    //Insert the new updates for the holidays
    public Boolean addTheDetails(){
        if(caretaker==null)
            return false;
        //update the list of the holidays
        List<String> holidays=new ArrayList<>();
        if(!date1.getText().toString().isEmpty() && !holidays.contains(date1.getText().toString()))
            holidays.add(date1.getText().toString());
        if(!date2.getText().toString().isEmpty()&& !holidays.contains(date2.getText().toString()))
            holidays.add(date2.getText().toString());
        if(!date3.getText().toString().isEmpty() && !holidays.contains(date3.getText().toString()))
            holidays.add(date3.getText().toString());
        if(!date4.getText().toString().isEmpty() && !holidays.contains(date4.getText().toString()))
            holidays.add(date4.getText().toString());

        if(holidays.size()!=4){
            return false;
        }
        caretaker.setHolidays(holidays);

        //update the  dp the table of the Holidays User
        for(String holiday:holidays){
            HolidayUser holidayUser=new HolidayUser(caretaker.getId(),holiday);
            String uidFirebase=caretaker.getId()+holiday;
            UsersDataBaseManager.getInstance().df.collection("HolidaysUser").document(uidFirebase)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
            UsersDataBaseManager.getInstance().updateHolidaysTablCollection(holidayUser);

        }
        caretaker.setHolidays(holidays);

        //update our list in the UsersDataBaseManager
        for(Caretaker c: UsersDataBaseManager.getInstance().getCaretakers()){
            if(caretaker.getEmail().equals(c.getEmail())) {
                Log.d("Iam true to delete",c.getEmail());
                UsersDataBaseManager.getInstance().getCaretakers().remove(c);
                UsersDataBaseManager.getInstance().getCaretakers().add(caretaker);
                break;
            }
        }


        return true;
    }


}
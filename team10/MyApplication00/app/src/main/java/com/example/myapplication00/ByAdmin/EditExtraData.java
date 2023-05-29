package com.example.myapplication00.ByAdmin;

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
public class EditExtraData extends Fragment {

    UsersDataBaseManager UsersDataBaseManager;
    Button save;
    ImageButton btn1,btn2,btn3,btn4;
    TextView name,month;
    TextView date1,date2,date3,date4,price;
    int i;
    Caretaker caretaker=null;

    User user;
    DatePickerDialog dataPickerDialog;


    public EditExtraData() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_extra_data, container, false);
        initDatePicker();

        //getting the selected user from the list
        UsersDataBaseManager=UsersDataBaseManager.getInstance();
        user =UsersDataBaseManager.getSelectedUser();
        for(Caretaker c: UsersDataBaseManager.getCaretakers()){
            if(user.getEmail().equals(c.getEmail())) {
                caretaker=c;
            }
        }


        //getting the id
        name=view.findViewById(R.id.nameofCaretaker);
        month=view.findViewById(R.id.numofmonthPa);
        date1=view.findViewById(R.id.date1);
        date2=view.findViewById(R.id.date2);
        date3=view.findViewById(R.id.date3);
        date4=view.findViewById(R.id.date4);
        price=view.findViewById(R.id.setprice);
        btn1=view.findViewById(R.id.cal1);
        btn2=view.findViewById(R.id.cal2);
        btn3=view.findViewById(R.id.cal3);
        btn4=view.findViewById(R.id.cal4);

        //By clicking on the calenders
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=1;
                opendatePicker(view);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=2;
                opendatePicker(view);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=3;
                opendatePicker(view);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=4;
                opendatePicker(view);
            }
        });

        Log.d("the user to update ",user.getId()+" "+user.getEmail()+" "+user.getPhoneNum());
        //btn
        save = view.findViewById(R.id.btnForCaretaker);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alritMess();
            }
        });


        //set the current details
        if (user!=null){
            String theName=caretaker.getFirstName().toString()+" "+caretaker.getLastName().toString();
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

    //Ask the admin to conform
    public  void alritMess() {

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
        alertdialog.setMessage("Are you sure that you want to Update this user?");
        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //call the method
                if(addTheDetails())
                    Toast.makeText(getContext(), "The User was updates successfully", Toast.LENGTH_LONG).show();
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

    private void initDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date=makeDateString(dayOfMonth,month,year);
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

        int style= AlertDialog.THEME_HOLO_LIGHT;
        dataPickerDialog=new DatePickerDialog(getContext(),style,dateSetListener,year,month,day);

    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return dayOfMonth+"."+getMonthFormat(month)+"."+year;
    }

    private String getMonthFormat(int month) {
        Calendar cal=Calendar.getInstance();
        int month1=cal.get(Calendar.MONTH);
        return String.valueOf(month1+1);
    }


    public void opendatePicker(View view){
        dataPickerDialog.show();
    }

    //Insert the new updates
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
        //update the payment per this month
        if(!price.getText().toString().isEmpty()){
            caretaker.getPayment_per_month().put(month.getText().toString(),price.getText().toString());
        }
        Log.d("size of holidays",String.valueOf(holidays.size()));
        if(holidays.size()!=4){
           return false;
        }
        caretaker.setHolidays(holidays);

        //update the Collections and dp and list
        PaymentPerUser paymentPerUser=new PaymentPerUser(caretaker.getId(),month.getText().toString(),price.getText().toString());
        UsersDataBaseManager.getInstance().updateCaretaker(caretaker);
        UsersDataBaseManager.getInstance().updatePaymentTablCollection(paymentPerUser);

        //update the  dp the table of the Holidays User
       for(String holiday:holidays){
           HolidayUser holidayUser=new HolidayUser(caretaker.getId(),holiday);
           String uidFirebase=caretaker.getId()+holiday;
           UsersDataBaseManager.df.collection("HolidaysUser").document(uidFirebase)
                   .delete()
                   .addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void aVoid) {
                           Log.d("TAG", "DocumentSnapshot HolidaysUser successfully updated!");
                       }
                   })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Log.w("TAG", "Error updated document", e);
                       }
                   });
            UsersDataBaseManager.getInstance().updateHolidaysTablCollection(holidayUser);

        }
        caretaker.setHolidays(holidays);

       //update our list in the UsersDataBaseManager
        for(Caretaker c: UsersDataBaseManager.getCaretakers()){
            if(caretaker.getEmail().equals(c.getEmail())) {
                Log.d("Iam true to delete",c.getEmail());
                UsersDataBaseManager.getCaretakers().remove(c);
                break;
            }
        }
        UsersDataBaseManager.getCaretakers().add(caretaker);

        return true;
    }

}
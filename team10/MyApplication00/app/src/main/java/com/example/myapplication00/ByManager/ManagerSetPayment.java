package com.example.myapplication00.ByManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


public class ManagerSetPayment extends DialogFragment {


    TextView name,month,price;
    Button save,cancel;
    Caretaker caretaker;
    User user;
    UsersDataBaseManager UsersDataBaseManager;


    public ManagerSetPayment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_manager_set_payment, container, false);

        //get the id's
        name=view.findViewById(R.id.caretakerNamePay);
        month=view.findViewById(R.id.numofmonthPa);
        price=view.findViewById(R.id.setPayment);
        save=view.findViewById(R.id.saveForPay);
        cancel=view.findViewById(R.id.cancelForPay);

        //Set the details
        //getting the selected user from the list
        UsersDataBaseManager=UsersDataBaseManager.getInstance();
        user =UsersDataBaseManager.getSelectedUser();
        for(Caretaker c: UsersDataBaseManager.getCaretakers()){
            if(user.getEmail().equals(c.getEmail())) {
                caretaker=c;
            }
        }

        Calendar c=Calendar.getInstance();
        String num= String.valueOf(c.get(Calendar.MONTH)+1);
        month.setText(num);
        String theprice=caretaker.getPayment_per_month().get(String.valueOf(c.get(Calendar.MONTH)+1));
        if(theprice==null){
            theprice= String.valueOf(100*28);
        }
        price.setText(theprice);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(price.getText().toString().isEmpty())
                    Toast.makeText(getContext(),"Please insert payment before saving",Toast.LENGTH_LONG).show();
                else{
                    alritMess();
                }
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


    //Ask the admin to conform
    public  void alritMess() {

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
        alertdialog.setMessage("Are you sure that you want to Update this payment?");
        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //call the method
                if(addTheDetails())
                    Toast.makeText(getContext(), "The payment updates successfully", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getContext(), "Please insert payment", Toast.LENGTH_LONG).show();


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

    //Insert the new updates
    public Boolean addTheDetails(){
        if(caretaker==null)
            return false;


        //update the payment per this month
        if(!price.getText().toString().isEmpty()){
            caretaker.getPayment_per_month().put(month.getText().toString(),price.getText().toString());
        }


        //update the Collections and dp and list
        PaymentPerUser paymentPerUser=new PaymentPerUser(caretaker.getId(),month.getText().toString(),price.getText().toString());
        UsersDataBaseManager.getInstance().updateCaretaker(caretaker);
        UsersDataBaseManager.getInstance().updatePaymentTablCollection(paymentPerUser);


        //update our list in the UsersDataBaseManager
        for(Caretaker c: UsersDataBaseManager.getCaretakers()){
            if(caretaker.getEmail().equals(c.getEmail())) {
                UsersDataBaseManager.getCaretakers().remove(c);
                UsersDataBaseManager.getCaretakers().add(caretaker);
                break;
            }
        }


        return true;
    }

}
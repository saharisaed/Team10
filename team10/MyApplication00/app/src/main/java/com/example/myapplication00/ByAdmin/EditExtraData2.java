package com.example.myapplication00.ByAdmin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication00.Classes.Caretaker;
import com.example.myapplication00.Classes.ElderlyClass;
import com.example.myapplication00.Classes.Medicine;
import com.example.myapplication00.Classes.User;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;

//extra data for Elderly person
public class EditExtraData2 extends Fragment {

    UsersDataBaseManager UsersDataBaseManager;
    Button save,addMed;
    ImageButton btn1;
    TextView name;
    TextView birthdayE,ageE,assistPhone,addressE;
    ElderlyClass elderlyperson=null;
    public static Medicine medicine=null;

    User user;
    DatePickerDialog dataPickerDialog;


    public EditExtraData2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_extra_data2, container, false);
        initDatePicker();

        //getting the selected user from the list
        UsersDataBaseManager=UsersDataBaseManager.getInstance();
        user =UsersDataBaseManager.getSelectedUser();
        for(ElderlyClass e: UsersDataBaseManager.getElderlies()){
            if(user.getId().equals(e.getId())) {
                elderlyperson=e;
            }
        }


        //getting the id
        name=view.findViewById(R.id.nameofِElderly4);
        btn1=view.findViewById(R.id.calbirthday);
        birthdayE=view.findViewById(R.id.birthdayE);
        //for calculate the age
        ageE=view.findViewById(R.id.ageEl);
        assistPhone=view.findViewById(R.id.assistPhone);
        addressE=view.findViewById(R.id.addressE);
        addMed=view.findViewById(R.id.addMediM);

        Log.d("the user is",elderlyperson.getFirstName());
        //set the current details
        if (user!=null){
            String theName=elderlyperson.getFirstName()+" "+elderlyperson.getLastName();
            name.setText(theName);
            Calendar c=Calendar.getInstance();
            birthdayE.setText(elderlyperson.getBirthday());
            ageE.setText(elderlyperson.getAge());
            assistPhone.setText(elderlyperson.getAssisPhone());
            addressE.setText(elderlyperson.getAddress());


        }

        //By clicking on the calenders
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendatePicker(view);
            }
        });


        //btn save to save the changes
        save = view.findViewById(R.id.btnForElderly2);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking first the inputs
                if(checkTheInputs()){
                    //make changes on the dp anf the firebase
                    alritMess();

                }

            }
        });

        //click on the button to handel adding new medicine to the user list
        addMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAddMedicine dialogAddMedicine=new DialogAddMedicine(elderlyperson);
                dialogAddMedicine.show(getChildFragmentManager(),"DialogAddMedicine");
            }
        });

        return view;
    }

    //update the database and the fireBase
    private Boolean updateEveryThing() {
        if(elderlyperson==null)
            return false;

        //srt the changes on the elderly object
        elderlyperson.setAge(ageE.getText().toString());
        elderlyperson.setBirthday(birthdayE.getText().toString());
        elderlyperson.setAssisPhone(assistPhone.getText().toString());
        elderlyperson.setAddress(addressE.getText().toString());

        //update the dp and the Firebase
        UsersDataBaseManager.getInstance().updateElderly(elderlyperson);

        Log.d("Iam","fine");
        for(ElderlyClass eld:UsersDataBaseManager.getInstance().getElderlies()){
            if(eld.getEmail().equals(elderlyperson.getEmail())){
                UsersDataBaseManager.getInstance().getElderlies().remove(eld);
                UsersDataBaseManager.getInstance().getElderlies().remove(elderlyperson);
                break;
            }
        }
        return true;
    }

    //Ask the admin to conform
    public  void alritMess() {

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
        alertdialog.setMessage("Are you sure that you want to Update this user?");
        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //call the method
                if( updateEveryThing())
                    Toast.makeText(getContext(), "The User was updates successfully", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getContext(), "No changes", Toast.LENGTH_LONG).show();


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

    //When click on the save button check the inputs first
    private boolean checkTheInputs() {
        Boolean flage=true;

        //check the birthday
        if(birthdayE.getText().toString().isEmpty()){
            flage=false;
        }
        //check assistPhone
        if(assistPhone.getText().toString().isEmpty()){
            flage=false;
            assistPhone.setError("Null phone number");
        }
        //check the code
        if(addressE.getText().toString().isEmpty()){
            flage=false;
            addressE.setError("Null address");
        }

        return flage;
    }

    //DatePicker to pick the birthday
    private void initDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                Calendar cal=Calendar.getInstance();
                int currYear=cal.get(Calendar.YEAR);
                if(currYear-year > 55 && currYear-year<115){
                    String date=makeDateString(dayOfMonth,month,year);
                    birthdayE.setText(date);
                    ageE.setText(String.valueOf(currYear-year));
                }
                else{
                    Toast.makeText(getContext(), "The age should be +55", Toast.LENGTH_SHORT).show();
                }

            }


        };

        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        int style= AlertDialog.THEME_HOLO_LIGHT;
        int sendYear=year-56;
        dataPickerDialog=new DatePickerDialog(getContext(),style,dateSetListener,sendYear,month,day);

    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return dayOfMonth+"."+month+"."+year;
    }

    private String getMonthFormat(int month) {
        return String.valueOf(month+1);
    }


    public void opendatePicker(View view){
        dataPickerDialog.show();
    }

    //adding the Medicine to the list of the user
}
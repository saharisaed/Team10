package com.example.myapplication00.ByAdmin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication00.Classes.Caretaker;
import com.example.myapplication00.Classes.ElderlyClass;
import com.example.myapplication00.Classes.User;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//fragment after choosing a user to change his details
public class EditSpecUser extends Fragment {

    UsersDataBaseManager UsersDataBaseManager;
    Button save;
    TextInputEditText idInput,fnamInput,lnameInput,codeInput,emailInput,phoneNumInput;

    //Manager Caretaker ElderlyPerson
    User user;
    Caretaker c;
    ElderlyClass e;
    private FirebaseAuth mAuth;

    public EditSpecUser() {
        // Required empty public constructor
    }

    public Boolean updateEveryThing(User user){
        //update the user data from the dataManager
        if(user.getUserType().equals("caretaker")){
            c.setId(idInput.getText().toString());
            c.setFirstName(fnamInput.getText().toString());
            c.setLastName(lnameInput.getText().toString());
            c.setPhoneNum(phoneNumInput.getText().toString());
            //update our list in the UsersDataBaseManager
            for(Caretaker ca: UsersDataBaseManager.getCaretakers()){
                if(c.getEmail().equals(ca.getEmail())) {
                    UsersDataBaseManager.getCaretakers().remove(c);
                    UsersDataBaseManager.getCaretakers().add(c);
                    break;
                }
            }
            UsersDataBaseManager.getInstance().updateCaretaker(c);
            return true;
        }

        else if(user.getUserType().equals("elderly Person")){
            e.setId(idInput.getText().toString());
            e.setFirstName(fnamInput.getText().toString());
            e.setLastName(lnameInput.getText().toString());
            e.setPhoneNum(phoneNumInput.getText().toString());
            //update our list in the UsersDataBaseManager
            for(ElderlyClass ca: UsersDataBaseManager.getElderlies()){
                if(e.getEmail().equals(ca.getEmail())) {
                    UsersDataBaseManager.getElderlies().remove(e);
                    UsersDataBaseManager.getElderlies().add(e);
                    break;
                }
            }
            UsersDataBaseManager.getInstance().updateElderly(e);
            return true;

        }
        else{
            user.setId(idInput.getText().toString());
            user.setFirstName(fnamInput.getText().toString());
            user.setLastName(lnameInput.getText().toString());
            user.setPhoneNum(phoneNumInput.getText().toString());
            UsersDataBaseManager.getInstance().updateManager(user);
            //update our list in the UsersDataBaseManager
            for(User ca: UsersDataBaseManager.getManagers()){
                if(user.getEmail().equals(ca.getEmail())) {
                    UsersDataBaseManager.getManagers().remove(user);
                    UsersDataBaseManager.getManagers().add(user);
                    break;
                }
            }
            return true;
        }

    }

    public  void alritMess(User user) {

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
        alertdialog.setMessage("Are you sure that you want to Update this user?");
        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //call the method
                if( updateEveryThing(user))
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_spec_user, container, false);
        mAuth = FirebaseAuth.getInstance();

        //getting the selected user from the list
        UsersDataBaseManager=UsersDataBaseManager.getInstance();
        final User user = UsersDataBaseManager.getSelectedUser();

        //getting by id when press the button save
        idInput = view.findViewById(R.id.idInput1);
        fnamInput = view.findViewById(R.id.fnamInput2);
        lnameInput = view.findViewById(R.id.lnameInput2);
        emailInput = view.findViewById(R.id.emailInput1);
        phoneNumInput = view.findViewById(R.id.phoneNum2);
        codeInput = view.findViewById(R.id.codeInput2);


        //setting the text
        idInput.setText(user.getId());
        fnamInput.setText(user.getFirstName());
        lnameInput.setText(user.getLastName());
        phoneNumInput.setText(user.getPhoneNum());
        emailInput.setText(user.getEmail());
        codeInput.setText(user.getPassword());


        //we cannot change the email and the password
        emailInput.setEnabled(false);
        codeInput.setEnabled(false);
        idInput.setEnabled(false);

        //getting the specefic user
        if(user.getUserType().equals("caretaker")){
            for (Caretaker selected:UsersDataBaseManager.getInstance().getCaretakers())
                if(user.getEmail().equals(selected.getEmail())){
                    c=selected;
                }
        }
        if(user.getUserType().equals("elderly Person")){
            for (ElderlyClass selected:UsersDataBaseManager.getInstance().getAllElderlies())
                if(user.getEmail().equals(selected.getEmail())){
                    e=selected;
                }

        }



        //btn
        save = view.findViewById(R.id.savebtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("the update user ",user.getId()+" "+user.getEmail()+" "+user.getPhoneNum());
                if(checkTheDataUpdate()){
                    alritMess(user);
                }

            }
        });

        return view;
    }






    //check if the inserted data are correct
    public boolean checkTheDataUpdate(){
        Boolean flage=true;
        //check the id
        if(idInput.getText().toString().isEmpty() || idInput.getText().toString().length()!=9){
            flage=false;
            idInput.setError("Null ID");
        }
        //check first  name
        if(fnamInput.getText().toString().isEmpty()){
            flage=false;
            fnamInput.setError("Null first Name");
        }
        //check last name
        if(lnameInput.getText().toString().isEmpty()){
            flage=false;
            lnameInput.setError("Null Last Name");
        }
        //check the phoneNum
        if(phoneNumInput.getText().toString().isEmpty()){
            flage=false;
            phoneNumInput.setError("Null phone number");
        }
        return flage;
    }



}
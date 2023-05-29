package com.example.myapplication00.ByAdmin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication00.Classes.Caretaker;
import com.example.myapplication00.Classes.ElderlyClass;
import com.example.myapplication00.Classes.PaymentPerUser;
import com.example.myapplication00.Classes.User;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

//the fragment that handel adding user by the admin
public class AdminAddUser extends Fragment {


    Button save;
    TextInputEditText idInput,fnamInput,lnameInput,codeInput,emailInput,phoneNumInput;
    Spinner spinner;
    View view;

    private FirebaseAuth mAuth;
    private FirebaseFirestore df;

    User user;
    Caretaker c;
    ElderlyClass e;

    UsersDataBaseManager UsersDataBaseManager;

    public AdminAddUser() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
         view =  inflater.inflate(R.layout.fragment_admin_add_user, container, false);
        mAuth = FirebaseAuth.getInstance();
        df=FirebaseFirestore.getInstance();
        UsersDataBaseManager=UsersDataBaseManager.getInstance();
        //getting by id
        idInput = view.findViewById(R.id.idInput);
        fnamInput = view.findViewById(R.id.fnamInput);
        lnameInput = view.findViewById(R.id.lnameInput);
        emailInput = view.findViewById(R.id.emailInput);
        phoneNumInput = view.findViewById(R.id.phoneNum);
        codeInput = view.findViewById(R.id.codeInput);
        spinner = view.findViewById(R.id.spinner);

        //save is the button to save the changes
        save = view.findViewById(R.id.addButton);
        save.setOnClickListener(createNewAccountListner);

        return view;
    }

    //Listner to create the new User after pressing on the save button
    private View.OnClickListener createNewAccountListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startChecking();
        }
    };

    //Checking the inputs and call the alriteMess
    public void startChecking(){

        String email= emailInput.getText().toString().trim();
        String phoneNum= phoneNumInput.getText().toString().trim();
        String id= idInput.getText().toString().trim();
        String fname=fnamInput.getText().toString().trim();
        String lname= lnameInput.getText().toString().trim();
        String code=codeInput.getText().toString().trim();
        String userType=spinner.getSelectedItem().toString();

        if(userType.equals("caretaker")){
            c=new Caretaker();
            c.setId(id);
            c.setFirstName(fname);
            c.setLastName(lname);
            c.setPassword(code);
            c.setUserType(userType);
            c.setEmail(email);
            c.setPhoneNum(phoneNum);

        }
        if(userType.equals("elderly Person")){
            e=new ElderlyClass();
            e.setId(id);
            e.setFirstName(fname);
            e.setLastName(lname);
            e.setPassword(code);
            e.setUserType(userType);
            e.setEmail(email);
            e.setPhoneNum(phoneNum);
        }


            //create the user
            user = new User();
            user.setId(id);
            user.setFirstName(fname);
            user.setLastName(lname);
            user.setPassword(code);
            user.setUserType(userType);
            user.setEmail(email);
            user.setPhoneNum(phoneNum);



        //check validation of the inputs before adding new user
        Boolean isFine=checkValidation(id,fname,lname,code,email,phoneNum);
        if(isFine==true){
            Log.d("input","the input are fine");
            alritMess();
        }
    }

    //checking correctness and null inputs , know we check that only not getting empty inputs
    public boolean checkValidation(String id,String fname,String lname,String code,String email,String phoneNum){

        Boolean flage=true;
        //check the id
        if(id.isEmpty() || id.length()!=9){
            flage=false;
            idInput.setError("Null ID");
        }
        //check first  name
        if(fname.isEmpty()){
            flage=false;
            fnamInput.setError("Null first Name");
        }
        //check last name
        if(lname.isEmpty()){
            flage=false;
            lnameInput.setError("Null Last Name");
        }
        //check the code
        if(code.isEmpty() || code.length()<6){
            flage=false;
            codeInput.setError("Null Password");
        }
        //check the email
        if(email.isEmpty()){
            flage=false;
            emailInput.setError("Null email");
        }
        //check the phoneNum
        if(phoneNum.isEmpty()){
            flage=false;
            phoneNumInput.setError("Null phone number");
        }
        return flage;
    }

    //second
    //Ask the admin to conform
    public  void alritMess() {

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
        alertdialog.setMessage("Are you sure that you want to Add this user?");
        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Boolean checkaddUser=false;
                if(user.getUserType().equals("caretaker")){
                    checkaddUser =UsersDataBaseManager.addCaretaker1(c);
                    addCaretaker(c) ;
                }
                if(user.getUserType().equals("elderly Person")){
                    //adding the user to the database first
                    checkaddUser =UsersDataBaseManager.addElderly1(e);
                    addElderly(e);
                }

                if(user.getUserType().equals("manager")){
                    checkaddUser =UsersDataBaseManager.addManager1(user);
                    addManager(user);
                }
                if (checkaddUser) {
                    addAu();
                    ClearTheData();

                    Toast.makeText(getContext(), "New User Inserted", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(getContext(), "Cann't insert a new user", Toast.LENGTH_SHORT).show();


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


    //add new caretaker
    public void  addCaretaker(Caretaker user){

        Caretaker c = new Caretaker(user.getFirstName(),user.getLastName(),user.getId(),user.getUserType(),user.getPassword(),user.getPhoneNum(),user.getEmail());
        df.collection("caretakers")
                .document(c.getEmail())
                .set(c)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        System.out.println("Suucc");

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e+"FAILURE");
                    }
                });

        Calendar calendar=Calendar.getInstance();
        String mo=String.valueOf(calendar.get(Calendar.MONTH)+1) ;
        String sum= String.valueOf(Integer.valueOf(c.getPrice_per_day())*26);
        PaymentPerUser paymentPerUser=new PaymentPerUser(c.getId(),mo,sum);
        String idDocCollection=c.getId()+mo;
        df.collection("PaymentsUser")
                .document(idDocCollection)
                .set(paymentPerUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Suucc");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e+"FAILURE");
                    }
                });

        //fireBase for the collection HolidaysUser


        //add to the list of the code
        if(!UsersDataBaseManager.getCaretakers().contains(c)){
            UsersDataBaseManager.getCaretakers().add(c);
        }





    }

    //add new caretaker to the firebase and the list in the code
    public void  addElderly(ElderlyClass user){

        ElderlyClass c = new ElderlyClass(user.getFirstName(),user.getLastName(),user.getId(),user.getUserType(),user.getPassword(),user.getPhoneNum(),user.getEmail());
        df.collection("elderlies")
                .document(c.getEmail())
                .set(c)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("onSuccess");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e+"FAILURE");
                    }
                });

        //add to the list of the code
        if(!UsersDataBaseManager.getElderlies().contains(c)) {
            UsersDataBaseManager.getElderlies().add(c);
        }
    }

    //add new caretaker
    public void  addManager(User user){

        df.collection("managers")
                .document(user.getEmail())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Suu");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e+"FAILURE");
                    }
                });

        //add to the list of the code
        if(!UsersDataBaseManager.getManagers().contains(user)) {
            UsersDataBaseManager.getManagers().add(user);
        }
    }

    //Listner to create the new User after pressing on the save button
    private void addAu(){
        String email = emailInput.getText().toString().trim();
        String password = codeInput.getText().toString().trim();
        Log.d("AddAu",email+" "+password);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("OnComplite");
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getActivity(), task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    //Clear the data after we adding new user
    public  void ClearTheData(){
        idInput.setText(" ");
        fnamInput.setText(" ");
        lnameInput.setText(" ");
        codeInput.setText(" ");
        phoneNumInput.setText(" ");
        emailInput.setText(" ");

    }





}
package com.example.myapplication00;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.autofill.CharSequenceTransformation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication00.Classes.Caretaker;
import com.example.myapplication00.Classes.ElderlyClass;
import com.example.myapplication00.Classes.User;
import com.example.myapplication00.databinding.FragmentSecondBinding;
import com.example.myapplication00.logic_model.UsersDataBaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    TextInputLayout idLay,codeLay;
    Button enter;
    Spinner spinner;
    private FirebaseAuth mAuth;
    public FirebaseUser user;
    View view;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_second, container, false);
        idLay = view.findViewById(R.id.textInputLayout2);
        codeLay = view.findViewById(R.id.textInputLayout);

        mAuth = FirebaseAuth.getInstance();
        enter= view.findViewById(R.id.enter);
        ArrayAdapter <CharSequence> adapter=ArrayAdapter.createFromResource(requireContext(),R.array.userType, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner=view.findViewById(R.id.spinner2);
        //by click on the button enter
        enter.setOnClickListener(singInUserListener);

        return view;

    }

    private View.OnClickListener singInUserListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = idLay.getEditText().getText().toString();
            String password = codeLay.getEditText().getText().toString();
            Log.d("TAG", email+ " "+password);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "signInWithEmail:success");
                                 user = mAuth.getCurrentUser();
                                 UsersDataBaseManager.getInstance().setUser(user);
                                 updateUI(email,password);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                                //Toast.makeText(getActivity(), task.getException().toString(),Toast.LENGTH_SHORT).show();

                                CoordinatorLayout layoutMe;
                                layoutMe=view.findViewById(R.id.lay);
                                Snackbar.make(layoutMe,"signInWithEmail:failure",Snackbar.LENGTH_LONG)
                                        .setAction("Close", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        }).show();
                                //updateUI(null);
                            }

                        }
                    });
        }
    };

    private void updateUI(String email,String password) {
        User selectedUser=null;
        Log.d("the email",email);
        if(user!=null){
            String item;
            item=spinner.getSelectedItem().toString();
            if(item.equals("admin") && email.equals("admin@gmail.com")){
                //cal the method to check the inputs and switch to the fragment
                UsersDataBaseManager.getInstance().setSelectedUser(new User("admin","admin","0","admin","admin123","0","admin@gmail.com"));
                confirmInputs(item);
                return;
            }
            //check if he is caretaker
            if(item.equals("caretaker")){
                for(Caretaker c:UsersDataBaseManager.getInstance().getCaretakers()) {
                    if (c.getEmail().equals(email)) {
                        selectedUser = c;
                        UsersDataBaseManager.getInstance().setSelectedUser(c);
                        //cal the method to check the inputs and switch to the fragment
                        confirmInputs(item);
                        return;
                    }
                }
            }
            if(item.equals("elderly Person")){
                for(ElderlyClass e:UsersDataBaseManager.getInstance().getElderlies()) {
                    if (e.getEmail().equals(email)  ) {
                        selectedUser = e;
                        UsersDataBaseManager.getInstance().setSelectedUser(e);
                        //cal the method to check the inputs and switch to the fragment
                        confirmInputs(item);
                        break;
                    }
                }
            }
            if(item.equals("manager")){
                for(User m:UsersDataBaseManager.getInstance().getManagers()) {
                    if (m.getEmail().equals(email)) {
                        selectedUser=m;
                        UsersDataBaseManager.getInstance().setSelectedUser(selectedUser);
                        //cal the method to check the inputs and switch to the fragment
                        confirmInputs(item);
                        break;
                    }
                }
            }
            if(selectedUser==null){
                Toast.makeText(getContext(), "You are not user", Toast.LENGTH_SHORT).show();

            }



        }
    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }




    //check the Code
    private boolean validateCode(){

        String code = codeLay.getEditText().getText().toString().trim();
        if( code.isEmpty() ){
            codeLay.setError("Valid Code");
            return false;
        }

        else{
            codeLay.setErrorEnabled(false);
            return true;
        }
    }

    //confirm the Inputs and get the  fragment to the user
    public void confirmInputs(String item){

        if(  !validateCode() )
            return;

        //start new activity
        Intent in = new Intent(getActivity(),MainPageForUser.class);
        in.putExtra("userType",item).toString();
        startActivity(in);
    }



}
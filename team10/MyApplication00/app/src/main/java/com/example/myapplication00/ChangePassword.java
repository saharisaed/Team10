package com.example.myapplication00;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication00.Classes.Caretaker;
import com.example.myapplication00.Classes.ElderlyClass;
import com.example.myapplication00.Classes.User;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ChangePassword extends DialogFragment {

    Button save;
    EditText email,pass,oldPass;
    FirebaseAuth mAuth;
    CoordinatorLayout layoutMe;

    public ChangePassword()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_change_password, container, false);

        //get the id's
        email=view.findViewById(R.id.emailChange);
        pass=view.findViewById(R.id.passChange);
        save=view.findViewById(R.id.saveChang);
        oldPass=view.findViewById(R.id.oldPass);
        layoutMe=getActivity().findViewById(R.id.lay);

        //get the button

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change the password
                if(email.getText().toString().isEmpty() && pass.getText().toString().isEmpty() && oldPass.getText().toString().isEmpty() &&checkMe()){
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), oldPass.getText().toString())
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("TAG", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(getActivity(), task.getException().toString(),
                                                Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }

                                }
                            });
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    AuthCredential credential = EmailAuthProvider
                            .getCredential(email.getText().toString(), oldPass.getText().toString());

                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("TAG", "Password updated");

                                                    Snackbar.make(layoutMe,"Password updated",Snackbar.LENGTH_LONG)
                                                            .setAction("Close", new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {

                                                                }
                                                            })
                                                            .show();
                                                } else {
                                                    Snackbar.make(layoutMe,"Error password not updated",Snackbar.LENGTH_LONG)
                                                            .setAction("Close", new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {

                                                                }
                                                            })
                                                            .show();
                                                    Log.d("TAG", "Error password not updated");
                                                }
                                            }
                                        });
                                    } else {
                                        Snackbar.make(layoutMe,"Error auth failed",Snackbar.LENGTH_LONG)
                                                .setAction("Close", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                    }
                                                })
                                                .show();
                                    }
                                }
                            });

                    //update collections of the user

                    //change the password firebase auth
                    FirebaseAuth.getInstance().signOut();
                    getDialog().dismiss();
                }
                else{
                    if(checkMe()==false){
                        Snackbar.make(layoutMe,"There is no use",Snackbar.LENGTH_LONG)
                                .setAction("Close", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .show();
                        //Toast.makeText(getContext(), "There is no use", Toast.LENGTH_SHORT).show();
                    }else{
                        Snackbar.make(layoutMe,"There is empty field",Snackbar.LENGTH_LONG)
                                .setAction("Close", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .show();
                    }
                }

            }
        });
        return  view;
    }

    //check if there is this user
    public boolean checkMe(){

        boolean flage=false;
        for(Caretaker c:UsersDataBaseManager.getInstance().getCaretakers()) {
            if (c.getEmail().equals(email) &&  c.getPassword().equals(oldPass)) {
                flage=true;
                break;
            }
        }
        for(ElderlyClass e:UsersDataBaseManager.getInstance().getElderlies()) {
            if (e.getEmail().equals(email)&& e.getPassword().equals(oldPass)) {
                flage=true;
                break;
            }
        }
        for(User m:UsersDataBaseManager.getInstance().getManagers()) {
            if (m.getEmail().equals(email) && m.getPassword().equals(oldPass)  ) {
                flage=true;
                break;
            }
        }
        return flage;
    }
}
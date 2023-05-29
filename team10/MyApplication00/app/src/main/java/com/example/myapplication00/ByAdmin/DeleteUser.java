package com.example.myapplication00.ByAdmin;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication00.Classes.Caretaker;
import com.example.myapplication00.Classes.ElderlyClass;
import com.example.myapplication00.Classes.User;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


//the Fragment that handle deleting User from the list by admin
public class DeleteUser extends Fragment {

    private Button updateBtn;
    private ListView listView;
    private Context context;

    private ImageButton search;
    private EditText idinput;
    //the user to delete
    public static FirebaseUser user;

    public DeleteUser() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_delete_user, container, false);
        listView=  view.findViewById(R.id.cardList2);

        //getting the id and the button
        search = view.findViewById(R.id.search2);
        idinput = view.findViewById(R.id.idSearch2);

        List<User> list= new ArrayList<>();

        //click on the button
        String input=idinput.getText().toString();
        List<User> finalList = list;
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input;
                input=idinput.getText().toString();
                Log.d("insert",input);
                //check the validation
                if(input.equals("")){
                    finalList.addAll(UsersDataBaseManager.getInstance().getAllElderlies());
                    finalList.addAll(UsersDataBaseManager.getInstance().getAllCaretakers());
                    finalList.addAll(UsersDataBaseManager.getInstance().getAllManagers());

                    updateUserAdapter ca = new updateUserAdapter(getContext(),R.layout.card_view_for_update, finalList);
                    listView.setAdapter(ca);
                }

                if( input.length()!=9)
                    Toast.makeText(getContext(), "id should be 9 digits", Toast.LENGTH_SHORT).show();


                else{
                    User user=null;
                    for(ElderlyClass e :UsersDataBaseManager.getInstance().getAllElderlies())
                        if(e.getId().equals(input)){
                            user=e;
                        }
                    for(Caretaker e :UsersDataBaseManager.getInstance().getAllCaretakers())
                        if(e.getId().equals(input)){
                            user=e;
                        }
                    for(User e :UsersDataBaseManager.getInstance().getAllManagers())
                        if(e.getId().equals(input)){
                            user=e;
                        }
                    if(user!=null){
                        finalList.clear();
                        finalList.add(user);
                        updateUserAdapter ca = new updateUserAdapter(getContext(),R.layout.card_view_for_update, finalList);
                        listView.setAdapter(ca);
                    }
                    else{
                        Toast.makeText(getContext(), "There is no user with the insert id ُ", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });
        if(input.equals("")){
            list= new ArrayList<>();
            list.addAll(UsersDataBaseManager.getInstance().getAllElderlies());
            list.addAll(UsersDataBaseManager.getInstance().getAllCaretakers());
            list.addAll(UsersDataBaseManager.getInstance().getAllManagers());

            DeleteUserAdapter ca = new DeleteUserAdapter(getContext(),R.layout.card_layout, list);
            listView.setAdapter(ca);
        }

        return view;

    }


    }



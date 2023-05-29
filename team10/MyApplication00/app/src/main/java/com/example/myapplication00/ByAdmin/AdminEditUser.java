package com.example.myapplication00.ByAdmin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication00.Classes.Caretaker;
import com.example.myapplication00.Classes.ElderlyClass;
import com.example.myapplication00.Classes.User;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;

import java.util.ArrayList;
import java.util.List;

//the Fragment that handel editing User from the list by admin
public class AdminEditUser extends Fragment {

    private ListView listView;
    private ImageButton search;
    private EditText idinput;



    public AdminEditUser() {
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
        View view=inflater.inflate(R.layout.fragment_admin_edit_user, container, false);
        listView =  view.findViewById(R.id.cardList1);

        //getting the id and the button
        search = view.findViewById(R.id.search);
        idinput = view.findViewById(R.id.idSearch);

        List<User> list= new ArrayList<>();


        //click on the button
        String input=idinput.getText().toString();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input;
                input=idinput.getText().toString();
                Log.d("insert",input);
                //check the validation
                if(input.equals("")){
                    list.addAll(UsersDataBaseManager.getInstance().getElderlies());
                    list.addAll(UsersDataBaseManager.getInstance().getCaretakers());
                    list.addAll(UsersDataBaseManager.getInstance().getManagers());

                    updateUserAdapter ca = new updateUserAdapter(getContext(),R.layout.card_view_for_update, list);
                    listView.setAdapter(ca);
                }

                if( input.length()!=9)
                    Toast.makeText(getContext(), "id should be 9 digits", Toast.LENGTH_SHORT).show();


                else{
                    User user=null;
                    for(ElderlyClass e :UsersDataBaseManager.getInstance().getElderlies())
                        if(e.getId().equals(input)){
                            user=e;
                        }
                    for(Caretaker e :UsersDataBaseManager.getInstance().getCaretakers())
                        if(e.getId().equals(input)){
                            user=e;
                        }
                    for(User e :UsersDataBaseManager.getInstance().getManagers())
                        if(e.getId().equals(input)){
                            user=e;
                        }
                    if(user!=null){
                        list.clear();
                        list.add(user);
                        updateUserAdapter ca = new updateUserAdapter(getContext(),R.layout.card_view_for_update, list);
                        listView.setAdapter(ca);
                    }
                    else{
                        Toast.makeText(getContext(), "There is no user with the insert id ُ", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

        if(input.equals("")){
            list.addAll(UsersDataBaseManager.getInstance().getElderlies());
            list.addAll(UsersDataBaseManager.getInstance().getCaretakers());
            list.addAll(UsersDataBaseManager.getInstance().getManagers());


            updateUserAdapter ca = new updateUserAdapter(getContext(),R.layout.card_view_for_update, list);
            listView.setAdapter(ca);
        }


        return view;

    }

    public void onResume() {
        super.onResume();
        List<User> list= new ArrayList<>();
        list.addAll(UsersDataBaseManager.getInstance().getElderlies());
        list.addAll(UsersDataBaseManager.getInstance().getCaretakers());
        list.addAll(UsersDataBaseManager.getInstance().getManagers());

        updateUserAdapter ca = new updateUserAdapter(getContext(),R.layout.card_view_for_update, list);
        listView.setAdapter(ca);
    }
}
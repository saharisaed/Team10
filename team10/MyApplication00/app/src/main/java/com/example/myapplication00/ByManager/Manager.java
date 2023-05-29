package com.example.myapplication00.ByManager;

import android.app.DatePickerDialog;
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
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;

import java.util.ArrayList;
import java.util.List;


public class Manager extends Fragment {


    DatePickerDialog dataPickerDialog;
    private ListView listView;
    private ImageButton search;
    private EditText idinput;
    private Manager_WorkAdapter ca;
    private List<Caretaker> list = new ArrayList<>();



    public Manager() {
        // Required empty public constructor
        Log.d("Im"," in the manager");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manager, container, false);
        //set the card view for the manager user
        listView = view.findViewById(R.id.cardListManager);

        //getting the id and the button
        search = view.findViewById(R.id.searchinmanager);
        idinput = view.findViewById(R.id.idSearchinManager);


        //click on the button for search
        String input = idinput.getText().toString();
        search.setOnClickListener(onSearch);

        //get all the caretakers
        if (input.equals("")) {
            list.addAll(UsersDataBaseManager.getInstance().getCaretakers());
            ca = new Manager_WorkAdapter(getContext(), R.layout.card_for_manager, list);
            listView.setAdapter(ca);

        }
        return view;
    }


    //Listner to search
    private View.OnClickListener onSearch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String input;
            input = idinput.getText().toString();
            //check the validation
            if (input.equals("")) {
                list.addAll(UsersDataBaseManager.getInstance().getCaretakers());

                Manager_WorkAdapter ca = new Manager_WorkAdapter(getContext(), R.layout.card_for_manager, list);

                listView.setAdapter(ca);
            }

            if (input.length() != 9)
                Toast.makeText(getContext(), "id should be 9 digits", Toast.LENGTH_SHORT).show();


            else {
                Caretaker user = null;
                for (Caretaker e : UsersDataBaseManager.getInstance().getCaretakers())
                    if (e.getId().equals(input)) {
                        user = e;
                    }
                if (user != null) {
                    list.clear();
                    list.add(user);
                    Manager_WorkAdapter ca = new Manager_WorkAdapter(getContext(), R.layout.card_for_manager, list);
                    listView.setAdapter(ca);
                } else {
                    Toast.makeText(getContext(), "There is no caretaker with the insert id ُ", Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

    public void onResume() {
        super.onResume();
        List<Caretaker> list= new ArrayList<>();
        list.addAll(UsersDataBaseManager.getInstance().getCaretakers());

        ca = new Manager_WorkAdapter(getContext(), R.layout.card_for_manager, list);
        listView.setAdapter(ca);
    }
}
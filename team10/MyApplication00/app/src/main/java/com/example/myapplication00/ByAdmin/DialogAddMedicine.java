package com.example.myapplication00.ByAdmin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication00.Classes.ElderlyClass;
import com.example.myapplication00.Classes.Medicine;
import com.example.myapplication00.Classes.Medicine_user;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;


public class DialogAddMedicine extends DialogFragment {


    TextView cancel,add;
    TextInputEditText name,totalAmount,atHour,takeAt;
    static Medicine medicine=null;
    static Boolean isNotNull=false;
    //the user that we wand to update
    ElderlyClass elderlyperson=null;

    public DialogAddMedicine(ElderlyClass elderlyClass){
        this.elderlyperson=elderlyClass;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dialog_add_medicine, container, false);

        //get the button
        cancel=view.findViewById(R.id.cancelM);
        add=view.findViewById(R.id.addM);
        name=view.findViewById(R.id.nameInputM);
        totalAmount=view.findViewById(R.id.totalAmountInput);
        atHour=view.findViewById(R.id.atHourInput);
        takeAt=view.findViewById(R.id.takeItInput);


        //Clicking on the buttons
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cheking the input
                if(checkTheInputs()==true){
                    if(createMObj()){
                        if(addingMed()){
                            Toast.makeText(getContext(), "Successfully adding the new medicine ", Toast.LENGTH_SHORT).show();
                            getDialog().dismiss();
                        }

                    }
                }}
        });



        return view;
    }

    //adding the medicine to the dp and the fireBase
    private boolean addingMed() {
        if(elderlyperson==null || medicine==null)
            return false;

        Medicine_user medicine_user=new Medicine_user(medicine.getName(), elderlyperson.getId(), medicine.getTotalAmount(), medicine.getAthour(), "No");
        //insert new row in the tables in the db
        UsersDataBaseManager.getInstance().addMedician(medicine_user,medicine);
        //update the fireBase the collection MedicansUser (thats mean the user with the specefic medicine
        String uidFirebase=elderlyperson.getId()+medicine.getName();
        UsersDataBaseManager.getInstance().df.collection("MedicansUser").document(uidFirebase)
                .set(medicine_user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot MedicansUser successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error updated document", e);
                    }
                });

        //update the fireBase the collection of the elderlyPersons to update his list of medicine
        UsersDataBaseManager.getInstance().df.collection("elderlies")
                .document(elderlyperson.getEmail())
                .set(elderlyperson)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot elderlies successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error updated document", e);
                    }
                });
        UsersDataBaseManager.getInstance().getMedicine_users().add(medicine_user);

        //update the list in the Manager
        for(ElderlyClass eld:UsersDataBaseManager.getInstance().getElderlies()){
            if(eld.getEmail().equals(elderlyperson.getEmail())){
                UsersDataBaseManager.getInstance().getElderlies().remove(eld);
                UsersDataBaseManager.getInstance().getElderlies().remove(elderlyperson);
                break;
            }
        }

        return true;

    }



    /**
     *
     * @return true if the input are fine
     */
    public Boolean checkTheInputs(){

        Boolean flage=true;
        //check the name
        if(name.getText().toString().isEmpty()){
            flage=false;
            name.setError("null input");
        }
        //check first  name
        if(totalAmount.getText().toString().isEmpty()){
            flage=false;
            totalAmount.setError("null input");
        }
        //check last name
        if(atHour.getText().toString().isEmpty()){
            flage=false;
            atHour.setError("null input");
        }
        //check the code
        if(takeAt.getText().toString().isEmpty()){
            flage=false;
            takeAt.setError("null input");
        }

        return flage;
    }


    //Creating the object and adding it to the list for the user
    public boolean createMObj(){
        medicine=new Medicine(name.getText().toString(),totalAmount.getText().toString(),atHour.getText().toString(),takeAt.getText().toString());
        if(medicine!=null){
            EditExtraData2.medicine=medicine;
            isNotNull=true;
            if(elderlyperson.getMedicines()==null)
                elderlyperson.setMedicines(new ArrayList<>());

            return  elderlyperson.getMedicines().add(medicine);
        }
        return false;

    }

    public Medicine getMedicine(){
        return medicine;
    }

    //adding the Medicine to the list of the uer

}
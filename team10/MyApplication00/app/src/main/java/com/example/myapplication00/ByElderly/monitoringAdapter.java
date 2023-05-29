package com.example.myapplication00.ByElderly;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication00.Classes.ElderlyClass;
import com.example.myapplication00.Classes.Medicine;
import com.example.myapplication00.Classes.Medicine_user;
import com.example.myapplication00.Classes.User;
import com.example.myapplication00.MainActivity;
import com.example.myapplication00.MainPageForUser;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Locale;

public class monitoringAdapter extends RecyclerView.Adapter<monitoringAdapter.ViewHolder>  {

    private List<Medicine_user> contactList;
    private Context context;
    UsersDataBaseManager UsersDataBaseManager;
    SwitchCompat aSwitch;
    User user;
    List<Medicine_user> medicine_users;


    public monitoringAdapter(List<Medicine_user> medicine_users, Context context){
        this.medicine_users=medicine_users;
        this.context=context;


    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext()) ;
        View view=layoutInflater.inflate(R.layout.card_monitoring,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Medicine_user thedata= medicine_users.get(position);
        holder.nameMed.setText(thedata.getMedicineName());
        holder.athour.setText(thedata.getAtHour());
        holder.amount.setText(thedata.getCurrentAmount());

        UsersDataBaseManager=UsersDataBaseManager.getInstance();
        final User user=UsersDataBaseManager.getSelectedUser();
        Log.w("hiii",user.getEmail()+" "+user.getId());
        String im;
        if(user.getUserType().equals("caretaker")){
            im="no";
            holder.switchCompat.setEnabled(false);
            holder.switchCompat.setText("no");
        }
        else
            im="yes";



        //set the switch
        if(thedata.getTakeit().toLowerCase(Locale.ROOT).equals("yes")){
            holder.switchCompat.setEnabled(false);
            holder.switchCompat.setText("yes");

        }
        holder.switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.switchCompat.isChecked()){
                    holder.switchCompat.setEnabled(false);
                    //change the data
                    int i=Integer.valueOf(thedata.getCurrentAmount())-1;
                    thedata.setCurrentAmount(String.valueOf(i));
                    thedata.setTakeit("yes");
                    holder.amount.setText(String.valueOf(i));
                    changeTheFirebase(thedata,i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicine_users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameMed,athour,amount;
        SwitchCompat switchCompat;
        //getting the fields
        public ViewHolder(View itemView){
            super(itemView);
            //getting the fields
            nameMed= itemView.findViewById(R.id.nameOfMed);
            athour=itemView.findViewById(R.id.inputHour);
            amount= itemView.findViewById(R.id.inputAmount);
            switchCompat=itemView.findViewById(R.id.switch1);


        }
    }

    public void changeTheFirebase(Medicine_user item,int i){


        //update  the MedicineUser collection
        UsersDataBaseManager u=UsersDataBaseManager.getInstance();
        u.df.collection("MedicansUser").document(item.getUserId()+item.getMedicineName())
                .set(item)
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

        //update the elderly Person collection
        for(ElderlyClass e:u.getElderlies()){
            if(e.getId().equals(item.getUserId())){
                u.updateElderly(e);
                break;
            }
        }

    }
}

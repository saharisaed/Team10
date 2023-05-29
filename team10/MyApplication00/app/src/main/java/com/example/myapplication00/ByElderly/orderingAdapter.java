package com.example.myapplication00.ByElderly;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication00.ByAdmin.DialogAddMedicine;
import com.example.myapplication00.Classes.Medicine_user;
import com.example.myapplication00.Classes.User;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;
import java.util.List;

public class orderingAdapter extends RecyclerView.Adapter<orderingAdapter.ViewHolder>{

    private List<Medicine_user> contactList;
    private Context context;
    UsersDataBaseManager UsersDataBaseManager;
    SwitchCompat aSwitch;
    User user;
    List<Medicine_user> medicine_users;

    public orderingAdapter(List<Medicine_user> medicine_users, Context context){
        this.medicine_users=medicine_users;
        this.context=context;


    }

    @NonNull
    @Override
    public orderingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext()) ;
        View view=layoutInflater.inflate(R.layout.ordering,parent,false);
        orderingAdapter.ViewHolder viewHolder=new orderingAdapter.ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull orderingAdapter.ViewHolder holder, int position) {
        final Medicine_user thedata= medicine_users.get(position);
        holder.nameMed.setText(thedata.getMedicineName());
        holder.amount.setText(thedata.getCurrentAmount());

        //when click to order
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.button.setText("Already requested");
                holder.button.setBackgroundColor(Color.parseColor("#C4ECD0"));
                holder.button.setTextColor(Color.BLACK);
                holder.button.setEnabled(false);
                //get the message
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();

                dialogeMessage dialogeMessage=new dialogeMessage();
                dialogeMessage.show(fm, "dialogeMessage");

            }
        });


    }

    @Override
    public int getItemCount() {
        return medicine_users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameMed,athour,amount;
        Button button;
        //getting the fields
        public ViewHolder(View itemView){
            super(itemView);
            //getting the fields
            nameMed= itemView.findViewById(R.id.nameOfMed2);
            amount= itemView.findViewById(R.id.inputAmount2);
            button=itemView.findViewById(R.id.clickToOrder);


        }
    }

}

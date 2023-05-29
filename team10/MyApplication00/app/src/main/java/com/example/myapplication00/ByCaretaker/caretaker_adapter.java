package com.example.myapplication00.ByCaretaker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication00.ByElderly.ElderlyMonitor;
import com.example.myapplication00.ByElderly.monitoringAdapter;
import com.example.myapplication00.Classes.ElderlyClass;
import com.example.myapplication00.Classes.User;
import com.example.myapplication00.MainPageForUser;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;
import java.util.List;

public class caretaker_adapter extends  RecyclerView.Adapter<caretaker_adapter.ViewHolder> {

        private List<ElderlyClass> contactList;
        private Context context;
        UsersDataBaseManager UsersDataBaseManager;
        User user;
        List<ElderlyClass> elderlies;

        public caretaker_adapter(List<ElderlyClass> elderlies, Context context){
            this.elderlies=elderlies;
            this.context=context;
            UsersDataBaseManager= UsersDataBaseManager.getInstance();


            }

        @NonNull
        @Override
        public caretaker_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext()) ;
            View view=layoutInflater.inflate(R.layout.card_caretakers,parent,false);
            caretaker_adapter.ViewHolder viewHolder=new caretaker_adapter.ViewHolder(view);
            return viewHolder;
            }


        @Override
        public void onBindViewHolder(@NonNull caretaker_adapter.ViewHolder holder, int position) {
            final ElderlyClass thedata= elderlies.get(position);
            //set the data
            holder.firstName.setText(thedata.getFirstName());
            holder.lastName.setText(thedata.getLastName());
            holder.id.setText(thedata.getId());
            holder. userType.setText(thedata.getUserType());


            //open the medicine table for the specific elderly
            holder.checkbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //so we call the same card view that we did for the elderly
                    FragmentActivity activity = (FragmentActivity)(context);
                    FragmentManager fm = activity.getSupportFragmentManager();
                    UsersDataBaseManager.setUserIdSelected(thedata.getId());
                    Intent in = new Intent(activity, MainPageForUser.class);
                    in.putExtra("userType","monitor").toString();
                    activity.startActivity(in);

                }
            });

            holder.callbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "You have no sim card to call", Toast.LENGTH_LONG).show();

                }
            });

        }


        @Override
        public int getItemCount() {
            return elderlies.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
        TextView firstName,lastName,userType,id;
        Button callbtn,checkbtn;

            //getting the fields
            public ViewHolder(View itemView){
                super(itemView);
                //getting the fields
                firstName= itemView.findViewById(R.id.namegetC);
                lastName= itemView.findViewById(R.id.lastNamegetC);
                userType=itemView.findViewById(R.id.userTypegetC);
                id=itemView.findViewById(R.id.idgetC);
                callbtn = itemView.findViewById(R.id.Call);
                checkbtn =  itemView.findViewById(R.id.Check);


            }
        }


}

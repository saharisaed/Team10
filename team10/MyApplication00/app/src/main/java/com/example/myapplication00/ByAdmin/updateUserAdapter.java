
package com.example.myapplication00.ByAdmin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication00.Classes.User;
import com.example.myapplication00.MainPageForUser;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;

import java.util.List;

import kotlin.reflect.KVisibility;

//When open the list of Users to Update
public class updateUserAdapter extends ArrayAdapter<User> {

    private List<User> contactList;
    private Context context;
    UsersDataBaseManager UsersDataBaseManager;

    public updateUserAdapter(Context ctx, List<User> data){
        super(ctx, R.layout.card_view_for_update);
        this.contactList = data;
        this.context = ctx;
    }

    public updateUserAdapter(Context context,int resource, List<User> userList){
        super(context,resource,userList);
        this.contactList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.card_view_for_update, null);
        UsersDataBaseManager=UsersDataBaseManager.getInstance();
        //getting the fields
        TextView firstName =   v.findViewById(R.id.nameget1);
        TextView lastName =   v.findViewById(R.id.lastNameget1);
        TextView userType =  v.findViewById(R.id.userTypeget1);
        TextView id =  v.findViewById(R.id.idget1);



        final User  user = contactList.get(position);
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        userType.setText(user.getUserType());
        id.setText(user.getId());

        //for the extra data
        Button updatebtn2 =  v.findViewById(R.id.updatebtn2);
        if(userType.getText().equals("elderly Person") || userType.getText().equals("caretaker") ) {
            updatebtn2.setVisibility(View.VISIBLE);
        }


        //when press on the ubdate details button
        Button updatebtn = v.findViewById(R.id.updatebtn);
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UsersDataBaseManager.setSelectedUser(user);
                Intent in = new Intent(context,MainPageForUser.class);
                in.putExtra("userType","EditSpecUser").toString();
                context.startActivity(in);


            }
        });


        //when press on the ubdateExtra details button
        updatebtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userType.getText().equals("caretaker")){
                    UsersDataBaseManager.setSelectedUser(user);
                    Intent in = new Intent(context,MainPageForUser.class);
                    in.putExtra("userType","EditExtraData").toString();
                    context.startActivity(in);
                }

                if(userType.getText().equals("elderly Person") ){
                    UsersDataBaseManager.setSelectedUser(user);
                    Intent in = new Intent(context,MainPageForUser.class);
                    in.putExtra("userType","EditExtraData2").toString();
                    context.startActivity(in);

                }

            }
        });


        return v;
    }


    @Override
    public int getCount() {
        return contactList.size();
    }

    @Nullable
    @Override
    public User getItem(int position) {
        return contactList.get(position);
    }
}

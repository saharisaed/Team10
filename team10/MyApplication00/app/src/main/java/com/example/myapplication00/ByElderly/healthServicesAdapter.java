package com.example.myapplication00.ByElderly;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication00.Classes.HealthServices;
import com.example.myapplication00.Classes.User;
import com.example.myapplication00.MainPageForUser;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;

import java.util.List;

public class healthServicesAdapter extends ArrayAdapter<HealthServices> {

    private List<HealthServices> contactList;
    private Context context;
    UsersDataBaseManager UsersDataBaseManager;
    TextView name;
    ImageButton call;

    public healthServicesAdapter(Context context,int resource, List<HealthServices> userList){
        super(context,resource,userList);
        this.contactList = userList;
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.card_view_health_services, null);
        UsersDataBaseManager=UsersDataBaseManager.getInstance();
        //getting the fields
        name =v.findViewById(R.id.nameOfTheService);
        call = v.findViewById(R.id.callHealthService);

        final HealthServices  healthServices = contactList.get(position);
        name.setText(healthServices.getName());
        //lastName.setText(user.getLastName());


        //when press on the ubdate details button
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String phoneNum="tel:"+healthServices.getPhoneNum();
////                Intent intent = new Intent(Intent.ACTION_CALL);
////                intent.setData(Uri.parse(phoneNum));
////                getContext().startActivity(intent);
                Toast.makeText(getContext(), "You Have no sim to call", Toast.LENGTH_SHORT).show();

            }
        });



        return v;
    }
}

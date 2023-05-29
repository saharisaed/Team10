
package com.example.myapplication00.ByManager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.example.myapplication00.Classes.Caretaker;
import com.example.myapplication00.Classes.User;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;

import java.util.List;

public class Manager_WorkAdapter extends ArrayAdapter<Caretaker> {

    private List<Caretaker> contactList;
    private Context context;
    private Caretaker caretaker;


    public Manager_WorkAdapter(Context ctx, List<Caretaker> data){
        super(ctx, R.layout.card_for_manager);
        Log.d("Im","in Adapter manager");
        this.contactList = data;
        this.context = ctx;
    }

    public Manager_WorkAdapter(Context context,int resource, List<Caretaker> userList){
        super(context,resource,userList);
        Log.d("Im","in Adapter manager");
        this.contactList = userList;
        this.context = context;
    }

    //Setting the caretakers information for the manager user
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.card_for_manager, null,false);
        TextView firstName =  (TextView) v.findViewById(R.id.nameget2);
        TextView lastName = (TextView)  v.findViewById(R.id.lastNameget2);
        TextView userType = (TextView) v.findViewById(R.id.userTypeget2);
        TextView id = (TextView) v.findViewById(R.id.idget2);


        //set the buttons for setting the holidays and payment per month for the caretakers
        //we will set the functions later
        Button setHoliday = (Button) v.findViewById(R.id.SetHolidays);
        Button setPrice = (Button) v.findViewById(R.id.SetPayment);



        //get the caretaker and the details
        final User user= contactList.get(position);
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        id.setText(user.getId());
        userType.setText(user.getUserType());
        for(Caretaker c: UsersDataBaseManager.getInstance().getCaretakers()){
            if(c.getEmail().equals(user.getEmail()))
                caretaker=c;
        }

        //On Click
        setHoliday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsersDataBaseManager.getInstance().setSelectedUser(user);
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();

                ManagerSetHolidays managerSetHolidays=new ManagerSetHolidays();
                managerSetHolidays.show(fm, "ManagerSetHolidays");

            }
        });
        setPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsersDataBaseManager.getInstance().setSelectedUser(user);
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();

                ManagerSetPayment managerSetPayment=new ManagerSetPayment();
                managerSetPayment.show(fm, "ManagerSetPayment");
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
    public Caretaker getItem(int position) {
        return contactList.get(position);
    }
}

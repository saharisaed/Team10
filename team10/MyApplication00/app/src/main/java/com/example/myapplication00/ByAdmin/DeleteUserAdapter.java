
package com.example.myapplication00.ByAdmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication00.Classes.Caretaker;
import com.example.myapplication00.Classes.ElderlyClass;
import com.example.myapplication00.Classes.User;
import com.example.myapplication00.R;
import com.example.myapplication00.logic_model.UsersDataBaseManager;
import com.example.myapplication00.logic_model.UsersSQLiteDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


//The adapter for the Users List to be delete
public class DeleteUserAdapter extends ArrayAdapter<User> {

    private List<User> contactList;
    private  User user;
    private Context context;
    private FirebaseAuth mAuth;

    public DeleteUserAdapter(Context ctx, List<User> data){
        super(ctx, R.layout.card_layout);
        this.contactList = data;
        this.context = ctx;
    }

    public DeleteUserAdapter(Context context,int resource, List<User> userList){
        super(context,resource,userList);
        this.contactList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        UsersSQLiteDB m=new UsersSQLiteDB(getContext());
        final int pos = position;
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.card_layout, null,false);

        //getting the fields
        TextView firstName =  (TextView) v.findViewById(R.id.nameget);
        TextView lastName = (TextView)  v.findViewById(R.id.lastNameget);
        TextView userType = (TextView) v.findViewById(R.id.userTypeget);
        TextView id = (TextView) v.findViewById(R.id.idget);

        final User  user = contactList.get(position);
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        id.setText(user.getId());
        userType.setText(user.getUserType());


        //the button to delete a user
        Button vDelBtn = (Button) v.findViewById(R.id.deletebtn);

        vDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertdialog= new AlertDialog.Builder(getContext());
                alertdialog.setMessage("Are you sure that you want to Delete this user?");
                alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("start deleting",user.getFirstName());
                        final User user2 = user;
                        deleteTheUser(user2,position);
                        DeleteUserAdapter.this.notifyDataSetChanged();
                    }
                });

                alertdialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Cancel",Toast.LENGTH_LONG).show();
                    }
                });
                alertdialog.show();

            }
        });

        return v;
    }

    //delete the user from the collection and the sqlite
    public void deleteTheUser(User user2,int position){

        if(user2==null)
            return;
        Log.d("adapter",user2.getFirstName());

        //delete the user from the Firebase
        //getting the name of the collection
        String type=null;
        if(user2.getUserType().equals("caretaker")){
            for (Caretaker c:UsersDataBaseManager.getInstance().getAllCaretakers())
                if(user2.getEmail().equals(c.getEmail())){
                    UsersDataBaseManager.getInstance().deleteCaretaker(c);
                    UsersDataBaseManager.getInstance().deleteUser(user2,"caretakers");
                }
        }

        else if(user2.getUserType().equals("elderly Person")){
            for (ElderlyClass e:UsersDataBaseManager.getInstance().getAllElderlies())
                if(user2.getEmail().equals(e.getEmail())){
                    UsersDataBaseManager.getInstance().deleteElderly(e);
                    UsersDataBaseManager.getInstance().deleteUser(user2,"elderlies");
                }

        }

        else if(user2.getUserType().equals("manager")){
            for (User m:UsersDataBaseManager.getInstance().getAllManagers())
                if(user2.getEmail().equals(m.getEmail())){
                    UsersDataBaseManager.getInstance().deleteManager(m);
                    UsersDataBaseManager.getInstance().deleteUser(user2,"managers");
                }
        }

        contactList.remove(position);

    }

    //Listner to delete the  User after pressing on the delete button
    private void deleteAu(User user1){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(user1.getEmail(), user1.getPassword());
        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("TAG", "User account deleted.");
                                        }
                                    }
                                });

                    }
                });
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

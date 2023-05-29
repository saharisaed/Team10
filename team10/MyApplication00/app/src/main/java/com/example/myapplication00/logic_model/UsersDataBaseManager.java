package com.example.myapplication00.logic_model;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication00.Classes.Caretaker;
import com.example.myapplication00.Classes.ElderlyClass;
import com.example.myapplication00.Classes.HolidayUser;
import com.example.myapplication00.Classes.Medicine;
import com.example.myapplication00.Classes.Medicine_user;
import com.example.myapplication00.Classes.PaymentPerUser;
import com.example.myapplication00.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Data base manager
public class UsersDataBaseManager {

    private static UsersDataBaseManager instance = null;
    private Context context = null;
    private UsersSQLiteDB db = null;
    private User selectedUser = null;
    //the user that we select to update
    public FirebaseUser user;
    public  FirebaseFirestore df;
    public  String userIdSelected;
    private FirebaseAuth mAuth;

    //list for all the data to be use
    List<Caretaker> caretakers=new ArrayList<>();
    List<User> managers=new ArrayList<>();
    List<ElderlyClass> elderlies=new ArrayList<>();
    List<Medicine_user> medicine_users=new ArrayList<>();
    List<HolidayUser> holidayUsers=new ArrayList<>();
    List<PaymentPerUser> paymentPerUsers=new ArrayList<>();



    public List<Medicine_user> getMedicine_users() {
        return medicine_users;
    }

    public void setMedicine_users(List<Medicine_user> medicine_users) {
        this.medicine_users = medicine_users;
    }

    public String getUserIdSelected() {
        return userIdSelected;
    }

    public void setUserIdSelected(String userIdSelected) {
        this.userIdSelected = userIdSelected;
    }

    public List<Caretaker> getCaretakers() {
        return caretakers;
    }

    public void setCaretakers(List<Caretaker> caretakers) {
        this.caretakers = caretakers;
    }

    public List<User> getManagers() {
        return managers;
    }

    public void setManagers(List<User> managers) {
        this.managers = managers;
    }

    public List<ElderlyClass> getElderlies() {
        return elderlies;
    }

    public void setElderlies(List<ElderlyClass> elderlies) {
        this.elderlies = elderlies;
    }

    public static UsersDataBaseManager getInstance() {
        if (instance == null) {
            instance = new UsersDataBaseManager();
        }
        return instance;
    }



    public UsersSQLiteDB getDb() {
        return db;
    }

    //get the data from the fireBase
    public void getData(){
        //to get the data of the caretakers from the FireBase
        getCaretakersData();
        getManagerData();
        getElderliesData();
        //getMedicansUser();
        getHolidaysUser();
        getPaymentsUser();


    }

    //delete user from the Firebase
    public void deleteUser(User user,String type){
        df.collection(type).document(user.getEmail())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });
    }


    //to get the data of the elderly persons from the FireBase
    public void getElderliesData(){
        Task<QuerySnapshot> caretakers =df.collection("elderlies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("TAG", document.getId() + " => " + document.getData());
                                addElderly(document);

                            }
                        } else {
                            //Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                } ) ;

    }

    //to get the data of the getMedicansUser from the FireBase
    public void getMedicansUser(){
        Task<QuerySnapshot> caretakers =df.collection("MedicansUser")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("TAG", document.getId() + " => " + document.getData());
                                addMedicansUser(document);

                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                } ) ;

    }

    //getting the data from the document map and convert it to addMedicansUser object
    public void addMedicansUser(QueryDocumentSnapshot document ){

        Map<String,Object> mymap=document.getData();
        Medicine_user m;
        //getting the fields
        String medicineName = (String) mymap.get("medicineName");
        String userId = (String) mymap.get("userId");
        String currentAmount= (String) mymap.get("currentAmount");
        String atHour=(String) mymap.get("atHour");
        String takeIt= (String)mymap.get("takeit");


        m=new Medicine_user(medicineName,userId,currentAmount,atHour,takeIt);
        medicine_users.add(m);
        //add to dp
       // db.add_medician_user(m);


    }


    //to get the data of the getHolidaysUser from the FireBase
    public void getHolidaysUser(){
        Task<QuerySnapshot> caretakers =df.collection("HolidaysUser")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("TAG", document.getId() + " => " + document.getData());
                                addHolidaysUser(document);

                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                } ) ;

    }

    //getting the data from the document map and convert it to addHolidaysUser object
    public void addHolidaysUser(QueryDocumentSnapshot document ){

        Map<String,Object> mymap=document.getData();
        HolidayUser h;
        //getting the fields
        String userId = (String) mymap.get("userId");
        String date = (String) mymap.get("date");



        h=new HolidayUser(userId,date);
        holidayUsers.add(h);
        //add to dp
        db.add_holiday_user(h.getUserId(),h.getDate());


    }

    //to get the data of the getPaymentsUser from the FireBase
    public void getPaymentsUser(){
        Task<QuerySnapshot> caretakers =df.collection("PaymentsUser")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("TAG", document.getId() + " => " + document.getData());
                                addPaymentsUser(document);

                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                } ) ;

    }

    //getting the data from the document map and convert it to addPaymentsUser object
    public void addPaymentsUser(QueryDocumentSnapshot document ){

        Map<String,Object> mymap=document.getData();
        PaymentPerUser p;
        //getting the fields
        String userId = (String) mymap.get("userId");
        String month = (String) mymap.get("month");
        String sum = (String) mymap.get("sum");



        p=new PaymentPerUser(userId,month,sum);
        paymentPerUsers.add(p);
        //add to dp
        db.add_payment_user(p.getUserId(),p.getMonth(),p.getSum());

    }

    //to get the data of the caretakers from the FireBase
    public void getCaretakersData(){
        Log.d("Welcome","Hii");
        Task<QuerySnapshot> caretakers =df.collection("caretakers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("TAG", document.getId() + " => " + document.getData());
                                addCaretaker(document);

                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                } ) ;

    }

    //to get the data of the manager from the FireBase
    public void getManagerData(){
        Task<QuerySnapshot> managers =df.collection("managers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("TAG", document.getId() + " => " + document.getData());
                                addManager(document);

                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                } ) ;

    }

    //delete from the list
    public void deleteListManager(User m)
    {
        if(m.getUserType().equals("manager")){
            if(!managers.contains(m))
                managers.remove(m);
        }
    }

    //delete from the list
    public void deleteListCaretaker(Caretaker c)
    {
        caretakers.remove(c);
    }

    //getting the data from the document map and convert it to caretaker object
    public void addCaretaker(QueryDocumentSnapshot document ){

        Map<String,Object> mymap=document.getData();
        Caretaker c;

        //getting the fields
        String firstName = (String) mymap.get("firstName");
        String lastName = (String) mymap.get("lastName");
        String id= (String) mymap.get("id");
        String userType=(String) mymap.get("userType");
        String password= (String)mymap.get("password");
        String phoneNum= (String)mymap.get("phoneNum");
        String email= (String)mymap.get("email");

        List<String> holidays= (List<String>) mymap.get("holidays");

        //month,payment
        HashMap<String,String> payment_per_month = (HashMap<String, String>) mymap.get("payment_per_month");
        String max_num_of_holidays  = (String)mymap.get("max_num_holidays");
        String price_per_day = (String) mymap.get("price_per_day");
        if(email.equals("razan@gmail.com")){
            Log.d("Iam razan ? ",String.valueOf(payment_per_month.size()));
            Log.d("Iam razan ? ",payment_per_month.get("6"));
        }
        if(payment_per_month.isEmpty()){
            payment_per_month=new HashMap<>();
        }
        c=new Caretaker(firstName,lastName,id,userType,password,phoneNum,email,holidays,payment_per_month,max_num_of_holidays,price_per_day);
        //insert to the table holidays inb dp
        if(!holidays.isEmpty()){
            for(String d:holidays){
                Log.d("get holiday s",d);
                addHolidayUser(c,d);
            }
        }
        //insert to the table payment_per_month in dp
        if(!payment_per_month.isEmpty()){
            for(String i:payment_per_month.keySet())
                addPaymentUser(c,String.valueOf(i),String.valueOf(payment_per_month.get(i)));

        }
        caretakers.add(c);
        for(Caretaker caretaker:getCaretakers()){
            if(c.getEmail().equals(caretaker.getEmail()))
                Log.d("if list null",String.valueOf(caretaker.getHolidays().size()));
        }
        //add to the dp
        addCaretaker1(c);
        }



    //getting the data from the document map and convert it to elderly object
    public void addElderly(QueryDocumentSnapshot document ){

        Map<String,Object> mymap=document.getData();
        ElderlyClass elderly;
        //getting the fields
        String firstName = (String) mymap.get("firstName");
        String lastName = (String) mymap.get("lastName");
        String id= (String) mymap.get("id");
        String userType=(String) mymap.get("userType");
        String password= (String)mymap.get("password");
        String phoneNum= (String)mymap.get("phoneNum");
        String email= (String)mymap.get("email");

        //fields for the elderly person object
        List<Medicine> medicines= (List<Medicine>) mymap.get("medicines");
        String age  = (String)mymap.get("age");
        String address = (String) mymap.get("address");
        String birthday= (String) mymap.get("birthday");
        String assisPhone= (String) mymap.get("assisPhone");
        elderly=new ElderlyClass(firstName,lastName,id,userType,password,phoneNum,email,birthday,age,assisPhone,address,medicines);
        elderlies.add(elderly);

        //add to the dp
        addElderly1(elderly);

    }

    //getting the data from the document map and convert it to manager(User) object
    public void addManager(QueryDocumentSnapshot document ){

        Map<String,Object> mymap=document.getData();
        User m;
        //getting the fields
        String firstName = (String) mymap.get("firstName");
        String lastName = (String) mymap.get("lastName");
        String id= (String) mymap.get("id");
        String userType=(String) mymap.get("userType");
        String password= (String)mymap.get("password");
        String phoneNum= (String)mymap.get("phoneNum");
        String email= (String)mymap.get("email");

        m=new User(firstName,lastName,id,userType,password,phoneNum,email);
        managers.add(m);
        //add to dp
        addManager1(m);

    }


    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    public static void releaseInstance() {
        if (instance != null) {
            instance.clean();
            instance = null;
        }
    }

    private void clean() {

    }


    public Context getContext() {
        return context;

    }

    public void openDataBase(Context context) {
        this.context = context;
        df= FirebaseFirestore.getInstance();
        Log.d("df is null",String.valueOf(df==null));
        if (context != null) {
            //UsersSQLiteDB.deleteDatabase(context);
            db = new UsersSQLiteDB(context);
            db.open();
            Log.d("db open is not",String.valueOf(db==null));
        }
    }

    public void closeDataBase() {
        if(db!=null){
            db.close();
        }
    }

    //adding the users to the database
    public Boolean addManager1(User item) {
        if (db != null) {
            return db.addManager(item.getId(),item.getFirstName(),item.getLastName(),item.getUserType(),item.getPassword(),item.getPhoneNum(),item.getEmail());
        }
        return false;
    }

    //sending the user to the db
    public Boolean addCaretaker1(Caretaker item) {
        if (db != null) {
            return db.addCaretaker(item.getId(),item.getFirstName(),item.getLastName(),item.getUserType(),item.getPassword(),item.getPhoneNum(),item.getEmail(),
                    item.getMax_num_of_holidays(),item.getPrice_per_day());
        }
        return false;
    }

    public Boolean addElderly1(ElderlyClass item) {
        if (db != null) {
            return db.addElderly(item.getId(),item.getFirstName(),item.getLastName(),item.getUserType(),item.getPassword(),item.getPhoneNum(),item.getEmail(),
                    item.getBirthday(),item.getAge(),item.getAssisPhone(),item.getAddress());
        }
        return false;
    }

    //adding to the tabels Medicine and MedicineUser
    public Boolean addMedician(Medicine_user medicine_user,Medicine item) {
        if (db != null) {
            return db.addMedician(item) && db.add_medician_user(medicine_user);
        }
        return false;
    }



    public Boolean addHolidayUser(Caretaker user,String Date) {
        if (db != null) {
            return db.add_holiday_user(user.getId(),Date);
        }
        return false;
    }

    public Boolean addPaymentUser(Caretaker user,String month,String sum) {
        if (db != null) {
            return db.add_payment_user(user.getId(),month,sum);
        }
        return false;
    }


    public User readManager(String id) {
        User result = null;
        if (db != null) {
            result = db.readManager(id);
        }
        return result;
    }



    //getting the users
    public List<User> getAllManagers() {
        List<User> result = new ArrayList<User>();
        if (db != null) {
            result = db.getAllManagers();
        }
        return result;
    }

    public List<Caretaker> getAllCaretakers() {
        List<Caretaker> result = new ArrayList<Caretaker>();
        if (db != null) {
            result = db.getAllCaretakers();
        }
        return result;
    }

    public List<HolidayUser> getAllHolidays() {
        List<HolidayUser> result = new ArrayList<HolidayUser>();
        if (db != null) {
            result = db.getAllHolidays();
        }
        return result;
    }

    public List<ElderlyClass> getAllElderlies() {
        List<ElderlyClass> result = new ArrayList<ElderlyClass>();
        if (db != null) {
            result = db.getAllElderlies();
        }
        return result;
    }


    //update the manager
    public void updateManager(User item) {
        Log.d("update user managedp",item.getId()+" "+item.getEmail()+" "+item.getPhoneNum());

        if (db != null && item != null) {
            //update the dp first
            db.updateManager(item);
            //update the list in the manager
            managers.add(item);
            df.collection("managers").document(item.getEmail())
                    .set(item)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "DocumentSnapshot successfully updated!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG", "Error updated document", e);
                        }
                    });


        }
    }

    public void updateCaretaker(Caretaker item) {
        Log.d("update user managedp",item.getId()+" "+item.getEmail()+" "+item.getPhoneNum());
        if (db != null && item != null) {
            db.updateCaretaker(item);
            //update the list in the caretaker
            caretakers.add(item);
            df.collection("caretakers").document(item.getEmail())
                    .set(item)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "DocumentSnapshot successfully updated!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG", "Error updated document", e);
                        }
                    });


        }
    }



    public void updatePaymentTablCollection(PaymentPerUser item) {

        if (db != null && item != null) {
            db.updatePayment(item);
            //update the list in the paymentPerUsers
            paymentPerUsers.add(item);
            df.collection("PaymentsUser").document(item.getUserId()+item.getMonth())
                    .set(item)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "DocumentSnapshot PaymentsUser successfully updated!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG", "Error updated document", e);
                        }
                    });


        }
    }



    //update the Elderly class
    public void updateElderly(ElderlyClass item) {
        if (db != null && item != null) {
           //update the dp first
            db.updateElderly(item);
            //update the list in the elderlies
            df.collection("elderlies").document(item.getEmail())
                    .set(item)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "DocumentSnapshot successfully updated elderlies!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG", "Error updated document", e);
                        }
                    });
        }
    }


    //delete the user from the dp and the list
    public void deleteManager(User item) {
        db.open();
        if (db != null) {
            db.deleteManager(item);
            //delete from the list
            if(!managers.contains(item))
                managers.remove(item);
        }
    }

    //delete the user from the dp and the list
    public void deleteCaretaker(Caretaker item) {
        db.open();
        if (db != null) {
            db.deleteCaretaker(item);
            //delete from the list
            if(!caretakers.contains(item))
                caretakers.remove(item);
        }
    }

    //delete the user from the dp and the list
    public void deleteElderly(ElderlyClass item) {
        db.open();
        if (db != null) {
            db.deleteElderly(item);
            //delete from the list
            if(!elderlies.contains(item))
                elderlies.remove(item);
        }
    }


    public User getSelectedUser() {
        return this.selectedUser;
    }

    public void setSelectedUser(User selectedUser) {

        this.selectedUser = selectedUser;
    }

    public void removeAllUsers() {
        if(db!=null){
            db.deleteAllUsers();
        }
    }


    public void updateHolidaysTablCollection(HolidayUser holidayUser) {
        Log.d("holidaysNum",String.valueOf(holidayUser.getUserId()+ " "+holidayUser.getDate() ));
        if (db != null && holidayUser != null) {
            //dp update the table of the Holidays
            db.updateHolidays(holidayUser);
            //update the list in the paymentPerUsers
            holidayUsers.add(holidayUser);
            String uidFirebase=holidayUser.getUserId()+holidayUser.getDate();
            df.collection("HolidaysUser").document(uidFirebase)
                    .set(holidayUser)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "DocumentSnapshot HolidaysUser successfully updated!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG", "Error updated document", e);
                        }
                    });


        }
    }


}

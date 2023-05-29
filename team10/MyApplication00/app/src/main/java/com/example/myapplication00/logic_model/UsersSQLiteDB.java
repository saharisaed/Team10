package com.example.myapplication00.logic_model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication00.Classes.Caretaker;
import com.example.myapplication00.Classes.ElderlyClass;
import com.example.myapplication00.Classes.HolidayUser;
import com.example.myapplication00.Classes.Medicine;
import com.example.myapplication00.Classes.Medicine_user;
import com.example.myapplication00.Classes.PaymentPerUser;
import com.example.myapplication00.Classes.User;

public class UsersSQLiteDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SQLite1.db" ;

    //Tabels
    // Manager table
    private static final String TABLE_MANAGER_NAME = "managers";
    private static final String MANAGER_COLUMN_ID = "id";
    private static final String MANAGER_COLUMN_FNAME = "fname";
    private static final String MANAGER_COLUMN_LNAME = "lname";
    private static final String MANAGER_COLUMN_TYPE = "usertype";
    private static final String MANAGER_COLUMN_PASSWORD = "password";
    private static final String MANAGER_COLUMN_PHONE = "phone";
    private static final String MANAGER_COLUMN_EMAIL = "email";
    private static final String[] TABLE_MANAGER_COLUMNS = {MANAGER_COLUMN_ID, MANAGER_COLUMN_FNAME,
            MANAGER_COLUMN_LNAME, MANAGER_COLUMN_TYPE,MANAGER_COLUMN_PASSWORD,MANAGER_COLUMN_PHONE,MANAGER_COLUMN_EMAIL};



    // caretaker table
    private static final String TABLE_CARETAKER_NAME  = "caretakers";
    private static final String CARETAKER_COLUMN_ID = "id";
    private static final String CARETAKER_COLUMN_FNAME = "fname";
    private static final String CARETAKER_COLUMN_LNAME = "lname";
    private static final String CARETAKER_COLUMN_TYPE = "usertype";
    private static final String CARETAKER_COLUMN_PASSWORD = "password";
    private static final String CARETAKER_COLUMN_PHONE = "phone";
    private static final String CARETAKER_COLUMN_EMAIL = "email";
    private static final String CARETAKER_COLUMN_MAXHO = "max_num_of_holiday";
    private static final String CARETAKER_COLUMN_PRICED = "price_per_day";

    private static final String[] TABLE_CARETAKER_COLUMNS = {CARETAKER_COLUMN_ID, CARETAKER_COLUMN_FNAME,
            CARETAKER_COLUMN_LNAME, CARETAKER_COLUMN_TYPE,CARETAKER_COLUMN_PASSWORD,CARETAKER_COLUMN_PHONE,CARETAKER_COLUMN_EMAIL,
            CARETAKER_COLUMN_MAXHO,CARETAKER_COLUMN_PRICED};


    // Elderlies table
    private static final String TABLE_ELDERLY_NAME = "elderlies";
    private static final String ELDERLY_COLUMN_ID = "id";
    private static final String ELDERLY_COLUMN_FNAME = "fname";
    private static final String ELDERLY_COLUMN_LNAME = "lname";
    private static final String ELDERLY_COLUMN_TYPE = "usertype";
    private static final String ELDERLY_COLUMN_PASSWORD = "password";
    private static final String ELDERLY_COLUMN_PHONE = "phone";
    private static final String ELDERLY_COLUMN_EMAIL = "email";
    private static final String ELDERLY_COLUMN_BIRTH = "birthday";
    private static final String ELDERLY_COLUMN_AGE = "age";
    private static final String ELDERLY_COLUMN_PHONE2 = "assisPhone";
    private static final String ELDERLY_COLUMN_ADDRESS = "address";

    private static final String[] TABLE_ELDERLYR_COLUMNS = {ELDERLY_COLUMN_ID, ELDERLY_COLUMN_FNAME,
            ELDERLY_COLUMN_LNAME, ELDERLY_COLUMN_TYPE,ELDERLY_COLUMN_PASSWORD,ELDERLY_COLUMN_PHONE,ELDERLY_COLUMN_EMAIL,
            ELDERLY_COLUMN_BIRTH,ELDERLY_COLUMN_AGE,ELDERLY_COLUMN_PHONE2,ELDERLY_COLUMN_ADDRESS};



    // Medician table
    private static final String TABLE_MEDICIANS_NAME = "medicians";
    private static final String MEDICIANS_COLUMN_NAME = "name";
    private static final String MEDICIANS_COLUMN_TOTALAMOUNT = "totalAmount";

    private static final String[] MEDICIANS_COLUMNS = {MEDICIANS_COLUMN_NAME, MEDICIANS_COLUMN_TOTALAMOUNT};



    // medician_user table
    private static final String TABLE_MEDUSER_NAME = "medician_user";
    private static final String MEDUSER_COLUMN_MEDID = "medicianName";
    private static final String MEDUSER_COLUMN_USERUD = "userId";
    private static final String MEDUSER_COLUMN_CURAMOUNT = "currentAmount";
    private static final String MEDUSER_COLUMN_ATHOUR = "atHour";
    private static final String MEDUSER_COLUMN_TOKIT = "takeit";

    private static final String[] MEDUSER_COLUMNS = {MEDUSER_COLUMN_MEDID, MEDUSER_COLUMN_USERUD,MEDUSER_COLUMN_CURAMOUNT,
    };


    // holidays_user table
    private static final String TABLE_HOLUSER_NAME = "holidays_user";
    private static final String HOLUSER_COLUMN_USERID = "userId";
    private static final String HOLUSER_COLUMN_DATE = "date";

    private static final String[] HOLUSER_COLUMNS = {HOLUSER_COLUMN_USERID, HOLUSER_COLUMN_DATE};


    // payments_user table
    private static final String TABLE_PAYMENTS_NAME = "payments_user";
    private static final String PAYMENTS_COLUMN_USERID = "userId";
    private static final String PAYMENTS_COLUMN_MONTH = "month";
    private static final String PAYMENTS_COLUMN_SUM = "sum";

    private static final String[] PAYMENTS_COLUMNS = {PAYMENTS_COLUMN_USERID, PAYMENTS_COLUMN_MONTH,PAYMENTS_COLUMN_SUM};



    SQLiteDatabase db=null;
    public UsersSQLiteDB(Context context) {
        super(context, DATABASE_NAME, null , DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_MANAGERS);
            db.execSQL(CREATE_CARETAKERS_TABLE);
            db.execSQL(CREATE_ELDERLIES_TABLE);

            db.execSQL(CREATE_MEDICIAN_TABLE);
            db.execSQL(CREATE_MEDICIAN_USER);
            db.execSQL(CREATE_HOLIDAYS_USER);
            db.execSQL(CREATE_PAYMENT_USER);

        }
        catch (Throwable t) {
            t.printStackTrace();
        }

    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //QUERIES FOR CREATING THE TABLES
        //QUERY FOR CREATING THE MANAGER TABLE
        public static final String CREATE_MANAGERS = "create table if not exists " + TABLE_MANAGER_NAME + " ( "
                + MANAGER_COLUMN_ID + " TEXT, "
                + MANAGER_COLUMN_FNAME + " TEXT, "
                + MANAGER_COLUMN_LNAME + " TEXT, "
                + MANAGER_COLUMN_TYPE + " TEXT, "
                + MANAGER_COLUMN_PASSWORD + " TEXT, "
                + MANAGER_COLUMN_PHONE + " TEXT, "
                + MANAGER_COLUMN_EMAIL + " TEXT, "
                + "PRIMARY KEY(" + MANAGER_COLUMN_ID + ") " + ") ; ";

        // SQL: Create CARETAKERS TABLE
        public static final String CREATE_CARETAKERS_TABLE = "create table if not exists " + TABLE_CARETAKER_NAME + " ( "
                + CARETAKER_COLUMN_ID + " TEXT PRIMARY KEY, "
                + CARETAKER_COLUMN_FNAME + " TEXT, "
                + CARETAKER_COLUMN_LNAME + " TEXT, "
                + CARETAKER_COLUMN_TYPE + " TEXT, "
                + CARETAKER_COLUMN_PASSWORD + " TEXT, "
                + CARETAKER_COLUMN_PHONE + " TEXT, "
                + CARETAKER_COLUMN_EMAIL + " TEXT, "
                + CARETAKER_COLUMN_MAXHO + " TEXT, "
                + CARETAKER_COLUMN_PRICED + " TEXT " + ") ; ";

        // SQL: Create ELDERLIES TABLE
        public static final String CREATE_ELDERLIES_TABLE = "create table if not exists " + TABLE_ELDERLY_NAME + " ( "
                + ELDERLY_COLUMN_ID + " TEXT PRIMARY KEY, "
                + ELDERLY_COLUMN_FNAME + " TEXT, "
                + ELDERLY_COLUMN_LNAME + " TEXT, "
                + ELDERLY_COLUMN_TYPE + " TEXT, "
                + ELDERLY_COLUMN_PASSWORD + " TEXT, "
                + ELDERLY_COLUMN_PHONE + " TEXT, "
                + ELDERLY_COLUMN_EMAIL + " TEXT, "
                + ELDERLY_COLUMN_BIRTH + " TEXT, "
                + ELDERLY_COLUMN_AGE + " TEXT, "
                + ELDERLY_COLUMN_PHONE2 + " TEXT, "
                + ELDERLY_COLUMN_ADDRESS + " TEXT " + ") ; ";

        // SQL: Create MEDICIAN
        public static final String CREATE_MEDICIAN_TABLE = "create table if not exists " + TABLE_MEDICIANS_NAME + " ( "
                + MEDICIANS_COLUMN_NAME + " TEXT PRIMARY KEY, "
                + MEDICIANS_COLUMN_TOTALAMOUNT + " TEXT "+ ") ; ";

        // SQL: Create MEDICIAN_USER
        public static final String CREATE_MEDICIAN_USER = "create table if not exists " + TABLE_MEDUSER_NAME + " ( "
                + MEDUSER_COLUMN_MEDID + " TEXT, "
                + MEDUSER_COLUMN_USERUD+ " TEXT, "
                + MEDUSER_COLUMN_CURAMOUNT + " TEXT, "
                + MEDUSER_COLUMN_ATHOUR + " TEXT, "
                + MEDUSER_COLUMN_TOKIT + " TEXT, "
                + "PRIMARY KEY(" +MEDUSER_COLUMN_MEDID+","+  MEDUSER_COLUMN_USERUD+") "+") ; ";

    // SQL: Create MEDICIAN_USER
    public static final String CREATE_HOLIDAYS_USER = "create table if not exists " + TABLE_HOLUSER_NAME + " ( "
            + HOLUSER_COLUMN_USERID + " TEXT, "
            + HOLUSER_COLUMN_DATE+ " TEXT, "
            + "PRIMARY KEY(" +HOLUSER_COLUMN_USERID+","+  HOLUSER_COLUMN_DATE+") "+") ; ";

    // SQL: Create PAYMENT_USER
    public static final String CREATE_PAYMENT_USER = "create table if not exists " + TABLE_PAYMENTS_NAME + " ( "
            + PAYMENTS_COLUMN_USERID + " TEXT, "
            + PAYMENTS_COLUMN_MONTH+ " TEXT, "
            + PAYMENTS_COLUMN_SUM+ " TEXT, "
            + "PRIMARY KEY(" +PAYMENTS_COLUMN_USERID+","+  PAYMENTS_COLUMN_MONTH+") "+") ; ";

    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        onCreate(db);

    }


    //adding new manager
    public Boolean addManager(String id,String fname,String lname,String userType,String password,String phone,String email){
//        Log.d("Im","Im here");
//        Log.d("my",id+" "+fname+" "+lname+" "+password+" "+userType+" "+phone+" "+email);

        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("fname",fname);
        contentValues.put("lname",lname);
        contentValues.put("userType",userType);
        contentValues.put("password",password);
        contentValues.put("phone",phone);
        contentValues.put("email",email);


        if(readManager(id)!=null)
            return false;

        Log.d("the db",String.valueOf(db==null));
        long result = db.insert(TABLE_MANAGER_NAME,null,contentValues);
        if(result==-1){
            Log.d("Im","im stackkkk");
            return false;
        }
        else
            return true;
    }

    //adding new caretaker
    public Boolean addCaretaker(String id,String fname,String lname,String userType,String password,String phone,String email,
                                String max_num,String price_per_day){


        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("fname",fname);
        contentValues.put("lname",lname);
        contentValues.put("userType",userType);
        contentValues.put("password",password);
        contentValues.put("phone",phone);
        contentValues.put("email",email);
        contentValues.put("max_num_of_holiday",max_num);
        contentValues.put("price_per_day",price_per_day);


        if(readCaretaker(id)!=null)
            return false;
        long result = db.insert(TABLE_CARETAKER_NAME,null,contentValues);
        if(result==-1){
            Log.d("Im","im stackkkk");
            return false;
        }
        else
            return true;
    }

    //adding new elderly
    public Boolean addElderly(String id,String fname,String lname,String userType,String password,String phone,String email,
                                String birthday,String age,String assisPhone,String address){


        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("fname",fname);
        contentValues.put("lname",lname);
        contentValues.put("userType",userType);
        contentValues.put("password",password);
        contentValues.put("phone",phone);
        contentValues.put("email",email);
        contentValues.put("birthday",birthday);
        contentValues.put("age",age);
        contentValues.put("assisPhone",assisPhone);
        contentValues.put("address",address);

        if(readElderly(id)!=null)
            return false;

        long result = db.insert(TABLE_ELDERLY_NAME,null,contentValues);
        if(result==-1){
            Log.d("Im","im stackkkk");
            return false;
        }
        else
            return true;
    }

    //adding new medician
    public Boolean addMedician(Medicine medicine){

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",medicine.getName());
        contentValues.put("totalAmount",medicine.getTotalAmount());

        if(readMedicine(medicine.getName())!=null)
            return false;
        long result = db.insert(TABLE_MEDICIANS_NAME,null,contentValues);
        if(result==-1){
            Log.d("Im","im stackkkk");
            return false;
        }
        else
            return true;
    }

    //adding new medician_user
    public Boolean add_medician_user(Medicine_user medicine_user){

        ContentValues contentValues = new ContentValues();
        contentValues.put("medicianName",medicine_user.getMedicineName());
        contentValues.put("userId",medicine_user.getUserId());
        contentValues.put("currentAmount",medicine_user.getCurrentAmount());
        contentValues.put("atHour",medicine_user.getAtHour());
        contentValues.put("takeit",medicine_user.getTakeit());

        if(read_Medicine_user(medicine_user.getUserId(),medicine_user.getMedicineName())!=null)
            return false;


        long result = db.insert(TABLE_MEDUSER_NAME,null,contentValues);
        if(result==-1){
            Log.d("Im","im stackkkk");
            return false;
        }
        else
            return true;
    }

    //adding new holiday_user
    public Boolean add_holiday_user(String userId,String date){


        ContentValues contentValues = new ContentValues();
        contentValues.put("userId",userId);
        contentValues.put("date",date);

        if(read_holiday_user(userId)!=null)
            return false;
        long result = db.insert(TABLE_HOLUSER_NAME,null,contentValues);
        if(result==-1){
            Log.d("Im","im stackkkk");
            return false;
        }
        else
            return true;
    }

    //adding new holiday_user
    public Boolean add_payment_user(String userId,String month,String sum){


        ContentValues contentValues = new ContentValues();
        contentValues.put("userId",userId);
        contentValues.put("month",month);
        contentValues.put("sum",sum);

        if(read_PaymentPerUser(userId)!=null)
            return false;
        long result = db.insert(TABLE_PAYMENTS_NAME,null,contentValues);
        if(result==-1){
            Log.d("Im","im stackkkk");
            return false;
        }
        else
            return true;
    }


    //read caretaker
    public Caretaker readCaretaker(String id){

        Caretaker caretakser =null;
        Cursor cursor=null;
        try {
            cursor = db.query(TABLE_CARETAKER_NAME,TABLE_CARETAKER_COLUMNS,
                    CARETAKER_COLUMN_ID+ " = ?",
                    new String[] {id},
                    null,null,
                    null,null);

            // if results !=null, parse the first one
            if(cursor!=null && cursor.getCount()>0){
                cursor.moveToFirst();
                caretakser = cursorToْCaretaker(cursor);
            }


        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return caretakser;
    }
    //read caretaker
    public Medicine readMedicine(String name){
        Medicine medicine =null;
        Cursor cursor=null;
        try {
            cursor = db.query(TABLE_MEDICIANS_NAME,MEDICIANS_COLUMNS,
                    MEDICIANS_COLUMN_NAME+ " = ?",
                    new String[] {name},
                    null,null,
                    null,null);

            // if results !=null, parse the first one
            if(cursor!=null && cursor.getCount()>0){
                cursor.moveToFirst();
                medicine = cursorToMedicine(cursor);
            }


        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return medicine;
    }

    private Medicine cursorToMedicine(Cursor cursor) {

        Medicine result = new Medicine();
        try {
            result = new Medicine();
            result.setName(cursor.getString(0));
            result.setTotalAmount(cursor.getString(1));


        } catch (Throwable t) {
            t.printStackTrace();
        }

        return result;
    }

    //read elderly
    public ElderlyClass readElderly(String id){

        ElderlyClass elderly =null;
        Cursor cursor=null;
        try {
            cursor = db.query(TABLE_ELDERLY_NAME,TABLE_ELDERLYR_COLUMNS,
                    ELDERLY_COLUMN_ID+ " = ?",
                    new String[] {id},
                    null,null,
                    null,null);

            // if results !=null, parse the first one
            if(cursor!=null && cursor.getCount()>0){
                cursor.moveToFirst();
                elderly = cursorToْElderly(cursor);
            }


        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return elderly;
    }


    public User readManager(String id){

        User manager =null;
        Cursor cursor=null;
        try {
            cursor = db.query(TABLE_MANAGER_NAME,TABLE_MANAGER_COLUMNS,
                    MANAGER_COLUMN_ID+ " = ?",
                    new String[] {id},
                    null,null,
                    null,null);

            // if results !=null, parse the first one
            if(cursor!=null && cursor.getCount()>0){
                cursor.moveToFirst();
                manager = cursorToْManager(cursor);
            }


        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return manager;
    }

    //read Medicine_user
    public PaymentPerUser read_PaymentPerUser(String userid){

        PaymentPerUser paymentPerUser=null;
        Cursor cursor=null;
        try {
            cursor = db.query(TABLE_PAYMENTS_NAME,PAYMENTS_COLUMNS,
                    PAYMENTS_COLUMN_USERID+ " = ?" ,
                    new String[] {userid},
                    null,null,
                    null,null);

            // if results !=null, parse the first one
            if(cursor!=null && cursor.getCount()>0){
                cursor.moveToFirst();
                paymentPerUser = cursorToPayment(cursor);
            }


        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return paymentPerUser;
    }

    private PaymentPerUser cursorToPayment(Cursor cursor) {
        PaymentPerUser result = new PaymentPerUser();
        try {

            result = new PaymentPerUser();
            result.setUserId(cursor.getString(0));
            result.setMonth(cursor.getString(1));
            result.setSum(cursor.getString(2));


        } catch (Throwable t) {
            t.printStackTrace();
        }

        return result;
    }

    //read Medicine_user
    public Medicine_user read_Medicine_user(String userid,String medName){

        Medicine_user medicine_user=null;
        Cursor cursor=null;
        try {
            cursor = db.query(TABLE_MEDUSER_NAME,MEDUSER_COLUMNS,
                    MEDUSER_COLUMN_USERUD+ " = ? AND "+MEDUSER_COLUMN_MEDID+" = ?" ,
                    new String[] {userid,medName},
                    null,null,
                    null,null);

            // if results !=null, parse the first one
            Log.d("if it 1",String.valueOf(cursor.getCount()));
            if(cursor!=null && cursor.getCount()>0){
                cursor.moveToFirst();
                medicine_user = cursorToMedicineUser(cursor);
            }


        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return medicine_user;
    }
    //read holiday_user
    public HolidayUser read_holiday_user(String userid){

        HolidayUser holidayUser=null;
        Cursor cursor=null;
        try {
            cursor = db.query(TABLE_HOLUSER_NAME,HOLUSER_COLUMNS,
                    HOLUSER_COLUMN_USERID+ " = ?" ,
                    new String[] {userid},
                    null,null,
                    null,null);

            // if results !=null, parse the first one
            if(cursor!=null && cursor.getCount()>0){
                cursor.moveToFirst();
                holidayUser = cursorToHolidayUser(cursor);
            }


        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return holidayUser;
    }

    private HolidayUser cursorToHolidayUser(Cursor cursor) {

        HolidayUser result = new HolidayUser();
        try {
            result = new HolidayUser();
            result.setUserId(cursor.getString(0));
            result.setDate(cursor.getString(1));

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return result;
    }

    private Caretaker cursorToْCaretaker(Cursor cursor) {
        Caretaker result = new Caretaker();
        try {
            result = new Caretaker();
            result.setId(cursor.getString(0));
            result.setFirstName(cursor.getString(1));
            result.setLastName(cursor.getString(2));
            result.setUserType(cursor.getString(3));
            result.setPassword(cursor.getString(4));
            result.setPhoneNum(cursor.getString(5));
            result.setEmail(cursor.getString(6));
            result.setMax_num_of_holidays(cursor.getString(7));
            result.setPrice_per_day(cursor.getString(8));
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return result;
    }

    private Medicine_user cursorToMedicineUser(Cursor cursor) {
        Medicine_user result = new Medicine_user();
        try {
            result = new Medicine_user();
            Log.d("4 ",cursor.getString(0));

            result.setMedicineName(cursor.getString(0));
            result.setUserId(cursor.getString(1));
            result.setCurrentAmount(cursor.getString(2));
            Log.d("4 ",cursor.getString(3));
            result.setAtHour(cursor.getString(3));
            Log.d("5 ",cursor.getString(4));

            result.setTakeit(cursor.getString(4));

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return result;
    }

    private User cursorToْManager(Cursor cursor) {
        User result = new User();
        try {
            result = new User();
            result.setId(cursor.getString(0));
            result.setFirstName(cursor.getString(1));
            result.setLastName(cursor.getString(2));
            result.setUserType(cursor.getString(3));
            result.setPassword(cursor.getString(4));
            result.setPhoneNum(cursor.getString(5));
            result.setEmail(cursor.getString(6));
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return result;
    }

    private ElderlyClass cursorToْElderly(Cursor cursor) {
        ElderlyClass result = new ElderlyClass();
        try {
            result = new ElderlyClass();
            result.setId(cursor.getString(0));
            result.setFirstName(cursor.getString(1));
            result.setLastName(cursor.getString(2));
            result.setUserType(cursor.getString(3));
            result.setPassword(cursor.getString(4));
            result.setPhoneNum(cursor.getString(5));
            result.setEmail(cursor.getString(6));
            result.setBirthday(cursor.getString(7));
            result.setAge(cursor.getString(8));
            result.setAssisPhone(cursor.getString(9));
            result.setAddress(cursor.getString(10));
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return result;
    }

    //get the managers
    public List<User> getAllManagers() {
        List<User> result = new ArrayList<User>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_MANAGER_NAME, TABLE_MANAGER_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                User item = cursorToْManager(cursor);
                result.add(item);
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }

        return result;
    }

    //get the CARETAKERS
    public List<Caretaker> getAllCaretakers() {
        List<Caretaker> result = new ArrayList<Caretaker>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_CARETAKER_NAME, TABLE_CARETAKER_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Caretaker item = cursorToْCaretaker(cursor);
                result.add(item);
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }

        return result;
    }

    public List<HolidayUser> getAllHolidays() {

        List<HolidayUser> result = new ArrayList<HolidayUser>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_HOLUSER_NAME, HOLUSER_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                HolidayUser item = cursorToHolidayUser(cursor);
                result.add(item);
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }

        return result;

    }

   //get the Elderlies
    public List<ElderlyClass> getAllElderlies() {
        List<ElderlyClass> result = new ArrayList<ElderlyClass>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_ELDERLY_NAME, TABLE_ELDERLYR_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ElderlyClass item = cursorToْElderly(cursor);
                result.add(item);
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }

        return result;
    }

    //Update  Payment
    public int  updatePayment(PaymentPerUser item){
        int cnt = 0;
        try{


            ContentValues values = new ContentValues();
            values.put("userId",item.getUserId());
            values.put("month",item.getMonth());
            values.put("sum",item.getSum());

            //update
            cnt = db.update(TABLE_PAYMENTS_NAME, values, PAYMENTS_COLUMN_USERID + " = ? AND " + PAYMENTS_COLUMN_MONTH+ " = ?",
                    new String[] { String.valueOf(item.getUserId()) ,String.valueOf(item.getMonth())});

            Log.d("row affected",String.valueOf(cnt));
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return cnt;

    }

    //Update  Payment
    public int  updateHolidays(HolidayUser item){
        int cnt = 0;
        try{


            ContentValues values = new ContentValues();
            values.put("userId",item.getUserId());
            values.put("date",item.getDate());

            //update
            cnt = db.update(TABLE_HOLUSER_NAME, values, HOLUSER_COLUMN_USERID + " = ? AND " + HOLUSER_COLUMN_DATE+ " = ?",
                    new String[] { item.getDate() ,item.getUserId()});

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return cnt;

    }


    //Update  manager
    public int  updateManager(User item){
        int cnt = 0;
        try{
            ContentValues values = new ContentValues();
            values.put("id",item.getId());
            values.put("fname",item.getFirstName());
            values.put("lname",item.getLastName());
            values.put("userType",item.getUserType());
            values.put("password",item.getPassword());
            values.put("phone",item.getPhoneNum());
            values.put("email",item.getEmail());

            Log.d("id is now",item.getId());
            //update
            cnt = db.update(TABLE_MANAGER_NAME, values, MANAGER_COLUMN_ID + " = ?",
                    new String[] { String.valueOf(item.getId()) });

            Log.d("row affected",String.valueOf(cnt));
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return cnt;

    }

    //Update  caretaker
    public int  updateCaretaker(Caretaker item){
        int cnt = 0;
        try{
            ContentValues values = new ContentValues();
            values.put("id",item.getId());
            values.put("fname",item.getFirstName());
            values.put("lname",item.getLastName());
            values.put("userType",item.getUserType());
            values.put("password",item.getPassword());
            values.put("phone",item.getPhoneNum());
            values.put("email",item.getEmail());
            values.put("max_num_of_holiday",item.getMax_num_of_holidays());
            values.put("price_per_day",item.getPrice_per_day());

            //update
            cnt = db.update(TABLE_CARETAKER_NAME, values, CARETAKER_COLUMN_ID + " = ?",
                    new String[] { String.valueOf(item.getId()) });

            Log.d("row affected",String.valueOf(cnt));

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return cnt;

    }
    //Update  elderly
    public int  updateElderly(ElderlyClass item){
        int cnt = 0;
        try{
            ContentValues values = new ContentValues();
            values.put("id",item.getId());
            values.put("fname",item.getFirstName());
            values.put("lname",item.getLastName());
            values.put("userType",item.getUserType());
            values.put("password",item.getPassword());
            values.put("phone",item.getPhoneNum());
            values.put("email",item.getEmail());
            values.put("birthday",item.getBirthday());
            values.put("age",item.getAge());
            values.put("assisPhone",item.getAssisPhone());


            //update
            cnt = db.update(TABLE_ELDERLY_NAME, values, ELDERLY_COLUMN_ID+ " = ?",
                    new String[] { String.valueOf(item.getId()) });
            Log.d("row affected",String.valueOf(cnt));


        } catch (Throwable t) {
            t.printStackTrace();
        }

        return cnt;
    }


    public void deleteAllUsers() {
        try {
            // delete all
            db.delete(TABLE_MANAGER_NAME, null, null);
            db.delete(TABLE_ELDERLY_NAME, null, null);
            db.delete(TABLE_CARETAKER_NAME, null, null);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    //Delete   manager
    public void deleteManager(User user){
        try {
            // delete item
            int bol =db.delete(TABLE_MANAGER_NAME, MANAGER_COLUMN_ID + " = ?",
                    new String[] { user.getId() });
            Log.d("delete",String.valueOf(bol));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    //Delete   caretaker
    public void deleteCaretaker(Caretaker user){
        try {
            // delete item
            int bol =db.delete(TABLE_CARETAKER_NAME, CARETAKER_COLUMN_ID + " = ?",
                    new String[] { user.getId() });
            Log.d("delete from db",String.valueOf(bol));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    //Delete   caretaker
    public void deleteElderly(ElderlyClass user){
        try {
            // delete item
            int bol =db.delete(TABLE_ELDERLY_NAME, ELDERLY_COLUMN_ID + " = ?",
                    new String[] { user.getId() });
            Log.d("delete",String.valueOf(bol));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void deleteDatabase(Context mContext){
        mContext.deleteDatabase("SQLite1.db");

    }


    public void open() {
        try {
            db = getWritableDatabase();
            Log.d("the db is null ",String.valueOf(db==null));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


    public void close() {
        try {
            db.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }



}

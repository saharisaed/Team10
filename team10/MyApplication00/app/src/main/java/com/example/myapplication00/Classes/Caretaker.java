package com.example.myapplication00.Classes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

//for each caretaker ,the manager  need to set the full date of the holidays and to set the payment per month
public class Caretaker extends User {

    //list of the max_num_of_holidays in current month
    private List<String> holidays;
    //month,payment
    private HashMap<String,String> payment_per_month=new HashMap<>();
    private String max_num_of_holidays;
    private String price_per_day;



    public Caretaker(String firstName, String lastName, String id, String userType, String password, String phoneNum,String email) {
        super(firstName, lastName, id, userType, password, phoneNum,email);
        payment_per_month = new HashMap<>();
        holidays = new ArrayList<>();
        max_num_of_holidays = "4";
        price_per_day = "100";
    }

    public Caretaker(){
        payment_per_month = new HashMap<>();
        holidays = new ArrayList<>();
        max_num_of_holidays = "4";
        price_per_day = "100";
    }



    public Caretaker(String firstName, String lastName, String id, String userType, String password, String phoneNum, String email
                     , List<String> holidays , HashMap<String,String> payment_per_month
                     , String max_num_of_holidays
                     , String price_per_day) {
        super(firstName, lastName, id, userType, password, phoneNum,email);
        this.holidays=holidays;
        this.payment_per_month=payment_per_month;
        this.max_num_of_holidays=max_num_of_holidays;
        this.price_per_day=price_per_day;
    }

    /**
     *
     * by the number of the working days we calculate the payment
     * @return the payment amount in current month
     */
    public String calculate_payment(){
        String sum;
        Calendar c = Calendar.getInstance();
        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        //minus the holidays
        days = days-(holidays.size());
        //function that calculate the payment in the current month
        sum=String.valueOf(days*Double.valueOf(price_per_day));
        //update the map
        payment_per_month.put(String.valueOf(c.get(Calendar.MONTH)),sum);

        return sum;
    }

    /**
     * maybe the manager want for some reasons to change the payment
     * @param payment  the payment for the current month
     * @return true if we get the changes
     */
    public void updatePayment(String payment){
        Calendar c = Calendar.getInstance();
        payment_per_month.put(String.valueOf(c.get(Calendar.MONTH)),payment);
    }

    //adding new holiday

    /**
     *
     * @param newDate the new date we want to add
     * @return false if we couldn't
     */
    public Boolean updateHoliday(String newDate){

        //We gonna check first if he have the maxNum of holidays
        if(holidays.size() ==Integer.valueOf(max_num_of_holidays))
            return false;

        if(!holidays.contains(newDate))
            return holidays.add(newDate);

        return true;

    }

    /**
     * remove a holiday
     * @param deleteDate the  date we want to delete
     * @return false if we couldn't
     */
    public Boolean deleteHoliday(Date deleteDate){

        if(!holidays.contains(deleteDate))
            return false;

        return holidays.remove(deleteDate);

    }

    public String getPrice_per_day() {
        return price_per_day;
    }

    public void setPrice_per_day(String price_per_day) {
        this.price_per_day = price_per_day;
    }

    public HashMap<String, String> getPayment_per_month() {
        return payment_per_month;
    }

    public void setPayment_per_month(HashMap<String, String> payment_per_month) {
        this.payment_per_month = payment_per_month;
    }

    public List<String> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<String> holidays) {
        this.holidays = holidays;
    }

    public String getMax_num_of_holidays() {
        return max_num_of_holidays;
    }

    public void setMax_num_of_holidays(String max_num_of_holidays) {
        this.max_num_of_holidays = max_num_of_holidays;
    }



}



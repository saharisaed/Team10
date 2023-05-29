package com.example.myapplication00.Classes;

import java.util.ArrayList;
import java.util.List;

public class HolidayUser {

    private String userId;
    private String date;

    public HolidayUser(String userId,String date) {
        this.userId = userId;
        this.date = date;
    }

    public HolidayUser() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

package com.example.myapplication00.Classes;

public class PaymentPerUser {

    private String userId;
    private String month;
    private String sum;

    public PaymentPerUser(String userId, String month, String sum) {
        this.userId = userId;
        this.month = month;
        this.sum = sum;
    }

    public PaymentPerUser() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}

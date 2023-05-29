package com.example.myapplication00.Classes;

public class Medicine_user {

    private String medicineName;
    private String userId;
    private String currentAmount;
    private String at_hour;
    private String TAKEIT;

    public Medicine_user() {
    }
    public Medicine_user(String medicineName, String userId, String currentAmount, String atHour,String Takeit ) {
        this.medicineName = medicineName;
        this.userId = userId;
        this.currentAmount = currentAmount;
        this.at_hour = atHour;
        this.TAKEIT = Takeit;

    }

    public String getTakeit() {
        return TAKEIT;
    }

    public void setTakeit(String takeit) {
        TAKEIT = takeit;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(String currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getAtHour() {
        return at_hour;
    }

    public void setAtHour(String atHour) {
        this.at_hour = atHour;
    }
}

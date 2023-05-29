package com.example.myapplication00.Classes;

public class Medicine {

    private String name;
    private String totalAmount;
    private String athour;
    private String takeItEvery;//day,towdays,onePermonth


    public Medicine(String name, String totalAmount, String athour, String takeItEvery) {
        this.name = name;
        this.totalAmount=String.valueOf(30);
        this.athour = athour;
        this.takeItEvery = takeItEvery;
    }

    public Medicine() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAthour() {
        return athour;
    }

    public void setAthour(String athour) {
        this.athour = athour;
    }

    public String getTakeItEvery() {
        return takeItEvery;
    }

    public void setTakeItEvery(String takeItEvery) {
        this.takeItEvery = takeItEvery;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}

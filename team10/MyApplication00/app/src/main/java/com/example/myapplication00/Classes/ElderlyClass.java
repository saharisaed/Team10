package com.example.myapplication00.Classes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ElderlyClass extends User {

    private String birthday;
    private String age;
    private String assisPhone;
    private String address;
    private List<Medicine> medicines=new ArrayList<>();

    public ElderlyClass(String firstName, String lastName, String id, String userType, String password, String phoneNum, String email) {
        super(firstName, lastName, id, userType, password, phoneNum, email);
        medicines=new ArrayList<>();


    }

    public ElderlyClass(){
       medicines =new ArrayList<>();
    }

    public ElderlyClass(String firstName, String lastName, String id, String userType, String password, String phoneNum, String email, String birthday, String age, String assisPhone, String address, List<Medicine> medicines) {
        super(firstName, lastName, id, userType, password, phoneNum, email);
        this.birthday = birthday;
        this.age = age;
        this.assisPhone = assisPhone;
        this.address = address;
        this.medicines = medicines;
    }


    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAssisPhone() {
        return assisPhone;
    }

    public void setAssisPhone(String assisPhone) {
        this.assisPhone = assisPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }
}

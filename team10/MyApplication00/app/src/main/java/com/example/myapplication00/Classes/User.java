package com.example.myapplication00.Classes;

import java.util.Objects;

public class User{

    private String firstName;
    private String lastName;
    private String id;
    private String userType;
    private String password;
    private String phoneNum;
    private String email;


    public User() {
    }


    public User(String firstName, String lastName, String id, String userType,String password,String phoneNum,String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.userType= userType;
        this.password=password;
        this.phoneNum=phoneNum;
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getPassword().equals(user.getPassword()) && getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPassword(), getEmail());
    }
}

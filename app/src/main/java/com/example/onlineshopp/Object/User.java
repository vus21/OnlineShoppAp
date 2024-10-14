package com.example.onlineshopp.Object;

import java.io.Serializable;

public class User implements Serializable {
    private  String ID,Name,Gender,Date,Address,Phone;
    private  int Role;

    public User() {
    }

    public User(String ID, String name, String gender, String date, String address, String phone, int role) {
        this.ID = ID;
        Name = name;
        Gender = gender;
        Date = date;
        Address = address;
        Phone = phone;
        Role = role;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int role) {
        Role = role;
    }
}

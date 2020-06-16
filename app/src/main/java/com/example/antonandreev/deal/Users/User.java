package com.example.antonandreev.deal.Users;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User{

    public String firstname;
    public String secondname;
    public String telephone;
    public String email;

    public User() {

    }
    public User(String email) {
        this.email = email;
    }
    public User(String email, String firstname, String secondname, String telephone) {
        this.email = email;
        this.firstname = firstname;
        this.secondname = secondname;
        this.telephone = telephone;
    }
}
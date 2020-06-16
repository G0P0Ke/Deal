package com.example.antonandreev.deal.Users;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User1{

    public String firstname;

    public User1() {

    }
    public User1(String firstname) {
        this.firstname = firstname;
    }
}
package com.example.antonandreev.deal.Users;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User2{

    public String secondname;

    public User2() {

    }
    public User2(String secondname) {
        this.secondname = secondname;
    }
}
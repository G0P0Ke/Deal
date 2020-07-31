package com.example.antonandreev.deal.Users;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User3{

    public String telephone;

    public User3() {

    }
    public User3(String telephone) {
        this.telephone = telephone;
    }
}
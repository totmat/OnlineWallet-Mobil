package com.example.onlinewallet_mobilapp.data;

public class PersonalCard {
    private String fullname;
    private String documentumId;
    private String gender;
    private String year;
    private String month;
    private String day;

    public PersonalCard (){
        this.fullname = null;
        this.documentumId = null;
        this.gender = null;
        this.year = null;
        this.month = null;
        this.day = null;
    }
    public PersonalCard(String fullname, String documentumId, String gender, String year, String month, String day) {
        this.fullname = fullname;
        this.documentumId = documentumId;
        this.gender = gender;
        this.year = year;
        this.month = month;
        this.day = day;
    }
}

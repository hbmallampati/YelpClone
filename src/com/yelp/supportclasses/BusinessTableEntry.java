package com.yelp.supportclasses;

public class BusinessTableEntry {

    public String businessID;
    public String businessName;
    public String businessCity;
    public String businessState;
    public String businessStars;

    public BusinessTableEntry(String businessID, String businessName, String businessCity, String businessState, String businessStars) {
        this.businessID = businessID;
        this.businessName = businessName;
        this.businessCity = businessCity;
        this.businessState = businessState;
        this.businessStars = businessStars;
    }
}

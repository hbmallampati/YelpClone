package com.yelp.supportclasses;

public class UserTableEntry {

    public String userID;
    public String userName;
    public String yelpingSince;
    public String averageStars;

    public UserTableEntry(String userID, String userName, String yelpingSince, String averageStars) {
        this.userID = userID;
        this.userName = userName;
        this.yelpingSince = yelpingSince;
        this.averageStars = averageStars;
    }
}

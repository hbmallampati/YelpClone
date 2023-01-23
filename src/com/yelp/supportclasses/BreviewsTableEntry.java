package com.yelp.supportclasses;

//r.REVIEW_ID, r.DATEE, r.REVIEW_TEXT, yu.USER_NAME,r.STARS, r.COOL_VOTES, r.FUNNY_VOTES, r.USEFUL_VOTES

public class BreviewsTableEntry {
    public String reviewID;
    public String datee;
    public String reviewText;
    public String userName;
    public String stars;
    public String coolVotes;
    public String funnyVotes;
    public String usefulVotes;

    public BreviewsTableEntry(String reviewID, String datee, String reviewText, String userName, String stars, String coolVotes, String funnyVotes, String usefulVotes) {
        this.reviewID = reviewID;
        this.datee = datee;
        this.reviewText = reviewText;
        this.userName = userName;
        this.stars = stars;
        this.coolVotes = coolVotes;
        this.funnyVotes = funnyVotes;
        this.usefulVotes = usefulVotes;
    }
}

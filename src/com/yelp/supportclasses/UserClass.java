package com.yelp.supportclasses;


public class UserClass {
    private String member_since;
    private String review_count;
    private String no_of_friends;
    private String avg_stars;
    private String no_of_votes;

    private String member_since_operator;
    private String review_count_operator;
    private String no_of_friends_operator;
    private String avg_stars_operator;
    private String no_of_votes_operator;

    private String user_and_or_opeartor;

    public UserClass(String member_since, String review_count, String no_of_friends, String avg_stars, String no_of_votes,
                     String member_since_operator, String review_count_operator, String no_of_friends_operator,
                     String avg_stars_operator, String no_of_votes_operator, String user_and_or_opeartor) {
        this.member_since = member_since;
        this.review_count = review_count;
        this.no_of_friends = no_of_friends;
        this.avg_stars = avg_stars;
        this.no_of_votes = no_of_votes;
        this.member_since_operator = member_since_operator;
        this.review_count_operator = review_count_operator;
        this.no_of_friends_operator = no_of_friends_operator;
        this.avg_stars_operator = avg_stars_operator;
        this.no_of_votes_operator = no_of_votes_operator;
        this.user_and_or_opeartor = user_and_or_opeartor;
    }

    public UserClass(){
        user_and_or_opeartor = "AND";
        member_since_operator = "=";
        review_count_operator = "=";
        no_of_friends_operator = "=";
        avg_stars_operator = "=";
        no_of_votes_operator = "=";
    }

    public String getMember_since() {
        return member_since;
    }

    public void setMember_since(String member_since) {
        this.member_since = member_since;
    }

    public String getReview_count() {
        return review_count;
    }

    public void setReview_count(String review_count) {
        this.review_count = review_count;
    }

    public String getNo_of_friends() {
        return no_of_friends;
    }

    public void setNo_of_friends(String no_of_friends) {
        this.no_of_friends = no_of_friends;
    }

    public String getAvg_stars() {
        return avg_stars;
    }

    public void setAvg_stars(String avg_stars) {
        this.avg_stars = avg_stars;
    }

    public String getNo_of_votes() {
        return no_of_votes;
    }

    public void setNo_of_votes(String no_of_votes) {
        this.no_of_votes = no_of_votes;
    }

    public String getMember_since_operator() {
        return member_since_operator;
    }

    public void setMember_since_operator(String member_since_operator) {
        this.member_since_operator = member_since_operator;
    }

    public String getReview_count_operator() {
        return review_count_operator;
    }

    public void setReview_count_operator(String review_count_operator) {
        this.review_count_operator = review_count_operator;
    }

    public String getNo_of_friends_operator() {
        return no_of_friends_operator;
    }

    public void setNo_of_friends_operator(String no_of_friends_operator) {
        this.no_of_friends_operator = no_of_friends_operator;
    }

    public String getAvg_stars_operator() {
        return avg_stars_operator;
    }

    public void setAvg_stars_operator(String avg_stars_operator) {
        this.avg_stars_operator = avg_stars_operator;
    }

    public String getNo_of_votes_operator() {
        return no_of_votes_operator;
    }

    public void setNo_of_votes_operator(String no_of_votes_operator) {
        this.no_of_votes_operator = no_of_votes_operator;
    }

    public String getUser_and_or_opeartor() {
        return user_and_or_opeartor;
    }

    public void setUser_and_or_opeartor(String user_and_or_opeartor) {
        this.user_and_or_opeartor = user_and_or_opeartor;
    }
}

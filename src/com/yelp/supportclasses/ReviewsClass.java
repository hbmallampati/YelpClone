package com.yelp.supportclasses;

import java.time.LocalDate;

public class ReviewsClass {
    private String from_date;
    private String to_date;
    private String star_operator;
    private String star_value;
    private String votes_operator;
    private String votes_value;

    public ReviewsClass(String from_date, String to_date, String star_operator, String star_value,
                        String votes_operator, String votes_value) {
        this.from_date = from_date;
        this.to_date = to_date;
        this.star_operator = star_operator;
        this.star_value = star_value;
        this.votes_operator = votes_operator;
        this.votes_value = votes_value;
    }

    public String getFrom_date() {
        return from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public String getStar_operator() {
        return star_operator;
    }

    public String getStar_value() {
        return star_value;
    }

    public String getVotes_operator() {
        return votes_operator;
    }

    public String getVotes_value() {
        return votes_value;
    }

    public ReviewsClass(){
        this.star_operator = "=";
        this.votes_operator = "=";
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public void setStar_operator(String star_operator) {
        this.star_operator = star_operator;
    }

    public void setStar_value(String star_value) {
        this.star_value = star_value;
    }

    public void setVotes_operator(String votes_operator) {
        this.votes_operator = votes_operator;
    }

    public void setVotes_value(String votes_value) {
        this.votes_value = votes_value;
    }
}

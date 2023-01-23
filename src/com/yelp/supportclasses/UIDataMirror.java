package com.yelp.supportclasses;

public class UIDataMirror {
    BusinessClass businessClass;
    UserClass userClass;
    ReviewsClass reviewsClass;

    public UIDataMirror(BusinessClass businessClass, UserClass userClass, ReviewsClass reviewsClass) {
        this.businessClass = businessClass;
        this.userClass = userClass;
        this.reviewsClass = reviewsClass;
    }

    public UIDataMirror()
    {

    }

    public BusinessClass getBusinessClass() {
        return businessClass;
    }

    public void setBusinessClass(BusinessClass businessClass) {
        this.businessClass = businessClass;
    }

    public UserClass getUserClass() {
        return userClass;
    }

    public void setUserClass(UserClass userClass) {
        this.userClass = userClass;
    }

    public ReviewsClass getReviewsClass() {
        return reviewsClass;
    }

    public void setReviewsClass(ReviewsClass reviewsClass) {
        this.reviewsClass = reviewsClass;
    }
}


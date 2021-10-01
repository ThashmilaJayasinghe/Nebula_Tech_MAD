package com.example.project;

public class ReviewModel_K {
    String user;
    String review;

    ReviewModel_K(){

    }
    public ReviewModel_K(String user, String review) {
        this.user = user;
        this.review = review;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}


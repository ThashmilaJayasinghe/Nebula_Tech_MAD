package com.example.project;

public class Review {

    private String title;
    private String review;
    private String image;
    private String uid;
    private String lockey;

    public Review() {
    }

    public Review(String title, String review, String image, String uid, String lockey) {
        this.title = title;
        this.review = review;
        this.image = image;
        this.uid = uid;
        this.lockey = lockey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLockey() {
        return lockey;
    }

    public void setLockey(String lockey) {
        this.lockey = lockey;
    }
}

package com.example.project;

public class BookModel_K {
    String author;
    String title;
    String imageUrl;

    BookModel_K() {
    }

    public BookModel_K(String author, String title, String image) {
        this.author = author;
        this.title = title;
        this.imageUrl = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String image) {
        this.imageUrl = image;
    }
}


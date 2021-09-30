package com.example.project;

public class model {

    String author, description, isbn, title,image;

    model(){

    }

    public String getPurl() {
        return image;
    }

    public void setPurl(String image) {
        this.image = image;
    }

    public model(String author, String description, String isbn, String title, String image) {
        this.author = author;
        this.description = description;
        this.isbn = isbn;
        this.title = title;
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

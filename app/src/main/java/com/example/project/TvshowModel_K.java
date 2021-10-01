package com.example.project;

public class TvshowModel_K {
    String genre;
    String title;
    String imageUrl;

    public TvshowModel_K() {
    }

    public TvshowModel_K(String genre, String title, String imageUrl) {
        this.genre = genre;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

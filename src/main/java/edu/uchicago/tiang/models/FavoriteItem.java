package edu.uchicago.tiang.models;

import java.io.Serializable;
import java.util.List;

public class FavoriteItem implements Serializable {

    private String id;
    private String userEmail;
    private String title;
    private String year;
    private String description;
    private String authorName;
    private String link;
    private String preview;

    public FavoriteItem(String id, String userEmail, String title, String year, String description, String authorName, String link, String preview) {
        this.id = id;
        this.userEmail = userEmail;
        this.title = title;
        this.year = year;
        this.description = description;
        this.authorName = authorName;
        this.link = link;
        this.preview = preview;
    }

    public FavoriteItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    @Override
    public String toString() {
        return "FavoriteItem{" +
                "id='" + id + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", description='" + description + '\'' +
                ", authorName='" + authorName + '\'' +
                ", link='" + link + '\'' +
                ", preview='" + preview + '\'' +
                '}';
    }
}



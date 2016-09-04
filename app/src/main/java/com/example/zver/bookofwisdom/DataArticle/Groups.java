package com.example.zver.bookofwisdom.DataArticle;

import java.util.ArrayList;

/**
 * Created 31.07.2016.
 */
public class Groups {

    private String Title;
    private String ImagePath;
    private String Description;
    private ArrayList<Items> items;

    public ArrayList<Items> getItems() {
        return items;
    }

    public void setItems(ArrayList<Items> items) {
        this.items = items;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getTitle() {

        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }




}

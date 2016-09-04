package com.example.zver.bookofwisdom.CustomAdapter;

import android.graphics.Bitmap;

public class ItemsOfRow {

    private String titleName;
    private String titleContent;
    private Bitmap titleImage;

    public String getTitleContent() {
        return titleContent;
    }

    public void setTitleContent(String titleContent) {
        this.titleContent = titleContent;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public Bitmap getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(Bitmap titleImage) {
        this.titleImage = titleImage;
    }
}

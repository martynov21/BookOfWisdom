package com.example.zver.bookofwisdom.DataArticle;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created 31.07.2016.
 */
public class Items {

    private String Title;
    private String Content;
    private String ImagePath;
    private String YearBorn;
    private String ShortTitle;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getYearBorn() {
        return YearBorn;
    }

    public void setYearBorn(String yearBorn) {
        YearBorn = yearBorn;
    }

    public String getShortTitle() {
        return ShortTitle;
    }

    public void setShortTitle(String shortTitle) {
        ShortTitle = shortTitle;
    }

    public Bitmap getImage(Context context) {
        Bitmap image = null;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream in = assetManager.open(getImagePath());
            image = BitmapFactory.decodeStream(in);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        if (image == null) {
            Log.e("##Error", "Bitmap image - null");
        }
        return image;
    }
}

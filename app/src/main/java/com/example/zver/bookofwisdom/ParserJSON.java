package com.example.zver.bookofwisdom;


import android.content.Context;
import android.util.Log;

import com.example.zver.bookofwisdom.DataArticle.Groups;
import com.example.zver.bookofwisdom.DataArticle.Items;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;



public class ParserJSON {

    public final static String LOG_TAG = "myLOG";
    Context context;

    public ParserJSON(Context context) {
        this.context = context;
    }

    public ArrayList<Groups> getDataGroups() throws JSONException {

        String jsonStr =  loadJSONFromAsset(context);

        final String WISDOM_GROUPS = "Groups";
        final String WISDOM_TITLE = "Title";
        final String WISDOM_DESCRIPTION = "Description";
        final String WISDOM_IMAGE_PATH = "GroupHeaderImagePath";


        JSONObject bookJSON = new JSONObject(jsonStr);
        JSONArray rowsGroups = bookJSON.getJSONArray(WISDOM_GROUPS);

        ArrayList<Groups> resultDataGroups = new ArrayList<>();

        for (int i = 0; i < rowsGroups.length(); i++){

            String title;
            String description;
            String imagePath;


            JSONObject rows = rowsGroups.getJSONObject(i);

            title = rows.getString(WISDOM_TITLE);
            description = rows.getString(WISDOM_DESCRIPTION);
            imagePath = rows.getString(WISDOM_IMAGE_PATH);


            Groups groups = new Groups();

            groups.setTitle(title);
            groups.setDescription(description);
            groups.setImagePath(imagePath);


            resultDataGroups.add(groups);
        }

        return resultDataGroups;
    }

    public ArrayList<Items> getDataItems(int position) throws JSONException{

        String jsonStr =  loadJSONFromAsset(context);

        Log.v(LOG_TAG, jsonStr);

        final String WISDOM_GROUPS = "Groups";
        final String WISDOM_ITEMS = "Items";

        final String WISDOM_TITLE_ITEM = "Title";
        final String WISDOM_CONTENT_ITEM = "Content";
        final String WISDOM_IMAGE_PATH_ITEM = "ImagePath";
        final String WISDOM_YEAR_BORN_ITEM = "YearOfBorn";
        final String WISDOM_SHORT_TITLE_ITEM = "ShortTitle";




        ArrayList<Items> arrayItems = new ArrayList<>();

        JSONObject bookJSON = new JSONObject(jsonStr);
        JSONArray rowsGroups = bookJSON.getJSONArray(WISDOM_GROUPS);
        JSONObject rows = rowsGroups.getJSONObject(position);
        JSONArray arrayRows = rows.getJSONArray(WISDOM_ITEMS);

        Log.v(LOG_TAG, rowsGroups.length() + "");

        for (int i = 0; i < arrayRows.length(); i++){


            JSONObject allItem = rows.getJSONArray(WISDOM_ITEMS).getJSONObject(i);

            String titleItem;
            String contentItem;
            String imagePathItem;
            String yearBornItem;
            String shortTitleItem;

            titleItem = allItem.getString(WISDOM_TITLE_ITEM);
            contentItem = allItem.getJSONArray(WISDOM_CONTENT_ITEM).getString(0);
            imagePathItem = allItem.getString(WISDOM_IMAGE_PATH_ITEM);
            yearBornItem = allItem.getString(WISDOM_YEAR_BORN_ITEM);
            shortTitleItem = allItem.getString(WISDOM_SHORT_TITLE_ITEM);

            Items items = new Items();

            items.setTitle(titleItem);
            items.setContent(contentItem);
            items.setImagePath(imagePathItem);
            items.setYearBorn(yearBornItem);
            items.setShortTitle(shortTitleItem);

            arrayItems.add(items);

        }

        return arrayItems;
    }



    public String loadJSONFromAsset(Context context) {

        String json = null;
        try {

            InputStream is = context.getAssets().open("SampleData.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}

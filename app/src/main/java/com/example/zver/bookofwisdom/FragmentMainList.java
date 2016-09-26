package com.example.zver.bookofwisdom;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.zver.bookofwisdom.DataArticle.Groups;
import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.CardExpand;

public class FragmentMainList extends Fragment {

    ArrayList<ItemsOfRow> itemsOfRows;
    final static String myLog = "myLog";


    public FragmentMainList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);


        //PARSE DATA
        try {
            ParserJSON parserJSON = new ParserJSON(getActivity());
            dataInCardView(parserJSON.getDataGroups());

        } catch (JSONException e) {
            Log.e(myLog,"##Error" + e);
        }


        RVAdapter adapter = new RVAdapter(itemsOfRows,getContext());
        rv.setAdapter(adapter);
        return view;
    }

    public void dataInCardView (ArrayList<Groups> dataGroups){
        itemsOfRows = new ArrayList<>();
        for (Groups groups : dataGroups) {
            ItemsOfRow item = new ItemsOfRow();

            item.setTitleName(groups.getTitle());
            item.setTitleContent(groups.getDescription());
            item.setTitleImage(getImage(getActivity(), groups.getImagePath()));

            itemsOfRows.add(item);
        }
    }


    public Bitmap getImage(Context context, String imagePath) {
        Bitmap image = null;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream in = assetManager.open(imagePath);
            image = BitmapFactory.decodeStream(in);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        if (image == null) {
            Log.e(myLog, "Bitmap image - null");
        }
        return image;
    }
}

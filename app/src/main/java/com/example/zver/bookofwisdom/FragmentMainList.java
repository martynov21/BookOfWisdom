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
import android.widget.Toast;

import com.example.zver.bookofwisdom.CustomAdapter.ItemsOfRow;
import com.example.zver.bookofwisdom.DataArticle.Groups;
import com.example.zver.bookofwisdom.DataArticle.RecyclerItemClickListener;

import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FragmentMainList extends Fragment {

    ArrayList<Groups> dataGroups;

    ArrayList<ItemsOfRow> itemsOfRows;


    public FragmentMainList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        itemsOfRows = new ArrayList<>();

        ParserJSON parserJSON = new ParserJSON(getActivity());
        try {
            dataGroups = parserJSON.getDataGroups();


            for (Groups groups : dataGroups) {
                ItemsOfRow item = new ItemsOfRow();

                item.setTitleName(groups.getTitle());
                item.setTitleContent(groups.getDescription());
                item.setTitleImage(getImage(getActivity(), groups.getImagePath()));

                itemsOfRows.add(item);
            }
        } catch (JSONException e) {
            Log.e("Error", e + "");
        }


        RVAdapter adapter = new RVAdapter(itemsOfRows);
        rv.setAdapter(adapter);

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), DetailGroupActivity.class).putExtra(Intent.EXTRA_TEXT, position + "");
                        startActivity(intent);
                    }
                })
        );

//        groupsTitleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getActivity(), DetailGroupActivity.class).putExtra(Intent.EXTRA_TEXT, i + "");
//                startActivity(intent);
//            }
//        });

        return view;
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
            Log.e("Bitmap", "Bitmap null");
        }
        return image;
    }
}

package com.example.zver.bookofwisdom;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zver.bookofwisdom.CustomAdapter.CustomAdapter;
import com.example.zver.bookofwisdom.CustomAdapter.ItemsOfRow;
import com.example.zver.bookofwisdom.DataArticle.Groups;
import com.example.zver.bookofwisdom.DataArticle.Items;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DetailGroupActivity extends AppCompatActivity {

    public final static String LOG_TAG = "myLOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_detail, new FragmentDetailGroup())
                    .commit();
        }

//        FragmentDetailGroup mainFragment = new FragmentDetailGroup();
//        android.support.v4.app.FragmentTransaction fragmentTransaction =
//                getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.container_detail, mainFragment);
//        fragmentTransaction.commit();
    }


    public static class FragmentDetailGroup extends Fragment {


        ListView itemsList;
        View header;
        CustomAdapter adapter;
        ArrayList<Groups> dataGroups;
        ArrayList<Items> dataItems;
        ArrayList<ItemsOfRow> itemsListData;
        static int size;


        public FragmentDetailGroup() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View view = inflater.inflate(R.layout.fragment_detail_group, container, false);

            itemsList = (ListView) view.findViewById(R.id.itemsList);


            dataGroups = new ArrayList<>();
            ParserJSON parserJSON = new ParserJSON(getActivity());
            try {

                dataGroups = parserJSON.getDataGroups();

            } catch (JSONException e) {
                Log.e("Class", e + "");
            }


            Intent intent = getActivity().getIntent();
            if (intent == null && !intent.hasExtra(Intent.EXTRA_TEXT)) {
                return view;
            }

            final int position;
            position = Integer.parseInt(intent.getStringExtra(Intent.EXTRA_TEXT));
            Groups groupsOne;
            groupsOne = dataGroups.get(position);

            Bitmap image = getImage(this.getActivity(), groupsOne.getImagePath());

            header = createHeader(groupsOne.getTitle(), image, groupsOne.getDescription());


            dataItems = new ArrayList<>();
            try {
                dataItems = parserJSON.getDataItems(position);
            } catch (JSONException e) {
                Log.e("Error", e + "");
            }
            size = dataItems.size();

            adapter = new CustomAdapter(getActivity(),R.layout.list_view_detail,dataItems);


            itemsList.addHeaderView(header, "some text for header 2", false);
            itemsList.setAdapter(adapter);

            itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), QuoteActivity.class).putExtra("positionGroup", position + "")
                            .putExtra("positionItem",i -  itemsList.getHeaderViewsCount() + "");
                    startActivity(intent);
                }
            });

            return view;
        }

        public View createHeader(String tileHeader, Bitmap imageHeader, String descriptionHeader) {

            View v = getLayoutInflater(null).inflate(R.layout.header, null);
            ((TextView) v.findViewById(R.id.titleHeader)).setText(tileHeader);
            ((ImageView) v.findViewById(R.id.imageHeader)).setImageBitmap(imageHeader);
            ((TextView) v.findViewById(R.id.descriptionHeader)).setText(descriptionHeader);

            return v;
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

            return image;
        }


    }
}


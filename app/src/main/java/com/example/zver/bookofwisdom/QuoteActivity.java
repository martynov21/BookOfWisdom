package com.example.zver.bookofwisdom;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zver.bookofwisdom.DataArticle.Items;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class QuoteActivity extends AppCompatActivity {

    public static final String myLog = "myLog";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String titleActionBar = this.getIntent().getExtras().getString("title");
        getSupportActionBar().setTitle(titleActionBar);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setCurrentItem(0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public static class PlaceholderFragment extends Fragment {

        public static final String position_item = "position";
        int positionOfGroup;
        int positionOfItems;
        ArrayList<Items> arrayItems;

        TextView descriptionText;
        TextView authorQuote;
        ImageView imageView;

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.positionOfItems = sectionNumber;
            Bundle args = new Bundle();
            args.putInt(position_item, sectionNumber);
            fragment.setArguments(args);


            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_quote, container, false);

            Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);




            descriptionText = (TextView) rootView.findViewById(R.id.descriptionOfQuote);
            authorQuote = (TextView) rootView.findViewById(R.id.authorQuote);
            imageView = (ImageView) rootView.findViewById(R.id.imageQuote);

            positionOfGroup = Integer.parseInt(getActivity().getIntent().getExtras().getString("positionGroup"));



            Log.e(myLog,"positionOfGroup " + positionOfGroup);
            Log.i(myLog,"positionOfItems "+ positionOfItems);



            //Parse JSON
            try {
                arrayItems = new ArrayList<>();
                ParserJSON parserJSON = new ParserJSON(getActivity());
                arrayItems = parserJSON.getDataItems(positionOfGroup);
            } catch (JSONException e) {
                Log.e("Error JSON", e + "");
            }


            imageView.setImageBitmap(arrayItems.get(positionOfItems).getImage(getActivity()));
            descriptionText.setText(arrayItems.get(positionOfItems).getContent());
            authorQuote.setText(arrayItems.get(positionOfItems).getTitle());


            //Bottom buttons group

            ImageButton arrow_right = (ImageButton) rootView.findViewById(R.id.arrow_right);
            ImageButton arrow_left = (ImageButton) rootView.findViewById(R.id.arrow_left);
            ImageButton share_btn = (ImageButton) rootView.findViewById(R.id.btn_share);

            arrow_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    QuoteActivity.mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                }
            });

            arrow_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    QuoteActivity.mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                }
            });


            share_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, arrayItems.get(positionOfItems).getContent()
                            + "\n" + arrayItems.get(positionOfItems).getTitle());
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
            });


            return rootView;
        }



    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return new PlaceholderFragment().newInstance(position);
        }

        @Override
        public int getCount() {
            return 12;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return  position + "";
        }

    }
}

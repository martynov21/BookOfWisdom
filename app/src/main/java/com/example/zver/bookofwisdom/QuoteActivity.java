package com.example.zver.bookofwisdom;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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
import java.util.ArrayList;

public class QuoteActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static ViewPager mViewPager;

    private static int current;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);






        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        int currentItem = Integer.parseInt(this.getIntent().getExtras().getString("positionItem"));
        mViewPager.setCurrentItem(currentItem);
        current = mViewPager.getCurrentItem();


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
            descriptionText = (TextView) rootView.findViewById(R.id.descriptionOfQuote);
            authorQuote = (TextView) rootView.findViewById(R.id.authorQuote);
            imageView = (ImageView) rootView.findViewById(R.id.imageQuote);

           DetailGroupActivity.FragmentDetailGroup fragmentDetailGroup = new DetailGroupActivity.FragmentDetailGroup();


            positionOfGroup = Integer.parseInt(getActivity().getIntent().getExtras().getString("positionGroup"));
            Log.e("positionOfGroup", positionOfGroup + "");

            ParserJSON parserJSON = new ParserJSON(getActivity());
            arrayItems = new ArrayList<>();
            try {
                arrayItems = parserJSON.getDataItems(positionOfGroup);
            }catch (JSONException e){
                Log.e("Error JSON", e + "");
            }



            final ArrayList<String> description = new ArrayList<>();
            ArrayList<String> imagePath = new ArrayList<>();
            final ArrayList<String> title = new ArrayList<>();
            for (Items items : arrayItems){
                description.add(items.getContent());
                imagePath.add(items.getImagePath());
                title.add(items.getTitle());
            }

            Log.i("positionOfItems", positionOfItems + "");

            imageView.setImageBitmap(fragmentDetailGroup.getImage(getContext(), imagePath.get(positionOfItems)));
            descriptionText.setText(description.get(positionOfItems));
            authorQuote.setText(title.get(positionOfItems));


            ImageButton arrow_right = (ImageButton) rootView.findViewById(R.id.arrow_right);
            ImageButton arrow_left = (ImageButton) rootView.findViewById(R.id.arrow_left);
            //ImageButton copy_btn = (ImageButton) rootView.findViewById(R.id.btn_copy);
            ImageButton share_btn = (ImageButton) rootView.findViewById(R.id.btn_share);

            arrow_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    QuoteActivity.mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1);
                }
            });

            arrow_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    QuoteActivity.mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
                }
            });

//            copy_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
//                    clipboard.setText(description.get(positionOfItems));
//                    Log.e("Action: copy_btn", description.get(positionOfItems));
//                    Toast.makeText(getActivity(), "copy is was made", Toast.LENGTH_SHORT).show();
//                }
//            });

            share_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, description.get(positionOfItems)
                            + "\n" + title.get(positionOfItems));
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

            return DetailGroupActivity.FragmentDetailGroup.size;
        }

        @Override
        public CharSequence getPageTitle(int position){
            return "1";
        }

    }
}

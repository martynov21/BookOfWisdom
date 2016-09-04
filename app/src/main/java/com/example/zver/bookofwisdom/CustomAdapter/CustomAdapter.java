package com.example.zver.bookofwisdom.CustomAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zver.bookofwisdom.DataArticle.Items;
import com.example.zver.bookofwisdom.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 14.08.2016.
 */
public class CustomAdapter extends ArrayAdapter<Items> {

    public CustomAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CustomAdapter(Context context, int resource, ArrayList<Items> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.list_view_detail, null);
        }

        Items itemsOfRow = getItem(position);

        if (itemsOfRow != null) {

            TextView titleAuthor = (TextView) view.findViewById(R.id.titleAuthor);
            TextView contentTitle = (TextView) view.findViewById(R.id.contentTitle);

            if (titleAuthor != null) {
                titleAuthor.setText(itemsOfRow.getTitle() + ":");
            }

            if (contentTitle != null) {
                String shortTitle = null;

                int index = 0;
                int saveIndex = 0;
                String huy = itemsOfRow.getContent();

                for (int i = 0; i <= 2; i++) {

                    if (index < huy.length()) {
                        index += itemsOfRow.getContent().indexOf(' ', index + 1);
                        if ( index != -1){
                            saveIndex = index;
                        }

                    }

                }

                if ( index == -1) {
                    shortTitle = itemsOfRow.getContent().substring(0, saveIndex);
                }


                shortTitle = itemsOfRow.getContent().substring(0, index);
                Log.e("myLog", shortTitle + " - " + index + "");
                index = 0;
                contentTitle.setText(shortTitle + " ...");
            }
        }
        return view;
    }
}

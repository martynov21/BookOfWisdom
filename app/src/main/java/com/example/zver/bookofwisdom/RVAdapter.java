package com.example.zver.bookofwisdom;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.example.zver.bookofwisdom.R.attr.theme;

class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    private final static String myLog = "myLog";


    List<ItemsOfRow> items;
    ArrayList<Boolean> booleen;
    private Context context;


    RVAdapter(List<ItemsOfRow> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);

        booleen = new ArrayList<>();
        for (int k = 0; k<items.size(); k++ ){
            booleen.add(false);
        }

        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, int i) {


        Log.e(myLog, booleen.get(i) + " " + personViewHolder.getAdapterPosition());


        personViewHolder.imgGroupImage.setImageBitmap(items.get(i).getTitleImage());
        personViewHolder.txtTitleGroup.setText(items.get(i).getTitleName());
        personViewHolder.txtContentGroup.setText(items.get(i).getTitleContent());

        if (!booleen.get(i)){
            personViewHolder.txtContentGroup.setVisibility(View.GONE);
            personViewHolder.btnCollapse.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.down_collapse));
        }
        if (booleen.get(i)){
            personViewHolder.txtContentGroup.setVisibility(View.VISIBLE);
            personViewHolder.btnCollapse.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.up_collapse));
        }

        personViewHolder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, items.get(personViewHolder.getAdapterPosition()).getTitleContent());
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
            }
        });

        personViewHolder.btnLearnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QuoteActivity.class);
                intent.putExtra("title",items.get( personViewHolder.getAdapterPosition()).getTitleName());
                intent.putExtra("positionGroup", personViewHolder.getAdapterPosition() + "");
                view.getContext().startActivity(intent);
            }
        });

        personViewHolder.imgGroupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QuoteActivity.class);
                intent.putExtra("title",items.get( personViewHolder.getAdapterPosition()).getTitleName());
                intent.putExtra("positionGroup", personViewHolder.getAdapterPosition() + "");
                view.getContext().startActivity(intent);
            }
        });


        personViewHolder.btnCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (personViewHolder.txtContentGroup.getVisibility() == View.GONE){

                    personViewHolder.btnCollapse.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.up_collapse));
                    booleen.set(personViewHolder.getAdapterPosition(),true);
                    expand(personViewHolder.txtContentGroup);

//                    personViewHolder.txtContentGroup.setVisibility(View.VISIBLE);
//                    personViewHolder.btnCollapse.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.up_collapse));
//                    booleen.set(personViewHolder.getAdapterPosition(),true);
                }
                else if (personViewHolder.txtContentGroup.getVisibility() == View.VISIBLE){

                    personViewHolder.btnCollapse.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.down_collapse));
                    booleen.set(personViewHolder.getAdapterPosition(),false);
                    collapse(personViewHolder.txtContentGroup);
                }
            }
        });


    }


    private void expand(final View v) {
        v.measure(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? RecyclerView.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration(((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density))* 2);
        v.startAnimation(a);
    }


    private void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder{

        private CardView cv;

        private TextView txtTitleGroup;
        private TextView txtContentGroup;

        private ImageView imgGroupImage;

        private Button btnShare;
        private ImageButton btnCollapse;
        private Button btnLearnMore;


        PersonViewHolder(final View itemView) {
            super(itemView);


            this.cv = (CardView) itemView.findViewById(R.id.cv);

            this.imgGroupImage = (ImageView) itemView.findViewById(R.id.img_group_image);

            this.txtTitleGroup = (TextView) itemView.findViewById(R.id.txt_title_group);
            this.txtContentGroup = (TextView) itemView.findViewById(R.id.txt_content_group);


            this.btnShare = (Button) itemView.findViewById(R.id.btn_share_group);
            this.btnCollapse = (ImageButton) itemView.findViewById(R.id.btn_collapse);

            this.btnLearnMore = (Button) itemView.findViewById(R.id.btn_learn_more);

        }
    }
}


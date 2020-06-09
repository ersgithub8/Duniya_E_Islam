package ru.ironcodes.islamicwikipedia.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ru.ironcodes.islamicwikipedia.QuranPlayActivity;
import ru.ironcodes.islamicwikipedia.R;

import java.util.ArrayList;
import java.util.List;

import static ru.ironcodes.islamicwikipedia.MyApplication.getInstance;


public class ListAdapterr extends BaseAdapter {

    LayoutInflater inflater;
    private List<Data> Datalist = null;
    private ArrayList<Data> arraylist;
    Context context;

    public ListAdapterr(Activity context, List<Data> openSite) {
        this.context=context;
        this.Datalist = openSite;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(openSite);

    }

    @Override
    public int getCount() {
        return Datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return Datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        View Item =inflater.inflate(R.layout.quran_listview_item, null,true);

        ImageView QuranListIcon = (ImageView) Item.findViewById(R.id.quranitemicoid);
        Drawable quranlisticon1 = getInstance().getResources().getDrawable(R.drawable.quranlist1_1);
        Drawable quranlisticon2 = getInstance().getResources().getDrawable(R.drawable.quranlist1_2);
        Drawable quranlisticon3 = getInstance().getResources().getDrawable(R.drawable.quranlist1_3);
        Drawable quranlisticon4 = getInstance().getResources().getDrawable(R.drawable.quranlist1_4);
        Drawable quranlisticon5 = getInstance().getResources().getDrawable(R.drawable.quranlist1_5);
        Drawable quranlisticon6 = getInstance().getResources().getDrawable(R.drawable.quranlist1_6);
        Drawable quranlisticon7 = getInstance().getResources().getDrawable(R.drawable.quranlist1_7);





        TextView txtTitle = (TextView) Item.findViewById(R.id.titleid);
        txtTitle.setText(Datalist.get(position).getSubject());

        Item.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(context, QuranPlayActivity.class);
                i.putExtra("name",Datalist.get(position).getSubject());
                i.putExtra("url",Datalist.get(position).getLink());
                context.startActivity(i);
            }
        });


        if (position % 1 == 0){
            QuranListIcon.setImageDrawable(quranlisticon1);
        }

        if (position % 2 == 0){
            QuranListIcon.setImageDrawable(quranlisticon2);
        }

        if (position % 3 == 0){
            QuranListIcon.setImageDrawable(quranlisticon3);
        }

        if (position % 4 == 0){
            QuranListIcon.setImageDrawable(quranlisticon4);
        }
        if (position % 5 == 0){
            QuranListIcon.setImageDrawable(quranlisticon5);
        }
        if (position % 6 == 0){
            QuranListIcon.setImageDrawable(quranlisticon6);
        }
        if (position % 7 == 0){
            QuranListIcon.setImageDrawable(quranlisticon7);
        }




        return Item;



    };
}



/**
 * Created by IronCodes on 11.04.2019.
 * Website : http://ironcodes.tech/
 * "All rights reserved ©2014-©2019"
 */
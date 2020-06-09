package ru.ironcodes.islamicwikipedia;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import androidx.core.content.ContextCompat;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;
import androidx.appcompat.app.ActionBar;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by IronCodes on 11.04.2019.
 * Website : http://ironcodes.tech/
 * "All rights reserved ©2014-©2019"
 * Support & Emails : dev@ironcodes.tech , ironcodesdev@gmail.com
 */



public class AzkarsActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<Azkar> imageArry = new ArrayList<Azkar>();
    private AzkarsListAdapter adapter;
    private String Activitytype;
    private DataBaseHandler db;
    private ListView dataList;
    private int count;
    private ImageView noQuotes;
    private AdView adView;

    SlidingPaneLayout mSlidingPanel;
    public static final int IntialQteOfDayId = 8;
    private AlertDialog dialog;
    SharedPreferences preferences;
    private static final int RESULT_SETTINGS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_azkar);

        mSlidingPanel = (SlidingPaneLayout) findViewById(R.id.SlidingPanel);
        mSlidingPanel.setPanelSlideListener(panelListener);
        mSlidingPanel.setParallaxDistance(100);
        mSlidingPanel.setSliderFadeColor(ContextCompat.getColor(this, android.R.color.transparent));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(getString(R.string.app_name));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        db = new DataBaseHandler(this);
        noQuotes = (ImageView)findViewById(R.id.NoQuotes);
        adapter = new AzkarsListAdapter(this, R.layout.azkar_items, imageArry);
        dataList = (ListView) findViewById(R.id.quotesList);
        Button btnLoadMore = new Button(this);

        btnLoadMore.setBackgroundResource(R.drawable.btn_green);
        btnLoadMore.setText(getResources().getText(R.string.btn_LoadMore));
        btnLoadMore.setTextColor(0xffffffff);
        Activitytype = getIntent().getExtras().getString("mode");

        if (Activitytype.equals("isCategory")) {
            String categoryValue = getIntent().getExtras()
                    .getString("category");
            List<Azkar> contacts = db.getAzkarByCategory(categoryValue);
            for (Azkar cn : contacts) {

                imageArry.add(cn);

            }

        }
        if (Activitytype.equals("isSubCat")) {
            String authorValue = getIntent().getExtras().getString("name");
            List<Azkar> contacts = db.getAzkarBySubcat(authorValue);
            for (Azkar cn : contacts) {

                imageArry.add(cn);

            }
            ;

        }

        if (Activitytype.equals("isFavorite")) {
            actionBar.setTitle(getResources().getText(R.string.title_activity_favorites));
            List<Azkar> contacts = db.getFavorites();
            for (Azkar cn : contacts) {

                imageArry.add(cn);

            }
            ;
            if (imageArry.isEmpty()){

                noQuotes.setVisibility(View.VISIBLE);
            }

        }
        if (Activitytype.equals("allAzakers")) {

            List<Azkar> contacts = db.getAllAzkar(" LIMIT 50");
            for (Azkar cn : contacts) {

                imageArry.add(cn);

            }
            ;
            dataList.addFooterView(btnLoadMore);
        }


        dataList.setAdapter(adapter);
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long idInDB) {

                Intent i = new Intent(getApplicationContext(),
                        AzkarActivity.class);
                Azkar srr = imageArry.get(position);
                i.putExtra("id",position);
                i.putExtra("array", imageArry);
                i.putExtra("mode", "");

                startActivity(i);

            }

        });

        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Starting a new async task
                new loadMoreListView().execute();
            }
        });
        adView = new AdView(this);
        adView.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id));
        adView.setAdSize(AdSize.BANNER);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layAdsAzkars);
        layout.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


    }
    private class loadMoreListView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            // Before starting background task
            // Show Progress Dialog etc,.
        }

        protected Void doInBackground(Void... unused) {
            runOnUiThread(new Runnable() {
                public void run() {
                    count += 50;
                    List<Azkar> contacts = db.getAllAzkar(" LIMIT "+count+ ",50");
                    for (Azkar cn : contacts) {

                        imageArry.add(cn);

                    }
                    int currentPosition = dataList.getFirstVisiblePosition();
                    adapter = new AzkarsListAdapter(AzkarsActivity.this, R.layout.azkar_items, imageArry);
                    dataList.setSelectionFromTop(currentPosition + 1, 0);
                }

            });
            return (null);
        }

        protected void onPostExecute(Void unused) {

        }
    }

    @Override
    public void onBackPressed()
    {
        finish();
        super.onBackPressed();  // optional depending on your needs
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_azkar, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                if(mSlidingPanel.isOpen()){
                    mSlidingPanel.closePane();
                }else{
                    mSlidingPanel.openPane();
                }
                break;
            default:
                break;
        }

        return true;
    }

    SlidingPaneLayout.PanelSlideListener panelListener = new SlidingPaneLayout.PanelSlideListener(){

        @Override
        public void onPanelClosed(View arg0) {
            // TODO Auto-genxxerated method stub

        }

        @Override
        public void onPanelOpened(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPanelSlide(View arg0, float arg1) {
            // TODO Auto-generated method stub

        }

    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button0:
                Intent Dashboard = new Intent(AzkarsActivity.this,
                        MainActivity.class);
                SystemClock.sleep(1000);
                startActivity(Dashboard);
                onBackPressed();
                break;

            case R.id.button1:
                Intent intent = new Intent(AzkarsActivity.this,
                        AzkarsActivity.class);
                intent.putExtra("mode", "allAzakers");
                SystemClock.sleep(1000);
                startActivity(intent);
                onBackPressed();
                break;
            case R.id.button2:

                Intent author = new Intent(AzkarsActivity.this,
                        OccasionsActivity.class);
                SystemClock.sleep(1000);
                startActivity(author);
                onBackPressed();
                break;
            case R.id.button3:
                Intent favorites = new Intent(AzkarsActivity.this,
                        AzkarsActivity.class);
                favorites.putExtra("mode", "isFavorite");
                SystemClock.sleep(1000);
                startActivity(favorites);
                onBackPressed();
                break;
            case R.id.button4:
                Intent category = new Intent(AzkarsActivity.this,
                        CategoryActivity.class);
                SystemClock.sleep(1000);
                startActivity(category);
                onBackPressed();
                break;
            case R.id.button5:
                preferences = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());

                Intent quranmain = new Intent(AzkarsActivity.this,
                        Quranlist.class);

                startActivity(quranmain);
                onBackPressed();
                break;
            case R.id.button6:
                mSlidingPanel.closePane();
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AzkarsActivity.this);
                builder.setMessage(getResources().getString(
                        R.string.ratethisapp_msg));
                builder.setTitle(getResources().getString(
                        R.string.ratethisapp_title));
                builder.setPositiveButton(
                        getResources().getString(R.string.rate_it),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                Intent fire = new Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName()));           //ru.quotes.reminder"));
                                startActivity(fire);

                            }
                        });

                builder.setNegativeButton(
                        getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();

                            }
                        });
                dialog = builder.create();
                dialog.show();
                onBackPressed();
                break;
            case R.id.btnSetting:
                mSlidingPanel.closePane();
                Intent i = new Intent(this, UserSettingActivity.class);
                startActivityForResult(i, RESULT_SETTINGS);
                break;
            case R.id.button7:
                Intent About = new Intent(AzkarsActivity.this,
                        AboutActivity.class);
                SystemClock.sleep(500);
                startActivity(About);
                onBackPressed();
                break;
            default:
                break;
        }
    }
}

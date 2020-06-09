package ru.ironcodes.islamicwikipedia;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by IronCodes on 11.04.2019.
 * Website : http://ironcodes.tech/
 * "All rights reserved ©2014-2019"
 * Support & Emails : dev@ironcodes.tech , ironcodesdev@gmail.com
 */


public class OccasionsActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Azkar> imageArry = new ArrayList<Azkar>();
    private SubCategoriesListAdapter adapter;
    private DataBaseHandler db;
    private ListView dataList;
    SearchView searchView;
    private AdView adView;

    SlidingPaneLayout mSlidingPanel;
    public static final int IntialQteOfDayId = 8;
    private AlertDialog dialog;
    SharedPreferences preferences;
    private static final int RESULT_SETTINGS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_occasion);

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

        List<Azkar> authors = db.getAllSubCat("");
        for (Azkar cn : authors) {

            imageArry.add(cn);

        }


        adapter = new SubCategoriesListAdapter(this, R.layout.occasino_items, imageArry);

        dataList = (ListView) findViewById(R.id.listView1);
        dataList.setAdapter(adapter);
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long idInDB) {

                Azkar srr = imageArry.get(position);
                Intent i = new Intent(getApplicationContext(),
                        AzkarsActivity.class);
                i.putExtra("name", srr.getName());
                i.putExtra("mode", "isSubCat");
                startActivity(i);

            }
        });

        adView = new AdView(this);
        adView.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id));
        adView.setAdSize(AdSize.BANNER);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layAdsOccasions);
        layout.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_occasion, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
      //  SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getResources().getString(R.string.search));
        // Configure the search info and add any event listeners
      //  searchView.setSearchableInfo(searchManager
      //          .getSearchableInfo(getComponentName()));

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                imageArry.clear();

                List<Azkar> authors = db.getAllSubCat(searchView.getQuery());
                for (Azkar cn : authors) {

                    imageArry.add(cn);
                }
                dataList.setAdapter(adapter);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                // Here u can get the value "query" which is entered in the
                // search box.
                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onCreateOptionsMenu(menu);
    }

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
                Intent Dashboard = new Intent(OccasionsActivity.this,
                        MainActivity.class);
                SystemClock.sleep(1000);
                startActivity(Dashboard);
                onBackPressed();
                break;

            case R.id.button1:
                Intent intent = new Intent(OccasionsActivity.this,
                        AzkarsActivity.class);
                intent.putExtra("mode", "allAzakers");
                SystemClock.sleep(500);
                startActivity(intent);
                onBackPressed();
                break;
            case R.id.button2:
                Intent quranmain = new Intent(OccasionsActivity.this,
                        Quranlist.class);
                SystemClock.sleep(500);
                startActivity(quranmain);
                onBackPressed();
                break;
            case R.id.button3:
                Intent favorites = new Intent(OccasionsActivity.this,
                        AzkarsActivity.class);
                favorites.putExtra("mode", "isFavorite");
                SystemClock.sleep(500);
                startActivity(favorites);
                onBackPressed();
                break;
            case R.id.button4:
                Intent category = new Intent(OccasionsActivity.this,
                        CategoryActivity.class);
                SystemClock.sleep(500);
                startActivity(category);
                onBackPressed();
                break;
            case R.id.button5:
                preferences = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());

                Intent occasionaz = new Intent(OccasionsActivity.this,
                        OccasionsActivity.class);
                occasionaz.putExtra("mode", "occasions");
                SystemClock.sleep(500);
                startActivity(occasionaz);
                onBackPressed();
                break;
            case R.id.button6:
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        OccasionsActivity.this);
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
                                        Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName()));           //ru.ironcodes.islamicwikipedia"));
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
                SystemClock.sleep(500);
                dialog.show();
                onBackPressed();
                break;
            case R.id.btnSetting:
                mSlidingPanel.closePane();
                Intent i = new Intent(this, UserSettingActivity.class);
                startActivityForResult(i, RESULT_SETTINGS);
                break;

            case R.id.button7:
                Intent About = new Intent(OccasionsActivity.this,
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

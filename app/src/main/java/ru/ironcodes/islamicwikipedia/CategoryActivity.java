package ru.ironcodes.islamicwikipedia;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
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
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;



public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Category> imageArry = new ArrayList<Category>();
    private CategoriesListAdapter adapter;
    private DataBaseHandler db;
    private ListView dataList;
    private AdView adView;

    SlidingPaneLayout mSlidingPanel;
    public static final int IntialQteOfDayId = 8;
    private AlertDialog dialog;
    SharedPreferences preferences;
    private static final int RESULT_SETTINGS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_category);


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

        List<Category> categories = db.getAllCategories();
        for (Category cat : categories) {

            imageArry.add(cat);

        }

        adapter = new CategoriesListAdapter(this, R.layout.category_items,
                imageArry);

        dataList = (ListView) findViewById(R.id.categoryList);
        dataList.setAdapter(adapter);
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long idInDB) {

                Category srr = imageArry.get(position);
                Intent i = new Intent(getApplicationContext(),
                        AzkarsActivity.class);
                i.putExtra("category", srr.getName());
                i.putExtra("mode", "isCategory");
                startActivity(i);

            }
        });
        adView = new AdView(this);
        adView.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id));
        adView.setAdSize(AdSize.BANNER);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layAdsCategories);
        layout.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

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
                Intent Dashboard = new Intent(CategoryActivity.this,
                        MainActivity.class);
                SystemClock.sleep(300);
                startActivity(Dashboard);
                onBackPressed();
                break;

            case R.id.button1:

                Intent allazkars = new Intent(CategoryActivity.this,
                        AzkarsActivity.class);
                allazkars.putExtra("mode", "allAzakers");
                SystemClock.sleep(300);
                startActivity(allazkars);
                onBackPressed();
                break;
            case R.id.button2:
                Intent quranmian = new Intent(CategoryActivity.this,
                        Quranlist.class);
                SystemClock.sleep(300);
                startActivity(quranmian);
                onBackPressed();

                break;
            case R.id.button3:
                Intent favorites = new Intent(CategoryActivity.this,
                        AzkarsActivity.class);
                SystemClock.sleep(300);
                favorites.putExtra("mode", "isFavorite");
                startActivity(favorites);
                onBackPressed();

                break;
            case R.id.button4:
                Intent category = new Intent(CategoryActivity.this,
                        CategoryActivity.class);
                SystemClock.sleep(300);
                startActivity(category);
                onBackPressed();

                break;
            case R.id.button5:
                Intent occasion = new Intent(CategoryActivity.this,
                        OccasionsActivity.class);
                SystemClock.sleep(300);
                startActivity(occasion);
                onBackPressed();
                break;
            case R.id.button6:
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        CategoryActivity.this);
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
                Intent About = new Intent(CategoryActivity.this,
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

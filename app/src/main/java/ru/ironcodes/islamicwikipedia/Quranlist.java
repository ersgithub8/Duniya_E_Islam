package ru.ironcodes.islamicwikipedia;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.core.content.ContextCompat;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import ru.ironcodes.islamicwikipedia.utilities.Data;
import ru.ironcodes.islamicwikipedia.utilities.ListAdapterr;

import java.util.ArrayList;





/**
 * Created by IronCodes on 11.04.2019.
 * Website : http://ironcodes.tech/
 * "All rights reserved ©2014-2019"
 * Support & Emails : dev@ironcodes.tech , ironcodesdev@gmail.com
 */


public class Quranlist extends AppCompatActivity implements View.OnClickListener{

    final Context context = this;
    private AlertDialog dialog;
    SharedPreferences preferences;
    private static final int RESULT_SETTINGS = 1;
    SlidingPaneLayout mSlidingPanel;

    ListView listView;
    ArrayList<Data> arraylist = new ArrayList<>();
    ListAdapterr adapter;
    private AdView adView;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_quran);


        adView = new AdView(this);
        adView.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id));
        adView.setAdSize(AdSize.BANNER);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layAdsQuranList);
        layout.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);




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


        listView = (ListView) findViewById(R.id.listviewid);

        String[] IronCode_Subject = {getString(R.string.Sura1), getString(R.string.Sura2), getString(R.string.Sura3), getString(R.string.Sura4), getString(R.string.Sura5), getString(R.string.Sura6), getString(R.string.Sura7)
                , getString(R.string.Sura8), getString(R.string.Sura9), getString(R.string.Sura10)
                , getString(R.string.Sura11), getString(R.string.Sura12), getString(R.string.Sura13), getString(R.string.Sura14), getString(R.string.Sura15), getString(R.string.Sura16), getString(R.string.Sura17)
                , getString(R.string.Sura18), getString(R.string.Sura19), getString(R.string.Sura20)
                , getString(R.string.Sura21), getString(R.string.Sura22), getString(R.string.Sura23), getString(R.string.Sura24), getString(R.string.Sura25), getString(R.string.Sura26), getString(R.string.Sura27)
                , getString(R.string.Sura28), getString(R.string.Sura29), getString(R.string.Sura30)
                , getString(R.string.Sura31), getString(R.string.Sura32), getString(R.string.Sura33), getString(R.string.Sura34), getString(R.string.Sura35), getString(R.string.Sura36), getString(R.string.Sura37)
                , getString(R.string.Sura38), getString(R.string.Sura39), getString(R.string.Sura40)
                , getString(R.string.Sura41), getString(R.string.Sura42), getString(R.string.Sura43), getString(R.string.Sura44), getString(R.string.Sura45), getString(R.string.Sura46), getString(R.string.Sura47)
                , getString(R.string.Sura48), getString(R.string.Sura49), getString(R.string.Sura50)
                , getString(R.string.Sura51), getString(R.string.Sura52), getString(R.string.Sura53), getString(R.string.Sura54), getString(R.string.Sura55), getString(R.string.Sura56), getString(R.string.Sura57)
                , getString(R.string.Sura58), getString(R.string.Sura59), getString(R.string.Sura60)
                , getString(R.string.Sura61), getString(R.string.Sura62), getString(R.string.Sura63), getString(R.string.Sura64), getString(R.string.Sura65), getString(R.string.Sura66), getString(R.string.Sura67)
                , getString(R.string.Sura68), getString(R.string.Sura69), getString(R.string.Sura70)
                , getString(R.string.Sura71), getString(R.string.Sura72), getString(R.string.Sura73), getString(R.string.Sura74), getString(R.string.Sura75), getString(R.string.Sura76), getString(R.string.Sura77)
                , getString(R.string.Sura78), getString(R.string.Sura79), getString(R.string.Sura80)
                , getString(R.string.Sura81), getString(R.string.Sura82), getString(R.string.Sura83), getString(R.string.Sura84), getString(R.string.Sura85), getString(R.string.Sura86), getString(R.string.Sura87)
                , getString(R.string.Sura88), getString(R.string.Sura89), getString(R.string.Sura90)
                , getString(R.string.Sura91), getString(R.string.Sura92), getString(R.string.Sura93), getString(R.string.Sura94), getString(R.string.Sura95), getString(R.string.Sura96), getString(R.string.Sura97)
                , getString(R.string.Sura98), getString(R.string.Sura99), getString(R.string.Sura100)
                , getString(R.string.Sura101), getString(R.string.Sura102), getString(R.string.Sura103), getString(R.string.Sura104), getString(R.string.Sura105), getString(R.string.Sura106), getString(R.string.Sura107)
                , getString(R.string.Sura108), getString(R.string.Sura109), getString(R.string.Sura110)
                , getString(R.string.Sura111), getString(R.string.Sura112), getString(R.string.Sura113), getString(R.string.Sura114)};


        String[] IronCode_Link = {getString(R.string.Link1), getString(R.string.Link2), getString(R.string.Link3), getString(R.string.Link4), getString(R.string.Link5), getString(R.string.Link6), getString(R.string.Link7)
                , getString(R.string.Link8), getString(R.string.Link9), getString(R.string.Link10)
                , getString(R.string.Link11), getString(R.string.Link12), getString(R.string.Link13), getString(R.string.Link14), getString(R.string.Link15), getString(R.string.Link16), getString(R.string.Link17)
                , getString(R.string.Link18), getString(R.string.Link19), getString(R.string.Link20)
                , getString(R.string.Link21), getString(R.string.Link22), getString(R.string.Link23), getString(R.string.Link24), getString(R.string.Link25), getString(R.string.Link26), getString(R.string.Link27)
                , getString(R.string.Link28), getString(R.string.Link29), getString(R.string.Link30)
                , getString(R.string.Link31), getString(R.string.Link32), getString(R.string.Link33), getString(R.string.Link34), getString(R.string.Link35), getString(R.string.Link36), getString(R.string.Link37)
                , getString(R.string.Link38), getString(R.string.Link39), getString(R.string.Link40)
                , getString(R.string.Link41), getString(R.string.Link42), getString(R.string.Link43), getString(R.string.Link44), getString(R.string.Link45), getString(R.string.Link46), getString(R.string.Link47)
                , getString(R.string.Link48), getString(R.string.Link49), getString(R.string.Link50)
                , getString(R.string.Link51), getString(R.string.Link52), getString(R.string.Link53), getString(R.string.Link54), getString(R.string.Link55), getString(R.string.Link56), getString(R.string.Link57)
                , getString(R.string.Link58), getString(R.string.Link59), getString(R.string.Link60)
                , getString(R.string.Link61), getString(R.string.Link62), getString(R.string.Link63), getString(R.string.Link64), getString(R.string.Link65), getString(R.string.Link66), getString(R.string.Link67)
                , getString(R.string.Link68), getString(R.string.Link69), getString(R.string.Link70)
                , getString(R.string.Link71), getString(R.string.Link72), getString(R.string.Link73), getString(R.string.Link74), getString(R.string.Link75), getString(R.string.Link76), getString(R.string.Link77)
                , getString(R.string.Link78), getString(R.string.Link79), getString(R.string.Link80)
                , getString(R.string.Link81), getString(R.string.Link82), getString(R.string.Link83), getString(R.string.Link84), getString(R.string.Link85), getString(R.string.Link86), getString(R.string.Link87)
                , getString(R.string.Link88), getString(R.string.Link89), getString(R.string.Link90)
                , getString(R.string.Link91), getString(R.string.Link92), getString(R.string.Link93), getString(R.string.Link94), getString(R.string.Link95), getString(R.string.Link96), getString(R.string.Link97)
                , getString(R.string.Link98), getString(R.string.Link99), getString(R.string.Link100)
                , getString(R.string.Link101), getString(R.string.Link102), getString(R.string.Link103), getString(R.string.Link104), getString(R.string.Link105), getString(R.string.Link106), getString(R.string.Link107)
                , getString(R.string.Link108), getString(R.string.Link109), getString(R.string.Link110)
                , getString(R.string.Link111), getString(R.string.Link112), getString(R.string.Link113), getString(R.string.Link114)};


        for (int i = 0; i < IronCode_Subject.length; i++) {
            Data quransurahlist = new Data(IronCode_Subject[i], IronCode_Link[i]);
            arraylist.add(quransurahlist);
        }
        adapter = new ListAdapterr(this, arraylist);

        listView.setAdapter(adapter);

    }



    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent  i = new Intent(Quranlist.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }




    // @Override
    //  public boolean onCreateOptionsMenu(Menu menu) {
    //     Inflate the menu; this adds items to the action bar if it is present.
    //      getMenuInflater().inflate(R.menu.menu_main, menu);
    //     return true;
    // }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                if (mSlidingPanel.isOpen()) {
                    mSlidingPanel.closePane();
                } else {
                    mSlidingPanel.openPane();
                }
                break;
        }

            return super.onOptionsItemSelected(item);

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
                Intent Dashboard = new Intent(Quranlist.this,
                        MainActivity.class);

                SystemClock.sleep(250);
                startActivity(Dashboard);
                break;

            case R.id.button1:
                Intent azkarall = new Intent(Quranlist.this,
                        AzkarsActivity.class);
                azkarall.putExtra("mode", "allAzakers");
                SystemClock.sleep(250);
                startActivity(azkarall);
                break;
            case R.id.button2:
                Intent quranmain = new Intent(Quranlist.this,
                        Quranlist.class);
                SystemClock.sleep(250);
                startActivity(quranmain);
                break;
            case R.id.button3:
                Intent favorites = new Intent(Quranlist.this,
                        AzkarsActivity.class);
                favorites.putExtra("mode", "isFavorite");
                SystemClock.sleep(150);
                startActivity(favorites);

                break;
            case R.id.button4:
                Intent categoryaz = new Intent(Quranlist.this,
                        CategoryActivity.class);
                SystemClock.sleep(150);
                startActivity(categoryaz);
                break;
            case R.id.button5:
                Intent occasions = new Intent(Quranlist.this,
                        OccasionsActivity.class);
                SystemClock.sleep(150);
                startActivity(occasions);
                break;
            case R.id.button6:
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        Quranlist.this);
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
                break;
            case R.id.btnSetting:
                Intent i = new Intent(this, UserSettingActivity.class);
                startActivityForResult(i, RESULT_SETTINGS);
                break;


            case R.id.button7:
                Intent About = new Intent(Quranlist.this,
                        AboutActivity.class);
                SystemClock.sleep(150);
                startActivity(About);
                break;




            default:
                break;
        }
    }








}

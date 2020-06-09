package ru.ironcodes.islamicwikipedia;


import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import androidx.core.content.ContextCompat;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.varunest.sparkbutton.SparkButton;


import java.io.IOException;

import java.util.Locale;

import static ru.ironcodes.islamicwikipedia.DataBaseHandler.DATABASE_NAME;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    DataBaseHandler db;
    private AlertDialog dialog;
    public static final int IntialQteOfDayId = 8;
    private SparkButton btn_azkar, btn_occasions, btn_favorites, btn_categories, btn_quran ,btn_rateus,btn_reminder ,btn_setting;
    final Context context = this;
    SharedPreferences preferences;
    private static final int RESULT_SETTINGS = 1;
    private InterstitialAd interstitial;
    Locale myLocale ;
    public static String langholder;
    SlidingPaneLayout mSlidingPanel;
    ImageView background_main,ourmoon;
    public String USER_LANGUAGES = "user_langu";







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_main);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userlango = preferences.getString(USER_LANGUAGES , "en");

            Locale locale = new Locale(userlango);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());




            ActionBar actionBar = getSupportActionBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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


        mSlidingPanel = (SlidingPaneLayout) findViewById(R.id.SlidingPanel);
        mSlidingPanel.setPanelSlideListener(panelListener);
        mSlidingPanel.setParallaxDistance(100);
        mSlidingPanel.setSliderFadeColor(ContextCompat.getColor(this, android.R.color.transparent));

        background_main =(ImageView) findViewById(R.id.background_main);
//        background_main.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade));




        SharedPreferences sharedPreferences = getSharedPreferences("checkFail1", MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean("checkValue", false);
        if (!check) {
            AlarmReceiver alarmReceiver = new AlarmReceiver();
            int hours = sharedPreferences.getInt("minutes", 60);
            alarmReceiver.setAlarm(context, hours);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("checkValue", true);
            editor.apply();
            Log.d("XXX", "1111");
        }






        db = new DataBaseHandler(this);
        db.openDataBase() ;
        db.refresh();
        db.recreate();

        btn_azkar= (SparkButton) findViewById(R.id.btn_azkar);
        btn_occasions= (SparkButton) findViewById(R.id.btn_occasions);
        btn_reminder= (SparkButton) findViewById(R.id.btn_reminder);
        btn_favorites= (SparkButton) findViewById(R.id.btn_favorites);
        btn_categories= (SparkButton) findViewById(R.id.btn_categories);
        btn_quran= (SparkButton) findViewById(R.id.btn_quran);
        btn_setting= (SparkButton) findViewById(R.id.btn_setting);
        btn_rateus= (SparkButton) findViewById(R.id.btn_rateus);


        btn_azkar.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                interstitial = new InterstitialAd(MainActivity.this);
                interstitial.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
                AdRequest adRequest2 = new AdRequest.Builder().build();

                interstitial.loadAd(adRequest2);
                interstitial.setAdListener(new AdListener() {

                    @Override
                    public void onAdLoaded() {

                        displayInterstitial();

                    }
                });

                btn_azkar.playAnimation();

                Handler handler =new Handler();
                handler.postDelayed ( new Runnable() {

                    public void run() {

                        Intent intent = new Intent(MainActivity.this,
                        AzkarsActivity.class);
                intent.putExtra("mode", "allAzakers");
                startActivity(intent);







                    }
                } ,300);

            }
        });

        btn_occasions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btn_occasions.playAnimation();

                Handler handler =new Handler();
                handler.postDelayed ( new Runnable() {

                    public void run() {

                        Intent occasions = new Intent(MainActivity.this,
                        OccasionsActivity.class);
                        occasions.putExtra("mode", "isSubCat");

                        startActivity(occasions);

                    }
                } ,300);

            }
        });




        btn_reminder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btn_reminder.playAnimation();

                Handler handler =new Handler();
                handler.postDelayed ( new Runnable() {

                    public void run() {

                        Intent azkar_Reminder = new Intent(MainActivity.this,
                                AzkarsTimeSettingsActivity.class);
                        startActivity(azkar_Reminder);

                    }
                } ,300);

            }
        });



        btn_favorites.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btn_favorites.playAnimation();

                Handler handler =new Handler();
                handler.postDelayed ( new Runnable() {

                    public void run() {

                        Intent favorites = new Intent(MainActivity.this,
                        AzkarsActivity.class);
                favorites.putExtra("mode", "isFavorite");
                startActivity(favorites);

                    }
                } ,300);

            }
        });

        btn_categories.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btn_categories.playAnimation();
                Handler handler =new Handler();
                handler.postDelayed ( new Runnable() {

                    public void run() {

                        Intent category = new Intent(MainActivity.this,
                        CategoryActivity.class);
                startActivity(category);


                    }
                } ,300);
            }
        });

        btn_quran.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btn_quran.playAnimation();
                Handler handler =new Handler();
                handler.postDelayed ( new Runnable() {

                    public void run() {

                Intent Quran = new Intent(MainActivity.this,
                        Quranlist.class);

                startActivity(Quran);

                    }
                } ,300);

            }
        });




        btn_setting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btn_setting.playAnimation();

                Handler handler =new Handler();
                handler.postDelayed ( new Runnable() {

                    public void run() {

                        Intent Qoute_Reminder = new Intent(MainActivity.this,
                                UserSettingActivity.class);
                        startActivity(Qoute_Reminder);

                    }
                } ,1250);

            }
        });




        btn_rateus.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                btn_rateus.playAnimation();
                Handler handler =new Handler();
                handler.postDelayed ( new Runnable() {

                    public void run() {

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        MainActivity.this);
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

                    }
                } ,1250);





            }
        });



    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }




    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mainactvity, menu);

        return true;
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

            case R.id.bahasa_lang:
                langholder  = ("id");

                Toast.makeText(MainActivity.this,
                        "You have selected Bahasa Language", Toast.LENGTH_SHORT)
                        .show();
                saveuserlang ();
                DATABASE_NAME=("azkarId.db");
                db.openDataBase();
                setLocale(langholder);

                break;

            case R.id.turk_lang:
                langholder  = ("tr");
                Toast.makeText(MainActivity.this,
                        "You have selected Türkçe Language", Toast.LENGTH_SHORT)
                        .show();
                saveuserlang ();
                DATABASE_NAME=("azkarTr.db");
                db.openDataBase();
                setLocale(langholder);
                break;


            case R.id.arabic_lang:
                langholder  = ("ar");
                Toast.makeText(MainActivity.this,
                        "You have selected Arabic Language", Toast.LENGTH_SHORT)
                        .show();
                saveuserlang ();
                DATABASE_NAME=("azkarabic.db");
                db.openDataBase();
                setLocale(langholder);
                break;


            case R.id.russian_lang:
                langholder  = ("ru");
                Toast.makeText(MainActivity.this,
                        "You have selected Arabic Language", Toast.LENGTH_SHORT)
                        .show();
                saveuserlang ();
                DATABASE_NAME=("azkarRU.db");
                db.openDataBase();
                setLocale(langholder);
                break;


            case R.id.malay_labg:
                langholder  = ("ms");
                Toast.makeText(MainActivity.this,
                        "You have selected Malay Language", Toast.LENGTH_SHORT)
                        .show();
                saveuserlang ();
                DATABASE_NAME=("azkarMs.db");
                db.openDataBase();
                setLocale(langholder);
                break;

            case R.id.eng_labg:
                langholder  = ("en");
                Toast.makeText(MainActivity.this,
                        "You have selected Arabic Language", Toast.LENGTH_SHORT)
                        .show();
                saveuserlang ();
                DATABASE_NAME=("azkarenglish.db");
                db.openDataBase();
                setLocale(langholder);
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
                Intent Dashboard = new Intent(MainActivity.this,
                        MainActivity.class);
                SystemClock.sleep(250);
                startActivity(Dashboard);
                onBackPressed();
                break;

            case R.id.button1:
                Intent azkarall = new Intent(MainActivity.this,
                        AzkarsActivity.class);
                azkarall.putExtra("mode", "allAzakers");
                SystemClock.sleep(250);
                startActivity(azkarall);
                onBackPressed();
                break;
            case R.id.button2:
                Intent holyquran = new Intent(MainActivity.this,
                        Quranlist.class);
                SystemClock.sleep(250);
                startActivity(holyquran);
                onBackPressed();
                break;
            case R.id.button3:
                Intent favorites = new Intent(MainActivity.this,
                        AzkarsActivity.class);
                favorites.putExtra("mode", "isFavorite");
                SystemClock.sleep(250);
                startActivity(favorites);
                onBackPressed();
                break;
            case R.id.button4:
                Intent category = new Intent(MainActivity.this,
                        CategoryActivity.class);
                SystemClock.sleep(250);
                startActivity(category);
                onBackPressed();
                break;

            case R.id.button5:
                Intent occasion = new Intent(MainActivity.this,
                        OccasionsActivity.class);
                occasion.putExtra("mode", "azkarday");
                SystemClock.sleep(500);
                startActivity(occasion);
                onBackPressed();
                break;
            case R.id.button6:
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        MainActivity.this);
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
                Intent About = new Intent(MainActivity.this,
                        AboutActivity.class);
                SystemClock.sleep(500);
                startActivity(About);
                onBackPressed();
                break;




            default:
                break;
        }
    }





    public void saveuserlang () {

        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
        prefEditor.putString(USER_LANGUAGES, langholder);
        prefEditor.apply();


    }

    public void setLocale (String lang){

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent rego = new Intent(this, MainActivity.class);
        startActivity(rego);
        this.recreate();
        restart();
        this.finish();





    }



    public void onStart() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userlango = preferences.getString(USER_LANGUAGES , "en");

        if (userlango !=null) {

            Locale locale = new Locale(userlango);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        }

        super.onStart();
    }




    public void restart(){
        Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);
        db.recreate();

    }

    public void reload()   {
        db.close();
        copybase();
        db.refresh();
        db = new DataBaseHandler(this);
        db.openDataBase() ;
        db.getAllCategories();

    }

    protected void copybase() {
        db = new DataBaseHandler(this);

        try {
            db.CopyDataBaseFromAsset();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    public void recreate () {
        super.recreate();
        db.recreate();


    }


    }

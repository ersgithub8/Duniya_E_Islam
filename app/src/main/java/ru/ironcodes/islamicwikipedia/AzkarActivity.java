package ru.ironcodes.islamicwikipedia;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import androidx.appcompat.app.ActionBar;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by IronCodes on 11.04.2019.
 * Website : http://ironcodes.tech/
 * "All rights reserved ©2014-©2019"
 * Support & Emails : dev@ironcodes.tech , ironcodesdev@gmail.com
 */


public class AzkarActivity extends AppCompatActivity  {

    private int ID;
    private String mode,fav,text;
    private Azkar ziker;
    private DataBaseHandler db;
    private ArrayList<Azkar> myList = new ArrayList<>();
    private TextView textAuth,textQuote;
    private ImageView imgIcon;
    private ImageButton btnNext,btnPrevious;
    private TextToSpeech tts;
    private RoundImage roundedImage;
    private AdView adView;
    private InterstitialAd interstitial;
    SharedPreferences sharedPrefs;
    public String USER_LANGUAGES = "user_langu";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azkar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userlango = preferences.getString(USER_LANGUAGES , "en");
        Locale locale = new Locale(userlango);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());


        db = new DataBaseHandler(this);
        textAuth = (TextView) findViewById(R.id.textAuth);
        textQuote = (TextView) findViewById(R.id.textazkar);
        imgIcon = (ImageView) findViewById(R.id.imgcon);
        btnNext = (ImageButton) findViewById(R.id.btn_next);
        btnPrevious = (ImageButton) findViewById(R.id.btn_prev);
        Typeface fontQuote = Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-Light.ttf");
        Typeface fontAuth = Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-Italic.ttf");
        textQuote.setTypeface(fontQuote);
        textQuote.setTextSize(21);
        textAuth.setTypeface(fontAuth);
        ID = getIntent().getExtras().getInt("id");
        mode = getIntent().getExtras().getString("mode");
        if(mode.equals("zikerday")){
            ziker = db.getAzkar(ID);
            btnNext.setVisibility(View.GONE);
            btnPrevious.setVisibility(View.GONE);
        }
        else {
            myList = (ArrayList<Azkar>) getIntent().getSerializableExtra("array");
            ziker = myList.get(ID);}
        db = new DataBaseHandler(this);

        textAuth.setText(ziker.getName());
        textQuote.setText(ziker.getAzkar());
        checkPicure();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ID < (myList.size() - 1)) {

                    ID++;
                    ziker = myList.get(ID);
                    textAuth.setText(ziker.getName());
                    textQuote.setText(ziker.getAzkar());
                    checkPicure();
                }
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ID > 0) {

                    ID--;
                    ziker = myList.get(ID);
                    textAuth.setText(ziker.getName());
                    textQuote.setText(ziker.getAzkar());
                    checkPicure();
                }
            }
        });

        db = new DataBaseHandler(this);
        fav = ziker.getFav();



        adView = new AdView(this);
        adView.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id));
        adView.setAdSize(AdSize.BANNER);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layAdsazkar);
        layout.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        if(mode.equals("allAzakers")){


            interstitial = new InterstitialAd(this);
            interstitial.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
            AdRequest adRequest2 = new AdRequest.Builder().build();

            interstitial.loadAd(adRequest2);
            interstitial.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {

                    displayInterstitial();

                }
            });
        }


    }

    @Override
    public void onDestroy() {

        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    public void checkPicure(){
        boolean isExist = false;
        InputStream imageStream = null;
        try {
            imageStream = getAssets().open("subcategories/"+ziker.getFileName()+".png");

            isExist =true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        if (isExist != false){
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            roundedImage = new RoundImage(theImage);
            imgIcon.setImageDrawable(roundedImage );
        }
        else {
            Bitmap bm = BitmapFactory.decodeResource(getResources(),R.mipmap.azkar);
            roundedImage = new RoundImage(bm);
            imgIcon.setImageDrawable(roundedImage);
        }

    }



    public void doShare() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Azkar Reminder");
        intent.putExtra(Intent.EXTRA_TEXT,
                ziker.getAzkar() + "  - " + ziker.getName());
        AzkarActivity.this.startActivity(Intent.createChooser(intent,
                getResources().getString(R.string.share)));

    }


    private void speakOut() {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean speaker = sharedPrefs.getBoolean("prefSpeaker", true);

        if (speaker.equals(true)) {
            text = ziker.getAzkar() + "\n" + ziker.getName();
            if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            }
            else {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_azkar, menu);
        if (fav.equals("0")) {
            menu.findItem(R.id.action_favorite).setIcon(R.mipmap.not_fav);

        }
        if (fav.equals("1")) {
            menu.findItem(R.id.action_favorite).setIcon(R.mipmap.fav);

        }
        ;

        return true;
    }

    @TargetApi(11)
    private void copyToClipBoard(String ziker) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            clipboard.setText(ziker);
        } else {

            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("text", ziker);
            clipboard.setPrimaryClip(clip);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;

            case R.id.action_share:

                doShare();

                break;

            case R.id.copy:
                String text = ziker.getAzkar() + "- " + ziker.getName();
                copyToClipBoard(text);
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.copy_msg),
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.action_favorite:
                if (ziker.getFav().equals("0")) {
                    ziker.setFav("1");
                    db.updateAzkar(ziker);
                    item.setIcon(R.mipmap.fav);
                } else if (ziker.getFav().equals("1")) {
                    ziker.setFav("0");
                    db.updateAzkar(ziker);
                    item.setIcon(R.mipmap.not_fav);




                }
                break;

            case R.id.menu_overflow:
                //just override click
                return true;





        }


        return true;
    }



    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }
}

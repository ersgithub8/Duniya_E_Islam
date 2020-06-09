package ru.ironcodes.islamicwikipedia;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by IronCodes on 11.04.2019.
 * Website : http://ironcodes.tech/
 * "All rights reserved ©2014-2019"
 * Support & Emails : dev@ironcodes.tech , ironcodesdev@gmail.com
 */


public class SchedulingServiceToast extends IntentService  {

    private DataBaseHandler db;
    private Azkar azkar;
    Button azkarshow ;

    public SchedulingServiceToast() {
        super("SchedulingServiceToast");
    }

    @Override
    public void onCreate() {
        super.onCreate();



    }




    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SharedPreferences sharedPreferences = getSharedPreferences("checkFail1", MODE_PRIVATE);
        int idNumber = sharedPreferences.getInt("ID", 7);

        db = new DataBaseHandler(this);

        int id = db.getLastInsertId();
        if (id <= idNumber) {
            azkar = db.getAzkar(7);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("ID", 7);
            editor.apply();
            ShowToastInIntentService(azkar.getName(), azkar.getAzkar(), azkar.getCategory());
            Log.d("XXX", "2" + id);
        } else {
            azkar = db.getAzkar(idNumber);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            idNumber += 1;
            editor.putInt("ID", idNumber);
            editor.apply();
            if (azkar.getName() != null) {

                ShowToastInIntentService(azkar.getName(), azkar.getAzkar(), azkar.getCategory());

                Log.d("XXX", "3 " + id);

                Log.d("XXX", "ID : " + id + " - Number : " + idNumber);

            }else {
                db = new DataBaseHandler(this);
                azkar= db.getAzkar(idNumber);

            }



        }



    }



    public void ShowToastInIntentService(final String toastText1, final String toastText2, final String toastText3) {
        Log.d("XXX", "666");
        final  int toastDuration = 12000;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.toast, null);
                TextView subcategories = (TextView) layout.findViewById(R.id.subcategoryid);
                TextView azkartext = (TextView) layout.findViewById(R.id.azkartextid);
                TextView categoriesname = (TextView) layout.findViewById(R.id.categoryid);
                subcategories.setText(toastText1);
                azkartext.setText(toastText2);
                categoriesname.setText(toastText3);
                final Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 100);
                toast.setView(layout);
                // toast.getView().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                CountDownTimer toastCountDown;

                toastCountDown =new CountDownTimer(toastDuration,5000) {


                    public void onClick(){
                        stoptoast();

                    }
                    public void stoptoast () {

                        toast.cancel();

                    }

                    public void onTick(long millisUntilFinished) {
                        toast.show();
                    }

                    public void onFinish() {
                        toast.show();

                    }


                }.start();

                toastCountDown.start();

            }

        });



    }



}

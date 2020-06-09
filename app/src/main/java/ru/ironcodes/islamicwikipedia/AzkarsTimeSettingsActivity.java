package ru.ironcodes.islamicwikipedia;

import android.content.SharedPreferences;
import android.os.Handler;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import com.varunest.sparkbutton.SparkButton;


public class AzkarsTimeSettingsActivity extends AppCompatActivity {
    private RadioButton Radio_5_m,Radio_15_m ,Radio_60_m, Radio_120_m,Radio_180_m;
    private SparkButton donebut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azkars_time_settings);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);


        TextView textView = (TextView)findViewById(R.id.text);

        final SharedPreferences sharedPreferences = getSharedPreferences("checkFail1", MODE_PRIVATE);
        int hours = sharedPreferences.getInt("minutes", 5);

        Radio_5_m= (RadioButton) findViewById(R.id.Radio_5_m);
        Radio_15_m= (RadioButton) findViewById(R.id.Radio_15_m);
        Radio_60_m= (RadioButton) findViewById(R.id.Radio_60_m);
        Radio_120_m= (RadioButton) findViewById(R.id.Radio_120_m);
        Radio_180_m= (RadioButton) findViewById(R.id.Radio_180_m);
        donebut= (SparkButton) findViewById(R.id.donebut);

        switch (hours) {
            case 5:
                textView.setText(R.string.current_5m);
                Radio_5_m.setChecked(true);

                break;
            case 15:
                textView.setText(R.string.current_15m);
                Radio_15_m.setChecked(true);
                break;
            case 60:
                textView.setText(R.string.current_60m);
                Radio_60_m.setChecked(true);
                break;
            case 120:
                textView.setText(R.string.current_120m);
                Radio_120_m.setChecked(true);
                break;
            case 180:
                textView.setText(R.string.current_180m);
                Radio_180_m.setChecked(true);
                break;
        }


        findViewById(R.id.Radio_5_m).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmReceiver alarmReceiver = new AlarmReceiver();
                alarmReceiver.setAlarm(getApplicationContext(), 5);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("minutes", 5);
                Radio_15_m.setChecked(false);
                Radio_60_m.setChecked(false);
                Radio_120_m.setChecked(false);
                Radio_180_m.setChecked(false);
                editor.apply();


            }
        });
        findViewById(R.id.Radio_15_m).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmReceiver alarmReceiver = new AlarmReceiver();
                alarmReceiver.setAlarm(getApplicationContext(), 15);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("minutes", 15);
                Radio_5_m.setChecked(false);
                Radio_60_m.setChecked(false);
                Radio_120_m.setChecked(false);
                Radio_180_m.setChecked(false);
                editor.apply();


            }
        });
        findViewById(R.id.Radio_60_m).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmReceiver alarmReceiver = new AlarmReceiver();
                alarmReceiver.setAlarm(getApplicationContext(), 60);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("minutes", 60);
                Radio_5_m.setChecked(false);
                Radio_15_m.setChecked(false);
                Radio_120_m.setChecked(false);
                Radio_180_m.setChecked(false);
                editor.apply();

            }
        });
        findViewById(R.id.Radio_120_m).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmReceiver alarmReceiver = new AlarmReceiver();
                alarmReceiver.setAlarm(getApplicationContext(), 120);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("minutes", 120);
                Radio_5_m.setChecked(false);
                Radio_60_m.setChecked(false);
                Radio_15_m.setChecked(false);
                Radio_180_m.setChecked(false);
                editor.apply();


            }
        });
        findViewById(R.id.Radio_180_m).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmReceiver alarmReceiver = new AlarmReceiver();
                alarmReceiver.setAlarm(getApplicationContext(), 180);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("minutes", 180);
                Radio_5_m.setChecked(false);
                Radio_60_m.setChecked(false);
                Radio_120_m.setChecked(false);
                Radio_15_m.setChecked(false);
                editor.apply();

            }
        });
        findViewById(R.id.donebut).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                donebut.playAnimation();
                Handler handler =new Handler();
                handler.postDelayed ( new Runnable() {

                    public void run() {
                finish();


                    }
                } ,1200);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.\

        switch (item.getItemId()) {

            case android.R.id.home: {

                finish();
                return true;
            }

            default: {

                return super.onOptionsItemSelected(item);
            }
        }
    }
}

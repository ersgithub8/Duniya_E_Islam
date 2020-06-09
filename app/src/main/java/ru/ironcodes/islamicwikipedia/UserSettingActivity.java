package ru.ironcodes.islamicwikipedia;

import java.util.Calendar;
import java.util.Date;


import android.app.Dialog;
import android.content.Intent;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatSpinner;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.TimePicker;


/**
 * Created by IronCodes on 11.04.2019.
 * Website : http://ironcodes.tech/
 * "All rights reserved ©2014-2019"
 * Support & Emails : dev@ironcodes.tech , ironcodesdev@gmail.com
 */

public class UserSettingActivity extends PreferenceActivity {

    private SharedPreferences sharedPrefs;
    private Preference mytime, myPref, time;
    private AlertDialog dialog;
    private long summary;
    static final int DIALOG_ID = 10;

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        // Allow super to try and create a view first
        final View result = super.onCreateView(name, context, attrs);
        if (result != null) {
            return result;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // If we're running pre-L, we need to 'inject' our tint aware Views in place of the
            // standard framework versions
            switch (name) {
                case "EditText":
                    return new AppCompatEditText(this, attrs);
                case "Spinner":
                    return new AppCompatSpinner(this, attrs);
                case "CheckBox":
                    return new AppCompatCheckBox(this, attrs);
                case "RadioButton":
                    return new AppCompatRadioButton(this, attrs);
                case "CheckedTextView":
                    return new AppCompatCheckedTextView(this, attrs);
            }
        }

        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        addPreferencesFromResource(R.xml.settings);

        sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(UserSettingActivity.this);
        myPref = (Preference) findPreference("about");
        mytime = (Preference) findPreference("mytime");
        time = (Preference) findPreference("time");
        summary = sharedPrefs.getLong("userTime", 0);
        if (summary != 0) {
            mytime.setSummary(DateFormat.getTimeFormat(getBaseContext())
                    .format(summary));
        } else {
            mytime.setSummary("");
        }

        myPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        UserSettingActivity.this);
                builder.setMessage(getResources().getString(
                        R.string.about_msg));
                builder.setTitle(getResources().getString(
                        R.string.title_about));
                builder.setPositiveButton("OK", null);

                dialog = builder.create();
                dialog.show();
                return true;
            }
        });

        mytime.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                showDialog(DIALOG_ID);

                return false;
            }
        });

        time.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                startActivity(new Intent(UserSettingActivity.this, AzkarsTimeSettingsActivity.class));

                return false;
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ID:
                int hour,
                        minute;
                final Calendar c = Calendar.getInstance();
                if (summary != 0) {
                    c.setTimeInMillis(sharedPrefs.getLong("userTime",
                            System.currentTimeMillis()));
                }
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
                TimePickerDialog tpk = new TimePickerDialog(this, timePickerListener, hour, minute,
                        false);
                return new TimePickerDialog(this, timePickerListener, hour, minute,
                        false);

        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

        public void onTimeSet(TimePicker view, int pickerHour,
                              int pickerMinute) {
            Calendar Cal = Calendar.getInstance();
            int hour = pickerHour;
            int minute = pickerMinute;

            Cal.setTimeInMillis(System.currentTimeMillis());
            Cal.set(Calendar.HOUR_OF_DAY, hour);
            Cal.set(Calendar.MINUTE, minute);
            Cal.set(Calendar.SECOND, 0);

            Editor e = sharedPrefs.edit();
            e.putLong("userTime", Cal.getTimeInMillis());
            e.commit();
            mytime.setSummary(DateFormat.getTimeFormat(getBaseContext())
                    .format(new Date(Cal.getTimeInMillis())));

            new AlarmTask(UserSettingActivity.this, Cal).run();
        }
    };
}
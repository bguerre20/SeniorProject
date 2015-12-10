package com.ben.bryan.wilburn.roomies;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Activity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.nio.channels.CancelledKeyException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class ClaimCar extends Activity {

    private static TextView editTextStartDate;
    private static TextView editTextEndDate;
    private static TextView editTextStartTime;
    private static TextView editTextEndTime;

    static Date startDate;
    static Date endDate;
    static int  sHour;
    static int sMinute;
    static int eHour;
    static int eMinute;



    private DatePicker dpResult;
    private Button btnStartDate;

    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claim_car);

        editTextStartDate = (TextView) findViewById(R.id.editTextStartDate);
        editTextEndDate = (TextView) findViewById(R.id.editTextEndDate);
        editTextStartTime = (TextView) findViewById(R.id.editTextStartTime);
        editTextEndTime  = (TextView) findViewById(R.id.editTextEndTime);

    }

    public void StartDateClick(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "Start Date");
    }

    public void EndDateClick(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "End Date");
    }

    public void StartTimeClick(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "Start Time");
    }

    public void EndTimeClick(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "End Time");
    }

    public void FinalizeClaimClick (View view) {
        String start = "";
        String end = "";

        start = editTextStartDate.getText() + "-" + editTextStartTime.getText();
        end = editTextEndDate.getText() + "-" + editTextEndTime.getText();

        ParseUser user = ParseUser.getCurrentUser();
        String apt = user.getString("Apartment");
        ParseObject claim = new ParseObject("Claim");

        claim.put("StartDate", start);
        claim.put("EndDate", end);
        claim.put("Name", user.getString("displayname"));
        claim.put("Apartment", apt);
        try {
            claim.save();

            finish();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),start + "\n" + end, Toast.LENGTH_LONG).show();

    }



    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            long miliDate = c.getTimeInMillis();

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(month,day,year);


            if (this.getTag().toString().equals("Start Date")) {
                editTextStartDate.setText((month + 1) + "-" + day + "-" + year);
                startDate = new Date (c.getTimeInMillis());
            }

            else if (this.getTag().toString().equals("End Date")) {
                editTextEndDate.setText((month + 1) + "-" + day + "-" + year);
                endDate = new Date (c.getTimeInMillis());
            }
        }
    }


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


            if (this.getTag().toString().equals("Start Time")) {
                String newMinute = String.format(":%02d", minute);
                String newHour = String.format("%02d", hourOfDay);
                editTextStartTime.setText((newHour) + newMinute);
                sHour = hourOfDay;
                sMinute = minute;
            }

            else if (this.getTag().toString().equals("End Time")) {
                String newMinute = String.format(":%02d", minute);
                String newHour = String.format("%02d", hourOfDay);
                editTextEndTime.setText((newHour) +  (newMinute));
                eHour = hourOfDay;
                eMinute = minute;
            }
        }
    }

}

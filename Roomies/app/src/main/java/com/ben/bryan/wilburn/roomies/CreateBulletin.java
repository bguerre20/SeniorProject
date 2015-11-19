package com.ben.bryan.wilburn.roomies;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import com.parse.*;

import java.util.Date;

public class CreateBulletin extends Activity {

    private String name;
    private String description;
    private String date;
    private Date finalDate;

    private EditText edittext_name;
    private EditText edittext_description;
    private CalendarView editcalendar_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_bulletin);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        edittext_name = (EditText) findViewById(R.id.editText);
        edittext_description = (EditText) findViewById(R.id.editText2);
        editcalendar_date = (CalendarView) findViewById(R.id.calendarView);
    }
    public void CreateClick (View view) throws ParseException {
        name = edittext_name.getText().toString();
        description = edittext_description.getText().toString();

        finalDate = new Date(editcalendar_date.getDate());

        ParseUser user = ParseUser.getCurrentUser();
        String apt = user.getString("Apartment");
        ParseObject bulletin = new ParseObject("BulletinBoard");

        bulletin.put("NotificationDate", finalDate);
        bulletin.put("Notification", description);
        bulletin.put("Name", name);
        bulletin.put("Apartment", apt);
        bulletin.saveInBackground();
        //super.finish();
        //super.startActivity(super.getIntent());
        //finish();
    }

    public void CancelClick (View view) throws ParseException {
        finish();
    }

}

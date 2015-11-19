package com.ben.bryan.wilburn.roomies;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.parse.*;

public class CreateBulletin extends Activity {

    private String name;
    private String description;
    private String date;

    private EditText edittext_name;
    private EditText edittext_description;
    private EditText edittext_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_bulletin);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        edittext_name = (EditText) findViewById(R.id.editText);
        edittext_description = (EditText) findViewById(R.id.editText2);
        edittext_date = (EditText) findViewById(R.id.calendarView);
    }
    public void CreateClick (View view) throws ParseException {
        name = edittext_name.getText().toString();
        description = edittext_description.getText().toString();
        date = edittext_date.getText().toString();

        ParseUser user = ParseUser.getCurrentUser();
        ParseObject bulletin = new ParseObject("BulletinBoard");

        bulletin.put("NotificationDate", date);
        bulletin.put("Notification", description);
        bulletin.put("Name", name);
        bulletin.put("Apartment", user.get("Apartment"));
        bulletin.saveInBackground();
        finish();
    }

    public void CancelClick (View view) throws ParseException {
        finish();
    }

}

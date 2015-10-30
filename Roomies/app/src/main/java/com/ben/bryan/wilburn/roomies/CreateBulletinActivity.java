package com.ben.bryan.wilburn.roomies;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.parse.*;

public class CreateBulletinActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bulletin);
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void CreateClick (View view) throws ParseException {
        String name;
        String description;
        String date;


        EditText viewText = (EditText) findViewById(R.id.editText);
        name = viewText.getText().toString();

        viewText = (EditText) findViewById(R.id.editText2);
        description = viewText.getText().toString();

        viewText = (EditText) findViewById(R.id.calendarView);
        date = viewText.getText().toString();

        ParseUser user = ParseUser.getCurrentUser();

        ParseObject bulletin = new ParseObject("GameScore");
        bulletin.put("DeleteDate", date);
        bulletin.put("Notification", description);
        bulletin.put("Name", name);
        bulletin.put("Apartment", user.get("Apartment"));
        bulletin.saveInBackground();
    }

    public void CancelClick (View view) throws ParseException {

    }

}

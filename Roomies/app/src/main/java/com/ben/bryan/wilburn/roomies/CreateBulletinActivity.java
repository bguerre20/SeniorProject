package com.ben.bryan.wilburn.roomies;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import com.parse.*;

public class CreateBulletinActivity extends Activity {

    private String name;
    private String discription;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bulletin);
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void CreateClick (View view) throws ParseException {
        name = "";
        discription = "";
        date = "";
        ParseUser user = ParseUser.getCurrentUser();
        ParseObject bulletin = new ParseObject("GameScore");
        bulletin.put("DeleteDate", date);
        bulletin.put("Notification", discription);
        bulletin.put("Name", name);
        bulletin.put("Apartment", user.get("Apartment"));
    }

    public void CancelClick (View view) throws ParseException {

    }

}

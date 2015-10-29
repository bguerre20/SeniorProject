package com.ben.bryan.wilburn.roomies;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.app.IntentService;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;
import java.util.Arrays;


public class MyActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    // Variables
    ParseUser user = ParseUser.getCurrentUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        /* Create new ParseObject Apartment
        ParseObject apartment = new ParseObject("Apartment");
        apartment.put("userId", user.getObjectId());
        apartment.saveInBackground(); */

        // background listener for notifications
        // ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void EmsButtonClick(View view) {
        Intent intent = new Intent(this, EmsActivity.class);
        String message = "Hello 2nd activity!";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void ChoreClick(View view) {
        Intent intent = new Intent(this, ChoreActivity.class);
        String message = "Hello 2nd activity!";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void BulletinBoardClick(View view) {
        Intent intent = new Intent(this, BulletinBoardActivity.class);
        String message = "Hello 2nd activity!";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void SettingsClick(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        String message = "Hello 2nd activity!";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void LogoutClick(View view) {
        finish();
    }
}
/*
        ParseUser user = new ParseUser();

        user.setUsername("Bryan");
        user.setPassword("Guerre");
        user.setEmail("bguerre12@gmail.com");
        // other fields can be set just like with ParseObject
        user.put("phone", "951-795-9262");
        String ID = ParseInstallation.getCurrentInstallation().getInstallationId();
        user.put("phoneID", ID);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    // / Hooray! Let them use the app now.
                    int a = 5;
                }
                else {
                    // Sign up didn't succeed.
                    // Look at the ParseException
                    // to figure out what went wrong
                    int b = 2;
                }
            }
        });
        */
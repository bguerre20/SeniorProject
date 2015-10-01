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
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;


public class MyActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        //background listener for notifications
        Parse.initialize(this, "k1dHjdoF6RirSdBbn1vlVtG23MS16dIODIHDUzAx", "57JGIqDoufHntyBojqi1q0jWSfvYDr0JCE70aVHt");
        ParseInstallation.getCurrentInstallation().saveInBackground();

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

}

package com.ben.bryan.wilburn.roomies;

import android.os.Bundle;
import android.app.Activity;

import com.parse.*;

import java.util.List;

public class BulletinBoardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_board);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ParseUser user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> boardQuery = ParseQuery.getQuery("BulletinBoard");;
        boardQuery.whereEqualTo("Apartment", user.get("Apartment"));
        boardQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                } else {
                    // Something went wrong.
                }
            }
        });
    }

}

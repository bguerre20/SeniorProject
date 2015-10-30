package com.ben.bryan.wilburn.roomies;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ChoreActivity extends Activity {

    private ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    int clickCounter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chore);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        ParseUser user = ParseUser.getCurrentUser();
        final ParseQuery<ParseObject> boardQuery = ParseQuery.getQuery("Chore");
        boardQuery.whereEqualTo("Apartment", user.get("Apartment"));
        boardQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    for (int i = 0; i < objects.size(); i++) {
                        listItems.add(objects.get(i).getString("Name"));
                    }
                } else {
                    // Something went wrong.
                }
            }
        });

    }

}

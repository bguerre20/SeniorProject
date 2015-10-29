package com.ben.bryan.wilburn.roomies;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;

import com.parse.*;

import java.util.ArrayList;
import java.util.List;

public class BulletinBoardActivity extends Activity {

    private ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    int clickCounter=0;


    protected void onCreate(Bundle savedInstanceState, Bundle icicle) {
        super.onCreate(savedInstanceState);

        super.onCreate(icicle);
        setContentView(R.layout.activity_bulletin_board);
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);



        setContentView(R.layout.activity_bulletin_board);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ParseUser user = ParseUser.getCurrentUser();
        final ParseQuery<ParseObject> boardQuery = ParseQuery.getQuery("BulletinBoard");
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


    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v) {
        listItems.add("Clicked : " + clickCounter++);
        adapter.notifyDataSetChanged();
    }

}

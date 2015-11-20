package com.ben.bryan.wilburn.roomies;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.*;

import java.util.ArrayList;
import java.util.List;

public class BulletinBoard extends Activity {

    private ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    int clickCounter=0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //super.onCreate(icicle);
        setContentView(R.layout.bulletin_board);
        ParseUser user = ParseUser.getCurrentUser();
        final ParseQuery<ParseObject> boardQuery = ParseQuery.getQuery("BulletinBoard");
        boardQuery.whereEqualTo("Apartment", user.get("Apartment"));
        boardQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    for (int i = 0; i < objects.size(); i++) {
                        listItems.add(objects.get(i).getString("Name") + " " +
                                objects.get(i).getDate("NotificationDate").toString() + " " +
                                objects.get(i).getString("Notification"));
                    }
                } else {
                    // Something went wrong.
                }

                String[] strArray = new String[listItems.size()];
                for (int i = 0; i < listItems.size(); i++) {
                    strArray[i] = listItems.get(i);
                }

                adapter = new ArrayAdapter<String>(BulletinBoard.this,
                        android.R.layout.simple_list_item_1, strArray);

                ListView myList = (ListView) findViewById(R.id.listView2);
                myList.setAdapter(adapter);
            }
        });


    }


    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v) {
        listItems.add("Clicked : " + clickCounter++);
        adapter.notifyDataSetChanged();
    }

    public void CreateBClick(View view) {
        Intent intent = new Intent(this, CreateBulletin.class);
        String message = "Hello 2nd activity!";
        intent.putExtra("message", message);
        startActivity(intent);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
            return true; // you missed this line
        }
        return super.onKeyDown(keyCode, event);
    }
}

package com.ben.bryan.wilburn.roomies;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Chore extends Activity {
    private ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    int clickCounter=0;
    ListView myList;
    String itemValue;
    private String name;
    private String description;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chore);


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


                String[] strArray = new String[listItems.size()];
                for (int i = 0; i < listItems.size(); i++) {
                    strArray[i] = listItems.get(i);
                }

                adapter = new ArrayAdapter<String>(Chore.this,
                        android.R.layout.simple_list_item_1, strArray);

                myList = (ListView) findViewById(R.id.listView);
                myList.setAdapter(adapter);
                myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        // ListView Clicked item index
                        int itemPosition = position;

                        // ListView Clicked item value
                        itemValue = (String) myList.getItemAtPosition(position);

                        // Show Alert
                        Toast.makeText(getApplicationContext(),
                                "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                                .show();

                    }

                });
            }
        });



    }


    public void CreateClick (View view) throws ParseException {
        Intent intent = new Intent(this, CreateChore.class);
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

package com.ben.bryan.wilburn.roomies;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class CarSharing extends Activity{

    private int selectedIndex = 0;


    EditText costEditText;
    ListView listViewCarShare;
    ArrayList<String> claimedData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_sharing);

        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, claimedData.toArray(new String[claimedData.size()]));
        costEditText = (EditText)findViewById(R.id.edit_text_cost);
        listViewCarShare = (ListView)findViewById(R.id.listViewCarShare);
        listViewCarShare.setAdapter(adp2);

        getClaimData();

        // ListView Item Click Listener
        listViewCarShare.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listViewCarShare.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();

            }

        });
    }



    public void LogButtonOnClick(View view) {
        double costOfFillup = Double.parseDouble(costEditText.getText().toString());

    }

    public void ClaimButtonOnClick(View view) {

    }

    public void UnclaimButtonOnClick(View view) {

    }

    public void getClaimData() {
        //claimData.Add() -> data from database
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

package com.ben.bryan.wilburn.roomies;

import android.content.Intent;
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

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class CarSharing extends Activity{

    private int selectedIndex = 0;

    EditText costEditText;
    ListView listViewCarShare;
    ArrayList<String> claimedData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_sharing);


        costEditText = (EditText)findViewById(R.id.edit_text_cost);
        listViewCarShare = (ListView)findViewById(R.id.listViewCarShare);

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
        Fillup f = new Fillup();
        f.sendNewFillup(costOfFillup, ParseUser.getCurrentUser());
    }

    public void ClaimButtonOnClick(View view) {
        Intent intent = new Intent(this, ClaimCar.class);
        startActivity(intent);
    }

    public void UnclaimButtonOnClick(View view) {

    }

    public void getClaimData() {
        ParseQuery<ParseObject> claimQ = ParseQuery.getQuery("Claim");
        ParseUser user = ParseUser.getCurrentUser();
        claimQ.whereEqualTo("Apartment", user.getString("Apartment"));
        try {
            List<ParseObject> parseList = claimQ.find();

            for(ParseObject PO : parseList) {
                claimedData.add(PO.getString("Name") + ": " + PO.getString("StartDate") + "," + PO.getString("EndDate"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, claimedData.toArray(new String[claimedData.size()]));

        listViewCarShare.setAdapter(adp2);
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

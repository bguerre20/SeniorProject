package com.ben.bryan.wilburn.roomies;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class CreateChore extends Activity {

    private String name;
    private String description;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_chore);
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void CreateCClick (View view) throws ParseException {

        EditText viewText = (EditText) findViewById(R.id.editText);
        name = viewText.getText().toString();

        viewText = (EditText) findViewById(R.id.editText2);
        description = viewText.getText().toString();

        viewText = (EditText) findViewById(R.id.editText4);
        date = viewText.getText().toString();

        ParseUser user = ParseUser.getCurrentUser();
        ParseObject chore = new ParseObject("Chore");
        chore.put("Value", date);
        chore.put("Discription", description);
        chore.put("Name", name);
        chore.put("Apartment", user.get("Apartment"));

        chore.saveInBackground();
        finish();
    }

    public void CancelClick (View view) throws ParseException {
        finish();
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

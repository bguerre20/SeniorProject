package com.ben.bryan.wilburn.roomies;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;

import com.parse.ParseUser;


public class MainMenu extends Activity {

    public final static String EXTRA_MESSAGE = "extra message";
    // Variables
    ParseUser user = ParseUser.getCurrentUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
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
        Intent intent = new Intent(this, Ems.class);
        String message = "Hello 2nd activity!";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void ChoreClick(View view) {
        Intent intent = new Intent(this, Chore.class);
        String message = "Hello 2nd activity!";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void BulletinBoardClick(View view) {
        Intent intent = new Intent(this, BulletinBoard.class);
        String message = "Hello 2nd activity!";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


    public void LogoutClick(View view) {
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
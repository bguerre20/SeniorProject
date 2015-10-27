package com.ben.bryan.wilburn.roomies;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class LoginActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void LoginClick(View view) {
        Intent intent = new Intent(this, MyActivity.class);
        String message = "Hello 2nd activity!";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        startActivity(intent);
    }

    public void RegisterClick(View view) {
        Intent intent = new Intent(this, MyActivity.class);
        String message = "Hello 2nd activity!";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        startActivity(intent);
    }

    public void ExitClick(View view) {
        finish();
        System.exit(0);
    }
}

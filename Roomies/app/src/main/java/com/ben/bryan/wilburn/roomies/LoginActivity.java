package com.ben.bryan.wilburn.roomies;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.LogInCallback;
import com.parse.ParseException;

public class LoginActivity extends Activity {
    // Declare variables
    Button button_login;
    Button button_register;
    Button button_exit;
    String string_username;
    String string_password;
    EditText edittxt_username;
    EditText edittxt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Locate EditTexts in activity_login.xml
        edittxt_username = (EditText) findViewById(R.id.edittxt_username);
        edittxt_password = (EditText) findViewById(R.id.edittxt_password);

        // Locate Buttons in activity_login.xml
        button_login = (Button) findViewById(R.id.button_login);
        button_register = (Button) findViewById(R.id.button_register);
        button_exit = (Button) findViewById(R.id.button_exit);

        // Login Button Click Listener
        button_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                string_username = edittxt_username.getText().toString();
                string_password = edittxt_password.getText().toString();

                // Check if string_email or string_password is empty
                if(string_username.equals("") || string_password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter a email and/or password.",
                            Toast.LENGTH_LONG).show();
                } else {
                    // Send data to Parse.com for verification
                    ParseUser.logInInBackground(string_username, string_password,
                            new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if (user != null) {
                                        // If user exist and authenticated, send user to
                                        // MyActivity.class
                                        Intent intent = new Intent(LoginActivity.this,
                                                MyActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(),
                                                "Successfully Logged in.",
                                                Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(),
                                                "No such user exist, please Try Again or Register.",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        // Register Button Click Listener
        button_register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Send user to RegisterActivity.class
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Logout Button Click Listener
        button_exit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(getApplicationContext(),
                        "Goodbye!", Toast.LENGTH_LONG).show();
                finish();
                System.exit(0);
            }
        });
    }
}

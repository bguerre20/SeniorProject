package com.ben.bryan.wilburn.roomies;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;



public class EmsActivity extends Activity {

    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get the message from intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);

        setContentView(R.layout.activity_ems);

        createButtons(readLocal());
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

    public void AddMessageClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Message Input");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                addSingleButton(m_Text);
                writeLocal(m_Text + ",");
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        //show the button
        builder.show();
    }
    
    private void writeLocal(String messageToSave) {
        File f = new File("EmsMessages");
        if (f.exists()) {
            try {
                FileOutputStream outputStream = openFileOutput("EmsMessages", Context.MODE_APPEND);
                outputStream.write(messageToSave.getBytes());
                outputStream.close();
            }
            catch(Exception E) {
                Toast.makeText(this, "Error writing to file", Toast.LENGTH_SHORT);
            }
        }
        else {
            try {
                FileOutputStream outputStream = openFileOutput("EmsMessages", Context.MODE_APPEND);
                outputStream.write(messageToSave.getBytes());
                outputStream.close();
            }
            catch(Exception E) {
                Toast.makeText(this, "Error writing to file", Toast.LENGTH_SHORT);
            }
        }
    }

    private String[] readLocal() {
        String[] messages = null;
        try {
            FileInputStream inputStream = openFileInput("EmsMessages");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            messages = (sb.toString()).split(",");
        }
        catch(Exception e) {
            Toast.makeText(this, "error in read local", Toast.LENGTH_SHORT);
        }
        return messages;
    }

    private void createButtons(String[] buttonMessages) {
        if (buttonMessages == null || buttonMessages[0] == "") {
            //do nothing
        }
        else {
            for (int i = 0; i < buttonMessages.length; i++) {
                addSingleButton(buttonMessages[i]);
            }
        }
    }

    private void addSingleButton(String message) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.EmsLinearLayout);
        Button button1 = new Button(this);

        button1.setText(message);
        button1.setId(R.id.EmsLinearLayout);
        button1.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        button1.setPadding(16, 16, 16, 0);
        button1.setOnClickListener(getOnClickDoSomething(button1));
        linearLayout.addView(button1);
    }

    View.OnClickListener getOnClickDoSomething(final Button b) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                sendMessage(b.getText().toString());
            }
        };
    }

    private void sendMessage(String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("7609363116",null, message, null, null);
        Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
    }

    public void ClearMessages(View view) {
        try {
            String blank = "";
            FileOutputStream outputStream = openFileOutput("EmsMessages", Context.MODE_PRIVATE);
            outputStream.write(blank.getBytes());
            outputStream.close();
        }
        catch(Exception E) {
            Toast.makeText(this, "Error writing to file", Toast.LENGTH_SHORT);
        }
        finish();
        startActivity(getIntent());
    }
}

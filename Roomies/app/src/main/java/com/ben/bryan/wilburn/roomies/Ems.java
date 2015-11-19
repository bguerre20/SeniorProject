package com.ben.bryan.wilburn.roomies;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Ems extends Activity {

    private String m_Text = "";
    private int index = 0;
    private List<String> nlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ems);
        createButtons(readLocal());
        nlist = new ArrayList<String>();
        getUserList();
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
            } catch (Exception E) {
                Toast.makeText(this, "Error writing to file", Toast.LENGTH_SHORT);
            }
        } else {
            try {
                FileOutputStream outputStream = openFileOutput("EmsMessages", Context.MODE_APPEND);
                outputStream.write(messageToSave.getBytes());
                outputStream.close();
            } catch (Exception E) {
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
        } catch (Exception e) {
            Toast.makeText(this, "error in read local", Toast.LENGTH_SHORT);
        }
        return messages;
    }

    private void createButtons(String[] buttonMessages) {
        if (buttonMessages == null || buttonMessages[0] == "") {
            //do nothing
        } else {
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
                chooseDeviceToken(b.getText().toString());
            }
        };
    }

    private void sendMessage(final String message, String whoToSendTo) {

        //String whoToSendTo = chooseDeviceToken();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("displayname", whoToSendTo);

        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> list, ParseException e) {
                if (e == null) {

                    ParseUser user1 = list.get(0);
                    String test = user1.getString("phone");


                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(test, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();


                } else {
                    Log.d("id", "Error: " + e.getMessage());
                }
            }
        });



    }
    public String chooseDeviceToken(final String mssage) {
        index = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Recipient")
           .setItems(nlist.toArray(new String[nlist.size()]) , new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
                   // The 'which' argument contains the index position
                   // of the selected item
                   index = which;
                   sendMessage(mssage, nlist.get(index));
               }
           });
        builder.show();
        return nlist.get(index);
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

    private List<String> getUserList() {
        nlist.clear();
        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.whereEqualTo("Apartment", ParseUser.getCurrentUser().getString("Apartment"));
        userQuery.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    for (int i = 0; i < objects.size(); i++) {
                        nlist.add(objects.get(i).getString("displayname"));
                    }
                } else {
                    // Something went wrong.
                }
            }
        });
        return nlist;
    }
}

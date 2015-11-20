package com.ben.bryan.wilburn.roomies;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseUser;

public class AddFinancial extends Activity {

    String[] users;
    double[] paidVals;
    LinearLayout mainLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_financial);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
           users  = (String[])extras.get("passedUserList");
        }
         mainLL = (LinearLayout)findViewById(R.id.mainLinearLayout);

        if (users.length > 0) {
            LinearLayout[] newLayouts = new LinearLayout[users.length];

            for (int i = 0; i < users.length; i++) {
                View child = getLayoutInflater().inflate(R.layout.member_layout_card, null);
                TextView label = (TextView)child.findViewById(R.id.TextView);
                EditText inputField = (EditText)child.findViewById(R.id.editTextValue);

                label.setText(users[i]);
                mainLL.addView(child);
            }
        }
    }

    public void DoneClick(View view) {
        paidVals = new double[users.length];
        for(int i = 0; i < users.length; i++)
        {

            String textViewString = ((EditText)mainLL.findViewById(R.id.editTextValue)).getText().toString();
            paidVals[i] = Double.parseDouble(textViewString);
        }


        Intent result = new Intent(this, AddFinancial.class);
        result.putExtra("returnedData", paidVals);
        result.putExtra("returnedDesc", "TEST DESCRIPTION");
        setResult(RESULT_OK, result);
        finish();
    }

    public void fCancelClick(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

}

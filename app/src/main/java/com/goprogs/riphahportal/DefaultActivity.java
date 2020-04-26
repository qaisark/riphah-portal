package com.goprogs.riphahportal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DefaultActivity extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("loggedin", null);
        if (restoredText != null) {
            if(restoredText.equals("true"))
            {
                Intent intent = new Intent(DefaultActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else
            {
                setContentView(R.layout.activity_default);
            }
        }
        else {
            setContentView(R.layout.activity_default);
        }
    }
    public void defaultSignupaction(View v)
    {
        Intent intent = new Intent(DefaultActivity.this, SignupActivity.class);
        startActivity(intent);
    }
    public void defaultloginaction(View v)
    {
        Intent intent = new Intent(DefaultActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}

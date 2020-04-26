package com.goprogs.riphahportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    Button clickButton;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String HttpUrl = "https://riphahportal.com/public/API/User-Registration.php";
    String NameHolder, EmailHolder, PasswordHolder ;
    Boolean CheckEditText ;

    Button clickbtnlogin ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(SignupActivity.this);

        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(SignupActivity.this);
        clickButton = (Button) findViewById(R.id.signupregisterbtn);
        clickButton.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){
                    EditText passconfirm =(EditText) findViewById(R.id.signuppassconfirm);
                    if(passconfirm.getText().toString().equals(PasswordHolder)) {
                        // If any of EditText is filled then set variable value as True.
                        UserRegistration();
                    }
                    else
                    {
                        Toast.makeText(SignupActivity.this, "Password & Confirm Password not matched.", Toast.LENGTH_LONG).show();

                    }


                }
                else {

                    Toast.makeText(SignupActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
    public void clicklogin(View v)
    {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;
        //noinspection SimplifiableIfStatement
        if (id == R.id.search_icon) {
            Toast.makeText(SignupActivity.this, "Register", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void UserRegistration(){

        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(SignupActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(SignupActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("User_Email", EmailHolder);
                params.put("User_Password", PasswordHolder);
                params.put("User_Full_Name", NameHolder);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }
    public void CheckEditTextIsEmptyOrNot(){

        // Getting values from EditText.
        // Assigning ID's to EditText.
        // Creating EditText.
        EditText FullName, Email, Password ;
        FullName = (EditText) findViewById(R.id.signupname);

        Email = (EditText) findViewById(R.id.signupemail);

        Password = (EditText) findViewById(R.id.signuppass);

        NameHolder = FullName.getText().toString().trim();
        EmailHolder = Email.getText().toString().trim();
        PasswordHolder = Password.getText().toString().trim();

        // Checking whether EditText value is empty or not.
        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
        {

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        }
        else {

                // If any of EditText is filled then set variable value as True.
                CheckEditText = true;
        }
    }
}

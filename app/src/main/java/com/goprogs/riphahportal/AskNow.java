package com.goprogs.riphahportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class AskNow extends AppCompatActivity{
    // Creating button;
    Button asknowbtn;
    ProgressDialog progressDialog;
    Boolean CheckEditText;
    EditText title, desc,tags;
    String HttpUrl = "https://riphahportal.com/public/API/ask_now.php";
    String titleHolder, descHolder,tagsHolder;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_now);

        title = (EditText) findViewById(R.id.edituname);
        desc = (EditText) findViewById(R.id.editskills);
        tags = (EditText) findViewById(R.id.editedu);
        progressDialog = new ProgressDialog(AskNow.this);
        asknowbtn = (Button) findViewById(R.id.asknowbtnn);

        // Adding click listener to button.
        asknowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckIsEmptyOrNot();

                if (CheckEditText) {
                    AddQuestion();
                } else {

                    Toast.makeText(AskNow.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }
            }
        });
        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarasknow);
        setSupportActionBar(toolbar);

        // Create Navigation drawer and inlfate layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            VectorDrawableCompat indicator
                    = VectorDrawableCompat.create(getResources(), R.drawable.ic_menu, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(),R.color.white,getTheme()));
            supportActionBar.setHomeAsUpIndicator(indicator);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        // Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state
                        menuItem.setChecked(true);

                        // TODO: handle navigation
                        int id = menuItem.getItemId();

                        if (id == R.id.nav_ask_now) {
                            Intent intent = new Intent(AskNow.this, AskNow.class);
                            startActivity(intent);
                        } else if (id == R.id.nav_profile) {
                            Intent intent = new Intent(AskNow.this, UserActivity.class);
                            startActivity(intent);

                        } else if (id == R.id.nav_all_questions) {
                            Intent intent = new Intent(AskNow.this, AllQuestions.class);
                            startActivity(intent);
                        }
                        else if (id == R.id.nav_my_questions) {
                            Intent intent = new Intent(AskNow.this, UserQuestions.class);
                            startActivity(intent);
                        }else if (id == R.id.nav_explore) {

                        } else if (id == R.id.nav_search) {
                            Intent intent = new Intent(AskNow.this, SearchActivity.class);
                            startActivity(intent);
                        }
                        else if (id == R.id.nav_logout) {
                            Intent intent = new Intent(AskNow.this, logout.class);
                            startActivity(intent);
                        }
                        else if (id == R.id.nav_home) {
                            Intent intent = new Intent(AskNow.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            return true;
                        }
                        // Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

    }
    public void AddQuestion()
    {
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
                        Toast.makeText(AskNow.this, ServerResponse, Toast.LENGTH_LONG).show();
                        title.setText("");
                        desc.setText("");
                        tags.setText("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(AskNow.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("title", titleHolder);
                params.put("tags", tagsHolder);
                params.put("description", descHolder);
                params.put("user_id", "56");
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(AskNow.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
    public void CheckIsEmptyOrNot() {

        // Getting values from EditText.
        titleHolder = title.getText().toString().trim();
        descHolder = desc.getText().toString().trim();
        tagsHolder = tags.getText().toString().trim();

        // Checking whether EditText value is empty or not.
        if (TextUtils.isEmpty(titleHolder) || TextUtils.isEmpty(descHolder) || TextUtils.isEmpty(tagsHolder)) {

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        } else {

            // If any of EditText is filled then set variable value as True.
            CheckEditText = true;
        }
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
        //noinspection SimplifiableIfStatement
        if (id == R.id.search_icon) {
            Intent intent = new Intent(AskNow.this, SearchActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.notif_icon) {
            Intent intent = new Intent(AskNow.this, NotificationActivity.class);
            startActivity(intent);
        }
        else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}

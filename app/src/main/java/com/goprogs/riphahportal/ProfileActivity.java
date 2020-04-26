package com.goprogs.riphahportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    TextView tvname,tvedu,tvskills,tvabtme,tvques,tvans;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    private DrawerLayout mDrawerLayout;
    // Storing server url into String variable.
    String HttpUrl = "https://riphahportal.com/API/user_api_handler.php?action=fetch_single&id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int user_id = prefs.getInt("user_id", 0);
        // Receiving value into activity using intent.
        //int id = Integer.parseInt(getIntent().getStringExtra("Id"));
        if(user_id!=0&&user_id!=-1)
        {
            HttpUrl=HttpUrl+user_id;
            progressDialog = new ProgressDialog(ProfileActivity.this);
            getProfile();
        }
        else
        {
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);

        }
        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarprofile);
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
                            Intent intent = new Intent(ProfileActivity.this, AskNow.class);
                            startActivity(intent);
                        } else if (id == R.id.nav_profile) {
                            Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                            startActivity(intent);

                        } else if (id == R.id.nav_all_questions) {
                            Intent intent = new Intent(ProfileActivity.this, AllQuestions.class);
                            startActivity(intent);
                        }
                        else if (id == R.id.nav_my_questions) {
                            Intent intent = new Intent(ProfileActivity.this, UserQuestions.class);
                            startActivity(intent);
                        }else if (id == R.id.nav_explore) {

                        } else if (id == R.id.nav_search) {
                            Intent intent = new Intent(ProfileActivity.this, SearchActivity.class);
                            startActivity(intent);
                        }
                        else if (id == R.id.nav_logout) {
                            Intent intent = new Intent(ProfileActivity.this, logout.class);
                            startActivity(intent);
                        }
                        else if (id == R.id.nav_home) {
                            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
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
            Intent intent = new Intent(ProfileActivity.this, SearchActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.notif_icon) {
            Intent intent = new Intent(ProfileActivity.this, NotificationActivity.class);
            startActivity(intent);
        }
        else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
    // Creating user login function.
    public void getProfile() {

        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        //get request
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String id = jsonResponse.getString("id");
                            String name = jsonResponse.getString("name");
                            String edu = jsonResponse.getString("edu");
                            String skills = jsonResponse.getString("skills");
                            String aboutme = jsonResponse.getString("aboutme");
                            String questions = jsonResponse.getString("ques");
                            String answers = jsonResponse.getString("ans");
                            String picture = jsonResponse.getString("picture");

                            tvname = (TextView) findViewById(R.id.tvname);
                            tvedu = (TextView) findViewById(R.id.edu);
                            tvskills = (TextView) findViewById(R.id.skills);
                            tvabtme = (TextView) findViewById(R.id.aboutme);
                            tvques = (TextView) findViewById(R.id.questions);
                            tvans = (TextView) findViewById(R.id.answers);

                            tvname.setText(name);
                            tvedu.setText("Degree: "+edu);
                            tvskills.setText("Skills: "+skills);
                            tvabtme.setText("About Me: "+aboutme);
                            tvques.setText("Question Asked: "+questions);
                            tvans.setText("Answers: "+answers);
                            ImageView img = findViewById(R.id.profile);

                            Picasso.with(getBaseContext()).load("https://riphahportal.com/storage/profiles/"+picture).into(img);
                            progressDialog.dismiss();
                        }catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvname = (TextView) findViewById(R.id.tvname);
                tvname.setText("That didn't work!");
            }
        });


        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

}

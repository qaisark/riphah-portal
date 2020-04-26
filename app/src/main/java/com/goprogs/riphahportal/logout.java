package com.goprogs.riphahportal;

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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class logout extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    // Creating button;
    Button logoutbtn;
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        logoutbtn = (Button) findViewById(R.id.btnlogout);

        // Adding click listener to button.
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("loggedin", "false");
                editor.putInt("user_id", -1);
                editor.apply();

                Toast.makeText(logout.this, "Logged out successfully!.", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(logout.this, LoginActivity.class);
                //intent.putExtra("Id",user_id);
                startActivity(intent);

            }
        });
        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
                            Intent intent = new Intent(logout.this, AskNow.class);
                            startActivity(intent);
                        } else if (id == R.id.nav_profile) {
                            Intent intent = new Intent(logout.this, ProfileActivity.class);
                            startActivity(intent);

                        } else if (id == R.id.nav_all_questions) {
                            Intent intent = new Intent(logout.this, AllQuestions.class);
                            startActivity(intent);
                        }
                        else if (id == R.id.nav_my_questions) {
                            Intent intent = new Intent(logout.this, UserQuestions.class);
                            startActivity(intent);
                        }else if (id == R.id.nav_explore) {

                        } else if (id == R.id.nav_search) {
                            Intent intent = new Intent(logout.this, SearchActivity.class);
                            startActivity(intent);
                        }
                        else if (id == R.id.nav_logout) {
                            Intent intent = new Intent(logout.this, logout.class);
                            startActivity(intent);
                        }
                        else if (id == R.id.nav_home) {
                            Intent intent = new Intent(logout.this, MainActivity.class);
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
            Intent intent = new Intent(logout.this, SearchActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.notif_icon) {
            Intent intent = new Intent(logout.this, NotificationActivity.class);
            startActivity(intent);
        }
        else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}

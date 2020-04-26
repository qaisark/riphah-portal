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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllQuestions extends AppCompatActivity {
    public List<Query> querylist = new ArrayList<>();
    private RecyclerView recyclerView;
    private QueryAdapter mAdapter;
    ProgressDialog progressDialog;
    private DrawerLayout mDrawerLayout;

    final String url = "https://riphahportal.com/public/API/question_api_handler.php?action=fetch_all_ques";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_questions);

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarallquestions);
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
                            Intent intent = new Intent(AllQuestions.this, AskNow.class);
                            startActivity(intent);
                        } else if (id == R.id.nav_profile) {
                            Intent intent = new Intent(AllQuestions.this, UserActivity.class);
                            startActivity(intent);

                        } else if (id == R.id.nav_all_questions) {
                            Intent intent = new Intent(AllQuestions.this, AllQuestions.class);
                            startActivity(intent);
                        }
                        else if (id == R.id.nav_my_questions) {
                            Intent intent = new Intent(AllQuestions.this, UserQuestions.class);
                            startActivity(intent);
                        }else if (id == R.id.nav_explore) {

                        } else if (id == R.id.nav_search) {
                            Intent intent = new Intent(AllQuestions.this, SearchActivity.class);
                            startActivity(intent);
                        }
                        else if (id == R.id.nav_logout) {
                            Intent intent = new Intent(AllQuestions.this, logout.class);
                            startActivity(intent);
                        }
                        else if (id == R.id.nav_home) {
                            Intent intent = new Intent(AllQuestions.this, MainActivity.class);
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
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(AllQuestions.this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);

        mAdapter = new QueryAdapter(querylist);

        recyclerView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

        getQuestions();

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Query movie = querylist.get(position);
                //Toast.makeText(getApplicationContext(), movie.getId() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AllQuestions.this, question.class);
                intent.putExtra("id",movie.getId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void getQuestions()
    {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject question = response.getJSONObject(i);
                                String title    = question.getString("title");
                                String qcreated_at    = question.getString("created_at");
                                String qname    = question.getString("name");
                                int id = question.getInt("ID");
                                Query query = new Query(id,title,qcreated_at,qname);
                                querylist.add(query);

                            }
                            mAdapter.notifyDataSetChanged();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ErrorResponse", error.toString());
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(AllQuestions.this);

        // add it to the RequestQueue
        requestQueue.add(jsonArrayRequest);

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
            Intent intent = new Intent(AllQuestions.this, SearchActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.notif_icon) {
            Intent intent = new Intent(AllQuestions.this, NotificationActivity.class);
            startActivity(intent);
        }
        else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}

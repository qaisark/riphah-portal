package com.goprogs.riphahportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class question extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Context mContext;
    private Activity mActivity;
    private String mJSONURLString = "https://riphahportal.com/API/question_api_handler.php?action=fetch_single&id=";
    private String JSONAnsString = "https://riphahportal.com/API/answer_api_handler.php?action=fetch_answers&ques_id=";
    ProgressDialog progressDialog;
    TextView title, desc, askby,created_at;
    ListView anslist;
    AnswerListAdapter adapter;
    ArrayList<Answer> answerlist = new ArrayList<Answer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        // Get the application context
        mContext = getApplicationContext();
        mActivity = question.this;
        title = (TextView) findViewById(R.id.questiontitle);
        desc = (TextView) findViewById(R.id.descquestion);
        askby = (TextView) findViewById(R.id.askbyquestion);
        created_at = (TextView)  findViewById(R.id.createdatquestion);
        anslist = (ListView) findViewById(R.id.listviewanswers);

        anslist.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Answer item = adapter.getItem(position);
                Toast.makeText(getBaseContext(),"You selected : " + item.getID(),Toast.LENGTH_SHORT).show();
            }
        });
        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(question.this);

        int id = getIntent().getIntExtra("id",-1);
        if(id!=-1)
        {
            getQuestionDetails(id);
            getAnswers(id);
        }


        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarquestion);
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
                            Intent intent = new Intent(question.this, AskNow.class);
                            startActivity(intent);
                        } else if (id == R.id.nav_profile) {
                            Intent intent = new Intent(question.this, UserActivity.class);
                            startActivity(intent);

                        } else if (id == R.id.nav_all_questions) {
                            Intent intent = new Intent(question.this, AllQuestions.class);
                            startActivity(intent);
                        }
                        else if (id == R.id.nav_my_questions) {
                            Intent intent = new Intent(question.this, UserQuestions.class);
                            startActivity(intent);
                        }else if (id == R.id.nav_explore) {

                        } else if (id == R.id.nav_search) {
                            Intent intent = new Intent(question.this, SearchActivity.class);
                            startActivity(intent);
                        }
                        else if (id == R.id.nav_logout) {
                            Intent intent = new Intent(question.this, logout.class);
                            startActivity(intent);
                        }
                        else if (id == R.id.nav_home) {
                            Intent intent = new Intent(question.this, MainActivity.class);
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
        // Adding Floating Action Button to bottom right of main view
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabanswernow);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(question.this, AnswerNow.class);
                int qid = getIntent().getIntExtra("id",-1);
                intent.putExtra("ques_id",qid);
                startActivity(intent);
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
            Intent intent = new Intent(question.this, SearchActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.notif_icon) {
            Intent intent = new Intent(question.this, NotificationActivity.class);
            startActivity(intent);
        }
        else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
    public void getQuestionDetails(int id)
    {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        mJSONURLString+=id;
        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                mJSONURLString,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Process the JSON
                        try{
                            // Get the JSON array

                                // Get the current student (json object) data
                                String qtitle = response.getString("title");
                                String qdesc = response.getString("description");
                                String qviews = response.getString("views");
                                String qcreated_at = response.getString("created_at");
                                String quser_id = response.getString("user_id");
                                String qasked_by = response.getString("asked_by");
                                String qanswers = response.getString("answers");

                                qdesc = qdesc.replaceAll("&nbsp;", " ");
                                qdesc = qdesc.replaceAll("\\<br\\>", "\n");
                                qdesc = qdesc.replaceAll("\\<.*?\\>", "");

                                title.setText(qtitle);
                                desc.setText(qdesc);
                                askby.setText(qasked_by);
                                created_at.setText(qcreated_at);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        //Display error message whenever an error occurs
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }
    public void getAnswers(int id)
    {
        JSONAnsString+=id;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                JSONAnsString,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response

                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject answer = response.getJSONObject(i);
                                String title    = answer.getString("answer");
                                title = title.replaceAll("&nbsp;", " ");
                                title = title.replaceAll("\\<br\\>", "\n");
                                title = title.replaceAll("\\<.*?\\>", "");

                                int id = answer.getInt("ID");
                                Answer query = new Answer(id,title);
                                answerlist.add(query);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        // Pass results to ListViewAdapter Class
                        adapter = new AnswerListAdapter(getBaseContext(), answerlist);

                        // Binds the Adapter to the ListView
                        anslist.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ErrorResponse", error.toString());
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(question.this);
        // add it to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }
}

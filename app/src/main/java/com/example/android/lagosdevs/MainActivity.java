package com.example.android.lagosdevs;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProfileAdapter.ListItemClickListner {

    public static final List<Profiles> profilesLists = new ArrayList<>();
    private static final String GIT_URL = "https://api.github.com/search/users?q=location:lagos+language:java";
    ProgressBar mProgressBar;
    private RecyclerView recyclerView;
    private ProfileAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the recycler
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
        adapter = new ProfileAdapter(profilesLists, this);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // Method to retrieve the Githun data using the volley network library
        retrieveData();

    }


    // This section implements the search functionality for the recycler view
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    // Create and initialize text listerner for the search query
    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }


    private void retrieveData() {
        /*
         This section is designed to retrieve the user list from Github using the Volley Network
         library and the result is then parsed into the required Image and Text views
        */

        // Initialize progress bar reference to the view
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                GIT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Dismiss the progressbar once the required list is generated.
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jo = array.getJSONObject(i);
                        Profiles usr = new Profiles(jo.getString("login"), jo.getString("avatar_url"),
                                jo.getString("html_url"));

                        // add the extracted JSON data to the ArrayList
                        profilesLists.add(usr);
                    }

                    // set the adapter to the RecyclerView
                    recyclerView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onListItemClick(int clickedItemIndex) {

    }

}

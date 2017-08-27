package com.example.android.lagosdevs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private static final String GIT_URL = "https://api.github.com/search/users?q=location:lagos+language:java";
    String urlDetails;
    private Toast mToast;



    private RecyclerView recyclerView;
    private ProfileAdapter adapter;
    public static final List<Profiles> profilesLists = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
//    private Toast mToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
        adapter = new ProfileAdapter(profilesLists, this);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        retrieveData();

    }

    private void retrieveData() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Retrieving data...");
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(true);
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                GIT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jo = array.getJSONObject(i);
                        Profiles usr = new Profiles(jo.getString("login"), jo.getString("avatar_url"),
                                jo.getString("html_url"));
                        profilesLists.add(usr);
                    }
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

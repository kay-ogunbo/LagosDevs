package com.example.android.lagosdevs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailProfile extends AppCompatActivity {
    private static final String TAG = DetailProfile.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

//        //Remove title bar
//        getSupportActionBar().hide();
//        //Remove notification bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_detail_profile);

        final TextView profileName = (TextView) findViewById(R.id.profileFullName);
        final CircularImageView profileImage  = (CircularImageView) findViewById(R.id.profileUserImg);
        final TextView profileUserID = (TextView) findViewById(R.id.profileUserID);
        final TextView profileRepos = (TextView) findViewById(R.id.profile_repos_no);
        final TextView profileFFs = (TextView) findViewById(R.id.profile_followers_no);
        final TextView profileFollowing = (TextView) findViewById(R.id.profile_following_no);

        // Retrieve saved data from intent action.
        Intent intent = getIntent();
        final String userProfileId = intent.getStringExtra(appConstant.API_USER_NAME);
        final String userProfilePic = intent.getStringExtra(appConstant.API_AVATAR_URL);

        // Create the user URL by combining the base URL and the selected user IDl
        final String url = appConstant.BASE_URL+ "/users/" + userProfileId;

        // Parse the user id
        profileUserID.setText("@"+userProfileId);

        // Get the user image using picasso library
        Picasso.with(this)
                .load(userProfilePic)
                .placeholder(R.drawable.placeholder)
                .into(profileImage);


        // Create the network call and parse the data to their respective XML fields
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            profileName.setText(response.getString(appConstant.API_NAME));
                            profileRepos.setText(response.getString(appConstant.API_REPOS));
                            profileFFs.setText(response.getString(appConstant.API_FOLLOWERS));
                            profileFollowing.setText(response.getString(appConstant.API_FOLLOWING));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
    });
        requestQueue.add(objectRequest);


    }
}

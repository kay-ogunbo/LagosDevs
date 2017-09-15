package com.example.android.lagosdevs;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

    TextView profileName;
    CircularImageView profileImage;
    TextView profileUserID;
    TextView profileRepos;
    TextView profileFFs;
    TextView profileFollowing;
    TextView profileUserURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profile);


        // initialize the required views
        profileName = (TextView) findViewById(R.id.profileFullName);
        profileImage  = (CircularImageView) findViewById(R.id.profileUserImg);
        profileUserID = (TextView) findViewById(R.id.profileUserID);
        profileRepos = (TextView) findViewById(R.id.profile_repos_no);
        profileFFs = (TextView) findViewById(R.id.profile_followers_no);
        profileFollowing = (TextView) findViewById(R.id.profile_following_no);
        profileUserURL = (TextView) findViewById(R.id.git_url);

        // Retrieve saved data from intent action.
        final Intent intent = getIntent();
        final String userProfileId = intent.getStringExtra(appConstant.API_USER_NAME);
        final String userProfilePic = intent.getStringExtra(appConstant.API_AVATAR_URL);
        final String profileURL = intent.getStringExtra(appConstant.API_PROFILE_URL);

        // Create the user URL by combining the base URL and the selected user IDl
        final String url = appConstant.BASE_URL+ "/users/" + userProfileId;

        // Parse the user id into the required text view.
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


                            Profiles usrProfile = new Profiles(response.getString(appConstant.API_NAME), response.getString(appConstant.API_REPOS),
                                    response.getString(appConstant.API_FOLLOWERS), response.getString(appConstant.API_FOLLOWING));

                            // Add the extracted JSON responses to variables.
                            String fullName = usrProfile.getProfileFullName();
                            String repos = usrProfile.getProfileRepos();
                            String followers = usrProfile.getProfileFFs();
                            String following = usrProfile.getProfileFollowing();

                            // add the new variables to the corresponding TextViews
                            profileName.setText(fullName);
                            profileRepos.setText(repos);
                            profileFFs.setText(followers);
                            profileFollowing.setText(following);

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

        // Create URL Listener
        profileUserURL.setText(profileURL);
        profileUserURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(intent.ACTION_VIEW, Uri.parse(profileURL));
                startActivity(intent1);
            }
        });

        // Create listener for the Share button
        Button share = (Button) findViewById(R.id.btn_share);
        share.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Sharing: GitHub profile for @"+userProfileId);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome developer @" + userProfileId +
                        ", \n at " + profileURL + " \n");
                startActivity(Intent.createChooser(shareIntent,"Share using"));


            }
        });



    }
}

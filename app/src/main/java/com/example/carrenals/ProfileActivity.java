package com.example.carrenals;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private static final int EDIT_PROFILE_REQUEST_CODE = 1;

    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView emailTextView;
    private SharedPrefManager sharedPrefManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize SharedPrefManager
        sharedPrefManager = SharedPrefManager.getInstance(this);

        // Retrieve email from SharedPreferences
        String email = sharedPrefManager.readString("email", "noValue");

        // Initialize TextViews
        firstNameTextView = findViewById(R.id.firstNameTextView);
        lastNameTextView = findViewById(R.id.lastNameTextView);
        emailTextView = findViewById(R.id.emailTextView);

        // Fetch user information from PHP file
        getUserInfo(email);
        emailTextView.setText("Email: " + email);

        // Set up sign out button
        Button signOutButton = findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to login screen
                Intent intent = new Intent(ProfileActivity.this, MainActivityLogin.class);
                startActivity(intent);
                // Clear the activity stack to prevent going back to the profile page
                finishAffinity();
            }
        });

        // Set up edit profile button
        Button editProfileButton = findViewById(R.id.btnEdit);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open EditProfileActivity
                Intent intent = new Intent(ProfileActivity.this, EditProfile.class);
                startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE);
            }
        });
    }

    private void getUserInfo(String email) {
        String url = "http://192.168.1.117//api/get_user_info.php?email=" + email;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String firstName = jsonObject.getString("first_name");
                            String lastName = jsonObject.getString("last_name");

                            // Update UI with retrieved data
                            firstNameTextView.setText("First Name: " + firstName);
                            lastNameTextView.setText("Last Name: " + lastName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ProfileActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(ProfileActivity.this, "Error fetching user info", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_PROFILE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Handle the result from EditProfileActivity if needed
                // For example, you can update the UI with the edited data
            }
        }
    }
}
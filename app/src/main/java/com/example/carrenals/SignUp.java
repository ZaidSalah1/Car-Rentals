package com.example.carrenals;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private TextView txtBack;
    TextView first_name;
    TextView last_name;
    TextView Email;
    TextView Phone;
    TextView Password;
    TextView Confirm_password;
    Spinner gender;
    Spinner city;
    Spinner country;
    Button register;
    TextView zip;
    private static final String URL = "http://192.168.1.3/";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        String[] country_list = {"Palestine", "Egypt", "Jordan", "Saudi Arabia"};
        String[] city_Palestine = {"Nablus", "Ramallah", "Tulkarem"};
        String[] city_Egypt = {"Cairo", "Alexandria", "Gizeh"};
        String[] city_Jordan = {"Amman", "Irbid", "Al Zarqa"};
        String[] city_Saudi = {"Dammam", "Dhahran", "Al Bahah"};






        txtBack = findViewById(R.id.txtBack);

        // Set an OnClickListener on the TextView
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the LogIn activity
                Intent intent = new Intent(SignUp.this, MainActivityLogin.class);
                startActivity(intent);
            }
        });

        first_name = findViewById(R.id.signUpFirstnameBox);
        last_name = findViewById(R.id.signUpLastnameBox);
        Email = findViewById(R.id.signUpEmailBox);
        Phone = findViewById(R.id.signUpPhoneBox);
        Password = findViewById(R.id.signUpPassBox);
        Confirm_password = findViewById(R.id.signUpPassBox2);
        zip = findViewById(R.id.Zipcode);
        gender = findViewById(R.id.spinnerGinder);
        city = findViewById(R.id.spinnerCity);
        country = findViewById(R.id.spinnerCountry);
        register = findViewById(R.id.button2);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderAdapter);

        ArrayAdapter<CharSequence> countryAdapter = ArrayAdapter.createFromResource(this, R.array.Contry, android.R.layout.simple_spinner_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(countryAdapter);


        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                if (arg0.equals(country)) {

                    if (country.getSelectedItem().equals("Palistine")) {
                        ArrayAdapter<String> s1 = new ArrayAdapter<String>(SignUp.this, android.R.layout.simple_spinner_item, city_Palestine);
                        s1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        city.setAdapter(s1);
                        zip.setText("+970");
                    } else if (country.getSelectedItem().equals("Egypt")) {
                        ArrayAdapter<String> s2 = new ArrayAdapter<String>(SignUp.this, android.R.layout.simple_spinner_item, city_Egypt);
                        s2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        city.setAdapter(s2);
                        zip.setText("+20");

                    } else if (country.getSelectedItem().equals("Jordan")) {
                        ArrayAdapter<String> s3 = new ArrayAdapter<String>(SignUp.this, android.R.layout.simple_spinner_item, city_Jordan);
                        s3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        city.setAdapter(s3);
                        zip.setText("+962");

                    } else {
                        ArrayAdapter<String> s3 = new ArrayAdapter<String>(SignUp.this, android.R.layout.simple_spinner_item, city_Saudi);
                        s3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        city.setAdapter(s3);
                        zip.setText("+966");


                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean fieldsEmpty = Password.getText().toString().isEmpty() || Confirm_password.getText().toString().isEmpty() || Email.getText().toString().isEmpty() || first_name.getText().toString().isEmpty() || last_name.getText().toString().isEmpty() || Phone.getText().toString().isEmpty();
                if (fieldsEmpty) {
                    Toast.makeText(SignUp.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Password.getText().toString().equals(Confirm_password.getText().toString())) {
                    Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
                Pattern emailPattern = Pattern.compile(emailRegex);
                Matcher emailMatcher = emailPattern.matcher(Email.getText().toString());
                if (!emailMatcher.matches()) {
                    Toast.makeText(SignUp.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }


                String passwordRegex = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";
                Pattern passwordPattern = Pattern.compile(passwordRegex);
                Matcher passwordMatcher = passwordPattern.matcher(Password.getText().toString());
                if (!passwordMatcher.matches()) {
                    Toast.makeText(SignUp.this, "Password must meet the criteria", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (first_name.getText().toString().length() < 3 || last_name.getText().toString().length() < 3) {
                    Toast.makeText(SignUp.this, "First and last name must be at least 3 characters long", Toast.LENGTH_SHORT).show();
                    return;
                }
                CreateCustomer();

                Intent intent = new Intent(SignUp.this, MainActivityLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void CreateCustomer() {
        String requestUrl = URL + "api/customers.php";

        JSONObject postData = new JSONObject();
        try {
            postData.put("first_name", first_name.getText().toString());
            postData.put("last_name", last_name.getText().toString());
            postData.put("email", Email.getText().toString());
            postData.put("password", Password.getText().toString());
            postData.put("gender", gender.getSelectedItem().toString());
            postData.put("phone_number", Phone.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(requestUrl);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, requestUrl, postData, response -> {
            try {
                // Handle response
                System.out.println(response.toString());
                Toast.makeText(SignUp.this, "Customer created successfully", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(SignUp.this, "Error parsing response", Toast.LENGTH_LONG).show();
            }
        }, error -> {
            error.printStackTrace();
            String errorMessage = "Error: " + error.toString();
            if (error.networkResponse != null) {
                errorMessage += " Status Code: " + error.networkResponse.statusCode;
                errorMessage += " Response Data: " + new String(error.networkResponse.data);
            }
            Toast.makeText(SignUp.this, errorMessage, Toast.LENGTH_LONG).show();
        });

        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(SignUp.this);
        requestQueue.add(request);
    }

}

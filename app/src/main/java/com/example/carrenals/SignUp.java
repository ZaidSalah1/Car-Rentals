package com.example.carrenals;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

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
    Spinner Gender;
    Spinner city;
    Spinner country;
    Button register;
    TextView zip;

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
        Gender = findViewById(R.id.spinnerGinder);
        city = findViewById(R.id.spinnerCity);
        country = findViewById(R.id.spinnerCountry);
        register = findViewById(R.id.button2);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gender.setAdapter(genderAdapter);

        ArrayAdapter<CharSequence> countryAdapter = ArrayAdapter.createFromResource(this, R.array.Contry, android.R.layout.simple_spinner_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(countryAdapter);

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry = country.getSelectedItem().toString();
                ArrayAdapter<String> cityAdapter;

                switch (selectedCountry) {
                    case "Palestine":
                        cityAdapter = new ArrayAdapter<>(SignUp.this, android.R.layout.simple_spinner_item, city_Palestine);
                        zip.setText("+970");
                        break;
                    case "Egypt":
                        cityAdapter = new ArrayAdapter<>(SignUp.this, android.R.layout.simple_spinner_item, city_Egypt);
                        zip.setText("+20");
                        break;
                    case "Jordan":
                        cityAdapter = new ArrayAdapter<>(SignUp.this, android.R.layout.simple_spinner_item, city_Jordan);
                        zip.setText("+962");
                        break;
                    case "Saudi Arabia":
                        cityAdapter = new ArrayAdapter<>(SignUp.this, android.R.layout.simple_spinner_item, city_Saudi);
                        zip.setText("+966");
                        break;
                    default:
                        cityAdapter = new ArrayAdapter<>(SignUp.this, android.R.layout.simple_spinner_item, new String[]{});
                        break;
                }

                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                city.setAdapter(cityAdapter);

                // Set the text based on selectedCountry
                String countryCodeText = "";
                switch (selectedCountry) {
                    case "Palestine":
                        countryCodeText = "970";
                        break;
                    case "Egypt":
                        countryCodeText = "20";
                        break;
                    case "Jordan":
                        countryCodeText = "962";
                        break;
                    case "Saudi Arabia":
                        countryCodeText = "966";
                        break;
                    default:
                        countryCodeText = "970"; // Set to "eng" for other countries
                        break;
                }
                zip.setText(countryCodeText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
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

                Customer newCustomer = new Customer();
                newCustomer.setEmail(Email.getText().toString());
                newCustomer.setPassword(Password.getText().toString());
                newCustomer.setGender(Gender.getSelectedItem().toString());
                newCustomer.setCity(city.getSelectedItem().toString());
                newCustomer.setCountry(country.getSelectedItem().toString());
                newCustomer.setFirst_name(first_name.getText().toString());
                newCustomer.setSecond_name(last_name.getText().toString());
                newCustomer.setPhone(Phone.getText().toString());


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
            postData.put("Email", Email.getText().toString());
            postData.put("Password", Password.getText().toString());
            postData.put("Gender", Gender.getSelectedItem().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(requestUrl);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, requestUrl, postData,
                response -> {
                    try {
                        // Handle response
                        System.out.println(response.toString());
                        Toast.makeText(SignUp.this, "Customer created successfully", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(SignUp.this, "Error parsing response", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    String errorMessage = "Error: " + error.toString();
                    if (error.networkResponse != null) {
                        errorMessage += " Status Code: " + error.networkResponse.statusCode;
                        errorMessage += " Response Data: " + new String(error.networkResponse.data);
                    }
                    Toast.makeText(SignUp.this, errorMessage, Toast.LENGTH_LONG).show();
                }
        );


        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(SignUp.this);
        requestQueue.add(request);
    }

}

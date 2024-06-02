package com.example.carrenals;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carrenals.Model.CustomerModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

public class MainActivityLogin extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    private EditText emailEditText;
    private EditText passwordEditText;
    private CheckBox rememberMeCheckBox;
    private FloatingActionButton loginButton;
    private TextView txtSignUp;

    String URL = "http://192.168.1.105:80/api/cars.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_log_in);

        emailEditText = findViewById(R.id.EmailBox);
        passwordEditText = findViewById(R.id.PasswordBox);
        rememberMeCheckBox = findViewById(R.id.login_rememberme);
        loginButton = findViewById(R.id.loginButton);
        txtSignUp = findViewById(R.id.txtSignUp);

        // Set the CheckBox listener
        rememberMeCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Show password
                passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                // Hide password
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            // Move the cursor to the end of the input text
            passwordEditText.setSelection(passwordEditText.getText().length());
        });
        sharedPrefManager = SharedPrefManager.getInstance(this);
        if (!sharedPrefManager.readString("email", "noValue").equals("noValue")) {
            emailEditText.setText(sharedPrefManager.readString("email", "noValue"));
             passwordEditText.setText(sharedPrefManager.readString("password", "noValue"));
            rememberMeCheckBox.setChecked(true);
        }

        rememberMeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (rememberMeCheckBox.isChecked()) {
                    sharedPrefManager = SharedPrefManager.getInstance(MainActivityLogin.this);
                    sharedPrefManager.writeString("email", emailEditText.getText().toString());
                    sharedPrefManager.writeString("password", passwordEditText.getText().toString());
                } else if (!rememberMeCheckBox.isChecked()) {
                    sharedPrefManager = SharedPrefManager.getInstance(MainActivityLogin.this);
                    sharedPrefManager.writeString("email", "noValue");
                    sharedPrefManager.writeString("password", "noValue");
                }
            }
        });



        // Set the login button listener
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (isValidEmail(email) && isValidPassword(password)) {
                // Fetch customer data by email
                getCustomerByEmail(email);
            } else {
                // Show error message
                Toast.makeText(MainActivityLogin.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }

        });

        // Set the sign-up text listener
        txtSignUp.setOnClickListener(v -> {
            // Start SignUp activity
            startActivity(new Intent(MainActivityLogin.this, SignUp.class));
        });
    }

    // Function to validate email
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Function to validate password
    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    private void getCustomerByEmail(String email) {

     //   String requestUrl = URL + "api/customers.php?email=" + email;
     //   System.out.println(requestUrl);
        StringRequest request = new StringRequest(Request.Method.GET, URL, res -> {
            try {
                String jsonResponse = res.replace("Hello", "");
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONObject customerObject = jsonObject.getJSONObject("customer");

                int id = customerObject.getInt("customer_id");
                String emailResponse = customerObject.getString("email");
                String password = customerObject.getString("password");
                String firstName = customerObject.getString("first_name");
                String lastName = customerObject.getString("last_name");

                CustomerModel customer = new CustomerModel(emailResponse, password, firstName, lastName);
                customer.setId(id);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivityLogin.this, "Error parsing response", Toast.LENGTH_LONG).show();
            }
        }, err -> {
            Toast.makeText(MainActivityLogin.this, err.toString(), Toast.LENGTH_LONG).show();
        });

        Volley.newRequestQueue(MainActivityLogin.this).add(request);
    }
}

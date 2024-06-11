package com.example.carrenals;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

public class MainActivityLogin extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    private EditText emailEditText;
    private EditText passwordEditText;
    private CheckBox rememberMeCheckBox;
    private FloatingActionButton loginButton;
    private TextView txtSignUp;

    private TextView txtSignUptovendor;

    String URL = "http://192.168.1.117/";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        emailEditText = findViewById(R.id.EmailBox);
        passwordEditText = findViewById(R.id.PasswordBox);
        rememberMeCheckBox = findViewById(R.id.login_rememberme);
        loginButton = findViewById(R.id.loginButton);
        txtSignUptovendor=findViewById(R.id.txtSignUp);
        txtSignUp = findViewById(R.id.txtSignUp);

        sharedPrefManager = SharedPrefManager.getInstance(this);
        if (!sharedPrefManager.readString("email", "noValue").equals("noValue")) {
            emailEditText.setText(sharedPrefManager.readString("email", "noValue"));
            passwordEditText.setText(sharedPrefManager.readString("password", "noValue"));
            rememberMeCheckBox.setChecked(true);
        }

        rememberMeCheckBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                sharedPrefManager.writeString("email", emailEditText.getText().toString());
                sharedPrefManager.writeString("password", passwordEditText.getText().toString());
            } else {
                sharedPrefManager.writeString("email", "noValue");
                sharedPrefManager.writeString("password", "noValue");
            }
        });

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();


            if (isValidEmail(email) && isValidPassword(password)) {
                getCustomerByEmail(email, password);
            } else {
              //  Toast.makeText(MainActivityLogin.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
//            Intent intent = new Intent(MainActivityLogin.this, MainActivityCars.class);
//            startActivity(intent);

        });

       // txtSignUp.setOnClickListener(v -> startActivity(new Intent(MainActivityLogin.this, SignUp.class)));
        txtSignUp.setOnClickListener(v -> {
            Toast.makeText(MainActivityLogin.this, "Sign Up clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivityLogin.this, SignUp.class);
            startActivity(intent);
        });



    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    private void getCustomerByEmail(String email, String enteredPassword) {
        String requestUrl = URL + "api/customers.php?email=" + email;
        Log.d("URL", requestUrl); //

        StringRequest request = new StringRequest(Request.Method.GET, requestUrl, res -> {
            try {
                Log.d("Response", res); //
                String jsonResponse = res.replace("Hello", "");
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONObject customerObject = jsonObject.getJSONObject("customer");

                int id = customerObject.getInt("customer_id");
                String emailResponse = customerObject.getString("email");
                String password = customerObject.getString("password");
                String firstName = customerObject.getString("first_name");
                String lastName = customerObject.getString("last_name");
                String user_type = customerObject.getString("user_type");
                Toast.makeText(MainActivityLogin.this, user_type, Toast.LENGTH_SHORT).show();

                if (password.equals(enteredPassword)&&(user_type.equals("user")||user_type.equals(""))) {
                    Intent intent = new Intent(MainActivityLogin.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (password.equals(enteredPassword)&&user_type.equals("admin")) {
//                    Intent intent = new Intent(MainActivityLogin.this, VendorCars.class);
//                    startActivity(intent);
//                    finish();
                } else {
                    Toast.makeText(MainActivityLogin.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //Toast.makeText(MainActivityLogin.this, "Error parsing response", Toast.LENGTH_LONG).show();
            }
        }, err -> {
            if (err.networkResponse != null) {
                String errorMsg = "Error code: " + err.networkResponse.statusCode + "\n" + new String(err.networkResponse.data);
                Log.e("VolleyError", errorMsg); //
                Toast.makeText(MainActivityLogin.this, errorMsg, Toast.LENGTH_LONG).show();
            } else {
                Log.e("VolleyError", err.toString()); //
                Toast.makeText(MainActivityLogin.this, err.toString(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(MainActivityLogin.this).add(request);
    }

}

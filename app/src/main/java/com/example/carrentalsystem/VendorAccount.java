package com.example.carrentalsystem;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carrentalsystem.Model.Customer;

import org.json.JSONException;
import org.json.JSONObject;

public class VendorAccount extends AppCompatActivity {
private String businessName;
private int vendorId;
private int customerId;

private EditText businessNameField;
private EditText firstNameField;
private EditText lastNameField;
private EditText phoneNumberField;
private EditText emailField;

    private TextView businessNameView;
    private TextView firstNameView;
    private TextView lastNameView;
    private TextView phoneNumberView;
    private TextView emailView;
    private JSONObject userInfo;

private EditText password;

private Button saveButton;
private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vendor_account);
        Intent intent = getIntent();
        businessName = intent.getStringExtra("business_name");
        vendorId = intent.getIntExtra("vendor_id", 0);
        customerId = intent.getIntExtra("customer_id", 0);
        url = intent.getStringExtra("url");

        setupViews();
        getUserInfo();
        saveButton.setOnClickListener(v -> {
            try {
                updateCustomer();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            saveButton.setEnabled(false);
            Handler handler = new Handler();
            handler.postDelayed(() -> saveButton.setEnabled(true),1000);
        });

    }
    private void setupViews() {
    businessNameField = findViewById(R.id.businessNameField);
    firstNameField = findViewById(R.id.firstNameField);
    lastNameField = findViewById(R.id.lastNameField);
    phoneNumberField = findViewById(R.id.phoneNumberField);
    emailField = findViewById(R.id.emailField);
    saveButton = findViewById(R.id.saveButton);

    businessNameView = findViewById(R.id.displayBusinessName);
        firstNameView = findViewById(R.id.displayFirstName);
        lastNameView = findViewById(R.id.displayLastName);
        phoneNumberView = findViewById(R.id.displayPhoneNumber);
        emailView = findViewById(R.id.displayEmail);

        firstNameField = findViewById(R.id.firstNameField);
        lastNameField = findViewById(R.id.lastNameField);
        phoneNumberField = findViewById(R.id.phoneNumberField);
        emailField = findViewById(R.id.emailField);
    }

    private void getUserInfo() {
        StringRequest request = new StringRequest(Request.Method.GET, url+"/api/customers.php/?id="+customerId, res -> {
            try {
                JSONObject obj = new JSONObject(res);
                JSONObject customerObj = (JSONObject) obj.get("customer");
                emailView.setText("Email address: " + customerObj.getString("email"));
                firstNameView.setText("First name: " +customerObj.getString("first_name"));
                lastNameView.setText("Last name: " +customerObj.getString("last_name"));
                phoneNumberView.setText("Phone number: " +customerObj.getString("phone_number"));

            } catch (Exception ex) {
                Toast.makeText(VendorAccount.this, "Fail", Toast.LENGTH_LONG).show();
            }
        }, err -> Toast.makeText(VendorAccount.this, "DOESNOT", Toast.LENGTH_LONG).show());

        Volley.newRequestQueue(this).add(request);

        StringRequest vendorRequest = new StringRequest(Request.Method.GET, url+"/api/vendors.php/?id="+vendorId, res -> {
            try {
                JSONObject obj = new JSONObject(res);
                JSONObject vendorObj = (JSONObject) obj.get("vendor");
                businessNameView.setText("Business name: " +vendorObj.getString("business_name"));

            } catch (Exception ex) {
                Toast.makeText(VendorAccount.this, "Fail", Toast.LENGTH_LONG).show();
            }
        }, err -> Toast.makeText(VendorAccount.this, "WEUT", Toast.LENGTH_LONG).show());

        Volley.newRequestQueue(this).add(vendorRequest);
    }

    private void updateCustomer() throws JSONException {
        JSONObject updatedCustomerData = new JSONObject();
        if (!firstNameField.getText().equals(""))
            updatedCustomerData.put("first_name", firstNameField.getText());
        else
            updatedCustomerData.put("first_name", firstNameView.getText());

        if (!lastNameField.getText().equals(""))
            updatedCustomerData.put("last_name", lastNameField.getText());
        else
            updatedCustomerData.put("last_name", lastNameView.getText());

        if (!emailField.getText().equals(""))
            updatedCustomerData.put("email", emailField.getText());
        else
            updatedCustomerData.put("email", emailView.getText());

        if (!phoneNumberField.getText().equals(""))
            updatedCustomerData.put("phone_number", phoneNumberField.getText());
        else
            updatedCustomerData.put("phone_number", phoneNumberView.getText());


        JSONObject updatedVendorData = new JSONObject();
        if (!businessNameField.getText().equals(""))
            updatedVendorData.put("business_name", businessNameField.getText());
        else
            updatedVendorData.put("business_name", businessNameView.getText());



        JsonObjectRequest customerRequest = new JsonObjectRequest(Request.Method.PUT, url+"/api/customers.php/?id="+customerId,updatedCustomerData,
                res -> {
            try {
                Toast.makeText(VendorAccount.this, res.toString(), Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Toast.makeText(VendorAccount.this, "Fail", Toast.LENGTH_LONG).show();
            }
        }, err -> Toast.makeText(VendorAccount.this, err.toString(), Toast.LENGTH_LONG).show());

        Volley.newRequestQueue(this).add(customerRequest);


        JsonObjectRequest vendorRequest = new JsonObjectRequest(Request.Method.PUT, url+"/api/vendors.php/?id="+vendorId,updatedVendorData,
                res -> {
                    try {
                        Toast.makeText(VendorAccount.this, res.toString(), Toast.LENGTH_LONG).show();
                    } catch (Exception ex) {
                        Toast.makeText(VendorAccount.this, "Fail", Toast.LENGTH_LONG).show();
                    }
                }, err -> Toast.makeText(VendorAccount.this, err.toString(), Toast.LENGTH_LONG).show());

        Volley.newRequestQueue(this).add(vendorRequest);
        getUserInfo();


    }

}
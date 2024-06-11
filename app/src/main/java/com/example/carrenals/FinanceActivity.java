package com.example.carrenals;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FinanceActivity extends AppCompatActivity {

    private static final String URL = "http://192.168.1.117/api/money.php";
    private ListView listViewFinance;
    private TextView textViewRentingIncome;
    private ImageView imageViewSelectedCar;
    String totalPriceString;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

        listViewFinance = findViewById(R.id.listViewFinance);
        textViewRentingIncome = findViewById(R.id.textViewRentingIncome);
        imageViewSelectedCar = findViewById(R.id.imageViewSelectedCar);

        // Static list of car brands
        String[] carBrands = {"BMW", "Audi", "Mercedes", "chevrolet"};

        // Array of images corresponding to car brands
        int[] carImages = {R.drawable.bmw_car, R.drawable.audi_r8, R.drawable.mercedes_gclass, R.drawable.chevrolet_logo};

        // Create an ArrayAdapter using the string array and a default ListView layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, carBrands);

        // Set the adapter to the ListView
        listViewFinance.setAdapter(adapter);

        // Set an item click listener on the ListView
        listViewFinance.setOnItemClickListener((parent, view, position, id) -> {
            // Display the corresponding image in the ImageView
            imageViewSelectedCar.setImageResource(carImages[position]);
            imageViewSelectedCar.setVisibility(View.VISIBLE);
        });

        // Fetch data from server and calculate total price
        fetchCarPrices();

        // Apply padding to the main layout to account for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Inside your activity class
        // Inside your activity class
        Switch switchAction = findViewById(R.id.switchAction);
        TextView textViewRentingIncome = findViewById(R.id.textViewRentingIncome);
        Calendar calendar = Calendar.getInstance();

        switchAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchAction.isChecked()) {
                    // Switch is ON, show DatePickerDialog
                    DatePickerDialog datePickerDialog = new DatePickerDialog(FinanceActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // Set selected date to Calendar instance
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, monthOfYear);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            // Show TimePickerDialog after selecting date
                            TimePickerDialog timePickerDialog = new TimePickerDialog(FinanceActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    // Set selected time to Calendar instance
                                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    calendar.set(Calendar.MINUTE, minute);

                                    // Format selected date and time
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                                    String dateTime = sdf.format(calendar.getTime());

                                    // Display selected date and time in TextView
                                    textViewRentingIncome.setText(totalPriceString+"\n"+"Selected Date and Time: " + dateTime);

                                    // Save the selected date and time in a variable or a database
                                    // For example, you can save it in SharedPreferences
                                    // Or you can save it in a database using SQLite or Room
                                }
                            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
                            timePickerDialog.show();
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                } else {
                    // Switch is OFF
                    // Optionally, you can perform another action when the switch is turned off
                }
            }
        });


// Define the action to be performed


    }
    private void showAction() {
        // Perform the action you want to show when the switch is toggled ON
        // For example, show a toast message

    }

    private void fetchCarPrices() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            double totalCarsPrice = 0.0;
                            StringBuilder carModelList = new StringBuilder();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject carObject = jsonArray.getJSONObject(i);
                                String modelName = carObject.getString("model_name");
                                String priceString = carObject.getString("price").trim().replace("$", "");

                                if (!priceString.isEmpty()) {
                                    try {
                                        double price = Double.parseDouble(priceString);
                                        totalCarsPrice += price;
                                        // Append the car model to the list
                                        carModelList.append(modelName).append(", ");
                                    } catch (NumberFormatException e) {
                                        Log.e("FinanceActivity", "Error parsing price: " + priceString, e);
                                    }
                                }
                            }

                            // Remove the trailing comma and space
                            String carModels = carModelList.toString().replaceAll(", $", "");

                            // Format the total price string
                             totalPriceString = String.format("Money Generated from Renting: $%.2f\nCar Models: %s", totalCarsPrice, carModels);

                            // Set the text of the TextView
                            textViewRentingIncome.setText(totalPriceString);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(FinanceActivity.this, "Error parsing JSON response", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("FinanceActivity", "Error fetching data", error);
                        Toast.makeText(FinanceActivity.this, "Error fetching data: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(FinanceActivity.this).add(stringRequest);
    }
}

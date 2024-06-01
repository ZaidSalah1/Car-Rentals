package com.example.carrentalsystem;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carrentalsystem.Model.CarModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<CarModel> cars = new ArrayList<>();
    private static final String URL = "http://192.168.1.5/api/cars.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Log.d(TAG,"HEYO");

        if (isNetworkAvailable()) {
            Toast.makeText(this, "Network is available", Toast.LENGTH_SHORT).show();
            // Proceed with network operations
        } else {
            Toast.makeText(this, "No network connection", Toast.LENGTH_SHORT).show();
            // Handle no network scenario
        }

        listView  = findViewById(R.id.cars);
        loadCars();

    }

    private void loadCars() {

        StringRequest request = new StringRequest(Request.Method.GET, URL, res -> {

            try {
                JSONObject arrayObj = new JSONObject(res);
                JSONArray array = arrayObj.getJSONArray("cars");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject carObj = array.getJSONObject(i);

                    String name = carObj.getString("brand");
                    String color = carObj.getString("color");
                    String seatCapacity = carObj.getString("num_of_seats");
                    String year = carObj.getString("model_year");

                    CarModel car = new CarModel(name, color, Integer.parseInt(seatCapacity), year);
                    cars.add(car);
                }

                ArrayAdapter<CarModel> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, cars);
                listView.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, err -> Toast.makeText(MainActivity.this, err.toString(),Toast.LENGTH_LONG).show()
        );
        Volley.newRequestQueue(MainActivity.this).add(request);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
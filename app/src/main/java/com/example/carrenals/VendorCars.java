

//package com.example.carrentalsystem;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.android.volley.Request;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.carrentalsystem.Adapter.CarAdapter;
//import com.example.carrentalsystem.Model.VendorCar;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//import com.example.carrentalsystem.Adapter.CarAdapter;
//
//public class VendorCars extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private List<VendorCar> vendorCars = new ArrayList<>();
//    private CarAdapter carAdapter;
//    private List<Integer> carIds = new ArrayList<>();
//    private static final String URL = "http://192.168.1.2";
//    private TextView businessName;
//    private Button accountButton;
//    private int vendor_id;
//    private String business_name;
//    private int customer_id;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_vendor_cars);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        setupViews();
//        getVendorCars();
//
//        accountButton.setOnClickListener(v -> {
//            Intent intent = new Intent(VendorCars.this, VendorAccount.class);
//            intent.putExtra("vendor_id", vendor_id);
//            intent.putExtra("business_name", business_name);
//            intent.putExtra("url", URL);
//            intent.putExtra("customer_id", customer_id);
//            startActivity(intent);
//        });
//
//
//    }
//
//    private void setupViews() {
//        recyclerView = findViewById(R.id.vendorCarRecycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        this.carAdapter = new CarAdapter(VendorCars.this, vendorCars, itemClickListener) {
//            @Override
//            public void onItemClick(VendorCar vendorCar) {
//                // Handle item click event here
//                // For example, start a new activity passing data
//                Intent intent = new Intent(VendorCars.this, ManageCar.class);
//                intent.putExtra("car_id", vendorCar.getCarId());
//                intent.putExtra("vendor_id", vendor_id);
//                startActivity(intent);
//            }
//        };
//
//// Create an instance of CarAdapter and pass the itemClickListener
//        CarAdapter carAdapter = new CarAdapter(VendorCars.this, vendorCars, itemClickListener);
//        recyclerView.setAdapter(carAdapter);
//        businessName = findViewById(R.id.businessName);
//        accountButton = findViewById(R.id.accountButton);
//    }
//
//    private void getVendorCars() {
//        int vendor_id = 1;
//
//        StringRequest request = new StringRequest(Request.Method.GET, URL + "/api/vendor-cars.php/?vendor_id=" + vendor_id, res -> {
//            try {
//                JSONObject JSONObj = new JSONObject(res);
//                JSONArray vendorCarsArray = JSONObj.getJSONArray("vendor_cars");
//
//                for (int i = 0; i < vendorCarsArray.length(); i++) {
//                    JSONObject vendorCarObj = vendorCarsArray.getJSONObject(i);
//                    int car_id = vendorCarObj.getInt("car_id");
//                    double dailyCost = vendorCarObj.getDouble("daily_cost");
//
//                    VendorCar vendorCar = new VendorCar(vendor_id, car_id, dailyCost);
//                    this.vendor_id = vendor_id;
//                    vendorCars.add(vendorCar);
//                    carIds.add(car_id);
//                }
//
//                loadCarsById(carIds);
//
//            } catch (Exception ex) {
//                Toast.makeText(VendorCars.this, "Error parsing vendor cars", Toast.LENGTH_LONG).show();
//            }
//        }, err -> Toast.makeText(VendorCars.this, "No vendor cars found", Toast.LENGTH_LONG).show());
//
//        Volley.newRequestQueue(this).add(request);
//    }
//
//    private void loadCarsById(List<Integer> carIds) {
//        for (int i = 0; i < carIds.size(); i++) {
//            int finalI = i;
//            int id = carIds.get(i);
//            StringRequest request = new StringRequest(Request.Method.GET, URL + "/api/cars.php/?id=" + id, res -> {
//                try {
//                    JSONObject carObj = new JSONObject(res).getJSONObject("car");
//                    String brand = carObj.getString("brand");
//                    String image = carObj.getString("image");
//
//                    vendorCars.get(finalI).setBrand(brand);
//                    vendorCars.get(finalI).setImage(image);
//
//                    carAdapter.notifyDataSetChanged();
//
//                    // Call getVendorInfo once car details are loaded
//                    if (finalI == carIds.size() - 1) {
//                        getVendorInfo();
//                    }
//
//                } catch (Exception e) {
//                    Toast.makeText(VendorCars.this, "Error parsing car details", Toast.LENGTH_LONG).show();
//                }
//            }, err -> Toast.makeText(VendorCars.this, "Error fetching car details", Toast.LENGTH_LONG).show());
//
//            Volley.newRequestQueue(this).add(request);
//        }
//    }
//
//    private void getVendorInfo() {
//        if (vendorCars.isEmpty()) return;
//
//        int vendorId = vendorCars.get(0).getVendorId();
//
//        StringRequest request = new StringRequest(Request.Method.GET, URL + "/api/vendors.php/?id=" + vendorId, res -> {
//            try {
//                JSONObject obj = new JSONObject(res);
//                JSONObject vendor = obj.getJSONObject("vendor");
//                String name = vendor.getString("business_name");
//                int customerId = Integer.parseInt(vendor.getString("customer_id"));
//                this.customer_id = customerId;
//                businessName.setText(name);
//                this.business_name = name;
//            } catch (Exception e) {
//                Toast.makeText(VendorCars.this, "Error parsing vendor info", Toast.LENGTH_LONG).show();
//            }
//        }, err -> Toast.makeText(VendorCars.this, "Error fetching vendor info", Toast.LENGTH_LONG).show());
//
//        Volley.newRequestQueue(this).add(request);
//    }
//}
//package com.example.carrentalsystem;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.android.volley.Request;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.carrentalsystem.Adapter.CarAdapter;
//import com.example.carrentalsystem.Model.VendorCar;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class VendorCars extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private List<VendorCar> vendorCars = new ArrayList<>();
//    private CarAdapter carAdapter;
//    private List<Integer> carIds = new ArrayList<>();
//    private static final String URL = "http://192.168.1.2";
//    private TextView businessName;
//    private Button accountButton;
//    private Button createCarButton;
//    private int vendor_id;
//    private String business_name;
//    private int customer_id;
//
//    // Define itemClickListener as a global variable
//    private CarAdapter.OnItemClickListener itemClickListener = new CarAdapter.OnItemClickListener() {
//        @Override
//        public void onItemClick(VendorCar vendorCar) {
//            // Handle item click event here
//            // For example, start a new activity passing data
//            Intent intent = new Intent(VendorCars.this, ManageCar.class);
//            intent.putExtra("car_id", vendorCar.getCarId());
//            intent.putExtra("vendor_id", vendor_id);
//            startActivity(intent);
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_vendor_cars);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        setupViews();
//        getVendorCars();
//
//        accountButton.setOnClickListener(v -> {
//            Intent intent = new Intent(VendorCars.this, VendorAccount.class);
//            intent.putExtra("vendor_id", vendor_id);
//            intent.putExtra("business_name", business_name);
//            intent.putExtra("url", URL);
//            intent.putExtra("customer_id", customer_id);
//            startActivity(intent);
//        });
//
//        createCarButton.setOnClickListener(v -> {
//           Intent intent =  new Intent(VendorCars.this, CarCreator.class);
//           intent.putExtra("vendor_id", vendor_id);
//           startActivity(intent);
//        });
//    }
//
//    private void setupViews() {
//        recyclerView = findViewById(R.id.vendorCarRecycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        carAdapter = new CarAdapter(VendorCars.this, vendorCars, itemClickListener);
//        recyclerView.setAdapter(carAdapter);
//        businessName = findViewById(R.id.businessName);
//        accountButton = findViewById(R.id.accountButton);
//        createCarButton = findViewById(R.id.addCarButton);
//    }
//
//    private void getVendorCars() {
//        int vendor_id = 1;
//
//        StringRequest request = new StringRequest(Request.Method.GET, URL + "/api/vendor-cars.php/?vendor_id=" + vendor_id, res -> {
//            try {
//                JSONObject JSONObj = new JSONObject(res);
//                JSONArray vendorCarsArray = JSONObj.getJSONArray("vendor_cars");
//
//                for (int i = 0; i < vendorCarsArray.length(); i++) {
//                    JSONObject vendorCarObj = vendorCarsArray.getJSONObject(i);
//                    int car_id = vendorCarObj.getInt("car_id");
//                    double dailyCost = vendorCarObj.getDouble("daily_cost");
//
//                    VendorCar vendorCar = new VendorCar(vendor_id, car_id, dailyCost);
//                    this.vendor_id = vendor_id;
//                    vendorCars.add(vendorCar);
//                    carIds.add(car_id);
//                }
//
//                loadCarsById(carIds);
//
//            } catch (Exception ex) {
//                Toast.makeText(VendorCars.this, "Error parsing vendor cars", Toast.LENGTH_LONG).show();
//            }
//        }, err -> Toast.makeText(VendorCars.this, "No vendor cars found", Toast.LENGTH_LONG).show());
//
//        Volley.newRequestQueue(this).add(request);
//    }
//
//    private void loadCarsById(List<Integer> carIds) {
//        for (int i = 0; i < carIds.size(); i++) {
//            int finalI = i;
//            int id = carIds.get(i);
//            StringRequest request = new StringRequest(Request.Method.GET, URL + "/api/cars.php/?id=" + id, res -> {
//                try {
//                    JSONObject carObj = new JSONObject(res).getJSONObject("car");
//                    String brand = carObj.getString("brand");
//                    String image = carObj.getString("image");
//
//                    vendorCars.get(finalI).setBrand(brand);
//                    vendorCars.get(finalI).setImage(image);
//
//                    carAdapter.notifyDataSetChanged();
//
//                    // Call getVendorInfo once car details are loaded
//                    if (finalI == carIds.size() - 1) {
//                        getVendorInfo();
//                    }
//
//                } catch (Exception e) {
//                    Toast.makeText(VendorCars.this, "Error parsing car details", Toast.LENGTH_LONG).show();
//                }
//            }, err -> Toast.makeText(VendorCars.this, "Error fetching car details", Toast.LENGTH_LONG).show());
//
//            Volley.newRequestQueue(this).add(request);
//        }
//    }
//
//    private void getVendorInfo() {
//        if (vendorCars.isEmpty()) return;
//
//        int vendorId = vendorCars.get(0).getVendorId();
//
//        StringRequest request = new StringRequest(Request.Method.GET, URL + "/api/vendors.php/?id=" + vendorId, res -> {
//            try {
//                JSONObject obj = new JSONObject(res);
//                JSONObject vendor = obj.getJSONObject("vendor");
//                String name = vendor.getString("business_name");
//                int customerId = Integer.parseInt(vendor.getString("customer_id"));
//                this.customer_id = customerId;
//                businessName.setText(name);
//                this.business_name = name;
//            } catch (Exception e) {
//                Toast.makeText(VendorCars.this, "Error parsing vendor info", Toast.LENGTH_LONG).show();
//            }
//        }, err -> Toast.makeText(VendorCars.this, "Error fetching vendor info", Toast.LENGTH_LONG).show());
//
//        Volley.newRequestQueue(this).add(request);
//    }
//}

package com.example.carrenals;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carrenals.Adapter.CarAdapter;
import com.example.carrenals.Model.VendorCar;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VendorCars extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<VendorCar> vendorCars = new ArrayList<>();
    private CarAdapter carAdapter;
    private List<Integer> carIds = new ArrayList<>();
    private static final String URL = "http://192.168.1.117";
    private TextView businessName;
    private Button accountButton;
    private Button createCarButton;
    private int vendor_id = 1;
    private String business_name;
    private int customer_id;

    private CarAdapter.OnItemClickListener itemClickListener = new CarAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(VendorCar vendorCar) {
            // Handle item click event here
            Intent intent = new Intent(VendorCars.this, ManageCar.class);
            intent.putExtra("car_id", vendorCar.getCarId());
            intent.putExtra("vendor_id", vendor_id);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_cars);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupViews();
        getVendorCars();

        accountButton.setOnClickListener(v -> {
            Intent intent = new Intent(VendorCars.this, VendorAccount.class);
            intent.putExtra("vendor_id", vendor_id);
            intent.putExtra("business_name", business_name);
            intent.putExtra("url", URL);
            intent.putExtra("customer_id", customer_id);
            startActivity(intent);
        });

        createCarButton.setOnClickListener(v -> {
            Intent intent =  new Intent(VendorCars.this, CreateCar.class);
            intent.putExtra("vendor_id", vendor_id);
            startActivity(intent);

        });
    }

    private void setupViews() {
        recyclerView = findViewById(R.id.vendorCarRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        carAdapter = new CarAdapter(VendorCars.this, vendorCars, itemClickListener);
        recyclerView.setAdapter(carAdapter);
        businessName = findViewById(R.id.businessName);
        accountButton = findViewById(R.id.accountButton);
        createCarButton = findViewById(R.id.addCarButton);
    }

    private void getVendorCars() {
        StringRequest request = new StringRequest(Request.Method.GET, URL + "/api/vendor-cars.php/?vendor_id=" + vendor_id, res -> {
            try {
                JSONObject JSONObj = new JSONObject(res);
                JSONArray vendorCarsArray = JSONObj.getJSONArray("vendor_cars");

                for (int i = 0; i < vendorCarsArray.length(); i++) {
                    JSONObject vendorCarObj = vendorCarsArray.getJSONObject(i);
                    int car_id = vendorCarObj.getInt("car_id");
                    double dailyCost = vendorCarObj.getDouble("daily_cost");

                    VendorCar vendorCar = new VendorCar(vendor_id, car_id, dailyCost);
                    vendorCars.add(vendorCar);
                    carIds.add(car_id);
                }

                loadCarsById(carIds);

            } catch (Exception ex) {
                Toast.makeText(VendorCars.this, "Error parsing vendor cars", Toast.LENGTH_LONG).show();
            }
        }, err -> Toast.makeText(VendorCars.this, "No vendor cars found", Toast.LENGTH_LONG).show());

        Volley.newRequestQueue(this).add(request);
    }

    private void loadCarsById(List<Integer> carIds) {
        for (int i = 0; i < carIds.size(); i++) {
            int finalI = i;
            int id = carIds.get(i);
            StringRequest request = new StringRequest(Request.Method.GET, URL + "/api/cars.php/?id=" + id, res -> {
                try {
                    JSONObject carObj = new JSONObject(res).getJSONObject("car");
                    String brand = carObj.getString("brand_id");
                    String image = carObj.getString("image");

                    vendorCars.get(finalI).setBrand(brand);
                    vendorCars.get(finalI).setImage(image);

                    carAdapter.notifyDataSetChanged();

                    // Call getVendorInfo once car details are loaded
                    if (finalI == carIds.size() - 1) {
                        getVendorInfo();
                    }

                } catch (Exception e) {
                    Toast.makeText(VendorCars.this, "Error parsing car details", Toast.LENGTH_LONG).show();
                }
            }, err -> Toast.makeText(VendorCars.this, "Error fetching car details", Toast.LENGTH_LONG).show());

            Volley.newRequestQueue(this).add(request);
        }
    }

    private void getVendorInfo() {
        if (vendorCars.isEmpty()) return;

        int vendorId = vendorCars.get(0).getVendorId();

        StringRequest request = new StringRequest(Request.Method.GET, URL + "/api/vendors.php/?id=" + vendorId, res -> {
            try {
                JSONObject obj = new JSONObject(res);
                JSONObject vendor = obj.getJSONObject("vendor");
                String name = vendor.getString("business_name");
                int customerId = Integer.parseInt(vendor.getString("customer_id"));
                this.customer_id = customerId;
                businessName.setText(name);
                this.business_name = name;
            } catch (Exception e) {
                Toast.makeText(VendorCars.this, "Error parsing vendor info", Toast.LENGTH_LONG).show();
            }
        }, err -> Toast.makeText(VendorCars.this, "Error fetching vendor info", Toast.LENGTH_LONG).show());

        Volley.newRequestQueue(this).add(request);
    }
}

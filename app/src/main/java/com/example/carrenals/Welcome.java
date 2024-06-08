package com.example.carrenals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action when button is clicked
                Toast.makeText(Welcome.this, "Button clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Welcome.this, MainActivityLogin.class);
                startActivity(intent);
                finish();
                // Add your code here to navigate to another activity or perform any other action
            }
        });
    }
}

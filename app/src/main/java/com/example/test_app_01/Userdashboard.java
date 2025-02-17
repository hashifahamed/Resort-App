package com.example.test_app_01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Userdashboard extends AppCompatActivity {
    Button BTN_01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_userdashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        BTN_01 = findViewById(R.id.BTN_01);

        BTN_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveLogin = new Intent(getApplicationContext(), roomlist.class); // Going to next page we are using the word INTENT
                startActivity(moveLogin);
            }
        });

        Button BTN_02 = findViewById(R.id.BTN_02);


        BTN_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveLogin = new Intent(getApplicationContext(), ServiceRes.class); // Going to next page we are using the word INTENT
                startActivity(moveLogin);
            }
        });
    }
}
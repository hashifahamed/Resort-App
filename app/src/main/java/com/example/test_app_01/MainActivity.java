package com.example.test_app_01;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ImageView IMG_LogOut;
    TextView TXT_LogOut;

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

        IMG_LogOut = findViewById(R.id.IMG_LogOut);
        TXT_LogOut = findViewById(R.id.TXT_LogOut);

        IMG_LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();

            }
        });

        TXT_LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });

    }

        public void LogOut () {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), login.class));
            finish();


        }
    }

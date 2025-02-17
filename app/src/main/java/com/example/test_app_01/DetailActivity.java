package com.example.test_app_01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {

    TextView detailDesc, detailTitle;
    ImageView detailImage;

    Button BTN_BookNow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        detailDesc = findViewById(R.id.detailDesc);
        detailTitle = findViewById(R.id.detailTitle);
        detailImage = findViewById(R.id.detailImage);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailDesc.setText(bundle.getInt("Desc"));
            detailTitle.setText(bundle.getString("Title"));
            detailImage.setImageResource(bundle.getInt("Image"));
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        BTN_BookNow = findViewById(R.id.BTN_BookNow);
        BTN_BookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveLogin = new Intent(getApplicationContext(), roombooking.class);
                moveLogin.putExtra("roomID",detailTitle.getText().toString());
                startActivity(moveLogin);
            }
        });


    }

}

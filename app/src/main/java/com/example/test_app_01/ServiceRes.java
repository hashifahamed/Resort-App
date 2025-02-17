package com.example.test_app_01;

import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ServiceRes extends AppCompatActivity {

    RecyclerView recyclerView;
    List<DataClass> dataList;
    MyAdapter adapter;
    DataClass androidData;
    SearchView searchView;

    Button BTN_BookNow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_roomlist);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(ServiceRes.this,  1);
        recyclerView.setLayoutManager(gridLayoutManager);
        dataList = new ArrayList<>();

        androidData = new DataClass("Spa Treatments", R.string.Spa_Treatments,"$60/Hr", R.drawable.spa1);
        dataList.add(androidData);

        androidData = new DataClass("Fine Dining", R.string.Fine_Dining,"$45/Dinner", R.drawable.dining1);
        dataList.add(androidData);

        androidData = new DataClass("Poolside Cabanas", R.string.Poolside_Cabanas,"$10/Hr", R.drawable.caba);
        dataList.add(androidData);

        androidData = new DataClass("Beach Tours", R.string.beach_tours,"$55/Ride", R.drawable.btours);
        dataList.add(androidData);


        adapter = new MyAdapter(ServiceRes.this, dataList);
        recyclerView.setAdapter(adapter);


    }
    private void searchList(String text){
        List<DataClass> dataSearchList = new ArrayList<>();
        for (DataClass data : dataList){
            if(data.getDataTitle().toLowerCase().contains(text.toLowerCase())){
                dataSearchList.add(data);
            }
        }
        if (dataSearchList.isEmpty()){
            Toast.makeText(this,"Not Found",Toast.LENGTH_SHORT).show();
        } else {
            adapter.setSearchList(dataSearchList);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });


    }

}

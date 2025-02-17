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

public class roomlist extends AppCompatActivity {

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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(roomlist.this,  1);
        recyclerView.setLayoutManager(gridLayoutManager);
        dataList = new ArrayList<>();

        androidData = new DataClass("Deluxe Room 01", R.string.deluxe_01,"$120 / Night", R.drawable.deluxe_01);
        dataList.add(androidData);

        androidData = new DataClass("Deluxe Room 02", R.string.deluxe_02,"$115 / Night", R.drawable.deluxe_02);
        dataList.add(androidData);

        androidData = new DataClass("Deluxe Room 03", R.string.deluxe_03,"$110 / Night", R.drawable.deluxe_03);
        dataList.add(androidData);

        androidData = new DataClass("Deluxe Room 04", R.string.deluxe_04,"$130 / Night", R.drawable.deluxe_04);
        dataList.add(androidData);

        androidData = new DataClass("Deluxe Room 05", R.string.deluxe_05,"$125 / Night", R.drawable.deluxe_05);
        dataList.add(androidData);

        androidData = new DataClass("Beach Room 01", R.string.beach_01,"$150 / Night", R.drawable.beach_01);
        dataList.add(androidData);

        androidData = new DataClass("Beach Room 02", R.string.beach_02,"$140 / Night", R.drawable.beach_02);
        dataList.add(androidData);

        androidData = new DataClass("Beach Room 03", R.string.beach_03,"$160 / Night", R.drawable.beach_03);
        dataList.add(androidData);

        androidData = new DataClass("Beach Room 04", R.string.beach_04,"$155 / Night", R.drawable.beach_04);
        dataList.add(androidData);

        androidData = new DataClass("Beach Room 05", R.string.beach_05,"$165 / Night", R.drawable.beach_05);
        dataList.add(androidData);



        adapter = new MyAdapter(roomlist.this, dataList);
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

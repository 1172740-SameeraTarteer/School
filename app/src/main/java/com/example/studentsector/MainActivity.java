package com.example.studentsector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void addOnClick(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);

    }

    public void SearchOnClick(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);

    }

    public void onClickSearchbtn(View view) {
    }
}
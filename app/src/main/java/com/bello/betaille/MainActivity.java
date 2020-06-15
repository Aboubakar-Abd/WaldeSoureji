package com.bello.betaille;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

   }

    public void onBtnPartClick(View view) {
        Intent intent = new Intent(MainActivity.this, ListPropActivity.class);
        startActivity(intent);
    }

    public void onBtnDepClick(View view) {
        Intent intent = new Intent(MainActivity.this, ListDepActivity.class);
        startActivity(intent);
    }

    public void onBtnSoldeClick(View view) {
        Intent intent = new Intent(MainActivity.this, AddSoldeActivity.class);
        startActivity(intent);
    }
}
package com.bello.papa;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;


public class
MainActivity extends AppCompatActivity {

    public final String permission = Manifest.permission.READ_EXTERNAL_STORAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Permissions.check(this, permission, null , new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                finishAffinity();
                System.exit(0);
            }
        });
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
        Intent intent = new Intent(MainActivity.this, TroupeauActivity.class);
        startActivity(intent);
    }
}
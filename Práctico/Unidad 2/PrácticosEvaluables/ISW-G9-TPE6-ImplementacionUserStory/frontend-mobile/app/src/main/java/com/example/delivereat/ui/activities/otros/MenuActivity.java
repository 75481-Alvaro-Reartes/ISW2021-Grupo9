package com.example.delivereat.ui.activities.otros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.delivereat.R;
import com.example.delivereat.ui.activities.loquesea.ProductosActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViewById(R.id.btnPedirLoQueSea).setOnClickListener(x ->
                startActivity(new Intent(MenuActivity.this, ProductosActivity.class))
        );
    }
}
package com.example.delivereat.ui.activities.otros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.delivereat.R;

public class PresentacionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacion);

        findViewById(R.id.btnComenzar).setOnClickListener(x -> {
            Intent intent = new Intent(PresentacionActivity.this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.from_down, R.anim.to_up);
        });
    }
}
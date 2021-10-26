package com.example.delivereat.ui.activities.otros;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.delivereat.R;
import com.example.delivereat.control.IControl;
import com.example.delivereat.control.LauncherControl;
import com.example.delivereat.ui.abstracts.BaseActivity;
import com.example.delivereat.ui.activities.loquesea.CompletadoActivity;

public class LauncherActivity extends BaseActivity {

    private LauncherControl control;

    @Nullable
    @Override
    protected IControl getControl() {
        control = new LauncherControl(this);
        return control;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void iniciarViews() {
        control.iniciar();
    }

    public void iniciarActividad(Class<?> destino) {
        Intent intent = new Intent(this, destino);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.from_down, R.anim.to_up);
    }
}
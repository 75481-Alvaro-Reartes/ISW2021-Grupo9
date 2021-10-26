package com.example.delivereat.ui.activities.otros;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.delivereat.R;
import com.example.delivereat.control.IControl;
import com.example.delivereat.ui.abstracts.BaseActivity;
import com.example.delivereat.util.Constantes;

public class DatosGrupoActivity extends BaseActivity {

    @Nullable
    @Override
    protected IControl getControl() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_datos_grupo;
    }

    @Override
    protected void iniciarViews() {
        setAnimacionVolver(R.anim.from_down, R.anim.to_up);

        findViewById(R.id.btnRepositorio).setOnClickListener(x -> {
            Intent browserIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(Constantes.URL_REPOSITORIO));
            startActivity(browserIntent);
        });
    }
}
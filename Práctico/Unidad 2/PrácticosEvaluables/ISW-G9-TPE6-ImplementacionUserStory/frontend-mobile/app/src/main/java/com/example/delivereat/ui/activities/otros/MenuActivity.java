package com.example.delivereat.ui.activities.otros;

import com.example.delivereat.R;
import com.example.delivereat.control.IControl;
import com.example.delivereat.ui.abstracts.BaseActivity;
import com.example.delivereat.ui.activities.decomercio.ProximamenteActivity;
import com.example.delivereat.ui.activities.loquesea.ProductosActivity;

public class MenuActivity extends BaseActivity {

    @Override
    protected IControl getControl() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_menu;
    }

    @Override
    protected void iniciarViews() {
        findViewById(R.id.btnLoQueSea).setOnClickListener(x ->{

            setAnimacionNuevaActivity(R.anim.from_right, R.anim.to_left);
            navegar(ProductosActivity.class);

        });

        findViewById(R.id.btnComercio).setOnClickListener(x ->{
            setAnimacionNuevaActivity(R.anim.from_down, R.anim.to_up);
            navegar(ProximamenteActivity.class);
        });

        findViewById(R.id.btnGrupo).setOnClickListener(x ->{
            setAnimacionNuevaActivity(R.anim.from_up, R.anim.to_down);
            navegar(DatosGrupoActivity.class);
        });
    }
}
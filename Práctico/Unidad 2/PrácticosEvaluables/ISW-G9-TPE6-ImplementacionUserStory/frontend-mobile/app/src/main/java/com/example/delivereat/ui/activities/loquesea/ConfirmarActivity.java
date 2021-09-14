package com.example.delivereat.ui.activities.loquesea;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.delivereat.R;
import com.example.delivereat.control.ConfirmarControl;
import com.example.delivereat.control.IControl;
import com.example.delivereat.model.otros.Imagen;
import com.example.delivereat.model.pedidos.MetodoPago;
import com.example.delivereat.ui.abstracts.BaseActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class ConfirmarActivity extends BaseActivity {

    private ImageView imgProducto, imgPago;
    private TextView lblProducto, lblOrigen, lblDestino, lblPago, lblConfirmarError;
    private MaterialButton btnConfirmar;
    private LinearProgressIndicator progress;
    private ConfirmarControl control;

    @Override
    protected IControl getControl() {
        control = new ConfirmarControl(this);
        return control;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirmar;
    }

    @Override
    protected void iniciarViews() {

        imgProducto = findViewById(R.id.imgConfirmarProducto);
        imgPago = findViewById(R.id.imgConfirmarPago);
        lblProducto = findViewById(R.id.lblConfirmarProducto);
        lblOrigen = findViewById(R.id.lblConfirmarOrigen);
        lblDestino = findViewById(R.id.lblConfirmarDestino);
        lblPago = findViewById(R.id.lblConfirmarPago);
        progress = findViewById(R.id.progressConfirmar);
        lblConfirmarError  = findViewById(R.id.lblConfirmarError);

        findViewById(R.id.btnConfirmarProducto).setOnClickListener(v -> navegar(ProductosActivity.class));

        findViewById(R.id.btnConfirmarUbicacion).setOnClickListener(v -> navegar(UbicacionActivity.class));

        findViewById(R.id.btnConfirmarPago).setOnClickListener(v -> navegar(PagosActivity.class));

        findViewById(R.id.btnConfirmarLlegada).setOnClickListener(v -> navegar(EntregaActivity.class));

        btnConfirmar = findViewById(R.id.btnConfirmarPedido);
        btnConfirmar.setOnClickListener(v -> control.confirmar());
    }

    public void esperar(boolean esperar) {
        progress.setVisibility(esperar ? View.VISIBLE : View.GONE);
        btnConfirmar.setEnabled(!esperar);
    }

    public void siguiente() {
        Intent intent = new Intent(this, CompletadoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.from_right, R.anim.to_left);
    }

    public void setError(boolean hayError) {
        lblConfirmarError.setVisibility(hayError
                ? View.VISIBLE
                : View.GONE);
    }

    public void setProducto(String producto) {
        setTxtString(R.id.lblConfirmarProducto, producto);
    }

    public void setOrigen(String origen) {
        setTxtString(R.id.lblConfirmarOrigen, origen);
    }

    public void setDestino(String destino) {
        setTxtString(R.id.lblConfirmarDestino, destino);
    }

    public void setPago(String pago) {
        setTxtString(R.id.lblConfirmarPago, pago);
    }

    public void setImgProducto(Imagen imagen) {
        if (imagen == null)
            imgProducto.setImageResource(R.drawable.ic_cart);
        else
            imgProducto.setImageBitmap(imagen.getBm());
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        if (metodoPago == MetodoPago.Tarjeta)
            imgPago.setImageResource(R.drawable.ic_visa);
        else
            imgPago.setImageResource(R.drawable.ic_money);
    }

    public void setLlegada(String cuandoLlega) {
        setTxtString(R.id.lblConfirmarLlegada, cuandoLlega);
    }
}
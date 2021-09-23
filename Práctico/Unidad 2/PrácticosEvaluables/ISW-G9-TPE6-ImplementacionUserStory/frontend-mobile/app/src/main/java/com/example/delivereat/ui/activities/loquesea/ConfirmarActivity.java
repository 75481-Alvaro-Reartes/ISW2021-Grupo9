package com.example.delivereat.ui.activities.loquesea;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.delivereat.R;
import com.example.delivereat.control.ConfirmarControl;
import com.example.delivereat.control.IControl;
import com.example.delivereat.model.otros.Imagen;
import com.example.delivereat.model.pedidos.MetodoPago;
import com.example.delivereat.ui.abstracts.BaseActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;

/**
 * Interfaz gráfica de confirmación de pedido.
 */
public class ConfirmarActivity extends BaseActivity {

    /**
     * Imágen del producto.
     */
    private ImageView mImgProducto;

    /**
     * Imágen del método de pago.
     */
    private ImageView mImgPago;

    /**
     * Label que muestra cualquier error que haya quedado pendiente del pedido.
     */
    private TextView mLblConfirmarError;

    /**
     * Botón para confirmar el pedido.
     */
    private MaterialButton mBtnConfirmar;

    /**
     * Progress bar para esperar hasta validar el pedido luego de confirmado por el usuario.
     */
    private LinearProgressIndicator mProgress;

    /**
     * Controlador de lógica la interfaz.
     */
    private ConfirmarControl mControl;

    @Override
    protected IControl getControl() {
        mControl = new ConfirmarControl(this);
        return mControl;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirmar;
    }

    @Override
    protected void iniciarViews() {

        mImgProducto = findViewById(R.id.imgConfirmarProducto);
        mImgPago = findViewById(R.id.imgConfirmarPago);
        mProgress = findViewById(R.id.progressConfirmar);
        mLblConfirmarError = findViewById(R.id.lblConfirmarError);

        findViewById(R.id.btnConfirmarProducto).setOnClickListener(v ->
                navegar(ProductosActivity.class));

        findViewById(R.id.btnConfirmarUbicacion).setOnClickListener(v ->
                navegar(UbicacionActivity.class));

        findViewById(R.id.btnConfirmarPago).setOnClickListener(v ->
                navegar(PagosActivity.class));

        findViewById(R.id.btnConfirmarLlegada).setOnClickListener(v ->
                navegar(EntregaActivity.class));

        mBtnConfirmar = findViewById(R.id.btnConfirmarPedido);
        mBtnConfirmar.setOnClickListener(v -> mControl.confirmar());
    }

    /**
     * Muestra la barra de progreso y bloquea los botones de la interfaz.
     * @param esperar Hace que espere o no la interfaz gráfica.
     */
    public void esperar(boolean esperar) {
        mProgress.setVisibility(esperar ? View.VISIBLE : View.GONE);
        mBtnConfirmar.setEnabled(!esperar);
    }

    /**
     * Avanzar a la ventana de pedido completado, eliminando el stack de actividades para
     * evitar que el usuario navegue hacia atrás, y modifique el pedido luego de estar
     * completado el mismo.
     */
    public void siguiente() {
        Intent intent = new Intent(this, CompletadoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.from_right, R.anim.to_left);
    }

    /**
     * Muestra o no el cartel de error en el pedido.
     * @param hayError Hay error o no en el pedido.
     */
    public void setError(boolean hayError) {
        mLblConfirmarError.setVisibility(hayError
                ? View.VISIBLE
                : View.GONE);
    }

    /**
     * Mostrar el nombre del producto ingresado por el usuario.
     * @param producto Nombre del producto.
     */
    public void setProducto(String producto) {
        setTxtString(R.id.lblConfirmarProducto, producto);
    }

    /**
     * Mostrar los datos de la dirección de origen.
     * @param origen Datos de la dirección de origen.
     */
    public void setOrigen(String origen) {
        setTxtString(R.id.lblConfirmarOrigen, origen);
    }

    /**
     * Muestra los datos de la dirección de destino.
     * @param destino Datos de la dirección de origen.
     */
    public void setDestino(String destino) {
        setTxtString(R.id.lblConfirmarDestino, destino);
    }

    /**
     * Muestra los datos de pago del pedido.
     * @param pago Datos de pagos del pedido.
     */
    public void setPago(String pago) {
        setTxtString(R.id.lblConfirmarPago, pago);
    }

    /**
     * Muestra la imágen agregada del producto en caso de haberla, sino muestra una imágen genérica.
     * @param imagen Imágen guardada del producto.
     */
    public void setImgProducto(@Nullable Imagen imagen) {
        if (imagen == null)
            mImgProducto.setImageResource(R.drawable.ic_cart);
        else
            mImgProducto.setImageBitmap(imagen.getBm());
    }

    /**
     * Muestra el método de pago elegido para el pedido a través de una imágen.
     * @param metodoPago Método de pago elegido.
     */
    public void setMetodoPago(MetodoPago metodoPago) {
        if (metodoPago == MetodoPago.Tarjeta)
            mImgPago.setImageResource(R.drawable.ic_visa);
        else
            mImgPago.setImageResource(R.drawable.ic_money);
    }

    /**
     * Muestra los datos de la llegada del pedido al destino.
     * @param cuandoLlega Datos de llegada del pedido.
     */
    public void setLlegada(String cuandoLlega) {
        setTxtString(R.id.lblConfirmarLlegada, cuandoLlega);
    }
}
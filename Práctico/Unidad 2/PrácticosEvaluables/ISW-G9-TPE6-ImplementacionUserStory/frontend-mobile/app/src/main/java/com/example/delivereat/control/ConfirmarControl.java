package com.example.delivereat.control;

import com.example.delivereat.model.pedidos.Pago;
import com.example.delivereat.model.pedidos.Pedido;
import com.example.delivereat.persistencia.Datos;
import com.example.delivereat.service.IClientePagoPedido;
import com.example.delivereat.service.MockPagoEfectivo;
import com.example.delivereat.service.MockPagoTarjeta;
import com.example.delivereat.ui.activities.loquesea.ConfirmarActivity;
import com.example.delivereat.ui.activities.loquesea.IObserverPago;

public class ConfirmarControl implements IObserverPago, IControl {

    private final ConfirmarActivity mActivity;
    private final Pedido mPedido;

    public ConfirmarControl(ConfirmarActivity activity) {
        mActivity = activity;
        mPedido = Datos.getInstance().getPedido();
    }

    /**
     * Metodo que confirma un pedido sin errores y si los tiene avisa
     */
    public void confirmar() {
        if (mPedido.getErrores().hayError()){
            mActivity.setError(true);
            return;
        }
        mActivity.esperar(true);

        IClientePagoPedido mPagos;
        if (mPedido.getPago().esTarjeta()){
            mPagos = new MockPagoTarjeta().setObserver(this);
        }
        else {
            mPagos = new MockPagoEfectivo().setObserver(this);
        }

        mPagos.validar(new Pago());
    }

    /**
     * se conecta con la API de VISA y valida su resultado de si esta vigente o no
     * @param valida indica si la tarjeta pertenece a una tarjeta VISA
     */
    @Override
    public void tarjetaValida(boolean valida) {
        mActivity.esperar(false);
        if (valida) {
            mActivity.siguiente();
            limpiarDatos();
        }
        else mActivity.toast("No se pudo verificar la tarjeta.");
    }

    private void limpiarDatos() {
        Datos.getInstance().limpiar();
    }

    @Override
    public void recuperarDatos() {
        mActivity.setProducto(mPedido.getProducto().getNombre());
        mActivity.setOrigen(mPedido.getUbicacion().getOrigen().toString());
        mActivity.setDestino(mPedido.getUbicacion().getDestino().toString());
        mActivity.setPago(mPedido.getPago().toString());
        mActivity.setImgProducto(mPedido.getProducto().getImagen());
        mActivity.setMetodoPago(mPedido.getPago().getMetodoPago());
        mActivity.setLlegada(mPedido.getEntrega().cuandoLlega());

        mActivity.setError(false);
    }

    @Override
    public void guardarDatos() {

    }
}

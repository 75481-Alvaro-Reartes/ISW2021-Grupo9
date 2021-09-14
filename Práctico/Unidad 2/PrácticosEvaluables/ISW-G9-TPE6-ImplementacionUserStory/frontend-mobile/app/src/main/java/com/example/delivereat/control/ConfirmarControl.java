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

    private ConfirmarActivity mActivity;
    private IClientePagoPedido mPagos;
    private Pedido mPedido;

    public ConfirmarControl(ConfirmarActivity activity) {
        mActivity = activity;
        mPedido = Datos.getInstance().getPedido();
    }

    /**
     * Metodo que confirma un pedido sin errores y si los tiene avisa
     */
    public void confirmar() {
        if (pedido.getErrores().hayError()){
            activity.setError(true);
            return;
        }
        activity.esperar(true);

        if (pedido.getPago().esTarjeta()){
            pagos = new MockPagoTarjeta().setObserver(this);
        }
        else {
            pagos = new MockPagoEfectivo().setObserver(this);
        }

        pagos.validar(new Pago());
    }

    /**
     * se conecta con la API de VISA y valida su resultado de si esta vigente o no
     * @param valida indica si la tarjeta pertenece a una tarjeta VISA
     */
    @Override
    public void tarjetaValida(boolean valida) {
        activity.esperar(false);
        if (valida) {
            activity.siguiente();
            limpiarDatos();
        }
        else activity.toast("No se pudo verificar la tarjeta.");
    }

    private void limpiarDatos() {
        Datos.getInstance().limpiar();
    }

    @Override
    public void recuperarDatos() {
        activity.setProducto(pedido.getProducto().getNombre());
        activity.setOrigen(pedido.getUbicacion().getOrigen().toString());
        activity.setDestino(pedido.getUbicacion().getDestino().toString());
        activity.setPago(pedido.getPago().toString());
        activity.setImgProducto(pedido.getProducto().getImagen());
        activity.setMetodoPago(pedido.getPago().getMetodoPago());
        activity.setLlegada(pedido.getEntrega().cuandoLlega());

        activity.setError(false);
    }

    @Override
    public void guardarDatos() {

    }
}

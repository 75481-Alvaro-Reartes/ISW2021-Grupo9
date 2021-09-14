package com.example.delivereat.control;

import com.example.delivereat.model.pedidos.Pedido;
import com.example.delivereat.persistencia.Datos;
import com.example.delivereat.ui.activities.loquesea.EntregaActivity;

public class EntregaControl implements IControl {

    private final EntregaActivity activity;
    private final Pedido pedido;

    public EntregaControl(EntregaActivity activity) {
        this.activity = activity;
        pedido = Datos.getInstance().getPedido();
    }

    public void siguiente() {
        guardarDatos();

        activity.setErrores(pedido.getEntrega().getErrores());

        if (!pedido.getEntrega().getErrores().hayError())
            activity.siguiente();
    }

    @Override
    public void recuperarDatos() {
        activity.setEntregaPronto(pedido.getEntrega().isEntregaLoAntesPosible());
        activity.setFechaEntrega(pedido.getEntrega().getEntregaProgramada());
    }

    @Override
    public void guardarDatos() {
        pedido.getEntrega().setEntregaLoAntesPosible(activity.getEntregaPronto());
        pedido.getEntrega().setEntregaProgramada(activity.getFechaHoraEntrega());
    }
}

package com.example.delivereat.control;

import com.example.delivereat.model.Direccion;
import com.example.delivereat.model.Pedido;
import com.example.delivereat.service.ClienteApiDireccion;
import com.example.delivereat.ui.UbicacionActivity;

public class UbicacionControl {

    private final UbicacionActivity activity;
    private int codigoDireccion;

    public UbicacionControl(UbicacionActivity activity) {
        this.activity = activity;
    }

    public void siguiente() {
        Pedido p = Pedido.getInstance();

        p.setCalleOrigen(activity.getCalleOrigen());
        p.setCalleDestino(activity.getCalleDestino());
        p.setNumeroOrigen(activity.getNumeroOrigen());
        p.setNumeroDestino(activity.getNumeroDestino());
        p.setCiudadOrigen(activity.getCiudadOrigen());
        p.setCiudadDesitno(activity.getCiudadDestino());
        p.setComentarioOrigen(activity.getComentarioOrigen());
        p.setComentarioDestino(activity.getComentarioDestino());

        activity.setErrores(p.errores);

        if (!p.errores.errorFormUbicacion()) activity.siguiente();
    }

    public void buscarDireccion(int codigo) {
        Pedido p = Pedido.getInstance();
        codigoDireccion = codigo;

        activity.esperar(true);
        new ClienteApiDireccion(this).execute(new Direccion(p.getLat(), p.getLng()));
    }

    public void setDireccion(Direccion direccion) {
        activity.esperar(false);
        activity.setDireccion(codigoDireccion, direccion.getCalle(), direccion.getNumero(), direccion.getCiudad());
    }

    public void mostrarError(String mensaje) {
        activity.esperar(false);
        activity.toast(mensaje);
    }
}

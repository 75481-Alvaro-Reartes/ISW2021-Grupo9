package com.example.delivereat.control;

import androidx.annotation.Nullable;

import com.example.delivereat.R;
import com.example.delivereat.model.Direccion;
import com.example.delivereat.model.Pedido;
import com.example.delivereat.persistencia.Datos;
import com.example.delivereat.service.ClienteApiDireccion;
import com.example.delivereat.ui.activities.loquesea.UbicacionActivity;
import com.example.delivereat.util.Constantes;

import java.util.Arrays;
import java.util.List;

public class UbicacionControl implements IControl {

    private final UbicacionActivity activity;
    private int codigoUbicacion;
    private List<String> ciudades;
    private final Pedido pedido;

    public UbicacionControl(UbicacionActivity activity) {
        this.activity = activity;
        pedido = Datos.getInstance().getPedido();
    }

    public void iniciarCiudades() {
        ciudades = (Arrays.asList(activity.getResources().getStringArray(R.array.ciudades)));
        activity.setListaCiudades(ciudades);
    }

    public void siguiente() {
        guardarDatos();

        activity.setErrores(pedido.getUbicacion().getErrores());

        if (!pedido.getUbicacion().getErrores().hayError()) activity.siguiente();
    }

    @Override
    public void recuperarDatos() {
        Direccion origen = pedido.getUbicacion().getOrigen();
        activity.setCalleOrigen(origen.getCalle());
        activity.setNumeroOrigen(origen.getNumero());
        activity.setCiudadOrigen(origen.getCiudad());
        activity.setComentarioOrigen(origen.getComentario());

        Direccion destino = pedido.getUbicacion().getDestino();
        activity.setCalleDestino(destino.getCalle());
        activity.setNumeroDestino(destino.getNumero());
        activity.setCiudadDestino(destino.getCiudad());
        activity.setComentarioDestino(destino.getComentario());
    }

    @Override
    public void guardarDatos() {
        Direccion origen = pedido.getUbicacion().getOrigen();
        origen.setCalle(activity.getCalleOrigen());
        origen.setNumero(activity.getNumeroOrigen());
        origen.setCiudad(activity.getCiudadOrigen());
        origen.setComentario(activity.getComentarioOrigen());

        Direccion destino = pedido.getUbicacion().getDestino();
        destino.setCalle(activity.getCalleDestino());
        destino.setNumero(activity.getNumeroDestino());
        destino.setCiudad(activity.getCiudadDestino());
        destino.setComentario(activity.getComentarioDestino());
    }

    public void buscarDireccion(int codigo) {
        codigoUbicacion = codigo;

        activity.esperar(true);
        new ClienteApiDireccion(this).execute(pedido.getUbicacion().getTemp());
    }

    public void recibirResultadoDireccion(@Nullable Direccion resultado) {
        activity.esperar(false);

        if (resultado == null) {
            activity.toast("Ocurrió un error al buscar la dirección.");
            return;
        }

        if (ciudades.contains(resultado.getCiudad())){
            Direccion d = codigoUbicacion == Constantes.UBICACION_ORIGEN
                    ? pedido.getUbicacion().getOrigen()
                    : pedido.getUbicacion().getDestino();

            d.setCalle(resultado.getCalle());
            d.setNumero(resultado.getNumero());
            d.setCiudad(resultado.getCiudad());
            d.setComentario(resultado.getComentario());

            recuperarDatos();
        }
        else {
            activity.toast("Disculpá, todavía no trabajamos en " + resultado.getCiudad());
        }
    }
}

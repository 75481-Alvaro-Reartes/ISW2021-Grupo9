package com.example.delivereat.control;

import androidx.annotation.Nullable;

import com.example.delivereat.R;
import com.example.delivereat.model.pedidos.Direccion;
import com.example.delivereat.model.pedidos.Pedido;
import com.example.delivereat.persistencia.Datos;
import com.example.delivereat.service.ClienteApiDireccion;
import com.example.delivereat.service.ClienteApiRutas;
import com.example.delivereat.ui.activities.loquesea.UbicacionActivity;
import com.example.delivereat.util.Constantes;

import java.util.Arrays;
import java.util.List;

public class UbicacionControl implements IControl {

    private final UbicacionActivity mActivity;
    private int mCodigoUbicacion;
    private List<String> mCiudades;
    private final Pedido mPedido;

    public UbicacionControl(UbicacionActivity activity) {
        this.mActivity = activity;
        mPedido = Datos.getInstance().getPedido();
    }

    public void iniciarCiudades() {
        mCiudades = (Arrays.asList(mActivity.getResources().getStringArray(R.array.ciudades)));
        mActivity.setListaCiudades(mCiudades);
    }

    public void siguiente() {
        guardarDatos();

        mActivity.setErrores(mPedido.getUbicacion().getErrores());

        if (!mPedido.getUbicacion().getErrores().hayError()){
            mActivity.esperar(true);
            new ClienteApiRutas(this)
                    .execute(mPedido.getUbicacion().getOrigen(), mPedido.getUbicacion().getDestino());

        }
    }

    @Override
    public void recuperarDatos() {
        Direccion origen = mPedido.getUbicacion().getOrigen();
        mActivity.setCalleOrigen(origen.getCalle());
        mActivity.setNumeroOrigen(origen.getNumero());
        mActivity.setCiudadOrigen(origen.getCiudad());
        mActivity.setComentarioOrigen(origen.getComentario());

        Direccion destino = mPedido.getUbicacion().getDestino();
        mActivity.setCalleDestino(destino.getCalle());
        mActivity.setNumeroDestino(destino.getNumero());
        mActivity.setCiudadDestino(destino.getCiudad());
        mActivity.setComentarioDestino(destino.getComentario());
    }

    @Override
    public void guardarDatos() {
        Direccion origen = mPedido.getUbicacion().getOrigen();
        origen.setCalle(mActivity.getCalleOrigen());
        origen.setNumero(mActivity.getNumeroOrigen());
        origen.setCiudad(mActivity.getCiudadOrigen());
        origen.setComentario(mActivity.getComentarioOrigen());

        Direccion destino = mPedido.getUbicacion().getDestino();
        destino.setCalle(mActivity.getCalleDestino());
        destino.setNumero(mActivity.getNumeroDestino());
        destino.setCiudad(mActivity.getCiudadDestino());
        destino.setComentario(mActivity.getComentarioDestino());
    }

    public void buscarDireccion(int codigo) {
        mCodigoUbicacion = codigo;

        mActivity.esperar(true);
        new ClienteApiDireccion(this).execute(mPedido.getUbicacion().getTemp());
    }

    public void recibirResultadoDireccion(@Nullable Direccion resultado) {
        mActivity.esperar(false);

        if (resultado == null) {
            mActivity.toast("Ocurrió un error al buscar la dirección.");
            return;
        }

        if (mCiudades.contains(resultado.getCiudad())){
            Direccion d = mCodigoUbicacion == Constantes.UBICACION_ORIGEN
                    ? mPedido.getUbicacion().getOrigen()
                    : mPedido.getUbicacion().getDestino();

            d.setCalle(resultado.getCalle());
            d.setNumero(resultado.getNumero());
            d.setCiudad(resultado.getCiudad());
            d.setComentario(resultado.getComentario());
            d.setLat(resultado.getLat());
            d.setLng(resultado.getLng());

            recuperarDatos();
        }
        else {
            mActivity.toast("Disculpá, todavía no trabajamos en " + resultado.getCiudad());
        }
    }

    public void recibirResultadoRuta(int distancia) {
        mActivity.esperar(false);
        mPedido.getUbicacion().setDistancia(distancia);
        mPedido.getPago().calcularMonto(distancia);
        mActivity.siguiente();
    }

    public void direccionModificada() {
        mPedido.getUbicacion().limpiarCoordenadas();
    }
}

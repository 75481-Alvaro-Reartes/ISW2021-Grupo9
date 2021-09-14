package com.example.delivereat.model.pedidos;

import com.example.delivereat.model.otros.ErrorManager;

/**
 * Clase del dominio que representa una ubicación representativa del pedido.
 */
public class Ubicacion {

    private Direccion mOrigen, mDestino, mTemp;

    /**
     * Distancia en metros entre el origen y el destino.
     */
    private int distancia;

    public Ubicacion() {
        mOrigen = new Direccion();
        mDestino = new Direccion();
        mTemp = new Direccion();
    }

    public Direccion getOrigen() {
        return mOrigen;
    }

    public Direccion getDestino() {
        return mDestino;
    }

    public Direccion getTemp() {
        return mTemp;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    private Errores errores = new Errores();

    public Errores getErrores() {
        return errores;
    }

    /**
     * Setea las coordenadas del google maps en cero
     */
    public void limpiarCoordenadas() {
        mDestino.setLat(0);
        mOrigen.setLat(0);
        mOrigen.setLng(0);
        mDestino.setLng(0);
    }

    /**
     * Clase interna con errores de ubicaciones
     */
    public class Errores implements ErrorManager {

        @Override
        public boolean hayError() {
            return mOrigen.getErrores().hayError() || mDestino.getErrores().hayError() || eCiudadesDistintas() || eDireccionDuplicada();
        }

        public boolean eCiudadesDistintas() {
            return !mOrigen.getErrores().eCiudad() && !mDestino.getErrores().eCiudad() && !mOrigen.getCiudad().equals(mDestino.getCiudad());
        }

        public boolean eDireccionDuplicada() {
            return !mOrigen.getErrores().eCalle() && mOrigen.equals(mDestino);
        }

        public Direccion.Errores getErroresOrigen() {
            return mOrigen.getErrores();
        }
        public Direccion.Errores getErroresDestino() {
            return mDestino.getErrores();
        }
    }
}

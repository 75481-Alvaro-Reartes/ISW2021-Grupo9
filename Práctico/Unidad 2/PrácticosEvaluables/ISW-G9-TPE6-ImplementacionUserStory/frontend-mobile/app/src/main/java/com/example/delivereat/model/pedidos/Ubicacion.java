package com.example.delivereat.model.pedidos;

import com.example.delivereat.model.otros.ErrorManager;

public class Ubicacion {

    private Direccion origen, destino, temp;

    /**
     * Distancia en metros entre el origen y el destino.
     */
    private int distancia;

    public Ubicacion() {
        origen = new Direccion();
        destino = new Direccion();
        temp = new Direccion();
    }

    public Direccion getOrigen() {
        return origen;
    }

    public Direccion getDestino() {
        return destino;
    }

    public Direccion getTemp() {
        return temp;
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

    public void limpiarCoordenadas() {
        destino.setLat(0);
        origen.setLat(0);
        origen.setLng(0);
        destino.setLng(0);
    }

    public class Errores implements ErrorManager {

        @Override
        public boolean hayError() {
            return origen.getErrores().hayError() || destino.getErrores().hayError() || eCiudadesDistintas() || eDireccionDuplicada();
        }

        public boolean eCiudadesDistintas() {
            return !origen.getErrores().eCiudad() && !destino.getErrores().eCiudad() && !origen.getCiudad().equals(destino.getCiudad());
        }

        public boolean eDireccionDuplicada() {
            return !origen.getErrores().eCalle() && origen.equals(destino);
        }

        public Direccion.Errores getErroresOrigen() {
            return origen.getErrores();
        }
        public Direccion.Errores getErroresDestino() {
            return destino.getErrores();
        }
    }
}

package com.example.delivereat.model;

public class Ubicacion {

    private Direccion origen, destino, temp;

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

    private Errores errores = new Errores();

    public Errores getErrores() {
        return errores;
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

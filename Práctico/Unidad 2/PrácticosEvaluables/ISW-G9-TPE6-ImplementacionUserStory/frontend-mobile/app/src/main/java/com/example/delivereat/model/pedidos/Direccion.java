package com.example.delivereat.model.pedidos;

import androidx.annotation.NonNull;

import com.example.delivereat.model.otros.ErrorManager;
import com.example.delivereat.util.Constantes;

import java.util.Objects;

public class Direccion {

    private double lat;
    private double lng;
    private String calle;
    private int numero;
    private String ciudad;
    private String comentario;

    public Direccion() {
    }

    public Direccion(String calle, int numero, String ciudad) {
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
    }

    public Direccion(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direccion direccion = (Direccion) o;
        return numero == direccion.numero && Objects.equals(calle, direccion.calle) && Objects.equals(ciudad, direccion.ciudad);
    }

    @NonNull
    @Override
    public String toString() {
        return calle + " " + numero + ", " + ciudad + ". " + comentario;
    }

    // Manejo de errores
    private Errores errores = new Errores();

    public Errores getErrores() {
        return errores;
    }


    public class Errores implements ErrorManager {

        @Override
        public boolean hayError() {
            return eCalle() || eNumero() || eCiudad();
        }

        public boolean eCalle() {
            return calle.length() < Constantes.MIN_CARACTERES;
        }

        public boolean eNumero() {
            return numero == 0;
        }

        public boolean eCiudad() {
            return ciudad.length() < Constantes.MIN_CARACTERES;
        }
    }
}

package com.example.delivereat.model.pedidos;

import androidx.annotation.NonNull;

import com.example.delivereat.model.otros.ErrorManager;
import com.example.delivereat.util.Constantes;

import java.util.Objects;

public class Direccion {

    private double mLat;
    private double mLng;
    private String mCalle;
    private int mNumero;
    private String mCiudad;
    private String mComentario;

    public Direccion() {
    }

    public Direccion(String calle, int numero, String ciudad) {
        this.mCalle = calle;
        this.mNumero = numero;
        this.mCiudad = ciudad;
    }

    public Direccion(double lat, double lng) {
        mLat = lat;
        mLng = lng;
    }
    //settes y getters
    public double getLat() {
        return mLat;
    }

    public double getLng() {
        return mLng;
    }

    public String getCalle() {
        return mCalle;
    }

    public void setCalle(String calle) {
        mCalle = calle;
    }

    public int getNumero() {
        return mNumero;
    }

    public void setNumero(int numero) {
        mNumero = numero;
    }

    public String getCiudad() {
        return mCiudad;
    }

    public void setCiudad(String ciudad) {
        mCiudad = ciudad;
    }

    public String getComentario() {
        return mComentario;
    }

    public void setComentario(String comentario) {
        mComentario = comentario;
    }

    public void setLat(double lat) {
        mLat = lat;
    }

    public void setLng(double lng) {
        mLng = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direccion direccion = (Direccion) o;
        return mNumero == direccion.mNumero
                && Objects.equals(mCalle, direccion.mCalle)
                && Objects.equals(mCiudad, direccion.mCiudad);
    }

    @NonNull
    @Override
    public String toString() {
        return mCalle + " " + mNumero + ", " + mCiudad + ". " + mComentario;
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
            return mCalle.length() < Constantes.MIN_CARACTERES;
        }

        public boolean eNumero() {
            return mNumero == 0;
        }

        public boolean eCiudad() {
            return mCiudad.length() < Constantes.MIN_CARACTERES;
        }
    }
}

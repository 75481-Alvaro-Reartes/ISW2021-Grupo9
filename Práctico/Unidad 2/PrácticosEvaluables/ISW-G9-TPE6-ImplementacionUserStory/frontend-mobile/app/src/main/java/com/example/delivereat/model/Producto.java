package com.example.delivereat.model;

import com.example.delivereat.util.Constantes;

import java.util.List;

public class Producto {

    private String nombre;
    private List<Imagen> imagenes;

    public Producto() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public Imagen getPrimeraImagen() {
        return imagenes.size() == 0
                ? null
                : imagenes.get(0);
    }

    private final Errores errores = new Errores();

    public Errores getErrores() {
        return errores;
    }

    public class Errores implements ErrorManager {

        @Override
        public boolean hayError() {
            return eRequerido();
        }

        public boolean eRequerido() {
            return nombre.length() < Constantes.MIN_CARACTERES;
        }
    }
}

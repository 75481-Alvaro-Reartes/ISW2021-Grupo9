package com.example.delivereat.model;

import android.graphics.Bitmap;

public class Imagen {
    /**
     * Bitmap de la imagen.
     */
    private final Bitmap bm;

    /**
     * Peso en KB de la imagen.
     */
    private final double size;

    public Imagen(Bitmap bm, double size) {
        this.bm = bm;
        this.size = size;
    }

    public Bitmap getBm() {
        return bm;
    }

    public double getSize() {
        return size;
    }
}

package com.example.delivereat.model.otros;

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

    public String getFormatedSize() {
        String stringSize;

        if (size >= 1000) {
            stringSize = round(size / 1000d) + " MB";
        }
        else if (size == 0) {
            stringSize = "0 KB";
        }
        else {
            stringSize = round(size) + " KB";
        }
        return stringSize;
    }

    private double round(double num) {
        return Math.round(num * 100d) / 100d;
    }
}

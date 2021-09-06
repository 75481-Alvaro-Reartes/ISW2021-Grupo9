package com.example.delivereat.model;

import android.net.Uri;

import java.util.Calendar;
import java.util.List;

public class Pedido {

    // Singleton
    private Pedido() {

    }

    private static Pedido instance;

    public static Pedido getInstance() {
        if (instance == null) instance = new Pedido();
        return instance;
    }

    // Propiedades
    private String producto;
    private List<Uri> imagenes;
    private boolean recibirPronto;
    private Calendar fechaHoraRecepcion;

    private double lat, lng;
    private String calleOrigen, calleDestino, comentarioOrigen, comentarioDestino, ciudadOrigen, ciudadDesitno;
    private int numeroOrigen, numeroDestino;

    private MetodoPago metodoPago;
    private double monto;
    private String titular;
    private long tarjeta;
    private int cvc, mesVto, yearVto;

    // Errores
    public Errores errores = new Errores();

    public static class Errores {
        public boolean producto = false;
        public boolean fechaRequerida = false;
        public boolean fechaPasada = false;
        public boolean calleOrigen = false;
        public boolean calleDestino = false;
        public boolean numeroOrigen = false;
        public boolean numeroDestino = false;
        public boolean ciudadOrigen = false;
        public boolean ciudadDestino = false;
        public boolean tarjetaLargo = false;
        public boolean cvc = false;
        public boolean titular = false;
        public boolean mesVto = false;
        public boolean yearVto = false;
        public boolean tarjetaVencida = false;
        public boolean monto = false;
        public boolean tarjetaNoVisa = false;

        public boolean errorFormProducto() {
            return producto || fechaRequerida || fechaPasada;
        }

        public boolean errorFormUbicacion() {
            return calleDestino || calleOrigen || numeroOrigen || numeroDestino || ciudadOrigen || ciudadDestino;
        }

        public boolean errorFormPagos() {
            return tarjetaLargo || cvc || titular || mesVto || yearVto || monto || tarjetaNoVisa || tarjetaVencida;
        }
    }

    private void validarFecha() {
        errores.fechaRequerida = !recibirPronto && fechaHoraRecepcion == null;
        errores.fechaPasada =
                !recibirPronto
                        && fechaHoraRecepcion != null
                        && Calendar.getInstance().getTime().after(fechaHoraRecepcion.getTime());
    }

    // Setters y getters
    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;

        errores.producto = producto.length() < 5 || producto.length() > 250;
    }

    public List<Uri> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Uri> imagenes) {
        this.imagenes = imagenes;
    }

    public boolean isRecibirPronto() {
        return recibirPronto;
    }

    public void setRecibirPronto(boolean recibirPronto) {
        this.recibirPronto = recibirPronto;
        validarFecha();
    }

    public Calendar getFechaHoraRecepcion() {
        return fechaHoraRecepcion;
    }

    public void setFechaHoraRecepcion(Calendar fechaHoraRecepcion) {
        this.fechaHoraRecepcion = fechaHoraRecepcion;
        validarFecha();
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getCalleOrigen() {
        return calleOrigen;
    }

    public void setCalleOrigen(String calleOrigen) {
        this.calleOrigen = calleOrigen;

        errores.calleOrigen = calleOrigen.length() == 0;
    }

    public String getCalleDestino() {
        return calleDestino;
    }

    public void setCalleDestino(String calleDestino) {
        this.calleDestino = calleDestino;

        errores.calleDestino = calleDestino.length() == 0;
    }

    public int getNumeroOrigen() {
        return numeroOrigen;
    }

    public void setNumeroOrigen(int numeroOrigen) {
        this.numeroOrigen = numeroOrigen;

        errores.numeroOrigen = numeroOrigen == -1;
    }

    public int getNumeroDestino() {
        return numeroDestino;
    }

    public void setNumeroDestino(int numeroDestino) {
        this.numeroDestino = numeroDestino;

        errores.numeroDestino = numeroDestino == -1;
    }

    public String getComentarioOrigen() {
        return comentarioOrigen;
    }

    public void setComentarioOrigen(String comentarioOrigen) {
        this.comentarioOrigen = comentarioOrigen;
    }

    public String getComentarioDestino() {
        return comentarioDestino;
    }

    public void setComentarioDestino(String comentarioDestino) {
        this.comentarioDestino = comentarioDestino;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;

        errores.ciudadOrigen = ciudadOrigen.length() == 0;
    }

    public String getCiudadDesitno() {
        return ciudadDesitno;
    }

    public void setCiudadDesitno(String ciudadDesitno) {
        this.ciudadDesitno = ciudadDesitno;

        errores.ciudadDestino = ciudadDesitno.length() == 0;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;

        errores.monto = metodoPago == MetodoPago.Efectivo && monto == -1;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;

        errores.titular = metodoPago == MetodoPago.Tarjeta && titular.length() == 0;
    }

    public long getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(long tarjeta) {
        this.tarjeta = tarjeta;

        errores.tarjetaLargo = metodoPago == MetodoPago.Tarjeta && String.valueOf(tarjeta).length() != 16;
        errores.tarjetaNoVisa = metodoPago == MetodoPago.Tarjeta && String.valueOf(tarjeta).charAt(0) != '4';
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;

        errores.cvc = metodoPago == MetodoPago.Tarjeta && String.valueOf(cvc).length() != 3;
    }

    public int getMesVto() {
        return mesVto;
    }

    public void setMesVto(int mesVto) {
        this.mesVto = mesVto;

        errores.mesVto = metodoPago == MetodoPago.Tarjeta && mesVto == -1;
        validarVto();
    }

    private void validarVto() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int mes = Calendar.getInstance().get(Calendar.MONTH);

        errores.tarjetaVencida = metodoPago == MetodoPago.Tarjeta && yearVto == year && mesVto <= mes;
    }

    public int getYearVto() {
        return yearVto;
    }

    public void setYearVto(int yearVto) {
        this.yearVto = yearVto;

        errores.yearVto = metodoPago == MetodoPago.Tarjeta && yearVto == -1;
        validarVto();
    }
}

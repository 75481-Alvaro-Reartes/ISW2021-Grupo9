package com.example.delivereat.util;

/**
 * Clase contenedora de valores constantes para fututras validcaciones
 */
public class Constantes {

    /**
     * Cantidad mínima de caracteres en cada campo de texto
     */
    public static final int MIN_CARACTERES = 5;
    /**
     * Cantidad minima hora de entrega, una hora posterior al momento actual
     */
    public static final int MIN_HORA_ENTREGA = 1;
    /***
     * Codigo de respuesta para abrir la avtividad con maps
     */
    public static final int UBICACION_ORIGEN = 778;
    public static final int UBICACION_DESTINO = 779;
    /**
     * Son imitaciones de timepos de demora de espera luego de ralizar las transacciones de
     * confirmacion de pedido siendo efectivo o con tarjeta
     */
    public static final int MILIS_ESPERA_EFECTIVO = 3000;
    public static final int MILIS_ESPERA_TARJETA = 8000;
    /**
     * Cte que el mayor peso de la imagen debe ser el asignado. Solo una IMAGEN
     */
    public static final int MAYOR_PESO_IMAGEN_KB = 5000;
    /**
     * Variables que contienen las API KEY y la URL del servidor que provee los servicios de Google
     * Maps
     */
    public static final String KEYCODE = "AIzaSyB9Uuk4x2uMaCjul3jdbFFC8TTN57CD6D4";
    public static final String MAPS_API_URL = "https://maps.googleapis.com/maps/api/";

    public static final String[] MESES = {
            "Enero",
            "Febrero",
            "Marzo",
            "Abril",
            "Mayo",
            "Junio",
            "Julio",
            "Agosto",
            "Septiembre",
            "Octubre",
            "Noviembre",
            "Diciembre"
    };
    public static final int HORA_HABIL_MIN = 8;

    public static final double MONTO_POR_500_METROS = 50;
}

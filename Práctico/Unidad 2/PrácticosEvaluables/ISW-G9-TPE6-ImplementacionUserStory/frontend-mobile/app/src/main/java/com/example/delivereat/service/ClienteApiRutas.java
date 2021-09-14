package com.example.delivereat.service;

import android.os.AsyncTask;

import com.example.delivereat.control.UbicacionControl;
import com.example.delivereat.model.pedidos.Direccion;
import com.example.delivereat.util.Constantes;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class ClienteApiRutas extends AsyncTask<Direccion, Void, String> {

    private final UbicacionControl control;

    public ClienteApiRutas(UbicacionControl control) {
        this.control = control;
    }

    @Override
    protected String doInBackground(Direccion... direcciones) {
        InputStream inputStream;
        StringBuilder resultado = new StringBuilder();

        try {
            inputStream = new URL(Constantes.MAPS_API_URL + "directions/json?origin=" +
                    direcciones[0].getLat() + "," + direcciones[0].getLng() +
                    "&destination=" +
                    direcciones[1].getLat() + "," + direcciones[1].getLng() +
                    "&units=metric&mode=driving" +
                    "&key=" + Constantes.KEYCODE)
                    .openStream();

            if (inputStream != null) {

                BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
                String linea;

                while ((linea = buffer.readLine()) != null){
                    resultado.append(linea);
                }
            }
            assert inputStream != null;
            inputStream.close();

        } catch (Exception e){
            e.printStackTrace();
            return null;

        }
        return resultado.toString();
    }

    @Override
    protected void onPostExecute(String body) {
        if (body == null || body.isEmpty()) {
            control.recibirResultadoRuta(0);
            return;
        }

        try {
            int distancia = new JSONObject(body)
                    .getJSONArray("routes")
                    .getJSONObject(0)
                    .getJSONArray("legs")
                    .getJSONObject(0)
                    .getJSONObject("distance")
                    .getInt("value");

            control.recibirResultadoRuta(distancia);

        } catch (Exception e) {
            e.printStackTrace();
            control.recibirResultadoRuta(0);
        }
    }
}
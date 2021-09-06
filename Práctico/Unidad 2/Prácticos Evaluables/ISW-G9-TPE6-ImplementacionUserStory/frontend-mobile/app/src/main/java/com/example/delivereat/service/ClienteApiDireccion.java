package com.example.delivereat.service;


import android.os.AsyncTask;

import com.example.delivereat.control.UbicacionControl;
import com.example.delivereat.model.Direccion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class ClienteApiDireccion extends AsyncTask<Direccion, Void, String> {

    private static final String KEYCODE = "AIzaSyB9Uuk4x2uMaCjul3jdbFFC8TTN57CD6D4";
    private UbicacionControl control;
    private Direccion direccion;

    public ClienteApiDireccion(UbicacionControl control) {
        this.control = control;
    }

    @Override
    protected String doInBackground(Direccion... direcciones) {
        InputStream inputStream;
        StringBuilder resultado = new StringBuilder();
        direccion = direcciones[0];

        try {
            inputStream = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + direcciones[0].getLat() + "," + direcciones[0].getLng() + "&key=" + KEYCODE).openStream();

            if (inputStream != null) {

                BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
                String linea = "";

                while ((linea = buffer.readLine()) != null){
                    resultado.append(linea);
                }
            }
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
            mostrarError();
            return;
        }

        try {
            JSONObject json = new JSONObject(body);

            JSONArray resultado = json.getJSONArray("results").getJSONObject(0).getJSONArray("address_components");

            direccion.setNumero(resultado.getJSONObject(0).getString("long_name"));
            direccion.setCalle(resultado.getJSONObject(1).getString("long_name"));
            direccion.setCiudad(resultado.getJSONObject(2).getString("long_name"));

            control.setDireccion(direccion);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarError();
        }
    }

    private void mostrarError() {
        control.mostrarError("Ocurrió un error al cargar la dirección.");
    }
}

package com.example.delivereat.service;


import android.os.AsyncTask;

import com.example.delivereat.control.UbicacionControl;
import com.example.delivereat.model.pedidos.Direccion;
import com.example.delivereat.util.Constantes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class ClienteApiDireccion extends AsyncTask<Direccion, Void, String> {

    private final UbicacionControl control;
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
            inputStream = new URL(
                    Constantes.MAPS_API_URL + "geocode/json?latlng=" +
                            direcciones[0].getLat() + "," + direcciones[0].getLng() +
                            "&key=" + Constantes.KEYCODE)
                    .openStream();

            if (inputStream != null) {

                BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
                String linea = "";

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
            control.recibirResultadoDireccion(null);
            return;
        }

        try {
            JSONObject json = new JSONObject(body);

            JSONArray resultado = json.getJSONArray("results").getJSONObject(0).getJSONArray("address_components");

            direccion.setNumero(Integer.parseInt(resultado.getJSONObject(0).getString("long_name")));
            direccion.setCalle(resultado.getJSONObject(1).getString("long_name"));
            String ciudad = resultado.getJSONObject(2).getString("long_name");
            if (ciudad.length() == 3)
                ciudad = resultado.getJSONObject(3).getString("long_name");
            direccion.setCiudad(ciudad);


            // Maps le llama indistintamente Córdoba o Capital, lo dejo siempre en Córdoba
            if (direccion.getCiudad().equals("Capital")) direccion.setCiudad("Córdoba");

            control.recibirResultadoDireccion(direccion);

        } catch (Exception e) {
            e.printStackTrace();
            control.recibirResultadoDireccion(null);
        }
    }
}

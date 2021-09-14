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

/**
 * Clase que hace llamdas asincronas al web service de google maps
 */
public class ClienteApiDireccion extends AsyncTask<Direccion, Void, String> {

    private final UbicacionControl mControl;
    private Direccion mDireccion;

    public ClienteApiDireccion(UbicacionControl control) {
        mControl = control;
    }

    /**
     * Hace el pedido al web service de google maps
     * @param direcciones
     * @return
     */
    @Override
    protected String doInBackground(Direccion... direcciones) {
        InputStream inputStream;
        StringBuilder resultado = new StringBuilder();
        mDireccion = direcciones[0];

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

    /**
     * Procesa el resultado y hace el mapeo de objetos en Json y se lo devuelve a la clase de control
     * @param body
     */
    @Override
    protected void onPostExecute(String body) {
        if (body == null || body.isEmpty()) {
            mControl.recibirResultadoDireccion(null);
            return;
        }

        try {
            JSONObject json = new JSONObject(body);

            JSONArray resultado = json.getJSONArray("results").getJSONObject(0).getJSONArray("address_components");

            mDireccion.setNumero(Integer.parseInt(resultado.getJSONObject(0).getString("long_name")));
            mDireccion.setCalle(resultado.getJSONObject(1).getString("long_name"));
            String ciudad = resultado.getJSONObject(2).getString("long_name");
            if (ciudad.length() == 3)
                ciudad = resultado.getJSONObject(3).getString("long_name");
            mDireccion.setCiudad(ciudad);


            // Maps le llama indistintamente Córdoba o Capital, lo dejo siempre en Córdoba
            if (mDireccion.getCiudad().equals("Capital")) mDireccion.setCiudad("Córdoba");

            mControl.recibirResultadoDireccion(mDireccion);

        } catch (Exception e) {
            e.printStackTrace();
            mControl.recibirResultadoDireccion(null);
        }
    }
}

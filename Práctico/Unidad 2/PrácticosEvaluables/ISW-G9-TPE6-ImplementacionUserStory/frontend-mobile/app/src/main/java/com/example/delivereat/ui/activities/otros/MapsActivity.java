package com.example.delivereat.ui.activities.otros;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.delivereat.R;
import com.example.delivereat.model.Direccion;
import com.example.delivereat.model.Pedido;
import com.example.delivereat.persistencia.Datos;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng posicion;
    private MaterialButton botonAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        botonAceptar = findViewById(R.id.btnAceptarUbicacion);
        botonAceptar.setOnClickListener(x -> {
            Direccion d = Datos.getInstance().getPedido().getUbicacion().getTemp();
            d.setLat(posicion.latitude);
            d.setLng(posicion.longitude);
            setResult(RESULT_OK);
            finish();
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng cordoba = new LatLng(-31.44280144584487, -64.19400222031041);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cordoba, 14));

        mMap.setOnMapClickListener(latLng -> {

            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng));
            posicion = latLng;
            botonAceptar.setEnabled(true);
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_down, R.anim.to_up);
    }
}
package com.example.delivereat.control;

import android.content.Context;
import android.os.Handler;

import com.example.delivereat.ui.activities.otros.LauncherActivity;
import com.example.delivereat.ui.activities.otros.MenuActivity;
import com.example.delivereat.ui.activities.otros.PresentacionActivity;
import com.example.delivereat.util.Constantes;

public class LauncherControl implements IControl{

    private final LauncherActivity activity;

    public LauncherControl(LauncherActivity activity) {
        this.activity = activity;
    }

    @Override
    public void recuperarDatos() {

    }

    @Override
    public void guardarDatos() {

    }

    public void iniciar() {
        Class<?> destino;

        if (esPrimerInicio()) {
            registrarPrimerInicio();
            destino = PresentacionActivity.class;
        }
        else {
            destino = MenuActivity.class;
        }

        new Handler().postDelayed((Runnable) () -> {
            activity.iniciarActividad(destino);
        }, 2000);
    }

    private void registrarPrimerInicio() {
        activity.getPreferences(Context.MODE_PRIVATE)
                .edit()
                .putBoolean(Constantes.PRIMER_INICIO_APP, false)
                .apply();
    }

    private boolean esPrimerInicio() {
        return activity.getPreferences(Context.MODE_PRIVATE)
                .getBoolean(Constantes.PRIMER_INICIO_APP, true);
    }
}

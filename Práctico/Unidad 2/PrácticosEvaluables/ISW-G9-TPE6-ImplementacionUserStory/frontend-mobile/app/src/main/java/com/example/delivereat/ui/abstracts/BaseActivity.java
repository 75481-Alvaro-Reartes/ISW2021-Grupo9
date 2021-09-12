package com.example.delivereat.ui.abstracts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.delivereat.R;
import com.example.delivereat.control.IControl;

/**
 * Extender de esta clase en lugar de AppComparActivity directamente,
 * provee funcionalidad adicional, animaciones, navegación, persistencia
 * de datos al navegar por la app.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private int animFinEntrada = R.anim.from_left;
    private int animFinSalida = R.anim.to_right;
    private int animNuevaEntrada = R.anim.from_right;
    private int animNuevaSalida = R.anim.to_left;

    private IControl mControl;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mControl = getControl();
        iniciarViews();
    }

    /**
     * En este método se debe inicializar el controlador de la activity,
     * guardarlo, y retornarlo.
     * @return Controlador de la activity.
     */
    protected abstract IControl getControl();
    protected abstract int getLayoutId();
    protected abstract void iniciarViews();

    public int getTxtInt(int id, int porDefecto) {
        try{
            return Integer.parseInt(((TextView)findViewById(id)).getText().toString());
        }
        catch (Exception e) {
            return porDefecto;
        }
    }

    public int getTxtInt(int id) {
        return getTxtInt(id, 0);
    }

    public double getTxtDouble(int id) {
        return  getTxtDouble(id, 0);
    }

    public double getTxtDouble(int id, double porDefecto) {
        try{
            return Double.parseDouble(((TextView)findViewById(id)).getText().toString());
        }
        catch (Exception e) {
            return porDefecto;
        }
    }

    public long getTxtLong(int id, long porDefecto) {
        try{
            return Long.parseLong(((TextView)findViewById(id)).getText().toString());
        }
        catch (Exception e) {
            return porDefecto;
        }
    }

    public long getTxtLong(int id) {
        return getTxtLong(id, 0);
    }

    public String getTxtString(int id) {
        try{
            return ((TextView)findViewById(id)).getText().toString();
        }
        catch (Exception e) {
            return "";
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(animFinEntrada, animFinSalida);
        mControl.guardarDatos();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mControl.guardarDatos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mControl.recuperarDatos();
    }

    protected final void setAnimacionNuevaActivity(int animNuevaEntrada, int animNuevaSalida) {
        this.animNuevaEntrada = animNuevaEntrada;
        this.animNuevaSalida = animNuevaSalida;
    }

    protected final void setAnimacionFin(int animFinEntrada, int animFinSalida) {
        this.animFinEntrada = animFinEntrada;
        this.animFinSalida = animFinSalida;
    }

    protected void navegar(Class<?> nuevaActivity){
        startActivity(new Intent(this, nuevaActivity));
        overridePendingTransition(animNuevaEntrada, animNuevaSalida);
    }

    public void toast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    protected void setTxtString(int idTxt, String texto) {
        try {
            ((TextView)findViewById(idTxt)).setText(texto);
        }
        catch (Exception ignored) {

        }
    }
}

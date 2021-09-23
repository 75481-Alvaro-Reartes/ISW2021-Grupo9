package com.example.delivereat.ui.activities.loquesea;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.delivereat.R;
import com.example.delivereat.control.IControl;
import com.example.delivereat.control.ProductosControl;
import com.example.delivereat.model.otros.Imagen;
import com.example.delivereat.model.otros.ImagenFactory;
import com.example.delivereat.model.pedidos.Producto;
import com.example.delivereat.ui.abstracts.BaseActivity;
import com.example.delivereat.ui.abstracts.ObservadorLimpiador;
import com.example.delivereat.util.Constantes;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Clase de la interfaz gráfica de usuario para ingresar el producto del pedido.
 */
public class ProductosActivity extends BaseActivity {

    /**
     * Layout del campo de texto del producto.
     */
    private TextInputLayout mLayProducto;

    /**
     * Label con el peso de la imágen ingresada.
     */
    private TextView mLblPesoImg;

    /**
     * Imágen ingreasda.
     */
    private Imagen mImagen;

    /**
     * View con la imágen ingresada por el usuario.
     */
    private ImageView mImgProducto;

    /**
     * Layout que contiene la imágen del producto y el botón para borrarla.
     */
    private ConstraintLayout mLayoutImagen;

    /**
     * Controlador de la interfaz de usuario.
     */
    private ProductosControl mControl;

    /**
     * Objeto encargado de obtener la imágen del producto.
     */
    ActivityResultLauncher<String> mGetContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_productos;
    }

    @Override
    protected IControl getControl() {
        mControl = new ProductosControl(this);
        return mControl;
    }

    @Override
    protected void iniciarViews() {
        mImgProducto = findViewById(R.id.imgProducto);
        mLblPesoImg = findViewById(R.id.lblPesoImg);
        mLayProducto = findViewById(R.id.txtLayProducto);
        TextInputEditText txtProducto = findViewById(R.id.txtProducto);
        mLayoutImagen = findViewById(R.id.layoutImgProducto);

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                this::mostrarImagen);

        findViewById(R.id.txtSiguiente).setOnClickListener(x -> mControl.clickSiguiente());

        txtProducto.addTextChangedListener(new ObservadorLimpiador() {
            @Override
            public TextInputLayout setEditTextLayout() {
                return mLayProducto;
            }
        });

        findViewById(R.id.btnCargarImg).setOnClickListener(x -> abrirSelectorImagen());
        findViewById(R.id.btnBorrarImg).setOnClickListener(x -> borrarImagen());
    }

    /**
     * Borra la imágen ingresada.
     */
    private void borrarImagen() {
        mImgProducto.setImageBitmap(null);
        mImagen = null;
        mLblPesoImg.setText("0KB");
        mLayoutImagen.setVisibility(View.GONE);
    }

    /**
     * Muestra la imágen ingresada a partir de su URI.
     * @param uri URI de la imágen a mostrar del producto.
     */
    public void mostrarImagen(Uri uri) {
        mImagen = ImagenFactory.fabricar(uri, getContentResolver());

        mostrarImagen(mImagen);
    }

    /**
     * Muestra la imágen del producto a partir de si objeto del modelo.
     * @param imagen Objeto con la imágen del producto.
     */
    public void mostrarImagen(Imagen imagen) {
        if (imagen == null) return;

        if (imagen.getSize() > Constantes.MAYOR_PESO_IMAGEN_KB) {
            toast("La imágen no puede exceder los 5MB.");
            return;
        }

        mImgProducto.setImageBitmap(imagen.getBm());
        mLblPesoImg.setText(imagen.getFormatedSize());
        mLayoutImagen.setVisibility(View.VISIBLE);
    }

    /**
     * Abre la interfaz para selección de imágenes.
     */
    @SuppressLint("IntentReset")
    private void abrirSelectorImagen() {
        mGetContent.launch("image/*");
    }

    /**
     * Obtiene el producto ingresado por el usuario.
     * @return Producto ingresado.
     */
    public String getProducto() {
        return getTxtString(R.id.txtProducto);
    }

    /**
     * Muestra los errores del usuario en la interfaz grpafica.
     * @param errores Objeto con errores en la interfaz del usuario.
     */
    public void setErrores(Producto.Errores errores) {
        mLayProducto.setError(errores.eRequerido()
                ? "Ingresá un producto (5 a 280 caracteres)."
                : "");
    }

    /**
     * Navega hacia la interfaz de ubicación.
     */
    public void siguiente() {
        navegar(UbicacionActivity.class);
    }

    /**
     * Setea el producto previamente ingresado por el usuario.
     * @param producto Producto previamente ingresado.
     */
    public void setProducto(String producto) {
        setTxtString(R.id.txtProducto, producto);
    }

    /**
     * Obtiene la imágen ingresada por el usuario.
     * @return Imágen del producto.
     */
    public Imagen getImagen() {
        return mImagen;
    }
}
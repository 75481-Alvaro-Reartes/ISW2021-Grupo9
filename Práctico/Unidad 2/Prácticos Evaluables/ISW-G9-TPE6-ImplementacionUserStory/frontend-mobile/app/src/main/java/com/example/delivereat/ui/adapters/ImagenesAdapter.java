package com.example.delivereat.ui.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delivereat.R;
import com.example.delivereat.control.ProductosControl;
import com.example.delivereat.ui.ProductosActivity;

import java.util.ArrayList;
import java.util.List;

public class ImagenesAdapter extends RecyclerView.Adapter<ImagenesAdapter.ViewHolder> {

    private ProductosActivity activity;

    public ImagenesAdapter(ProductosActivity activity) {
        this.imagenes = new ArrayList<>();
        this.activity = activity;
    }

    public void addImagen(Uri uri) {
        imagenes.add(uri);
        notifyDataSetChanged();
    }

    private List<Uri> imagenes;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_imagen, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getImagen().setImageURI(imagenes.get(position));
    }

    @Override
    public int getItemCount() {
        return imagenes.size();
    }

    public void quitarImagen(int pos) {
        Uri borrada = imagenes.get(pos);
        imagenes.remove(pos);
        notifyDataSetChanged();
        activity.imagenEliminada(borrada);
    }

    public List<Uri> getImagenes() {
        return imagenes;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagen = itemView.findViewById(R.id.imgItem);
            itemView.findViewById(R.id.btnBorrarImg).setOnClickListener(x -> quitarImagen(getAdapterPosition()));
        }

        public ImageView getImagen() {
             return imagen;
        }
    }
}

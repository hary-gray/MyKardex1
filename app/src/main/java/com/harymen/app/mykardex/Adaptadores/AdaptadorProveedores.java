package com.harymen.app.mykardex.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.harymen.app.mykardex.BaseDeDatos.MyBaseDeDatos;
import com.harymen.app.mykardex.Entidades.Entrada;
import com.harymen.app.mykardex.Entidades.Producto;
import com.harymen.app.mykardex.R;

import java.util.ArrayList;

public class AdaptadorProveedores extends RecyclerView.Adapter<AdaptadorProveedores.ProveedoresViewHolder> {
    private ArrayList<String> proveedores;
    private Activity activity;
    private Context context;
    private MyBaseDeDatos myBaseDeDatos;

    public AdaptadorProveedores(ArrayList<String> proveedores, Activity activity, Context context) {
        this.proveedores = proveedores;
        this.activity = activity;
        this.context = context;
        myBaseDeDatos = new MyBaseDeDatos(context);
    }

    @NonNull
    @Override
    public ProveedoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proovedor,parent,false);
        return new ProveedoresViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProveedoresViewHolder holder, int position) {
        final String proveedor = proveedores.get(position);
        holder.tvNombre.setText(proveedor);
        ArrayList<Producto> productos = myBaseDeDatos.todosLosProducto();
        ArrayList<Entrada> entradas = myBaseDeDatos.entradasPorProveedor(proveedor);
        String stEntradas="";
        if(productos.size() > 0){
            stEntradas = "";
            for (int i = 0; i < productos.size(); i++){
                Double totalEntradas = 0.0;
                if(entradas.size() > 0){
                    for(int m = 0; m < entradas.size(); m++){
                        if(entradas.get(m).getRefProducto() == productos.get(i).getReferencia()){
                            totalEntradas += entradas.get(m).getCantidad();
                        }
                    }
                }
                if(totalEntradas > 0){
                    if(stEntradas.equals("")){
                        stEntradas += productos.get(i).getArticulo() + " de " + productos.get(i).getMaterial() + ": " + totalEntradas
                        + " " + productos.get(i).getUnidadMetrica();
                    } else {
                        stEntradas += "\n \n" + productos.get(i).getArticulo() + " de " + productos.get(i).getMaterial() + ": " + totalEntradas
                                + " " + productos.get(i).getUnidadMetrica();
                    }
                }
            }
        }
        holder.tvEntradas.setText(stEntradas);
    }

    @Override
    public int getItemCount() {
        return proveedores.size();
    }


    final static class ProveedoresViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre;
        private TextView tvEntradas;
        public ProveedoresViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = (TextView) itemView.findViewById(R.id.tvNombre_itemProveedor);
            tvEntradas = (TextView) itemView.findViewById(R.id.tvEntradas_itemProveedor);
        }
    }
}

package com.harymen.app.mykardex.Adaptadores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.harymen.app.mykardex.BaseDeDatos.MyBaseDeDatos;
import com.harymen.app.mykardex.Entidades.Entrada;
import com.harymen.app.mykardex.Entidades.Producto;
import com.harymen.app.mykardex.Entidades.Salida;
import com.harymen.app.mykardex.MainActivity;
import com.harymen.app.mykardex.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AdaptadorProducto extends RecyclerView.Adapter<AdaptadorProducto.ProductoViewHolder> {
    private ArrayList<Producto> productos;
    private int dia;
    private int mes;
    private int año;
    private Activity activity;
    private Context context;
    private AlertDialog.Builder myDialog;

    private MyBaseDeDatos myBaseDeDatos;

    public AdaptadorProducto(ArrayList<Producto> productos, int dia, int mes, int año, Activity activity, Context context) {
        this.productos = productos;
        this.activity = activity;
        this.context = context;
        this.dia = dia;
        this.mes = mes;
        this.año = año;
        myBaseDeDatos = new MyBaseDeDatos(context);
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto,parent,false);
        return new ProductoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        final  Producto producto = productos.get(position);
        String nombre = producto.getArticulo()+ " de " + producto.getMaterial();
        holder.tvProducto.setText(nombre);
        ArrayList<Entrada> entradas = myBaseDeDatos.entradasPorProducto(producto.getReferencia());
        ArrayList<Salida> salidas = myBaseDeDatos.obtenerSalidas(producto.getReferencia());
        Calendar fecha = Calendar.getInstance();
        fecha.set(Calendar.YEAR,año);
        fecha.set(Calendar.MONTH,mes);
        fecha.set(Calendar.DAY_OF_MONTH,dia);
        Double totalEntradas = 0.0;
        Double totalSalidas = 0.0;
        if(entradas.size() > 0){
            for(int m = 0; m < entradas.size(); m++){
                Calendar fechaEntrada = Calendar.getInstance();
                fechaEntrada.set(Calendar.YEAR,entradas.get(m).getAño());
                fechaEntrada.set(Calendar.MONTH,entradas.get(m).getMes());
                fechaEntrada.set(Calendar.DAY_OF_MONTH,entradas.get(m).getDia());
                if(fecha.compareTo(fechaEntrada) >= 0){
                    totalEntradas += entradas.get(m).getCantidad();
                }
            }
        }
        if(salidas.size() > 0){
            for(int m = 0; m < salidas.size(); m++){
                Calendar fechaSalida = Calendar.getInstance();
                fechaSalida.set(Calendar.YEAR,salidas.get(m).getAño());
                fechaSalida.set(Calendar.MONTH,salidas.get(m).getMes());
                fechaSalida.set(Calendar.DAY_OF_MONTH,salidas.get(m).getDia());
                if(fecha.compareTo(fechaSalida) >= 0){
                    totalSalidas += salidas.get(m).getCantidad();
                }
            }
        }
        holder.tvEntradas.setText("Entradas: " + totalEntradas+ " " + producto.getUnidadMetrica());
        holder.tvSalidas.setText("Salidas: "+totalSalidas+ " " + producto.getUnidadMetrica());
        holder.tvDisponibles.setText("Disponibles: " + (totalEntradas-totalSalidas) + " " + producto.getUnidadMetrica());
        holder.btEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog = new AlertDialog.Builder(context);
                myDialog.setMessage(R.string.eliminar_producto);
                myDialog.setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myBaseDeDatos.elimiarProducto(producto);
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
                myDialog.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog = myDialog.create();
                dialog.show();
            }
        });
        if(producto.getEstado() == 1){
            holder.btActivar.setText(R.string.inhabilitar);
            holder.rl.setBackgroundColor(activity.getResources().getColor(R.color.colorGreen));
        } else if(producto.getEstado() == 0){
            holder.btActivar.setText(R.string.habilitar);
            holder.rl.setBackgroundColor(activity.getResources().getColor(R.color.colorRed));
        }
        holder.btActivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(producto.getEstado() == 1){
                    producto.setEstado(0);
                    myBaseDeDatos.actualizarProducto(producto);
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                } else if(producto.getEstado() == 0){
                    producto.setEstado(1);
                    myBaseDeDatos.actualizarProducto(producto);
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    final static class ProductoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvProducto;
        private TextView tvEntradas;
        private TextView tvSalidas;
        private TextView tvDisponibles;
        private Button btActualizar;
        private Button btEliminar;
        private Button btActivar;
        private RelativeLayout rl;
        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProducto = (TextView) itemView.findViewById(R.id.tvProducto_itemProducto);
            tvEntradas = (TextView) itemView.findViewById(R.id.tvEntradas_itemProducto);
            tvSalidas = (TextView) itemView.findViewById(R.id.tvSalidas_itemProducto);
            tvDisponibles = (TextView) itemView.findViewById(R.id.tvDisponible_itemProducto);
            btActualizar = (Button) itemView.findViewById(R.id.btActualizar_itemProducto);
            btEliminar = (Button) itemView.findViewById(R.id.btEliminar_itemProducto);
            btActivar = (Button) itemView.findViewById(R.id.btActivar_itemProducto);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl_itemProducto);
        }
    }
}

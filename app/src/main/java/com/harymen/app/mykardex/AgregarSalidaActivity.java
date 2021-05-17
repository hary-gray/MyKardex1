package com.harymen.app.mykardex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.harymen.app.mykardex.BaseDeDatos.MyBaseDeDatos;
import com.harymen.app.mykardex.Entidades.Articulo;
import com.harymen.app.mykardex.Entidades.Entrada;
import com.harymen.app.mykardex.Entidades.Producto;
import com.harymen.app.mykardex.Entidades.Salida;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AgregarSalidaActivity extends AppCompatActivity {
    private Spinner spProducto;
    private EditText etCantidad;
    private TextView tvFecha;
    private Button btVolver;
    private Button btAddSalida;

    private MyBaseDeDatos myBaseDeDatos;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private AlertDialog.Builder myDialog;
    private Calendar fecha;
    private int dia;
    private int mes;
    private int año;
    private ArrayList<Producto> productos;
    private ArrayList<String> proveedores;
    private Locale locale;
    private NumberFormat numberFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_salida);
        spProducto = (Spinner) findViewById(R.id.spProctucto_AddSalida);
        etCantidad = (EditText) findViewById(R.id.etCantidad_AddSalida);
        tvFecha = (TextView) findViewById(R.id.tvFecha_AddSalida);
        btVolver = (Button) findViewById(R.id.btVolver_AddSalida);
        btAddSalida = (Button) findViewById(R.id.btAddSalida_AddSalida);
        myBaseDeDatos = new MyBaseDeDatos(this);
        fecha = Calendar.getInstance();
        dia = fecha.get(Calendar.DAY_OF_MONTH);
        mes = fecha.get(Calendar.MONTH);
        año = fecha.get(Calendar.YEAR);
        String movDate = dia + "/" + monthsOfYear(mes) + "/" + año;
        tvFecha.setText(movDate);
        productos = myBaseDeDatos.todosLosProducto();
        proveedores = myBaseDeDatos.obtenerProveedores();
        ArrayList<String> nombresProducto = new ArrayList<>();
        if(productos.size() > 0){
            for(int i = 0; i < productos.size(); i++){
                if(productos.get(i).getEstado() == 1){
                    nombresProducto.add(productos.get(i).getArticulo() + " de " + productos.get(i).getMaterial());
                }

            }
        }
        ArrayAdapter<String> adaptadorProductos = new ArrayAdapter<>(this,R.layout.item_spinner,nombresProducto);
        spProducto.setAdapter(adaptadorProductos);
        locale = new Locale("en","us");
        numberFormat = NumberFormat.getCurrencyInstance(locale);

        setBtAddSalida();
        setTvFecha();
        setOnDateSetListener();
        setBtVolver();
    }
    private void BtAddSalida(){
        Boolean ok = true;
        final Producto producto = productos.get(spProducto.getSelectedItemPosition());
        final Double cantidad = Double.parseDouble(0 + etCantidad.getText().toString());
       if(cantidad <= 0){
            Toast.makeText(this, R.string.cantidad_vacia, Toast.LENGTH_SHORT).show();
            ok = false;
        } else if(cantidad > producto.getDisponible()){
           Toast.makeText(this, R.string.cantidad_exedida, Toast.LENGTH_SHORT).show();
           ok = false;
       }
        if(ok){
            ArrayList<Articulo> articulos = myBaseDeDatos.obtenerArticulos();
            Double minimo = 0.0;
            Double articulosDisponibles = 0.0;
            String articulo = producto.getArticulo();
            if(articulos.size() > 0){
                for (int i = 0; i < articulos.size(); i++){
                    if(articulos.get(i).getNombre().equals(articulo)){
                        minimo = articulos.get(i).getMinimo();
                    }
                }
            }
            if(productos.size() > 0){
                for(int i = 0; i < productos.size(); i++){
                    if (productos.get(i).getArticulo().equals(articulo)){
                        articulosDisponibles += productos.get(i).getDisponible();
                    }
                }
            }
            if((articulosDisponibles-cantidad) <= minimo){
                myDialog = new AlertDialog.Builder(this);
                myDialog.setMessage(R.string.cantidad_minima);
                myDialog.setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Salida salida = new Salida(dia,mes,año,cantidad,producto.getReferencia());
                        myBaseDeDatos.addSalida(salida);
                        Intent intent = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = myDialog.create();
                dialog.show();
            }

        }
    }
    private void setBtAddSalida(){
        btAddSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BtAddSalida();
            }
        });
    }

    private void TvFecha(){
        datePickerDialog = new DatePickerDialog(this, android.app.AlertDialog.THEME_HOLO_DARK,onDateSetListener ,año,mes,dia);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }
    private void setTvFecha(){
        tvFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TvFecha();
            }
        });
    }
    private void setOnDateSetListener(){
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override //año, mes, dia
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                String movDate = i2 + "/" + monthsOfYear(i1) + "/" + i;
                tvFecha.setText(movDate);
                dia = i2;
                mes = i1;
                año = i;
            }
        };
    }
    private String monthsOfYear(int num){
        if(num==0){return getResources().getString(R.string.jan);}
        else if(num==1){return getResources().getString(R.string.feb);}
        else if(num==2){return getResources().getString(R.string.mar);}
        else if(num==3){return getResources().getString(R.string.apr);}
        else if(num==4){return getResources().getString(R.string.may);}
        else if(num==5){return getResources().getString(R.string.jun);}
        else if(num==6){return getResources().getString(R.string.jul);}
        else if(num==7){return getResources().getString(R.string.ago);}
        else if(num==8){return getResources().getString(R.string.sep);}
        else if(num==9){return getResources().getString(R.string.oct);}
        else if(num==10){return getResources().getString(R.string.nov);}
        else if(num==11){return getResources().getString(R.string.dec);}
        else{return "";}
    }

    private void setBtVolver(){
        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
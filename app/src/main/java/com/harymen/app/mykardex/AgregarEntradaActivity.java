package com.harymen.app.mykardex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
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

import com.harymen.app.mykardex.Adaptadores.AdaptadorProducto;
import com.harymen.app.mykardex.BaseDeDatos.MyBaseDeDatos;
import com.harymen.app.mykardex.Entidades.Entrada;
import com.harymen.app.mykardex.Entidades.Producto;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AgregarEntradaActivity extends AppCompatActivity {
    private Spinner spProducto;
    private Spinner spProveedor;
    private EditText etFactura;
    private EditText etCantidad;
    private EditText etValor;
    private TextView tvFecha;
    private TextView tvValor;
    private Button btVolver;
    private Button btAddEntrada;

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
        setContentView(R.layout.activity_agregar_entrada);
        spProducto = (Spinner) findViewById(R.id.spProctucto_AddEntrada);
        spProveedor = (Spinner) findViewById(R.id.spProveedor_AddEntrada);
        etFactura = (EditText) findViewById(R.id.etFactura_AddEntrada);
        etCantidad = (EditText) findViewById(R.id.etCantidad_AddEntrada);
        etValor = (EditText) findViewById(R.id.etValor_AddEntrada);
        tvFecha = (TextView) findViewById(R.id.tvFecha_AddEntrada);
        tvValor = (TextView) findViewById(R.id.tvValor_AddEntrada);
        btVolver = (Button) findViewById(R.id.btVolver_AddEntrada);
        btAddEntrada = (Button) findViewById(R.id.btAddEntrada_AddEntrada);
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
        ArrayAdapter<String> adaptadorProveedores = new ArrayAdapter<>(this,R.layout.item_spinner,proveedores);
        spProveedor.setAdapter(adaptadorProveedores);
        locale = new Locale("en","us");
        numberFormat = NumberFormat.getCurrencyInstance(locale);

        setBtAddEntrada();
        setTvFecha();
        setOnDateSetListener();
        setEtValor();
        setBtVolver();
    }

    private void BtAddEntrada(){
        Boolean ok = true;
        int producto = productos.get(spProducto.getSelectedItemPosition()).getReferencia();
        String proveedor = proveedores.get(spProveedor.getSelectedItemPosition());
        String factura = etFactura.getText().toString().trim();
        Double cantidad = Double.parseDouble(0 + etCantidad.getText().toString());
        Double valor = Double.parseDouble(0 + etValor.getText().toString());
        if(factura.equals("")){
            Toast.makeText(this, R.string.factura_vacia, Toast.LENGTH_SHORT).show();
            ok = false;
        } else if(cantidad <= 0){
            Toast.makeText(this, R.string.cantidad_vacia, Toast.LENGTH_SHORT).show();
            ok = false;
        } else if(valor <= 0){
            Toast.makeText(this, R.string.valor_vacio, Toast.LENGTH_SHORT).show();
            ok = false;
        }
        if(ok){
            Entrada entrada = new Entrada(dia,mes,año,factura,producto,cantidad,valor,proveedor);
            myBaseDeDatos.addEntrada(entrada);
            Intent intent = new Intent(getBaseContext(),MainActivity.class);
            startActivity(intent);
        }
    }
    private void setBtAddEntrada(){
        btAddEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BtAddEntrada();
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
    private void setEtValor(){
        etValor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Double amount =Double.parseDouble( 0 + etValor.getText().toString());
                tvValor.setText(numberFormat.format(amount));
            }
        });
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
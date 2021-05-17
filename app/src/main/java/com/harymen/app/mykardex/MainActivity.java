package com.harymen.app.mykardex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.harymen.app.mykardex.Adaptadores.AdapradorFacturas;
import com.harymen.app.mykardex.Adaptadores.AdaptadorProducto;
import com.harymen.app.mykardex.Adaptadores.AdaptadorProveedores;
import com.harymen.app.mykardex.BaseDeDatos.Constantes;
import com.harymen.app.mykardex.BaseDeDatos.MyBaseDeDatos;
import com.harymen.app.mykardex.Entidades.Articulo;
import com.harymen.app.mykardex.Entidades.Entrada;
import com.harymen.app.mykardex.Entidades.Producto;
import com.harymen.app.mykardex.Entidades.Salida;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spVista;
    private TextView tvFecha;
    private RecyclerView rv;
    private Button btEntradas;
    private Button btSalidas;
    private Button btNuevoProducto;
    private Button btBuscar;
    private EditText etFactura;


    private String[] spMain;
    private SharedPreferences myShared;
    private MyBaseDeDatos myBaseDeDatos;
    private ArrayAdapter<String> adapter;
    private ArrayList<Producto> productos;
    private int dia;
    private int mes;
    private int año;
    private Calendar fecha;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spVista = (Spinner) findViewById(R.id.spVista_Main);
        tvFecha = (TextView) findViewById(R.id.tvFecha_Main);
        rv = (RecyclerView) findViewById(R.id.rv_Main);
        btEntradas = (Button) findViewById(R.id.btEntradas_Main);
        btSalidas = (Button) findViewById(R.id.btSalidas_Main);
        btNuevoProducto = (Button) findViewById(R.id.btNuevoProducto_Main);
        btBuscar = (Button) findViewById(R.id.btBuscar_Main);
        etFactura = (EditText) findViewById(R.id.etFactura_Main);
        spMain = getResources().getStringArray(R.array.spMain);
        adapter = new ArrayAdapter<>(this,R.layout.item_spinner,spMain);
        spVista.setAdapter(adapter);
        spVista.setOnItemSelectedListener(this);

        myShared = getSharedPreferences(Constantes.NOMBRE_SHARED,MODE_PRIVATE);
        myBaseDeDatos = new MyBaseDeDatos(this);
        int inicio = myShared.getInt(Constantes.INICIO,0);

        if(inicio == 0){
            myBaseDeDatos.addProveedor("La alfombra mágica");
            myBaseDeDatos.addProveedor("Rubio y compañia");
            myBaseDeDatos.addProveedor("Mesas industriales");
            myBaseDeDatos.addProveedor("Las sillas del mañana");
            myBaseDeDatos.addProveedor("Sillas y mesas de lujo");

            myBaseDeDatos.addMaterial("Paño");
            myBaseDeDatos.addMaterial("Cuero");
            myBaseDeDatos.addMaterial("Metal");
            myBaseDeDatos.addMaterial("Plástico");
            myBaseDeDatos.addMaterial("Lana");
            myBaseDeDatos.addMaterial("Material Sintético");

            ArrayList<Articulo> articulos = new ArrayList<>();
            articulos.add(new Articulo("Silla",100.0));
            articulos.add(new Articulo("Mesa",22.0));
            articulos.add(new Articulo("Alfombra",1000.0));

            for (int m = 0; m < articulos.size(); m++){
                myBaseDeDatos.addArticulo(articulos.get(m));
            }

            productos = new ArrayList<>();
            productos.add(new Producto("Silla","Paño",0.0,"Unidades"));
            productos.add(new Producto("Silla","Cuero",0.0,"Unidades"));
            productos.add(new Producto("Mesa","Metal",0.0,"Unidades"));
            productos.add(new Producto("Mesa","Plástico",0.0,"Unidades"));
            productos.add(new Producto("Alfombra","Lana",0.0,"metros"));
            productos.add(new Producto("Alfombra","Material Sintético",0.0,"metros"));

            for (int m = 0; m < productos.size(); m++){
                myBaseDeDatos.addProducto(productos.get(m));
            }
            ArrayList<Entrada> entradas = new ArrayList<>();
            entradas.add(new Entrada(12,0,2017,"2341",1,50.0,135000.0,"Sillas y mesas de lujo"));
            entradas.add(new Entrada(8,5,2017,"9876",1,32.0,138000.0,"Las sillas del mañana"));
            entradas.add(new Entrada(12,0,2017,"2341",2,62.0,105000.0,"Sillas y mesas de lujo"));
            entradas.add(new Entrada(12,0,2017,"2341",3,34.0,186000.0,"Sillas y mesas de lujo"));
            entradas.add(new Entrada(5,7,2017,"8756",3,16.0,188000.0,"Mesas industriales"));
            entradas.add(new Entrada(12,11,2017,"9311",3,5.0,175000.0,"Rubio y compañia"));
            entradas.add(new Entrada(9,8,2017,"7654",5,800.0,93800.0,"La alfombra mágica"));
            entradas.add(new Entrada(9,8,2017,"7654",6,1239.0,31200.0,"La alfombra mágica"));

            for (int m = 0; m < entradas.size(); m++){
                myBaseDeDatos.addEntrada(entradas.get(m));
            }

            ArrayList<Salida> salidas = new ArrayList<>();
            salidas.add(new Salida(12,2,2017,10.0,1));
            salidas.add(new Salida(14,2,2017,2.0,2));
            salidas.add(new Salida(15,2,2017,4.0,3));

            for (int m = 0; m < salidas.size(); m++){
                myBaseDeDatos.addSalida(salidas.get(m));
            }

            SharedPreferences.Editor editor = myShared.edit();
            editor.putInt(Constantes.INICIO,1);
            editor.commit();
        }
        productos = myBaseDeDatos.todosLosProducto();
        fecha = Calendar.getInstance();
        dia = fecha.get(Calendar.DAY_OF_MONTH);
        mes = fecha.get(Calendar.MONTH);
        año = fecha.get(Calendar.YEAR);
        String movDate = dia + "/" + monthsOfYear(mes) + "/" + año;
        tvFecha.setText(movDate);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(llm);

        setTvFecha();
        setOnDateSetListener(this,this);
        setBtBuscar();
        setBtEntradas();
        setBtNuevoProducto();
        setBtSalidas();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectAdapter = adapterView.getAdapter().toString();
        if(selectAdapter.equals(adapter.toString())){
            if(i == 0){
                AdaptadorProducto adaptadorProducto = new AdaptadorProducto(productos,dia,mes,año,this,this);
                rv.setAdapter(adaptadorProducto);
                tvFecha.setVisibility(View.VISIBLE);
                btBuscar.setVisibility(View.INVISIBLE);
                etFactura.setVisibility(View.INVISIBLE);
            }else if(i == 1){
                ArrayList<String> proveedores = myBaseDeDatos.obtenerProveedores();
                AdaptadorProveedores adaptadorProveedores = new AdaptadorProveedores(proveedores,this,this);
                rv.setAdapter(adaptadorProveedores);
                tvFecha.setVisibility(View.INVISIBLE);
                btBuscar.setVisibility(View.INVISIBLE);
                etFactura.setVisibility(View.INVISIBLE);
            } else if(i == 2){
                BtBuscar();
                tvFecha.setVisibility(View.INVISIBLE);
                btBuscar.setVisibility(View.VISIBLE);
                etFactura.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
    private void setOnDateSetListener(final Activity activity, final Context context){
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override //año, mes, dia
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                String movDate = i2 + "/" + monthsOfYear(i1) + "/" + i;
                tvFecha.setText(movDate);
                dia = i2;
                mes = i1;
                año = i;
                AdaptadorProducto adaptadorProducto = new AdaptadorProducto(productos,dia,mes,año,activity,context);
                rv.setAdapter(adaptadorProducto);

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

    private void BtBuscar(){
        ArrayList<String> facatuas = new ArrayList<>();
        facatuas.add(etFactura.getText().toString());
        AdapradorFacturas adapradorFacturas = new AdapradorFacturas(facatuas,this,this);
        rv.setAdapter(adapradorFacturas);
    }
    private void setBtBuscar(){
        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BtBuscar();
            }
        });
    }
    private void setBtEntradas(){
        btEntradas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),AgregarEntradaActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setBtNuevoProducto(){
        btNuevoProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),AgregarProductoActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setBtSalidas(){
        btSalidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),AgregarSalidaActivity.class);
                startActivity(intent);
            }
        });
    }
}
package com.harymen.app.mykardex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.harymen.app.mykardex.BaseDeDatos.MyBaseDeDatos;
import com.harymen.app.mykardex.Entidades.Articulo;
import com.harymen.app.mykardex.Entidades.Producto;

import java.util.ArrayList;

public class AgregarProductoActivity extends AppCompatActivity {
    private Spinner spArticulo;
    private Spinner spMaterial;
    private Spinner spUnidadMetrica;
    private Button btVolver;
    private Button btAddProducto;

    private ArrayList<Articulo> articulos;
    private ArrayList<String> materiales;
    private String[] unidades;
    private MyBaseDeDatos myBaseDeDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);
        spArticulo = (Spinner) findViewById(R.id.spArticulo_AddProducto);
        spMaterial = (Spinner) findViewById(R.id.spMaterial_AddProducto);
        spUnidadMetrica = (Spinner) findViewById(R.id.spUnidadMetrica_AddProducto);
        btVolver = (Button) findViewById(R.id.btVolver_AddProducto);
        btAddProducto = (Button) findViewById(R.id.btAddProducto_AddProducto);

        myBaseDeDatos = new MyBaseDeDatos(this);
        articulos = myBaseDeDatos.obtenerArticulos();
        ArrayList<String> nombreArticulos = new ArrayList<>();
        if(articulos.size() > 0){
            for(int i = 0; i < articulos.size(); i ++){
                nombreArticulos.add(articulos.get(i).getNombre());
            }
        }
        materiales = myBaseDeDatos.obtenerMateriales();
        unidades = getResources().getStringArray(R.array.unidadesMetricas);

        ArrayAdapter<String> adpArticulos = new ArrayAdapter<>(this,R.layout.item_spinner,nombreArticulos);
        ArrayAdapter<String> adpMateriales = new ArrayAdapter<>(this,R.layout.item_spinner,materiales);
        ArrayAdapter<String> adpUnidades = new ArrayAdapter<>(this,R.layout.item_spinner,unidades);

        spArticulo.setAdapter(adpArticulos);
        spUnidadMetrica.setAdapter(adpUnidades);
        spMaterial.setAdapter(adpMateriales);

        setBtVolver();
        setBtAddProducto();
    }
    private void BtAddProducto(){
        Boolean ok = true;
        String articulo = articulos.get(spArticulo.getSelectedItemPosition()).getNombre();
        String material = materiales.get(spMaterial.getSelectedItemPosition());
        String unidad = unidades[spUnidadMetrica.getSelectedItemPosition()];
        if(ok){
            Producto producto = new Producto(articulo,material,0.0,unidad);
            myBaseDeDatos.addProducto(producto);
            Intent intent = new Intent(getBaseContext(),MainActivity.class);
            startActivity(intent);
        }
    }
    private void  setBtAddProducto(){
        btAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BtAddProducto();
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
package com.harymen.app.mykardex.BaseDeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.harymen.app.mykardex.Entidades.Articulo;
import com.harymen.app.mykardex.Entidades.Entrada;
import com.harymen.app.mykardex.Entidades.Producto;
import com.harymen.app.mykardex.Entidades.Salida;

import java.util.ArrayList;

public class MyBaseDeDatos extends SQLiteOpenHelper {
    public MyBaseDeDatos(@Nullable Context context) {
        super(context, Constantes.NOMBRE_BASE_DATOS, null, Constantes.VERSION_BASE_DATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryTablaProductos = "CREATE TABLE " + Constantes.TABLAPRODUCTO + " ("
                + Constantes.Referencia_TABLAPRODUCTO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constantes.Articulo_TABLAPRODUCTO + " TEXT, "
                + Constantes.Material_TABLAPRODUCTO + " TEXT, "
                + Constantes.Disponible_TABLAPRODUCTO + " DOUBLE, "
                + Constantes.Estado_TABLAPRODUCTO + " INTEGER, "
                + Constantes.UnidadMetrica_TABLAPRODUCTO + " TEXT "
                +")";

        String queryTablaEntradas = "CREATE TABLE " + Constantes.TABLAENTRADAS + " ("
                + Constantes.Referencia_TABLAENTRADAS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constantes.Dia_TABLAENTRADAS + " INTEGER, "
                + Constantes.Mes_TABLAENTRADAS + " INTEGER, "
                + Constantes.Año_TABLAENTRADAS + " INTEGER, "
                + Constantes.Factura_TABLAENTRADAS + " TEXT, "
                + Constantes.RefProducto_TABLAENTRADAS + " INTEGER, "
                + Constantes.Cantidad_TABLAENTRADAS + " DOUBLE, "
                + Constantes.ValorUnidad_TABLAENTRADAS + " DOUBLE, "
                + Constantes.Proveedor_TABLAENTRADAS + " TEXT "
                +")";

        String queryTablaSalidas = "CREATE TABLE " + Constantes.TABLASALIDAS + " ("
                + Constantes.Referencia_TABLASALIDAS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constantes.Dia_TABLASALIDAS + " INTEGER, "
                + Constantes.Mes_TABLASALIDAS + " INTEGER, "
                + Constantes.Año_TABLASALIDAS + " INTEGER, "
                + Constantes.RefProducto_TABLASALIDAS + " INTEGER, "
                + Constantes.Cantidad_TABLASALIDAS + " DOUBLE "
                +")";

        String queryTablaArticulos = "CREATE TABLE " + Constantes.TABLAARTICULOS + " ("
                + Constantes.Nombre_TABLAARTICULOS + " TEXT, "
                + Constantes.Minimo_TABLAARTICULOS + " DOUBLE) ";

        String queryTablaProveedores = "CREATE TABLE " + Constantes.TABLAPROVEEDORES + " ("
                + Constantes.Nombre_TABLAPROVEEDORES + " TEXT) ";

        String queryTablaMateriales = "CREATE TABLE " + Constantes.TABLAMATEIRALES + " ("
                + Constantes.Nombre_TABLAMATERIALES + " TEXT) ";

        sqLiteDatabase.execSQL(queryTablaProductos);
        sqLiteDatabase.execSQL(queryTablaEntradas);
        sqLiteDatabase.execSQL(queryTablaSalidas);
        sqLiteDatabase.execSQL(queryTablaArticulos);
        sqLiteDatabase.execSQL(queryTablaProveedores);
        sqLiteDatabase.execSQL(queryTablaMateriales);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addProducto(Producto producto){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constantes.Articulo_TABLAPRODUCTO,producto.getArticulo());
        contentValues.put(Constantes.Material_TABLAPRODUCTO,producto.getMaterial());
        contentValues.put(Constantes.Disponible_TABLAPRODUCTO,producto.getDisponible());
        contentValues.put(Constantes.Estado_TABLAPRODUCTO,producto.getEstado());
        contentValues.put(Constantes.UnidadMetrica_TABLAPRODUCTO,producto.getUnidadMetrica());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(Constantes.TABLAPRODUCTO,null,contentValues);
        db.close();
    }

    public void actualizarProducto(Producto producto){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constantes.Articulo_TABLAPRODUCTO,producto.getArticulo());
        contentValues.put(Constantes.Material_TABLAPRODUCTO,producto.getMaterial());
        contentValues.put(Constantes.Disponible_TABLAPRODUCTO,producto.getDisponible());
        contentValues.put(Constantes.Estado_TABLAPRODUCTO,producto.getEstado());
        contentValues.put(Constantes.UnidadMetrica_TABLAPRODUCTO,producto.getUnidadMetrica());
        SQLiteDatabase db = getWritableDatabase();
        db.update(Constantes.TABLAPRODUCTO,contentValues,Constantes.Referencia_TABLAPRODUCTO + "='" +
                producto.getReferencia() + "'",null);
        db.close();
    }
    public Producto obtenerProducto(int referencia){
        Producto producto = new Producto();
        SQLiteDatabase db = getWritableDatabase();
        Cursor registro = db.rawQuery("SELECT * FROM " + Constantes.TABLAPRODUCTO + " WHERE " +
                Constantes.Referencia_TABLAPRODUCTO + "='" + referencia + "'",null);
        if(registro.moveToFirst()){
            producto.setReferencia(registro.getInt(0));
            producto.setArticulo(registro.getString(1));
            producto.setMaterial(registro.getString(2));
            producto.setDisponible(registro.getDouble(3));
            producto.setEstado(registro.getInt(4));
            producto.setUnidadMetrica(registro.getString(5));
        }
        db.close();
        return producto;
    }

    public ArrayList<Producto> todosLosProducto(){
        ArrayList<Producto> productos = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor registro = db.rawQuery("SELECT * FROM " + Constantes.TABLAPRODUCTO,null);
        if(registro.moveToFirst()){
            do{
                Producto producto = new Producto();
                producto.setReferencia(registro.getInt(0));
                producto.setArticulo(registro.getString(1));
                producto.setMaterial(registro.getString(2));
                producto.setDisponible(registro.getDouble(3));
                producto.setEstado(registro.getInt(4));
                producto.setUnidadMetrica(registro.getString(5));
                productos.add(producto);
            } while (registro.moveToNext());
        }
        db.close();
        return productos;
    }

    public void elimiarProducto(Producto producto){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Constantes.TABLAENTRADAS,Constantes.RefProducto_TABLAENTRADAS + " ='"+
                producto.getReferencia()+ "'",null);
        db.delete(Constantes.TABLASALIDAS,Constantes.RefProducto_TABLASALIDAS + " ='"+
                producto.getReferencia()+ "'",null);
        db.delete(Constantes.TABLAPRODUCTO,Constantes.Referencia_TABLAPRODUCTO + " ='"+
                producto.getReferencia()+ "'",null);
        db.close();
    }

    public void addEntrada(Entrada entrada){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constantes.Dia_TABLAENTRADAS,entrada.getDia());
        contentValues.put(Constantes.Mes_TABLAENTRADAS,entrada.getMes());
        contentValues.put(Constantes.Año_TABLAENTRADAS,entrada.getAño());
        contentValues.put(Constantes.Factura_TABLAENTRADAS,entrada.getFactura());
        contentValues.put(Constantes.RefProducto_TABLAENTRADAS,entrada.getRefProducto());
        contentValues.put(Constantes.Cantidad_TABLAENTRADAS,entrada.getCantidad());
        contentValues.put(Constantes.ValorUnidad_TABLAENTRADAS,entrada.getValorPorUnidad());
        contentValues.put(Constantes.Proveedor_TABLAENTRADAS,entrada.getProveedor());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(Constantes.TABLAENTRADAS,null,contentValues);
        db.close();
        Producto producto = obtenerProducto(entrada.getRefProducto());
        producto.setDisponible(producto.getDisponible()+entrada.getCantidad());
        actualizarProducto(producto);
    }

    public ArrayList<Entrada> entradasPorProducto(int referencia){
        ArrayList<Entrada> entradas = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor registro = db.rawQuery("SELECT * FROM " + Constantes.TABLAENTRADAS + " WHERE " +
                Constantes.RefProducto_TABLAENTRADAS + "='" + referencia + "'",null);
        if(registro.moveToFirst()){
            do{
                Entrada entrada = new Entrada();
                entrada.setReferencia(registro.getInt(0));
                entrada.setDia(registro.getInt(1));
                entrada.setMes(registro.getInt(2));
                entrada.setAño(registro.getInt(3));
                entrada.setFactura(registro.getString(4));
                entrada.setRefProducto(registro.getInt(5));
                entrada.setCantidad(registro.getDouble(6));
                entrada.setValorPorUnidad(registro.getDouble(7));
                entrada.setProveedor(registro.getString(8));
                entradas.add(entrada);
            } while (registro.moveToNext());
        }
        db.close();
        return entradas;
    }
    public ArrayList<Entrada> entradasPorProveedor(String referencia){
        ArrayList<Entrada> entradas = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor registro = db.rawQuery("SELECT * FROM " + Constantes.TABLAENTRADAS + " WHERE " +
                Constantes.Proveedor_TABLAENTRADAS + "='" + referencia + "'",null);
        if(registro.moveToFirst()){
            do{
                Entrada entrada = new Entrada();
                entrada.setReferencia(registro.getInt(0));
                entrada.setDia(registro.getInt(1));
                entrada.setMes(registro.getInt(2));
                entrada.setAño(registro.getInt(3));
                entrada.setFactura(registro.getString(4));
                entrada.setRefProducto(registro.getInt(5));
                entrada.setCantidad(registro.getDouble(6));
                entrada.setValorPorUnidad(registro.getDouble(7));
                entrada.setProveedor(registro.getString(8));
                entradas.add(entrada);
            } while (registro.moveToNext());
        }
        db.close();
        return entradas;
    }
    public ArrayList<Entrada> entradasPorFactura(String referencia){
        ArrayList<Entrada> entradas = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor registro = db.rawQuery("SELECT * FROM " + Constantes.TABLAENTRADAS + " WHERE " +
                Constantes.Factura_TABLAENTRADAS + "='" + referencia + "'",null);
        if(registro.moveToFirst()){
            do{
                Entrada entrada = new Entrada();
                entrada.setReferencia(registro.getInt(0));
                entrada.setDia(registro.getInt(1));
                entrada.setMes(registro.getInt(2));
                entrada.setAño(registro.getInt(3));
                entrada.setFactura(registro.getString(4));
                entrada.setRefProducto(registro.getInt(5));
                entrada.setCantidad(registro.getDouble(6));
                entrada.setValorPorUnidad(registro.getDouble(7));
                entrada.setProveedor(registro.getString(8));
                entradas.add(entrada);
            } while (registro.moveToNext());
        }
        db.close();
        return entradas;
    }
    public void addSalida(Salida salida){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constantes.Dia_TABLASALIDAS,salida.getDia());
        contentValues.put(Constantes.Mes_TABLASALIDAS,salida.getMes());
        contentValues.put(Constantes.Año_TABLASALIDAS,salida.getAño());
        contentValues.put(Constantes.RefProducto_TABLASALIDAS,salida.getRefProducto());
        contentValues.put(Constantes.Cantidad_TABLASALIDAS,salida.getCantidad());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(Constantes.TABLASALIDAS,null,contentValues);
        db.close();
        Producto producto = obtenerProducto(salida.getRefProducto());
        producto.setDisponible(producto.getDisponible()-salida.getCantidad());
        actualizarProducto(producto);
    }

    public ArrayList<Salida> obtenerSalidas(int referencia){
        ArrayList<Salida> salidas = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor registro = db.rawQuery("SELECT * FROM " + Constantes.TABLASALIDAS + " WHERE " +
                Constantes.RefProducto_TABLASALIDAS + "='" + referencia + "'",null);
        if(registro.moveToFirst()){
            do{
                Salida salida = new Salida();
                salida.setReferencia(registro.getInt(0));
                salida.setDia(registro.getInt(1));
                salida.setMes(registro.getInt(2));
                salida.setAño(registro.getInt(3));
                salida.setRefProducto(registro.getInt(4));
                salida.setCantidad(registro.getDouble(5));
                salidas.add(salida);
            } while (registro.moveToNext());
        }
        db.close();
        return salidas;
    }
    public void addArticulo(Articulo articulo){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constantes.Nombre_TABLAARTICULOS,articulo.getNombre());
        contentValues.put(Constantes.Minimo_TABLAARTICULOS,articulo.getMinimo());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(Constantes.TABLAARTICULOS,null,contentValues);
        db.close();
    }

    public ArrayList<Articulo> obtenerArticulos(){
        ArrayList<Articulo> articulos = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor registro = db.rawQuery("SELECT * FROM " + Constantes.TABLAARTICULOS ,null);
        if(registro.moveToFirst()){
            do{
                Articulo articulo = new Articulo();
                articulo.setNombre(registro.getString(0));
                articulo.setMinimo(registro.getDouble(1));
                articulos.add(articulo);
            } while (registro.moveToNext());
        }
        db.close();
        return articulos;
    }

    public void addProveedor(String nombre){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constantes.Nombre_TABLAPROVEEDORES,nombre);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(Constantes.TABLAPROVEEDORES,null,contentValues);
        db.close();
    }

    public ArrayList<String> obtenerProveedores(){
        ArrayList<String> proveedores = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor registro = db.rawQuery("SELECT * FROM " + Constantes.TABLAPROVEEDORES ,null);
        if(registro.moveToFirst()){
            do{
                proveedores.add(registro.getString(0));
            } while (registro.moveToNext());
        }
        db.close();
        return proveedores;
    }

    public void addMaterial(String nombre){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constantes.Nombre_TABLAMATERIALES,nombre);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(Constantes.TABLAMATEIRALES,null,contentValues);
        db.close();
    }

    public ArrayList<String> obtenerMateriales(){
        ArrayList<String> materiales = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor registro = db.rawQuery("SELECT * FROM " + Constantes.TABLAMATEIRALES ,null);
        if(registro.moveToFirst()){
            do{
                materiales.add(registro.getString(0));
            } while (registro.moveToNext());
        }
        db.close();
        return materiales;
    }

}

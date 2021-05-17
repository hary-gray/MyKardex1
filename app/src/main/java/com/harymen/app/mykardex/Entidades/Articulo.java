package com.harymen.app.mykardex.Entidades;

public class Articulo {
    private String nombre;
    private Double minimo;

    public Articulo() {
    }

    public Articulo(String nombre, Double minimo) {
        this.nombre = nombre;
        this.minimo = minimo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getMinimo() {
        return minimo;
    }

    public void setMinimo(Double minimo) {
        this.minimo = minimo;
    }
}

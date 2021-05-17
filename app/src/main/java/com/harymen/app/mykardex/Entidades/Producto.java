package com.harymen.app.mykardex.Entidades;

public class Producto {
    private String articulo;
    private String material;
    private Double disponible;
    private String unidadMetrica;
    private int referencia;
    private int estado;

    public Producto() {
    }

    public Producto(String articulo, String material, Double disponible, String unidadMetrica) {
        this.articulo = articulo;
        this.material = material;
        this.disponible = disponible;
        this.unidadMetrica = unidadMetrica;
        this.estado = 1;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Double getDisponible() {
        return disponible;
    }

    public void setDisponible(Double disponible) {
        this.disponible = disponible;
    }

    public String getUnidadMetrica() {
        return unidadMetrica;
    }

    public void setUnidadMetrica(String unidadMetrica) {
        this.unidadMetrica = unidadMetrica;
    }

    public int getReferencia() {
        return referencia;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}

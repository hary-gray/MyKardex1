package com.harymen.app.mykardex.Entidades;

public class Entrada {
    private int dia;
    private int mes;
    private int año;
    private String factura;
    private int refProducto;
    private Double cantidad;
    private Double valorPorUnidad;
    private String proveedor;
    private int referencia;

    public Entrada() {
    }

    public Entrada(int dia, int mes, int año, String factura, int refProducto, Double cantidad, Double valorPorUnidad, String proveedor) {
        this.dia = dia;
        this.mes = mes;
        this.año = año;
        this.factura = factura;
        this.refProducto = refProducto;
        this.cantidad = cantidad;
        this.valorPorUnidad = valorPorUnidad;
        this.proveedor = proveedor;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public int getRefProducto() {
        return refProducto;
    }

    public void setRefProducto(int refProducto) {
        this.refProducto = refProducto;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getValorPorUnidad() {
        return valorPorUnidad;
    }

    public void setValorPorUnidad(Double valorPorUnidad) {
        this.valorPorUnidad = valorPorUnidad;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public int getReferencia() {
        return referencia;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }
}

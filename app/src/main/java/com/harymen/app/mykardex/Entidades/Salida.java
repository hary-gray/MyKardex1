package com.harymen.app.mykardex.Entidades;

public class Salida {
    private int dia;
    private int mes;
    private int año;
    private Double cantidad;
    private int refProducto;
    private int referencia;

    public Salida() {
    }

    public Salida(int dia, int mes, int año, Double cantidad, int refProducto) {
        this.dia = dia;
        this.mes = mes;
        this.año = año;
        this.cantidad = cantidad;
        this.refProducto = refProducto;
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

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public int getRefProducto() {
        return refProducto;
    }

    public void setRefProducto(int refProducto) {
        this.refProducto = refProducto;
    }

    public int getReferencia() {
        return referencia;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }
}

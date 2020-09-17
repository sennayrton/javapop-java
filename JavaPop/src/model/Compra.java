/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Compra implements Serializable {

    private String nombreVendedor;
    private String nombreComprador;
    private String dniVendedor;
    private String dniComprador;
    private LocalDate fecha;
    private Producto producto;
    private boolean confirmada;

    public Compra() {

    }

    public Compra(String nombreVendedor, String nombreComprador, String dniVendedor, String dniComprador,
            LocalDate fecha, Producto producto) {
        super();
        this.nombreVendedor = nombreVendedor;
        this.nombreComprador = nombreComprador;
        this.dniVendedor = dniVendedor;
        this.dniComprador = dniComprador;
        this.fecha = fecha;
        this.producto = producto;
        this.confirmada = false;
    }

    @Override
    public String toString() {
        return "Compra [nombreVendedor=" + nombreVendedor + ", nombreComprador=" + nombreComprador + ", dniVendedor="
                + dniVendedor + ", dniComprador=" + dniComprador + ", fecha=" + fecha + ", producto=" + producto
                + ", confirmada=" + confirmada + "]";
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public String getNombreComprador() {
        return nombreComprador;
    }

    public void setNombreComprador(String nombreComprador) {
        this.nombreComprador = nombreComprador;
    }

    public String getDniVendedor() {
        return dniVendedor;
    }

    public void setDniVendedor(String dniVendedor) {
        this.dniVendedor = dniVendedor;
    }

    public String getDniComprador() {
        return dniComprador;
    }

    public void setDniComprador(String dniComprador) {
        this.dniComprador = dniComprador;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    public void setConfirmada(boolean confirmada) {
        this.confirmada = confirmada;
    }

}

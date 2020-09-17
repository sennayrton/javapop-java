/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Producto implements Serializable {

    private int id;
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private EstadoProducto estado;
    private double precio;
    private LocalDate fecha;
    private Ubicacion ubicacion;
    private boolean urgente;
    private String añadidoPor;

    public Producto() {

    }

    public Producto(int id, String titulo, String descripcion, Categoria categoria, EstadoProducto estado, double precio, LocalDate fecha, Ubicacion ubicacion, String añadidoPor) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.estado = estado;
        this.precio = precio;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.urgente = false;
        this.añadidoPor = añadidoPor;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Producto other = (Producto) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public EstadoProducto getEstado() {
        return estado;
    }

    public void setEstado(EstadoProducto estado) {
        this.estado = estado;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean isUrgente() {
        return urgente;
    }

    public void setUrgente(boolean urgente) {
        this.urgente = urgente;
    }

    @Override
    public String toString() {
        return "Producto [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", categoria=" + categoria
                + ", estado=" + estado + ", precio=" + precio + ", fecha=" + fecha + ", ubicacion=" + ubicacion
                + ", urgente=" + urgente + ", añadidoPor=" + añadidoPor + "]";
    }

    public String getAñadidoPor() {
        return añadidoPor;
    }

    public void setAñadidoPor(String añadidoPor) {
        this.añadidoPor = añadidoPor;
    }

}

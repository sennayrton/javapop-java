/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package model;

import java.io.Serializable;

public class Cliente extends BasicClient implements Serializable {

    private String dni;
    private String nombre;
    private Ubicacion ubicacion;
    private String tarjeta;

    public Cliente() {
        super();
    }

    public Cliente(String dni, String nombre, String correo, String clave, Ubicacion ubicacion, String tarjeta) {
        super(correo, clave);

        if (tarjeta.length() != 16) {
            throw new IllegalArgumentException("La tarjeta tiene que ser de 16 digitos");
        }

        if (dni.length() != 8) {
            throw new IllegalArgumentException("El DNI tiene que ser de 8 caracteres");
        }

        this.dni = dni;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.tarjeta = tarjeta;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setDni(String dni) {
        if (dni.length() != 8) {
            throw new IllegalArgumentException("El DNI tiene que ser de 8 caracteres");
        }

        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setTarjeta(String tarjeta) {
        if (tarjeta.length() != 16) {
            throw new IllegalArgumentException("La tarjeta tiene que ser de 16 digitos");
        }
        this.tarjeta = tarjeta;
    }

    @Override
    public String toString() {
        return "Cliente [dni=" + dni + ", nombre=" + nombre + ", ubicacion=" + ubicacion + ", tarjeta=" + tarjeta + "]";
    }

}

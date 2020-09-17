/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package model;

public class ClienteProfesional extends Cliente {

    private String descripcion;
    private Horario horario;
    private String telefono;
    private String web;

    public ClienteProfesional() {
        super();
    }

    public ClienteProfesional(String dni, String nombre, String correo, String clave, Ubicacion ubicacion,
            String tarjeta, String descripcion, Horario horario, String telefono, String web) {
        super(dni, nombre, correo, clave, ubicacion, tarjeta);
        this.descripcion = descripcion;
        this.horario = horario;
        this.telefono = telefono;
        this.web = web;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Horario getHorario() {
        return horario;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getWeb() {
        return web;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setWeb(String web) {
        this.web = web;
    }

}

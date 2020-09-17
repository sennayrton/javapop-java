/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalTime;

public class Horario implements Serializable {

    private LocalTime apertura;
    private LocalTime cierre;

    public Horario() {

    }

    public Horario(LocalTime apertura, LocalTime cierre) {
        super();

        if (apertura.isAfter(cierre)) {
            throw new IllegalArgumentException("La hora de apertura tiene que ser anterior a la cierre");
        }

        this.apertura = apertura;
        this.cierre = cierre;
    }

    public LocalTime getApertura() {
        return apertura;
    }

    public void setApertura(LocalTime apertura) {
        this.apertura = apertura;
    }

    public LocalTime getCierre() {
        return cierre;
    }

    public void setCierre(LocalTime cierre) {
        this.cierre = cierre;
    }

    @Override
    public String toString() {
        return "Horario [apertura=" + apertura + ", cierre=" + cierre + "]";
    }

}

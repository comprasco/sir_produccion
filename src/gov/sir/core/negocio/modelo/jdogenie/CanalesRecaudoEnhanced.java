/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CanalesRecaudo;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class CanalesRecaudoEnhanced extends Enhanced {

    private int idCanal;
    private String nombreCanal;
    private boolean activo;

    public int getIdCanal() {
        return idCanal;
    }

    public void setIdCanal(int idCanal) {
        this.idCanal = idCanal;
    }

    public String getNombreCanal() {
        return nombreCanal;
    }

    public void setNombreCanal(String nombreCanal) {
        this.nombreCanal = nombreCanal;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    public static CanalesRecaudoEnhanced enhance(CanalesRecaudo canalesRecaudoEnhanced) {
        return (CanalesRecaudoEnhanced) Enhanced.enhance(canalesRecaudoEnhanced);
    }

}

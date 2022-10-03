/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.JustificaTipos;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class JustificaTiposEnhanced extends Enhanced {

    private int tipIdTipo; //pk
    private String tipNombreNovedad;
    private String tipDescripcion;
    private int tipVersion;

    public JustificaTiposEnhanced() {
    }

    public int getTipIdTipo() {
        return tipIdTipo;
    }

    public void setTipIdTipo(int tipIdTipo) {
        this.tipIdTipo = tipIdTipo;
    }

    public String getTipNombreNovedad() {
        return tipNombreNovedad;
    }

    public void setTipNombreNovedad(String tipNombreNovedad) {
        this.tipNombreNovedad = tipNombreNovedad;
    }

    public String getTipDescripcion() {
        return tipDescripcion;
    }

    public void setTipDescripcion(String tipDescripcion) {
        this.tipDescripcion = tipDescripcion;
    }
    
    public int getTipVersion() {
        return tipVersion;
    }

    public void setTipVersion(int tipVersion) {
        this.tipVersion = tipVersion;
    }

    public static JustificaTiposEnhanced enhance(
            JustificaTipos justificaTipos) {
        return (JustificaTiposEnhanced) Enhanced.enhance(justificaTipos);
    }

}

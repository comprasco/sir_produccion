/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.util;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class CamposCapturaEstados {
    
    private int idRelFormaCampo;
    private int idCampo;
    private String nombreCampo;
    private int estado;

    public int getIdRelFormaCampo() {
        return idRelFormaCampo;
    }

    public void setIdRelFormaCampo(int idRelFormaCampo) {
        this.idRelFormaCampo = idRelFormaCampo;
    }
    
    public int getIdCampo() {
        return idCampo;
    }

    public void setIdCampo(int idCampo) {
        this.idCampo = idCampo;
    }

    public String getNombreCampo() {
        return nombreCampo;
    }

    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
}

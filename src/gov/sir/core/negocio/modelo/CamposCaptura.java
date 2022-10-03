/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class CamposCaptura implements TransferObject {

    public CamposCaptura() {
    }

    private static final long serialVersionUID = 1L;
    
    private int idCamposCaptura;
    private String nombreCampo;
    private String campoDestino;
    private String formLabel;
    private String formName;

    public int getIdCamposCaptura() {
        return idCamposCaptura;
    }

    public void setIdCamposCaptura(int idCamposCaptura) {
        this.idCamposCaptura = idCamposCaptura;
    }

    public String getNombreCampo() {
        return nombreCampo;
    }

    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }

    public String getCampoDestino() {
        return campoDestino;
    }

    public void setCampoDestino(String campoDestino) {
        this.campoDestino = campoDestino;
    }

    public String getFormLabel() {
        return formLabel;
    }

    public void setFormLabel(String formLabel) {
        this.formLabel = formLabel;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }
    
}

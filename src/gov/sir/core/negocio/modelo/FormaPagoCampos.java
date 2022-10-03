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
public class FormaPagoCampos implements TransferObject {

    public FormaPagoCampos() {
    }

    private static final long serialVersionUID = 1L;
    
    private int idFormaPagoCampos;
    private int idFormaPago;
    private int idCamposCaptura;
    private boolean estado;

    public int getIdFormaPagoCampos() {
        return idFormaPagoCampos;
    }

    public void setIdFormaPagoCampos(int idFormaPagoCampos) {
        this.idFormaPagoCampos = idFormaPagoCampos;
    }

    public int getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(int idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public int getIdCamposCaptura() {
        return idCamposCaptura;
    }

    public void setIdCamposCaptura(int idCamposCaptura) {
        this.idCamposCaptura = idCamposCaptura;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
       
}

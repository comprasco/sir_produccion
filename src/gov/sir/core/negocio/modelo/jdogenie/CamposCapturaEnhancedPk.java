/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CamposCapturaPk;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class CamposCapturaEnhancedPk implements java.io.Serializable {

    public int idCamposCaptura;

    public CamposCapturaEnhancedPk() {
    }

    public CamposCapturaEnhancedPk(String s) {
    }

    public CamposCapturaEnhancedPk(CamposCapturaPk id) {
        idCamposCaptura = id.idCamposCaptura;
    }

    public CamposCapturaPk getCamposCapturaID() {
        CamposCapturaPk id = new CamposCapturaPk();
        id.idCamposCaptura = idCamposCaptura;
        return id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CamposCapturaEnhancedPk)) {
            return false;
        }
        final CamposCapturaEnhancedPk id = (CamposCapturaEnhancedPk) o;

        if (this.idCamposCaptura != id.idCamposCaptura) {
            return false;
        }
        return true;
    }

    public int hashCode() {        
        int result = 0;
        result = (29 * result) + (int) idCamposCaptura;
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCamposCaptura);
        return buffer.toString();
    }

}

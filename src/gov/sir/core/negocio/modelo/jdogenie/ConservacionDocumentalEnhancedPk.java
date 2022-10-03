/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ConservacionDocumentalPk;

/**
 *
 * @author desarrollo3
 */
public class ConservacionDocumentalEnhancedPk implements java.io.Serializable {
    
    public int idConservacion;

    public ConservacionDocumentalEnhancedPk() {
    }

    public ConservacionDocumentalEnhancedPk(ConservacionDocumentalPk id) {
        idConservacion = id.idConservacion;
    }

    public ConservacionDocumentalEnhancedPk(String s) {
        int i;
        int p = 0;
        idConservacion = Integer.parseInt(s.substring(p));
    }

    public ConservacionDocumentalPk getidConservacion() {
        ConservacionDocumentalPk rta = new ConservacionDocumentalPk();
        rta.idConservacion = idConservacion;
        return rta;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ConservacionDocumentalEnhancedPk)) {
            return false;
        }
        final ConservacionDocumentalEnhancedPk id = (ConservacionDocumentalEnhancedPk) o;

        if (this.idConservacion != id.idConservacion) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) + (int) idConservacion;
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idConservacion);

        return buffer.toString();
    }
}

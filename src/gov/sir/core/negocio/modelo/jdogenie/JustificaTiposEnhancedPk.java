/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.JustificaTiposPk;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class JustificaTiposEnhancedPk implements java.io.Serializable {

    public int tipIdTipo;

    public JustificaTiposEnhancedPk() {
    }

    public JustificaTiposEnhancedPk(JustificaTiposPk id) {
        tipIdTipo = id.tipIdTipo;
    }

    public JustificaTiposEnhancedPk(String s) {
        int i;
        int p = 0;
        tipIdTipo = Integer.parseInt(s.substring(p));
    }

    public JustificaTiposPk getTipIdTipo() {
        JustificaTiposPk rta = new JustificaTiposPk();
        rta.tipIdTipo = tipIdTipo;
        return rta;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof JustificaTiposEnhancedPk)) {
            return false;
        }
        final JustificaTiposEnhancedPk id = (JustificaTiposEnhancedPk) o;

        if (this.tipIdTipo != id.tipIdTipo) {
            return false;
        }

        return true;
    }
    
    public int hashCode() {
        int result = 0;
        result = (29 * result) + (int) tipIdTipo;
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(tipIdTipo);

        return buffer.toString();
    }

}

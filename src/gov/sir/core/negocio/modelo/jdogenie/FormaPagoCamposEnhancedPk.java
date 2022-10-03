/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.FormaPagoCamposPk;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class FormaPagoCamposEnhancedPk implements java.io.Serializable {

    public int idFormaPagoCampos;

    public FormaPagoCamposEnhancedPk() {
    }

    public FormaPagoCamposEnhancedPk(String s) {
    }

    public FormaPagoCamposEnhancedPk(FormaPagoCamposPk id) {
        idFormaPagoCampos = id.idFormaPagoCampos;
    }

    public FormaPagoCamposPk getFormaPagoCamposID() {
        FormaPagoCamposPk id = new FormaPagoCamposPk();
        id.idFormaPagoCampos = idFormaPagoCampos;
        return id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormaPagoCamposEnhancedPk)) {
            return false;
        }
        final FormaPagoCamposEnhancedPk id = (FormaPagoCamposEnhancedPk) o;

        if (this.idFormaPagoCampos != id.idFormaPagoCampos) {
            return false;
        }
        return true;
    }

    public int hashCode() {        
        int result = 0;
        result = (29 * result) + (int) idFormaPagoCampos;
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idFormaPagoCampos);
        return buffer.toString();
    }

}

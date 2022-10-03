/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CuentasBancariasPk;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class CuentasBancariasEnhancedPk implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    public int id;

    public CuentasBancariasEnhancedPk() {
    }

    public CuentasBancariasEnhancedPk(String s) {
    }

    public CuentasBancariasEnhancedPk(CuentasBancariasPk idCanal) {
        id = idCanal.id;
    }

    public CuentasBancariasPk getCuentasBancariasID() {
        CuentasBancariasPk idCanal = new CuentasBancariasPk();
        idCanal.id = id;
        return idCanal;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CuentasBancariasEnhancedPk)) {
            return false;
        }
        final CuentasBancariasEnhancedPk idCuenta = (CuentasBancariasEnhancedPk) o;

        if (this.id != idCuenta.id) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) + (int) id;
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(id);
        return buffer.toString();
    }

}

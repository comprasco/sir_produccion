/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CanalesRecaudoPk;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class CanalesRecaudoEnhancedPk implements java.io.Serializable {

    public int idCanal;

    public CanalesRecaudoEnhancedPk() {
    }

    public CanalesRecaudoEnhancedPk(String s) {
    }

    public CanalesRecaudoEnhancedPk(CanalesRecaudoPk id) {
        idCanal = id.idCanal;
    }

    public CanalesRecaudoPk getCanalesRecaudoID() {
        CanalesRecaudoPk id = new CanalesRecaudoPk();
        id.idCanal = idCanal;
        return id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CanalesRecaudoEnhancedPk)) {
            return false;
        }
        final CanalesRecaudoEnhancedPk id = (CanalesRecaudoEnhancedPk) o;

        if (this.idCanal != id.idCanal) {
            return false;
        }
        return true;
    }

    public int hashCode() {        
        int result = 0;
        result = (29 * result) + (int) idCanal;
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCanal);
        return buffer.toString();
    }

}

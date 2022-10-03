/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TramiteSuspensionPk;

/**
 *
 * @author developer5
 */
public class TramiteSuspensionEnhancedPk implements java.io.Serializable {

    public int tramsId;   

    public TramiteSuspensionEnhancedPk() {
    }

    public TramiteSuspensionEnhancedPk(TramiteSuspensionPk id) {
        tramsId = id.tramsId;
    }

    public TramiteSuspensionEnhancedPk(String s) {
        int i;
        int p = 0;
        tramsId = Integer.parseInt(s.substring(p));
    }

    public TramiteSuspensionPk getTramsId() {
        TramiteSuspensionPk rta = new TramiteSuspensionPk();
        rta.tramsId = tramsId;
        return rta;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof TramiteSuspensionPk)) {
            return false;
        }
        final TramiteSuspensionEnhancedPk id = (TramiteSuspensionEnhancedPk) o;

        if (this.tramsId != id.tramsId) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) + (int) tramsId;
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(tramsId);

        return buffer.toString();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DevolucionConservacionDocPk;

/**
 *
 * @author desarrollo3
 */
public class DevolucionConservacionDocEnhancedPk  implements java.io.Serializable{
    public int idDevolucionConservaDoc;

    public DevolucionConservacionDocEnhancedPk() {
    }

    public DevolucionConservacionDocEnhancedPk(DevolucionConservacionDocPk id) {
        idDevolucionConservaDoc = id.idDevolucionConservaDoc;
    }

    public DevolucionConservacionDocEnhancedPk(String s) {
        int i;
        int p = 0;
        idDevolucionConservaDoc = Integer.parseInt(s.substring(p));
    }

    public DevolucionConservacionDocPk getidLiquidacionConserva() {
        DevolucionConservacionDocPk rta = new DevolucionConservacionDocPk();
        rta.idDevolucionConservaDoc = idDevolucionConservaDoc;
        return rta;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DevolucionConservacionDocEnhancedPk)) {
            return false;
        }
        final DevolucionConservacionDocEnhancedPk id = (DevolucionConservacionDocEnhancedPk) o;

        if (this.idDevolucionConservaDoc != id.idDevolucionConservaDoc) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) + (int) idDevolucionConservaDoc;
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idDevolucionConservaDoc);

        return buffer.toString();
    } 
}

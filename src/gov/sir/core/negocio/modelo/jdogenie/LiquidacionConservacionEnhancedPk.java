/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.LiquidacionConservacionPk;

/**
 *
 * @author desarrollo3
 */
public class LiquidacionConservacionEnhancedPk implements java.io.Serializable{
    public int idLiquidacionConserva;

    public LiquidacionConservacionEnhancedPk() {
    }

    public LiquidacionConservacionEnhancedPk(LiquidacionConservacionPk id) {
        idLiquidacionConserva = id.idLiquidacionConserva;
    }

    public LiquidacionConservacionEnhancedPk(String s) {
        int i;
        int p = 0;
        idLiquidacionConserva = Integer.parseInt(s.substring(p));
    }

    public LiquidacionConservacionPk getidLiquidacionConserva() {
        LiquidacionConservacionPk rta = new LiquidacionConservacionPk();
        rta.idLiquidacionConserva = idLiquidacionConserva;
        return rta;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof LiquidacionConservacionEnhancedPk)) {
            return false;
        }
        final LiquidacionConservacionEnhancedPk id = (LiquidacionConservacionEnhancedPk) o;

        if (this.idLiquidacionConserva != id.idLiquidacionConserva) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) + (int) idLiquidacionConserva;
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idLiquidacionConserva);

        return buffer.toString();
    }
}

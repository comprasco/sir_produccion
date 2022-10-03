/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo;

/**
 *
 * @author desarrollo3
 */
public class LiquidacionConservacionPk {
    public int idLiquidacionConserva;

    public LiquidacionConservacionPk() {
    }

    public LiquidacionConservacionPk(String s) {
        int i;
        int p = 0;
        idLiquidacionConserva = Integer.parseInt(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof LiquidacionConservacionPk)) {
            return false;
        }

        final LiquidacionConservacionPk id = (LiquidacionConservacionPk) o;

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

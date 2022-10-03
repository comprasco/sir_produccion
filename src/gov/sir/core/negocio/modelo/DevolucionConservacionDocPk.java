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
public class DevolucionConservacionDocPk {
   public int idDevolucionConservaDoc;

    public DevolucionConservacionDocPk() {
    }

    public DevolucionConservacionDocPk(String s) {
        int i;
        int p = 0;
        idDevolucionConservaDoc = Integer.parseInt(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DevolucionConservacionDocPk)) {
            return false;
        }

        final DevolucionConservacionDocPk id = (DevolucionConservacionDocPk) o;

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

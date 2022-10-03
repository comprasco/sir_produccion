/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo;

/**
 *
 * @author developer5
 */
public class TramiteSuspensionPk implements java.io.Serializable {

    public int tramsId;

    public TramiteSuspensionPk() {
    }

    public TramiteSuspensionPk(String s) {
        int i;
        int p = 0;
        tramsId = Integer.parseInt(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof TramiteSuspensionPk)) {
            return false;
        }

        final TramiteSuspensionPk id = (TramiteSuspensionPk) o;

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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class JustificaAdmPk implements java.io.Serializable {

    public int admId;

    public JustificaAdmPk() {
    }

    public JustificaAdmPk(String s) {
        int i;
        int p = 0;
        admId = Integer.parseInt(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof JustificaAdmPk)) {
            return false;
        }

        final JustificaAdmPk id = (JustificaAdmPk) o;

        if (this.admId != id.admId) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) + (int) admId;

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(admId);

        return buffer.toString();
    }

}

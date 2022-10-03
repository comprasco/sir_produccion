/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.JustificaAdmPk;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class JustificaAdmEnhancedPk implements java.io.Serializable {

    public int admId;

    public JustificaAdmEnhancedPk() {
    }

    public JustificaAdmEnhancedPk(JustificaAdmPk id) {
        admId = id.admId;
    }

    public JustificaAdmEnhancedPk(String s) {
        int i;
        int p = 0;
        admId = Integer.parseInt(s.substring(p));
    }

    public JustificaAdmPk getAdmId() {
        JustificaAdmPk rta = new JustificaAdmPk();
        rta.admId = admId;
        return rta;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof JustificaAdmEnhancedPk)) {
            return false;
        }
        final JustificaAdmEnhancedPk id = (JustificaAdmEnhancedPk) o;

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

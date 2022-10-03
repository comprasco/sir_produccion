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
public class JustificaTiposPk implements java.io.Serializable {

    public int tipIdTipo;

    public JustificaTiposPk() {
    }

    public JustificaTiposPk(String s) {
        int i;
        int p = 0;
        tipIdTipo = Integer.parseInt(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof JustificaTiposPk)) {
            return false;
        }

        final JustificaTiposPk id = (JustificaTiposPk) o;

        if (this.tipIdTipo != id.tipIdTipo) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) + (int) tipIdTipo;

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(tipIdTipo);

        return buffer.toString();
    }

}

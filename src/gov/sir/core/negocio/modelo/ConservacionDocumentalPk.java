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
public class ConservacionDocumentalPk implements java.io.Serializable{
 public int idConservacion;

    public ConservacionDocumentalPk() {
    }

    public ConservacionDocumentalPk(String s) {
        int i;
        int p = 0;
        idConservacion = Integer.parseInt(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ConservacionDocumentalPk)) {
            return false;
        }

        final ConservacionDocumentalPk id = (ConservacionDocumentalPk) o;

        if (this.idConservacion != id.idConservacion) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) + (int) idConservacion;

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idConservacion);

        return buffer.toString();
    }

    
}

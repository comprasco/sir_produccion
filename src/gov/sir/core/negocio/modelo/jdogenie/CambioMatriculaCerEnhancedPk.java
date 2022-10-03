package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CambioMatriculaCerPk;

/**
 * Application identity objectid-class.
 */
public class CambioMatriculaCerEnhancedPk implements java.io.Serializable {

    public int idCambioMatricula;

    public CambioMatriculaCerEnhancedPk() {
    }

    public CambioMatriculaCerEnhancedPk(String s) {
        int i, p = 0;
        idCambioMatricula = Integer.parseInt(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CambioMatriculaCerPk)) return false;

        final CambioMatriculaCerPk id = (CambioMatriculaCerPk) o;

        if (this.idCambioMatricula != id.idCambioMatricula) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (int) (idCambioMatricula ^ (idCambioMatricula >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCambioMatricula);
        return buffer.toString();
    }
}
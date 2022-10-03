package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.NumeroMatriculaPk;

/**
 * Application identity objectid-class.
 */
public class NumeroMatriculaEnhancedPk implements java.io.Serializable {
    public String idNumero;

    public NumeroMatriculaEnhancedPk() {
    }

    public NumeroMatriculaEnhancedPk(String s) {
        int i;
        int p = 0;
        idNumero = s.substring(p);
    }
    
    public NumeroMatriculaEnhancedPk(NumeroMatriculaPk id){
        idNumero = id.idNumero;
    }
    
    public NumeroMatriculaPk getNumeroMatriculaID(){
        return new NumeroMatriculaPk(idNumero);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof NumeroMatriculaEnhancedPk)) {
            return false;
        }

        final NumeroMatriculaEnhancedPk id = (NumeroMatriculaEnhancedPk) o;

        if ((this.idNumero != null) ? (!idNumero.equals(id.idNumero))
                                        : (id.idNumero != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idNumero != null) ? idNumero.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idNumero);

        return buffer.toString();
    }
}
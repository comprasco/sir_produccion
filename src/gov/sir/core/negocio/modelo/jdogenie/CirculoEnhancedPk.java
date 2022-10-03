package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CirculoPk;

/**
 * Application identity objectid-class.
 */
public class CirculoEnhancedPk implements java.io.Serializable {
    public String idCirculo;

    public CirculoEnhancedPk() {
    }

    public CirculoEnhancedPk(CirculoPk id) {
        idCirculo = id.idCirculo;
    }

    public CirculoEnhancedPk(String s) {
        //int i;
        int p = 0;
        idCirculo = s.substring(p);
    }

    public CirculoPk getCirculoID() {
        CirculoPk id = new CirculoPk();
        id.idCirculo = idCirculo;

        return id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof CirculoEnhancedPk)) {
            return false;
        }

        final CirculoEnhancedPk idCirculo = (CirculoEnhancedPk) o;

        if ((this.idCirculo != null)
                ? (!idCirculo.equals(idCirculo.idCirculo))
                    : (idCirculo.idCirculo != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idCirculo != null) ? idCirculo.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCirculo);

        return buffer.toString();
    }
}
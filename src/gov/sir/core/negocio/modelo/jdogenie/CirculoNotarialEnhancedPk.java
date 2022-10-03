package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CirculoNotarialPk;

/**
 * Application identity objectid-class.
 */
public class CirculoNotarialEnhancedPk implements java.io.Serializable {
    public String idCirculo;

    public CirculoNotarialEnhancedPk() {
    }

    public CirculoNotarialEnhancedPk(CirculoNotarialPk id) {
        idCirculo = id.idCirculo;
    }

    public CirculoNotarialEnhancedPk(String s) {
        int i;
        int p = 0;
        idCirculo = s.substring(p);
    }

    public CirculoNotarialPk getCirculoNotarialID() {
        CirculoNotarialPk id = new CirculoNotarialPk();
        id.idCirculo = idCirculo;

        return id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof CirculoNotarialEnhancedPk)) {
            return false;
        }

        final CirculoNotarialEnhancedPk id = (CirculoNotarialEnhancedPk) o;

        if ((this.idCirculo != null) ? (!idCirculo.equals(id.idCirculo))
                                         : (id.idCirculo != null)) {
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
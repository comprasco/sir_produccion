package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ProhibicionPk;

/**
 * Application identity objectid-class.
 */
public class ProhibicionEnhancedPk implements java.io.Serializable {
    public String idProhibicion;

    public ProhibicionEnhancedPk() {
    }

    public ProhibicionEnhancedPk(ProhibicionPk id) {
        idProhibicion = id.idProhibicion;
    }

    public ProhibicionEnhancedPk(String s) {
        int i;
        int p = 0;
        idProhibicion = s.substring(p);
    }

    public ProhibicionPk getProhibicionID() {
        ProhibicionPk id = new ProhibicionPk();
        id.idProhibicion = idProhibicion;

        return id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ProhibicionEnhancedPk)) {
            return false;
        }

        final ProhibicionEnhancedPk id = (ProhibicionEnhancedPk) o;

        if ((this.idProhibicion != null)
                ? (!idProhibicion.equals(id.idProhibicion))
                    : (id.idProhibicion != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idProhibicion != null) ? idProhibicion.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idProhibicion);

        return buffer.toString();
    }
}
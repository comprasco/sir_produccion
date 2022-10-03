package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.OficioPk;

/**
 * Application identity objectid-class.
 */
public class OficioEnhancedPk implements java.io.Serializable {

    public String idOficio;

    public OficioEnhancedPk() {
    }

    public OficioEnhancedPk(String s) {
        int i, p = 0;
        idOficio = s.substring(p);
    }
    
	public OficioEnhancedPk(OficioPk id) {
		idOficio = id.idOficio;
	}
	
	public OficioPk getOficioID() {
		OficioPk rta = new OficioPk();
		rta.idOficio = idOficio;
		return rta;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OficioEnhancedPk)) return false;

        final OficioEnhancedPk id = (OficioEnhancedPk) o;

        if (this.idOficio != null ? !idOficio.equals(id.idOficio) : id.idOficio != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idOficio != null ? idOficio.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idOficio);
        return buffer.toString();
    }
}
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.MinutaPk;

/**
 * Application identity objectid-class.
 */
public class MinutaEnhancedPk implements java.io.Serializable {

    public String idMinuta;

    public MinutaEnhancedPk() {
    }

    public MinutaEnhancedPk(String s) {
        int i, p = 0;
        idMinuta = s.substring(p);
    }
    

	public MinutaEnhancedPk(MinutaPk id) {
		idMinuta = id.idMinuta;
	}
	
	public MinutaPk getMinutaID() {
		MinutaPk rta = new MinutaPk();
		rta.idMinuta = idMinuta;
		return rta;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MinutaEnhancedPk)) return false;

        final MinutaEnhancedPk id = (MinutaEnhancedPk) o;

        if (this.idMinuta != null ? !idMinuta.equals(id.idMinuta) : id.idMinuta != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idMinuta != null ? idMinuta.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idMinuta);
        return buffer.toString();
    }
}
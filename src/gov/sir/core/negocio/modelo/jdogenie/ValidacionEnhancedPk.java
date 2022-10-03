package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ValidacionPk;

/**
 * Application identity objectid-class.
 */
public class ValidacionEnhancedPk implements java.io.Serializable {

    public String idValidacion;

    public ValidacionEnhancedPk() {
    }
    

	public ValidacionEnhancedPk(ValidacionPk id) {
		idValidacion = id.idValidacion;
	}
	

	public ValidacionPk getUsuarioID() {
		ValidacionPk rta = new ValidacionPk();
		rta.idValidacion = idValidacion;
		return rta;
	}

    public ValidacionEnhancedPk(String s) {
        int i, p = 0;
        idValidacion = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValidacionEnhancedPk)) return false;

        final ValidacionEnhancedPk id = (ValidacionEnhancedPk) o;

        if (this.idValidacion != null ? !idValidacion.equals(id.idValidacion) : id.idValidacion != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idValidacion != null ? idValidacion.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idValidacion);
        return buffer.toString();
    }
}
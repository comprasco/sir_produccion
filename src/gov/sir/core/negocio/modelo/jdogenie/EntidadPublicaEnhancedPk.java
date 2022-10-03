package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.EntidadPublicaPk;

/**
 * Application identity objectid-class.
 */
public class EntidadPublicaEnhancedPk implements java.io.Serializable {

    public String idEntidadPublica;

    public EntidadPublicaEnhancedPk() {
    }

    public EntidadPublicaEnhancedPk(String s) {
        int i, p = 0;
        idEntidadPublica = s.substring(p);
    }
    

	public EntidadPublicaEnhancedPk(EntidadPublicaPk id){
		idEntidadPublica = id.idEntidadPublica;
	}
	
	public EntidadPublicaPk getEntidadPublicaID(){
		EntidadPublicaPk id = new EntidadPublicaPk();
		id.idEntidadPublica = idEntidadPublica;
		return id;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntidadPublicaEnhancedPk)) return false;

        final EntidadPublicaEnhancedPk id = (EntidadPublicaEnhancedPk) o;

        if (this.idEntidadPublica != null ? !idEntidadPublica.equals(id.idEntidadPublica) : id.idEntidadPublica != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idEntidadPublica != null ? idEntidadPublica.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idEntidadPublica);
        return buffer.toString();
    }
}
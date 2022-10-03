package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.MinutaEntidadPublicaPk;

/**
 * Application identity objectid-class.
 */
public class MinutaEntidadPublicaEnhancedPk implements java.io.Serializable {

    public String idEntidadPublica;
    public String idMinuta;

    public MinutaEntidadPublicaEnhancedPk() {
    }

    public MinutaEntidadPublicaEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idEntidadPublica = s.substring(p, i);
        p = i + 1;
        idMinuta = s.substring(p);
    }
    

	public MinutaEntidadPublicaEnhancedPk(MinutaEntidadPublicaPk id){
		idMinuta = id.idMinuta;
		idEntidadPublica = id.idEntidadPublica;
	}
            
	public MinutaEntidadPublicaPk getMinutaEntidadPublicaID(){
		MinutaEntidadPublicaPk id = new MinutaEntidadPublicaPk();
		id.idMinuta = idMinuta;
		id.idEntidadPublica = idEntidadPublica;
		return id;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MinutaEntidadPublicaEnhancedPk)) return false;

        final MinutaEntidadPublicaEnhancedPk id = (MinutaEntidadPublicaEnhancedPk) o;

        if (this.idEntidadPublica != null ? !idEntidadPublica.equals(id.idEntidadPublica) : id.idEntidadPublica != null) return false;
        if (this.idMinuta != null ? !idMinuta.equals(id.idMinuta) : id.idMinuta != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idEntidadPublica != null ? idEntidadPublica.hashCode() : 0);
        result = 29 * result + (idMinuta != null ? idMinuta.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idEntidadPublica);
        buffer.append('-');
        buffer.append(idMinuta);
        return buffer.toString();
    }
}
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ComplementacionPk;

/**
 * Application identity objectid-class.
 */
public class ComplementacionEnhancedPk implements java.io.Serializable {

    public String idComplementacion;

    public ComplementacionEnhancedPk() {
    }

	public ComplementacionEnhancedPk(ComplementacionPk id){
		idComplementacion = id.idComplementacion;
	}
	
	public ComplementacionPk getComplementacionID(){
		ComplementacionPk id = new ComplementacionPk();
		id.idComplementacion = idComplementacion;
		return id;
	}
	
    public ComplementacionEnhancedPk(String s) {
        int i, p = 0;
        idComplementacion = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComplementacionEnhancedPk)) return false;

        final ComplementacionEnhancedPk id = (ComplementacionEnhancedPk) o;

        if (this.idComplementacion != null ? !idComplementacion.equals(id.idComplementacion) : id.idComplementacion != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idComplementacion != null ? idComplementacion.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idComplementacion);
        return buffer.toString();
    }
}
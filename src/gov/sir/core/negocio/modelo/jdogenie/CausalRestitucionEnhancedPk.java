package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CausalRestitucionPk;

/**
 * Application identity objectid-class.
 */
public class CausalRestitucionEnhancedPk implements java.io.Serializable {

	public String idCausalRestitucion;

	public CausalRestitucionEnhancedPk() {
	}
    
	public CausalRestitucionEnhancedPk(CausalRestitucionPk id){
		idCausalRestitucion = id.idCausalRestitucion;        	
	}
    
	public CausalRestitucionPk getCausalRestitucionID(){
		CausalRestitucionPk id = new CausalRestitucionPk();
		id.idCausalRestitucion  = idCausalRestitucion;
		return id;
	}    

	public CausalRestitucionEnhancedPk(String s) {
		int i, p = 0;
		idCausalRestitucion = s.substring(p);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CausalRestitucionEnhancedPk)) return false;

		final CausalRestitucionEnhancedPk id = (CausalRestitucionEnhancedPk) o;

		if (this.idCausalRestitucion != null ? !idCausalRestitucion.equals(id.idCausalRestitucion) : id.idCausalRestitucion != null) return false;
		return true;
	}

	public int hashCode() {
		int result = 0;
		result = 29 * result + (idCausalRestitucion != null ? idCausalRestitucion.hashCode() : 0);
		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idCausalRestitucion);
		return buffer.toString();
	}
}
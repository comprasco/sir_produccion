package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidadPk;

/**
 * Application identity objectid-class.
 */
public class NaturalezaJuridicaEntidadEnhancedPk implements java.io.Serializable {

    public String idNatJuridicaEntidad;

    public NaturalezaJuridicaEntidadEnhancedPk() {
    }

    public NaturalezaJuridicaEntidadEnhancedPk(String s) {
        int i, p = 0;
        idNatJuridicaEntidad = s.substring(p);
    }
    

	public NaturalezaJuridicaEntidadEnhancedPk(NaturalezaJuridicaEntidadPk id){
		idNatJuridicaEntidad = id.idNatJuridicaEntidad;
	}
    
	public NaturalezaJuridicaEntidadPk getNaturalezaJuridicaEntidadID(){
		NaturalezaJuridicaEntidadPk id = new NaturalezaJuridicaEntidadPk();
		id.idNatJuridicaEntidad = idNatJuridicaEntidad;
		return id;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NaturalezaJuridicaEntidadEnhancedPk)) return false;

        final NaturalezaJuridicaEntidadEnhancedPk id = (NaturalezaJuridicaEntidadEnhancedPk) o;

        if (this.idNatJuridicaEntidad != null ? !idNatJuridicaEntidad.equals(id.idNatJuridicaEntidad) : id.idNatJuridicaEntidad != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idNatJuridicaEntidad != null ? idNatJuridicaEntidad.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idNatJuridicaEntidad);
        return buffer.toString();
    }
}
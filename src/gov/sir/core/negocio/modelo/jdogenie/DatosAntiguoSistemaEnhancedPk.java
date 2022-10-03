package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DatosAntiguoSistemaPk;

/**
 * Application identity objectid-class.
 */
public class DatosAntiguoSistemaEnhancedPk implements java.io.Serializable {

    public String idDatosAntiguoSistema;

    public DatosAntiguoSistemaEnhancedPk() {
    }
    
	public DatosAntiguoSistemaEnhancedPk(DatosAntiguoSistemaPk id){
		idDatosAntiguoSistema = id.idDatosAntiguoSistema;
	}
	
	public DatosAntiguoSistemaPk getDatosAntiguoSistemaID(){
		DatosAntiguoSistemaPk id = new DatosAntiguoSistemaPk();
		id.idDatosAntiguoSistema = idDatosAntiguoSistema;
		return id;
	}
	
    public DatosAntiguoSistemaEnhancedPk(String s) {
        int i, p = 0;
		idDatosAntiguoSistema = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatosAntiguoSistemaEnhancedPk)) return false;

        final DatosAntiguoSistemaEnhancedPk id = (DatosAntiguoSistemaEnhancedPk) o;

        if (this.idDatosAntiguoSistema != null ? !idDatosAntiguoSistema.equals(id.idDatosAntiguoSistema) : id.idDatosAntiguoSistema != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idDatosAntiguoSistema != null ? idDatosAntiguoSistema.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idDatosAntiguoSistema);
        return buffer.toString();
    }
}
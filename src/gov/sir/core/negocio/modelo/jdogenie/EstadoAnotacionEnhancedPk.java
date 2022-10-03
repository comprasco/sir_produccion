package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.EstadoAnotacionPk;

/**
 * Application identity objectid-class.
 */
public class EstadoAnotacionEnhancedPk implements java.io.Serializable {

    public String idEstadoAn;

    public EstadoAnotacionEnhancedPk() {
    }

    public EstadoAnotacionEnhancedPk(String s) {
        int i, p = 0;
        idEstadoAn = s.substring(p);
    }

	public EstadoAnotacionEnhancedPk(EstadoAnotacionPk id){
		idEstadoAn = id.idEstadoAn;
	}
	
	public EstadoAnotacionPk getEstadoAnotacionID(){
		EstadoAnotacionPk id = new EstadoAnotacionPk();
		id.idEstadoAn = idEstadoAn;
		return id;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EstadoAnotacionEnhancedPk)) return false;

        final EstadoAnotacionEnhancedPk id = (EstadoAnotacionEnhancedPk) o;

        if (this.idEstadoAn != null ? !idEstadoAn.equals(id.idEstadoAn) : id.idEstadoAn != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idEstadoAn != null ? idEstadoAn.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idEstadoAn);
        return buffer.toString();
    }
}
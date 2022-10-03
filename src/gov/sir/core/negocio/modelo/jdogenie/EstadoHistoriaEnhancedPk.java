package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.EstadoHistoriaPk;

/**
 * Application identity objectid-class.
 */
public class EstadoHistoriaEnhancedPk implements java.io.Serializable {

    public String idEstadoHistoria;
    public String idMatricula;

    public EstadoHistoriaEnhancedPk() {
    }

    public EstadoHistoriaEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idEstadoHistoria = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idMatricula = s.substring(p, i);
    }
    

	public EstadoHistoriaEnhancedPk(EstadoHistoriaPk id){
		idEstadoHistoria = id.idEstadoHistoria;
		idMatricula = id.idMatricula;
	}
	
	public EstadoHistoriaPk getEstadoHistoriaID(){
		EstadoHistoriaPk id = new EstadoHistoriaPk();
		id.idEstadoHistoria= idEstadoHistoria;
		id.idMatricula = idMatricula;
		return id;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EstadoHistoriaEnhancedPk)) return false;

        final EstadoHistoriaEnhancedPk id = (EstadoHistoriaEnhancedPk) o;

        if (this.idEstadoHistoria != null ? !idEstadoHistoria.equals(id.idEstadoHistoria) : id.idEstadoHistoria != null) return false;
        if (this.idMatricula != null ? !idMatricula.equals(id.idMatricula) : id.idMatricula != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idEstadoHistoria != null ? idEstadoHistoria.hashCode() : 0);
        result = 29 * result + (idMatricula != null ? idMatricula.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idEstadoHistoria);
        buffer.append('-');
        buffer.append(idMatricula);
        return buffer.toString();
    }
}
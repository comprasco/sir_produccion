package gov.sir.core.negocio.modelo.jdogenie;


import gov.sir.core.negocio.modelo.SolicitantePk;

/**
 * Application identity objectid-class.
 */
public class SolicitanteEnhancedPk implements java.io.Serializable {

    
    public String idSolicitud;
    public String idCiudadano;

    public SolicitanteEnhancedPk() {
    }

    public SolicitanteEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idSolicitud = s.substring(p, i);
        p = i + 1;
        idCiudadano = s.substring(p);
    }
    
	public SolicitanteEnhancedPk(SolicitantePk id) {
		idSolicitud = id.idSolicitud;
		idCiudadano = id.idCiudadano;
	}

	public SolicitantePk getSolicitanteID() {
		SolicitantePk id = new SolicitantePk();
		idSolicitud = id.idSolicitud;
		idCiudadano = id.idCiudadano;
		return id;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SolicitanteEnhancedPk)) return false;

        final SolicitanteEnhancedPk id = (SolicitanteEnhancedPk) o;

        if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null) return false;
        if (this.idCiudadano != null ? !idCiudadano.equals(id.idCiudadano) : id.idCiudadano != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
        result = 29 * result + (idCiudadano != null ? idCiudadano.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idSolicitud);
        buffer.append('-');
        buffer.append(idCiudadano);
        return buffer.toString();
    }
}
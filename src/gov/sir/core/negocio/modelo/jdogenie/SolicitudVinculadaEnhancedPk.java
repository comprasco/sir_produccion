package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SolicitudVinculadaPk;

/**
 * Application identity objectid-class.
 */
public class SolicitudVinculadaEnhancedPk implements java.io.Serializable {

    public String idSolicitud;
    public String idSolicitud1;

    public SolicitudVinculadaEnhancedPk() {
    }
    

	public SolicitudVinculadaEnhancedPk(SolicitudVinculadaPk id) {
		idSolicitud = id.idSolicitud;
		idSolicitud1 = id.idSolicitud1;
	}

	public SolicitudVinculadaPk getSolicitudVinculadaID() {
		SolicitudVinculadaPk id = new SolicitudVinculadaPk();
		idSolicitud = id.idSolicitud;
		idSolicitud1 = id.idSolicitud1;
		return id;
	}

    public SolicitudVinculadaEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idSolicitud = s.substring(p, i);
        p = i + 1;
        idSolicitud1 = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SolicitudVinculadaEnhancedPk)) return false;

        final SolicitudVinculadaEnhancedPk id = (SolicitudVinculadaEnhancedPk) o;

        if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null) return false;
        if (this.idSolicitud1 != null ? !idSolicitud1.equals(id.idSolicitud1) : id.idSolicitud1 != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
        result = 29 * result + (idSolicitud1 != null ? idSolicitud1.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idSolicitud);
        buffer.append('-');
        buffer.append(idSolicitud1);
        return buffer.toString();
    }
}
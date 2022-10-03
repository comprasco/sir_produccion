package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.NotaActuacionPk;

/**
 * Application identity objectid-class.
 */
public class NotaActuacionEnhancedPk implements java.io.Serializable {

    public String idNotaActuacion;
    public String idSolicitud;

    public NotaActuacionEnhancedPk() {
    }

    public NotaActuacionEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idNotaActuacion = s.substring(p, i);
        p = i + 1;
        idSolicitud = s.substring(p);
    }
    
	public NotaActuacionEnhancedPk(NotaActuacionPk id) {
		idSolicitud = id.idSolicitud;
		idNotaActuacion = id.idNotaActuacion;
	}

	public NotaActuacionPk getNotaActuacionID() {
		NotaActuacionPk id = new NotaActuacionPk();
		idSolicitud = id.idSolicitud;
		idNotaActuacion = id.idNotaActuacion;
		return id;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotaActuacionEnhancedPk)) return false;

        final NotaActuacionEnhancedPk id = (NotaActuacionEnhancedPk) o;

        if (this.idNotaActuacion != null ? !idNotaActuacion.equals(id.idNotaActuacion) : id.idNotaActuacion != null) return false;
        if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idNotaActuacion != null ? idNotaActuacion.hashCode() : 0);
        result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idNotaActuacion);
        buffer.append('-');
        buffer.append(idSolicitud);
        return buffer.toString();
    }
}
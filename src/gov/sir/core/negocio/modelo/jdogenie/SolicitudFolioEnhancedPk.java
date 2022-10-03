package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SolicitudFolioPk;

/**
 * Application identity objectid-class.
 */
public class SolicitudFolioEnhancedPk implements java.io.Serializable {

	public String idMatricula;
	public String idSolicitud;

	public SolicitudFolioEnhancedPk() {
	}

	public SolicitudFolioEnhancedPk(SolicitudFolioPk id) {
		idMatricula = id.idMatricula;
		idSolicitud = id.idSolicitud;
	}

	public SolicitudFolioPk getSolicitudFolioID() {
		SolicitudFolioPk id = new SolicitudFolioPk();
		id.idMatricula = idMatricula;
		id.idSolicitud = idSolicitud;
		return id;
	}

	public SolicitudFolioEnhancedPk(String s) {
		int i, p = 0;
		i = s.indexOf('-', p);
		idMatricula = s.substring(p, i);
		p = i + 1;
		i = s.indexOf('-', p);
		idSolicitud = s.substring(p, i);
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof SolicitudFolioEnhancedPk))
			return false;

		final SolicitudFolioEnhancedPk id = (SolicitudFolioEnhancedPk) o;

		if (this.idMatricula != null ? !idMatricula.equals(id.idMatricula) : id.idMatricula != null)
			return false;
		if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null)
			return false;
		return true;
	}

	public int hashCode() {
		int result = 0;
		result = 29 * result + (idMatricula != null ? idMatricula.hashCode() : 0);
		result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idMatricula);
		buffer.append('-');
		buffer.append(idSolicitud);
		return buffer.toString();
	}
}
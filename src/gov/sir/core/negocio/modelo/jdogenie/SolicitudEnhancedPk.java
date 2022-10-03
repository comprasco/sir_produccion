package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SolicitudPk;

/**
* Application identity objectid-class.
*/
public class SolicitudEnhancedPk implements java.io.Serializable {
	public String idSolicitud;

	public SolicitudEnhancedPk() {
	}

	public SolicitudEnhancedPk(SolicitudPk id) {
		idSolicitud = id.idSolicitud;
	}

	public SolicitudPk getSolicitudID() {
		SolicitudPk id = new SolicitudPk();
		id.idSolicitud = idSolicitud;
		return id;
	}

	public SolicitudEnhancedPk(String s) {
		int i;
		int p = 0;
		idSolicitud = s.substring(p);
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof SolicitudEnhancedPk)) {
			return false;
		}

		final SolicitudEnhancedPk id = (SolicitudEnhancedPk) o;

		if ((this.idSolicitud != null)
			? (!idSolicitud.equals(id.idSolicitud))
			: (id.idSolicitud != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = (29 * result) + ((idSolicitud != null) ? idSolicitud.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idSolicitud);

		return buffer.toString();
	}
}
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ResultadoFolioPk;

/**
 * Application identity objectid-class.
 */
public class ResultadoFolioEnhancedPk implements java.io.Serializable {

	public String idBusqueda;
	public String idMatricula;
	public String idSolicitud;

	public ResultadoFolioEnhancedPk() {
	}

	public ResultadoFolioEnhancedPk(ResultadoFolioPk id) {
		idBusqueda = id.idBusqueda;
		idMatricula = id.idMatricula;
		idSolicitud = id.idSolicitud;
	}

	public ResultadoFolioPk ResultadoFolioID() {
		ResultadoFolioPk id = new ResultadoFolioPk();
		id.idBusqueda = idBusqueda;
		id.idMatricula = idMatricula;
		id.idSolicitud = idSolicitud;
		return id;
	}

	public ResultadoFolioEnhancedPk(String s) {
		int i, p = 0;
		i = s.indexOf('-', p);
		idBusqueda = s.substring(p, i);
		p = i + 1;
		i = s.indexOf('-', p);
		idMatricula = s.substring(p, i);
		p = i + 1;
		i = s.indexOf('-', p);
		idSolicitud = s.substring(p, i);
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ResultadoFolioEnhancedPk))
			return false;

		final ResultadoFolioEnhancedPk id = (ResultadoFolioEnhancedPk) o;

		if (this.idBusqueda != null ? !idBusqueda.equals(id.idBusqueda) : id.idBusqueda != null)
			return false;
		if (this.idMatricula != null ? !idMatricula.equals(id.idMatricula) : id.idMatricula != null)
			return false;
		if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null)
			return false;
		return true;
	}

	public int hashCode() {
		int result = 0;
		result = 29 * result + (idBusqueda != null ? idBusqueda.hashCode() : 0);
		result = 29 * result + (idMatricula != null ? idMatricula.hashCode() : 0);
		result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idBusqueda);
		buffer.append('-');
		buffer.append(idMatricula);
		buffer.append('-');
		buffer.append(idSolicitud);
		return buffer.toString();
	}
}
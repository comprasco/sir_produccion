package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ResultadoAnotacionPk;

/**
 * Application identity objectid-class.
 */
public class ResultadoAnotacionEnhancedPk implements java.io.Serializable {

	public String idAnotacion;
	public String idBusqueda;
	public String idMatricula;
	public String idSolicitud;

	public ResultadoAnotacionEnhancedPk() {
	}

	public ResultadoAnotacionEnhancedPk(ResultadoAnotacionPk id) {
		idAnotacion = id.idAnotacion;
		idBusqueda = id.idBusqueda;
		idMatricula = id.idMatricula;
		idSolicitud = id.idSolicitud;
	}

	public ResultadoAnotacionPk getResultadoAnotacionID() {
		ResultadoAnotacionPk id = new ResultadoAnotacionPk();
		id.idAnotacion = idAnotacion;
		id.idBusqueda = idBusqueda;
		id.idMatricula = idMatricula;
		id.idSolicitud = idSolicitud;
		return id;
	}

	public ResultadoAnotacionEnhancedPk(String s) {
		int i, p = 0;
		i = s.indexOf('-', p);
		idAnotacion = s.substring(p, i);
		p = i + 1;
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
		if (!(o instanceof ResultadoAnotacionEnhancedPk))
			return false;

		final ResultadoAnotacionEnhancedPk id = (ResultadoAnotacionEnhancedPk) o;

		if (this.idAnotacion != null ? !idAnotacion.equals(id.idAnotacion) : id.idAnotacion != null)
			return false;
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
		result = 29 * result + (idAnotacion != null ? idAnotacion.hashCode() : 0);
		result = 29 * result + (idBusqueda != null ? idBusqueda.hashCode() : 0);
		result = 29 * result + (idMatricula != null ? idMatricula.hashCode() : 0);
		result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idAnotacion);
		buffer.append('-');
		buffer.append(idBusqueda);
		buffer.append('-');
		buffer.append(idMatricula);
		buffer.append('-');
		buffer.append(idSolicitud);
		return buffer.toString();
	}
}
package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoConsulta */

public class TipoConsultaPk implements java.io.Serializable {

	public String idTipoConsulta;

	public TipoConsultaPk() {
	}

	public TipoConsultaPk(String s) {
		int i, p = 0;
		idTipoConsulta = s.substring(p);
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof TipoConsultaPk))
			return false;

		final TipoConsultaPk id = (TipoConsultaPk) o;

		if (this.idTipoConsulta != null
			? !idTipoConsulta.equals(id.idTipoConsulta)
			: id.idTipoConsulta != null)
			return false;
		return true;
	}

	public int hashCode() {
		int result = 0;
		result = 29 * result + (idTipoConsulta != null ? idTipoConsulta.hashCode() : 0);
		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idTipoConsulta);
		return buffer.toString();
	}
}
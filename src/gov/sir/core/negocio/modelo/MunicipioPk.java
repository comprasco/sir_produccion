package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Municipio  */
public class MunicipioPk implements java.io.Serializable {
	public String idMunicipio;
	public String idDepartamento;

	public MunicipioPk() {
	}

	public MunicipioPk(String s) {
		int i;
		int p = 0;
		i = s.indexOf('-', p);
		idMunicipio = s.substring(p, i);
		p = i + 1;
		idDepartamento = s.substring(p);
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof MunicipioPk)) {
			return false;
		}

		final MunicipioPk id = (MunicipioPk) o;

		if ((this.idMunicipio != null)
				? (!idMunicipio.equals(id.idMunicipio))
					: (id.idMunicipio != null)) {
			return false;
		}

		if ((this.idDepartamento != null)
				? (!idDepartamento.equals(id.idDepartamento))
					: (id.idDepartamento != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = (29 * result) +
			((idMunicipio != null) ? idMunicipio.hashCode() : 0);
		result = (29 * result) +
			((idDepartamento != null) ? idDepartamento.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idMunicipio);
		buffer.append('-');
		buffer.append(idDepartamento);

		return buffer.toString();
	}
}
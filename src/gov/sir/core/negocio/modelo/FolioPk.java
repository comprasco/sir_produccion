package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Folio  */
public class FolioPk implements java.io.Serializable {
	public String idMatricula;

	public FolioPk() {
	}

	public FolioPk(String s) {
		idMatricula = s;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof FolioPk)) {
			return false;
		}

		final FolioPk id = (FolioPk) o;

		if ((this.idMatricula != null)
				? (!idMatricula.equals(id.idMatricula))
					: (id.idMatricula != null)) {
			return false;
		}
		return true;
	}

	public int hashCode() {
		int result = 0;
		result = (29 * result) +
			((idMatricula != null) ? idMatricula.hashCode() : 0);
		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idMatricula);
		return buffer.toString();
	}
}
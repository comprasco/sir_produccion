package gov.sir.core.negocio.modelo;

/**
* Clase que define los atributos que identifican a CausalRestitucion
*/
public class CausalRestitucionPk implements java.io.Serializable {

	public String idCausalRestitucion;

	public CausalRestitucionPk() {
	}

	public CausalRestitucionPk(String s) {
		int i, p = 0;
		idCausalRestitucion = s.substring(p);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CausalRestitucionPk)) return false;

		final CausalRestitucionPk id = (CausalRestitucionPk) o;

		if (this.idCausalRestitucion != null ? !idCausalRestitucion.equals(id.idCausalRestitucion) : id.idCausalRestitucion != null) return false;
		return true;
	}

	public int hashCode() {
		int result = 0;
		result = 29 * result + (idCausalRestitucion != null ? idCausalRestitucion.hashCode() : 0);
		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idCausalRestitucion);
		return buffer.toString();
	}
}
package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a RolSIR	  */

public class RolSIRPk implements java.io.Serializable {

	public String idRol;

	public RolSIRPk() {
	}

	public RolSIRPk(String s) {
		int i, p = 0;
		idRol = s.substring(p);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RolSIRPk)) return false;

		final RolSIRPk id = (RolSIRPk) o;

		if (this.idRol != null ? !idRol.equals(id.idRol) : id.idRol != null) return false;
		return true;
	}

	public int hashCode() {
		int result = 0;
		result = 29 * result + (idRol != null ? idRol.hashCode() : 0);
		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idRol);
		return buffer.toString();
	}
}
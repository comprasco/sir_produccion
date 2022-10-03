package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a RolPantalla	  */

public class RolPantallaPk implements java.io.Serializable {

	public long idPantallaAdministrativa;
	public String idRol;

	public RolPantallaPk() {
	}

	public RolPantallaPk(String s) {
		int i, p = 0;
		i= s.indexOf('-', p);
		idPantallaAdministrativa = Long.parseLong(s.substring(p, i));
		p = i + 1;
		idRol = s.substring(p);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RolPantallaPk)) return false;

		final RolPantallaPk id = (RolPantallaPk) o;

		if (this.idPantallaAdministrativa != id.idPantallaAdministrativa) return false;
		if (this.idRol != null ? !idRol.equals(id.idRol) : id.idRol != null) return false;
		return true;
	}

	public int hashCode() {
		int result = 0;
		result = 29 * result + (int) (idPantallaAdministrativa ^ (idPantallaAdministrativa >>> 32));
		result = 29 * result + (idRol != null ? idRol.hashCode() : 0);
		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idPantallaAdministrativa);
		buffer.append('-');
		buffer.append(idRol);
		return buffer.toString();
	}
}
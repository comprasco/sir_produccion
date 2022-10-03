package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a PantallaAdministrativa	  */

public class PantallaAdministrativaPk implements java.io.Serializable {

	public long idPantallaAdministrativa;

	public PantallaAdministrativaPk() {
	}

	public PantallaAdministrativaPk(String s) {
		int i, p = 0;
		idPantallaAdministrativa = Long.parseLong(s.substring(p));
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PantallaAdministrativaPk)) return false;

		final PantallaAdministrativaPk id = (PantallaAdministrativaPk) o;

		if (this.idPantallaAdministrativa != id.idPantallaAdministrativa) return false;
		return true;
	}

	public int hashCode() {
		int result = 0;
		result = 29 * result + (int) (idPantallaAdministrativa ^ (idPantallaAdministrativa >>> 32));
		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idPantallaAdministrativa);
		return buffer.toString();
	}
}
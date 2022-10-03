package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoRecepcionPeticion */

public class TipoRecepcionPeticionPk implements java.io.Serializable {

	public String idTipoRecepcionPeticion;

	public TipoRecepcionPeticionPk() {
	}

	public TipoRecepcionPeticionPk(String s) {
		int i, p = 0;
		idTipoRecepcionPeticion = s.substring(p);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TipoRecepcionPeticionPk)) return false;

		final TipoRecepcionPeticionPk id = (TipoRecepcionPeticionPk) o;

		if (this.idTipoRecepcionPeticion != null ? !idTipoRecepcionPeticion.equals(id.idTipoRecepcionPeticion) : id.idTipoRecepcionPeticion != null) return false;
		return true;
	}

	public int hashCode() {
		int result = 0;
		result = 29 * result + (idTipoRecepcionPeticion != null ? idTipoRecepcionPeticion.hashCode() : 0);
		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idTipoRecepcionPeticion);
		return buffer.toString();
	}
}
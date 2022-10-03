package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoRecepcionPeticionPk;

/**
 * Application identity objectid-class.
 */
public class TipoRecepcionPeticionEnhancedPk implements java.io.Serializable {

	public String idTipoRecepcionPeticion;

	public TipoRecepcionPeticionEnhancedPk() {
	}
	
	public TipoRecepcionPeticionEnhancedPk (TipoRecepcionPeticionPk id)
	{
		idTipoRecepcionPeticion = id.idTipoRecepcionPeticion;
	}


	public TipoRecepcionPeticionEnhancedPk(String s) {
		int i, p = 0;
		idTipoRecepcionPeticion = s.substring(p);
	}
	
	public TipoRecepcionPeticionPk getTipoRecepcionPeticionID()
	{
		   TipoRecepcionPeticionPk id = new TipoRecepcionPeticionPk();
		   id.idTipoRecepcionPeticion = idTipoRecepcionPeticion;
		   return id;
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TipoRecepcionPeticionEnhancedPk)) return false;

		final TipoRecepcionPeticionEnhancedPk id = (TipoRecepcionPeticionEnhancedPk) o;

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
package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a LlavesMostrarFolioHelper  */

public class LLavesMostrarFolioHelperPk implements java.io.Serializable {

	public String idBanco;

	public LLavesMostrarFolioHelperPk() {
	}

	public LLavesMostrarFolioHelperPk(String s) {
		int i, p = 0;
		idBanco = s.substring(p);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof LLavesMostrarFolioHelperPk)) return false;

		final LLavesMostrarFolioHelperPk id = (LLavesMostrarFolioHelperPk) o;

		if (this.idBanco != null ? !idBanco.equals(id.idBanco) : id.idBanco != null) return false;
		return true;
	}

	public int hashCode() {
		int result = 0;
		result = 29 * result + (idBanco != null ? idBanco.hashCode() : 0);
		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idBanco);
		return buffer.toString();
	}
}
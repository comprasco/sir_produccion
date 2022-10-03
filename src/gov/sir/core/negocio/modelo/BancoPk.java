package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a Banco
 */
public class BancoPk implements java.io.Serializable {

	public String idBanco;

	public BancoPk() {
	}

	public BancoPk(String s) {
		int i, p = 0;
		idBanco = s.substring(p);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BancoPk)) return false;

		final BancoPk id = (BancoPk) o;

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
package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoPantalla */

public class TipoPantallaPk implements java.io.Serializable {

	public long idTipoPantalla;

	public TipoPantallaPk() {
	}

	public TipoPantallaPk(String s) {
		int i, p = 0;
		idTipoPantalla = Long.parseLong(s.substring(p));
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TipoPantallaPk)) return false;

		final TipoPantallaPk id = (TipoPantallaPk) o;

		if (this.idTipoPantalla != id.idTipoPantalla) return false;
		return true;
	}

	public int hashCode() {
		int result = 0;
		result = 29 * result + (int) (idTipoPantalla ^ (idTipoPantalla >>> 32));
		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idTipoPantalla);
		return buffer.toString();
	}
}
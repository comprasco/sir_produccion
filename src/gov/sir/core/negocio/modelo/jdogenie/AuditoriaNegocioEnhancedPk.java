package gov.sir.core.negocio.modelo.jdogenie;

/**
 * Application identity objectid-class.
 */
public class AuditoriaNegocioEnhancedPk implements java.io.Serializable {

	public long idAuditoria;

	public AuditoriaNegocioEnhancedPk() {
	}

	public AuditoriaNegocioEnhancedPk(String s) {
		int i, p = 0;
		idAuditoria = Integer.parseInt(s.substring(p));
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AuditoriaEnhancedPk)) return false;

		final AuditoriaEnhancedPk id = (AuditoriaEnhancedPk) o;

		if (this.idAuditoria != id.idAuditoria) return false;
		return true;
	}

	public int hashCode() {
		int result = 0;
		result = 29 * result + (int) idAuditoria;
		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idAuditoria);
		return buffer.toString();
	}
}
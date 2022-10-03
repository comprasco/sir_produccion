package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SecuenciasPk;

/**
 * Application identity objectid-class.
 */
public class SecuenciasEnhancedPk implements java.io.Serializable {

	public String tableName;

	public SecuenciasEnhancedPk() {
	}

	public SecuenciasEnhancedPk(String s) {
		int i, p = 0;
		tableName = s.substring(p);
	}

	public SecuenciasEnhancedPk(SecuenciasPk id) {
		tableName = id.tableName;
	}

	public SecuenciasPk getSecuenciasID() {
		SecuenciasPk id = new SecuenciasPk();
		id.tableName = tableName;
		return id;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof SecuenciasEnhancedPk))
			return false;

		final SecuenciasEnhancedPk id = (SecuenciasEnhancedPk) o;

		if (this.tableName != null ? !tableName.equals(id.tableName) : id.tableName != null)
			return false;
		return true;
	}

	public int hashCode() {
		int result = 0;
		result = 29 * result + (tableName != null ? tableName.hashCode() : 0);
		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(tableName);
		return buffer.toString();
	}
}
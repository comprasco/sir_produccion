package gov.sir.core.negocio.modelo.jdogenie;

/**
 * Application identity objectid-class.
 * @author juan.rave
 */
public class CertificadoEnhancedPk implements java.io.Serializable {

	public String idMatricula;
	public String idCirculo;


	public CertificadoEnhancedPk() {
	}

	public CertificadoEnhancedPk(String s) {
		int i;
		i = s.indexOf('-', 0);
		idCirculo = s.substring(0, i);
		idMatricula = s;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof CertificadoEnhancedPk)) {
			return false;
		}

		final CertificadoEnhancedPk id = (CertificadoEnhancedPk) o;

		if ((this.idMatricula != null)
				? (!this.idMatricula.equals(id.idMatricula))
						: (id.idMatricula != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = ((idMatricula != null) ? idMatricula.hashCode() : 0);

		return result;
	}

	public String toString() {
		return idMatricula;
	}
}
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SalvedadFolioPk;

/**
* Application identity objectid-class.
*/
public class SalvedadFolioEnhancedPk implements java.io.Serializable {
	public String idMatricula;
	public String idSalvedad;

	public SalvedadFolioEnhancedPk() {
	}

	public SalvedadFolioEnhancedPk(SalvedadFolioPk id) {
		idMatricula = id.idMatricula;
		idSalvedad = id.idSalvedad;
	}

	public SalvedadFolioPk getSalvedadFolioID() {
		SalvedadFolioPk id = new SalvedadFolioPk();
		id.idMatricula = idMatricula;
		idSalvedad = id.idSalvedad;
		return id;
	}

	public SalvedadFolioEnhancedPk(String s) {
		int i;
		int p = 0;
		i = s.indexOf('-', p);
		idMatricula = s.substring(p, i);
		p = i + 1;
		i = s.indexOf('-', p);
		idSalvedad = s.substring(p, i);
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof SalvedadFolioEnhancedPk)) {
			return false;
		}

		final SalvedadFolioEnhancedPk id = (SalvedadFolioEnhancedPk) o;

		if ((this.idMatricula != null)
			? (!idMatricula.equals(id.idMatricula))
			: (id.idMatricula != null)) {
			return false;
		}

		if ((this.idSalvedad != null)
			? (!idSalvedad.equals(id.idSalvedad))
			: (id.idSalvedad != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = (29 * result) + ((idMatricula != null) ? idMatricula.hashCode() : 0);
		result = (29 * result) + ((idSalvedad != null) ? idSalvedad.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idMatricula);
		buffer.append('-');
		buffer.append(idSalvedad);

		return buffer.toString();
	}
}
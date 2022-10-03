package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SalvedadAnotacionPk;

/**
* Application identity objectid-class.
*/
public class SalvedadAnotacionEnhancedPk implements java.io.Serializable {
	public String idAnotacion;
	public String idMatricula;
	public String idSalvedad;

	public SalvedadAnotacionEnhancedPk() {
	}

	public SalvedadAnotacionEnhancedPk(SalvedadAnotacionPk id) {
		idAnotacion = id.idAnotacion;
		idMatricula = id.idMatricula;
		idSalvedad = id.idSalvedad;
	}

	public SalvedadAnotacionPk getSalvedadAnotacionID() {
		SalvedadAnotacionPk id = new SalvedadAnotacionPk();
		idAnotacion = id.idAnotacion;
		id.idMatricula = idMatricula;
		idSalvedad = id.idSalvedad;
		return id;
	}

	public SalvedadAnotacionEnhancedPk(String s) {
		int i;
		int p = 0;
		i = s.indexOf('-', p);
		idAnotacion = s.substring(p, i);
		p = i + 1;
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

		if (!(o instanceof SalvedadAnotacionEnhancedPk)) {
			return false;
		}

		final SalvedadAnotacionEnhancedPk id = (SalvedadAnotacionEnhancedPk) o;

		if ((this.idAnotacion != null)
			? (!idAnotacion.equals(id.idAnotacion))
			: (id.idAnotacion != null)) {
			return false;
		}

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
		result = (29 * result) + ((idAnotacion != null) ? idAnotacion.hashCode() : 0);
		result = (29 * result) + ((idMatricula != null) ? idMatricula.hashCode() : 0);
		result = (29 * result) + ((idSalvedad != null) ? idSalvedad.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idAnotacion);
		buffer.append('-');
		buffer.append(idMatricula);
		buffer.append('-');
		buffer.append(idSalvedad);

		return buffer.toString();
	}
}
package gov.sir.core.negocio.modelo.jdogenie;


import gov.sir.core.negocio.modelo.TestamentoCiudadanoPk;

public class TestamentoCiudadanoEnhancedPk implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	public String idTestamento;
	public String idCiudadano;

	public TestamentoCiudadanoEnhancedPk() {
	}

	public TestamentoCiudadanoEnhancedPk(String s) {
		int i;
		int p = 0;
		i = s.indexOf('-', p);
		idTestamento = s.substring(p, i);
		p = i + 1;
		idCiudadano = s.substring(p);
	}
	
	public TestamentoCiudadanoEnhancedPk(TestamentoCiudadanoPk id){
		idTestamento = id.idTestamento;
		idCiudadano = id.idCiudadano;
	}
	
	public TestamentoCiudadanoPk getTestamentoCiudadanoID(){
		TestamentoCiudadanoPk id = new TestamentoCiudadanoPk();
		id.idTestamento = idTestamento;
		id.idCiudadano = idCiudadano;
		return id;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof TestamentoCiudadanoEnhancedPk)) {
			return false;
		}

		final TestamentoCiudadanoEnhancedPk id = (TestamentoCiudadanoEnhancedPk) o;

		if ((this.idTestamento != null)
				? (!idTestamento.equals(id.idTestamento))
					: (id.idTestamento != null)) {
			return false;
		}

		if ((this.idCiudadano != null)
				? (!idCiudadano.equals(id.idCiudadano))
					: (id.idCiudadano != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = (29 * result) +
			((idTestamento != null) ? idTestamento.hashCode() : 0);
		result = (29 * result) +
			((idCiudadano != null) ? idCiudadano.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(idTestamento);
		buffer.append('-');
		buffer.append(idCiudadano);

		return buffer.toString();
	}
}

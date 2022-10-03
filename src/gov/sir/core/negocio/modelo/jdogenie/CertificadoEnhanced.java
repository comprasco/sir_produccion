package gov.sir.core.negocio.modelo.jdogenie;

/**
 * Application identity objectid-class.
 * @author juan.rave
 */
public class CertificadoEnhanced extends Enhanced {
	
	private String idMatricula; // pk
	private String idCirculo; // pk
	private byte[] datos;

	public CertificadoEnhanced() {
	}
	
	public String getIdMatricula() {
		return idMatricula;
	}

	public void setIdMatricula(String id) {
		this.idMatricula = id;
	}

	public String getIdCirculo() {
		return idCirculo;
	}

	public void setIdCirculo(String id) {
		this.idCirculo = id;
	}

	public byte[] getDatos() {
		return datos;
	}

	public void setDatos(byte[] datos) {
		this.datos = datos;
	}
}
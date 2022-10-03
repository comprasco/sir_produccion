package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/** Pablo Quintana - SIR-62 - Junio 23 2008
 *  Clase intemedia entre testamentos y ciudadanos*/

public class TestamentoCiudadano implements TransferObject {

	private static final long serialVersionUID = 1L;
	
	private String idTestamento; //pk
	private String idCiudadano;	//pk
	private Ciudadano ciudadano; // inverse Ciudadano.ciudadanos
	private Testamento testamento; // inverse Testamento.testadores
	
	public TestamentoCiudadano() {
	
	}
	
	public String getIdTestamento() {
		return idTestamento;
	}
	
	public void setIdTestamento(String idTestamento) {
		this.idTestamento = idTestamento;
	}
	
	public Testamento getTestamento() {
		return testamento;
	}

	public void setTestamento(Testamento testamento) {
		this.testamento = testamento;
	}
	
	public Ciudadano getCiudadano() {
		return ciudadano;
	}

	public void setCiudadano(Ciudadano ciudadano) {
		this.ciudadano = ciudadano;
	}

	public String getIdCiudadano() {
		return idCiudadano;
	}

	public void setIdCiudadano(String idCiudadano) {
		this.idCiudadano = idCiudadano;
	}	
	
	
}

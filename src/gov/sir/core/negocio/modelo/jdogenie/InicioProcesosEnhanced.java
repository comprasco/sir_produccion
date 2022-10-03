package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.InicioProcesos;

public class InicioProcesosEnhanced extends Enhanced{

	private int idProceso;
	private String idFase;
	private String idRol;
	
	public String getIdFase() {
		return idFase;
	}
	public void setIdFase(String idFase) {
		this.idFase = idFase;
	}
	public int getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(int idProceso) {
		this.idProceso = idProceso;
	}
	public String getIdRol() {
		return idRol;
	}
	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}
	
	public static InicioProcesosEnhanced enhance(InicioProcesos inicioProcesos) {
		return (InicioProcesosEnhanced) Enhanced.enhance(inicioProcesos);
	}
}

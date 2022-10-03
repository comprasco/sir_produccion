package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.WfProcesos;

public class WfProcesosEnhanced extends Enhanced{
	private int idProceso;
	private String idFaseInicial;
	private String respuesta;
	private String idFaseFinal;
	private String idRol;
	
	public String getIdFaseFinal() {
		return idFaseFinal;
	}
	public void setIdFaseFinal(String idFaseFinal) {
		this.idFaseFinal = idFaseFinal;
	}
	public String getIdFaseInicial() {
		return idFaseInicial;
	}
	public void setIdFaseInicial(String idFaseInicial) {
		this.idFaseInicial = idFaseInicial;
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
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
	public static WfProcesosEnhanced enhance(WfProcesos wfProcesos) {
		return (WfProcesosEnhanced) Enhanced.enhance(wfProcesos);
	}
}

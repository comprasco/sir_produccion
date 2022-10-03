package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

public class WfProcesos implements TransferObject{
	private int idProceso;
	private String idFaseInicial;
	private String respuesta;
	private String idFaseFinal;
	private String idRol;
	private static final long serialVersionUID = 1L;
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
	
	
}

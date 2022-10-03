package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

public class InicioProcesos implements TransferObject{
	private int idProceso;
	private String idFase;
	private String idRol;
        private static final long serialVersionUID = 1L;
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
	
	
}

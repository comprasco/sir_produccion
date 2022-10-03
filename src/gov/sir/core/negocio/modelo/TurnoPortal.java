package gov.sir.core.negocio.modelo;

import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;

public class TurnoPortal implements java.io.Serializable{
	private String idTurno;
	private ImprimibleRecibo recibo;
	private String idMatricula;
	private int idCertificado;
	private static final long serialVersionUID = 1L;
	public TurnoPortal(String idTurno, ImprimibleRecibo recibo, String idMatricula, int idCertificado){
		this.idCertificado = idCertificado;
		this.idMatricula = idMatricula;
		this.idTurno = idTurno;
		this.recibo = recibo;
	}
	
	public TurnoPortal(String idTurno, String idMatricula, int idCertificado){
		this.idCertificado = idCertificado;
		this.idMatricula = idMatricula;
		this.idTurno = idTurno;
	}
	
	public String getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	public ImprimibleRecibo getRecibo() {
		return recibo;
	}
	public void setRecibo(ImprimibleRecibo recibo) {
		this.recibo = recibo;
	}
	public String getIdMatricula() {
		return idMatricula;
	}
	public void setIdMatricula(String idMatricula) {
		this.idMatricula = idMatricula;
	}
	public int getIdCertificado() {
		return idCertificado;
	}
	public void setIdCertificado(int idCertificado) {
		this.idCertificado = idCertificado;
	}
}

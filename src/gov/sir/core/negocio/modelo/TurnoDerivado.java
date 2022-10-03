package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

public class TurnoDerivado implements TransferObject {
	
	private String anioPadre; // pk 
    private String idCirculoPadre; // pk 
    private long idProcesoPadre; // pk 
    private String idTurnoPadre; // pk 
    private String anioHijo; // pk 
    private String idCirculoHijo; // pk 
    private long idProcesoHijo; // pk 
    private String idTurnoHijo; // pk
    private Turno turnoPadre;
    private Turno turnoHijo;
        private static final long serialVersionUID = 1L;
    public TurnoDerivado() {
    }
    
	public String getAnioHijo() {
		return anioHijo;
	}
	public void setAnioHijo(String anioHijo) {
		this.anioHijo = anioHijo;
	}
	public String getAnioPadre() {
		return anioPadre;
	}
	public void setAnioPadre(String anioPadre) {
		this.anioPadre = anioPadre;
	}
	public String getIdCirculoHijo() {
		return idCirculoHijo;
	}
	public void setIdCirculoHijo(String idCirculoHijo) {
		this.idCirculoHijo = idCirculoHijo;
	}
	public String getIdCirculoPadre() {
		return idCirculoPadre;
	}
	public void setIdCirculoPadre(String idCirculoPadre) {
		this.idCirculoPadre = idCirculoPadre;
	}
	public long getIdProcesoHijo() {
		return idProcesoHijo;
	}
	public void setIdProcesoHijo(long idProcesoHijo) {
		this.idProcesoHijo = idProcesoHijo;
	}
	public long getIdProcesoPadre() {
		return idProcesoPadre;
	}
	public void setIdProcesoPadre(long idProcesoPadre) {
		this.idProcesoPadre = idProcesoPadre;
	}
	public String getIdTurnoHijo() {
		return idTurnoHijo;
	}
	public void setIdTurnoHijo(String idTurnoHijo) {
		this.idTurnoHijo = idTurnoHijo;
	}
	public String getIdTurnoPadre() {
		return idTurnoPadre;
	}
	public void setIdTurnoPadre(String idTurnoPadre) {
		this.idTurnoPadre = idTurnoPadre;
	}
	public Turno getTurnoHijo() {
		return turnoHijo;
	}
	public void setTurnoHijo(Turno turnoHijo) {
		this.turnoHijo = turnoHijo;
		setAnioHijo(turnoHijo.getAnio());
		setIdCirculoHijo(turnoHijo.getIdCirculo());
		setIdProcesoHijo(turnoHijo.getIdProceso());
		setIdTurnoHijo(turnoHijo.getIdTurno());
	}
	public Turno getTurnoPadre() {
		return turnoPadre;
	}
	public void setTurnoPadre(Turno turnoPadre) {
		this.turnoPadre = turnoPadre;
		setAnioPadre(turnoPadre.getAnio());
		setIdCirculoPadre(turnoPadre.getIdCirculo());
		setIdProcesoPadre(turnoPadre.getIdProceso());
		setIdTurnoPadre(turnoPadre.getIdTurno());
	}
}

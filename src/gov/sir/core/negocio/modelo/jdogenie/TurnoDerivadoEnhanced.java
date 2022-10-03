package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TurnoDerivado;

public class TurnoDerivadoEnhanced extends Enhanced {

	private String anioPadre; // pk 
    private String idCirculoPadre; // pk 
    private long idProcesoPadre; // pk 
    private String idTurnoPadre; // pk 
    private String anioHijo; // pk 
    private String idCirculoHijo; // pk 
    private long idProcesoHijo; // pk 
    private String idTurnoHijo; // pk
    private TurnoEnhanced turnoPadre;
    private TurnoEnhanced turnoHijo;
    
    public TurnoDerivadoEnhanced() {
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
	
	public TurnoEnhanced getTurnoHijo() {
		return turnoHijo;
	}
	
	public void setTurnoHijo(TurnoEnhanced turnoHijo) {
		this.turnoHijo = turnoHijo;
		setAnioHijo(turnoHijo.getAnio());
		setIdCirculoHijo(turnoHijo.getIdCirculo());
		setIdProcesoHijo(turnoHijo.getIdProceso());
		setIdTurnoHijo(turnoHijo.getIdTurno());
	}
	
	public TurnoEnhanced getTurnoPadre() {
		return turnoPadre;
	}
	
	public void setTurnoPadre(TurnoEnhanced turnoPadre) {
		this.turnoPadre = turnoPadre;
		setAnioPadre(turnoPadre.getAnio());
		setIdCirculoPadre(turnoPadre.getIdCirculo());
		setIdProcesoPadre(turnoPadre.getIdProceso());
		setIdTurnoPadre(turnoPadre.getIdTurno());
	}
	
	public static TurnoDerivadoEnhanced enhance(TurnoDerivado turnoDerivado) {
		return (TurnoDerivadoEnhanced) Enhanced.enhance(turnoDerivado);
	}
}

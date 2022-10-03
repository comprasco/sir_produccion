package gov.sir.core.negocio.modelo;


import org.auriga.core.modelo.TransferObject;


/**
 * Clase que define los Folios que tiene asociados un turno en tramite en el sistema FOLIO 
 * @author ppabon
 */
public class TurnoFolioTramiteMig implements TransferObject {

	/**
	 * Identificador del turno
	 */
    private String idTurno; // pk
    
	/**
	 * Identificador del folio
	 */
    private long idFolio; // pk 
    
	/**
	 * Identificador del proceso
	 */
    private long idProceso; // pk     
    private static final long serialVersionUID = 1L;
    /**
     * Identificador del circulo
     */
    private String idCirculo; // pk
    
    /**
     * Identificador de la secuencia de proceso
     */
    private long secProceso; // pk 
    
    /**
     * Determina si el registro es inválido
     */    
    private boolean anulado;
 


	/**
	 * @return
	 */
	public boolean isAnulado() {
		return anulado;
	}


	/**
	 * @param anulado
	 */
	public void setAnulado(boolean anulado) {
		this.anulado = anulado;
	}


	/**
	 * @return
	 */
	public String getIdCirculo() {
		return idCirculo;
	}


	/**
	 * @param idCirculo
	 */
	public void setIdCirculo(String idCirculo) {
		this.idCirculo = idCirculo;
	}


	/**
	 * @return
	 */
	public long getIdFolio() {
		return idFolio;
	}


	/**
	 * @param idFolio
	 */
	public void setIdFolio(long idFolio) {
		this.idFolio = idFolio;
	}


	/**
	 * @return
	 */
	public String getIdTurno() {
		return idTurno;
	}


	/**
	 * @param idTurno
	 */
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}


	/**
	 * @return
	 */
	public long getSecProceso() {
		return secProceso;
	}


	/**
	 * @param secProceso
	 */
	public void setSecProceso(long secProceso) {
		this.secProceso = secProceso;
	}

	/**
	 * @return
	 */
	public long getIdProceso() {
		return idProceso;
	}


	/**
	 * @param idProceso
	 */
	public void setIdProceso(long idProceso) {
		this.idProceso = idProceso;
	}







}
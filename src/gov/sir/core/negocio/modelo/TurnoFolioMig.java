package gov.sir.core.negocio.modelo;


import org.auriga.core.modelo.TransferObject;


/**
 * Clase que define los Folios que tiene asociados un turno en el sistema FOLIO
 * @author ppabon
 */
public class TurnoFolioMig implements TransferObject {

	/**
	 * Identificador del turno
	 */
    private String idTurno; // pk
    
	/**
	 * Identificador del folio
	 */
    private String idFolio; // pk 
    private static final long serialVersionUID = 1L;
    /**
     * Identificador del proceso
     */
    private long idProceso; // pk

	/**
	 * Identificador del circulo
	 */
    private String idCirculo; // pk 
    
    
    /**
     * Determina si el registro es inválido
     */    
    private boolean anulado;
    
    /**
     * Determina si el registro fue creado en SIR
     */    
    private boolean creadoSir;    
    


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
	public boolean isCreadoSir() {
		return creadoSir;
	}



	/**
	 * @param creadoSir
	 */
	public void setCreadoSir(boolean creadoSir) {
		this.creadoSir = creadoSir;
	}



	/**
	 * @return
	 */
	public String getIdFolio() {
		return idFolio;
	}



	/**
	 * @param idFolio
	 */
	public void setIdFolio(String idFolio) {
		this.idFolio = idFolio;
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
	public String getIdCirculo() {
		return idCirculo;
	}

	/**
	 * @param idCirculo
	 */
	public void setIdCirculo(String idCirculo) {
		this.idCirculo = idCirculo;
	}






}
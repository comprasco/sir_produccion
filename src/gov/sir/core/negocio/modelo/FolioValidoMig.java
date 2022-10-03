package gov.sir.core.negocio.modelo;


import org.auriga.core.modelo.TransferObject;


/**
 * Clase que define los Folios que tiene asociados un turno en el sistema FOLIO
 * @author ppabon
 */
public class FolioValidoMig implements TransferObject {

	/**
	 * Identificador del folio
	 */
    private String idFolio; // pk 
    private static final long serialVersionUID = 1L;
	/**
	 * Identificador del circulo
	 */
    private String idCirculo; // pk 
    
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
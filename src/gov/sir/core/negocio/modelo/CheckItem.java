package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;


/*
 * Generated by JDO Genie (version:3.2.0beta7 (20 Sep 2004))
 */

/**
 * Clase que modela los requisitos que se deben chequear en la solicitud de 
 * registro de documentos; estos requisitos estan por subtipo de solicitud
 */
public class CheckItem  implements TransferObject{

    private String idCheckItem; // pk 
    private String idSubtipoSol; // pk 
    private String nombre;
    private boolean obligatorio;
    private SubtipoSolicitud subtipoSolicitud; // inverse SubtipoSolicitudEnhanced.checkItems
    private static final long serialVersionUID = 1L;
    /** Metodo constructor por defecto  */
    
    public CheckItem() {
    }

    /**
     * Retorna el identificador del requisito
     * @return idCheckItem
     */
    public String getIdCheckItem() {
        return idCheckItem;
    }

    /**
     * Cambia el identificador del requisito
     * @param idCheckItem
     */
    public void setIdCheckItem(String idCheckItem) {
        this.idCheckItem = idCheckItem;
    }

    /**
     * Retorna el identificador del subtipo de solicitud
     * @return idSubtipoSol
     */
    public String getIdSubtipoSol() {
        return idSubtipoSol;
    }

    /**
     * Cambia el identificador del subtipo de solicitud
     * @param idSubtipoSol
     */
    public void setIdSubtipoSol(String idSubtipoSol) {
        this.idSubtipoSol = idSubtipoSol;
    }

    /**
     * Retorna el nombre del requisito
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre del requisito
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** Retorna el identificador del subtipo de solicitud  */
    
    public SubtipoSolicitud getSubtipoSolicitud() {
        return subtipoSolicitud;
    }

    /** Modifica el identificador del subtipo de solicitud  */
    
    public void setSubtipoSolicitud(SubtipoSolicitud subtipoSolicitud) {
        this.subtipoSolicitud = subtipoSolicitud;
        setIdSubtipoSol(subtipoSolicitud.getIdSubtipoSol());
    }

    /** Indica si es obligatorio el requisito
	 * @return obligatorio
	 */
	public boolean isObligatorio() {
		return obligatorio;
	}

	/** Modifica el flag que indica si es obligatorio el requisito
	 * @param b
	 */
	public void setObligatorio(boolean b) {
		obligatorio = b;
	}

}
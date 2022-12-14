package gov.sir.core.negocio.modelo;

import java.util.Date;

import org.auriga.core.modelo.TransferObject;


/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004)) */
/** Clase que modela la información de la complementación del folio */
public class ComplementacionConflictivaComplementacion implements TransferObject {

	private String idAuditoria; // pk 
    private String idComplementacion; 
    private String complementacion;
    private String complementacionConflictiva;
    private Date fechaCreacion;
    private Usuario usuario; // inverse Usuario.Auditoria
    private static final long serialVersionUID = 1L;
    /** Constructor por defecto */
    public ComplementacionConflictivaComplementacion() {
    }

    /** Retorna el identificador de la complementacion del folio */
    public String getIdComplementacion() {
        return idComplementacion;
    }

    /** Cambia el identificador de la complementacion del folio */
    public void setIdComplementacion(String idComplementacion) {
        this.idComplementacion = idComplementacion;
    }

    /** Retorna el identificador de la complementacion del folio */
    public String getComplementacion() {
        return complementacion;
    }

    /** Cambia el identificador de la complementacion del folio */
    public void setComplementacion(String complementacion) {
        this.complementacion = complementacion;
    }
    
	/**
	 * Retorna la complementacion conflictiva. (Que es del sistema FOLIO)
	 * @return
	 */
	public String getComplementacionConflictiva() {
		return complementacionConflictiva;
	}

	/**
	 * Cambia la complementacion conflictiva. (Que es del sistema FOLIO)
	 * @param complementacionConflictiva
	 */
	public void setComplementacionConflictiva(String complementacionConflictiva) {
		this.complementacionConflictiva = complementacionConflictiva;
	}    
	
	/**
	 * @return
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return
	 */
	public String getIdAuditoria() {
		return idAuditoria;
	}

	/**
	 * @param idAuditoria
	 */
	public void setIdAuditoria(String idAuditoria) {
		this.idAuditoria = idAuditoria;
	}

	/**
	 * @return
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}



}
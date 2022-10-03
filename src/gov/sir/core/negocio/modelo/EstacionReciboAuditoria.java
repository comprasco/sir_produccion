package gov.sir.core.negocio.modelo;

import java.util.Date;

import org.auriga.core.modelo.TransferObject;

/*
 * Generated by Versant Open Access (version:3.2.6 (17 Nov 2004)) */
/** Clase que modela las secuencias de los recibos por estaci�n  */
public class EstacionReciboAuditoria implements TransferObject{

	private int idAuditoria; // pk 
	private String idEstacion; 
    private Date fechaCreacion;
    private long numeroNuevo;
    private long numeroAnterior;
    private long numeroProceso; 
    private Usuario usuario; // inverse Usuario.Auditoria
    private static final long serialVersionUID = 1L;
    /** Constructor por defecto */
    public EstacionReciboAuditoria() {
    }

    /**
     * Retorna el identificador de auditoria
     * @return idAuditoria
     */
    public int getIdAuditoria() {
        return idAuditoria;
    }

    /**
     * Cambia el identificador de auditoria
     * @paranm idAuditoria
     */
    public void setIdAuditoria(int idAuditoria) {
        this.idAuditoria = idAuditoria;
    }
    
    /** Retorna el identificador de la estaci�n */
    public String getIdEstacion() {
        return idEstacion;
    }

    /** Cambia el identificador de la estaci�n */
    public void setIdEstacion(String idEstacion) {
        this.idEstacion = idEstacion;
    }

    /** Retorna la fecha creaci�n del registro en la base de datos */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /** Cambia la fecha creaci�n del registro en la base de datos */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /** Retorna el n�mero final de secuencia de recibo */
    public long getNumeroNuevo() {
        return numeroNuevo;
    }

    /** Cambia el n�mero final de secuencia de recibo */
    public void setNumeroNuevo(long numeroNuevo) {
        this.numeroNuevo = numeroNuevo;
    }

    /** Retorna el n�mero inicial de secuencia de recibo */
    public long getNumeroAnterior() {
        return numeroAnterior;
    }

    /** Cambia el n�mero Anterior de secuencia de recibo */
    public void setNumeroAnterior(long numeroAnterior) {
        this.numeroAnterior = numeroAnterior;
    }

    public long getNumeroProceso() {
		return numeroProceso;
	}

	public void setNumeroProceso(long numeroProceso) {
		this.numeroProceso = numeroProceso;
	}
	
	/**
     * Retorna el identificador de usuario
     * @return usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Cambia el identificador de usuario
     * @paranm usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
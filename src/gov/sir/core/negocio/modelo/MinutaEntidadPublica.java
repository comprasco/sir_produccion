package gov.sir.core.negocio.modelo;

import java.util.Date;

import org.auriga.core.modelo.TransferObject;

/* Generated by Versant Open Access (version:3.2.9 (17 Jan 2005)) */

/** Clase que modela la asociaci?n entre las entidades publicas otorgantes con las minutas */

public class MinutaEntidadPublica implements TransferObject {

    private String idEntidadPublica; // pk 
    private String idMinuta; // pk 
    private Date fechaCreacion;
    private boolean exento;
    private EntidadPublica entidadPublica;
    private Minuta minuta; // inverse MinutaEnhanced.entidadesPublicas
    private static final long serialVersionUID = 1L;
    /** Constructor por defecto */
    public MinutaEntidadPublica() {
    }

    /** Retorna el identificador de la entidad p?blica  */
    public String getIdEntidadPublica() {
        return idEntidadPublica;
    }

    /** Cambia el identificador de la entidad p?blica  */
    public void setIdEntidadPublica(String idEntidadPublica) {
        this.idEntidadPublica = idEntidadPublica;
    }

    /** Retorna el identificador de la minuta */
    public String getIdMinuta() {
        return idMinuta;
    }

    /** Cambia el identificador de la minuta */
    public void setIdMinuta(String idMinuta) {
        this.idMinuta = idMinuta;
    }

    /** Retorna la fecha de creaci?n del registro en la base de datos */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /** Cambia la fecha de creaci?n del registro en la base de datos */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /** Retorna el identificador de la entidad p?blica  */
    public EntidadPublica getEntidadPublica() {
        return entidadPublica;
    }

    /** Cambia el identificador de la entidad p?blica  */
    public void setEntidadPublica(EntidadPublica entidadPublica) {
        this.entidadPublica = entidadPublica;
        setIdEntidadPublica(entidadPublica.getIdEntidadPublica());
    }

    /** Retorna el identificador de la minuta */
    public Minuta getMinuta() {
        return minuta;
    }

    /** Cambia el identificador de la minuta */
    public void setMinuta(Minuta minuta) {
        this.minuta = minuta;
        setIdMinuta(minuta.getIdMinuta());
    }

    public boolean isExento() {
		return exento;
	}

	public void setExento(boolean exento) {
		this.exento = exento;
	}
}

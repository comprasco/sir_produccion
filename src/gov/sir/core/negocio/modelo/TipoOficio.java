package gov.sir.core.negocio.modelo;

import java.util.Date;

import org.auriga.core.modelo.TransferObject;

/** Clase que modela los tipos de oficios configurados en el sistema  */
/*
 * Generated by Versant Open Access (version:3.2.6 (17 Nov 2004))
 */
public class TipoOficio implements TransferObject{

    private String idTipoOficio; // pk 
    private Date fechaCreacion;
    private String nombre;
    private static final long serialVersionUID = 1L;
    /** Metodo constructor por defecto  */
    public TipoOficio() {
    }

    /** Retorna el identificador del tipo de oficio */
    public String getIdTipoOficio() {
        return idTipoOficio;
    }

    /** Modifica el identificador del tipo de oficio */
    public void setIdTipoOficio(String idTipoOficio) {
        this.idTipoOficio = idTipoOficio;
    }

    /** Retorna la fecha de creacion del registro en la base de datos  */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /** Modifica la fecha de creacion del registro en la base de datos  */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /** Retorna el nombre del tipo de oficio */
    public String getNombre() {
        return nombre;
    }

    /** Modifica el nombre del tipo de oficio */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

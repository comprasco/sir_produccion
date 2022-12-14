package gov.sir.core.negocio.modelo;

import java.util.Date;

import org.auriga.core.modelo.TransferObject;

/*
 * Generated by JDO Genie (version:3.2.0beta7 (20 Sep 2004))
 */
public class TipoFotocopia implements TransferObject { 

    private String idTipoFotocopia; // pk 
    private Date fechaCreacion;
    private String nombre;
    private static final long serialVersionUID = 1L;
    /** Metodo constructor por defecto  */
    
    public TipoFotocopia() {
    }

    /** Retorna el identificador del tipo de fotocopia  */
    public String getIdTipoFotocopia() {
        return idTipoFotocopia;
    }

    /** Modifica el identificador del tipo de fotocopia  */
    public void setIdTipoFotocopia(String idTipoFotocopia) {
        this.idTipoFotocopia = idTipoFotocopia;
    }

    /** Retorna la fecha de creacion del registro en la base de datos  */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /** Modifica la fecha de creacion del registro en la base de datos  */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /** Retorna el nombre del tipo de fotocopia */
    public String getNombre() {
        return nombre;
    }
    
    /** Modifica el nombre del tipo de fotocopia */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
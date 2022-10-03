package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/** Clase que modela los tipos de anotacion configurados en el sistema  */
/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */
public class TipoAnotacion implements TransferObject{
    private String idTipoAnotacion; // pk 
    private String nombre;
private static final long serialVersionUID = 1L;
    /** Metodo constructor por defecto */
    public TipoAnotacion() {
    }

    /** Retorna el identificador del tipo de anotacion  */
    public String getIdTipoAnotacion() {
        return idTipoAnotacion;
    }

    /** Modifica el identificador del tipo de anotacion  */
    public void setIdTipoAnotacion(String idTipoAnotacion) {
        this.idTipoAnotacion = idTipoAnotacion;
    }

    /** Retorna el tipo de nombre de la anotacion  */
    public String getNombre() {
        return nombre;
    }

    /** Modifica el tipo de nombre de la anotacion  */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

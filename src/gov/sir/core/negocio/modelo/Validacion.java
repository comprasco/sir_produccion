package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/** Clase que modela las validaciones de folios configuradas en el sistema  */
/*
 * Generated by JDO Genie (version:3.2.0beta7 (20 Sep 2004))
 */
public class Validacion implements TransferObject {

    private String idValidacion; // pk 
    private String nombre;
    private static final long serialVersionUID = 1L;
    /** Metodo constructor por defecto  */
    
    public Validacion() {
    }

    /** Retorna el identificador de la validacion  */
    
    public String getIdValidacion() {
        return idValidacion;
    }

    /** Modifica el identificador de la validacion  */
    
    public void setIdValidacion(String idValidacion) {
        this.idValidacion = idValidacion;
    }

    /** Retorna el nombre de la validación  */
    
    public String getNombre() {
        return nombre;
    }

    /** Modifica el nombre de la validación  */
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
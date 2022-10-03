/*
 * Created on 07-jun-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package gov.sir.core.negocio.modelo;

import java.util.List;

import org.auriga.core.modelo.TransferObject;

/** Clase que modela la configuracion de los procesos y las fases 
 * @author dsalas */

public class Fase implements TransferObject{
    /**
     * Turnos que se encuentran en la fase, para un círculo
     * @link aggregation
     *       @associates <{gov.sir.core.negocio.modelo.Turno}>
     * @supplierCardinality 0..*
     * @clientCardinality 1
     */
    private List turnos;
    private String ID;
    private String nombre;
    private String descripcion;
    private String estacion;
    private static final long serialVersionUID = 1L;
    /** Constructor por defecto */
    
    public Fase() {
    }

    public Fase(String ID, String nombre, String descripcion, String estacion) {
        this.ID = ID;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estacion = estacion;
    }

    /**
     * Getter for property ID.
     * @return Value of property ID.
     */
    public java.lang.String getID() {
        return ID;
    }

    /**
     * Setter for property ID.
     * @param ID New value of property ID.
     */
    public void setID(java.lang.String ID) {
        this.ID = ID;
    }

    /**
     * Getter for property nombre.
     * @return Value of property nombre.
     */
    public java.lang.String getNombre() {
        return nombre;
    }

    /**
     * Setter for property nombre.
     * @param nombre New value of property nombre.
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter for property estacion.
     * @return Value of property estacion.
     */
    public java.lang.String getEstacion() {
        return estacion;
    }

    /**
     * Setter for property estacion.
     * @param estacion New value of property estacion.
     */
    public void setEstacion(java.lang.String estacion) {
        this.estacion = estacion;
    }

    /**
     * Getter for property descripcion.
     * @return Value of property descripcion.
     */
    public java.lang.String getDescripcion() {
        return descripcion;
    }

    /**
     * Setter for property descripcion.
     * @param descripcion New value of property descripcion.
     */
    public void setDescripcion(java.lang.String descripcion) {
        this.descripcion = descripcion;
    }
    
    public boolean equals(Object obj) {

        Fase temp = (Fase)obj;

        return this.ID.equals(temp.getID());
    }

}

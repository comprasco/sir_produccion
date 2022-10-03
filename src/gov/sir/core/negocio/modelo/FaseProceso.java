/*
 * Created on 26/01/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo;

import java.io.Serializable;


/**
 * Clase creada para almancenar los datos de una fase relacionada
 * con un proceso determinado (Es diferente a la clase <code>Fase</code> la cual
 * modela los Turnos que se encuentran en la fase, para un círculo)
 * [Estos objetos se crean luego de una consulta de una vista. Por tanto
 * no tienen relación directa con el modelo de persistencia]
 *
 * @author jmendez
 *
 */
public class FaseProceso implements Serializable {
    private String idProceso; // pk
    private String idFase; // pk
    private String nombre;
    private String descripcion;
    private static final long serialVersionUID = 1L;
    /** Constructor por defecto   */
    
    public FaseProceso() {
        super();
    }

    /** Retorna la descripcion asociada
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /** Retorna el identificador de la fase
     * @return idFase
     */
    public String getIdFase() {
        return idFase;
    }

    /** Retorna el identificador del proceso
     * @return idProceso
     */
    public String getIdProceso() {
        return idProceso;
    }

    /** Retorna el nombre
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /** Modifica la descripcion
     * @param string
     */
    public void setDescripcion(String string) {
        descripcion = string;
    }

    /** Modifica el identificador de la fase
     * @param string
     */
    public void setIdFase(String string) {
        idFase = string;
    }

    /** Modifica el identificador del proceso
     * @param string
     */
    public void setIdProceso(String string) {
        idProceso = string;
    }

    /** Modifica el nombre
     * @param string
     */
    public void setNombre(String string) {
        nombre = string;
    }
}

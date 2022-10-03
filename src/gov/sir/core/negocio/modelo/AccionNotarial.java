package gov.sir.core.negocio.modelo;

import javax.swing.text.StyledEditorKit;
import org.auriga.core.modelo.TransferObject;


/**
 * Clase que representa los tipos de contrato que se celebran entre las dos entidades 
 * relacionadas en una minuta
 * @author fceballos
 */
public class AccionNotarial implements TransferObject{


    /**
     * Identificador de la acción notarial
     */
    private String idAccionNotarial; // pk 
    private static final long serialVersionUID = 1L;
    /**
     * Indica si las minutas asociadas a esta acción notarial requieren cuantía
     */
    private boolean cuantia;
    
    /**
     * Nombre de la acción notarial
     */
    private String nombre;
    
    /**
     * Tipo de acción notarial
     */
    private String tipo;
    
    /**
    * Indica si esta activo
    */
    private boolean activo;
    /**
     * Constructor por defecto
     */
    
    public AccionNotarial() {
    }

	/**
	 * Retorna el identificador de la acción notarial
	 * @return idAccionNotarial
	 */
    public String getIdAccionNotarial() {
        return idAccionNotarial;
    }

	/**
	 * Cambia el identificador de la acción notarial
	 * @param idAccionNotarial
	 */
    public void setIdAccionNotarial(String idAccionNotarial) {
        this.idAccionNotarial = idAccionNotarial;
    }

	/**
	 * Retorna el atributo que indica si las minutas asociadas 
	 * a esta acción notarial requieren cuantía
	 * @return cuantia
	 */
    public boolean isCuantia() {
        return cuantia;
    }

	/**
	 * Cambia el atributo que indica si las minutas asociadas 
	 * a esta acción notarial requieren cuantía
	 * @param cuantia
	 */
    public void setCuantia(boolean cuantia) {
        this.cuantia = cuantia;
    }

	/**
	 * Retorna el nombre de la acción notarial
	 * @return nombre
	 */
    public String getNombre() {
        return nombre;
    }

	/**
	 * Cambia el nombre de la acción notarial
	 * @param nombre
	 */
	public void setNombre(String nombre) {
        this.nombre = nombre;
    }

	/**
	 * Retorna el tipo de acción notarial
	 * @return tipo
	 */
    public String getTipo() {
        return tipo;
    }

	/**
	 * Cambia el tipo de acción notarial
	 * @param tipo
	 */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    /**
	 * Retorna si al canal esta activo
	 * @return antivo
	 */
    public boolean isActivo() {
        return activo;
    }

	/**
	 * Cambia el estado de accion notarial
	 * @param activo
	 */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
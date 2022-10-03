package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.RepartoNotarial;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Clase que Representa objetos de Tipo Reparto Notarial Enhanced.
 * <p>Un Reparto Notarial consta de un identificador obtenido a partir de un secuencial,
 * la fecha en la que se realiza (tomada del sistema), un boolean que indica si el Reparto es
 * Normal (true) o extraordinario (false) y un listado de todas las Minutas asociadas con
 * el proceso de Reparto. 
 * @author dlopez
 */
public class RepartoNotarialEnhanced extends Enhanced{

    
	/**
	* Identificador del Reparto Notarial
	*/
    private String idRepartoNotarial; 
    
	/**
	* Fecha de Creación del Reparto Notarial 
	*/
    private Date fechaCreacion;
    
	/**
	* Identificador que indica el tipo de Reparto Notarial.
	* Normal (true), Extraordinario (false). 
	*/
    private boolean normal;
    
	/**
	* Listado de todas las Minutas asociadas con el Reparto Notarial 
	*/
    private List minutas = new ArrayList(); 
    
    
    
	/**
	* Constructor de la Clase Reparto Notarial Enhanced
	*/
    public RepartoNotarialEnhanced() {
    }

    
	/**
	* Obtiene el identificador de un <code>RepartoNotarial</code>
	* @return identificador de un <code>RepartoNotarial</code>
	*/
    public String getIdRepartoNotarial() {
        return idRepartoNotarial;
    }

    
	/**
	* Asigna al atributo idRepartoNotarial el valor recibido como parámetro. 
	* @param idRepartoNotarial identificador que se va a asignar al Reparto Notarial
	*/
    public void setIdRepartoNotarial(String idRepartoNotarial) {
        this.idRepartoNotarial = idRepartoNotarial;
    }

    
	/**
	* Obtiene la fecha en la que fue realizado un Reparto Notarial.
	* @return fecha de Creación de un reparo notarial.
	*/
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

	/**
	* Asigna al atributo fechaCreacion la fecha recibida como parámetro. 
	* @param fechaCreacion Fecha de creación que se va a asignar al Reparto Notarial
	*/
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

    
	/**
	* Retorna la lista de Minutas asociadas con el Reparto Notarial.
	* @return Lista con las Minutas asociadas con el Reparto Notarial.
	* @see gov.sir.core.negocio.modelo.Minuta
	*/
    public List getMinutas() {
        return Collections.unmodifiableList(minutas);
    }

    
	/**
	 * Agrega una <code>Minuta</code> al Reparto Notarial.
	 * @param newMinuta <code>Minuta que se va a agregar al Reparto Notarial
	 * @return true o false dependiendo del resultado de la inserción.
	 */
    public boolean addMinuta(MinutaEnhanced newMinuta) {
        return minutas.add(newMinuta);
    }

    
	/**
	* Elimina una <code>Minuta</code> del Reparto Notarial.
	* @param oldMinuta <code>Minuta que se va a eliminar del Reparto Notarial
	* @return true o false dependiendo del resultado de la eliminación. 
	*/
    public boolean removeMinuta(MinutaEnhanced oldMinuta) {
        return minutas.remove(oldMinuta);
    }
    
    
	/**
	* Retorna el valor del atributo booleano que indica la clase de Reparto Notarial.
	* <p>
	* Normal (true), Extraordinario (false).
	* @return atributo booleano que indica la clase de Reparto Notarial, 
	* Normal (true), Extraordinario (false).
	*/
	public boolean isNormal() {
		return normal;
	}

	/**
	* Asigna al atributo booleano que indica la clase de Reparto Notarial, el valor recibido como
	* parámetro.
	* <p>
	* Normal (true), Extraordinario (false).
	* @param b booleano que indica la clase de Reparto Notarial, 
	* Normal (true), Extraordinario (false).
	*/
	public void setNormal(boolean b) {
		normal = b;
	}


    public static RepartoNotarialEnhanced enhance(RepartoNotarial reparto) {
		return (RepartoNotarialEnhanced) Enhanced.enhance(reparto);
	}

}
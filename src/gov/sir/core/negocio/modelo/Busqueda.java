package gov.sir.core.negocio.modelo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.auriga.core.modelo.TransferObject;

/*
 * Generated by JDO Genie (version:3.2.0beta2 (28 Jun 2004))
 */

/**
 * Clase que modela los criterios de busqueda para consultas
 */
public class Busqueda implements TransferObject{
    private static final long serialVersionUID = 1L;
    private String idBusqueda; // pk 
    private String idSolicitud; // pk 
    private String apellido1Ciudadano;
    private String apellido2Ciudadano;
    private String direccion;
    private String matricula;
    private String nombreCiudadano;
    private String numeroCatastral;
    private String numeroDocCiudadano;
    private long numeroIntentos;
    private String tipoDocCiudadano;
    private String idTipoPredio;
    private SolicitudConsulta solicitud; // inverse SolicitudConsulta.busqueda
    private List resultadosFolio = new ArrayList(); // contains ResultadoFolio  inverse ResultadoFolio.busqueda
	private String idEje;
	private String nombreEje;
	private String valorEje;
	private String nombreNaturalezaJuridica;
	private long numeroResultados;
	private String idCirculoBusqueda;
	
	private Date fechaCreacion;
	
	/** Constructor por defecto */
    public Busqueda() {
    }

    /**
     * Retorna el identificador de la busqueda relativa a la solicitud 
     * @return idBusqueda */
    public String getIdBusqueda() {
        return idBusqueda;
    }

    /**
     * Cambia el identificador de la busqueda relativa a la solicitud 
     * @paranm idBusqueda*/
    public void setIdBusqueda(String idBusqueda) {
        this.idBusqueda = idBusqueda;
    }

    /**
     * Retorna el identificador de la solicitud
     * @return idSolicitud
     */
    public String getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * Cambia el identificador de la solicitud
     * @paranm idSolcitud
     */
    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * Retorna el criterio de busqueda relativo al primer apellido de ciudadano en anotaci�n
     * @return apellidoCiudadano
     */
    public String getApellido1Ciudadano() {
        return apellido1Ciudadano;
    }

    /**
     * Cambia el criterio de busqueda relativo al primer apellido de ciudadano en anotaci�n
     * @paranm apellidoCiudadano
     */
    public void setApellido1Ciudadano(String apellido1Ciudadano) {
        this.apellido1Ciudadano = apellido1Ciudadano;
    }

    /**
     * Retorna el criterio de busqueda relativo al segundo apellido de ciudadano en anotaci�n
     * @return apellido2Ciudadano
     */
    public String getApellido2Ciudadano() {
        return apellido2Ciudadano;
    }

    /**
     * Cambia el criterio de busqueda relativo al segundo apellido de ciudadano en anotaci�n
     * @paranm apellido2Ciudadano
     */
    public void setApellido2Ciudadano(String apellido2Ciudadano) {
        this.apellido2Ciudadano = apellido2Ciudadano;
    }

    /**
     * Retorna el criterio de busqueda relativo a la direcci�n de folio
     * @return direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Cambia el criterio de busqueda relativo a la direcci�n de folio
     * @paranm idMatricula
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Retorna el criterio de b�squeda relativo al n�mero de matr�cula
     * @return matricula
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Cambia el criterio de b�squeda relativo al n�mero de matr�cula
     * @param matricula
     * 
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * Retorna el criterio de b�squeda relativo al nombre de ciudadano en anotaci�n
     * @return nombreCiudadano
     */
    public String getNombreCiudadano() {
        return nombreCiudadano;
    }

    /**
     * Cambia el criterio de b�squeda relativo al nombre de ciudadano en anotaci�n
     * @param nombreCiudadano
     */
    public void setNombreCiudadano(String nombreCiudadano) {
        this.nombreCiudadano = nombreCiudadano;
    }

    /**
     * Retorna el criterio de b�squeda relativo al c�digo catastral del folio
     * @return numeroCatastral
     */
    public String getNumeroCatastral() {
        return numeroCatastral;
    }

    /**
     * Cambia el criterio de b�squeda relativo al c�digo catastral del folio
     * @param numeroCatastral
     */
    public void setNumeroCatastral(String numeroCatastral) {
        this.numeroCatastral = numeroCatastral;
    }

    /**
     * Retorna el criterio de b�squeda relativo al n�mero de documento del ciudadano en la anotaci�n
     * @return numeroDocCiudadano
     */
    public String getNumeroDocCiudadano() {
        return numeroDocCiudadano;
    }

    /**
     * Cambia el criterio de b�squeda relativo al n�mero de documento del ciudadano en la anotaci�n
     * @param numeroDocCiudadano
     */
    public void setNumeroDocCiudadano(String numeroDocCiudadano) {
        this.numeroDocCiudadano = numeroDocCiudadano;
    }

    /**
     * Retorna el n�mero de intentos de la b�squeda
     * @return numeroIntentos
     */
    public long getNumeroIntento() {
        return numeroIntentos;
    }

    /**
     * Cambia el n�mero de intentos de la b�squeda
     * @param numeroIntentos
     */
    public void setNumeroIntento(long numeroIntentos) {
        this.numeroIntentos = numeroIntentos;
    }

    /**
     * Retorna el criterio de b�squeda relativo al tipo de documento del ciudadano en la anotaci�n
     * @return tipoDocCiudadano
     */
    public String getTipoDocCiudadano() {
        return tipoDocCiudadano;
    }

    /**
     * Cambia el criterio de b�squeda relativo al tipo de documento del ciudadano en la anotaci�n
     * @param tipoDocCiudadano
     */
    public void setTipoDocCiudadano(String tipoDocCiudadano) {
        this.tipoDocCiudadano = tipoDocCiudadano;
    }


    /**
     * Retorna el identificador de la solicitud
     * @return solicitud
     */
    public SolicitudConsulta getSolicitud() {
        return solicitud;
    }
    
	/**
	* Retorna el criterio de b�squeda relativo al c�rculo del folio
	* @return el identificador del c�rculo asociado a la b�squeda
	*/
	public String getIdCirculoBusqueda(){
		return this.idCirculoBusqueda;
	}
	
	/**
	* Cambia el criterio de b�squeda relativo al c�rculo del folio
	* @param idCirculoBusqueda el identificador del C�rculo asociado
	* a la b�squeda
	*/
	public void setIdCirculoBusqueda(String idCirculoBusqueda){
		   this.idCirculoBusqueda = idCirculoBusqueda;
	   }

	/**
	 * Cambia el identificador de la solicitud */
    public void setSolicitud(SolicitudConsulta solicitud) {
        this.solicitud = solicitud;
        setIdSolicitud(solicitud.getIdSolicitud());
    }

    /** Retorna la lista resultadosFolio   */
    
    public List getResultadosFolios() {
        return Collections.unmodifiableList(resultadosFolio);
    }

    /**
     * A�ade un ResultadoFolio a la lista resultadosFolio
     */
    public boolean addResultadosFolio(ResultadoFolio newResultadosFolio) {
        return resultadosFolio.add(newResultadosFolio);
    }

    /**
     * Elimina un ResultadoFolio a la lista resultadosFolio
     */
    public boolean removeResultadosFolio(ResultadoFolio oldResultadosFolio) {
        return resultadosFolio.remove(oldResultadosFolio);
    }
    
    /** Elimina todos los ResultadosFolio de la lista resultadosFolio  */
    
    public void removeAllResultadosFolio(){
    	resultadosFolio.clear();
    }

    /**
	 * Retorna el criterio de b�squeda relativo al valor del eje de la direcci�n del folio
	 * @return valorEje
	 */
	public String getValorEje() {
		return valorEje;
	}

	/**
	 * Cambia el criterio de b�squeda relativo al valor del eje de la direcci�n del folio
	 * @param string
	 */
	public void setValorEje(String string) {
		valorEje = string;
	}

	/**
	 * Retorna el criterio de b�squeda relativo al eje de la direcci�n del folio
	 * @return idEje
	 */
	public String getIdEje() {
		return idEje;
	}

	/**
	 * Retorna el criterio de b�squeda relativo al tipo de predio del folio
	 * @return idTipoPredio
	 */
	public String getIdTipoPredio() {
		return idTipoPredio;
	}

	/**
	 * Cambia el criterio de b�squeda relativo al eje de la direcci�n del folio
	 * @param string
	 */
	public void setIdEje(String string) {
		idEje = string;
	}

	/**
	 * Cambia el criterio de b�squeda relativo al tipo de predio del folio
	 * @param string
	 */
	public void setIdTipoPredio(String string) {
		idTipoPredio = string;
	}

	/**
	 * Retorna el criterio de b�squeda relativo la naturaleza jur�dica de la anotaci�n
	 * @return nombreNaturalezaJuridica
	 */
	public String getNombreNaturalezaJuridica() {
		return nombreNaturalezaJuridica;
	}

	/**
	 * Cambia el criterio de b�squeda relativo la naturaleza jur�dica de la anotaci�n
	 * @param string
	 */
	public void setNombreNaturalezaJuridica(String string) {
		nombreNaturalezaJuridica = string;
	}

	/**
	 * Retorna el n�mero de resultados que cumplen el criterio de b�squeda
	 * @return numeroResultados
	 */
	public long getNumeroResultados() {
		return numeroResultados;
	}

	/**
	 * Cambia el n�mero de resultados que cumplen el criterio de b�squeda
	 * @param l
	 */
	public void setNumeroResultados(long l) {
		numeroResultados = l;
	}

	/**
	 * Retorna la fecha creaci�n del registro en la base de datos
	 * @return fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Cambia la fecha creaci�n del registro en la base de datos
	 * @param date
	 */
	public void setFechaCreacion(Date date) {
		fechaCreacion = date;
	}

	public String getNombreEje() {
		return nombreEje;
	}

	public void setNombreEje(String nombreEje) {
		this.nombreEje = nombreEje;
	}

	/**
	 * Retorna el listado de resulados folio para que pueda ser modificadas
	 * @return List resultadosFolio
	 */
	public List getResultadosFoliosModificable() {
        return (resultadosFolio);
    }
	/**
	 * Cambia los Resultado Folio de la busqueda
	 * @param List resultadosFolios
	 */
    public void setResultadosFolios(List resultadosFolios) {
        this.resultadosFolio = resultadosFolios;
    }
}
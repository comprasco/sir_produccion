package gov.sir.core.negocio.modelo;



import org.auriga.core.modelo.TransferObject;

/* Generated by JDO Genie (version:3.2.0beta2 (28 Jun 2004)) */

/** Clase que modela las anotaciones encontradas en una b�squeda  */

public class ResultadoAnotacion implements TransferObject {

    private String idAnotacion; // pk 
    private String idBusqueda; // pk 
    private String idMatricula; // pk 
    private String idSolicitud; // pk 
    private Anotacion anotacion;
    private ResultadoFolio resultadoFolio; // inverse ResultadoFolio.resultadosAnotacion
	private boolean isPropietario;
	private Ciudadano ciudadanoPropietario;
        private static final long serialVersionUID = 1L;
	/** M�todo constructor por defecto  */
    public ResultadoAnotacion() {
    }

    /** Retorna el identificador de la anotaci�n */
    public String getIdAnotacion() {
        return idAnotacion;
    }

    /** Cambia el identificador de la anotaci�n */
    public void setIdAnotacion(String idAnotacion) {
        this.idAnotacion = idAnotacion;
    }

    /** Retorna el identificador de la b�squeda  */
    public String getIdBusqueda() {
        return idBusqueda;
    }

    /** Cambia el identificador de la b�squeda  */
    public void setIdBusqueda(String idBusqueda) {
        this.idBusqueda = idBusqueda;
    }

    /** Retorna el identificador de la matr�cula  */
    public String getIdMatricula() {
        return idMatricula;
    }

    /** Cambia el identificador de la matr�cula  */
    public void setIdMatricula(String idMatricula) {
        this.idMatricula = idMatricula;
    }

    /** Retorna el identificador de la b�squeda correspondiente a la solicitud   */
    public String getIdSolicitud() {
        return idSolicitud;
    }

    /** Cambia el identificador de la b�squeda correspondiente a la solicitud   */
    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /** Retorna el identificador de la anotaci�n  */
    public Anotacion getAnotacion() {
        return anotacion;
    }

    /** Cambia el identificador de la anotaci�n  */
    public void setAnotacion(Anotacion anotacion) {
        this.anotacion = anotacion;
        setIdAnotacion(anotacion.getIdAnotacion());
        setIdMatricula(anotacion.getIdMatricula());
    }

    public ResultadoFolio getResultadoFolio() {
        return resultadoFolio;
    }

    public void setResultadoFolio(ResultadoFolio resultadoFolio) {
        this.resultadoFolio = resultadoFolio;
        setIdBusqueda(resultadoFolio.getIdBusqueda());
        setIdMatricula(resultadoFolio.getIdMatricula());
        setIdSolicitud(resultadoFolio.getIdSolicitud());
    }

    /** Indica si el ciudadano que se esta buscando es propietario
	 * @return
	 */
	public boolean isPropietario() {
		return isPropietario;
	}

	/** Modifica la marca que indica si el ciudadano que se esta buscando es propietario
	 * @param b
	 */
	public void setPropietario(boolean b) {
		isPropietario = b;
	}


	/** Retorna el identificador del ciudadano encontrado en la anotacion
	 * @return ciudadanoPropietario
	 */
	public Ciudadano getCiudadanoPropietario() {
		return ciudadanoPropietario;
	}

	/** Modifica el identificador del ciudadano encontrado en la anotacion
	 * @param ciudadano
	 */
	public void setCiudadanoPropietario(Ciudadano ciudadano) {
		ciudadanoPropietario = ciudadano;
	}

}
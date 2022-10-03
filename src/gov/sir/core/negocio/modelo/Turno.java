package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

import gov.sir.core.negocio.modelo.constantes.CModoBloqueo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/** Clase que modela la informacion de turnos creados en el sistema  */

public class Turno implements TransferObject {
    private Solicitud solicitud;
    private String anio; // pk 
    private String idCirculo; // pk 
    private long idProceso; // pk 
    private String idTurno; // pk 
    private String ultimaRespuesta;
    private String turnoREL;
    private String reasignacion;
    private CirculoProceso circuloproceso;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean error;
    private static final long serialVersionUID = 1L;
	private boolean revocatoria;
    private Usuario usuarioDestino; 
    private int modoBloqueo = CModoBloqueo.NORMAL;
	private String idRelacionActual;
	private String idFaseRelacionActual;
	private String observacionesAnulacion;
	private String observacionesHabilitar;
	private String anulado;
	private Usuario usuarioAnulacion;
	private boolean nacional;
	
	private String idMatriculaUltima;
	

    /**
     * @link aggregation
     * @associates <{gov.sir.core.negocio.modelo.Nota}>
     * @supplierCardinality 0..*
     * @clientCardinality 1
     */
    private List notas = new ArrayList(); // contains Nota  inverse Nota.turno

    //	...
    private List historial = new ArrayList(); // contains TurnoHistoria  inverse TurnoHistoria.turno

    //	...
    private String idWorkflow;
    private long lastIdHistoria;
    private long lastIdNota;

    /**
     * Variable que almacena el identificador unico para la fase en la cual se encuentra el turno
     */
    private String idFase;

    /**
     * Variable que almacena la descripcion del turno
     */
    private String descripcion;
    
    /**
     * Indica la consistencia del folio con respecto a WF (0,1,2) 0=tiene wokflow consistente, 1=tiene workflow inconsistente, 2=No tiene Workflow
     */
    private int consistenciaWF;
    
    private String relStat;
    private String relEndpoint;
    /**
     * Método constructor por defecto de la clase Turno. Crea una nueva instancia de la clase Turno.
     */
    public Turno() {
        this.notas = new ArrayList();
    }

    /**
     * Metodo constructor con valores especificos. Crea una nueva instancia de la clase Turno
     * @param id tiene la informacion del identificador del turno
     * @param solicitante tiene la informacion del solicitante que le pertenece el turno
     * @param notasInf tiene todas la informacion acerca de la notas informativas asociadas al turno
     */
    public Turno(String idTurno, long idProceso, String idFase, List notasInf,
        String descripcion) {
        this.idTurno = idTurno;
        this.idProceso = idProceso;
        this.idFase = idFase;

        if (notasInf != null) {
            this.notas = notasInf;
        } else {
            this.notas = new ArrayList();
        }

        this.descripcion = descripcion;
    }

    public String getIdMatriculaUltima() {
		return idMatriculaUltima;
	}

	public void setIdMatriculaUltima(String idMatriculaUltima) {
		this.idMatriculaUltima = idMatriculaUltima;
	}

	/**
     * Este método retorna el valor del identificador del turno.
     * @return Un número (long) positivo, diferente de 0
     */
    public String getIdTurno() {
        return idTurno;
    }

    /**
     * Este método cambia el valor que tenga el identificador del turno.
     * @param idTurno variable tipo long que tiene el nuevo identificador del turno
     */
    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    /**
     * Este método retorna el valor del identificador de la fase asocioada al turno.
     * @return Un número (long) positivo, diferente de 0
     */
    public String getIdFase() {
        return idFase;
    }

    /**
     * Este método cambia el valor que tenga el identificador de la fase asocioada al turno.
     * @param idFase variable tipo long que tiene el nuevo identificador del turno
     */
    public void setIdFase(String idFase) {
        this.idFase = idFase;
    }

    /**
     * Este método retorna el valor de la descripcion del turno.
     * @return Una cadena (String) no vacia, diferente de nulo.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Este método cambia el valor que tenga la descripcion del turno.
     * @param descripcion variable tipo String que tiene la nueva descripcion del turno
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /** Retorna el identificador de la solicitud asociada al turno
     * @return
     */
    public Solicitud getSolicitud() {
        return solicitud;
    }

    /** Modifica el identificador de la solicitud asociada al turno
     * @param solicitud
     */
    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    /** Retorna la lista notas  */
    public List getNotas() {
        return Collections.unmodifiableList(notas);
    }

    /** Añade una nota a la lista notas */
    
    public boolean addNota(Nota newNota) {
        return notas.add(newNota);
    }

    /** Elimina una nota a la lista notas */
    
    public boolean removeNota(Nota oldNota) {
        return notas.remove(oldNota);
    }

    /** Retorna la lista historial */
    
    public List getHistorials() {
        return Collections.unmodifiableList(historial);
    }
    
    public void setHistorials(List historial) {
        this.historial = historial;
    }

    /** Añade un turno historia a la lista historial */
    
    public boolean addHistorial(TurnoHistoria newHistorial) {
        return historial.add(newHistorial);
    }

    /** Elimina un turno historia a la lista historial */
    
    public boolean removeHistorial(TurnoHistoria oldHistorial) {
        return historial.remove(oldHistorial);
    }

    /** Retorna la relacion entre el circulo y el proceso */
    
    public CirculoProceso getCirculoproceso() {
        return circuloproceso;
    }

    /** Modifica la relacion entre el circulo y el proceso */
    public void setCirculoproceso(CirculoProceso circuloproceso) {
        this.circuloproceso = circuloproceso;
        setIdCirculo(circuloproceso.getIdCirculo());
        setIdProceso(circuloproceso.getIdProceso());
        setAnio(circuloproceso.getAnio());
    }

    /** Retorna el identificador del circulo
     * @return
     */
    public String getIdCirculo() {
        return idCirculo;
    }

    /** Modifica el identificador del circulo
     * @param string
     */
    public void setIdCirculo(String string) {
        idCirculo = string;
    }

    /** Retorna la ultima respuesta con que avanzo por el Workflow
     * @return
     */
    public String getUltimaRespuesta() {
        return ultimaRespuesta;
    }

    /** Modifica la ultima respuesta con que avanzo por el Workflow
     * @param string
     */
    public void setUltimaRespuesta(String string) {
        ultimaRespuesta = string;
    }

    public String getTurnoREL() {
        return turnoREL;
    }

    public void setTurnoREL(String turnoREL) {
        this.turnoREL = turnoREL;
    }

    /** Retorna el identificador del turno correspondiente al año
     * @return
     */
    public String getAnio() {
        return anio;
    }

    /** Modifica el identificador del turno correspondiente al año
     * @param string
     */
    public void setAnio(String string) {
        anio = string;
    }

    /** Modifica el identificador del turno correspondiente al proceso
     * @param l
     */
    public void setIdProceso(long l) {
        idProceso = l;
    }

    /** Retorna el identificador del turno correspondiente al proceso
     * @return
     */
    public long getIdProceso() {
        return idProceso;
    }

    /** Retorna el identificador del turno en workflow (anio-circulo-proceso-consecutivo)
     * @return
     */
    public String getIdWorkflow() {
        return idWorkflow;
    }

    /** Retorna el secuencial de historial de turno
     * @return
     */
    public long getLastIdHistoria() {
        return lastIdHistoria;
    }

    /** Modifica el identificador del turno en workflow (anio-circulo-proceso-consecutivo)
     * @param string
     */
    public void setIdWorkflow(String string) {
        idWorkflow = string;
    }

    /** Modifica el secuencial de historial de turno
     * @param l
     */
    public void setLastIdHistoria(long l) {
        lastIdHistoria = l;
    }

    /** Retorna la fecha de finalizacion del turno
	 * @return
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/** Retorna la fecha de inicio del turno
	 * @return
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/** Modifica la fecha de finalizacion del turno
	 * @param date
	 */
	public void setFechaFin(Date date) {
		fechaFin = date;
	}

	/** Modifica la fecha de inicio del turno
	 * @param date
	 */
	public void setFechaInicio(Date date) {
		fechaInicio = date;
	}

	/** Retorna el secuencial de notas informativas de turno
	 * @return
	 */
	public long getLastIdNota() {
		return lastIdNota;
	}

	/** Modifica el secuencial de notas informativas de turno
	 * @param l
	 */
	public void setLastIdNota(long l) {
		lastIdNota = l;
	}

	/** Indica si es necesario que el turno pase a correcciones
	 * @return
	 */
	public boolean isError() {
		return error;
	}

	/** Modifica el flag que indica si es necesario que el turno pase a correcciones
	 * @param b
	 */
	public void setError(boolean b) {
		error = b;
	}

	/** Retorna el identificador del usuario que crea el turno
	 * @return
	 */
	public Usuario getUsuarioDestino() {
		return usuarioDestino;
	}

	/** Cambia el identificador del usuario que crea el turno
	 * @param usuario
	 */
	public void setUsuarioDestino(Usuario usuario) {
		usuarioDestino = usuario;
	}

	/** Retorna el modo de bloqueo de folios (0,1,2) 0=normal, 1=delegar_usuarrio, 2=delegar_cualquiera
	 * @return
	 */
	public int getModoBloqueo() {
		return modoBloqueo;
	}

	/** Modifica el modo de bloqueo de folios (0,1,2) 0=normal, 1=delegar_usuarrio, 2=delegar_cualquiera
	 * @param i
	 */
	public void setModoBloqueo(int i) {
		modoBloqueo = i;
	}

	/** Retorna la consistencia del folio con respecto a WF (0,1,2) 0=tiene wokflow consistente, 
	 * 1=tiene workflow inconsistente, 2=No tiene Workflow
	 * @return
	 */
	public int getConsistenciaWF() {
		return consistenciaWF;
	}

	/** Modifica la consistencia del folio con respecto a WF (0,1,2) 0=tiene wokflow consistente, 
	 * 1=tiene workflow inconsistente, 2=No tiene Workflow
	 * @param i
	 */
	public void setConsistenciaWF(int i) {
		consistenciaWF = i;
	}

	public String getIdFaseRelacionActual() {
		return idFaseRelacionActual;
	}

	public void setIdFaseRelacionActual(String idFaseRelacionActual) {
		this.idFaseRelacionActual = idFaseRelacionActual;
	}

	public String getIdRelacionActual() {
		return idRelacionActual;
	}

	public void setIdRelacionActual(String idRelacionActual) {
		this.idRelacionActual = idRelacionActual;
	}

	public String getObservacionesAnulacion() {
		return observacionesAnulacion;
	}

	public void setObservacionesAnulacion(String observacionesAnulacion) {
		this.observacionesAnulacion = observacionesAnulacion;
	}

	public String getAnulado() {
		return anulado;
	}

	public void setAnulado(String anulado) {
		this.anulado = anulado;
	}

	public Usuario getUsuarioAnulacion() {
		return usuarioAnulacion;
	}

	public void setUsuarioAnulacion(Usuario usuarioAnulacion) {
		this.usuarioAnulacion = usuarioAnulacion;
	}

	/**
	 * @return
	 */
	public boolean isRevocatoria() {
		return revocatoria;
	}

	/**
	 * @param b
	 */
	public void setRevocatoria(boolean b) {
		revocatoria = b;
	}

	public boolean isNacional() {
		return nacional;
	}

	public void setNacional(boolean nacional) {
		this.nacional = nacional;
	}

	public String getObservacionesHabilitar() {
		return observacionesHabilitar;
	}

	public void setObservacionesHabilitar(String observacionesHabilitar) {
		this.observacionesHabilitar = observacionesHabilitar;
	}

        public String getReasignacion() {
            return reasignacion;
        }

        public void setReasignacion(String reasignacion) {
            this.reasignacion = reasignacion;
        }
        
            /**
        * @return the relStat
        */
        public String getRelStat() {
           return relStat;
        }

        /**
        * @param relStat the relStat to set
        */
        public void setRelStat(String relStat) {
           this.relStat = relStat;
        }

        /**
        * @return the relEndpoint
        */
        public String getRelEndpoint() {
           return relEndpoint;
        }

        /**
        * @param relEndpoint the relEndpoint to set
        */
        public void setRelEndpoint(String relEndpoint) {
           this.relEndpoint = relEndpoint;
        }
      
}

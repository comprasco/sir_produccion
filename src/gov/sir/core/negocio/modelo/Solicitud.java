package gov.sir.core.negocio.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.auriga.core.modelo.TransferObject;


/* Generated by JDO Genie (version:3.0.0 (08 Jun 2004)) */

/** Clase que modela la informaci�n de los diferentes tipos de solicitudes  */

public class Solicitud implements TransferObject{
    private String idSolicitud; // pk
	private Turno turno; 
    private Turno turnoAnterior;
	private Circulo circulo;
	private Usuario usuario;
    private List solicitudFolios = new ArrayList(); // contains SolicitudFolio  inverse SolicitudFolio.solicitud
    private List liquidaciones = new ArrayList(); // contains Liquidacion  inverse Liquidacion.solicitud
    private Date fecha;
    private long lastIdLiquidacion;
    private Proceso proceso;
    private String comentario;
	private List solicitudesPadre = new ArrayList(); // contains SolicitudAsociada  inverse SolicitudAsociada.solicitudHija
	private List solicitudesHijas = new ArrayList(); // contains SolicitudAsociada  inverse SolicitudAsociada.solicitudPadre
	private List solicitudesVinculadas = new ArrayList(); // contains SolicitudVinculada  inverse SolicitudVinculada.solicitudPadre
	private int numReimpresionesRecibo;
	private static final long serialVersionUID = 1L;
	/** M�todo constructor por defecto  */
    public Solicitud() {
    }

    /** Retorna el identificador de la solicitud  */
    public String getIdSolicitud() {
        return idSolicitud;
    }

    /** Cambia el identificador de la solicitud  */
    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /** Retorna el identificador del turno anterior asociado  */
    public Turno getTurnoAnterior() {
        return turnoAnterior;
    }

    /** Cambia el identificador del turno anterior asociado  */
    public void setTurnoAnterior(Turno turnoAnterior) {
        this.turnoAnterior = turnoAnterior;
    }

    /** Retorna la lista solicitudFolios  */
    public List getSolicitudFolios() {
        return Collections.unmodifiableList(solicitudFolios);
    }

    /** A�ade una SolicitudFolio a la lista solicitudFolios  */  
    public boolean addSolicitudFolio(SolicitudFolio newSolicitudFolio) {
        return solicitudFolios.add(newSolicitudFolio);
    }

    /** Elimina una SolicitudFolio a la lista solicitudFolios  */
    public boolean removeSolicitudFolio(SolicitudFolio oldSolicitudFolio) {
        return solicitudFolios.remove(oldSolicitudFolio);
    }

    /** Retorna la lista liquidaciones  */
    public List getLiquidaciones() {
        return Collections.unmodifiableList(liquidaciones);
    }

    /** A�ade una liquidaci�n a la lista liquidaciones   */
    public boolean addLiquidacion(Liquidacion newLiquidacion) {
        return liquidaciones.add(newLiquidacion);
    }

    /** Elimina una liquidaci�n a la lista liquidaciones   */
    public boolean removeLiquidacion(Liquidacion oldLiquidacion) {
        return liquidaciones.remove(oldLiquidacion);
    }
    
    /** Retorna la lista solicitudesPadre  */
	public List getSolicitudesPadres() {
		return Collections.unmodifiableList(solicitudesPadre);
	}

	/** A�ade una solicitud padre a la lista solicitudesPadre   */
	public boolean addSolicitudesPadre(SolicitudAsociada newSolicitudesPadre) {
		return solicitudesPadre.add(newSolicitudesPadre);
	}

	/** Elimina una solicitud padre de la lista solicitudesPadre   */
	public boolean removeSolicitudesPadre(SolicitudAsociada oldSolicitudesPadre) {
		return solicitudesPadre.remove(oldSolicitudesPadre);
	}
	
	/** Retorna la lista solicitudesHijas  */
	 public List getSolicitudesHijas() {
		 return Collections.unmodifiableList(solicitudesHijas);
	 }

	 /** A�ade una solicitud hija a la lista solicitudesHijas  */
	 public boolean addSolicitudesHija(SolicitudAsociada newSolicitudesHija) {
		 return solicitudesHijas.add(newSolicitudesHija);
	 }

	 /** Elimina una solicitud hija de la lista solicitudesHijas  */
	 public boolean removeSolicitudesHija(SolicitudAsociada oldSolicitudesHija) {
		 return solicitudesHijas.remove(oldSolicitudesHija);
	 }
	

    /** Retorna el identificador del turno
	 * @return
	 */
	public Turno getTurno() {
		return turno;
	}

	/** Modifica el identificador del turno
	 * @param turno
	 */
	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	/** Retorna el identificador del c�rculo asociado a la solicitud
	 * @return circulo
	 */
	public Circulo getCirculo() {
		return circulo;
	}

	/** Retorna el identificador del usuario que crea la solicitud
	 * @return
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/** Cambia el identificador del c�rculo asociado a la solicitud
	 * @param circulo
	 */
	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}

	/** Cambia el identificador del usuario que crea la solicitud
	 * @param usuario
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/** Retorna la fecha enq ue se realiza la solicitud
	 * @return
	 */	
	public Date getFecha(){
		return fecha;
	}
	
	/** Modifica la fecha enq ue se realiza la solicitud
	 * @param fecha
	 */
	public void setFecha(Date fecha){
		this.fecha=fecha;
	}

	/** Retorna la secuencia de liquidaciones
	 * @return
	 */
	public long getLastIdLiquidacion() {
		return lastIdLiquidacion;
	}

	/** Cambia la secuencia de liquidaciones
	 * @param l
	 */
	public void setLastIdLiquidacion(long l) {
		lastIdLiquidacion = l;
	}

	/** Retorna el identificador del proceso asociado
	 * @return
	 */
	public Proceso getProceso() {
		return proceso;
	}

	/** Cambia el identificador del proceso asociado
	 * @param proceso
	 */
	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	/** Retorna el comentario de la solicitud
	 * @return
	 */
	public String getComentario() {
		return comentario;
	}

	/** Cambia el comentario de la solicitud
	 * @param string
	 */
	public void setComentario(String string) {
		comentario = string;
	}

	/** Retorna el identificador del ciudadano solicitante
	 * @return
	 */
	public Ciudadano getCiudadano() {
		return null;
	}

	/** Retorna el identificador de datos de antiguo sistema
	 * @return
	 */
	public DatosAntiguoSistema getDatosAntiguoSistema() {
		return null;
	}
	
	/** Cambia el identificador del ciudadano solicitante
	 * @return
	 */
	public void setCiudadano(Ciudadano ciudadano) {
	}

	/** Cambia el identificador de datos de antiguo sistema
	 * @return
	 */
	public void setDatosAntiguoSistema(DatosAntiguoSistema datosAntiguoSistema) {
	}
	
	/** Modifica la lista solicitudFolio  */
	public void setSolicitudFolios(List listaSolicitudes)
	{
		this.solicitudFolios = listaSolicitudes;
	}
	
	/** Retorna la lista solicitudesVinculadas  */
	public List getSolicitudesVinculadas() {
		return Collections.unmodifiableList(solicitudesVinculadas);
	}

	/** A�ade una solicitud vinculada a la lista solicutdesVinculadas   */
	public boolean addSolicitudesVinculada(SolicitudVinculada newSolicitudesVinculada) {
		return solicitudesVinculadas.add(newSolicitudesVinculada);
	}

	/** Elimina una solicitud vinculada a la lista solicutdesVinculadas   */
	public boolean removeSolicitudesVinculada(SolicitudVinculada oldSolicitudesVinculada) {
		return solicitudesVinculadas.remove(oldSolicitudesVinculada);
	}
	/**
	 * @return Returns the numReimpresionesRecibo.
	 */
	public int getNumReimpresionesRecibo() {
		return numReimpresionesRecibo;
	}
	/**
	 * @param numReimpresionesRecibo The numReimpresionesRecibo to set.
	 */
	public void setNumReimpresionesRecibo(int numReimpresionesRecibo) {
		this.numReimpresionesRecibo = numReimpresionesRecibo;
	}
}
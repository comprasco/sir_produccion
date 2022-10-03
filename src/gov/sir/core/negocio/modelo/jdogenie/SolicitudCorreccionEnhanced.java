/*
 * Created on 21 de septiembre de 2004
 * SolicitudCorreccion.java
 */
package gov.sir.core.negocio.modelo.jdogenie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gov.sir.core.negocio.modelo.RangoFolio;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;

/**
 * @author dlopez
 * 
 */
public class SolicitudCorreccionEnhanced extends SolicitudEnhanced {

	/** 
	* Indica c�mo se recibi� la solicitud (Personalmente o por correo).
	*/
	private TipoRecepcionPeticionEnhanced tipoRecepcionPeticion;

	/**
	* La direccion de envio de la correccion cuando se recibi� la solicitud por correo.
	*/
	private String direccionEnvio;

	/**
	 * Indica si la solicitud de correcci�n est� asociada a un derecho de petici�n.
	 */
	private boolean derechoPeticion;

	/**
	 * El ciudadano que solicit� la correcci�n. 
	 */
	private CiudadanoEnhanced ciudadano;

	/**
	 * La descripci�n de la correcci�n
	 */
	private String descripcion;
	
		 
	/**
	* Lista con  los rangos de las solicitudes de Folio, asociadas con la
	* solicitud.
	*/
	private List rangosFolio = new ArrayList(); // contains RangoFolioEnhanced  inverse RangoFolioEnhanced.solicitud
	
	
	/**
	* Identificador del �ltimo rango asociado con la solicitud. 
	*/
	private long lastIdRangoFolio;
	
	
	/**
	 * Indica si la solicitud es aprobada
	 */
	private boolean aprobada;
	
	/**
	 * Datos de antiguo sistema
	 */
	private DatosAntiguoSistemaEnhanced datosAntiguoSistema;
	
	/**
	 * Lista de permisos asociados
	 */
	private List permisos = new ArrayList(); // contains SolicitudPermisoCorreccionEnhanced  inverse SolicitudPermisoCorreccionEnhanced.solicitud


	private boolean solicitadaAnteriormente;
	private String interesJuridico;
	private int lastIdNotaActuacion;
	
//	 ...
    private List notasActuaciones = new ArrayList(); // contains NotaActuacionEnhanced  inverse NotaActuacionEnhanced.solicitud
// ...
    public List getNotasActuaciones() {
        return Collections.unmodifiableList(notasActuaciones);
    }

    public boolean addNotasActuacione(NotaActuacionEnhanced newNotasActuacione) {
        return notasActuaciones.add(newNotasActuacione);
    }

    public boolean removeNotasActuacione(NotaActuacionEnhanced oldNotasActuacione) {
        return notasActuaciones.remove(oldNotasActuacione);
    }
// ...


	/**
	 * @return el tipo de recepcion de la petici�n. 
	 */
	public TipoRecepcionPeticionEnhanced getTipoRecepcionPeticion() {
		return this.tipoRecepcionPeticion;
	}

	/**
	 * @param el tipo de recepcion de la petici�n. 
	 */
	public void setTipoRecepcionPeticion(TipoRecepcionPeticionEnhanced tipoRecepcion) {
		this.tipoRecepcionPeticion = tipoRecepcion;
	}

	/**
	 * @return el ciudadano que solicit� la correcci�n
	 */
	public CiudadanoEnhanced getCiudadano() {
		return ciudadano;
	}

	/**
	 * @param el ciudadano que solicit� la correcci�n
	 */
	public void setCiudadano(CiudadanoEnhanced ciudadano) {
		this.ciudadano = ciudadano;
	}

	/**
	 * @return si la solicitud tiene asociado derecho de petici�n.
	 */
	public boolean getDerechoPeticion() {
		return derechoPeticion;
	}

	/**
	 * @param booelan que indica si la solicitud tiene asociado derecho de petici�n.
	 */
	public void setDerechoPeticion(boolean derechoPet) {
		this.derechoPeticion = derechoPet;
	}

	/**
	 * @return direcci�n de env�o del ciudadano que solicit� la correcci�n
	 */
	public String getDireccionEnvio() {
		return this.direccionEnvio;
	}

	/**
	 * @param direcci�n de env�o del ciudadano que solicit� la correcci�n
	 */
	public void setDireccionEnvio(String direccion) {
		this.direccionEnvio = direccion;
	}

	/**
	 * @return descripcion de la correccion. 
	 */
	public String getDescripcion() {
		return this.descripcion;
	}

	/**
	 * @param descripcion de la correccion. 
	 */
	public void setDescripcion(String descr) {
		this.descripcion = descr;
	}

	public static SolicitudCorreccionEnhanced enhance(SolicitudCorreccion solicitud) {
		return (SolicitudCorreccionEnhanced) Enhanced.enhance(solicitud);
	}
	
	
	
	public List getRangosFolios() {
		return Collections.unmodifiableList(rangosFolio);
	}

	public boolean addRangosFolio(RangoFolio newRangosFolio) {
		return rangosFolio.add(newRangosFolio);
	}

	public boolean removeRangosFolio(RangoFolio oldRangosFolio) {
		return rangosFolio.remove(oldRangosFolio);
	}

	/**
	 * @return
	 */
	public long getLastIdRangoFolio() {
		return lastIdRangoFolio;
	}

	/**
	 * @param l
	 */
	public void setLastIdRangoFolio(long l) {
		lastIdRangoFolio = l;
	}


	/**
	 * @return
	 */
	public boolean isAprobada() {
		return aprobada;
	}

	/**
	 * @param b
	 */
	public void setAprobada(boolean b) {
		aprobada = b;
	}

	/**
	 * @return
	 */
	public DatosAntiguoSistemaEnhanced getDatosAntiguoSistema() {
		return datosAntiguoSistema;
	}

	/**
	 * @param enhanced
	 */
	public void setDatosAntiguoSistema(DatosAntiguoSistemaEnhanced enhanced) {
		datosAntiguoSistema = enhanced;
	}
	

	public List getPermisos() {
		return Collections.unmodifiableList(permisos);
	}

	public boolean addPermiso(SolicitudPermisoCorreccionEnhanced newSolicitudPermisoCorreccionEnhanced) {
		return permisos.add(newSolicitudPermisoCorreccionEnhanced);
	}

	public boolean removePermiso(SolicitudPermisoCorreccionEnhanced oldSolicitudPermisoCorreccionEnhanced) {
		return permisos.remove(oldSolicitudPermisoCorreccionEnhanced);
	}

	/**
	 * @return
	 */
	public String getInteresJuridico() {
		return interesJuridico;
	}

	/**
	 * @return
	 */
	public boolean isSolicitadaAnteriormente() {
		return solicitadaAnteriormente;
	}

	/**
	 * @param string
	 */
	public void setInteresJuridico(String string) {
		interesJuridico = string;
	}

	/**
	 * @param b
	 */
	public void setSolicitadaAnteriormente(boolean b) {
		solicitadaAnteriormente = b;
	}

	public int getLastIdNotaActuacion() {
		return lastIdNotaActuacion;
	}

	public void setLastIdNotaActuacion(int lastIdNotaActuacion) {
		this.lastIdNotaActuacion = lastIdNotaActuacion;
	}

}

/*
 * Created on 21 de septiembre de 2004
 * SolicitudCorreccion.java
 */
package gov.sir.core.negocio.modelo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.auriga.core.modelo.TransferObject;


/** Clase que modela la informaci�n de las solicitudes de correcci�n
 * @author dlopez
 */
public class SolicitudCorreccion extends Solicitud implements TransferObject {
	
     /** 
     * Indica c�mo se recibi� la solicitud (Personalmente o por correo)
     */
     private TipoRecepcionPeticion tipoRecepcionPeticion;
     private static final long serialVersionUID = 1L;
     /**
     * La direccion de envio de la correccion cuando se recibi� la solicitud por correo
     */
	 private String direccionEnvio;
	 
	 /**
	  * Indica si la solicitud de correcci�n est� asociada a un derecho de petici�n
	  */
	 private boolean derechoPeticion;
	 
	 /**
	  * El ciudadano que solicit� la correcci�n
	  */
	 private Ciudadano ciudadano;
	 
	 /**
	  * La descripci�n de la correcci�n
	  */
	 private String descripcion;
	 
	/**
	* Lista con los rangos de las solicitudes de Folio, asociadas con la
	* solicitud
	*/
	private List rangosFolio = new ArrayList(); // contains RangoFolioEnhanced  inverse RangoFolioEnhanced.solicitud
	
	/**
	* Identificador del �ltimo rango asociado con la solicitud 
	*/
	private long lastIdRangoFolio;
	
	/**
	 * Indica si la solicitud es aprobada
	 */
	private boolean aprobada;
	
	/**
	 * Datos de antiguo sistema
	 */
	private DatosAntiguoSistema datosAntiguoSistema;

	/**
	 * Lista de permisos asociados
	 */
	private List permisos = new ArrayList(); // contains SolicitudPermisoCorreccion  inverse SolicitudPermisoCorreccionE.solicitud

	private boolean solicitadaAnteriormente;
	private String interesJuridico;
	
	private int lastIdNotaActuacion;
	
//	 ...
   private List notasActuaciones = new ArrayList(); // contains NotaActuacionEnhanced  inverse NotaActuacionEnhanced.solicitud
//...
   public List getNotasActuaciones() {
       return Collections.unmodifiableList(notasActuaciones);
   }

   public boolean addNotasActuacione(NotaActuacion newNotasActuacione) {
       return notasActuaciones.add(newNotasActuacione);
   }

   public boolean removeNotasActuacione(NotaActuacion oldNotasActuacione) {
       return notasActuaciones.remove(oldNotasActuacione);
   }
//...

	/** Retorna el identificador del tipo de recepci�n de la petici�n 
	 * @return el tipo de recepcion de la petici�n
	 */
	public TipoRecepcionPeticion getTipoRecepcionPeticion(){
		return this.tipoRecepcionPeticion;
	}

	/** Cambia el identificador del tipo de recepci�n de la petici�n
	 * @param el tipo de recepcion de la petici�n 
	 */
	public void setTipoRecepcionPeticion(TipoRecepcionPeticion tipoRecepcion) {
		this.tipoRecepcionPeticion = tipoRecepcion;
	}
	
	/** Retorna el identificador del ciudadano solicitante
	 * @return el ciudadano que solicit� la correcci�n
	 */
	public Ciudadano getCiudadano() {
		return ciudadano;
	}

	/** Cambia el identificador del ciudadano solicitante
	 * @param el ciudadano que solicit� la correcci�n
	 */
	public void setCiudadano(Ciudadano ciudadano) {
		this.ciudadano = ciudadano;
	}
	
	/** Retorna el flag que indica si es derecho de petici�n
	 * @return si la solicitud tiene asociado derecho de petici�n
	 */
	public boolean getDerechoPeticion() {
		return derechoPeticion;
	}

	/** Cambia el flag que indica si es derecho de petici�n
	 * @param booelan que indica si la solicitud tiene asociado derecho de petici�n
	 */
	public void setDerechoPeticion (boolean derechoPet) {
		this.derechoPeticion = derechoPet;
	}
	
	/** Retorna la direcci�n de env�o del ciudadano
	 * @return direcci�n de env�o del ciudadano que solicit� la correcci�n
	 */
	public String getDireccionEnvio() {
		return this.direccionEnvio;
	}

	/** Cambia la direcci�n de env�o del ciudadano
	 * @param direcci�n de env�o del ciudadano que solicit� la correcci�n
	 */
	public void setDireccionEnvio(String direccion) {
		this.direccionEnvio = direccion;
	}

	/** Retorna la descripci�n de la solicitud
	 * @return descripcion de la correccion
	 */
	public String getDescripcion() {
		return this.descripcion;
	}

	/** Cambia la descripci�n de la solicitud
	 * @param descripcion de la correccion
	 */
	public void setDescripcion (String descr) {
		this.descripcion = descr;
	}

	/** Retorna la lista rangosFolio */
	public List getRangosFolios() {
		return Collections.unmodifiableList(rangosFolio);
	}

	/** A�ade un RangosFolio a la lista rangosFolio  */
	public boolean addRangosFolio(RangoFolio newRangosFolio) {
		return rangosFolio.add(newRangosFolio);
	}

	/** Elimina un RangosFolio a la lista rangosFolio  */
	public boolean removeRangosFolio(RangoFolio oldRangosFolio) {
		return rangosFolio.remove(oldRangosFolio);
	}

	/** Retorna la secuencia de rangos de folio
	 * @return lastIdRangoFolio
	 */
	public long getLastIdRangoFolio() {
		return lastIdRangoFolio;
	}

	/** Cambia la secuencia de rangos de folio
	 * @param l
	 */
	public void setLastIdRangoFolio(long l) {
		lastIdRangoFolio = l;
	}

	/** Indica si una solicitud es aprobada en determinado momento del flujo
	 * @return
	 */
	public boolean isAprobada() {
		return aprobada;
	}

	/** Modifica la marca que indica si una solicitud es aprobada 
	 * en determinado momento del flujo
	 * @param b
	 */
	public void setAprobada(boolean b) {
		aprobada = b;
	}

	/** Retorna el identificador de los datos de antiguo sistema
	 * @return
	 */
	public DatosAntiguoSistema getDatosAntiguoSistema() {
		return datosAntiguoSistema;
	}

	/** Cambia el identificador de los datos de antiguo sistema
	 * @param sistema
	 */
	public void setDatosAntiguoSistema(DatosAntiguoSistema sistema) {
		datosAntiguoSistema = sistema;
	}

	/** Retorna la lista permisos */
	public List getPermisos() {
		return Collections.unmodifiableList(permisos);
	}

	/** A�ade un permiso a la lista permisos  */
	public boolean addPermiso(SolicitudPermisoCorreccion newSolicitudPermisoCorreccionEnhanced) {
		return permisos.add(newSolicitudPermisoCorreccionEnhanced);
	}

	/** Elimina un permiso a la lista permisos  */
	public boolean removePermiso(SolicitudPermisoCorreccion oldSolicitudPermisoCorreccionEnhanced) {
		return permisos.remove(oldSolicitudPermisoCorreccionEnhanced);
	}

	/** Retorna el interes jur�dico asociado a la solicitud de correcci�n
	 * @return interesJuridico
	 */
	public String getInteresJuridico() {
		return interesJuridico;
	}

	/** Indica si la solicitud de correccion fue solicitada con anterioridad
	 * @return
	 */
	public boolean isSolicitadaAnteriormente() {
		return solicitadaAnteriormente;
	}

	/** Cambia el interes jur�dico asociado a la solicitud de correcci�n
	 * @param string
	 */
	public void setInteresJuridico(String string) {
		interesJuridico = string;
	}

	/** Modifica la marca que indica si la solicitud de correccion 
	 * fue solicitada con anterioridad
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

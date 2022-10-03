/*
 * Created on 21 de septiembre de 2004
 * SolicitudCorreccion.java
 */
package gov.sir.core.negocio.modelo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.auriga.core.modelo.TransferObject;


/** Clase que modela la información de las solicitudes de corrección
 * @author dlopez
 */
public class SolicitudCorreccion extends Solicitud implements TransferObject {
	
     /** 
     * Indica cómo se recibió la solicitud (Personalmente o por correo)
     */
     private TipoRecepcionPeticion tipoRecepcionPeticion;
     private static final long serialVersionUID = 1L;
     /**
     * La direccion de envio de la correccion cuando se recibió la solicitud por correo
     */
	 private String direccionEnvio;
	 
	 /**
	  * Indica si la solicitud de corrección está asociada a un derecho de petición
	  */
	 private boolean derechoPeticion;
	 
	 /**
	  * El ciudadano que solicitó la corrección
	  */
	 private Ciudadano ciudadano;
	 
	 /**
	  * La descripción de la corrección
	  */
	 private String descripcion;
	 
	/**
	* Lista con los rangos de las solicitudes de Folio, asociadas con la
	* solicitud
	*/
	private List rangosFolio = new ArrayList(); // contains RangoFolioEnhanced  inverse RangoFolioEnhanced.solicitud
	
	/**
	* Identificador del último rango asociado con la solicitud 
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

	/** Retorna el identificador del tipo de recepción de la petición 
	 * @return el tipo de recepcion de la petición
	 */
	public TipoRecepcionPeticion getTipoRecepcionPeticion(){
		return this.tipoRecepcionPeticion;
	}

	/** Cambia el identificador del tipo de recepción de la petición
	 * @param el tipo de recepcion de la petición 
	 */
	public void setTipoRecepcionPeticion(TipoRecepcionPeticion tipoRecepcion) {
		this.tipoRecepcionPeticion = tipoRecepcion;
	}
	
	/** Retorna el identificador del ciudadano solicitante
	 * @return el ciudadano que solicitó la corrección
	 */
	public Ciudadano getCiudadano() {
		return ciudadano;
	}

	/** Cambia el identificador del ciudadano solicitante
	 * @param el ciudadano que solicitó la corrección
	 */
	public void setCiudadano(Ciudadano ciudadano) {
		this.ciudadano = ciudadano;
	}
	
	/** Retorna el flag que indica si es derecho de petición
	 * @return si la solicitud tiene asociado derecho de petición
	 */
	public boolean getDerechoPeticion() {
		return derechoPeticion;
	}

	/** Cambia el flag que indica si es derecho de petición
	 * @param booelan que indica si la solicitud tiene asociado derecho de petición
	 */
	public void setDerechoPeticion (boolean derechoPet) {
		this.derechoPeticion = derechoPet;
	}
	
	/** Retorna la dirección de envío del ciudadano
	 * @return dirección de envío del ciudadano que solicitó la corrección
	 */
	public String getDireccionEnvio() {
		return this.direccionEnvio;
	}

	/** Cambia la dirección de envío del ciudadano
	 * @param dirección de envío del ciudadano que solicitó la corrección
	 */
	public void setDireccionEnvio(String direccion) {
		this.direccionEnvio = direccion;
	}

	/** Retorna la descripción de la solicitud
	 * @return descripcion de la correccion
	 */
	public String getDescripcion() {
		return this.descripcion;
	}

	/** Cambia la descripción de la solicitud
	 * @param descripcion de la correccion
	 */
	public void setDescripcion (String descr) {
		this.descripcion = descr;
	}

	/** Retorna la lista rangosFolio */
	public List getRangosFolios() {
		return Collections.unmodifiableList(rangosFolio);
	}

	/** Añade un RangosFolio a la lista rangosFolio  */
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

	/** Añade un permiso a la lista permisos  */
	public boolean addPermiso(SolicitudPermisoCorreccion newSolicitudPermisoCorreccionEnhanced) {
		return permisos.add(newSolicitudPermisoCorreccionEnhanced);
	}

	/** Elimina un permiso a la lista permisos  */
	public boolean removePermiso(SolicitudPermisoCorreccion oldSolicitudPermisoCorreccionEnhanced) {
		return permisos.remove(oldSolicitudPermisoCorreccionEnhanced);
	}

	/** Retorna el interes jurídico asociado a la solicitud de corrección
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

	/** Cambia el interes jurídico asociado a la solicitud de corrección
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

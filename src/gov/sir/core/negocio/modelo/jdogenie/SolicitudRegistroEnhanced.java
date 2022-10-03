/*
 * Created on 06-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SolicitudRegistro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SolicitudRegistroEnhanced extends SolicitudEnhanced{
	
	private DocumentoEnhanced documento;
	private CiudadanoEnhanced ciudadano;
	private SubtipoSolicitudEnhanced subtipoSolicitud;
	private SubtipoAtencionEnhanced subtipoAtencion;
	private TipoRecepcionPeticionEnhanced tipoRecepcion;
	private List checkedItems = new ArrayList(); // contains SolicitudCheckedItemEnhanced  inverse SolicitudCheckedItemEnhanced.solicitud
	private DatosAntiguoSistemaEnhanced datosAntiguoSistema;
	private List rangosFolio = new ArrayList(); // contains RangoFolioEnhanced  inverse RangoFolioEnhanced.solicitud
	private long lastIdRangoFolio;
	private TestamentoEnhanced testamento;
	
	/**
	* Atributo utilizado para guardar el estado de clasificación de un turno de registro.
	* <p> Puede tomar los valores: INSCRITO, DEVUELTO, MAYOR VALOR o REGISTRO PARCIAL.
	*/
	private String clasificacionRegistro;
	
	
	/**
	* Atributo para indicar que un turno de registro requiere de un ajuste.  
	*/
	private boolean ajuste;
	
	/**
	 * Lista de permisos asociados
	 */
	private List permisos = new ArrayList(); // contains SolicitudPermisoCorreccionEnhanced  inverse SolicitudPermisoCorreccionEnhanced.solicitud
	
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
	
	
	public SolicitudRegistroEnhanced(){
		super();
	}
	/**
	 * @return
	 */
	public DocumentoEnhanced getDocumento() {
		return documento;
	}

	/**
	 * @param documento
	 */
	public void setDocumento(DocumentoEnhanced documento) {
		this.documento = documento;
	}

	/**
	 * @return
	 */
	public CiudadanoEnhanced getCiudadano() {
		return ciudadano;
	}

	/**
	 * @param enhanced
	 */
	public void setCiudadano(CiudadanoEnhanced enhanced) {
		ciudadano = enhanced;
	}


	 public SubtipoSolicitudEnhanced getSubtipoSolicitud() {
		 return subtipoSolicitud;
	 }

	 public void setSubtipoSolicitud(SubtipoSolicitudEnhanced subtipoSolicitud) {
		 this.subtipoSolicitud = subtipoSolicitud;
	 }

	 public List getCheckedItems() {
		 return Collections.unmodifiableList(checkedItems);
	 }

	 public boolean addCheckedItem(SolicitudCheckedItemEnhanced newCheckedItem) {
		 return checkedItems.add(newCheckedItem);
	 }

	 public boolean removeCheckedItem(SolicitudCheckedItemEnhanced oldCheckedItem) {
		 return checkedItems.remove(oldCheckedItem);
	 }

	 public SubtipoAtencionEnhanced getSubtipoAtencion() {
		 return subtipoAtencion;
	 }

	 public void setSubtipoAtencion(SubtipoAtencionEnhanced subtipoAtencion) {
		 this.subtipoAtencion = subtipoAtencion;
	 }
	 
	public static SolicitudRegistroEnhanced enhance(SolicitudRegistro solicitud) {
		return (SolicitudRegistroEnhanced) Enhanced.enhance(solicitud);
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

	/**
	 * @return
	 */
	public TipoRecepcionPeticionEnhanced getTipoRecepcion() {
		return tipoRecepcion;
	}

	/**
	 * @param enhanced
	 */
	public void setTipoRecepcion(TipoRecepcionPeticionEnhanced enhanced) {
		tipoRecepcion = enhanced;
	}
	
	
	public List getRangosFolios() {
		return Collections.unmodifiableList(rangosFolio);
	}

	public boolean addRangosFolio(RangoFolioEnhanced newRangosFolio) {
		return rangosFolio.add(newRangosFolio);
	}

	public boolean removeRangosFolio(RangoFolioEnhanced oldRangosFolio) {
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
	public TestamentoEnhanced getTestamento() {
		return testamento;
	}

	/**
	 * @param enhanced
	 */
	public void setTestamento(TestamentoEnhanced enhanced) {
		testamento = enhanced;
	}

	/**
	* Retorna el estado de clasificación de un turno de registro
	* @return El estado de clasificación de un turno de registro.
	* @see gov.sir.core.negocio.modelo.constantes.CRespuestaMesaControl
	*/
	public String getClasificacionRegistro() {
		return clasificacionRegistro;
	}


	/**
	 * Asigna al atributo clasificación de un turno de registro el valor recibido
	 * como parámetro. 
	 * @param string clasificacion el valor que va a ser asignado al atributo clasificacion
	 * turno de registro. 
	 * @see gov.sir.core.negocio.modelo.constantes.CRespuestaMesaControl
	 */
	public void setClasificacionRegistro(String clasificacion) {
		clasificacionRegistro = clasificacion;
	}


	
	/**
	* Retorna el valor del atributo ajuste, dentro de una solicitud de registro
	* @return el valor del atributo ajuste, dentro de una solicitud de registro
	* @see gov.sir.core.negocio.modelo.SolicitudRegistro
	*/
	public boolean getAjuste() {
		return ajuste;
	}


	/**
	 * Asigna al atributo ajuste, el valor recibido como parámetro. 
	 * @param valorAjuste valor que va a ser asignado al atributo ajuste de una
	 * solicitud de registro.  
	 * @see gov.sir.core.negocio.modelo.SolicitudRegistro
	 */
	public void setAjuste(boolean valorAjuste) {
		ajuste = valorAjuste;
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

	public int getLastIdNotaActuacion() {
		return lastIdNotaActuacion;
	}

	public void setLastIdNotaActuacion(int lastIdNotaActuacion) {
		this.lastIdNotaActuacion = lastIdNotaActuacion;
	}

}

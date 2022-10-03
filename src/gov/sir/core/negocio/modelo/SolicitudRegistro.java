/*
 * Created on 06-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo;

import gov.sir.core.negocio.modelo.jdogenie.NotaActuacionEnhanced;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/** Clase que modela la informacion de las solcitudes de registro de documentos
 * @author fceballos
 */
public class SolicitudRegistro extends Solicitud{

    private Documento documento;
    private Ciudadano ciudadano;
    private SubtipoSolicitud subtipoSolicitud;
    private SubtipoAtencion subtipoAtencion;
    private TipoRecepcionPeticion tipoRecepcion;
    private List checkedItems = new ArrayList(); // contains SolicitudCheckedItemEnhanced  inverse SolicitudCheckedItemEnhanced.solicitud
    private DatosAntiguoSistema datosAntiguoSistema;
    private List rangosFolio = new ArrayList(); // contains RangoFolioEnhanced  inverse RangoFolioEnhanced.solicitud
    private long lastIdRangoFolio;
    private Testamento testamento;
    private static final long serialVersionUID = 1L;
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
    private List permisos = new ArrayList(); // contains SolicitudPermisoCorreccion  inverse SolicitudPermisoCorreccionE.solicitud

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

       
    /**
     * @Autor: Santiago Vásquez
     * @Change: 2062.TARIFAS.REGISTRALES.2017
     */
    private boolean incentivoRegistral;

    public boolean isIncentivoRegistral() {
        return incentivoRegistral;
    }

    public void setIncentivoRegistral(boolean incentivoRegistral) {
        this.incentivoRegistral = incentivoRegistral;
    }

	
	/** Metodo constructor por defecto */
	public SolicitudRegistro(){
        super();
    }
	/** Retorna el identificador del documento
     * @return documento
     */
    public Documento getDocumento() {
        return documento;
    }

	/** Cambia el identificador del documento
     * @param documento
     */
    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

	/** Retorna el identificador del ciudadano
     * @return ciudadano
     */
    public Ciudadano getCiudadano() {
        return ciudadano;
    }

	/** Cambia el identificador del ciudadano
     * @param enhanced
     */
    public void setCiudadano(Ciudadano enhanced) {
        ciudadano = enhanced;
    }

	/** Retorna el identificador del subtipo de solicitud */
    public SubtipoSolicitud getSubtipoSolicitud() {
        return subtipoSolicitud;
    }

	 /** Cambia el identificador del subtipo de solicitud */
    public void setSubtipoSolicitud(SubtipoSolicitud subtipoSolicitud) {
        this.subtipoSolicitud = subtipoSolicitud;
    }

	 /** Obtiene la lista checkedItems */
    public List getCheckedItems() {
        return Collections.unmodifiableList(checkedItems);
    }

	 /** Añade un identificador de requisito a la lista checkedItems */
    public boolean addCheckedItem(SolicitudCheckedItem newCheckedItem) {
        return checkedItems.add(newCheckedItem);
    }

	 /** Elimina un identificador de requisito a la lista checkedItems */
    public boolean removeCheckedItem(SolicitudCheckedItem oldCheckedItem) {
        return checkedItems.remove(oldCheckedItem);
    }

	 /** Retorna el identificador del subtipo de atencion */
    public SubtipoAtencion getSubtipoAtencion() {
        return subtipoAtencion;
    }

	 /** Cambia el identificador del subtipo de atencion */
    public void setSubtipoAtencion(SubtipoAtencion subtipoAtencion) {
        this.subtipoAtencion = subtipoAtencion;
    }


	/** Retorna el identificador de los datos de antiguo sistema correspondiente asociados 
	 * a la solicitud
     * @return
     */
    public DatosAntiguoSistema getDatosAntiguoSistema() {
        return datosAntiguoSistema;
    }

	/** Cambia el identificador de los datos de antiguo sistema correspondiente asociados 
	 * a la solicitud
     * @param sistema
     */
    public void setDatosAntiguoSistema(DatosAntiguoSistema sistema) {
        datosAntiguoSistema = sistema;
    }

	/** Retorna el identificador del tipo de recepcion
     * @return
     */
    public TipoRecepcionPeticion getTipoRecepcion() {
        return tipoRecepcion;
    }

	/** Cambia el identificador del tipo de recepcion
     * @param peticion
     */
    public void setTipoRecepcion(TipoRecepcionPeticion peticion) {
        tipoRecepcion = peticion;
    }

	/** Retorna la lista rangosFolios */
    public List getRangosFolios() {
        return Collections.unmodifiableList(rangosFolio);
    }

	/** Añade un rango de folios relativos a la solicitud a la 
	 * lista rangosFolio
     */
    public boolean addRangosFolio(RangoFolio newRangosFolio) {
        return rangosFolio.add(newRangosFolio);
    }

	/** Elimina un rango de folios relativos a la solicitud a la 
	 * lista rangosFolio*/
    public boolean removeRangosFolio(RangoFolio oldRangosFolio) {
        return rangosFolio.remove(oldRangosFolio);
    }

	/** Retorna el identificador del rango de folio relativo a la solicitud
     * @return lastIdRangoFolio
     */
    public long getLastIdRangoFolio() {
        return lastIdRangoFolio;
    }

	/** Cambia el identificador del rango de folio relativo a la solicitud
     * @param l
     */
    public void setLastIdRangoFolio(long l) {
        lastIdRangoFolio = l;
    }

	/** Retorna el identificador del testamento
     * @return testamento
     */
    public Testamento getTestamento() {
        return testamento;
    }

	/** Modifica el identificador del testamento
     * @param testamento
     */
    public void setTestamento(Testamento testamento) {
        this.testamento = testamento;
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
	 * @param  clasificacion el valor que va a ser asignado al atributo clasificacion
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

	/** Retorna la lista permisos */
    public List getPermisos() {
        return Collections.unmodifiableList(permisos);
    }

	/** Añade un permiso a la lista permisos */
    public boolean addPermiso(SolicitudPermisoCorreccion newSolicitudPermisoCorreccionEnhanced) {
        return permisos.add(newSolicitudPermisoCorreccionEnhanced);
    }

	/** Elimina un permiso a la lista permisos */
    public boolean removePermiso(SolicitudPermisoCorreccion oldSolicitudPermisoCorreccionEnhanced) {
        return permisos.remove(oldSolicitudPermisoCorreccionEnhanced);
    }

    public int getLastIdNotaActuacion() {
        return lastIdNotaActuacion;
    }

    public void setLastIdNotaActuacion(int lastIdNotaActuacion) {
        this.lastIdNotaActuacion = lastIdNotaActuacion;
    }

}

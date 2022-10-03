/*
 * Created on 27-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SolicitudConsulta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SolicitudConsultaEnhanced extends SolicitudEnhanced {

	private List busqueda = new ArrayList(); // contains Busqueda  inverse Busqueda.solicitud
	private TipoConsultaEnhanced tipoConsulta;
	private long lastIdBusqueda;
	private CiudadanoEnhanced ciudadano;
	private int numeroMaximoBusquedas;
	private DocumentoEnhanced documento;

	public List getBusquedas() {
		return Collections.unmodifiableList(busqueda);
	}

	public boolean addBusqueda(BusquedaEnhanced newBusqueda) {
		return busqueda.add(newBusqueda);
	}

	public boolean removeBusqueda(BusquedaEnhanced oldBusqueda) {
		return busqueda.remove(oldBusqueda);
	}

	/**
	 * @return
	 */
	public TipoConsultaEnhanced getTipoConsulta() {
		return tipoConsulta;
	}

	/**
	 * @param consulta
	 */
	public void setTipoConsulta(TipoConsultaEnhanced consulta) {
		tipoConsulta = consulta;
	}

	/**
	 * @return
	 */
	public long getLastIdBusqueda() {
		return lastIdBusqueda;
	}

	/**
	 * @param l
	 */
	public void setLastIdBusqueda(long l) {
		lastIdBusqueda = l;
	}

	/**
	 * @return
	 */
	public CiudadanoEnhanced getCiudadano() {
		return ciudadano;
	}

	/**
	 * @param ciudadano
	 */
	public void setCiudadano(CiudadanoEnhanced ciudadano) {
		this.ciudadano = ciudadano;
	}

	/*
		 * 
		 */
	public static SolicitudConsultaEnhanced enhance(SolicitudConsulta solicitud) {
		return (SolicitudConsultaEnhanced) Enhanced.enhance(solicitud);
	}

	/**
	 * @return
	 */
	public int getNumeroMaximoBusquedas() {
		return numeroMaximoBusquedas;
	}

	/**
	 * @param i
	 */
	public void setNumeroMaximoBusquedas(int i) {
		numeroMaximoBusquedas = i;
	}

	/**
	 * @return
	 */
	public DocumentoEnhanced getDocumento() {
		return documento;
	}

	/**
	 * @param enhanced
	 */
	public void setDocumento(DocumentoEnhanced enhanced) {
		documento = enhanced;
	}

}

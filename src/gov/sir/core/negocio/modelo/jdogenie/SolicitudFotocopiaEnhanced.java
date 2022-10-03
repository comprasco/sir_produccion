/*
 * Created on 27-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo.jdogenie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gov.sir.core.negocio.modelo.SolicitudFotocopia;

/**
 * @author fceballos, dsalas
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SolicitudFotocopiaEnhanced extends SolicitudEnhanced {

	private String descripcion;
	private CiudadanoEnhanced ciudadano;
	private long lastIdDocumentoFotocopia;
	
	private List documentos = new ArrayList(); // contains DocumentoFotocopiaEnhanced  inverse DocumentoFotocopiaEnhanced.solicitud


	public static SolicitudFotocopiaEnhanced enhance(SolicitudFotocopia solicitud) {
		return (SolicitudFotocopiaEnhanced) Enhanced.enhance(solicitud);
	}

	/**
	 * @return
	 */
	public CiudadanoEnhanced getCiudadano() {
		return ciudadano;
	}

	/**
	 * @return
	 */
	public String getDescripcion() {
		return descripcion;
	}



	/**
	 * @param ciudadano
	 */
	public void setCiudadano(CiudadanoEnhanced ciudadano) {
		this.ciudadano = ciudadano;
	}

	/**
	 * @param string
	 */
	public void setDescripcion(String string) {
		descripcion = string;
	}

	

	public List getDocumentoFotocopia() {
		return Collections.unmodifiableList(documentos);
	}

	public boolean addDocumentoFotocopia(DocumentoFotocopiaEnhanced newDocumentoFotocopiaEnhanced) {
		return documentos.add(newDocumentoFotocopiaEnhanced);
	}

	public boolean removeDocumentoFotocopia(DocumentoFotocopiaEnhanced oldDocumentoFotocopiaEnhanced) {
		return documentos.remove(oldDocumentoFotocopiaEnhanced);
	}

	/**
	 * @return
	 */
	public long getLastIdDocumentoFotocopia() {
		return lastIdDocumentoFotocopia;
	}

	/**
	 * @param l
	 */
	public void setLastIdDocumentoFotocopia(long l) {
		lastIdDocumentoFotocopia = l;
	}

}

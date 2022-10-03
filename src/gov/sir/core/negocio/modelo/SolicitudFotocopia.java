/*
 * Created on 27-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.auriga.core.modelo.TransferObject;

/** Clase que modela la información de las solicitudes de fotocopias
 * @author fceballos, dsalas
 */
public class SolicitudFotocopia extends Solicitud implements TransferObject{

	
	private String descripcion;
	private Ciudadano ciudadano;
	private long lastIdDocumentoFotocopia;
	private static final long serialVersionUID = 1L;
	
	private List documentos = new ArrayList(); // contains DocumentoFotocopia  inverse DocumentoFotocopia.solicitud


	/** Retorna la informacion relativa al ciudadano solicitante
	 * @return ciudadano
	 */
	public Ciudadano getCiudadano() {
		return ciudadano;
	}


	/** Modifica la informacion relativa al ciudadano solicitante
	 * @param ciudadano
	 */
	public void setCiudadano(Ciudadano ciudadano) {
		this.ciudadano = ciudadano;
	}

	/** Retorna la descripción de la solicitud
	 * @return
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/** Cambia la descripción de la solicitud
	 * @param string
	 */
	public void setDescripcion(String string) {
		descripcion = string;
	}
	
	/** Retorna la lista documentos */
	public List getDocumentoFotocopia() {
		return Collections.unmodifiableList(documentos);
	}
 
	/** Añade un documento a la lista documentos  */
	public boolean addDocumentoFotocopia(DocumentoFotocopia newDocumentoFotocopiaEnhanced) {
		return documentos.add(newDocumentoFotocopiaEnhanced);
	}

	/** Elimina un documento de la lista documentos  */
	public boolean removeDocumentoFotocopia(DocumentoFotocopia oldDocumentoFotocopiaEnhanced) {
		return documentos.remove(oldDocumentoFotocopiaEnhanced);
	}

	/** Retorna la secuencia de documentos de fotocopias 
	 * @return
	 */
	public long getLastIdDocumentoFotocopia() {
		return lastIdDocumentoFotocopia;
	}

	/** Cambia la secuencia de documentos de fotocopias 
	 * @param l
	 */
	public void setLastIdDocumentoFotocopia(long l) {
		lastIdDocumentoFotocopia = l;
	}

}

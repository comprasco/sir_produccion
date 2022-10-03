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


/** Clase que modela la información de las solicitudes de consultas
 * @author fceballos
 */
public class SolicitudConsulta extends Solicitud  implements TransferObject{
	

	 private List busqueda = new ArrayList(); // contains Busqueda  inverse Busqueda.solicitud
	 private TipoConsulta tipoConsulta;
	 private long lastIdBusqueda;
	 private Ciudadano ciudadano;
	 private int numeroMaximoBusquedas;
	 private Documento documento;
	private static final long serialVersionUID = 1L;
	 /** Retorna la lista busqueda  */
	 public List getBusquedas() {
		 return Collections.unmodifiableList(busqueda);
	 }

	 /** Añade una busqueda a la lista busqueda */
	 public boolean addBusqueda(Busqueda newBusqueda) {
		 return busqueda.add(newBusqueda);
	 }

	 /** Elimina una busqueda de la lista busqueda */
	 public boolean removeBusqueda(Busqueda oldBusqueda) {
		 return busqueda.remove(oldBusqueda);
	 }

	/** Retorna el identificador del tipo de consulta
	 * @return tipoConsulta
	 */
	public TipoConsulta getTipoConsulta() {
		return tipoConsulta;
	}

	/** Cambia el identificador del tipo de consulta
	 * @param consulta
	 */
	public void setTipoConsulta(TipoConsulta consulta) {
		tipoConsulta = consulta;
	}

	/** Retorna la secuencia de búsquedas
	 * @return lastIdBusqueda
	 */
	public long getLastIdBusqueda() {
		return lastIdBusqueda;
	}

	/** Cambia la secuencia de búsquedas
	 * @param l
	 */
	public void setLastIdBusqueda(long l) {
		lastIdBusqueda = l;
	}

	/** Retorna el identificador del ciudadano solicitante
	 * @return ciudadano
	 */
	public Ciudadano getCiudadano() {
		return ciudadano;
	}

	/** Cambia el identificador del ciudadano solicitante
	 * @param ciudadano
	 */
	public void setCiudadano(Ciudadano ciudadano) {
		this.ciudadano = ciudadano;
	}

	/** Retorna el número máximo de búsquedas
	 * @return numeroMaximoBusquedas
	 */
	public int getNumeroMaximoBusquedas() {
		return numeroMaximoBusquedas;
	}

	/** Cambia el número máximo de búsquedas
	 * @param i
	 */
	public void setNumeroMaximoBusquedas(int i) {
		numeroMaximoBusquedas = i;
	}

	/** Retorna el identificador del documento asociado
	 * @return documento
	 */
	public Documento getDocumento() {
		return documento;
	}

	 /** Cambia el identificador del documento asociado 
	 * @param documento
	 */
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

}

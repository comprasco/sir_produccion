/*
 * Created on 28-oct-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/** Clase que modela la información de las solicitudes de reparto notarial
 * @author fceballos
 */
public class SolicitudRepartoNotarial extends Solicitud implements TransferObject {

	private static final long serialVersionUID = 1L;
	private Minuta minuta;
	
	/** Retorna el identificador de la minuta correspondiente al identificador de la solicitud
	 * @return
	 */
	public Minuta getMinuta() {
		return minuta;
	}

	/** Modifica el identificador de la minuta correspondiente al identificador de la solicitud
	 * @param enhanced
	 */
	public void setMinuta(Minuta enhanced) {
		minuta = enhanced;
	}

}

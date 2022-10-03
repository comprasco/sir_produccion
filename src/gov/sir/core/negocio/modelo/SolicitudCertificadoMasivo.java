/*
 * Created on 06-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;


/**
 * @author mrios
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SolicitudCertificadoMasivo extends Solicitud implements TransferObject {

	private Ciudadano ciudadano;
        /*
         * @author      :   Julio Alcázar Rivas
         * @change      :   Nuevo atributo de la clase
         * Caso Mantis  :   000941
         */
        private Documento documento;
	private static final long serialVersionUID = 1L;	
	/** Metodo constructor  */
	
	public SolicitudCertificadoMasivo(){
		super();
	}

	public Ciudadano getCiudadano() {
		return ciudadano;
	}

	public void setCiudadano(Ciudadano ciudadano) {
		this.ciudadano = ciudadano;
	}

         /*  @author      :   Julio Alcázar Rivas
          *  @change      :   Metodo Get -> documento
	  *  @param documento
	  */
	public Documento getDocumento() {
		return documento;
	}

	 /*  @author      :   Julio Alcázar Rivas
          *  @change      :   Metodo Set -> documento
	  *  @param documento
	  */
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
}

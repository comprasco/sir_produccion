/*
 * Created on 06-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo;

/**
 * @author mrios
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SolicitudCertificadoMasivoEnhanced extends SolicitudEnhanced {

	private CiudadanoEnhanced ciudadano;
        /*
         * @author      :   Julio Alcázar Rivas
         * @change      :   Nuevo atributo de la clase
         * Caso Mantis  :   000941
         */
        private DocumentoEnhanced documento;

	public SolicitudCertificadoMasivoEnhanced() {
		super();
	}

	public CiudadanoEnhanced getCiudadano() {
		return ciudadano;
	}

	public void setCiudadano(CiudadanoEnhanced ciudadano) {
		this.ciudadano = ciudadano;
	}

	/*
	 * @param sistema
	 */
	public static SolicitudCertificadoMasivoEnhanced enhance(SolicitudCertificadoMasivo solicitud) {
		return (SolicitudCertificadoMasivoEnhanced) Enhanced.enhance(solicitud);
	}

        /*  @author      :   Julio Alcázar Rivas
         *  @change      :   Metodo Get -> documento
	 *  @param documento
	 */
	public DocumentoEnhanced getDocumento() {
		return documento;
	}

	/*  @author      :   Julio Alcázar Rivas
         *  @change      :   Metodo Set -> documento
	 *  @param documento
	 */
	public void setDocumento(DocumentoEnhanced enhanced) {
		documento = enhanced;
	}
}

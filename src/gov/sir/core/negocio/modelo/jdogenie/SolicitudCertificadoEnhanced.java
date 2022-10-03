/*
 * Created on 06-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SolicitudCertificado;

/**
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SolicitudCertificadoEnhanced extends SolicitudEnhanced {
	private CiudadanoEnhanced ciudadano;
	private String condicionEntrega;
	private int numeroCertificados;
	private int numImpresiones;
	private DatosAntiguoSistemaEnhanced datosAntiguoSistema;
	private long numeroAnotaciones;
	private DocumentoEnhanced documento;
	private String matriculaNoExistente;

	public SolicitudCertificadoEnhanced() {
		super();
	}

	public CiudadanoEnhanced getCiudadano() {
		return ciudadano;
	}

	public void setCiudadano(CiudadanoEnhanced ciudadano) {
		this.ciudadano = ciudadano;
	}

	/**
	 * @return
	 */
	public String getCondicionEntrega() {
		return condicionEntrega;
	}

	/**
	 * @return
	 */
	public int getNumeroCertificados() {
		return numeroCertificados;
	}

	/**
	 * @param string
	 */
	public void setCondicionEntrega(String string) {
		condicionEntrega = string;
	}

	/**
	 * @param i
	 */
	public void setNumeroCertificados(int i) {
		numeroCertificados = i;
	}

	/**
	 * @return
	 */
	public int getNumImpresiones() {
		return numImpresiones;
	}

	/**
	 * @param i
	 */
	public void setNumImpresiones(int i) {
		numImpresiones = i;
	}

	/**
	 * @return
	 */
	public DatosAntiguoSistemaEnhanced getDatosAntiguoSistema() {
		return datosAntiguoSistema;
	}

	/**
	 * @param sistema
	 */
	public void setDatosAntiguoSistema(DatosAntiguoSistemaEnhanced sistema) {
		datosAntiguoSistema = sistema;
	}

	/*
	 * @param sistema
	 */
	public static SolicitudCertificadoEnhanced enhance(SolicitudCertificado solicitud) {
		return (SolicitudCertificadoEnhanced) Enhanced.enhance(solicitud);
	}

	/**
	 * @return
	 */
	public long getNumeroAnotaciones() {
		return numeroAnotaciones;
	}

	/**
	 * @param l
	 */
	public void setNumeroAnotaciones(long l) {
		numeroAnotaciones = l;
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
	public String getMatriculaNoExistente() {
		return matriculaNoExistente;
	}

	/**
	 * @param string
	 */
	public void setMatriculaNoExistente(String string) {
		matriculaNoExistente = string;
	}

}

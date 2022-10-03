package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DatosAntiguoSistema;

import java.util.Date;

/*
 * Generated by JDO Genie (version:3.2.0beta7 (20 Sep 2004))
 */
public class DatosAntiguoSistemaEnhanced extends Enhanced{

    private String idDatosAntiguoSistema; // pk 
    private Date fechaCreacion;
    private String libroTipo;
    private String libroNumero;
    private String libroPagina;
    private String libroAnio;
    private String tomoNumero;
	private String tomoPagina;
	private String tomoMunicipio;
	private String tomoAnio;
    private String comentario;
	private DocumentoEnhanced documento;

    public DatosAntiguoSistemaEnhanced() {
    }


    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    
	public static DatosAntiguoSistemaEnhanced enhance(DatosAntiguoSistema datosAntiguoSistema){
	   return (DatosAntiguoSistemaEnhanced)Enhanced.enhance(datosAntiguoSistema);
	}


    /**
	 * @return
	 */
	public String getComentario() {
		return comentario;
	}


	/**
	 * @param string
	 */
	public void setComentario(String string) {
		comentario = string;
	}


	

	/**
	 * @return
	 */
	public String getLibroAnio() {
		return libroAnio;
	}

	/**
	 * @return
	 */
	public String getLibroNumero() {
		return libroNumero;
	}

	/**
	 * @return
	 */
	public String getLibroPagina() {
		return libroPagina;
	}

	/**
	 * @return
	 */
	public String getLibroTipo() {
		return libroTipo;
	}

	/**
	 * @return
	 */
	public String getTomoAnio() {
		return tomoAnio;
	}

	/**
	 * @return
	 */
	public String getTomoMunicipio() {
		return tomoMunicipio;
	}

	/**
	 * @return
	 */
	public String getTomoNumero() {
		return tomoNumero;
	}

	/**
	 * @return
	 */
	public String getTomoPagina() {
		return tomoPagina;
	}

	/**
	 * @param string
	 */
	public void setLibroAnio(String string) {
		libroAnio = string;
	}

	/**
	 * @param string
	 */
	public void setLibroNumero(String string) {
		libroNumero = string;
	}

	/**
	 * @param string
	 */
	public void setLibroPagina(String string) {
		libroPagina = string;
	}

	/**
	 * @param string
	 */
	public void setLibroTipo(String string) {
		libroTipo = string;
	}

	/**
	 * @param string
	 */
	public void setTomoAnio(String string) {
		tomoAnio = string;
	}

	/**
	 * @param string
	 */
	public void setTomoMunicipio(String string) {
		tomoMunicipio = string;
	}

	/**
	 * @param string
	 */
	public void setTomoNumero(String string) {
		tomoNumero = string;
	}

	/**
	 * @param string
	 */
	public void setTomoPagina(String string) {
		tomoPagina = string;
	}

	/**
	 * @return
	 */
	public String getIdDatosAntiguoSistema() {
		return idDatosAntiguoSistema;
	}

	/**
	 * @param string
	 */
	public void setIdDatosAntiguoSistema(String string) {
		idDatosAntiguoSistema = string;
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
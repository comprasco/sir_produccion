package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SolicitudAntiguoSistema;

public class SolicitudAntiguoSistemaEnhanced extends SolicitudEnhanced {
	
	private DatosAntiguoSistemaEnhanced datosAntiguoSistema;

	public SolicitudAntiguoSistemaEnhanced() {
		super();
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
	public static SolicitudAntiguoSistemaEnhanced enhance(SolicitudAntiguoSistema solicitud) {
		return (SolicitudAntiguoSistemaEnhanced) Enhanced.enhance(solicitud);
	}
}

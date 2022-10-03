/*
 * Created on 28-oct-2004
 * SolicitudRepartoNotarialEnhanced.java
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;

/**
 * @author dlopez
 * Clase para el manejo de Solicitudes de reparto Notarial 
 * 
 */
public class SolicitudRepartoNotarialEnhanced extends SolicitudEnhanced {

	private MinutaEnhanced minuta;
	
	/**
	 * @return
	 */
	public MinutaEnhanced getMinuta() {
		return minuta;
	}

	/**
	 * @param enhanced
	 */
	public void setMinuta(MinutaEnhanced enhanced) {
		minuta = enhanced;
	}
	

	public static SolicitudRepartoNotarialEnhanced enhance(SolicitudRepartoNotarial solicitud) {
		return (SolicitudRepartoNotarialEnhanced) Enhanced.enhance(solicitud);
	}

}

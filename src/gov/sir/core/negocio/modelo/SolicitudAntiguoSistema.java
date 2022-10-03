package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

public class SolicitudAntiguoSistema extends Solicitud implements TransferObject {
	
	private DatosAntiguoSistema datosAntiguoSistema;
	private static final long serialVersionUID = 1L;	
	/** Método constructor por defecto */
	public SolicitudAntiguoSistema() {
		super();
	}

	/** Retorna el identificador de datos de antiguo sistema
	 * @return datosAntiguoSistema
	 */
	public DatosAntiguoSistema getDatosAntiguoSistema() {
		return datosAntiguoSistema;
	}

	/** Cambia el identificador de datos de antiguo sistema
	 * @param sistema
	 */
	public void setDatosAntiguoSistema(DatosAntiguoSistema sistema) {
		datosAntiguoSistema = sistema;
	}
}

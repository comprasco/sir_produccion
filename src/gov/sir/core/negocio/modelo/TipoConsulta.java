package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/** Clase que modela los tipos de consulta configurados en el sistema  */
/*
 * @author dlopez
 */

public class TipoConsulta implements TransferObject {

	private String idTipoConsulta; // pk

	//constantes para el manejo de los tipos de consultas
	public static final String TIPO_SIMPLE = "0";
	public static final String TIPO_FOLIOS = "1";
	public static final String TIPO_COMPLEJO = "2";
	public static final String TIPO_SIMPLE_CALIFICACION = "3";
	public static final String TIPO_FOLIOS_CALIFICACION = "4";
	public static final String TIPO_EXENTO = "5";
	public static final String TIPO_INTERNET = "6";
	public static final String TIPO_CONSTANCIA = "7";
	
        private static final long serialVersionUID = 1L;
	public static final String SIMPLE = "SIMPLE";
	public static final String FOLIO = "FOLIO";
	public static final String COMPLEJA = "COMPLEJA";
	public static final String INTERNET = "INTERNET";
	//sir-113
	public static final String CONSTANCIA = "CONSTANCIA";

	private String nombre;

	/** Metodo constructor por defecto  */
	public TipoConsulta() {
	}

	/** Retorna el identificador del tipo de consulta  */
	public String getIdTipoConsulta() {
		return idTipoConsulta;
	}

	/** Modifica el identificador del tipo de consulta  */
	public void setIdTipoConsulta(String idTipoConsulta) {
		this.idTipoConsulta = idTipoConsulta;
	}

	/** Retorna el nombre del tipo de consulta  */
	public String getNombre() {
		return nombre;
	}

	/** Modifica el nombre del tipo de consulta  */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
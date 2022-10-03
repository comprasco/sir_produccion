package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;


/** Clase para el manejo de los Tipos de Pantallas Administrativas utilizados en la aplicación */

public class TipoPantalla implements TransferObject{

	private long idTipoPantalla; // pk
	private String nombre;
        private static final long serialVersionUID = 1L;
	/** Metodo constructor por defecto  */
	
	public TipoPantalla() {
	}

	/** Retorna el identificador del tipo de pantalla */
	
	public long getIdTipoPantalla() {
		return idTipoPantalla;
	}

	/** Modifica el identificador del tipo de pantalla */
	public void setIdTipoPantalla(long idTipoPantalla) {
		this.idTipoPantalla = idTipoPantalla;
	}

	/** Retorna el nombre de la pantalla  */
	
	public String getNombre() {
		return nombre;
	}

	/** Modifica el nombre de la pantalla  */
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}

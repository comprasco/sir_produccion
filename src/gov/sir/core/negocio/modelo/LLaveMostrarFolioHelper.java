package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;


public class LLaveMostrarFolioHelper implements TransferObject {

	private String nombrePaginador;
	private String nombreResultado;
	private String nombreNumPagina;
        private static final long serialVersionUID = 1L;
	/** Metodo constructor  */
	
	public LLaveMostrarFolioHelper() {
		
	}

	/** Retorna el nombre del paginador
	 * @return Returns the nombrePaginador
	 */
	
	public String getNombrePaginador() {
		return nombrePaginador;
	}
	
	/** Modifica el nombre del paginador
	 * @param nombrePaginador The nombrePaginador to set
	 */
	
	public void setNombrePaginador(String nombrePaginador) {
		this.nombrePaginador = nombrePaginador;
	}
	
	/** Retorna el nombre del resultado
	 * @return Returns the nombreResultado.
	 */
	public String getNombreResultado() {
		return nombreResultado;
	}
	
	/** Modifica el nombre del resultado
	 * @param nombreResultado The nombreResultado to set.
	 */
	
	public void setNombreResultado(String nombreResultado) {
		this.nombreResultado = nombreResultado;
	}
	
	/** Retorna nombreNumPagina
	 * @return Returns the nombreNumPagina.
	 */
	public String getNombreNumPagina() {
		return nombreNumPagina;
	}
	
	/** Modifica nombreNumPagina
	 * @param nombreNumPagina The nombreNumPagina to set
	 */
	public void setNombreNumPagina(String nombreNumPagina) {
		this.nombreNumPagina = nombreNumPagina;
	}
}
package gov.sir.core.negocio.modelo;

/** Clase que modela la imagen que indica si un folio esta guardado de forma temporal
 * @author ppabon
 */
public class Imagen {
	private String nombre;
	private String width;
	private String height;
	private String funcion;
        private static final long serialVersionUID = 1L;
	/** Retorna la funcion
	 * @return funcion
	 */
	public String getFuncion() {
		return funcion;
	}

	/** Retorna la altura de la imagen
	 * @return height
	 */
	public String getHeight() {
		return height;
	}

	/** Retorna el nombre
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/** Retora la anchura de la imagen
	 * @return width
	 */
	public String getWidth() {
		return width;
	}

	/** Modifica la funcion
	 * @param string
	 */
	public void setFuncion(String string) {
		funcion = string;
	}

	/** Modifica la altura de la imagen
	 * @param string
	 */
	public void setHeight(String string) {
		height = string;
	}

	/** Modifica el nombre
	 * @param string
	 */
	public void setNombre(String string) {
		nombre = string;
	}

	/** Modifica la anchura de la imagen
	 * @param string
	 */
	public void setWidth(String string) {
		width = string;
	}

}

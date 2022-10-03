package gov.sir.core.negocio.modelo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.auriga.core.modelo.TransferObject;

/**
 * Clase para el manejo de los diferentes roles asociados con la visibilidad de las pantallas
 * administrativas.
 */
public class RolSIR implements TransferObject 
{

	private String idRol; // pk
	private String descripcion;
	private String nombre;
	private List pantallas = new ArrayList(); // contains RolPantalla  inverse RolPantalla.rol
        private static final long serialVersionUID = 1L;
	/** Constructor por defecto  */
	public RolSIR() {
	}

	/** Retorna el identificador del rol  */
	public String getIdRol() {
		return idRol;
	}

	/** Cambia el identificador del rol  */
	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}

	/** Retorna la descripción del rol  */
	public String getDescripcion() {
		return descripcion;
	}

	/** Cambia la descripción del rol  */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/** Retorna el nombre del rol  */
	public String getNombre() {
		return nombre;
	}

	/** Cambia el nombre del rol  */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/** Retorna la lista pantallas  */
	public List getPantallas() {
		return Collections.unmodifiableList(pantallas);
	}

	/** Añade una pantalla a la lista pantallas  */
	public boolean addPantalla(RolPantalla newPantalla) {
		return pantallas.add(newPantalla);
	}

	/** Elimina una pantalla de la lista pantallas  */
	public boolean removePantalla(RolPantalla oldPantalla) {
		return pantallas.remove(oldPantalla);
	}
}

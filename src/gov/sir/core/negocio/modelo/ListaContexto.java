package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

public class ListaContexto implements TransferObject {

	private String nombre;
	private String descripcion;
	private String id;
	private static final long serialVersionUID = 1L;
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}

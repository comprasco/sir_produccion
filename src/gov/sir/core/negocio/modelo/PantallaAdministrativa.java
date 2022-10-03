package gov.sir.core.negocio.modelo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.auriga.core.modelo.TransferObject;

  /** Clase para el manejo de las pantallas administrativas utilizadas en la aplicación  */

public class PantallaAdministrativa implements TransferObject
 {

	private long idPantallaAdministrativa; // pk
	private String nombre;
	private String vista;
	private String pagina;
	private TipoPantalla tipoPantalla;
	private List roles = new ArrayList(); // contains RolPantalla  
        private static final long serialVersionUID = 1L;
	/** Constructor por defecto */
	public PantallaAdministrativa() {
	}

	/** Retorna el identificador de la pantalla administrativa  */
	public long getIdPantallaAdministrativa() {
		return idPantallaAdministrativa;
	}

	/** Cambia el identificador de la pantalla administrativa  */
	public void setIdPantallaAdministrativa(long idPantallaAdministrativa) {
		this.idPantallaAdministrativa = idPantallaAdministrativa;
	}

	/** Retorna el nombre de la pantalla administrativa (describe funcionalidad)   */
	public String getNombre() {
		return nombre;
	}

	/** Cambia el nombre de la pantalla administrativa (describe funcionalidad)   */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/** Retorna la vista asociada con la pantalla administrativa   */
	public String getVista() {
		return vista;
	}

	/** Cambia la vista asociada con la pantalla administrativa   */
	public void setVista(String vista) {
		this.vista = vista;
	}

	/** Retorna el identificador del tipo de pantalla administrativa   */
	public TipoPantalla getTipoPantalla() {
		return tipoPantalla;
	}

	/** Cambia el identificador del tipo de pantalla administrativa   */
	public void setTipoPantalla(TipoPantalla tipoPantalla) {
		this.tipoPantalla = tipoPantalla;
	}

	/** Retorna la pantalla asociada al mostrarse como un menu-item */
	public String getPagina() {
		return pagina;
	}

	/** Cambia la pantalla asociada al mostrarse como un menu-item */
	public void setPagina(String pagina) {
		this.pagina = pagina;
	}


	/** Retorna la lista roles */
	public List getRoles() {
		return Collections.unmodifiableList(roles);
	}

	/** Añade un rol a la lista roles */
	public boolean addRole(RolPantalla newRole) {
		return roles.add(newRole);
	}

	/** Elimina un rol a la lista roles */
	public boolean removeRole(RolPantalla oldRole) {
		return roles.remove(oldRole);
	}
}

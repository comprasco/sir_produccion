package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;


/**
 * Clase para el manejo de de las pantallas Administrativas que son visibles para los diferentes
 * roles de la aplicación
 */
public class RolPantalla implements TransferObject
{

	private long idPantallaAdministrativa; // pk
	private String idRol; // pk
	private PantallaAdministrativa pantalla; // inverse PantallaAdministrativa.roles
	private RolSIR rol; // inverse Rol.pantallas
        private static final long serialVersionUID = 1L;
	/** Constructor por defecto  */
	public RolPantalla() {
	}

	/** Retorna el identificador de la pantalla administrativa visible para el rol  */
	public long getIdPantallaAdministrativa() {
		return idPantallaAdministrativa;
	}

	/** Cambia el identificador de la pantalla administrativa visible para el rol  */
	public void setIdPantallaAdministrativa(long idPantallaAdministrativa) {
		this.idPantallaAdministrativa = idPantallaAdministrativa;
	}

	/** Retorna el identificador del rol  */
	public String getIdRol() {
		return idRol;
	}

	/** Cambia el identificador del rol  */
	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}

	/** Retorna el identificador de la pantalla administrativa  */
	public PantallaAdministrativa getPantalla() {
		return pantalla;
	}

	/** Modifica el identificador de la pantalla administrativa  */
	public void setPantalla(PantallaAdministrativa pantalla) {
		this.pantalla = pantalla;
		setIdPantallaAdministrativa(pantalla.getIdPantallaAdministrativa());
	}

	/** Retorna el identificador del rol  */
	public RolSIR getRol() {
		return rol;
	}

	/** Modifica el identificador del rol  */
	public void setRol(RolSIR rol) {
		this.rol = rol;
		setIdRol(rol.getIdRol());
	}
}

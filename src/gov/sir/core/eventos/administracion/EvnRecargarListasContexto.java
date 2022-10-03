package gov.sir.core.eventos.administracion;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Circulo;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;

public class EvnRecargarListasContexto extends EvnSIR {

	/**
	 * Constante que indica a la acción de negocio que debe construir la 
	 * lista de listas de contexto
	 */
	public static String CARGAR_LISTAS_CONTEXTO = "CARGAR_LISTAS_CONTEXTO";
	
	private Circulo circulo;

	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;

	private Estacion estacion;

	private Rol rol;

	public EvnRecargarListasContexto(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
	}

	public EvnRecargarListasContexto(Usuario usuario, String tipoEvento, List ListasARecargar) {
		super(usuario, tipoEvento);
	}

	public Circulo getCirculo() {
		return circulo;
	}

	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}

	public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
		return usuarioSIR;
	}

	public void setUsuarioSIR(gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		this.usuarioSIR = usuarioSIR;
	}

	public Estacion getEstacion() {
		return estacion;
	}

	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
}

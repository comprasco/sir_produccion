
package gov.sir.core.eventos.comun;

import java.util.Hashtable;
import java.util.List;

import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * @author mmunoz
 */
public class EvnSistema extends EvnSIR{

	/**Constante que identifica que el evento es para que la accion de negocio 
	 * inicie los servicios*/
	public static final String INIT_PARAMETROS="INIT_PARAMETROS";

	/**Constante que identifica que el evento es para que la accion de negocio 
	 * finalize los servicios*/	
	public static final String FINALIZAR_SERVICIOS="FINALIZAR_PARAMETROS";
	
	/**Constante que identifica que el evento es para que la accion de negocio 
	 * recarge las listas indicadas en el parámetro de entrada*/	
	public static final String RECARGAR_LISTAS="REGARGAR_LISTAS";
	
	/**Constante que identifica que el evento es para que la accion de negocio 
	 * construya la lista de listas de contexto.*/	
	public static final String CARGAR_LISTAS="CARGAR_LISTAS";
	
	private List listasARecargar;
	
	private Hashtable listas;

	/**
	 * Metodo constructor del evento
	 * @param usuario org.auriga.core.modelo.transferObjects.Usuario
	 * @param tipoEvento String
	 */
	public EvnSistema(Usuario usuario, String tipoEvento) {
		super(usuario,tipoEvento);
	}
	
	public EvnSistema(Usuario usuario, String tipoEvento, Hashtable listas) {
		super(usuario,tipoEvento);
		this.setListas(listas);
	}
	
	public EvnSistema(Usuario usuario, String tipoEvento, List listasARecargar) {
		super(usuario,tipoEvento);
		this.setListasARecargar(listasARecargar);
	}

	public Hashtable getListas() {
		return listas;
	}

	public void setListas(Hashtable listas) {
		this.listas = listas;
	}

	public List getListasARecargar() {
		return listasARecargar;
	}

	public void setListasARecargar(List listasARecargar) {
		this.listasARecargar = listasARecargar;
	}



}

package gov.sir.core.eventos.comun;

import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.negocio.modelo.Vereda;

/**
 * @author mmunoz
 */
public class EvnOficinas extends EvnSIR{
	
	/**Constante que identifica la accion de cargar las oficinas */
	public static final String CARGAR_OFICINAS = "CARGAR_OFICINAS";
        /**
         * @author Carlos Torres
         * @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        public static final String CARGAR_OFICINA = "CARGAR_OFICINA";
	
	/**Vereda de donde se van a sacar las oficinas */
	private Vereda vereda;
         /**
         * @author Carlos Torres
         * @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        private String idOficinaOrigen;

	/**
	 * Constructor del evento con parametros.
	 * @param usuario org.auriga.core.modelo.transferObjects.Usuario
	 * @param vereda gov.sir.core.negocio.modelo.Vereda
	 */
	public EvnOficinas(Usuario usuario,Vereda vereda) {
		super(usuario,EvnOficinas.CARGAR_OFICINAS);
		this.vereda = vereda;
	}
        
        /**
         * @author Carlos Torres
         * @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        public EvnOficinas(Usuario usuario,String idOficicina) {
		super(usuario,EvnOficinas.CARGAR_OFICINA);
		this.idOficinaOrigen = idOficicina;
	}
	/**
	 * Retorna la vereda del evento
	 * @return gov.sir.core.negocio.modelo.Vereda
	 */
	public Vereda getVereda() {
		return vereda;
	}
        
        /**
         * @author Carlos Torres
         * @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        public String getIdOficinaOrigen() {
            return idOficinaOrigen;
        }

        public void setIdOficinaOrigen(String idOficinaOrigen) {
            this.idOficinaOrigen = idOficinaOrigen;
        }
}

package gov.sir.core.eventos.comun;
 /**
 * @author Carlos Torres
 * @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
 */
import gov.sir.core.negocio.modelo.OficinaOrigen;
import java.util.Hashtable;

/**
 * @author mmunoz
 */
public class EvnRespOficinas extends EvnSIRRespuesta{
	
	/**Constante que identifica que el evento contiene la tabla con las oficinas */
	public static final String TABLA_OFICINAS = "TABLA_OFICINAS";
         /**
         * @author Carlos Torres
         * @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        public static final String OFICINA_ORIGEN ="OFICINA_ORIGEN";

	/**Tabla donde se guardan las oficinas de acuerdo a su tipo de oficina */
	private Hashtable mapa;
         /**
         * @author Carlos Torres
         * @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        private OficinaOrigen oficina;
	/**
	 * Constructor por parametros del evento
	 * @param oficinas Hashtable
	 */
	public EvnRespOficinas(Hashtable oficinas) {
		super(oficinas,EvnRespOficinas.TABLA_OFICINAS);
		this.mapa = oficinas;
	}
         /**
         * @author Carlos Torres
         * @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
	 * Constructor por parametros del evento
	 * @param oficinas Hashtable
	 */
	public EvnRespOficinas(OficinaOrigen oficina) {
		super(oficina,EvnRespOficinas.OFICINA_ORIGEN);
		this.oficina = oficina;
	}
	
	/**
	 * Retorna la Hashtable que contiene las oficinas
	 * @return Hashtable 
	 */
	public Hashtable getOficinas(){
		return mapa;
	}
        /**
         * @author Carlos Torres
         * @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        public OficinaOrigen getOficina() {
            return oficina;
        }
}

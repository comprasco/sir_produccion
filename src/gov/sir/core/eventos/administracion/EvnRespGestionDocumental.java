/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.sir.core.eventos.administracion;


import gov.sir.core.eventos.comun.EvnSIRRespuesta;

/**
 *
 * @author Ellery R
 */
public class EvnRespGestionDocumental extends EvnSIRRespuesta {




	/**Esta constante se utiliza  para identificar el evento de consulta satisfactoria de los usuarios del sistema */
	public static final String CONSULTA_USUARIOS_OK = "CONSULTA_USUARIOS_OK";

        public static final String MOSTRAR_CIRCULO_USUARIO_OK = "MOSTRAR_CIRCULO_USUARIO_OK";

        public static final String TURNOS_GESTION_DOCUMENTAL_OK = "TURNOS_GESTION_DOCUMENTAL_OK";

        public static final String TURNO_REENCOLAR = "TURNO_REENCOLAR";

        public static final String SELECCIONA_TURNOS_POR_FECHA = "SELECCIONA_TURNOS_POR_FECHA";

        public static final String DEPURAR_TURNOS_POR_FECHA = "DEPURAR_TURNOS_POR_FECHA";

        /**
	 * @param payload
	 */
	public EvnRespGestionDocumental(Object payload) {
		super(payload);
	}

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespGestionDocumental(Object payload, String tipoEvento) {
		super(payload, tipoEvento);
	}

	
}





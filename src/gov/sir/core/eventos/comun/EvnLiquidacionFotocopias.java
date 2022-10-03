package gov.sir.core.eventos.comun;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.Proceso;

/** NOT USED YET */

public class EvnLiquidacionFotocopias extends EvnLiquidacion {

	public EvnLiquidacionFotocopias(Usuario usuario) {
		super(usuario);
	}

        public EvnLiquidacionFotocopias(
                Usuario usuario,
                Liquidacion liquidacion,
                Proceso proceso,
                Estacion estacion,
                boolean habilitarPago,
                gov.sir.core.negocio.modelo.Usuario usuarioSIR) {

                  super(usuario,
                  liquidacion,
                  proceso,
                  estacion,
                  habilitarPago,
                  usuarioSIR);
	}

	public EvnLiquidacionFotocopias(
		Usuario usuario,
		Liquidacion liquidacion,
		Proceso proceso,
		Estacion estacion,
		boolean habilitarPago,
		gov.sir.core.negocio.modelo.Usuario usuarioSIR,
                String tipoEvento) {

               super(usuario,
               liquidacion,
               proceso,
               estacion,
               habilitarPago,
               usuarioSIR,
               tipoEvento);
	}

}

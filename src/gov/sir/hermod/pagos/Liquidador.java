/*
 * Liquidador.java
 * Clase que se encarga de obteber el valor que debe ser liquidado
 * por concepto de una solicitud.
 * Liquidador.java
 */

package gov.sir.hermod.pagos;

import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo;
import gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto;
import gov.sir.core.negocio.modelo.Tarifa;

import gov.sir.hermod.dao.HermodDAOFactory;

/**
 *
 * @author  mrios, mortiz, dlopez, dsalas
 */
public abstract class Liquidador {
    
    
    /**
     * Retorna una instancia de la subclase adecuada, dependiendo de
     * el tipo de <code>Liquidación</code> que deba generarse. 
     * @param liquidacion Un objeto de tipo <code>Liquidacion</code> sobre
     * el cual se desa obtener al liquidador correspondiente.
     * @return La instancia del liquidador correspondiente a la <code>
     * Liquidacion</code> recibida como parámetro. 
     * @throws <code>LiquidadorException</code>
     * @see gov.sir.hermod.pagos.LiquidadorCertificados
     * @see gov.sir.hermod.pagos.LiquidadorCertificadosMasivos
     * @see gov.sir.hermod.pagos.LiquidadorConsultas
     * @see gov.sir.hermod.pagos.LiquidadorCorrecciones
     * @see gov.sir.hermod.pagos.LiquidadorDevoluciones
     * @see gov.sir.hermod.pagos.LiquidadorFotocopia
     * @see gov.sir.hermod.pagos.LiquidadorRegistro
     * @see gov.sir.hermod.pagos.LiquidadorRepartoNotarial
     * @see gov.sir.hermod.pagos.LiquidadorRestitucion
     */
    public static Liquidador getLiquidador(Liquidacion liquidacion) throws LiquidadorException 
    {
       
        //Liquidación Turnos de Certificados.
        if ( liquidacion instanceof LiquidacionTurnoCertificado ){
            return new LiquidadorCertificados();
        }

		//Liquidación Turnos de Registro.
		if ( liquidacion instanceof LiquidacionTurnoRegistro ){
			return new LiquidadorRegistro();
		}
		        
        //Liquidación Turnos Consultas. 
		if ( liquidacion instanceof LiquidacionTurnoConsulta ){
			return new LiquidadorConsultas();
		}
		
		//Liquidación Turno Devoluciones.
		if ( liquidacion instanceof LiquidacionTurnoDevolucion ){
			return new LiquidadorDevoluciones();
		}
		
		//Liquidación Turno Fotocopias.
		if ( liquidacion instanceof LiquidacionTurnoFotocopia ){
			return new LiquidadorFotocopia();
		}
		
		//Liquidación Turno Correcciones
		if ( liquidacion instanceof LiquidacionTurnoCorreccion ){
			return new LiquidadorCorrecciones();
		}
		
		//Liquidacion Turno Reparto Notarial Minuta
		if ( liquidacion instanceof LiquidacionTurnoRepartoNotarial){
			return new LiquidadorRepartoNotarial ();
		}
		
        //Liquidacion Turno Restitución Reparto Notarial
		if ( liquidacion instanceof LiquidacionTurnoRestitucionReparto){
				return new LiquidadorRestitucion ();
			}

		//Liquidacion Turno Certificado Masivo
		if ( liquidacion instanceof LiquidacionTurnoCertificadoMasivo){
				return new LiquidadorCertificadosMasivos ();
			}
					
        throw new LiquidadorException(LiquidadorException.LIQUIDADOR_NO_OBTENIDO);
       
    }
           
    /**
     * Obtiene el valor de una tarifa, dado un tipo y un código. 
     * @param tipo El nombre del proceso, este valor está asociado con el
     * <code>OPLookupType</code>. 
     * @param codigo Nombre asociado a la obtención del valor a pagar, este valor
     * está asociado con el <code>OPLookupCode</code>
     * @return La tarifa, asociada a la combinación tipo-código, recibida
     * como parámetro.
     * @throws <code>LiquidadorException</code>
     */
    public static Tarifa getTarifa(String tipo, String codigo) throws LiquidadorException 
    {
        try {
            return HermodDAOFactory.getFactory().getTarifasDAO().getTarifa(tipo, codigo);
        } catch (Exception e){
            throw new LiquidadorException(LiquidadorException.VALOR_NO_OBTENIDO);
        }
    }
    
    
	/**
	* Obtiene el valor que debe ser liquidado por concepto de una solicitud, de
	* acuerdo al tipo de liquidación recibida. 
	* @param liquidacion Liquidacion antes de obtener el valor a pagar.
	* @return La liquidación con el valor que debe pagarse. 
	* @see gov.sir.core.negocio.modelo.Liquidacion
	* @throws <code>LiquidarException</code>
	*/
    public abstract Liquidacion liquidar(Liquidacion liquidacion) throws LiquidarException;
}
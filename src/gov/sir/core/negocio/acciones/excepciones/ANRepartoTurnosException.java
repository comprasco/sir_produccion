/*
 * Created on 28-mar-2005
 *
 */
package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;


/**
 * 
 * Excepcion que se arroja cuando ocurre cualquier problema 
 * en el proceso de reparto de turnos de registro.
 * 
 * @author dcantor
 *
 */
public class ANRepartoTurnosException extends EventoException {

    /**
     * @param string
     * @param fe
     */
    public ANRepartoTurnosException(String string, Throwable e) {
        super(string,e);
    }
}

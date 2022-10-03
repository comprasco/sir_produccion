/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 *
 * @author developer5
 */
public class TramiteSuspensionException extends EventoException {

    /**
     *
     */
    public TramiteSuspensionException() {
        super();
    }

    /**
     * @param arg0
     * @param arg1
     */
    public TramiteSuspensionException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * @param arg0
     */
    public TramiteSuspensionException(String arg0) {
        super(arg0);
    }

}

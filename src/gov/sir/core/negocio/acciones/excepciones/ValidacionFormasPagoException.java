/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.acciones.excepciones;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class ValidacionFormasPagoException extends ValidacionParametrosHTException {

    private static final long serialVersionUID = 1L;

    public ValidacionFormasPagoException() {
        super();
    }

    public ValidacionFormasPagoException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public ValidacionFormasPagoException(String arg0) {
        super(arg0);
    }

}

package gov.sir.core.negocio.acciones.excepciones;

import gov.sir.forseti.ForsetiException;

import java.util.Hashtable;

/**
 * @author jfrias
 *
 */
public class ErrorRevisionInicialException extends
        ValidacionParametrosHTException {

    /**  */
    public ErrorRevisionInicialException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /** @param arg0 */
    public ErrorRevisionInicialException(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /** @param arg0
     /** @param arg1 */
    public ErrorRevisionInicialException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

    /** @param e */
    public ErrorRevisionInicialException(ForsetiException e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

    /** @param ht */
    public ErrorRevisionInicialException(Hashtable ht) {
        super(ht);
        // TODO Auto-generated constructor stub
    }

}

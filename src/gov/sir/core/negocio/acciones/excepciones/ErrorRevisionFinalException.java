package gov.sir.core.negocio.acciones.excepciones;

import gov.sir.forseti.ForsetiException;

import java.util.Hashtable;

/**
 * @author jfrias
 *
 */
public class ErrorRevisionFinalException extends
        ValidacionParametrosHTException {

    /**  */
    public ErrorRevisionFinalException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /** @param arg0 */
    public ErrorRevisionFinalException(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /** @param arg0
     /** @param arg1 */
    public ErrorRevisionFinalException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

    /** @param e */
    public ErrorRevisionFinalException(ForsetiException e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

    /** @param ht */
    public ErrorRevisionFinalException(Hashtable ht) {
        super(ht);
        // TODO Auto-generated constructor stub
    }

}

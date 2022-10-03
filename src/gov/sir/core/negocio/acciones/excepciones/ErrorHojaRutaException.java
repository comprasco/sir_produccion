package gov.sir.core.negocio.acciones.excepciones;

import gov.sir.forseti.ForsetiException;

import java.util.Hashtable;

/**
 * @author jfrias
 *
 */
public class ErrorHojaRutaException extends ValidacionParametrosHTException {

    /**  */
    public ErrorHojaRutaException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /** @param arg0 */
    public ErrorHojaRutaException(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /** @param arg0
     /** @param arg1 */
    public ErrorHojaRutaException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

    /** @param e */
    public ErrorHojaRutaException(ForsetiException e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

    /** @param ht */
    public ErrorHojaRutaException(Hashtable ht) {
        super(ht);
        // TODO Auto-generated constructor stub
    }

}

package gov.sir.core.negocio.acciones.excepciones;

import gov.sir.forseti.ForsetiException;

import java.util.Hashtable;

/**
 * @author jfrias
 *
 */
public class ErrorHacerDefinitivoFolioException extends
        ValidacionParametrosHTException {

    /**  */
    public ErrorHacerDefinitivoFolioException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /** @param arg0 */
    public ErrorHacerDefinitivoFolioException(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /** @param arg0
     /** @param arg1 */
    public ErrorHacerDefinitivoFolioException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

    /** @param e */
    public ErrorHacerDefinitivoFolioException(ForsetiException e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

    /** @param ht */
    public ErrorHacerDefinitivoFolioException(Hashtable ht) {
        super(ht);
        // TODO Auto-generated constructor stub
    }

}

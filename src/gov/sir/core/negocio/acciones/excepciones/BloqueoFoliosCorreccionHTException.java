package gov.sir.core.negocio.acciones.excepciones;

import gov.sir.forseti.ForsetiException;

import java.util.Hashtable;

/**
 * @author jfrias
 *
 */
public class BloqueoFoliosCorreccionHTException extends
        ValidacionParametrosHTException {

    /**  */
    public BloqueoFoliosCorreccionHTException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /** @param arg0 */
    public BloqueoFoliosCorreccionHTException(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /** @param arg0
     /** @param arg1 */
    public BloqueoFoliosCorreccionHTException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

    /** @param e */
    public BloqueoFoliosCorreccionHTException(ForsetiException e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

    /** @param ht */
    public BloqueoFoliosCorreccionHTException(Hashtable ht) {
        super(ht);
        // TODO Auto-generated constructor stub
    }

}

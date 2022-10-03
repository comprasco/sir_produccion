package gov.sir.core.negocio.acciones.excepciones;



import org.auriga.smart.eventos.EventoException;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class CorreccionImpresionCertificadoException extends
        EventoException {
    public CorreccionImpresionCertificadoException() {
    }

    /**
     * @param arg0
     * @param arg1
     */
    public CorreccionImpresionCertificadoException(String arg0, Throwable arg1) {
            super(arg0, arg1);
    }

    /**
     * @param str
     */
    public CorreccionImpresionCertificadoException(String str) {
            super(str);
    }


}

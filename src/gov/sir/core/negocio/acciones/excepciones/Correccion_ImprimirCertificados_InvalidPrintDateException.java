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
public class Correccion_ImprimirCertificados_InvalidPrintDateException extends
        EventoException {
    public Correccion_ImprimirCertificados_InvalidPrintDateException() {
    }

    /**
     * @param arg0
     * @param arg1
     */
    public Correccion_ImprimirCertificados_InvalidPrintDateException(String arg0, Throwable arg1) {
            super(arg0, arg1);
    }

    /**
     * @param str
     */
    public Correccion_ImprimirCertificados_InvalidPrintDateException(String str) {
            super(str);
    }


}

package gov.sir.core.web.acciones.excepciones;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.auriga.smart.web.acciones.AccionWebException;


/**
 * Esta clase permite generar una lista de errores de validaci�n
 * que puede ser asociada a una vista espec�fica de tal forma que el
 * usuario pueda ver todos los errores de validaci�n ocurridos de forma
 * simul�nea.
 * @author eacosta
 */
public class MatriculaInvalidaException extends AccionWebException {
    /**
     * Lista de cadenas de texto con la descripci�n de los errore de
     * validaci�n.
     */
    private List errores = new Vector();

    /**
     * Constructor por omisi�n, sin par�metros.
     */
    public MatriculaInvalidaException() {
        super();
    }

    /**
     * Constructor por omisi�n, sin par�metros.
     */
    public MatriculaInvalidaException(String message) {
        super(message);
    }

    /**
     * Devuelve la lista de errores actual.
     * @return
     */
    public List getErrores() {
        return Collections.unmodifiableList(errores);
    }

    /**
     * Permite adicionar un String con la descripci�n de un error a la lista de
     * errores de validaci�n
     * @param newError
     * @return
     */
    public boolean addError(String newError) {
        return errores.add(newError);
    }
}

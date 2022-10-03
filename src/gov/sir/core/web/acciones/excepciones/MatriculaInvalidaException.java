package gov.sir.core.web.acciones.excepciones;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.auriga.smart.web.acciones.AccionWebException;


/**
 * Esta clase permite generar una lista de errores de validación
 * que puede ser asociada a una vista específica de tal forma que el
 * usuario pueda ver todos los errores de validación ocurridos de forma
 * simulánea.
 * @author eacosta
 */
public class MatriculaInvalidaException extends AccionWebException {
    /**
     * Lista de cadenas de texto con la descripción de los errore de
     * validación.
     */
    private List errores = new Vector();

    /**
     * Constructor por omisión, sin parámetros.
     */
    public MatriculaInvalidaException() {
        super();
    }

    /**
     * Constructor por omisión, sin parámetros.
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
     * Permite adicionar un String con la descripción de un error a la lista de
     * errores de validación
     * @param newError
     * @return
     */
    public boolean addError(String newError) {
        return errores.add(newError);
    }
}

package gov.sir.print.server;


/**
 *
 * @author  dcantor
 */
public class EstacionNoEncontradaException extends PrintJobsException {
    /**
     * Creates a new instance of <code>EstacionNotFoundException</code> 
     * without detail message.
     */
    public EstacionNoEncontradaException() {
    }

    /**
     * Constructs an instance of <code>EstacionNotFoundException</code> 
     * with the specified detail message.
     * @param msg the detail message.
     */
    public EstacionNoEncontradaException(String msg) {
        super(msg);
    }

    /**
     * Construct an instance of  <code>EstacionNotFoundException</code> 
     * with the specified Throwable
     * @param t
     */
    public EstacionNoEncontradaException(Throwable t) {
        super(t);
    }
}

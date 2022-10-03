package gov.sir.print.server;


/**
 *
 * @author  dcantor
 */
public class CommunicationException extends PrintJobsException {
    /**
     * Creates a new instance of <code>CommunicationException</code>
     * without detail message.
     */
    public CommunicationException() {
    }

    /**
     * Construct an instance of  <code>CommunicationException</code>
     * with the specified Throwable
     * @param t
     */
    public CommunicationException(Throwable t) {
        super(t);
    }

    /**
     * Constructs an instance of <code>CommunicationException</code>
     * with the specified detail message.
     * @param msg the detail message.
     */
    public CommunicationException(String msg) {
        super(msg);
    }
}

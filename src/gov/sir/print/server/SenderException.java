/*
 * SenderException.java
 *
 * Created on 15 de septiembre de 2004, 14:38
 */

package gov.sir.print.server;

/**
 *
 * @author  dcantor
 */
public class SenderException extends PrintJobsException {
    
    /**
     * Creates a new instance of <code>SenderException</code> without detail message.
     */
    public SenderException() {
    }
    
    
    /**
     * Constructs an instance of <code>SenderException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public SenderException(String msg) {
        super(msg);
    }
}
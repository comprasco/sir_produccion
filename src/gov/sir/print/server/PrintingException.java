/*
 * PrintingException.java
 *
 * Created on 15 de septiembre de 2004, 14:39
 */

package gov.sir.print.server;

/**
 *
 * @author  dcantor
 */
public class PrintingException extends PrintJobsException {
    
    /**
     * Creates a new instance of <code>PrintingException</code> without detail message.
     */
    public PrintingException() {
    }
    
    
    /**
     * Constructs an instance of <code>PrintingException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public PrintingException(String msg) {
        super(msg);
    }
}
package gov.sir.print.server;


/**
 *
 * @author  dcantor
 */
public class PrintJobsException extends java.lang.Exception {
	
	private int idImpresion = 0; 
	
    /**
     * Creates a new instance of <code>SIMException</code> 
     * without detail message.
     */
    public PrintJobsException() {
    }

    /**
     * Constructs an instance of <code>SIMException</code> 
     * with the specified detail message.
     * @param msg the detail message.
     */
    public PrintJobsException(String msg) {
        super(msg);
    }

    /**
     * Construct an instance of  <code>PrintJobsException</code>
     * with the specified Throwable
     * @param t
     */
    public PrintJobsException(Throwable t) {
        super(t.getMessage());
    }

    /**
     * Construct an instance of  <code>PrintJobsException</code>
     * with the specified Throwable and a message
     * @param msg
     * @param t
     */
    public PrintJobsException(String msg, Throwable t) {
        super(msg, t);
    }
    
    
	/**
	 * Construct an instance of  <code>PrintJobsException</code>
	 * with the specified Throwable
	 * @param t
	 * @param idImpresion
	 */
	public PrintJobsException(Throwable t, int idImpresion) {
		super(t.getMessage());
		this.idImpresion = idImpresion;
	}

	/**
	 * Construct an instance of  <code>PrintJobsException</code>
	 * with the specified Throwable and a message
	 * @param msg
	 * @param idImpresion
	 * @param t
	 */
	public PrintJobsException(String msg, Throwable t, int idImpresion) {
		super(msg, t);
		this.idImpresion = idImpresion;
	}    
    
	/**
	 * @return
	 */
	public int getIdImpresion() {
		return idImpresion;
	}

}

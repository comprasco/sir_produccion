/*
 * Created on 09-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.forseti.dao;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


/**
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DAOException extends Exception {
    /**
     *
     */
    private List errores = new ArrayList();
	private Hashtable hashErrores = new Hashtable();

    /** Constructor por defecto */
    public DAOException() {
        super();
    }

    /**
     * @param arg0
     */
    public DAOException(String arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     */
    public DAOException(Throwable arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     */
    public DAOException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public List getErrores() {
        return errores;
    }
    
    public void setErrores(List errores){
    	this.errores = errores;
    }

    public boolean addError(String newError) {
        return errores.add(newError);
    }

    public boolean removeError(String oldError) {
        return errores.remove(oldError);
    }
    
    public boolean appendErrores(List errores1){
		return this.errores.addAll(errores1);
    }
    
	
	/**
	 * @return
	 */
	public Hashtable getHashErrores() {
		return hashErrores;
	}

	/**
	 * @param hashtable
	 */
	public void setHashErrores(Hashtable hashtable) {
		hashErrores = hashtable;
	}
    	
}

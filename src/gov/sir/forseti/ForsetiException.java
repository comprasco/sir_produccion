/*
 * Created on 15-jul-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.forseti;

import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;


/**
 * @author fceballos
 *
 */
public class ForsetiException extends Exception {
    public static String USUARIO_INVALIDO = "Usuario Inválido";
    public static String USUARIO_BLOQUEADO = "Usuario Bloqueado";
    public static String ERROR_CONEXION_DIRECTORIO = "Imposible obtener conexión con el Directorio";
    public static String ERROR_AUDITORIA = "No fue posible agregar auditoría";
    public static String LISTADO_ESTACIONES_NO_OBTENIDO = "No fue posible obtener el listado de estaciones";
    public static String LOGOUT_NO_EFECTUADO = "No fue posible efectuar log out";
    public static String ID_USUARIO_INVALIDO = "El usuario no existe";

	private List errores = new ArrayList();
	private Hashtable hashErrores = new Hashtable();
	
    /**
     *
     */
    public ForsetiException() {
        super();
    }

    /**
    * @param arg0
    */
    public ForsetiException(String arg0) {
        super(arg0);
    }

    /**
    * @param arg0
    */
    public ForsetiException(Throwable arg0) {
        super(arg0);
    }

    /**
    * @param arg0
    * @param arg1
    */
    public ForsetiException(String arg0, Throwable arg1) {
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

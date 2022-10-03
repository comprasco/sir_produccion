/*
 * Created on 21-jul-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.forseti.dao;


/**
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FactoryException extends Exception {
    public static String USUARIO_INVALIDO = "Usuario Inválido";
    public static String USUARIO_BLOQUEADO = "Usuario Bloqueado";
    public static String ERROR_CONEXION_DIRECTORIO = "Imposible obtener conexión con el Directorio";
    public static String ERROR_AUDITORIA = "No fue posible agregar auditoría";
    public static String LISTADO_ESTACIONES_NO_OBTENIDO = "No fue posible obtener el listado de estaciones";
    public static String LOGOUT_NO_EFECTUADO = "No fue posible efectuar log out";
    public static String ID_USUARIO_INVALIDO = "El usuario no existe";

    /**
     *
     */
    public FactoryException() {
        super();
    }

    /**
     * @param arg0
     */
    public FactoryException(String arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     */
    public FactoryException(Throwable arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     */
    public FactoryException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}

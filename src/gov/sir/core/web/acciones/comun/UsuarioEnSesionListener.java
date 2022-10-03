/*
 * Created on Jul 23, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.acciones.comun;

import java.io.Serializable;

import gov.sir.core.eventos.comun.EvnSeguridad;
import gov.sir.core.negocio.modelo.util.Log;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.web.ProcesadorEventosNegocioProxy;


/**
 * @author mmunoz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UsuarioEnSesionListener implements Serializable, HttpSessionBindingListener {
	
    private Usuario usuario;
    private gov.sir.core.negocio.modelo.Usuario usuarioNeg;

    public UsuarioEnSesionListener(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
        this.usuario = usuario;
        this.usuarioNeg = usuarioNeg;
    }

    public void valueBound(HttpSessionBindingEvent evento) {
        String nombre = evento.getName();
        Object valor = evento.getValue();
    }

    public void valueUnbound(HttpSessionBindingEvent evento) {
        String nombre = evento.getName();
        Object valor = evento.getValue();
        
        HttpSession sesion = evento.getSession();

        if (sesion != null) {
			if (usuario != null) {
                ProcesadorEventosNegocioProxy proxy = new ProcesadorEventosNegocioProxy();
                try {
                	
                    proxy.manejarEvento(new EvnSeguridad(usuario, usuarioNeg.getLoginID(),EvnSeguridad.LOGOUT));
                } catch (EventoException e) {
                	Log.getInstance().error(UsuarioEnSesionListener.class, e);
                }
            } 
        } 
    }
}

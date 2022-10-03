package gov.sir.core.eventos.comun;
import gov.sir.core.negocio.modelo.InfoUsuario;
import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.eventos.SoporteEvento;


/**
 * Evento base del Sistema de Informacion y Registro SIR.
 * @author I.Siglo21
 */
public abstract class EvnSIR extends SoporteEvento {
    
    /**
     * @Author Carlos Torres
     * @Mantis 13176
     * @Chaged Se agrega propiedad para envion de informacion de ip y maquina que realiza la operacion;
     */
    private gov.sir.core.negocio.modelo.InfoUsuario infoUsuario;
    
    /**
     * Constructor usado cuando el evento no requiere un tipo de evento.
     * Los tipos de evento le indican a la capa de negocio la accion
     * especifica que se debe llevar a cabo. Los tipos de evento permiten tambien
     * asignar permisos muy especificos a ciertos usuarios del sistema. El
     * nivel de seguridad es evento y tipo de evento.
     * @param usuario usuario que genera este evento
     */
    public EvnSIR(Usuario usuario){   
        super(usuario);
    }
    
    /**
     * Constructor utilizado cuando se requiere indicar el tipo de evento.
     * Los tipos de evento le indican a la capa de negocio la accion
     * especifica que se debe llevar a cabo. Los tipos de evento permiten tambien
     * asignar permisos muy especificos a ciertos usuarios del sistema. El
     * nivel de seguridad es evento y tipo de evento.
     * @param usuario el usuario que genera este evento
     * @param tipoEvento el tipo de evento
     */
    public EvnSIR(Usuario usuario, String tipoEvento) {
        super(usuario,tipoEvento);
    }
    
    
    
    /**
     * Devuelve el usuario que genero este evento
     * @return una instancia de <CODE>gov.sir.core.negocio.modelo.Usuario</CODE>
     * @see gov.sir.core.negocio.modelo.Usuario
     */
    public Usuario getUsuario(){
        return super.getUsuario();
    }
/**
     * @Author Carlos Torres
     * @Mantis 13176
     * @Chaged Metodo accesor a la propiedad infoUsuario;
     */
    public InfoUsuario getInfoUsuario() {
        return infoUsuario;
    }
/**
     * @Author Carlos Torres
     * @Mantis 13176
     * @Chaged Metodo accesor a la propiedad infoUsuario;
     */
    public void setInfoUsuario(InfoUsuario infoUsuario) {
        this.infoUsuario = infoUsuario;
    }
    
}

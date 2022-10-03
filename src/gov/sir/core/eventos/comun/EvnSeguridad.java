package gov.sir.core.eventos.comun;

import gov.sir.core.eventos.EventoInvalidoException;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.InfoUsuario;
import gov.sir.core.negocio.modelo.Proceso;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Envio de solicitudes de autenticación y autorización en el sistema, a la capa
 * de negocio
 *
 * @author I.Siglo21
 */
public class EvnSeguridad extends EvnSIR {

    /**
     * Constante que identifica que el usuario intenta identificarse para
     * ingresar al sistema
     */
    public static final String LOGIN = "LOGIN";

    /**
     * Constante que identifica que el usuario intenta ingresar como
     * administrador
     */
    public static final String INICIAR_ADMINISTRACION = "INICIAR_ADMINISTRACION";

    /**
     * Constante que identifica que el usuario va a salir del sistema
     */
    public static final String LOGOUT = "LOGOUT";

    /**
     * Constante que identifica que se desea obtener la lista de estaciones
     * asociados a un usuario
     */
    public static final String CONSULTAR_ESTACIONES = "CONSULTAR_ESTACIONES";

    /**
     * Constante que identifica que se desea obtener la lista de procesos
     * asociados a una estacion (rol)
     */
    public static final String CONSULTAR_PROCESOS = "CONSULTAR_PROCESOS";

    /**
     * Constante que identifica que se desea obtener la lista de fases de un
     * proceso
     */
    public static final String CONSULTAR_FASES = "CONSULTAR_FASES";

    /**
     * Constante que identifica que se desea obtener la lista de roles de un
     * usuario
     */
    public static final String CONSULTAR_ROLES = "CONSULTAR_ROLES";
    private String loginID;
    private String password;
    private Usuario usuario;
    private Rol rol;
    private Estacion estacion;
    private Proceso proceso;
    private Fase fase;
    private InfoUsuario infoUsuario;
    private String remoteAd;
    private Circulo circulo;
    private String uid;

    private boolean validarPerfilAdministrativo;

    /**
     * Usado cuando se solicita la lista de procesos a la capa de negocio.El
     * tipo de evento generado es CONSULTAR_PROCESOS
     *
     * @param usuario el usuario que genera este evento
     * @param estacion la estacion asociada a este usuario
     */
    public EvnSeguridad(Usuario usuario, Rol rol) {
        super(usuario, CONSULTAR_PROCESOS);
        this.rol = rol;
        this.proceso = null;
    }

    /**
     * Usado para consultar los procesos a los que puede acceder el usuario,
     * dependiendo del rol y la estación que escogió
     *
     * @param usuario Usuario del que se quieren saber sus procesos disponibles
     * @param rol Rol con el que se registró el usuario
     * @param estacion Estación que escogió el usuario
     * @param remote Dirección remota del cliente
     * @param TipoEvento Tipo de evento que se genera
     */
    public EvnSeguridad(Usuario usuario, Rol rol, Estacion estacion, String remote, String TipoEvento) {
        super(usuario, TipoEvento);
        this.rol = rol;
        this.estacion = estacion;
        this.remoteAd = remote;
    }

    /**
     * Usado para consultar las estaciones del usuario, cuando se registra con
     * un rol dado
     *
     * @param usuario Usuario del que se quieren obtener sus estaciones
     * @param rol Rol con el que se registró el usuario
     * @param TipoEvento Tipo de evento que se genera
     * @param remote Dirección remota del usuario
     */
    public EvnSeguridad(Usuario usuario, Rol rol, String TipoEvento, String remote) {
        super(usuario, TipoEvento);
        this.rol = rol;
        this.remoteAd = remote;
    }

    /**
     * Usado cuando se desea consultar las fases del usuario, dependiendo del
     * proceso y rol que escogió
     *
     * @param usuario Usuario del que se van a consultar sus fases
     * @param rol Rol que escogió el usuario
     * @param proceso Proceso que escogió el usuario
     */
    public EvnSeguridad(Usuario usuario, Rol rol, Proceso proceso) {
        super(usuario, CONSULTAR_FASES);
        this.rol = rol;
        this.proceso = proceso;
    }

    /**
     * Usado cuando se desea consultar las fases del usuario, dependiendo del
     * proceso y rol que escogió
     *
     * @param usuario Usuario del que se van a consultar sus fases
     * @param rol Rol que escogió el usuario
     * @param proceso Proceso que escogió el usuario
     */
    public EvnSeguridad(Usuario usuario, Rol rol, Proceso proceso, Fase fase) {
        super(usuario, CONSULTAR_FASES);
        this.rol = rol;
        this.proceso = proceso;
        this.fase = fase;
    }

    /**
     * Usado cuando se desea generar un evento del tipo LOGOUT. El resultado de
     * la operación es hacer logout del cliente en el sistema
     *
     * @param usuario Usuario que va a salir del sistema
     * @param loginId Identificador del usuario
     * @param TipoEvento Tipo del evento que se genera
     */
    public EvnSeguridad(Usuario usuario, String loginId, String TipoEvento) {
        super(usuario);
        if (TipoEvento.equals(EvnSeguridad.LOGOUT)) {
            this.setTipoEvento(TipoEvento);
            this.loginID = loginId;
        } else {
            throw new EventoInvalidoException("El evento es inválido");
        }
    }

    /**
     * Usado cuando se desea generar un evento del tipo LOGIN. Esta operacion
     * consiste en que el negocio anote en la auditoria la accion de cerrar la
     * sesion
     *
     * @param usuario el usuario que genera este evento
     * @param loginID el identificador unico del usuario
     */
    public EvnSeguridad(Usuario usuario, InfoUsuario infoCliente, String password, String TipoEvento) {
        super(usuario);
        this.infoUsuario = infoCliente;
        this.password = password;
        this.setTipoEvento(TipoEvento);

    }

    /**
     * Usado cuando se desea generar un evento del iniciar como administrador.
     *
     * @param usuario el usuario que genera este evento
     * @param infoUsuario
     */
    public EvnSeguridad(Usuario usuario, String TipoEvento) {
        super(usuario);
        this.setTipoEvento(TipoEvento);
    }

    /**
     * Usado para obtener la estacion
     *
     * @return la estacion asociada
     */
    public Estacion getEstacion() {
        return estacion;
    }

    /**
     * Usado para obtener el proceso
     *
     * @return el proceso asociado
     */
    public Proceso getProceso() {
        return proceso;
    }

    /**
     * Usado para obtener el id
     *
     * @return el id asociado
     */
    public String getID() {
        return loginID;
    }

    /**
     * Usado para obtener el uid
     *
     * @return el uid asociado
     */
    public String getUID() {
        return uid;
    }

    /**
     * Usado para obtener la información del cliente
     *
     * @return
     */
    public InfoUsuario getInfoUsuario() {
        return infoUsuario;
    }

    /**
     * Usado para obtener el rol
     *
     * @return
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Usado para obtener la contraseña del cliente
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Usado para obtener la dirección remota del cliente
     *
     * @return
     */
    public String getRemoteAd() {
        return remoteAd;
    }

    /**
     * @return
     */
    public boolean isValidarPerfilAdministrativo() {
        return validarPerfilAdministrativo;
    }

    /**
     * @param b
     */
    public void setValidarPerfilAdministrativo(boolean b) {
        validarPerfilAdministrativo = b;
    }

    /**
     * Establece el uid asociado al evento
     *
     * @param uid el uid asociado al evento
     */
    public void setUID(String uid) {
        this.uid = uid;
    }

    /**
     * @return
     */
    public Fase getFase() {
        return fase;
    }

    /**
     * @return
     */
    public Circulo getCirculo() {
        return circulo;
    }

}

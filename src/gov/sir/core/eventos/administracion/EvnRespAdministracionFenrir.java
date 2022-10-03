package gov.sir.core.eventos.administracion;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

/**
 * Evento de Respuesta de solicitudes a la capa de negocio relacionadas con la
 * administración de objetos de Fenrir
 *
 * @author jmendez
 */
public class EvnRespAdministracionFenrir extends EvnSIRRespuesta {

    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria de los usuarios del sistema
     */
    public static final String CONSULTA_USUARIOS_OK = "CONSULTA_USUARIOS_OK";

    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria de los usuarios del sistema por círculo
     */
    public static final String CONSULTA_USUARIOS_POR_CIRCULO_OK = "CONSULTA_USUARIOS_POR_CIRCULO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de una responsabilidad
     */
    public static final String RESPONSABILIDAD_CREAR_OK = "RESPONSABILIDAD_CREAR_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un rol
     */
    public static final String ROL_CREAR_OK = "ROL_CREAR_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un nivel
     */
    public static final String NIVEL_CREAR_OK = "NIVEL_CREAR_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de un nivel
     */
    public static final String NIVEL_ELIMINAR_OK = "NIVEL_ELIMINAR_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un usuario
     */
    public static final String USUARIO_CREAR_OK = "USUARIO_CREAR_OK";

    /**
     * Esta constante se utiliza para identificar el evento de cambio de clave
     * satisfactoria a un usuario
     */
    public static final String USUARIO_CAMBIO_CLAVE_OK = "USUARIO_CAMBIO_CLAVE_OK";

    /**
     * Esta constante se utiliza para identificar el evento habilitación
     * satisfactoria de un usuario
     */
    public static final String USUARIO_HABILITACION_OK = "USUARIO_HABILITACION_OK";

    /**
     * Esta constante se utiliza para definir la respuesta luego de actualizar
     * los datos del usuario
     */
    public static final String USUARIO_ACTUALIZACION_OK = "USUARIO_ACTUALIZACION_OK";

    /**
     * Constante que se utuliza para identificar el evento consultar rol para editarlo
     */
    public static final String CONSULTAR_ROL_EDITAR_OK = "CONSULTAR_ROL_EDITAR_OK";

    /**
     * Constante que se utiliza para identificar el evento edicion de rol
     */
    public static final String EDITAR_ROL_OK = "EDITAR_ROL_OK";

    public static final String CONSULTA_PERFIL_USUARIO_OK = "CONSULTA_PERFIL_USUARIO_OK";

    public static final String LISTA_ESTACIONES_ROL = "LISTA_ESTACIONES_ROL";

    public static final String LISTA_USUARIOS_ROL_ESTACION = "LISTA_USUARIOS_ROL_ESTACION";

    /**
     * Constante que se utiliza para identificar la lista de roles permitidos
     * para un administrador regional
     */
    public static final String LISTA_ADMINISTRADOR_REGIONAL_ROLES = "LISTA_ADMINISTRADOR_REGIONAL_ROLES";

    public static final String LISTA_ESTACIONES_SISTEMA = "LISTA_ESTACIONES_SISTEMA";

    public static final String LISTA_ESTACIONES_CIRCULO = "LISTA_ESTACIONES_CIRCULO";

    public static final String MOSTRAR_CIRCULO_USUARIO_OK = "MOSTRAR_CIRCULO_USUARIO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria de las justificaciones por usuario del sistema
     */
    public static final String CONSULTA_USUARIOS_JUSTIFICACIONES_OK = "CONSULTA_USUARIOS_JUSTIFICACIONES_OK";
    
    
    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria de los campos de captura por forma de pago
     */
    public static final String CONSULTA_CAMPOS_CAPTURA_OK = "CONSULTA_CAMPOS_CAPTURA_OK";
    
    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria de los campos de captura del sistema
     */
    public static final String CARGA_CAMPOS_CAPTURA_OK = "CARGA_CAMPOS_CAPTURA_OK";
    
    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de una forma de pago
     */
    public static final String FORMA_PAGO_CREAR_OK = "FORMA_PAGO_CREAR_OK";
    
    

    private List rolesAdministradorRegional = null;
    private List justificacionesPerfiles = null;
    private List tipoJustificaciones = null;

    /**
     * @param payload
     */
    public EvnRespAdministracionFenrir(Object payload) {
        super(payload);
    }

    /**
     * @param payload
     * @param tipoEvento
     */
    public EvnRespAdministracionFenrir(Object payload, String tipoEvento) {
        super(payload, tipoEvento);
    }

    public List getRolesAdministradorRegional() {
        return rolesAdministradorRegional;
    }

    public void setRolesAdministradorRegional(List rolesAdministradorRegional) {
        this.rolesAdministradorRegional = rolesAdministradorRegional;
    }

    public List getJustificacionesPerfiles() {
        return justificacionesPerfiles;
    }

    public void setJustificacionesPerfiles(List justificacionesPerfiles) {
        this.justificacionesPerfiles = justificacionesPerfiles;
    }
    
    public List getTipoJustificaciones() {
        return tipoJustificaciones;
    }

    public void setTipoJustificaciones(List tipoJustificaciones) {
        this.tipoJustificaciones = tipoJustificaciones;
    }
       

}

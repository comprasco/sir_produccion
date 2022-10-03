package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.negocio.modelo.constantes.CTipoPago;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWAdministracionFenrir;
import gov.sir.core.web.acciones.administracion.AWReportes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileUpload;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Control de Navegación para <code>AWAdministracionFenrir</code>
 *
 * @author jmendez
 *
 */
public class ControlNavegacionAdministracionFenrir implements ControlNavegacion {

    /**
     *
     */
    public ControlNavegacionAdministracionFenrir() {
        super();
    }

    /**
     * Prepara el procesamiento de la navegación.
     *
     * @param request
     */
    public void doStart(HttpServletRequest arg0) {
    }

    /**
     * Método que procesa la siguiente acción de navegación dentro del flujo de
     * pantallas
     *
     * @param request
     * @return nombre de la acción siguiente
     * @throws ControlNavegacionException
     */
    @Override
    public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
        //String accion = (String) request.getParameter(WebKeys.ACCION);

        HttpSession session = request.getSession();
        boolean isMultipart = FileUpload.isMultipartContent(request);
        String accion = "";

        if (isMultipart) {
//            return AWAdministracionFenrir.USUARIOS_CONSULTAR;
            String accionMultipart = (String) session.getAttribute(WebKeys.ACCION);
            System.out.println("ACCION EN SESSION MULTIPART: " + accionMultipart);
            if (accionMultipart != null) {
                if (accionMultipart.equals(AWAdministracionFenrir.USUARIOS_ACTUALIZAR_INFORMACION)) {
                    return AWAdministracionFenrir.USUARIOS_CONSULTAR;
                } else if (accionMultipart.equals(AWAdministracionFenrir.AGREGAR_ESTACION_ROL_USUARIO)) {
                    return AWAdministracionFenrir.EDITAR_RELACIONES;
                } else if (accionMultipart.equals(AWAdministracionFenrir.EDITAR_ROL_ESTACION)) {
                    return AWAdministracionFenrir.EDITAR_ROL_ESTACION;
                } else if (accionMultipart.equals(AWAdministracionFenrir.ELIMINAR_ROL_ESTACION_USUARIO)) {
                    return AWAdministracionFenrir.EDITAR_RELACIONES;
                } else if (accionMultipart.equals(AWAdministracionFenrir.ACTUALIZAR_ESTADO_REL_ROL_ESTACION)) {
                    return AWAdministracionFenrir.EDITAR_RELACIONES;
                } else if (accionMultipart.equals(AWAdministracionFenrir.CARGAR_ESTACIONES_ROL_CIRCULO)) {
                    return AWAdministracionFenrir.EDITAR_USUARIO_CON_RELACIONES;
                } else if (accionMultipart.equals(AWAdministracionFenrir.IR_AGREGAR_ESTACION_ROL)) {
                    return AWAdministracionFenrir.AGREGAR_ESTACION_ROL;
                } else if (accionMultipart.equals(AWAdministracionFenrir.REGRESAR_ESCOGER_USUARIO)) {
                    return AWAdministracionFenrir.USUARIOS_CIRCULO;
                } else if (accionMultipart.equals(AWAdministracionFenrir.FORM_JUST_ELIMINAR_ACT_INACT_PERFIL)) {
                    return AWAdministracionFenrir.EDITAR_RELACIONES;
                } else if (accionMultipart.equals(AWAdministracionFenrir.REGRESAR_PERFIL_INFOUSUARIO)) {
                    return AWAdministracionFenrir.EDITAR_RELACIONES;
                } else if (accionMultipart.equals(AWAdministracionFenrir.JUSTIFICACIONES_RANGO_FECHA)) {
                    return AWAdministracionFenrir.USUARIOS_MODIFICAR;
                } else if (accionMultipart.equals(AWAdministracionFenrir.JUSTIFICACIONES_RANGO_FECHA_PERFIL)) {
                    System.out.println("llego a control navegacion multiparte");
                    return AWAdministracionFenrir.EDITAR_RELACIONES;
                }

            } else {
                return null;
            }

        } else {
            accion = request.getParameter(WebKeys.ACCION);
        }

        if ((accion == null) || accion.equals("")) {
            throw new ControlNavegacionException("La accion enviada por la accion web no es valida");
        }

        if (accion.equals(AWAdministracionFenrir.USUARIOS_CONSULTAR)
                || accion.equals(AWAdministracionFenrir.USUARIOS_CONSULTAR_POR_CIRCULO)
                || accion.equals(AWAdministracionFenrir.USUARIOS_CREAR)
                || accion.equals(AWAdministracionFenrir.CANCELA_CREAR_USUARIO) //|| accion.equals(AWAdministracionFenrir.USUARIOS_DESHABILITAR)
                //|| accion.equals(AWAdministracionFenrir.USUARIOS_ACTUALIZAR_INFORMACION)
                //|| accion.equals(AWAdministracionFenrir.USUARIOS_HABILITAR)
                ) {
            return AWAdministracionFenrir.USUARIOS_CONSULTAR;
        } else if (accion.equals(AWAdministracionFenrir.USUARIOS_MODIFICAR)
                || accion.equals(AWAdministracionFenrir.USUARIOS_DESHABILITAR)
                || accion.equals(AWAdministracionFenrir.USUARIOS_HABILITAR)) {
            return AWAdministracionFenrir.USUARIOS_MODIFICAR;
        } else if (accion.equals(AWAdministracionFenrir.USUARIOS_SELECCIONAR)) {
            return AWAdministracionFenrir.USUARIOS_SELECCIONAR;
        } else if (accion.equals(AWAdministracionFenrir.USUARIOS_NUEVO)) {
            return AWAdministracionFenrir.USUARIOS_NUEVO;
        } else if (accion.equals(AWAdministracionFenrir.TERMINA)) {
            request.getSession().removeAttribute(CTipoPago.ID_TIPO_PAGO);
            return AWAdministracionFenrir.TERMINA;
        } else if (accion.equals(AWAdministracionFenrir.RESPONSABILIDAD_CREAR)) {
            return AWAdministracionFenrir.RESPONSABILIDAD_CREAR;
        } else if (accion.equals(AWAdministracionFenrir.ROL_CREAR)
                || accion.equals(AWAdministracionFenrir.VOLVER_EDITAR_ROL)) {
            return AWAdministracionFenrir.ROL_CREAR;
        } else if (accion.equals(AWAdministracionFenrir.NIVEL_CREAR)
                || accion.equals(AWAdministracionFenrir.NIVEL_ELIMINAR)) {
            return AWAdministracionFenrir.NIVEL_CREAR;
        } else if (accion.equals(AWAdministracionFenrir.HORARIO_CREAR)
                || accion.equals(AWAdministracionFenrir.HORARIO_ELIMINAR)) {
            return AWAdministracionFenrir.HORARIO_CREAR;
        } else if (accion.equals(AWAdministracionFenrir.USUARIOS_CAMBIAR_CLAVE)) {
            return AWAdministracionFenrir.USUARIOS_CAMBIAR_CLAVE;
        } else if (accion.equals(AWAdministracionFenrir.CONSULTAR_ROL_EDITAR)) {
            return AWAdministracionFenrir.CONSULTAR_ROL_EDITAR;
        } else if (accion.equals(AWAdministracionFenrir.EDITAR_ROL)) {
            return AWAdministracionFenrir.ROL_CREAR;
        } else if (accion.equals(AWAdministracionFenrir.USUARIOS_CIRCULO)) {
            return AWAdministracionFenrir.IR_ELEGIR_USUARIO_PERFILES;
        } else if (accion.equals(AWAdministracionFenrir.REGRESAR_ESCOGER_USUARIO)) {
            return AWAdministracionFenrir.USUARIOS_CIRCULO;
        } else if (accion.equals(AWAdministracionFenrir.REGRESAR_ESCOGER_CIRCULO)) {
            String adminNal = (String) request.getSession().getAttribute(WebKeys.ROL_ADMINISTRADOR_NACIONAL);
            if (adminNal.equals("false")) {
                return AWAdministracionFenrir.TERMINA;
            } else {
                return AWAdministracionFenrir.ELEGIR_CIRCULOS;
            }
        } else if (accion.equals(AWAdministracionFenrir.EDITAR_RELACIONES)) {
            return AWAdministracionFenrir.EDITAR_RELACIONES;
        } else if (accion.equals(AWAdministracionFenrir.EDITAR_USUARIO_CON_RELACIONES)) {
            return AWAdministracionFenrir.EDITAR_USUARIO_CON_RELACIONES;
        } else if (accion.equals(AWAdministracionFenrir.CARGAR_ESTACIONES_ROL)) {
            return AWAdministracionFenrir.EDITAR_RELACIONES;
        } else if (accion.equals(AWAdministracionFenrir.CARGAR_ESTACIONES_ROL_CIRCULO)) {
            return AWAdministracionFenrir.EDITAR_USUARIO_CON_RELACIONES;
        } else if (accion.equals(AWAdministracionFenrir.AGREGAR_ESTACION_ROL_USUARIO)) {
            return AWAdministracionFenrir.EDITAR_RELACIONES;
        } /*else if (accion.equals(AWAdministracionFenrir.ELIMINAR_ROL_ESTACION_USUARIO)) {
            return AWAdministracionFenrir.EDITAR_RELACIONES;
        } */ else if (accion.equals(AWAdministracionFenrir.ACTUALIZAR_ESTADO_REL_ROL_ESTACION)) {
            return AWAdministracionFenrir.EDITAR_RELACIONES;
        } else if (accion.equals(AWAdministracionFenrir.EDITAR_ROL_ESTACION)) {
            return AWAdministracionFenrir.EDITAR_ROL_ESTACION;
        } else if (accion.equals(AWAdministracionFenrir.AGREGAR_NUEVO_USUARIO_ROL_ESTACION)) {
            return AWAdministracionFenrir.EDITAR_ROL_ESTACION;
        } else if (accion.equals(AWAdministracionFenrir.ELIMINAR_USUARIO_ROL_ESTACION_ACTUAL)) {
            return AWAdministracionFenrir.EDITAR_ROL_ESTACION;
        } else if (accion.equals(AWAdministracionFenrir.REGRESAR_PERFIL_INFOUSUARIO)) {
            return AWAdministracionFenrir.EDITAR_RELACIONES;
        } else if (accion.equals(AWAdministracionFenrir.IR_AGREGAR_ESTACION_ROL)) {
            return AWAdministracionFenrir.AGREGAR_ESTACION_ROL;
        } else if (accion.equals(AWAdministracionFenrir.CARGAR_ESTACIONES_ROL_AGREGAR)) {
            return AWAdministracionFenrir.AGREGAR_ESTACION_ROL;
        } else if (accion.equals(AWAdministracionFenrir.AGREGAR_ESTACION_ROL)) {
            return AWAdministracionFenrir.AGREGAR_ESTACION_ROL;
        } else if (accion.equals(AWAdministracionFenrir.ELIMINAR_ESTACION_ROL)) {
            return AWAdministracionFenrir.AGREGAR_ESTACION_ROL;
        } else if (accion.equals(AWAdministracionFenrir.REGRESAR_AGREGAR_ESTACION_ROL)) {
            return AWAdministracionFenrir.EDITAR_RELACIONES;
        } else if (accion.equals(AWAdministracionFenrir.IR_ELEGIR_USUARIO_PERFILES)) {
            return AWAdministracionFenrir.IR_ELEGIR_USUARIO_PERFILES;
        } else if (accion.equals(AWAdministracionFenrir.MOSTRAR_CIRCULO_USUARIO)) {
            return AWAdministracionFenrir.MOSTRAR_CIRCULO_USUARIO;
        } else if (accion.equals(AWAdministracionFenrir.CONSULTAR_CAMPOS_CAPTURA)
                || accion.equals(AWAdministracionFenrir.ACTUALIZAR_ESTADO_CAMPOS_CAPTURA)) {
            //request.getSession().removeAttribute(CTipoPago.NOMBRE_TIPO_PAGO);            
            return AWAdministracionFenrir.FORMAS_PAGO;
        } else if (accion.equals(AWAdministracionFenrir.FORMAS_PAGO_CREAR)) {
            return AWAdministracionFenrir.FORMAS_PAGO_CREAR;
        } else if (accion.equals(AWAdministracionFenrir.CANCELA_CREAR_FORMA_PAGO)) {
            request.getSession().removeAttribute(CTipoPago.ID_TIPO_PAGO);
            return AWAdministracionFenrir.FORMAS_PAGO;
        } else if (accion.equals(AWAdministracionFenrir.CREAR_FORMA_PAGO)) {
            request.getSession().removeAttribute(CTipoPago.ID_TIPO_PAGO);
            return AWAdministracionFenrir.FORMAS_PAGO;
        }
        

        return null;
    }

    /**
     * Finalización de la navegación
     *
     * @param request
     * @see
     * org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
     */
    public void doEnd(HttpServletRequest arg0) {

    }

}

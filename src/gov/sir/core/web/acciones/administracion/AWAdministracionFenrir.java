package gov.sir.core.web.acciones.administracion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gov.sir.core.eventos.administracion.EvnAdministracionFenrir;
import gov.sir.core.eventos.administracion.EvnRespAdministracionFenrir;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.FormaPagoCampos;
import gov.sir.core.negocio.modelo.JustificaAdm;
import gov.sir.core.negocio.modelo.TipoPago;
import gov.sir.core.negocio.modelo.UsuarioCirculo;
import gov.sir.core.negocio.modelo.constantes.CCirculo;
import gov.sir.core.negocio.modelo.constantes.CEstacion;
import gov.sir.core.negocio.modelo.constantes.CNivel;
import gov.sir.core.negocio.modelo.constantes.CResponsabilidad;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.core.negocio.modelo.constantes.CUsuario;
import gov.sir.core.negocio.modelo.constantes.CJustificaAdm;
import gov.sir.core.negocio.modelo.constantes.CArchivosJustifica;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CTipoPago;
import gov.sir.core.negocio.modelo.util.CamposCapturaEstados;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.CargarArchivoExcepcion;
import gov.sir.core.web.acciones.excepciones.UsuarioPerfilInvalidoException;
import gov.sir.core.web.acciones.excepciones.ValidacionAdicionEstacionRolUsuarioException;
import gov.sir.core.web.acciones.excepciones.ValidacionCambioClaveException;
import gov.sir.core.web.acciones.excepciones.ValidacionCirculoPerfilesException;
import gov.sir.core.web.acciones.excepciones.ValidacionConsultaUsuariosException;
import gov.sir.core.web.acciones.excepciones.ValidacionCreacionUsuarioException;
import gov.sir.core.web.acciones.excepciones.ValidacionFormasPagoException;
import gov.sir.core.web.acciones.excepciones.ValidacionNivelException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosAgregarEstacionRol;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosPerfilException;
import gov.sir.core.web.acciones.excepciones.ValidacionRolEdicionException;
import gov.sir.core.web.acciones.excepciones.ValidacionRolException;
import gov.sir.core.web.helpers.comun.ElementoLista;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FilenameUtils;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Nivel;
import org.auriga.core.modelo.transferObjects.RelRolEstacion;
import org.auriga.core.modelo.transferObjects.RelUsuRol;
import org.auriga.core.modelo.transferObjects.RelUsuRolEst;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * Acción Web encargada de manejar solicitudes para generar eventos provenientes
 * de solicitudes realizadas a través del protocolo HTTP para administración de
 * objetos de Fenrir
 *
 * @author jmendez
 */
public class AWAdministracionFenrir extends SoporteAccionWeb {

    /**
     * Constante que identifica la acción de consultar los usuarios del sistema
     */
    public static final String USUARIOS_CONSULTAR = "USUARIOS_CONSULTAR";

    /**
     * Constante que identifica la acción de consultar los usuarios del sistema
     * por círculo
     */
    public static final String USUARIOS_CONSULTAR_POR_CIRCULO = "USUARIOS_CONSULTAR_POR_CIRCULO";

    /**
     * Constante que identifica la acción de crar usuarios en el sistema
     */
    public static final String USUARIOS_CREAR = "USUARIOS_CREAR";

    /**
     * Constante que identifica la acción de editar usuarios del sistema
     */
    public static final String USUARIOS_EDITAR = "USUARIOS_EDITAR";

    /**
     * Constante que identifica la acción de seleccionar un usuario
     */
    public static final String USUARIOS_SELECCIONAR = "USUARIOS_SELECCIONAR";

    /**
     * Constante que identifica la acción para configurar las pantallas
     * destinadas a crar un nuevo usuario
     */
    public static final String USUARIOS_NUEVO = "USUARIOS_NUEVO";

    /**
     * Constante que identifica la acción de cambio de clave para un usuario
     */
    public static final String USUARIOS_CAMBIAR_CLAVE = "USUARIOS_CAMBIAR_CLAVE";

    /**
     * Constante que identifica la acción de habilitación de un usuario en el
     * sistema
     */
    public static final String USUARIOS_HABILITAR = "USUARIOS_HABILITAR";

    /**
     * Constante que identifica la acción de habilitar la opción de edición de
     * usuarios
     */
    public static final String USUARIOS_MODIFICAR = "USUARIOS_MODIFICAR";

    /**
     * Constante que identifica la acción de habilitar la opción de guardar los
     * datos modificados
     */
    public static final String USUARIOS_ACTUALIZAR_INFORMACION = "USUARIOS_ACTUALIZAR_INFORMACION";

    /**
     * Constante que identifica la acción de deshabilitación de un usuario en el
     * sistema
     */
    public static final String USUARIOS_DESHABILITAR = "USUARIOS_DESHABILITAR";

    /**
     * Constante que identifica la acción de crar usuarios en el sistema
     */
    public static final String RESPONSABILIDAD_CREAR = "RESPONSABILIDAD_CREAR";

    /**
     * Constante que identifica la acción de crear un rol en el sistema
     */
    public static final String ROL_CREAR = "ROL_CREAR";

    /**
     * Constante que identifica la acción de crear un nivel en el sistema
     */
    public static final String NIVEL_CREAR = "NIVEL_CREAR";

    /**
     * Constante que identifica la acción de eliminar un nivel en el sistema
     */
    public static final String NIVEL_ELIMINAR = "NIVEL_ELIMINAR";

    /**
     * Constante que identifica la acción de crear un horario en el sistema
     */
    public static final String HORARIO_CREAR = "HORARIO_CREAR";

    /**
     * Constante que identifica la acción de eliminar un horario en el sistema
     */
    public static final String HORARIO_ELIMINAR = "HORARIO_ELIMINAR";

    /**
     * Constante que identifica la acción de cancelar la creación de un usuario
     * en el sistema
     */
    public static final String CANCELA_CREAR_USUARIO = "CANCELA_CREAR_USUARIO";

    /**
     * Constante que identifica la acción de terminar la utilización de los
     * servicios de la acción WEB (Para limpiar la sesión y redirigir a la
     * página principal de páginas administrativas
     */
    public static final String TERMINA = "TERMINA";

    /**
     * Constante que identifica la acción de consultar los roles existentes en
     * el sistema
     */
    public static final String ROLES_CONSULTAR = "ROLES_CONSULTAR";

    /**
     * Constante que identifican la variable de sesión para almacenar la lista
     * de respuesta a la consulta de usuarios
     */
    public static final String LISTA_USUARIOS = "LISTA_USUARIOS";

    /**
     * Constante que identifica las acción de mostrar el circulo correspondiente
     * al administrador del sistema
     */
    public static final String MOSTRAR_CIRCULO_USUARIO = "MOSTRAR_CIRCULO_USUARIO";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * un Usuario seleccionado
     */
    public static final String USUARIO_SELECCIONADO = "USUARIO_SELECCIONADO";

    /**
     * Constante que identifica la acción para editar un Rol
     */
    public static final String EDITAR_ROL = "EDITAR_ROL";

    /**
     * Constante que identifica la acción para consultar un Rol para se editado
     */
    public static final String CONSULTAR_ROL_EDITAR = "CONSULTAR_ROL_EDITAR";

    /**
     * Constante que identifica la acción para regresar de la pantalla de
     * edición de rol
     */
    public static final String VOLVER_EDITAR_ROL = "VOLVER_EDITAR_ROL";

    /**
     * Constante que identifica la acción para consultar los usuarios de un
     * círculo
     */
    public static final String USUARIOS_CIRCULO = "USUARIOS_CIRCULO";

    /**
     * Constante que identifica la acción para consultar los círculos
     */
    public static final String CIRCULOS = "CIRCULOS";

    /**
     * Constante que identifica la acción para ir a la pantalla de edición de
     * relaciones entre estaciones, roles y usuarios
     */
    public static final String EDITAR_RELACIONES = "EDITAR_RELACIONES";

    /**
     * Constante que identifica el nombre de la variable de la sesión donde se
     * guarda la información del perfil del usuario
     */
    public static final String PERFIL_USUARIO = "PERFIL_USUARIO";

    /**
     * Constante que identifica la acción para editar la relación de un rol y
     * sus estaciones
     */
    public static final String EDITAR_ROL_ESTACION = "EDITAR_ROL_ESTACION";

    /**
     * Constante que identifica la acción para eliminar la relación de una
     * estación con el rol de un usuario
     */
    public static final String ELIMINAR_ROL_ESTACION_USUARIO = "ELIMINAR_ROL_ESTACION_USUARIO";

    /**
     * Constante que identifica la acción para cambiar el estado de la relación
     * de una estación con el rol de un usuario
     */
    public static final String ACTUALIZAR_ESTADO_REL_ROL_ESTACION = "ACTUALIZAR_ESTADO_REL_ROL_ESTACION";

    /**
     * Constante que identifica la variable de sesión donde se guardan las
     * estaciones de un rol
     */
    public static final String ESTACIONES_ROL = "ESTACIONES_ROL";

    /**
     * Constante que identifica la variable de sesión donde se guardan las
     * estaciones de un circulo
     */
    public static final String ESTACIONES_CIRCULO = "ESTACIONES_CIRCULO";

    /**
     * Constante que identifica la acción para cargar las estaciones de un rol
     */
    public static final String CARGAR_ESTACIONES_ROL = "CARGAR_ESTACIONES_ROL";

    /**
     * Constante que identifica la acción para cargar las estaciones de un rol
     */
    public static final String CARGAR_ESTACIONES_ROL_CIRCULO = "CARGAR_ESTACIONES_ROL_CIRCULO";

    /**
     * Constante que identifica la variable de sesión donde se guarda la lista
     * de roles de un usuario
     */
    public static final String LISTA_ROLES_PERFIL = "LISTA_ROLES_PERFIL";

    /**
     * Constante que identifica la variable de sesión donde se guarda la lista
     * de estaciones de un rol de un usuario
     */
    public static final String LISTA_ESTACIONES_PERFIL = "LISTA_ESTACIONES_PERFIL";

    /**
     * Constante que identifica la acción para agregar una estación a un rol de
     * un usuario
     */
    public static final String AGREGAR_ESTACION_ROL_USUARIO = "AGREGAR_ESTACION_ROL_USUARIO";

    /**
     * Constante que identifica la acción para agregar una estación a un rol
     */
    public static final String AGREGAR_ESTACION_ROL = "AGREGAR_ESTACION_ROL";

    /**
     * Constante que identifica la acción para eliminar una estación de un rol
     */
    public static final String ELIMINAR_ESTACION_ROL = "ELIMINAR_ESTACION_ROL";

    /**
     * Constante que identifica la variable de sesión para guardar la lista de
     * estaciones de un rol de un usuario
     */
    public static final String LISTA_USUARIOS_ROL_ESTACION = "LISTA_USUARIOS_ROL_ESTACION";

    /**
     * Constante que identifica la acción para ir a la pantalla para editar una
     * estación de un rol
     */
    public static final String ROL_EDITAR_ROL_ESTACION = "ROL_EDITAR_ROL_ESTACION";

    /**
     * Constante que identifica la variable de sesión donde se guarda la
     * estación del rol que se editó
     */
    public static final String ESTACION_EDITAR_ROL_ESTACION = "ESTACION_EDITAR_ROL_ESTACION";

    /**
     * Constante que identifica la variable de sesión donde se guarda la lista
     * de usuarios con un rol determinado
     */
    public static final String USUARIOS_CON_ROL = "USUARIOS_CON_ROL";

    /**
     * Constante que identifica la variable de sesión donde se guarda la lista
     * de usuarios sin un rol determinado
     */
    public static final String USUARIOS_SIN_ROL = "USUARIOS_SIN_ROL";

    /**
     * Constante que identifica la acción para agregar un usuario nuevo a una
     * estación de un rol
     */
    public static final String AGREGAR_NUEVO_USUARIO_ROL_ESTACION = "AGREGAR_NUEVO_USUARIO_ROL_ESTACION";

    /**
     * Constante que identifica la acción para eliminar un usuario de una
     * estación de un rol
     */
    public static final String ELIMINAR_USUARIO_ROL_ESTACION_ACTUAL = "ELIMINAR_USUARIO_ROL_ESTACION_ACTUAL";

    /**
     * Constante que identifica la acción para regresar a la pantalla de
     * información del perfil de un usuario
     */
    public static final String REGRESAR_PERFIL_INFOUSUARIO = "REGRESAR_PERFIL_INFOUSUARIO";

    /**
     * Constante que identifica la acción para regresar a la pantalla donde se
     * escogen los usuarios a modificar
     */
    public static final String REGRESAR_ESCOGER_USUARIO = "REGRESAR_ESCOGER_USUARIO";

    /**
     * Constante que identifica la acción para regresar a la pantalla donde se
     * escogen el círculo a modificar sus usuarios
     */
    public static final String REGRESAR_ESCOGER_CIRCULO = "REGRESAR_ESCOGER_CIRCULO";

    /**
     * Constante que identifica la acción para ir a la pantalla donde se agrega
     * una estación a un rol
     */
    public static final String IR_AGREGAR_ESTACION_ROL = "IR_AGREGAR_ESTACION_ROL";

    /**
     * Constante que identifica la variable donde se guardan las estaciones de
     * un rol para la pantalla de agregar estacion a rol
     */
    public static final String ESTACIONES_ROL_AGREGAR = "ESTACIONES_ROL_AGREGAR";

    /**
     * Constante que identifica la variable de sesión donde se guarda la lista
     * de roles de un perfil
     */
    public static final String LISTA_ROLES_PERFIL_AGREGAR = "LISTA_ROLES_PERFIL_AGREGAR";

    /**
     * Constante que identifica la variable de sesión donde se guarda la lista
     * de estacinoes de un perfil
     */
    public static final String LISTA_ESTACIONES_PERFIL_AGREGAR = "LISTA_ESTACIONES_PERFIL_AGREGAR";

    /**
     * Constante que identifica la acción para regresar en la pantalla de
     * agregar estaciones a roles
     */
    public static final String REGRESAR_AGREGAR_ESTACION_ROL = "REGRESAR_AGREGAR_ESTACION_ROL";

    /**
     * Constante que identifica la acción para cargar las estaciones de un rol
     * determinado
     */
    public static final String CARGAR_ESTACIONES_ROL_AGREGAR = "CARGAR_ESTACIONES_ROL_AGREGAR";

    /**
     * Constante que identifica la acción para cargar un usaurio con todos sus
     * roles y estaciones
     */
    public static final String EDITAR_USUARIO_CON_RELACIONES = "EDITAR_USUARIO_CON_RELACIONES";

    public static final String ADMIN_PERFILES_PARAM_PCIRCULONOMBRE = "P_CIRCULO_ID";

    /**
     * Constante que identifica la acción para ir a la pantalla donde se agrega
     * una estación a un rol
     */
    public static final String IR_ELEGIR_USUARIO_PERFILES = "IR_ELEGIR_USUARIO_PERFILES";

    /**
     * Constante que identifica la acción para ir a la pantalla donde se agrega
     * una estación a un rol
     */
    public static final String ELEGIR_CIRCULOS = "ELEGIR_CIRCULOS";

    /**
     * Constante que identifica que el usuario es administrador nacional
     */
    public static final String CIRCULOS_ADMINISTRADOR_NACIONAL = "CIRCULOS_ADMINISTRADOR_NACIONAL";

    /**
     * Constante que identifica la variable de sesión donde se guarda la lista
     * de roles permitidos a asignar de un administrador regional
     */
    public static final String LISTA_ADMINISTRADOR_REGIONAL_ROLES = "LISTA_ADMINISTRADOR_REGIONAL_ROLES";

    /**
     * Ruta donde se almacenaran los archivos de justificaciones para las
     * novedades
     */
    public String RUTA_DESTINO_ARCHIVO_JUSTIFICACION = gov.sir.fenrir.FenrirProperties.getInstancia().getProperty(gov.sir.fenrir.FenrirProperties.RUTA_DESTINO_ARCHIVO_JUSTIFICACION);

    /**
     * Constante que identifican la variable de sesión para almacenar la lista
     * de respuesta a la consulta de justificaciones por usuario
     */
    public static final String LISTA_JUSTIFICACIONES_USUARIOS = "LISTA_JUSTIFICACIONES_USUARIOS";

    /**
     * Constante que identifican la variable de sesión para almacenar la lista
     * de respuesta a la consulta de tipos de justificaciones
     */
    public static final String LISTA_TIPOS_JUSTIFICACIONES = "LISTA_TIPOS_JUSTIFICACIONES";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una lista de novedades al editar activar o inactivar usuario para ser
     * desplegados en un helper de combobox
     */
    //public static final String LISTA_NOVEDADES_POR_EVENTO = "LISTA_NOVEDADES_POR_EVENTO";
    /**
     * Constante que identifica la acción para cargar el formulario de
     * justificacion de eliminar, activar o inactivar rol (perfiles)
     */
    public static final String FORM_JUST_ELIMINAR_ACT_INACT_PERFIL = "FORM_JUST_ELIMINAR_ACT_INACT_PERFIL";

    public static final String REPORTE_NOVEDADES_X_USUARIO = "REPORTE_NOVEDADES_X_USUARIO";

    public static final String REPORTES_JASPER_SERVLET = "REPORTES_JASPER_SERVLET";

    public static final String JUSTIFICACIONES_RANGO_FECHA = "JUSTIFICACIONES_RANGO_FECHA";
    public static final String JUSTIFICACIONES_RANGO_FECHA_PERFIL = "JUSTIFICACIONES_RANGO_FECHA_PERFIL";

    public static final String CONSULTAR_CAMPOS_CAPTURA = "CONSULTAR_CAMPOS_CAPTURA";

    public static final String FORMAS_PAGO = "FORMAS_PAGO";

    public static final String LISTA_CAMPOS_CAPTURA = "LISTA_CAMPOS_CAPTURA";

    public static final String LISTA_CARGA_CAMPOS_CAPTURA = "LISTA_CARGA_CAMPOS_CAPTURA";

    public static final String ACTUALIZAR_ESTADO_CAMPOS_CAPTURA = "ACTUALIZAR_ESTADO_CAMPOS_CAPTURA";

    public static final String FORMAS_PAGO_CREAR = "FORMAS_PAGO_CREAR";

    public static final String CANCELA_CREAR_FORMA_PAGO = "CANCELA_CREAR_FORMA_PAGO";

    public static final String CREAR_FORMA_PAGO = "CREAR_FORMA_PAGO";

    /**
     *
     */
    public AWAdministracionFenrir() {
        super();
    }

    /**
     * Este método se encarga de procesar la solicitud del
     * <code>HttpServletRequest</code> de acuerdo al tipo de accion que tenga
     * como parámetro
     *
     * @param request
     * @return
     * @throws org.auriga.smart.web.acciones.AccionWebException
     */
    @Override
    public Evento perform(HttpServletRequest request) throws AccionWebException {

        //boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        boolean isMultipart = FileUpload.isMultipartContent(request);
        String accion = "";
        if (isMultipart) {
            //System.out.println("llego a la accion " + accion);
            //return resolverMultipart(request);
            return resolverMultipart(request);
        } else {
            accion = request.getParameter(WebKeys.ACCION).trim();
        }

        //String accion = request.getParameter(WebKeys.ACCION).trim();
        HttpSession session = request.getSession();
        
        System.out.println("<<<<<DNilson226 vlr accion:"+ accion +">>>>>");

        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion valida");
        } else if (accion.equals(AWAdministracionFenrir.USUARIOS_CONSULTAR)) {
            return consultarUsuarios(request);
        } else if (accion.equals(AWAdministracionFenrir.USUARIOS_CONSULTAR_POR_CIRCULO)) {
            return consultarUsuariosPorCirculo(request);
        } else if (accion.equals(AWAdministracionFenrir.USUARIOS_NUEVO)) {
            return verificarCirculo(request);
        } else if (accion.equals(AWAdministracionFenrir.USUARIOS_CREAR)) {
            return crearUsuario(request);
        } else if (accion.equals(AWAdministracionFenrir.USUARIOS_SELECCIONAR)) {
            return seleccionaUsuario(request);
        } else if (accion.equals(AWAdministracionFenrir.USUARIOS_CAMBIAR_CLAVE)) {
            return cambiarClaveUsuario(request);
        } /*else if (accion.equals(AWAdministracionFenrir.USUARIOS_HABILITAR)) {
            //return actualizarDatosUsuario(request, true);
            return habilitarUsuario(request, true);
        } else if (accion.equals(AWAdministracionFenrir.USUARIOS_DESHABILITAR)) {
            return habilitarUsuario(request, false);
            //return actualizarDatosUsuario(request, true);
        } */ else if (accion.equals(AWAdministracionFenrir.USUARIOS_HABILITAR)
                || accion.equals(AWAdministracionFenrir.USUARIOS_DESHABILITAR)) {
            return habilitarDeshabilitarUsuario(request);
        } else if (accion.equals(AWAdministracionFenrir.USUARIOS_MODIFICAR)) {
            return modificarUsuario(request, false);
        } else if (accion.equals(AWAdministracionFenrir.USUARIOS_ACTUALIZAR_INFORMACION)) {
            //return actualizarDatosUsuario(request, false);
        } else if (accion.equals(AWAdministracionFenrir.ROL_CREAR)) {
            return crearRol(request);
        } else if (accion.equals(AWAdministracionFenrir.NIVEL_CREAR)) {
            return crearNivel(request);
        } else if (accion.equals(AWAdministracionFenrir.NIVEL_ELIMINAR)) {
            return eliminarNivel(request);
        } else if (accion.equals(AWAdministracionFenrir.CANCELA_CREAR_USUARIO)) {
            limpiarUsuarioDeSesion(request);
            return null;
        } else if (accion.equals(AWAdministracionFenrir.TERMINA)) {
            return limpiarSesion(request);
        } else if (accion.equals(AWAdministracionFenrir.CONSULTAR_ROL_EDITAR)) {
            return consultarEditarRol(request);
        } else if (accion.equals(AWAdministracionFenrir.EDITAR_ROL)) {
            return editarRol(request);
        } else if (accion.equals(AWAdministracionFenrir.VOLVER_EDITAR_ROL)) {
            return limpiarRolSesion(request);
        } else if (accion.equals(AWAdministracionFenrir.USUARIOS_CIRCULO)) {
            return consultarUsuariosPorCirculo(request);
        } else if (accion.equals(AWAdministracionFenrir.EDITAR_RELACIONES)) {
            return cargarInformacionUsuario(request);
        } else if (accion.equals(AWAdministracionFenrir.EDITAR_USUARIO_CON_RELACIONES)) {

            return cargarInformacionUsuarioConRelaciones(request);
        } else if (accion.equals(AWAdministracionFenrir.CARGAR_ESTACIONES_ROL)) {
            return cargarEstacionesRol(request);
        } else if (accion.equals(AWAdministracionFenrir.CARGAR_ESTACIONES_ROL_CIRCULO)) {
            return cargarEstacionesRolCirculo(request);
        } else if (accion.equals(AWAdministracionFenrir.EDITAR_ROL_ESTACION)) {
            System.out.println("llego a EDITAR_ROL_ESTACION sin multipart");
            return editarRolEstacion(request);
        } //        else if (accion.equals(AWAdministracionFenrir.ELIMINAR_ROL_ESTACION_USUARIO)) {
        //            return eliminarRolEstacionUsuario(request);
        //        } 
        //        else if (accion.equals(AWAdministracionFenrir.ACTUALIZAR_ESTADO_REL_ROL_ESTACION)) {
        //            return actualizarEstadoRelRolEstacionUsuario(request);
        //        } 
        else if (accion.equals(AWAdministracionFenrir.AGREGAR_ESTACION_ROL_USUARIO)) {
            //return agregarEstacionRolUsuario(request);
        } else if (accion.equals(AWAdministracionFenrir.AGREGAR_ESTACION_ROL)) {
            return agregarEstacionRol(request);
        } else if (accion.equals(AWAdministracionFenrir.ELIMINAR_ESTACION_ROL)) {
            return eliminarEstacionRol(request);
        } else if (accion.equals(AWAdministracionFenrir.AGREGAR_NUEVO_USUARIO_ROL_ESTACION)) {
            return agregarNuevoUsuarioRolEstacion(request);
        } else if (accion.equals(AWAdministracionFenrir.ELIMINAR_USUARIO_ROL_ESTACION_ACTUAL)) {
            return eliminarUsuarioRolEstacionActual(request);
        } else if (accion.equals(AWAdministracionFenrir.REGRESAR_PERFIL_INFOUSUARIO)) {
            System.out.println("Llego a la accion sin multipart");
            return regresarPerfilInfoUsuario(request);
        } else if (accion.equals(AWAdministracionFenrir.REGRESAR_ESCOGER_USUARIO)) {
            return regresarEscogerUsuario(request);
        } else if (accion.equals(AWAdministracionFenrir.REGRESAR_ESCOGER_CIRCULO)) {
            return regresarEscogerCirculo(request);
        } else if (accion.equals(AWAdministracionFenrir.IR_AGREGAR_ESTACION_ROL)) {
            return irAgregarEstacionRol(request);
        } else if (accion.equals(AWAdministracionFenrir.IR_ELEGIR_USUARIO_PERFILES)) {
            return irElegirUsuarioPerfiles(request);
        } else if (accion.equals(AWAdministracionFenrir.CARGAR_ESTACIONES_ROL_AGREGAR)) {
            return cargarEstacionesRolAgregar(request);
        } else if (accion.equals(AWAdministracionFenrir.REGRESAR_AGREGAR_ESTACION_ROL)) {
            return regresarAgregarEstacionRol(request);
        } else if (accion.equals(AWAdministracionFenrir.MOSTRAR_CIRCULO_USUARIO)) {
            return mostrarCirculoUsuario(request);
        } else if (accion.equals(AWAdministracionFenrir.CONSULTAR_CAMPOS_CAPTURA)) {
            return consultarCamposCaptura(request);
        } else if (accion.equals(AWAdministracionFenrir.ACTUALIZAR_ESTADO_CAMPOS_CAPTURA)) {
            return actualizarCamposCaptura(request);
        } else if (accion.equals(AWAdministracionFenrir.FORMAS_PAGO_CREAR)) {
            return formCrearFormaPago(request);
        } else if (accion.equals(AWAdministracionFenrir.CREAR_FORMA_PAGO)) {
            return crearFormaPago(request);
        } else if (accion.equals(AWAdministracionFenrir.CANCELA_CREAR_FORMA_PAGO)) {
            session.removeAttribute(AWAdministracionFenrir.LISTA_CAMPOS_CAPTURA);
            session.removeAttribute(AWAdministracionFenrir.LISTA_CARGA_CAMPOS_CAPTURA);
            return null;
        }

        return null;
    }

    private void do_Reporte_Novedades_Usuario(HttpServletRequest request) {
        System.out.println("llego a reportear en excel");
    }

    private Evento regresarAgregarEstacionRol(HttpServletRequest request) {
        request.getSession().removeAttribute(AWAdministracionFenrir.ESTACIONES_ROL);
        request.getSession().removeAttribute(AWAdministracionFenrir.LISTA_ROLES_PERFIL_AGREGAR);
        request.getSession().removeAttribute(AWAdministracionFenrir.LISTA_ESTACIONES_PERFIL_AGREGAR);
        request.getSession().removeAttribute(AWAdministracionFenrir.LISTA_ROLES_PERFIL);
        request.getSession().removeAttribute(AWAdministracionFenrir.LISTA_ESTACIONES_PERFIL);
        request.getSession().removeAttribute(AWAdministracionFenrir.ESTACIONES_ROL);
        return null;
    }

    private Evento cargarEstacionesRolAgregar(HttpServletRequest request) throws AccionWebException {
        String idRol = request.getParameter(AWAdministracionFenrir.LISTA_ROLES_PERFIL_AGREGAR);
        if (idRol == null || idRol.equals(WebKeys.SIN_SELECCIONAR)) {
            throw new ValidacionParametrosAgregarEstacionRol("Debe seleccionar un rol válido");
        }

        request.getSession().setAttribute(AWAdministracionFenrir.LISTA_ROLES_PERFIL_AGREGAR, idRol);
        Rol rol = new Rol();
        rol.setRolId(idRol);
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionFenrir evento = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.CARGAR_ESTACIONES_ROL_CIRCULO);
        evento.setRol(rol);
        evento.setCirculo(circulo);
        return evento;
    }

    private Evento regresarEscogerUsuario(HttpServletRequest request) {

        request.getSession().removeAttribute(AWAdministracionFenrir.ROL_EDITAR_ROL_ESTACION);
        request.getSession().removeAttribute(AWAdministracionFenrir.ESTACION_EDITAR_ROL_ESTACION);
        request.getSession().removeAttribute(AWAdministracionFenrir.ESTACIONES_ROL);
        request.getSession().removeAttribute(AWAdministracionFenrir.PERFIL_USUARIO);
        request.getSession().removeAttribute(AWAdministracionFenrir.LISTA_USUARIOS_ROL_ESTACION);
        return null;
    }

    private Evento regresarEscogerCirculo(HttpServletRequest request) {
        request.getSession().setAttribute(WebKeys.CIRCULO, request.getSession().getAttribute(WebKeys.CIRCULO_PERFILES));
        request.getSession().removeAttribute(AWAdministracionFenrir.LISTA_USUARIOS);
        return null;
    }

    private Evento irAgregarEstacionRol(HttpServletRequest request) {
        request.getSession().removeAttribute(AWAdministracionFenrir.ESTACIONES_ROL);
        Circulo circulo = new Circulo();
        circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionFenrir evento = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.CARGAR_ESTACIONES_CIRCULO);
        evento.setCirculo(circulo);
        return evento;
    }

    private Evento irElegirUsuarioPerfiles(HttpServletRequest request) throws AccionWebException {
        String idCirculo = request.getParameter(AWAdministracionFenrir.ADMIN_PERFILES_PARAM_PCIRCULONOMBRE);

        if (idCirculo == null || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            throw new ValidacionCirculoPerfilesException("Debe seleccionar un círculo válido");
        }

        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        request.getSession().setAttribute(WebKeys.CIRCULO_PERFILES, request.getSession().getAttribute(WebKeys.CIRCULO));
        request.getSession().setAttribute(WebKeys.CIRCULO, circulo);

        /*
		Usuario usuarioSes = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnAdministracionFenrir evento=new EvnAdministracionFenrir(usuarioSes,EvnAdministracionFenrir.CONSULTAR_PERFILES_USUARIOS_CIRCULO);
		evento.setCirculo(circulo);
		return evento;
         */
        return null;

    }

    private Evento regresarPerfilInfoUsuario(HttpServletRequest request) {
        request.getSession().removeAttribute(AWAdministracionFenrir.ROL_EDITAR_ROL_ESTACION);
        request.getSession().removeAttribute(AWAdministracionFenrir.ESTACION_EDITAR_ROL_ESTACION);
        request.getSession().removeAttribute(AWAdministracionFenrir.LISTA_USUARIOS_ROL_ESTACION);

        System.out.println("TIPO JUSTIFICACION REGRESAR PERFIL: " + request.getSession().getAttribute(CUsuario.TIPO_JUSTIFICACION));
        request.getSession().removeAttribute(AWAdministracionFenrir.LISTA_TIPOS_JUSTIFICACIONES);

        Usuario usuarioPerfil = (Usuario) request.getSession().getAttribute(AWAdministracionFenrir.PERFIL_USUARIO);

        Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionFenrir evento = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.CONSULTAR_USUARIO_CON_RELACIONES);
        evento.setNombreUsuario(usuarioPerfil.getUsuarioId());
        evento.setTipoJust((String) request.getSession().getAttribute(CUsuario.TIPO_JUSTIFICACION));
        return evento;
    }

    private Evento eliminarUsuarioRolEstacionActual(HttpServletRequest request) {
        String idUsuario = request.getParameter(CUsuario.ID_USUARIO);
        Rol rol = (Rol) request.getSession().getAttribute(AWAdministracionFenrir.ROL_EDITAR_ROL_ESTACION);
        Estacion estacion = (Estacion) request.getSession().getAttribute(AWAdministracionFenrir.ESTACION_EDITAR_ROL_ESTACION);
        Usuario usuarioAgregar = new Usuario();
        usuarioAgregar.setUsuarioId(idUsuario);
        Usuario usuarioSes = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionFenrir evento = new EvnAdministracionFenrir(usuarioSes, EvnAdministracionFenrir.ELIMINAR_USUARIO_ROL_ESTACION_ACTUAL);
        evento.setEstacion(estacion);
        evento.setRol(rol);
        evento.setUsuarioPerfil(usuarioAgregar);
        /**
         * @Author Carlos Torres
         * @Mantis 13176
         * @Chaged
         */
        gov.sir.core.negocio.modelo.Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioSes.getUsuarioId(), String.valueOf(userNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new java.util.Date(request.getSession().getCreationTime()));
        evento.setInfoUsuario(infoUsuario);

        return evento;
    }

    private Evento agregarNuevoUsuarioRolEstacion(HttpServletRequest request) {
        String idUsuario = request.getParameter(AWAdministracionFenrir.USUARIOS_SIN_ROL);
        Rol rol = (Rol) request.getSession().getAttribute(AWAdministracionFenrir.ROL_EDITAR_ROL_ESTACION);
        Estacion estacion = (Estacion) request.getSession().getAttribute(AWAdministracionFenrir.ESTACION_EDITAR_ROL_ESTACION);
        Usuario usuarioAgregar = new Usuario();
        usuarioAgregar.setUsuarioId(idUsuario);
        Usuario usuarioSes = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionFenrir evento = new EvnAdministracionFenrir(usuarioSes, EvnAdministracionFenrir.AGREGAR_NUEVO_USUARIO_ROL_ESTACION);
        evento.setEstacion(estacion);
        evento.setRol(rol);
        evento.setUsuarioPerfil(usuarioAgregar);
        /**
         * @Author Carlos Torres
         * @Mantis 13176
         * @Chaged
         */
        gov.sir.core.negocio.modelo.Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioSes.getUsuarioId(), String.valueOf(userNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new java.util.Date(request.getSession().getCreationTime()));
        evento.setInfoUsuario(infoUsuario);

        return evento;
    }

    private Evento eliminarEstacionRol(HttpServletRequest request) {
        String idRol = request.getParameter(AWAdministracionFenrir.LISTA_ROLES_PERFIL_AGREGAR);
        Rol rol = new Rol();
        rol.setRolId(idRol);
        String idEstacion = request.getParameter(WebKeys.ESTACION);
        Estacion estacion = new Estacion();
        estacion.setEstacionId(idEstacion);
        Usuario usuarioSes = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = new Circulo();
        circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        EvnAdministracionFenrir evento = new EvnAdministracionFenrir(usuarioSes, EvnAdministracionFenrir.ELIMINAR_ROL_ESTACION);
        evento.setRol(rol);
        evento.setEstacion(estacion);
        evento.setCirculo(circulo);
        /**
         * @Author Carlos Torres
         * @Mantis 13176
         * @Chaged ;
         */
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioNeg.getUsername(), String.valueOf(usuarioNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new java.util.Date(request.getSession().getCreationTime()));
        evento.setInfoUsuario(infoUsuario);
        return evento;
    }

    private Evento agregarEstacionRol(HttpServletRequest request) throws AccionWebException {
        String idRol = request.getParameter(AWAdministracionFenrir.LISTA_ROLES_PERFIL_AGREGAR);
        Rol rol = new Rol();
        rol.setRolId(idRol);
        // TODO
        String idEstacion = request.getParameter(AWAdministracionFenrir.LISTA_ESTACIONES_PERFIL_AGREGAR);
        if (idEstacion == null || idEstacion.equals(WebKeys.SIN_SELECCIONAR)) {
            throw new ValidacionParametrosAgregarEstacionRol("Debe seleccionar una estación válida");
        }

        List estRols = (List) request.getSession().getAttribute(AWAdministracionFenrir.ESTACIONES_ROL);
        Iterator itEstRols = estRols.iterator();
        while (itEstRols.hasNext()) {
            ElementoLista elem = (ElementoLista) itEstRols.next();
            if (elem.getId().equals(idEstacion)) {
                throw new ValidacionParametrosAgregarEstacionRol("Ese rol ya tiene asignada la estación escogida");
            }
        }
        Estacion estacion = new Estacion();
        estacion.setEstacionId(idEstacion);
        Usuario usuarioSes = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionFenrir evento = new EvnAdministracionFenrir(usuarioSes, EvnAdministracionFenrir.AGREGAR_ROL_ESTACION);
        evento.setRol(rol);
        evento.setEstacion(estacion);
        /**
         * @Author Carlos Torres
         * @Mantis 13176
         * @Chaged
         */
        gov.sir.core.negocio.modelo.Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioSes.getUsuarioId(), String.valueOf(userNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new java.util.Date(request.getSession().getCreationTime()));
        evento.setInfoUsuario(infoUsuario);
        return evento;
    }

    private Evento agregarEstacionRolUsuario(HttpServletRequest request, FileItem fileItem) throws AccionWebException {
        HttpSession session = request.getSession();
        ValidacionAdicionEstacionRolUsuarioException exception = new ValidacionAdicionEstacionRolUsuarioException();

        Usuario usuarioSes1 = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario userNeg1 = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        //String idRol = request.getParameter(AWAdministracionFenrir.LISTA_ROLES_PERFIL);
        String idRol = (String) session.getAttribute(AWAdministracionFenrir.LISTA_ROLES_PERFIL);
        Rol rol = new Rol();
        rol.setRolId(idRol);
        //String idEstacion = request.getParameter(AWAdministracionFenrir.LISTA_ESTACIONES_PERFIL);
        String idEstacion = (String) session.getAttribute(AWAdministracionFenrir.LISTA_ESTACIONES_PERFIL);
        if (idEstacion == null || idEstacion.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe escoger una estación válida");
        }
        Estacion estacion = new Estacion();
        estacion.setEstacionId(idEstacion);
        Usuario usuario = (Usuario) request.getSession().getAttribute(AWAdministracionFenrir.PERFIL_USUARIO);

        List usuRols = usuario.getRelUsuRols();
        for (Iterator iter = usuRols.iterator(); iter.hasNext();) {
            RelUsuRol usuRol = (RelUsuRol) iter.next();
            List usuRolEsts = usuRol.getRelUsuRolEsts();
            for (Iterator iterEst = usuRolEsts.iterator(); iterEst.hasNext();) {
                RelUsuRolEst usuRolEst = (RelUsuRolEst) iterEst.next();
                if (usuRolEst.getRolId().equals(idRol) && usuRolEst.getEstacionId().equals(idEstacion)) {
                    exception.addError("El usuario ya tiene esta estación en ese rol");
                }
                if (rol.getRolId().equals(CRoles.SIR_ROL_CALIFICADOR) && usuRolEst.getRolId().equals(idRol)) {
                    if (usuRolEsts.size() >= 1) {
                        exception.addError("El rol Calificador solo debe tener una estación "
                                + "la cual para este usuario debe ser únicamente \"X_" + usuario.getUsuarioId() + "\"");
                    } else if (!idEstacion.equals("X_" + usuario.getUsuarioId())) {
                        exception.addError("El rol Calificador solo debe tener una estación "
                                + "la cual para este usuario debe ser únicamente \"X_" + usuario.getUsuarioId() + "\"");
                    }
                }
            }
        }

        String tipoNovedad = (String) session.getAttribute(CJustificaAdm.TIP_ID_TIPO);
        if (tipoNovedad.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar un tipo novedad para la nota informativa");
        }

        String descripcionNovedad = (String) session.getAttribute(CJustificaAdm.ADM_DESCRIPCION);
        if ((descripcionNovedad == null) || (descripcionNovedad.trim().equals(""))) {
            exception.addError("Debe proporcionar una descripción para la nota informativa");
        }

        String descripcionTamano = (String) session.getAttribute(CJustificaAdm.ADM_MAX_LENGTH);
        if ((!descripcionTamano.equals("0")) || (descripcionTamano == null) || (descripcionTamano.trim().equals(""))) {
            exception.addError("La descripción debe tener minimo 20 carácteres");
        }

        String fileName = (String) session.getAttribute(CJustificaAdm.FILE_NAME);

        String fileSize = (String) session.getAttribute(CArchivosJustifica.FILE_SIZE);

        String fileFormat = (String) session.getAttribute(CArchivosJustifica.FILE_EXTENSION);

        String fileProceso = (String) session.getAttribute(CArchivosJustifica.NOMBRE_PROCESO);

        gov.sir.core.negocio.modelo.ArchivosJustifica archivo = null;
        if (!fileName.trim().equals("")) {
            archivo = new gov.sir.core.negocio.modelo.ArchivosJustifica();

            if ((fileSize == null) || (fileSize.trim().equals(""))) {
                exception.addError("No fue posible determinar el tamaño del archivo");
            }
            //EL TAM MAX DEL ARCHIVO DEBE IR EN UN ARCHIVO DE PROPIEDADES            
            if (Integer.parseInt(fileSize) > 2000000) {
                exception.addError("El tamaño del archivo supera el tamaño permitido (2MB)");
            }
            if ((fileFormat == null) || (fileFormat.trim().equals(""))) {
                exception.addError("No fue posible determinar la extension del archivo");
            }

            archivo.setJusNombreOriginal(fileName);
            archivo.setJusTipoArchivo(fileFormat);
            archivo.setJusTamanoArchivo(Integer.parseInt(fileSize));
            archivo.setJusNombreProceso(fileProceso);
            archivo.setJusRutaFisica(RUTA_DESTINO_ARCHIVO_JUSTIFICACION);
            archivo.setJusFechaDeSubida(new Date());
        }

        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }

        gov.sir.core.negocio.modelo.JustificaAdm justificaAdm = new gov.sir.core.negocio.modelo.JustificaAdm();
        justificaAdm.setAdmDescripcion(descripcionNovedad);
        justificaAdm.setAdmIpPcFuncLog(request.getRemoteHost());
        justificaAdm.setAdmFecha(new Date());

        gov.sir.core.negocio.modelo.JustificaTipos justificaTipos = new gov.sir.core.negocio.modelo.JustificaTipos();
        justificaTipos.setTipIdTipo(Integer.parseInt(tipoNovedad));

        Usuario usuarioSes = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        justificaAdm.setAdmUsuarioModifica(usuarioSes.getUsuarioId());

        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        justificaAdm.setAdmCirculoUsrModificado(circulo.getIdCirculo());

        session.removeAttribute(AWAdministracionFenrir.LISTA_TIPOS_JUSTIFICACIONES);
        System.out.println("TIPO JUSTIFICACION ADD ROL ESTACION USUARIO " + session.getAttribute(CUsuario.TIPO_JUSTIFICACION));

        EvnAdministracionFenrir evento = new EvnAdministracionFenrir(usuarioSes, EvnAdministracionFenrir.AGREGAR_ROL_ESTACION_USUARIO);
        evento.setRol(rol);
        evento.setEstacion(estacion);
        evento.setUsuarioPerfil(usuario);
        evento.setArchivosJustifica(archivo);
        evento.setJustificaAdm(justificaAdm);
        evento.setFileItem(fileItem);
        evento.setJustificaTipos(justificaTipos);
        evento.setTipoJust((String) session.getAttribute(CUsuario.TIPO_JUSTIFICACION));
        /**
         * @Author Carlos Torres
         * @Mantis 13176
         * @Chaged
         */
        gov.sir.core.negocio.modelo.Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioSes.getUsuarioId(), String.valueOf(userNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new java.util.Date(request.getSession().getCreationTime()));
        evento.setInfoUsuario(infoUsuario);
        return evento;
    }

    private Evento eliminarRolEstacionUsuario(HttpServletRequest request, FileItem fileItem) throws AccionWebException {
        HttpSession session = request.getSession();
        ValidacionAdicionEstacionRolUsuarioException exception = new ValidacionAdicionEstacionRolUsuarioException();
        String rolesEstaciones = (String) session.getAttribute(WebKeys.SESSION_ROL_ESTACION);
        List roles = listaRoles(rolesEstaciones);
        List estaciones = listaEstaciones(rolesEstaciones);

        String tipoNovedad = (String) session.getAttribute(CJustificaAdm.TIP_ID_TIPO);
        if (tipoNovedad.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar un tipo novedad para la nota informativa");
        }

        String descripcionNovedad = (String) session.getAttribute(CJustificaAdm.ADM_DESCRIPCION);
        if ((descripcionNovedad == null) || (descripcionNovedad.trim().equals(""))) {
            exception.addError("Debe proporcionar una descripción para la nota informativa");
        }
        System.out.println("DESCRIPCION NOVEDAD: " + descripcionNovedad);

        String descripcionTamano = (String) session.getAttribute(CJustificaAdm.ADM_MAX_LENGTH);
        if ((!descripcionTamano.equals("0")) || (descripcionTamano == null) || (descripcionTamano.trim().equals(""))) {
            exception.addError("La descripción debe tener minimo 20 carácteres");
        }

        String fileName = (String) session.getAttribute(CJustificaAdm.FILE_NAME);

        String fileSize = (String) session.getAttribute(CArchivosJustifica.FILE_SIZE);

        String fileFormat = (String) session.getAttribute(CArchivosJustifica.FILE_EXTENSION);

        String fileProceso = (String) session.getAttribute(CArchivosJustifica.NOMBRE_PROCESO);
        System.out.println("PERFIL PROCESO: " + fileProceso);

        gov.sir.core.negocio.modelo.ArchivosJustifica archivo = null;
        if (!fileName.trim().equals("")) {
            archivo = new gov.sir.core.negocio.modelo.ArchivosJustifica();

            if ((fileSize == null) || (fileSize.trim().equals(""))) {
                exception.addError("No fue posible determinar el tamaño del archivo");
            }
            //EL TAM MAX DEL ARCHIVO DEBE IR EN UN ARCHIVO DE PROPIEDADES
            System.out.println("TAMANO DEL ARCHIVO: " + fileSize);
            if (Integer.parseInt(fileSize) > 2000000) {
                exception.addError("El tamaño del archivo supera el tamaño permitido (2MB)");
            }
            if ((fileFormat == null) || (fileFormat.trim().equals(""))) {
                exception.addError("No fue posible determinar la extension del archivo");
            }

            archivo.setJusNombreOriginal(fileName);
            archivo.setJusTipoArchivo(fileFormat);
            archivo.setJusTamanoArchivo(Integer.parseInt(fileSize));
            archivo.setJusNombreProceso(fileProceso);
            archivo.setJusRutaFisica(RUTA_DESTINO_ARCHIVO_JUSTIFICACION);
            archivo.setJusFechaDeSubida(new Date());
        }

        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }

        gov.sir.core.negocio.modelo.JustificaAdm justificaAdm = new gov.sir.core.negocio.modelo.JustificaAdm();
        justificaAdm.setAdmDescripcion(descripcionNovedad);
        justificaAdm.setAdmIpPcFuncLog(request.getRemoteHost());
        justificaAdm.setAdmFecha(new Date());

        gov.sir.core.negocio.modelo.JustificaTipos justificaTipos = new gov.sir.core.negocio.modelo.JustificaTipos();
        justificaTipos.setTipIdTipo(Integer.parseInt(tipoNovedad));

        session.removeAttribute(AWAdministracionFenrir.LISTA_TIPOS_JUSTIFICACIONES);
        System.out.println("TIPO JUSTIFICACION ELIMINAR ROL ESTACION USUARIO " + session.getAttribute(CUsuario.TIPO_JUSTIFICACION));

        Usuario usuario = (Usuario) request.getSession().getAttribute(AWAdministracionFenrir.PERFIL_USUARIO);

        Usuario usuarioSes = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        justificaAdm.setAdmUsuarioModifica(usuarioSes.getUsuarioId());

        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        justificaAdm.setAdmCirculoUsrModificado(circulo.getIdCirculo());

        EvnAdministracionFenrir evento = new EvnAdministracionFenrir(usuarioSes, EvnAdministracionFenrir.ELIMINAR_ROL_ESTACION_USUARIO);
        /*evento.setRol(rol);
        evento.setEstacion(estacion);*/
        evento.setRoles(roles);
        evento.setEstaciones(estaciones);
        evento.setUsuarioPerfil(usuario);
        evento.setCirculo(circulo);
        evento.setArchivosJustifica(archivo);
        evento.setJustificaAdm(justificaAdm);
        evento.setFileItem(fileItem);
        evento.setJustificaTipos(justificaTipos);
        evento.setTipoJust((String) session.getAttribute(CUsuario.TIPO_JUSTIFICACION));
        /**
         * @Author Carlos Torres
         * @Mantis 13176
         * @Chaged
         */
        gov.sir.core.negocio.modelo.Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioSes.getUsuarioId(), String.valueOf(userNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new java.util.Date(request.getSession().getCreationTime()));
        evento.setInfoUsuario(infoUsuario);

        return evento;
    }

    private List listaRoles(String rolesString) {

        List roles = new ArrayList();

        String[] rolArray = rolesString.split(",");

        for (int i = 0; i < rolArray.length; i++) {
            String[] rolAdd = rolArray[i].split("&");
            System.out.println("ROL: " + rolAdd[0]);

            Rol rol = new Rol();
            rol.setRolId(rolAdd[0]);
            roles.add(rol);
        }

        return roles;
    }

    private List listaEstaciones(String estacionesString) {
        List estaciones = new ArrayList();
        String[] estacionArray = estacionesString.split(",");
        for (int i = 0; i < estacionArray.length; i++) {
            String[] estacionAdd = estacionArray[i].split("&");
            System.out.println("ESTACION: " + estacionAdd[1]);

            Estacion estacion = new Estacion();
            estacion.setEstacionId(estacionAdd[1]);
            estaciones.add(estacion);
        }
        return estaciones;
    }

    private List listaEstados(String estadosString) {
        List estados = new ArrayList();
        String[] estadoArray = estadosString.split(",");
        for (int i = 0; i < estadoArray.length; i++) {
            String[] estadoAdd = estadoArray[i].split("&");
            System.out.println("ESTADO: " + estadoAdd[2]);

            estados.add(estadoAdd[2]);
        }
        return estados;
    }

    private Evento cargarFormJustificacion(HttpServletRequest request) {
        HttpSession session = request.getSession();
        //System.out.println("TIPO JUSTIFICACION: " + session.getAttribute(CUsuario.TIPO_JUSTIFICACION));
        session.setAttribute(WebKeys.SESSION_ROL_ESTACION, session.getAttribute(WebKeys.ROL_ESTACION));
        session.setAttribute(WebKeys.SESSION_ROL_ESTACION_ESTADO, session.getAttribute(WebKeys.ROL_ESTACION_ESTADO));
        //session.setAttribute(CUsuario.TIPO_JUSTIFICACION, request.getParameter(CUsuario.TIPO_JUSTIFICACION));
        Usuario usuarioPerfil = (Usuario) request.getSession().getAttribute(AWAdministracionFenrir.PERFIL_USUARIO);
        //System.out.println("USUARIO PARA ELIMINAR ROLES: " + usuarioPerfil.getUsuarioId());

        session.removeAttribute(AWAdministracionFenrir.LISTA_TIPOS_JUSTIFICACIONES);

        System.out.println("TIPO JUSTIFICACION ROL ACT/INAC/ELIMINAR " + session.getAttribute(CUsuario.TIPO_JUSTIFICACION));

        Usuario usuarioSes = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        JustificaAdm justificaAdm = new JustificaAdm();
        EvnAdministracionFenrir evento = new EvnAdministracionFenrir(usuarioSes, EvnAdministracionFenrir.CONSULTAR_USUARIO_CON_RELACIONES);
        evento.setNombreUsuario(usuarioPerfil.getUsuarioId());
        evento.setJustificaAdm(justificaAdm);
        evento.setTipoJust((String) session.getAttribute(CUsuario.TIPO_JUSTIFICACION));

        return evento;
    }

    private Evento actualizarEstadoRelRolEstacionUsuario(HttpServletRequest request, FileItem fileItem) throws AccionWebException {

        HttpSession session = request.getSession();
        ValidacionAdicionEstacionRolUsuarioException exception = new ValidacionAdicionEstacionRolUsuarioException();
        String rolesEstacionesEstados = (String) session.getAttribute(WebKeys.SESSION_ROL_ESTACION_ESTADO);
        List roles = listaRoles(rolesEstacionesEstados);
        List estaciones = listaEstaciones(rolesEstacionesEstados);
        List estados = listaEstados(rolesEstacionesEstados);

        String tipoNovedad = (String) session.getAttribute(CJustificaAdm.TIP_ID_TIPO);
        if (tipoNovedad.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar un tipo novedad para la nota informativa");
        }

        String descripcionNovedad = (String) session.getAttribute(CJustificaAdm.ADM_DESCRIPCION);
        if ((descripcionNovedad == null) || (descripcionNovedad.trim().equals(""))) {
            exception.addError("Debe proporcionar una descripción para la nota informativa");
        }
        System.out.println("DESCRIPCION NOVEDAD: " + descripcionNovedad);

        String descripcionTamano = (String) session.getAttribute(CJustificaAdm.ADM_MAX_LENGTH);
        if ((!descripcionTamano.equals("0")) || (descripcionTamano == null) || (descripcionTamano.trim().equals(""))) {
            exception.addError("La descripción debe tener minimo 20 carácteres");
        }

        String fileName = (String) session.getAttribute(CJustificaAdm.FILE_NAME);

        String fileSize = (String) session.getAttribute(CArchivosJustifica.FILE_SIZE);

        String fileFormat = (String) session.getAttribute(CArchivosJustifica.FILE_EXTENSION);

        String fileProceso = (String) session.getAttribute(CArchivosJustifica.NOMBRE_PROCESO);
        System.out.println("PERFIL PROCESO: " + fileProceso);

        gov.sir.core.negocio.modelo.ArchivosJustifica archivo = null;
        if (!fileName.trim().equals("")) {
            archivo = new gov.sir.core.negocio.modelo.ArchivosJustifica();

            if ((fileSize == null) || (fileSize.trim().equals(""))) {
                exception.addError("No fue posible determinar el tamaño del archivo");
            }
            //EL TAM MAX DEL ARCHIVO DEBE IR EN UN ARCHIVO DE PROPIEDADES
            System.out.println("TAMANO DEL ARCHIVO: " + fileSize);
            if (Integer.parseInt(fileSize) > 2000000) {
                exception.addError("El tamaño del archivo supera el tamaño permitido (2MB)");
            }
            if ((fileFormat == null) || (fileFormat.trim().equals(""))) {
                exception.addError("No fue posible determinar la extension del archivo");
            }

            archivo.setJusNombreOriginal(fileName);
            archivo.setJusTipoArchivo(fileFormat);
            archivo.setJusTamanoArchivo(Integer.parseInt(fileSize));
            archivo.setJusNombreProceso(fileProceso);
            archivo.setJusRutaFisica(RUTA_DESTINO_ARCHIVO_JUSTIFICACION);
            archivo.setJusFechaDeSubida(new Date());
        }

        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }

        gov.sir.core.negocio.modelo.JustificaAdm justificaAdm = new gov.sir.core.negocio.modelo.JustificaAdm();
        justificaAdm.setAdmDescripcion(descripcionNovedad);
        justificaAdm.setAdmIpPcFuncLog(request.getRemoteHost());
        justificaAdm.setAdmFecha(new Date());

        gov.sir.core.negocio.modelo.JustificaTipos justificaTipos = new gov.sir.core.negocio.modelo.JustificaTipos();
        justificaTipos.setTipIdTipo(Integer.parseInt(tipoNovedad));

        session.removeAttribute(AWAdministracionFenrir.LISTA_TIPOS_JUSTIFICACIONES);
        System.out.println("TIPO JUSTIFICACION ACTUALIZAR ESTADO ROL ESTACION USUARIO " + session.getAttribute(CUsuario.TIPO_JUSTIFICACION));

        Usuario usuario = (Usuario) request.getSession().getAttribute(AWAdministracionFenrir.PERFIL_USUARIO);

        Usuario usuarioSes = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        justificaAdm.setAdmUsuarioModifica(usuarioSes.getUsuarioId());

        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        justificaAdm.setAdmCirculoUsrModificado(circulo.getIdCirculo());

        EvnAdministracionFenrir evento = new EvnAdministracionFenrir(usuarioSes, EvnAdministracionFenrir.ACTUALIZAR_ESTADO_REL_ROL_ESTACION);
        /*evento.setRol(rol);
        evento.setEstacion(estacion);*/
        evento.setUsuarioPerfil(usuario);
        evento.setCirculo(circulo);
        evento.setRoles(roles);
        evento.setEstaciones(estaciones);
        evento.setEstados(estados);
        evento.setArchivosJustifica(archivo);
        evento.setJustificaAdm(justificaAdm);
        evento.setFileItem(fileItem);
        evento.setJustificaTipos(justificaTipos);
        evento.setTipoJust((String) session.getAttribute(CUsuario.TIPO_JUSTIFICACION));
        //evento.setEstadoRolEstacion(estado);
        /**
         * @Author Carlos Torres
         * @Mantis 13176
         * @Chaged
         */
        gov.sir.core.negocio.modelo.Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioSes.getUsuarioId(), String.valueOf(userNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new java.util.Date(request.getSession().getCreationTime()));
        evento.setInfoUsuario(infoUsuario);

        return evento;
    }

    private Evento editarRolEstacion(HttpServletRequest request) {
        HttpSession session = request.getSession();
        //String idRol = request.getParameter(WebKeys.ROL);
        String idRol = (String) session.getAttribute(WebKeys.ROL_PERFIL);
        Rol rol = new Rol();
        rol.setRolId(idRol);
        //String idEstacion = request.getParameter(WebKeys.ESTACION);
        String idEstacion = (String) session.getAttribute(WebKeys.ESTACION);
        System.out.println("Estacion en session editarRolEstacion " + idEstacion);
        Estacion estacion = new Estacion();
        estacion.setEstacionId(idEstacion);

        Usuario usuarioSes = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionFenrir evento = new EvnAdministracionFenrir(usuarioSes, EvnAdministracionFenrir.CARGAR_USUARIOS_ROL_ESTACION);
        evento.setEstacion(estacion);
        evento.setRol(rol);
        /**
         * @Author Carlos Torres
         * @Mantis 13176
         * @Chaged
         */
        gov.sir.core.negocio.modelo.Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioSes.getUsuarioId(), String.valueOf(userNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new java.util.Date(request.getSession().getCreationTime()));
        evento.setInfoUsuario(infoUsuario);
        return evento;
    }

    private Evento cargarEstacionesRol(HttpServletRequest request) throws AccionWebException {
        String idRol = request.getParameter(AWAdministracionFenrir.LISTA_ROLES_PERFIL);
        if (idRol == null || idRol.equals(WebKeys.SIN_SELECCIONAR)) {
            throw new ValidacionParametrosPerfilException("Debe escoger un rol válido");
        }
        request.getSession().setAttribute(AWAdministracionFenrir.LISTA_ROLES_PERFIL, idRol);
        Rol rol = new Rol();
        rol.setRolId(idRol);
        Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionFenrir evento = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.CARGAR_ESTACIONES_ROL);
        evento.setRol(rol);
        return evento;
    }

    private Evento cargarEstacionesRolCirculo(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        //String idRol = request.getParameter(AWAdministracionFenrir.LISTA_ROLES_PERFIL);
        String idRol = (String) session.getAttribute(AWAdministracionFenrir.LISTA_ROLES_PERFIL);
        if (idRol == null || idRol.equals(WebKeys.SIN_SELECCIONAR)) {
            throw new ValidacionParametrosPerfilException("Debe escoger un rol válido");
        }
        request.getSession().setAttribute(AWAdministracionFenrir.LISTA_ROLES_PERFIL, idRol);
        Rol rol = new Rol();
        Circulo circulo = new Circulo();
        circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        rol.setRolId(idRol);
        Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionFenrir evento = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.CARGAR_ESTACIONES_ROL_CIRCULO);
        evento.setRol(rol);
        evento.setCirculo(circulo);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de mostrar
     * circulos segun el administrador
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con los datos del
     * administrador
     * @throws AccionWebException
     */
    private Evento mostrarCirculoUsuario(HttpServletRequest request)
            throws AccionWebException {

        //OBTENER VARIABLES DE LA SESSION
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        //CREAR EVENTO
        EvnAdministracionFenrir evento
                = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.MOSTRAR_CIRCULO_USUARIO);
        return evento;

    }

    private Evento cargarInformacionUsuario(HttpServletRequest request) throws AccionWebException {
        System.out.println("que pasa ome");
        String username = request.getParameter(CUsuario.LOGIN_USUARIO);
        System.out.println("que pasa?? " + username);
        if (username == null || username.equals(WebKeys.SIN_SELECCIONAR)) {
            throw new UsuarioPerfilInvalidoException("Debe escoger un usuario válido");
        }
        Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionFenrir evento = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.CONSULTAR_RELACIONES_USUARIO);
        evento.setNombreUsuario(username);
        return evento;
    }

    private Evento limpiarRolSesion(HttpServletRequest request) {
        request.getSession().removeAttribute(AWAdministracionFenrir.CONSULTAR_ROL_EDITAR);
        return null;
    }

    private Evento editarRol(HttpServletRequest request) throws AccionWebException {
        Rol rolEditar = (Rol) request.getSession().getAttribute(AWAdministracionFenrir.CONSULTAR_ROL_EDITAR);

        HttpSession session = request.getSession();

        ValidacionRolEdicionException exception = new ValidacionRolEdicionException();

        String nombre = request.getParameter(CRol.ROL_NOMBRE);
        if ((nombre == null) || (nombre.trim().equals(""))) {
            exception.addError("Debe Proporcionar un Nombre para el Rol.");
        }

        String descripcion = request.getParameter(CRol.ROL_DESCRIPCION);
        if ((descripcion == null) || (descripcion.trim().equals(""))) {
            exception.addError("Debe Proporcionar una descripción para el Rol.");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Rol dato = new Rol();
        dato.setDescripcion(descripcion);
        dato.setNombre(nombre);

        dato.setRolId(rolEditar.getRolId());
        EvnAdministracionFenrir evento
                = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.EDITAR_ROL);
        evento.setRol(dato);

        return evento;
    }

    private Evento consultarEditarRol(HttpServletRequest request) {
        String idRol = request.getParameter(AWAdministracionFenrir.CONSULTAR_ROL_EDITAR);
        Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionFenrir evento = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.CONSULTAR_ROL_EDITAR);
        evento.setIdRol(idRol);
        return evento;
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de
     * usuarios
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información
     * del nombre de usuario a consultar a crear.
     * @throws AccionWebException
     */
    private EvnAdministracionFenrir consultarUsuarios(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();
        String nombre = request.getParameter(CUsuario.NOMBRE_USUARIO);
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnAdministracionFenrir evento
                = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.CONSULTAR_USUARIOS);
        if ((nombre != null) && !nombre.trim().equals("")) {
            evento.setNombreUsuario(nombre);
        }
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de
     * usuarios por círculo
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información
     * del circulo.
     * @throws AccionWebException
     */
    private EvnAdministracionFenrir consultarUsuariosPorCirculo(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        ValidacionConsultaUsuariosException exception = new ValidacionConsultaUsuariosException();

        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if (idCirculo != null) {
            session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);
        }

        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }

        session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);

        EvnAdministracionFenrir evento
                = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.CONSULTAR_USUARIOS_POR_CIRCULO);
        evento.setCirculo(circulo);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de
     * usuarios por círculo
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información
     * del circulo.
     * @throws AccionWebException
     */
    private EvnAdministracionFenrir consultarCirculos(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        ValidacionConsultaUsuariosException exception = new ValidacionConsultaUsuariosException();

        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Circulo.");
        } else {
            session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);
        }

        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);

        EvnAdministracionFenrir evento
                = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.CONSULTAR_USUARIOS_POR_CIRCULO);
        evento.setCirculo(circulo);
        return evento;
    }

    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para seleccionar un
     * <code>Usuario</code>en la sesión.
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información
     * del circulo.
     * @throws AccionWebException
     */
    private EvnAdministracionFenrir seleccionaUsuario(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();
        String idStr = request.getParameter(CUsuario.ID_USUARIO);
        int id = -1;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            throw new AccionInvalidaException(idStr + " no es un identificador de usuario válido.");
        }

        List elementos = (List) session.getAttribute(AWAdministracionFenrir.LISTA_USUARIOS);

        for (Iterator iter = elementos.iterator(); iter.hasNext();) {
            gov.sir.core.negocio.modelo.Usuario usuario
                    = (gov.sir.core.negocio.modelo.Usuario) iter.next();
            if (usuario.getIdUsuario() == id) {
                session.setAttribute(USUARIO_SELECCIONADO, usuario);
                return null;
            }
        }
        throw new AccionInvalidaException("No se encontró el usuario.");
    }

    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de verificar la existencia de un círculo en la
     * sesión del usuario
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información
     * del circulo.
     * @throws AccionWebException
     */
    private EvnAdministracionFenrir verificarCirculo(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        ValidacionConsultaUsuariosException exception = new ValidacionConsultaUsuariosException();
        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Circulo.");
        } else {
            session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);
        }

        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        return null;
    }

    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de creación de un
     * usuario
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información
     * del usuario.
     * @throws AccionWebException
     */
    private EvnAdministracionFenrir crearUsuario(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();

        ValidacionCreacionUsuarioException exception = new ValidacionCreacionUsuarioException();

        String login = request.getParameter(CUsuario.LOGIN_USUARIO);
        if ((login == null) || (login.trim().equals(""))) {
            exception.addError("Debe Proporcionar un nombre de usuario.");
        } else {
            session.setAttribute(CUsuario.LOGIN_USUARIO, login);
        }

        String clave = request.getParameter(CUsuario.CLAVE_USUARIO);
        if ((clave == null) || (clave.trim().equals(""))) {
            exception.addError("Debe Proporcionar una clave para el usuario.");
        } else {
            session.setAttribute(CUsuario.CLAVE_USUARIO, clave);
        }

        String confirmaClave = request.getParameter(CUsuario.CLAVE_USUARIO_CONFIRMACION);
        if ((confirmaClave == null) || (confirmaClave.trim().equals(""))) {
            exception.addError("Debe confirmar la clave para el usuario.");
        } else {
            session.setAttribute(CUsuario.CLAVE_USUARIO_CONFIRMACION, confirmaClave);
        }

        if ((confirmaClave == null) || (confirmaClave.trim().equals(""))
                || (clave == null) || (clave.trim().equals(""))) {

        } else {
            if (!confirmaClave.equals(clave)) {
                exception.addError("La clave y su confirmación deben coincidir.");
            }

        }

        validarClave(clave, exception);

        String nombre = request.getParameter(CUsuario.NOMBRE_USUARIO);
        if ((nombre == null) || (nombre.trim().equals(""))) {
            exception.addError("Debe Proporcionar un Nombre.");
        } else {
            session.setAttribute(CUsuario.NOMBRE_USUARIO, nombre);
        }

        String apellido1 = request.getParameter(CUsuario.APELLIDO1_USUARIO);
        if ((apellido1 == null) || (apellido1.trim().equals(""))) {
            exception.addError("Debe Proporcionar el primer Apellido.");
        } else {
            session.setAttribute(CUsuario.APELLIDO1_USUARIO, apellido1);
        }

        String apellido2 = request.getParameter(CUsuario.APELLIDO2_USUARIO);
        if ((apellido2 == null) || (apellido2.trim().equals(""))) {
            exception.addError("Debe Proporcionar el Segundo Apellido.");
        } else {
            session.setAttribute(CUsuario.APELLIDO2_USUARIO, apellido2);
        }

        String idCirculo = (String) request.getParameter(CCirculo.ID_CIRCULO);// session.getAttribute(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Circulo.");
        } else {
            session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);
        }
        
        /**
         * DNilson226 validaciones Tipo Identicacion y Numero Identificacion
         */
        String idTipoDoc = (String) request.getParameter(CCiudadano.TIPODOC);
        System.out.println("<<<<<DNilson226 vlr idTipoDoc: "+ idTipoDoc +">>>>>");
        if((idTipoDoc == null) || (idTipoDoc.trim().length()==0) 
                               || idTipoDoc.equals(WebKeys.SIN_SELECCIONAR)){
            exception.addError("Debe Seleccionar un Tipo de Idenficacion");
        }else {
            session.setAttribute(CCiudadano.TIPODOC, idTipoDoc);
        }
        


        String numDoc =  (String) request.getParameter(CCiudadano.IDCIUDADANO); //CCiudadano.DOCUMENTO
        System.out.println("<<<<<DNilson226 vlr numDoc: "+ numDoc +">>>>>");
        if( (numDoc==null) || (numDoc.trim().equals("")) ){
            exception.addError("Debe Proporcionar el Numero de Documento");
        }else {
            session.setAttribute(CCiudadano.IDCIUDADANO, numDoc);
        }
        
        /*
        DNilson226 Req. Consulta Nacional        
        */
         
        List roles = new ArrayList();
        String[] rolesArray = request.getParameterValues(CRol.ID_ROL);
        //DNilson226 
        //if (rolesArray == null || rolesArray.length == 0) {
        if (rolesArray != null && rolesArray.length > 0) {
            //DNilson226 
            /* exception.addError("Debe Seleccionar al menos un Rol.");*/
        //} else {
            for (int i = 0; i < rolesArray.length; i++) {
                if (rolesArray[i].equals(WebKeys.SIN_SELECCIONAR)) {
                    continue;
                }
                Rol rol = new Rol();
                rol.setRolId(rolesArray[i]);
                roles.add(rol);
            }
        }
 
        /**
         * DNilson226
        if (roles.isEmpty()) {
            exception.addError("Debe Seleccionar al menos un Rol.");
        }
        */
        

        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }

        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);

        UsuarioCirculo uCir = new UsuarioCirculo();
        uCir.setCirculo(circulo);

        gov.sir.core.negocio.modelo.Usuario usuario = new gov.sir.core.negocio.modelo.Usuario();
        usuario.setLoginID(login);
        usuario.setUsername(login);
        usuario.setNombre(nombre);
        usuario.setApellido1(apellido1);
        usuario.setApellido2(apellido2);
        usuario.setPassword(clave);
        usuario.setActivo(true);
        usuario.addUsuarioCirculo(uCir);
        usuario.setTipoIdentificacion(idTipoDoc);
        long longNumDoc = 0;
        try{
            longNumDoc = Long.parseLong(numDoc);
        }catch(Exception e){
            System.err.println(">>>>>>>DNilson: "+longNumDoc+" <<<<<<<<");
            e.printStackTrace();
        }
        usuario.setNumIdentificacion(longNumDoc);

        Usuario usuarioAuriga = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnAdministracionFenrir evento
                = new EvnAdministracionFenrir(usuarioAuriga, EvnAdministracionFenrir.USUARIO_CREAR);
        evento.setUsuarioNegocio(usuario);
        evento.setRoles(roles);
        evento.setCirculo(circulo);
        /**
         * @Author Carlos Torres
         * @Mantis 13176
         * @Chaged ;
         */
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioNeg.getUsername(), String.valueOf(usuarioNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new java.util.Date(request.getSession().getCreationTime()));
        evento.setInfoUsuario(infoUsuario);
        return evento;
    }

    //	//////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de cambio de clave
     * de un usuario
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información
     * del usuario.
     * @throws AccionWebException
     */
    private EvnAdministracionFenrir cambiarClaveUsuario(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();

        ValidacionCambioClaveException exception = new ValidacionCambioClaveException();

        String clave = request.getParameter(CUsuario.CLAVE_USUARIO);
        if ((clave == null) || (clave.trim().equals(""))) {
            exception.addError("Debe proporcionar su contraseña actual.");
        }

        String claveNueva = request.getParameter(CUsuario.CLAVE_USUARIO_NUEVA);

        if ((claveNueva == null) || (claveNueva.trim().equals(""))) {
            exception.addError("Debe proporcionar la nueva contraseña.");
        }

        String claveNuevaConf = request.getParameter(CUsuario.CLAVE_USUARIO_NUEVA_CONF);

        if ((claveNuevaConf == null) || (claveNuevaConf.trim().equals(""))) {
            exception.addError("Debe confirmar la nueva contraseña.");
        } else if (!claveNuevaConf.equals(claveNueva)) {
            exception.addError("La contraseña nueva y la confirmación no coinciden.");
        }

        validarClave(claveNueva, exception);

        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }

        gov.sir.core.negocio.modelo.Usuario usuario = new gov.sir.core.negocio.modelo.Usuario();
        usuario.setPassword(claveNueva);

        Usuario usuarioAuriga = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnAdministracionFenrir evento
                = new EvnAdministracionFenrir(usuarioAuriga, EvnAdministracionFenrir.USUARIO_CAMBIAR_CLAVE);
        evento.setUsuarioNegocio(usuario);
        evento.setOldPassword(clave);
        return evento;
    }

    //	//////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de habilitación de
     * un usuario
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información
     * del usuario.
     * @throws AccionWebException
     */
    private EvnAdministracionFenrir habilitarUsuario(HttpServletRequest request, boolean habilitar)
            throws AccionWebException {
        HttpSession session = request.getSession();

        ValidacionCreacionUsuarioException exception = new ValidacionCreacionUsuarioException();

        String id = request.getParameter(CUsuario.ID_USUARIO);
        if ((id == null) || (id.trim().equals(""))) {
            exception.addError("Debe Proporcionar un identificador de usuario.");
        }

        String idCirculo = (String) session.getAttribute(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Circulo.");
        }

        long idval = 0;
        try {
            idval = Long.parseLong(id);
        } catch (NumberFormatException e) {
            exception.addError(id + " no es un número válido.");
        }

        String login = request.getParameter(CUsuario.LOGIN_USUARIO);
        if ((login == null) || (login.trim().equals(""))) {
            exception.addError("Debe Proporcionar un nombre de usuario.");
        }

        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }

        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);

        gov.sir.core.negocio.modelo.Usuario usuario = new gov.sir.core.negocio.modelo.Usuario();
        usuario.setIdUsuario(idval);
        usuario.setLoginID(login);
        usuario.setUsername(login);
        usuario.setActivo(habilitar);

        Usuario usuarioAuriga = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnAdministracionFenrir evento
                = new EvnAdministracionFenrir(usuarioAuriga, EvnAdministracionFenrir.USUARIO_HABILITAR);
        evento.setUsuarioNegocio(usuario);
        evento.setCirculo(circulo);
        /**
         * @Author Carlos Torres
         * @Mantis 13176
         * @Chaged ;
         */
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioNeg.getUsername(), String.valueOf(usuarioNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new java.util.Date(request.getSession().getCreationTime()));
        evento.setInfoUsuario(infoUsuario);

        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento que habilita la
     * edición de ciudadanos
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información
     * del usuario.
     * @throws AccionWebException
     */
    private EvnAdministracionFenrir modificarUsuario(HttpServletRequest request, boolean habilitar)
            throws AccionWebException {
        System.out.println("VOY A MODIFICAR");
        HttpSession session = request.getSession();

        ValidacionCreacionUsuarioException exception = new ValidacionCreacionUsuarioException();

        String id = request.getParameter(CUsuario.ID_USUARIO);
        if ((id == null) || (id.trim().equals(""))) {
            exception.addError("Debe Proporcionar un identificador de usuario.");
        }

        String idCirculo = (String) session.getAttribute(CCirculo.ID_CIRCULO);
        System.out.println("CIRCULO CARE TOMATE " + idCirculo);

        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Circulo.");
        }

        long idval = 0;
        try {
            idval = Long.parseLong(id);
        } catch (NumberFormatException e) {
            exception.addError(id + " no es un número válido.");
        }

        String login = request.getParameter(CUsuario.LOGIN_USUARIO);
        if ((login == null) || (login.trim().equals(""))) {
            exception.addError("Debe Proporcionar un nombre de usuario.");
        }

        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }

        session.setAttribute(CUsuario.ID_USUARIO, id);
        session.setAttribute(CUsuario.LOGIN_USUARIO, login);
        session.setAttribute(CUsuario.NOMBRE_USUARIO, request.getParameter(CUsuario.NOMBRE_USUARIO));
        session.setAttribute(CUsuario.APELLIDO1_USUARIO, request.getParameter(CUsuario.APELLIDO1_USUARIO));
        session.setAttribute(CUsuario.APELLIDO2_USUARIO, request.getParameter(CUsuario.APELLIDO2_USUARIO));
        session.setAttribute(CUsuario.TIPO_JUSTIFICACION, request.getParameter(CUsuario.TIPO_JUSTIFICACION));
        session.setAttribute(CArchivosJustifica.NOMBRE_PROCESO, CArchivosJustifica.PROCESO_EDITAR);
        session.removeAttribute(AWAdministracionFenrir.LISTA_JUSTIFICACIONES_USUARIOS);
        session.removeAttribute(AWAdministracionFenrir.LISTA_TIPOS_JUSTIFICACIONES);

        System.out.println("TIPO JUSTIFICACION USUARIOS MODIFICAR " + request.getParameter(CUsuario.TIPO_JUSTIFICACION));

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        JustificaAdm justificaAdm = new JustificaAdm();
        justificaAdm.setAdmIdUsuario(Integer.parseInt(id));

        String fechaIni = (String) session.getAttribute(WebKeys.RANGO_FECHA_INI);
        String fechaFin = (String) session.getAttribute(WebKeys.RANGO_FECHA_FIN);

        EvnAdministracionFenrir evento
                = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.CONSULTAR_JUSTIFICACIONES_USUARIO);

        evento.setJustificaAdm(justificaAdm);
        evento.setFechaIni(fechaIni);
        evento.setFechaFin(fechaFin);
        evento.setTipoJust(request.getParameter(CUsuario.TIPO_JUSTIFICACION));

        return evento;
    }

    private EvnAdministracionFenrir habilitarDeshabilitarUsuario(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();

        ValidacionCreacionUsuarioException exception = new ValidacionCreacionUsuarioException();

        String id = request.getParameter(CUsuario.ID_USUARIO);
        if ((id == null) || (id.trim().equals(""))) {
            exception.addError("Debe Proporcionar un identificador de usuario.");
        }

        String idCirculo = (String) session.getAttribute(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Circulo.");
        }

        long idval = 0;
        try {
            idval = Long.parseLong(id);
        } catch (NumberFormatException e) {
            exception.addError(id + " no es un número válido.");
        }

        String login = request.getParameter(CUsuario.LOGIN_USUARIO);
        if ((login == null) || (login.trim().equals(""))) {
            exception.addError("Debe Proporcionar un nombre de usuario.");
        }

        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }

        session.setAttribute(CUsuario.ID_USUARIO, id);
        session.setAttribute(CUsuario.LOGIN_USUARIO, login);
        session.setAttribute(CUsuario.TIPO_JUSTIFICACION, request.getParameter(CUsuario.TIPO_JUSTIFICACION));
        System.out.println("TIPO JUSTIFICACION USUARIOS HABILITAR/DESHABILITAR " + request.getParameter(CUsuario.TIPO_JUSTIFICACION));

        String accion = (String) session.getAttribute(CUsuario.TIPO_JUSTIFICACION);
        if (accion.equals(CUsuario.USUARIO_ACTIVAR)) {
            System.out.println("voy a activar");
            session.setAttribute(CArchivosJustifica.NOMBRE_PROCESO, CArchivosJustifica.PROCESO_ACTIVAR);
        } else if (accion.equals(CUsuario.USUARIO_INACTIVAR)) {
            System.out.println("voy a inactivar");
            session.setAttribute(CArchivosJustifica.NOMBRE_PROCESO, CArchivosJustifica.PROCESO_INACTIVAR);
        }

        session.removeAttribute(AWAdministracionFenrir.LISTA_JUSTIFICACIONES_USUARIOS);
        session.removeAttribute(AWAdministracionFenrir.LISTA_TIPOS_JUSTIFICACIONES);

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        JustificaAdm justificaAdm = new JustificaAdm();
        justificaAdm.setAdmIdUsuario(Integer.parseInt(id));

        EvnAdministracionFenrir evento
                = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.CONSULTAR_JUSTIFICACIONES_USUARIO);

        evento.setJustificaAdm(justificaAdm);
        evento.setFechaIni(null);
        evento.setFechaFin(null);
        evento.setTipoJust(request.getParameter(CUsuario.TIPO_JUSTIFICACION));
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento que actualiza los
     * datos modificados
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información
     * del usuario.
     * @throws AccionWebException
     */
    private EvnAdministracionFenrir actualizarDatosUsuario(HttpServletRequest request, boolean habilitar, FileItem fileItem)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        ValidacionCreacionUsuarioException exception = new ValidacionCreacionUsuarioException();
        
        // FileItem fileItem = cargarArchivo(request);
        String id = (String) session.getAttribute(CUsuario.ID_USUARIO);
        if ((id == null) || (id.trim().equals(""))) {
            exception.addError("Debe Proporcionar un identificador de usuario.");
        }

        String idCirculo = (String) session.getAttribute(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Circulo.");
        }

        long idval = 0;
        try {
            idval = Long.parseLong(id);
        } catch (NumberFormatException e) {
            exception.addError(id + " no es un número válido.");
        }

        String login = (String) session.getAttribute(CUsuario.LOGIN_USUARIO);
        if ((login == null) || (login.trim().equals(""))) {
            exception.addError("Debe Proporcionar un nombre de usuario.");
        }
        String nombre = "";
        String apellido1 = "";
        String apellido2 = "";
//        String descripcionNovedad = "";
//        String tipoNovedad = "";
//        String fileName = "";

        if (session.getAttribute(CUsuario.TIPO_JUSTIFICACION).equals(CUsuario.USUARIO_EDITAR)) {

            nombre = (String) session.getAttribute(CUsuario.NOMBRE_USUARIO);
            if ((nombre == null) || (nombre.trim().equals(""))) {
                exception.addError("Debe proporcionar un nombre para el usuario.");
            }

            apellido1 = (String) session.getAttribute(CUsuario.APELLIDO1_USUARIO);
            if ((apellido1 == null) || (apellido1.trim().equals(""))) {
                exception.addError("Debe proporcionar un apellido para el usuario.");
            }

            apellido2 = (String) session.getAttribute(CUsuario.APELLIDO2_USUARIO);

        }

        String tipoNovedad = (String) session.getAttribute(CJustificaAdm.TIP_ID_TIPO);
        if (tipoNovedad.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar un tipo novedad para la nota informativa");
        }
        
        String descripcionNovedad = (String) session.getAttribute(CJustificaAdm.ADM_DESCRIPCION);
        if ((descripcionNovedad == null) || (descripcionNovedad.trim().equals(""))) {
            exception.addError("Debe proporcionar una descripción para la nota informativa");
        }

        String descripcionTamano = (String) session.getAttribute(CJustificaAdm.ADM_MAX_LENGTH);
        if ((!descripcionTamano.trim().equals("0")) || (descripcionTamano == null) || (descripcionTamano.trim().equals(""))) {
            exception.addError("La descripción debe tener minimo 20 carácteres");
        }

        String fileName = (String) session.getAttribute(CJustificaAdm.FILE_NAME);
        String fileSize = (String) session.getAttribute(CArchivosJustifica.FILE_SIZE);
        String fileFormat = (String) session.getAttribute(CArchivosJustifica.FILE_EXTENSION);
        String fileProceso = (String) session.getAttribute(CArchivosJustifica.NOMBRE_PROCESO);

        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);

        gov.sir.core.negocio.modelo.Usuario usuario = new gov.sir.core.negocio.modelo.Usuario();
        usuario.setIdUsuario(idval);
        usuario.setLoginID(login);
        usuario.setUsername(login);
        
        if (session.getAttribute(CUsuario.TIPO_JUSTIFICACION).equals(CUsuario.USUARIO_EDITAR)) {
            usuario.setNombre(nombre);
            usuario.setApellido1(apellido1);
            usuario.setApellido2(apellido2);
        }
        if (session.getAttribute(CUsuario.TIPO_JUSTIFICACION).equals(CUsuario.USUARIO_ACTIVAR)) {
            usuario.setActivo(true);
        }
        if (session.getAttribute(CUsuario.TIPO_JUSTIFICACION).equals(CUsuario.USUARIO_INACTIVAR)) {
            usuario.setActivo(false);
        }

        gov.sir.core.negocio.modelo.ArchivosJustifica archivo = null;
        if (!fileName.trim().equals("")) {
            archivo = new gov.sir.core.negocio.modelo.ArchivosJustifica();

            if ((fileSize == null) || (fileSize.trim().equals(""))) {
                exception.addError("No fue posible determinar el tamaño del archivo");
            }
            //EL TAM MAX DEL ARCHIVO DEBE IR EN UN ARCHIVO DE PROPIEDADES
            System.out.println("TAMANO DEL ARCHIVO: " + fileSize);
            if (Integer.parseInt(fileSize) > 2000000) {
                exception.addError("El tamaño del archivo supera el tamaño permitido (2MB)");
            }
            if ((fileFormat == null) || (fileFormat.trim().equals(""))) {
                exception.addError("No fue posible determinar la extension del archivo");
            }

            archivo.setJusNombreOriginal(fileName);
            archivo.setJusTipoArchivo(fileFormat);
            archivo.setJusTamanoArchivo(Integer.parseInt(fileSize));
            archivo.setJusNombreProceso(fileProceso);
            archivo.setJusRutaFisica(RUTA_DESTINO_ARCHIVO_JUSTIFICACION);
            archivo.setJusFechaDeSubida(new Date());

        }

        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }

        gov.sir.core.negocio.modelo.JustificaAdm justificaAdm = new gov.sir.core.negocio.modelo.JustificaAdm();
        justificaAdm.setAdmDescripcion(descripcionNovedad);
        justificaAdm.setAdmIpPcFuncLog(request.getRemoteHost());
        justificaAdm.setAdmIdUsuario(Integer.parseInt(id));
        justificaAdm.setAdmFecha(new Date());

        gov.sir.core.negocio.modelo.JustificaTipos justificaTipos = new gov.sir.core.negocio.modelo.JustificaTipos();
        justificaTipos.setTipIdTipo(Integer.parseInt(tipoNovedad));

        Usuario usuarioAuriga = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);        

        justificaAdm.setAdmUsuarioModifica(usuarioAuriga.getUsuarioId());
        justificaAdm.setAdmCirculoUsrModificado(idCirculo);

        EvnAdministracionFenrir evento = null;

        if (session.getAttribute(CUsuario.TIPO_JUSTIFICACION).equals(CUsuario.USUARIO_EDITAR)) {
            evento = new EvnAdministracionFenrir(usuarioAuriga, EvnAdministracionFenrir.USUARIOS_ACTUALIZAR_INFORMACION);
        } else {            
            evento = new EvnAdministracionFenrir(usuarioAuriga, EvnAdministracionFenrir.USUARIO_HABILITAR);
        }
        
        evento.setUsuarioNegocio(usuario);
        evento.setCirculo(circulo);
        evento.setArchivosJustifica(archivo);
        evento.setJustificaAdm(justificaAdm);
        evento.setFileItem(fileItem);
        evento.setJustificaTipos(justificaTipos);

        /**
         * @Author Carlos Torres
         * @Mantis 13176
         * @Chaged ;
         */
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioNeg.getUsername(), String.valueOf(usuarioNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new java.util.Date(request.getSession().getCreationTime()));
        evento.setInfoUsuario(infoUsuario);
        return evento;

    }

    /**
     * Este método se encarga de validar la construcción de la clave Utilizando
     * expresiones regulares. Reglas: - La clave debe tener al menos un número:
     * [0-9]+ - La clave puede tener caracteres especiales como: /()/&%$, etc. -
     * Antes y después del número pueden existir letras o caracteres especiales:
     * [\\w|\\W]* o .*
     *
     * @param clave
     * @return
     */
    private void validarClave(String clave, ValidacionParametrosException exception) {
        //Pattern p = Pattern.compile("[\\w|\\W]*[0-9]+[\\w\\W]*");
        Pattern p = Pattern.compile(".*[0-9]+.*");
        Matcher m = p.matcher(clave);
        boolean esValida = m.matches();

        if (!esValida) {
            exception.addError("La clave debe tener al menos un número");
        }

        if (clave.length() < 6) {
            exception.addError("La clave debe tener mínimo 6 caracteres.");
        }
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de creación de un
     * Rol
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información
     * del Rol.
     * @throws AccionWebException
     */
    private EvnAdministracionFenrir crearRol(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();

        ValidacionRolException exception = new ValidacionRolException();

        String id = request.getParameter(CRol.ID_ROL);
        if ((id == null) || (id.trim().equals(""))) {
            exception.addError("Debe Proporcionar un identificador para el Rol.");
        }

        String nombre = request.getParameter(CRol.ROL_NOMBRE);
        if ((nombre == null) || (nombre.trim().equals(""))) {
            exception.addError("Debe Proporcionar un Nombre para el Rol.");
        }

        String descripcion = request.getParameter(CRol.ROL_DESCRIPCION);
        if ((descripcion == null) || (descripcion.trim().equals(""))) {
            exception.addError("Debe Proporcionar una descripción para el Rol.");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Rol dato = new Rol();
        dato.setRolId(id);
        dato.setDescripcion(descripcion);
        dato.setNombre(nombre);

        String[] estaciones = request.getParameterValues(CEstacion.ESTACION_ID);
        if (estaciones != null) {
            for (int i = 0; i < estaciones.length; i++) {
                String estId = estaciones[i];
                if (!estId.equals(WebKeys.SIN_SELECCIONAR)) {
                    RelRolEstacion relRolEst = new RelRolEstacion();
                    relRolEst.setRol(dato);
                    relRolEst.setEstacionId(estId);
                    dato.addRelRolEstacion(relRolEst);
                }
            }
        }

        EvnAdministracionFenrir evento
                = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.ROL_CREAR);
        evento.setRol(dato);

        return evento;
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de creación de un
     * Nivel
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información
     * del Nivel.
     * @throws AccionWebException
     */
    private EvnAdministracionFenrir crearNivel(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();

        ValidacionNivelException exception = new ValidacionNivelException();

        String id = request.getParameter(CNivel.NIVEL_ID);
        if ((id == null) || (id.trim().equals(""))) {
            exception.addError("Debe Proporcionar un identificador para el nivel.");
        }

        String nombre = request.getParameter(CNivel.NIVEL_NOMBRE);
        if ((nombre == null) || (nombre.trim().equals(""))) {
            exception.addError("Debe Proporcionar un Nombre para el nivel.");
        }

        String idpadre = request.getParameter(CNivel.NIVEL_SUPERIOR);

        String atributo1 = request.getParameter(CNivel.NIVEL_ATRIBUTO1);
        String atributo2 = request.getParameter(CNivel.NIVEL_ATRIBUTO2);
        String atributo3 = request.getParameter(CNivel.NIVEL_ATRIBUTO3);
        String atributo4 = request.getParameter(CNivel.NIVEL_ATRIBUTO4);
        String atributo5 = request.getParameter(CNivel.NIVEL_ATRIBUTO5);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Nivel dato = new Nivel();
        dato.setNivelId(id);
        dato.setNombre(nombre);
        if ((idpadre != null) && (!idpadre.trim().equals(""))) {
            Nivel padre = new Nivel();
            padre.setNivelId(idpadre);
            dato.setParent(padre);
        }
        if ((atributo1 != null) && (!atributo1.trim().equals(""))) {
            dato.setAtributo1(atributo1);
        }
        if ((atributo2 != null) && (!atributo2.trim().equals(""))) {
            dato.setAtributo2(atributo2);
        }
        if ((atributo3 != null) && (!atributo3.trim().equals(""))) {
            dato.setAtributo3(atributo3);
        }
        if ((atributo4 != null) && (!atributo4.trim().equals(""))) {
            dato.setAtributo4(atributo4);
        }
        if ((atributo5 != null) && (!atributo5.trim().equals(""))) {
            dato.setAtributo5(atributo5);
        }

        EvnAdministracionFenrir evento
                = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.NIVEL_CREAR);
        evento.setNivel(dato);

        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * un Nivel
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información
     * del Nivel.
     * @throws AccionWebException
     */
    private EvnAdministracionFenrir eliminarNivel(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        ValidacionNivelException exception = new ValidacionNivelException();

        String id = request.getParameter(CNivel.NIVEL_ID);
        if ((id == null) || (id.trim().equals(""))) {
            exception.addError("Debe Proporcionar un identificador para el nivel.");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Nivel dato = new Nivel();
        dato.setNivelId(id);

        EvnAdministracionFenrir evento
                = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.NIVEL_ELIMINAR);
        evento.setNivel(dato);

        return evento;
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de limpiar la sesión luego que el usuario ha
     * terminado de usar las pantallas administrativas relacionadas con Fenrir
     */
    private EvnAdministracionFenrir limpiarSesion(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        session.removeAttribute(AWAdministracionFenrir.LISTA_USUARIOS);
        session.removeAttribute(CCirculo.ID_CIRCULO);
        session.removeAttribute(AWAdministracionFenrir.CONSULTAR_ROL_EDITAR);
        //session.removeAttribute(AWAdministracionFenrir.LISTA_NOVEDADES_POR_EVENTO);
        session.removeAttribute(AWAdministracionFenrir.LISTA_JUSTIFICACIONES_USUARIOS);
        session.removeAttribute(AWAdministracionFenrir.LISTA_CAMPOS_CAPTURA);
        session.removeAttribute(AWAdministracionFenrir.LISTA_CARGA_CAMPOS_CAPTURA);
        return null;
    }

    /**
     * Este método se encarga de limpiar los datos de un usuario de la sesión
     */
    private EvnAdministracionFenrir limpiarUsuarioDeSesion(HttpServletRequest request) {

        HttpSession session = request.getSession();

        session.removeAttribute(CUsuario.APELLIDO1_USUARIO);
        session.removeAttribute(CUsuario.APELLIDO2_USUARIO);
        session.removeAttribute(CUsuario.CLAVE_USUARIO);
        session.removeAttribute(CUsuario.ID_USUARIO);
        session.removeAttribute(CUsuario.LOGIN_USUARIO);
        session.removeAttribute(CUsuario.NOMBRE_USUARIO);
        session.removeAttribute(CResponsabilidad.RESPONSABILIDAD_ID);
        return null;
    }

    /**
     * Este método se encarga de limpiar los datos de una justificación de la
     * sesión
     */
    private EvnAdministracionFenrir limpiarJustificacionDeSesion(HttpServletRequest request) {

        HttpSession session = request.getSession();

        session.removeAttribute(CJustificaAdm.TIP_ID_TIPO);
        session.removeAttribute(CJustificaAdm.FILE_NAME);
        session.removeAttribute(CJustificaAdm.ADM_DESCRIPCION);
        session.removeAttribute(CJustificaAdm.ADM_MAX_LENGTH);
        return null;
    }

    private EvnAdministracionFenrir limpiarArchivoDeSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(CArchivosJustifica.FILE_SIZE);
        session.removeAttribute(CArchivosJustifica.FILE_TYPE);
        session.removeAttribute(CArchivosJustifica.FILE_EXTENSION);
        return null;
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de manejar el evento de respuesta proveniente de
     * la acción de negocio. Sube datos a sesión de acuerdo al tipo de respuesta
     * proveniente en el evento
     *
     */
    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
        
        EvnRespAdministracionFenrir respuesta = (EvnRespAdministracionFenrir) evento;
        
        HttpSession session = request.getSession();
        context = session.getServletContext();

        if (respuesta != null) {
            String tipoEvento = respuesta.getTipoEvento();
            if (tipoEvento.equals(EvnRespAdministracionFenrir.CONSULTA_USUARIOS_OK)
                    || tipoEvento.equals(EvnRespAdministracionFenrir.CONSULTA_USUARIOS_POR_CIRCULO_OK)
                    || tipoEvento.equals(EvnRespAdministracionFenrir.USUARIO_CREAR_OK)
                    || tipoEvento.equals(EvnRespAdministracionFenrir.USUARIO_ACTUALIZACION_OK)
                    || tipoEvento.equals(EvnRespAdministracionFenrir.USUARIO_HABILITACION_OK)) {
                List elementos = (List) respuesta.getPayload();
                session.setAttribute(AWAdministracionFenrir.LISTA_USUARIOS, elementos);
                if (tipoEvento.equals(EvnRespAdministracionFenrir.USUARIO_CREAR_OK)
                        || tipoEvento.equals(EvnRespAdministracionFenrir.USUARIO_HABILITACION_OK)) {
                    limpiarUsuarioDeSesion(request);
                    limpiarJustificacionDeSesion(request);
                    limpiarArchivoDeSession(request);
                }
                return;
            } else if (tipoEvento.equals(EvnRespAdministracionFenrir.ROL_CREAR_OK)) {
                List roles = (List) respuesta.getPayload();
                Iterator itResp = roles.iterator();
                List elementos = new ArrayList();
                while (itResp.hasNext()) {
                    Rol dato = (Rol) itResp.next();
                    elementos.add(new ElementoLista(dato.getRolId(), dato.getNombre()));
                }
                context.setAttribute(
                        WebKeys.LISTA_ESTACIONES_SISTEMA,
                        Collections.unmodifiableList(elementos));
                return;
            } else if (tipoEvento.equals(EvnRespAdministracionFenrir.NIVEL_CREAR_OK)
                    || tipoEvento.equals(EvnRespAdministracionFenrir.NIVEL_ELIMINAR_OK)) {
                List niveles = (List) respuesta.getPayload();
                context.setAttribute(
                        WebKeys.LISTA_NIVELES_SISTEMA,
                        Collections.unmodifiableList(niveles));
                return;
            } else if (tipoEvento.equals(EvnRespAdministracionFenrir.USUARIO_CAMBIO_CLAVE_OK)) {
                limpiarUsuarioDeSesion(request);
            } else if (tipoEvento.equals(EvnRespAdministracionFenrir.CONSULTAR_ROL_EDITAR_OK)) {
                Rol rol = (Rol) respuesta.getPayload();
                if (rol != null) {
                    session.setAttribute(AWAdministracionFenrir.CONSULTAR_ROL_EDITAR, rol);
                }
            } else if (tipoEvento.equals(EvnRespAdministracionFenrir.EDITAR_ROL_OK)) {
                Rol rol = (Rol) respuesta.getPayload();
                String idRol = rol.getRolId();
                List roles = (List) session.getServletContext().getAttribute(WebKeys.LISTA_ROLES_SISTEMA);
                Iterator itRoles = roles.iterator();
                while (itRoles.hasNext()) {
                    ElementoLista elem = (ElementoLista) itRoles.next();
                    if (elem.getId().equals(idRol)) {
                        elem.setValor(rol.getNombre());
                        break;
                    }
                }
                session.removeAttribute(AWAdministracionFenrir.CONSULTAR_ROL_EDITAR);
            } else if (tipoEvento.equals(EvnRespAdministracionFenrir.CONSULTA_PERFIL_USUARIO_OK)) {
                Usuario usuario = (Usuario) respuesta.getPayload();
                if (usuario != null) {
                    session.setAttribute(AWAdministracionFenrir.PERFIL_USUARIO, usuario);
                }
                System.out.println("LLEGO CON EL LISTADO");
                session.setAttribute(AWAdministracionFenrir.LISTA_JUSTIFICACIONES_USUARIOS, respuesta.getJustificacionesPerfiles());

                session.setAttribute(AWAdministracionFenrir.LISTA_ADMINISTRADOR_REGIONAL_ROLES, respuesta.getRolesAdministradorRegional());
                session.setAttribute(AWAdministracionFenrir.LISTA_TIPOS_JUSTIFICACIONES, respuesta.getTipoJustificaciones());

            } else if (tipoEvento.equals(EvnRespAdministracionFenrir.LISTA_ESTACIONES_ROL)) {
                List estaciones = (List) respuesta.getPayload();
                if (estaciones != null) {
                    List estacionesElemLista = new ArrayList();
                    Iterator itEstaciones = estaciones.iterator();
                    while (itEstaciones.hasNext()) {
                        Estacion estacion = (Estacion) itEstaciones.next();
                        ElementoLista elem = new ElementoLista();
                        elem.setId(estacion.getEstacionId());
                        elem.setValor(estacion.getEstacionId());
                        estacionesElemLista.add(elem);
                    }
                    session.setAttribute(AWAdministracionFenrir.ESTACIONES_ROL, estacionesElemLista);
                }

            } else if (tipoEvento.equals(EvnRespAdministracionFenrir.LISTA_ESTACIONES_CIRCULO)) {
                List estaciones = (List) respuesta.getPayload();
                if (estaciones != null) {
                    List estacionesElemLista = new ArrayList();
                    Iterator itEstaciones = estaciones.iterator();
                    while (itEstaciones.hasNext()) {
                        Estacion estacion = (Estacion) itEstaciones.next();
                        ElementoLista elem = new ElementoLista();
                        elem.setId(estacion.getEstacionId());
                        elem.setValor(estacion.getEstacionId());
                        estacionesElemLista.add(elem);
                    }
                    session.setAttribute(AWAdministracionFenrir.ESTACIONES_CIRCULO, estacionesElemLista);
                }

            } else if (tipoEvento.equals(EvnRespAdministracionFenrir.LISTA_USUARIOS_ROL_ESTACION)) {
                List usuarios = (List) respuesta.getPayload();
                session.setAttribute(AWAdministracionFenrir.LISTA_USUARIOS_ROL_ESTACION, usuarios);
//                String idRol = request.getParameter(WebKeys.ROL);
                String idRol = (String) session.getAttribute(WebKeys.ROL_PERFIL);
                System.out.println("ROL AL EDITAR PERFIL ROL USUARIO " + idRol);
                Rol rol = new Rol();
                rol.setRolId(idRol);
//                String idEstacion = request.getParameter(WebKeys.ESTACION);

                String idEstacion = (String) session.getAttribute(WebKeys.ESTACION);
                Estacion estacion = new Estacion();
                estacion.setEstacionId(idEstacion);
                session.setAttribute(AWAdministracionFenrir.ROL_EDITAR_ROL_ESTACION, rol);
                session.setAttribute(AWAdministracionFenrir.ESTACION_EDITAR_ROL_ESTACION, estacion);
            } else if (tipoEvento.equals(EvnRespAdministracionFenrir.MOSTRAR_CIRCULO_USUARIO_OK)) {
                session.setAttribute(AWAdministracionFenrir.CIRCULOS_ADMINISTRADOR_NACIONAL, respuesta.getPayload());
                session.setAttribute(WebKeys.RECARGA, new Boolean(false));
            } else if (tipoEvento.equals(EvnRespAdministracionFenrir.CONSULTA_USUARIOS_JUSTIFICACIONES_OK)) {
                System.out.println("LLEGO CON EL LISTADO 2");
                Usuario usuario = (Usuario) respuesta.getPayload();
                if (usuario != null) {
                    session.setAttribute(AWAdministracionFenrir.PERFIL_USUARIO, usuario);
                }
//                List elementos = (List) respuesta.getPayload();
                if (respuesta.getJustificacionesPerfiles() != null) {
                    System.out.println("RESPUESTA CON VALORES " + respuesta.getJustificacionesPerfiles());
                    session.setAttribute(AWAdministracionFenrir.LISTA_JUSTIFICACIONES_USUARIOS, respuesta.getJustificacionesPerfiles());
                }

                session.setAttribute(AWAdministracionFenrir.LISTA_ADMINISTRADOR_REGIONAL_ROLES, respuesta.getRolesAdministradorRegional());
//                session.setAttribute(AWAdministracionFenrir.LISTA_JUSTIFICACIONES_USUARIOS, elementos);
                session.removeAttribute(WebKeys.RANGO_FECHA_INI);
                session.removeAttribute(WebKeys.RANGO_FECHA_FIN);
                session.setAttribute(AWAdministracionFenrir.LISTA_TIPOS_JUSTIFICACIONES, respuesta.getTipoJustificaciones());

            } else if (tipoEvento.equals(EvnRespAdministracionFenrir.CONSULTA_CAMPOS_CAPTURA_OK)) {
                session.setAttribute(AWAdministracionFenrir.LISTA_CAMPOS_CAPTURA, respuesta.getPayload());
                /*List elementos = (List) respuesta.getPayload();
                for (Iterator iter = elementos.iterator(); iter.hasNext();) {
                    CamposCapturaEstados cce = (CamposCapturaEstados) iter.next();
                    System.out.println("LA PRUEBA VA BIEN: " + cce.getIdRelFormaCampo());
                }*/
            } else if (tipoEvento.equals(EvnRespAdministracionFenrir.CARGA_CAMPOS_CAPTURA_OK)) {
                session.setAttribute(AWAdministracionFenrir.LISTA_CARGA_CAMPOS_CAPTURA, respuesta.getPayload());
                //List elementos = (List) respuesta.getPayload();
            } else if (tipoEvento.equals(EvnRespAdministracionFenrir.FORMA_PAGO_CREAR_OK)) {
                session.setAttribute(AWAdministracionFenrir.LISTA_CAMPOS_CAPTURA, null);
                List elementos = (List) respuesta.getPayload();
                Iterator tipoPagos = elementos.iterator();
                List elementosLista = new ArrayList();

                while (tipoPagos.hasNext()) {
                    TipoPago tipoPago = (TipoPago) tipoPagos.next();
                    elementosLista.add(new ElementoLista(tipoPago.getIdTipoDocPago() + "", tipoPago.getNombre()));

                }

                context.setAttribute(WebKeys.LISTA_TIPOS_PAGO, elementosLista);
            }
        }
    }

    private Evento cargarInformacionUsuarioConRelaciones(HttpServletRequest request) throws AccionWebException {
        String username = request.getParameter(CUsuario.LOGIN_USUARIO);

        HttpSession session = request.getSession();

        session.setAttribute(CUsuario.LOGIN_USUARIO, username);
        session.setAttribute(CUsuario.TIPO_JUSTIFICACION, request.getParameter(CUsuario.TIPO_JUSTIFICACION));

        System.out.println("TIPO JUSTIFICACION ADD ROL " + request.getParameter(CUsuario.TIPO_JUSTIFICACION));

        session.removeAttribute(AWAdministracionFenrir.LISTA_JUSTIFICACIONES_USUARIOS);
        session.removeAttribute(AWAdministracionFenrir.LISTA_TIPOS_JUSTIFICACIONES);

        if (username == null || username.equals(WebKeys.SIN_SELECCIONAR)) {
            throw new UsuarioPerfilInvalidoException("Debe escoger un usuario válido");
        }
        Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnAdministracionFenrir evento = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.CONSULTAR_USUARIO_CON_RELACIONES);
        evento.setNombreUsuario(username);
        evento.setFechaIni(null);
        evento.setFechaFin(null);
        evento.setTipoJust(request.getParameter(CUsuario.TIPO_JUSTIFICACION));
        return evento;
    }

    private Evento resolverMultipart(HttpServletRequest request)
            throws AccionWebException {
        
        System.out.println("****** entro a resolverMultipart ******* ");
        HttpSession session = request.getSession();
        Evento evento = null;
        FileItem fileItem = cargarArchivo(request);
        String accion = (String) session.getAttribute(WebKeys.ACCION);
        System.out.println("POR FIN LA ACCION " + accion);

        if (accion.equals(AWAdministracionFenrir.USUARIOS_ACTUALIZAR_INFORMACION)) {
            evento = actualizarDatosUsuario(request, false, fileItem);
        } else if (accion.equals(AWAdministracionFenrir.AGREGAR_ESTACION_ROL_USUARIO)) {
            session.setAttribute(CArchivosJustifica.NOMBRE_PROCESO, CArchivosJustifica.PROCESO_AGREGAR_ROL);
            evento = agregarEstacionRolUsuario(request, fileItem);
        } else if (accion.equals(AWAdministracionFenrir.EDITAR_ROL_ESTACION)) {
            System.out.println("llego a EDITAR_ROL_ESTACION con multipart");
            evento = editarRolEstacion(request);
        } else if (accion.equals(AWAdministracionFenrir.ELIMINAR_ROL_ESTACION_USUARIO)) {
            session.setAttribute(CArchivosJustifica.NOMBRE_PROCESO, CArchivosJustifica.PROCESO_ELIMINAR_ROL);
            evento = eliminarRolEstacionUsuario(request, fileItem);
        } else if (accion.equals(AWAdministracionFenrir.ACTUALIZAR_ESTADO_REL_ROL_ESTACION)) {
            session.setAttribute(CArchivosJustifica.NOMBRE_PROCESO, CArchivosJustifica.PROCESO_ACTIVAR_INACTIVAR_ROL);

            evento = actualizarEstadoRelRolEstacionUsuario(request, fileItem);
        } else if (accion.equals(AWAdministracionFenrir.CARGAR_ESTACIONES_ROL_CIRCULO)) {
            evento = cargarEstacionesRolCirculo(request);
        } else if (accion.equals(AWAdministracionFenrir.IR_AGREGAR_ESTACION_ROL)) {
            evento = irAgregarEstacionRol(request);
        } else if (accion.equals(AWAdministracionFenrir.REGRESAR_ESCOGER_USUARIO)) {
            evento = regresarEscogerUsuario(request);
        } else if (accion.equals(AWAdministracionFenrir.FORM_JUST_ELIMINAR_ACT_INACT_PERFIL)) {
            evento = cargarFormJustificacion(request);
        } else if (accion.equals(AWAdministracionFenrir.REGRESAR_PERFIL_INFOUSUARIO)) {
            System.out.println("Llego a la accion con multipart");
            return regresarPerfilInfoUsuario(request);
        } else if (accion.equals(AWAdministracionFenrir.REPORTE_NOVEDADES_X_USUARIO)) {
            do_Reporte_Novedades_Usuario(request);
        } else if (accion.equals(AWAdministracionFenrir.JUSTIFICACIONES_RANGO_FECHA)) {
            return justificacionesRangoFecha(request, false);
        } else if (accion.equals(AWAdministracionFenrir.JUSTIFICACIONES_RANGO_FECHA_PERFIL)) {
            return justificacionesRangoFecha(request, true);
        }

        return evento;
    }

    private EvnAdministracionFenrir justificacionesRangoFecha(HttpServletRequest request, boolean isPerfil) throws AccionWebException {
        System.out.println("EVENTO DE RANGO DE FECHAS");
        HttpSession session = request.getSession();

        System.out.println("TIPO JUSTIFICACION RANGO FECHA " + session.getAttribute(CUsuario.TIPO_JUSTIFICACION));

        String id = (String) session.getAttribute(CUsuario.ID_USUARIO);
        System.out.println("EVENTO DE RANGO DE FECHAS ID " + id);
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        JustificaAdm justificaAdm = new JustificaAdm();

        session.removeAttribute(AWAdministracionFenrir.LISTA_JUSTIFICACIONES_USUARIOS);
        session.removeAttribute(AWAdministracionFenrir.LISTA_TIPOS_JUSTIFICACIONES);

        String fechaIni = (String) session.getAttribute(WebKeys.RANGO_FECHA_INI);
        String fechaFin = (String) session.getAttribute(WebKeys.RANGO_FECHA_FIN);
        System.out.println("EVENTO DE RANGO DE FECHAS INICIAL " + fechaIni);
        System.out.println("EVENTO DE RANGO DE FECHAS FINAL " + fechaFin);

        EvnAdministracionFenrir evento
                = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.CONSULTAR_JUSTIFICACIONES_USUARIO);

        if (isPerfil) {
            Usuario usuarioPerfil = (Usuario) session.getAttribute(AWAdministracionFenrir.PERFIL_USUARIO);
            evento.setUsuarioPerfil(usuarioPerfil);
            evento.setJustificaAdm(null);
        } else {
            justificaAdm.setAdmIdUsuario(Integer.parseInt(id));
            evento.setJustificaAdm(justificaAdm);
//            evento.setUsuarioPerfil(usuarioPerfil);
        }
        evento.setUsuario(usuario);

        evento.setFechaIni(fechaIni);
        evento.setFechaFin(fechaFin);
        evento.setTipoJust((String) session.getAttribute(CUsuario.TIPO_JUSTIFICACION));

        return evento;
    }

    private FileItem cargarArchivo(HttpServletRequest request) throws AccionWebException {

        
        HttpSession session = request.getSession();
        CargarArchivoExcepcion exceptionArchivo = new CargarArchivoExcepcion();

        int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB
        //int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
        int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
        FileItem fileItem = null;
        //EvnAdministracionFenrir eventoFenrir = null;

        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //Umbral de memoria
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            //ubicación temporal
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
            ServletFileUpload upload = new ServletFileUpload(factory);            
            
            //tamaño maximo solicitud (data + archivo)
            upload.setSizeMax(MAX_REQUEST_SIZE);
            String uploadPath = RUTA_DESTINO_ARCHIVO_JUSTIFICACION;
            
            //se crea el directorio si no existe
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            // Analiza el contenido de la solicitud para extraer datos de archivo
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);

            String accion = "";
            //FileItem fileItem = null;
            if (formItems != null && formItems.size() > 0) {

                // Itera sobre campos de formulario
                for (FileItem item : formItems) {
                    System.out.println("************** items carga pdf ******** " + item.getString());
                    if (item.getFieldName().equals(WebKeys.ACCION)) {
                        accion = item.getString().trim();
                        session.removeAttribute(WebKeys.ACCION);
                        session.setAttribute(WebKeys.ACCION, accion);
                    }
                    if (item.getFieldName().equals(CUsuario.ID_USUARIO)) {
                        session.removeAttribute(CUsuario.ID_USUARIO);
                        session.setAttribute(CUsuario.ID_USUARIO, item.getString());
                    } else if (item.getFieldName().equals(CUsuario.NOMBRE_USUARIO)) {
                        session.removeAttribute(CUsuario.NOMBRE_USUARIO);
                        session.setAttribute(CUsuario.NOMBRE_USUARIO, item.getString());
                    } else if (item.getFieldName().equals(CUsuario.APELLIDO1_USUARIO)) {
                        session.removeAttribute(CUsuario.APELLIDO1_USUARIO);
                        session.setAttribute(CUsuario.APELLIDO1_USUARIO, item.getString());
                    } else if (item.getFieldName().equals(CUsuario.APELLIDO2_USUARIO)) {
                        session.removeAttribute(CUsuario.APELLIDO2_USUARIO);
                        session.setAttribute(CUsuario.APELLIDO2_USUARIO, item.getString());
                    } else if (item.getFieldName().equals(CJustificaAdm.TIP_ID_TIPO)) {
                        session.removeAttribute(CJustificaAdm.TIP_ID_TIPO);
                        session.setAttribute(CJustificaAdm.TIP_ID_TIPO, item.getString());
                    } else if (item.getFieldName().equals(CJustificaAdm.ADM_DESCRIPCION)) {
                        session.removeAttribute(CJustificaAdm.ADM_DESCRIPCION);
                        session.setAttribute(CJustificaAdm.ADM_DESCRIPCION, item.getString());
                    } else if (item.getFieldName().equals(CJustificaAdm.ADM_MAX_LENGTH)) {
                        session.removeAttribute(CJustificaAdm.ADM_MAX_LENGTH);
                        session.setAttribute(CJustificaAdm.ADM_MAX_LENGTH, item.getString());
                    } //                    else if (item.getFieldName().equals(CJustificaAdm.FILE_NAME)) {
                    //                        session.removeAttribute(CJustificaAdm.FILE_NAME);
                    //                        session.setAttribute(CJustificaAdm.FILE_NAME, item.getName());
                    //                    } 
                    //Campos necesarios para la segunda historio (Perfiles)
                    else if (item.getFieldName().equals(WebKeys.ROL)) {
                        session.removeAttribute(WebKeys.ROL_PERFIL);
                        session.setAttribute(WebKeys.ROL_PERFIL, item.getString());
                    } else if (item.getFieldName().equals(WebKeys.ESTACION)) {
                        session.removeAttribute(WebKeys.ESTACION);
                        session.setAttribute(WebKeys.ESTACION, item.getString());
                    } else if (item.getFieldName().equals(WebKeys.ESTADO_REL_ROL_ESTACION)) {
                        session.removeAttribute(WebKeys.ESTADO_REL_ROL_ESTACION);
                        session.setAttribute(WebKeys.ESTADO_REL_ROL_ESTACION, item.getString());
                    } else if (item.getFieldName().equals(AWAdministracionFenrir.LISTA_ROLES_PERFIL)) {
                        session.removeAttribute(AWAdministracionFenrir.LISTA_ROLES_PERFIL);
                        session.setAttribute(AWAdministracionFenrir.LISTA_ROLES_PERFIL, item.getString());
                    } else if (item.getFieldName().equals(AWAdministracionFenrir.LISTA_ESTACIONES_PERFIL)) {
                        session.removeAttribute(AWAdministracionFenrir.LISTA_ESTACIONES_PERFIL);
                        session.setAttribute(AWAdministracionFenrir.LISTA_ESTACIONES_PERFIL, item.getString());
                    } else if (item.getFieldName().equals(CUsuario.TIPO_JUSTIFICACION)) {
                        session.removeAttribute(CUsuario.TIPO_JUSTIFICACION);
                        session.setAttribute(CUsuario.TIPO_JUSTIFICACION, item.getString());
                    } else if (item.getFieldName().equals(WebKeys.ROL_ESTACION)) {
                        session.removeAttribute(WebKeys.ROL_ESTACION);
                        session.setAttribute(WebKeys.ROL_ESTACION, item.getString());
                    } else if (item.getFieldName().equals(WebKeys.ROL_ESTACION_ESTADO)) {
                        session.removeAttribute(WebKeys.ROL_ESTACION_ESTADO);
                        session.setAttribute(WebKeys.ROL_ESTACION_ESTADO, item.getString());
                    } else if (item.getFieldName().equals(WebKeys.RANGO_FECHA_INI)) {
                        session.removeAttribute(WebKeys.RANGO_FECHA_INI);
                        session.setAttribute(WebKeys.RANGO_FECHA_INI, item.getString());
                    } else if (item.getFieldName().equals(WebKeys.RANGO_FECHA_FIN)) {
                        session.removeAttribute(WebKeys.RANGO_FECHA_FIN);
                        session.setAttribute(WebKeys.RANGO_FECHA_FIN, item.getString());
                    }

                    // Sólo procesa campos que no son campos de formulario
                    if (!item.isFormField()) {
                                
                        String fileName = new File(item.getName()).getName();
                        String fileSize = item.getSize() + "";
                        String fileType = item.getContentType();

                        session.removeAttribute(CJustificaAdm.FILE_NAME);
                        session.setAttribute(CJustificaAdm.FILE_NAME, fileName);

                        session.removeAttribute(CArchivosJustifica.FILE_SIZE);
                        session.setAttribute(CArchivosJustifica.FILE_SIZE, fileSize);

                        session.removeAttribute(CArchivosJustifica.FILE_TYPE);
                        session.setAttribute(CArchivosJustifica.FILE_TYPE, fileType);
                        
                        //String filePath = uploadPath + fileName;
                        //File storeFile = new File(filePath);
                        String format = "none";

                        format = FilenameUtils.getExtension(fileName);
                        session.removeAttribute(CArchivosJustifica.FILE_EXTENSION);
                        session.setAttribute(CArchivosJustifica.FILE_EXTENSION, format);

                        fileItem = item;
                        //System.out.println("RESOLVER MULTIPART " + accion);
                        //return actualizarDatosUsuario(request, false, item);
                        // Guarda el archivo en el disco
                        //item.write(storeFile);
                    }
                }
            }
            System.out.println("ACCION EN SESSION: " + session.getAttribute(WebKeys.ACCION));
            System.out.println("RESOLVER MULTIPART " + accion);

        } catch (FileUploadException e) {
            exceptionArchivo.addError("Ocurrió un error cargando el archivo");
        }
        return fileItem;

    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de
     * campos de captura para una forma de pago
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información de
     * los campos de captura.
     * @throws AccionWebException
     */
    private EvnAdministracionFenrir consultarCamposCaptura(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        String idTipoPago = (String) request.getParameter(CTipoPago.ID_TIPO_PAGO);
        request.getSession().setAttribute(CTipoPago.ID_TIPO_PAGO, idTipoPago);

        ValidacionFormasPagoException exception = new ValidacionFormasPagoException();

        if (idTipoPago.equals(WebKeys.SIN_SELECCIONAR)) {
            session.removeAttribute(AWAdministracionFenrir.LISTA_CAMPOS_CAPTURA);
            exception.addError("Debe seleccionar una forma de pago.");
        }

        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }

        TipoPago tipoPago = new TipoPago();
        tipoPago.setIdTipoDocPago(Long.parseLong(request.getParameter(CTipoPago.ID_TIPO_PAGO)));

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnAdministracionFenrir evento
                = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.CONSULTAR_CAMPOS_CAPTURA);
        evento.setTipoPago(tipoPago);

        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de actualizacion o
     * adicion relaciones entre formas de pago y campos de captura
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información de
     * los campos de captura.
     * @throws AccionWebException
     */
    private EvnAdministracionFenrir actualizarCamposCaptura(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        String idTipoPago = (String) request.getParameter(CTipoPago.ID_TIPO_PAGO);
        request.getSession().setAttribute(CTipoPago.ID_TIPO_PAGO, idTipoPago);

        String idRelCampoForma = (String) request.getParameter(CTipoPago.ID_REL_FORMA_CAMPOS);
        String idCampo = (String) request.getParameter(CTipoPago.ID_CAMPO_CAPTURA);
        int estado = Integer.parseInt(request.getParameter(CTipoPago.ESTADO_CAMPO_CAPTURA));

        System.out.println("FORMA PAGO: " + idTipoPago + " REL CAMPO FORMA: " + idRelCampoForma + " ID CAMPO: " + idCampo + " ESTADO: " + estado);

        ValidacionFormasPagoException exception = new ValidacionFormasPagoException();

        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }

        TipoPago tipoPago = new TipoPago();
        tipoPago.setIdTipoDocPago(Long.parseLong(idTipoPago));

        FormaPagoCampos formaPagoCampos = new FormaPagoCampos();
        formaPagoCampos.setIdFormaPagoCampos(Integer.parseInt(idRelCampoForma));
        formaPagoCampos.setIdFormaPago(Integer.parseInt(idTipoPago));
        formaPagoCampos.setIdCamposCaptura(Integer.parseInt(idCampo));
        if (estado > 0) {
            formaPagoCampos.setEstado(false);
        } else {
            formaPagoCampos.setEstado(true);
        }

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnAdministracionFenrir evento
                = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.ACTUALIZAR_ESTADO_CAMPOS_CAPTURA);
        evento.setFormaPagoCampos(formaPagoCampos);
        evento.setTipoPago(tipoPago);

        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento que cargara los
     * campos de captura disponibles para la creación de una forma de pago
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información de
     * los campos de captura.
     * @throws AccionWebException
     */
    private EvnAdministracionFenrir formCrearFormaPago(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnAdministracionFenrir evento
                = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.CARGAR_CAMPOS_CAPTURA);

        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de creacion de la
     * forma de pago y a su vez asociar los campos de captura correspondientes
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información de
     * la forma de pago creada
     * @throws AccionWebException
     */
    private EvnAdministracionFenrir crearFormaPago(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        ValidacionFormasPagoException exception = new ValidacionFormasPagoException();

        String nombreFormaPago = (String) request.getParameter(CTipoPago.NOMBRE_TIPO_PAGO);
        request.getSession().setAttribute(CTipoPago.NOMBRE_TIPO_PAGO, nombreFormaPago);

        String camposCaptura = request.getParameter(WebKeys.CAMPOS_CAPTURA);

        if (nombreFormaPago.length() <= 0) {
            exception.addError("Debe diligenciar un nombre para la nueva forma de pago.");
        }

        if (camposCaptura.length() <= 0) {
            exception.addError("Debe seleccionar por lo menos un campo de captura para la forma de pago");
        }

        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnAdministracionFenrir evento
                = new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.CREAR_FORMA_PAGO);
        evento.setNombreFormaPago(nombreFormaPago);
        evento.setCamposCaptura(camposCaptura);

        return evento;
    }

}

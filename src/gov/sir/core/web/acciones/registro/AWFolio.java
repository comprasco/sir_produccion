package gov.sir.core.web.acciones.registro;

import gov.sir.core.eventos.comun.EvnCiudadano;
import gov.sir.core.eventos.comun.EvnRespCiudadano;
import gov.sir.core.eventos.registro.EvnFolio;
import gov.sir.core.eventos.registro.EvnRespFolio;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Complementacion;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.Direccion;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.Eje;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.NaturalezaJuridica;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.TipoAnotacion;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoPredio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoFolio;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.ZonaRegistral;
import gov.sir.core.negocio.modelo.constantes.CAnotacionCiudadano;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema;
import gov.sir.core.negocio.modelo.constantes.CDireccion;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.CSalvedadAnotacion;
import gov.sir.core.negocio.modelo.constantes.CSalvedadFolio;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro;
import gov.sir.core.negocio.modelo.constantes.CTipoAnotacion;
import gov.sir.core.negocio.modelo.constantes.CTipoDocumento;
import gov.sir.core.negocio.modelo.constantes.CTipoPredio;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.correccion.AWModificarFolio;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.FolioInvalidoException;
import gov.sir.core.web.acciones.excepciones.ParametroInvalidoException;
import gov.sir.core.web.acciones.excepciones.ValidacionParamentrosCrearFolioException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoEdicionCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoSegregacionCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosEdicionCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.ValidacionDatosOficinaOrigen;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import java.math.BigDecimal;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Esta accion web se encarga de generar eventos de negocio relacionados con la
 * gestión de folios. REQUERIMIENTOS ASOCIADOS: 9. Asocia matriculas 23. Crear
 * folios 31. Grabacion temporal 34. Ver detalles folios 38. Englobar 39.
 * Desenglobar 47. Escoger radicaciones y folios asociados
 *
 * @author mmunoz
 * @author gvillal
 * @author mortiz
 */
public class AWFolio extends SoporteAccionWeb {

    /**
     * Constante que indica que se desea consultar los detalles de un folio
     */
    public static final String CONSULTA = "CONSULTA";
    public static final String OBTENER_TIPOS_OFICINA = "OBTENER_TIPOS_OFICINA";

    /**
     * Constante que indica que se desea obtener una lista de folios que cumple
     * un criterio
     */
    public static final String BUSCAR = "BUSCAR";

    //TODO decidir si se hacen transferObject que permitan definir criterios de
    //busqueda complejos de folios y radicaciones
    /**
     * Constante que indica que se desea crear un folio
     */
    public static final String CREAR = "CREAR";

    /**
     * Constante que indica que se desea grabar en temporal las anotaciones de
     * un folio
     */
    public static final String GRABAR_TMP = "GRABAR_TMP";

    /**
     * Constante que indica que se desae finalizar la creacion de un
     * folio-->avanza el turno.*
     */
    public static final String CREACION_FOLIO_FINALIZADO = "CREACION_FOLIO_FINALIZADO";

    /**
     * Constante que indica que se desea registrar la creacion un folio
     */
    public static final String REGISTRAR_CREACION_FOLIO = "REGISTRAR_CREACION_FOLIO";
    public static final String MOSTRAR_FOLIO = "MOSTRAR_FOLIO";
    public static final String REVISION_INICIAL_CONFIRMAR = "REVISION_INICIAL_CONFIRMAR";
    public static final String REVISION_INICIAL_NEGAR = "REVISION_INICIAL_NEGAR";
    public static final String REVISION_CONFIRMAR = "REVISION_CONFIRMAR";
    public static final String REVISION_NEGAR = "REVISION_NEGAR";
    public static final String CANCELAR_EDICION = "CANCELAR_EDICION";
    public static final String GRABAR_EDICION = "GRABAR_EDICION";
    public final static String REFRESCAR_EDICION = "REFRESCAR_EDICION";
    public static final String AGREGAR_VARIOS_CIUDADANOS_EDICION = "AGREGAR_VARIOS_CIUDADANOS_EDICION";
    public final static String VALIDAR_CIUDADANO_EDICION = "VALIDAR_CIUDADANO_EDICION";

    /**
     * Constante que indica que se va a agregar un registro en la tabla de
     * agragar ciudadanos*
     */
    public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION";

    /**
     * Constante que indica que se va a remover un registro en la tabla de
     * agragar ciudadanos*
     */
    public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION";

    /**
     * Constante que indica que se desea crear un folio desenglobándolo desde
     * otro
     */
    public static final String DESENGLOBAR = "DESENGLOBAR";

    /**
     * Constante que indica que se desea crear un folio englobando una lista de
     * folios existentes
     */
    public static final String ENGLOBAR = "ENGLOBAR";

    /**
     * Constante que identifica que el usuario ha llenado una anotacion y la va
     * agregar a la lista de anotaciones
     */
    public static final String AGREGAR_ANOTACION = "AGREGAR_ANOTACION";

    /**
     * Constante que identifica que el usuario va a eliminar una anotacion a la
     * lista de anotaciones
     */
    public static final String ELIMINAR_ANOTACION = "ELIMINAR_ANOTACION";

    /**
     * Constante que identifica que el usuario ha llenado una persona en una
     * anotacion y la va agregar a la lista de ciudadanos
     */
    public static final String AGREGAR_CIUDADANO = "AGREGAR_CIUDADANO";

    /**
     * Constante que identifica *
     */
    public static final String PRESERVAR_INFO = "PRESERVAR_INFO";

    /**
     * Constante que identifica que el usuario va a eliminar una persona a la
     * lista de ciudadanos
     */
    public static final String ELIMINAR_CIUDADANO = "ELIMINAR_CIUDADANO";

    /**
     * Constante que identifica que el usuario va a eliminar una persona a la
     * lista de ciudadanos en edicion temporal
     */
    public static final String ELIMINAR_CIUDADANO_EDICION = "ELIMINAR_CIUDADANO_EDICION";

    /**
     * Esta constante identifica el numero de algun objeto que se quiere
     * eliminar
     */
    public static final String POSICION = "POSICION";

    /**
     * Esta constante identifica el numero de algun objeto que se quiere
     * modificar
     */
    public static final String NUM_ANOTACION_TEMPORAL = "NUM_ANOTACION_TEMPORAL";

    /**
     * Esta constante identifica que el usuario va modificar una anotacion
     */
    public static final String EDITAR_ANOTACION = "EDITAR_ANOTACION";

    /**
     * Esta constante identifica el prefijo utilizado para que el popup pueda
     * regrezar los valores a la ventana que lo abrio
     */
    public static final String FOLIO_LOCACION = "FOLIO_LOCACION";

    /**
     * Constante que indica que se usa en el request para obtener el numero de
     * registros de la tabla de adicion de ciudadanos a la anotacion.*
     */
    public static final String NUM_REGISTROS_TABLA_CIUDADANOS = "NUM_REGISTROS_TABLA_CIUDADANOS";

    /**
     * Constante que identifica que el usuario ha llenado una DIRECCION en el
     * folio
     */
    public static final String AGREGAR_DIRECCION = "AGREGAR_DIRECCION";

    /**
     * Constante que identifica que el usuario quiere quitar una Dirección al
     * folio
     */
    public static final String ELIMINAR_DIRECCION = "ELIMINAR_DIRECCION";

    /**
     * HELPER DE VARIOS CIUDADANOS
     */
    public static final int DEFAULT_NUM_CIUDADANOS_TABLA = 2;

    /**
     * Aca se guarda la session global para la clase
     */
    private HttpSession session;

    /**
     * Variable donde se guarda la accion enviada en el request
     */
    private String accion;

    /**
     * Colocar todos los parametros que llegaron en el request en sesión
     *
     * @param request
     */
    private void preservarParametros(HttpServletRequest request) {
        Enumeration parametrosEnum = request.getParameterNames();
        session = request.getSession();

        while (parametrosEnum.hasMoreElements()) {
            String parametro = (String) parametrosEnum.nextElement();
            String valorParametro = request.getParameter(parametro);

            if ((valorParametro != null) && (valorParametro.length() > 0)) {
                session.setAttribute(parametro, valorParametro);
            }
        }
    }

    /**
     * Método principal de esta acción web. Aqui se realiza toda la lógica
     * requerida de validación y de generación de eventos de negocio.
     *
     * @param request trae la informacion del formulario
     * @throws AccionWebException cuando ocurre un error
     * @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier
     * otro caso
     */
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        session = request.getSession();
        accion = request.getParameter(WebKeys.ACCION);

        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion valida");
        }

        if (request.getSession().getAttribute(WebKeys.VISTA_PARA_REGRESAR) != null) {
            request.getSession().setAttribute(SMARTKeys.VISTA_ACTUAL, request.getSession().getAttribute(WebKeys.VISTA_PARA_REGRESAR));
            request.getSession().removeAttribute(WebKeys.VISTA_PARA_REGRESAR);
        }

        this.preservarParametros(request);

        //      Varios ciudadanos
        if (accion.equals(AWCalificacion.AGREGAR_VARIOS_CIUDADANOS)) {
            return agregarVariosCiudadanos(request, accion);
        } else if (accion.equals(
                AWCalificacion.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS)) {
            return aumentarNumeroVariosCiudadanos(request);
        } else if (accion.equals(
                AWCalificacion.REMOVER_1_REGISTRO_TABLA_CIUDADANOS)) {
            return disminuirNumeroVariosCiudadanos(request);
        } else if (accion.equals(AWCalificacion.ELIMINAR_CIUDADANO_ANOTACION)) {
            return eliminarCiudadano(request);
        } else if (accion.equals(AWCalificacion.REFRESCAR_ANOTACION)) {
            return refrescarAnotacion(request);
        } else if (accion.equals(AWCalificacion.VALIDAR_CIUDADANO)) {
            return this.validarCiudadano(request);
        }

        if (accion.equals(MOSTRAR_FOLIO)) {
            return mostrarFolio(request);
        } else if (accion.equals(CREAR)) {
            return crearFolio(request);
        } else if (accion.equals(GRABAR_TMP)) {
            return grabarTemporal(request);
        } else if (accion.equals(REGISTRAR_CREACION_FOLIO)) {
            return registrarCreacionFolio(request);
        } else if (accion.equals(CONSULTA)) {
            return consultaFolio(request);
        } else if (accion.equals(AGREGAR_ANOTACION)) {
            return agregarAnotacion(request);
        } else if (accion.equals(ELIMINAR_ANOTACION)) {
            return eliminarAnotacion(request);
        } else if (accion.equals(AGREGAR_CIUDADANO)) {
            return agregarCiudadano(request);
        } else if (accion.equals(PRESERVAR_INFO)) {
            return preservarInfo(request);
        } else if (accion.equals(ELIMINAR_CIUDADANO)) {
            return eliminarCiudadano(request);
        } else if (accion.equals(DESENGLOBAR)) {
            return desenglobarFolio(request);
        } else if (accion.equals(BUSCAR)) {
            return buscarFolios(request);
        } else if (accion.equals(REVISION_INICIAL_CONFIRMAR)) {
            return revisionInicialConfirmar(request);
        } else if (accion.equals(REVISION_INICIAL_NEGAR)) {
            return revisionInicialNegar(request);
        } else if (accion.equals(REVISION_CONFIRMAR)) {
            return revisionConfirmar(request);
        } else if (accion.equals(REVISION_NEGAR)) {
            return revisionNegar(request);
        } else if (accion.equals(EDITAR_ANOTACION)) {
            return editarAnotacion(request);
        } else if (accion.equals(GRABAR_EDICION)) {
            return grabarEdicion(request);
        } else if (accion.equals(REFRESCAR_EDICION)) {
            return refrescarEdicion(request);
        } else if (accion.equals(CANCELAR_EDICION)) {
            return cancelarEdicion(request);
        } else if (accion.equals(AGREGAR_VARIOS_CIUDADANOS_EDICION)) {
            return agregarVariosCiudadanosEdicion(request);
        } else if (accion.equals(AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION)) {
            return aumentarNumeroVariosCiudadanos(request);
        } else if (accion.equals(REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION)) {
            return disminuirNumeroVariosCiudadanos(request);
        } else if (accion.equals(ELIMINAR_CIUDADANO_EDICION)) {
            return eliminarCiudadanoEdicion(request);
        } else if (accion.equals(VALIDAR_CIUDADANO_EDICION)) {
            return validarCiudadano(request);
        } else if (accion.equals(AGREGAR_DIRECCION)) {
            return agregarDireccion(request);
        } else if (accion.equals(ELIMINAR_DIRECCION)) {
            return eliminarDireccion(request);
        } else {
            throw new AccionInvalidaException("La accion " + accion
                    + " no es valida.");
        }
    }

    /**
     * Valida un ciudadano en anotación, recibe el request, crea el objeto
     * ciudadano y lanza un evento de negocio
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento validarCiudadano(HttpServletRequest request)
            throws AccionWebException {
        //eliminarInfoBasicaAnotacion(request);
        Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioArq = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosCiudadanoCalificacionException exception = new ValidacionParametrosCiudadanoCalificacionException();
        String ver = request.getParameter(CAnotacionCiudadano.SECUENCIA);
        preservarInfoBasicaVariosCiudadanos(request);
        preservarInfoAnotacion(request);

        String tipoDoc = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA
                + ver);

        if ((tipoDoc == null) || tipoDoc.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError(
                    "Debe escoger un tipo de identificación para la persona en la anotacion");
        }

        String numDoc = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA
                + ver);

        if ((numDoc == null) || numDoc.equals("")) {
            exception.addError("Debe digitar un número de identificación");
        }

        if (!exception.getErrores().isEmpty()) {
            preservarInfo(request);
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        request.getSession().setAttribute(CFolio.ANOTACION_NUM_ID_PERSONA
                + ver, numDoc);
        request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO,
                new Boolean(true));

        Ciudadano ciud = new Ciudadano();
        ciud.setDocumento(numDoc);
        ciud.setTipoDoc(tipoDoc);

        //Se setea el circulo del ciudadano
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        ciud.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

        return new EvnCiudadano(usuarioArq,
                EvnCiudadano.CONSULTAR_CIUDADANO_ANOTACION, ciud);
    }

    /**
     * @param request
     * @return
     */
    private Evento refrescarAnotacion(HttpServletRequest request) {
        //eliminarInfoBasicaAnotacion(request);
        String ver = request.getParameter("SECUENCIA");
        request.getSession().setAttribute("SECUENCIA", ver);
        request.getSession().setAttribute("CAMBIO", "SI");
        preservarInfoAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

        return null;
    }

    /**
     * @param request
     * @param exception
     * @return
     * @throws AccionWebException
     *
     *
     */
    private Evento agregarVariosCiudadanos(HttpServletRequest request,
            ValidacionParametrosException exception) throws AccionWebException {
        /*
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoCancelacion(request);
         */
        preservarInfoBasicaVariosCiudadanos(request); //varios

        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new Vector();
        }

        int numCiudadanosTabla = this.numeroRegistrosTablaAgregarCiudadanos(request);

        for (int i = 0; i < numCiudadanosTabla; i++) {
            if (agregoPersona(request, i)) {
                int b = i + 1;
                boolean datosBien = true;

                String tipoPersona = request.getParameter(CFolio.ANOTACION_TIPO_PERSONA + i);

                if ((tipoPersona == null) || tipoPersona.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError("Debe escojer un tipo de persona para el ciudadano " + b + " en la anotacion");
                    datosBien = false;
                }

                String tipoId = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA
                        + i);

                if ((tipoId == null) || tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError(
                            "Debe escojer un tipo de identificación para la persona "
                            + b + " en la anotacion");
                    datosBien = false;
                }

                String sexo = request.getParameter(CFolio.ANOTACION_SEXO_PERSONA + i);

                if (!tipoPersona.equals(CCiudadano.TIPO_PERSONA_JURIDICA)) {
                    if ((sexo == null) || sexo.equals(WebKeys.SIN_SELECCIONAR)) {
                        exception.addError("Debe escojer un tipo de sexo para la persona en la anotacion");
                        datosBien = false;
                    }
                }

                String numId = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA
                        + i);
                String apellido1 = request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA
                        + i);
                String apellido2 = request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA
                        + i);
                String nombres = request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA
                        + i);

                if (tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)) {

                    if (tipoPersona.equals(CCiudadano.TIPO_PERSONA_NATURAL)) {
                        if (apellido1 == null || apellido1.trim().equals("")) {
                            exception.addError("Primer apellido invalido para la persona " + b);
                            datosBien = false;
                        }
                    } else {
                        if (nombres == null || nombres.trim().equals("")) {
                            exception.addError("Razon social invalida para el ciudadano " + b);
                            datosBien = false;
                        }
                    }

                } else {
                    if ((numId == null) || numId.trim().equals("")) {
                        exception.addError(
                                "Documento Inválido para la persona " + b);
                        datosBien = false;
                    }

                    double valorId = 0.0d;

                    if (numId == null) {
                        exception.addError(
                                "El número de identificación de la persona " + b
                                + " en la anotación es inválido");
                        datosBien = false;
                    } else {
                        if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_NIT)) {
                            try {
                                valorId = Double.parseDouble(numId);

                                if (valorId <= 0) {
                                    exception.addError(
                                            "El valor del documento de la persona "
                                            + b + " no puede ser negativo o cero");
                                    datosBien = false;
                                }
                            } catch (NumberFormatException e) {
                                exception.addError(
                                        "El número de identificación de la persona "
                                        + b + " en la anotación es inválido");
                                datosBien = false;
                            }
                        }
                    }

                    if (tipoId.equals(CCiudadano.TIPO_DOC_ID_NIT)) {
                        if ((nombres == null) || nombres.trim().equals("")) {
                            exception.addError(
                                    "Razón social inválida del campo " + b);
                            datosBien = false;
                        }
                    } else {
                        if ((nombres == null) || nombres.equals("")) {
                            exception.addError(
                                    "Debe ingresar el nombre de la persona " + b
                                    + " en la anotación");
                            datosBien = false;
                        }

                        if ((apellido1 == null) || apellido1.equals("")) {
                            exception.addError(
                                    "Debe ingresar el primer apellido de la persona "
                                    + b + " en la anotación");
                            datosBien = false;
                        }
                    }
                }

                String tipoIntervencion = request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION
                        + i);

                if ((tipoIntervencion == null)
                        || tipoIntervencion.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError(
                            "Debe ingresar un tipo de intervención para la persona "
                            + b + " en la anotación");
                    datosBien = false;
                }

                AnotacionCiudadano c;
                Iterator ic = ciudadanos.iterator();

                while (ic.hasNext()) {
                    c = (AnotacionCiudadano) ic.next();

                    if ((tipoIntervencion.equals(c.getRolPersona()))
                            && (numId.equals(c.getCiudadano().getDocumento()))
                            && (tipoId.equals(c.getCiudadano().getTipoDoc()))) {
                        exception.addError(
                                "La persona no puede tener dos veces el mismo rol");
                        datosBien = false;
                    }
                }

                String participacion = request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i);
                if ((participacion != null) && !participacion.equals("")) {
                    if (participacion.length() > 50) {
                        exception.addError("El campo participación de una persona no puede tener mas de 50 caracteres");
                        datosBien = false;
                    }
                }

                if (datosBien) {
                    AnotacionCiudadano anotacionCiudadano = new AnotacionCiudadano();
                    Ciudadano ciudadano = new Ciudadano();

                    if (apellido1 != null) {
                        ciudadano.setApellido1(apellido1);
                    }

                    if (apellido2 != null) {
                        ciudadano.setApellido2(apellido2);
                    }

                    ciudadano.setNombre(nombres);

                    if (numId != null) {
                        ciudadano.setDocumento(numId);
                    }

                    //Se setea el circulo del ciudadano
                    Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
                    ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

                    ciudadano.setTipoDoc(tipoId);
                    ciudadano.setTipoPersona(tipoPersona);
                    ciudadano.setSexo(sexo);
                    anotacionCiudadano.setCiudadano(ciudadano);
                    anotacionCiudadano.setRolPersona(tipoIntervencion);
                    anotacionCiudadano.setParticipacion(participacion);

                    String marcaPropietario = request.getParameter(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION
                            + i);

                    if ((marcaPropietario == null)
                            || marcaPropietario.equals("")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.NO_PROPIETARIO);
                    } else if (marcaPropietario.equals("X")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DERECHO_REAL_DOMINIO);
                    } else if (marcaPropietario.equals("I")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DOMINIO_INCOMPLETO);
                    }

                    ciudadanos.add(anotacionCiudadano);
                }
            }
        }

        if (exception.getErrores().size() > 0) {
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        eliminarInfoBasicaVariosCiudadanos(request);

        //if (!existeEnListaCiudadanos(anotacionCiudadano, ciudadanos)) {
        List listaCompletaCiudadanos = new Vector();

        listaCompletaCiudadanos.addAll(ciudadanos);

        request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION,
                listaCompletaCiudadanos);

        /*} else {
            exception.addError("El ciudadano que esta intentado ingresar ya se encuentra registrado en la anotacion");
            throw exception;
        }*/
 /*      String vista=(String)request.getSession().getAttribute(WebKeys.ULTIMA_VISTA_TEMPORAL);
                if (vista!=null){
                    request.getSession().setAttribute(SMARTKeys.VISTA_ACTUAL,vista);
                }
         */
        return null;
    }

    /**
     * @param request
     * @param accion1
     * @return
     * @throws AccionWebException
     */
    private Evento agregarVariosCiudadanos(HttpServletRequest request,
            String accion1) throws AccionWebException {
        ValidacionParametrosException exception;

        if (accion1.equals(AWCalificacion.AGREGAR_VARIOS_CIUDADANOS_SEGREGACION)) {
            exception = new ValidacionParametrosCiudadanoSegregacionCalificacionException();
        } else if (accion1.equals(AWCalificacion.AGREGAR_VARIOS_CIUDADANOS)) {
            exception = new ValidacionParametrosCiudadanoCalificacionException();
        } else {
            exception = new ValidacionParametrosException();
        }

        try {
            return agregarVariosCiudadanos(request, exception);
        } catch (AccionWebException e) {
            throw e;
        } catch (Throwable t) {
            t.printStackTrace();
            throw new AccionWebException(t.getLocalizedMessage());
        }
    }

    /**
     * @param request
     * @return
     */
    private Evento revisionNegar(HttpServletRequest request) {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);

        return new EvnFolio(usuarioAuriga, EvnFolio.REVISION_INIC_NEGAR, turno,
                fase, EvnFolio.REVISION_INIC_NEGAR);
    }

    /**
     * @param request
     * @return
     */
    private Evento revisionConfirmar(HttpServletRequest request) {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);

        return new EvnFolio(usuarioAuriga, EvnFolio.REVISION_INIC_NEGAR, turno,
                fase, EvnFolio.REVISION_INIC_NEGAR);
    }

    /**
     * @param request
     * @return
     */
    private Evento revisionInicialNegar(HttpServletRequest request) {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);

        return new EvnFolio(usuarioAuriga, EvnFolio.REVISION_INIC_NEGAR, turno,
                fase, EvnFolio.REVISION_INIC_NEGAR);
    }

    /**
     * @param request
     * @return
     */
    private Evento revisionInicialConfirmar(HttpServletRequest request) {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);

        return new EvnFolio(usuarioAuriga, EvnFolio.REVISION_INIC_CONFIRMAR,
                turno, fase, EvnFolio.REVISION_INIC_CONFIRMAR);
    }

    /**
     * @param request
     * @return
     */
    private EvnFolio registrarCreacionFolio(HttpServletRequest request)
            throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);

        if (folio == null) {
            throw new FolioInvalidoException(
                    "No se encontro en sesion el folio que se queria crear");
        }

        return new EvnFolio(usuarioAuriga, EvnFolio.CREACION_FOLIO_CREADO,
                turno, fase, EvnFolio.CREACION_FOLIO_CREADO, folio, usuario);
    }

    /**
     * @param request
     * @return
     */
    private Evento preservarInfo(HttpServletRequest request) {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoAntiguoSistemaAnotacion(request);
        preservarInfoBasicaCiudadano(request);

        return null;
    }

    private EvnFolio consultaFolio(HttpServletRequest request)
            throws AccionWebException {
        //TODO: Genera un evento del tipo CONSULTA_FOLIO
        return null;
    }

    private EvnFolio mostrarFolio(HttpServletRequest request)
            throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);

        List solFolios = null;

        // interceptor: Solicitud Correcciones
        // en el caso de solicitud correcciones las solicitudes pasan a este punto
        // para crear una sola solicitud; no se carga en sesion ningun folio
        Solicitud solicitud = turno.getSolicitud();

        if (solicitud instanceof SolicitudCorreccion) {
            solFolios = null;
        } else {
            solFolios = turno.getSolicitud().getSolicitudFolios();
        }

        if (solFolios != null) {
            if (solFolios.size() > 0) {
                int index = solFolios.size() - 1;
                SolicitudFolio solFolio = (SolicitudFolio) solFolios.get(index);
                Folio folio = solFolio.getFolio();
                String idMatricula = folio.getIdMatricula();

                if (idMatricula != null) {
                    session.setAttribute(CFolio.FOLIO_ID_MATRICULA, idMatricula);
                }

                if (folio != null) {
                    //String matricula = folio.getIdMatricula();
                    return new EvnFolio(usuarioAuriga, turno,
                            EvnFolio.CONSULTA_ID_USER, folio,
                            (Usuario) session.getAttribute(WebKeys.USUARIO));
                }
            }
        }

        return null;
    }

    /**
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnFolio crearFolio(HttpServletRequest request)
            throws AccionWebException {
        ValidacionParamentrosCrearFolioException exception = new ValidacionParamentrosCrearFolioException();

        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaCiudadano(request);
        preservarInfoAntiguoSistemaAnotacion(request);

        Folio folioSesion = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);

        if (turno == null) {
            throw new AccionWebException("No se encontro el turno en la sesion");
        }

        //SE RECUPERA LA INFORMACIÓN DEL FORMULARIO
        //String valorRadicacion = request.getParameter(CFolio.FOLIO_NUM_RADICACION);
        String valorDepartamento = request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO);

        if (valorDepartamento == null) {
            valorDepartamento = new String();
        }

        if (valorDepartamento.length() <= 0) {
            exception.addError("Debe seleccionar un departamento.");
        }

        String valorMunicipio = request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC);

        if (valorMunicipio == null) {
            valorMunicipio = new String();
        }

        if (valorMunicipio.length() <= 0) {
            exception.addError("Debe seleccionar un municipio valido.");
        }

        String valorVereda = request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA);

        if (valorVereda == null) {
            valorVereda = new String();
        }

        String valorTipoPredio = request.getParameter(CFolio.FOLIO_TIPO_PREDIO);

        if ((valorTipoPredio == null)
                || (valorTipoPredio.equals("SIN_SELECCIONAR"))) {
            exception.addError("Debe seleccionar un tipo de predio valido");
        }

        String valorNupre = request.getParameter(CFolio.FOLIO_NUPRE);

        String valorDeterminaInm = request.getParameter(CFolio.FOLIO_DETERMINACION_INMUEBLE);

        if ((valorDeterminaInm == null) || (valorTipoPredio.equals(WebKeys.SIN_SELECCIONAR))) {
            exception.addError("Debe seleccionar una determinaci&oacute;n de inmuble validad");
        }

        String valorPrivMetros = request.getParameter(CFolio.FOLIO_PRIVMETROS);

        String valorPrivCentimetros = request.getParameter(CFolio.FOLIO_PRIVCENTIMETROS);

        String valorConsMetros = request.getParameter(CFolio.FOLIO_CONSMETROS);

        String valorConsCentimetros = request.getParameter(CFolio.FOLIO_CONSCENTIMETROS);

        String valorCoeficiente = request.getParameter(CFolio.FOLIO_COEFICIENTE);

        String valorHectareas = request.getParameter(CFolio.FOLIO_HECTAREAS);
        String valorMetros = request.getParameter(CFolio.FOLIO_METROS);
        String valorCentimetros = request.getParameter(CFolio.FOLIO_CENTIMETROS);
        int unidadmedidaexception = 3;

        if (valorHectareas == null || valorHectareas.length() <= 0) {
            unidadmedidaexception--;
            valorHectareas = "0";
        }

        if (valorMetros == null || valorMetros.length() <= 0) {
            unidadmedidaexception--;
            valorMetros = "0";
        }

        if (valorCentimetros == null || valorCentimetros.length() <= 0) {
            unidadmedidaexception--;
            valorCentimetros = "0";
        }

        if (unidadmedidaexception == 0) {
            exception.addError("Debe digitar por lo menos una unidad de medida válida para el Area del Terreno");
        }

        boolean revisarVereda = true;

        if (valorTipoPredio.equals(CTipoPredio.TIPO_URBANO)) {
            revisarVereda = false;
        }

        if ((valorVereda.length() <= 0) && revisarVereda) {
            exception.addError("Debe seleccionar una vereda valida.");
        }

        String valorComplementacion = request.getParameter(CFolio.FOLIO_COMPLEMENTACION);
        if (valorComplementacion != null) {
            valorComplementacion = valorComplementacion.toUpperCase();
        }

        String idComplementacion = request.getParameter(CFolio.FOLIO_ID_COMPLEMENTACION);
        String tipoAccion = request.getParameter(CFolio.SELECCIONAR_FOLIO
                + AWModificarFolio.FOLIO_COMPLEMENTACION);

        if ((tipoAccion != null) && tipoAccion.equals(CFolio.ASOCIAR)) {
            if (idComplementacion == null) {
                exception.addError(
                        "Debe ingresar un identificador para la complementación válido");
            }
        }

        String valorLindero = request.getParameter(CFolio.FOLIO_LINDERO);

        if (valorLindero == null) {
            exception.addError("Debe ingresar informacion del lindero valida");
        }

        String valorCodCatastral = request.getParameter(CFolio.FOLIO_COD_CATASTRAL);

        if (valorCodCatastral == null) {
            exception.addError("Debe ingresar un codigo catastral valido");
        }

        //SE RECUPERAN LAS ANOTACIONES DE LA SESIÓN.
        List anotaciones = new ArrayList();
        List anotacionesTemp = null;
        anotacionesTemp = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);

        if (anotacionesTemp != null) {
            Iterator it = anotacionesTemp.iterator();

            while (it.hasNext()) {
                Anotacion anotacion = (Anotacion) it.next();
                anotaciones.add(anotacion);
            }
        }

        session.setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, anotaciones);

        if ((anotaciones == null) || (anotaciones.size() == 0)) {
            exception.addError("No se puede crear un folio sin anotaciones");
        } else {
            int tam = anotaciones.size();

            for (int i = 0; i < tam; i++) {
                Anotacion anota = (Anotacion) anotaciones.get(i);
                TipoAnotacion tipoAnota = new TipoAnotacion();
                tipoAnota.setIdTipoAnotacion(CTipoAnotacion.ESTANDAR);
                anota.setTipoAnotacion(tipoAnota);
            }
        }

        //SE RECUPERA EL FOLIO Y EL CIRCULO
        Folio folio = null;

        if (turno.getSolicitud() instanceof SolicitudCertificado) {
            folio = this.getFolioFromTurno(turno);
        } else {
            folio = new Folio();
        }

        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        //SE CARGA LA DIRECCIÓN TEMPORAL EN EL CASO DE QUE YA SE HAYA GUARDADO EL FOLIO POR PRIMERA VEZ
        Direccion direccionTemp = null;

        if ((folioSesion != null) && (folioSesion.getDirecciones() != null)
                && !folioSesion.getDirecciones().isEmpty()) {
            direccionTemp = (Direccion) folioSesion.getDirecciones().get(folioSesion.getDirecciones()
                    .size()
                    - 1);

            if (direccionTemp != null) {
                request.getSession().setAttribute(CDireccion.DIRECCION_TEMPORAL,
                        direccionTemp);
            }
        }

        //SE CREA UN NUEVO FOLIO SI NO EXISTE, DE LO CONTRARIO SE BORRAR LAS LISTAS, PARA QUE SEAN CARGADAS NUEVAMENTE
        //CON LAS QUE SE INGRESARON EN LA PÁGINA
        if (folio == null) {
            folio = new Folio();
        } else {
            this.removerAnotacionesFromFolio(folio);
            this.removerDireccionesFromFolio(folio);
        }

        //SE COMIENZAN A CARGAR LOS DATOS EN EL NUEVO FOLIO, CON LA NUEVA INFORMACIÓN
        folio.setFechaApertura(new Date(System.currentTimeMillis()));

        folio.setCodCatastral(valorCodCatastral);

        if (circulo == null) {
            circulo = new Circulo();
        }

        folio.setLindero(valorLindero);
        folio.setNupre(valorNupre);
        folio.setDeterminaInm(valorDeterminaInm);
        folio.setPrivMetros(valorMetros);
        folio.setPrivCentimetros(valorCentimetros);
        folio.setConsMetros(valorMetros);
        folio.setConsCentimetros(valorCentimetros);
        folio.setCoeficiente(valorCoeficiente);
        folio.setHectareas(valorHectareas);
        folio.setMetros(valorMetros);
        folio.setCentimetros(valorCentimetros);

        //SE LE INGRESA LA COMPLEMENTACIÓN AL FOLIO
        Complementacion comp = new Complementacion();

        String tipoComp = request.getParameter(CFolio.SELECCIONAR_FOLIO
                + AWModificarFolio.FOLIO_COMPLEMENTACION);

        if (tipoComp.equals(CFolio.NUEVA) || tipoComp.equals(CFolio.COPIAR)) {
            comp.setComplementacion(valorComplementacion);
        }

        if (tipoComp.equals(CFolio.ASOCIAR)) {
            comp.setIdComplementacion(idComplementacion);
            comp.setComplementacion(valorComplementacion);
        }

        folio.setComplementacion(comp);

        //SE INGRESA EL TIPO DE PREDIO
        List tiposPredio = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PREDIO);

        if (tiposPredio != null) {
            Iterator itPredios = tiposPredio.iterator();

            while (itPredios.hasNext()) {
                ElementoLista elementoTipoPredio = (ElementoLista) itPredios.next();

                if (elementoTipoPredio.getId().equals(valorTipoPredio)) {
                    TipoPredio tipoPredio = new TipoPredio();
                    tipoPredio.setIdPredio(valorTipoPredio);
                    tipoPredio.setNombre(elementoTipoPredio.getValor());
                    folio.setTipoPredio(tipoPredio);
                }
            }
        } else {
            exception.addError("La lista de los tipos de predio no se encontro");
        }

        ZonaRegistral zona = new ZonaRegistral();
        zona.setCirculo(circulo);

        Departamento departamento = new Departamento();
        departamento.setIdDepartamento(valorDepartamento);

        Municipio municipio = new Municipio();
        municipio.setIdMunicipio(valorMunicipio);

        municipio.setDepartamento(departamento);

        Vereda vereda = new Vereda();
        vereda.setIdDepartamento(valorDepartamento);
        vereda.setIdMunicipio(valorMunicipio);
        vereda.setIdVereda(valorVereda);
        vereda.setMunicipio(municipio);
        zona.setVereda(vereda);

        String idZonaRegistral = folio.getZonaRegistral().getIdZonaRegistral();
        zona.setIdZonaRegistral(idZonaRegistral);

        folio.setZonaRegistral(zona);

        Documento documento = new Documento();
        TipoDocumento tipoDoc = new TipoDocumento();

        tipoDoc.setIdTipoDocumento(CTipoDocumento.ID_TIPO_CERTIFICADO);
        documento.setFecha(new Date());
        documento.setTipoDocumento(tipoDoc);
        folio.setDocumento(documento);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        //SE LE INRESAN LAS NUEVAS DIRECCIONES AL FOLIO
        if (anotaciones != null) {
            Iterator itAnotaciones = anotaciones.iterator();

            while (itAnotaciones.hasNext()) {
                folio.addAnotacione((Anotacion) itAnotaciones.next());
            }
        }

        Solicitud solicitud = turno.getSolicitud();

        if (solicitud instanceof SolicitudRegistro) {
            SolicitudRegistro registro = (SolicitudRegistro) solicitud;
            folio.setDocumento(registro.getDocumento());
        }

        List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if ((direcciones != null) && (direcciones.size() > 0)) {
            Iterator it = direcciones.iterator();

            while (it.hasNext()) {
                Direccion direccion = (Direccion) it.next();
                folio.addDireccione(direccion);
            }
        }

        /*String valorEje1 = request.getParameter(CFolio.FOLIO_EJE1);
        if ((valorEje1.length() <= 0) || valorEje1.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe seleccionar el primer eje válido para la direccion");
        }

        String valorValor1 = request.getParameter(CFolio.FOLIO_VALOR1);
        if (valorValor1.length() <= 0) {
                exception.addError("Debe ingresar valor válido para el primer eje de la direccion");
        }

        String valorEje2 = request.getParameter(CFolio.FOLIO_EJE2);
        String valorValor2 = request.getParameter(CFolio.FOLIO_VALOR2);
        if ((valorEje2.length() <= 0) || valorEje2.equals(WebKeys.SIN_SELECCIONAR)) {
                valorEje2 = new String();
        } else {
                if (valorValor2.length() <= 0) {
                        exception.addError("Debe ingresar valor válido para el segundo eje  de la direccion");
                }
        }

        String complemento = request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR);*/
 /*if (complemento.length() <= 0) {
                exception.addError("Debe ingresar un complemento válido");
        }*/
 /*List ejes = (List) session.getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
        Direccion direccion = new Direccion();
        direccion.setEspecificacion(complemento);

        if (ejes != null) {
                Iterator itEje = ejes.iterator();

                while (itEje.hasNext()) {
                        ElementoLista elementoEje = (ElementoLista) itEje.next();
                        Eje eje;

                        if (elementoEje.getId().equals(valorEje1)) {
                                eje = new Eje();
                                eje.setIdEje(elementoEje.getId());
                                eje.setNombre(elementoEje.getValor());
                                direccion.setEje(eje);
                                direccion.setValorEje(valorValor1);
                        }

                        if (elementoEje.getId().equals(valorEje2)) {
                                eje = new Eje();
                                eje.setIdEje(elementoEje.getId());
                                eje.setNombre(elementoEje.getValor());
                                direccion.setEje1(eje);
                        }

                        if (valorValor2 != null) {
                                direccion.setValorEje1(valorValor2);
                        }
                }
        } else {
                exception.addError("La lista de los ejes no se encontro");
        }

        if (exception.getErrores().size() > 0) {
                throw exception;
        }*/
        //SE AGREGA LA NUEVA DIRECCIÓN SOLO SI LA DIRECCIÓN INICIAL NO ERA LA MISMA QUE LA VIENE EN EL REQUEST

        /*if(folioSesion==null || folioSesion.getDirecciones().size()==0){
                folio.addDireccione(direccion);
        }else if(!compararDirecciones(direccionTemp, direccion)){
                folio.addDireccione(direccion);
        }*/
        TurnoFolio tFolio = new TurnoFolio();
        tFolio.setTurno(turno);
        folio.addTurnosFolio(tFolio);
        folio.setEstado(null);

        session.setAttribute(WebKeys.FOLIO, folio);

        //session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        //session.removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        return null;
    }

    /**
     * Método que devuelve si dos folios son iguales o no, con el objeto de que
     * no se creen registros iguales de direcciones, cuando se esta editando la
     * hoja de ruta, sólo si los dos objetos son diferentes ingresa la nueva
     * dirección.
     *
     * @param direccion
     * @param direccionTemp
     * @return
     */
    public boolean compararDirecciones(Direccion direccion,
            Direccion direccionTemp) {
        if ((direccion == null) || (direccionTemp == null)) {
            return false;
        }

        if (((direccion.getEje() != null) && (direccionTemp.getEje() == null))
                || ((direccion.getEje() == null)
                && (direccionTemp.getEje() != null))
                || ((direccion.getEje() != null)
                && (direccionTemp.getEje() != null)
                && (direccion.getEje().getIdEje() != null)
                && (direccionTemp.getEje().getIdEje() != null)
                && !direccion.getEje().getIdEje().equals(direccionTemp.getEje()
                        .getIdEje()))) {
            return false;
        }

        if (((direccion.getEje1() != null)
                && (direccionTemp.getEje1() == null))
                || ((direccion.getEje1() == null)
                && (direccionTemp.getEje1() != null))
                || ((direccion.getEje1() != null)
                && (direccionTemp.getEje1() != null)
                && (direccion.getEje1().getIdEje() != null)
                && (direccionTemp.getEje1().getIdEje() != null)
                && !direccion.getEje1().getIdEje().equals(direccionTemp.getEje1()
                        .getIdEje()))) {
            return false;
        }

        if (((direccion.getValorEje() != null)
                && (direccionTemp.getValorEje() == null))
                || ((direccion.getValorEje() == null)
                && (direccionTemp.getValorEje() != null))
                || ((direccion.getValorEje() != null)
                && (direccionTemp.getValorEje() != null)
                && !direccion.getValorEje().equals(direccionTemp.getValorEje()))) {
            return false;
        }

        if (((direccion.getValorEje1() != null)
                && (direccionTemp.getValorEje1() == null))
                || ((direccion.getValorEje1() == null)
                && (direccionTemp.getValorEje1() != null))
                || ((direccion.getValorEje1() != null)
                && (direccionTemp.getValorEje1() != null)
                && !direccion.getValorEje1().equals(direccionTemp.getValorEje1()))) {
            return false;
        }

        if (((direccion.getEspecificacion() != null)
                && (direccionTemp.getEspecificacion() == null))
                || ((direccion.getEspecificacion() == null)
                && (direccionTemp.getEspecificacion() != null))
                || ((direccion.getEspecificacion() != null)
                && (direccionTemp.getEspecificacion() != null)
                && !direccion.getEspecificacion().equals(direccionTemp.getEspecificacion()))) {
            return false;
        }

        return true;
    }

    /**
     * Retorna el ultimo folio asociado al turno o null si no hay ninguno.
     *
     * @param turno
     * @return
     */
    private Folio getFolioFromTurno(Turno turno) {
        Folio folio = new Folio();
        Solicitud sol = turno.getSolicitud();
        List solicitudesFolio = null;
        SolicitudFolio solFol = null;

        if (sol != null) {
            solicitudesFolio = sol.getSolicitudFolios();
        }

        if (solicitudesFolio != null) {
            if (solicitudesFolio.size() > 0) {
                int index = solicitudesFolio.size() - 1;
                solFol = (SolicitudFolio) solicitudesFolio.get(index);
                folio = solFol.getFolio();
            }
        }

        return folio;
    }

    /**
     * Remueve las anotaciones del folio.
     *
     * @param folio
     */
    private void removerAnotacionesFromFolio(Folio folio) {
        Anotacion anotacion;
        List anotaciones = folio.getAnotaciones();

        if (anotaciones != null) {
            int max = anotaciones.size();

            for (int i = 0; i < max; i++) {
                anotacion = (Anotacion) anotaciones.get(0);
                folio.removeAnotacione(anotacion);
            }
        }
    }

    /**
     * Remueve las direcciones del folio.
     *
     * @param folio
     */
    private void removerDireccionesFromFolio(Folio folio) {
        Direccion anotacion;
        List direcciones = folio.getDirecciones();

        if (direcciones != null) {
            int max = direcciones.size();

            for (int i = 0; i < max; i++) {
                anotacion = (Direccion) direcciones.get(0);
                folio.removeDireccione(anotacion);
            }
        }
    }

    private void adicionarDireccionesToFolio(Folio folio,
            HttpServletRequest request)
            throws ValidacionParamentrosCrearFolioException {
        ValidacionParamentrosCrearFolioException exception = new ValidacionParamentrosCrearFolioException();

        String valorEje1 = request.getParameter(CFolio.FOLIO_EJE1);

        if (valorEje1 == null) {
            valorEje1 = new String();
        }

        if ((valorEje1.length() <= 0)
                || valorEje1.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError(
                    "Debe seleccionar el primer eje valido para la direccion");
        }

        String valorValor1 = request.getParameter(CFolio.FOLIO_VALOR1);

        if (valorValor1 == null) {
            valorValor1 = new String();
        }

        if (valorValor1.length() <= 0) {
            exception.addError(
                    "Debe ingresar valor valido para el primer eje de la direccion");
        }

        String valorEje2 = request.getParameter(CFolio.FOLIO_EJE2);

        if (valorEje2 == null) {
            valorEje2 = new String();
        }

        String valorValor2 = request.getParameter(CFolio.FOLIO_VALOR2);

        if (valorValor2 == null) {
            valorValor2 = new String();
        }

        if ((valorEje2.length() <= 0)
                || valorEje2.equals(WebKeys.SIN_SELECCIONAR)) {
            valorEje2 = new String();
        } else {
            if (valorValor2.length() <= 0) {
                exception.addError(
                        "Debe ingresar valor valido para el segundo eje  de la direccion");
            }
        }

        String complemento = request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR);

        if (complemento == null) {
            complemento = new String();
        }

        if (complemento.length() <= 0) {
            exception.addError("Debe ingresar un complemento valido");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        List ejes = (List) session.getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
        Direccion direccion = new Direccion();
        direccion.setEspecificacion(complemento != null ? complemento.toUpperCase() : null);

        if (ejes != null) {
            Iterator itEje = ejes.iterator();

            while (itEje.hasNext()) {
                ElementoLista elementoEje = (ElementoLista) itEje.next();
                Eje eje;

                if (elementoEje.getId().equals(valorEje1)) {
                    eje = new Eje();
                    eje.setIdEje(elementoEje.getId());
                    eje.setNombre(elementoEje.getValor());
                    direccion.setEje(eje);
                    direccion.setValorEje(valorValor1);
                }

                if (elementoEje.getId().equals(valorEje2)) {
                    eje = new Eje();
                    eje.setIdEje(elementoEje.getId());
                    eje.setNombre(elementoEje.getValor());
                    direccion.setEje1(eje);
                    direccion.setValorEje1(valorValor2);
                }
            }
        } else {
            exception.addError("La lista de los ejes no se encontro");
        }

        folio.addDireccione(direccion);
    }

    /**
     *
     * @param request
     * @return
     * @throws AccionWebException
     *
     */
    private EvnFolio grabarTemporal(HttpServletRequest request)
            throws AccionWebException {
        HttpSession currrentSession = request.getSession();
        Folio folio = (Folio) currrentSession.getAttribute(WebKeys.FOLIO);

        Direccion direccionTemp = (Direccion) currrentSession.getAttribute(CDireccion.DIRECCION_TEMPORAL);
        Direccion direccion = null;

        if ((folio != null) && (folio.getDirecciones() != null)
                && !folio.getDirecciones().isEmpty()) {
            direccion = (Direccion) folio.getDirecciones().get(folio.getDirecciones()
                    .size()
                    - 1);
        }

        this.removerDireccionesFromFolio(folio);

        if (!compararDirecciones(direccionTemp, direccion)) {
            if (direccion != null) {
                folio.addDireccione(direccion);
            }
        }

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) currrentSession.getAttribute(WebKeys.USUARIO_AURIGA);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        String tipoEvento = EvnFolio.GRABAR_TEMPORAL;

        return new EvnFolio(usuarioAuriga, tipoEvento, folio, usuarioSIR);
    }

    private EvnFolio desenglobarFolio(HttpServletRequest request)
            throws AccionWebException {
        //TODO: Genera un evento del tipo DESENGLOBAR_FOLIO
        return null;
    }

    private EvnFolio buscarFolios(HttpServletRequest request)
            throws AccionWebException {
        /*ValidacionParamentrosCrearFolioException exception = new ValidacionParamentrosCrearFolioException();
        String valorRadicacion = request.getParameter(AWFolio.FOLIO_COD_CATASTRAL);
        double numRadicacion = 0.0d;
        if(valorRadicacion != null){
                        try{
                                        numRadicacion = Double.parseDouble(valorRadicacion);
                                        if (numRadicacion<=0){
                                                        exception.addError("El valor de la radicacion del folio no puede ser negativo o cero");
                                        }
                        } catch (NumberFormatException e){
                                        exception.addError("El valor de la radicacion del folio es inválido");
                        }
        }
         */
        return null;
    }

    /**
     * @BUGID: 2066
     * @param request
     * @return
     */
    private EvnFolio agregarAnotacion(HttpServletRequest request)
            throws AccionWebException {
        preservarInfo(request);
        preservarInfoAntiguoSistemaAnotacion(request);

        List anotaciones = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);

        if (anotaciones == null) {
            anotaciones = new Vector();
        }

        ValidacionParamentrosCrearFolioException exception = new ValidacionParamentrosCrearFolioException();

        /*
        String idRadicacion = request.getParameter(AWFolio.ANOTACION_ID_ANOTACION);

        double valorIdRadicacion = 0.0d;

        if ((idRadicacion == null) || idRadicacion.equals("")) {
                exception.addError(
                        "El numero del identificador de la anotacion es invalido");
        } else {
                try {
                        valorIdRadicacion = Double.parseDouble(idRadicacion);

                        if (valorIdRadicacion <= 0) {
                                exception.addError(
                                        "El valor del identificador en la anotacion no puede ser negativo o cero");
                        }
                } catch (NumberFormatException e) {
                        exception.addError(
                                "El valor del identificador en la anotacion es inválido");
                }
        }*/
        String valFechaRadicacion = request.getParameter(CFolio.ANOTACION_FECHA_RADICACION);
        Calendar fechaRadicacion = null;

        if ((valFechaRadicacion == null) || valFechaRadicacion.equals("")) {
            exception.addError(
                    "La fecha de la radicacion en la anotacion no puede ser vacia");
        } else {
            fechaRadicacion = darFecha(valFechaRadicacion);

            if (fechaRadicacion == null) {
                exception.addError(
                        "La fecha de la radicacion en la anotacion es invalida");
            }
        }

        String numRadAnotacion = request.getParameter(CFolio.ANOTACION_NUM_RADICACION);

        if ((numRadAnotacion == null) || numRadAnotacion.equals("")) {
            numRadAnotacion = " ";
        }

        //Se elimina esta validación, según requerimiento del usuario.

        /*
        if ((numRadAnotacion == null) || numRadAnotacion.equals("")) {
                exception.addError(
                        "El numero del numero de la radicacion es invalido");
        }
         */
        String tipoDocumento = request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO);
        List tiposDoc = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
        String id_tipoDocumento = request.getParameter(CSolicitudRegistro.ID_TIPO_ENCABEZADO);

        if ((id_tipoDocumento != null) && !id_tipoDocumento.equals("")) {
            if (!existeId(tiposDoc, id_tipoDocumento)) {
                exception.addError("El tipo de documento digitado es inválido");
            } else {
                if (!tipoDocumento.equals(WebKeys.SIN_SELECCIONAR)) {
                    if (!id_tipoDocumento.equals(tipoDocumento)) {
                        exception.addError(
                                "El tipo de documento digitado no correponde al tipo de documento seleccionado");
                    } else {
                        tipoDocumento = new String(id_tipoDocumento);
                    }
                }
            }
        }

        if (((tipoDocumento == null)
                || tipoDocumento.equals(WebKeys.SIN_SELECCIONAR))
                && ((id_tipoDocumento == null) || id_tipoDocumento.equals(""))) {
            exception.addError(
                    "Debe escojer un tipo para el documento de la anotacion");
        }

        String numDocumento = request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO);

        /*if ((numDocumento == null) || numDocumento.equals("")) {
                exception.addError(
                        "El valor del numero del documento en la anotacion es inválido");
        }*/
        String valFechaDocumento = request.getParameter(CFolio.ANOTACION_FECHA_DOCUMENTO);
        Calendar fechaDocumento = null;

        if ((valFechaDocumento == null) || valFechaDocumento.equals("")) {
            exception.addError(
                    "La fecha del documento en la anotacion no puede ser vacia");
        } else {
            fechaDocumento = darFecha(valFechaDocumento);

            if (fechaDocumento == null) {
                exception.addError(
                        "La fecha del documento en la anotacion es invalida");
            }

            if (fechaRadicacion != null && fechaDocumento != null && fechaRadicacion.before(fechaDocumento)) {
                exception.addError(
                        "La fecha de radicación no puede ser anterior a la fecha del documento");
            }
        }

        String valorDepartamento = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        String nomDepartamento = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);

        if (valorDepartamento == null) {
            valorDepartamento = new String();
        }

        if (nomDepartamento == null) {
            nomDepartamento = new String();
        }

        if ((valorDepartamento.length() <= 0)) {
            exception.addError(
                    "Debe seleccionar un departamento valido para la oficina de procedencia del documento el la anotacion");
        }

        String valorMunicipio = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        String nomMunicipio = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);

        if (valorMunicipio == null) {
            valorMunicipio = new String();
        }

        if (nomMunicipio == null) {
            nomMunicipio = new String();
        }

        if ((valorMunicipio.length() <= 0)) {
            exception.addError(
                    "Debe seleccionar un municipio valido para la oficina de procedencia del documento el la anotacion");
        }

        String valorVereda = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        String nomVereda = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);

        if (((valorVereda == null) || (valorVereda.trim().equals("")))
                || nomDepartamento.equals("") || nomMunicipio.equals("")) {
            valorVereda = ValidacionDatosOficinaOrigen.obtenerVereda((List) request.getSession()
                    .getServletContext()
                    .getAttribute(WebKeys.LISTA_DEPARTAMENTOS),
                    valorDepartamento, valorMunicipio);
        }

        if (valorVereda == null) {
            valorVereda = new String();
        }

        if (nomVereda == null) {
            nomVereda = new String();
        }

        if ((valorVereda.length() <= 0)) {
            exception.addError(
                    "Debe seleccionar una vereda valida para la oficina de procedencia del documento el la anotacion");
        }

        String idOficina = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        String oficinaVersion = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
        double valorIdOficina = 0.0d;
        String numOficina = new String();
        String nomOficina = new String();

        if ((idOficina == null) || (idOficina.length() <= 0)) {
            exception.addError(
                    "Debe seleccionar una oficina para la oficina de procedencia del documento en la anotacion");
        } else {
            nomOficina = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
            numOficina = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
        }

        String numEspecificacion = request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION);

        if (numEspecificacion != null) {
            numEspecificacion = numEspecificacion.replaceAll(",", "");
        }

        double valorEspecificacion = 0.0d;

        //if((numEspecificacion == null) || numEspecificacion.equals("")) {
        //    exception.addError("Debe ingresar el valor de la especificacion en la anotacion");
        //} else {
        try {
            valorEspecificacion = Double.parseDouble(numEspecificacion);

            if (valorEspecificacion <= 0) {
                exception.addError(
                        "El valor del numero de la especificacion en la anotacion no puede ser negativo o cero");
            }
        } catch (NumberFormatException e) {
            //exception.addError("El valor de la especificacion en la anotacion es inválido");
            valorEspecificacion = 0.0d;
        }

        //}EN ANTIGUO SISTEMA NO SE EXIGE VALOR*/
        String idNaturaleza = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);

        String nomNatJuridica = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);

        if ((idNaturaleza == null) || (idNaturaleza.length() <= 0)) {
            exception.addError(
                    "Debe seleccionar una naturaleza juridica para la especificacion en la anotacion");
        }

        String modalidad = request.getParameter(CFolio.ANOTACION_MODALIDAD);
        if (idNaturaleza.equals("0125") || idNaturaleza.equals("0126")) {
            if (modalidad == null || modalidad.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe especificar una modalidad en la anotacion");
            }
        }

        //		verificar si idNaturaleza existe en la lista de contexto
        List grupoNaturalezas = (List) session.getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change :
         */
        NaturalezaJuridica natJuridica = null;
        if (!existeNaturaleza(idNaturaleza, grupoNaturalezas, natJuridica)) {
            exception.addError("El código de naturaleza juridica no es válido");
        }

        String comentario = request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        //@BUGID: 2066
        this.agregarVariosCiudadanos(request, accion);

        //@END BUGID: 2066
        Anotacion anotacion = new Anotacion();

        int pos = anotaciones.size() + 1;
        anotacion.setOrden(String.valueOf(pos));

        //anotacion.setIdAnotacion(idRadicacion);
        anotacion.setFechaRadicacion(fechaRadicacion.getTime());

        Documento documento = new Documento();

        TipoDocumento tipoDoc = new TipoDocumento();

        Iterator itTiposDocs = ((List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO)).iterator();

        while (itTiposDocs.hasNext()) {
            ElementoLista elemento = (ElementoLista) itTiposDocs.next();

            if (elemento.getId().equals(tipoDocumento)) {
                tipoDoc.setNombre(elemento.getValor());
            }
        }

        tipoDoc.setIdTipoDocumento(tipoDocumento);

        documento.setFecha(fechaDocumento.getTime());
        documento.setNumero(numDocumento);

        OficinaOrigen oficina = new OficinaOrigen();
        oficina.setIdOficinaOrigen(idOficina);
        /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        oficina.setVersion(Long.parseLong(oficinaVersion));
        oficina.setNumero(numOficina);
        oficina.setNombre(nomOficina);

        Vereda vereda = new Vereda();
        vereda.setIdDepartamento(valorDepartamento);
        vereda.setIdMunicipio(valorMunicipio);
        vereda.setIdVereda(valorVereda);
        vereda.setNombre(nomVereda);
        oficina.setVereda(vereda);

        documento.setTipoDocumento(tipoDoc);
        documento.setOficinaOrigen(oficina);

        anotacion.setDocumento(documento);

        NaturalezaJuridica naturalezaJuridica = new NaturalezaJuridica();
        anotacion.setValor(valorEspecificacion);

        naturalezaJuridica.setIdNaturalezaJuridica(idNaturaleza);
        naturalezaJuridica.setNombre(nomNatJuridica);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change :
         */
        naturalezaJuridica.setVersion(natJuridica.getVersion());

        anotacion.setNaturalezaJuridica(naturalezaJuridica);
        anotacion.setComentario(comentario);
        anotacion.setModalidad(modalidad);

        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);

        //String numRadAnotacion=turno.getIdWorkflow();
        anotacion.setNumRadicacion(numRadAnotacion);
        anotacion.setIdWorkflow(turno != null ? turno.getIdWorkflow() : null);

        //SE LLENAN LOS DATOS DE LAS VARIABLES DE ANTIGUO SISTEMA
        String anotLibroAno = request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS);
        String anotLibroNum = request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS);
        String anotLibroPag = request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS);
        String anotLibroTip = request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS);

        String anotTomoAno = request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS);
        String anotTomoMun = request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS);
        String anotTomoNum = request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS);
        String anotTomoPag = request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS);

        if (((anotLibroAno == null) || anotLibroAno.equals(""))
                && ((anotLibroNum == null) || anotLibroNum.equals(""))
                && ((anotLibroPag == null) || anotLibroPag.equals(""))
                && ((anotLibroTip == null) || anotLibroTip.equals(""))
                && ((anotTomoAno == null) || anotTomoAno.equals(""))
                && ((anotTomoMun == null) || anotTomoMun.equals(""))
                && ((anotTomoNum == null) || anotTomoNum.equals(""))
                && ((anotTomoPag == null) || anotTomoPag.equals(""))) {
            exception.addError(
                    "Debe ingresar los datos relacionados al libro y tomo donde se encuentra la anotación");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        DatosAntiguoSistema das = new DatosAntiguoSistema();
        das.setLibroAnio(anotLibroAno);
        das.setLibroNumero(anotLibroNum);
        das.setLibroPagina(anotLibroPag);
        das.setLibroTipo(anotLibroTip);

        das.setTomoAnio(anotTomoAno);
        das.setTomoMunicipio(anotTomoMun);
        das.setTomoNumero(anotTomoNum);
        das.setTomoPagina(anotTomoPag);

        anotacion.setDatosAntiguoSistema(das);
        preservarInfoAntiguoSistemaAnotacion(request);

        List ciudadanos = (List) session.getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if ((ciudadanos == null) || ciudadanos.isEmpty()) {
            exception.addError(
                    "Debe ingresar por lo menos un ciudadano en la anotación");
            throw exception;
        }

        if (ciudadanos != null) {
            Iterator itPersonas = ciudadanos.iterator();

            while (itPersonas.hasNext()) {
                AnotacionCiudadano ciudadano = (AnotacionCiudadano) itPersonas.next();
                ciudadano.setAnotacion(anotacion);

                //ciudadano.setIdMatricula("idMatricula");
                anotacion.addAnotacionesCiudadano(ciudadano);
            }
        }

        Vector vectorAnotaciones;

        if (anotaciones instanceof Vector) {
            vectorAnotaciones = (Vector) anotaciones;
            vectorAnotaciones.add(anotacion);
        } else {
            vectorAnotaciones = new Vector();

            for (int i = 0; i < anotaciones.size(); i++) {
                Anotacion anota = (Anotacion) anotaciones.get(i);
                vectorAnotaciones.add(anota);
            }

            vectorAnotaciones.add(anotacion);
        }

        session.removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        session.setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, vectorAnotaciones);

        eliminarInfoBasicaAnotacion();
        session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        return null;
    }

    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
        session = request.getSession();

        if ((evento != null) && evento instanceof EvnRespCiudadano) {
            EvnRespCiudadano evn = (EvnRespCiudadano) evento;
            String ver = request.getParameter(CAnotacionCiudadano.SECUENCIA);

            if (evn.isCiudadanoEncontrado()) {
                request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA
                        + ver, evn.getCiudadano().getApellido1());
                request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA
                        + ver, evn.getCiudadano().getApellido2());
                request.getSession().setAttribute(CFolio.ANOTACION_NOMBRES_PERSONA
                        + ver, evn.getCiudadano().getNombre());
                request.getSession().setAttribute(CFolio.ANOTACION_SEXO_PERSONA
                        + ver, evn.getCiudadano().getSexo());
                request.getSession().setAttribute(CFolio.CIUDADANO_EDITABLE
                        + ver, new Boolean(true));
            } else {
                request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA
                        + ver);
                request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA
                        + ver);
                request.getSession().removeAttribute(CFolio.ANOTACION_NOMBRES_PERSONA
                        + ver);
                request.getSession().removeAttribute(CFolio.ANOTACION_SEXO_PERSONA
                        + ver);
            }
        } else if (evento instanceof EvnRespFolio) {
            EvnRespFolio respuesta = (EvnRespFolio) evento;

            if ((accion != null) && (respuesta != null)) {
                if (accion.equals(MOSTRAR_FOLIO)) {
                    doEndMostrarFolio(respuesta);
                } else if (accion.equals(CREAR)) {
                    //no hay que hacer nada.
                } else if (accion.equals(REGISTRAR_CREACION_FOLIO)
                        || accion.equals(GRABAR_TMP)
                        || accion.equals(ELIMINAR_ANOTACION)) {
                    Folio folio = respuesta.getFolio();
                    session.setAttribute(WebKeys.FOLIO, folio);

                    Turno turno = respuesta.getTurno();

                    if (turno != null) {
                        session.setAttribute(WebKeys.TURNO, turno);
                    }

                    String idMatricula = folio.getIdMatricula();

                    if (idMatricula != null) {
                        session.setAttribute(CFolio.FOLIO_ID_MATRICULA,
                                idMatricula);
                    }

                    doEndMostrarFolio(respuesta);
                } else if (accion.equals(CREACION_FOLIO_FINALIZADO)) {
                    eliminarInfoBasicaFolio();
                    eliminarInfoBasicaAnotacion();
                    session.removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
                    session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

                    Folio folio = respuesta.getFolio();
                    session.setAttribute(WebKeys.FOLIO, folio);
                } else if (respuesta.getTipoEvento().equals(EvnRespFolio.FOLIO_TEMPORAL)) {
                    request.getSession().setAttribute(WebKeys.FOLIO,
                            respuesta.getFolio());
                    request.getSession().setAttribute(WebKeys.MAYOR_EXTENSION_FOLIO,
                            new Boolean(respuesta.getMayorExtensionFolio()));
                    request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_PADRE,
                            respuesta.getFoliosPadre());
                    request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_HIJO,
                            respuesta.getFoliosHijo());
                    request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_GRAVAMEN,
                            respuesta.getGravamenes());
                    request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_MEDIDAS_CAUTELARES,
                            respuesta.getMedidasCautelares());
                    request.getSession().setAttribute(WebKeys.LISTA_SALVEDADES_ANOTACIONES,
                            respuesta.getSalvedadesAnotaciones());
                    request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_CANCELACION,
                            respuesta.getCancelaciones());
                    request.getSession().setAttribute(WebKeys.TOTAL_ANOTACIONES,
                            new BigDecimal(respuesta.getNumeroAnotaciones()));

                    if (respuesta.getListATemp() != null) {
                        request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO,
                                respuesta.getListATemp());
                    }

                    if (request.getParameter(WebKeys.ACCION).equals("GRABAR_EDICION")) {
                        //Como ya se grabó la anotacion, se borran los datos relacionados
                        request.getSession().setAttribute(WebKeys.FOLIO,
                                respuesta.getPayload());
                        request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO,
                                respuesta.getListATemp());

                        request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO);
                        request.getSession().removeAttribute(CFolio.ANOTACION_NUM_DOCUMENTO);
                        request.getSession().removeAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO);
                        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
                        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
                        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
                        request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
                    }
                }
            }
        }
    }

    private void doEndMostrarFolio(EvnRespFolio respuesta) {
        Folio folio = respuesta.getFolio();

        if (folio != null) {
            session.setAttribute(WebKeys.FOLIO, folio);
        }

        Turno turno = respuesta.getTurno();

        if (turno != null) {
            session.setAttribute(WebKeys.TURNO, turno);
        }

        List anotaciones = folio.getAnotaciones();

        if (anotaciones != null) {
            session.setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, anotaciones);
        } else {
            session.removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        }

        //session.setAttribute(AWFolio.FOLIO_NUM_RADICACION,);
        ZonaRegistral zonaReg = folio.getZonaRegistral();
        Circulo circulo = zonaReg.getCirculo();

        Vereda vereda = zonaReg.getVereda();

        String nombreVereda = vereda.getNombre();
        String idVereda = vereda.getIdVereda();

        Municipio municipio = vereda.getMunicipio();
        String nombreMunicipio = municipio.getNombre();
        String idMunicipio = municipio.getIdMunicipio();

        Departamento depto = municipio.getDepartamento();
        String idDepto = depto.getIdDepartamento();
        String nombreDepto = depto.getNombre();

        String complementacion = null;

        if (null != folio.getComplementacion()) {
            complementacion = folio.getComplementacion().getComplementacion();
        }

        String lindero = folio.getLindero();
        String tipoPredio = folio.getTipoPredio().getIdPredio();

        List direcciones = folio.getDirecciones();
        Direccion direccion = null;

        if (direcciones.size() > 0) {
            direccion = (Direccion) direcciones.get(direcciones.size() - 1);
        }

        //SE CARGA LA DIRECCIÓN TEMPORAL EN EL CASO DE QUE YA SE HAYA GUARDADO EL FOLIO POR PRIMERA VEZ
        Direccion direccionTemp = null;

        if ((folio != null) && (folio.getDirecciones() != null)
                && !folio.getDirecciones().isEmpty()) {
            direccionTemp = (Direccion) folio.getDirecciones().get(folio.getDirecciones()
                    .size()
                    - 1);

            if (direccionTemp != null) {
                session.setAttribute(CDireccion.DIRECCION_TEMPORAL,
                        direccionTemp);
            }
        }

        if ((direccion != null) && (direccion.getEje() != null)) {
            Eje eje = direccion.getEje();

            if (eje.getIdEje() != null) {
                session.setAttribute(CFolio.FOLIO_EJE1, eje.getIdEje());
            }
        }

        if ((direccion != null) && (direccion.getEje1() != null)) {
            Eje eje1 = direccion.getEje1();

            if (eje1.getIdEje() != null) {
                session.setAttribute(CFolio.FOLIO_EJE2, eje1.getIdEje());
            }
        }

        if ((direccion != null) && (direccion.getValorEje() != null)) {
            session.setAttribute(CFolio.FOLIO_VALOR1, direccion.getValorEje());
        }

        if ((direccion != null) && (direccion.getValorEje1() != null)) {
            session.setAttribute(CFolio.FOLIO_VALOR2, direccion.getValorEje1());
        }

        if ((direccion != null) && (direccion.getEspecificacion() != null)) {
            session.setAttribute(CFolio.FOLIO_COMPLEMENTO_DIR,
                    direccion.getEspecificacion());
        }

        String codigoCatastral = "";

        if (folio.getCodCatastral() != null) {
            codigoCatastral = folio.getCodCatastral();
        }

        String nupre = "";

        if (folio.getNupre() != null) {
            nupre = folio.getNupre();
        }

        String determinaInm = WebKeys.SIN_SELECCIONAR;

        if (folio.getDeterminaInm() != null) {
            determinaInm = folio.getDeterminaInm();
        }

        String privMetros = "";

        if (folio.getPrivMetros() != null) {
            privMetros = folio.getPrivMetros();
        }

        String privCentimetros = "";

        if (folio.getPrivCentimetros() != null) {
            privCentimetros = folio.getPrivCentimetros();
        }

        String consMetros = "";

        if (folio.getConsMetros() != null) {
            consMetros = folio.getConsMetros();
        }

        String consCentimetros = "";

        if (folio.getConsCentimetros() != null) {
            consCentimetros = folio.getConsCentimetros();
        }

        String coeficiente = "";

        if (folio.getCoeficiente() != null) {
            coeficiente = folio.getCoeficiente();
        }

        String hectareas = "";

        if (folio.getHectareas() != null) {
            hectareas = folio.getHectareas();
        }

        String metros = "";

        if (folio.getMetros() != null) {
            metros = folio.getMetros();
        }

        String centimetros = "";

        if (folio.getCentimetros() != null) {
            centimetros = folio.getCentimetros();
        }

        String linderosDef = "";

        if (folio.getLinderosDef() != null) {
            linderosDef = folio.getLinderosDef();
        }

        try {
            session.setAttribute(CFolio.FOLIO_COD_CATASTRAL, codigoCatastral);
            session.setAttribute(CFolio.FOLIO_NUPRE, nupre);
            session.setAttribute(CFolio.FOLIO_DETERMINACION_INMUEBLE, determinaInm);
            session.setAttribute(CFolio.FOLIO_PRIVMETROS, privMetros);
            session.setAttribute(CFolio.FOLIO_PRIVCENTIMETROS, privCentimetros);
            session.setAttribute(CFolio.FOLIO_CONSMETROS, consMetros);
            session.setAttribute(CFolio.FOLIO_CONSCENTIMETROS, consCentimetros);
            session.setAttribute(CFolio.FOLIO_COEFICIENTE, coeficiente);
            session.setAttribute(CFolio.FOLIO_HECTAREAS, hectareas);
            session.setAttribute(CFolio.FOLIO_METROS, metros);
            session.setAttribute(CFolio.FOLIO_CENTIMETROS, centimetros);
            session.setAttribute(CFolio.FOLIO_LINDEROS_DEFINIDOS, linderosDef);
            session.setAttribute(CFolio.FOLIO_LOCACION_ID_DEPTO, idDepto);
            session.setAttribute(CFolio.FOLIO_LOCACION_NOM_DEPTO, nombreDepto);
            session.setAttribute(CFolio.FOLIO_LOCACION_ID_MUNIC, idMunicipio);
            session.setAttribute(CFolio.FOLIO_LOCACION_NOM_MUNIC,
                    nombreMunicipio);
            session.setAttribute(CFolio.FOLIO_LOCACION_ID_VEREDA, idVereda);
            session.setAttribute(CFolio.FOLIO_LOCACION_NOM_VEREDA, nombreVereda);
            session.setAttribute(CFolio.FOLIO_TIPO_PREDIO, tipoPredio);
            session.setAttribute(CFolio.FOLIO_COMPLEMENTACION, complementacion);
            session.setAttribute(CFolio.FOLIO_LINDERO, lindero);
        } catch (Exception e) {
            Log.getInstance().debug(AWFolio.class, e);
        }
    }

    /**
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnFolio agregarCiudadano(HttpServletRequest request)
            throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaCiudadano(request);
        preservarInfoAntiguoSistemaAnotacion(request);

        ValidacionParamentrosCrearFolioException exception = new ValidacionParamentrosCrearFolioException();

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new Vector();
        }

        String tipoPersona = request.getParameter(CFolio.ANOTACION_TIPO_PERSONA);

        if ((tipoPersona == null) || tipoPersona.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError(
                    "Debe escojer un tipo de persona para el ciudadano en la anotacion");
        }

        String tipoId = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA);

        if ((tipoId == null) || tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError(
                    "Debe escojer un tipo de identificación para la persona en la anotacion");
        }

        String sexo = request.getParameter(CFolio.ANOTACION_SEXO_PERSONA);

        if ((sexo == null) || sexo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError(
                    "Debe escojer un sexo para el ciudadano en la anotacion");
        }

        String numId = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA);

        String apellido1 = request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA);
        String apellido2 = request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA);
        String nombres = request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA);

        if (false) {
            if ((apellido1 == null) || apellido1.trim().equals("")) {
                exception.addError("Primer apellido inválido");
            }
        } else {
            if (((numId == null) || numId.trim().equals(""))
                    && !tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)) {
                exception.addError("Documento Inválido");
            }

            double valorId = 0.0d;

            if ((numId == null)
                    && !tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)) {
                exception.addError(
                        "El número de identificación de la persona en la anotación es inválido");
            } else {
                try {
                    valorId = Double.parseDouble(numId);

                    if (valorId <= 0) {
                        exception.addError(
                                "El valor del documento no puede ser negativo o cero");
                    }
                } catch (NumberFormatException e) {
                    //exception.addError("El número de identificación de la persona en la anotación es inválido");
                    valorId = 0;
                }
            }

            if (false) {
                if ((nombres == null) || nombres.trim().equals("")) {
                    exception.addError("Razón social inválida");
                }
            } else {
                //if ((nombres == null) || nombres.equals("")) {
                //	exception.addError("Debe ingresar el nombre de la persona en la anotación");
                //}NO SE DEBE EXIGIR NOMBRE PORQUE PUEDE SER UNA PERSONA JURIDICA, QUE QUEDA EN PRIMER APELLIDO
                if ((apellido1 == null) || apellido1.equals("")) {
                    exception.addError(
                            "Debe ingresar el primer apellido de la persona en la anotación");
                }
            }
        }

        String tipoIntervecion = request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION);

        if ((tipoIntervecion == null)
                || tipoIntervecion.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError(
                    "Debe ingresar un tipo de intervención para la persona en la anotación");
        }

        String participacion = request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION);
        if ((participacion != null) && !participacion.equals("")) {
            if (participacion.length() > 50) {
                exception.addError("El campo participación de una persona no puede tener mas de 50 caracteres");
            }
        }

        if (exception.getErrores().size() > 0) {
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        AnotacionCiudadano anotacionCiudadano = new AnotacionCiudadano();
        Ciudadano ciudadano = new Ciudadano();
        ciudadano.setApellido1(apellido1);
        ciudadano.setApellido2(apellido2);
        ciudadano.setNombre(nombres);
        ciudadano.setDocumento(numId);
        ciudadano.setTipoDoc(tipoId);
        ciudadano.setTipoPersona(tipoPersona);
        ciudadano.setSexo(sexo);

        //Se setea el circulo del ciudadano
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

        anotacionCiudadano.setCiudadano(ciudadano);
        anotacionCiudadano.setRolPersona(tipoIntervecion);
        anotacionCiudadano.setParticipacion(participacion);

        String marcaPropietario = request.getParameter(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION);

        if ((marcaPropietario == null) || marcaPropietario.equals("")) {
            anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.NO_PROPIETARIO);
        } else if (marcaPropietario.equals("X")) {
            anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DERECHO_REAL_DOMINIO);
        } else if (marcaPropietario.equals("I")) {
            anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DOMINIO_INCOMPLETO);
        }

        if (sePuedeInsertar(anotacionCiudadano, ciudadanos.iterator())) {
            ciudadanos.add(anotacionCiudadano);
            session.setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, ciudadanos);
            eliminarInfoBasicaCiudadano(request);
        } else {
            exception.addError(
                    "El ciudadano que esta tratando de agregar ya se encuentra con el mismo rol en la anotacion");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        return null;
    }

    /**
     * @param ciudadano
     * @param iterator
     * @return
     */
    private boolean sePuedeInsertar(AnotacionCiudadano antCiudadano,
            Iterator iterator) {
        Ciudadano ciudadano = antCiudadano.getCiudadano();
        AnotacionCiudadano antTemp = null;
        boolean resuelto = false;
        boolean respuesta = true;

        while (iterator.hasNext() && !resuelto) {
            antTemp = (AnotacionCiudadano) iterator.next();

            Ciudadano temp = antTemp.getCiudadano();

            if (temp.getTipoDoc().equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
                if (temp.getApellido1().equalsIgnoreCase(ciudadano.getApellido1())
                        && temp.getNombre().equalsIgnoreCase(ciudadano.getNombre())
                        && temp.getTipoDoc().equals(ciudadano.getTipoDoc())
                        && antTemp.getRolPersona().equalsIgnoreCase(antCiudadano.getRolPersona())) {
                    resuelto = true;
                    respuesta = false;
                }
            } else {
                if (temp.getDocumento().equals(ciudadano.getDocumento())
                        && temp.getTipoDoc().equals(ciudadano.getTipoDoc())
                        && (antTemp.getRolPersona().equalsIgnoreCase(antCiudadano.getRolPersona()))) {
                    resuelto = true;
                    respuesta = false;
                }
            }
        }

        return respuesta;
    }

    /**
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnFolio eliminarCiudadano(HttpServletRequest request)
            throws AccionWebException {
        /*
                preservarInfoBasicaFolio(request);
                preservarInfoBasicaAnotacion(request);
         */
        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new Vector();
        }

        try {
            int aplicacionNumero = Integer.parseInt(request.getParameter(
                    AWCalificacion.POSICION));
            ciudadanos.remove(aplicacionNumero);
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException(
                    "El número del documento a eliminar es inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (ciudadanos.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacía");
            }

            throw new ParametroInvalidoException(
                    "El índice del documento a eliminar está fuera del rango");
        }

        request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION,
                ciudadanos);

        return null;
    }

    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnFolio eliminarAnotacion(HttpServletRequest request)
            throws AccionWebException {
        preservarInfo(request);

        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);

        List anotacionesTemp = new ArrayList();
        List anotaciones = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);

        Anotacion anotacionAEliminar = null;

        //Vector anotaciones = (Vector) session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        if (anotaciones == null) {
            anotaciones = new ArrayList();
        }

        try {
            int aplicacionNumero = Integer.parseInt(request.getParameter(
                    AWFolio.POSICION));

            for (int i = 0; i < anotaciones.size(); i++) {
                if (i != aplicacionNumero) {
                    Anotacion anotacion = (Anotacion) anotaciones.get(i);

                    if (i > aplicacionNumero) {
                        anotacion.setOrden(""
                                + (Integer.parseInt(anotacion.getOrden()) - 1));
                    }

                    anotacionesTemp.add(anotacion);
                } else {
                    anotacionAEliminar = (Anotacion) anotaciones.get(i);
                    anotacionAEliminar.setToDelete(true);
                }
            }

            /*anotaciones.remove(aplicacionNumero);
            for(int i=aplicacionNumero; i<anotaciones.size(); i++){
                    Anotacion anotacion = (Anotacion)anotaciones.get(i);
                    anotacion.setOrden(""+(Integer.parseInt(anotacion.getOrden())-1));
            }*/
            session.setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO,
                    anotacionesTemp);
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException(
                    "El número del documento a eliminar es inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (anotaciones.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacía");
            }

            throw new ParametroInvalidoException(
                    "El índice del documento a eliminar está fuera del rango");
        }

        if ((anotacionAEliminar.getIdAnotacion() != null)
                || (anotacionAEliminar.getIdMatricula() != null)) {
            Folio nFolio = new Folio();
            nFolio.setIdMatricula(folio.getIdMatricula());
            nFolio.setZonaRegistral(folio.getZonaRegistral());
            nFolio.addAnotacion(0, anotacionAEliminar);

            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(WebKeys.USUARIO_AURIGA);
            gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
            String tipoEvento = EvnFolio.GRABAR_TEMPORAL;

            return new EvnFolio(usuarioAuriga, tipoEvento, nFolio, usuarioSIR);
        }

        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento editarAnotacion(HttpServletRequest request) {
        String ver = request.getParameter(AWCalificacion.NUM_ANOTACION_TEMPORAL);
        request.getSession().setAttribute(AWCalificacion.NUM_ANOTACION_TEMPORAL,
                ver);

        return null;
    }

    private Evento grabarEdicion(HttpServletRequest request)
            throws ValidacionParametrosException {
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);

        //Codigo  de grabar Edicion
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);

        //obtencion del folio
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);

        ValidacionParametrosException exception = null;

        exception = new ValidacionParametrosEdicionCalificacionException();

        //obtencion lista de ciudadanosT en el sesion (Aqui estan tanto los ciudadanos sin editar como los editados)
        List ciudadanosT = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        /*
        try {
            this.agregarVariosCiudadanos(request, exception);
        } catch (AccionWebException e1) {
            e1.printStackTrace();
        }
         */
        if (ciudadanosT == null) {
            ciudadanosT = new Vector();
        }

        //obtencion lista de ciudadanosB del folio (aqui estan solo los ciudadanos q existen antes de la edicion)
        List ciudadanosB = null; // se inicializara despues.

        NaturalezaJuridica nat = null;
        boolean cambioNat = false; //flag para saber si se cambio la naturaleza juridica
        String idNaturalezaB = "";
        String nombreNaturalezaB = "";
        String comentarioB = "";
        double valorB = 0;

        //aqui se comienza a ver q valores se han cambiado
        Anotacion a = new Anotacion();
        String pos = (String) request.getSession().getAttribute(NUM_ANOTACION_TEMPORAL);

        //Se setea el id de la anotacion q se va cambiar
        List anotacionF = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        Iterator iaf = anotacionF.iterator();

        while (iaf.hasNext()) {
            Anotacion temp = (Anotacion) iaf.next();

            if (pos.equals(temp.getOrden())) {
                a.setIdAnotacion(temp.getIdAnotacion());

                //se obtiene los datos de la anotacion para compararlos con los datos q esten en la interfaz
                idNaturalezaB = temp.getNaturalezaJuridica()
                        .getIdNaturalezaJuridica();
                nombreNaturalezaB = temp.getNaturalezaJuridica().getNombre();
                valorB = temp.getValor();
                comentarioB = temp.getComentario();
                nat = temp.getNaturalezaJuridica();

                //obtener la lista de ciudadanos de esa anotacion y guardarla en ciudadanosB
                ciudadanosB = temp.getAnotacionesCiudadanos();

                break;
            }
        }

        /*if((a.getIdAnotacion()==null) || (a.getIdAnotacion().equals("")) ){
                exception.addError("La anotacion q va editar no existe.");
        }*/
        if (ciudadanosB == null) {
            ciudadanosB = new Vector();
        }

        if (idNaturalezaB == null) {
            idNaturalezaB = "";
        }

        if (nombreNaturalezaB == null) {
            nombreNaturalezaB = "";
        }

        if (comentarioB == null) {
            comentarioB = "";
        }

        //fin de obtencion de los datosB (Before)
        Log.getInstance().debug(AWFolio.class, "\n\n"
                + "La anotacion a editar tenia los siguientes datos \n" + "ID ="
                + a.getIdAnotacion() + "\n" + "idNaturaleza =" + idNaturalezaB
                + "\n" + "nombreNaturaleza =" + nombreNaturalezaB + "\n" + "valor ="
                + Double.toString(valorB) + "\n" + "comentario =" + comentarioB
                + "\n" + "fin");

        //Comienzo de los datosA (After)
        String valorSA = (String) request.getSession().getAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        double valorA = 0;

        if (valorSA != null) {
            valorSA = valorSA.replaceAll(",", "");
        }

        if ((valorSA != null) && !valorSA.equals("")) {
            try {
                valorA = Double.parseDouble(valorSA);

                if (valorA <= 0) {
                    exception.addError(
                            "El valor de la especificacion no puede ser negativo o cero");
                }
            } catch (NumberFormatException e) {
                exception.addError(
                        "El valor de la especificacion en la anotación es inválido");
            }
        }

        String idNaturalezaA = (String) request.getSession().getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        String nombreNaturalezaA = (String) request.getSession().getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        String comentarioA = (String) request.getSession().getAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change :
         */
        NaturalezaJuridica natJuridica = null;
        if ((idNaturalezaA == null) || (idNaturalezaA.length() <= 0)) {
            exception.addError(
                    "Debe seleccionar una naturaleza juridica para la especificacion en la anotacion");
        }

        boolean esta = false;
        List grupoNaturalezas = (List) request.getSession().getAttribute("listanat");

        Iterator ig = grupoNaturalezas.iterator();

        while (ig.hasNext()) {
            GrupoNaturalezaJuridica group = (GrupoNaturalezaJuridica) ig.next();
            List natus = group.getNaturalezaJuridicas();
            Iterator id = natus.iterator();
            /**
             * @author : Carlos Mario Torres Urina
             * @casoMantis : 0012705.
             * @actaReq : Acta - Requerimiento No
             * 056_453_Modificiación_de_Naturaleza_Jurídica
             * @change :
             */
            while (id.hasNext() && !esta) {
                NaturalezaJuridica natA = (NaturalezaJuridica) id.next();

                if (natA.getIdNaturalezaJuridica().equals(idNaturalezaA)) {
                    /**
                     * @author : Carlos Mario Torres Urina
                     * @casoMantis : 0012705.
                     * @actaReq : Acta - Requerimiento No
                     * 056_453_Modificiación_de_Naturaleza_Jurídica
                     * @change :
                     */
                    natJuridica = natA;
                    esta = true;
                }
            }
        }

        if (!esta) {
            exception.addError(
                    "Debe colocar un codigo de naturaleza juridica valido");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Log.getInstance().debug(AWFolio.class, "\n\n"
                + "La anotacion en la interfaz tiene los siguientes datos \n"
                + "idNaturaleza =" + idNaturalezaA + "\n" + "nombreNaturaleza ="
                + nombreNaturalezaA + "\n" + "valor =" + Double.toString(valorA)
                + "\n" + "comentario =" + comentarioA + "\n" + "fin");
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change :
         */
        nat.setVersion(natJuridica.getVersion());
        //comienzo de comparacion y construccion de la anotacion edicion
        if (!idNaturalezaB.equals(idNaturalezaA)) {
            nat.setIdNaturalezaJuridica(idNaturalezaA);
            cambioNat = true;
        }

        if (!nombreNaturalezaB.equals(nombreNaturalezaA)) {
            nat.setNombre(nombreNaturalezaA);
            cambioNat = true;
        }

        if (cambioNat) {
            a.setNaturalezaJuridica(nat);
        }

        if (valorA != valorB) {
            a.setValor(valorA);
            a.setToUpdateValor(true);
        }

        if (!comentarioB.equals(comentarioA)) {
            a.setComentario(comentarioA);
        }

        Log.getInstance().debug(AWFolio.class, "\n\n"
                + "La anotacion edicion tiene los siguientes datos");

        if (a.getNaturalezaJuridica() != null) {
            Log.getInstance().debug(AWFolio.class, "idNaturaleza ="
                    + a.getNaturalezaJuridica().getIdNaturalezaJuridica() + "\n"
                    + "nombreNaturaleza =" + a.getNaturalezaJuridica().getNombre());
        } else {
            Log.getInstance().debug(AWFolio.class, "idNaturaleza =" + "" + "\n"
                    + "nombreNaturaleza =" + "" + "\n");
        }

        Log.getInstance().debug(AWFolio.class, "valor =" + Double.toString(a.getValor()) + "\n"
                + "comentario =" + a.getComentario() + "\n" + "fin");

        //parte de ciudadanos (aqui se recorrera la lista de ciudadanosT y se comparara con ciudadanosB para crear ciudadanosA)
        List ciudadanosA = new Vector();

        //se obtienen los ciudadanos mandados a eliminar
        Log.getInstance().debug(AWFolio.class, "el # de ciudadanosB =" + ciudadanosB.size());
        Log.getInstance().debug(AWFolio.class, "el # de ciudadanosT =" + ciudadanosT.size());

        int finAB = ciudadanosB.size();
        int cont = 0;

        while (cont != finAB) {
            AnotacionCiudadano cb = (AnotacionCiudadano) ciudadanosB.get(cont);
            AnotacionCiudadano ct = (AnotacionCiudadano) ciudadanosT.get(cont);
            Log.getInstance().debug(AWFolio.class, "ciudadano cedula ="
                    + ct.getCiudadano().getDocumento() + "toDelete = "
                    + Boolean.toString(ct.isToDelete()));

            if ((cb != null) && (ct != null) && (cb.getIdCiudadano() != null)
                    && (ct.getIdCiudadano() != null)
                    && cb.getIdCiudadano().equalsIgnoreCase(ct.getIdCiudadano())
                    && ct.isToDelete()) {
                ciudadanosA.add(ct);
            }

            cont++;
        }

        //se obtienen los ciudadanos agregados
        int inicioSL = ciudadanosB.size();
        int finSL = ciudadanosT.size();
        //List subList = ciudadanosT.subList(inicioSL, finSL);

        List temp = ciudadanosT.subList(inicioSL, finSL);
        List subList = new ArrayList();

        if (temp != null) {
            Iterator it = temp.iterator();
            while (it.hasNext()) {
                subList.add(it.next());
            }
        }

        //		toca remover los ciudadanos q han sido creados y eliminados en la misma pantalla
        Iterator isl = subList.iterator();

        while (isl.hasNext()) {
            AnotacionCiudadano c = (AnotacionCiudadano) isl.next();

            if (c.isToDelete()) {
                isl.remove();
            }
        }

        int SL = subList.size();
        Log.getInstance().debug(AWFolio.class, "el tamaño de la Sublista =" + SL);

        ciudadanosA.addAll(subList);
        Log.getInstance().debug(AWFolio.class, "el # de ciudadanos a editar y/o agregar es = "
                + ciudadanosA.size());

        Iterator ica = ciudadanosA.iterator();

        while (ica.hasNext()) {
            a.addAnotacionesCiudadano((AnotacionCiudadano) ica.next());
        }

        List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO);

        if (anotaciones == null) {
            anotaciones = new Vector();
        }

        anotaciones.add(a);
        Log.getInstance().debug(AWFolio.class, "# de anotaciones a editar 1 ="
                + anotaciones.size());

        //codigo de grabar en temporal
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        if (anotaciones != null) {
            Iterator itAnotaciones = anotaciones.iterator();

            while (itAnotaciones.hasNext()) {
                folio.addAnotacione((Anotacion) itAnotaciones.next());
            }
        } else {
            return null;
        }

        request.getSession().setAttribute(WebKeys.FOLIO, folio);

        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO);

        String tipoEvento = "GRABAR_EDICION";
        usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        return new EvnFolio(usuario, folio, anotaciones, usuarioNeg);
    }

    /**
     * @param request
     * @return
     */
    private Evento refrescarEdicion(HttpServletRequest request) {
        //eliminarInfoBasicaAnotacion(request);
        String ver = request.getParameter("SECUENCIA");
        request.getSession().setAttribute("SECUENCIA", ver);
        preservarInfoAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento cancelarEdicion(HttpServletRequest request) {
        eliminarInfoBasicaAnotacion(request);
        eliminarInfoBasicaVariosCiudadanos(request);

        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento agregarVariosCiudadanosEdicion(HttpServletRequest request)
            throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request); //varios

        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);

        ValidacionParametrosException exception = null;

        exception = new ValidacionParametrosCiudadanoEdicionCalificacionException();

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new Vector();
        }

        List ciudadanosAgregados = new ArrayList();
        int numCiudadanosTabla = this.numeroRegistrosTablaAgregarCiudadanos(request);

        for (int i = 0; i < numCiudadanosTabla; i++) {
            if (agregoPersona(request, i)) {
                int b = i + 1;
                boolean datosBien = true;

                String tipoPersona = request.getParameter(CFolio.ANOTACION_TIPO_PERSONA + i);

                if ((tipoPersona == null) || tipoPersona.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError("Debe escojer un tipo de persona para el ciudadano " + b + " en la anotacion");
                    datosBien = false;

                }
                String tipoId = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA
                        + i);

                if ((tipoId == null) || tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError(
                            "Debe escojer un tipo de identificación para la persona "
                            + b + " en la anotacion");
                    datosBien = false;
                }

                String sexo = request.getParameter(CFolio.ANOTACION_SEXO_PERSONA + i);

                if (!tipoPersona.equals(CCiudadano.TIPO_PERSONA_JURIDICA)) {
                    if ((sexo == null) || sexo.equals(WebKeys.SIN_SELECCIONAR)) {
                        exception.addError("Debe escojer un tipo de sexo para la persona en la anotacion");
                        datosBien = false;
                    }
                }

                String numId = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA
                        + i);
                String apellido1 = request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA
                        + i);
                String apellido2 = request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA
                        + i);
                String nombres = request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA
                        + i);

                if (tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)) {

                    if (tipoPersona.equals(CCiudadano.TIPO_PERSONA_NATURAL)) {
                        if (apellido1 == null || apellido1.trim().equals("")) {
                            exception.addError("Primer apellido invalido para la persona " + b);
                            datosBien = false;
                        }
                    } else {
                        if (nombres == null || nombres.trim().equals("")) {
                            exception.addError("Razon social invalida para el ciudadano " + b);
                            datosBien = false;
                        }
                    }

                } else {
                    if ((numId == null) || numId.trim().equals("")) {
                        exception.addError(
                                "Documento Inválido para la persona " + b);
                        datosBien = false;
                    }

                    double valorId = 0.0d;

                    if (numId == null) {
                        exception.addError(
                                "El número de identificación de la persona " + b
                                + " en la anotación es inválido");
                        datosBien = false;
                    } else {
                        if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_NIT)) {
                            try {
                                valorId = Double.parseDouble(numId);

                                if (valorId <= 0) {
                                    exception.addError(
                                            "El valor del documento de la persona "
                                            + b + " no puede ser negativo o cero");
                                    datosBien = false;
                                }
                            } catch (NumberFormatException e) {
                                exception.addError(
                                        "El número de identificación de la persona "
                                        + b + " en la anotación es inválido");
                                datosBien = false;
                            }
                        }
                    }

                    if (tipoId.equals(CCiudadano.TIPO_DOC_ID_NIT)) {
                        if ((nombres == null) || nombres.trim().equals("")) {
                            exception.addError(
                                    "Razón social inválida del campo " + b);
                            datosBien = false;
                        }
                    } else {
                        if ((nombres == null) || nombres.equals("")) {
                            exception.addError(
                                    "Debe ingresar el nombre de la persona " + b
                                    + " en la anotación");
                            datosBien = false;
                        }

                        if ((apellido1 == null) || apellido1.equals("")) {
                            exception.addError(
                                    "Debe ingresar el primer apellido de la persona "
                                    + b + " en la anotación");
                            datosBien = false;
                        }
                    }
                }

                String tipoIntervecion = request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION
                        + i);

                if ((tipoIntervecion == null)
                        || tipoIntervecion.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError(
                            "Debe ingresar un tipo de intervención para la persona "
                            + b + " en la anotación");
                    datosBien = false;
                }

                AnotacionCiudadano c;
                Iterator ic = ciudadanos.iterator();

                while (ic.hasNext()) {
                    c = (AnotacionCiudadano) ic.next();

                    if ((tipoIntervecion.equals(c.getRolPersona()))
                            && (numId.equals(c.getCiudadano().getDocumento()))
                            && (tipoId.equals(c.getCiudadano().getTipoDoc()))) {
                        exception.addError(
                                "La persona no puede tener dos veces el mismo rol");
                    }
                }

                String participacion = request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i);
                if ((participacion != null) && !participacion.equals("")) {
                    if (participacion.length() > 50) {
                        exception.addError("El campo participación de una persona no puede tener mas de 50 caracteres");
                        datosBien = false;
                    }
                }

                if (datosBien) {
                    AnotacionCiudadano anotacionCiudadano = new AnotacionCiudadano();
                    Ciudadano ciudadano = new Ciudadano();

                    if (apellido1 != null) {
                        ciudadano.setApellido1(apellido1);
                    }

                    if (apellido2 != null) {
                        ciudadano.setApellido2(apellido2);
                    }

                    ciudadano.setNombre(nombres);

                    if (numId != null) {
                        ciudadano.setDocumento(numId);
                    }

                    //Se setea el circulo del ciudadano
                    Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
                    ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

                    ciudadano.setTipoDoc(tipoId);
                    anotacionCiudadano.setCiudadano(ciudadano);
                    anotacionCiudadano.setRolPersona(tipoIntervecion);
                    anotacionCiudadano.setParticipacion(participacion);

                    String marcaPropietario = request.getParameter(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION
                            + i);

                    if ((marcaPropietario == null)
                            || marcaPropietario.equals("")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.NO_PROPIETARIO);
                    } else if (marcaPropietario.equals("X")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DERECHO_REAL_DOMINIO);
                    } else if (marcaPropietario.equals("I")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DOMINIO_INCOMPLETO);
                    }

                    ciudadanosAgregados.add(anotacionCiudadano);
                }
            }
        }

        if (exception.getErrores().size() > 0) {
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        eliminarInfoBasicaVariosCiudadanos(request);

        //if (!existeEnListaCiudadanos(anotacionCiudadano, ciudadanos)) {
        List listaCompletaCiudadanos = new Vector();

        listaCompletaCiudadanos.addAll(ciudadanos);
        listaCompletaCiudadanos.addAll(ciudadanosAgregados);

        request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION,
                listaCompletaCiudadanos);

        /*} else {
                exception.addError("El ciudadano que esta intentado ingresar ya se encuentra registrado en la anotacion");
                throw exception;
        }*/
        return null;
    }

    /**
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento aumentarNumeroVariosCiudadanos(HttpServletRequest request)
            throws AccionWebException {
        /*
              this.preservarInfoBasicaFolio(request);
              preservarInfoBasicaAnotacion(request);
              preservarInfoCancelacion(request);
              preservarInfoBasicaVariosCiudadanos(request);
         */
        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);
        numCiud++;
        this.session = request.getSession();

        Integer num = new Integer(numCiud);
        this.session.setAttribute(NUM_REGISTROS_TABLA_CIUDADANOS, num);
        request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO,
                new Boolean(true));

        return null;
    }

    /**
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento disminuirNumeroVariosCiudadanos(HttpServletRequest request)
            throws AccionWebException {
        /*
                        preservarInfoBasicaFolio(request);
                        preservarInfoBasicaAnotacion(request);
                        preservarInfoBasicaVariosCiudadanos(request);
         */
        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);

        if (numCiud > 1) {
            numCiud--;
        }

        this.session = request.getSession();

        Integer num = new Integer(numCiud);
        this.session.setAttribute(NUM_REGISTROS_TABLA_CIUDADANOS, num);
        request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO,
                new Boolean(true));

        return null;
    }

    private void preservarInfoBasicaVariosCiudadanos(HttpServletRequest request) {
        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);

        this.session = request.getSession();

        String key = null;
        Object parametro = null;

        for (int i = 0; i < numCiud; i++) {
            key = CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_TIPO_ID_PERSONA + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_TIPO_PERSONA + i;
            parametro = request.getParameter(key);
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_COD_VERIFICACION + i;
            parametro = request.getParameter(key);
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_SEXO_PERSONA + i;
            parametro = request.getParameter(key);
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_NUM_ID_PERSONA + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_APELLIDO_1_PERSONA + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_APELLIDO_2_PERSONA + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_NOMBRES_PERSONA + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                session.setAttribute(key, parametro);
            }
        }
    }

    private void eliminarInfoBasicaVariosCiudadanos(HttpServletRequest request) {
        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);

        for (int i = 0; i < numCiud; i++) {
            request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA
                    + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_PERSONA
                    + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_SEXO_PERSONA
                    + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_COD_VERIFICACION
                    + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_NUM_ID_PERSONA
                    + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA
                    + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA
                    + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_NOMBRES_PERSONA
                    + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_INTER_ASOCIACION
                    + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_PORCENTAJE_ASOCIACION
                    + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION
                    + i);
        }
    }

    private int numeroRegistrosTablaAgregarCiudadanos(
            HttpServletRequest request) {
        session = request.getSession();

        Integer num = (Integer) session.getAttribute(NUM_REGISTROS_TABLA_CIUDADANOS);
        int numCiud;

        if (num == null) {
            numCiud = DEFAULT_NUM_CIUDADANOS_TABLA;
        } else {
            numCiud = num.intValue();
        }

        return numCiud;
    }

    /**
     * @param request
     * @return
     */
    private Evento eliminarCiudadanoEdicion(HttpServletRequest request)
            throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new Vector();
        }

        /**
         * Organizar los ciudadanos. Se debe tener en cuenta el orden ya que el
         * helper (VariosCiuddanosAnotacionHelper)los muestra de esta manera y
         * se necesita manejar el mismo contador para eliminar el que realmente
         * se esta indicando
         */
        Iterator itCiudadanos = ciudadanos.iterator();
        List losA = new ArrayList();
        List losDe = new ArrayList();
        while (itCiudadanos.hasNext()) {
            AnotacionCiudadano anotacionCiudadano = (AnotacionCiudadano) itCiudadanos.next();
            String rol = anotacionCiudadano.getRolPersona();
            if (rol.equals("DE")) {
                losDe.add(anotacionCiudadano);
            } else if (rol.equals("A")) {
                losA.add(anotacionCiudadano);
            }
        }
        losDe.addAll(losA);
        ciudadanos.clear();
        ciudadanos = losDe;

        try {
            int aplicacionNumero = Integer.parseInt(request.getParameter(
                    AWCalificacion.POSICION));
            AnotacionCiudadano c = (AnotacionCiudadano) ciudadanos.get(aplicacionNumero);
            c.setToDelete(true);
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException(
                    "El número del documento a eliminar es inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (ciudadanos.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacía");
            }

            throw new ParametroInvalidoException(
                    "El índice del documento a eliminar está fuera del rango");
        }

        request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION,
                ciudadanos);

        String vista = (String) request.getSession().getAttribute("ULITIMA_VISTA_TEMPORAL");

        if (vista != null) {
            request.getSession().setAttribute(SMARTKeys.VISTA_ACTUAL, vista);
        }

        return null;
    }

    private void eliminarInfoBasicaAnotacion(HttpServletRequest request) {
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        request.getSession().removeAttribute(CFolio.ANOTACION_ID_ANOTACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_FECHA_RADICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_NUM_DOCUMENTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        request.getSession().removeAttribute(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS);
        request.getSession().removeAttribute("tipo_oficina");
        request.getSession().removeAttribute("tipo_nom_oficina");
        request.getSession().removeAttribute("numero_oficina");
        request.getSession().removeAttribute("id_oficina");
    }

    private void eliminarInfoBasicaFolio(HttpServletRequest request) {
        request.getSession().removeAttribute(CFolio.ID_MATRICULA);
        request.getSession().removeAttribute(CFolio.FOLIO_COD_CATASTRAL);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_ID_DEPTO);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_NOM_DEPTO);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_ID_MUNIC);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_NOM_MUNIC);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_ID_VEREDA);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_NOM_VEREDA);
        request.getSession().removeAttribute(CFolio.FOLIO_TIPO_PREDIO);
        request.getSession().removeAttribute(CFolio.FOLIO_COMPLEMENTACION);
        request.getSession().removeAttribute(CFolio.FOLIO_LINDERO);
        request.getSession().removeAttribute(CFolio.FOLIO_COD_CATASTRAL);
        request.getSession().removeAttribute(CFolio.FOLIO_EJE1);
        request.getSession().removeAttribute(CFolio.FOLIO_VALOR1);
        request.getSession().removeAttribute(CFolio.FOLIO_EJE2);
        request.getSession().removeAttribute(CFolio.FOLIO_VALOR2);
        request.getSession().removeAttribute(CFolio.FOLIO_COMPLEMENTO_DIR);
        request.getSession().removeAttribute(CFolio.FOLIO_COD_DOCUMENTO);
        request.getSession().removeAttribute(CFolio.FOLIO_FECHA_APERTURA);
        request.getSession().removeAttribute(CFolio.FOLIO_TIPO_PREDIO);
        request.getSession().removeAttribute(CFolio.FOLIO_ESTADO_FOLIO);
        request.getSession().removeAttribute(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO);
    }

    /**
     * @param request
     * @param i
     * @return
     */
    private boolean agregoPersona(HttpServletRequest request, int i) {
        int cantDatosValidos = 0;

        String numId = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA + i);
        if (numId != null && numId.trim().length() > 0) {
            cantDatosValidos++;
        }
        String apellido1 = request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA + i);
        if (apellido1 != null && apellido1.trim().length() > 0) {
            cantDatosValidos++;
        }

        //Comentado dado que no es obligatorio el segundo apellido para ningun tipo de
        //Docuemento de identidad.
        /*String apellido2 = request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA + i);
		if (apellido2 != null) {
			return true;
		}*/
        String tipoID = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA);
        String nombres = request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA + i);

        if (nombres != null && nombres.trim().length() > 0) {
            cantDatosValidos++;
        }

        boolean verificarNombre = false;
        if (tipoID != null && (tipoID.equals(CCiudadano.TIPO_DOC_ID_CEDULA)
                || tipoID.equals(CCiudadano.TIPO_DOC_ID_CEDULA_EXTRANJERIA)
                || tipoID.equals(CCiudadano.TIPO_DOC_ID_TARJETA))) {
            verificarNombre = true;
        }

        if (verificarNombre && nombres != null && nombres.trim().length() > 0) {
            cantDatosValidos++;
        }

        String valPorcentaje = request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i);
        if (valPorcentaje != null && valPorcentaje.trim().length() > 0) {
            cantDatosValidos++;
        }

        if (cantDatosValidos > 0) {
            return true;
        }

        return false;
    }

    /**
     * Permite agregar una nueva dirección al folio objeto de corrección.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento agregarDireccion(HttpServletRequest request)
            throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);

        List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if (direcciones == null) {
            direcciones = new Vector();
        }

        ValidacionParametrosException exception = new ValidacionParametrosException();

        String valorEje1 = request.getParameter(CFolio.FOLIO_EJE1);
        String valorValor1 = request.getParameter(CFolio.FOLIO_VALOR1);
        String valorEje2 = request.getParameter(CFolio.FOLIO_EJE2);
        String valorValor2 = request.getParameter(CFolio.FOLIO_VALOR2);
        String complemento = request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR);

        if ((valorEje1.length() <= 0)
                || valorEje1.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError(
                    "Debe seleccionar el primer eje válido para la direccion");
        } else {
            if (!valorEje1.equals(CDireccion.SIN_DIRECCION)) {
                if (valorValor1.length() <= 0) {
                    exception.addError(
                            "Debe ingresar valor válido para el primer eje de la direccion");
                }

                if ((valorEje2.length() <= 0)
                        || valorEje2.equals(WebKeys.SIN_SELECCIONAR)) {
                    valorEje2 = new String();
                } else {
                    if (valorValor2.length() <= 0) {
                        exception.addError(
                                "Debe ingresar valor válido para el segundo eje  de la direccion");
                    }
                }
            }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        List ejes = (List) request.getSession().getServletContext()
                .getAttribute(WebKeys.LISTA_EJES_DIRECCION);
        Direccion direccion = new Direccion();
        direccion.setEspecificacion(complemento != null ? complemento.toUpperCase() : null);

        if (ejes != null) {
            Iterator itEje = ejes.iterator();

            while (itEje.hasNext()) {
                ElementoLista elementoEje = (ElementoLista) itEje.next();
                Eje eje;

                if (elementoEje.getId().equals(valorEje1)) {
                    eje = new Eje();
                    eje.setIdEje(elementoEje.getId());
                    eje.setNombre(elementoEje.getValor());
                    direccion.setEje(eje);
                    direccion.setValorEje(valorValor1);
                }

                if (elementoEje.getId().equals(valorEje2)) {
                    eje = new Eje();
                    eje.setIdEje(elementoEje.getId());
                    eje.setNombre(elementoEje.getValor());
                    direccion.setEje1(eje);
                }

                if (valorValor2 != null) {
                    direccion.setValorEje1(valorValor2);
                }
            }
        } else {
            exception.addError("La lista de los ejes no se encontro");
        }

        direcciones.add(direccion);

        request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION,
                direcciones);

        return null;
    }

    /**
     * Permite eliminar una dirección de un folio determinado.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento eliminarDireccion(HttpServletRequest request)
            throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);

        List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if (direcciones == null) {
            direcciones = new Vector();
        }

        try {
            int aplicacionNumero = Integer.parseInt(request.getParameter(
                    WebKeys.POSICION));
            direcciones.remove(aplicacionNumero);
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException(
                    "El número del documento a eliminar es inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (direcciones.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacía");
            }

            throw new ParametroInvalidoException(
                    "El índice del documento a eliminar está fuera del rango");
        }

        request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION,
                direcciones);

        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento preservarInfoAnotacion(HttpServletRequest request) {
        preservarInfoBasicaAnotacion(request);

        return null;
    }

    private void preservarInfoBasicaFolio(HttpServletRequest request) {
        //Se salva temporalmente la informacion sobre el folio. Esto no hace persistente el folio, ni cumple con el requerimiento de grabar de manera temporal
        if (request.getParameter(CFolio.FOLIO_NUM_RADICACION) != null) {
            session.setAttribute(CFolio.FOLIO_NUM_RADICACION,
                    request.getParameter(CFolio.FOLIO_NUM_RADICACION));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO) != null) {
            session.setAttribute(CFolio.FOLIO_LOCACION_ID_DEPTO,
                    request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_DEPTO) != null) {
            session.setAttribute(CFolio.FOLIO_LOCACION_NOM_DEPTO,
                    request.getParameter(CFolio.FOLIO_LOCACION_NOM_DEPTO));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC) != null) {
            session.setAttribute(CFolio.FOLIO_LOCACION_ID_MUNIC,
                    request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_MUNIC) != null) {
            session.setAttribute(CFolio.FOLIO_LOCACION_NOM_MUNIC,
                    request.getParameter(CFolio.FOLIO_LOCACION_NOM_MUNIC));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA) != null) {
            session.setAttribute(CFolio.FOLIO_LOCACION_ID_VEREDA,
                    request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_VEREDA) != null) {
            session.setAttribute(CFolio.FOLIO_LOCACION_NOM_VEREDA,
                    request.getParameter(CFolio.FOLIO_LOCACION_NOM_VEREDA));
        }

        if (request.getParameter(CFolio.FOLIO_ESTADO_FOLIO) != null) {
            session.setAttribute(CFolio.FOLIO_ESTADO_FOLIO,
                    request.getParameter(CFolio.FOLIO_ESTADO_FOLIO));
        }

        if (request.getParameter(CFolio.FOLIO_TIPO_PREDIO) != null) {
            session.setAttribute(CFolio.FOLIO_TIPO_PREDIO,
                    request.getParameter(CFolio.FOLIO_TIPO_PREDIO));
        }

        if (request.getParameter(CFolio.FOLIO_NUPRE) != null) {
            session.setAttribute(CFolio.NUPRE, request.getParameter(CFolio.NUPRE));
        }

        if (request.getParameter(CFolio.FOLIO_DETERMINACION_INMUEBLE) != null) {
            session.setAttribute(CFolio.DETERMINACION_INMUEBLE, request.getParameter(CFolio.DETERMINACION_INMUEBLE));
        }

        if (request.getParameter(CFolio.FOLIO_AVALUO) != null) {
            session.setAttribute(CFolio.AVALUO, request.getParameter(CFolio.AVALUO));
        }

        if (request.getParameter(CFolio.FOLIO_FECHA_AVALUO) != null) {
            session.setAttribute(CFolio.FECHA_AVALUO, request.getParameter(CFolio.FECHA_AVALUO));
        }

        if (request.getParameter(CFolio.FOLIO_DESTINACION_ECONOMICA) != null) {
            session.setAttribute(CFolio.DESTINACION_ECONOMICA, request.getParameter(CFolio.DESTINACION_ECONOMICA));
        }

        if (request.getParameter(CFolio.FOLIO_PRIVMETROS) != null) {
            session.setAttribute(CFolio.PRIVMETROS, request.getParameter(CFolio.PRIVMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_PRIVCENTIMETROS) != null) {
            session.setAttribute(CFolio.PRIVCENTIMETROS, request.getParameter(CFolio.PRIVCENTIMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_CONSMETROS) != null) {
            session.setAttribute(CFolio.CONSMETROS, request.getParameter(CFolio.CONSMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_CONSCENTIMETROS) != null) {
            session.setAttribute(CFolio.CONSCENTIMETROS, request.getParameter(CFolio.CONSCENTIMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_COEFICIENTE) != null) {
            session.setAttribute(CFolio.COEFICIENTE, request.getParameter(CFolio.COEFICIENTE));
        }

        if (request.getParameter(CFolio.FOLIO_HECTAREAS) != null) {
            session.setAttribute(CFolio.HECTAREAS, request.getParameter(CFolio.HECTAREAS));
        }

        if (request.getParameter(CFolio.FOLIO_METROS) != null) {
            session.setAttribute(CFolio.METROS, request.getParameter(CFolio.METROS));
        }

        if (request.getParameter(CFolio.FOLIO_CENTIMETROS) != null) {
            session.setAttribute(CFolio.CENTIMETROS, request.getParameter(CFolio.CENTIMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_LINDEROS_DEFINIDOS) != null) {
            session.setAttribute(CFolio.LINDEROS_DEFINIDOS, request.getParameter(CFolio.LINDEROS_DEFINIDOS));
        }

        if (request.getParameter(CFolio.FOLIO_COMPLEMENTACION) != null) {
            session.setAttribute(CFolio.FOLIO_COMPLEMENTACION,
                    request.getParameter(CFolio.FOLIO_COMPLEMENTACION));
        }

        if (request.getParameter(CFolio.FOLIO_LINDERO) != null) {
            session.setAttribute(CFolio.FOLIO_LINDERO,
                    request.getParameter(CFolio.FOLIO_LINDERO));
        }

        if (request.getParameter(CFolio.FOLIO_TIPO_PREDIO) != null) {
            session.setAttribute(CFolio.FOLIO_TIPO_PREDIO,
                    request.getParameter(CFolio.FOLIO_TIPO_PREDIO));
        }

        if (request.getParameter(CFolio.FOLIO_COD_CATASTRAL) != null) {
            session.setAttribute(CFolio.FOLIO_COD_CATASTRAL,
                    request.getParameter(CFolio.FOLIO_COD_CATASTRAL));
        }

        if (request.getParameter(CFolio.FOLIO_EJE1) != null) {
            session.setAttribute(CFolio.FOLIO_EJE1,
                    request.getParameter(CFolio.FOLIO_EJE1));
        }

        if (request.getParameter(CFolio.FOLIO_EJE2) != null) {
            session.setAttribute(CFolio.FOLIO_EJE2,
                    request.getParameter(CFolio.FOLIO_EJE2));
        }

        if (request.getParameter(CFolio.FOLIO_VALOR1) != null) {
            session.setAttribute(CFolio.FOLIO_VALOR1,
                    request.getParameter(CFolio.FOLIO_VALOR1));
        }

        if (request.getParameter(CFolio.FOLIO_VALOR2) != null) {
            session.setAttribute(CFolio.FOLIO_VALOR2,
                    request.getParameter(CFolio.FOLIO_VALOR2));
        }

        if (request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR) != null) {
            session.setAttribute(CFolio.FOLIO_COMPLEMENTO_DIR,
                    request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR));
        }
    }

    private void preservarInfoBasicaAnotacion(HttpServletRequest request) {
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM,
                    request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO,
                    request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO,
                    request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA,
                    request.getParameter(
                            CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA));
        }

        if (request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION) != null) {
            session.setAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION,
                    request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_FECHA_DOCUMENTO) != null) {
            session.setAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO,
                    request.getParameter(CFolio.ANOTACION_FECHA_DOCUMENTO));
        }

        if (request.getParameter(CFolio.ANOTACION_FECHA_RADICACION) != null) {
            session.setAttribute(CFolio.ANOTACION_FECHA_RADICACION,
                    request.getParameter(CFolio.ANOTACION_FECHA_RADICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_NUM_RADICACION) != null) {
            session.setAttribute(CFolio.ANOTACION_NUM_RADICACION,
                    request.getParameter(CFolio.ANOTACION_NUM_RADICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION) != null) {
            session.setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION,
                    request.getParameter(
                            CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_MODALIDAD) != null) {
            session.setAttribute(CFolio.ANOTACION_MODALIDAD,
                    request.getParameter(CFolio.ANOTACION_MODALIDAD));
        }

        if (request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO) != null) {
            session.setAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO,
                    request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO));
        }

        if (request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION) != null) {
            session.setAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION,
                    request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO) != null) {
            session.setAttribute(CFolio.ANOTACION_NUM_DOCUMENTO,
                    request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO,
                    request.getParameter(
                            CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO,
                    request.getParameter(
                            CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC,
                    request.getParameter(
                            CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC,
                    request.getParameter(
                            CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA,
                    request.getParameter(
                            CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA,
                    request.getParameter(
                            CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            session.setAttribute("tipo_oficina",
                    request.getParameter("tipo_oficina"));
            session.setAttribute("tipo_nom_oficina",
                    request.getParameter("tipo_nom_oficina"));
            session.setAttribute("numero_oficina",
                    request.getParameter("numero_oficina"));
            session.setAttribute("id_oficina",
                    request.getParameter("id_oficina"));
        }

        if (request.getParameter(
                CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID) != null) {
            session.setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID,
                    request.getParameter(
                            CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID));
        }

        if (request.getParameter(
                CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM) != null) {
            session.setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM,
                    request.getParameter(
                            CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM));
        }
    }

    private void eliminarInfoBasicaAnotacion() {
        session.removeAttribute(CFolio.ANOTACION_NUM_RADICACION);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        session.removeAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        session.removeAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO);
        session.removeAttribute(CFolio.ANOTACION_FECHA_RADICACION);
        session.removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION);
        session.removeAttribute(CFolio.ANOTACION_MODALIDAD);
        session.removeAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO);
        session.removeAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        session.removeAttribute(CFolio.ANOTACION_NUM_DOCUMENTO);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        session.removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        session.removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        session.removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS);
        session.removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS);
        session.removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS);
        session.removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS);
        session.removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS);
        session.removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS);
        session.removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS);
        session.removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS);
        session.removeAttribute("tipo_oficina");
        session.removeAttribute("tipo_nom_oficina");
        session.removeAttribute("numero_oficina");
        session.removeAttribute("id_oficina");
    }

    private void eliminarInfoBasicaFolio() {
        session.removeAttribute(CFolio.FOLIO_COD_CATASTRAL);
        session.removeAttribute(CFolio.FOLIO_LOCACION_ID_DEPTO);
        session.removeAttribute(CFolio.FOLIO_LOCACION_NOM_DEPTO);
        session.removeAttribute(CFolio.FOLIO_LOCACION_ID_MUNIC);
        session.removeAttribute(CFolio.FOLIO_LOCACION_NOM_MUNIC);
        session.removeAttribute(CFolio.FOLIO_LOCACION_ID_VEREDA);
        session.removeAttribute(CFolio.FOLIO_LOCACION_NOM_VEREDA);
        session.removeAttribute(CFolio.FOLIO_TIPO_PREDIO);
        session.removeAttribute(CFolio.FOLIO_NUPRE);
        session.removeAttribute(CFolio.FOLIO_DETERMINACION_INMUEBLE);
        session.removeAttribute(CFolio.FOLIO_AVALUO);
        session.removeAttribute(CFolio.FOLIO_FECHA_AVALUO);
        session.removeAttribute(CFolio.FOLIO_DESTINACION_ECONOMICA);
        session.removeAttribute(CFolio.FOLIO_PRIVMETROS);
        session.removeAttribute(CFolio.FOLIO_PRIVCENTIMETROS);
        session.removeAttribute(CFolio.FOLIO_CONSMETROS);
        session.removeAttribute(CFolio.FOLIO_CONSCENTIMETROS);
        session.removeAttribute(CFolio.FOLIO_COEFICIENTE);
        session.removeAttribute(CFolio.FOLIO_HECTAREAS);
        session.removeAttribute(CFolio.FOLIO_METROS);
        session.removeAttribute(CFolio.FOLIO_CENTIMETROS);
        session.removeAttribute(CFolio.FOLIO_LINDEROS_DEFINIDOS);
        session.removeAttribute(CFolio.FOLIO_COMPLEMENTACION);
        session.removeAttribute(CFolio.FOLIO_LINDERO);
        session.removeAttribute(CFolio.FOLIO_EJE1);
        session.removeAttribute(CFolio.FOLIO_VALOR1);
        session.removeAttribute(CFolio.FOLIO_EJE2);
        session.removeAttribute(CFolio.FOLIO_VALOR2);
        session.removeAttribute(CFolio.FOLIO_COMPLEMENTO_DIR);
    }

    private static Calendar darFecha(String fechaInterfaz) {

        java.util.Date date = null;

        try {
            date = DateFormatUtil.parse(fechaInterfaz);
            if (fechaInterfaz.indexOf("-") != -1) {
                return null;
            }
        } catch (ParseException e) {
            return null;
        } catch (Throwable t) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        String[] partido = fechaInterfaz.split("/");
        Date dHoy = new Date();

        Calendar hoy = Calendar.getInstance();
        hoy.setTime(dHoy);

        if (partido.length == 3) {
            int dia = Integer.parseInt(partido[0]);
            int mes = Integer.parseInt(partido[1]) - 1;
            int año = Integer.parseInt(partido[2]);
            calendar.set(año, mes, dia);

            if ((calendar.get(Calendar.YEAR) == año)
                    && (calendar.get(Calendar.MONTH) == mes)
                    && (calendar.get(Calendar.DAY_OF_MONTH) == dia)
                    && !calendar.after(hoy)) {
                return calendar;
            }
        }

        return null;
    }

    private void preservarInfoBasicaCiudadano(HttpServletRequest request) {
        if (request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA) != null) {
            session.setAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA,
                    request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_TIPO_PERSONA) != null) {
            session.setAttribute(CFolio.ANOTACION_TIPO_PERSONA,
                    request.getParameter(CFolio.ANOTACION_TIPO_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_SEXO_PERSONA) != null) {
            session.setAttribute(CFolio.ANOTACION_SEXO_PERSONA,
                    request.getParameter(CFolio.ANOTACION_SEXO_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_COD_VERIFICACION) != null) {
            session.setAttribute(CFolio.ANOTACION_COD_VERIFICACION,
                    request.getParameter(CFolio.ANOTACION_COD_VERIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA) != null) {
            session.setAttribute(CFolio.ANOTACION_NUM_ID_PERSONA,
                    request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA) != null) {
            session.setAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA,
                    request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA) != null) {
            session.setAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA,
                    request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA) != null) {
            session.setAttribute(CFolio.ANOTACION_NOMBRES_PERSONA,
                    request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION) != null) {
            session.setAttribute(CFolio.ANOTACION_TIPO_INTER_ASOCIACION,
                    request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION));
        }

        if (request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION) != null) {
            session.setAttribute(CFolio.ANOTACION_PORCENTAJE_ASOCIACION,
                    request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION));
        }
    }

    private void preservarInfoAntiguoSistemaAnotacion(
            HttpServletRequest request) {
        session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS));

        session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS));

        session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS));

        session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS));

        session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS));

        session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS,
                request.getParameter(
                        CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS));

        session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS));

        session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS));
    }

    private void eliminarInfoBasicaCiudadano(HttpServletRequest request) {
        session.removeAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA);
        session.removeAttribute(CFolio.ANOTACION_TIPO_PERSONA);
        session.removeAttribute(CFolio.ANOTACION_SEXO_PERSONA);
        session.removeAttribute(CFolio.ANOTACION_COD_VERIFICACION);
        session.removeAttribute(CFolio.ANOTACION_NUM_ID_PERSONA);
        session.removeAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA);
        session.removeAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA);
        session.removeAttribute(CFolio.ANOTACION_NOMBRES_PERSONA);
        session.removeAttribute(CFolio.ANOTACION_TIPO_INTER_ASOCIACION);
        session.removeAttribute(CFolio.ANOTACION_PORCENTAJE_ASOCIACION);
    }

    /**
     * @author : Carlos Mario Torres Urina
     * @casoMantis : 0012705.
     * @actaReq : Acta - Requerimiento No
     * 056_453_Modificiación_de_Naturaleza_Jurídica
     * @change :
     */
    private boolean existeNaturaleza(String idNaturaleza, List gruposNatualeza, NaturalezaJuridica natJuridica) {
        for (Iterator it = gruposNatualeza.iterator(); it.hasNext();) {
            GrupoNaturalezaJuridica grupo = (GrupoNaturalezaJuridica) it.next();

            if (grupo.getNaturalezaJuridicas().iterator() == null) {
                continue;
            }

            for (Iterator itNat = grupo.getNaturalezaJuridicas().iterator();
                    itNat.hasNext();) {
                NaturalezaJuridica naturaleza = (NaturalezaJuridica) itNat.next();

                if (naturaleza.getIdNaturalezaJuridica().equals(idNaturaleza)) {
                    /**
                     * @author : Carlos Mario Torres Urina
                     * @casoMantis : 0012705.
                     * @actaReq : Acta - Requerimiento No
                     * 056_453_Modificiación_de_Naturaleza_Jurídica
                     * @change :
                     */
                    natJuridica = naturaleza;
                    return true;
                }
            }
        }

        return false;
    }

    private boolean existeId(List tiposDoc, String id_tipo_encabezado) {
        for (Iterator it = tiposDoc.iterator(); it.hasNext();) {
            ElementoLista e = (ElementoLista) it.next();

            if (e.getId().equals(id_tipo_encabezado)) {
                return true;
            }
        }

        return false;
    }
}

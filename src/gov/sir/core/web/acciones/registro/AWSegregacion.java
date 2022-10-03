package gov.sir.core.web.acciones.registro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import gov.sir.core.eventos.comun.EvnCiudadano;
import gov.sir.core.eventos.comun.EvnRespCiudadano;
import gov.sir.core.eventos.registro.EvnSegregacion;
import gov.sir.core.eventos.registro.EvnRespSegregacion;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Complementacion;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.Direccion;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.Eje;
import gov.sir.core.negocio.modelo.EstadoFolio;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.LLavesMostrarFolioHelper;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.NaturalezaJuridica;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.SalvedadAnotacion;
import gov.sir.core.negocio.modelo.SalvedadFolio;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.WebAnotaHereda;
import gov.sir.core.negocio.modelo.WebAnotacion;
import gov.sir.core.negocio.modelo.WebCiudadano;
import gov.sir.core.negocio.modelo.WebDocumento;
import gov.sir.core.negocio.modelo.WebFolioDerivado;
import gov.sir.core.negocio.modelo.WebFolioHeredado;
import gov.sir.core.negocio.modelo.WebSegregacion;
import gov.sir.core.negocio.modelo.constantes.CAnotacion;
import gov.sir.core.negocio.modelo.constantes.CAnotacionCiudadano;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CDepartamento;
import gov.sir.core.negocio.modelo.constantes.CDireccion;
import gov.sir.core.negocio.modelo.constantes.CDocumento;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CFolioDerivado;
import gov.sir.core.negocio.modelo.constantes.CMunicipio;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CSalvedadAnotacion;
import gov.sir.core.negocio.modelo.constantes.CSalvedadFolio;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro;
import gov.sir.core.negocio.modelo.constantes.CTipoDocumento;
import gov.sir.core.negocio.modelo.constantes.CVereda;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.correccion.AWModificarFolio;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ParametroInvalidoException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoSegregacionCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosEditarFolioException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosSegregacion;
import gov.sir.core.web.acciones.excepciones.ValidacionSegregacionAnotacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionSegregacionEscogerAnotacionException;
import gov.sir.core.web.helpers.comun.DatosRespuestaPaginador;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.MostrarFolioHelper;
import gov.sir.core.web.helpers.comun.ValidacionDatosOficinaOrigen;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author ppabon Esta accion web se encarga de generar eventos de negocio
 * relacionados con el proceso de segregación. Se encarga de realizar las
 * validaciones primarias a nivel web y por medio de eventos hace llamados a la
 * capa de negocio para que su vez llame los servicios que se requieren.
 */
public class AWSegregacion extends SoporteAccionWeb {

    private String accion;

    /**
     * Id de accion cuando se cancela en paso x de wizard
     */
    public static final String CANCELAR_SEGREGACION = "CANCELAR_SEGREGACION";

    /**
     * Id de acción cuando se desea eliminar una segregación en curso
     */
    public static final String ELIMINAR_SEGREGACION = "ELIMINAR_SEGREGACION";

    /**
     * HELPER DE VARIOS CIUDADANOS
     */
    public static final int DEFAULT_NUM_CIUDADANOS_TABLA = 2;

    /**
     * Constante que indica el número de registros de ciudadanos que tiene la
     * sesión*
     */
    public static final String NUM_REGISTROS_TABLA_CIUDADANOS = "NUM_REGISTROS_TABLA_CIUDADANOS";

    /**
     * Constante que indica que se va a remover un ciudadano de la anotacion
     */
    public static final String ELIMINAR_CIUDADANO_ANOTACION = "ELIMINAR_CIUDADANO_ANOTACION";

    //VARIABLES PARA MANEJAR EL HELPER DE CIUDADANOS
    /**
     * Constante que indica que se van a agregar varios ciudadanos a una
     * anotacion*
     */
    public static final String AGREGAR_VARIOS_CIUDADANOS_SEGREGACION = "AGREGAR_VARIOS_CIUDADANOS_SEGREGACION";

    /**
     * Constante que indica que se va a agregar un registro en la tabla de
     * agragar ciudadanos*
     */
    public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION";

    /**
     * Constante que indica que se va a remover un registro en la tabla de
     * agragar ciudadanos*
     */
    public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION";

    /**
     * Constante que indica que se va a remover un ciudadano de la anotacion
     */
    public static final String ELIMINAR_CIUDADANO_ANOTACION_SEGREGACION = "ELIMINAR_CIUDADANO_ANOTACION_SEGREGACION";

    /**
     * Constante para actualizar los datos de la anotación sobre la cuál se
     * desea realizar la segregación
     */
    public final static String REFRESCAR_ANOTACION_SEGREGACION = "REFRESCAR_ANOTACION_SEGREGACION";

    /**
     * Constante que indica que se va a validar un ciudadano de la anotacion
     */
    public final static String VALIDAR_CIUDADANO_SEGREGACION = "VALIDAR_CIUDADANO_SEGREGACION";

    //VARIABLES PARA PASAR POR LA FASE DE LA CREACIÓN DE UNA ANOTACIÓN, FASE 1 DE UNA SEGREGACIÓN
    /**
     * Constante para llamar a la acción que guarda una anotación en el objeto
     * WebSegregacion
     */
    public final static String SEGREGACION_ANOTACION = "SEGREGACION_ANOTACION";

    /**
     * Constante para llamar a la acción que guarda una anotación en el objeto
     * WebSegregacion
     */
    public final static String SEGREGACION_SIN_ANOTACION = "SEGREGACION_SIN_ANOTACION";

    /**
     * Constante para llamar a la acción que guarda una anotación de las que
     * había creado el usuario
     */
    public final static String SEGREGACION_ESCOGER_ANOTACION = "SEGREGACION_ESCOGER_ANOTACION";

    /**
     * Constante para llamar a la acción que guarda los identificadores de las
     * anotaciones que deben ser copiadas a los nuevos folios
     */
    public final static String SEGREGACION_HERENCIA = "SEGREGACION_HERENCIA";

    /**
     * Constante para agregar un folio derivado a la lista de folios derivados
     */
    public static final String AGREGAR_FOLIO_DERIVADO = "AGREGAR_FOLIO_DERIVADO";

    /**
     * Constante para editar un folio derivado
     */
    public static final String EDITAR_FOLIO_DERIVADO = "EDITAR_FOLIO_DERIVADO";

    /**
     * Constante para agregar un folio derivado a la lista de folios derivados
     */
    public static final String ELIMINAR_FOLIO_DERIVADO = "ELIMINAR_FOLIO_DERIVADO";

    /**
     * Constante para realizar la segregación con los folios derivados
     */
    public static final String SEGREGAR_MASIVO = "SEGREGAR_MASIVO";

    /**
     * Constante para realizar la consulta del nuevo folio creado y poder
     * editarlo.
     */
    public static final String CONSULTAR_NUEVO_FOLIO = "CONSULTAR_NUEVO_FOLIO";

    /**
     * Constante para guardar los cambios que se han realizado a los folios
     * segregados.
     */
    public static final String GUARDAR_CAMBIOS_FOLIO = "GUARDAR_CAMBIOS_FOLIO";

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
     * Constante para llamar a la acción que guarda los cambios realizados en la
     * segregación
     */
    public final static String GUARDAR_WEB_SEGREGACION = "GUARDAR_WEB_SEGREGACION";

    /**
     * Constante que indica que se van a agregar consultar los ultimos
     * propietarios de una folio*
     */
    public static final String GET_ULTIMOS_PROPIETARIOS = "GET_ULTIMOS_PROPIETARIOS";

    /**
     * Constante que indica que se van a agregar consultar los ultimos
     * propietarios de una folio*
     */
    public static final String LISTA_PROPIETARIOS_FOLIO = "LISTA_PROPIETARIOS_FOLIO";

    /**
     * Constante que indica que se van a agregar al la lista de ciudadanos los
     * propietarios de una folio*
     */
    public static final String GUARDAR_PROPIETARIOS = "GUARDAR_PROPIETARIOS";

    /**
     * Constante que indica que se cancela el proceso de consultar los ultimos
     * propietarios*
     */
    public static final String CANCELAR_PROPIETARIOS = "CANCELAR_PROPIETARIOS";

    /**
     * Crea una instancia de la accion web
     */
    public AWSegregacion() {
        super();
    }

    /* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#perform(javax.servlet.http.HttpServletRequest)
     */
    public Evento perform(HttpServletRequest request) throws AccionWebException {

        accion = request.getParameter(WebKeys.ACCION);

        if (request.getSession().getAttribute(WebKeys.VISTA_PARA_REGRESAR) != null) {
            request.getSession().setAttribute(SMARTKeys.VISTA_ACTUAL, request.getSession().getAttribute(WebKeys.VISTA_PARA_REGRESAR));
            request.getSession().removeAttribute(WebKeys.VISTA_PARA_REGRESAR);
        }

        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion valida");
        }

        //PAGÍNA CREACIÓN DE ANOTACIÓN
        if (accion.equals(REFRESCAR_ANOTACION_SEGREGACION)) {
            return refrescarAnotacion(request);
        } else if (accion.equals(VALIDAR_CIUDADANO_SEGREGACION)) {
            return validarCiudadano(request);
        } else if (accion.equals(AGREGAR_VARIOS_CIUDADANOS_SEGREGACION)) {
            return agregarVariosCiudadanos(request, accion);
        } else if (accion.equals(AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION)) {
            return aumentarNumeroVariosCiudadanos(request);
        } else if (accion.equals(REMOVER_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION)) {
            return disminuirNumeroVariosCiudadanos(request);
        } else if (accion.equals(ELIMINAR_CIUDADANO_ANOTACION_SEGREGACION)) {
            return eliminarCiudadano(request);
        } else if (accion.equals(SEGREGACION_ANOTACION)) {
            /**
             * @author: Guillermo Cabrera.
             * @change: Se adiciona un campo para identificar si se va a
             * realizar una segregacion para cargar en sesion todas las
             * anotaciones del folio ya que anteriormente solo se cargaban las
             * anotaciones del paginador actual, quedando incompletas las
             * anotaciones a heredar. MANTIS: 1726
             *
             */
            request.getSession().setAttribute(WebKeys.CAMPO_ES_SEGREGACION, request.getParameter(WebKeys.CAMPO_ES_SEGREGACION));
            return crearAnotacion(request);
        } else if (accion.equals(SEGREGACION_SIN_ANOTACION)) {
            return crearAnotacionVacia(request);
        } else if (accion.equals(SEGREGACION_ESCOGER_ANOTACION)) {
            return escogerAnotacion(request);
        } else if (accion.equals(SEGREGACION_HERENCIA)) {
            return heredarAnotaciones(request);
        } else if (accion.equals(AGREGAR_FOLIO_DERIVADO)) {
            return agregarFolioDerivado(request);
        } else if (accion.equals(ELIMINAR_FOLIO_DERIVADO)) {
            return eliminarFolioDerivado(request);
        } else if (accion.equals(EDITAR_FOLIO_DERIVADO)) {
            return editarFolioDerivado(request);
        } else if (accion.equals(GUARDAR_WEB_SEGREGACION)) {
            return guardarWebSegregacion(request);
        } else if (accion.equals(SEGREGAR_MASIVO)) {
            return segregarMasivo(request);
        } else if (accion.equals(CONSULTAR_NUEVO_FOLIO)) {
            return consultarNuevoFolio(request);
        } else if (accion.equals(GUARDAR_CAMBIOS_FOLIO)) {
            return guardarCambiosFolio(request);
        } else if (accion.equals(AGREGAR_DIRECCION)) {
            return agregarDireccion(request);
        } else if (accion.equals(ELIMINAR_DIRECCION)) {
            return eliminarDireccion(request);
        } else if (accion.equals(CANCELAR_SEGREGACION)) {
            return cancelarSegregacion(request);
        } else if (accion.equals(SEGREGACION_HERENCIA)) {
            return heredarAnotaciones(request);
        } else if (accion.equals(ELIMINAR_SEGREGACION)) {
            return eliminarSegregacion(request);
        } else if (accion.equals(GET_ULTIMOS_PROPIETARIOS)) {
            return consultarUltimosPropietarios(request);
        } else if (accion.equals(CANCELAR_PROPIETARIOS)) {
            return cancelarPropietario(request);
        } else if (accion.equals(GUARDAR_PROPIETARIOS)) {
            return guardarPropietario(request);
        } //ACCIÓN INVÁLIDA
        else {
            throw new AccionInvalidaException("La acción " + accion + " no es válida.");
        }
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
     * @return
     */
    private Evento agregarVariosCiudadanos(HttpServletRequest request, String accion1) throws AccionWebException {

        ValidacionParametrosException exception;

        if (accion1.equals(AWSegregacion.AGREGAR_VARIOS_CIUDADANOS_SEGREGACION)) {
            exception = new ValidacionParametrosCiudadanoSegregacionCalificacionException();
        } else if (accion1.equals(AWSegregacion.AGREGAR_VARIOS_CIUDADANOS_SEGREGACION)) {
            exception = new ValidacionParametrosCiudadanoCalificacionException();
        } else {
            exception = new ValidacionParametrosException();
        }

        return agregarVariosCiudadanos(request, exception);

    }

    /**
     *
     *
     */
    private Evento agregarVariosCiudadanos(HttpServletRequest request, ValidacionParametrosException exception) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);//varios
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new Vector();
        }

        List ciudadanosFinales = new Vector(ciudadanos);

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

                String tipoId = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA + i);

                if ((tipoId == null) || tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError("Debe escojer un tipo de identificación para la persona " + b + " en la anotacion");
                    datosBien = false;
                }

                String numId = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA + i);
                String apellido1 = request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA + i);
                String apellido2 = request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA + i);
                String nombres = request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA + i);

                String sexo = request.getParameter(CFolio.ANOTACION_SEXO_PERSONA + i);

                if (!tipoPersona.equals(CCiudadano.TIPO_PERSONA_JURIDICA)) {
                    if ((sexo == null) || sexo.equals(WebKeys.SIN_SELECCIONAR)) {
                        exception.addError("Debe escojer un tipo de sexo para la persona en la anotacion");
                        datosBien = false;
                    }
                }

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
                        exception.addError("Documento Inválido para la persona " + b);
                        datosBien = false;
                    }
                    double valorId = 0.0d;
                    if (numId == null) {
                        exception.addError("El número de identificación de la persona " + b + " en la anotación es inválido");
                        datosBien = false;
                    } else {
                        /*
                                            * @author : CTORRES
                                            * @change : Se implemento la validación para solo permitir caracteres alfanuméricos en 
                                            *           numId cuando el tipo de identificación es PASAPORTE.
                                            * Caso Mantis : 11056
                                            * No Requerimiento: 073_151
                         */
                        if (tipoId.contains("PS")) {
                            String regexSL = "^[a-zA-Z]+$";
                            String regexSN = "^[0-9]+$";
                            String regexLN = "^[a-zA0-Z9]+$";
                            java.util.regex.Pattern patternSL = java.util.regex.Pattern.compile(regexSL);
                            java.util.regex.Pattern patternSN = java.util.regex.Pattern.compile(regexSN);
                            java.util.regex.Pattern patternLN = java.util.regex.Pattern.compile(regexLN);
                            boolean esC = false;
                            if (patternSL.matcher(numId).matches()) {
                                esC = true;
                            } else if (patternSN.matcher(numId).matches()) {
                                esC = true;
                            } else if (patternLN.matcher(numId).matches()) {
                                esC = true;
                            } else {
                                datosBien = false;
                                exception.addError("El número de identificación de la persona es inválido. Debe ser alfanumérico");
                            }
                        } else {
                            if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_NIT)) {
                                try {
                                    valorId = Double.parseDouble(numId);
                                    if (valorId <= 0) {
                                        exception.addError("El valor del documento de la persona " + b + " no puede ser negativo o cero");
                                        datosBien = false;
                                    }
                                } catch (NumberFormatException e) {
                                    exception.addError("El número de identificación de la persona " + b + " en la anotación es inválido");
                                    datosBien = false;
                                }
                            }
                        }
                    }
                    if (tipoId.equals(CCiudadano.TIPO_DOC_ID_NIT)) {
                        if (nombres == null || nombres.trim().equals("")) {
                            exception.addError("Razón social inválida del campo " + b);
                            datosBien = false;
                        }
                    } else {
                        if ((nombres == null) || nombres.equals("")) {
                            exception.addError("Debe ingresar el nombre de la persona " + b + " en la anotación");
                            datosBien = false;
                        }

                        if ((apellido1 == null) || apellido1.equals("")) {
                            exception.addError("Debe ingresar el primer apellido de la persona " + b + " en la anotación");
                            datosBien = false;
                        }
                    }
                }

                String tipoIntervencion = request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i);

                if ((tipoIntervencion == null) || tipoIntervencion.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError("Debe ingresar un tipo de intervención para la persona " + b + " en la anotación");
                    datosBien = false;
                }

                AnotacionCiudadano c;
                Iterator ic = ciudadanosFinales.iterator();
                while (ic.hasNext()) {
                    c = (AnotacionCiudadano) ic.next();
                    if ((tipoIntervencion.equals(c.getRolPersona())) && (numId.equals(c.getCiudadano().getDocumento())) && (tipoId.equals(c.getCiudadano().getTipoDoc()))
                            && (!tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA))) {
                        exception.addError("La persona no puede tener dos veces el mismo rol");
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

                    String marcaPropietario = request.getParameter(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i);

                    if (marcaPropietario == null || marcaPropietario.equals("")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.NO_PROPIETARIO);
                    } else if (marcaPropietario.equals("X")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DERECHO_REAL_DOMINIO);
                    } else if (marcaPropietario.equals("I")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DOMINIO_INCOMPLETO);
                    }

                    ciudadanosFinales.add(anotacionCiudadano);
                }

            }
        }

        if (exception.getErrores().size() > 0) {
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        eliminarInfoBasicaVariosCiudadanos(request);

        List listaCompletaCiudadanos = new Vector();

        listaCompletaCiudadanos.addAll(ciudadanosFinales);

        //request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, listaCompletaCiudadanos);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnSegregacion evento = new EvnSegregacion(usuarioAuriga, EvnSegregacion.AGREGAR_CIUDADANO_ANOTACION);
        evento.setAnotacionCiudadanos(ciudadanosFinales);
        evento.setListaCompletaCiudadanos(listaCompletaCiudadanos);
        evento.setTurno(turno);
        return evento;

    }

    /**
     * Valida un ciudadano en anotación, recibe el request, crea el objeto
     * ciudadano y lanza un evento de negocio
     *
     * @param request
     * @return
     */
    private Evento validarCiudadano(HttpServletRequest request) throws AccionWebException {
        //eliminarInfoBasicaAnotacion(request);

        this.preservarAllInfo(request);

        Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioArq = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosCiudadanoSegregacionCalificacionException exception = new ValidacionParametrosCiudadanoSegregacionCalificacionException();
        String ver = request.getParameter(CAnotacionCiudadano.SECUENCIA);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        preservarInfoBasicaVariosCiudadanos(request);

        String tipoDoc = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA + ver);

        if ((tipoDoc == null) || tipoDoc.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe escoger un tipo de identificación para la persona en la anotacion");
        }

        String numDoc = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA + ver);
        if ((numDoc == null) || numDoc.equals("")) {
            exception.addError("Debe digitar un número de identificación");
        }

        if (!exception.getErrores().isEmpty()) {
            preservarInfo(request);
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        request.getSession().setAttribute(CFolio.ANOTACION_NUM_ID_PERSONA + ver, numDoc);

        Ciudadano ciud = new Ciudadano();
        ciud.setDocumento(numDoc);
        ciud.setTipoDoc(tipoDoc);

        //Se setea el circulo del ciudadano
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        ciud.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

        EvnCiudadano evnCiu = new EvnCiudadano(usuarioArq, EvnCiudadano.CONSULTAR_CIUDADANO_ANOTACION, ciud);
        if (turno != null) {
            if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_CORRECCIONES)) {
                evnCiu.setCorrecciones(true);
            } else {
                if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_REGISTRO)) {
                    evnCiu.setRegistro(true);
                }
            }
        }

        return evnCiu;
    }

    /**
     * Método para preservar toda la información del folio.
     *
     * @param request
     */
    private void preservarAllInfo(HttpServletRequest request) {

        for (java.util.Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements();) {
            String key = (String) enumeration.nextElement();
            request.getSession().setAttribute(key, request.getParameter(key));
        }

    }

    /**
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento aumentarNumeroVariosCiudadanos(HttpServletRequest request) throws AccionWebException {

        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);
        numCiud++;
        HttpSession session = request.getSession();
        Integer num = new Integer(numCiud);
        session.setAttribute(NUM_REGISTROS_TABLA_CIUDADANOS, num);

        return null;
    }

    /**
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento disminuirNumeroVariosCiudadanos(HttpServletRequest request) throws AccionWebException {

        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);

        if (numCiud > 1) {
            numCiud--;
        }

        HttpSession session = request.getSession();
        Integer num = new Integer(numCiud);
        session.setAttribute(NUM_REGISTROS_TABLA_CIUDADANOS, num);

        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento eliminarCiudadano(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

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
            int aplicacionNumero = Integer.parseInt(request.getParameter(AWCalificacion.POSICION));
            ciudadanos.remove(aplicacionNumero);
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El número del documento a eliminar es inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (ciudadanos.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacía");
            } else {
                throw new ParametroInvalidoException("El índice del documento a eliminar está fuera del rango");
            }
        }

        request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, ciudadanos);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento crearAnotacion(HttpServletRequest request) throws ValidacionParametrosException {

        HttpSession session = request.getSession();
        Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Date fechaAnotacion = null;

        WebSegregacion webSegregacion = (WebSegregacion) request.getSession().getAttribute(WebKeys.WEB_SEGREGACION);
        if (webSegregacion == null) {
            webSegregacion = new WebSegregacion();
            String idProc = "" + turno.getIdProceso();
            webSegregacion.setIdProceso(new Integer(idProc).intValue());
        }

        String sIdDocumento = (String) session.getAttribute(CFolio.ANOTACION_ID_DOCUMENTO);
        String sOrden = (String) session.getAttribute(CFolio.NEXT_ORDEN_ANOTACION);
        String sIdNatJur = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        String sNomNatJur = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change :
         */
        String sVersionNatJuridica = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);
        String sComentario = request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        String sValor = request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        String copiarComentarioSegregadas = request.getParameter(CAnotacion.GUARDAR_COMENTARIO_ANOTACION);
        String sNumeroRadicacion = request.getParameter(CSolicitudRegistro.NUMERO_RADICACION);
        String sFechaAnotacion = request.getParameter(CSolicitudRegistro.CALENDAR2);
        String sModalidad = request.getParameter(CFolio.ANOTACION_MODALIDAD);

        if (sValor != null) {
            sValor = sValor.replaceAll(",", "");
        }

        session.setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, sIdNatJur);
        session.setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, sNomNatJur);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change :
         */
        session.setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER, sVersionNatJuridica);
        session.setAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION, sComentario);
        session.setAttribute(CAnotacion.GUARDAR_COMENTARIO_ANOTACION, copiarComentarioSegregadas);
        session.setAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION, sValor);
        session.setAttribute(CFolio.ANOTACION_MODALIDAD, sModalidad);
        session.setAttribute(CSolicitudRegistro.NUMERO_RADICACION, sNumeroRadicacion);
        session.setAttribute(CSolicitudRegistro.CALENDAR2, sFechaAnotacion);

        //VALIDACIONES
        ValidacionSegregacionAnotacionException exception = new ValidacionSegregacionAnotacionException();

        //SOLICITU DEL TURNO
        Solicitud local_Solicitud;
        local_Solicitud = turno.getSolicitud();

        boolean local_ProcesoRegistroFlag = (local_Solicitud instanceof SolicitudRegistro);

        //NULOS
        if (local_ProcesoRegistroFlag && (sIdDocumento == null || sIdDocumento.equals(""))) {
            exception.addError("Documento inválido");
        }
        if (sIdNatJur == null || sIdNatJur.equals("")) {
            exception.addError("Naturaleza jurídica inválida");
        }
        if (sOrden == null || sOrden.equals("")) {
            exception.addError("Orden inválido");
        }

        if (local_Solicitud instanceof SolicitudCorreccion) {
            if (sNumeroRadicacion == null || sNumeroRadicacion.equals("")) {
                exception.addError("El número de radicación no debe estar vacio");
            }
            if (sFechaAnotacion == null || sFechaAnotacion.equals("")) {
                exception.addError("La fecha de la anotación no debe estar vacía");
            } else {
                try {
                    fechaAnotacion = DateFormatUtil.parse(sFechaAnotacion);
                } catch (ParseException e) {
                    exception.addError("La fecha de la anotación no es válida");
                }
            }
        }

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        if (ciudadanos == null || ciudadanos.size() == 0) {
            exception.addError("Ingrese por lo menos un ciudadano");
        }

        double valor = 0;
        try {
            if (sValor != null && sValor.length() > 0) {
                valor = Double.parseDouble(sValor);
            }
        } catch (NumberFormatException e) {
            exception.addError("El valor de la anotación no es un valor numérico");
        }

        if (sIdNatJur != null && (sIdNatJur.equals("0125") || sIdNatJur.equals("0126"))) {
            if (sModalidad == null || sModalidad.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe ingresar una modalidad para este código de naturaleza jurídica");
            }
        }

        //SE CREA EL DOCUMENTO CUANDO ES UNA SEGREGACIÓN EN CORRECCIONES
        Documento local_Documento = null;

        // datos de documento para la anotacion
        if (local_Solicitud instanceof SolicitudRegistro) {
            // nothing
        } else if (local_Solicitud instanceof SolicitudCorreccion) {
            local_Documento = crearDocumento(request, exception, true);
        }

        if (exception.getErrores().size() < 1) {
            //OBJETOS AUXILIARES
            Documento documento = new Documento();
            documento.setIdDocumento(sIdDocumento);
            NaturalezaJuridica natJur = new NaturalezaJuridica();
            natJur.setIdNaturalezaJuridica(sIdNatJur);
            natJur.setNombre(sNomNatJur);
            /**
             * @author : Carlos Mario Torres Urina
             * @casoMantis : 0012705.
             * @actaReq : Acta - Requerimiento No
             * 056_453_Modificiación_de_Naturaleza_Jurídica
             * @change :
             */
            natJur.setVersion(Long.parseLong(sVersionNatJuridica));

            //OBJETO ANOTACION
            Anotacion anotacion = new Anotacion();
            anotacion.setDocumento(documento);
            anotacion.setNaturalezaJuridica(natJur);
            anotacion.setComentario(sComentario);
            anotacion.setOrden(sOrden);
            anotacion.setValor(valor);
            anotacion.setModalidad(sModalidad);
            anotacion.setTemporal(true);

            //OBJETO WEBANOTACION 
            WebAnotacion webAnotacion = new WebAnotacion();
            webAnotacion.setIdNaturalezaJuridica(sIdNatJur);
            /**
             * @author : Carlos Mario Torres Urina
             * @casoMantis : 0012705.
             * @actaReq : Acta - Requerimiento No
             * 056_453_Modificiación_de_Naturaleza_Jurídica
             * @change :
             */
            webAnotacion.setVersion(Long.parseLong(sVersionNatJuridica));
            webAnotacion.setComentario(sComentario);
            webAnotacion.setValor("" + valor);
            webAnotacion.setModalidad(sModalidad);
            if (copiarComentarioSegregadas.equals(CAnotacion.SI_GUARDAR_COMENTARIO_ANOTACION)) {
                webAnotacion.setCopiaComentario(1);
            } else {
                webAnotacion.setCopiaComentario(0);
            }
            webAnotacion.setIdWebSegeng(webSegregacion.getIdWebSegeng());
            webAnotacion.setIdSolicitud(webSegregacion.getIdSolicitud());

            //Se coloca el documento a la solicitud
            if (turno != null && turno.getSolicitud() != null) {
                if (local_Solicitud instanceof SolicitudRegistro) {

                    SolicitudRegistro sol = (SolicitudRegistro) local_Solicitud;
                    anotacion.setDocumento(sol.getDocumento());

                    webAnotacion.setIdDocumento(sol.getDocumento().getIdDocumento());

                } else if (local_Solicitud instanceof SolicitudCorreccion) {
                    SolicitudCorreccion sol = (SolicitudCorreccion) local_Solicitud;
                    anotacion.setDocumento(local_Documento);

                    WebDocumento webDocumento = new WebDocumento();
                    webDocumento.setComentario(local_Documento.getComentario());
                    webDocumento.setFecha(local_Documento.getFecha());
                    if (local_Documento.getOficinaOrigen() != null) {
                        webDocumento.setIdOficinaOrigen(local_Documento.getOficinaOrigen().getIdOficinaOrigen());
                        /*
                                   *  @author Carlos Torres
                                   *  @chage   se agrega validacion de version diferente
                                   *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                         */
                        webDocumento.setVersion(local_Documento.getOficinaOrigen().getVersion());
                    }
                    //webDocumento.setIdSolicitud(local_Solicitud.getIdSolicitud());
                    //webDocumento.setIdWebSegeng(local_Solicitud.getIdSolicitud());
                    if (local_Documento.getTipoDocumento() != null) {
                        webDocumento.setIdTipoDocumento(local_Documento.getTipoDocumento().getIdTipoDocumento());
                    }
                    webDocumento.setNumero(local_Documento.getNumero());
                    webDocumento.setOficinaInternacional(local_Documento.getOficinaInternacional());

                    webDocumento.setIdSolicitud(webSegregacion.getIdSolicitud());
                    webDocumento.setIdWebSegeng(webSegregacion.getIdWebSegeng());
                    webAnotacion.setDocumento(webDocumento);
                    if (local_Documento.getIdDocumento() != null) {
                        webAnotacion.setIdDocumento(local_Documento.getIdDocumento());
                    }

                }
            }

            if (turno != null) {
                //TFS 3778: El número de radicación y la fecha de la anotación
                //para las correcciones, serán digitadas por el usuario
                if (local_Solicitud instanceof SolicitudCorreccion) {
                    webAnotacion.setNumeroRadicacion(sNumeroRadicacion);
                    webAnotacion.setFechaRadicacion(fechaAnotacion);
                    anotacion.setIdWorkflow(turno.getIdWorkflow());
                } else {
                    webAnotacion.setNumeroRadicacion(turno.getIdWorkflow());
                    webAnotacion.setFechaRadicacion(turno.getFechaInicio());
                    anotacion.setNumRadicacion(turno.getIdWorkflow());
                    anotacion.setIdWorkflow(turno.getIdWorkflow());
                    anotacion.setFechaRadicacion(turno.getFechaInicio());
                }

            }

            Iterator it = ciudadanos.iterator();
            while (it.hasNext()) {
                AnotacionCiudadano anot = (AnotacionCiudadano) it.next();
                anotacion.addAnotacionesCiudadano(anot);

                WebCiudadano webCiudadano = new WebCiudadano();
                webCiudadano.setTipoPersona(anot.getCiudadano().getTipoPersona());
                webCiudadano.setSexo(anot.getCiudadano().getSexo());
                webCiudadano.setApellido1(anot.getCiudadano().getApellido1());
                webCiudadano.setApellido2(anot.getCiudadano().getApellido2());
                webCiudadano.setNombre(anot.getCiudadano().getNombre());
                webCiudadano.setNumDocumento(anot.getCiudadano().getDocumento());
                webCiudadano.setTipoDocumento(anot.getCiudadano().getTipoDoc());
                webCiudadano.setTipoIntervencion(anot.getRolPersona());
                webCiudadano.setPorcentaje(anot.getParticipacion());
                webCiudadano.setPropietario("" + anot.getMarcaPropietario());
                webCiudadano.setIdSolicitud(webSegregacion.getIdSolicitud());
                webCiudadano.setIdWebSegeng(webSegregacion.getIdWebSegeng());
                webAnotacion.addCiudadano(webCiudadano);

            }

            webSegregacion.setAnotacion(webAnotacion);
            session.removeAttribute(CAnotacion.GUARDAR_COMENTARIO_ANOTACION);
        } else {
            throw exception;
        }

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        EvnSegregacion ev = new EvnSegregacion(usuarioAuriga, folio, turno, webSegregacion, EvnSegregacion.CONSULTA_FOLIO, usuarioNeg);

        return ev;
    }

    // sof:block ( from AWCrearFolio ) ----------------------------------------------------
    /**
     * Éste método recibe los valores ingresados en el formulario para ingresar
     * un documento, hace las validaciones necesarias cuando el documento sea
     * obligatorio, crea el objeto Documento con los valores ingresados y
     * retorna dicho objeto.
     *
     * @param request
     * @param exception
     * @param validarRequerido
     * @return
     * @see AWCrearFolio
     */
    private boolean validarNaturalezaJuridica(HttpServletRequest request) throws ValidacionParametrosSegregacion {
        ValidacionParametrosSegregacion exception = new ValidacionParametrosSegregacion();
        HttpSession session = request.getSession();
        boolean datosbien = false;
        //TODO
        return datosbien;
    }

    private Documento crearDocumento(HttpServletRequest request, ValidacionParametrosException exception, boolean validarRequerido) {

        Documento documento = null;
        Calendar fechaDocumento = null;
        HttpSession session = request.getSession();

        //SE CAPTURAN LOS DATOS DEL DOCUMENTO
        List tiposDoc = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
        String id_tipo_encabezado = request.getParameter(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
        String tipo_encabezado = request.getParameter(CSolicitudRegistro.TIPO_ENCABEZADO);
        String id_encabezado = request.getParameter(CSolicitudRegistro.ID_ENCABEZADO);
        String fecha = request.getParameter(CSolicitudRegistro.CALENDAR);

        //SE CAPTURAN LOS DATOS DE LA OFICINA DÓNDE SE CREO EL DOCUMENTO.
        String idTipoOficina = request.getParameter(WebKeys.TIPO_OFICINA_I_N);
        idTipoOficina = (idTipoOficina != null) ? idTipoOficina : "";

        String nomDepto = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        String idDepto = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);

        String nomMunic = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        String idMunic = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);

        String nomVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
        String idVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);

        if ((idVereda == null) || nomDepto.equals("") || nomMunic.equals("")) {
            List listaVeredas = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS);
            idVereda = ValidacionDatosOficinaOrigen.obtenerVereda(listaVeredas, idDepto, idMunic);
        }

        String tipo_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        String numero_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);
        String id_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        /*
           *  @author Carlos Torres
           *  @chage   se agrega validacion de version diferente
           *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        String version_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
        String oficinaInternacional = request.getParameter(WebKeys.TIPO_OFICINA_INTERNACIONAL);

        //SE REALIZAN LAS VALIDACIONES NECESARIAS
        if (validarRequerido) {

            //VALIDACIONES DEL ENCABEZADO DEL DOCUMENTO
            if ((id_tipo_encabezado != null) && !id_tipo_encabezado.equals("")) {
                tipo_encabezado = existeId(tiposDoc, id_tipo_encabezado);

                if (tipo_encabezado.equals("")) {
                    exception.addError("El tipo de documento digitado no correponde al tipo de documento seleccionado");
                }
            }

            if (tipo_encabezado.equals("SIN_SELECCIONAR") && id_tipo_encabezado.equals("")) {
                exception.addError("El campo tipo del encabezado es inválido");
            }

            if (id_encabezado.trim().equals("") && !id_tipo_encabezado.equals(CTipoDocumento.ID_TIPO_SENTENCIA) && !id_tipo_encabezado.equals(CTipoDocumento.ID_TIPO_AUTO) && !id_tipo_encabezado.equals(CTipoDocumento.ID_TIPO_REMATE)) {
                exception.addError("El campo número del encabezado del documento no puede estar vacio");
            }

            if ((fecha == null) || fecha.trim().equals("")) {
                exception.addError("La fecha del encabezado es inválida");
            } else {
                if (fecha != null) {
                    fechaDocumento = darFechaAsCalendar(fecha);
                    if (fechaDocumento.getTimeInMillis() > (new Date()).getTime()) {
                        exception.addError("La fecha del documento no puede ser mayor al día de hoy");
                    }
                } else {
                    fechaDocumento = null;
                }

                if (fechaDocumento == null) {
                    exception.addError("La fecha del documento es invalida");
                } else if (fechaDocumento.get(Calendar.YEAR) < AWLiquidacionRegistro.ANIO_MINIMO) {
                    exception.addError("La fecha del encabezado del documento no puede ser inferior a la fecha 01/01/" + AWLiquidacionRegistro.ANIO_MINIMO + ".");
                }

            }

            //VALIDACIONES DE LA OFICINA DÓNDE SE CREO EL DOCUMENTO
            if (idTipoOficina.equalsIgnoreCase(WebKeys.TIPO_OFICINA_NACIONAL)) {

                if ((idVereda == null) || idVereda.trim().equals("")) {
                    exception.addError("La vereda o ciudad es inválida");
                }

                if ((idDepto == null) || idDepto.trim().equals("")) {
                    exception.addError("El departamento es inválido");
                }

                if ((idMunic == null) || idMunic.trim().equals("")) {
                    exception.addError("El municipio es inválido");
                }

                if ((id_oficina == null) || id_oficina.trim().equals("")) {
                    exception.addError("El tipo de oficina es inválido");
                }

            } else {
                if ((oficinaInternacional == null) || (oficinaInternacional.length() == 0)) {
                    exception.addError("El campo Oficina Ubicación Internacional no puede estar en blanco");
                }
            }

        }

        //SE CREA EL DOCUMENTO A PARTIR DE LA INFORMACIÓN INGRESADA
        if (tipo_encabezado != null && ((oficinaInternacional != null && oficinaInternacional.length() > 0) || (id_oficina != null && id_oficina.length() > 0))) {
            documento = new Documento();

            //DATOS DEL ENCABEZADO DEL DOCUMENTO
            TipoDocumento tipoDoc = new TipoDocumento();
            tipoDoc.setIdTipoDocumento(tipo_encabezado);

            List tiposDocs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
            Iterator itTiposDocs = tiposDocs.iterator();

            while (itTiposDocs.hasNext()) {
                ElementoLista elemento = (ElementoLista) itTiposDocs.next();

                if (elemento.getId().equals(tipo_encabezado)) {
                    tipoDoc.setNombre(elemento.getValor());
                }
            }

            documento.setTipoDocumento(tipoDoc);
            documento.setNumero(id_encabezado);
            documento.setFecha(fechaDocumento != null ? fechaDocumento.getTime() : null);

            //DATOS DE LA OFICINA DÓNDE SE CREO EL DOCUMENTO
            if (idTipoOficina.equalsIgnoreCase(WebKeys.TIPO_OFICINA_NACIONAL)) {
                OficinaOrigen oficinaOrigen = new OficinaOrigen();

                if (tipo_oficina != null) {
                    oficinaOrigen.setNombre(tipo_oficina);
                }

                if (numero_oficina != null) {
                    oficinaOrigen.setNumero(numero_oficina);
                }

                oficinaOrigen.setIdOficinaOrigen(id_oficina);
                /*
                           *  @author Carlos Torres
                           *  @chage   se agrega validacion de version diferente
                           *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                oficinaOrigen.setVersion((version_oficina != null && !version_oficina.equals("")) ? Long.parseLong(version_oficina) : null);

                Vereda vereda = new Vereda();

                if (nomVereda != null) {
                    vereda.setNombre(nomVereda);
                }

                vereda.setIdVereda(idVereda);
                vereda.setIdDepartamento(idDepto);
                vereda.setIdMunicipio(idMunic);

                oficinaOrigen.setVereda(vereda);

                if (id_oficina != null && id_oficina.length() > 0) {
                    documento.setOficinaOrigen(oficinaOrigen);
                }

            } else {
                documento.setOficinaInternacional(oficinaInternacional);
            }

        }

        //COLOCAR LAS VARIABLES EN LA SESIÓN NUEVAMENTE
        preservarInfoBasicaDocumento(request);
        return documento;
    }

    /**
     * Verificar si exite el id_tipo_encabezado en la Lista de tiposDoc
     *
     * @param tiposDoc Lista con los datos
     * @param id_tipo_encabezado cadena que se busca que exista en la lista
     * @return true si existe id_tipo_encabeado en la lista, false si no
     */
    private String existeId(List tiposDoc, String id_tipo_encabezado) {
        for (Iterator it = tiposDoc.iterator(); it.hasNext();) {
            ElementoLista e = (ElementoLista) it.next();
            int id = Integer.parseInt(e.getId());

            if (id == Integer.parseInt(id_tipo_encabezado)) {
                return e.getId();
            }
        }

        return "";
    }

    /**
     * @param request
     */
    private void preservarInfoBasicaDocumento(HttpServletRequest request) {
        if (request.getParameter(CSolicitudRegistro.ID_TIPO_ENCABEZADO) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.ID_TIPO_ENCABEZADO, request.getParameter(CSolicitudRegistro.ID_TIPO_ENCABEZADO));
        }
        if (request.getParameter(CSolicitudRegistro.TIPO_ENCABEZADO) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.TIPO_ENCABEZADO, request.getParameter(CSolicitudRegistro.TIPO_ENCABEZADO));
        }
        if (request.getParameter(CSolicitudRegistro.ID_ENCABEZADO) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.ID_ENCABEZADO, request.getParameter(CSolicitudRegistro.ID_ENCABEZADO));
        }
        if (request.getParameter(CSolicitudRegistro.CALENDAR) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.CALENDAR, request.getParameter(CSolicitudRegistro.CALENDAR));
        }

        if (request.getParameter(WebKeys.TIPO_OFICINA_I_N) != null) {
            request.getSession().setAttribute(WebKeys.TIPO_OFICINA_I_N, request.getParameter(WebKeys.TIPO_OFICINA_I_N));
        }
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO));
        }
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO));
        }
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC));
        }
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC));
        }
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA));
        }
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA));
        }

        if (request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO, request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO));
        }
        if (request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM, request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM));
        }
        if (request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA, request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA));
        }
        if (request.getParameter(WebKeys.TIPO_OFICINA_INTERNACIONAL) != null) {
            request.getSession().setAttribute(WebKeys.TIPO_OFICINA_INTERNACIONAL, request.getParameter(WebKeys.TIPO_OFICINA_INTERNACIONAL));
        }
        /*
               *  @author Carlos Torres
               *  @chage   se agrega validacion de version diferente
               *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        if (request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION, request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION));
        }

    }

    /**
     * @param fechaInterfaz
     * @return
     */
    private static Calendar darFechaAsCalendar(String fechaInterfaz) {
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

        if (partido.length == 3) {
            int dia = Integer.parseInt(partido[0]);
            int mes = Integer.parseInt(partido[1]) - 1;
            int año = Integer.parseInt(partido[2]);
            calendar.set(año, mes, dia);

            if ((calendar.get(Calendar.YEAR) == año) && (calendar.get(Calendar.MONTH) == mes) && (calendar.get(Calendar.DAY_OF_MONTH) == dia)) {
                return calendar;
            }
        }

        return null;
    }
    // eof:block -------------------------------------------------------------

    /**
     * @param request
     * @return
     */
    private Evento crearAnotacionVacia(HttpServletRequest request) throws ValidacionParametrosException {

        WebSegregacion webSegregacion = (WebSegregacion) request.getSession().getAttribute(WebKeys.WEB_SEGREGACION);
        if (webSegregacion == null) {
            webSegregacion = new WebSegregacion();
        }

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        return new EvnSegregacion(usuarioAuriga, folio, turno, webSegregacion, EvnSegregacion.CONSULTA_ANOTACIONES_TEMPORALES, usuarioNeg);
    }

    /**
     * @param request
     * @return
     */
    private Evento escogerAnotacion(HttpServletRequest request) throws ValidacionParametrosException {

        WebSegregacion webSegregacion = (WebSegregacion) request.getSession().getAttribute(WebKeys.WEB_SEGREGACION);
        if (webSegregacion == null) {
            webSegregacion = new WebSegregacion();
        }

        Anotacion anotacionEscogida = new Anotacion();
        Anotacion anotacion = null;
        String[] anota = request.getParameterValues("Anotaciones");
        List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO);
        Iterator it = anotaciones.iterator();
        ValidacionSegregacionEscogerAnotacionException ex = new ValidacionSegregacionEscogerAnotacionException();

        int seleccionadas = 0;
        if (anota != null) {
            while (it.hasNext()) {
                anotacion = (Anotacion) it.next();
                for (int i = 0; i < anota.length; i++) {
                    String an = anota[i];
                    if (anotacion.getOrden().equals(an)) {
                        if (seleccionadas > 0) {
                            if (!anotacion.getOrden().equals(anotacionEscogida.getOrden())) {
                                seleccionadas += 1;
                            }
                        } else {
                            seleccionadas += 1;
                        }

                        anotacionEscogida = anotacion;
                    }
                }
            }
        }

        if (seleccionadas == 0) {
            ex.addError("Debe Seleccionar una anotación temporal.");
            throw ex;
        }

        if (seleccionadas > 1) {
            ex.addError("Seleccione solamente una anotación temporal.");
            throw ex;
        }

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        if (anotacionEscogida != null) {
            if (turno != null && turno.getSolicitud() != null) {
                SolicitudRegistro sol = (SolicitudRegistro) turno.getSolicitud();
                anotacionEscogida.setDocumento(sol.getDocumento());
            }
        }

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        return new EvnSegregacion(usuarioAuriga, folio, turno, webSegregacion, EvnSegregacion.CONSULTA_FOLIO, usuarioNeg);
    }

    /**
     * @param request
     * @return
     */
    private Evento heredarAnotaciones(HttpServletRequest request) {
        WebSegregacion webSegregacion = (WebSegregacion) request.getSession().getAttribute(WebKeys.WEB_SEGREGACION);
        if (webSegregacion == null) {
            webSegregacion = new WebSegregacion();
        }
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);

        String copiarComentarioHeredadas = request.getParameter(CAnotacion.GUARDAR_COMENTARIO_ANOTACION);
        String[] anota = null;

        while (webSegregacion.getFoliosHeredados().size() > 0) {

            List foliosHeredados = webSegregacion.getFoliosHeredados();

            for (int i = 0; i < foliosHeredados.size(); i++) {
                WebFolioHeredado foliosHeredado = (WebFolioHeredado) foliosHeredados.get(0);
                webSegregacion.removeFoliosHeredado(foliosHeredado);
            }

        }

        WebFolioHeredado webFolio = new WebFolioHeredado();
        webFolio.setIdMatricula(folio.getIdMatricula());
        webFolio.setFechaCreacion(new Date());
        webFolio.setIdWebSegeng(webSegregacion.getIdWebSegeng());
        webFolio.setIdSolicitud(webSegregacion.getIdSolicitud());

        if (copiarComentarioHeredadas.equals(CAnotacion.SI_GUARDAR_COMENTARIO_ANOTACION)) {
            webFolio.setCopiaComentario(true);
        } else {
            webFolio.setCopiaComentario(false);
        }

        final String LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID = "TARGET_SELECTIONCOLLECTOR";

        String idsAnotacionesCsv = request.getParameter(LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID);

        // only the last is added
        anota = gov.sir.core.web.helpers.comun.JvLocalUtils.csvStringToStringArray(idsAnotacionesCsv, gov.sir.core.web.helpers.comun.JvLocalUtils.DEFAULT_SEPARATOR, true);

        request.getSession().setAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION, anota);

        String nombreResultado = (String) request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
        DatosRespuestaPaginador RPag = (DatosRespuestaPaginador) request.getSession().getAttribute(nombreResultado);
        //List anotaciones = RPag.getAnotacionesActual();
        List anotaciones = (List) request.getSession().getAttribute(WebKeys.ANOTACIONES_A_HEREDAR);
        if (anotaciones == null) {
            anotaciones = RPag.getAnotacionesActual();
        }

        if (anota != null) {
            for (int i = 0; i < anota.length; i++) {
                String an = anota[i];
                if (!an.equals("")) {
                    WebAnotaHereda webAnotacionHereda = new WebAnotaHereda();
                    webAnotacionHereda.setIdWebSegeng(webSegregacion.getIdWebSegeng());
                    webAnotacionHereda.setIdSolicitud(webSegregacion.getIdSolicitud());

                    //con el id de la anotación consulto el orden
                    /*AHERRENO 28/03/2012
                                                          REQ 042_151 SE ENVIA EL CAMPO ID_ANOTACION PARA QUE ESTE SE ALMACENE EN LA TABLA
                                                          SIR_OP_WEB_ANOTA_HEREDA, TABLA QUE ES LEIDA EN EL PROCESO SP_SG*/
                    if (anotaciones != null && anotaciones.size() > 0) {
                        for (int j = 0; j < anotaciones.size(); j++) {
                            if (an.equals(((Anotacion) anotaciones.get(j)).getIdAnotacion())) {
                                an = ((Anotacion) anotaciones.get(j)).getIdAnotacion();
                                break;
                            }
                        }
                    }
                    webAnotacionHereda.setIdAnotacion(an);
                    webAnotacionHereda.setFechaCreacion(new Date());
                    webFolio.addAnotacionesHeredada(webAnotacionHereda);
                }
            }
        }
        webSegregacion.addFoliosHeredado(webFolio);

        /**
         * @author: Guillermo Cabrera.
         * @change: Se remueve de sesión las anotaciones que se cargaron
         * inicialmente. MANTIS: 1726
         *
         */
        request.getSession().removeAttribute(WebKeys.ANOTACIONES_A_HEREDAR);

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        return new EvnSegregacion(usuarioAuriga, folio, turno, webSegregacion, EvnSegregacion.GUARDAR_WEB_SEGREGACION, usuarioNeg);
    }

    /**
     * @param request
     * @return
     * @throws ValidacionParametrosSegregacion
     */
    private Evento agregarFolioDerivado(HttpServletRequest request) throws ValidacionParametrosSegregacion {

        ValidacionParametrosSegregacion exception = new ValidacionParametrosSegregacion();
        HttpSession session = request.getSession();

        WebSegregacion webSegregacion = (WebSegregacion) request.getSession().getAttribute(WebKeys.WEB_SEGREGACION);
        if (webSegregacion == null) {
            webSegregacion = new WebSegregacion();
        }

        List foliosDerivados = webSegregacion.getFoliosDerivados();
        if (foliosDerivados == null) {
            foliosDerivados = new Vector();
        }

        Iterator itFoliosD = foliosDerivados.iterator();

        /**
         * @author: Henry Gómez Rocha
         * @change: Modificacion del tipo de dato a las variables
         * porcentajeTotal y porcentaje de tipo double a tipo BigDecimal, para
         * controlar inexactitud ocacionada por usar tipo double al almacenar la
         * división de los lotes al momento de segregar. * MANTIS: 0003877
         *
         */
        BigDecimal porcentajeTotal = new BigDecimal("0.0");
        while (itFoliosD.hasNext()) {
            try {
                BigDecimal numPorFolio = new BigDecimal("0.0");
                try {
                    numPorFolio = new BigDecimal(((WebFolioDerivado) itFoliosD.next()).getPorcentaje().replaceAll("%", "").trim());
                } catch (Exception ee) {
                    numPorFolio = new BigDecimal("0.0");
                }
                porcentajeTotal = porcentajeTotal.add(numPorFolio);
            } catch (NumberFormatException e) {
                porcentajeTotal = porcentajeTotal.add(new BigDecimal("0.0"));
            }
        }

        String lote = request.getParameter(CFolioDerivado.NOMBRE_LOTE);
        String hectareas = request.getParameter(CFolioDerivado.HECTAREAS);
        String metros = request.getParameter(CFolioDerivado.METROS);
        String centimetros = request.getParameter(CFolioDerivado.CENTIMETROS);
        String porcen = request.getParameter(CFolioDerivado.PORCENTAJE);
        String privMetros = request.getParameter(CFolio.PRIVMETROS);
        String privCentimetros = request.getParameter(CFolio.PRIVCENTIMETROS);
        String consMetros = request.getParameter(CFolio.CONSMETROS);
        String consCentimetros = request.getParameter(CFolio.CONSCENTIMETROS);
        String determinaInm = request.getParameter(CFolio.DETERMINACION_INMUEBLE);
        //String unidad = request.getParameter(CFolioDerivado.UNIDAD_MEDIDA);

        int unidadmedidaexception = 3;

        if (hectareas == null || hectareas.length() <= 0) {
            unidadmedidaexception--;
            hectareas = "0";
        }

        if (metros == null || metros.length() <= 0) {
            unidadmedidaexception--;
            metros = "0";
        }

        if (centimetros == null || centimetros.length() <= 0) {
            unidadmedidaexception--;
            centimetros = "0";
        }

        if (unidadmedidaexception == 0) {

        }

        String natJuridica = (String) request.getSession().getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        Folio folioAreas = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        String hectareasM = (folioAreas.getHectareas() != null ? folioAreas.getHectareas() : "0");
        String metrosM = (folioAreas.getMetros() != null ? folioAreas.getMetros() : "0");
        String centimetrosM = (folioAreas.getCentimetros() != null ? folioAreas.getCentimetros() : "0");
        boolean datosMenor = true;
        boolean datosIgual = false;
        boolean datosMenorIgual = true;

        if (hectareasM.isEmpty()) {
            hectareasM = "0";
        }
        if (metrosM.isEmpty()) {
            metrosM = "0";
        }
        if (centimetrosM.isEmpty()) {
            centimetrosM = "0";
        }

        //Area Matriz para Validar
        int mHectareas = Integer.parseInt(hectareasM);
        int mMetros = Integer.parseInt(metrosM);
        int mCentimetros = Integer.parseInt(centimetrosM);

        if (hectareas == null || hectareas.isEmpty()) {
            hectareas = "0";
        }
        if (metros == null || metros.isEmpty()) {
            metros = "0";
        }
        if (centimetros == null || centimetros.isEmpty()) {
            centimetros = "0";
        }
        //Area Segregada para Validar
        int sHectareas = Integer.parseInt(hectareas);
        int sMetros = Integer.parseInt(metros);
        int sCentimetros = Integer.parseInt(centimetros);

        if (natJuridica.equals("01004")
                || natJuridica.equals("0110")
                || natJuridica.equals("0913")
                || natJuridica.equals("0918")
                || natJuridica.equals("0919")
                || natJuridica.equals("0920")
                || natJuridica.equals("0924")
                || natJuridica.equals("0103")
                || natJuridica.equals("0104")
                || natJuridica.equals("0105")
                || natJuridica.equals("0111")
                || natJuridica.equals("0116")
                || natJuridica.equals("0117")
                || natJuridica.equals("0121")
                || natJuridica.equals("0122")
                || natJuridica.equals("0124")
                || natJuridica.equals("0126")
                || natJuridica.equals("0131")
                || natJuridica.equals("0140")
                || natJuridica.equals("0141")
                || natJuridica.equals("0166")
                || natJuridica.equals("0171")
                || natJuridica.equals("0173")
                || natJuridica.equals("0185")
                || natJuridica.equals("0192")
                || natJuridica.equals("0915")
                || natJuridica.equals("01012")) {

            if (sHectareas > mHectareas) {
                datosMenorIgual = false;
            } else {
                if (sHectareas == mHectareas) {
                    if (sMetros > mMetros) {
                        datosMenorIgual = false;
                    } else {
                        if (sMetros == mMetros) {
                            if (sCentimetros > mCentimetros) {
                                datosMenorIgual = false;
                            }
                        }
                    }
                }
            }

            if (!datosMenorIgual) {
                if (!(mHectareas == 0 && mMetros == 0 && mCentimetros == 0)) {
                    exception.addError("El area segregada debe ser menor o igual al area matriz");
                }
            }
        }

        int TotalHectareas = 0;
        int TotalMetros = 0;
        int TotalCentimetros = 0;
        boolean areaMenorError = true;
        boolean areaIgualError = true;
        boolean areaMenorIgualError = true;

        if (!(mHectareas == 0 && mMetros == 0 && mCentimetros == 0)) {
            Iterator itFoliosDe = foliosDerivados.iterator();
            while (itFoliosDe.hasNext()) {
                try {
                    int hectareasPorFolio = 0;
                    int metrosPorFolio = 0;
                    int centimetrosPorFolio = 0;
                    String auxh, auxm, auxc;
                    try {
                        WebFolioDerivado dev = (WebFolioDerivado) itFoliosDe.next();
                        auxh = dev.getHectareas();
                        auxm = dev.getMetros();
                        auxc = dev.getCentimetros();
                        hectareasPorFolio = Integer.parseInt((auxh != null && !auxh.isEmpty() ? auxh : "0"));
                        metrosPorFolio = Integer.parseInt((auxm != null && !auxm.isEmpty() ? auxm : "0"));
                        centimetrosPorFolio = Integer.parseInt((auxc != null && !auxc.isEmpty() ? auxc : "0"));
                    } catch (Exception ee) {
                        hectareasPorFolio = 0;
                        metrosPorFolio = 0;
                        centimetrosPorFolio = 0;
                    }
                    TotalHectareas += hectareasPorFolio;
                    TotalMetros += metrosPorFolio;
                    TotalCentimetros += centimetrosPorFolio;
                } catch (NumberFormatException e) {
                    TotalHectareas = 0;
                    TotalMetros = 0;
                    TotalCentimetros = 0;
                }
            }

            TotalHectareas += sHectareas;
            TotalMetros += sMetros;
            TotalCentimetros += sCentimetros;

            if (TotalMetros >= 10000) {
                TotalHectareas += 1;
                TotalMetros -= 10000;
            }

            if (TotalCentimetros >= 10000) {
                TotalMetros += 1;
                TotalCentimetros -= 10000;
            }
        }

        if (natJuridica.equals("0110")
                || natJuridica.equals("0913")
                || natJuridica.equals("0918")
                || natJuridica.equals("0919")
                || natJuridica.equals("0924")) {

            if (TotalHectareas > mHectareas) {
                areaIgualError = false;
            } else {
                if (TotalHectareas == mHectareas) {
                    if (TotalMetros > mMetros) {
                        areaIgualError = false;
                    } else {
                        if (TotalMetros == mMetros) {
                            if (TotalCentimetros > mCentimetros) {
                                areaIgualError = false;
                            }
                        }
                    }
                }
            }

            if (!areaIgualError) {
                if (!(mHectareas == 0 && mMetros == 0 && mCentimetros == 0)) {
                    exception.addError("El area total segregada debe ser igual al area matriz");
                }
            }
        }

        if (natJuridica.equals("01004")
                || natJuridica.equals("0920")
                || natJuridica.equals("0103")
                || natJuridica.equals("0104")
                || natJuridica.equals("0105")
                || natJuridica.equals("0111")
                || natJuridica.equals("0116")
                || natJuridica.equals("0117")
                || natJuridica.equals("0121")
                || natJuridica.equals("0122")
                || natJuridica.equals("0124")
                || natJuridica.equals("0126")
                || natJuridica.equals("0131")
                || natJuridica.equals("0140")
                || natJuridica.equals("0141")
                || natJuridica.equals("0166")
                || natJuridica.equals("0171")
                || natJuridica.equals("0173")
                || natJuridica.equals("0185")
                || natJuridica.equals("0192")
                || natJuridica.equals("0915")
                || natJuridica.equals("01012")) {

            if (TotalHectareas > mHectareas) {
                areaMenorIgualError = false;
            } else {
                if (TotalHectareas == mHectareas) {
                    if (TotalMetros > mMetros) {
                        areaMenorIgualError = false;
                    } else {
                        if (TotalMetros == mMetros) {
                            if (TotalCentimetros > mCentimetros) {
                                areaMenorIgualError = false;
                            }
                        }
                    }
                }
            }

            if (!areaMenorIgualError) {
                if (!(mHectareas == 0 && mMetros == 0 && mCentimetros == 0)) {
                    exception.addError("El area total segregada debe ser menor o igual al area matriz");
                }
            }
        }

        if (lote == null || lote.length() <= 0) {
            exception.addError("Debe ingresar el nombre del inmueble para el folio derivado");
        }

        porcen = (porcen.replaceAll("%", "")).trim();
        BigDecimal porcentaje = new BigDecimal("0.0");

        if (porcen != null) {
            if (porcen.length() > 0 || !porcen.equals("")) {
                try {
                    porcentaje = new BigDecimal(porcen);
                    if (porcentaje.doubleValue() <= 0) {
                        exception.addError("El coeficiente no puede ser negativo o cero");
                    }
                } catch (NumberFormatException e) {
                    exception.addError("El coeficiente es inválido");
                }
                porcen += "%";
            }
        }

        /**
         * @author: Henry Gómez Rocha
         * @change: Almacenando en la variable porcentajeTotal la totalidad de
         * los lotes y verificando que dicha totalidad no sea superio a 100. *
         * MANTIS: 0003877
         *
         */
        porcentajeTotal = porcentajeTotal.add(porcentaje);
        if (porcentajeTotal.doubleValue() > 100) {
            exception.addError("El coeficiente no puede superar 100%");
        }

        if (privMetros == null || privMetros.isEmpty()) {
            privMetros = "0";
        }

        if (privCentimetros == null || privCentimetros.isEmpty()) {
            privCentimetros = "0";
        }

        if (consMetros == null || consMetros.isEmpty()) {
            consMetros = "0";
        }

        if (consCentimetros == null || consCentimetros.isEmpty()) {
            consCentimetros = "0";
        }

        int pMetros = Integer.parseInt(privMetros);
        int pCentimetros = Integer.parseInt(privCentimetros);
        int cMetros = Integer.parseInt(consMetros);
        int cCentimetros = Integer.parseInt(consCentimetros);
        boolean datosPrivBien = true;
        boolean datosConsBien = true;

        if (!natJuridica.equals("0911")) {
            if (pMetros != 0 || pCentimetros != 0) {

                if (cMetros == 0 && cCentimetros == 0) {

                } else {
                    if (pMetros > cMetros) {
                        datosPrivBien = false;
                    } else {
                        if (pMetros == cMetros) {
                            if (pCentimetros > cCentimetros) {
                                datosPrivBien = false;
                            }
                        }
                    }

                    if (!datosPrivBien) {
//                    exception.addError("El area privada no puede ser mayor al area construida");
                    }

                    if (sHectareas <= 0) {
                        if (cMetros > sMetros) {
                            datosConsBien = false;
                        } else {
                            if (cMetros == sMetros) {
                                if (cCentimetros > sCentimetros) {
                                    datosConsBien = false;
                                }
                            }
                        }
                    }

                    if (!datosConsBien) {
                        //exception.addError("El area construida no puede ser mayor al area del terreno");
                    }
                }
            } else {
                if (cMetros != 0 || cCentimetros != 0) {

                    if (sHectareas <= 0) {
                        if (cMetros > sMetros) {
                            datosConsBien = false;
                        } else {
                            if (cMetros == sMetros) {
                                if (cCentimetros > sCentimetros) {
                                    datosConsBien = false;
                                }
                            }
                        }
                    }

                    if (!datosConsBien) {
//                    exception.addError("El area construida no puede ser mayor al area del terreno");
                    }
                }
            }
        }

        if (privMetros.equals("0") && privCentimetros.equals("0")) {
            privMetros = null;
            privCentimetros = null;
        }

        if (consMetros.equals("0") && consCentimetros.equals("0")) {
            consMetros = null;
            consCentimetros = null;
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        WebFolioDerivado webFolioDerivado = new WebFolioDerivado();
        webFolioDerivado.setHectareas(hectareas);
        webFolioDerivado.setMetros(metros);
        webFolioDerivado.setCentimetros(centimetros);
        webFolioDerivado.setInmueble(lote);
        webFolioDerivado.setPorcentaje(porcen.toUpperCase());
        if (privMetros != null) {
            webFolioDerivado.setPrivMetros(privMetros);
        }
        if (privCentimetros != null) {
            webFolioDerivado.setPrivCentimetros(privCentimetros);
        }
        if (consMetros != null) {
            webFolioDerivado.setConsMetros(consMetros);
        }
        if (consCentimetros != null) {
            webFolioDerivado.setConsCentimetros(consCentimetros);
        }
        if (determinaInm != null && !determinaInm.equals(WebKeys.SIN_SELECCIONAR)) {
            webFolioDerivado.setDeterminaInm(determinaInm);
        }
        
        session.setAttribute(CFolioDerivado.HECTAREAS,(hectareas!=null?hectareas:"0"));
        session.setAttribute(CFolioDerivado.METROS,(metros!=null?metros:"0"));
        session.setAttribute(CFolioDerivado.CENTIMETROS,(centimetros!=null?centimetros:"0"));
        session.setAttribute(CFolioDerivado.NOMBRE_LOTE,(lote!=null?lote:"0"));
        session.setAttribute(CFolioDerivado.PORCENTAJE,(porcen!=null?porcen.toUpperCase():"0"));
        session.setAttribute(CFolio.PRIVMETROS,(privMetros!=null?privMetros:"0"));
        session.setAttribute(CFolio.PRIVCENTIMETROS,(privCentimetros!=null?privCentimetros:"0"));
        session.setAttribute(CFolio.CONSMETROS,(consMetros!=null?consMetros:"0"));
        session.setAttribute(CFolio.CONSCENTIMETROS,(consCentimetros!=null?consCentimetros:"0"));
        session.setAttribute(CFolio.DETERMINACION_INMUEBLE,(determinaInm!=null?determinaInm:"0"));
        webFolioDerivado.setFechaCreacion(new Date());

        if (!existeFolioDerivado(foliosDerivados, webFolioDerivado)) {
            webSegregacion.addFoliosDerivado(webFolioDerivado);
            request.getSession().setAttribute(WebKeys.WEB_SEGREGACION, webSegregacion);
        } else {
            exception.addError("El folio derivado que intenta agregar, ya existe");
            throw exception;
        }

        return null;
    }

    /**
     * Método que determina si un folio derivado ya fue agregado a la lista de
     * foliosderivados o no.
     *
     * @param foliosDerivados
     * @param folioDerivado
     * @return boolean
     * @throws ValidacionParametrosSegregacion
     */
    private boolean existeFolioDerivado(List foliosDerivados, WebFolioDerivado folioDerivado) throws ValidacionParametrosSegregacion {

        boolean existefolio = false;

        if (foliosDerivados != null && foliosDerivados.size() > 0) {
            Iterator it = foliosDerivados.iterator();
            while (it.hasNext()) {
                WebFolioDerivado folioTemp = (WebFolioDerivado) it.next();
                if (folioTemp.getInmueble() != null && folioDerivado.getInmueble() != null && folioTemp.getInmueble().toUpperCase().equals(folioDerivado.getInmueble().toUpperCase())) {
                    return true;
                }
            }

        }

        return existefolio;
    }

    /**
     * Método que determina si un folio derivado puede actualizarse o no.
     *
     * @param foliosDerivados
     * @param folioDerivado
     * @return boolean
     * @throws ValidacionParametrosSegregacion
     */
    private boolean puedeActualizarse(List foliosDerivados, WebFolioDerivado folioDerivado, int posicion) throws ValidacionParametrosSegregacion {

        boolean existefolio = false;

        if (foliosDerivados != null && foliosDerivados.size() > 0) {
            Iterator it = foliosDerivados.iterator();
            int secuencia = 0;
            while (it.hasNext()) {
                WebFolioDerivado folioTemp = (WebFolioDerivado) it.next();
                if (posicion != secuencia) {
                    if (folioTemp.getInmueble() != null && folioDerivado.getInmueble() != null && folioTemp.getInmueble().toUpperCase().equals(folioDerivado.getInmueble().toUpperCase())) {
                        return true;
                    }
                }
                secuencia++;
            }

        }

        return existefolio;
    }

    /**
     * @param request
     * @return
     */
    private Evento eliminarFolioDerivado(HttpServletRequest request) throws ValidacionParametrosSegregacion {
        HttpSession session = request.getSession();
        ValidacionParametrosSegregacion exception = new ValidacionParametrosSegregacion();

        WebSegregacion webSegregacion = (WebSegregacion) request.getSession().getAttribute(WebKeys.WEB_SEGREGACION);
        if (webSegregacion == null) {
            webSegregacion = new WebSegregacion();
        }
        List foliosD = webSegregacion.getFoliosDerivados();

        if (foliosD == null || foliosD.isEmpty()) {
            exception.addError("Debe incluir por lo menos la informacion de un folio derivado");
            throw exception;
        }
        String i = request.getParameter(WebKeys.POSICION);
        int pos = Integer.valueOf(i).intValue();
        WebFolioDerivado webFolioDerivado = (WebFolioDerivado) foliosD.get(pos);
        webSegregacion.removeFoliosDerivado(webFolioDerivado);

        List foliosDerivados = webSegregacion.getFoliosDerivados();

        Folio folioM = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        String hectareasM = "0";
        String metrosM = "0";
        String centimetrosM = "0";

        if (folioM.getHectareas() != null && !folioM.getHectareas().isEmpty()) {
            hectareasM = folioM.getHectareas();
        }

        if (folioM.getMetros() != null && !folioM.getMetros().isEmpty()) {
            metrosM = folioM.getMetros();
        }

        if (folioM.getCentimetros() != null && !folioM.getCentimetros().isEmpty()) {
            centimetrosM = folioM.getCentimetros();
        }

        int mHectareas = Integer.parseInt(hectareasM);
        int mMetros = Integer.parseInt(metrosM);
        int mCentimetros = Integer.parseInt(centimetrosM);

        int TotalHectareas = 0;
        int TotalMetros = 0;
        int TotalCentimetros = 0;

        Iterator itFoliosDe = foliosD.iterator();
        while (itFoliosDe.hasNext()) {
            try {
                int hectareasPorFolio = 0;
                int metrosPorFolio = 0;
                int centimetrosPorFolio = 0;
                String auxh, auxm, auxc;
                try {
                    WebFolioDerivado dev = (WebFolioDerivado) itFoliosDe.next();
                    auxh = dev.getHectareas();
                    auxm = dev.getMetros();
                    auxc = dev.getCentimetros();
                    hectareasPorFolio = Integer.parseInt((auxh != null && !auxh.isEmpty() ? auxh : "0"));
                    metrosPorFolio = Integer.parseInt((auxm != null && !auxm.isEmpty() ? auxm : "0"));
                    centimetrosPorFolio = Integer.parseInt((auxc != null && !auxc.isEmpty() ? auxc : "0"));
                } catch (Exception ee) {
                    hectareasPorFolio = 0;
                    metrosPorFolio = 0;
                    centimetrosPorFolio = 0;
                }
                TotalHectareas += hectareasPorFolio;
                TotalMetros += metrosPorFolio;
                TotalCentimetros += centimetrosPorFolio;
            } catch (NumberFormatException e) {
                TotalHectareas = 0;
                TotalMetros = 0;
                TotalCentimetros = 0;
            }

            if (TotalMetros >= 10000) {
                TotalHectareas += 1;
                TotalMetros -= 10000;
            }

            if (TotalCentimetros >= 10000) {
                TotalMetros += 1;
                TotalCentimetros -= 10000;
            }
        }

        request.getSession().setAttribute(CFolioDerivado.HECTAREAS_MATRIZ, mHectareas);
        request.getSession().setAttribute(CFolioDerivado.METROS_MATRIZ, mMetros);
        request.getSession().setAttribute(CFolioDerivado.CENTIMETROS_MATRIZ, mCentimetros);
        request.getSession().setAttribute(CFolioDerivado.HECTAREAS_TOTAL, TotalHectareas);
        request.getSession().setAttribute(CFolioDerivado.METROS_TOTAL, TotalMetros);
        request.getSession().setAttribute(CFolioDerivado.CENTIMETROS_TOTAL, TotalCentimetros);

        request.getSession().setAttribute(WebKeys.WEB_SEGREGACION, webSegregacion);

        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento editarFolioDerivado(HttpServletRequest request) throws ValidacionParametrosSegregacion {
        ValidacionParametrosSegregacion exception = new ValidacionParametrosSegregacion();
        HttpSession session = request.getSession();

        WebSegregacion webSegregacion = (WebSegregacion) request.getSession().getAttribute(WebKeys.WEB_SEGREGACION);
        if (webSegregacion == null) {
            webSegregacion = new WebSegregacion();
        }

        List foliosDerivados = webSegregacion.getFoliosDerivados();
        if (foliosDerivados == null) {
            foliosDerivados = new Vector();
        }

        Iterator itFoliosD = foliosDerivados.iterator();

        /**
         * @author: Henry Gómez Rocha
         * @change: Modificacion del tipo de dato a las variables
         * porcentajeTotal y porcentaje de tipo double a tipo BigDecimal, para
         * controlar inexactitud ocacionada por usar tipo double al almacenar la
         * división de los lotes al momento de segregar. * MANTIS: 0003877
         *
         */
        BigDecimal porcentajeTotal = new BigDecimal("0.0");
        while (itFoliosD.hasNext()) {
            try {
                BigDecimal numPorFolio = new BigDecimal("0.0");
                try {
                    numPorFolio = new BigDecimal(((WebFolioDerivado) itFoliosD.next()).getPorcentaje().replaceAll("%", "").trim());
                } catch (Exception ee) {
                    numPorFolio = new BigDecimal("0.0");
                }
                porcentajeTotal = porcentajeTotal.add(numPorFolio);
            } catch (NumberFormatException e) {
                porcentajeTotal = porcentajeTotal.add(new BigDecimal("0.0"));
            }
        }
        
        String i = request.getParameter(WebKeys.POSICION);
        int pos = Integer.valueOf(i);
        WebFolioDerivado folioDerivado = (WebFolioDerivado) foliosDerivados.get(pos);
        
        String lote = request.getParameter(CFolioDerivado.NOMBRE_LOTE + i);
        String hectareas = request.getParameter(CFolioDerivado.HECTAREAS + i);
        String metros = request.getParameter(CFolioDerivado.METROS + i);
        String centimetros = request.getParameter(CFolioDerivado.CENTIMETROS + i);
        String porcen = request.getParameter(CFolioDerivado.PORCENTAJE + i);
        String privMetros = request.getParameter(CFolio.PRIVMETROS + i);
        String privCentimetros = request.getParameter(CFolio.PRIVCENTIMETROS + i);
        String consMetros = request.getParameter(CFolio.CONSMETROS + i);
        String consCentimetros = request.getParameter(CFolio.CONSCENTIMETROS + i);
        String determinaInm = request.getParameter(CFolio.DETERMINACION_INMUEBLE + i);
        //String unidad = request.getParameter(CFolioDerivado.UNIDAD_MEDIDA);

        int unidadmedidaexception = 3;

        if (hectareas == null || hectareas.length() <= 0) {
            unidadmedidaexception--;
            hectareas = "0";
        }

        if (metros == null || metros.length() <= 0) {
            unidadmedidaexception--;
            metros = "0";
        }

        if (centimetros == null || centimetros.length() <= 0) {
            unidadmedidaexception--;
            centimetros = "0";
        }

        if (unidadmedidaexception == 0) {

        }

        String natJuridica = (String) request.getSession().getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        Folio folioAreas = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        String hectareasM = (folioAreas.getHectareas() != null ? folioAreas.getHectareas() : "0");
        String metrosM = (folioAreas.getMetros() != null ? folioAreas.getMetros() : "0");
        String centimetrosM = (folioAreas.getCentimetros() != null ? folioAreas.getCentimetros() : "0");
        boolean datosMenor = true;
        boolean datosIgual = false;
        boolean datosMenorIgual = true;

        if (hectareasM.isEmpty()) {
            hectareasM = "0";
        }
        if (metrosM.isEmpty()) {
            metrosM = "0";
        }
        if (centimetrosM.isEmpty()) {
            centimetrosM = "0";
        }

        //Area Matriz para Validar
        int mHectareas = Integer.parseInt(hectareasM);
        int mMetros = Integer.parseInt(metrosM);
        int mCentimetros = Integer.parseInt(centimetrosM);

        if (hectareas == null || hectareas.isEmpty()) {
            hectareas = "0";
        }
        if (metros == null || metros.isEmpty()) {
            metros = "0";
        }
        if (centimetros == null || centimetros.isEmpty()) {
            centimetros = "0";
        }
        //Area Segregada para Validar
        int sHectareas = Integer.parseInt(hectareas);
        int sMetros = Integer.parseInt(metros);
        int sCentimetros = Integer.parseInt(centimetros);

        if (natJuridica.equals("01004")
                || natJuridica.equals("0110")
                || natJuridica.equals("0913")
                || natJuridica.equals("0918")
                || natJuridica.equals("0919")
                || natJuridica.equals("0920")
                || natJuridica.equals("0924")
                || natJuridica.equals("0103")
                || natJuridica.equals("0104")
                || natJuridica.equals("0105")
                || natJuridica.equals("0111")
                || natJuridica.equals("0116")
                || natJuridica.equals("0117")
                || natJuridica.equals("0121")
                || natJuridica.equals("0122")
                || natJuridica.equals("0124")
                || natJuridica.equals("0126")
                || natJuridica.equals("0131")
                || natJuridica.equals("0140")
                || natJuridica.equals("0141")
                || natJuridica.equals("0166")
                || natJuridica.equals("0171")
                || natJuridica.equals("0173")
                || natJuridica.equals("0185")
                || natJuridica.equals("0192")
                || natJuridica.equals("0915")
                || natJuridica.equals("01012")) {

            if (sHectareas > mHectareas) {
                datosMenorIgual = false;
            } else {
                if (sHectareas == mHectareas) {
                    if (sMetros > mMetros) {
                        datosMenorIgual = false;
                    } else {
                        if (sMetros == mMetros) {
                            if (sCentimetros > mCentimetros) {
                                datosMenorIgual = false;
                            }
                        }
                    }
                }
            }

            if (!datosMenorIgual) {
                if (!(mHectareas == 0 && mMetros == 0 && mCentimetros == 0)) {
                    exception.addError("El area segregada debe ser menor o igual al area matriz");
                }
            }
        }

        int TotalHectareas = 0;
        int TotalMetros = 0;
        int TotalCentimetros = 0;
        boolean areaMenorError = true;
        boolean areaIgualError = true;
        boolean areaMenorIgualError = true;

        if (!(mHectareas == 0 && mMetros == 0 && mCentimetros == 0)) {
            Iterator itFoliosDe = foliosDerivados.iterator();
            while (itFoliosDe.hasNext()) {
                try {
                    int hectareasPorFolio = 0;
                    int metrosPorFolio = 0;
                    int centimetrosPorFolio = 0;
                    String auxh, auxm, auxc;
                    try {
                        WebFolioDerivado dev = (WebFolioDerivado) itFoliosDe.next();
                        auxh = dev.getHectareas();
                        auxm = dev.getMetros();
                        auxc = dev.getCentimetros();
                        hectareasPorFolio = Integer.parseInt((auxh != null && !auxh.isEmpty() ? auxh : "0"));
                        metrosPorFolio = Integer.parseInt((auxm != null && !auxm.isEmpty() ? auxm : "0"));
                        centimetrosPorFolio = Integer.parseInt((auxc != null && !auxc.isEmpty() ? auxc : "0"));
                    } catch (Exception ee) {
                        hectareasPorFolio = 0;
                        metrosPorFolio = 0;
                        centimetrosPorFolio = 0;
                    }
                    TotalHectareas += hectareasPorFolio;
                    TotalMetros += metrosPorFolio;
                    TotalCentimetros += centimetrosPorFolio;
                } catch (NumberFormatException e) {
                    TotalHectareas = 0;
                    TotalMetros = 0;
                    TotalCentimetros = 0;
                }
            }

            TotalHectareas += sHectareas;
            TotalMetros += sMetros;
            TotalCentimetros += sCentimetros;

            if (TotalMetros >= 10000) {
                TotalHectareas += 1;
                TotalMetros -= 10000;
            }

            if (TotalCentimetros >= 10000) {
                TotalMetros += 1;
                TotalCentimetros -= 10000;
            }
        }

        if (natJuridica.equals("0110")
                || natJuridica.equals("0913")
                || natJuridica.equals("0918")
                || natJuridica.equals("0919")
                || natJuridica.equals("0924")) {

            if (TotalHectareas > mHectareas) {
                areaIgualError = false;
            } else {
                if (TotalHectareas == mHectareas) {
                    if (TotalMetros > mMetros) {
                        areaIgualError = false;
                    } else {
                        if (TotalMetros == mMetros) {
                            if (TotalCentimetros > mCentimetros) {
                                areaIgualError = false;
                            }
                        }
                    }
                }
            }

            if (!areaIgualError) {
                if (!(mHectareas == 0 && mMetros == 0 && mCentimetros == 0)) {
                    exception.addError("El area total segregada debe ser igual al area matriz");
                }
            }
        }

        if (natJuridica.equals("01004")
                || natJuridica.equals("0920")
                || natJuridica.equals("0103")
                || natJuridica.equals("0104")
                || natJuridica.equals("0105")
                || natJuridica.equals("0111")
                || natJuridica.equals("0116")
                || natJuridica.equals("0117")
                || natJuridica.equals("0121")
                || natJuridica.equals("0122")
                || natJuridica.equals("0124")
                || natJuridica.equals("0126")
                || natJuridica.equals("0131")
                || natJuridica.equals("0140")
                || natJuridica.equals("0141")
                || natJuridica.equals("0166")
                || natJuridica.equals("0171")
                || natJuridica.equals("0173")
                || natJuridica.equals("0185")
                || natJuridica.equals("0192")
                || natJuridica.equals("0915")
                || natJuridica.equals("01012")) {

            if (TotalHectareas > mHectareas) {
                areaMenorIgualError = false;
            } else {
                if (TotalHectareas == mHectareas) {
                    if (TotalMetros > mMetros) {
                        areaMenorIgualError = false;
                    } else {
                        if (TotalMetros == mMetros) {
                            if (TotalCentimetros > mCentimetros) {
                                areaMenorIgualError = false;
                            }
                        }
                    }
                }
            }

            if (!areaMenorIgualError) {
                if (!(mHectareas == 0 && mMetros == 0 && mCentimetros == 0)) {
                    exception.addError("El area total segregada debe ser menor o igual al area matriz");
                }
            }
        }

        if (lote == null || lote.length() <= 0) {
            exception.addError("Debe ingresar el nombre del inmueble para el folio derivado");
        }

        porcen = (porcen.replaceAll("%", "")).trim();
        BigDecimal porcentaje = new BigDecimal("0.0");

        if (porcen != null) {
            if (porcen.length() > 0 || !porcen.equals("")) {
                try {
                    porcentaje = new BigDecimal(porcen);
                    if (porcentaje.doubleValue() <= 0) {
                        exception.addError("El coeficiente no puede ser negativo o cero");
                    }
                } catch (NumberFormatException e) {
                    exception.addError("El coeficiente es inválido");
                }
                porcen += "%";
            }
        }

        /**
         * @author: Henry Gómez Rocha
         * @change: Almacenando en la variable porcentajeTotal la totalidad de
         * los lotes y verificando que dicha totalidad no sea superio a 100. *
         * MANTIS: 0003877
         *
         */
        porcentajeTotal = porcentajeTotal.add(porcentaje);
        if (porcentajeTotal.doubleValue() > 100) {
            exception.addError("El coeficiente no puede superar 100%");
        }

        if (privMetros == null || privMetros.isEmpty()) {
            privMetros = "0";
        }

        if (privCentimetros == null || privCentimetros.isEmpty()) {
            privCentimetros = "0";
        }

        if (consMetros == null || consMetros.isEmpty()) {
            consMetros = "0";
        }

        if (consCentimetros == null || consCentimetros.isEmpty()) {
            consCentimetros = "0";
        }

        int pMetros = Integer.parseInt(privMetros);
        int pCentimetros = Integer.parseInt(privCentimetros);
        int cMetros = Integer.parseInt(consMetros);
        int cCentimetros = Integer.parseInt(consCentimetros);
        boolean datosPrivBien = true;
        boolean datosConsBien = true;

        if (!natJuridica.equals("0911")) {
            if (pMetros != 0 || pCentimetros != 0) {

                if (cMetros == 0 && cCentimetros == 0) {

                } else {
                    if (pMetros > cMetros) {
                        datosPrivBien = false;
                    } else {
                        if (pMetros == cMetros) {
                            if (pCentimetros > cCentimetros) {
                                datosPrivBien = false;
                            }
                        }
                    }

                    if (!datosPrivBien) {
//                    exception.addError("El area privada no puede ser mayor al area construida");
                    }

                    if (sHectareas <= 0) {
                        if (cMetros > sMetros) {
                            datosConsBien = false;
                        } else {
                            if (cMetros == sMetros) {
                                if (cCentimetros > sCentimetros) {
                                    datosConsBien = false;
                                }
                            }
                        }
                    }

                    if (!datosConsBien) {
                        //exception.addError("El area construida no puede ser mayor al area del terreno");
                    }
                }
            } else {
                if (cMetros != 0 || cCentimetros != 0) {

                    if (sHectareas <= 0) {
                        if (cMetros > sMetros) {
                            datosConsBien = false;
                        } else {
                            if (cMetros == sMetros) {
                                if (cCentimetros > sCentimetros) {
                                    datosConsBien = false;
                                }
                            }
                        }
                    }

                    if (!datosConsBien) {
//                    exception.addError("El area construida no puede ser mayor al area del terreno");
                    }
                }
            }
        }

        if (privMetros.equals("0") && privCentimetros.equals("0")) {
            privMetros = null;
            privCentimetros = null;
        }

        if (consMetros.equals("0") && consCentimetros.equals("0")) {
            consMetros = null;
            consCentimetros = null;
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        WebFolioDerivado folioDerivadoTMP = new WebFolioDerivado();
        folioDerivadoTMP.setHectareas(hectareas);
        folioDerivadoTMP.setMetros(metros);
        folioDerivadoTMP.setCentimetros(centimetros);
        folioDerivadoTMP.setInmueble(lote);
        folioDerivadoTMP.setPorcentaje(porcen.toUpperCase());
        if (privMetros != null) {
            folioDerivadoTMP.setPrivMetros(privMetros);
        }
        if (privCentimetros != null) {
            folioDerivadoTMP.setPrivCentimetros(privCentimetros);
        }
        if (consMetros != null) {
            folioDerivadoTMP.setConsMetros(consMetros);
        }
        if (consCentimetros != null) {
            folioDerivadoTMP.setConsCentimetros(consCentimetros);
        }
        if (determinaInm != null && !determinaInm.equals(WebKeys.SIN_SELECCIONAR)) {
            folioDerivadoTMP.setDeterminaInm(determinaInm);
        }
        
        session.setAttribute(CFolioDerivado.HECTAREAS + i,(hectareas!=null?hectareas:"0"));
        session.setAttribute(CFolioDerivado.METROS + i,(metros!=null?metros:"0"));
        session.setAttribute(CFolioDerivado.CENTIMETROS + i,(centimetros!=null?centimetros:"0"));
        session.setAttribute(CFolioDerivado.NOMBRE_LOTE + i,(lote!=null?lote:"0"));
        session.setAttribute(CFolioDerivado.PORCENTAJE + i,(porcen!=null?porcen.toUpperCase():"0"));
        session.setAttribute(CFolio.PRIVMETROS + i,(privMetros!=null?privMetros:"0"));
        session.setAttribute(CFolio.PRIVCENTIMETROS + i,(privCentimetros!=null?privCentimetros:"0"));
        session.setAttribute(CFolio.CONSMETROS + i,(consMetros!=null?consMetros:"0"));
        session.setAttribute(CFolio.CONSCENTIMETROS + i,(consCentimetros!=null?consCentimetros:"0"));
        session.setAttribute(CFolio.DETERMINACION_INMUEBLE + i,(determinaInm!=null?determinaInm:"0"));
        folioDerivadoTMP.setFechaCreacion(new Date());

        if (!puedeActualizarse(foliosDerivados, folioDerivadoTMP, pos)) {
            folioDerivado.setHectareas(hectareas);
            folioDerivado.setMetros(metros);
            folioDerivado.setCentimetros(centimetros);
            folioDerivado.setInmueble(lote);
            folioDerivado.setPorcentaje(porcen);
            folioDerivado.setPrivMetros(privMetros);
            folioDerivado.setPrivCentimetros(privCentimetros);
            folioDerivado.setConsMetros(consMetros);
            folioDerivado.setConsCentimetros(consCentimetros);
            folioDerivado.setDeterminaInm(determinaInm);
            request.getSession().setAttribute(WebKeys.WEB_SEGREGACION, webSegregacion);
        } else {
            exception.addError("El folio derivado que intenta editar, ya existe");
            throw exception;
        }

        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento guardarWebSegregacion(HttpServletRequest request) {

        WebSegregacion webSegregacion = (WebSegregacion) request.getSession().getAttribute(WebKeys.WEB_SEGREGACION);
        if (webSegregacion == null) {
            webSegregacion = new WebSegregacion();
        }

        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        return new EvnSegregacion(usuarioAuriga, folio, turno, webSegregacion, EvnSegregacion.GUARDAR_WEB_SEGREGACION, usuarioNeg);
    }

    /**
     * @param request
     * @return
     * @author : Julio Alcázar Rivas
     * @change : Se cambiaron las constantes de navegacion web y se modifico el
     * comportamiento de las salvedades cambiaron de tipo SalvedadFolio a
     * SalvedadAnotacion Caso Mantis : 04131
     */
    private Evento segregarMasivo(HttpServletRequest request) throws ValidacionParametrosSegregacion {
        HttpSession session = request.getSession();
        ValidacionParametrosSegregacion exception = new ValidacionParametrosSegregacion();

        WebSegregacion webSegregacion = (WebSegregacion) request.getSession().getAttribute(WebKeys.WEB_SEGREGACION);
        if (webSegregacion == null) {
            webSegregacion = new WebSegregacion();
        }

        List foliosD = webSegregacion.getFoliosDerivados();
        if (foliosD == null || foliosD.isEmpty()) {
            exception.addError("Debe incluir por lo menos la informacion de un folio derivado");
            throw exception;
        }

        String natJuridica = (String) request.getSession().getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        Folio folioM = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        String hectareasM = "0";
        String metrosM = "0";
        String centimetrosM = "0";

        if (folioM.getHectareas() != null && !folioM.getHectareas().isEmpty()) {
            hectareasM = folioM.getHectareas();
        }

        if (folioM.getMetros() != null && !folioM.getMetros().isEmpty()) {
            metrosM = folioM.getMetros();
        }

        if (folioM.getCentimetros() != null && !folioM.getCentimetros().isEmpty()) {
            centimetrosM = folioM.getCentimetros();
        }

        int mHectareas = Integer.parseInt(hectareasM);
        int mMetros = Integer.parseInt(metrosM);
        int mCentimetros = Integer.parseInt(centimetrosM);

        int TotalHectareas = 0;
        int TotalMetros = 0;
        int TotalCentimetros = 0;
        boolean areaIgualError = false;

        if (!(mHectareas == 0 && mMetros == 0 && mCentimetros == 0)) {
            Iterator itFoliosDe = foliosD.iterator();
            while (itFoliosDe.hasNext()) {
                try {
                    int hectareasPorFolio = 0;
                    int metrosPorFolio = 0;
                    int centimetrosPorFolio = 0;
                    String auxh, auxm, auxc;
                    try {
                        WebFolioDerivado dev = (WebFolioDerivado) itFoliosDe.next();
                        auxh = dev.getHectareas();
                        auxm = dev.getMetros();
                        auxc = dev.getCentimetros();
                        hectareasPorFolio = Integer.parseInt((auxh != null && !auxh.isEmpty() ? auxh : "0"));
                        metrosPorFolio = Integer.parseInt((auxm != null && !auxm.isEmpty() ? auxm : "0"));
                        centimetrosPorFolio = Integer.parseInt((auxc != null && !auxc.isEmpty() ? auxc : "0"));
                    } catch (Exception ee) {
                        hectareasPorFolio = 0;
                        metrosPorFolio = 0;
                        centimetrosPorFolio = 0;
                    }
                    TotalHectareas += hectareasPorFolio;
                    TotalMetros += metrosPorFolio;
                    TotalCentimetros += centimetrosPorFolio;
                } catch (NumberFormatException e) {
                    TotalHectareas = 0;
                    TotalMetros = 0;
                    TotalCentimetros = 0;
                }

                if (TotalMetros >= 10000) {
                    TotalHectareas += 1;
                    TotalMetros -= 10000;
                }

                if (TotalCentimetros >= 10000) {
                    TotalMetros += 1;
                    TotalCentimetros -= 10000;
                }
            }

        }

        if (natJuridica.equals("0110")
                || natJuridica.equals("0913")
                || natJuridica.equals("0918")
                || natJuridica.equals("0919")
                || natJuridica.equals("0924")) {
            if (TotalHectareas == mHectareas && TotalMetros == mMetros && TotalCentimetros == mCentimetros) {
                areaIgualError = true;
            }

            if (!areaIgualError) {
                if (!(mHectareas == 0 && mMetros == 0 && mCentimetros == 0)) {
                    exception.addError("El area total segregada debe ser igual al area matriz");
                }
            }
        }

        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        String salvedadDesc = request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION);

        if (!fase.getID().equals(CFase.CAL_CALIFICACION)) {
            session.setAttribute(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION, salvedadDesc);

            if (salvedadDesc == null || salvedadDesc.equals("")) {
                exception.addError("Debe ingresar una salvedad");
            } else if (salvedadDesc.length() < 30) {
                exception.addError("La salvedad debe tener más de 30 caracteres");
            } else if (salvedadDesc.length() > 1024) {
                exception.addError("La salvedad debe tener máximo 1024 caracteres");
            }
        }

        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }

        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);
        String idSalvedad = (String) session.getAttribute(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID);
        SalvedadAnotacion salvedadAnotacion = null;
        if (!fase.getID().equals(CFase.CAL_CALIFICACION)) {
            salvedadAnotacion = new SalvedadAnotacion();
            salvedadAnotacion.setDescripcion(salvedadDesc);
            salvedadAnotacion.setIdSalvedad(idSalvedad);
            salvedadAnotacion.setIdMatricula(folio.getIdMatricula());
            salvedadAnotacion.setNumRadicacion(turno.getIdWorkflow());
        }

        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        EvnSegregacion ev = new EvnSegregacion(usuarioAuriga, folio, webSegregacion, turno, circulo, EvnSegregacion.SEGREGACION_FOLIO, usuarioSIR);
        if (!fase.getID().equals(CFase.CAL_CALIFICACION)) {
            ev.setSalvedadanotacion(salvedadAnotacion);
        }
        /**
         * @Author Carlos Torres
         * @Mantis 13176
         * @Chaged ;
         */
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioNeg.getUsername(), String.valueOf(usuarioNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
        ev.setInfoUsuario(infoUsuario);

        return ev;
    }

    /**
     * Permite cargar el folio para que pueda ser editado.
     *
     * @param request
     * @return
     */
    private Evento consultarNuevoFolio(HttpServletRequest request) throws AccionWebException {
        WebSegregacion webSegregacion = (WebSegregacion) request.getSession().getAttribute(WebKeys.WEB_SEGREGACION);
        if (webSegregacion == null) {
            webSegregacion = new WebSegregacion();
        }

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioSIR = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        String idMatricula = request.getParameter(CFolio.ID_MATRICULA);

        Folio folio = new Folio();
        folio.setIdMatricula(idMatricula);

        return new EvnSegregacion(usuarioAuriga, folio, turno, webSegregacion, EvnSegregacion.CONSULTAR_NUEVO_FOLIO, usuarioSIR);
    }

    /**
     * Permite guardar los cambios que se efectuaron sobre un folio, en el
     * proceso de segregación.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento guardarCambiosFolio(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();
        ValidacionParametrosEditarFolioException exception = new ValidacionParametrosEditarFolioException();

        Folio folioOriginal = (Folio) session.getAttribute(WebKeys.FOLIO_EDITADO);
        if (folioOriginal == null) {
            folioOriginal = new Folio();
        }

        Folio folio = new Folio();
        folio.setIdMatricula(folioOriginal.getIdMatricula());
        //folio.setZonaRegistral(folioOriginal.getZonaRegistral());

        //Realizar validaciones
        preservarInfoBasicaFolio(request);

        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        if (turno == null) {
            throw new AccionWebException("No se encontro el turno en la sesion");
        }

        String valorComplementacion = request.getParameter(CFolio.FOLIO_COMPLEMENTACION);
        if (valorComplementacion != null) {
            valorComplementacion = valorComplementacion.toUpperCase();
        }

        String valorCodCatastral = request.getParameter(CFolio.FOLIO_COD_CATASTRAL);

        String estadoFolio = null;
        estadoFolio = request.getParameter(CFolio.FOLIO_ESTADO_FOLIO);
        if (estadoFolio == null || estadoFolio.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar el estado para el folio");
        }

        //Lanzar excepción si existio en la validación
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        List idFoliosEditados = (List) request.getSession().getAttribute(WebKeys.LISTA_ID_FOLIOS_SEGREGADOS_EDITADOS);
        if (idFoliosEditados == null) {
            idFoliosEditados = new ArrayList();
        }
        idFoliosEditados.add(folio.getIdMatricula());
        request.getSession().setAttribute(WebKeys.LISTA_ID_FOLIOS_SEGREGADOS_EDITADOS, idFoliosEditados);

        EstadoFolio estado = null;
        if (estadoFolio != null && !estadoFolio.equals("")) {
            estado = new EstadoFolio();
            estado.setIdEstado(estadoFolio);
            folio.setEstado(estado);
        }

        Complementacion comp = new Complementacion();
        comp.setComplementacion(valorComplementacion);

        folio.setComplementacion(comp);
        folio.setCodCatastral(valorCodCatastral);

        List direcciones = (List) session.getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
        if (direcciones != null) {
            Iterator itDirecciones = direcciones.iterator();

            while (itDirecciones.hasNext()) {
                Direccion temp = ((Direccion) itDirecciones.next());
                folio.addDireccione(temp);
            }
        }

        session.setAttribute(WebKeys.FOLIO_EDITADO, folio);

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        return new EvnSegregacion(usuarioAuriga, usuarioSIR, turno, folio, EvnSegregacion.GUARDAR_CAMBIOS_FOLIO);
    }

    /**
     * @param request
     * @return
     */
    private Evento preservarInfoAnotacion(HttpServletRequest request) {
        preservarInfoBasicaAnotacion(request);
        return null;
    }

    private Date darFecha(String fechaInterfaz) {
        Calendar calendar = Calendar.getInstance();
        String[] partido = fechaInterfaz.split("/");

        if (partido.length == 3) {
            int dia = Integer.parseInt(partido[0]);
            int mes = Integer.parseInt(partido[1]) - 1;
            int año = Integer.parseInt(partido[2]);
            calendar.set(año, mes, dia);

            if ((calendar.get(Calendar.YEAR) == año)
                    && (calendar.get(Calendar.MONTH) == mes)
                    && (calendar.get(Calendar.DAY_OF_MONTH) == dia)) {
                return calendar.getTime();
            }
        }

        return null;
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
     * @param request
     * @return
     */
    private Evento consultarUltimosPropietarios(HttpServletRequest request) {

        HttpSession session = request.getSession();

        preservarInfoBasicaFolio(request);
        preservarInfoBasicaVariosCiudadanos(request);
        preservarInfoAnotacion(request);

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request
                .getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String UID = request.getSession().getId();
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(
                WebKeys.USUARIO);

        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);

        String numAnotacionTmp = (String) session.getAttribute(CFolio.NEXT_ORDEN_ANOTACION);
        //String numAnotacionTmp = (String) request.getParameter(AWCalificacion.NUM_ANOTACION_TEMPORAL);

        if (numAnotacionTmp != null) {
            request.getSession().setAttribute(AWCalificacion.NUM_ANOTACION_TEMPORAL, numAnotacionTmp);
        }
        /*request.getSession().setAttribute(
				AWCalificacion.NUM_ANOTACION_TEMPORAL,
				);*/

        request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO,
                new Boolean(true));

        // crear e inicializar el evento
        EvnSegregacion evento = new EvnSegregacion(usuarioAuriga, usuarioNeg, turno, folio, EvnSegregacion.CONSULTAR_ULTIMOS_PROPIETARIOS);
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento cancelarPropietario(HttpServletRequest request) {
        request.getSession().removeAttribute(
                AWCalificacion.LISTA_PROPIETARIOS_FOLIO);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento guardarPropietario(HttpServletRequest request) throws AccionWebException {
        // request.getSession().removeAttribute(AWCalificacion.LISTA_PROPIETARIOS_FOLIO);

        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);
        int auxnumCiud = numCiud;

        int ciudadanosEliminados = 0;

        HttpSession session = request.getSession();
        String key = null;
        Object parametro = null;

        // Aca se valida la cantidad de registro que de verdad estan llenos.
        boolean continuarGuardado = false;
        while (!continuarGuardado && (auxnumCiud > 0)) {
            key = CFolio.ANOTACION_NUM_ID_PERSONA + (auxnumCiud - 1);

            if (request.getSession().getAttribute(key) != null
                    && !(((String) request.getSession().getAttribute(key))
                            .equals(""))) {
                continuarGuardado = true;
            }
            key = CFolio.ANOTACION_APELLIDO_1_PERSONA + (auxnumCiud - 1);
            if (request.getSession().getAttribute(key) != null
                    && !(((String) request.getSession().getAttribute(key))
                            .equals(""))) {
                continuarGuardado = true;
            }
            key = CFolio.ANOTACION_APELLIDO_2_PERSONA + (auxnumCiud - 1);
            if (request.getSession().getAttribute(key) != null
                    && !(((String) request.getSession().getAttribute(key))
                            .equals(""))) {
                continuarGuardado = true;
            }
            key = CFolio.ANOTACION_NOMBRES_PERSONA + (auxnumCiud - 1);
            if (request.getSession().getAttribute(key) != null
                    && !(((String) request.getSession().getAttribute(key))
                            .equals(""))) {
                continuarGuardado = true;
            }

            if (!continuarGuardado) {
                ciudadanosEliminados++;
            }
            auxnumCiud--;
        }

        String[] numPropietario = request
                .getParameterValues("ESCOGER_PROPIETARIO");

        if (numPropietario == null) {
            throw new ParametroInvalidoException("Debe Seleccionar un Ciudadano");
        }

        int numSelecionados = numPropietario.length;
        String[] selPropietarios = new String[numPropietario.length];

        for (int i = 0; i < numPropietario.length; i++) {
            selPropietarios[i] = numPropietario[i];
        }

        List listaUltimosPropietarios = (List) request.getSession()
                .getAttribute(AWCalificacion.LISTA_PROPIETARIOS_FOLIO);

        int contadorSelecion = 0;
        for (int i = (numCiud - ciudadanosEliminados); i < ((numCiud - ciudadanosEliminados) + numSelecionados); i++) {
            AnotacionCiudadano anotacionciu = (AnotacionCiudadano) listaUltimosPropietarios.get(Integer
                    .valueOf(selPropietarios[contadorSelecion]).intValue());
            /*Ciudadano ciu = (Ciudadano) listaUltimosPropietarios.get(Integer
					.valueOf(selPropietarios[contadorSelecion]).intValue());*/
            Ciudadano ciu = anotacionciu.getCiudadano();
            contadorSelecion++;
            /*
			 * key = CFolio.ANOTACION_TIPO_INTER_ASOCIACION+i; parametro =
			 * request.getParameter(key); if (parametro != null)
			 * session.setAttribute(key, parametro);
             */

            key = CFolio.ANOTACION_TIPO_ID_PERSONA + i;
            parametro = (String) ciu.getTipoDoc();
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            if (!parametro.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)) {
                key = CFolio.ANOTACION_NUM_ID_PERSONA + i;
                parametro = (String) ciu.getDocumento();
                if (parametro != null) {
                    session.setAttribute(key, parametro);
                }
                key = CFolio.CIUDADANO_EDITABLE + i;
                Boolean par = new Boolean(true);
                parametro = par;
                if (parametro != null) {
                    session.setAttribute(key, parametro);
                }
            }

            key = CFolio.ANOTACION_APELLIDO_1_PERSONA + i;
            parametro = (String) ciu.getApellido1();
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_APELLIDO_2_PERSONA + i;
            parametro = (String) ciu.getApellido2();
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_NOMBRES_PERSONA + i;
            parametro = (String) ciu.getNombre();
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

        }
        request.getSession().removeAttribute(
                AWCalificacion.LISTA_PROPIETARIOS_FOLIO);
        numCiud = (numCiud - ciudadanosEliminados) + numSelecionados;
        Integer num = new Integer(numCiud);
        session.setAttribute(NUM_REGISTROS_TABLA_CIUDADANOS, num);
        return null;
    }

    private void eliminarInfoBasicaCiudadano(HttpServletRequest request) {
        request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA);
        request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_PERSONA);
        request.getSession().removeAttribute(CFolio.ANOTACION_SEXO_PERSONA);
        request.getSession().removeAttribute(CFolio.ANOTACION_COD_VERIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_NUM_ID_PERSONA);
        request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA);
        request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA);
        request.getSession().removeAttribute(CFolio.ANOTACION_NOMBRES_PERSONA);
        request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_INTER_ASOCIACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_PORCENTAJE_ASOCIACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION);
    }

    private void eliminarInfoBasicaVariosCiudadanos(HttpServletRequest request) {
        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);
        for (int i = 0; i < numCiud; i++) {
            request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_SEXO_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_COD_VERIFICACION + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_NUM_ID_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_NOMBRES_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i);
        }
    }

    /**
     * @return
     */
    private Evento preservarInfo(HttpServletRequest request) {
        preservarInfoBasicaFolio(request);
        return null;
    }

    private void preservarInfoBasicaCiudadano(HttpServletRequest request) {
        if (request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA, request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA));
        }
        if (request.getParameter(CFolio.ANOTACION_TIPO_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_PERSONA, request.getParameter(CFolio.ANOTACION_TIPO_PERSONA));
        }
        if (request.getParameter(CFolio.ANOTACION_SEXO_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_SEXO_PERSONA, request.getParameter(CFolio.ANOTACION_SEXO_PERSONA));
        }
        if (request.getParameter(CFolio.ANOTACION_COD_VERIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_COD_VERIFICACION, request.getParameter(CFolio.ANOTACION_COD_VERIFICACION));
        }
        if (request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NUM_ID_PERSONA, request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA));
        }
        if (request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA, request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA));
        }
        if (request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA, request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA));
        }
        if (request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NOMBRES_PERSONA, request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA));
        }
        if (request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_INTER_ASOCIACION, request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION));
        }
        if (request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_PORCENTAJE_ASOCIACION, request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION));
        }
        if (request.getParameter(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION, request.getParameter(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION));
        }
    }

    private void preservarInfoBasicaVariosCiudadanos(HttpServletRequest request) {

        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);

        HttpSession session = request.getSession();
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
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_SEXO_PERSONA + i;
            parametro = request.getParameter(key);
            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_COD_VERIFICACION + i;
            parametro = request.getParameter(key);
            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
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

    /**
     * @param request
     */
    private void preservarInfoBasicaAnotacion(HttpServletRequest request) {

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA));
        }
        /*
               *  @author Carlos Torres
               *  @chage   se agrega validacion de version diferente
               *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        if (request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION, request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION));
        }

        if (request.getParameter(CFolio.ANOTACION_ID_ANOTACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_ID_ANOTACION, request.getParameter(CFolio.ANOTACION_ID_ANOTACION));
        }

        if (request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION, request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_FECHA_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO, request.getParameter(CFolio.ANOTACION_FECHA_DOCUMENTO));
        }

        if (request.getParameter(CFolio.ANOTACION_FECHA_RADICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_FECHA_RADICACION, request.getParameter(CFolio.ANOTACION_FECHA_RADICACION));
        }
        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO, request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO));
        }

        if (request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION, request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_MODALIDAD) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_MODALIDAD, request.getParameter(CFolio.ANOTACION_MODALIDAD));
        }

        if (request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NUM_DOCUMENTO, request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            request.getSession().setAttribute("tipo_oficina", request.getParameter("tipo_oficina"));
            request.getSession().setAttribute("tipo_nom_oficina", request.getParameter("tipo_nom_oficina"));
            request.getSession().setAttribute("numero_oficina", request.getParameter("numero_oficina"));
            request.getSession().setAttribute("id_oficina", request.getParameter("id_oficina"));
            /*
                       *  @author Carlos Torres
                       *  @chage   se agrega validacion de version diferente
                       *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            request.getSession().setAttribute("version", request.getParameter("version"));
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID));
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM));
        }
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change :
         */
        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER));
        }

        if (request.getParameter(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION) != null) {
            request.getSession().setAttribute(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION, request.getParameter(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION));
        }

        if (request.getParameter(CSolicitudRegistro.NUMERO_RADICACION) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.NUMERO_RADICACION, request.getParameter(CSolicitudRegistro.NUMERO_RADICACION));
        }

        if (request.getParameter(CSolicitudRegistro.CALENDAR2) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.CALENDAR2, request.getParameter(CSolicitudRegistro.CALENDAR2));
        }
    }

    /**
     * @param request
     */
    private void preservarInfoBasicaFolio(HttpServletRequest request) {
        if (request.getParameter(CFolio.ID_MATRICULA) != null) {
            request.getSession().setAttribute(CFolio.ID_MATRICULA, request.getParameter(CFolio.ID_MATRICULA));
        }
        if (request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_ID_DEPTO, request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_NOM_DEPTO, request.getParameter(CFolio.FOLIO_LOCACION_NOM_DEPTO));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_ID_MUNIC, request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_NOM_MUNIC, request.getParameter(CFolio.FOLIO_LOCACION_NOM_MUNIC));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_ID_VEREDA, request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_NOM_VEREDA, request.getParameter(CFolio.FOLIO_LOCACION_NOM_VEREDA));
        }

        if (request.getParameter(CFolio.FOLIO_ESTADO_FOLIO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_ESTADO_FOLIO, request.getParameter(CFolio.FOLIO_ESTADO_FOLIO));
        }

        if (request.getParameter(CFolio.FOLIO_TIPO_PREDIO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_TIPO_PREDIO, request.getParameter(CFolio.FOLIO_TIPO_PREDIO));
        }

        if (request.getParameter(CFolio.FOLIO_NUPRE) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_NUPRE, request.getParameter(CFolio.FOLIO_NUPRE));
        }

        if (request.getParameter(CFolio.FOLIO_DETERMINACION_INMUEBLE) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_DETERMINACION_INMUEBLE, request.getParameter(CFolio.FOLIO_DETERMINACION_INMUEBLE));
        }

        if (request.getParameter(CFolio.FOLIO_AVALUO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_AVALUO, request.getParameter(CFolio.FOLIO_AVALUO));
        }

        if (request.getParameter(CFolio.FOLIO_FECHA_AVALUO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_FECHA_AVALUO, request.getParameter(CFolio.FOLIO_FECHA_AVALUO));
        }

        if (request.getParameter(CFolio.FOLIO_DESTINACION_ECONOMICA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_DESTINACION_ECONOMICA, request.getParameter(CFolio.FOLIO_DESTINACION_ECONOMICA));
        }

        if (request.getParameter(CFolio.FOLIO_PRIVMETROS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_PRIVMETROS, request.getParameter(CFolio.FOLIO_PRIVMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_PRIVCENTIMETROS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_PRIVCENTIMETROS, request.getParameter(CFolio.FOLIO_PRIVCENTIMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_CONSMETROS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_CONSMETROS, request.getParameter(CFolio.FOLIO_CONSMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_CONSCENTIMETROS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_CONSCENTIMETROS, request.getParameter(CFolio.FOLIO_CONSCENTIMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_COEFICIENTE) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_COEFICIENTE, request.getParameter(CFolio.FOLIO_COEFICIENTE));
        }

        if (request.getParameter(CFolio.FOLIO_HECTAREAS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_HECTAREAS, request.getParameter(CFolio.FOLIO_HECTAREAS));
        }

        if (request.getParameter(CFolio.FOLIO_METROS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_METROS, request.getParameter(CFolio.FOLIO_METROS));
        }

        if (request.getParameter(CFolio.FOLIO_CENTIMETROS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_CENTIMETROS, request.getParameter(CFolio.FOLIO_CENTIMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_LINDEROS_DEFINIDOS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LINDEROS_DEFINIDOS, request.getParameter(CFolio.FOLIO_LINDEROS_DEFINIDOS));
        }

        if (request.getParameter(CFolio.FOLIO_COMPLEMENTACION) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_COMPLEMENTACION, request.getParameter(CFolio.FOLIO_COMPLEMENTACION));
        }

        if (request.getParameter(CFolio.FOLIO_LINDERO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LINDERO, request.getParameter(CFolio.FOLIO_LINDERO));
        }

        if (request.getParameter(CFolio.FOLIO_TIPO_PREDIO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_TIPO_PREDIO, request.getParameter(CFolio.FOLIO_TIPO_PREDIO));
        }

        if (request.getParameter(CFolio.FOLIO_COD_CATASTRAL) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_COD_CATASTRAL, request.getParameter(CFolio.FOLIO_COD_CATASTRAL));
        }

        if (request.getParameter(CFolio.FOLIO_EJE1) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_EJE1, request.getParameter(CFolio.FOLIO_EJE1));
        }

        if (request.getParameter(CFolio.FOLIO_EJE2) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_EJE2, request.getParameter(CFolio.FOLIO_EJE2));
        }

        if (request.getParameter(CFolio.FOLIO_VALOR1) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_VALOR1, request.getParameter(CFolio.FOLIO_VALOR1));
        }

        if (request.getParameter(CFolio.FOLIO_VALOR2) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_VALOR2, request.getParameter(CFolio.FOLIO_VALOR2));
        }

        if (request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_COMPLEMENTO_DIR, request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR));
        }

        if (request.getParameter(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO) != null) {
            request.getSession().setAttribute(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO, request.getParameter(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO));
        }
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
        request.getSession().removeAttribute(CFolio.ANOTACION_MODALIDAD);
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
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change :
         */
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);
        request.getSession().removeAttribute(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
        request.getSession().removeAttribute("tipo_oficina");
        request.getSession().removeAttribute("tipo_nom_oficina");
        request.getSession().removeAttribute("numero_oficina");
        request.getSession().removeAttribute("id_oficina");
        request.getSession().removeAttribute(CSolicitudRegistro.NUMERO_RADICACION);
        request.getSession().removeAttribute(CSolicitudRegistro.CALENDAR2);
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
        request.getSession().removeAttribute(CFolio.FOLIO_NUPRE);
        request.getSession().removeAttribute(CFolio.FOLIO_DETERMINACION_INMUEBLE);
        request.getSession().removeAttribute(CFolio.FOLIO_AVALUO);
        request.getSession().removeAttribute(CFolio.FOLIO_FECHA_AVALUO);
        request.getSession().removeAttribute(CFolio.FOLIO_DESTINACION_ECONOMICA);
        request.getSession().removeAttribute(CFolio.FOLIO_PRIVMETROS);
        request.getSession().removeAttribute(CFolio.FOLIO_PRIVCENTIMETROS);
        request.getSession().removeAttribute(CFolio.FOLIO_CONSMETROS);
        request.getSession().removeAttribute(CFolio.FOLIO_CONSCENTIMETROS);
        request.getSession().removeAttribute(CFolio.FOLIO_COEFICIENTE);
        request.getSession().removeAttribute(CFolio.FOLIO_HECTAREAS);
        request.getSession().removeAttribute(CFolio.FOLIO_METROS);
        request.getSession().removeAttribute(CFolio.FOLIO_CENTIMETROS);
        request.getSession().removeAttribute(CFolio.FOLIO_LINDEROS_DEFINIDOS);
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
     *
     * @param ciudadano
     * @param ciudadanos
     * @return
     */
    private boolean existeEnListaCiudadanos(AnotacionCiudadano ciudadano, List ciudadanos) {
        Iterator itCiudadanos = ciudadanos.iterator();

        while (itCiudadanos.hasNext()) {
            AnotacionCiudadano temp = (AnotacionCiudadano) itCiudadanos.next();

            if (temp.getCiudadano().getDocumento().equals(ciudadano.getCiudadano().getDocumento()) && temp.getCiudadano().getTipoDoc().equals(ciudadano.getCiudadano().getTipoDoc())) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param anotacion
     * @param anotaciones
     * @return
     */
    private boolean existeEnListaAnotaciones(Anotacion anotacion, List anotaciones) {
        Iterator itAnotaciones = anotaciones.iterator();

        while (itAnotaciones.hasNext()) {
            Anotacion temp = (Anotacion) itAnotaciones.next();

            if (temp.getIdAnotacion().equals(anotacion.getIdAnotacion()) && temp.getIdMatricula().equals(anotacion.getIdMatricula())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Carga en la sesión la información de un folio.
     */
    private void cargarInfoBasicaFolio(Folio folio, HttpServletRequest request) {
        //Se salva temporalmente la informacion sobre el folio. Esto no hace persistente el folio, ni cumple con el requerimiento de grabar de manera temporal
        eliminarInfoBasicaNuevoFolio(request);
        HttpSession session = request.getSession();

        if ((folio != null) && (folio.getZonaRegistral() != null) && (folio.getZonaRegistral().getVereda() != null)) {
            Vereda vereda = folio.getZonaRegistral().getVereda();

            if ((vereda != null) && (vereda.getIdVereda() != null)) {
                session.setAttribute(CVereda.ID_VEREDA, vereda.getIdVereda());
            }

            if ((vereda != null) && (vereda.getNombre() != null)) {
                session.setAttribute(CVereda.NOMBRE_VEREDA, vereda.getNombre());
            }

            if (vereda.getMunicipio() != null) {
                Municipio municipio = vereda.getMunicipio();

                if ((municipio != null) && (municipio.getIdMunicipio() != null)) {
                    session.setAttribute(CMunicipio.ID_MUNICIPIO, municipio.getIdMunicipio());
                }

                if ((municipio != null) && (municipio.getNombre() != null)) {
                    session.setAttribute(CMunicipio.NOMBRE_MUNICIPIO, municipio.getNombre());
                }

                if (municipio.getDepartamento() != null) {
                    Departamento departamento = municipio.getDepartamento();

                    if ((departamento != null) && (departamento.getIdDepartamento() != null)) {
                        session.setAttribute(CDepartamento.ID_DEPARTAMENTO, departamento.getIdDepartamento());
                    }

                    if ((departamento != null) && (departamento.getNombre() != null)) {
                        session.setAttribute(CDepartamento.NOMBRE_DEPARTAMENTO, departamento.getNombre());
                    }
                }
            }
        }

        if ((folio != null) && (folio.getDocumento() != null)) {

            if (folio.getDocumento().getIdDocumento() != null) {
                session.setAttribute(CDocumento.ID_DOCUMENTO, folio.getDocumento().getIdDocumento());
            }

            if (folio.getDocumento().getTipoDocumento() != null) {
                session.setAttribute(CDocumento.ID_TIPO_DOCUMENTO, folio.getDocumento().getTipoDocumento().getIdTipoDocumento());
                session.setAttribute(CDocumento.TIPO_DOCUMENTO, folio.getDocumento().getTipoDocumento().getIdTipoDocumento());
            }

            if (folio.getDocumento().getNumero() != null) {
                session.setAttribute(CDocumento.NUM_DOCUMENTO, folio.getDocumento().getNumero());
            }

            if (folio.getDocumento().getFecha() != null) {
                Date date = folio.getDocumento().getFecha();
                String fechaApertura = "";
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                if (date != null) {
                    fechaApertura = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
                }
                session.setAttribute(CDocumento.FECHA_RADICACION, fechaApertura);
            }

            if (folio.getDocumento().getOficinaOrigen() != null) {
                session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA, folio.getDocumento().getOficinaOrigen().getIdOficinaOrigen());
                session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM, folio.getDocumento().getOficinaOrigen().getNombre());
                if (folio.getDocumento().getOficinaOrigen().getTipoOficina() != null) {
                    session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO, folio.getDocumento().getOficinaOrigen().getTipoOficina().getNombre());
                }

            }

            if (folio.getDocumento().getOficinaOrigen() != null && folio.getDocumento().getOficinaOrigen().getVereda() != null) {

                Vereda vereda = folio.getDocumento().getOficinaOrigen().getVereda();

                if ((vereda != null) && (vereda.getIdVereda() != null)) {
                    session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA, vereda.getIdVereda());
                }

                if ((vereda != null) && (vereda.getNombre() != null)) {
                    session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA, vereda.getNombre());
                }

                if (vereda.getMunicipio() != null) {
                    Municipio municipio = vereda.getMunicipio();

                    if ((municipio != null) && (municipio.getIdMunicipio() != null)) {
                        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC, municipio.getIdMunicipio());
                    }

                    if ((municipio != null) && (municipio.getNombre() != null)) {
                        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC, municipio.getNombre());
                    }

                    if (municipio.getDepartamento() != null) {
                        Departamento departamento = municipio.getDepartamento();

                        if ((departamento != null) && (departamento.getIdDepartamento() != null)) {
                            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO, departamento.getIdDepartamento());
                        }

                        if ((departamento != null) && (departamento.getNombre() != null)) {
                            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO, departamento.getNombre());
                        }
                    }
                }
            }

        }

        if ((folio != null) && (folio.getIdMatricula() != null)) {
            session.setAttribute(CFolio.ID_MATRICULA, folio.getIdMatricula());
        }

        if ((folio != null) && (folio.getFechaApertura() != null)) {
            Date date = folio.getFechaApertura();
            String fechaApertura = "";
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            if (date != null) {
                fechaApertura = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
            }

            session.setAttribute(CFolio.FOLIO_FECHA_APERTURA, fechaApertura);
        }

        if ((folio != null) && (folio.getTipoPredio() != null) && (folio.getTipoPredio().getIdPredio() != null)) {
            session.setAttribute(CFolio.FOLIO_TIPO_PREDIO, folio.getTipoPredio().getIdPredio());
        }

        if ((folio != null) && (folio.getComplementacion() != null) && (folio.getComplementacion().getComplementacion() != null)) {
            session.setAttribute(CFolio.FOLIO_COMPLEMENTACION, folio.getComplementacion().getComplementacion());
        }

        if ((folio != null) && (folio.getLindero() != null)) {
            session.setAttribute(CFolio.FOLIO_LINDERO, folio.getLindero());
        }

        if ((folio != null) && (folio.getComentario() != null)) {
            session.setAttribute(CFolio.FOLIO_COMENTARIO, folio.getComentario());
        }

        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);

        if ((folio != null) && (folio.getCodCatastral() != null)) {
            session.setAttribute(CFolio.FOLIO_COD_CATASTRAL, folio.getCodCatastral());
        }

        if ((folio != null) && (folio.getTipoPredio() != null) && (folio.getTipoPredio().getIdPredio() != null)) {
            session.setAttribute(CFolio.FOLIO_TIPO_PREDIO, folio.getTipoPredio().getIdPredio());
        }

        if ((folio != null) && (folio.getDirecciones() != null)) {
            List direcciones = folio.getDirecciones();

            if (direcciones.size() > 0) {
                Direccion direccion = (Direccion) direcciones.get(direcciones.size() - 1);

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
                    session.setAttribute(CFolio.FOLIO_COMPLEMENTO_DIR, direccion.getEspecificacion());
                }
            }
        }

        if ((folio != null) && (folio.getEstado() != null)) {
            EstadoFolio estadoFolio = folio.getEstado();

            if ((estadoFolio != null) && (estadoFolio.getIdEstado() != null)) {
                session.setAttribute(CFolio.FOLIO_ESTADO_FOLIO, estadoFolio.getIdEstado());
            }
        }
    }

    /**
     * Permite agregar una nueva dirección al folio objeto de corrección.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnSegregacion agregarDireccion(HttpServletRequest request) throws AccionWebException {
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
        String especificacion = request.getParameter(AWModificarFolio.FOLIO_ESPECIFICACION_DIR);

        if ((valorEje1.length() <= 0) || valorEje1.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar el primer eje válido para la direccion");
        } else {

            if (!valorEje1.equals(CDireccion.SIN_DIRECCION)) {

                if (valorValor1.length() <= 0) {
                    exception.addError("Debe ingresar valor válido para el primer eje de la direccion");
                }

                if ((valorEje2.length() <= 0) || valorEje2.equals(WebKeys.SIN_SELECCIONAR)) {
                    valorEje2 = new String();
                } else {
                    if (valorValor2.length() <= 0) {
                        exception.addError("Debe ingresar valor válido para el segundo eje  de la direccion");
                    }
                }

            }

        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        List ejes = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
        Direccion direccion = new Direccion();
        direccion.setEspecificacion(complemento);
        direccion.setEspecificacion((especificacion + " " + complemento).toUpperCase());

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

        request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);
        return null;
    }

    /**
     * Permite eliminar una dirección de un folio determinado.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnSegregacion eliminarDireccion(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);

        List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if (direcciones == null) {
            direcciones = new Vector();
        }

        try {
            int aplicacionNumero = Integer.parseInt(request.getParameter(WebKeys.POSICION));
            direcciones.remove(aplicacionNumero);
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El número del documento a eliminar es inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (direcciones.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacía");
            } else {
                throw new ParametroInvalidoException("El índice del documento a eliminar está fuera del rango");
            }
        }

        request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);

        return null;
    }

//    private void eliminarInfoBasicaSegregacion(HttpServletRequest request){
//        HttpSession session = request.getSession();
//        session.removeAttribute(CFolioDerivado.NOMBRE_LOTE);
//        session.removeAttribute(CFolioDerivado.HECTAREAS);
//        session.removeAttribute(CFolioDerivado.METROS);
//        session.removeAttribute(CFolioDerivado.CENTIMETROS);
//        session.removeAttribute(CFolioDerivado.PORCENTAJE);
//        session.removeAttribute(CFolio.PRIVMETROS);
//        session.removeAttribute(CFolio.PRIVCENTIMETROS);
//        session.removeAttribute(CFolio.CONSMETROS);
//        session.removeAttribute(CFolio.CONSCENTIMETROS);
//        session.removeAttribute(CFolio.DETERMINACION_INMUEBLE);
//    }
    /**
     * Elimina de la sesión la información de un folio.
     */
    private void eliminarInfoBasicaNuevoFolio(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(CFolio.ID_MATRICULA);
        session.removeAttribute(CFolio.FOLIO_COD_CATASTRAL);
        session.removeAttribute(CFolio.FOLIO_LOCACION_ID_DEPTO);
        session.removeAttribute(CFolio.FOLIO_LOCACION_NOM_DEPTO);
        session.removeAttribute(CFolio.FOLIO_LOCACION_ID_MUNIC);
        session.removeAttribute(CFolio.FOLIO_LOCACION_NOM_MUNIC);
        session.removeAttribute(CFolio.FOLIO_LOCACION_ID_VEREDA);
        session.removeAttribute(CFolio.FOLIO_LOCACION_NOM_VEREDA);
        session.removeAttribute(CFolio.FOLIO_TIPO_PREDIO);
        session.removeAttribute(CFolio.FOLIO_COMPLEMENTACION);
        session.removeAttribute(CFolio.FOLIO_LINDERO);
        session.removeAttribute(CFolio.FOLIO_COMENTARIO);
        session.removeAttribute(CFolio.FOLIO_EJE1);
        session.removeAttribute(CFolio.FOLIO_VALOR1);
        session.removeAttribute(CFolio.FOLIO_EJE2);
        session.removeAttribute(CFolio.FOLIO_VALOR2);
        session.removeAttribute(CFolio.FOLIO_COMPLEMENTO_DIR);
        session.removeAttribute(CFolio.FOLIO_COD_DOCUMENTO);
        session.removeAttribute(CFolio.FOLIO_FECHA_APERTURA);
        session.removeAttribute(CFolio.FOLIO_TIPO_PREDIO);
        session.removeAttribute(CFolio.FOLIO_ESTADO_FOLIO);
        session.removeAttribute(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO);
    }

    /**
     * Hace una copia de un objeto, para evitar referencias de apuntadores en
     * memoria.
     *
     * @param orig
     * @return
     *
     */
    private static Object copy(Object orig) {
        Object obj = null;

        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(orig);
            out.flush();
            out.close();

            // Make an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            obj = in.readObject();
        } catch (IOException e) {
            Log.getInstance().error(AWSegregacion.class, e);
        } catch (ClassNotFoundException cnfe) {
            Log.getInstance().error(AWSegregacion.class, cnfe);
        }

        return obj;
    }

    //OPCIONES GENERICAS DE CADA PASO. COMO CANCELAR Y ELIMINAR SEGREGACION
    //OPCIONES DE REDIRECCCIÓN DEL BOTON CANCELAR
    private Evento cancelarSegregacion(HttpServletRequest request) throws AccionWebException {
        eliminarInfoBasicaVariosCiudadanos(request);
        return doProcess_OpcionSegregacionPasoXCancelar(request);
    } // end-method: doProcess_OpcionSegregacionPaso1Cancelar

    //OPCIONES PARA ELIMINAR UN ENGLOBE EN CURSO.
    private Evento eliminarSegregacion(HttpServletRequest request) throws AccionWebException {

        eliminarInfoBasicaVariosCiudadanos(request);

        Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        WebSegregacion webSegregacion = (WebSegregacion) request.getSession().getAttribute(WebKeys.WEB_SEGREGACION);
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);

        return new EvnSegregacion(usuarioAuriga, folio, turno, webSegregacion, EvnSegregacion.ELIMINAR_SEGREGACION, usuario);
    }

    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento doProcess_OpcionSegregacionPasoXCancelar(HttpServletRequest request) throws AccionWebException {

        // the session
        HttpSession session;
        session = request.getSession();

        // the exception collector
        ValidacionParametrosEditarFolioException exception = new ValidacionParametrosEditarFolioException();

        WebSegregacion local_Segregacion;
        local_Segregacion = (WebSegregacion) session.getAttribute(WebKeys.WEB_SEGREGACION);

        // acciones segun el proceso
        // acciones segun atributos en sesion
        if (null != local_Segregacion) {
            int procesoCorrecciones = new Integer(CProceso.PROCESO_CORRECCIONES).intValue();
            if (local_Segregacion.getIdProceso() == procesoCorrecciones) {

                doProcess_OpcionSegregacionPasoX_RemoveState(request);

            } // if

        } // if

        return null;
    } // end-method: doProcess_OpcionSegregacionPasoXCancelar

    /**
     * @param request
     */
    private void doProcess_OpcionSegregacionPasoX_RemoveState(HttpServletRequest request) {

        HttpSession session;
        session = request.getSession();

        String[] itemIds;

        itemIds = new String[]{ // TODO: objetos a remover de sesion
        };

        delete_PageItemsState(itemIds, request, session);

    } // end-method: doProcess_OpcionSegregacionPaso_RemoveState

    /**
     * @param itemIds
     * @param request
     * @param session
     */
    private void delete_PageItemsState(String[] itemIds, HttpServletRequest request, HttpSession session) {
        if (null == itemIds) {
            return;
        }

        for (int i = 0; i < itemIds.length; i++) {

            if (null == itemIds[i]) {
                continue;
            }
            if ("".equals(itemIds[i])) {
                continue;
            }

            delete_PageItemState_Simple(itemIds[i], request, session);
        } // :for
    } // :delete_PageItemsState

    /**
     * @param itemId
     * @param request
     * @param session
     */
    private void delete_PageItemState_Simple(String itemId, HttpServletRequest request, HttpSession session) {
        session.removeAttribute(itemId);
    } // end-method: delete_PageItemState_Simple	

    /**
     * @see
     * org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest,
     * org.auriga.smart.eventos.EventoRespuesta)
     */
    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {

        if (evento instanceof EvnRespSegregacion) {
            EvnRespSegregacion respuesta = (EvnRespSegregacion) evento;

            if (respuesta.getTipoEvento().equals(EvnRespSegregacion.CONSULTA_FOLIO)) {

                //SE RECUPERA EL FOLIO QUE SE CONSULTO.
                Folio padre = (Folio) respuesta.getPayload();
                List anotaciones = new ArrayList();

                if (padre != null) {
                    anotaciones.addAll(padre.getAnotaciones());
                }

                if (respuesta.getWebSegEng() != null) {
                    request.getSession().setAttribute(WebKeys.WEB_SEGREGACION, respuesta.getWebSegEng());
                }

                request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, anotaciones);

                LLavesMostrarFolioHelper llaves = (LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
                if (llaves != null) {
                    llaves.removeLLaves(request);
                }
                request.getSession().removeAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);

            } else if (respuesta.getTipoEvento().equals(EvnRespSegregacion.GUARDAR_WEB_SEGREGACION)) {

                if (respuesta.getWebSegEng() != null) {
                    request.getSession().setAttribute(WebKeys.WEB_SEGREGACION, respuesta.getWebSegEng());
                }

            } else if (respuesta.getTipoEvento().equals(EvnRespSegregacion.CONSULTA_ANOTACIONES_TEMPORALES)) {

                //SE RECUPERA EL FOLIO QUE SE CONSULTO.
                List anotacionTMP = (List) respuesta.getPayload();

                if (anotacionTMP == null) {
                    anotacionTMP = new ArrayList();
                }

                request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO, anotacionTMP);
            } else if (respuesta.getTipoEvento().equals(EvnRespSegregacion.SEGREGACION_FOLIO)) {

                HttpSession session = request.getSession();
                /*
                 * @author      :   Julio Alcázar Rivas
                 * @change      :   Eliminar los atributos de salvedad de la session
                 * Caso Mantis  :   04131
                 */
                session.removeAttribute(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION);
                session.removeAttribute(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID);
                session.removeAttribute(AWModificarFolio.VISTA_SEGREGACION_ANOTACION_DEFINITIVA);
                request.getSession().removeAttribute(AWModificarFolio.MODIFICA_DEFINITIVA);

                //SE RECUPERA EL FOLIO QUE SE CONSULTO.
                List foliosDerivados = (List) respuesta.getPayload();
                if (foliosDerivados == null) {
                    foliosDerivados = new ArrayList();
                }

                session.setAttribute(WebKeys.LISTA_FOLIOS_DERIVADOS, foliosDerivados);

                WebSegregacion local_Segregacion;
                local_Segregacion = (WebSegregacion) session.getAttribute(WebKeys.WEB_SEGREGACION);

                if (null != local_Segregacion) {
                    int procesoCorrecciones = new Integer(CProceso.PROCESO_CORRECCIONES).intValue();
                    if (local_Segregacion.getIdProceso() == procesoCorrecciones) {
                        // --------------------------------------------------------------
                        // bug 3563
                        // reload data from turno
                        Turno local_Turno;
                        local_Turno = respuesta.getTurno();
                        session.setAttribute(WebKeys.TURNO, local_Turno);

                        // --------------------------------------------------------------
                        return;
                    } // if

                } // if

                request.getSession().setAttribute(WebKeys.TURNO, respuesta.getTurno());

            } else if (respuesta.getTipoEvento().equals(EvnRespSegregacion.CONSULTA_NUEVO_FOLIO)) {

                request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

                Folio folio = (Folio) respuesta.getPayload();
                request.getSession().setAttribute(WebKeys.FOLIO_EDITADO, folio);
                request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, new ArrayList());

                cargarInfoBasicaFolio(folio, request);

                Folio folioOriginal = new Folio();
                folioOriginal = (Folio) copy(folio);
                request.getSession().setAttribute(WebKeys.FOLIO_EDITADO, folioOriginal);

            } else if (respuesta.getTipoEvento().equals(EvnRespSegregacion.CONSULTAR_PROPIETARIOS_FOLIO)) {
                request.getSession().setAttribute(AWSegregacion.LISTA_PROPIETARIOS_FOLIO, respuesta.getPropietariosFolios());
            } else if (respuesta.getTipoEvento().equals(EvnRespSegregacion.VOLVER_AGREGAR_CIUDADANOS)) {
                request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, respuesta.getListaCompletaCiudadanos());
            }

        } else if (evento instanceof EvnRespCiudadano) {
            EvnRespCiudadano evn = (EvnRespCiudadano) evento;
            String ver = request.getParameter(CAnotacionCiudadano.SECUENCIA);

            if (evn.isCiudadanoEncontrado()) {
                request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA + ver, evn.getCiudadano().getApellido1());
                request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA + ver, evn.getCiudadano().getApellido2());
                request.getSession().setAttribute(CFolio.ANOTACION_NOMBRES_PERSONA + ver, evn.getCiudadano().getNombre());
                request.getSession().setAttribute(CFolio.ANOTACION_SEXO_PERSONA + ver, evn.getCiudadano().getSexo());
                if (evn.isMostrarCiudadano()) {
                    request.getSession().setAttribute(CFolio.CIUDADANO_EDITABLE + ver, new Boolean(true));
                } else {
                    request.getSession().removeAttribute(CFolio.CIUDADANO_EDITABLE + ver);
                }
            } else {
                request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA + ver);
                request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA + ver);
                request.getSession().removeAttribute(CFolio.ANOTACION_NOMBRES_PERSONA + ver);
                request.getSession().removeAttribute(CFolio.ANOTACION_SEXO_PERSONA + ver);
                request.getSession().removeAttribute(CFolio.CIUDADANO_EDITABLE + ver);
            }
        }
    }

    private int numeroRegistrosTablaAgregarCiudadanos(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer num = (Integer) session.getAttribute(NUM_REGISTROS_TABLA_CIUDADANOS);
        int numCiud;

        if (num == null) {
            numCiud = DEFAULT_NUM_CIUDADANOS_TABLA;
        } else {
            numCiud = num.intValue();
        }

        return numCiud;
    }

}

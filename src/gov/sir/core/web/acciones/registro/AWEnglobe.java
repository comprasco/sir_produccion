package gov.sir.core.web.acciones.registro;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import gov.sir.core.eventos.comun.EvnCiudadano;
import gov.sir.core.eventos.comun.EvnRespCiudadano;
import gov.sir.core.eventos.registro.EvnEnglobe;
import gov.sir.core.eventos.registro.EvnRespEnglobe;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Direccion;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.Eje;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.LLavesMostrarFolioHelper;
import gov.sir.core.negocio.modelo.NaturalezaJuridica;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.WebAnotaHereda;
import gov.sir.core.negocio.modelo.WebAnotacion;
import gov.sir.core.negocio.modelo.WebCiudadano;
import gov.sir.core.negocio.modelo.WebDireccion;
import gov.sir.core.negocio.modelo.WebDocumento;
import gov.sir.core.negocio.modelo.WebEnglobe;
import gov.sir.core.negocio.modelo.WebFolioHeredado;
import gov.sir.core.negocio.modelo.WebFolioNuevo;
import gov.sir.core.negocio.modelo.constantes.CAnotacion;
import gov.sir.core.negocio.modelo.constantes.CAnotacionCiudadano;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CDireccion;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CFolioDerivado;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CSalvedadAnotacion;
import gov.sir.core.negocio.modelo.constantes.CSalvedadFolio;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro;
import gov.sir.core.negocio.modelo.constantes.CTipoDocumento;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.correccion.AWModificarFolio;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionEnglobeAnotacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCancelacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.excepciones.WizardEnglobeException;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.MostrarFolioHelper;

import gov.sir.core.web.helpers.comun.ValidacionDatosOficinaOrigen;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicConditionalValidator;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicStringMaxLengthValidatorWrapper;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicStringMinLengthValidatorWrapper;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicStringNotNullOrEmptyValidatorWrapper;
import java.text.ParseException;

/**
 * @author dlopez Acción Web para el manejo de todo el Asistente que orienta al
 * usuario a través del proceso de englobe
 */
public class AWEnglobe extends SoporteAccionWeb {

    /**
     * Id de acción cuando se cancela en paso x del wizard
     */
    public static final String CANCELAR_ENGLOBE = "CANCELAR_ENGLOBE";

    /**
     * Id de acción cuando se desea eliminar un englobe en curso
     */
    public static final String ELIMINAR_ENGLOBE = "ELIMINAR_ENGLOBE";

    /**
     * Acción solicitada y recibida dentro del request.
     */
    private String accion;

    /* CONSTANTES HELPER DE VARIOS CIUDADANOS*/
    /**
     * HELPER DE VARIOS CIUDADANOS
     */
    public static final int DEFAULT_NUM_CIUDADANOS_TABLA = 2;

    /**
     * Constante que indica el número de registros de ciudadanos que tiene la
     * sesión*
     */
    public static final String NUM_REGISTROS_TABLA_CIUDADANOS = "NUM_REGISTROS_TABLA_CIUDADANOS";

    public static final String ELIMINAR_CIUDADANO_ANOTACION = "ELIMINAR_CIUDADANO_ANOTACION";

    /**
     * Constante que indica que se va a validar un usuario en la anotación
     */
    public static final String VALIDAR_CIUDADANO_ENGLOBE = "VALIDAR_CIUDADANO_ENGLOBE";

    //PASO1 - ESCOGER FOLIOS A ENGLOBAR
    /**
     * Acción que indica que se desean escoger los folios que van a ser
     * englobados.
     */
    public static final String ESCOGER_FOLIOS = "ESCOGER_FOLIOS";

    //PASO2 - CREAR O ESCOGER UNA ANOTACIÓN TEMPORAL
    //VARIABLES PARA MANEJAR EL HELPER DE CIUDADANOS
    /**
     * Constante que indica que se van a agregar varios ciudadanos a una
     * anotacion*
     */
    public static final String AGREGAR_VARIOS_CIUDADANOS_ENGLOBE = "AGREGAR_VARIOS_CIUDADANOS_ENGLOBE";

    /**
     * Constante que indica que se va a agregar un registro en la tabla de
     * agragar ciudadanos*
     */
    public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_ENGLOBE = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_ENGLOBE";

    /**
     * Constante que indica que se va a remover un registro en la tabla de
     * agragar ciudadanos*
     */
    public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS_ENGLOBE = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS_ENGLOBE";

    /**
     * Constante que indica que se va a remover un ciudadano de la anotacion
     */
    public static final String ELIMINAR_CIUDADANO_ANOTACION_ENGLOBE = "ELIMINAR_CIUDADANO_ANOTACION_ENGLOBE";

    /**
     * Constante para actualizar los datos de la anotación sobre la cuál se
     * desea realizar la segregación
     */
    public final static String REFRESCAR_ANOTACION_ENGLOBE = "REFRESCAR_ANOTACION_ENGLOBE";

    //CONSTANTES PARA LA CREACIÓN O SELECCIÓN DE UNA ANOTACIÓN TEMPORAL.
    /**
     * Constante para llamar a la acción que guarda una anotación en el objeto
     * Engloe
     */
    public final static String ENGLOBE_ANOTACION = "ENGLOBE_ANOTACION";

    /**
     * Constante para llamar a la acción que guarda una anotación vacia en el
     * objeto Enblobe. se usa cuando se desa escoger una anotación de la lista
     * de anotaciónes temporales que se han creado.
     */
    public final static String ENGLOBE_SIN_ANOTACION = "ENGLOBE_SIN_ANOTACION";

    /**
     * Acción que indica que se desea cargar las anotaciones temporales de un
     * folio.
     */
    public static final String RECARGAR_ANOTACIONES_TEMPORALES = "RECARGAR_ANOTACIONES_TEMPORALES";

    /**
     * Acción que indica que se desea escoger la anotación de englobe.
     */
    public static final String ESCOGER_ANOTACION = "ESCOGER_ANOTACION";

    //PASO3 - SELECCIONAR ANOTACIONES A HEREDAR
    /**
     * Acción que indica que se desea cargar las anotaciones temporales de un
     * folio.
     */
    public static final String RECARGAR_ANOTACIONES_DEFINITIVAS = "RECARGAR_ANOTACIONES_DEFINITIVAS";
    /**
     * Acción que indica que se desean guardar las anotaciones a heredar de un
     * folio.
     */
    public static final String GUARDAR_ANOTACIONES_HEREDADAS = "GUARDAR_ANOTACIONES_HEREDADAS";
    /**
     * Acción que indica que se desean heredar anotaciones.
     */
    public static final String HEREDAR_ANOTACION = "HEREDAR_ANOTACION";

    //PASO4 - SELECCIONAR FOLIO PARA SACAR LA UBICACIÓN GEOGRÁFICA
    /**
     * Acción que indica que se desea seleccionar ubicación geográfica.
     */
    public static final String UBICACION_GEOGRAFICA = "UBICACION_GEOGRAFICA";

    //PASO5 - CREAR INFORMACIÓN DEL NUEVO LOTE, EN EL NUEVO FOLIO
    /**
     * Acción que indica que se desean ingresar información del lote.
     */
    public static final String INFORMACION_LOTE = "INFORMACION_LOTE";
    /**
     * Acción que indica que se desea agregar una dirección.
     */
    public static final String AGREGAR_DIRECCION = "AGREGAR_DIRECCION";
    /**
     * Acción que indica que se desea eliminar una dirección.
     */
    public static final String ELIMINAR_DIRECCION = "ELIMINAR_DIRECCION";

    //PASO6 - ENGLOBAR
    /**
     * Acción que indica que se desea realizar el englobe con la información que
     * se ha recogido.
     */
    public static final String ENGLOBAR = "ENGLOBAR";

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
     * Crea una instancia de la Acción Web de englobe
     */
    public AWEnglobe() {
        super();
    }

    /**
     * Método que permite determinar la acción solicitada, y hacer el llamado
     * correspondiente, de acuerdo con la solicitud.
     */
    public Evento perform(HttpServletRequest request)
            throws AccionWebException {

        accion = request.getParameter(WebKeys.ACCION);

        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion valida");
        } //ESCOGER FOLIOS ENGLOBADOS
        else if (accion.equals(AWEnglobe.ESCOGER_FOLIOS)) {
            return escogerFoliosFuente(request);
        } //CREAR LA NUEVA ANOTACIÓN
        else if (accion.equals(REFRESCAR_ANOTACION_ENGLOBE)) {
            return refrescarAnotacion(request);
        } else if (accion.equals(AGREGAR_VARIOS_CIUDADANOS_ENGLOBE)) {
            return agregarVariosCiudadanos(request);
        } else if (accion.equals(VALIDAR_CIUDADANO_ENGLOBE)) {
            return validarCiudadano(request);
        } else if (accion.equals(AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_ENGLOBE)) {
            return aumentarNumeroVariosCiudadanos(request);
        } else if (accion.equals(REMOVER_1_REGISTRO_TABLA_CIUDADANOS_ENGLOBE)) {
            return disminuirNumeroVariosCiudadanos(request);
        } else if (accion.equals(ELIMINAR_CIUDADANO_ANOTACION_ENGLOBE)) {
            return eliminarCiudadano(request);
        } else if (accion.equals(ENGLOBE_ANOTACION)) {
            return crearAnotacion(request);
        } else if (accion.equals(ENGLOBE_SIN_ANOTACION)) {
            return crearAnotacionVacia(request);
        } //ESCOGER ANOTACION DE ENGLOBE.
        else if (accion.equals(AWEnglobe.RECARGAR_ANOTACIONES_TEMPORALES)) {
            return recargarAnotacionesTemporales(request);
        } else if (accion.equals(AWEnglobe.ESCOGER_ANOTACION)) {
            return escogerAnotacion(request);
        } //ESCOGER ANOTACIONES HEREDADAS
        else if (accion.equals(AWEnglobe.RECARGAR_ANOTACIONES_DEFINITIVAS)) {
            return recargarAnotacionesDefinitivas(request);
        } else if (accion.equals(AWEnglobe.GUARDAR_ANOTACIONES_HEREDADAS)) {
            return guardarAnotacionesHeredadas(request);
        } else if (accion.equals(AWEnglobe.HEREDAR_ANOTACION)) {
            return salirHeredarAnotacion(request);
        } //ESCOGER UBICACION GEOGRAFICA
        else if (accion.equals(AWEnglobe.UBICACION_GEOGRAFICA)) {
            return seleccionarFolioUbicacionGeografica(request);
        } //ESCOGER INFORMACION LOTE
        else if (accion.equals(AWEnglobe.INFORMACION_LOTE)) {
            return crearInformacionLote(request);
        } else if (accion.equals(AGREGAR_DIRECCION)) {
            return agregarDireccion(request);
        } else if (accion.equals(ELIMINAR_DIRECCION)) {
            return eliminarDireccion(request);
        } //ENGLOBAR LOS FOLIOS
        else if (accion.equals(AWEnglobe.ENGLOBAR)) {
            return englobarFolios(request);
        }

        //OPCIONES DE REDIRECCIÓN DE LOS BOTONES DE CANCELAR
        if (accion.equals(CANCELAR_ENGLOBE)) {
            return doProcess_OpcionEnglobePaso1Cancelar(request);
        }

        //OPCIONES DE ELIMINAR UN ENGLOBE EN CURSO
        if (accion.equals(ELIMINAR_ENGLOBE)) {
            return eliminarEnglobe(request);
        }

        if (accion.equals(GET_ULTIMOS_PROPIETARIOS)) {
            return consultarUltimosPropietarios(request);
        } else if (accion.equals(CANCELAR_PROPIETARIOS)) {
            return cancelarPropietario(request);
        } else if (accion.equals(GUARDAR_PROPIETARIOS)) {
            return guardarPropietario(request);
        } //Acción inválida
        else {
            throw new AccionInvalidaException("La acción " + accion + " no es válida.");
        }
    }

    //PASO 1
    /**
     * @param request
     * @return
     */
    private Evento escogerFoliosFuente(HttpServletRequest request) throws AccionWebException {

        WizardEnglobeException exception = new WizardEnglobeException();
        List foliosFuente = null;

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Solicitud solicitud = turno.getSolicitud();
        List solFolios = solicitud.getSolicitudFolios();

        WebEnglobe webEnglobe = (WebEnglobe) request.getSession().getAttribute(WebKeys.WEB_ENGLOBE);
        if (webEnglobe == null) {
            webEnglobe = new WebEnglobe();
            String idProc = "" + turno.getIdProceso();
            webEnglobe.setIdProceso(new Integer(idProc).intValue());
        }

        //LISTA CON LOS FOLIOS SELECCIONADOS HASTA EL MOMENTO PARA REALIZAR EL ENGLOBE.
        //SI EN LA LISTA NO ESTA ALGUNO DE LOS SELECCIONADOS SE AGREGA A DICHA LISTA
        //SI YA ESTA NO SE HACE NADA
        //Y SI SE DESELECCIONO SE QUITA DE DICHA LISTA
        List foliosActuales = webEnglobe.getFoliosHeredados();
        List idFoliosActuales = new ArrayList();
        Iterator itFoliosActuales = foliosActuales.iterator();
        while (itFoliosActuales.hasNext()) {
            WebFolioHeredado webFolioHeredado = (WebFolioHeredado) itFoliosActuales.next();
            idFoliosActuales.add(webFolioHeredado.getIdMatricula());
        }

        String[] matriculasImp = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);

        //SI EN LA LISTA NO ESTA ALGUNO DE LOS SELECCIONADOS SE AGREGA A DICHA LISTA
        //SI YA ESTA NO SE HACE NADA
        List idFoliosNuevos = new ArrayList();
        Iterator it = null;
        if (matriculasImp != null) {
            foliosFuente = new ArrayList();
            for (int i = 0; i < matriculasImp.length; i++) {

                String idSeleccionada = matriculasImp[i];
                idFoliosNuevos.add(idSeleccionada);
                if (!idFoliosActuales.contains(idSeleccionada)) {
                    it = solFolios.iterator();
                    while (it.hasNext()) {
                        SolicitudFolio solFolio = (SolicitudFolio) it.next();
                        if (solFolio.getIdMatricula().equals(matriculasImp[i])) {
                            Folio folio = new Folio();
                            folio.setIdMatricula(solFolio.getIdMatricula());
                            folio.setDefinitivo(solFolio.getFolio().isDefinitivo());
                            folio.setNombreLote(solFolio.getFolio().getNombreLote());
                            foliosFuente.add(folio);

                            WebFolioHeredado webFolio = new WebFolioHeredado();
                            webFolio.setIdMatricula(folio.getIdMatricula());
                            webFolio.setFechaCreacion(new Date());
                            webFolio.setIdWebSegeng(webEnglobe.getIdWebSegeng());
                            webFolio.setIdSolicitud(webEnglobe.getIdSolicitud());
                            webEnglobe.addFoliosHeredado(webFolio);

                            break;
                        }
                    }
                }
            }

        }

        //SI SE DESELECCIONO SE QUITA DE DICHA LISTA
        //SE CREA UNA LISTA CON LOS QUE DEBEN QUEDAR
        List foliosDefinitivos = new ArrayList();

        itFoliosActuales = webEnglobe.getFoliosHeredados().iterator();
        while (itFoliosActuales.hasNext()) {
            WebFolioHeredado webFolioHeredado = (WebFolioHeredado) itFoliosActuales.next();

            if (idFoliosNuevos.contains(webFolioHeredado.getIdMatricula())) {
                foliosDefinitivos.add(webFolioHeredado);
            }

        }

        //SE BORRAN TODOS LOS FOLIOS HEREDADOS
        while (webEnglobe.getFoliosHeredados().size() > 0) {

            List foliosHeredados = webEnglobe.getFoliosHeredados();

            for (int i = 0; i < foliosHeredados.size(); i++) {
                WebFolioHeredado foliosHeredado = (WebFolioHeredado) foliosHeredados.get(0);
                webEnglobe.removeFoliosHeredado(foliosHeredado);
            }

        }

        //SE COLOCAN LOS DE LA LISTA QUE DEBIAN QUEDAR
        itFoliosActuales = foliosDefinitivos.iterator();
        while (itFoliosActuales.hasNext()) {
            WebFolioHeredado webFolioHeredado = (WebFolioHeredado) itFoliosActuales.next();
            webEnglobe.addFoliosHeredado(webFolioHeredado);
        }

        if (webEnglobe == null || webEnglobe.getFoliosHeredados().size() == 0
                || webEnglobe.getFoliosHeredados().size() < 2) {
            exception.addError("Debe seleccionar por lo menos dos folios para crear un englobe");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        request.getSession().setAttribute(WebKeys.WEB_ENGLOBE, webEnglobe);

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        return new EvnEnglobe(usuarioAuriga, EvnEnglobe.SELECCIONAR_FOLIOS, turno, webEnglobe, usuarioSIR);
    }

    //PASO2
    /**
     * @param request
     * @return
     */
    private Evento refrescarAnotacion(HttpServletRequest request) {
        String ver = request.getParameter("SECUENCIA");
        request.getSession().setAttribute("SECUENCIA", ver);
        request.getSession().setAttribute("CAMBIO", "SI");
        /*
               *  @author Edgar Lora
               *  @mantis 0013414: Acta - Requerimiento No 020_589_Código_Notaria_NC
         */
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaDocumento(request);
        preservarInfoEnglobe(request);
        preservarInfoBasicaVariosCiudadanos(request);
        return null;
    }

    /**
     *
     *
     */
    private Evento agregarVariosCiudadanos(HttpServletRequest request) throws AccionWebException {

        ValidacionParametrosException exception = new ValidacionParametrosException();

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

                String sexo = request.getParameter(CFolio.ANOTACION_SEXO_PERSONA + i);

                if (!tipoPersona.equals(CCiudadano.TIPO_PERSONA_JURIDICA)) {
                    if ((sexo == null) || sexo.equals(WebKeys.SIN_SELECCIONAR)) {
                        exception.addError("Debe escojer un tipo de sexo para la persona en la anotacion");
                        datosBien = false;
                    }
                }

                String numId = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA + i);
                String apellido1 = request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA + i);
                String apellido2 = request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA + i);
                String nombres = request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA + i);
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
                            /*
                                            * @author : CTORRES
                                            * @change : Se implemento la validación para solo permitir caracteres alfanuméricos en 
                                            *           numId cuando el tipo de identificación es PASAPORTE.
                                            * Caso Mantis : 11056
                                            * No Requerimiento: 073_151
                             */
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
                    ciudadano.setSexo(sexo);

                    if (numId != null) {
                        ciudadano.setDocumento(numId);
                    }

                    //Se setea el circulo del ciudadano
                    Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
                    ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

                    ciudadano.setTipoDoc(tipoId);
                    ciudadano.setTipoPersona(tipoPersona);
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
            preservarInfoBasicaVariosCiudadanos(request);
            throw exception;
        }

        eliminarInfoBasicaVariosCiudadanos(request);

        List listaCompletaCiudadanos = new Vector();

        listaCompletaCiudadanos.addAll(ciudadanosFinales);

        //request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, listaCompletaCiudadanos);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnEnglobe evento = new EvnEnglobe(usuarioAuriga, EvnEnglobe.AGREGAR_CIUDADANO_ANOTACION);
        evento.setAnotacionCiudadanos(ciudadanosFinales);
        evento.setListaCompletaCiudadanos(listaCompletaCiudadanos);
        evento.setTurno(turno);
        return evento;
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

        ValidacionParametrosException exception = new ValidacionParametrosException();

        try {
            int aplicacionNumero = Integer.parseInt(request.getParameter(AWCalificacion.POSICION));
            ciudadanos.remove(aplicacionNumero);
        } catch (NumberFormatException e) {
            exception.addError("El número del documento a eliminar es inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (ciudadanos.size() == 0) {
                exception.addError("La lista es vacía");
            } else {
                exception.addError("El índice del documento a eliminar está fuera del rango");
            }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, ciudadanos);
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
            request.getSession().removeAttribute(CFolio.ANOTACION_NUM_ID_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_SEXO_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_COD_VERIFICACION + i);
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
        if (request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NUM_ID_PERSONA, request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA));
        }
        if (request.getParameter(CFolio.ANOTACION_TIPO_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_PERSONA, request.getParameter(CFolio.ANOTACION_TIPO_PERSONA));
        }
        if (request.getParameter(CFolio.ANOTACION_COD_VERIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_COD_VERIFICACION, request.getParameter(CFolio.ANOTACION_COD_VERIFICACION));
        }
        if (request.getParameter(CFolio.ANOTACION_SEXO_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_SEXO_PERSONA, request.getParameter(CFolio.ANOTACION_SEXO_PERSONA));
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

        if (request.getParameter(CFolio.ANOTACION_ID_ANOTACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_ID_ANOTACION, request.getParameter(CFolio.ANOTACION_ID_ANOTACION));
        }

        if (request.getParameter(CFolio.ANOTACION_MODALIDAD) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_MODALIDAD, request.getParameter(CFolio.ANOTACION_MODALIDAD));
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
        if (request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO, request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO));
        }

        if (request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION, request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION));
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
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID));
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM));
        }

        if (request.getParameter(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION) != null) {
            request.getSession().setAttribute(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION, request.getParameter(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION));
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

        if (request.getParameter(CFolio.FOLIO_COMPLEMENTACION) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_COMPLEMENTACION, request.getParameter(CFolio.FOLIO_COMPLEMENTACION));
        }

        if (request.getParameter(CFolio.FOLIO_LINDERO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LINDERO, request.getParameter(CFolio.FOLIO_LINDERO));
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
        request.getSession().removeAttribute(CFolio.ANOTACION_NUM_DOCUMENTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_MODALIDAD);
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
    private Evento crearAnotacion(HttpServletRequest request) throws ValidacionParametrosException {

        HttpSession session = request.getSession();

        WebEnglobe webEnglobe = (WebEnglobe) request.getSession().getAttribute(WebKeys.WEB_ENGLOBE);
        if (webEnglobe == null) {
            webEnglobe = new WebEnglobe();
        }
        Date fechaAnotacion = null;

        String sIdDocumento = (String) session.getAttribute(CFolio.ANOTACION_ID_DOCUMENTO);
        String sIdNatJur = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change :
         */
        String sVersionNatJuridica = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);
        String sNomNatJur = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        String sComentario = request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        String sValor = request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        String copiarComentarioEnglobadas = request.getParameter(CAnotacion.GUARDAR_COMENTARIO_ANOTACION);
        //TFS 3576: Se debe digitar el numero de radicación y la fecha de anotacion 
        String sNumeroRadicacion = request.getParameter(CSolicitudRegistro.NUMERO_RADICACION);
        String sFechaAnotacion = request.getParameter(CSolicitudRegistro.CALENDAR2);
        String sModalidad = request.getParameter(CFolio.ANOTACION_MODALIDAD);

        if (sValor != null) {
            sValor = sValor.replaceAll(",", "");
        }

        session.setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, sIdNatJur);
        session.setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, sNomNatJur);
        session.setAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION, sComentario);
        session.setAttribute(CAnotacion.GUARDAR_COMENTARIO_ANOTACION, copiarComentarioEnglobadas);
        session.setAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION, sValor);
        session.setAttribute(CSolicitudRegistro.NUMERO_RADICACION, sNumeroRadicacion);
        session.setAttribute(CSolicitudRegistro.CALENDAR2, sFechaAnotacion);
        session.setAttribute(CFolio.ANOTACION_MODALIDAD, sModalidad);

        Turno turno;
        Folio folio;

        turno = (Turno) session.getAttribute(WebKeys.TURNO);
        // folio se resuelve mas adelante

        // localData: turno.solicitud
        Solicitud local_Solicitud;
        local_Solicitud = turno.getSolicitud();

        //VALIDACIONES
        ValidacionEnglobeAnotacionException exception = new ValidacionEnglobeAnotacionException();

        boolean local_ProcesoRegistroFlag = (local_Solicitud instanceof SolicitudRegistro);

        if (local_ProcesoRegistroFlag) {
            folio = new Folio();
        } else {
            folio = (Folio) session.getAttribute(WebKeys.FOLIO);
        }

        //NULOS
        if (local_ProcesoRegistroFlag && (sIdDocumento == null || sIdDocumento.equals(""))) {
            exception.addError("Documento inválido");
        }
        if (sIdNatJur == null || sIdNatJur.equals("")) {
            exception.addError("Naturaleza jurídica inválida");
        }

        if (sIdNatJur.equals("0125") || sIdNatJur.equals("0126")) {
            if (sModalidad == null || sModalidad.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe seleccionar una modalidad para los códigos de naturaleza juridica 0125 y 0126 en la anotación");

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

        Documento local_Documento = null;

        // datos de documento para la anotacion
        if (local_Solicitud instanceof SolicitudRegistro) {
            // nothing
        } else if (local_Solicitud instanceof SolicitudCorreccion) {
            local_Documento = crearDocumento(request, exception, true);

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
            if (copiarComentarioEnglobadas.equals(CAnotacion.SI_GUARDAR_COMENTARIO_ANOTACION)) {
                webAnotacion.setCopiaComentario(1);
            } else {
                webAnotacion.setCopiaComentario(0);
            }
            webAnotacion.setIdWebSegeng(webEnglobe.getIdWebSegeng());
            webAnotacion.setIdSolicitud(webEnglobe.getIdSolicitud());

            //OBJETO ANOTACION
            Anotacion anotacion = new Anotacion();
            anotacion.setDocumento(documento);
            anotacion.setNaturalezaJuridica(natJur);
            anotacion.setComentario(sComentario);
            anotacion.setValor(valor);
            anotacion.setModalidad(sModalidad);
            anotacion.setTemporal(true);

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

                    webDocumento.setIdSolicitud(webEnglobe.getIdSolicitud());
                    webDocumento.setIdWebSegeng(webEnglobe.getIdWebSegeng());
                    webAnotacion.setDocumento(webDocumento);
                    if (local_Documento.getIdDocumento() != null) {
                        webAnotacion.setIdDocumento(local_Documento.getIdDocumento());
                    }
                }

            }

            if (turno != null) {
                //TFS 3576: El número de radicación y la fecha de la anotación
                //para las correcciones, serán digitadas por el usuario
                if (local_Solicitud instanceof SolicitudCorreccion) {
                    webAnotacion.setNumeroRadicacion(sNumeroRadicacion);
                    webAnotacion.setFechaRadicacion(fechaAnotacion);
                    anotacion.setIdWorkflow(turno.getIdWorkflow());
                } else {
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
                webCiudadano.setIdSolicitud(webEnglobe.getIdSolicitud());
                webCiudadano.setIdWebSegeng(webEnglobe.getIdWebSegeng());
                webAnotacion.addCiudadano(webCiudadano);
            }

            webEnglobe.setAnotacion(webAnotacion);

            session.removeAttribute(CAnotacion.GUARDAR_COMENTARIO_ANOTACION);
        } else {
            throw exception;
        }

        webEnglobe.getFoliosHeredados();
        //List foliosPadre = englobe.getFoliosPadre();
        if (webEnglobe != null && webEnglobe.getFoliosHeredados().size() > 0) {
            WebFolioHeredado folioHeredado = (WebFolioHeredado) webEnglobe.getFoliosHeredados().get(0);
            if (folio == null) {
                folio = new Folio();
            }
            folio.setIdMatricula(folioHeredado.getIdMatricula());
        }
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        return new EvnEnglobe(usuarioAuriga, folio, webEnglobe, EvnEnglobe.CONSULTA_FOLIO, usuarioNeg);
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
        /**
         * @author Carlos Torres
         * @chage se agrega validacion de version diferente
         * @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        String oficinaVersion = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);

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
                oficinaOrigen.setVersion(Long.parseLong(oficinaVersion));
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
               *  @author Edgar Lora
               *  @mantis 0013414: Acta - Requerimiento No 020_589_Código_Notaria_NC
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

        WebEnglobe webEnglobe = (WebEnglobe) request.getSession().getAttribute(WebKeys.WEB_ENGLOBE);
        if (webEnglobe == null) {
            webEnglobe = new WebEnglobe();
        }

        Folio folio = new Folio();
        List foliosPadre = webEnglobe.getFoliosHeredados();
        if (foliosPadre != null && foliosPadre.size() > 0) {
            WebFolioHeredado folioHeredado = (WebFolioHeredado) foliosPadre.get(0);
            folio.setIdMatricula(folioHeredado.getIdMatricula());
        }

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        return new EvnEnglobe(usuarioAuriga, folio, webEnglobe, EvnEnglobe.CONSULTA_ANOTACIONES_TEMPORALES, usuarioNeg);
    }

    /**
     * @param request
     * @return
     */
    private Evento recargarAnotacionesTemporales(HttpServletRequest request) throws ValidacionParametrosException {

        WebEnglobe webEnglobe = (WebEnglobe) request.getSession().getAttribute(WebKeys.WEB_ENGLOBE);
        if (webEnglobe == null) {
            webEnglobe = new WebEnglobe();
        }

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        String matricula = request.getParameter(CFolio.ID_MATRICULA);

        if (matricula == null) {
            WizardEnglobeException exception = new WizardEnglobeException();
            exception.addError("Debe seleccionar el folio al que desea consultarle las anotaciones temporales");
            throw exception;
        }

        Folio folio = new Folio();
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Solicitud solicitud = turno.getSolicitud();
        List solFolios = solicitud.getSolicitudFolios();
        Iterator it = solFolios.iterator();

        while (it.hasNext()) {
            SolicitudFolio solFolio = (SolicitudFolio) it.next();
            if (solFolio.getIdMatricula().equals(matricula)) {
                folio.setIdMatricula(solFolio.getIdMatricula());
                break;
            }
        }

        return new EvnEnglobe(usuarioAuriga, folio, webEnglobe, EvnEnglobe.CONSULTA_ANOTACIONES_TEMPORALES, usuarioNeg);
    }

    /**
     * @param request
     * @return
     */
    private Evento escogerAnotacion(HttpServletRequest request) throws ValidacionParametrosException {

        WebEnglobe webEnglobe = (WebEnglobe) request.getSession().getAttribute(WebKeys.WEB_ENGLOBE);
        if (webEnglobe == null) {
            webEnglobe = new WebEnglobe();
        }

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        Anotacion anotacionEscogida = new Anotacion();
        Anotacion anotacion = null;
        String[] anota = request.getParameterValues("Anotaciones");
        List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO);
        Iterator it = anotaciones.iterator();
        WizardEnglobeException ex = new WizardEnglobeException();

        int seleccionadas = 0;
        if (anota != null) {
            while (it.hasNext()) {
                anotacion = (Anotacion) it.next();
                for (int i = 0; i < anota.length; i++) {
                    String an = anota[i];
                    if (!an.equals("")) {
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
        }

        if (seleccionadas > 1) {
            ex.addError("Seleccione solamente una anotación temporal.");
            throw ex;
        }

        if (seleccionadas == 0) {
            if (webEnglobe.getAnotacion() == null) {
                ex.addError("Debe Seleccionar una anotación temporal.");
                throw ex;
            }
        }

        if (seleccionadas == 1) {
            if (anotacionEscogida != null) {
                if (turno != null && turno.getSolicitud() != null) {
                    SolicitudRegistro sol = (SolicitudRegistro) turno.getSolicitud();
                    anotacionEscogida.setDocumento(sol.getDocumento());
                }
            }

        }

        Folio folio = new Folio();
        List foliosPadre = webEnglobe.getFoliosHeredados();
        if (foliosPadre != null && foliosPadre.size() > 0) {
            WebFolioHeredado folioHeredado = (WebFolioHeredado) foliosPadre.get(0);
            folio.setIdMatricula(folioHeredado.getIdMatricula());
        }

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        return new EvnEnglobe(usuarioAuriga, folio, webEnglobe, EvnEnglobe.CONSULTA_FOLIO, usuarioNeg);
    }

    //PASO 3
    /**
     * @param request
     * @return
     */
    private Evento recargarAnotacionesDefinitivas(HttpServletRequest request) throws ValidacionParametrosException {

        WebEnglobe webEnglobe = (WebEnglobe) request.getSession().getAttribute(WebKeys.WEB_ENGLOBE);
        if (webEnglobe == null) {
            webEnglobe = new WebEnglobe();
        }

        //SE GUARDA EN LA SESIÓN EL VALOR DE QUE SI SE SE DESEA COPIAR LA COMPLEMENTACIÓN DE LAS ANOTACIONES A HEREDAR
        request.getSession().setAttribute(CAnotacion.GUARDAR_COMENTARIO_ANOTACION, request.getParameter(CAnotacion.GUARDAR_COMENTARIO_ANOTACION));

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        String matricula = request.getParameter(CFolio.ID_MATRICULA);

        if (matricula == null) {
            WizardEnglobeException exception = new WizardEnglobeException();
            exception.addError("Debe seleccionar el folio al que desea consultarle las anotaciones a heredar");
            throw exception;
        }

        Folio folio = new Folio();
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Solicitud solicitud = turno.getSolicitud();
        List solFolios = solicitud.getSolicitudFolios();
        Iterator it = solFolios.iterator();

        while (it.hasNext()) {
            SolicitudFolio solFolio = (SolicitudFolio) it.next();
            if (solFolio.getIdMatricula().equals(matricula)) {
                folio.setIdMatricula(solFolio.getIdMatricula());
                break;
            }
        }

        // se elimina la selección de anotaciones del folio anterior
        Hashtable ht = (Hashtable) request.getSession().getAttribute("LISTA_ANOTACIONES_MULT_CANCELACIONXFOLIO");
        if (ht == null) {
            ht = new Hashtable();
            request.getSession().setAttribute("LISTA_ANOTACIONES_MULT_CANCELACIONXFOLIO", ht);
        }
        String anotsAntCvs = (String) request.getParameter("TARGET_SELECTIONCOLLECTOR");
        String[] anotsAnt = gov.sir.core.web.helpers.comun.JvLocalUtils.csvStringToStringArray(anotsAntCvs, gov.sir.core.web.helpers.comun.JvLocalUtils.DEFAULT_SEPARATOR, true);

        Folio folioAnt = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        if (folioAnt != null) {
            ht.put(folioAnt.getIdMatricula(), anotsAnt);
        }
        String[] anotsNuevo = null;
        anotsNuevo = (String[]) ht.get(folio.getIdMatricula());
        request.getSession().setAttribute("LISTA_ANOTACIONES_MULT_CANCELACION", anotsNuevo);
        return new EvnEnglobe(usuarioAuriga, folio, webEnglobe, EvnEnglobe.CONSULTA_FOLIO, usuarioNeg);
    }

    /**
     * @param request
     * @return
     */
    private Evento guardarAnotacionesHeredadas(HttpServletRequest request) throws ValidacionParametrosException {

        //SE GUARDA EN LA SESIÓN EL VALOR DE QUE SI SE SE DESEA COPIAR LA COMPLEMENTACIÓN DE LAS ANOTACIONES A HEREDAR
        request.getSession().setAttribute(CAnotacion.GUARDAR_COMENTARIO_ANOTACION, request.getParameter(CAnotacion.GUARDAR_COMENTARIO_ANOTACION));

        WebEnglobe webEnglobe = (WebEnglobe) request.getSession().getAttribute(WebKeys.WEB_ENGLOBE);
        if (webEnglobe == null) {
            webEnglobe = new WebEnglobe();
        }

        //SE OBTIENE EL FOLIO AL QUE SE LE DESEA GUARDAR LAS ANOTACIONES A HEREDAR
        String matricula = request.getParameter(CFolio.ID_MATRICULA);
        if (matricula == null || matricula.equals(WebKeys.SIN_SELECCIONAR)) {
            WizardEnglobeException exception = new WizardEnglobeException();
            exception.addError("Debe seleccionar el folio al que desea guardarle las anotaciones a heredar");
            throw exception;
        }

        WebFolioHeredado folioHeredado = null;
        if (webEnglobe.getFoliosHeredados().size() > 0) {

            List foliosHeredados = webEnglobe.getFoliosHeredados();

            for (int i = 0; i < foliosHeredados.size(); i++) {
                folioHeredado = (WebFolioHeredado) foliosHeredados.get(i);
                if (matricula.equals(folioHeredado.getIdMatricula())) {
                    break;
                }
                //webEnglobe.removeFoliosHeredado(foliosHeredado);
            }
        }

        WebFolioHeredado webFolio = new WebFolioHeredado();
        webFolio.setIdMatricula(folioHeredado.getIdMatricula());
        webFolio.setFechaCreacion(new Date());
        webFolio.setIdWebSegeng(webEnglobe.getIdWebSegeng());
        webFolio.setIdSolicitud(webEnglobe.getIdSolicitud());

        String copiarComentarioHeredadas = request.getParameter(CAnotacion.GUARDAR_COMENTARIO_ANOTACION);

        if (copiarComentarioHeredadas.equals(CAnotacion.SI_GUARDAR_COMENTARIO_ANOTACION)) {
            webFolio.setCopiaComentario(true);
        } else {
            webFolio.setCopiaComentario(false);
        }

        //SE OBTIENE LAS ANOTACIONES QUE SE LE DESEAN HEREDAR
        final String LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID = "TARGET_SELECTIONCOLLECTOR";

        String idsAnotacionesCsv = request.getParameter(LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID);

        // only the last is added
        String[] anota = gov.sir.core.web.helpers.comun.JvLocalUtils.csvStringToStringArray(idsAnotacionesCsv, gov.sir.core.web.helpers.comun.JvLocalUtils.DEFAULT_SEPARATOR, true);

        request.getSession().setAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION, anota);

        if (anota != null) {
            for (int i = 0; i < anota.length; i++) {
                String an = anota[i];
                if (!an.equals("")) {
                    WebAnotaHereda webAnotacionHereda = new WebAnotaHereda();
                    webAnotacionHereda.setIdWebSegeng(webEnglobe.getIdWebSegeng());
                    webAnotacionHereda.setIdSolicitud(webEnglobe.getIdSolicitud());
                    webAnotacionHereda.setIdAnotacion(an);
                    webAnotacionHereda.setFechaCreacion(new Date());
                    webFolio.addAnotacionesHeredada(webAnotacionHereda);
                }
            }
        }

        webEnglobe.addFoliosHeredado(webFolio);
        webEnglobe.removeFoliosHeredado(folioHeredado);

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        return new EvnEnglobe(usuarioAuriga, EvnEnglobe.GUARDAR_WEB_ENGLOBE, turno, webEnglobe, usuarioNeg);
    }

    /**
     * @param request
     * @return
     */
    private Evento salirHeredarAnotacion(HttpServletRequest request) throws ValidacionParametrosException {

        return this.guardarAnotacionesHeredadas(request);
    }

    //PASO 4
    /**
     * @param request
     * @return
     */
    private Evento seleccionarFolioUbicacionGeografica(HttpServletRequest request) throws AccionWebException {

        WebEnglobe webEnglobe = (WebEnglobe) request.getSession().getAttribute(WebKeys.WEB_ENGLOBE);
        if (webEnglobe == null) {
            webEnglobe = new WebEnglobe();
        }

        //SE RECUPERA INFORMACIÓN DEL FORMULARIO Y DE LA SESIÓN
        String matricula = request.getParameter(WebKeys.TITULO_CHECKBOX_ELIMINAR);

        if (matricula == null || matricula.equals("")) {
            if (webEnglobe.getIdMatriculaUbicacion() == null) {
                WizardEnglobeException exception = new WizardEnglobeException();
                exception.addError("Debe seleccionar un folio para determinar la ubicación geográfica del folio englobado");
                throw exception;
            }
        }

        //SE RECUPERA LA SOLICITUD-FOLIO CON ESE ID DE MATRÍCULA PARA CREAR UN FOLIO CON LOS ID'S DEL FOLIO COMPLETOS.
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Solicitud solicitud = turno.getSolicitud();
        List solFolios = solicitud.getSolicitudFolios();
        Iterator it = solFolios.iterator();

        while (it.hasNext()) {
            SolicitudFolio solFolio = (SolicitudFolio) it.next();
            if (solFolio.getIdMatricula().equals(matricula)) {
                webEnglobe.setIdMatriculaUbicacion(matricula);
            }

        }

        //SE GUARDA EL FOLIO DEL QUE SE DESEA COLOCAR LA INFORMACIÓN GEOGRÁFICA EN EN OBJETO ENGLOBE
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        request.getSession().setAttribute(WebKeys.WEB_ENGLOBE, webEnglobe);

        return new EvnEnglobe(usuarioAuriga, EvnEnglobe.GUARDAR_WEB_ENGLOBE, turno, webEnglobe, usuarioNeg);

    }

    //PASO 5 - INFORMACIÓN DEL LOTE.
    /**
     * @param request
     * @return
     */
    private Evento crearInformacionLote(HttpServletRequest request) throws AccionWebException {

        WizardEnglobeException exception = new WizardEnglobeException();
        HttpSession session = request.getSession();

        WebEnglobe webEnglobe = (WebEnglobe) request.getSession().getAttribute(WebKeys.WEB_ENGLOBE);
        if (webEnglobe == null) {
            webEnglobe = new WebEnglobe();
        }

        //SE RECUPERA Y VALIDA LOS DATOS PARA EL FOLIO DERIVADO
        String nombre = request.getParameter(CFolioDerivado.NOMBRE_NUMERO);
        String area = request.getParameter(CFolioDerivado.AREA);
        String hectareas = request.getParameter(CFolioDerivado.HECTAREAS);
        String metros = request.getParameter(CFolioDerivado.METROS);
        String centimetros = request.getParameter(CFolioDerivado.CENTIMETROS);
        String descripcion = request.getParameter(CFolioDerivado.DESCRIPCION);

        if (nombre == null || nombre.length() <= 0) {
            exception.addError("Debe ingresar nombre para el folio derivado");
        }

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
            exception.addError("Debe digitar por lo menos una unidad de medida para el folio derivado");
        }

        if (exception.getErrores().size() > 0) {
            session.setAttribute(CFolioDerivado.NOMBRE_NUMERO, nombre);
            session.setAttribute(CFolioDerivado.AREA, area);
            session.setAttribute(CFolioDerivado.HECTAREAS, hectareas);
            session.setAttribute(CFolioDerivado.METROS, metros);
            session.setAttribute(CFolioDerivado.CENTIMETROS, centimetros);
            session.setAttribute(CFolioDerivado.DESCRIPCION, descripcion);

            request.getSession().setAttribute(CFolio.FOLIO_EJE1, CFolio.FOLIO_EJE1);
            request.getSession().setAttribute(CFolio.FOLIO_EJE2, CFolio.FOLIO_EJE2);
            request.getSession().setAttribute(CFolio.FOLIO_VALOR1, CFolio.FOLIO_VALOR1);
            request.getSession().setAttribute(CFolio.FOLIO_VALOR2, CFolio.FOLIO_VALOR2);
            request.getSession().setAttribute(CFolio.FOLIO_COMPLEMENTO_DIR, CFolio.FOLIO_COMPLEMENTO_DIR);

            throw exception;
        }

        //SI SE PASARON LAS VALIDACIONES SE LLENA EL FOLIO DERIVADO
        WebFolioNuevo webFolioNuevo = new WebFolioNuevo();
        webFolioNuevo.setArea(area);
        webFolioNuevo.setHectareas(hectareas);
        webFolioNuevo.setMetros(metros);
        webFolioNuevo.setCentimetros(centimetros);
        webFolioNuevo.setNombre(nombre);
        webFolioNuevo.setDescripcion(descripcion);
        webFolioNuevo.setFechaCreacion(new Date());

        //SI SE PASARON LAS VALIDACIONES SE LE COLOCA LA DIRECCIÓN AL FOLIO SELECCIONADO EN EL PASO ANTERIOR
        //CON EL FIN DE GUARDAR EN EL NUEVO FOLIO ESTA DIRECCIÓN.
        List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if (direcciones != null && (direcciones.size() > 0)) {
            Iterator it = direcciones.iterator();
            while (it.hasNext()) {
                Direccion direccion = (Direccion) it.next();
                direccion.setIdDireccion(null);

                WebDireccion webDireccion = new WebDireccion();
                webDireccion.setIdEje1(direccion.getEje().getIdEje());
                webDireccion.setIdEje2(direccion.getEje1() != null ? direccion.getEje1().getIdEje() : null);
                webDireccion.setValorEje1(direccion.getValorEje());
                webDireccion.setValorEje2(direccion.getValorEje1());
                webDireccion.setEspecificacion(direccion.getEspecificacion());
                webFolioNuevo.addDireccione(webDireccion);
            }
        }

        webEnglobe.setFolioNuevo(webFolioNuevo);

        //SE GUARDA EL FOLIO DEL QUE SE DESEA COLOCAR LA INFORMACIÓN GEOGRÁFICA EN EN OBJETO ENGLOBE
        request.getSession().setAttribute(WebKeys.WEB_ENGLOBE, webEnglobe);

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        return new EvnEnglobe(usuarioAuriga, EvnEnglobe.GUARDAR_WEB_ENGLOBE, turno, webEnglobe, usuarioNeg);
    }

    //ACCIONES PARA GUARDAR EN LA SESIÓN LAS DIRECCIONES DEL FOLIO
    /**
     * @param request
     * @return
     */
    private Evento agregarDireccion(HttpServletRequest request) throws AccionWebException {
        preservarInfoEnglobe(request);
        List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if (direcciones == null) {
            direcciones = new Vector();
        }

        WizardEnglobeException exception = new WizardEnglobeException();

        String valorEje1 = request.getParameter(CFolio.FOLIO_EJE1);
        String valorValor1 = request.getParameter(CFolio.FOLIO_VALOR1);
        String valorEje2 = request.getParameter(CFolio.FOLIO_EJE2);
        String valorValor2 = request.getParameter(CFolio.FOLIO_VALOR2);
        String complemento = request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR);
        String especificacion = request.getParameter(AWModificarFolio.FOLIO_ESPECIFICACION_DIR);

        if ((valorEje1.length() <= 0) || valorEje1.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar el primer eje válido para la dirección");
        } else {
            if (!valorEje1.equals(CDireccion.SIN_DIRECCION)) {
                if (valorValor1.length() <= 0) {
                    exception.addError("Debe ingresar valor válido para el primer eje de la dirección");
                }

                if ((valorEje2.length() <= 0) || valorEje2.equals(WebKeys.SIN_SELECCIONAR)) {
                    valorEje2 = new String();
                } else {
                    if (valorValor2.length() <= 0) {
                        exception.addError("Debe ingresar valor válido para el segundo eje  de la dirección");
                    }
                }
            }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        List ejes = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
        Direccion direccion = new Direccion();
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
            exception.addError("La lista de los ejes no se encontró");
        }

        Integer idDir = (Integer) request.getSession().getAttribute(WebKeys.ID_DIRECCION);
        if (idDir == null) {
            idDir = new Integer(direcciones == null ? 1 : direcciones.size() + 1);
        }

        direccion.setIdDireccion(idDir.toString());
        Integer nextId = new Integer(idDir.intValue() + 1);
        request.getSession().setAttribute(WebKeys.ID_DIRECCION, nextId);

        direcciones.add(direccion);

        request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);
        eliminarInfoDireccion(request);

        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento eliminarDireccion(HttpServletRequest request) {
        preservarInfoEnglobe(request);
        List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if (direcciones == null) {
            direcciones = new Vector();
        }

        int aplicacionNumero = Integer.parseInt(request.getParameter(WebKeys.POSICION));

        direcciones.remove(aplicacionNumero);

        request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);

        return null;
    }

    /**
     * @param request
     */
    private void eliminarInfoDireccion(HttpServletRequest request) {
        request.getSession().removeAttribute(CFolio.FOLIO_EJE1);
        request.getSession().removeAttribute(CFolio.FOLIO_EJE2);
        request.getSession().removeAttribute(CFolio.FOLIO_VALOR1);
        request.getSession().removeAttribute(CFolio.FOLIO_VALOR2);
        request.getSession().removeAttribute(CFolio.FOLIO_COMPLEMENTO_DIR);
    }

    /**
     * @param request
     */
    private void preservarInfoEnglobe(HttpServletRequest request) {
        request.getSession().setAttribute(CFolioDerivado.NOMBRE_NUMERO, request.getParameter(CFolioDerivado.NOMBRE_NUMERO));
        request.getSession().setAttribute(CFolioDerivado.AREA, request.getParameter(CFolioDerivado.AREA));
        request.getSession().setAttribute(CFolioDerivado.HECTAREAS, request.getParameter(CFolioDerivado.HECTAREAS));
        request.getSession().setAttribute(CFolioDerivado.METROS, request.getParameter(CFolioDerivado.METROS));
        request.getSession().setAttribute(CFolioDerivado.CENTIMETROS, request.getParameter(CFolioDerivado.CENTIMETROS));
        request.getSession().setAttribute(CFolioDerivado.DESCRIPCION, request.getParameter(CFolioDerivado.DESCRIPCION));
        if (request.getParameter(CSolicitudRegistro.NUMERO_RADICACION) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.NUMERO_RADICACION, request.getParameter(CSolicitudRegistro.NUMERO_RADICACION));
        }

        if (request.getParameter(CSolicitudRegistro.CALENDAR2) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.CALENDAR2, request.getParameter(CSolicitudRegistro.CALENDAR2));
        }
    }

    //PASO 6
    /*
         * @author      :   Julio Alcázar Rivas
         * @change      :   Se modifica el metodo para que lance excepciones de tipo AccionWebException
         * Caso Mantis  :   04131
     */
    private Evento englobarFolios(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();

        /*
                 * @author      :   Julio Alcázar Rivas
                 * @change      :   Se realiza validaciones de la información de la salvedad y se cambio de lugar
                 *                  la variable turno
                 * Caso Mantis  :   04131
         */
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        String param_AnotacionSalvedad_Descripcion = null;
        String param_AnotacionSalvedad_Id = null;
        if (turno.getSolicitud() instanceof SolicitudCorreccion) {
            WizardEnglobeException exception = new WizardEnglobeException();
            param_AnotacionSalvedad_Descripcion = request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION);
            param_AnotacionSalvedad_Id = request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID);

            BasicConditionalValidator stage1_validator;

            stage1_validator = new BasicStringNotNullOrEmptyValidatorWrapper();
            stage1_validator.setObjectToValidate(param_AnotacionSalvedad_Descripcion);
            stage1_validator.validate();

            if (stage1_validator.getResult() != true) {
                exception.addError("anotacion:salvedad; debe escribir un valor en la salvedad");
            }

            // 1:B maxima longitud
            stage1_validator = new BasicStringMaxLengthValidatorWrapper(1024);
            stage1_validator.setObjectToValidate(param_AnotacionSalvedad_Descripcion);
            stage1_validator.validate();

            if (stage1_validator.getResult() != true) {
                exception.addError("anotacion:salvedad; la salvedad debe tener maximo de 1024 caracteres");
            }

            // 1:C minima longitud
            stage1_validator = new BasicStringMinLengthValidatorWrapper(30);
            stage1_validator.setObjectToValidate(param_AnotacionSalvedad_Descripcion);
            stage1_validator.validate();

            if (stage1_validator.getResult() != true) {
                exception.addError("anotacion:salvedad; la salvedad debe tener mas de 30 caracteres");
            }

            if ((null != param_AnotacionSalvedad_Id)
                    && ("".equals(param_AnotacionSalvedad_Id.trim()))) {
                param_AnotacionSalvedad_Id = null;
            }

            if (exception.getErrores().size() > 0) {
                throw exception;
            }
        }

        WebEnglobe webEnglobe = (WebEnglobe) request.getSession().getAttribute(WebKeys.WEB_ENGLOBE);
        if (webEnglobe == null) {
            webEnglobe = new WebEnglobe();
        }
        List ejes = (List) session.getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
        List direcciones = new ArrayList();
        /*Obtener la direccion del englobe para dejarlo persistente en DIRECCION_TMP*/
        if (webEnglobe.getFolioNuevo() != null && webEnglobe.getFolioNuevo().getDirecciones() != null) {
            for (int i = 0; i < webEnglobe.getFolioNuevo().getDirecciones().size(); i++) {
                String valorEje1 = ((WebDireccion) webEnglobe.getFolioNuevo().getDirecciones().get(i)).getIdEje1();
                String valorValor1 = ((WebDireccion) webEnglobe.getFolioNuevo().getDirecciones().get(i)).getValorEje1();
                String valorEje2 = ((WebDireccion) webEnglobe.getFolioNuevo().getDirecciones().get(i)).getIdEje2();
                String valorValor2 = ((WebDireccion) webEnglobe.getFolioNuevo().getDirecciones().get(i)).getValorEje2();
                Direccion direccion = new Direccion();

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
                    //throw new EnglobeException("Debe ingresar valor válido para el segundo eje  de la dirección");
                }

                String especificacion = ((WebDireccion) webEnglobe.getFolioNuevo().getDirecciones().get(i)).getEspecificacion();
                direccion.setEspecificacion(especificacion != null ? especificacion : null);
                /**
                 * ********************
                 *////////////////
                direcciones.add(direccion);
            }
        }

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        EvnEnglobe evnEnglobe = new EvnEnglobe(usuarioAuriga, EvnEnglobe.ENGLOBAR_FOLIOS, turno, circulo, webEnglobe, usuarioSIR);
        evnEnglobe.setListaCompletaEjes(ejes);
        evnEnglobe.setDireccionesEnglobe(direcciones);
        /*
                 * @author      :   Julio Alcázar Rivas
                 * @change      :   Se agrega al evento evnEnglobe informacion de la salvedad para turno tipo SolicitudCorreccion
                 * Caso Mantis  :   04131
         */
        if (turno.getSolicitud() instanceof SolicitudCorreccion) {
            evnEnglobe.setSalvedadDescripcion(param_AnotacionSalvedad_Descripcion);
            evnEnglobe.setSalvedadId(param_AnotacionSalvedad_Id);
        }
        return evnEnglobe;
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
        preservarInfoEnglobe(request);

        Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioArq = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosCiudadanoCalificacionException exception = new ValidacionParametrosCiudadanoCalificacionException();
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
     * @param request
     * @return
     */
    private Evento consultarUltimosPropietarios(HttpServletRequest request) {

        HttpSession session = request.getSession();

        this.preservarAllInfo(request);

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request
                .getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String UID = request.getSession().getId();
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(
                WebKeys.USUARIO);

        WebEnglobe webEnglobe = (WebEnglobe) request.getSession().getAttribute(WebKeys.WEB_ENGLOBE);
        List foliosHeredados = null;
        if (webEnglobe.getFoliosHeredados().size() > 0) {
            foliosHeredados = webEnglobe.getFoliosHeredados();
        }

        Folio folio = new Folio();

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
        EvnEnglobe evento = new EvnEnglobe(usuarioAuriga, folio, EvnEnglobe.CONSULTAR_ULTIMOS_PROPIETARIOS);
        evento.setFoliosEnglobados(foliosHeredados);
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
            key = CFolio.ANOTACION_SEXO_PERSONA + (auxnumCiud - 1);
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
            WizardEnglobeException exception = new WizardEnglobeException();
            exception.addError("Debe Seleccionar un Ciudadano");
            throw exception;
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

            key = CFolio.ANOTACION_TIPO_PERSONA + i;
            parametro = (String) ciu.getTipoPersona();
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

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

            key = CFolio.ANOTACION_SEXO_PERSONA + i;
            parametro = (String) ciu.getSexo();
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

    //OPCIONES GENERICAS DE CADA PASO. COMO CANCELAR Y ELIMINAR ENGLOBE
    //OPCIONES DE REDIRECCCIÓN DEL BOTON CANCELAR
    private Evento doProcess_OpcionEnglobePaso1Cancelar(HttpServletRequest request) throws AccionWebException {
        eliminarInfoBasicaVariosCiudadanos(request);
        return doProcess_OpcionEnglobePasoXCancelar(request);
    } // end-method: doProcess_OpcionEnglobePaso1Cancelar

    //OPCIONES PARA ELIMINAR UN ENGLOBE EN CURSO.
    private Evento eliminarEnglobe(HttpServletRequest request) throws AccionWebException {

        Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        WebEnglobe webEnglobe = (WebEnglobe) request.getSession().getAttribute(WebKeys.WEB_ENGLOBE);

        return new EvnEnglobe(usuarioAuriga, EvnEnglobe.ELIMINAR_ENGLOBE, turno, webEnglobe, usuario);
    }

    /**
     * Método
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento doProcess_OpcionEnglobePasoXCancelar(HttpServletRequest request) throws AccionWebException {

        // the session
        HttpSession session;
        session = request.getSession();

        // the exception collector
        WizardEnglobeException exception = new WizardEnglobeException();

        WebEnglobe local_Englobe;
        local_Englobe = (WebEnglobe) session.getAttribute(WebKeys.WEB_ENGLOBE);

        // acciones segun el proceso
        // acciones segun atributos en sesion
        if (null != local_Englobe) {
            int procesoCorrecciones = new Integer(CProceso.PROCESO_CORRECCIONES).intValue();
            if (local_Englobe.getIdProceso() == procesoCorrecciones) {

                doProcess_OpcionEnglobePasoX_RemoveState(request);

                // tratar de colocar el folio si se habia guardado
                // al iniciar el englobe en correcciones
                // Bug 3563:
                // metodo englobar remueve WebKeys.FOLIO
                session.setAttribute(WebKeys.FOLIO, session.getAttribute("LOCAL:WEBKEYS.FOLIO"));

            } // if

        } // if

        return null;
    } // end-method: doProcess_OpcionSegregacionPasoXCancelar

    /**
     * @param request
     */
    private void doProcess_OpcionEnglobePasoX_RemoveState(HttpServletRequest request) {

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

        if (evento instanceof EvnRespEnglobe) {
            EvnRespEnglobe respuesta = (EvnRespEnglobe) evento;

            if (respuesta.getTipoEvento().equals(EvnRespEnglobe.CONSULTA_FOLIO)) {

                //SE RECUPERA EL FOLIO QUE SE CONSULTO.
                Folio padre = (Folio) respuesta.getPayload();
                request.getSession().setAttribute(WebKeys.FOLIO, padre);

                LLavesMostrarFolioHelper llaves = (LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
                if (llaves != null) {
                    llaves.removeLLaves(request);
                }
                request.getSession().removeAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);

            } else if (respuesta.getTipoEvento().equals(EvnRespEnglobe.GUARDAR_WEB_ENGLOBE)) {

                if (respuesta.getWebSegEng() != null) {
                    request.getSession().setAttribute(WebKeys.WEB_ENGLOBE, respuesta.getWebSegEng());
                    /**
                     * @author : Carlos Mario Torres Urina
                     * @casoMantis : 0012705.
                     * @actaReq : Acta - Requerimiento No
                     * 056_453_Modificiación_de_Naturaleza_Jurídica
                     * @change :
                     */
                    if (respuesta.getWebSegEng().getAnotacion() != null) {
                        request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER, respuesta.getWebSegEng().getAnotacion().getVersion());
                    }
                }

                if (respuesta.getOficinaOrigen() != null) {
                    request.getSession().setAttribute(WebKeys.OFICINA_ORIGEN, respuesta.getOficinaOrigen());
                }

            } else if (respuesta.getTipoEvento().equals(EvnRespEnglobe.CONSULTA_ANOTACIONES_TEMPORALES)) {

                //SE RECUPERA LAS ANOTACIONES CONSULTADAS.
                List anotacionTMP = (List) respuesta.getPayload();

                if (anotacionTMP == null) {
                    anotacionTMP = new ArrayList();
                }

                request.getSession().setAttribute(WebKeys.FOLIO, respuesta.getFolio());
                request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO, anotacionTMP);

            } else if (respuesta.getTipoEvento().equals(EvnRespEnglobe.ENGLOBAR_FOLIOS)) {

                // doEnd: (englobe)
                HttpSession session = request.getSession();
                /*
                                * @author      :   Julio Alcázar Rivas
                                * @change      :   Eliminar los atributos de salvedad de la session
                                * Caso Mantis  :   04131
                 */
                session.removeAttribute(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION);
                session.removeAttribute(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID);
                WebEnglobe englobe = (WebEnglobe) session.getAttribute(WebKeys.WEB_ENGLOBE);

                if ((null != englobe)) {
                    // opc: Proceso.CORRECCIONES
                    int procesoCorrecciones = new Integer(CProceso.PROCESO_CORRECCIONES).intValue();
                    if (englobe.getIdProceso() == procesoCorrecciones) {

                        // --------------------------------------------------------------
                        // bug 3563
                        // reload data from turno
                        Turno local_Turno;
                        Folio local_Folio; // TODO: revisar si es necesitado
                        local_Turno = respuesta.getTurno();
                        local_Folio = respuesta.getFolio();
                        session.setAttribute(WebKeys.TURNO, local_Turno);
                        session.setAttribute(WebKeys.FOLIO, local_Folio);

                        // --------------------------------------------------------------
                        return;

                    } // if

                }

                //SE RECUPERA EL FOLIO QUE SE CONSULTO.
                Folio folio = (Folio) respuesta.getPayload();

                request.getSession().setAttribute(WebKeys.FOLIO, respuesta.getFolio());
                request.getSession().setAttribute(WebKeys.TURNO, respuesta.getTurno());

                request.getSession().removeAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
                request.getSession().removeAttribute(CFolioDerivado.NOMBRE_NUMERO);
                request.getSession().removeAttribute(CFolioDerivado.AREA);
                request.getSession().removeAttribute(CFolioDerivado.HECTAREAS);
                request.getSession().removeAttribute(CFolioDerivado.METROS);
                request.getSession().removeAttribute(CFolioDerivado.CENTIMETROS);
                request.getSession().removeAttribute(CFolioDerivado.DESCRIPCION);
            } else if (respuesta.getTipoEvento().equals(EvnRespEnglobe.CONSULTAR_PROPIETARIOS_FOLIO)) {
                request.getSession().setAttribute(AWEnglobe.LISTA_PROPIETARIOS_FOLIO, respuesta.getPropietariosFolios());
            }
            if (respuesta.getTipoEvento().equals(EvnRespEnglobe.VOLVER_AGREGAR_CIUDADANOS)) {
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

}

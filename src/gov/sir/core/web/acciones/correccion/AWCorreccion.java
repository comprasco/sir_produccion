package gov.sir.core.web.acciones.correccion;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import gov.sir.core.eventos.correccion.EvnCorreccion;
import gov.sir.core.eventos.correccion.EvnRespCorreccion;
import gov.sir.core.negocio.modelo.Acto;
/*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  se abilita el uso de las clases Circulo y DeltaTestamento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
 */
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.DeltaTestamento;
import gov.sir.core.negocio.modelo.DeltaFolio;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.TipoActo;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.registro.AWCalificacion;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.ProcesadorEventosNegocioProxy;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
/**
 * @author: Cesar Ramirez
 * @change: 1157.111.NO.INCLUIR.LINDEROS.PROPIEDAD.HORIZONTAL
 * Se añade la clase con las constantes CTipoAnotacion
 **/
import gov.sir.core.negocio.modelo.constantes.CTipoAnotacion;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.impl.jdogenie.JDOLiquidacionesDAO;
import gov.sir.hermod.pagos.LiquidadorRegistro;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta accion web se encarga de generar eventos de negocio relacionados con el
 * proceso de correcciones. Se encarga de realizar las validaciones primarias a nivel web y
 * por medio de eventos hace llamados a la capa de negocio para que su vez llame los servicios
 * que se requieren.
 *
 * @author ppabon, jvelez
 *
 */
public class AWCorreccion extends SoporteAccionWeb {

	/** Si se tiene esta accion, se enviará el conjunto de diferencias para que el auxiliar
	 *  pueda enviar estos cambios a un revisor, quien podrá avalar o rechazar estos cambios. */
    public static final String ENVIARCORRECCIONSIMPLEAREVISIONAPROBACION_ACTION = AwCorrecciones_Constants.ENVIARCORRECCIONSIMPLEAREVISIONAPROBACION_ACTION;
    public static final String VISUALIZAR_PDF = "VISUALIZAR_PDF";
	/** Esta acción permite guardar los cambios que se realizaron en la actuación administrativa y avanzar el turno*/
    public static final String TERMINAR_EJECUCION_ACTUACION_ADMINISTRATIVA = "TERMINAR_EJECUCION_ACTUACION_ADMINISTRATIVA";

    public static final String ENVIARCORRECCIONSIMPLEAREVISIONANALISIS_ACTION = AwCorrecciones_Constants.ENVIARCORRECCIONSIMPLEAREVISIONANALISIS_ACTION;

    // opciones digitacion-masiva, copiar direccion, copiar complementaciones
    public static final String PAGE_REGION__STARTOPCIONESDIGITACIONMASIVA_ACTION
            = "PAGE_REGION__STARTOPCIONESDIGITACIONMASIVA_ACTION";

    public static final String PAGE_CORRECCIONREVISARAPROBAR_BTNDESHACERCAMBIOS_ACTION
            = "PAGE_CORRECCIONREVISARAPROBAR_BTNDESHACERCAMBIOS_ACTION";

    //
    public static final String CORRECCIONSIMPLEMAIN_PRINTFORM_STEP0_BTNSTART_ACTION
            = "CORRECCIONSIMPLEMAIN_PRINTFORM_STEP0_BTNSTART_ACTION";

    /**
     *
     * Separar impresion de formulario de correcciones
	 *
     */
    public static final String CORR_REVISIONAPROBACION__PRINTFORM
            = "CORR_REVISIONAPROBACION__PRINTFORM";

    /**
	 * Constante que identifica que se desea APROBAR la corrección
     */
    public final static String APROBAR = "APROBAR";

    /**
	 * Constante que identifica que se desea ver las diferencias entre los datos que están definitivos y los cambios temporales
	 * con el fin de determinar cuáles son las diferencias que se hicieron en el proceso de correcciones y
	 * aprobarlas o negarlas.
     *
     */
    public final static String CARGAR_CAMBIOS_PROPUESTOS = "CARGAR_CAMBIOS_PROPUESTOS";

    /**
	 * Constante que identifica que se desea forzar la aprobación de la corrección
     */
    public final static String FORZAR_APROBACION = "FORZAR_APROBACION";

    /**
	 * Constante que identifica que se desea preguntar si se manda a mayor valor o se aprueba el caso definitivamente.
     */
    public final static String PREGUNTAR_APROBACION = "PREGUNTAR_APROBACION";

    /**
	 * Constante que identifica que se desea NEGAR la corrección
     */
    public final static String NEGAR = "NEGAR";

    /**
	 * Constante que identifica que se desea APROBAR la corrección en  especializado
     */
    public final static String APROBAR_ESPECIALIZADO = "APROBAR_ESPECIALIZADO";

    /**
	 * Constante que identifica que se desea NEGAR la corrección en  especializado
     */
    public final static String NEGAR_ESPECIALIZADO = "NEGAR_ESPECIALIZADO";

    /**
	 * Constante que identifica que se desea APROBAR la solicitud de microfilmación
     */
    public final static String APROBAR_MICROFILMACION = "APROBAR_MICROFILMACION";

    /**
	 * Constante que identifica que se desea NEGAR la solicitud de microfilmación
     */
    public final static String NEGAR_MICROFILMACION = "NEGAR_MICROFILMACION";

    /**
	 * Constante que identifica que se desea APROBAR la corrección en  digitación
     */
    public final static String APROBAR_DIGITACION = "APROBAR_DIGITACION";

    /**
	 * Constante que identifica que se desea NEGAR la corrección en  digitación
     */
    public final static String NEGAR_DIGITACION = "NEGAR_DIGITACION";

    /**
	 * Constante que identifica que se desea BLOQUEAR los folios mientras se realiza el proceso de corrección
     */
    public final static String TOMAR_FOLIO = "TOMAR_FOLIO";

    /**
	 * Constante que identifica que se desea BLOQUEAR los folios mientras se realiza el proceso de corrección
     */
    public final static String TOMAR_FOLIO_CORRECCION = "TOMAR_FOLIO_CORRECCION";

    /**
	 * Constante que identifica que se desea BLOQUEAR los folios mientras se realiza el proceso de corrección
     */
    public final static String TOMAR_FOLIO_DIGITACION = "TOMAR_FOLIO_DIGITACION";

    /**
	 * Constante que identifica que se desea BLOQUEAR los folios mientras se realiza el proceso de corrección
     */
    public final static String TOMAR_FOLIO_ESPECIALIZADO = "TOMAR_FOLIO_ESPECIALIZADO";

    /**
	 * Constante que identifica que se desea EDITAR los folios porque hace parte de la corrección
     */
    public final static String EDITAR_FOLIO = "EDITAR_FOLIO";

    /**
	 * Constante que identifica que se desea DELEGAR el turno de la corrección
     */
    public final static String DELEGAR_CASO = "DELEGAR_CASO";

    /**
	 * Constante que identifica que se desea DELEGAR el turno de la corrección a MICROFILMACIÓN
     */
    public final static String DELEGAR_CASO_MICROFILMACION = "DELEGAR_CASO_MICROFILMACION";

    /**
	 * Constante que identifica que se desea DELEGAR el turno de la corrección a ESPECIALIZADO
     */
    public final static String DELEGAR_CASO_ESPECIALIZADO = "DELEGAR_CASO_ESPECIALIZADO";

    /**
	 * Constante que identifica que se desea DELEGAR el turno de la corrección a ANTIGUO_SISTEMA
     */
    public final static String DELEGAR_CASO_ANTIGUO_SISTEMA = "DELEGAR_CASO_ANTIGUO_SISTEMA";

    /**
	 * Constante que identifica que se desea DELEGAR el turno de la corrección a DIGITACION
     */
    public final static String DELEGAR_CASO_DIGITACION = "DELEGAR_CASO_DIGITACION";

    /**
	 * Constante que identifica que se desea DEVOLVER la corrección a quién fue asignada originalmente
     */
    public final static String DEVOLVER_CASO = "DEVOLVER_CASO";

    /**
	 * Constante que identifica que se desea REDIRECCIONAR la corrección para que se trabaje por mayor valor o por especializado
     */
    public final static String REDIRECCIONAR_CASO = "REDIRECCIONAR_CASO";

    /**
	 * Constante que identifica que se desea IMPRIMIR llos certificados asociados a la corrección.
     */
    public final static String IMPRIMIR = "IMPRIMIR";

    public final static String IMPRIMIR_INDIVIDUAL = "IMPRIMIR_INDIVIDUAL";

    /**
	 * Constante que identifica que se desea avanzar el turno de Impresión de Certificados.
     */
    public final static String AVANZAR_IMPRIMIR = "AVANZAR_IMPRIMIR";

    /**
	 * Constante que identifica que la notificación al ciudadano ha sido exitosa
     */
    public final static String NOTIFICAR_CIUDADANO_EXITO = "NOTIFICAR_CIUDADANO_EXITO";

    /**
	 * Constante que identifica que la notificación al ciudadano ha fracasado
     */
    public final static String NOTIFICAR_CIUDADANO_FRACASO = "NOTIFICAR_CIUDADANO_FRACASO";

    /**
     * Constante que identifica que se quiere enviar por pago mayor valor
     */
    public final static String PAGO_MAYOR_VALOR = "PAGO_MAYOR_VALOR";

    /**
     * Constante que identifica que se quiere enviar por pago mayor valor
     */
    public final static String RAZON_MAYOR_VALOR = "RAZON_MAYOR_VALOR";

    public final static String FOLIOS_NO_IMPRESOS = "FOLIOS_NO_IMPRESOS";

    public final static String PAGO_MAYOR_VALOR_IMPUESTOS = "PAGO_MAYOR_VALOR_IMPUESTOS";

    public final static String PAGO_MAYOR_VALOR_DERECHOS = "PAGO_MAYOR_VALOR_DERECHOS";

    public final static String PAGO_MAYOR_VALOR_CERTIFICADOS = "PAGO_MAYOR_VALOR_CERTIFICADOS";

    public final static String IMPRIMIO_FORMULARIO_OK = "IMPRIMIO_FORMULARIO_OK";

    /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  constantes usuadas en la correccion del testamento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
     */
 /*constante para la accion tomar turno de testamento*/
    public final static String TOMAR_TURNO_TESTAMENTO = "TOMAR_TURNO_TESTAMENTO";

    public final static String EDITAR_TESTAMENTO = "EDITAR_TESTAMENTO";

    public final static String CARGAR_TESTAMENTO = "CARGAR_TESTAMENTO";

    public final static String GUARDAR_CAMBIOS_TESTAMENTO_TMP = "GUARDAR_CAMBIOS_TESTAMENTO_TMP";

    /**
     * Variable donde se guarda la accion enviada en el request
     */
    private String accion;

    /**
     * Constructor de la clase AWCorreccion
     */
    public AWCorreccion() {
        super();
    }

    /**
	 * Método principal de esta acción web. Aqui se realiza
	 * toda la lógica requerida de validación y de generación
	 * de eventos de negocio.
     * @param request trae la informacion del formulario
     * @throws AccionWebException cuando ocurre un error
     * @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier
     * otro caso
     */
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        accion = request.getParameter(WebKeys.ACCION);

        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion valida");
        }

        //HttpSession session = request.getSession();
        //Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        if(VISUALIZAR_PDF.equals(accion)){
            return null;
        }else if (ENVIARCORRECCIONSIMPLEAREVISIONAPROBACION_ACTION.equals(accion)) {
            return doEnviarCorreccionSimpleARevisionAprobacion(request);
        } else if (ENVIARCORRECCIONSIMPLEAREVISIONANALISIS_ACTION.equals(accion)) {
            return doEnviarCorreccionSimpleARevisionAnalisis(request);
        } else if (CORR_REVISIONAPROBACION__PRINTFORM.equals(accion)) {
            return doCorr_RevisionAprobacion_PrintForm(request);
        } else if (CORRECCIONSIMPLEMAIN_PRINTFORM_STEP0_BTNSTART_ACTION.equals(accion)) {
            // bug 4992
            return doCorr_RevisionAprobacion_PrintForm(request, true);
        } // edicion folio-digitacionmasiva-startopciones
        else if (PAGE_REGION__STARTOPCIONESDIGITACIONMASIVA_ACTION.equals(accion)) {
            // TODO
            return doCorr_PageEvent_StartOpcionesDigitacionMasiva(request);
        } // correccion.revisaraprobar:: deshacer cambios
        else if (PAGE_CORRECCIONREVISARAPROBAR_BTNDESHACERCAMBIOS_ACTION.equals(accion)) {
            return doProcess_BtnDeshacerCambios(request);
        } // ----
        //
        else if (accion.equals(APROBAR)) {
            return aprobarCorreccion(request);
        } else if (accion.equals(FORZAR_APROBACION)) {
            return forzarAprobacion(request);
        } else if (accion.equals(NEGAR)) {
            return negarCorreccion(request);
        } else if (accion.equals(CARGAR_CAMBIOS_PROPUESTOS)) {
            return cargarCambiosPropuestos(request);
        } else if (accion.equals(APROBAR_ESPECIALIZADO)) {
            return aprobarCorreccionEspecializado(request);
        } else if (accion.equals(NEGAR_ESPECIALIZADO)) {
            return negarCorreccionEspecializado(request);
        } else if (accion.equals(APROBAR_DIGITACION)) {
            return aprobarCorreccionDigitacion(request);
        } else if (accion.equals(NEGAR_DIGITACION)) {
            return negarCorreccionDigitacion(request);
        } else if (accion.equals(APROBAR_MICROFILMACION)) {
            return aprobarMicrofilmacion(request);
        } else if (accion.equals(NEGAR_MICROFILMACION)) {
            return negarMicrofilmacion(request);
        } else if (accion.equals(TOMAR_FOLIO_CORRECCION)) {
            return tomarFoliosCorreccion(request);
        } else if (accion.equals(TOMAR_FOLIO_DIGITACION)) {
            return tomarFoliosDigitacion(request);
        } else if (accion.equals(TOMAR_FOLIO_ESPECIALIZADO)) {
            return tomarFoliosEspecializado(request);
        } else if (accion.equals(EDITAR_FOLIO)) {
            return editarFolios(request);
        } else if (accion.equals(DELEGAR_CASO)) {
            return delegarCorreccion(request);
        } else if (accion.equals(DEVOLVER_CASO)) {
            return devolverCorreccion(request);
        } else if (accion.equals(REDIRECCIONAR_CASO)) {
            return redireccionarCorreccion(request);
        } else if (accion.equals(IMPRIMIR)) {
            return imprimirCertificados(request);
        } else if (accion.equals(IMPRIMIR_INDIVIDUAL)) {
            return imprimirCertificadosIndividual(request);
        } else if (accion.equals(AVANZAR_IMPRIMIR)) {
            return avanzarImprimir(request);
        } else if (accion.equals(NOTIFICAR_CIUDADANO_EXITO)) {
            return notificarCiudadanoExito(request);
        } else if (accion.equals(NOTIFICAR_CIUDADANO_FRACASO)) {
            return notificarCiudadanoFracaso(request);
        } else if (accion.equals(PAGO_MAYOR_VALOR)) {
            return pagoMayorValor(request);
        } else if (accion.equals(TERMINAR_EJECUCION_ACTUACION_ADMINISTRATIVA)) {
            return terminarEjecucionActuacionAdministrativa(request);
        } /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  se agregan condiciones para las acciones de testamento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
         */ else if (accion.equals(TOMAR_TURNO_TESTAMENTO)) {
            return tomarTurnoTestamento(request);
        } else if (accion.equals(EDITAR_TESTAMENTO)) {
            return editarTestamento(request);
        } else {
            throw new AccionInvalidaException("La accion " + accion + " no es valida.");
        }
    }

    /**
     * @param request
     * @return
     */
    private Evento pagoMayorValor(HttpServletRequest request) throws AccionWebException {
        ValidacionParametrosException exception = new ValidacionParametrosException();
        //Obtener los datos para la liquidacion
        //String valorPagar= request.getParameter(AWCorreccion.PAGO_MAYOR_VALOR);

        // Se obtienen los valores para los derechos y los impuestos por separado
        this.preservarInfoMayorValor(request);
        String valorDerechos = request.getParameter(AWCorreccion.PAGO_MAYOR_VALOR_DERECHOS);
        String valorCertificados = request.getParameter(AWCalificacion.PAGO_MAYOR_VALOR_CERTIFICADOS);
        String valorImpuestos = request.getParameter(AWCorreccion.PAGO_MAYOR_VALOR_IMPUESTOS);
        String razonMayorValor = request.getParameter(AWCorreccion.RAZON_MAYOR_VALOR);

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        double dValorDerechos = 0, dValorImpuestos = 0, nvalor = 0;
        double dValorCertificados = 0;

       
        
        
        if (valorDerechos == null || valorDerechos.equals("")) {
            dValorDerechos = 0;
        } else {
            valorDerechos = valorDerechos.replaceAll(",", "");
            try {
                if (!valorDerechos.equals("")) {
                    dValorDerechos = Double.parseDouble(valorDerechos);
                } else {
                    dValorDerechos = 0;
                }
			}catch(NumberFormatException e){
				exception.addError("El valor por derechos es inválido");
            }
        }
        
        Acto actoMayorValor = new Acto();
        TipoActo tipoActoMayorValor = new TipoActo();
        tipoActoMayorValor.setIdTipoActo(CRespuesta.MAYOR_VALOR_ID);

        actoMayorValor.setTipoActo(tipoActoMayorValor);

        JDOLiquidacionesDAO jdoLiquidacionesDAO = new JDOLiquidacionesDAO();

        double dporcentajeConservacionDoc = 0;
        try {
            dporcentajeConservacionDoc = jdoLiquidacionesDAO.TraerConservacionActo(actoMayorValor);
        } catch (DAOException ex) {
            Logger.getLogger(AWCorreccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         double dValorConservacionDoc = jdoLiquidacionesDAO.roundValue(dValorDerechos * dporcentajeConservacionDoc);

        if (valorImpuestos == null || valorImpuestos.equals("")) {
            dValorImpuestos = 0;
        } else {
            valorImpuestos = valorImpuestos.replaceAll(",", "");
            try {
                if (!valorImpuestos.equals("")) {
                    dValorImpuestos = Double.parseDouble(valorImpuestos);
                } else {
                    dValorImpuestos = 0;
                }
			}catch(NumberFormatException e){
				exception.addError("El valor por impuestos es inválido");
            }
        }

        if (valorCertificados == null || valorCertificados.equals("")) {
            dValorCertificados = 0;
        } else {
            valorCertificados = valorCertificados.replaceAll(",", "");
            try {
                if (!valorCertificados.equals("")) {
                    dValorCertificados = Double.parseDouble(valorCertificados);
                } else {
                    dValorCertificados = 0;
                }
			}catch(NumberFormatException e){
				exception.addError("El valor por certificados es inválido");
            }
        }

        nvalor = dValorImpuestos + dValorDerechos + dValorCertificados + dValorConservacionDoc;

		if(razonMayorValor==null || razonMayorValor.equals("")){
			exception.addError("La razón del pago esta vacia");
        }

        if (nvalor == 0.0) {
            exception.addError("Al menos un valor debe ser diferente a cero");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        //crear liquidacion a partir de los datos
        LiquidacionTurnoCorreccion liq = new LiquidacionTurnoCorreccion();
        liq.setFecha(new Date());
        liq.setUsuario(usuarioNeg);
        if (!valorDerechos.equals("")) {
            liq.setValorDerechos(dValorDerechos);
        }
        if (!valorImpuestos.equals("")) {
            liq.setValorImpuestos(dValorImpuestos);
        }
        liq.setJustificacionMayorValor(razonMayorValor);
        liq.setValor(nvalor);
        liq.setValorConservacionDoc(dValorConservacionDoc);

        //crear e inicializar el evento
        EvnCorreccion evento = new EvnCorreccion(usuarioAuriga, EvnCorreccion.APROBAR_MAYOR_VALOR, turno, liq, fase, usuarioNeg);

        return evento;
    }

    /**
     * Mantiene en sesion la informacion que se ingreaso para mayor valor
     *
     * @param request
     * @return
     */
    private void preservarInfoMayorValor(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (request.getParameter(AWCorreccion.PAGO_MAYOR_VALOR_DERECHOS) != null) {
            session.setAttribute(AWCorreccion.PAGO_MAYOR_VALOR_DERECHOS, request.getParameter(AWCorreccion.PAGO_MAYOR_VALOR_DERECHOS));
        }
        if (request.getParameter(AWCalificacion.PAGO_MAYOR_VALOR_CERTIFICADOS) != null) {
            session.setAttribute(AWCorreccion.PAGO_MAYOR_VALOR_CERTIFICADOS, request.getParameter(AWCalificacion.PAGO_MAYOR_VALOR_CERTIFICADOS));
        }
        if (request.getParameter(AWCorreccion.PAGO_MAYOR_VALOR_IMPUESTOS) != null) {
            session.setAttribute(AWCorreccion.PAGO_MAYOR_VALOR_IMPUESTOS, request.getParameter(AWCorreccion.PAGO_MAYOR_VALOR_IMPUESTOS));
        }
        if (request.getParameter(AWCorreccion.RAZON_MAYOR_VALOR) != null) {
            session.setAttribute(AWCorreccion.RAZON_MAYOR_VALOR, request.getParameter(AWCorreccion.RAZON_MAYOR_VALOR));
        }

    }

    /**
     * Avance de Wf para que los cambios puedan ser vistos por el revisor
     *
     * @param request
     * @return
     */
    private EvnCorreccion
            doEnviarCorreccionSimpleARevisionAprobacion(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        session.removeAttribute(AWModificarFolio.NUMERO_SEGREGACIONES_VACIAS);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);

        //	Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO_EDITADO);
        String accion = EvnCorreccion.ENVIARCORRECCIONSIMPLEAREVISIONAPROBACION_EVENT;
        String accionWf = EvnCorreccion.CONFIRMAR_WF;

        /*        if (folio == null) {
                        folio = new Folio();
                }
                List anotaciones = folio.getAnotaciones();
                if (anotaciones != null && anotaciones.size() > 0) {
                        Iterator it = anotaciones.iterator();
                        while (it.hasNext()) {
                                Anotacion anotacion = (Anotacion) it.next();
                                if (anotacion != null && anotacion.getIdAnotacion() == null) {
                                        accion =EvnCorreccion.REDIRECCIONAR_CASO;
                                        accionWf =EvnCorreccion.MAYOR_VALOR;
                                }
                        }
                }
         */
        /**
         * @Autor: Edgar Lora
         * @Mantis: 0013038
         * @Requerimiento: 060_453
         */
        ValidacionParametrosException exception = new ValidacionParametrosException();
        ValidacionesSIR validacionesSIR = new ValidacionesSIR();

        List solicitudFolios = turno.getSolicitud().getSolicitudFolios();
        Iterator iFolios = solicitudFolios.iterator();
        while (iFolios.hasNext()) {
            SolicitudFolio sf = (SolicitudFolio) iFolios.next();
            Folio folio = sf.getFolio();

            try {
                String matricula = folio.getIdMatricula();
                String lindero = validacionesSIR.getLinderoDeMatricula(matricula);
                if (validacionesSIR.esFolioNuevo(turno.getIdWorkflow(), matricula)) {
                    /**
                     * @author: Cesar Ramirez
                             * @change: 1157.111.NO.INCLUIR.LINDEROS.PROPIEDAD.HORIZONTAL
                             * Se cambia el método de de validarLinderos por validarLinderosPrimeraAnotacionDerivada.
                             **/
                    if (validacionesSIR.validarLinderosPrimeraAnotacionDerivada(matricula, CNaturalezaJuridica.NATURALEZA_PARA_VALIDAR_LINDEROS, CTipoAnotacion.DERIVADO)) {
                                String articulo = ("Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012").toUpperCase();
                                if(lindero.indexOf(articulo) != -1){
                            int tamArticulo = lindero.indexOf(articulo) + articulo.length();
                                    if(lindero.length() - tamArticulo < 100){
                                        exception.addError("Debe añadir minimo 100 caracteres al campo de linderos de la matricula: " + matricula);
                            }
                                }else{
                                    exception.addError("El lindero de la matricula " + matricula + " debe tener 100 caracteres a partir del texto \"Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012\", Porfavor no lo borre.");
                        }
                    }
                }
            } catch (GeneralSIRException ex) {
                exception.addError(ex.getMessage());
            }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, accion, accionWf);
        evn.setEjecutadaCorreccionSimple(true);
        evn.setUID(request.getSession().getId());

        return evn;
    }

    /**
	 * Guarda los cambios realizados en la actuación administrativa y avanza.
     * @param request
     * @return
     */
    private EvnCorreccion terminarEjecucionActuacionAdministrativa(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);

        String accion = EvnCorreccion.TERMINAR_EJECUCION_ACTUACION_ADMINISTRATIVA;
        String accionWf = EvnCorreccion.CONFIRMAR_WF;

        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, accion, accionWf);
        evn.setUID(request.getSession().getId());
        evn.setImpresionTemporalDeAuxiliarEnabled(false);
        evn.setCirculoId(turno.getIdCirculo());

        /*
                 * @autor         : HGOMEZ 
                 * @mantis        : 11631 
                 * @Requerimiento : 036_453_Correccion_Actuaciones_Administrativas  
                 * @descripcion   : Se habilita en el rol "AUXILIAR ACTUACIONES ADMINISTRATIVAS" 
                 * el llamado al evento que se ejecuta en el botón "Imprimir Formularios"
         */
        EvnCorreccion evento = doCorr_RevisionAprobacion_PrintForm(request, false);
        ProcesadorEventosNegocioProxy proxy = new ProcesadorEventosNegocioProxy();
        ValidacionParametrosException excepcion = new ValidacionParametrosException();
        try {
            EvnRespCorreccion respuesta = (EvnRespCorreccion) proxy.manejarEvento(evento);
            doEndCorr_RevisionAprobacion_PrintForm(request, respuesta);
        } catch (EventoException e) {
            excepcion.addError("Hubo un error al intentar imprimir el formulario de correcciones: " + e.getMessage());
            throw excepcion;
        }

        return evn;
    }

    /**
     * Avance de Wf para que se pueda volver a asignar permisos o a insertar
     * folios
     *
     * @param request
     * @return
     */
    private EvnCorreccion
            doEnviarCorreccionSimpleARevisionAnalisis(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);

        //	Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO_EDITADO);
        String accion = EvnCorreccion.ENVIARCORRECCIONSIMPLEAREVISIONANALISIS_EVENT;
        String accionWf = EvnCorreccion.DEVOLVERAEVISIONANALISIS_WF;

        /*        if (folio == null) {
                        folio = new Folio();
                }
                List anotaciones = folio.getAnotaciones();
                if (anotaciones != null && anotaciones.size() > 0) {
                        Iterator it = anotaciones.iterator();
                        while (it.hasNext()) {
                                Anotacion anotacion = (Anotacion) it.next();
                                if (anotacion != null && anotacion.getIdAnotacion() == null) {
                                        accion =EvnCorreccion.REDIRECCIONAR_CASO;
                                        accionWf =EvnCorreccion.MAYOR_VALOR;
                                }
                        }
                }
         */
        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, accion, accionWf);
        evn.setEjecutadaCorreccionSimple(true);
        evn.setUID(request.getSession().getId());

        return evn;
    }

    private EvnCorreccion
            doCorr_RevisionAprobacion_PrintForm(HttpServletRequest request)
            throws AccionWebException {
        return doCorr_RevisionAprobacion_PrintForm(request, false);
    } // end-method;

    /**
     * Mantis 6457: Acta - Requerimiento No 231 - Error Proceso Correcciones -
     * Fase Aprobar, Se realiza refactory en el metodo de
     * doCorr_RevisionAprobacion_PrintForm, ya que, exite un bug cuando se
     * realiza la validacion de cambios realizados sobre los folios asociados al
     * turno. Se cambia esta validacion usando la libreria GeneralSIR para
     * realizar esta validacion.
     *
     * @author fernando padilla velez
     */
    /*
    * @autor : HGOMEZ 
    * @mantis : 11631 
    * @Requerimiento : 036_453_Correccion_Actuaciones_Administrativas 
    * @descripcion : Se hacen ajustes generales al método para satisfacer 
    * el requerimiento.
     */
    private EvnCorreccion doCorr_RevisionAprobacion_PrintForm(HttpServletRequest request, boolean impresionTemporal) throws AccionWebException {

        HttpSession session = request.getSession();

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        /*
         * @fecha 23/11/2012 @author Carlos Torres @chage se agrega varible
         * turno anterior @mantis 12291: Acta - Requerimiento No
         * 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
         */
        Turno turnoAnterior = turno.getSolicitud().getTurnoAnterior();
        String accionWf = "";
        String accion = EvnCorreccion.CORR_REVISIONAPROBACION__PRINTFORM_EVENT;

        ValidacionParametrosException exception = new ValidacionParametrosException();

        try {
            ValidacionesSIR validacionesSIR = new ValidacionesSIR();
            /*
             * @fecha 23/11/2012 @author Carlos Torres @chage se agrega
             * validacion de turno de testamento @mantis 12291: Acta -
             * Requerimiento No
             * 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
             */
            if (turnoAnterior == null || turnoAnterior.getIdProceso() != 6) {
                if (fase != null && fase.getID() != null && !fase.getID().equals(CFase.COR_ACT_EJECUTAR)) {
                    if (!validacionesSIR.isTurnoMatriculasTemporales(turno.getIdWorkflow())) {
                        exception.addError("No se puede imprimir el formulario porque no se han realizado correcciones.");
                        throw exception;
                    }
                }
            } else {
                /*
                 * @fecha 23/11/2012 @author Carlos Torres @chage se comprueba
                 * si el testamento tiene datos temporales @mantis 12291: Acta -
                 * Requerimiento No
                 * 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
                 */
                if (!validacionesSIR.isTestamentoTMP(turnoAnterior.getSolicitud().getIdSolicitud())) {
                    exception.addError("No se puede imprimir el formulario porque no se han realizado correcciones.");
                    throw exception;
                }
            }

            int copias = 1;

            if (fase != null && fase.getID() != null && fase.getID().equals(CFase.COR_REVISAR_APROBAR)
                    || (fase.getID().equals(CFase.COR_ACT_EJECUTAR) && request.getParameter(WebKeys.NUMERO_COPIAS_IMPRESION).equals(""))) {
                copias = 2;
            } else {
                String valCopias = request.getParameter(WebKeys.NUMERO_COPIAS_IMPRESION);
                if (valCopias != null) {
                    try {
                        copias = Integer.valueOf(valCopias).intValue();
                        if (copias < 1) {
                            exception.addError("El número de copias es inválido");
                        }
                    } catch (Exception e) {
                        exception.addError("El número de copias es inválido");
                    }
                }
            }

            // raise if needed --------------------------------------------
            if (exception.getErrores().size() > 0) {
                throw exception;
            }
            // ------------------------------------------------------------

            EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, accion, accionWf);
            // evn.setEjecutadaCorreccionSimple( true );

            Circulo local_Circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

            String local_CirculoId = local_Circulo.getIdCirculo();

            evn.setUID(request.getSession().getId());

            // cambiado: se usa la impresion por la
            // impresora laser; se mantiene
            // sin embargo el id de sesion
            evn.setCirculoId(local_CirculoId);

            if (!validacionesSIR.isTurnoMatriculasTemporales(turno.getIdWorkflow()) && fase.getID().equals(CFase.COR_ACT_EJECUTAR)) {
                evn.setNumeroCopias(0);
            } else {
                evn.setNumeroCopias(copias);
            }

            evn.setImpresionTemporalDeAuxiliarEnabled(impresionTemporal);

            return evn;
        } catch (GeneralSIRException ex) {
            /*
             * @fecha 23/11/2012 @author Carlos Torres @chage se corrige el
             * mensaje @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
             */
            exception.addError("No se puede imprimir el formulario porque se produce "
                    + "un error al validar el turno: " + ex.getMessage());
            throw exception;
        }
    } // end-method: doCorr_RevisionAprobacion_PrintForm

    /**
	 * Método que avanza el workflow por éxito en la fase de corrección de documentos.
	 * Por este método se permite hacer definitivos los cambios que estan en termporal.
     * @param request
     * @return
     */
    private EvnCorreccion aprobarCorreccion(HttpServletRequest request) throws AccionWebException {

        EvnCorreccion evento = doCorr_RevisionAprobacion_PrintForm(request);
        ProcesadorEventosNegocioProxy proxy = new ProcesadorEventosNegocioProxy();
        ValidacionParametrosException excepcion = new ValidacionParametrosException();
        try {
            EvnRespCorreccion respuesta = (EvnRespCorreccion) proxy.manejarEvento(evento);
            doEndCorr_RevisionAprobacion_PrintForm(request, respuesta);
        } catch (EventoException e) {
            excepcion.addError("Hubo un error al intentar imprimir el formulario de correcciones: " + e.getMessage());
            throw excepcion;
        }
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);

        //	Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO_EDITADO);
        String accion = EvnCorreccion.APROBAR;
        String accionWf = EvnCorreccion.CONFIRMAR_WF;

        /*        if (folio == null) {
		                folio = new Folio();
		        }
		        List anotaciones = folio.getAnotaciones();
		        if (anotaciones != null && anotaciones.size() > 0) {
		                Iterator it = anotaciones.iterator();
		                while (it.hasNext()) {
		                        Anotacion anotacion = (Anotacion) it.next();
		                        if (anotacion != null && anotacion.getIdAnotacion() == null) {
		                                accion =EvnCorreccion.REDIRECCIONAR_CASO;
		                                accionWf =EvnCorreccion.MAYOR_VALOR;
		                        }
		                }
		        }
         */
        ValidacionParametrosException exception = new ValidacionParametrosException();

        String valCopias = request.getParameter(WebKeys.NUMERO_COPIAS_IMPRESION);

        // revision: ahora las copias se imprimen por otra accion
        // @see doCorr_RevisionAprobacion_PrintForm
        valCopias = null;

        int copias = 1;

        if (valCopias != null) {
            try {
                copias = Integer.valueOf(valCopias).intValue();
				if(copias < 1){
					exception.addError("El número de copias es inválido");
                }
			}catch(Exception e){
				exception.addError("El número de copias es inválido");
            }
        }

        if (!(request.getSession().getAttribute(AWCorreccion.IMPRIMIO_FORMULARIO_OK) != null)) {
            exception.addError("Se debe imprimir el formulario de Correciones antes de avanzar el turno");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, accion, accionWf);
        /**
         * @Author Carlos Torres
         * @Mantis 13176
         * @Chaged ;
         */
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioNeg.getUsername(), String.valueOf(usuarioNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
        evn.setInfoUsuario(infoUsuario);
        // evn.setEjecutadaCorreccionSimple( true );
        evn.setUID(request.getSession().getId());
        /*
                * @autor         : HGOMEZ 
                * @mantis        : 11631 
                * @Requerimiento : 036_453_Correccion_Actuaciones_Administrativas  
                * @descripcion   : Se comenta la siguiente linea para que no imprima
                * una copia de más.
         */
        //evn.setNumeroCopias(copias);

        return evn;
    }

    /**
	 * Método que avanza el workflow por éxito en la fase de corrección de documentos.
	 * a diferencia de aprobar se entra en este método cuando hay anotaciones en temporal y a pesar de
	 * esto el corrector desea guardar las anotaciones que estan en temporal.
     * @param request
     * @return
     */
    private EvnCorreccion forzarAprobacion(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnCorreccion.FORZAR_APROBACION, EvnCorreccion.CONFIRMAR_WF);
        evn.setUID(request.getSession().getId());
        return evn;
    }

    /**
	 * Método que avanza el workflow por fracaso en la fase de corrección de documentos.
	 * Por este método se deshacen los cambios que estan en temporal.
     * @param request
     * @return
     */
    private EvnCorreccion negarCorreccion(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);

        //request.getSession().removeAttribute(WebKeys.LISTA_DIFERENCIAS);
        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnCorreccion.NEGAR, EvnCorreccion.NEGAR_WF);

        return evn;
    }

    /**
	 * Método que permite cargar en la pantalla avanza el workflow por éxito en la fase de corrección de documentos.
	 * a diferencia de aprobar se entra en este método cuando hay anotaciones en temporal y a pesar de
	 * esto el corrector desea guardar las anotaciones que estan en temporal.
     * @param request
     * @return
     */
    private EvnCorreccion cargarCambiosPropuestos(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        //Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, EvnCorreccion.CARGAR_CAMBIOS_PROPUESTOS);
        return evn;
    }

    /**
	 * Método que avanza el workflow por exito en la fase de usuario especializado en el proceso de correcciones.
	 * Por este método se hacen definitivos los cambios que estan en temporal.
     * @param request
     * @return
     */
    private EvnCorreccion aprobarCorreccionEspecializado(HttpServletRequest request) {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);

        /*        ValidacionNotaInformativaCorreccionException exception = new ValidacionNotaInformativaCorreccionException();

		        String visibilidad = request.getParameter(CTipoNota.VISIBILIDAD_TIPO_NOTA);
		        String descripcion = request.getParameter(CNota.DESCRIPCION);

		        if (descripcion == null) {
		                exception.addError("Descripción inválida");
		        } else {
		                descripcion = descripcion.trim();
		                if (descripcion.equals("")) {
		                        exception.addError("Descripción inválida");
		                }
		        }
		        if ((visibilidad == null) || visibilidad.equals("SIN_SELECCIONAR")) {
		                exception.addError("Derecho de petición inválido");
		        }

		        if (exception.getErrores().size() > 0) {
		                if (descripcion != null) {
		                        request.getSession().setAttribute(CNota.DESCRIPCION, descripcion.trim());
		                }
		                if (visibilidad != null) {
		                        request.getSession().setAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA, visibilidad);
		                }
		                throw exception;
		        }

		        TipoNota tipoNota = new TipoNota();
		        tipoNota.setDescripcion(descripcion);
		        tipoNota.setVisibilidad(visibilidad);
		        tipoNota.setDevolutiva(true);
		        tipoNota.setProceso(turno.getSolicitud().getProceso());
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(new Date());


		        Nota nota = new Nota();
		        nota.setAnio("" + calendar.get(Calendar.YEAR));
		        nota.setDescripcion(descripcion);
		        nota.setIdTurno(turno.getIdTurno());
		        nota.setTime(new Date());
		        nota.setTiponota(tipoNota);
		        nota.setTurno(turno);
		        nota.setUsuario(turno.getSolicitud().getUsuario());

		        if (turno.getSolicitud().getProceso() != null) {
		                nota.setIdProceso(turno.getSolicitud().getProceso().getIdProceso());
		        }
		        if (turno.getSolicitud().getCirculo() != null) {
		                nota.setIdCirculo(turno.getSolicitud().getCirculo().getIdCirculo());
		        }

		        turno.addNota(nota);
         */
        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnCorreccion.APROBAR_ESPECIALIZADO, EvnCorreccion.CONFIRMAR_WF_ESP);

        return evn;
    }

    /**
	 * Método que avanza el workflow por fracaso en la fase de usuario especializado en el proceso de correcciones.
	 * Por este método se deshacen los cambios que estan en temporal.
     * @param request
     * @return
     */
    private EvnCorreccion negarCorreccionEspecializado(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);

        /*        ValidacionNotaInformativaCorreccionException exception = new ValidacionNotaInformativaCorreccionException();

		        String visibilidad = request.getParameter(CTipoNota.VISIBILIDAD_TIPO_NOTA);
		        String descripcion = request.getParameter(CNota.DESCRIPCION);

		        if (descripcion == null) {
		                exception.addError("Descripción inválida");
		        } else {
		                descripcion = descripcion.trim();
		                if (descripcion.equals("")) {
		                        exception.addError("Descripción inválida");
		                }
		        }
		        if ((visibilidad == null) || visibilidad.equals("SIN_SELECCIONAR")) {
		                exception.addError("Derecho de petición inválido");
		        }

		        if (exception.getErrores().size() > 0) {
		                if (descripcion != null) {
		                        request.getSession().setAttribute(CNota.DESCRIPCION, descripcion.trim());
		                }
		                if (visibilidad != null) {
		                        request.getSession().setAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA, visibilidad);
		                }
		                throw exception;
		        }

		        TipoNota tipoNota = new TipoNota();
		        tipoNota.setDescripcion(descripcion);
		        tipoNota.setVisibilidad(visibilidad);
		        tipoNota.setDevolutiva(true);
		        tipoNota.setProceso(turno.getSolicitud().getProceso());
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(new Date());


		        Nota nota = new Nota();
		        nota.setAnio("" + calendar.get(Calendar.YEAR));
		        nota.setDescripcion(descripcion);
		        nota.setIdTurno(turno.getIdTurno());
		        nota.setTime(new Date());
		        nota.setTiponota(tipoNota);
		        nota.setTurno(turno);
		        nota.setUsuario(turno.getSolicitud().getUsuario());

		        if (turno.getSolicitud().getProceso() != null) {
		                nota.setIdProceso(turno.getSolicitud().getProceso().getIdProceso());
		        }
		        if (turno.getSolicitud().getCirculo() != null) {
		                nota.setIdCirculo(turno.getSolicitud().getCirculo().getIdCirculo());
		        }

		        turno.addNota(nota);
         */
        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnCorreccion.NEGAR_ESPECIALIZADO, EvnCorreccion.NEGAR_WF);

        return evn;
    }

    /**
	 * Método que avanza el workflow por exito en la fase de digitador en el proceso de correcciones.
     * @param request
     * @return
     */
    /**
     * @Autor: Edgar Lora
     * @Mantis: 0013038
     * @Requerimiento: 060_453
     */
    private EvnCorreccion aprobarCorreccionDigitacion(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);

        /*        ValidacionNotaInformativaCorreccionException exception = new ValidacionNotaInformativaCorreccionException();

		        String visibilidad = request.getParameter(CTipoNota.VISIBILIDAD_TIPO_NOTA);
		        String descripcion = request.getParameter(CNota.DESCRIPCION);

		        if (descripcion == null) {
		                exception.addError("Descripción inválida");
		        } else {
		                descripcion = descripcion.trim();
		                if (descripcion.equals("")) {
		                        exception.addError("Descripción inválida");
		                }
		        }
		        if ((visibilidad == null) || visibilidad.equals("SIN_SELECCIONAR")) {
		                exception.addError("Derecho de petición inválido");
		        }

		        if (exception.getErrores().size() > 0) {
		                if (descripcion != null) {
		                        request.getSession().setAttribute(CNota.DESCRIPCION, descripcion.trim());
		                }
		                if (visibilidad != null) {
		                        request.getSession().setAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA, visibilidad);
		                }
		                throw exception;
		        }

		        TipoNota tipoNota = new TipoNota();
		        tipoNota.setDescripcion(descripcion);
		        tipoNota.setVisibilidad(visibilidad);
		        tipoNota.setDevolutiva(true);
		        tipoNota.setProceso(turno.getSolicitud().getProceso());
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(new Date());


		        Nota nota = new Nota();
		        nota.setAnio("" + calendar.get(Calendar.YEAR));
		        nota.setDescripcion(descripcion);
		        nota.setIdTurno(turno.getIdTurno());
		        nota.setTime(new Date());
		        nota.setTiponota(tipoNota);
		        nota.setTurno(turno);
		        nota.setUsuario(turno.getSolicitud().getUsuario());

		        if (turno.getSolicitud().getProceso() != null) {
		                nota.setIdProceso(turno.getSolicitud().getProceso().getIdProceso());
		        }
		        if (turno.getSolicitud().getCirculo() != null) {
		                nota.setIdCirculo(turno.getSolicitud().getCirculo().getIdCirculo());
		        }

		        turno.addNota(nota);
         */
        List folios = turno.getSolicitud().getSolicitudFolios();
        Iterator iFolios = folios.iterator();
        String mensajeError = "";
        ValidacionesSIR validacionesSIR = new ValidacionesSIR();
        while (iFolios.hasNext()) {
            SolicitudFolio sf = (SolicitudFolio) iFolios.next();
            Folio folio = sf.getFolio();
            try {
                String matricula = folio.getIdMatricula();
                String lindero = validacionesSIR.getLinderoDeMatricula(folio.getIdMatricula());
                if (validacionesSIR.esFolioNuevo(turno.getIdWorkflow(), matricula)) {
                    /**
                     * @author: Cesar Ramirez
                             * @change: 1157.111.NO.INCLUIR.LINDEROS.PROPIEDAD.HORIZONTAL
                             * Se cambia el método de de validarLinderos por validarLinderosPrimeraAnotacionDerivada.
                             **/
                    if (validacionesSIR.validarLinderosPrimeraAnotacionDerivada(matricula, CNaturalezaJuridica.NATURALEZA_PARA_VALIDAR_LINDEROS, CTipoAnotacion.DERIVADO)) {
                                String articulo = ("Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012").toUpperCase();
                                if(lindero.indexOf(articulo) != -1){
                            int tamArticulo = lindero.indexOf(articulo) + articulo.length();
                                    if(lindero.length() - tamArticulo < 100){                                    
                                        mensajeError = mensajeError + "Debe añadir minimo 100 caracteres al campo de linderos de la matricula: " + matricula + "<br />";
                            }
                                }else{                                
                                    mensajeError = mensajeError + "El lindero de la matricula " + matricula + " debe tener 100 caracteres a partir del texto \"Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012\", Porfavor no lo borre.<br />";
                        }
                    }
                }
            } catch (GeneralSIRException ex) {
                mensajeError = mensajeError + ex.getMessage() + "<br />";
            }
        }
        if (mensajeError.length() > 0) {
          //  throw new AccionWebException(mensajeError);
        }
        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnCorreccion.APROBAR_DIGITACION, EvnCorreccion.EXITO);

        return evn;
    }

    /**
	 * Método que avanza el workflow por fracaso en la fase de digitador en el proceso de correcciones.
     * @param request
     * @return
     */
    private EvnCorreccion negarCorreccionDigitacion(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);

        /*        ValidacionNotaInformativaCorreccionException exception = new ValidacionNotaInformativaCorreccionException();

		        String visibilidad = request.getParameter(CTipoNota.VISIBILIDAD_TIPO_NOTA);
		        String descripcion = request.getParameter(CNota.DESCRIPCION);

		        if (descripcion == null) {
		                exception.addError("Descripción inválida");
		        } else {
		                descripcion = descripcion.trim();
		                if (descripcion.equals("")) {
		                        exception.addError("Descripción inválida");
		                }
		        }
		        if ((visibilidad == null) || visibilidad.equals("SIN_SELECCIONAR")) {
		                exception.addError("Derecho de petición inválido");
		        }

		        if (exception.getErrores().size() > 0) {
		                if (descripcion != null) {
		                        request.getSession().setAttribute(CNota.DESCRIPCION, descripcion.trim());
		                }
		                if (visibilidad != null) {
		                        request.getSession().setAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA, visibilidad);
		                }
		                throw exception;
		        }

		        TipoNota tipoNota = new TipoNota();
		        tipoNota.setDescripcion(descripcion);
		        tipoNota.setVisibilidad(visibilidad);
		        tipoNota.setDevolutiva(true);
		        tipoNota.setProceso(turno.getSolicitud().getProceso());
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(new Date());


		        Nota nota = new Nota();
		        nota.setAnio("" + calendar.get(Calendar.YEAR));
		        nota.setDescripcion(descripcion);
		        nota.setIdTurno(turno.getIdTurno());
		        nota.setTime(new Date());
		        nota.setTiponota(tipoNota);
		        nota.setTurno(turno);
		        nota.setUsuario(turno.getSolicitud().getUsuario());

		        if (turno.getSolicitud().getProceso() != null) {
		                nota.setIdProceso(turno.getSolicitud().getProceso().getIdProceso());
		        }
		        if (turno.getSolicitud().getCirculo() != null) {
		                nota.setIdCirculo(turno.getSolicitud().getCirculo().getIdCirculo());
		        }

		        turno.addNota(nota);
         */
        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnCorreccion.NEGAR_DIGITACION, EvnCorreccion.FRACASO);

        return evn;
    }

    /**
	 * Método que avanza el workflow por exito en la fase de microfilmación en el proceso de correcciones.
     * @param request
     * @return
     */
    private EvnCorreccion aprobarMicrofilmacion(HttpServletRequest request) {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);

        EvnCorreccion evn = null;
        if (fase.getID().equals(CFase.CAL_MICROFILMADO)) {
            evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnCorreccion.APROBAR_MICROFILMACION, EvnCorreccion.EXITO);
        } else {
            evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnCorreccion.APROBAR_MICROFILMACION, EvnCorreccion.CONFIRMAR_WF);
        }

        return evn;
    }

    /**
	 * Método que avanza el workflow por fracaso en la fase de microfilmación en el proceso de correcciones.
     * @param request
     * @return
     */
    private EvnCorreccion negarMicrofilmacion(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);

        EvnCorreccion evn = null;
        if (fase.getID().equals(CFase.CAL_MICROFILMADO)) {
            evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnCorreccion.NEGAR_MICROFILMACION, EvnCorreccion.FRACASO);
        } else {
            evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnCorreccion.NEGAR_MICROFILMACION, EvnCorreccion.NEGAR_WF);
        }

        return evn;
    }

    /**
	 * Método que permite bloquear los folios en la fase de corrección de docummentos
     * @param request
     * @return
     */
    private EvnCorreccion tomarFoliosCorreccion(HttpServletRequest request) {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        request.getSession().removeAttribute(WebKeys.LISTA_DIFERENCIAS);
        gov.sir.core.negocio.modelo.Usuario usuarioSir;
        usuarioSir = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        /**
         * @author: Fernando Padilla
         * @change: 4574: Acta - Requerimiento No 198 - No carga la pagina, Se
         * elimina este codigo inoficioso.
         */
        /*
		try{
			ProcesadorEventosNegocioProxy proxy = new ProcesadorEventosNegocioProxy();

			Evn_CorrSimpleMain_VerAlertasOptions eventoAlerta;
			eventoAlerta = new Evn_CorrSimpleMain_VerAlertasOptions(  Evn_CorrSimpleMain_VerAlertasOptions.CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART__EVENT );

			eventoAlerta.setUsuarioAuriga( usuarioAuriga ); 
			eventoAlerta.setUsuarioSir( usuarioSir );       
			eventoAlerta.setTurno( turno );   

			EvnResp_CorrSimpleMain_VerAlertasOptions respuesta = 
				(EvnResp_CorrSimpleMain_VerAlertasOptions)proxy.manejarEvento(
						eventoAlerta);

			List lstFoliosSinCambios = respuesta.getFoliosInTurnoSinCambiosList();
			if(lstFoliosSinCambios!=null && lstFoliosSinCambios.size()>0){
				request.getSession().setAttribute("GENERAR_ALERTA_SIN_CAMBIOS", "TRUE");
			}
		}catch (Exception e) {
			// TODO: handle exception
		}*/

        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, EvnCorreccion.TOMAR_FOLIO_CORRECCION);

        return evn;
    }

    /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  metodo para tomar el turno de testamento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
     */
    /**
	 * Método que permite tomar el turno de testamentos asociado al turno
     * @param request
     * @return EvnCorreccion
     */
    private EvnCorreccion tomarTurnoTestamento(HttpServletRequest request) {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        request.getSession().removeAttribute(WebKeys.LISTA_DIFERENCIAS);
        return null;
    }

    /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  metodo para editar el testamento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
     */
    /**
	 * Método que permite editar el testamento
     * @param request
     * @return EvnCorreccion
     */
    private EvnCorreccion editarTestamento(HttpServletRequest request) {
        return null;
    }

    /**
	 * Método que permite bloquear los folios en la fase de usuario especialiado
     * @param request
     * @return
     */
    private EvnCorreccion tomarFoliosEspecializado(HttpServletRequest request) {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        request.getSession().removeAttribute(WebKeys.LISTA_DIFERENCIAS);

        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, EvnCorreccion.TOMAR_FOLIO_ESPECIALIZADO);

        return evn;
    }

    /**
	 * Método que permite bloquear los folios en la fase de digitación
     * @param request
     * @return
     */
    private EvnCorreccion tomarFoliosDigitacion(HttpServletRequest request) {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        request.getSession().removeAttribute(WebKeys.LISTA_DIFERENCIAS);

        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, EvnCorreccion.TOMAR_FOLIO_DIGITACION);

        return evn;
    }

    /**
	 * Método que permite editar los folios en digitación masiva.
     * @param request
     * @return
     */
    private EvnCorreccion editarFolios(HttpServletRequest request) {
        //String folio = request.getParameter(CFolio.ID_MATRICULA);
        return null;
    }

    /**
	 * Método que permite delegar la corrección a otras roles. Estos pueden ser
     * microfilmador, digitador, antiguo sistema, y usuario especializado.
     *
     * @param request
     * @return
     */
    private EvnCorreccion delegarCorreccion(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        String delegar = request.getParameter(AWCorreccion.DELEGAR_CASO);
        String avazarWF = "";

        if (delegar != null) {
            if (delegar.equals(AWCorreccion.DELEGAR_CASO_ANTIGUO_SISTEMA)) {
                avazarWF = EvnCorreccion.ANTIGUO_SISTEMA;
            }

            if (delegar.equals(AWCorreccion.DELEGAR_CASO_DIGITACION)) {
                avazarWF = EvnCorreccion.DIGITACION;
            }

            if (delegar.equals(AWCorreccion.DELEGAR_CASO_ESPECIALIZADO)) {
                avazarWF = EvnCorreccion.ESPECIALIZADO;
            }

            if (delegar.equals(AWCorreccion.DELEGAR_CASO_MICROFILMACION)) {
                avazarWF = EvnCorreccion.MICROFILMS;
            }
        }

        request.getSession().removeAttribute(AWCorreccion.DELEGAR_CASO);
        //request.getSession().removeAttribute(WebKeys.LISTA_DIFERENCIAS);

        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnCorreccion.DELEGAR_CASO, avazarWF);

        return evn;
    }

    /**
	 * Método que permite devolver el caso al usuario que lo había delegado.
     * @param request
     * @return
     */
    private EvnCorreccion devolverCorreccion(HttpServletRequest request) {
        return null;
    }

    /**
	 * Método que envia el turno de correcciones a pago por mayor valor.
     * @param request
     * @return
     */
    private EvnCorreccion redireccionarCorreccion(HttpServletRequest request) {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnCorreccion.REDIRECCIONAR_CASO, EvnCorreccion.MAYOR_VALOR);

        return evn;
    }

    /**
	 * Método que permite imprimir los certificados.
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnCorreccion imprimirCertificados(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
        Circulo cir = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        String impresora = request.getParameter(WebKeys.IMPRESORA);

        //Solicitud solicitud = (Solicitud) turno.getSolicitud();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        //se saca la informacion del turno padre para saber si la correccion es interna o externa
        Turno turnoPadre = (Turno) session.getAttribute(WebKeys.TURNO_PADRE);

        EvnCorreccion eventoCorreccion = new EvnCorreccion(usuario, null, turno, fase, estacion, EvnCorreccion.IMPRIMIR);
        eventoCorreccion.setCirculoId(cir.getIdCirculo());
        eventoCorreccion.setImpresora(impresora);
        eventoCorreccion.setTurnoPadre(turnoPadre);
        return eventoCorreccion;
    }

    /**
	 * Método que permite imprimir los certificados seleccionados.
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnCorreccion imprimirCertificadosIndividual(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Circulo cir = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
        //Solicitud solicitud = (Solicitud) turno.getSolicitud();
        String impresora = request.getParameter(WebKeys.IMPRESORA);
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        List folios = new ArrayList();

        String matriculasImp = request.getParameter(WebKeys.TITULO_CHECKBOX_ELIMINAR);
        if (matriculasImp != null) {
            Folio folio = new Folio();
            folio.setIdMatricula(matriculasImp);
            folios.add(folio);
        }
        if (folios.size() > 0) {
            EvnCorreccion eventoCorreccion = new EvnCorreccion(usuario, folios, turno, fase, estacion, EvnCorreccion.IMPRIMIR_INDIVIDUAL);
            eventoCorreccion.setCirculoId(cir.getIdCirculo());
            eventoCorreccion.setImpresora(impresora);
            eventoCorreccion.setUsuarioSIR(usuarioSIR);
            return eventoCorreccion;
        } else {
            return null;
        }

    }

    /**
	 * Método que avanza el workflow de la fase de impresión.
     * @param request
     * @return
     */
    private EvnCorreccion avanzarImprimir(HttpServletRequest request) {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnCorreccion.AVANZAR_IMPRIMIR, EvnCorreccion.EXITO);
        evn.setUsuarioSIR(usuario);

        return evn;
    }

    /**
	 * Método que avanza el wrkflow por éxito en la fase de entrega.
     * @param request
     * @return
     */
    private EvnCorreccion notificarCiudadanoExito(HttpServletRequest request) {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnCorreccion.NOTIFICAR_CIUDADANO, EvnCorreccion.CONFIRMAR_WF);

        return evn;
    }

    /**
	 * Método que avanza el worlflow por fracaso en la fase de entrega.
     * @param request
     * @return
     */
    private EvnCorreccion notificarCiudadanoFracaso(HttpServletRequest request) {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnCorreccion.NOTIFICAR_CIUDADANO, EvnCorreccion.FRACASO);

        return evn;
    }

    private void
            doEndCorr_RevisionAprobacion_PrintForm(HttpServletRequest request, EvnRespCorreccion evento) {

        HttpSession session;
        session = request.getSession();

        // session data -----------------------------------------------------------------
        // ---------------------------------------------------------------------------------
        session.setAttribute(AWCorreccion.APROBAR, evento.getTipoEvento());

        session.setAttribute(AWCorreccion.IMPRIMIO_FORMULARIO_OK, "OK");

        // request data -----------------------------------------------------------------
        // send-message -----------------------------------------------------------------
        // ---------------------------------------------------------------------------------
    } // end-method: doEndProcess_CorreccionSimpleMain_VerAlertasOptions_Step0_BtnStart

    /*
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
     */
    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {

        HttpSession session = request.getSession();

        EvnRespCorreccion respuesta = (EvnRespCorreccion) evento;

        if (respuesta != null) {

            String local_TipoEvento;
            local_TipoEvento = (null != respuesta.getTipoEvento()) ? (respuesta.getTipoEvento()) : ("");

            // bug 3536
            if (EvnRespCorreccion.PAGEEVENT_CORRECCIONREVISARAPROBAR_DESHACERCAMBIOS_EVENTRESPOK.equals(local_TipoEvento)) {
                String local_ResultMessage = "Cambios Eliminados Satisfactoriamente";
                session.setAttribute("LOCAL_RESULT:MESSAGE", local_ResultMessage);
            } // if

            if (EvnRespCorreccion.CORR_REVISIONAPROBACION__PRINTFORM_EVENTRESP_OK.equals(local_TipoEvento)) {
                // compatibility
                doEndCorr_RevisionAprobacion_PrintForm(request, respuesta);
            } // if

            if (respuesta.getTipoEvento().equals(EvnRespCorreccion.APROBAR)
                    || respuesta.getTipoEvento().equals(EvnRespCorreccion.PREGUNTAR_APROBACION)
                    || respuesta.getTipoEvento().equals(EvnRespCorreccion.REDIRECCIONAR_CASO)) {

                request.getSession().setAttribute(AWCorreccion.APROBAR, respuesta.getTipoEvento());
            }

            if (respuesta.getTipoEvento().equals(EvnRespCorreccion.TOMAR_FOLIO)) {
                request.getSession().setAttribute(WebKeys.FOLIOS_BLOQUEADOS, respuesta.getLlaveBloqueo());
            }

            if (respuesta.getTipoEvento().equals(EvnRespCorreccion.APROBAR_ESPECIALIZADO)) {
                request.getSession().setAttribute(WebKeys.FOLIOS_BLOQUEADOS, respuesta.getLlaveBloqueo());
            }

            if (respuesta.getTipoEvento().equals(EvnRespCorreccion.IMPRIMIR)) {
                List foliosNoImpresos = respuesta.getFoliosNoImpresos();

                List foliosStr = new ArrayList();

                Iterator iter = foliosNoImpresos.iterator();
                while (iter.hasNext()) {
                    foliosStr.add(((SolicitudFolio) iter.next()).getIdMatricula());
                }

                request.getSession().setAttribute(FOLIOS_NO_IMPRESOS, foliosStr);
            }

            if (respuesta.getTipoEvento().equals(EvnRespCorreccion.CARGAR_CAMBIOS_PROPUESTOS)) {
                List diferencias = new ArrayList();
                List deltas = respuesta.getDeltasFolio();
                Iterator it = deltas.iterator();
                /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  Se edita el algorimo para mostra los cambios propuestos
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
                 */
                while (it.hasNext()) {
                    Object deltaT = it.next();
                    DeltaFolio delta = null;
                    DeltaTestamento deltaTesta = null;
                    if (deltaT instanceof DeltaFolio) {
                        delta = (DeltaFolio) deltaT;
                    } else {
                        deltaTesta = (DeltaTestamento) deltaT;
                    }
                    List diferenciasTMP = null;
                    if (delta != null) {
                        diferenciasTMP = delta.getDiferencias();
                    } else if (deltaTesta != null) {
                        diferenciasTMP = deltaTesta.getDiferencias();
                    } else {
                        diferenciasTMP = new ArrayList();
                    }

                    diferencias.addAll(diferenciasTMP);
                }
                request.getSession().setAttribute(WebKeys.LISTA_DIFERENCIAS, diferencias);
            }

        } else {
            if (this.accion.equals(PAGE_REGION__STARTOPCIONESDIGITACIONMASIVA_ACTION)) {
                gov.sir.core.negocio.modelo.Turno turno
                        = (gov.sir.core.negocio.modelo.Turno) session.getAttribute(gov.sir.core.web.WebKeys.TURNO);

                java.util.List modelPermisosList = null;

                if (turno.getSolicitud() instanceof SolicitudCorreccion) {
                    SolicitudCorreccion solicitudCorreccion
                            = (SolicitudCorreccion) turno.getSolicitud();
                    modelPermisosList = solicitudCorreccion.getPermisos();
                }

                if (turno.getSolicitud() instanceof SolicitudRegistro) {
                    SolicitudRegistro solicitudCorreccion
                            = (SolicitudRegistro) turno.getSolicitud();
                    modelPermisosList = solicitudCorreccion.getPermisos();
                }

                session.setAttribute(gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants.PARAM__MODEL_PERMISOS_LIST, modelPermisosList);
            }
        }
    }

    private Evento
            doCorr_PageEvent_StartOpcionesDigitacionMasiva(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session;
        session = request.getSession();

        final String PAGEITEM_OPCIONESDIGITACIONMASIVA_FROMPROCESS
                = "PAGEITEM_OPCIONESDIGITACIONMASIVA_FROMPROCESS";

        String local_ProcessId = CProceso.PROCESO_CORRECCIONES;
        // Correcciones (Session Marks For SharedProcess)
        session.setAttribute(PAGEITEM_OPCIONESDIGITACIONMASIVA_FROMPROCESS, local_ProcessId);

        // Build Message
        //Evento result;
        //result = segregarAnotacion( request );
        return null;

    } // end-method: doCorr_PageEvent_StartOpcionesDigitacionMasiva

    private Evento
            doProcess_BtnDeshacerCambios(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        // the exception collector
        ValidacionParametrosException exception;
        exception = new ValidacionParametrosException();

        // session data -----------------------------------------------------------------
        org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;
        param_UsuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        gov.sir.core.negocio.modelo.Usuario param_UsuarioSir;
        param_UsuarioSir = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        Circulo param_Circulo;
        param_Circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        Turno param_Turno;
        param_Turno = (Turno) session.getAttribute(WebKeys.TURNO);

        Fase param_Fase;
        param_Fase = (Fase) session.getAttribute(WebKeys.FASE);

        // build-message -----------------------------------------------------------------
        EvnCorreccion local_Result;
        local_Result = new EvnCorreccion(EvnCorreccion.PAGEEVENT_CORRECCIONREVISARAPROBAR_DESHACERCAMBIOS);

        // get-set injection
        local_Result.setUsuarioAuriga(param_UsuarioAuriga);
        local_Result.setUsuarioSir(param_UsuarioSir);
        local_Result.setTurno(param_Turno);
        local_Result.setFase(param_Fase);
        local_Result.setRespuestaWF(EvnCorreccion.DEVOLVERAEVISIONANALISIS_WF);
        // ---------------------------------------------------------------------------------

        // send-message -----------------------------------------------------------------
        return local_Result;
        // ---------------------------------------------------------------------------------
    } // end-method: doProcess_BtnDeshacerCambios

} // end-class

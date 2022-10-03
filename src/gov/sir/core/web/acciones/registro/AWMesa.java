package gov.sir.core.web.acciones.registro;

import gov.sir.core.eventos.comun.EvnRespTurno;
import gov.sir.core.eventos.registro.EvnMesaControl;
import gov.sir.core.eventos.registro.EvnRespMesaControl;
import gov.sir.core.negocio.modelo.Acto;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CSolicitudFolio;
import gov.sir.core.negocio.modelo.constantes.CTipoActo;
import gov.sir.core.negocio.modelo.util.IDidworkflowComparator;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWRelacion;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ImpresionCertificadosAsociadosException;
import gov.sir.core.web.acciones.excepciones.ListarException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosMesaControlException;
import gov.sir.hermod.HermodService;
import gov.sir.hermod.dao.impl.jdogenie.JDOTurnosDAO;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * @author pmunoz, ddiaz
 */
public class AWMesa extends SoporteAccionWeb {

    //ACCIONES
    /**
     * Acción que se esta procesando
     */
    private String accion;
    /**
     * constante para asociados
     */
    public static final String ASOCIADOS = "ASOCIADOS";

    /**
     * constante para mesa de control
     */
    public static final String MESA_CONTROL = "MESA_CONTROL";

    /**
     * Variable que identifica la accion de consultar turnos dado un rango en la
     * fase de mesa control 1
     */
    public static final String CONSULTAR_TURNOS = "CONSULTAR_TURNOS";

    /**
     * Variable que identifica la accion de enviar la respuesta en la fase de
     * mesa control 1
     */
    public static final String ENVIAR_RESPUESTA = "ENVIAR_RESPUESTA";

    /**
     * Variable que identifica la accion de enviar a revision calificacion en la
     * fase de mesa control 1
     */
    public static final String DEVOLVER_REVISION = "DEVOLVER_REVISION";

    /**
     * Variable que identifica la accion de imprimir un certificado asociado
     * para mesa de control 2
     */
    public static final String IMPRIMIR_CERTIFICADO = "IMPRIMIR_CERTIFICADO";

    /**
     * Variable que identifica la acción de imprimir un certificado asociado por
     * relaciones para mesa de control 2
     */
    public static final String IMPRIMIR_CERTIFICADO_RELACION = "IMPRIMIR_CERTIFICADO_RELACION";

    /**
     * Variable que identifica la accion de cambiar el # de matricula de un
     * certificado asociado para mesa de control 2
     */
    public static final String CAMBIAR_MATRICULA = "CAMBIAR_MATRICULA";

    /**
     * Variable que identifica la accion de cambiar el # de matricula de un
     * certificado asociado para mesa de control 2 que se hace por relación
     */
    public static final String CAMBIAR_MATRICULA_RELACION = "CAMBIAR_MATRICULA_RELACION";

    /**
     * Variable que identifica la accion de consultar la información asociada a
     * una relación
     */
    public static final String CONSULTAR_RELACION = "CONSULTAR_RELACION";

    /**
     * Variable que identifica la accion salir de la fase
     */
    public static final String TERMINAR = "TERMINAR";

    /**
     * Variable que identifica la accion salir de la fase con resultado
     * confirmar
     */
    public static final String AVANZAR_MESA_II = "AVANZAR_MESA_II";

    /**
     * Variable que identifica la accion salir de la fase con resultado negar
     */
    public static final String NEGAR_MESA_II = "NEGAR_MESA_II";

    /**
     * Variable que identifica la accion para validar las solicitudes de
     * certificado
     */
    public static final String VALIDAR_SOLICITUDES = "VALIDAR_SOLICITUDES";

    /**
     * Variable que identifica la accion de cargar la página para cambiar la
     * matricula
     */
    public static final String CARGAR_CAMBIAR_MATRICULA = "CARGAR_CAMBIAR_MATRICULA";

    //CONSTANTES
    /**
     * Variable que identifica la constantes donde se va guardar el turno desde
     * el que se hara la consulta
     */
    public static final String TURNO_DESDE = "TURNO_DESDE";

    /**
     * Variable que identifica la constantes donde se va guardar el turno hasta
     * el que se hara la consulta
     */
    public static final String TURNO_HASTA = "TURNO_HASTA";

    /**
     * Variable que identifica la constante donde se guarda cual fue la
     * respuesta en la fase de mesa control 1
     */
    public static final String RESPUESTA = "RESPUESTA";

    /**
     * Variable que identifica la constantes donde se va guardar la lista de
     * turnos devueltas de la consulta
     */
    public static final String LISTA_TURNOS_CONSULTA = "LISTA_TURNOS_CONSULTA";

    /**
     * Variable que identifica la constantes donde se va guardar la lista de
     * certificados asociados devueltas de la consulta
     */
    public static final String LISTA_CERTIFICADOS_ASOCIADOS = "LISTA_CERTIFICADOS_ASOCIADOS";

    /**
     * Variable que identifica la constantes donde se va guardar el número de
     * certificados que faltan imprimir
     */
    public static final String CERTIFICADOS_A_IMPRIMIR = "CERTIFICADOS_A_IMPRIMIR";

    /**
     * Variable que identifica la constantes donde se va guardar el número de
     * certificados que faltan imprimir
     */
    public static final String CERTIFICADOS_A_CAMBIAR_MATRICULA = "CERTIFICADOS_A_CAMBIAR_MATRICULA";

    /**
     * Variable que identifica la constantes donde se va guardar si el
     * certificado esa valido o no para imprimir
     */
    public static final String LISTA_VALIDO = "LISTA_VALIDO";

    /**
     * Variable que identifica la constantes donde se va guardar el numero de
     * orden certificado asociado, para realizar correctamente la accion
     * respectiva
     */
    public static final String NUM_CERT = "NUM_CERT";

    /**
     * Variable que identifica la constantes donde se va guardar el numero de
     * turno de registro para un certificado asociado
     */
    public static final String NUM_REG = "NUM_REG";

    /**
     * Variable que identifica la constantes donde se va guardar el numero de
     * orden de la matricula, para realizar correctamente la accion respectiva
     */
    public static final String NUM_MAT = "NUM_MAT";

    /**
     * Variable que identifica la constantes donde se va guardar año actual,
     * para realizar correctamente la accion respectiva
     */
    public static final String ANIO = "ANIO";

    /**
     * Variable que identifica la constante de la fase necesaria para saber si
     * el turno es devuelto o de mayor valor
     * <p>
     * Necesaria para la validacion del comboBox en mesa de control I
     */
    public static final String CAL_CALIFICACION = "CAL_CALIFICACION";

    /**
     * Variable que identifica la constante de la fase de testamentos para saber
     * su respuesta
     */
    public static final String REG_TESTAMENTO = "REG_TESTAMENTO";

    /**
     * Variable que identifica la constante de la respuesta que identifica que
     * el turno es de mayor valor
     */
    public static final String MAYOR_VALOR = "MAYOR_VALOR";

    /**
     * Variable que identifica la constante de la respuesta que identifica que
     * el turno es de inscripcion parcial
     */
    public static final String INSCRIPCION_PARCIAL = "INSCRIPCION_PARCIAL";

    /**
     * Variable que identifica la constante de la respuesta que identifica que
     * el turno es de devolucion
     */
    public static final String DEVOLUCION = "DEVOLUCION";

    /**
     * Variable que identifica la constante de la respuesta que identifica que
     * el turno en testamentos fue inscrito
     */
    public static final String CONFIRMAR = "CONFIRMAR";

    /**
     * Variable que identifica la constante de la respuesta que identifica que
     * el turno es termino ok la calificacion
     */
    public static final String OK_CALIFICACION = "OK";

    /**
     * Variable que identifica la constante que identifica al proceso de
     * registro para la fase de mesa de control I
     */
    public static final String ID_PROCESO_REGISTRO = "6";

    /**
     * Variable que identifica la constante que identifica al nuevo numero de
     * matricula para la fase de mesa de control II
     */
    public static final String NUEVO_NUM_MAT = "NUEVO_NUM_MAT";

    /**
     * Variable que identifica la constante que identifica la lista de las
     * solicitudes folio asociadas al turno en fase de mesa de control II
     */
    public static final String LIST_SOL_FOLIO_ASOCIADAS = "LIST_SOL_FOLIO_ASOCIADAS";

    /**
     * Variable que identifica la constante que identifica la lista de las
     * matriculas asociadas turno en fase de mesa de control II
     */
    public static final String LIST_MATRICULAS_ASOCIADAS = "LIST_MATRICULAS_ASOCIADAS";

    /**
     * Variable que identifica que retornan un turno a testamento para
     * corrección Modifica Pablo Quintana Junio 19 2008
     */
    public static final String DEVOLVER_TESTAMENTO = "DEVOLVER_TESTAMENTO";

    /**
     * Crea una instancia de la accion web
     */
    public AWMesa() {
        super();
    }

    /* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#perform(javax.servlet.http.HttpServletRequest)
     */
    public Evento perform(HttpServletRequest request)
            throws AccionWebException {

        accion = request.getParameter(WebKeys.ACCION);

        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion valida");
        }

        if (accion.equals(AWMesa.ASOCIADOS)) {
            return asociados(request);
        } else if (accion.equals(AWMesa.CONSULTAR_TURNOS)) {
            return consultarTurnos(request);
        } else if (accion.equals(AWMesa.ENVIAR_RESPUESTA)) {
            return enviarRespuesta(request);
        } else if (accion.equals(AWMesa.DEVOLVER_REVISION)) {
            return devolverRevision(request);
        } else if (accion.equals(AWMesa.VALIDAR_SOLICITUDES)) {
            return validarCertificados(request);
        } else if (accion.equals(AWMesa.IMPRIMIR_CERTIFICADO)) {
            return imprimirCertificado(request);
        } else if (accion.equals(AWMesa.IMPRIMIR_CERTIFICADO_RELACION)) {
            return imprimirCertificadoRelacion(request);
        } else if (accion.equals(AWMesa.AVANZAR_MESA_II)) {
            return avanzarMesaII(request);
        } else if (accion.equals(AWMesa.NEGAR_MESA_II)) {
            return negarMesaII(request);
        } else if (accion.equals(AWMesa.CAMBIAR_MATRICULA)) {
            return cambiarMatricula(request);
        } else if (accion.equals(AWMesa.CAMBIAR_MATRICULA_RELACION)) {
            return cambiarMatriculaRelacion(request);
        } else if (accion.equals(AWMesa.CARGAR_CAMBIAR_MATRICULA)) {
            return determinarMatriculasCertificadosAsociados(request);
        } else if (accion.equals(AWMesa.CONSULTAR_RELACION)) {
            return consultarRelacion(request);
        }/**
         * Devolver testamento. Pablo Quintana Junio 19 2008
         */
        else if (accion.equals(AWMesa.DEVOLVER_TESTAMENTO)) {
            return devolverTestamento(request);
        } else {
            throw new AccionInvalidaException("La accion " + accion
                    + " no es valida.");
        }
    }

    /**
     * Método que recibe httpRequest sus atributos y los valida y crea un
     * EvnMesa despues de validarlos del metodo consultarTurnos las anotaciones
     * de un folio
     *
     * @param request
     * @return evento EventoMesaControl
     * @throws AccionWebException
     */
    private Evento consultarTurnos(HttpServletRequest request) throws AccionWebException {
        /*preparar excepcion*/
        ValidacionParametrosMesaControlException exception
                = new ValidacionParametrosMesaControlException();

        /*obtener datos de la session para efectuar la consulta*/
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        String TurnoDesde = "";
        String TurnoHasta = "";

        //obtener año
        String ano = request.getParameter(AWMesa.ANIO);
        if (ano == null || ano.equals("")) {
            exception.addError("El turno desde que se va hacer la consulta es invalido o vacio");
        }
        TurnoDesde += ano;
        TurnoHasta += ano;

        //obtener circulo		
        Circulo cir = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        String scir = cir.getIdCirculo();
        if (scir == null || scir.equals("")) {
            exception.addError("El turno desde que se va hacer la consulta es invalido o vacio");
        }
        TurnoDesde += "-" + scir;
        TurnoHasta += "-" + scir;

        //obtener proceso registro
        String idProceso = AWMesa.ID_PROCESO_REGISTRO;
        TurnoDesde += "-" + idProceso;
        TurnoHasta += "-" + idProceso;

        //obtener turno desde
        String numDesde = request.getParameter(AWMesa.TURNO_DESDE);
        if (numDesde == null || numDesde.equals("")) {
            exception.addError("El turno desde que se va hacer la consulta es invalido o vacio");
        }

        String circDesde = "";
        int anioDesde = 0;
        int idTurnoDesde = 0;

        String[] datosTurno = numDesde.split("-");
        if (datosTurno.length != 4) {
            exception.addError("El turno desde que se va hacer la consulta es invalido");
        } else {
            try {
                anioDesde = Integer.parseInt(datosTurno[0]);
                circDesde = datosTurno[1];
                Long.parseLong(datosTurno[2]);
                idTurnoDesde = Integer.parseInt(datosTurno[3]);
            } catch (NumberFormatException e) {
                exception.addError("El turno desde que se va hacer la consulta es invalido");
            }
            if (!circDesde.equals(cir.getIdCirculo())) {
                exception.addError("El turno desde no pertenece al circulo del usuario");
            }
        }
        TurnoDesde = numDesde;
        //agregar turno hasta
        String numHasta = request.getParameter(AWMesa.TURNO_HASTA);
        if (numHasta == null || numHasta.equals("")) {
            exception.addError("El turno hasta que se va hacer la consulta es invalido o vacio");
        }

        String circHasta = "";
        int anioHasta = 0;
        int idTurnoHasta = 0;
        datosTurno = numHasta.split("-");
        if (datosTurno.length != 4) {
            exception.addError("El turno hasta que se va hacer la consulta es invalido");
        } else {
            try {
                anioHasta = Integer.parseInt(datosTurno[0]);
                circHasta = datosTurno[1];
                Long.parseLong(datosTurno[2]);
                idTurnoHasta = Integer.parseInt(datosTurno[3]);
            } catch (NumberFormatException e) {
                exception.addError("El turno hasta que se va hacer la consulta es invalido");
            }
            if (!circHasta.equals(cir.getIdCirculo())) {
                exception.addError("El turno hasta no pertenece al circulo del usuario");
            }
        }
        TurnoHasta = numHasta;
        try {
            if (anioDesde > anioHasta) {
                exception.addError("rango invalido");
            } else if (anioDesde == anioHasta) {
                if (idTurnoDesde > idTurnoHasta) {
                    exception.addError("rango invalido");
                }
            }
        } catch (NumberFormatException e) {
            exception.addError("rango invalido, por favor insertar numeros.");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        session.setAttribute(AWMesa.TURNO_DESDE, numDesde);
        session.setAttribute(AWMesa.TURNO_HASTA, numHasta);

        EvnMesaControl evento = new EvnMesaControl(usuario, EvnMesaControl.CONSULTAR_TURNOS);
        evento.setTurnoDesde(TurnoDesde);
        evento.setTurnoHasta(TurnoHasta);
        evento.setFase(fase);
        return evento;
    }

    /**
     * Método que recibe httpRequest sus atributos y los valida y crea un
     * EvnMesa despues de validarlos del metodo enviarRespuesta las anotaciones
     * de un folio
     *
     * @param request
     * @return evento EventoMesaControl
     * @throws AccionWebException
     */
    private Evento enviarRespuesta(HttpServletRequest request) throws AccionWebException {
        /*preparar excepcion*/
        ValidacionParametrosMesaControlException exception
                = new ValidacionParametrosMesaControlException();

        /*obtener datos de la session para efectuar la consulta*/
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        String respuesta;
        respuesta = request.getParameter(AWMesa.RESPUESTA);
        if (respuesta == null || respuesta.equals("SIN_SELECCIONAR")) {
            exception.addError("Tiene que colocar una respuesta valida");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        EvnMesaControl evento = new EvnMesaControl(usuario, EvnMesaControl.ENVIAR_RESPUESTA);
        evento.setRespuesta(respuesta);
        evento.setTurno(turno);
        evento.setFase(fase);
        evento.setUsuarioNeg(usuarioNeg);
        return evento;
    }

    /**
     * Método que recibe httpRequest sus atributos y los valida y crea un
     * EvnMesa despues de validarlos del metodo enviarRespuesta las anotaciones
     * de un folio
     *
     * @param request
     * @return evento EventoMesaControl
     * @throws AccionWebException
     */
    private Evento devolverRevision(HttpServletRequest request) throws AccionWebException {
        /*preparar excepcion*/
        ValidacionParametrosMesaControlException exception
                = new ValidacionParametrosMesaControlException();

        /*obtener datos de la session para efectuar la consulta*/
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        EvnMesaControl evento = new EvnMesaControl(usuario, EvnMesaControl.DEVOLVER_REVISION);
        evento.setTurno(turno);
        evento.setFase(fase);
        evento.setUsuarioNeg(usuarioNeg);
        return evento;
    }

    /**
     * Método que recibe httpRequest sus atributos y los valida y crea un
     * EvnMesa despues de validarlos del metodo consultarCertAsociados las
     * anotaciones de un folio
     *
     * @param request
     * @return evento EventoMesaControl
     * @throws AccionWebException
     */
    private Evento imprimirCertificado(HttpServletRequest request) throws AccionWebException, ImpresionCertificadosAsociadosException {

        /*preparar excepcion*/
        ValidacionParametrosMesaControlException exception
                = new ValidacionParametrosMesaControlException();

        /*obtener datos de la session para efectuar la consulta*/
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);

        List listCert = new Vector();
        //obtener la lista de certificados del session
        listCert = (List) session.getAttribute(AWMesa.LISTA_CERTIFICADOS_ASOCIADOS);

        /*obtener parametros para la impresion*/
        List solicitudesAImprimir = new ArrayList();
        String[] llavesSol = request.getParameterValues(AWMesa.NUM_CERT);

        /*comprobacion de datos*/
        //LANZA EXCEPCIÓN SI NO HAY CERTIFICADOS ASOCIADOS EN EL TURNO.
        if (listCert.size() == 0) {
            exception.addError("Este turno no tiene certificados asociados.");
        }

        if (llavesSol == null) {
            exception.addError("No ha selecccionado ningun certificado asociado para imprimir.");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        SolicitudRegistro solicitud = (SolicitudRegistro) turno.getSolicitud();

        List historials = turno.getHistorials();
        String respHist = "";

        boolean devolucionEmbargo = false;

        for (int i = historials.size() - 1; i > 0; i--) {
            TurnoHistoria hist = (TurnoHistoria) historials.get(i);
            if (hist.getFase().equals(CFase.CAL_CALIFICACION)) {
                respHist = hist.getRespuesta();
                break;
            }
        }

        if (respHist != null && respHist.equals(CRespuesta.DEVOLUCION)) {
            LiquidacionTurnoRegistro liq = (LiquidacionTurnoRegistro) solicitud.getLiquidaciones().get(0);
            List actos = liq.getActos();
            for (int i = 0; i < actos.size(); i++) {
                Acto acto = (Acto) actos.get(i);
                if (acto.getTipoActo().getNombre().equals(CTipoActo.ACTO_EMBARGO)) {
                    devolucionEmbargo = true;
                }
            }
        }

        /*obtener el certificado asociado a imprimir a traves de los datos */
        for (int i = 0; i < llavesSol.length; i++) {
            SolicitudCertificado sol = new SolicitudCertificado();
            String sIndice = llavesSol[i];
            if (sIndice != null && !sIndice.equals("")) {
                String mat = "";
                try {
                    int indice = Integer.parseInt(sIndice);
                    sol = (SolicitudCertificado) listCert.get(indice);
                    System.out.println("Parcial Solicitud "+sol+" turno "+turno);
                    if (validaInscritoParcial(sol, turno)) {
                        System.out.println("Validacion inscrito parcial");
                    }else 
                    if (!this.teniaDatosTMP(sol, turno) && !devolucionEmbargo) {
                        mat = ((SolicitudFolio) sol.getSolicitudFolios().get(0)).getIdMatricula();
                        throw new Throwable("No hubo modificaciones en el folio seleccionado");
                    }
                    solicitudesAImprimir.add(sol);
                } catch (Throwable t) {
                    ValidacionParametrosMesaControlException err = new ValidacionParametrosMesaControlException();
                    err.addError("No hubo modificaciones en el folio " + mat
                            + ", Por lo tanto, no se permite la impresión del certificado asociado");
                    throw err;
                }
                //se añade a la lista de solicitudes ya impresas para ser inahabilitadas en el jsp.
            }
        }

        //SI EL TURNO TIENE CERTIFICADOS ASOCIADOS Y NO SE HA SELCCIONADO CERTIFICADOS A IMPRIMIR
        //SE REALIZAN LAS SICUIENTES VALIDACIONES
        if (solicitudesAImprimir.size() == 0) {
            ImpresionCertificadosAsociadosException exc = new ImpresionCertificadosAsociadosException();

            String certificadosAImprimir = request.getParameter(AWMesa.CERTIFICADOS_A_IMPRIMIR);
            String certificadosACambiarMat = request.getParameter(AWMesa.CERTIFICADOS_A_CAMBIAR_MATRICULA);

            //VALIDACIÓN 1. LOS CERTIFICADOS ASOCIADOS YA FUERON IMPRESOS.
            if (certificadosAImprimir == null || new Integer(certificadosAImprimir).intValue() == 0) {
                exc.addError("Los certificados asociados a este turno ya fueron impresos.");

                //VALIDACIÓN 2. LOS CERTIFICADOS ASOCIADOS DEBEN CAMBIARSE LA MATRÍCULA.					
            } else if (certificadosACambiarMat != null && new Integer(certificadosACambiarMat).intValue() > 0) {
                exc.addError("Debe seleccionar los certificados asociados a imprimir, recuerde cambiar la matrícula a aquellos certificados asociados que aún no la tienen.");

                //VALIDACIÓN 3. SE DEBE SELECCIONAR LOS CERTIFICADOS ASOCIADOS A IMPRIMIR.		
            } else {
                exc.addError("Seleccione los certificados que desea imprimir.");
            }

            throw exc;
        }

        //obtener el path de las firmas
        context = session.getServletContext();
        String pathFirmas = null;

        File file = (File) context.getAttribute(WebKeys.FIRMAS_DIRECTORY);
        if (file != null) {
            pathFirmas = file.getPath();

        } else {
            pathFirmas = "C:";
        }

        EvnMesaControl evento = new EvnMesaControl(usuario, EvnMesaControl.IMPRIMIR_CERTIFICADO);
        evento.setSolicitudesCertificado(solicitudesAImprimir);
        evento.setTurno(turno);
        evento.setFase(fase);
        evento.setEstacion(estacion);
        evento.setUsuarioNeg(usuarioNeg);
        evento.setPathFirmas(pathFirmas);
        return evento;
    }

    /**
     * Método que recibe httpRequest sus atributos y los valida y crea un
     * EvnMesa despues de validarlos
     *
     * @param request
     * @return evento EventoMesaControl
     * @throws AccionWebException
     */
    private Evento imprimirCertificadoRelacion(HttpServletRequest request) throws AccionWebException, ImpresionCertificadosAsociadosException {

        //EXCEPCIÓN LANZADA POR VALIDACIONES WEB
        ValidacionParametrosMesaControlException exception = new ValidacionParametrosMesaControlException();

        //VARIABLES DE LA SESIÓN REQUERIDAS PARA LA IMPRESIÓN DE LOS CERTIFICADOS ASOCIADOS
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
        String idRelacion = (String) session.getAttribute(AWRelacion.ID_RELACION);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        //OBTENER LOS CERTIFICADOS ASOCIADOS A IMPRIMIR
        List solicitudesAImprimir = new ArrayList();
        String[] llavesSol = request.getParameterValues(AWMesa.NUM_CERT);

        //SE LANZA EXCEPCIÓN SI NO HAY CERTIFICADOS ASOCIADOS EN EL TURNO.
        if (llavesSol == null || llavesSol.length == 0) {
            exception.addError("Debe seleccionar por lo menos un turno asociado para imprimir.");
        }

        if (idRelacion == null) {
            exception.addError("Se debe tener el número de relación.");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        List certificadosAsociados = new ArrayList();

        Hashtable turnosRegistro = (Hashtable) session.getAttribute(WebKeys.LISTA_TURNOS_RELACION);
        Hashtable turnosValidosImpresion = (Hashtable) session.getAttribute(WebKeys.LISTA_TURNOS_VALIDOS);
        ArrayList keysTurnosRegistro = new ArrayList(turnosRegistro.keySet());
        Collections.sort(keysTurnosRegistro, new IDidworkflowComparator());
        Iterator iter = keysTurnosRegistro.iterator();
        Boolean turnoValidoImpresion = null;

        while (iter.hasNext()) {
            String key = (String) iter.next();
            List turnosCertificados = (List) turnosRegistro.get(key);

            if (turnosCertificados.size() > 0) {
                Iterator it = turnosCertificados.iterator();
                while (it.hasNext()) {
                    SolicitudCertificado solicitudCertificado = (SolicitudCertificado) it.next();
                    Turno turnoCertificado = solicitudCertificado.getTurno();

                    turnoValidoImpresion = (Boolean) turnosValidosImpresion.get(turnoCertificado.getIdWorkflow());

                    if (turnoValidoImpresion != null) {
                        if (turnoValidoImpresion.booleanValue()) {
                            if (turnoCertificado != null) {
                                String certificadosAsociadosT = turnoCertificado.getIdWorkflow();
                                //SE AGREGAN  LOS CERTIFICADOS ASOCIADOS QUE SE DESEAN IMPRIMIR
                                forCertificadosAsociados:
                                for (int i = 0; i < llavesSol.length; i++) {
                                    String sIndice = llavesSol[i];
                                    if (sIndice != null && sIndice.equals(certificadosAsociadosT)) {
                                        certificadosAsociados.add(sIndice);
                                        break forCertificadosAsociados;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        /*//SE AGREGAN  LOS CERTIFICADOS ASOCIADOS QUE SE DESEAN IMPRIMIR
		for (int i = 0; i < llavesSol.length; i++) {
			String sIndice=llavesSol[i];
			if(sIndice!=null){
				certificadosAsociados.add(sIndice);
			}
		}*/

        //OBTENER EL PATH DE LAS FIRMAS
        context = session.getServletContext();
        String pathFirmas = null;

        File file = (File) context.getAttribute(WebKeys.FIRMAS_DIRECTORY);
        if (file != null) {
            pathFirmas = file.getPath();
        } else {
            pathFirmas = "C:";
        }

        EvnMesaControl evento = new EvnMesaControl(usuario, EvnMesaControl.IMPRIMIR_CERTIFICADO_RELACION);
        evento.setSolicitudesCertificado(certificadosAsociados);
        evento.setFase(fase);
        evento.setEstacion(estacion);
        evento.setUsuarioNeg(usuarioNeg);
        evento.setPathFirmas(pathFirmas);
        evento.setNumeroRelacion(idRelacion);
        evento.setCirculo(circulo);
        return evento;
    }

    /**
     * Método que recibe httpRequest sus atributos y los valida y crea un
     * EvnMesa despues de validarlos del metodo avanzarMesaII las anotaciones de
     * un folio
     *
     * @param request
     * @return evento EventoMesaControl
     */
    private Evento validarCertificados(HttpServletRequest request) {

        /*preparar excepcion*/
        ValidacionParametrosMesaControlException exception
                = new ValidacionParametrosMesaControlException();

        /*obtener datos de la session para efectuar el avance*/
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        SolicitudRegistro solR = (SolicitudRegistro) turno.getSolicitud();
        List solHijas = solR.getSolicitudesHijas();
        List Valido = new Vector();
        List solCerts = new Vector();
        Iterator isolh = solHijas.iterator();
        for (; isolh.hasNext();) {
            SolicitudAsociada solA = (SolicitudAsociada) isolh.next();
            SolicitudCertificado solC = (SolicitudCertificado) solA.getSolicitudHija();

            solCerts.add(solC);

        }

        //se crea la lista de folios asociados y se suben a la session
        List listMat = solR.getSolicitudFolios();
        session.setAttribute(AWMesa.LIST_SOL_FOLIO_ASOCIADAS, listMat);

        EvnMesaControl evento = new EvnMesaControl(usuario, EvnMesaControl.VALIDAR_CERTIFICADOS);
        evento.setTurno(turno);
        evento.setSolicitudesCertificado(solCerts);
        evento.setUsuarioNeg(usuarioNeg);
        return evento;
    }

    /**
     * Método que recibe httpRequest sus atributos y los valida y crea un
     * EvnMesa despues de validarlos del metodo avanzarMesaII las anotaciones de
     * un folio
     *
     * @param request
     * @return evento EventoMesaControl
     */
    private Evento avanzarMesaII(HttpServletRequest request) {

        /*preparar excepcion*/
        ValidacionParametrosMesaControlException exception
                = new ValidacionParametrosMesaControlException();

        /*obtener datos de la session para efectuar el avance*/
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);

        List listCert = new Vector();
        //obtener la lista de certificados del session
        listCert = (List) session.getAttribute(AWMesa.LISTA_CERTIFICADOS_ASOCIADOS);

        EvnMesaControl evento = new EvnMesaControl(usuario, EvnMesaControl.AVANZAR_MESA_II);
        evento.setTurno(turno);
        evento.setFase(fase);
        evento.setUsuarioNeg(usuarioNeg);
        evento.setAllSolicitudCertificados(listCert);
        return evento;
    }

    /**
     * Método que recibe httpRequest sus atributos y los valida y crea un
     * EvnMesa despues de validarlos del metodo negarMesaII las anotaciones de
     * un folio
     *
     * @param request
     * @return evento EventoMesaControl
     */
    private Evento negarMesaII(HttpServletRequest request) {

        /*preparar excepcion*/
        ValidacionParametrosMesaControlException exception
                = new ValidacionParametrosMesaControlException();

        /*obtener datos de la session para efectuar el avance*/
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);

        EvnMesaControl evento = new EvnMesaControl(usuario, EvnMesaControl.NEGAR_MESA_II);
        evento.setTurno(turno);
        evento.setFase(fase);
        return evento;
    }

    /**
     * @param request
     * @return evento EventoMesaControl
     */
    private Evento asociados(HttpServletRequest request) {

        LinkedList resultado = new LinkedList();
        HttpSession session = request.getSession();
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        SolicitudRegistro solicitud = (SolicitudRegistro) turno.getSolicitud();
        List solicitudesHijas = solicitud.getSolicitudesHijas();
        solicitudesHijas.size();

        for (int i = 0; i < solicitudesHijas.size(); i++) {
            SolicitudAsociada solicitudAsociada = (SolicitudAsociada) solicitudesHijas.get(i);
            Solicitud solicitudesAsociadasHijas = solicitudAsociada.getSolicitudHija();
            Turno turnoAsociado = solicitudesAsociadasHijas.getTurno();
            turnoAsociado.getIdTurno();

            TurnoPk tid = new TurnoPk();
            tid.idTurno = turnoAsociado.getIdTurno();
            tid.idCirculo = turnoAsociado.getIdCirculo();
            tid.idProceso = turnoAsociado.getIdProceso();
            tid.anio = turnoAsociado.getAnio();

            HermodService hs;
            try {
                hs = HermodService.getInstance();
                Turno turnoReal = hs.getTurno(tid);
                Solicitud solicitudHijaAsociada = turnoReal.getSolicitud();
                List solicitudFolios = solicitudHijaAsociada.getSolicitudFolios();
                for (int j = 0; j < solicitudFolios.size(); j++) {
                    SolicitudFolio solicitudFolio = (SolicitudFolio) solicitudFolios.get(j);
                    resultado.add(solicitudFolio.getFolio());
                }

            } catch (Exception e) {
                return null;
            }
        }
        org.auriga.core.modelo.transferObjects.Usuario usuarioArq = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        return new EvnMesaControl(usuarioArq, MESA_CONTROL, resultado);
    }

    /**
     * Método que recibe httpRequest sus atributos y los valida y crea un
     * EvnMesa despues de validarlos del metodo cambiarMatricula las anotaciones
     * de un folio
     *
     * @param request
     * @return evento EventoMesaControl
     */
    private Evento cambiarMatricula(HttpServletRequest request) {

        /*preparar excepcion*/
        ValidacionParametrosMesaControlException exception
                = new ValidacionParametrosMesaControlException();

        /*obtener datos de la session para efectuar el avance*/
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        String numMatricula = request.getParameter(AWMesa.NUEVO_NUM_MAT);
        String numCert = request.getParameter(AWMesa.NUM_CERT);
        int ncert = Integer.parseInt(numCert);
        SolicitudCertificado solC = null;
        List solCerts = (List) session.getAttribute(AWMesa.LISTA_CERTIFICADOS_ASOCIADOS);
        if (solCerts != null && ncert < solCerts.size()) {
            solC = (SolicitudCertificado) solCerts.get(ncert);
        }

        //obtener la solicitudFolios segun su id
        List solFolios = (List) session.getAttribute(AWMesa.LIST_SOL_FOLIO_ASOCIADAS);
        SolicitudFolio solF = null;
        Iterator it = solFolios.iterator();
        for (; it.hasNext();) {
            SolicitudFolio tempF = (SolicitudFolio) it.next();
            if (tempF.getIdMatricula().equals(numMatricula)) {
                solF = tempF;
            }
        }

        EvnMesaControl evento = new EvnMesaControl(usuario, EvnMesaControl.CAMBIAR_MATRICULA);
        evento.setTurno(turno);
        evento.setSolicitudCertificado(solC);
        evento.setSolFolio(solF);
        evento.setUsuarioNeg(usuarioNeg);
        return evento;
    }

    /**
     * Método que recibe httpRequest sus atributos y los valida y crea un
     * EvnMesa despues de validarlos del metodo
     * determinarMatriculasCertificadosAsociados
     *
     * @param request
     * @return evento EventoMesaControl
     */
    private Evento determinarMatriculasCertificadosAsociados(HttpServletRequest request) throws AccionWebException {

        /*obtener datos de la session para efectuar el avance*/
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        String numReg = request.getParameter(AWMesa.NUM_REG);

        Turno turnoRegistro = new Turno();
        turnoRegistro.setIdWorkflow(numReg);

        /*se crea el evento y se retorna para que sea procesado por la acción de negocio*/
        EvnMesaControl evento = new EvnMesaControl(usuario, EvnMesaControl.CARGAR_MATRICULAS_VALIDAS_CAMBIO);
        evento.setTurnoRegistro(turnoRegistro);
        evento.setUsuarioNeg(usuarioNeg);
        return evento;
    }

    /**
     * Método que recibe httpRequest sus atributos y los valida y crea un
     * EvnMesa despues de validarlos del metodo cambiarMatricula las anotaciones
     * de un folio
     *
     * @param request
     * @return evento EventoMesaControl
     */
    private Evento cambiarMatriculaRelacion(HttpServletRequest request) throws AccionWebException {

        /*Preparar excepcion*/
        ValidacionParametrosMesaControlException exception = new ValidacionParametrosMesaControlException();

        /*Obtener datos de la session para efectuar el avance*/
        HttpSession session = request.getSession();
        session.setAttribute("CARGAR_MESA_CONTROL", new Boolean(true));
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        /*Recuperar parametros del formulario*/
        String numMatricula = request.getParameter(AWMesa.NUEVO_NUM_MAT);
        String numCert = request.getParameter(AWMesa.NUM_CERT);
        String numReg = request.getParameter(AWMesa.NUM_REG);

        /*Realizar las validaciones necesarias*/
        if (numMatricula == null || numMatricula.equals("")) {
            exception.addError("Debe ingresar el nuevo número de matrícula.");
        }
        if (numCert == null || numCert.equals("")) {
            exception.addError("Debe ingresar el turno de certificados al cuál le desea cambiar la matrícula");
        }
        if (numReg == null || numReg.equals("")) {
            exception.addError("Debe ingresar el turno de documento");
        }
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        /*Se crean los objetos con la información necesaria para el cambio de matrícula*/
        SolicitudFolio solFolio = new SolicitudFolio();
        solFolio.setIdMatricula(numMatricula);

        Turno turnoCertificado = new Turno();
        turnoCertificado.setIdWorkflow(numCert);

        Turno turnoRegistro = new Turno();
        turnoRegistro.setIdWorkflow(numReg);

        /*se crea el evento y se retorna para que sea procesado por la acción de negocio*/
        EvnMesaControl evento = new EvnMesaControl(usuario, EvnMesaControl.CAMBIAR_MATRICULA_RELACION);
        evento.setTurnoRegistro(turnoRegistro);
        evento.setTurnoCertificado(turnoCertificado);
        evento.setSolFolio(solFolio);
        evento.setUsuarioNeg(usuarioNeg);
        return evento;
    }

    /**
     * Este método es llamado cuando se quiere consultar los turnos de una
     * relación.
     *
     * @param request HttpServletRequest
     * @return EvnTurno
     */
    private EvnMesaControl consultarRelacion(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Log.getInstance().debug(AWMesa.class, "ENTRO CONSULTA RELACION aw");

        //SE RECUPERA LA INFORMACIÓN DEL FORMULARIO
        Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        String idRelacion = request.getParameter(AWRelacion.ID_RELACION);

        //SE REALIZAN LAS VALIDACIONES NECESARIAS SOBRE LOS DATOS RECIBIDOS
        if (proceso == null) {
            throw new ListarException("El proceso recibido es inválido.");
        }
        if (fase == null) {
            throw new ListarException("La fase recibida es inválida.");
        }
        if (idRelacion == null || idRelacion.equals("")) {
            throw new ListarException("Se debe ingresar un número válido de relación.");
        }

        //SE DEJA EN LA SESIÓN LA INFORMACIÓN PARA EVITAR QUE SE BORRE SI SE GENERA EXCEPCIÓN.
        if ((idRelacion != null) && (idRelacion.trim().length() != 0)) {
            session.setAttribute(AWRelacion.ID_RELACION, idRelacion);
        }

        EvnMesaControl evnMesa = new EvnMesaControl(usuario, EvnMesaControl.CONSULTAR_RELACION);

        evnMesa.setProceso(proceso);
        evnMesa.setFase(fase);
        evnMesa.setIdRelacion(idRelacion);
        evnMesa.setCirculo(circulo);

        return evnMesa;
    }

    /* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
     */
    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {

        EvnRespMesaControl respuesta = (EvnRespMesaControl) evento;

        if (respuesta != null) {
            if (respuesta.getTipoEvento().equals(EvnRespMesaControl.CONSULTAR_TURNOS)) {
                //se envian el session los valores deseados;
                List listTurnosDepurada = new Vector();
                List listTurnos = (List) respuesta.getPayload();
                //se depura la lista para que solo se muestren los que tienen una respuesta asociada
                Iterator it = listTurnos.iterator();
                for (; it.hasNext();) {
                    listTurnosDepurada.add((Turno) it.next());
                    /*Turno temp= (Turno) it.next();
					SolicitudRegistro solReg= (SolicitudRegistro)temp.getSolicitud();
					if(solReg.getClasificacionRegistro()!=null){
						if(!solReg.getClasificacionRegistro().equals("")){
							listTurnosDepurada.add(temp);
						}
					}*/
                }

                request.getSession().setAttribute(AWMesa.LISTA_TURNOS_CONSULTA, listTurnosDepurada);

            } else if (respuesta.getTipoEvento().equals(EvnRespMesaControl.CAMBIAR_MATRICULA)) {
                //subir el turno a la session
                request.getSession().setAttribute(WebKeys.TURNO, respuesta.getPayload());
                request.getSession().setAttribute("CERRAR_VENTANA", new Boolean(true));
            } else if (respuesta.getTipoEvento().equals(EvnRespMesaControl.CARGAR_MATRICULAS_VALIDAS_CAMBIO)) {
                //subir la lista de matrículas válidas para el cambio de las mismas en turnos de cert. asociados
                request.getSession().setAttribute(WebKeys.LISTA_MATRICULAS_VALIDAS_CAMBIO, respuesta.getPayload());
            } else if (respuesta.getTipoEvento().equals(EvnRespMesaControl.VALIDAR_CERTIFICADOS)) {
                //subir el turno a la session
                List solCerts = (List) respuesta.getPayload();
                List Valido = (List) respuesta.getValidos();
                //se sube a la session la lista de validaciones yh la de certificados
                request.getSession().setAttribute(AWMesa.LISTA_CERTIFICADOS_ASOCIADOS, solCerts);
                request.getSession().setAttribute(AWMesa.LISTA_VALIDO, Valido);
                request.getSession().setAttribute("CARGAR_MESA_CONTROL", new Boolean(false));
            } else if (respuesta.getTipoEvento().equals(EvnRespMesaControl.IMPRIMIR_CERTIFICADO)) {
                request.getSession().setAttribute("CARGAR_MESA_CONTROL", new Boolean(true));
            } else if (respuesta.getTipoEvento().equals(EvnRespTurno.CONSULTAR_RELACION)) {
                request.getSession().setAttribute(WebKeys.LISTA_TURNOS_RELACION, respuesta.getPayload());
                request.getSession().setAttribute(WebKeys.LISTA_TURNOS_VALIDOS, respuesta.getTurnosCertificadosValidos());
                request.getSession().setAttribute("CARGAR_MESA_CONTROL", new Boolean(false));
            }

        }

    }

    /**
     * Método que recibe httpRequest sus atributos y los valida y crea un
     * EvnMesa despues de validarlos del metodo enviarRespuesta. Devuelve el
     * turno a la fase de testamento para ser corregido.
     *
     * @param request
     * @return evento EventoMesaControl
     * @throws AccionWebException Pablo Quintana Junio 19 2008
     */
    private Evento devolverTestamento(HttpServletRequest request) throws AccionWebException {
        ValidacionParametrosMesaControlException exception
                = new ValidacionParametrosMesaControlException();
        /*obtener datos de la session para efectuar la consulta*/
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        EvnMesaControl evento = new EvnMesaControl(usuario, EvnMesaControl.DEVOLVER_TESTAMENTO);
        evento.setTurno(turno);
        evento.setFase(fase);
        evento.setUsuarioNeg(usuarioNeg);
        return evento;
    }

    private boolean teniaDatosTMP(SolicitudCertificado solCert, Turno turno) {
        List solFolios = turno.getSolicitud().getSolicitudFolios();
        Iterator iter = solFolios.iterator();
        boolean noAsociado = true;
        SolicitudFolio solCerFolio = (SolicitudFolio) solCert.getSolicitudFolios().get(0);
        while (iter.hasNext()) {
            SolicitudFolio solFolio = (SolicitudFolio) iter.next();
            if (solFolio.getIdMatricula().equals(solCerFolio.getIdMatricula())) {
                noAsociado = false;
                if (solFolio.getEstado() == CSolicitudFolio.ESTADO_INSCRITO_PARCIAL_NO_TMP
                        || solFolio.getEstado() == CSolicitudFolio.SIN_ESTADO) {
                    return false;
                }
            }
        }
        if (noAsociado) {
            return false;
        }
        return true;
    }

    private boolean validaInscritoParcial(SolicitudCertificado solCert, Turno turno) {
        List solFolios = turno.getSolicitud().getSolicitudFolios();
        Iterator iter = solFolios.iterator();
        boolean isParcial = false;
        boolean noAsociado = true;
        SolicitudFolio solCerFolio = (SolicitudFolio) solCert.getSolicitudFolios().get(0);
        while (iter.hasNext()) {
            System.out.println("iter inscripcion parcial ");
            SolicitudFolio solFolio = (SolicitudFolio) iter.next();
            System.out.println("iter inscripcion parcial matricula "+solFolio.getIdMatricula());
            System.out.println("iter inscripcion parcial matricula folio "+solCerFolio.getIdMatricula());
            if (solFolio.getIdMatricula().equals(solCerFolio.getIdMatricula())) {
                
                System.out.println("Estado inscripcion parcial "+solFolio.getEstado());
                if (solFolio.getEstado() == CSolicitudFolio.ESTADO_INSCRITO_PARCIALMENTE
                        || solFolio.getEstado() == CSolicitudFolio.ESTADO_INSCRITO_PARCIAL_NO_TMP) {
                    isParcial = true;
                    //return isParcial;
                }
            }else {
                isParcial = noAsociado;
            }
        }
//        if (noAsociado) {
//            return false;
//        }
        return isParcial;
    }

}

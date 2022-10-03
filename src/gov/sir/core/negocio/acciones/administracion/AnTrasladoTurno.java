package gov.sir.core.negocio.acciones.administracion;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.validaciones.CertificadosSIR;
import gov.sir.core.eventos.administracion.EvnRespTrasladoTurno;
import gov.sir.core.eventos.administracion.EvnTrasladoTurno;
/*
        * @author : CTORRES
        * @change : Se abilita el uso de las clases EvnRespCorreccion,ANCorreccion,ValidacionesSIR
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
 */
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import gov.sir.core.eventos.correccion.EvnRespCorreccion;
import gov.sir.core.negocio.acciones.correccion.ANCorreccion;
import gov.sir.core.negocio.acciones.excepciones.ConsultaTurnoExceptionException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoEncontradoException;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.CirculoTraslado;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Liquidacion;
/**
 * @author : HGOMEZ.
 * @casoMantis : 13898.
 * @actaRequerimiento : No 075_453_Reanotaciï¿½n_Turnos_Registro
 * @change : Clases necesarias para darle soluciï¿½n al requerimiento.
 */
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.Acto;
import gov.sir.core.negocio.modelo.Solicitud;

import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.ProcesoPk;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.DocPagoGeneral;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.DocPagoTarjetaCredito;
import gov.sir.core.negocio.modelo.DocPagoTarjetaDebito;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import java.text.SimpleDateFormat;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.UsuarioPk;
/*
        * @author : CTORRES
        * @change : Se abilita el uso de las clases SolicitudFolio,Testamento
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
 */
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.TipoOficina;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.constantes.CTipoTarifa;
import gov.sir.core.negocio.modelo.util.IDidTurnoComparator;
import gov.sir.core.negocio.modelo.util.IdTurnoHistoriaComparator;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.fenrir.FenrirException;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.gdocumental.integracion.SGD;
import gov.sir.hermod.interfaz.HermodServiceInterface;

/**
 * @author : HGOMEZ
 *** @change : Se habilita el uso del paquete SolicitudFotocopia ** en esta
 * clase. ** Caso Mantis : 12288
 */
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.constantes.CQueries;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoGeneralEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoEnhanced;
import gov.sir.hermod.dao.DAOException;

/**
 * @author : HGOMEZ
 *** @change : Import necesario para auditar minuta. ** Caso Mantis : 13176
 */
import gov.sir.hermod.dao.impl.jdogenie.JDOTurnosDAO;
import gov.sir.hermod.dao.impl.jdogenie.JDOPagosDAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/*
        * @author : CTORRES
        * @change : Se agregan imports
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

/**
 * Acciï¿½n de negocio encargada de manejar los eventos de tipo
 * <code>EvnTrasladoTurno</code> destinados a manejar el traslado de turnos
 * entre usuarios
 *
 * @author jmendez
 */
public class AnTrasladoTurno extends SoporteAccionNegocio {

    /**
     * Instancia del ServiceLocator
     */
    private ServiceLocator service = null;

    /**
     * Instancia de la interfaz de Hermod
     */
    private HermodServiceInterface hermod;

    /**
     * Instancia de la intefaz de Forseti
     */
    private ForsetiServiceInterface forseti;

    /**
     * Instancia de la intefaz de Fenrir
     */
    private FenrirServiceInterface fenrir;

    /**
     * Constructor encargado de inicializar los servicios a ser utilizados por
     * la acciï¿½n de Negocio
     *
     * @throws EventoException
     */
    public AnTrasladoTurno() throws EventoException {
        super();
        service = ServiceLocator.getInstancia();
        try {
            hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
            fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");

            if (hermod == null) {
                throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
            }
            if (fenrir == null) {
                throw new ServicioNoEncontradoException("Servicio fenrir no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod");
        }

        if (hermod == null) {
            throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
        }

        try {
            forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

            if (forseti == null) {
                throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio Forseti");
        }

        if (forseti == null) {
            throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
        }

        try {
            fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");

            if (fenrir == null) {
                throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio Fenrir");
        }

        if (forseti == null) {
            throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
        }
    }

    /**
     * Manejador de eventos de tipo <code>EvnTrasladoTurno</code>. Se encarga de
     * procesar las acciones solicitadas por la capa Web de acuerdo al tipo de
     * evento que llegue a la acciï¿½n de negocio. Este mï¿½todo redirige la acciï¿½n
     * a otros mï¿½todos en la clase de acuerdo al tipo de evento que llega como
     * parï¿½metro.
     *
     * @param evento <code>EvnRespTrasladoTurno</code> evento con los parï¿½metros
     * de la acciï¿½n a realizar utilizando los servicios disponibles en la clase.
     *
     * @return <code>EventoRespuesta</code> con la informaciï¿½n resultante de la
     * ejecuciï¿½n de la acciï¿½n sobre los servicios
     *
     * @throws <code>EventoException</code>
     */
    public EventoRespuesta perform(Evento e) throws EventoException {
        EvnTrasladoTurno evento = (EvnTrasladoTurno) e;

        if (evento == null || evento.getTipoEvento() == null) {
            return null;
        }
        String tipoEvento = evento.getTipoEvento();

        if (tipoEvento.equals(EvnTrasladoTurno.CONSULTAR_TURNOS_Y_USUARIOS_POR_CIRCULO)) {
            return consultarTurnosUsuariosPorCirculo(evento);
        } else if (tipoEvento.equals(EvnTrasladoTurno.CONSULTAR_TURNO_POR_IDENTIFICADOR)) {
            try {
                return consultarTurnoPorIdentificador(evento);
            } catch (DAOException ex) {
                Logger.getLogger(AnTrasladoTurno.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (tipoEvento.equals(EvnTrasladoTurno.CONSULTAR_TURNO_POR_MATRICULA)) {
            return consultarTurnoPorMatricula(evento);
        } else if (tipoEvento.equals(EvnTrasladoTurno.ANULAR_TURNO)) {
            return anularTurno(evento);
        } else if (tipoEvento.equals(EvnTrasladoTurno.HABILITAR_TURNO)) {
            return habilitarTurno(evento);
        } else if (tipoEvento.equals(EvnTrasladoTurno.CONSULTAR_TURNO_POR_MATRICULA_CIRCULO_PROCESO)) {
            try {
                return consultarTurnosPorProcesoCirculoMatricula(evento);
            } catch (DAOException ex) {
                Logger.getLogger(AnTrasladoTurno.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (tipoEvento.equals(EvnTrasladoTurno.CONSULTAR_TURNO_POR_MATRICULA_CIRCULO)) {
            return consultarTurnosCirculoMatricula(evento);
        } else if (tipoEvento.equals(EvnTrasladoTurno.CONSULTAR_TURNO_CERTIFICADO_CAMBIO_MATRICULA)) {
            return consultarTurnoCertificadoCambioMatricula(evento);
        } else if (tipoEvento.equals(EvnTrasladoTurno.CANCELAR_CAMBIO_MATRICULA)) {
            return cancelarConsultarTurnoCertificadoCambioMatricula(evento);
        } else if (tipoEvento.equals(EvnTrasladoTurno.VALIDAR_CIUDADANO_EDICION)) {
            return consultarCiudadanoEdicion(evento);
        } else if (tipoEvento.equals(EvnTrasladoTurno.RELIZAR_CIUDADANO_EDICION)) {
            return realizarCiudadanoEdicion(evento);
        } else if (tipoEvento.equals(EvnTrasladoTurno.REANOTAR_TURNO_ESPECIFICACION)) {
            return reanotarTurnoInicio(evento);
        } else if (tipoEvento.equals(EvnTrasladoTurno.REANOTAR_TURNO)) {
            return reanotarTurno(evento);
            /*
        * @author : CTORRES
        * @change : Se agrega la condicion para el tipo de evento VER_DETALLES_TURNO_TESTAMENTO
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
             */
        } else if (tipoEvento.equals(EvnTrasladoTurno.VER_DETALLES_TURNO_TESTAMENTO)) {
            return cargarDetallesTurnoTestamento(evento);
        }

        return null;
    }

    /**
     * Este mï¿½todo se encarga de invocar los servicios necesarios para consultar
     * los turnos relacionados con una matrï¿½cula y un cï¿½rculo especï¿½ficos
     *
     * @return <code>EvnRespTrasladoTurno</code> con los turnos obtenidos
     * @throws <code>EventoException</code>
     */
    private EvnRespTrasladoTurno consultarTurnosCirculoMatricula(EvnTrasladoTurno evento)
            throws EventoException {
        org.auriga.core.modelo.transferObjects.Usuario user = evento.getUsuario();

        List turnos = null;

        try {
            Circulo circulo = evento.getCirculo();
            String idMatricula = evento.getFolio().getIdMatricula();
            boolean puedeConsultarTurno = false;
            Circulo circuloTemp = null;

            //SI SE QUIERE CONSULTAR UN TURNO DE OTRO CIRCULO 
            if (!idMatricula.startsWith(circulo.getIdCirculo())) {

                long id = fenrir.darIdUsuario(user.getUsuarioId());
                List roles = fenrir.darRolUsuario(id);
                List rolesString = new ArrayList();

                //SE DEBE VERIFICAR QUE TENGA EL ROL QUE PUEDE HACERLO.
                //SE CONSULTAN LOS ROLES QUE PUEDEN CONSULTAR TURNOS DE CUALQUIER CIRCULO
                List rolesValidos = hermod.getOPLookupCodes(COPLookupTypes.ROLES_CONSULTA_TURNOS);

                //SE CONSULTAN LOS ROLES QUE PUEDEN CONSULTAR TURNOS DE CUALQUIER CIRCULO
                Iterator itRoles = roles.iterator();
                while (itRoles.hasNext()) {
                    Rol rol = (Rol) itRoles.next();
                    rolesString.add(rol.getRolId());
                }

                Iterator it = rolesValidos.iterator();
                while (it.hasNext()) {
                    OPLookupCodes code = (OPLookupCodes) it.next();
                    if (rolesString.contains(code.getValor())) {
                        puedeConsultarTurno = true;

                        if (idMatricula.indexOf("-") != -1) {
                            circuloTemp = new Circulo();
                            circuloTemp.setIdCirculo(idMatricula.substring(0, idMatricula.indexOf("-")));
                        }
                        break;

                    }
                }

                if (!puedeConsultarTurno) {
                    throw new ConsultaTurnoExceptionException("No puede consultar turnos de otros circulos.");
                }
            } else {
                //SI EL TURNO QUE SE QUIERE CONSULTAR ES DEL MISMO CIRCULO, NO HAY NINGï¿½N PROBLEMA.
                puedeConsultarTurno = true;
            }

            //turnos = hermod.getTurnosCirculo(circuloTemp!=null?circuloTemp : circulo, idMatricula);
            turnos = hermod.getTurnos(idMatricula);

            /**
             * SE DEBEN AGREGAR LOS TURNOS QUE ESTAN ASOCIADOS AL RESULTADO DEL
             * SERVICIO ANTERIOR (turnos) (DEVOLUCION, MAYOR VALOR). ESTOS NO
             * TIENEN UNA REFERENCIA DIRECTA DE LA MATRICULA SOLO POR EL TURNO
             */
            List idWorkFlows = new ArrayList();
            for (Iterator iterTurnos = turnos.iterator(); iterTurnos.hasNext();) {
                idWorkFlows.add((String) ((Turno) iterTurnos.next()).getIdWorkflow());
            }
            boolean turnoExistente = false;
            for (int cont = 0; cont < turnos.size(); cont++) {
                List turnosAsociados = hermod.getTurnosSiguientes((String) idWorkFlows.get(cont));
                for (int cont2 = 0; cont2 < turnosAsociados.size(); cont2++) {
                    Turno turno = (Turno) turnosAsociados.get(cont2);
                    for (int cont3 = 0; cont3 < idWorkFlows.size(); cont3++) {
                        if (idWorkFlows.get(cont3).equals(turno.getIdWorkflow())) {
                            turnoExistente = true;
                        }
                        if (turnoExistente) {
                            break;
                        } else if (cont3 == idWorkFlows.size() - 1) {
                            if (turno.getIdCirculo().equals(circuloTemp != null ? circuloTemp.getIdCirculo() : circulo.getIdCirculo())) {
                                turnos.add((Turno) turnosAsociados.get(cont2));
                                idWorkFlows.add(((Turno) turnosAsociados.get(cont2)).getIdWorkflow());
                                turnoExistente = false;
                                break;
                            }
                        }

                    }
                }
            }

            /**
             * **************************************************************************
             */
            //Se deben ordenar los turnos correctamente            
            try {

                Collections.sort(turnos, new IDidTurnoComparator());

            } catch (Exception e) {

                Log.getInstance().error(AnTrasladoTurno.class, "No se pudieron ordenar los Turnos");

            }

        } catch (ConsultaTurnoExceptionException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "No se pudo consultar el turno", e);
            throw e;
        } catch (FenrirException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar los turnos por cï¿½rculo y proceso", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar los turnos por cï¿½rculo y proceso", e);
            throw new EventoException(e.getMessage(), e);
        }

        if (turnos == null || turnos.size() == 0) {
            throw new ConsultaTurnoExceptionException("No se encontraron turnos asociados.");
        }

        /*EvnRespTrasladoTurno evRespuesta =
            new EvnRespTrasladoTurno(
                turnos,
                EvnRespTrasladoTurno.CONSULTAR_TURNO_POR_MATRICULA_CIRCULO_OK);*/
        EvnRespTrasladoTurno evRespuesta
                = new EvnRespTrasladoTurno(turnos, EvnRespTrasladoTurno.CONSULTAR_TURNOS_POR_MATRICULA_OK);
        evRespuesta.setTurnos(turnos);
        return evRespuesta;
    }

    /**
     * Este mï¿½todo se encarga de invocar los servicios necesarios para consultar
     * los turnos relacionados con una matrï¿½cula, un cï¿½rculo y un proceso
     * especï¿½ficos
     *
     * @return <code>EvnRespTrasladoTurno</code> con los turnos obtenidos
     * @throws <code>EventoException</code>
     */
    private EvnRespTrasladoTurno consultarTurnosPorProcesoCirculoMatricula(EvnTrasladoTurno evento)
            throws EventoException, DAOException {

        List turnos = null;

        try {
            Circulo circulo = evento.getCirculo();
            Proceso proceso = evento.getProceso();
            String idMatricula = evento.getFolio().getIdMatricula();
            Circulo circuloTemp = null;

            if (!idMatricula.startsWith(circulo.getIdCirculo())) {
                if (idMatricula.indexOf("-") != -1) {
                    circuloTemp = new Circulo();
                    circuloTemp.setIdCirculo(idMatricula.substring(0, idMatricula.indexOf("-")));
                }
            }

            /**
             * CASO ESPECIAL DE TURNOS DE DEVOLUCIONES O MAYOR VALOR
             * (Solicitudes que no tienen asociada la matricula)
             */
            Long procesoAux = new Long(proceso.getIdProceso());
            if (procesoAux.toString().equals(CProceso.PROCESO_DEVOLUCIONES)) {

                List turnosAux = hermod.getTurnosCirculo(circuloTemp != null ? circuloTemp : circulo, idMatricula);
                List idWorkFlows = new ArrayList();
                for (Iterator iterTurnos = turnosAux.iterator(); iterTurnos.hasNext();) {
                    idWorkFlows.add((String) ((Turno) iterTurnos.next()).getIdWorkflow());
                }
                turnos = new ArrayList();
                //boolean turnoExistente = false;
                for (int cont = 0; cont < turnosAux.size(); cont++) {
                    List turnosAsociados = hermod.getTurnosSiguientes((String) idWorkFlows.get(cont));
                    for (int cont2 = 0; cont2 < turnosAsociados.size(); cont2++) {
                        Turno turno = (Turno) turnosAsociados.get(cont2);
                        if (turno.getIdProceso() == proceso.getIdProceso()) {
                            turnos.add(turno);
                        }
                    }
                }
            } else {
                turnos = hermod.getTurnosCirculo(proceso, (circuloTemp != null ? circuloTemp : circulo), idMatricula);
            }

            /**
             * SE DEBEN AGREGAR LOS TURNOS QUE ESTAN ASOCIADOS AL RESULTADO DEL
             * SERVICIO ANTERIOR (turnos) (DEVOLUCION, MAYOR VALOR). ESTOS NO
             * TIENEN DRERANDCINASKFSKDNFISBFNBAKSNDBFIA
             */
            /*List idWorkFlows = new ArrayList();
            for(Iterator iterTurnos = turnos.iterator();iterTurnos.hasNext();){
            	idWorkFlows.add((String)((Turno)iterTurnos.next()).getIdWorkflow());
            }
            
			for(int cont = 0; cont<turnos.size();cont++){
				List turnosAsociados = hermod.getTurnosSiguientes((String)idWorkFlows.get(cont));
				for(int cont2 = 0; cont2< turnosAsociados.size(); cont2++){
					Turno turno = (Turno)turnosAsociados.get(cont2);
					if(!(idWorkFlows.get(cont).equals(turno.getIdWorkflow()))&&(turno.getIdCirculo().equals(circuloTemp)))
						turnos.add((Turno)turnosAsociados.get(cont2));
				}
			}*/
            /**
             * **************************************************************************
             */
            // Se deben ordenar los turnos correctamente
            try {

                Collections.sort(turnos, new IDidTurnoComparator());

            } catch (Exception e) {

                Log.getInstance().error(AnTrasladoTurno.class, "No se pudieron ordenar los Turnos");

            }

            //turnos = hermod.getTurnosFase(proceso, fase, circulo);
        } catch (FenrirException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar los turnos por cï¿½rculo y proceso", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar los turnos por cï¿½rculo y proceso", e);
            throw new EventoException(e.getMessage(), e);
        }

        /*EvnRespTrasladoTurno evRespuesta =
            new EvnRespTrasladoTurno(
                turnos,
                EvnRespTrasladoTurno.CONSULTAR_TURNO_POR_MATRICULA_CIRCULO_PROCESO_OK);*/
        EvnRespTrasladoTurno evRespuesta
                = new EvnRespTrasladoTurno(turnos, EvnRespTrasladoTurno.CONSULTAR_TURNOS_POR_MATRICULA_OK);
        evRespuesta.setTurnos(turnos);
        return evRespuesta;
    }

    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este mï¿½todo se encarga de invocar los servicios necesarios para consultar
     * los turnos y usuarios relacionados con un cï¿½rculo y una fase especï¿½fica
     *
     * @return <code>EvnRespTrasladoTurno</code> con la listas actualizadas.
     * @throws <code>EventoException</code>
     */
    private EvnRespTrasladoTurno consultarTurnosUsuariosPorCirculo(EvnTrasladoTurno evento)
            throws EventoException {

        List usuarios = null;
        List turnos = null;

        try {
            Circulo circulo = evento.getCirculo();
            Fase fase = evento.getFase();
            Proceso proceso = evento.getProceso();

            usuarios = fenrir.consultarUsuariosPorCirculo(circulo);
            if (usuarios == null) {
                throw new EventoException(
                        "Ocurriï¿½ un error al consultar los usuarios y turnos por cï¿½rculo y fase .",
                        null);
            }

            turnos = hermod.getTurnosFase(proceso, fase, circulo);

        } catch (FenrirException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar los usuarios y turnos por cï¿½rculo y fase .", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar los usuarios y turnos por cï¿½rculo y fase .", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespTrasladoTurno evRespuesta
                = new EvnRespTrasladoTurno(
                        usuarios,
                        EvnRespTrasladoTurno.CONSULTAR_TURNOS_Y_USUARIOS_POR_CIRCULO_OK);
        evRespuesta.setTurnos(turnos);
        return evRespuesta;
    }

    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este mï¿½todo se encarga de invocar los servicios necesarios para consultar
     * un turno especï¿½fico dado su ID de Workflow
     *
     * @return <code>EvnRespTrasladoTurno</code> con la listas actualizadas.
     * @throws <code>EventoException</code>
     */
    private EvnRespTrasladoTurno consultarTurnoPorIdentificador(EvnTrasladoTurno evento)
            throws EventoException, DAOException {

        //yeferson
        String obsPertenencia = null;
        String idturnos = null;
        /**
         * @autor Yeferson martinez caso 351144 conservacion documental
         * @parameter Sting sumaconservacion
         */
        String sumaConservacion = null;
        Turno turno = null;
//        boolean existe_error = false;
        Circulo circulo = evento.getCirculo();
        org.auriga.core.modelo.transferObjects.Usuario user = evento.getUsuario();
        List turnosAnteriores = null;
        List turnosSiguientes = null;
        List turnosSolicitudesAsociadas = null;
        Turno turnoDerivado = null;
        Turno turnoPadre = null;
        List turnosNoMigrados = null;
        /*
                * inicializar variable turnosDevoluciones.
                * @author: Julio Alcazar
                * @change: 7393: Acta - Requerimiento No 215 - Ver Detalles de Turno - Turnos Devolucion Negados
         */
        List turnosDevoluciones = null;
        String idAdministradorSAS = null; //Estacion del turno asociado (Certificados Asociados)
        List resoluciones = null;

        // Map< java.lang.String: idTurno , List< gov.sir.core.negocio.modelo.Imprimible: imprimible > >
        Map local_ImprimiblesPendientes_TurnoPadre = null;
        Map local_ImprimiblesPendientes_TurnoHijos = null;
        int local_ImprimiblesPendientes_TurnoPadreCount = 0;
        int local_ImprimiblesPendientes_TurnoHijosCount = 0;

        boolean turnoInternet = false;
        Minuta minuta = null;
        /*En caso de ser un turno de reparto notarial se obtiene la categoria*/
        try {
            minuta = hermod.getMinutaByTurnoWF(evento.getTurno().getIdWorkflow());
        } catch (Throwable e) {
        }        
        try {
            boolean puedeConsultarTurno = false;
            Circulo circuloTemp = null;

            turno = hermod.getTurnobyWF(evento.getTurno().getIdWorkflow());

            //SI SE QUIERE CONSULTAR UN TURNO DE OTRO CIRCULO 
            if (!turno.getIdCirculo().equals(circulo.getIdCirculo())) {

                //SE DEBE VERIFICAR QUE TENGA EL ROL QUE PUEDE HACERLO.
                //SE CONSULTAN LOS ROLES QUE PUEDEN CONSULTAR TURNOS DE CUALQUIER CIRCULO
                List rolesValidos = hermod.getOPLookupCodes(COPLookupTypes.ROLES_CONSULTA_TURNOS);

                //SE CONSULTAN LOS ROLES QUE PUEDEN CONSULTAR TURNOS DE CUALQUIER CIRCULO
                long id = fenrir.darIdUsuario(user.getUsuarioId());
                List roles = fenrir.darRolUsuario(id);
                List rolesString = new ArrayList();

                boolean tieneRolConsultaCirculosInhab = false;
                Iterator itRoles = roles.iterator();
                while (itRoles.hasNext()) {
                    Rol rol = (Rol) itRoles.next();
                    rolesString.add(rol.getRolId());
                    if (rol.getRolId().equals(CRoles.SIR_ROL_CONSULTA_CIRCULO_INHABILITADO)) {
                        tieneRolConsultaCirculosInhab = true;
                    }
                }

                Iterator it = rolesValidos.iterator();
                while (it.hasNext()) {
                    OPLookupCodes code = (OPLookupCodes) it.next();
                    if (rolesString.contains(code.getValor())) {
                        puedeConsultarTurno = true;
                        break;
                    }
                }

//				VERIFICAR SI EL USUARIO TIENE EL ROL PARA CONSULTAR TURNOS DE CIRCULOS INHABILITADOS
                if (tieneRolConsultaCirculosInhab && !puedeConsultarTurno) {
                    List circulos = null;
                    circulos = forseti.getCirculosInhabilitados(circulo);
                    Iterator itCirculosInh = circulos.iterator();
                    while (itCirculosInh.hasNext()) {
                        CirculoTraslado ct = (CirculoTraslado) itCirculosInh.next();
                        if (ct.getCirculoOrigen().getIdCirculo().equals(turno.getIdCirculo())) {
                            puedeConsultarTurno = true;
                            break;
                        }
                    }
                }

                if (!puedeConsultarTurno) {
                    throw new ConsultaTurnoExceptionException("No puede consultar turnos de otros circulos.");
                }
            }

            if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_DEVOLUCIONES)) {
                TurnoPk oid = new TurnoPk();
                oid.anio = turno.getAnio();
                oid.idCirculo = turno.getIdCirculo();
                oid.idProceso = turno.getIdProceso();
                oid.idTurno = turno.getIdTurno();

                resoluciones = hermod.getOficiosTurno(oid);
            }
            //TFS 4443: SE DEBE CONSULTAR EL ESTADO DEL FOLIO
            if (turno.getSolicitud() != null && turno.getSolicitud().getSolicitudFolios() != null) {
                Iterator itSolicitudFolio = turno.getSolicitud().getSolicitudFolios().iterator();
                int i = 0;
                while (itSolicitudFolio.hasNext()) {
                    SolicitudFolio solFolio = (SolicitudFolio) itSolicitudFolio.next();
                    Folio folio = forseti.getFolioByID(solFolio.getIdMatricula());
                    if (folio != null) {
                        ((SolicitudFolio) turno.getSolicitud().getSolicitudFolios().get(i)).setFolio(folio);
                    } else {
                        throw new Throwable("No se pudo obtener el folio " + solFolio.getIdMatricula());
                    }
                    i++;
                }
            }

            boolean reimpresion = false;
            List turnosHistoria = turno.getHistorials();
            for (Iterator iter = turnosHistoria.iterator(); iter.hasNext();) {
                TurnoHistoria turnoHistoria = (TurnoHistoria) iter.next();
                String idFase = turnoHistoria.getFase();

                // Desafortunadamente no es trivial crear una fase para un proceso que no existe,
                // ya que son muchos los registros que se deben modificar manualmente dentro de la
                // base de datos para que estos figuren en la consulta de de Fases. Una vï¿½a mucho
                // mï¿½s sencilla es agregar lï¿½gica de negocio para determinar el nombre de una fase.
                // Como los turnos manuales no cuentan con una representaciï¿½n dentro del Workflow,
                // se agrega lï¿½gica de negocio para determinar el nombre de la fase.
                if (idFase.equals(CFase.REG_TMAN_CREACION) || idFase.equals(CFase.CER_TMAN_CREACION)) {
                    turnoHistoria.setNombreFase(CFase.CREACION);
                    continue;
                } else if (idFase.equals(CFase.REG_TMAN_CIERRE) || idFase.equals(CFase.CER_TMAN_CIERRE)) {
                    turnoHistoria.setNombreFase(CFase.CIERRE);
                    continue;
                }

                Fase fase = hermod.getFase(idFase);

                if (fase.getNombre() != null && fase.getNombre().equals(CFase.IMPRIMIR)) {
                    if (!reimpresion) {
                        turnoHistoria.setNombreFase(fase.getNombre());
                        reimpresion = true;
                    } else {
                        turnoHistoria.setNombreFase(CFase.REIMPRIMIR);
                    }
                } else {
                    turnoHistoria.setNombreFase(fase.getNombre());
                }
            }

            if (turnosHistoria != null && turnosHistoria.size() > 0) {
                if (hermod.lastAdministradorTurnoSAS(evento.getTurno().getIdWorkflow()) != null) {
                    TurnoHistoria ultimoTurnoHistoria = (TurnoHistoria) turnosHistoria.get(turnosHistoria.size() - 1);
                    ultimoTurnoHistoria.setIdAdministradorSAS(hermod.lastAdministradorTurnoSAS(evento.getTurno().getIdWorkflow()));
                }

                // Se deben ordenar los turnos historia por fecha y si son iguales por identificador.
                List historialesOrdenados = new ArrayList();
                historialesOrdenados.addAll(turnosHistoria);
                try {
                    Collections.sort(historialesOrdenados, new IdTurnoHistoriaComparator());
                } catch (Exception e) {
                    Log.getInstance().error(AnTrasladoTurno.class, "No se pudieron ordenar el historial del Turno");
                }
                turno.setHistorials(historialesOrdenados);
            }

            //aqui se obtienen la lista de turnos anteriores.
            turnosAnteriores = hermod.getTurnosAnteriores(turno.getIdWorkflow());
            ValidacionesSIR validacioneSir = new ValidacionesSIR();
            /*
                        * @author Carlos Torres Urina
                        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
                        * @change: se agrega instrucion para consultar turnos siguientes para turnos de testamento
                        **/
            if (turno.getSolicitud() instanceof SolicitudRegistro && validacioneSir.isTurnoTestamentoValido(turno.getIdWorkflow())) {
                turnosSiguientes = hermod.getTurnosSiguientesTestamento(turno.getIdWorkflow());

            } else {
                //aqui se obtienen la lista de turnos siguientes.
                turnosSiguientes = hermod.getTurnosSiguientes(turno.getIdWorkflow());
            }
            /*
                         * Obtiene los turnos de devolucion asociados con el turno ingresado como parï¿½metro.
                         * @author: Julio Alcazar
                         * @change: 7393: Acta - Requerimiento No 215 - Ver Detalles de Turno - Turnos Devolucion Negados
             */
            turnosDevoluciones = hermod.getTurnosDevolucion(turno);
            if (turnosDevoluciones != null) {
                Iterator itera = turnosDevoluciones.iterator();
                List templist = new ArrayList();
                while (itera.hasNext()) {
                    Turno turnotemp = (Turno) itera.next();
                    templist.add(hermod.getTurnobyWF(turnotemp.getIdWorkflow()));
                }
                turnosDevoluciones = templist;
            }

            if (turno == null) {
                throw new TurnoNoEncontradoException("El turno solicitado no existe.", null);
            }
            Turno turnoAsoc = null;
            Solicitud solicitud = turno.getSolicitud();
            if (solicitud instanceof SolicitudCertificado) {
                turnosSolicitudesAsociadas = new ArrayList();
                SolicitudCertificado solCert = (SolicitudCertificado) solicitud;
                List solPadres = solCert.getSolicitudesPadres();
                if (!solPadres.isEmpty()) {
                    for (Iterator iter = solPadres.iterator(); iter.hasNext();) {
                        SolicitudAsociada solAsoc = (SolicitudAsociada) iter.next();
                        SolicitudPk solid = new SolicitudPk();
                        solid.idSolicitud = solAsoc.getIdSolicitud();
                        turnoAsoc = forseti.getTurnoBySolicitud(solid);
                        if (turnoAsoc != null) {
                            turnosSolicitudesAsociadas.add(turnoAsoc);
                        }
                    }
                }
            } else if (solicitud instanceof SolicitudCertificadoMasivo) {
                turnosSolicitudesAsociadas = new ArrayList();
                SolicitudCertificadoMasivo solCert = (SolicitudCertificadoMasivo) solicitud;
                List solHijos = solCert.getSolicitudesHijas();
                if (!solHijos.isEmpty()) {
                    for (Iterator iter = solHijos.iterator(); iter.hasNext();) {
                        SolicitudAsociada solAsoc = (SolicitudAsociada) iter.next();
                        SolicitudPk solid = new SolicitudPk();
                        solid.idSolicitud = solAsoc.getIdSolicitud1();
                        turnoAsoc = forseti.getTurnoBySolicitud(solid);
                        if (turnoAsoc != null) {
                            turnosSolicitudesAsociadas.add(turnoAsoc);
                        }
                    }
                }
            } /**
             * @author : HGOMEZ
             *** @change : Se valida que la solicitud sea una instancia de **
             * SolicitudFotocpia para devolver valores apropiados al proceso **
             * 5 de SIR . ** Caso Mantis : 12288
             */
            else if (solicitud instanceof SolicitudFotocopia) {
                turnosSolicitudesAsociadas = new ArrayList();
                SolicitudFotocopia solFot = (SolicitudFotocopia) solicitud;
                List solHijos = solFot.getSolicitudesHijas();
                if (!solHijos.isEmpty()) {
                    for (Iterator iter = solHijos.iterator(); iter.hasNext();) {
                        SolicitudAsociada solAsoc = (SolicitudAsociada) iter.next();
                        SolicitudPk solid = new SolicitudPk();
                        solid.idSolicitud = solAsoc.getIdSolicitud1();
                        turnoAsoc = forseti.getTurnoBySolicitud(solid);
                        if (turnoAsoc != null) {
                            turnosSolicitudesAsociadas.add(turnoAsoc);
                        }
                    }
                }
            }
//********************************************************************//			
            /*Obtiene la estacion de un turno asociado (Certificados asociados)*/
            TurnoHistoria thUltimoAsoc;
            Turno turnoAsocAux = null;
            if (turnoAsoc != null) {
                turnoAsocAux = hermod.getTurnobyWF(turnoAsoc.getIdWorkflow());
                List hist = turnoAsocAux.getHistorials();
                if (hist != null && hist.size() > 0) {
                    thUltimoAsoc = (TurnoHistoria) hist.get(hist.size() - 1);
                    idAdministradorSAS = thUltimoAsoc.getIdAdministradorSAS();
                }

            }
            // 
            String local_TurnoIdWorkflow;
            String local_TipoImprimibleId;

            // para  el turno, cargar los imprimibles
            List local_ImprimiblesPendientes_TurnoPadreTmp;
            local_ImprimiblesPendientes_TurnoPadre = null;

            // para los turnos-asociados, cargar los imprimibles
            // List< IdTurno, Imprimible > 
            List local_ImprimiblesPendientes_TurnoHijosTmp;
            local_ImprimiblesPendientes_TurnoHijos = null;

            local_ImprimiblesPendientes_TurnoPadre = new HashMap();
            local_ImprimiblesPendientes_TurnoHijos = new HashMap();

            //  
            local_TurnoIdWorkflow = turno.getIdWorkflow();

            // por el momento se filtra en web / No necesariamente es un solo tipo de imprimible
            // local_TipoImprimibleId = gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo.class.getName();
            local_TipoImprimibleId = null;

            local_ImprimiblesPendientes_TurnoPadreTmp = hermod.getImprimiblesPendientesByWfId(local_TurnoIdWorkflow, local_TipoImprimibleId);
            local_ImprimiblesPendientes_TurnoPadreCount += (null != local_ImprimiblesPendientes_TurnoPadreTmp) ? (local_ImprimiblesPendientes_TurnoPadreTmp.size()) : (0);
            //local_ImprimiblesPendientes_TurnoPadreCount += ( null != local_ImprimiblesPendientes_TurnoPadreTmp )?( 1 ):( 0 );

            local_ImprimiblesPendientes_TurnoPadre.put(local_TurnoIdWorkflow, local_ImprimiblesPendientes_TurnoPadreTmp);

            //SE CARGAN COMPLETAMENTE LAS SOLICITUDES HIJAS.
            Turno turnoCertificado = null;
            List certificadosAsociados = solicitud.getSolicitudesHijas();
            if (certificadosAsociados != null) {

                int temp = 0;
                for (temp = 0; temp < certificadosAsociados.size(); temp++) {
                    SolicitudAsociada solAsoc = (SolicitudAsociada) certificadosAsociados.get(0);
                    solicitud.removeSolicitudesHija(solAsoc);
                    Solicitud solicitudTemp = solAsoc.getSolicitudHija();
                    solicitudTemp = hermod.getSolicitudById(solicitudTemp.getIdSolicitud());

                    SolicitudPk idSolicitud = new SolicitudPk();
                    idSolicitud.idSolicitud = solicitudTemp.getIdSolicitud();
                    turnoCertificado = hermod.getTurnoBySolicitud(idSolicitud);
                    solicitudTemp.setTurno(turnoCertificado);

                    solAsoc.setSolicitudHija(solicitudTemp);
                    solicitud.addSolicitudesHija(solAsoc);

                    //
                    if (turnoCertificado != null) {
                        local_TurnoIdWorkflow = turnoCertificado.getIdWorkflow();

                        local_ImprimiblesPendientes_TurnoHijosTmp = hermod.getImprimiblesPendientesByWfId(local_TurnoIdWorkflow, null);

                        local_ImprimiblesPendientes_TurnoHijosCount += (null != local_ImprimiblesPendientes_TurnoHijosTmp) ? (local_ImprimiblesPendientes_TurnoPadreTmp.size()) : (0);
                        //local_ImprimiblesPendientes_TurnoHijosCount += ( null != local_ImprimiblesPendientes_TurnoHijosTmp )?( 1 ):( 0 );

                        local_ImprimiblesPendientes_TurnoHijos.put(local_TurnoIdWorkflow, local_ImprimiblesPendientes_TurnoHijosTmp);
                    }

                } // for

            }
            Circulo circuloPortal = hermod.getCirculoPortal();
            String idCirculoPortal = null;
            if (circuloPortal != null) {
                idCirculoPortal = circuloPortal.getIdCirculo();
            }

            if (idCirculoPortal != null && idCirculoPortal.equals(turno.getIdCirculo())) {
                turnoInternet = true;
            }

            TurnoPk turnoId = new TurnoPk();
            turnoId.anio = turno.getAnio();
            turnoId.idCirculo = turno.getIdCirculo();
            turnoId.idProceso = turno.getIdProceso();
            turnoId.idTurno = turno.getIdTurno();

            turnoDerivado = hermod.getTurnoDependiente(turnoId);
            turnoPadre = hermod.getTurnoPadre(turnoId);
            turnosNoMigrados = hermod.getTurnosFolioNoMigrados(turno);
            
        } catch (HermodException e) {
//            existe_error = true;
            Log.getInstance().error(AnTrasladoTurno.class, "OcurriÃ³ un error al consultar el turno .", e);
            throw new TurnoNoEncontradoException(e.getMessage(), e);
        } catch (ConsultaTurnoExceptionException e) {
//            existe_error = true;
            Log.getInstance().error(AnTrasladoTurno.class, "OcurriÃ³ un error al consultar el turno.", e);
            throw e;
        } catch (TurnoNoEncontradoException e) {
//            existe_error = true;
            Log.getInstance().error(AnTrasladoTurno.class, "OcurriÃ³ un error al consultar el turno.", e);
            throw e;
        } catch (Throwable e) {
//            existe_error = true;
            Log.getInstance().error(AnTrasladoTurno.class, "OcurriÃ³ un error al consultar el turno .", e);
            throw new TurnoNoEncontradoException(e.getMessage(), e);
        }
        
//        System.out.println("CONTINUA CONSULTA");

        EvnRespTrasladoTurno evRespuesta
                = new EvnRespTrasladoTurno(
                        turno,
                        turnosAnteriores,
                        turnosSiguientes,
                        EvnRespTrasladoTurno.CONSULTAR_TURNOS_POR_IDENTIFICADOR_OK);
        if (idAdministradorSAS != null) {
            evRespuesta.setIdAdministradorSAS(idAdministradorSAS);
        }
        
//        evRespuesta.setExiste_error_evento(existe_error);
        
        /**
        *@author: yeferson martinez
        *@change: REQUERIMIENTO 317097
        *HACE EL LLAMADO PARA LA CONSULTA A BD SE ENVIA LOS PARAMATROS NECESARIO PARA LA CONSULTA Y SETEA LA RESPUESTA AL 
        *EVENTO evRespuesta
        */
        JDOTurnosDAO jdoTurnosDAO = new JDOTurnosDAO ();
        
        String proceso = String.valueOf(turno.getIdProceso());
        
        obsPertenencia = jdoTurnosDAO.obsPertenencia(turno.getIdTurno(),turno.getAnio(),
            turno.getIdCirculo(),proceso);
       
        evRespuesta.setObsPertenencia(obsPertenencia);
        
        //        CARLOS
        String idSolicitud = "";
        List idDocumentoPago = new ArrayList();
        List info = new ArrayList();
        List error = new ArrayList();
        String idCtp = "";
        String idCb = "";
        String idCr = "";
        String idTipoDocPago = "";
        String cuentaBancaria = "";
        String canalRecaudo = "";
        String tipoDocPago = "";
        String total = "";
        String dcpgNoAprovacion = "";
        String dcpgNoConsignacion = "";
        String numeroReferencia = "";
        String bancoId = "";
        String bancoNombre = "";
        idSolicitud = jdoTurnosDAO.encontrarIdSolicitudByTurno(turno.getIdWorkflow());
        System.out.print("El id Documento Pago es: " + idSolicitud);        
        idDocumentoPago = jdoTurnosDAO.encontrarIdDocumentoPagoByIdSolicitud (idSolicitud);
        for (int i = 0; i < idDocumentoPago.size(); i++) {
             System.out.print("Los idDocumentoPago son en el for: " + i + " y " + idDocumentoPago.get(i));
                idCtp = jdoTurnosDAO.encontrarIdCtpByIdDocumentoPago(idDocumentoPago.get(i).toString());
                System.out.print("Los idCtp en el for: " + idCtp );
                idCb = jdoTurnosDAO.encontrarIdCbByIdCtp(idCtp);
                System.out.print("Los idCb en el for: " + idCb );
                if(idCb != null){
                    cuentaBancaria = jdoTurnosDAO.encontrarNumerosCuenta(idCb);
                    System.out.print("Las cuentaBancaria en el for: " + cuentaBancaria );
                }else{
                    cuentaBancaria = " ";
                }
                idCr = jdoTurnosDAO.encontrarIdCrByIdCtp(idCtp);
                System.out.print("Los idCr en el for: " + idCr );
                canalRecaudo = jdoTurnosDAO.encontrarCanalRecaudoByIdCr(idCr);
                System.out.print("Los canalRecaudo en el for: " + canalRecaudo );
                dcpgNoAprovacion = jdoTurnosDAO.encontrarDcpgNoAprovacionByIdDocumentoPago(idDocumentoPago.get(i).toString());
                System.out.print("Los dcpgNoAprovacion en el for: " + dcpgNoAprovacion );
                if(dcpgNoAprovacion != null){
                    numeroReferencia = dcpgNoAprovacion;
                }else if((dcpgNoConsignacion = jdoTurnosDAO.encontrarDcpgNoConsignacionByIdDocumentoPago(idDocumentoPago.get(i).toString()))!=null){
                    numeroReferencia = dcpgNoConsignacion;
                }else{
                    numeroReferencia = null;
                }
                idTipoDocPago = jdoTurnosDAO.encontrarIdTipoDocPagoByIdCtp(idCtp);
                System.out.print("Los idTipoDocPago en el for: " + idTipoDocPago );
                tipoDocPago = jdoTurnosDAO.encontrarTipoDocPagoByIdTipoDocPago(idTipoDocPago);
                System.out.print("Los tipoDocPago en el for: " + tipoDocPago );
                bancoId = jdoTurnosDAO.encontrarIdBancoByCuentaBancaria(cuentaBancaria);
                System.out.print("Los encontrarIdBancoByCuentaBancaria en el for: " + bancoId );
                bancoNombre = jdoTurnosDAO.encontrarBancoByIdBanco(bancoId);
                System.out.print("Los bancoNombre en el for: " + bancoNombre);
                total = idCtp+"-"+idCb+"-"+cuentaBancaria+"-"+idCr+"-"+canalRecaudo+"-"+idTipoDocPago+"-"+tipoDocPago+"-"+numeroReferencia+"-"+bancoNombre;
                info.add(total);
            }
        if(cuentaBancaria != null && canalRecaudo != null){
            evRespuesta.setCanalRecaudoYcuentas(info);
        }else{
            evRespuesta.setCanalRecaudoYcuentas(error);
        }    
        
        //        CARLOS
        /**
        *@author: David A Rubio J
        *@change: REQUERIMIENTO 
        *HACE EL LLAMADO PARA LA CONSULTA del TESTAMENTO A BD SE ENVIA LOS PARAMATROS NECESARIO PARA LA CONSULTA Y SETEA LA RESPUESTA AL 
        *EVENTO evRespuesta
        */
         Testamento elTestamento;
        try{
               
            Turno turnoTT = hermod.getTurnobyWF(evento.getTurno().getIdWorkflow());
                        
            elTestamento=hermod.getTestamentoByID(turnoTT.getSolicitud().getIdSolicitud());

            evRespuesta.setTestamento(elTestamento);
        
        } catch (Exception ex){
          Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) { //cuando no encuentra el testamento
            Logger.getLogger(AnTrasladoTurno.class.getName()).log(Level.SEVERE, null, ex);
            elTestamento=new Testamento(); //envÃ­a una variable testamento vacÃ­a en el evento de respuesta
            evRespuesta.setTestamento(elTestamento);
        }

        sumaConservacion = jdoTurnosDAO.SumaConservacion(turno.getIdTurno(), turno.getAnio(),
                turno.getIdCirculo(), proceso);
        evRespuesta.setSumaConservacion(sumaConservacion);       
        
        String conservacionMayorValor = jdoTurnosDAO.liquidaConservaMayorValor(turno.getIdTurno(), turno.getAnio(),
                turno.getIdCirculo(), proceso);

        evRespuesta.setLiquidaConservacionMayorvalor(conservacionMayorValor);        
//
        evRespuesta.setTurnosAsociados(turnosSolicitudesAsociadas);
        evRespuesta.setTurnoPadre(turnoPadre);
        evRespuesta.setTurnoDerivado(turnoDerivado);
        evRespuesta.setImprimiblesPendientesTurnoPadre(local_ImprimiblesPendientes_TurnoPadre);
        evRespuesta.setImprimiblesPendientesTurnoHijos(local_ImprimiblesPendientes_TurnoHijos);
        evRespuesta.setImprimiblesPendientesTurnoPadreCount(local_ImprimiblesPendientes_TurnoPadreCount);
        evRespuesta.setListaTurnoFolioMig(turnosNoMigrados);
        evRespuesta.setImprimiblesPendientesTurnoHijosCount(local_ImprimiblesPendientes_TurnoHijosCount);
        evRespuesta.setTurnoInternet(turnoInternet);
        /*
                 * Set turnosDevoluciones en el evento respuesta.
                 * @author: Julio Alcazar
                 * @change: 7393: Acta - Requerimiento No 215 - Ver Detalles de Turno - Turnos Devolucion Negados
         */
        evRespuesta.setTurnosDevoluciones(turnosDevoluciones);
        if (minuta != null) {
            evRespuesta.setCategoriaMinuta(minuta.getCategoria().getNombre());
        }
        if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_DEVOLUCIONES)) {
            evRespuesta.setResoluciones(resoluciones);
        }

        return evRespuesta;
    }

//	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este mï¿½todo se encarga de invocar los servicios necesarios para consultar
     * un turno especï¿½fico dado su ID de Workflow
     *
     * @return <code>EvnRespTrasladoTurno</code> con la listas actualizadas.
     * @throws <code>EventoException</code>
     */
    private EvnRespTrasladoTurno consultarTurnoCertificadoCambioMatricula(EvnTrasladoTurno evento)
            throws EventoException {

        Turno turno = null;
        Folio folio = null;
        List tiposNota = null;
        Circulo circulo = evento.getCirculo();
        org.auriga.core.modelo.transferObjects.Usuario user = evento.getUsuario();

        CirculoPk circuloPk = new CirculoPk();
        circuloPk.idCirculo = circulo.getIdCirculo();

        try {
            boolean puedeConsultarTurno = false;
            Circulo circuloTemp = null;

            turno = hermod.getTurnobyWF(evento.getTurno().getIdWorkflow());

            if (turno == null) {
                throw new TurnoNoEncontradoException("El turno solicitado no existe.", null);
            }

            //SI SE QUIERE CONSULTAR UN TURNO DE OTRO CIRCULO 
            if (!turno.getSolicitud().getCirculo().getIdCirculo().equals(circulo.getIdCirculo())
                    && turno.getSolicitud().getCirculo().getIdCirculo().equals(turno.getIdCirculo())) {
                throw new ConsultaTurnoExceptionException("No puede consultar turnos de otros circulos.");
            }

            if (!String.valueOf(turno.getIdProceso()).equals(CProceso.PROCESO_CERTIFICADOS)) {
                throw new ConsultaTurnoExceptionException("El turno debe ser de Certificados.");
            }

            /*
                         * @author      :   Julio Alcï¿½zar Rivas
                         * @change      :   Los turnos deben estar en la fase CFase.CER_ENTREGAR or CFase.FINALIZADO.
                         * Caso Mantis  :   02359
             */
            if (!turno.getIdFase().equals(CFase.CER_ENTREGAR) && !turno.getIdFase().equals(CFase.FINALIZADO)) {
                throw new ConsultaTurnoExceptionException("El turno debe estar en la fase de Entrega Certificados o Finalizado.");
            }

            if ((!turno.getIdCirculo().equals(turno.getSolicitud().getCirculo().getIdCirculo()))) {
                throw new ConsultaTurnoExceptionException("no se permiten cambios de matricula en certificados de orden nacional");
            }

            Solicitud solicitud = turno.getSolicitud();
            LiquidacionTurnoCertificado liquidacion = new LiquidacionTurnoCertificado();
            List liquidaciones = solicitud.getLiquidaciones();
            for (int i = 0; i < liquidaciones.size(); i++) {
                double id = new Double(((LiquidacionTurnoCertificado) liquidaciones.get(i)).getIdLiquidacion()).doubleValue();
                if (id == solicitud.getLastIdLiquidacion()) {
                    liquidacion = (LiquidacionTurnoCertificado) liquidaciones.get(i);
                }
            }
            /*
                    * @author      :   Julio Alcï¿½zar Rivas
                    * @change      :   Si el certificado es TIPO_PERTENENCIA se valida si ha pasado por Antiguos Sistema.
                    * Caso Mantis  :   02359
             */
            boolean antiguo_sistema = false;
            if (liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_PERTENENCIA_ID)) {
                List turnohistoria = turno.getHistorials();
                Iterator itera = turnohistoria.iterator();
                while (itera.hasNext()) {
                    TurnoHistoria historial = (TurnoHistoria) itera.next();
                    if (historial.getFase().equals(CFase.ANT_REVISION_INICIAL)) {
                        antiguo_sistema = true;
                        break;
                    }
                }
            }

            if (((liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_ANTIGUO_SISTEMA_ID))
                    || (liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_ASOCIADO_ID))
                    || ((liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_PERTENENCIA_ID)) && antiguo_sistema))) {
                if (!turno.getSolicitud().getSolicitudFolios().isEmpty()) {
                    folio = ((SolicitudFolio) turno.getSolicitud().getSolicitudFolios().get(0)).getFolio();
                    if (folio == null) {
                        throw new ConsultaTurnoExceptionException("El turno no tiene un folio asociado.");
                    }
                }
            } else {
                folio = ((SolicitudFolio) turno.getSolicitud().getSolicitudFolios().get(0)).getFolio();
                if (folio == null) {
                    throw new ConsultaTurnoExceptionException("El turno no tiene un folio asociado.");
                }
            }

            /*
                    * @author      :   Julio Alcï¿½zar Rivas
                    * @change      :   validaciones de tipos de certificados exentos
                    * Caso Mantis  :   02359
             */
            if (liquidacion.getTipoTarifa() != null) {
                if (liquidacion.getTipoTarifa().equals(CTipoTarifa.EXENTO)) {
                    throw new ConsultaTurnoExceptionException("no se permiten cambios de matricula en certificados con tipo de tarifa EXENTO.");
                }
            }

            /*
                    * @author      :   Julio Alcï¿½zar Rivas
                    * @change      :   validaciones de tipos de certificados permitidos
                    * Caso Mantis  :   02359
             */
            if ((!liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_ANTIGUO_SISTEMA_ID))
                    && (!liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_ASOCIADO_ID))
                    && (!liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_PERTENENCIA_ID))
                    && (!liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_INMEDIATO_ID))
                    && (!liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_AMPLIACION_TRADICION_ID))) {

                throw new ConsultaTurnoExceptionException("no se permiten cambios de matricula en certificados diferentes a los tipos: "
                        + "Inmediatos, Asociados, Pertenecia, Antiguo Sistema y Ampliacion de Tradiciï¿½n.");

            }

            /*
                    * @author      : Julio Alcï¿½zar Rivas
                    * @change      : Validaciones para realizar cambio de matriculas de turno de certificados
                    * Caso Mantis  :   07647
             */
            CertificadosSIR certificadosSIR = new CertificadosSIR();

            if ((liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_ANTIGUO_SISTEMA_ID)
                    || liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_PERTENENCIA_ID))
                    && certificadosSIR.CreacionFolioHR(turno.getAnio(), turno.getIdCirculo(), turno.getIdProceso(), turno.getIdTurno())) {

                throw new ConsultaTurnoExceptionException("no se permite cambio de matricula de certificados de Antiguo Sistema o Pertenencia "
                        + "que crearon el folio en Hoja de Ruta.");
            }

            List turnosHist = turno.getHistorials();
            for (Iterator iter = turnosHist.iterator(); iter.hasNext();) {
                TurnoHistoria turnoHistoria = (TurnoHistoria) iter.next();
                if (turnoHistoria.getFase().equals(CFase.CER_AMPLIACION_TRADICION) && turnoHistoria.getRespuesta().equals(CRespuesta.AMPLIACION_TRADICION)) {
                    throw new ConsultaTurnoExceptionException("no se permite el cambio de matricula, por que el turno de certificado incluyo informaciï¿½n al folio.");
                }
                if (((liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_ANTIGUO_SISTEMA_ID)
                        || liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_AMPLIACION_TRADICION_ID)
                        || liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_PERTENENCIA_ID)))
                        && (turnoHistoria.getFase().equals(CFase.ANT_REVISION_INICIAL) && turnoHistoria.getRespuesta().equals(CRespuesta.NEGAR))) {

                    throw new ConsultaTurnoExceptionException("El certificado de tipo: " + liquidacion.getTipoCertificado().getNombre() + " fue negado por lo tanto no se puede cambiar la matricula.");
                }

            }
            if (liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_ASOCIADO_ID)) {
                if (!certificadosSIR.CertificadoAsociadoVal(turno.getIdWorkflow())) {
                    throw new ConsultaTurnoExceptionException("no se permite cambio de matricula de certificados asociados a registro con resoluciï¿½n devuelto");
                }
            }
            ProcesoPk id = new ProcesoPk();
            id.idProceso = 1;

            tiposNota = hermod.getTiposNotaProceso(id, CFase.CER_REIMPRESION_ESPECIAL, false);

            /*
                    * @author      : Julio Alcï¿½zar Rivas
                    * @change      : Validaciones para realizar cambio de matriculas de turno de certificados
                    * Caso Mantis  :   07647
             */
            int numImpresionesCert = ((SolicitudCertificado) solicitud).getNumImpresiones();

            ProcesoPk oid = new ProcesoPk();
            oid.idProceso = Long.parseLong(CProceso.PROCESO_CERTIFICADOS);
            Proceso proceso = hermod.getProcesoById(oid);

            Rol rol = new Rol();
            rol.setRolId(CRoles.SIR_ROL_REIMPRESION_CERTIFICADOS);
            int maxImprUsuario = hermod.getNumeroMaximoImpresiones(rol, proceso);

            if (maxImprUsuario <= numImpresionesCert - 1 && (turno.getIdFase().equals(CFase.CER_ENTREGAR) || turno.getIdFase().equals(CFase.FINALIZADO))) {
                throw new ConsultaTurnoExceptionException("El turno de certificado ha alcanzado el nï¿½mero mï¿½ximo(" + maxImprUsuario + ") de reimpresiones por lo tanto no se permite el cambio de matricula");
            }

            /*
                      * @author      :   Julio Alcï¿½zar Rivas
                      * @change      :   Valida que la matricula solo pueda ser cambiada una sola vez, que puede ser
                      *  en la fase CFase.CER_ENTREGAR o en la fase CFase.FINALIZADO.
                      * Caso Mantis  :   02359
             */
            TurnoPk turnoId = new TurnoPk();
            turnoId.anio = turno.getAnio();
            turnoId.idCirculo = turno.getIdCirculo();
            turnoId.idProceso = turno.getIdProceso();
            turnoId.idTurno = turno.getIdTurno();
            if (hermod.getCambioMatriculaAuditoria(turnoId)) {
                throw new ConsultaTurnoExceptionException("La matricula solo puede ser cambiada una vez");
            }

            /*
                    * @author      : Julio Alcï¿½zar Rivas
                    * @change      : Validaciones para realizar cambio de matriculas de turno de certificados
                    * Caso Mantis  :   07647
             */
            if (numImpresionesCert > 1) {
                throw new ConsultaTurnoExceptionException("El turno de certificado ha sido reimpreso (" + (numImpresionesCert - 1) + ") por lo tanto no se permite el cambio de matricula");
            }

            /*
                      * @author      :   Julio Alcï¿½zar Rivas
                      * @change      :   Verifica la fecha limite para cambiar la matricula a un turno de certificado
                      * Caso Mantis  :   02359
             */
            List turnosHistoria = turno.getHistorials();
            for (Iterator iter = turnosHistoria.iterator(); iter.hasNext();) {
                TurnoHistoria turnoHistoria = (TurnoHistoria) iter.next();
                if ((turnoHistoria.getFase().equals(CFase.CER_SOLICITUD) && (liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_INMEDIATO_ID)))
                        || (turnoHistoria.getFase().equals(turno.getIdFase()) && (!liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_INMEDIATO_ID)))) {
                    Calendar calendarsol = Calendar.getInstance();
                    calendarsol.setTime(turnoHistoria.getFecha());
                    calendarsol.add(Calendar.DATE, 1);
                    int dia = calendarsol.get(Calendar.DAY_OF_MONTH);
                    int mes = calendarsol.get(Calendar.MONTH);
                    int anio = calendarsol.get(Calendar.YEAR);
                    GregorianCalendar fechasol = new GregorianCalendar(anio, mes, dia);
                    Calendar calendaractual = Calendar.getInstance();
                    int dia1 = calendaractual.get(Calendar.DAY_OF_MONTH);
                    int mes1 = calendaractual.get(Calendar.MONTH);
                    int anio1 = calendaractual.get(Calendar.YEAR);
                    GregorianCalendar fechaactual = new GregorianCalendar(anio1, mes1, dia1);

                    int numDiasHabiles = 0;
                    while ((fechasol.getTime().compareTo(fechaactual.getTime()) <= 0)) {
                        try {
                            if (!forseti.isFestivo(fechasol.getTime(), circuloPk)) {
                                numDiasHabiles++;
                            }
                        } catch (ForsetiException e) {
                            Log.getInstance().error(AnTrasladoTurno.class, e.getMessage(), e);
                            throw new EventoException(e.getMessage(), e);
                        } catch (Throwable e) {
                            Log.getInstance().error(AnTrasladoTurno.class, e.getMessage(), e);
                            throw new EventoException(e.getMessage(), e);
                        }
                        fechasol.add(GregorianCalendar.DATE, 1);
                    }
                    if (numDiasHabiles > 4) {
                        if ((!liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_INMEDIATO_ID))) {
                            throw new ConsultaTurnoExceptionException("La matricula solo puede ser cambiada en los 5 primeros dias habiles apartir de la fase entrega o entregado");
                        }
                        throw new ConsultaTurnoExceptionException("La matricula solo puede ser cambiada en los 5 primeros dias habiles apartir de la radicaciï¿½n");
                    }
                    break;
                }

            }

        } catch (HermodException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno .", e);
            throw new TurnoNoEncontradoException(e.getMessage(), e);
        } catch (ConsultaTurnoExceptionException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno.", e);
            throw e;
        } catch (TurnoNoEncontradoException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno.", e);
            throw e;
        } catch (Throwable e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno .", e);
            throw new TurnoNoEncontradoException(e.getMessage(), e);
        }

        EvnRespTrasladoTurno evRespuesta;
        evRespuesta = new EvnRespTrasladoTurno(turno, EvnRespTrasladoTurno.TURNO_CERTIFICADO_CAMBIO_MATRICULA);
        evRespuesta.setTurno(turno);
        evRespuesta.setFolio(folio);
        evRespuesta.setListaTipoNota(tiposNota);

        return evRespuesta;
    }

//	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este mï¿½todo se encarga de invocar los servicios necesarios para consultar
     * un turno especï¿½fico dado su ID de Workflow
     *
     * @return <code>EvnRespTrasladoTurno</code> con la listas actualizadas.
     * @throws <code>EventoException</code>
     */
    private EvnRespTrasladoTurno cancelarConsultarTurnoCertificadoCambioMatricula(EvnTrasladoTurno evento)
            throws EventoException {

        Turno turno = null;
        EvnRespTrasladoTurno evRespuesta;
        evRespuesta = new EvnRespTrasladoTurno(turno, EvnRespTrasladoTurno.CANCELAR_CAMBIO_MATRICULA);
        return evRespuesta;
    }

    /**
     * Este mï¿½todo se encarga de invocar los servicios necesarios para consultar
     * un ciudadano dado su ID de Workflow
     *
     * @return <code>EvnRespTrasladoTurno</code> con la listas actualizadas.
     * @throws <code>EventoException</code>
     */
    private EvnRespTrasladoTurno consultarCiudadanoEdicion(EvnTrasladoTurno evento) throws EventoException {

        Ciudadano ciudadanoToEdit = evento.getCiudadanoToEdit();
        Ciudadano ciudadanoCompleto;
        try {
            ciudadanoCompleto = forseti.getCiudadanoByDocumento(ciudadanoToEdit.getTipoDoc(), ciudadanoToEdit.getDocumento(), ciudadanoToEdit.getIdCirculo());

            if (ciudadanoCompleto == null) {
                throw new TurnoNoEncontradoException("El Ciudadano identificado: " + ciudadanoToEdit.getTipoDoc() + " - " + ciudadanoToEdit.getDocumento() + ", No existe.", null);
            }
            //Se valida que este no este en ninguna anotacionCiudadano ni en AnotacionCiudadanoTMP;
            //No tenga el ciudadano en TEmporal ni en definitivo
            boolean estaCiudadano = forseti.estaCiudadanoEnAnotacionCiudadano(ciudadanoCompleto.getIdCiudadano());

            if (estaCiudadano) {
                throw new TurnoNoEncontradoException("El Ciudadano identificado: " + ciudadanoToEdit.getTipoDoc() + " - " + ciudadanoToEdit.getDocumento() + ", esta asociado en uno o varios Folios.", null);
            }
        } catch (ForsetiException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno .", e);
            throw new TurnoNoEncontradoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno .", e);
            throw new TurnoNoEncontradoException(e.getMessage(), e);
        }
        EvnRespTrasladoTurno evRespuesta;
        evRespuesta = new EvnRespTrasladoTurno(EvnRespTrasladoTurno.VALIDAR_CIUDADANO_EDICION_OK);
        evRespuesta.setTipoEvento(EvnRespTrasladoTurno.VALIDAR_CIUDADANO_EDICION_OK);
        evRespuesta.setCiudadanoToEdit(ciudadanoCompleto);
        return evRespuesta;
    }

    /**
     * Este mï¿½todo se encarga de invocar los servicios necesarios para realizar
     * la edicion de un ciudadano dado su ID de Workflow
     *
     * @return <code>EvnRespTrasladoTurno</code> con la listas actualizadas.
     * @throws <code>EventoException</code>
     */
    private EvnRespTrasladoTurno realizarCiudadanoEdicion(EvnTrasladoTurno evento) throws EventoException {

        Ciudadano ciudadanoToEdit = evento.getCiudadanoToEdit();
        Usuario usuario = evento.getUsuarioNegocio();
        Ciudadano ciudadanoCompleto;

        String mensajeEdicion = "El Ciudadano identificado: " + ciudadanoToEdit.getTipoDoc() + " - " + ciudadanoToEdit.getDocumento() + ", Se actualizï¿½ Satisfactoriamente.";
        boolean rta = false;
        try {

            rta = forseti.updateCiudadanoAdministrativa(ciudadanoToEdit, usuario);

            ciudadanoCompleto = forseti.getCiudadanoByDocumento(ciudadanoToEdit.getTipoDoc(), ciudadanoToEdit.getDocumento(), ciudadanoToEdit.getIdCirculo());

            if (ciudadanoCompleto == null) {
                throw new TurnoNoEncontradoException("El Ciudadano identificado: " + ciudadanoToEdit.getTipoDoc() + " - " + ciudadanoToEdit.getDocumento() + ", No pudo ser Actualizado.", null);
            }

        } catch (ForsetiException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno .", e);
            throw new TurnoNoEncontradoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno .", e);
            throw new TurnoNoEncontradoException(e.getMessage(), e);
        }
        EvnRespTrasladoTurno evRespuesta;
        evRespuesta = new EvnRespTrasladoTurno(EvnRespTrasladoTurno.VALIDAR_CIUDADANO_EDICION_OK);
        evRespuesta.setTipoEvento(EvnRespTrasladoTurno.VALIDAR_CIUDADANO_EDICION_OK);
        evRespuesta.setCiudadanoToEdit(ciudadanoCompleto);
        evRespuesta.setMensajeEdicion(mensajeEdicion);
        return evRespuesta;
    }

    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este mï¿½todo se encarga de invocar los servicios necesarios para consultar
     * un turno especï¿½fico dado su ID de Workflow
     *
     * @return <code>EvnRespTrasladoTurno</code> con la listas actualizadas.
     * @throws <code>EventoException</code>
     */
    private EvnRespTrasladoTurno consultarTurnoPorMatricula(EvnTrasladoTurno evento)
            throws EventoException {

        List turnos = null;
        try {
            turnos = hermod.getTurnosByMatricula(evento.getFolio().getIdMatricula());
            if (turnos == null) {
                throw new TurnoNoEncontradoException(
                        "No se encontraron turnos relacionados con la matricula proporcionada.",
                        null);
            }
            if (turnos.isEmpty()) {
                throw new TurnoNoEncontradoException(
                        "No se encontraron turnos relacionados con la matricula proporcionada.",
                        null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno .", e);
            throw new TurnoNoEncontradoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno .", e);
            throw new TurnoNoEncontradoException(e.getMessage(), e);
        }

        EvnRespTrasladoTurno evRespuesta
                = new EvnRespTrasladoTurno(turnos, EvnRespTrasladoTurno.CONSULTAR_TURNOS_POR_MATRICULA_OK);
        evRespuesta.setTurnos(turnos);
        return evRespuesta;
    }

    /**
     * Este mï¿½todo se encarga de invocar los servicios necesarios para consultar
     * un turno especï¿½fico dado su ID de Workflow
     *
     * @return <code>EvnRespTrasladoTurno</code> con la listas actualizadas.
     * @throws <code>EventoException</code>
     */
    private EvnRespTrasladoTurno anularTurno(EvnTrasladoTurno evento)
            throws EventoException {

        Turno turno = null;
        Circulo circulo = evento.getCirculo();
        org.auriga.core.modelo.transferObjects.Usuario user = evento.getUsuario();
        Usuario usuarioSIR = evento.getUsuarioNegocio();
        List turnosAnteriores = null;
        List turnosSiguientes = null;
        /**
         * @author: Fernando Padilla
         * @change: Esta variable nunca es usada.
         *
         */
        //List turnosSolicitudesAsociadas = null;

        try {
            /**
             * @author: Fernando Padilla
             * @change: Estas variables nunca son usadas.
            *
             */
            //boolean puedeConsultarTurno = false;
            //Circulo circuloTemp = null;

            turno = hermod.getTurnobyWF(evento.getTurno().getIdWorkflow());

            
            /*
            Requerimiento validar limite de 2 días habile a partir de creado y para todos diferentes al registrador
            */
            
            
            boolean existeRolRegistrador = false;
            
            for(Rol r: evento.getRol())
            {
                if(r.getRolId().equals(CRoles.SIR_ROL_REGISTRADOR))
                    existeRolRegistrador = true;
            }
            
            
            if(!existeRolRegistrador)//validar si no existe rol registrador
            {
                Calendar tempFecha = Calendar.getInstance();//establecer fecha actual
                Calendar c = new GregorianCalendar(tempFecha.get(Calendar.YEAR),
                        tempFecha.get(Calendar.MONTH),
                        tempFecha.get(Calendar.DAY_OF_MONTH));
                
                            
                Calendar c1 = Calendar.getInstance();
               c1.setTime(turno.getFechaInicio());//establecer fecha inicio turno
              
               //proceso de comparacion
               int w1 = c1.get(Calendar.DAY_OF_WEEK);
               c1.add(Calendar.DAY_OF_WEEK, -w1);
               
               Calendar c2 = Calendar.getInstance();
               c2.setTime(c.getTime());
               
               int w2 = c2.get(Calendar.DAY_OF_WEEK);
               c2.add(Calendar.DAY_OF_WEEK, -w2);

               //end Saturday to start Saturday 
               long days = (c2.getTimeInMillis()-c1.getTimeInMillis())/(1000*60*60*24);
               long daysWithoutWeekendDays = days-(days*2/7);

               // Adjust days to add on (w2) and days to subtract (w1) so that Saturday
               // and Sunday are not included
               if (w1 == Calendar.SUNDAY && w2 != Calendar.SATURDAY) {
                   w1 = Calendar.MONDAY;
               } else if (w1 == Calendar.SATURDAY && w2 != Calendar.SUNDAY) {
                   w1 = Calendar.FRIDAY;
               } 

               if (w2 == Calendar.SUNDAY) {
                   w2 = Calendar.MONDAY;
               } else if (w2 == Calendar.SATURDAY) {
                   w2 = Calendar.FRIDAY;
               }
               
               if(daysWithoutWeekendDays-w1+w2 > 2)
               {
                   throw new HermodException("El turno supera la fecha límite de dos (2) días hábiles para ser anulado");
               }
            }
            
            if ((!circulo.getIdCirculo().equals(turno.getSolicitud().getCirculo().getIdCirculo()))
                    && (!turno.getIdCirculo().equals(turno.getSolicitud().getCirculo().getIdCirculo()))) {
                throw new HermodException("El turno es de orden nacional y solo puede ser anulado en el circulo "
                        + turno.getSolicitud().getCirculo().getNombre());
            }
            /**
             * @author: Diana Lora
             * @change: Mantis 0010028: Acta - Requerimiento No 058_151 -
             * Certificados de otros circulos.
             */
            if (turno.isNacional()) {
                Calendar siguienteFechaInicio = Calendar.getInstance();
                siguienteFechaInicio.setTime(turno.getFechaInicio());
                siguienteFechaInicio.add(Calendar.DAY_OF_MONTH, 1);

                Calendar tempFecha = Calendar.getInstance();
                Calendar hoy = new GregorianCalendar(tempFecha.get(Calendar.YEAR),
                        tempFecha.get(Calendar.MONTH),
                        tempFecha.get(Calendar.DAY_OF_MONTH));
                if (hoy.getTime().after(siguienteFechaInicio.getTime())) {
                    throw new HermodException("Se venciï¿½ el tiempo establecido para anular turnos de certificados nacionales");
                }
            }

            TurnoPk turnoId = new TurnoPk();
            turnoId.anio = turno.getAnio();
            turnoId.idCirculo = turno.getIdCirculo();
            turnoId.idProceso = turno.getIdProceso();
            turnoId.idTurno = turno.getIdTurno();

            if (turno.getAnulado() != null) {
                if (turno.getAnulado().equals(CTurno.TURNO_ANULADO)) {
                    throw new HermodException("El turno ya se encuentra anulado. ");
                }
            }

            if (!turno.getSolicitud().getCirculo().getIdCirculo().equals(circulo.getIdCirculo())
                    && turno.getSolicitud().getCirculo().getIdCirculo().equals(turno.getIdCirculo())) {
                throw new HermodException("No puede anular turnos de otros circulos. ");
            }

            /*JALcazar Caso Mantis 3883 Requerimiento No 180 - Error al anular turno
                         * Se incluyen 2 nuevas validaciones:
                         * 1) PROCESO CERTIFICADOS INMEDIATOS validar que no se permita anular turnos que realizaron reimpresiones
                         * 2) PROCESO REGISTRO validar que no se permita anular turnos de registro que se avanzaron a la mesa de control
             */
            if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_CERTIFICADOS)) {
                Solicitud solicitud = turno.getSolicitud();
                SolicitudCertificado solicitudCert = (SolicitudCertificado) solicitud;
                int numImpresionesCert = solicitudCert.getNumImpresiones();

                LiquidacionTurnoCertificado liqui = new LiquidacionTurnoCertificado();
                List liquidaciones = solicitud.getLiquidaciones();
                for (int i = 0; i < liquidaciones.size(); i++) {
                    double id = new Double(((LiquidacionTurnoCertificado) liquidaciones.get(i)).getIdLiquidacion()).doubleValue();
                    if (id == solicitud.getLastIdLiquidacion()) {
                        liqui = (LiquidacionTurnoCertificado) liquidaciones.get(i);
                    }
                }

                if (((liqui.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_INMEDIATO_ID))) && numImpresionesCert > 1) {
                    throw new HermodException("No se puede anular turnos de certificados que realizaron al menos una reimpresion. ");
                }
            }

            if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_REGISTRO)) {
                boolean avanzo_mesa_control = false;
                List historial = turno.getHistorials();
                for (int i = 0; i < historial.size(); i++) {
                    TurnoHistoria turnoHistoria = (TurnoHistoria) historial.get(i);
                    if (turnoHistoria.getFase().equals(CFase.REG_MESA_CONTROL)) {
                        avanzo_mesa_control = true;
                        break;
                    }
                }

                if (avanzo_mesa_control) {
                    throw new HermodException("No se puede anular turnos de registro que se avanzaron a la mesa de control. ");
                }
            }

            turno.setObservacionesAnulacion(evento.getObservacionesAnulacion());
            turno.setAnulado(CTurno.TURNO_ANULADO);
            turno.setUsuarioAnulacion(evento.getUsuarioNegocio());
            turno.setFechaFin(new Date());

            //------ VALIDAR SI EL TURNO PUEDE SER ANULAR -------//
            boolean ok = hermod.validarAnulacionTurno(turnoId);
            //------ FIN VALIDAR SI EL TURNO PUEDE SER ANULAR -------//
            List sfolios = turno.getSolicitud().getSolicitudFolios();
            SolicitudFolio sf = null;
            if (ok) {

                //---------- ANULAR TURNOS HIJOS -----//
                long idProceso = turno.getIdProceso();
                if (idProceso != Long.parseLong(CProceso.PROCESO_DEVOLUCIONES)) {
                    List solicitudesHijas = turno.getSolicitud().getSolicitudesHijas();
                    Iterator ithijas = solicitudesHijas.iterator();
                    while (ithijas.hasNext()) {
                        SolicitudAsociada solAsoc = (SolicitudAsociada) ithijas.next();
                        Solicitud sol = solAsoc.getSolicitudHija();
                        SolicitudPk sid = new SolicitudPk();
                        sid.idSolicitud = sol.getIdSolicitud();
                        Turno turnohijo = hermod.getTurnoBySolicitud(sid);
                        turnohijo = hermod.getTurnobyWF(turnohijo.getIdWorkflow());
                        TurnoPk turnohijoId = new TurnoPk();
                        turnohijoId.anio = turnohijo.getAnio();
                        turnohijoId.idCirculo = turnohijo.getIdCirculo();
                        turnohijoId.idProceso = turnohijo.getIdProceso();
                        turnohijoId.idTurno = turnohijo.getIdTurno();

                        turnohijo.setObservacionesAnulacion(turno.getObservacionesAnulacion());
                        turnohijo.setAnulado(turno.getAnulado());
                        turnohijo.setUsuarioAnulacion(turno.getUsuarioAnulacion());
                        turnohijo.setFechaFin(new Date());

                        try {
                            //------ DESBLOQUEAR CAMBIOS TEMPORALES TURNO -------//
                            forseti.deshacerCambiosTurno(turnohijoId, usuarioSIR, true);
                            hermod.removeDevolutivasFromTurno(turnohijoId);

                            //------ FIN DESBLOQUEAR CAMBIOS TEMPORALES TURNO -------//
                        } catch (ForsetiException e) {
                            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno .", e);
                        }

                        //------ ANULAR TURNO -------//
                        hermod.anularTurno(turnohijo);
                        //------ FIN ANULAR TURNO -------//
                        /**
                         * @author Fernando Padilla Velez
                         * @change Acta - Requerimiento No 178 - Integraciï¿½n
                         * Gestiï¿½n Documental y SIR, Se agrega esta fragmento de
                         * codigo para la publicacion del mensaje del turno
                         * creado.
                         */
                        String temp = turnohijo.getIdFase();
                        turnohijo.setIdFase("FINALIZADO");
                        /**
                         * @author Fernando Padilla Velez
                         * @change 6760: Acta - Requerimiento No 191 - Pantalla
                         * Administrativa SGD, Se comentan estan lineas, ya que
                         * se realizï¿½ refactoring al proceso y ya no son
                         * necesarias. S agrega el nuevo envio del mensaje.
                         */
                        //PublisherIntegracion  clienteJMS = new PublisherIntegracion();
                        //clienteJMS.setUsuario);
                        //clienteJMS.setTurno(turnohijo);
                        //clienteJMS.enviar();
                        SGD sgd = new SGD(turnohijo, usuarioSIR.getUsername());
                        sgd.enviarAnulado();
                        turnohijo.setIdFase(temp);

                    }
                } else {
                    SolicitudPk sid = new SolicitudPk();
                    sid.idSolicitud = turno.getSolicitud().getIdSolicitud();

                    //Borrar las solicitudes asociadas
                    /*
                                         * Se mantiene la relacion de un turno de devolucion con su turno anterior
                                         * @author: Julio Alcazar
                                         * @change: 7393: Acta - Requerimiento No 215 - Ver Detalles de Turno - Turnos Devolucion Negados
                     */
                    //hermod.deleteTurnoAnterior(sid);
                }

                //---------- FIN ANULAR TURNOS HIJOS ---//
                try {
//				------ DESBLOQUEAR CAMBIOS TEMPORALES TURNO -------//
                    if (sfolios != null && sfolios.size() > 0) {
                        Usuario usuarioBloqueo = null;

                        sf = (SolicitudFolio) sfolios.get(0);
                        FolioPk fid = new FolioPk();
                        fid.idMatricula = sf.getIdMatricula();
                        usuarioBloqueo = forseti.getBloqueoFolio(fid);

                        if (usuarioBloqueo != null) {
                            forseti.deshacerCambiosTurno(turnoId, usuarioBloqueo, true);
                        }

                    }

                    //------ FIN DESBLOQUEAR CAMBIOS TEMPORALES TURNO -------//
                } catch (ForsetiException e) {
                    Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno .", e);
                }

                //------ ELIMINAR LOS FOLIOS TEMPORALES RELACIONADOS CON EL TURNO ------//
                for (Iterator itr = sfolios.iterator(); itr.hasNext();) {
                    sf = (SolicitudFolio) itr.next();
                    String matricula = sf.getIdMatricula();
                    FolioPk folioID = new FolioPk();
                    folioID.idMatricula = matricula;
                    Folio folioTemp = forseti.getFolioByID(matricula);
                    if (folioTemp != null) {
                        if ((!folioTemp.isDefinitivo()) && (folioTemp.getRadicacion().equals(turno.getIdWorkflow()))) {
                            forseti.deleteFolio(folioID, "Anulaciï¿½n del turno Asociado");
                        }
                    }
                }

                /**
                 * @autor HGOMEZ
                 * @mantis 13176
                 * @Requerimiento 061_453_Requerimiento_Auditoria
                 * @descripcion Mantiene la informaciï¿½n del usuario.
                 */
                if (idProceso == Long.parseLong(CProceso.PROCESO_REPARTO_NOTARIAL_MINUTAS)) {
                    java.util.Map infoUsuario = new java.util.HashMap();
                    if (evento.getInfoUsuario() != null) {
                        infoUsuario.put("user", evento.getInfoUsuario().getUser());
                        infoUsuario.put("host", evento.getInfoUsuario().getHost());
                        infoUsuario.put("logonTime", evento.getInfoUsuario().getLogonTime());
                        infoUsuario.put("address", evento.getInfoUsuario().getAddress());
                    }

                    JDOTurnosDAO jdoTurnosDAO = new JDOTurnosDAO();
                    jdoTurnosDAO.setDatosTerminal(infoUsuario);
                }

                //------ ANULAR TURNO -------//
                hermod.anularTurno(turno);

                /**
                 * @author Fernando Padilla Velez
                 * @change Acta - Requerimiento No 178 - Integraciï¿½n Gestiï¿½n
                 * Documental y SIR, Se agrega esta fragmento de codigo para la
                 * publicacion del mensaje del turno creado.
                 */
                if (turno.getIdProceso() == 1) {
                    if (turno.getSolicitud().getLiquidaciones() != null
                            && turno.getSolicitud().getLiquidaciones().size() > 0) {
                        Liquidacion liquidacion = (Liquidacion) turno.getSolicitud().
                                getLiquidaciones().get(turno.getSolicitud().getLiquidaciones().size() - 1);

                        if (liquidacion instanceof LiquidacionTurnoCertificado) {
                            LiquidacionTurnoCertificado liquidaCert = (LiquidacionTurnoCertificado) liquidacion;
                            if (("PERTENENCIA").equals(liquidaCert.getTipoCertificado().getNombre())
                                    || ("AMPLIACION_TRADICION").equals(liquidaCert.getTipoCertificado().getNombre())
                                    || ("ANTIGUO_SISTEMA").equals(liquidaCert.getTipoCertificado().getNombre())) {
                                String temp = turno.getIdFase();
                                turno.setIdFase("FINALIZADO");
                                /**
                                 * @author Fernando Padilla Velez
                                 * @change 6760: Acta - Requerimiento No 191 -
                                 * Pantalla Administrativa SGD, Se comentan
                                 * estan lineas, ya que se realizï¿½ refactoring
                                 * al proceso y ya no son necesarias. S agrega
                                 * el nuevo envio del mensaje.
                                 */
                                SGD sgd = new SGD(turno, usuarioSIR.getUsername());
                                sgd.enviarAnulado();
                                turno.setIdFase(temp);
                            }
                        }
                    }
                } else {
                    /**
                     * @author Fernando Padilla Velez
                     * @change 6760: Acta - Requerimiento No 191 - Pantalla
                     * Administrativa SGD, Se comentan estan lineas, ya que se
                     * realizï¿½ refactoring al proceso y ya no son necesarias. S
                     * agrega el nuevo envio del mensaje.
                     */
                    if (turno.getIdProceso() == 2
                            || turno.getIdProceso() == 3
                            || turno.getIdProceso() == 4
                            || turno.getIdProceso() == 6) {
                        SGD sgd = new SGD(turno, usuarioSIR.getUsername());
                        sgd.enviarAnulado();
                    }
                }

                hermod.removeDevolutivasFromTurno(turnoId);

                //------ FIN ANULAR TURNO -------//
            } else {
                throw new TurnoNoEncontradoException("El turno no es valido para eliminarse.");
            }

        } catch (HermodException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno .", e);
            throw new TurnoNoEncontradoException(e.getMessage(), e);
        } catch (ConsultaTurnoExceptionException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno.", e);
            throw e;
        } catch (TurnoNoEncontradoException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno.", e);
            throw e;
        } catch (Throwable e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno .", e);
            throw new TurnoNoEncontradoException(e.getMessage(), e);
        }

        EvnRespTrasladoTurno evRespuesta
                = new EvnRespTrasladoTurno(
                        turno,
                        turnosAnteriores,
                        turnosSiguientes,
                        EvnRespTrasladoTurno.ANULAR_TURNO_POR_IDENTIFICADOR_OK);
        return evRespuesta;
    }

    /**
     * Este mï¿½todo se encarga de invocar los servicios necesarios para consultar
     * un turno especï¿½fico dado su ID de Workflow
     *
     * @return <code>EvnRespTrasladoTurno</code> con la listas actualizadas.
     * @throws <code>EventoException</code>
     */
    private EvnRespTrasladoTurno habilitarTurno(EvnTrasladoTurno evento)
            throws EventoException {

        Turno turno = null;
        Circulo circulo = evento.getCirculo();
        org.auriga.core.modelo.transferObjects.Usuario user = evento.getUsuario();
        Usuario usuarioSIR = evento.getUsuarioNegocio();
        List turnosAnteriores = null;
        List turnosSiguientes = null;
        List turnosSolicitudesAsociadas = null;

        try {
            boolean puedeConsultarTurno = false;
            Circulo circuloTemp = null;

            turno = hermod.getTurnobyWF(evento.getTurno().getIdWorkflow());

            TurnoPk turnoId = new TurnoPk();
            turnoId.anio = turno.getAnio();
            turnoId.idCirculo = turno.getIdCirculo();
            turnoId.idProceso = turno.getIdProceso();
            turnoId.idTurno = turno.getIdTurno();

            if (turno.getAnulado() != null) {
                if (turno.getAnulado().equals(CTurno.TURNO_NO_ANULADO)) {
                    throw new HermodException("El turno no se encuentra anulado. ");
                }
            }

            if (!turno.getSolicitud().getCirculo().getIdCirculo().equals(circulo.getIdCirculo())
                    && turno.getSolicitud().getCirculo().getIdCirculo().equals(turno.getIdCirculo())) {
                throw new HermodException("No puede habilitar turnos de otros circulos. ");
            }
            
            for (Iterator iterLiquid = turno.getSolicitud().getLiquidaciones().iterator(); iterLiquid.hasNext();) {
            Liquidacion liquidacion = (Liquidacion) iterLiquid.next();

            for (Iterator iterApPagos = liquidacion.getPago().getAplicacionPagos().iterator(); iterApPagos.hasNext();) {
                AplicacionPago aplicacionPago = (AplicacionPago) iterApPagos.next();
                JDOPagosDAO mod = new JDOPagosDAO();
                            
                DocumentoPago dp = mod.getDocumentobyIdDocPago(aplicacionPago.getDocumentoPago().getIdDocumentoPago()) ;
                
                   String canal = mod.getCanalPago(dp.getTipoPago());
                        
                        if(canal.equals("DCPG_NO_CONSIGNACION")){
                                
                            if(dp.getConsignacion_ant() != null)
                            {
                                if(mod.numeroDeConsignacionEnUso(dp.getConsignacion_ant(), dp.getIdDocumentoPago()))
                                {
                                    throw new HermodException("El turno no puede habilitarse porque el pago relacionado ya fue usado en un turno distinto. ");
                                }

                            }
                        }
                        
                        if(canal.equals("DCPG_NO_TARJETA")){
                            if(dp.getAprobacion_ant() !=null || dp.getTarjeta_ant()!= null)
                            {

                                if(mod.datosTarjetaEnUso(dp.getTarjeta_ant(), dp.getAprobacion_ant(), dp.getIdDocumentoPago()))
                                {
                                    throw new HermodException("El turno no puede habilitarse porque el pago relacionado ya fue usado en un turno distinto. ");
                                }
                            }
                        }
                            
                        if(canal.equals("DCPG_NO_APROBACION")){
                            if(dp.getAprobacion_ant() !=null)
                            {

                                if(mod.numeroDeAprobacionEnUso(dp.getAprobacion_ant(), dp.getIdDocumentoPago()))
                                {
                                    throw new HermodException("El turno no puede habilitarse porque el pago relacionado ya fue usado en un turno distinto. ");
                                }
                            }
                        }


                    }
                
                
                
                
            }

            if (turno.getSolicitud() instanceof SolicitudCertificado) {
                SolicitudCertificado solicitud = (SolicitudCertificado) turno.getSolicitud();
                LiquidacionTurnoCertificado liquidacion = new LiquidacionTurnoCertificado();
                List liquidaciones = solicitud.getLiquidaciones();
                for (int i = 0; i < liquidaciones.size(); i++) {
                    double id = new Double(((LiquidacionTurnoCertificado) liquidaciones.get(i)).getIdLiquidacion()).doubleValue();
                    if (id == solicitud.getLastIdLiquidacion()) {
                        liquidacion = (LiquidacionTurnoCertificado) liquidaciones.get(i);
                    }
                }

                /*JAlcazar 23/10/2009 Caso MANTIS 0002358
                                * Se valida que si el turno es de certificado asociado,
                                * su solicitud padre no se encuentre anulada
                                //---------- HABILITAR TURNOS CERTIFICADO ASOCIADO -----//
                 */
                List padre = turno.getSolicitud().getSolicitudesPadres();
                Iterator itPadre = padre.iterator();
                while (itPadre.hasNext()) {
                    SolicitudAsociada solpadre = (SolicitudAsociada) itPadre.next();
                    Solicitud sol = solpadre.getSolicitudPadre();
                    SolicitudPk sid = new SolicitudPk();
                    sid.idSolicitud = sol.getIdSolicitud();
                    Turno turnopadre = hermod.getTurnoBySolicitud(sid);
                    if (turnopadre.getAnulado().equals(CTurno.TURNO_ANULADO)) {
                        throw new HermodException("Este Turno esta asociado a un Turno que se encuentra anulado. ");
                    }
                }
                /*----------------------------------------------------------------------------------------*/

                if (!solicitud.getCirculo().getIdCirculo().equals(circulo.getIdCirculo())
                        && !solicitud.getCirculo().getIdCirculo().equals(turno.getIdCirculo())) {
                    throw new HermodException("Este turno es de orden Nacional y solo puede ser habilitado en " + solicitud.getCirculo().getNombre());
                }

                String tipoCert = liquidacion.getTipoCertificado().getNombre();
                if (tipoCert != null && tipoCert.equals(CTipoCertificado.TIPO_NACIONAL_NOMBRE)) {
                    if (turno.getIdFase() != null) {
                        if (turno.getIdFase().equals(CTurno.FINALIZADO)) {
                            throw new HermodException("El turno ya ha sido entregado.");
                        } else {
                            Date today = new Date();
                            String fechaAct;
                            fechaAct = DateFormatUtil.format(today);
                            Date dateTurno = turno.getFechaInicio();
                            String fechaTurno;
                            fechaTurno = DateFormatUtil.format(dateTurno);
                            if (!fechaTurno.equals(fechaAct)) {
                                throw new HermodException("La fecha de radicacion del turno no coincide con la fecha actual.");
                            }
                        }
                    }
                }
            }

            //Se debe validar que la fecha de anulacion sea la misma.
            turno.setObservacionesHabilitar(evento.getObservacionesHabilitar());
            turno.setAnulado(CTurno.TURNO_NO_ANULADO);
            turno.setUsuarioAnulacion(evento.getUsuarioNegocio());
            turno.setFechaFin(null);
            /*JAlcazar 11/09/2009 Caso MANTIS 0002358
             * Se agrega al proceso de habilitar turnos de Registro la posibilidad de habilitar
             * los turnos de certificados asociados si los hay, en el mismo instante de la solicitud
             * para el turno de registro
             //---------- HABILITAR TURNOS HIJOS -----//
             */
            if (turno.getSolicitud() instanceof SolicitudRegistro) {

                long idProceso = turno.getIdProceso();
                if (idProceso != Long.parseLong(CProceso.PROCESO_DEVOLUCIONES)) {
                    List solicitudesHijas = turno.getSolicitud().getSolicitudesHijas();
                    Iterator ithijas = solicitudesHijas.iterator();
                    while (ithijas.hasNext()) {
                        SolicitudAsociada solAsoc = (SolicitudAsociada) ithijas.next();
                        Solicitud sol = solAsoc.getSolicitudHija();
                        SolicitudPk sid = new SolicitudPk();
                        sid.idSolicitud = sol.getIdSolicitud();
                        Turno turnohijo = hermod.getTurnoBySolicitud(sid);
                        if (CTurno.TURNO_ANULADO.equals(turnohijo.getAnulado())) {
                            turnohijo = hermod.getTurnobyWF(turnohijo.getIdWorkflow());
                            TurnoPk turnohijoId = new TurnoPk();
                            turnohijoId.anio = turnohijo.getAnio();
                            turnohijoId.idCirculo = turnohijo.getIdCirculo();
                            turnohijoId.idProceso = turnohijo.getIdProceso();
                            turnohijoId.idTurno = turnohijo.getIdTurno();

                            turnohijo.setObservacionesAnulacion(evento.getObservacionesHabilitar());
                            turnohijo.setAnulado(CTurno.TURNO_NO_ANULADO);
                            turnohijo.setUsuarioAnulacion(evento.getUsuarioNegocio());
                            turnohijo.setFechaFin(null);

                            //------ HABILITAR TURNO -------//
                            hermod.habilitarTurno(turnohijo);
                            boolean mensaje = true;

                            /**
                             * @author Fernando Padilla Velez
                             * @change Acta - Requerimiento No 178 - Integraciï¿½n
                             * Gestiï¿½n Documental y SIR, Se agrega esta
                             * fragmento de codigo para la publicacion del
                             * mensaje del turno creado.
                             */
                            String temp = turnohijo.getIdFase();
                            turnohijo.setIdFase("SOLICITUD");
                            /**
                             * @author Fernando Padilla Velez
                             * @change 6760: Acta - Requerimiento No 191 -
                             * Pantalla Administrativa SGD, Se borran estan
                             * lineas, ya que se realizï¿½ refactoring al proceso
                             * y ya no son necesarias. S agrega el nuevo envio
                             * del mensaje.
                             */
                            SGD sgd = new SGD(turnohijo, usuarioSIR.getUsername());
                            sgd.enviarHabilitado();
                            turnohijo.setIdFase(temp);
                            //------ FIN HABILITAR TURNO -------//
                        }
                    }

                    //---------- FIN ANULAR TURNOS HIJOS ---//
                }
            }

            hermod.habilitarTurno(turno);
            /**
             * @author Fernando Padilla Velez
             * @change Acta - Requerimiento No 178 - Integraciï¿½n Gestiï¿½n
             * Documental y SIR, Se agrega esta fragmento de codigo para la
             * publicacion del mensaje del turno creado.
             */

            if (turno.getIdProceso() == 1) {
                if (turno.getSolicitud().getLiquidaciones() != null
                        && turno.getSolicitud().getLiquidaciones().size() > 0) {
                    Liquidacion liquidacion = (Liquidacion) turno.getSolicitud().
                            getLiquidaciones().get(turno.getSolicitud().getLiquidaciones().size() - 1);

                    if (liquidacion instanceof LiquidacionTurnoCertificado) {
                        LiquidacionTurnoCertificado liquidaCert = (LiquidacionTurnoCertificado) liquidacion;
                        if (("PERTENENCIA").equals(liquidaCert.getTipoCertificado().getNombre())
                                || ("AMPLIACION_TRADICION").equals(liquidaCert.getTipoCertificado().getNombre())
                                || ("ANTIGUO_SISTEMA").equals(liquidaCert.getTipoCertificado().getNombre())) {
                            /**
                             * @author Fernando Padilla Velez
                             * @change 6760: Acta - Requerimiento No 191 -
                             * Pantalla Administrativa SGD, Se borran estan
                             * lineas, ya que se realizï¿½ refactoring al proceso
                             * y ya no son necesarias. S agrega el nuevo envio
                             * del mensaje.
                             */
                            SGD sgd = new SGD(turno, usuarioSIR.getUsername());
                            sgd.enviarHabilitado();
                        }
                    }
                }
            } else {
                /**
                 * @author Fernando Padilla Velez
                 * @change 6760: Acta - Requerimiento No 191 - Pantalla
                 * Administrativa SGD, Se borran estan lineas, ya que se realizï¿½
                 * refactoring al proceso y ya no son necesarias. S agrega el
                 * nuevo envio del mensaje.
                 */
                if (turno.getIdProceso() == 2 || turno.getIdProceso() == 3
                        || turno.getIdProceso() == 4 || turno.getIdProceso() == 6) {

                    SGD sgd = new SGD(turno, usuarioSIR.getUsername());
                    sgd.enviarHabilitado();
                }

            }

        } catch (HermodException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno .", e);
            throw new TurnoNoEncontradoException(e.getMessage(), e);
        } catch (ConsultaTurnoExceptionException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno.", e);
            throw e;
        } catch (TurnoNoEncontradoException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno.", e);
            throw e;
        } catch (Throwable e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno .", e);
            throw new TurnoNoEncontradoException(e.getMessage(), e);
        }

        EvnRespTrasladoTurno evRespuesta
                = new EvnRespTrasladoTurno(
                        turno,
                        turnosAnteriores,
                        turnosSiguientes,
                        EvnRespTrasladoTurno.HABILITAR_TURNO_POR_IDENTIFICADOR_OK);
        return evRespuesta;
    }

    /**
     * Este mï¿½todo se encarga de invocar los servicios necesarios para consultar
     * un turno especï¿½fico dado su ID de Workflow
     *
     * @return <code>EvnRespTrasladoTurno</code> con la listas actualizadas.
     * @throws <code>EventoException</code>
     */
    /**
     * @author : HGOMEZ.
     * @casoMantis : 13898.
     * @actaRequerimiento : No 075_453_Reanotaciï¿½n_Turnos_Registro
     * @change : Se hicieron varios cambios al mï¿½todo para darle soluciï¿½n al
     * requerimiento.
     */
    private EvnRespTrasladoTurno reanotarTurnoInicio(EvnTrasladoTurno evento)
            throws EventoException {

        Turno turno = null;
        Circulo circulo = evento.getCirculo();
        List turnosAnteriores = null;
        List turnosSiguientes = null;
        List tiposNota = null;
        List calificadores = null;

        try {

            turno = hermod.getTurnobyWF(evento.getTurno().getIdWorkflow());

            if (turno.getAnulado() != null) {
                if (turno.getAnulado().equals(CTurno.TURNO_ANULADO)) {
                    throw new HermodException("El turno se encuentra anulado. ");
                }
            }
            String idProceso = Long.toString(turno.getIdProceso());

            if (!turno.getSolicitud().getCirculo().getIdCirculo().equals(circulo.getIdCirculo())
                    && turno.getSolicitud().getCirculo().getIdCirculo().equals(turno.getIdCirculo())) {
                throw new HermodException("No puede reanotar turnos de otros circulos. ");
            }

            Iterator iter = turno.getHistorials().iterator();
            boolean valido = false;
            boolean tipoCalificacion = false;
            /**
             * Autor: Carlos Torres Mantis: 13898
             */
            boolean inscritoparsial = false;
            boolean esDesanotado = false;

            boolean esProcesoRegistro = false;
            boolean esActoEmbargo = false;
            boolean esDevueltoAlPublico = false;
            boolean esCertificadoAsociadoExpedido = false;
            boolean esTurnoFinalizado = false;
            boolean tieneCertificadoPosterior = false;
            boolean pasoPorFirmaRegistro = false;
            boolean isNotNota = false;

            if (idProceso.equals(CProceso.PROCESO_REGISTRO)
                    && (turno.getSolicitud() != null)
                    && (turno.getSolicitud().getLiquidaciones() != null)) {
                esProcesoRegistro = true;
                esActoEmbargo = tieneActoEmbargo(turno.getSolicitud().getLiquidaciones());
                esCertificadoAsociadoExpedido = tieneCertificadoAsociadoExpedido(turno.getSolicitud().getSolicitudesHijas());
            }

           while (iter.hasNext()) {
                TurnoHistoria hist = (TurnoHistoria) iter.next();

                if (hist.getFase().equals(CFase.REANOTACION)) {
                    throw new HermodException("El turno ya fue reanotado");
                } else if (hist.getFase().equals(CFase.FINALIZADO) && idProceso.equals(CProceso.PROCESO_CERTIFICADOS)) {
                    valido = true;
                    break;
                } else if (idProceso.equals(CProceso.PROCESO_CERTIFICADOS)) {
                    if (hist.getFase().equals(CFase.CER_SOLICITUD)) {
                        valido = false;
                        break;
                    }
                } else if (idProceso.equals(CProceso.PROCESO_REGISTRO)) {
                    /**
                     * Autor: Edgar Lora Mantis: 13898
                     */
                    if (hist.getFase().equals(CFase.CAL_CALIFICACION)
                            && hist.getRespuesta().equals("DEVOLUCION")) {
                        esDevueltoAlPublico = true;
                    } /**
                     * Autor: Carlos Torres Mantis: 13898
                     */
                    else if (hist.getFase().equals(CFase.CAL_CALIFICACION) && hist.getRespuesta().equals("OK")) {
                        tipoCalificacion = true;
                    } else if (hist.getFase().equals(CFase.CAL_CALIFICACION) && hist.getRespuesta().equals("INSCRIPCION_PARCIAL")) {
                        inscritoparsial = true;
                    } else if (hist.getFase().equals(CFase.REG_CERTIFICADOS_ASOCIADOS)) {
                        valido = true;

                        
                        /**
                         * 
                         * Autor: Carlos Torres Mantis: 13898
                         */
                        if (hist.getRespuesta() != null && !hist.getRespuesta().equals("")) {
                            esDesanotado = true;
                        }
                    } else {
                        if (hist.getFase().equals(CFase.FINALIZADO)) {
                            esTurnoFinalizado = true;
                        }
                        valido = false;
                    }
                }
            }
           
            if(turno.getIdFase().equals(CFase.NOT_NOTA_DEVOLUTIVA)){
                valido = true;
                isNotNota = true;
            } else if(turno.getIdFase().equals(CFase.NOT_NOTA_NOTIFICADA) || turno.getIdFase().equals(CFase.NOT_RECURSOS_NOTA)
                    || turno.getIdFase().equals(CFase.REG_CERTIFICADOS_ASOCIADOS)){
                valido = false;
                isNotNota = false;
                throw new HermodException("No es posible reanotar este turno.");
            }
                    
           
             if(isNotNota){
               hermod.executeDMLFromSQL(CQueries.deleteEveryNotifications(turno.getIdWorkflow()));
             }

            List<SolicitudFolio> listSolitudFolios = turno.getSolicitud().getSolicitudFolios();
            int resultado = 0;
            ValidacionesSIR validacion = new ValidacionesSIR();

            for (SolicitudFolio solicitudFolio : listSolitudFolios) {
                resultado = validacion.tieneCertificadoPosterior(solicitudFolio.getIdMatricula(), turno.getAnio(), turno.getIdCirculo(), Long.toString(turno.getIdProceso()), turno.getIdTurno());
                if (resultado > 0) {
                    tieneCertificadoPosterior = true;
                    break;
                }

            }

            if (!valido) {
                if (idProceso.equals(CProceso.PROCESO_CERTIFICADOS)) {
                    throw new HermodException("El turno de certificados no ha sido entregado al pï¿½blico");
                }
                if (esTurnoFinalizado) {
                    throw new HermodException("Este turno no puede ser reanotado, ya ha sido finalizado.");
                }
            }

            /**
             * Autor: Edgar Lora Mantis: 13898
             */
            if (idProceso.equals(CProceso.PROCESO_REGISTRO) && !pasoPorFirmaRegistro && !isNotNota && !esTurnoFinalizado) {
                /**
                 * Autor: Carlos Torres Mantis: 13898
                 */
                throw new HermodException("No es posible reanotar este turno.");
            }
            /**
             * Autor: Carlos Torres Mantis: 13898
             */
            if (pasoPorFirmaRegistro && tipoCalificacion) {
                throw new HermodException("No es posible reanotar este turno. Tiene resoluciï¿½n Inscrito.");
            }

            if (pasoPorFirmaRegistro && inscritoparsial) {
                throw new HermodException("No es posible reanotar este turno. Tiene Resoluciï¿½n inscrito parcialmente.");
            }

            if (esProcesoRegistro && esActoEmbargo && esDevueltoAlPublico && esCertificadoAsociadoExpedido) {
                throw new HermodException("No es posible reanotar este turno. Tiene acto de embargo con resoluciï¿½n devuelto al pï¿½blico y se expidiï¿½ certificado asociado.");
            }

            if (esProcesoRegistro && (pasoPorFirmaRegistro && !esTurnoFinalizado) && esActoEmbargo && inscritoparsial) {
                throw new HermodException("No es posible reanotar este turno. Tiene acto de embargo con resoluciï¿½n inscrito parcialmente.");
            }

            if (tieneCertificadoPosterior) {
                throw new HermodException("No es posible reanotar este turno. tiene una matrï¿½cula asociada a un turno de certificado posterior expedido.");
            }

            if (turno.getSolicitud() instanceof SolicitudCertificado) {
                List solicitudesPadre = turno.getSolicitud().getSolicitudesPadres();
                for (int i = 0; i < solicitudesPadre.size(); i++) {
                    SolicitudAsociada solAsociada = (SolicitudAsociada) solicitudesPadre.get(i);
                    if (!(solAsociada.getSolicitudPadre() instanceof SolicitudCertificadoMasivo)) {
                        throw new HermodException("El turno de certificado no esta asociado a uno de certificados masivos");
                    }
                }
            }

            if (turno.getSolicitud() instanceof SolicitudRegistro) {
                turnosSiguientes = new ArrayList();
                List solFolios = turno.getSolicitud().getSolicitudFolios();
                for (int i = 0; i < solFolios.size(); i++) {
                    String idMatricula = ((SolicitudFolio) solFolios.get(i)).getIdMatricula();
                    List listaTurnos = hermod.getTurnosCertificadoPosteriores(idMatricula, turno);
                    if (listaTurnos != null && listaTurnos.size() > 0) {
                        turnosSiguientes.add("El folio " + idMatricula + " asociado al turno tiene turnos de certificados posteriores");
                    }
                }
                Rol rol = new Rol();
                rol.setRolId(CRoles.SIR_ROL_CALIFICADOR);

                /**
                 * @author Daniel Forero
                 * @change REQ 1156 - Filtro de estaciones activas por rol y
                 * usuario.
                 */
                calificadores = fenrir.consultarUsuariosPorCirculoRolByEstado(circulo, rol, true);
            }

            ProcesoPk id = new ProcesoPk();
            id.idProceso = turno.getIdProceso();

            tiposNota = hermod.getTiposNotaProceso(id, CFase.FINALIZADO);

        } catch (HermodException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno .", e);
            throw new TurnoNoEncontradoException(e.getMessage());
        } catch (ConsultaTurnoExceptionException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno.", e);
            throw e;
        } catch (TurnoNoEncontradoException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno.", e);
            throw e;
        } catch (Throwable e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno .", e);
            throw new TurnoNoEncontradoException(e.getMessage());
        }

        EvnRespTrasladoTurno evRespuesta
                = new EvnRespTrasladoTurno(
                        turno,
                        turnosAnteriores,
                        turnosSiguientes,
                        EvnRespTrasladoTurno.REANOTAR_TURNO_ESPECIFICACION_OK);
        evRespuesta.setListaTipoNota(tiposNota);
        evRespuesta.setCalificadores(calificadores);
        return evRespuesta;
    }

    /**
     * @author : HGOMEZ.
     * @casoMantis : 13898.
     * @actaRequerimiento : No 075_453_Reanotaciï¿½n_Turnos_Registro
     * @change : Verifica si el turno tiene un acto de Embargo.
     */
    private boolean tieneActoEmbargo(List<Liquidacion> listLiquidacion) {
        for (Liquidacion liquidacion : listLiquidacion) {
            LiquidacionTurnoRegistro liquidacionTurnoRegistro = (LiquidacionTurnoRegistro) liquidacion;
            List<Acto> listActos = liquidacionTurnoRegistro.getActos();
            for (Acto acto : listActos) {
                /**
                 * Autor: Edgar Lora Mantis: 13898
                 */
                if (acto.getTipoActo().getIdTipoActo().equals("10")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @author : HGOMEZ.
     * @casoMantis : 13898.
     * @actaRequerimiento : No 075_453_Reanotaciï¿½n_Turnos_Registro
     * @change : Verifica si el turno tiene un Certificado Asociado Expedido.
     */
    private boolean tieneCertificadoAsociadoExpedido(List<SolicitudAsociada> listSolicitudAsociada) {
        for (SolicitudAsociada solicitudAsociada : listSolicitudAsociada) {
            if (solicitudAsociada.getSolicitudHija() instanceof SolicitudCertificado) {
                /**
                 * Autor: Edgar Lora Mantis: 13898
                 */
                SolicitudCertificado solicitudHija = (SolicitudCertificado) solicitudAsociada.getSolicitudHija();
                if (solicitudHija.getNumImpresiones() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
        * @author : CTORRES
        * @change : se Agrega metodo cargarDetallesTurnoTestamento
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
     */
    private EvnRespTrasladoTurno cargarDetallesTurnoTestamento(EvnTrasladoTurno evento) throws EventoException {
        Solicitud solicitud = evento.getTurno().getSolicitud();
        Turno turnoAnterior = solicitud.getTurnoAnterior();
        if (turnoAnterior == null) {
            throw new EventoException("No se encontro el turno de testamento");
        }

        EvnRespTrasladoTurno evn = new EvnRespTrasladoTurno(evento.getTurno(), EvnRespCorreccion.CARGAR_TESTAMENTO_OK);
        try {

            co.com.iridium.generalSIR.negocio.testamento.TestamentoSIR testamentoSir = new co.com.iridium.generalSIR.negocio.testamento.TestamentoSIR();
            Map testamento = testamentoSir.getTestamento(turnoAnterior.getSolicitud().getIdSolicitud(), evento.getTurno().getIdWorkflow());
            Map documentoMap = testamentoSir.getEncabezado(turnoAnterior.getSolicitud().getIdSolicitud(), evento.getTurno().getIdWorkflow());
            if (testamento == null) {
                throw new EventoException("No se encontro tentamento en el turno asociado");
            }
            Testamento test = getTestamentoFromMap(testamento);
            Documento doc = getDocumentoFromMap(documentoMap);
            List<Map> testadores = testamentoSir.getTestadores(test.getIdTestamento(), evento.getTurno().getIdWorkflow());
            List<Ciudadano> ciudadanos = new ArrayList<Ciudadano>();
            for (Map testador : testadores) {
                Ciudadano ciudadano = getTestadorFromMap(testador);
                ciudadanos.add(ciudadano);
            }
            test.setTestadores(ciudadanos);

            Map salvedad = testamentoSir.getSalvedad(test.getIdTestamento(), evento.getTurno().getIdWorkflow());
            evn.setTestamento(test);
            evn.setSalvedasTest(salvedad);
            evn.setDocumento(doc);

        } catch (Throwable ex) {
            Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return evn;
    }

    /**
     * Este mï¿½todo se encarga de invocar los servicios necesarios para consultar
     * un turno especï¿½fico dado su ID de Workflow
     *
     * @return <code>EvnRespTrasladoTurno</code> con la listas actualizadas.
     * @throws <code>EventoException</code>
     */
    private EvnRespTrasladoTurno reanotarTurno(EvnTrasladoTurno evento)
            throws EventoException {

        Turno turno = null;
        Nota nota = evento.getNota();
        Usuario calificador = evento.getUsuarioCalificador();
        Usuario usuarioSIR = evento.getUsuarioNegocio();
        Estacion estacionCalificacion = null;

        try {
            Turno turnoAux = hermod.getTurnobyWF(evento.getTurno().getIdWorkflow());
            if (turnoAux.getAnulado() != null) {
                if (turnoAux.getAnulado().equals(CTurno.TURNO_ANULADO)) {
                    throw new HermodException("El turno se encuentra anulado. ");
                }
            }
            /*
                        * @author : CTORRES
                        * @change : se agrega condicion para validar turnos de testamento.
                        * Caso Mantis : 12291
                        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
             */

            ValidacionesSIR vsir = new ValidacionesSIR();
            /*
                            *  @fecha 28/08/2013
                            *  @author Carlos Torres
                            *  @chage  metodo para comprobar si el turno de testamento es valido para ser reanotado
                            *  @mantis 13898: Acta - Requerimiento No 024_589_Reanotaciï¿½n_Turnos_Registro
             */
            if (turnoAux.getIdProceso() == 6 && vsir.isTurnoTestamentoValido(turnoAux.getIdWorkflow(), true)) {
                if (!vsir.isTurnoTestamentoValidoReanotacion(turnoAux.getIdWorkflow())) {
                    throw new HermodException("El turno de testameno no es valido para ser reanotado");
                }
            }

            Iterator iter = turnoAux.getSolicitud().getSolicitudFolios().iterator();

            while (iter.hasNext()) {
                String idMatricula = ((SolicitudFolio) iter.next()).getIdMatricula();
                boolean folioValido = hermod.validarFolioTurnoReanotacion(idMatricula, turnoAux);
                if (!folioValido) {
                    throw new HermodException("La matrícula " + idMatricula + " asociada a este turno ya se encuentra asociada a turnos posteriores");
                }
            }

            UsuarioPk userid = new UsuarioPk();
            userid.idUsuario = usuarioSIR.getIdUsuario();
            usuarioSIR = fenrir.getUsuarioById(userid);

            if (calificador != null) {
                UsuarioPk idUsuario = new UsuarioPk();
                idUsuario.idUsuario = calificador.getIdUsuario();
                calificador = fenrir.getUsuarioById(idUsuario);
                Rol rol = fenrir.getRolByID(CRoles.SIR_ROL_CALIFICADOR);
                /**
                 * @author Daniel Forero
                 * @change REQ 1156 - Filtro de estaciones activas por rol y
                 * usuario.
                 */
                List estaciones = fenrir.getEstacionesUsuarioByEstadoRol(calificador, rol, true);
                for (int i = 0; i < estaciones.size(); i++) {
                    if (((Estacion) estaciones.get(i)).getEstacionId().substring(2).equals(calificador.getUsername())) {
                        estacionCalificacion = (Estacion) estaciones.get(i);
                    }
                }
                if (estacionCalificacion == null) {
                    estacionCalificacion = (Estacion) estaciones.get(0);
                }
            }

            hermod.reanotarTurno(turnoAux, nota, calificador, usuarioSIR, estacionCalificacion);

            turno = hermod.getTurnobyWF(evento.getTurno().getIdWorkflow());

        } catch (HermodException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno .", e);
            throw new TurnoNoEncontradoException(e.getMessage());
        } catch (ConsultaTurnoExceptionException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno.", e);
            throw e;
        } catch (TurnoNoEncontradoException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno.", e);
            throw e;
        } catch (Throwable e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al consultar el turno .", e);
            throw new TurnoNoEncontradoException(e.getMessage());
        }

        EvnRespTrasladoTurno evRespuesta = new EvnRespTrasladoTurno(turno, evento.getTipoEvento());

        return evRespuesta;
    }

    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este mï¿½todo se encarga de invocar los servicios necesarios para trasladar
     * un turno a un usuario especï¿½fico.
     *
     * @return <code>EvnRespTrasladoTurno</code>
     * @throws <code>EventoException</code>
     */
    private EvnRespTrasladoTurno reasignarTurno(EvnTrasladoTurno evento) throws EventoException {

        try {
            org.auriga.core.modelo.transferObjects.Usuario usuarioAurig
                    = new org.auriga.core.modelo.transferObjects.Usuario();
            usuarioAurig.setUsuarioId(evento.getUsuarioNegocio().getUsername());
            Usuario usuario = fenrir.getUsuario(usuarioAurig);
            Turno turno = hermod.getTurnobyWF(evento.getTurno().getIdWorkflow());
            hermod.setUsuarioToTurno(usuario, turno);

            long id = fenrir.darIdUsuario(usuario.getUsername());
            List roles = fenrir.darRolUsuario(id);

            Estacion estacion = null;
            if (!roles.isEmpty()) {
                Rol rol = (Rol) roles.get(0);
                List estaciones = fenrir.darEstacionUsuario(id, rol);
                if (!estaciones.isEmpty()) {
                    estacion = (Estacion) estaciones.get(0);
                }
            }

            hermod.trasladarTurnoSAS(turno, estacion);
        } catch (FenrirException e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al reasignar el turno .", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnTrasladoTurno.class, "Ocurriï¿½ un error al reasignar el turno .", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespTrasladoTurno evRespuesta
                = new EvnRespTrasladoTurno(null, EvnRespTrasladoTurno.REASIGNAR_TURNO_OK);
        return evRespuesta;
    }

    /*
        * @author : CTORRES
        * @change : se Agrega metodo getTestadorFromMap 
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
     */
    private Ciudadano getTestadorFromMap(Map testador) {
        if (testador != null) {
            Ciudadano ciudadano = new Ciudadano();
            ciudadano.setIdCiudadano((String) testador.get("ID_CIUDADANO"));
            ciudadano.setDocumento((String) testador.get("CDDN_DOCUMENTO"));
            ciudadano.setNombre((String) testador.get("CDDN_NOMBRE"));
            ciudadano.setTipoDoc((String) testador.get("CDDN_TIPO_DOCUMENTO"));
            ciudadano.setTelefono((String) testador.get("CDDN_TELEFONO"));
            ciudadano.setApellido1((String) testador.get("CDDN_APELLIDO1"));
            ciudadano.setApellido2((String) testador.get("CDDN_APELLIDO2"));
            ciudadano.setIdCirculo((String) testador.get("ID_CIRCULO"));
            return ciudadano;
        }
        return null;
    }

    /*
        * @author : CTORRES
        * @change : se Agrega metodo getTestamentoFromMap
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
     */

    private Testamento getTestamentoFromMap(Map testamento) {
        if (testamento != null) {
            Testamento test = new Testamento();
            test.setTomo((String) testamento.get("TSTM_TOMO"));
            test.setIdTestamento((String) testamento.get("ID_TESTAMENTO"));
            test.setNumeroAnotaciones((String) testamento.get("TSTM_NUMERO_ANOTACIONES"));
            test.setNumeroCopias((String) testamento.get("TSTM_NUMERO_COPIAS"));
            test.setNumeroRadicacion((String) testamento.get("TSTM_NUM_RADICACION"));
            test.setObservacion(testamento.get("TSTM_OBSERVACION").toString());
            test.setRevocaEscritura((String) testamento.get("TSTM_REVOCA_ESCRITURA"));
            test.setFechaCreacion((Date) testamento.get("TSTM_FECHA_CREACION"));
            return test;
        }
        return null;
    }

    /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed:Convierte Map a documento
     */
    private Documento getDocumentoFromMap(Map documento) {
        if (documento != null) {
            Documento doc = new Documento();
            doc.setIdDocumento((String) documento.get("ID_DOCUMENTO"));
            doc.setComentario((String) documento.get("DCMN_COMENTARIO"));
            doc.setFecha((Date) documento.get("DCMN_FECHA"));
            doc.setCirculo((String) documento.get("ID_CIRCULO"));
            doc.setNumero((String) documento.get("DCMN_NUMERO"));

            TipoDocumento tipoDoc = new TipoDocumento();
            tipoDoc.setIdTipoDocumento((String) documento.get("ID_TIPO_DOCUMENTO"));
            tipoDoc.setNombre((String) documento.get("tpdc_nombre".toUpperCase()));
            doc.setTipoDocumento(tipoDoc);

            doc.setOficinaInternacional((String) documento.get("DCMN_OFICINA_INTERNACIONAL"));
            OficinaOrigen oficeO = new OficinaOrigen();
            oficeO.setIdOficinaOrigen((String) documento.get("ID_OFICINA_ORIGEN"));
            /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Cï¿½digo_Notaria_NC
             */
            oficeO.setVersion((Long) documento.get("VERSION"));
            oficeO.setNombre((String) documento.get("FCRG_NOMBRE"));
            oficeO.setNumero((String) documento.get("FCRG_NUMERO"));

            TipoOficina tipo = new TipoOficina();
            tipo.setIdTipoOficina((String) documento.get("id_tipo_oficina".toUpperCase()));
            tipo.setNombre((String) documento.get("tpfc_nombre".toUpperCase()));
            oficeO.setTipoOficina(tipo);

            Vereda vereda = new Vereda();
            vereda.setIdVereda((String) documento.get("id_vereda".toUpperCase()));
            vereda.setIdMunicipio((String) documento.get("id_municipio".toUpperCase()));
            vereda.setIdDepartamento((String) documento.get("id_departamento".toUpperCase()));
            vereda.setNombre((String) documento.get("vrda_nombre".toUpperCase()));
            vereda.setMunicipio(new Municipio());
            vereda.getMunicipio().setIdMunicipio((String) documento.get("id_municipio".toUpperCase()));
            vereda.getMunicipio().setNombre((String) documento.get("mncp_nombre".toUpperCase()));
            vereda.getMunicipio().setDepartamento(new Departamento());
            vereda.getMunicipio().getDepartamento().setIdDepartamento((String) documento.get("id_departamento".toUpperCase()));
            vereda.getMunicipio().getDepartamento().setNombre((String) documento.get("dprt_nombre".toUpperCase()));
            oficeO.setVereda(vereda);
            doc.setOficinaOrigen(oficeO);

            return doc;
        }
        return null;
    }
}

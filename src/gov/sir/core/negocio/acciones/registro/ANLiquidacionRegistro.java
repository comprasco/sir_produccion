/*
 * Created on 29-sep-2004
 */
package gov.sir.core.negocio.acciones.registro;

import co.com.iridium.generalSIR.negocio.validaciones.CalificacionSIR;
import co.com.iridium.generalSIR.negocio.validaciones.TrasladoSIR;
import gov.sir.core.eventos.comun.EvnLiquidacion;
import gov.sir.core.eventos.comun.EvnRespLiquidacion;
import gov.sir.core.eventos.registro.EvnLiquidacionRegistro;
import gov.sir.core.eventos.registro.EvnRespLiquidacionRegistro;
import gov.sir.core.negocio.acciones.administracion.ANTurnoManualRegistro;
import gov.sir.core.negocio.acciones.excepciones.CrearTurnoException;
import gov.sir.core.negocio.acciones.excepciones.LiquidacionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.MatriculaAsociadaInvalidaRegistroException;
import gov.sir.core.negocio.acciones.excepciones.MatriculaInvalidaRegistroException;
import gov.sir.core.negocio.acciones.excepciones.RegistroLiquidacionSolicitudVinculadaInvalidParameterException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.SolicitudInvalidaRegistroException;
import gov.sir.core.negocio.acciones.excepciones.TurnoAnteriorInvalidoException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosHTException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionRegistroNoProcesadoException;
import gov.sir.core.negocio.modelo.Acto;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.CheckItem;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.CirculoTipoPago;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.DocPagoGeneral;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.EstacionReciboPk;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.InicioProcesos;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.PagoPk;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudDevolucion;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudFolioMig;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SubtipoSolicitud;
import gov.sir.core.negocio.modelo.TipoActo;
import gov.sir.core.negocio.modelo.TipoActoPk;
import gov.sir.core.negocio.modelo.TipoImpuesto;
import gov.sir.core.negocio.modelo.TipoPago;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoFolioMig;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CTipoImpuesto;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleConstanciaLiquidacion;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.helpers.correccion.diff.utils.comparators.BasicDateFormatComparator;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodService;
import gov.sir.hermod.gdocumental.integracion.SGD;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.OrdenImpresion;
import gov.sir.print.server.PrintJobsException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

/**
 * @author jfrias
 */
public class ANLiquidacionRegistro extends SoporteAccionNegocio {

    /**
     * Instancia de Forseti
     */
    private ForsetiServiceInterface forseti;

    /**
     * Instancia del service locator
     */
    private ServiceLocator service = null;

    /**
     * Instancia de Hermod
     */
    private HermodServiceInterface hermod;

    /**
     * Instancia de Fenrir
     */
    private FenrirServiceInterface fenrir;

    /**
     * Instancia de la intefaz de PrntJobs
     */
    private PrintJobsInterface printJobs;

    /**
     * @throws EventoException
     *
     */
    public ANLiquidacionRegistro() throws EventoException {
        super();
        service = ServiceLocator.getInstancia();

        try {
            hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");

            forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

            printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");

            fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");

        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio", e);
        }

    }

    /* (non-Javadoc)
	 * @see org.auriga.smart.negocio.acciones.AccionNegocio#perform(org.auriga.smart.eventos.Evento)
     */
    public EventoRespuesta perform(Evento e) throws EventoException {
        EvnLiquidacionRegistro evento = (EvnLiquidacionRegistro) e;

        if ((evento == null) || (evento.getTipoEvento() == null)) {
            return null;
        }

        if (evento.getTipoEvento().equals(EvnLiquidacionRegistro.VALIDAR)) {
            return validar(evento);
        } else if (EvnLiquidacionRegistro.REGISTRO_VINCULARTURNO_ADDITEM_EVENT.equals(evento.getTipoEvento())) {
            return validarSolicitudVinculada(evento);
        } else if (evento.getTipoEvento().equals(EvnLiquidacionRegistro.VALIDAR_TURNO)) {
            return validarTurno(evento);
        } else if (evento.getTipoEvento().equals(EvnLiquidacionRegistro.VALIDAR_MATRICULA)) {
            return validarMatricula(evento);
        } else if (evento.getTipoEvento().equals(EvnLiquidacionRegistro.AGREGAR_ACTO)) {
            return agregarActo(evento);
        } else if (evento.getTipoEvento().equals(EvnLiquidacionRegistro.VALIDAR_MATRICULA_ASOCIADA)) {
            return validarMatriculaAsociada(evento);
        } else if (evento.getTipoEvento().equals(EvnLiquidacion.LIQUIDAR)) {
            return liquidar(evento);
        } else if (evento.getTipoEvento().equals(EvnLiquidacionRegistro.PRELIQUIDAR)) {
            return preliquidar(evento);
        } else if (evento.getTipoEvento().equals(EvnLiquidacionRegistro.EDITAR_LIQUIDACION)) {
            return editarLiquidacion(evento);
        } else if (evento.getTipoEvento().equals(EvnLiquidacionRegistro.BUSCAR_SOLICITUD)) {
            return buscarSolicitud(evento);
        } else if (evento.getTipoEvento().equals(EvnLiquidacionRegistro.BUSCAR_SOLICITUD_EDICION)) {
            return buscarSolicitudEdicion(evento);
        } else if (evento.getTipoEvento().equals(EvnLiquidacionRegistro.VALIDAR_SOLICITUD)) {
            return validarSolicitud(evento);
        } else if (evento.getTipoEvento().equals(EvnLiquidacionRegistro.INCREMENTAR_SECUENCIAL_RECIBO)) {
            return incrementarSecuencial(evento);
        } else if (evento.getTipoEvento().equals(EvnLiquidacionRegistro.CREAR_TURNO)) {
            return crearTurnoRegistro(evento);
        } else if (evento.getTipoEvento().equals(EvnLiquidacionRegistro.CARGAR_TIPO_ACTO)) {
            return cargarTipoActo(evento);
        } else if (evento.getTipoEvento().equals(EvnLiquidacionRegistro.CARGAR_VALOR_DERECHOS)) {
            return cargarValorDerechosActo(evento);
        } else if (evento.getTipoEvento().equals(EvnLiquidacionRegistro.LISTAR_TURNOS_REGISTRO_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS)) {
            return listarTurnosRegistroParaAgregarCertificadosAsociados(evento);
        } else if (evento.getTipoEvento().equals(EvnLiquidacionRegistro.SOLICITAR_AGREGAR_CERTIFICADOS_ASOCIADOS)) {
            return solicitarAgregarCertificadosAsociadosTurno(evento);
        } else if (evento.getTipoEvento().equals(EvnLiquidacionRegistro.AGREGAR_CERTIFICADOS_ASOCIADOS)) {
            return agregarCertificadosAsociadosTurno(evento);
        } else if (evento.getTipoEvento().equals(EvnLiquidacionRegistro.VALIDAR_MATRICULA_MIG)) {
            return validarMatriculaMig(evento);
        }

        return null;
    }

    /**
     * Este metodo hace el llamado al negocio para que se haga la liquidacion de
     * la solicitud
     *
     * @param evento el evento recibido. Este evento debe ser del tipo
     * <CODE>EvnLiquidacion</CODE>
     * @return el evento de respuesta. Este evento debe ser del tipo
     * <CODE>EvnRespLiquidacion</CODE>
     * @throws EventoException
     */
    private EventoRespuesta liquidar(EvnLiquidacionRegistro evento) throws EventoException {
        EvnRespLiquidacion evRespuesta = null;

        Liquidacion liquidacion = evento.getLiquidacion();
        ImprimibleBase imprimible = null;
        String UID = "";
        int idImprimible = 0;

        if (liquidacion == null) {
            throw new LiquidacionNoEfectuadaException("No existe liquidaci�n asociada");
        }

        Solicitud solicitud = liquidacion.getSolicitud();

        if (solicitud == null) {
            throw new LiquidacionNoEfectuadaException("No existe solicitud asociada");
        }

        
        try {
            /**
             * Para turnos de registro se valida la cuantia de cada acto
             * existente. No debe ser menor al anterior
             */
            //Se modifica si es menor se debe colocar 0 en la liquidacion.
            if (liquidacion instanceof LiquidacionTurnoRegistro) {
                LiquidacionTurnoRegistro liqReg = (LiquidacionTurnoRegistro) liquidacion;
                if (solicitud.getTurnoAnterior() != null) {
                    int i = 1;
                    Turno turnoAnterior = solicitud.getTurnoAnterior();
                    for (Iterator iterActos = liqReg.getActos().iterator(); iterActos.hasNext();) {
                        Acto acto = (Acto) iterActos.next();
                        acto.setIdSolicitud(turnoAnterior.getSolicitud().getIdSolicitud());
                        //hermod.validacionActo(acto, i);
                        i++;
                    }
                }
            }

            /**
             * @Autor: Santiago V�squez
             * @Change: 2062.TARIFAS.REGISTRALES.2017
             */
            if (!liquidacion.getSolicitud().getSolicitudesHijas().isEmpty()) {
                List solicitudesHijas = liquidacion.getSolicitud().getSolicitudesHijas();
                for (int i = 0; i < solicitudesHijas.size(); i++) {
                    if (solicitudesHijas.get(i) instanceof SolicitudAsociada) {
                        SolicitudAsociada sa = (SolicitudAsociada) solicitudesHijas.get(i);
                        List liquidaciones = sa.getSolicitudHija().getLiquidaciones();
                        if (!liquidaciones.isEmpty()) {
                            for (int j = 0; j < liquidaciones.size(); j++) {
                                Liquidacion liq = (Liquidacion) liquidaciones.get(j);
                                if (liq instanceof LiquidacionTurnoCertificado) {
                                    LiquidacionTurnoCertificado liqCer = (LiquidacionTurnoCertificado) liq;

                                    if (sa.getSolicitudHija().getSolicitudFolios() != null
                                            && sa.getSolicitudHija().getSolicitudFolios() != null
                                            && sa.getSolicitudHija().getSolicitudFolios().size() > 0
                                            && sa.getSolicitudHija().getSolicitudFolios().get(0) instanceof SolicitudFolio) {
                                        SolicitudFolio solFolio = (SolicitudFolio) sa.getSolicitudHija().getSolicitudFolios().get(0);
                                        liqCer.setEsFolioMayorExtension(forseti.mayorExtensionFolio(solFolio.getIdMatricula()));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            List solFolio = solicitud.getSolicitudFolios();
            List matriculas = new Vector();
            Iterator itSolFolio = solFolio.iterator();

            while (itSolFolio.hasNext()) {
                SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
                matriculas.add(sol.getFolio().getIdMatricula());
            }

            forseti.validarMatriculas(hermod.getValidacionesSolicitud(solicitud), matriculas);
            solicitud.addLiquidacion(liquidacion);
            liquidacion.setUsuario(evento.getUsuarioSIR());

            liquidacion = hermod.liquidar(liquidacion);

            if (evento.getTurnosFolioMig() != null && evento.getTurnosFolioMig().size() > 0) {
                List turnoFolioMig = evento.getTurnosFolioMig();
                List solFoliosMig = new ArrayList();
                Iterator it = turnoFolioMig.iterator();
                while (it.hasNext()) {
                    String fol = (String) it.next();
                    SolicitudFolioMig solFolMig = new SolicitudFolioMig();
                    solFolMig.setIdFolio(fol);
                    solFolMig.setIdSolicitud(liquidacion.getIdSolicitud());
                    solFolMig.setIdProceso(liquidacion.getSolicitud().getProceso().getIdProceso());
                    solFolMig.setIdCirculo(liquidacion.getCirculo());
                    solFolMig.setAnulado(false);
                    solFolMig.setCreadoSir(true);
                    solFoliosMig.add(solFolMig);
                }

                hermod.addSolicitudFolioMigToTurno(liquidacion.getSolicitud(), solFoliosMig);
            }

            //Se debe generar un pago con valor 0, para indicar que la solicitud ya fue paga
            if ((liquidacion.getValor() == 0) && !(solicitud instanceof SolicitudRegistro)) {
                Estacion estacion = evento.getEstacion();
                Pago pago = new Pago(liquidacion, null);
                DocumentoPago documentoEfectivo = new DocPagoEfectivo(0);
                AplicacionPago appEfectivo = new AplicacionPago(documentoEfectivo, 0);

                pago.addAplicacionPago(appEfectivo);
                pago.setUsuario(evento.getUsuarioSIR());

                pago = hermod.registrarPago(pago, estacion.getEstacionId());
            }

            //SE VERIFICA SI EL USUARIO ES CAJERO PARA MOSTRAR O NO LA PANTALLA DE REGISTRO DE PAGO.
            Boolean esCajero = new Boolean(false);
            try {
                List roles = fenrir.darRolUsuario(evento.getUsuarioSIR().getIdUsuario());

                Iterator it = roles.iterator();
                while (it.hasNext()) {
                    Rol rol = (Rol) it.next();
                    if (rol.getRolId().equals(CRoles.CAJERO)) {
                        esCajero = new Boolean(true);
                    }
                }

            } catch (Exception fe) {
                ExceptionPrinter printer = new ExceptionPrinter(fe);
                Log.getInstance().error(ANLiquidacionRegistro.class, "Ha ocurrido una excepcion al consultar los reles del usuario " + printer.toString());
            }

            //SE VERIFICA SI EL USUARIO ES CAJERO DE REGISTRO PARA MOSTRAR O NO EL BOTON DE SIGUIENTE
            //QUE CONDUCE A LA RADICACI�N DEL TURNO.
            Boolean esCajeroRegistro = new Boolean(false);
            try {
                List roles = fenrir.darRolUsuario(evento.getUsuarioSIR().getIdUsuario());

                Iterator it = roles.iterator();
                while (it.hasNext()) {
                    Rol rol = (Rol) it.next();
                    if (rol.getRolId().equals(CRoles.CAJERO_REGISTRO)) {
                        esCajeroRegistro = new Boolean(true);
                    }
                }

            } catch (Exception fe) {
                ExceptionPrinter printer = new ExceptionPrinter(fe);
                Log.getInstance().error(ANLiquidacionRegistro.class, "Ha ocurrido una excepcion al consultar los reles del usuario " + printer.toString());
            }

            //Imprime la liquidaci�n, s�lo si se se desea imprimir
            if (evento.isImprimirConstancia()) {
                UID = evento.getUID();
                String fechaImpresion = this.getFechaImpresion();
                imprimible = new ImprimibleConstanciaLiquidacion(liquidacion, fechaImpresion, CTipoImprimible.RECIBO);
                idImprimible = this.imprimir(imprimible, UID, null, 0);
            }

            //System.out.println("vamos a buscar con este circulo 22 enero " + evento.getCirculo().getIdCirculo());
            List listaCanalesXCirculo = hermod.getCanalesRecaudoXCirculo(evento.getCirculo());
            /*for (Iterator iter = listaCanalesXCirculo.iterator(); iter.hasNext();) {
                CirculoTipoPago circuloTipoPago = (CirculoTipoPago) iter.next();
                System.out.println("INFOOOOOOOOO 22 ENERO ");
                System.out.println("FORMA PAGO ID " + circuloTipoPago.getIdCirculoTipoPago());
                System.out.println("NOMBRE FORMA " + circuloTipoPago.getTipoPago().getNombre());
            }*/

            //Genera la respuesta
            evRespuesta = new EvnRespLiquidacion(liquidacion, EvnRespLiquidacion.LIQUIDACION);
            evRespuesta.setEsCajero(esCajero);
            evRespuesta.setIdImprimible(idImprimible);
            evRespuesta.setEsCajeroRegistro(esCajeroRegistro);
            evRespuesta.setCanalesXCirculo(listaCanalesXCirculo);

        } catch (HermodException e) {
            throw new LiquidacionNoEfectuadaException(e.getMessage(), e);
        } catch (ForsetiException e) {
            throw new ValidacionParametrosHTException(e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        return evRespuesta;
    }

    /**
     * Este metodo hace el llamado al negocio para que se haga la liquidacion de
     * la solicitud
     *
     * @param evento el evento recibido. Este evento debe ser del tipo
     * <CODE>EvnLiquidacion</CODE>
     * @return el evento de respuesta. Este evento debe ser del tipo
     * <CODE>EvnRespLiquidacion</CODE>
     * @throws EventoException
     */
    private EventoRespuesta preliquidar(EvnLiquidacionRegistro evento) throws EventoException {
        EvnRespLiquidacion evRespuesta = null;

        Liquidacion liquidacion = evento.getLiquidacion();
        ImprimibleBase imprimible = null;
        String UID = "";
        int idImprimible = 0;

        if (liquidacion == null) {
            throw new LiquidacionNoEfectuadaException("No existe liquidaci�n asociada");
        }

        Solicitud solicitud = liquidacion.getSolicitud();

        if (solicitud == null) {
            throw new LiquidacionNoEfectuadaException("No existe solicitud asociada");
        }

        try {
            List solFolio = solicitud.getSolicitudFolios();
            List matriculas = new Vector();
            Iterator itSolFolio = solFolio.iterator();

            while (itSolFolio.hasNext()) {
                SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
                matriculas.add(sol.getFolio().getIdMatricula());
            }

            forseti.validarMatriculas(hermod.getValidacionesSolicitud(solicitud), matriculas);
            liquidacion.setUsuario(evento.getUsuarioSIR());
            LiquidacionTurnoRegistro liqReg = (LiquidacionTurnoRegistro) liquidacion;
            liqReg.setPreliquidacion(true);
            liquidacion = hermod.liquidar(liqReg);

            //Genera la respuesta
            evRespuesta = new EvnRespLiquidacion(liquidacion, EvnRespLiquidacion.PRELIQUIDACION);

        } catch (HermodException e) {
            throw new LiquidacionNoEfectuadaException(e.getMessage(), e);
        } catch (ForsetiException e) {
            throw new ValidacionParametrosHTException(e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        return evRespuesta;
    }

    /**
     * Este metodo hace el llamado al negocio para que modifique una liquidaci�n
     * existente.
     *
     * @param evento el evento recibido. Este evento debe ser del tipo
     * <CODE>EvnLiquidacion</CODE>
     * @return el evento de respuesta. Este evento debe ser del tipo
     * <CODE>EvnRespLiquidacion</CODE>
     * @throws EventoException
     */
    private EventoRespuesta editarLiquidacion(EvnLiquidacionRegistro evento) throws EventoException {
        EvnRespLiquidacion evRespuesta = null;

        Liquidacion liquidacion = evento.getLiquidacion();
        ImprimibleBase imprimible = null;
        String UID = "";
        int idImprimible = 0;

        if (liquidacion == null) {
            throw new LiquidacionNoEfectuadaException("No existe liquidaci�n asociada");
        }

        Solicitud solicitud = liquidacion.getSolicitud();

        if (solicitud == null) {
            throw new LiquidacionNoEfectuadaException("No existe solicitud asociada");
        }
        
        String idSol = liquidacion.getIdSolicitud();
        try{
            hermod.deleteActosBySolicitud(idSol);
        } catch(Throwable t){
            System.out.println("NO FUE POSIBLE ELIMINAR LOS ACTOS POR SOLICITUD");
        }

        try {

            /**
             * Para turnos de registro se valida la cuantia de cada acto
             * existente. No debe ser menor al anterior
             */
            if (liquidacion instanceof LiquidacionTurnoRegistro) {
                LiquidacionTurnoRegistro liqReg = (LiquidacionTurnoRegistro) liquidacion;
                if (solicitud.getTurnoAnterior() != null) {
                    int i = 1;
                    Turno turnoAnterior = solicitud.getTurnoAnterior();
                    if (turnoAnterior.getSolicitud() == null) {
                        turnoAnterior = hermod.getTurnobyWF(turnoAnterior.getIdWorkflow());
                    }
                    for (Iterator iterActos = liqReg.getActos().iterator(); iterActos.hasNext();) {
                        Acto acto = (Acto) iterActos.next();
                        acto.setIdSolicitud(turnoAnterior.getSolicitud().getIdSolicitud());
                        //hermod.validacionActo(acto, i);
                        i++;
                    }
                }
            }
            List solFolio = solicitud.getSolicitudFolios();
            List matriculas = new Vector();
            Iterator itSolFolio = solFolio.iterator();

            while (itSolFolio.hasNext()) {
                SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
                matriculas.add(sol.getFolio().getIdMatricula());
            }

            forseti.validarMatriculas(hermod.getValidacionesSolicitud(solicitud), matriculas);
            liquidacion.setUsuario(evento.getUsuarioSIR());
            liquidacion = hermod.modificarLiquidacionRegistro(liquidacion);

            //obtener la solicitud y a�aderla a la liquidacion.
            String idSolicitud = liquidacion.getIdSolicitud();
            Solicitud sol = hermod.getSolicitudById(idSolicitud);
            liquidacion.setSolicitud(sol);

            //SE VERIFICA SI EL USUARIO ES CAJERO PARA MOSTRAR O NO LA PANTALLA DE REGISTRO DE PAGO.
            Boolean esCajero = new Boolean(false);
            try {
                List roles = fenrir.darRolUsuario(evento.getUsuarioSIR().getIdUsuario());

                Iterator it = roles.iterator();
                while (it.hasNext()) {
                    Rol rol = (Rol) it.next();
                    if (rol.getRolId().equals(CRoles.CAJERO)) {
                        esCajero = new Boolean(true);
                    }
                }

            } catch (Exception fe) {
                ExceptionPrinter printer = new ExceptionPrinter(fe);
                Log.getInstance().error(ANLiquidacionRegistro.class, "Ha ocurrido una excepcion al consultar los reles del usuario " + printer.toString());
            }

            if (!evento.isVieneDeCajero()) {
                //Imprime la liquidaci�n
                UID = evento.getUID();
                String fechaImpresion = this.getFechaImpresion();
                imprimible = new ImprimibleConstanciaLiquidacion(liquidacion, fechaImpresion, CTipoImprimible.RECIBO);
                idImprimible = this.imprimir(imprimible, UID, null, 0);
            }

            //Genera la respuesta
            evRespuesta = new EvnRespLiquidacion(liquidacion, EvnRespLiquidacion.LIQUIDACION);
            evRespuesta.setEsCajero(esCajero);
            evRespuesta.setIdImprimible(idImprimible);

        } catch (HermodException e) {
            throw new LiquidacionNoEfectuadaException(e.getMessage(), e);
        } catch (ForsetiException e) {
            throw new ValidacionParametrosHTException(e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        return evRespuesta;
    }

    /**
     * @param evento
     * @return evento respuesta
     */
    private EventoRespuesta cargarTipoActo(EvnLiquidacionRegistro evento) throws EventoException {

        TipoActo tipoActo = evento.getTipoActo();
        TipoActo rta = null;

        try {
            if (tipoActo != null) {
                TipoActoPk tid = new TipoActoPk();
                tid.idTipoActo = tipoActo.getIdTipoActo();
                rta = hermod.getTipoActo(tid);
            }
        } catch (HermodException e) {
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "Ha ocurrido una excepcion al consultar los tipos de acto "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        return new EvnRespLiquidacionRegistro(rta, EvnRespLiquidacionRegistro.CARGAR_TIPO_ACTO);
    }

    /**
     * Actualizar el valor de los derechos de un acto deg�n su cantidad
     * ingresada
     *
     * @param evento
     * @return evento respuesta
     */
    private EventoRespuesta cargarValorDerechosActo(EvnLiquidacionRegistro evento) throws EventoException {

        Acto acto = evento.getActo();
        Acto rta = null;

        try {
            if (evento.getTurnoAnterior() != null) {
                acto.setIdSolicitud(evento.getTurnoAnterior().getSolicitud().getIdSolicitud());
                if (acto != null) {
                    if (hermod.validacionActo(acto, evento.getPosicion())) {
                        rta = hermod.getLiquidacionActo(acto);
                    }
                }
            }
        } catch (HermodException e) {
            throw new LiquidacionNoEfectuadaException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "Ha ocurrido una excepcion al consultar el valor de los derechos de un acto"
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        return new EvnRespLiquidacionRegistro(rta, EvnRespLiquidacionRegistro.CARGAR_VALOR_DERECHOS);
    }

    /**
     * @param evento
     * @return evento respuesta
     */
    private EventoRespuesta listarTurnosRegistroParaAgregarCertificadosAsociados(EvnLiquidacionRegistro evento) throws EventoException {

        //InicializarDatos
        List turnos = null;

        Circulo cir = evento.getCirculo();

        try {
            //Llamar servicio de listado de turnos
            turnos = hermod.listarTurnosRegistroParaAgregarCertificadosAsociados(cir);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "Ha ocurrido una excepcion al consultar los tipos de acto "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        return new EvnRespLiquidacionRegistro(turnos, EvnRespLiquidacionRegistro.LISTAR_TURNOS_REGISTRO_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS);
    }

    /**
     * @param evento
     * @return evento respuesta
     */
    private EventoRespuesta solicitarAgregarCertificadosAsociadosTurno(EvnLiquidacionRegistro evento) throws EventoException {

        //InicializarDatos
        Turno turno = evento.getTurno();
        TurnoPk tid = new TurnoPk();
        tid.anio = turno.getAnio();
        tid.idCirculo = turno.getIdCirculo();
        tid.idProceso = turno.getIdProceso();
        tid.idTurno = turno.getIdTurno();

        try {
            //Llamar servicio de listado de turnos
            turno = hermod.getTurno(tid);

            //Obtener completas los certificados asociados ya ingresadoas
            Solicitud sol = turno.getSolicitud();
            List solicitudesAsociadas = sol.getSolicitudesHijas();
            Iterator it = solicitudesAsociadas.iterator();
            while (it.hasNext()) {
                SolicitudAsociada solasoc = (SolicitudAsociada) it.next();
                Solicitud solh = solasoc.getSolicitudHija();
                solh = hermod.getSolicitudById(solh.getIdSolicitud());
                solasoc.setSolicitudHija(solh);
            }

        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "Ha ocurrido una excepcion al consultar los tipos de acto "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        return new EvnRespLiquidacionRegistro(turno, EvnRespLiquidacionRegistro.SOLICITAR_AGREGAR_CERTIFICADOS_ASOCIADOS);
    }

    /**
     * @param evento
     * @return evento respuesta
     */
    private EventoRespuesta agregarCertificadosAsociadosTurno(EvnLiquidacionRegistro evento) throws EventoException {

        //InicializarDatos
        Turno turno = evento.getTurno();
        TurnoPk tid = new TurnoPk();
        tid.anio = turno.getAnio();
        tid.idCirculo = turno.getIdCirculo();
        tid.idProceso = turno.getIdProceso();
        tid.idTurno = turno.getIdTurno();

        List liqudacionessolicitudesAAgregar = evento.getLiquidacionesSolicitudesCertificadosAAgregar();

        List liquidacionesYaProcesadas = new ArrayList();
        try {

            Iterator it = liqudacionessolicitudesAAgregar.iterator();
            while (it.hasNext()) {
                LiquidacionTurnoCertificado liqCert = (LiquidacionTurnoCertificado) it.next();
                Liquidacion liq = hermod.liquidar(liqCert);
                liquidacionesYaProcesadas.add(liq);
            }

        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "Ha ocurrido una excepcion al consultar los tipos de acto "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        return new EvnRespLiquidacionRegistro(liquidacionesYaProcesadas, EvnRespLiquidacionRegistro.AGREGAR_CERTIFICADOS_ASOCIADOS);
    }

    /**
     * @param evento
     * @return evento respuesta
     */
    private EventoRespuesta agregarActo(EvnLiquidacionRegistro evento) throws EventoException {
        Acto acto = null;
        LiquidacionTurnoRegistro liquidacion = (LiquidacionTurnoRegistro) evento.getActo().getLiquidacion();
        Solicitud solicitud = liquidacion.getSolicitud();

        try {
            /**
             * Para turnos de registro se valida la cuantia de cada acto
             * existente. No debe ser menor al anterior
             */
            if (liquidacion instanceof LiquidacionTurnoRegistro) {
                if (solicitud.getTurnoAnterior() != null) {
                    Turno turnoAnterior = solicitud.getTurnoAnterior();
                    Acto actoValidar = evento.getActo();
                    actoValidar.setIdSolicitud(turnoAnterior.getSolicitud().getIdSolicitud());
                    //hermod.validacionActo(actoValidar, 0);
                }
            }
            /**
             * @Autor: Santiago V�squez
             * @Change: 2062.TARIFAS.REGISTRALES.2017
             */
            if (evento.getSolicitud() != null) {
                evento.getActo().setIncentivoRegistral(evento.getSolicitud().isIncentivoRegistral());
            }

            acto = hermod.getLiquidacionActo(evento.getActo());

            if (acto == null) {
                throw new ValidacionParametrosException("El acto no pudo ser agregado.");
            }
//                        /*Conservaci�n documental*/
//                        double valorActoTest = acto.getValor();
//                        acto.setValor(valorActoTest);
//                        /**/

        } catch (ServiceLocatorException e) {
            Log.getInstance().error(ANLiquidacionRegistro.class, e);
            throw new ValidacionParametrosException("No se pudo encontrar el servicio de hermod.");
        } catch (HermodException e) {
            Log.getInstance().error(ANLiquidacionRegistro.class, e);
            throw new LiquidacionNoEfectuadaException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANLiquidacionRegistro.class, e);
        }

        CirculoPk cirID = new CirculoPk();
        if (evento.getSolicitudRegistro() != null && evento.getSolicitudRegistro().getCirculo() != null) {
            cirID.idCirculo = evento.getSolicitudRegistro().getCirculo().getIdCirculo();
        }
        EvnRespLiquidacionRegistro evnRes = new EvnRespLiquidacionRegistro(acto, EvnRespLiquidacionRegistro.AGREGAR_ACTO);
        evnRes.setAlertasDocumentos(verificarAlertaActos(cirID, evento.getSolicitudRegistro()));
        return evnRes;
    }

    private boolean verificarAlertaActos(CirculoPk id, SolicitudRegistro solicitud) throws EventoException {
        if (solicitud == null || solicitud.getDocumento() == null) {
            return false;
        }

        long miliFechaDoc = solicitud.getDocumento().getFecha().getTime();
        long miliFechaSol = solicitud.getFecha().getTime();
        String strPlazo = "";
        try {
            strPlazo = hermod.getPlazoVencimientoRegistroActos();
        } catch (HermodException e) {
            Log.getInstance().error(ANLiquidacionRegistro.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANLiquidacionRegistro.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }
        int numDiasHabiles = 0;
        while (miliFechaDoc <= miliFechaSol) {
            try {
                if (!forseti.isFestivo(new Date(miliFechaDoc), id)) {
                    numDiasHabiles++;
                }
                /**
                 * @Autor: Santiago V�squez
                 * @Change: 2062.TARIFAS.REGISTRALES.2017
                 */
                if (numDiasHabiles > Integer.parseInt(strPlazo)) {
                    return true;
                }
            } catch (ForsetiException e) {
                Log.getInstance().error(ANLiquidacionRegistro.class, e.getMessage(), e);
                throw new EventoException(e.getMessage(), e);
            } catch (Throwable e) {
                Log.getInstance().error(ANLiquidacionRegistro.class, e.getMessage(), e);
                throw new EventoException(e.getMessage(), e);
            }
            miliFechaDoc = miliFechaDoc + (24 * 60 * 60 * 1000);
        }
        return false;
    }

    /**
     * @param evento
     * @return evento respuesta
     * @throws EventoException
     */
    private EventoRespuesta validarMatricula(EvnLiquidacionRegistro evento) throws EventoException {
        String matricula = evento.getMatricula();

        try {
            List matriculas = new Vector();
            matriculas.add(matricula);

            List validaciones = hermod.getValidacionesRegistro();
            
            if (forseti.existeFolio(matricula)) {
                forseti.validarMatriculas(validaciones, matriculas);

                /**
                 * @author : Julio Alcazar
                 * @change : Revision: Validacion bloqueo por traslado. Caso
                 * Mantis : 0007676: Acta - Requerimiento No 247 - Traslado de
                 * Folios V2
                 */
                TrasladoSIR trasladoSIR = new TrasladoSIR();
                if (trasladoSIR.isTrasladoSinConf(matricula)) {
                    throw new MatriculaInvalidaRegistroException("El folio se encuentra bloqueado");
                }
                return new EvnRespLiquidacion(matricula, EvnRespLiquidacion.VALIDACION_MATRICULA);
            }

            throw new MatriculaInvalidaRegistroException("La matr�cula no existe");
        } catch (MatriculaInvalidaRegistroException e) {
            throw e;
        } catch (ForsetiException e) {
            ExceptionPrinter ep = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "Error en el servicio para validar la matr�cula:" + ep.toString());

            String errores = "";
            Iterator it = ((List) e.getHashErrores().get(matricula)).iterator();

            while (it.hasNext()) {
                errores = errores + "\n" + it.next();
            }

            throw new MatriculaInvalidaRegistroException("Resultado de la validaci�n de la matr�cula " + matricula + ": " + errores);
        } catch (Throwable e) {
            ExceptionPrinter ep = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "Se produjo un error validando la matr�cula:" + ep.toString());
            throw new MatriculaInvalidaRegistroException("Se produjo un error validando la matr�cula", e);
        }
    }

    /**
     * @param evento
     * @return evento respuesta
     * @throws EventoException
     */
    private EventoRespuesta validarMatriculaMig(EvnLiquidacionRegistro evento) throws EventoException {
        String matricula = evento.getMatricula();

        try {
            Folio folio = new Folio();
            folio.setIdMatricula(matricula);

            //List validaciones = hermod.getValidacionesRegistro();
            if (hermod.isFolioValidoSirMig(folio)) {
                return new EvnRespLiquidacion(matricula, EvnRespLiquidacion.VALIDACION_MATRICULA_MIG);
            }

            throw new MatriculaInvalidaRegistroException("La matr�cula no es valida para el proceso de Migraci�n");
        } catch (MatriculaInvalidaRegistroException e) {
            throw e;
        } catch (ForsetiException e) {
            ExceptionPrinter ep = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "Error en el servicio para validar la matr�cula:" + ep.toString());

            String errores = "";
            Iterator it = ((List) e.getHashErrores().get(matricula)).iterator();

            while (it.hasNext()) {
                errores = errores + "\n" + it.next();
            }

            throw new MatriculaInvalidaRegistroException("Resultado de la validaci�n de la matr�cula " + matricula + ": " + errores);
        } catch (Throwable e) {
            ExceptionPrinter ep = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "Se produjo un error validando la matr�cula:" + ep.toString());
            throw new MatriculaInvalidaRegistroException("Se produjo un error validando la matr�cula", e);
        }
    }

    /**
     * @param evento
     * @return envento respuesta
     * @throws EventoException
     */
    private EventoRespuesta validarMatriculaAsociada(EvnLiquidacionRegistro evento) throws EventoException {
        List matriculasAsociar = new ArrayList();

        String matricula = evento.getMatricula();
        if (matricula == null || matricula.equals("")) {
            String[] matriculasVarias = evento.getMatriculasVarias();
            if (matriculasVarias != null && matriculasVarias.length > 0) {
                for (int i = 0; i < matriculasVarias.length; i++) {
                    matriculasAsociar.add(matriculasVarias[i]);
                }
            }
        } else {
            matriculasAsociar.add(matricula);
        }
        try {

            for (int i = 0; i < matriculasAsociar.size(); i++) {
                if (!forseti.existeFolio((String) matriculasAsociar.get(i))) {
                    throw new MatriculaAsociadaInvalidaRegistroException("La matr�cula no existe");
                }
            }
            return new EvnRespLiquidacion(matriculasAsociar, EvnRespLiquidacion.VALIDACION_MATRICULA_ASOCIADA);
        } catch (MatriculaInvalidaRegistroException e) {
            throw e;
        } catch (ForsetiException e) {
            ExceptionPrinter ep = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "Error en el servicio para validar la matr�cula:" + ep.toString());
            throw new MatriculaAsociadaInvalidaRegistroException("Error en el servicio para validar la matr�cula", e);
        } catch (Throwable e) {
            ExceptionPrinter ep = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "Se produjo un error validando la matr�cula:" + ep.toString());
            throw new MatriculaAsociadaInvalidaRegistroException("Se produjo un error validando la matr�cula", e);
        }
    }

    protected EventoRespuesta
            validarSolicitudVinculada(EvnLiquidacionRegistro evento)
            throws EventoException {

        // pipe + "wrap" the event
        gov.sir.core.negocio.modelo.SolicitudVinculada param_t0_SolicitudVinculada = null;
        param_t0_SolicitudVinculada = evento.getSolicitudVinculada();

        gov.sir.core.negocio.modelo.SolicitudVinculada param_t1_SolicitudVinculada = null;

        try {

            gov.sir.core.negocio.modelo.SolicitudPk solicitudId
                    = new gov.sir.core.negocio.modelo.SolicitudPk();
            solicitudId.idSolicitud = param_t0_SolicitudVinculada.getIdSolicitud();

            boolean validationResult = hermod.validarSolicitudVinculada(solicitudId);

            gov.sir.core.negocio.modelo.Solicitud solicitudVinculada
                    = hermod.getSolicitudById(solicitudId.idSolicitud);

            // set solicitud1
            param_t1_SolicitudVinculada = new gov.sir.core.negocio.modelo.SolicitudVinculada();
            param_t1_SolicitudVinculada.setSolicitudPadre(solicitudVinculada);

//      Se valida que esa solicitud sea la del dia
            Date local_Date;
            local_Date = new Date(System.currentTimeMillis());

            Date fechaCreacionSolicitud = param_t1_SolicitudVinculada.getSolicitudPadre().getFecha();

            String fechaCreacionSolicitud_String = DateFormatUtil.format(fechaCreacionSolicitud);

            BasicDateFormatComparator comparator = new BasicDateFormatComparator(DateFormatUtil.DEFAULT_FORMAT);

            if (comparator.compare(local_Date, fechaCreacionSolicitud) > 0) {
                throw new RegistroLiquidacionSolicitudVinculadaInvalidParameterException("La Solicitud debe ser del dia de Hoy, Fecha de Solicitud: " + fechaCreacionSolicitud_String + ".");
            }

            // observar si el turno vinculado esta
            // vinculado a otro turno ?
            // List<Turno> ::
            // hermod.getTurnosVinculados( param_t1_TurnoVinculado.getIdWorkFlow() )
            // si tiene items,
            // throw new Exception("El turno: " + turno.getIdWorkflow() + " ya fue utilizado como turno vinculado ");
            // foreach, validate ?
        } catch (HermodException h) {
            throw new RegistroLiquidacionSolicitudVinculadaInvalidParameterException("solicitud-vinculada; error: ", h);
        } catch (Throwable e) {
            throw new RegistroLiquidacionSolicitudVinculadaInvalidParameterException("Imposible validar el turno", e);
        }

        EvnRespLiquidacionRegistro processedEvent = null;
        processedEvent = new EvnRespLiquidacionRegistro(null);

        processedEvent.setTipoEvento(EvnRespLiquidacionRegistro.REGISTRO_VINCULARTURNO_ADDITEM__EVENTRESP_OK);
        processedEvent.setSolicitudVinculada(param_t1_SolicitudVinculada);

        return processedEvent;
    }

    /**
     * @param evento
     * @return envento respuesta
     * @throws EventoException
     */
    private EventoRespuesta validarTurno(EvnLiquidacionRegistro evento) throws EventoException {
        Turno t = evento.getTurnoAnterior();
        Turno turno = null;
        List turnosValidacion = new ArrayList();
        List turnosDevoluciones = new ArrayList();
        List turnosFolioMig = null;
        double otroImp = 0;

        try {
            turno = hermod.getTurnobyWF(t.getIdWorkflow());

            /*
                        * @author      :   Carlos Mario Torres Urina
                        * @change      :   Se valida si tiene folios trasladados
                        * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
             */
            TrasladoSIR validacion = new TrasladoSIR();
            if (validacion.tieneFoliosTraslado(turno.getIdWorkflow())) {
                throw new TurnoAnteriorInvalidoException("El turno " + turno.getIdWorkflow() + " tiene folios asociados en estado trasladados");
            }
            //REALIZAR LAS VALIDACIONES PARA DETERMINAR SI EL TURNO ANTERIOR INGRESADO, YA ES TURNO ANTERIOR
            //DE OTRO TURNO.
            try {
                turnosValidacion = hermod.getTurnosSiguientes(turno.getIdWorkflow());
                turnosDevoluciones = hermod.getTurnosDevolucion(turno);
            } catch (Throwable e1) {
                throw new TurnoAnteriorInvalidoException("Error obteniendo turnos siguientes del turno: " + turno.getIdWorkflow(), e1);
            }

            Iterator itera = turnosDevoluciones.iterator();
            String respuesta = "";
            String resp = "";
            while (itera.hasNext()) {
                Turno turnoAnteriorTemp = (Turno) itera.next();
                if (CProceso.PROCESO_DEVOLUCIONES.equals(Long.toString(turnoAnteriorTemp.getIdProceso()))) {
                    TurnoPk idTurnoTemp = new TurnoPk();
                    idTurnoTemp.anio = turnoAnteriorTemp.getAnio();
                    idTurnoTemp.idCirculo = turnoAnteriorTemp.getIdCirculo();
                    idTurnoTemp.idProceso = turnoAnteriorTemp.getIdProceso();
                    idTurnoTemp.idTurno = turnoAnteriorTemp.getIdTurno();
                    turnoAnteriorTemp = hermod.getTurno(idTurnoTemp);
                    respuesta = "";
                    resp = "";
                    List historial = turnoAnteriorTemp.getHistorials();
                    if (historial != null) {
                        Iterator iterator = historial.iterator();
                        while (iterator.hasNext()) {
                            TurnoHistoria turnoHistoria = (TurnoHistoria) iterator.next();
                            if (turnoHistoria.getFase() != null && ((turnoHistoria.getFase()).equals(CFase.DEV_ANALISIS))) {
                                respuesta = turnoHistoria.getRespuesta();
                            }
                            if (turnoHistoria.getFase() != null && ((turnoHistoria.getFase()).equals(CFase.DEV_RECURSOS))) {
                                resp = turnoHistoria.getRespuesta();
                            }
                        }
                        if ((CRespuesta.NEGAR).equals(respuesta) && ("*").equals(resp) && (CFase.FINALIZADO).equals(turnoAnteriorTemp.getIdFase())) {
                            itera.remove();
                        }
                    }
                }
            }
            if (turnosDevoluciones != null && !turnosDevoluciones.isEmpty()) {
                Turno turnoAnteriorDevolucion = (Turno) turnosDevoluciones.get(0);
                if (turnoAnteriorDevolucion != null) {
                    TurnoPk idTurno = new TurnoPk();
                    idTurno.anio = turnoAnteriorDevolucion.getAnio();
                    idTurno.idCirculo = turnoAnteriorDevolucion.getIdCirculo();
                    idTurno.idProceso = turnoAnteriorDevolucion.getIdProceso();
                    idTurno.idTurno = turnoAnteriorDevolucion.getIdTurno();
                    turnoAnteriorDevolucion = hermod.getTurno(idTurno);
                    if (turnoAnteriorDevolucion.getSolicitud() != null) {
                        Solicitud solicitudDevolucion = turnoAnteriorDevolucion.getSolicitud();
                        if (solicitudDevolucion instanceof SolicitudDevolucion) {
                            if (turnoAnteriorDevolucion.getAnulado().equals("N")) {
                                throw new TurnoAnteriorInvalidoException("El turno " + turno.getIdWorkflow() + " tiene devoluci�n de dinero registrado en el turno " + turnoAnteriorDevolucion.getIdWorkflow() + ".");
                            }
                        }
                    }
                }

            }

            if ((turnosValidacion != null) && !turnosValidacion.isEmpty()) {

                Turno turnoAnterior = (Turno) turnosValidacion.get(0);

                if (turnoAnterior != null) {
                    TurnoPk idTurno = new TurnoPk();
                    idTurno.anio = turnoAnterior.getAnio();
                    idTurno.idCirculo = turnoAnterior.getIdCirculo();
                    idTurno.idProceso = turnoAnterior.getIdProceso();
                    idTurno.idTurno = turnoAnterior.getIdTurno();
                    turnoAnterior = hermod.getTurno(idTurno);
                    if (turnoAnterior.getSolicitud() != null) {
                        Solicitud solicitud = turnoAnterior.getSolicitud();
                        if (solicitud instanceof SolicitudRegistro) {
                            if (turnoAnterior.getAnulado().equals("N")) {
                                throw new TurnoAnteriorInvalidoException("El turno " + turno.getIdWorkflow() + " ya tiene nuevas entradas en el turno " + turnoAnterior.getIdWorkflow() + ".");
                            }
                        }
                    }
                }
            }

            //BUGID: 0002018
            boolean turnoAnteriorManual = false;
            boolean devueltoManual = false;

            //Se valida si por algun lado tiene la fase de identifica si es un turno manual
            if (turno.getHistorials() != null) {
                for (int i = 0; i < turno.getHistorials().size(); i++) {
                    TurnoHistoria ultimoHistorial = null;
                    ultimoHistorial = (TurnoHistoria) turno.getHistorials().get(i);
                    if (ultimoHistorial != null && ultimoHistorial.getFase().equals(CFase.REG_TMAN_CIERRE)) {
                        turnoAnteriorManual = true;
                    }
                    if (ultimoHistorial != null && ultimoHistorial.getFase().equals(CFase.CAL_CALIFICACION)) {
                        if (ultimoHistorial.getRespuesta() != null
                                && (ultimoHistorial.getRespuesta().equals(CRespuesta.DEVOLUCION)
                                || ultimoHistorial.getRespuesta().equals(CRespuesta.INSCRIPCION_PARCIAL))) {
                            devueltoManual = true;
                        }
                    }
                }
            }

            if (turnoAnteriorManual) {
                if (!devueltoManual) {
                    throw new TurnoAnteriorInvalidoException(" El turno anterior con el ID: " + turno.getIdWorkflow() + " no puede ser ingresado como turno anterior.  Para que un turno pueda ser ingresado como turno anterior se requiere que haya sido entregado a p�blico, y que en calificaci�n o testamento haya sido devuelto.");
                }
            } else {
                validarTurnoHistorialFases(turno);
            }

            //
            SolicitudRegistro solicitudReg = (SolicitudRegistro) turno.getSolicitud();
            long idLiq = solicitudReg.getLastIdLiquidacion() - 1;
            List liquidaciones = solicitudReg.getLiquidaciones();

            List listaTemp = new Vector();
            List listaActosTemp = new Vector();
            List actos = new Vector();

            Iterator it = liquidaciones.iterator();
            while (it.hasNext()) {

                listaTemp = new Vector();
                listaActosTemp = new Vector();

                Liquidacion liq = (Liquidacion) it.next();
                LiquidacionTurnoRegistro liqReq = (LiquidacionTurnoRegistro) liq;
                liqReq.setSolicitud(solicitudReg);

                actos = liqReq.getActos();

                for (Iterator iter = actos.iterator(); iter.hasNext();) {
                    Object element = iter.next();
                    listaTemp.add(element);
                }

                /*if(turno.getHistorials().size() == 0){
					turnoAnteriorManual = true;
				}*/
                for (Iterator iterActos = listaTemp.iterator(); iterActos.hasNext();) {
                    Acto actoTemp = (Acto) iterActos.next();
                    Acto nuevoActo = new Acto();

                    liqReq.removeActo(actoTemp);
                    actoTemp.setLiquidacion(liqReq);

                    if (turnoAnteriorManual) {
                        //actoTemp.getTipoActo().getTipoCalculo().setIdTipoCalculo(CTipoCalculo.MANUAL);
                        if (actoTemp.isCobroImpuestos()) {
                            actoTemp.setTipoImpuesto(new TipoImpuesto());
                            actoTemp.getTipoImpuesto().setIdTipoImpuesto(CTipoImpuesto.NORMAL);
                        }
                    }

                    actoTemp = hermod.getLiquidacionActo(actoTemp);
                    listaActosTemp.add(actoTemp);
                }

                for (Iterator iter = listaActosTemp.iterator(); iter.hasNext();) {
                    Acto element = (Acto) iter.next();
                    liqReq.addActo(element);
                }

            }

            Turno turnoCertificado = null;
            List certificadosAsociados = solicitudReg.getSolicitudesHijas();
            if (certificadosAsociados != null) {

                int temp = 0;
                for (temp = 0; temp < certificadosAsociados.size(); temp++) {
                    SolicitudAsociada solAsoc = (SolicitudAsociada) certificadosAsociados.get(0);
                    solicitudReg.removeSolicitudesHija(solAsoc);
                    Solicitud solicitud = solAsoc.getSolicitudHija();
                    solicitud = hermod.getSolicitudById(solicitud.getIdSolicitud());
                    SolicitudPk idSolicitud = new SolicitudPk();
                    idSolicitud.idSolicitud = solicitud.getIdSolicitud();
                    turnoCertificado = hermod.getTurnoBySolicitud(idSolicitud);
                    List turnosSig = hermod.getTurnosSiguientesDevolucion(turnoCertificado.getIdWorkflow());
                    if (turnosSig == null || turnosSig.size() == 0) {
                        solicitud.setTurno(turnoCertificado);
                        solAsoc.setSolicitudHija(solicitud);
                        solicitudReg.addSolicitudesHija(solAsoc);
                    }
                }

            }

            TurnoPk idTurno = new TurnoPk();
            idTurno.idTurno = turno.getIdTurno();
            idTurno.idCirculo = turno.getIdCirculo();
            idTurno.idProceso = turno.getIdProceso();
            idTurno.anio = turno.getAnio();

            otroImp = hermod.getValorOtroImpuestoTurnosAnteriores(idTurno);
            turnosFolioMig = hermod.getTurnosFolioNoMigrados(t);

        } catch (TurnoAnteriorInvalidoException e) {
            throw e;
        } catch (HermodException h) {
            throw new TurnoAnteriorInvalidoException("El turno anterior ingresado es inv�lido", h);
        } catch (Throwable e) {
            throw new TurnoAnteriorInvalidoException("Imposible validar el turno", e);
        }

        EvnRespLiquidacion eventoRespuesta = new EvnRespLiquidacion(turno);
        eventoRespuesta.setTotalOtroImp(otroImp);
        eventoRespuesta.setTurnosFolioMig(turnosFolioMig);

        return eventoRespuesta;
    }

    /**
     * @param turno
     * @throws TurnoAnteriorInvalidoException
     */
    private void validarTurnoHistorialFases(Turno turno) throws TurnoAnteriorInvalidoException {
        List turnoHists = turno.getHistorials();
        Iterator itHis = turnoHists.iterator();
        boolean devuelto = false;
        boolean entrega = false;

        //Los turnos manuales no tiene TurnoHistorias y ya estan terminados
        // por ende no se debe verificar lo de abajo
        if (turnoHists.size() == 0) {
            return;
        }

        if (turno.getAnulado() != null && turno.getAnulado().equals(CTurno.TURNO_ANULADO)) {
            throw new TurnoAnteriorInvalidoException(" El turno anterior con el ID: " + turno.getIdWorkflow() + " no puede ser ingresado como turno anterior.  El turno se encuentra ANULADO");
        }

        if (turno.getFechaFin() == null) {
            throw new TurnoAnteriorInvalidoException(" El turno anterior con el ID: " + turno.getIdWorkflow() + " no puede ser ingresado como turno anterior.  Para que un turno pueda ser ingresado como turno anterior se requiere que haya sido entregado a p�blico, y que en calificaci�n o testamento haya sido devuelto.");
        }

        while (itHis.hasNext()) {
            TurnoHistoria turHis = (TurnoHistoria) itHis.next();

            // EL TURNO PASO POR ENTREGA REGISTRO � ENTREGA CORRESPONDENCIA
            if (turHis.getFase().equalsIgnoreCase(CFase.REG_ENTREGA) || turHis.getFase().equalsIgnoreCase(CFase.REG_ENTREGA_EXTERNO)) {
                entrega = true;
                if (devuelto) {
                    break;
                }
            }

            // EL TURNO PASO POR UNA RESPUESTA DEVOLUCION EN LA FASE CALIFICACION
            //TFS 3613: SE PUEDE INGRESAR UN TURNO QUE HAYA TENIDO INSCRIPCION PARCIAL EN CALIFICACION
            if ((turHis.getRespuesta() != null)
                    && turHis.getFase().equals(CFase.CAL_CALIFICACION)) {
                if (turHis.getRespuesta().equals(CRespuesta.DEVOLUCION)
                        || turHis.getRespuesta().equals(CRespuesta.INSCRIPCION_PARCIAL)) {
                    devuelto = true;
                    if (entrega) {
                        break;
                    }
                } else {
                    devuelto = false;
                    if (entrega) {
                        break;
                    }
                }
            }

            //SI EL TURNO PASO POR LA FASE DE TESTAMENTO Y POSEE LA RESPUESTA NEGAR
            if (turHis.getFase().equalsIgnoreCase(CFase.REG_TESTAMENTO)
                    && turHis.getRespuesta().equals(CRespuesta.NEGAR)) {
                devuelto = true;
            }
        }

        //EL turno es v�lido
        if (devuelto && entrega) {
            return;
        }

        throw new TurnoAnteriorInvalidoException(" El turno anterior con el ID: " + turno.getIdWorkflow() + " no puede ser ingresado como turno anterior.  Para que un turno pueda ser ingresado como turno anterior se requiere que haya sido entregado a p�blico, y que en calificaci�n o testamento haya sido devuelto.");
    }

    /**
     * @param evento
     * @return envento respuesta
     * @throws EventoException
     */
    private EventoRespuesta validar(EvnLiquidacionRegistro evento) throws EventoException {
        SolicitudRegistro solicitud = evento.getSolicitud();

        Boolean esCajero = new Boolean(false);

        try {
            List solFolio = solicitud.getSolicitudFolios();
            List matriculas = new Vector();
            Iterator itSolFolio = solFolio.iterator();

            while (itSolFolio.hasNext()) {
                SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
                matriculas.add(sol.getFolio().getIdMatricula());
            }

            forseti.validarMatriculas(hermod.getValidacionesSolicitud(solicitud), matriculas);
            solicitud.setIncentivoRegistral(hermod.isIncentivoRegistral(solicitud.getDocumento().getFecha()));

            //SE VERIFICA SI EL USUARIO ES CAJERO PARA MOSTRAR O NO EL BOTON SIGUIENTE PARA REGISTRAR EL PAGO.
            try {
                List roles = fenrir.darRolUsuario(evento.getUsuarioSIR().getIdUsuario());

                Iterator it = roles.iterator();
                while (it.hasNext()) {
                    Rol rol = (Rol) it.next();
                    if (rol.getRolId().equals(CRoles.CAJERO)) {
                        esCajero = new Boolean(true);
                    }

                }

            } catch (Exception fe) {
                ExceptionPrinter printer = new ExceptionPrinter(fe);
                Log.getInstance().error(ANLiquidacionRegistro.class, "Ha ocurrido una excepcion al consultar los reles del usuario " + printer.toString());
            }

        } catch (ForsetiException e) {
            ExceptionPrinter ep = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "No se pudieron validar las matr�culas:" + ep.toString());
            throw new ValidacionRegistroNoProcesadoException("Error en la validaci�n de matr�culas", e);
        } catch (HermodException e) {
            ExceptionPrinter ep = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "No se pudieron obtener las validaciones de las matr�culas:" + ep.toString());
            throw new ValidacionRegistroNoProcesadoException("Error obteniendo las validaciones de las matr�culas", e);
        } catch (Throwable e) {
            ExceptionPrinter ep = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "No se pudo validar:" + ep.toString());
            throw new ValidacionRegistroNoProcesadoException("No se pudo validar", e);
        }

        EvnRespLiquidacion evnLiq = new EvnRespLiquidacion(solicitud);
        evnLiq.setEsCajero(esCajero);

        CirculoPk cirID = new CirculoPk();
        if (evento.getSolicitudRegistro() != null && evento.getSolicitudRegistro().getCirculo() != null) {
            cirID.idCirculo = evento.getSolicitudRegistro().getCirculo().getIdCirculo();
        }
        evnLiq.setAlertasDocumentos(verificarAlertaActos(cirID, solicitud));
        return evnLiq;
    }

    /**
     * @param evento
     * @return envento respuesta
     * @throws EventoException
     */
    private EventoRespuesta buscarSolicitud(EvnLiquidacionRegistro evento) throws EventoException {
        String idSolicitud = evento.getIdSolicitud();

        Solicitud solicitud = null;
        Boolean esCajeroRegistro = new Boolean(false);

        try {
            solicitud = hermod.getSolicitudById(idSolicitud);

            List listaLiquidaciones = solicitud.getLiquidaciones();

            if (listaLiquidaciones.size() >= 0) {
                Liquidacion liq = (Liquidacion) listaLiquidaciones.get(0);

                if (liq.getPago() != null) {
                    throw new SolicitudInvalidaRegistroException("La solicitud ya fue pagada");
                }
            } else {
                throw new SolicitudInvalidaRegistroException("La solicitud no tiene liquidaciones asociadas");
            }

            //SE VERIFICA SI EL USUARIO ES CAJERO PARA MOSTRAR O NO LA PANTALLA DE REGISTRO DE PAGO.
            try {
                List roles = fenrir.darRolUsuario(evento.getUsuarioSIR().getIdUsuario());

                Iterator it = roles.iterator();
                while (it.hasNext()) {
                    Rol rol = (Rol) it.next();
                    if (rol.getRolId().equals(CRoles.CAJERO_REGISTRO)) {
                        esCajeroRegistro = new Boolean(true);
                    }

                }

            } catch (Exception fe) {
                ExceptionPrinter printer = new ExceptionPrinter(fe);
                Log.getInstance().error(ANLiquidacionRegistro.class, "Ha ocurrido una excepcion al consultar los reles del usuario " + printer.toString());
            }

        } catch (HermodException e) {
            ExceptionPrinter ep = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "La solicitud es invalida o no existe:" + ep.toString());
            throw new SolicitudInvalidaRegistroException("La solicitud es invalida o no existe", e);
        } catch (Throwable e) {
            ExceptionPrinter ep = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "No se pudo validar:" + ep.toString());
            throw new SolicitudInvalidaRegistroException("No se pudo validar", e);
        }

        EvnRespLiquidacionRegistro respuesta = new EvnRespLiquidacionRegistro(solicitud, EvnRespLiquidacionRegistro.BUSCAR_SOLICITUD);
        respuesta.setEsCajeroRegistro(esCajeroRegistro);
        return respuesta;
    }

    /**
     * M�todo que se encarga de consultar una solicitud para cargar sus
     * liquidaciones y poder modificarlas. Es usado para poder editar las
     * liquidaciones antes de que sean pagadas.
     *
     * @param evento
     * @return envento respuesta
     * @throws EventoException
     */
    private EventoRespuesta buscarSolicitudEdicion(EvnLiquidacionRegistro evento) throws EventoException {
        String idSolicitud = evento.getIdSolicitud();

        Solicitud solicitud = null;

        try {
            solicitud = hermod.getSolicitudById(idSolicitud);

            List listaLiquidaciones = solicitud.getLiquidaciones();

            if (listaLiquidaciones.size() >= 0) {
                Liquidacion liq = (Liquidacion) listaLiquidaciones.get(0);

                if (liq.getPago() != null) {
                    throw new SolicitudInvalidaRegistroException("La solicitud ya fue pagada");
                }
            } else {
                throw new SolicitudInvalidaRegistroException("La solicitud no tiene liquidaciones asociadas");
            }

        } catch (HermodException e) {
            ExceptionPrinter ep = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "La solicitud es inv�lida o no existe:" + ep.toString());
            throw new SolicitudInvalidaRegistroException("La solicitud es invalida o no existe", e);
        } catch (Throwable e) {
            ExceptionPrinter ep = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "No se pudo validar:" + ep.toString());
            throw new SolicitudInvalidaRegistroException("No se pudo validar", e);
        }

        EvnRespLiquidacionRegistro respuesta = new EvnRespLiquidacionRegistro(solicitud, EvnRespLiquidacionRegistro.BUSCAR_SOLICITUD_EDICION);

        return respuesta;
    }

    /**
     * @param evento
     * @return envento respuesta
     * @throws EventoException
     */
    private EventoRespuesta validarSolicitud(EvnLiquidacionRegistro evento) throws EventoException {
        String idSolicitud = evento.getIdSolicitud();
        SolicitudPk solId = new SolicitudPk();
        solId.idSolicitud = idSolicitud;

        Estacion estacion = evento.getEstacion();
        EstacionReciboPk estacionRecibo = new EstacionReciboPk();
        estacionRecibo.idEstacion = estacion.getEstacionId();

        SolicitudRegistro solicitud = null;
        long valorSecuencial;

        long idProceso = Long.valueOf(CProceso.PROCESO_REGISTRO).longValue();

        try {
            gov.sir.core.negocio.modelo.Usuario user = null;

            try {
                user = evento.getUsuarioNec();
            } catch (Exception ex) {
                Log.getInstance().debug(ANLiquidacionRegistro.class, "No se encontro el usuario en el evento");
            }

            valorSecuencial = hermod.getNextNumeroReciboSinAvanzar(estacionRecibo, user, idProceso);
            solicitud = (SolicitudRegistro) hermod.getSolicitudById(idSolicitud);

            //Se valida que esa solicitud sea la del dia
            Date local_Date;
            local_Date = new Date(System.currentTimeMillis());

            Date fechaCreacionSolicitud = solicitud.getFecha();

            // formatear la fecha para salida de exception
            String fechaCreacionSolicitud_String = DateFormatUtil.format(fechaCreacionSolicitud);

            BasicDateFormatComparator comparator = new BasicDateFormatComparator(DateFormatUtil.DEFAULT_FORMAT);

            if (comparator.compare(local_Date, fechaCreacionSolicitud) > 0) {
                throw new Exception("La Solicitud debe ser del dia de Hoy, Fecha de Solicitud: " + fechaCreacionSolicitud_String + ".");
            }

            //validar si la solicitud tiene un pago
            Pago pago;
            List liquidaciones = solicitud.getLiquidaciones();

            if (liquidaciones != null) {
                Liquidacion liq = (Liquidacion) liquidaciones.get(0);

                if (liq != null) {
                    pago = liq.getPago();

                    if (pago == null) {
                        throw new Exception("La solicitud no tiene un pago asociado.");
                    }
                } else {
                    throw new Exception("La solicitud no tiene una liquidacion asociada.");
                }
            } else {
                throw new Exception("La solicitud no tiene una lista de liquidaciones asociada.");
            }

            //mirar si ya tiene un turno asociado
            Turno turno = hermod.getTurnoBySolicitud(solId);

            if (turno != null) {
                throw new Exception("La solicitud ya tiene un turno asociado.");
            }

            //obtener checkitmes y agregarlos a la solicitud.
            SubtipoSolicitud subsol = solicitud.getSubtipoSolicitud();
            List checkitems = hermod.getCheckItemsBySubtipoSolicitud(subsol);
            Iterator ic = checkitems.iterator();

            for (; ic.hasNext();) {
                CheckItem check = (CheckItem) ic.next();
                subsol.addCheckItem(check);
            }

            solicitud.setSubtipoSolicitud(subsol);
        } catch (HermodException e) {
            ExceptionPrinter ep = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "La solicitud es invalida o no existe:" + ep.toString());
            throw new SolicitudInvalidaRegistroException("La solicitud es invalida o no existe", e);
        } catch (Throwable e) {
            ExceptionPrinter ep = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "No se pudo validar:" + ep.toString());
            throw new SolicitudInvalidaRegistroException("No se pudo validar", e);
        }

        EvnRespLiquidacionRegistro respuesta = new EvnRespLiquidacionRegistro(solicitud, EvnRespLiquidacionRegistro.VALIDAR_SOLICITUD);
        respuesta.setValorSecuencial(valorSecuencial);

        return respuesta;
    }

    /**
     * Este metodo hace el llamado al negocio para que se incremente el
     * secuencial de recibos.
     *
     * @param evento el evento recibido. Este evento debe ser del tipo
     * <CODE>EvnCertificado</CODE>
     * @return el evento de respuesta. Este evento debe ser del tipo
     * <CODE>EvnRespCertificado</CODE>
     * @throws EventoException
     */
    private EventoRespuesta incrementarSecuencial(EvnLiquidacionRegistro evento) throws EventoException {
        EvnRespLiquidacionRegistro evRespuesta = null;
        long valorSecuencial;
        long idProceso = Long.valueOf(CProceso.PROCESO_REGISTRO).longValue();

        try {
            gov.sir.core.negocio.modelo.Usuario user = null;

            try {
                user = evento.getUsuarioNec();
            } catch (Exception ex) {
                Log.getInstance().debug(ANLiquidacionRegistro.class, "No se encontro el usuario en el evento");
            }

            Estacion estacion = evento.getEstacion();
            EstacionReciboPk estacionRecibo = new EstacionReciboPk();
            estacionRecibo.idEstacion = estacion.getEstacionId();
            valorSecuencial = hermod.avanzarNumeroRecibo(estacionRecibo, 1, idProceso);
            valorSecuencial = hermod.getNextNumeroReciboSinAvanzar(estacionRecibo, user, idProceso);

            boolean motivo = hermod.almacenarMotivoIncrementoSecuencial(evento.getUsuarioNec(), valorSecuencial, evento.getMotivo());
        } catch (HermodException e) {
            throw new EventoException("No se pudo obtener el secuencial de recibos.", e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        evRespuesta = new EvnRespLiquidacionRegistro(new Long(valorSecuencial), EvnRespLiquidacionRegistro.INCREMENTAR_SECUENCIAL_RECIBO);

        return evRespuesta;
    }

    /**
     * Este metodo hace el llamado al negocio para que se incremente el
     * secuencial de recibos.
     *
     * @param evento el evento recibido. Este evento debe ser del tipo
     * <CODE>EvnCertificado</CODE>
     * @return el evento de respuesta. Este evento debe ser del tipo
     * <CODE>EvnRespCertificado</CODE>
     * @throws EventoException
     */
    private EventoRespuesta crearTurnoRegistro(EvnLiquidacionRegistro evento) throws EventoException {
        EvnRespLiquidacionRegistro evRespuesta = null;
        SolicitudRegistro sol = evento.getSolicitudRegistro();
        Usuario usuario = evento.getUsuarioSIR();
        Turno turno = null;
        String fechaImp = evento.getFechaImpresion();
        Circulo circulo = evento.getCirculo();
        LiquidacionTurnoRegistro liq = null;
        String uid;
        int idImprimible = 0;
        String estacionAsignada = null;

        long idProceso = Long.valueOf(CProceso.PROCESO_REGISTRO).longValue();

        //inicializar el UID
        uid = evento.getUID();

        //obtener el pago de la solicitud
        Pago pago = null;

        List liquidaciones = sol.getLiquidaciones();

        if ((liquidaciones != null) && (liquidaciones.size() > 0)) {
            liq = (LiquidacionTurnoRegistro) liquidaciones.get(0);
            liq.setUid(uid);
            pago = liq.getPago();
        }

        if (pago == null) {
            throw new CrearTurnoException("el pago de la solicitud es null");
        }

        Estacion estacion = evento.getEstacion();
        EstacionReciboPk estacionRecibo = new EstacionReciboPk();
        estacionRecibo.idEstacion = estacion.getEstacionId();

        try {
            long valorSecuencial = hermod.getNextNumeroRecibo(estacionRecibo, usuario, idProceso);
            //long valorSecuencial = hermod.getNextNumeroReciboSinAvanzar(estacionRecibo);
            pago.setNumRecibo("" + valorSecuencial);

            PagoPk pagoID = new PagoPk();
            pagoID.idLiquidacion = pago.getIdLiquidacion();
            pagoID.idSolicitud = pago.getIdSolicitud();
            hermod.setNumeroReciboPago(pagoID, "" + valorSecuencial);

            /**
             * NUMERO DE RECIBO EN LOS CERTIFICADOS ASOCIADOS
             */
            List listaAsociados = sol.getSolicitudesHijas();
            int tamAsociadas = listaAsociados.size();
            long valorSecuencialCertificadoAsociado = 0;

            for (int i = 0; i < tamAsociadas; i++) {
                valorSecuencialCertificadoAsociado = hermod.getNextNumeroRecibo(estacionRecibo, usuario, idProceso);
                SolicitudAsociada solAsociada = (SolicitudAsociada) listaAsociados.get(i);
                SolicitudCertificado solCert = (SolicitudCertificado) solAsociada.getSolicitudHija();
                //Obtener la liquidaci�n asociada con la solicitud
                Liquidacion liqCertificados = (LiquidacionTurnoCertificado) solCert.getLiquidaciones().get(0);
                //	Los certificados asociados van a consumir el mismo secuencial de los recibos de registro//
                PagoPk pagoCID = new PagoPk();
                pagoCID.idLiquidacion = liqCertificados.getIdLiquidacion();
                pagoCID.idSolicitud = liqCertificados.getIdSolicitud();
                hermod.setNumeroReciboPago(pagoCID, "" + valorSecuencialCertificadoAsociado);
            }

            /**
             * *******
             */
            /*SECCION CREACION del turno registro (este tambien crea los turnos de los certificados asociados)*/
            turno = hermod.crearTurnoRegistro(sol, usuario);

            /**
             * @author Fernando Padilla Velez
             * @change Acta - Requerimiento No 178 - Integraci�n Gesti�n
             * Documental y SIR, Se agrega esta fragmento de codigo para la
             * publicacion del mensaje del turno creado.
             */
            /**
             * @author Fernando Padilla Velez
             * @change 6760: Acta - Requerimiento No 191 - Pantalla
             * Administrativa SGD, Se comentan estan lineas, ya que se realiz�
             * refactoring al proceso y ya no son necesarias.
             */
            SGD sgd = new SGD(turno, usuario.getUsername());
            sgd.enviarTurnoRegistro();
            
            List matriculasLiquidador = evento.getMatriculasLiquidador();
            if(matriculasLiquidador != null){
                Iterator itLiq = matriculasLiquidador.iterator();
                while(itLiq.hasNext()){
                    String matricula = (String) itLiq.next();
                    hermod.addCtrlMatricula(matricula, "1", "LIQUIDADOR", turno.getIdWorkflow());
                }
            }

            List solFoliosMig = hermod.getSolicitudFolioMigBySolicitud(turno.getSolicitud());
            if (solFoliosMig != null && solFoliosMig.size() > 0) {

                List turnosFoliosMig = new ArrayList();
                Iterator it = solFoliosMig.iterator();
                while (it.hasNext()) {
                    SolicitudFolioMig solFolMig = (SolicitudFolioMig) it.next();

                    TurnoFolioMig turnoFolMig = new TurnoFolioMig();
                    turnoFolMig.setIdFolio(solFolMig.getIdFolio());
                    turnoFolMig.setIdTurno(turno.getAnio() + "-" + turno.getIdTurno());
                    turnoFolMig.setIdProceso(solFolMig.getIdProceso());
                    turnoFolMig.setIdCirculo(solFolMig.getIdCirculo());
                    turnoFolMig.setAnulado(false);
                    turnoFolMig.setCreadoSir(true);
                    turnosFoliosMig.add(turnoFolMig);
                }

                hermod.addTurnosFolioMigToTurno(turno, turnosFoliosMig);
                hermod.removeSolicitudFolioMig(turno.getSolicitud());
            }

            SolicitudRegistro solReg = (SolicitudRegistro) turno.getSolicitud();
            if (solReg.getTurnoAnterior() != null) {
                List turnosFolioMig = hermod.getTurnosFolioNoMigrados(solReg.getTurnoAnterior());
                if (turnosFolioMig != null) {
                    hermod.addTurnosFolioMigToTurno(turno, turnosFolioMig);
                }
            }

            /**
             * **************************************************
             */
            /*                ELIMINACION SAS                    */
            /**
             * *************************************************
             */
            //2. Crear el turno historia asociado con la creaci�n del turno.
            InicioProcesos inicioProcesos = hermod.obtenerFaseInicial(CProceso.PROCESO_REGISTRO);

            Hashtable parametrosInicio = new Hashtable();
            parametrosInicio.put(Processor.ROL, inicioProcesos.getIdRol());
            parametrosInicio.put(Processor.ITEM_KEY, turno.getIdWorkflow());
            parametrosInicio.put(Processor.ACTIVITY, inicioProcesos.getIdFase());
            parametrosInicio.put(Processor.NOT_ID, "1");

            //4. Obtener estaci�n a la que se asocia el turno. 
            estacionAsignada = hermod.obtenerEstacionTurno(parametrosInicio, turno.getIdCirculo());

            //5. Guardar informaci�n del turno: ITEMKEY, NOTIFICATION ID, ESTACION ASIGNADA, etc. 
            hermod.guardarInfoTurnoEjecucion(parametrosInicio, estacionAsignada, turno, usuario);

            // Bug 3479
            // :: ProcId: Registro
            // (usuario que genera la solicitud)
            // jxpath-search:.
            // Bug 5223
            // realizar busqueda de usuario
            String local_UsuarioGeneraRecibo = "";

            local_SearchImpl_jx:
            {
                // Cambio, no es el usuario de solicitud sino el usuario
                // que se envia para afectar el wf
                //

                // String userData;
                // userData = ( null == usuario )?(""):( usuario.getUsername() );
                // userData = ( null == usuario )?(""):( usuario.getUsername() );
                // -----------------------------------------------------------------------------
                // Bug 05223
                local_UsuarioGeneraRecibo = print_FootUtils_BuildUserName(usuario);
                // -----------------------------------------------------------------------------

            } // :searchImpl_jx


            /*FIN DE SECCION*/
 /*SECCION IMPRESION del recibo de solicitud*/
            //inicilizar pago para el imprimible de recibo
            SolicitudRegistro soli = null;
            if (liq != null) {
                soli = (SolicitudRegistro) hermod.getSolicitudById(sol.getIdSolicitud());
                soli.setTurno(turno);
                liq.setSolicitud(soli);
                pago.setLiquidacion(liq);
            }

            OrdenImpresion ordenImpresion = new OrdenImpresion();
            int ordenActual = 1;

            //crear imprimible
            ImprimibleRecibo imprimible = new ImprimibleRecibo(pago, circulo, fechaImp, CTipoImprimible.RECIBO);

            imprimible.setUsuarioGeneraRecibo(local_UsuarioGeneraRecibo);

            //mandar a imprimir dandole el uid
            idImprimible = this.imprimir(imprimible, uid, ordenImpresion, ordenActual);
            ordenActual++;

            //imprimir recibos de certificados asociados			
            if (soli.getSolicitudesHijas() != null && !soli.getSolicitudesHijas().isEmpty()) {
                Iterator itSolHijas = soli.getSolicitudesHijas().iterator();
                while (itSolHijas.hasNext()) {
                    SolicitudAsociada solAsociada = (SolicitudAsociada) itSolHijas.next();
                    Solicitud solHija = solAsociada.getSolicitudHija();
                    if (solHija instanceof SolicitudCertificado) {
                        imprimirReciboCertAsociados(solHija, usuario, uid, turno, ordenImpresion, ordenActual);
                        ordenActual++;
                    }
                }
            }
            /*FIN DE SECCION*/
        } catch (HermodException e) {
            throw new EventoException("No se pudo obtener el secuencial de recibos.", e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANLiquidacionRegistro.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        evRespuesta = new EvnRespLiquidacionRegistro(turno, EvnRespLiquidacionRegistro.CREAR_TURNO);
        evRespuesta.setIdImprimible(idImprimible);

        return evRespuesta;
    }

    private void imprimirReciboCertAsociados(Solicitud solHija, Usuario usuario, String uid, Turno padre, OrdenImpresion ordenImpresion, int ordenActual) throws Throwable {
        SolicitudPk oid = new SolicitudPk();
        oid.idSolicitud = solHija.getIdSolicitud();
        Turno turnoHijo = hermod.getTurnoBySolicitud(oid);

        SolicitudCertificado sol = (SolicitudCertificado) hermod.getSolicitudById(solHija.getIdSolicitud());
        SolicitudAsociada solPadre = (SolicitudAsociada) sol.getSolicitudesPadres().get(0);
        solPadre.getSolicitudPadre().setTurno(padre);
        List liquidaciones = sol.getLiquidaciones();

        /* Obtener la liquidaci�n asociada con la solicitud*/
        Liquidacion liqCertificados = (LiquidacionTurnoCertificado) liquidaciones.get(0);
        //Cambiar el numero de recibo de pago del certificado asociado para la impresion
        PagoPk pagoCID = new PagoPk();
        pagoCID.idLiquidacion = liqCertificados.getIdLiquidacion();
        pagoCID.idSolicitud = liqCertificados.getIdSolicitud();
        String numRecibo = hermod.getNumReciboPagoByID(pagoCID);

        /**
         * Se obtienen las aplicaciones pago al turno de certificado asociado
         */
        Pago pago = hermod.getPagoByID(pagoCID);
        pago.setLiquidacion(((LiquidacionTurnoCertificado) liquidaciones.get(liquidaciones.size() - 1)));
        pago.getLiquidacion().setSolicitud(sol);
        pago.getLiquidacion().getSolicitud().setTurno(turnoHijo);
        String usuarioIniciador = usuario.getUsername();
        long idUsuario = fenrir.darIdUsuario(usuarioIniciador);
        List estacionesUsuario = null;
        Rol rol = new Rol();
        rol.setRolId(CRoles.CAJERO_REGISTRO);

        estacionesUsuario = fenrir.darEstacionUsuario(idUsuario, rol);

        boolean flagSalidaEstacion = false;
        int posicionEstacion = 0;
        String privadaUsuario = "X-" + usuarioIniciador;

        EstacionReciboPk estacionReciboID = null;

        if (estacionesUsuario != null) {

            for (int i = 0; i < estacionesUsuario.size() && !flagSalidaEstacion; i++) {
                Estacion estacionActual = (Estacion) estacionesUsuario.get(i);
                if (estacionActual.getEstacionId().equals(privadaUsuario)) {
                    posicionEstacion = i;
                    flagSalidaEstacion = true;
                }
            }
            Estacion estacion = (Estacion) estacionesUsuario.get(posicionEstacion);
            estacionReciboID = new EstacionReciboPk();
            estacionReciboID.idEstacion = estacion.getEstacionId();

            pago.setNumRecibo(numRecibo);
        } else {
            throw new EventoException("No se encontraron estaciones para imprimir");
        }

        Circulo circulo = sol.getCirculo();
        String fechaImpresion = this.getFechaImpresion();

//        DocumentoPago docPago;
//        List aplicaciones = pago.getAplicacionPagos();
//        if (aplicaciones != null) {
//            Banco banco = null;
//            Iterator it = aplicaciones.iterator();
//            while (it.hasNext()) {
//                AplicacionPago ap = (AplicacionPago) it.next();
//                docPago = ap.getDocumentoPago();
//                if (docPago instanceof DocPagoGeneral) {
//                    try {
//                        CirculoTipoPago circuloTipoPago = hermod.getCirculoTipoPagoByID(((DocPagoGeneral) docPago).getIdCtp());
//                        ((DocPagoGeneral) docPago).setNombreCanal(circuloTipoPago.getCanalesRecaudo().getNombreCanal());
//                        ((DocPagoGeneral) docPago).setNombreFormaPago(circuloTipoPago.getTipoPago().getNombre());
//                        if (((DocPagoGeneral) docPago).getBanco().getIdBanco() != null) {
//                            banco = hermod.getBancoByID(((DocPagoGeneral) docPago).getBanco().getIdBanco());
//                            ((DocPagoGeneral) docPago).setBanco(banco);
//                        } else {
//                            ((DocPagoGeneral) docPago).setBanco(banco);
//                        }
//
//                    } catch (Throwable ex) {
//                        Logger.getLogger(ANLiquidacionRegistro.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                }
//            }
//
//        }

        ImprimibleRecibo impRec = new ImprimibleRecibo(pago, circulo, fechaImpresion, CTipoImprimible.RECIBO);
        impRec.setUsuarioGeneraRecibo("" + usuario.getIdUsuario());
        LiquidacionTurnoCertificado liquidaCert = (LiquidacionTurnoCertificado) liquidaciones.get(liquidaciones.size() - 1);
        String idTipoCertificado = liquidaCert.getTipoCertificado().getIdTipoCertificado();
        impRec.setTipoCertificadoId(idTipoCertificado);
        impRec.setMayorExtension(false);
        this.imprimir(impRec, uid, ordenImpresion, ordenActual);
    }

// -----------------------------------------------------------------------------
    private String
            print_FootUtils_BuildUserName(long userId) {
        return "" + userId;
    } // end method

    private String
            print_FootUtils_BuildUserName(Long userId) {
        if (null == userId) {
            return getNullableString(true);
        }
        return print_FootUtils_BuildUserName(userId.longValue());
    } // end method

    private String
            print_FootUtils_BuildUserName(gov.sir.core.negocio.modelo.Usuario user) {
        if (null == user) {
            return getNullableString(true);
        }
        return print_FootUtils_BuildUserName(user.getIdUsuario());
    } // end method

    public static String
            Nvl(String string, String replaceIfNull) {
        return (null == string) ? (replaceIfNull) : (string);
    } // end-method: Nvl

    public static String
            getNullableString(boolean treatBlankAsNull) {
        return ((treatBlankAsNull) ? ("") : (null));
    } // end-method: Nvl

// -----------------------------------------------------------------------------
    /**
     * @param imprimible
     * @param uid
     */
    private int imprimir(ImprimibleBase imprimible, String uid, OrdenImpresion ordenImpresion, int ordenActual) throws Exception {
        //OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
        int maxIntentos;
        int espera;
        int idImprimible = 0;

        //INGRESO DE INTENTOS DE IMPRESION
        try {
            String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO);

            if (intentosImpresion != null) {
                Integer intentos = new Integer(intentosImpresion);
                maxIntentos = intentos.intValue();
            } else {
                Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
                maxIntentos = intentosDefault.intValue();
            }

            //INGRESO TIEMPO DE ESPERA IMPRESION
            String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_RECIBO);

            if (intentosImpresion != null) {
                Integer esperaInt = new Integer(esperaImpresion);
                espera = esperaInt.intValue();
            } else {
                Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
                espera = esperaDefault.intValue();
            }
        } catch (Throwable t) {
            Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
            maxIntentos = intentosDefault.intValue();

            Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
            espera = esperaDefault.intValue();
        }

        Bundle bundle = new Bundle(imprimible);
        if(imprimible.getTipoImprimible().equals(CTipoImprimible.RECIBO)){
                    try{
                        ImprimibleRecibo imprimibleR = (ImprimibleRecibo) imprimible;
                        String copyActive = hermod.getCopiaImp(imprimibleR.getCirculo().getIdCirculo());
                        if(copyActive.equals(CHermod.ACTIVE)){
                             bundle.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
                        }
                    } catch (Throwable ex) {
                        try{
                            ImprimibleConstanciaLiquidacion imprimibleR = (ImprimibleConstanciaLiquidacion) imprimible;
                            String copyActive = hermod.getCopiaImp(imprimibleR.getLiquidacion().getCirculo());
                            if(copyActive.equals(CHermod.ACTIVE)){
                                 bundle.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
                            }
                        } catch(Throwable th){
                            
                        }
                    }  
        }
        try {
            //se manda a imprimir el recibo por el identificador unico de usuario
            if (ordenImpresion != null) {
                printJobs.enviar(uid, bundle, maxIntentos, espera, ordenImpresion, ordenActual);
            } else {
                printJobs.enviar(uid, bundle, maxIntentos, espera);
            }

        } catch (PrintJobsException t) {
            t.printStackTrace();
            idImprimible = t.getIdImpresion();
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return idImprimible;
    }

    /**
     * Metodo que retorna la cadena con la fecha actual de impresi�n.
     *
     * @return
     */
    protected String getFechaImpresion() {

        Calendar c = Calendar.getInstance();
        int dia, ano, hora;
        String min, seg, mes;

        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = ImprimibleConstantes.MESES[c.get(Calendar.MONTH)]; //0-Based
        ano = c.get(Calendar.YEAR);

        hora = c.get(Calendar.HOUR_OF_DAY);
        if (hora > 12) {
            hora -= 12;
        }

        min = formato(c.get(Calendar.MINUTE));
        seg = formato(c.get(Calendar.SECOND));

        String fechaImp
                = "Impreso el "
                + dia
                + " de "
                + mes
                + " de "
                + ano
                + " a las "
                + formato(hora)
                + ":"
                + min
                + ":"
                + seg
                + " "
                + DateFormatUtil.getAmPmString(c.get(Calendar.AM_PM));

        return fechaImp;
    }

    /**
     * Metodo que retorna un numero con un "0" antes en caso de ser menor que
     * 10.
     *
     * @param i el numero.
     * @return
     */
    protected String formato(int i) {
        if (i < 10) {
            return "0" + (new Integer(i)).toString();
        }
        return (new Integer(i)).toString();
    }
}

package gov.sir.hermod;

import gov.sir.core.negocio.modelo.AccionNotarial;
import gov.sir.core.negocio.modelo.AccionNotarialPk;
import gov.sir.core.negocio.modelo.Acto;
import gov.sir.core.negocio.modelo.AlcanceGeografico;
import gov.sir.core.negocio.modelo.AlcanceGeograficoPk;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.BancoPk;
import gov.sir.core.negocio.modelo.BancosXCirculo;
import gov.sir.core.negocio.modelo.BancosXCirculoPk;
import gov.sir.core.negocio.modelo.Busqueda;
import gov.sir.core.negocio.modelo.CanalesRecaudo;
import gov.sir.core.negocio.modelo.Categoria;
import gov.sir.core.negocio.modelo.CategoriaPk;
import gov.sir.core.negocio.modelo.CausalRestitucion;
import gov.sir.core.negocio.modelo.CausalRestitucionPk;
import gov.sir.core.negocio.modelo.CheckItem;
import gov.sir.core.negocio.modelo.CheckItemPk;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoNotarial;
import gov.sir.core.negocio.modelo.CirculoNotarialPk;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.CirculoProceso;
import gov.sir.core.negocio.modelo.CirculoProcesoPk;
import gov.sir.core.negocio.modelo.CirculoTipoPago;
import gov.sir.core.negocio.modelo.CirculoTipoPagoPk;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.CuentasBancarias;
import gov.sir.core.negocio.modelo.CuentasBancariasPk;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.EntidadPublica;
import gov.sir.core.negocio.modelo.EstacionRecibo;
import gov.sir.core.negocio.modelo.EstacionReciboPk;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Imprimible;
import gov.sir.core.negocio.modelo.ImprimiblePk;
import gov.sir.core.negocio.modelo.InicioProcesos;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.NotaActuacion;
import gov.sir.core.negocio.modelo.NotaPk;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.OPLookupCodesPk;
import gov.sir.core.negocio.modelo.OPLookupTypes;
import gov.sir.core.negocio.modelo.OPLookupTypesPk;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Oficio;
import gov.sir.core.negocio.modelo.OficioPk;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.PagoPk;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.ProcesoPk;
import gov.sir.core.negocio.modelo.ProcesoReparto;
import gov.sir.core.negocio.modelo.Recurso;
import gov.sir.core.negocio.modelo.RecursoPk;
import gov.sir.core.negocio.modelo.Relacion;
import gov.sir.core.negocio.modelo.Reparto;
import gov.sir.core.negocio.modelo.RepartoNotarial;
import gov.sir.core.negocio.modelo.RepartoNotarialPk;
import gov.sir.core.negocio.modelo.RolPantalla;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAntiguoSistema;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudFolioPk;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;
import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;
import gov.sir.core.negocio.modelo.SubtipoAtencion;
import gov.sir.core.negocio.modelo.SubtipoAtencionPk;
import gov.sir.core.negocio.modelo.SubtipoSolicitud;
import gov.sir.core.negocio.modelo.SubtipoSolicitudPk;
import gov.sir.core.negocio.modelo.SucursalBanco;
import gov.sir.core.negocio.modelo.SucursalBancoPk;
import gov.sir.core.negocio.modelo.Tarifa;
import gov.sir.core.negocio.modelo.TarifaPk;
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.TestamentoCiudadanoPk;
import gov.sir.core.negocio.modelo.TextoImprimible;
import gov.sir.core.negocio.modelo.TextoImprimiblePk;
import gov.sir.core.negocio.modelo.TipoActo;
import gov.sir.core.negocio.modelo.TipoActoPk;
import gov.sir.core.negocio.modelo.DocumentoPagoCorreccion;
import gov.sir.core.negocio.modelo.NotificacionNota;
import gov.sir.core.negocio.modelo.TipoCalculo;
import gov.sir.core.negocio.modelo.TipoCalculoPk;
import gov.sir.core.negocio.modelo.TipoCertificado;
import gov.sir.core.negocio.modelo.TipoCertificadoPk;
import gov.sir.core.negocio.modelo.TipoCertificadoValidacion;
import gov.sir.core.negocio.modelo.TipoCertificadoValidacionPk;
import gov.sir.core.negocio.modelo.TipoConsulta;
import gov.sir.core.negocio.modelo.TipoConsultaPk;
import gov.sir.core.negocio.modelo.TipoDerechoReg;
import gov.sir.core.negocio.modelo.TipoDerechoRegPk;
import gov.sir.core.negocio.modelo.TipoFotocopia;
import gov.sir.core.negocio.modelo.TipoFotocopiaPk;
import gov.sir.core.negocio.modelo.TipoImpuesto;
import gov.sir.core.negocio.modelo.TipoImpuestoPk;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.TipoNotaPk;
import gov.sir.core.negocio.modelo.TipoPago;
import gov.sir.core.negocio.modelo.TipoRecepcionPeticion;
import gov.sir.core.negocio.modelo.TipoRecepcionPeticionPk;
import gov.sir.core.negocio.modelo.TipoRelacion;
import gov.sir.core.negocio.modelo.TipoTarifa;
import gov.sir.core.negocio.modelo.TipoTarifaPk;
import gov.sir.core.negocio.modelo.Traslado;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoEjecucion;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.UsuarioPk;
import gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion;
import gov.sir.core.negocio.modelo.UsuarioSubtipoAtencionPk;
import gov.sir.core.negocio.modelo.Validacion;
import gov.sir.core.negocio.modelo.ValidacionNota;
import gov.sir.core.negocio.modelo.ValidacionNotaDetalle;
import gov.sir.core.negocio.modelo.ValidacionNotaPk;
import gov.sir.core.negocio.modelo.VeredaPk;
import gov.sir.core.negocio.modelo.WebSegEng;
import gov.sir.core.negocio.modelo.WebSegEngPk;
import gov.sir.core.negocio.modelo.ZonaNotarial;
import gov.sir.core.negocio.modelo.constantes.CAvanzarTurno;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.constantes.CValidacion;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;
/**
 * @author : Carlos Mario Torre Urina
 * @casoMantis :11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
 * @change :Se habilita el uso de la clase
 */
import gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.fenrir.dao.FenrirFactory;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.dao.ForsetiFactory;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.HermodDAOFactory;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.pagos.Liquidador;
import gov.sir.hermod.pagos.LiquidadorException;
import gov.sir.hermod.pagos.LiquidarException;
import gov.sir.hermod.workflow.Message;

import gov.sir.print.common.Bundle;
import gov.sir.print.server.dao.PrintFactory;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.auriga.core.modelo.Servicio;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.util.ExceptionPrinter;
import gov.sir.core.negocio.modelo.ReproduccionSellos;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoCorreccionEnhanced;
import org.portal.modelo.Transaccion;

/**
 * @author mrios, mortiz, dlopez Clase que se utiliza como fachada para el
 * acceso a todos los servicios ofrecidos por Hermod.
 * <p>
 * El servicio hermod se encarga de manejar el modelo operativo de la aplicación
 * SIR.
 * <p>
 * Se incluyen servicios para el manejo de turnos, procesos, fases y para la
 * consulta y actualización de variables de tipo operativo.
 */
public class HermodService implements Servicio, HermodServiceInterface {

    /* Adiciona Funcionalidad Boton de Pago
         * Author: Ingeniero Diego Henandez
         * Modificacion en: 2010/02/23 by jvenegas 
     */
    private static HermodService instancia;
    private HermodDAOFactory factory;
    private PrintFactory impresion;
    private ForsetiFactory forseti;
    private FenrirFactory fenrir;

    /**
     * Método constructor de la Clase <code>HermodService</code>.
     */
    private HermodService() throws HermodException {
        try {

            /* Adiciona Funcionalidad Boton de Pago
                        * Author: Ingeniero Diego Henandez
                        * Modificacion en: 2010/02/23 by jvenegas
             */
            factory = HermodDAOFactory.getFactory();
            forseti = ForsetiFactory.getFactory();
            fenrir = FenrirFactory.getInstance();
            impresion = PrintFactory.getFactory();

        } catch (DAOException e) {
            Log.getInstance().error(this.getClass(), e);
            Log.getInstance().error(this.getClass(), e.getMessage(), e);
        } catch (gov.sir.print.server.dao.FactoryException e) {
            throw new HermodException("Error instanciando el servicio.", e);
        } catch (gov.sir.forseti.dao.FactoryException e) {
            throw new HermodException("Error instanciando el servicio.", e);
        }
    }

    /**
     * Obtiene la instancia de la fachada
     *
     * @return la instancia de la fachada.
     */
    public static HermodService getInstance() throws HermodException {
        if (instancia == null) {
            instancia = new HermodService();
        }
        return instancia;
    }

    /**
     * Avanza el <code>Turno</code> correspondiente a una Estación y la fase
     * donde se encuentra.
     *
     * @return <code>true</code> o <code>false</code> dependiendo de si se pudo
     * realizar el avance.
     * @param turno tiene la información del <code>Turno</code>
     * @param fase tiene la información de la actividad en la que se encuentra
     * el <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Fase
     * @throws <code>HermodException</code>
     */
    /*
	boolean avanzarTurno(Turno turno, Fase fase, Hashtable parametros) throws HermodException {
		Hashtable ht = new Hashtable();
		boolean rta = false;
		ServiceLocator sl = ServiceLocator.getInstancia();
		String proceso;
		long idProceso;
		try{
			proceso=this.getProcesoByIdFase(fase.getID());
			idProceso= Long.parseLong(proceso);
		}catch(HermodException e){
			throw new HermodException(e.getMessage(), e);
		}catch(NumberFormatException e1){
			throw new HermodException(e1.getMessage(), e1);
		}

		//Validacion sobre nota devolutiva para la fase ingresada
		if (factory
			.getVariablesOperativasDAO()
			.validarNotaDevolutiva(
				fase.getID(),
				(String) parametros.get("RESULT"),
				idProceso)) {

			int numNotas = turno.getNotas().size();
			if (turno.getNotas().size() == 0) {
				throw new HermodException(HermodException.TURNO_SIN_NOTA);
			} else {
				Nota nota = (Nota) turno.getNotas().get(numNotas - 1);
				if (nota == null) {
					throw new HermodException(HermodException.TURNO_SIN_NOTA);
				} else {
					if (nota.getTiponota() == null) {
						throw new HermodException(HermodException.TURNO_SIN_NOTA);
					}
					/*
					else if (!nota.getTiponota().isDevolutiva()){
						throw new HermodException(HermodException.TURNO_SIN_NOTA_DEVOLUTIVA);
					}
				}
			}

		}

		try {
			SASInterface sas = (SASInterface) sl.getServicio("org.auriga.sas.SAS");
			ht.put(Processor.ITEM_KEY, turno.getIdWorkflow());
			ht.put(SASKeys.SAS_ID_SOLICITUD, fase.getID());
			ht.put(Processor.ACTIVITY, fase.getID());

			List registros = sas.seleccionarRegistros("EN EJECUCION", ht);
			Iterator i = registros.iterator();

			while (i.hasNext()) {
				RegistroEjecucion re = (RegistroEjecucion) i.next();

				if (re.getFase().equals("EN EJECUCION")) {
					EjecucionSolicitud es = re.getEjecucionSolicitud();
					long ejecucionId = es.getIdEjecucion();
					parametros.put(SASKeys.SAS_ID_EJECUCION, String.valueOf(ejecucionId));
					parametros.put(Processor.ITEM_KEY, turno.getIdWorkflow());
					parametros.put(Processor.ACTIVITY, fase.getID());
					parametros.put(SASKeys.SAS_ID_SOLICITUD, fase.getID());
					sas.ejecutarSolicitud(parametros);

					return true;
				} else {
					return false;
				}
			}

			return rta;
		} catch (SASException e) {
			Log.getInstance().error(this.getClass(),e);
			Log.getInstance().error(this.getClass(),e.getMessage(), e);
			throw new HermodException(e.getMessage(), e);
		} catch (ServiceLocatorException e) {
			Log.getInstance().error(this.getClass(),e);
			Log.getInstance().error(this.getClass(),e.getMessage(), e);
			throw new HermodException(e.getMessage(), e);
		}
	}*/
    /**
     * Obtiene la lista de procesos asociados con un Rol
     *
     * @return una lista de objetos </code>Proceso</code>
     * @param id_rol el identificador del rol del cual se buscan los procesos
     * asociados.
     * @see gov.sir.core.negocio.modelo.Proceso
     * @throws <code>HermodException</code>
     */
    public List getProcesos(String id_rol) throws HermodException {
        try {
            return factory.getProcesosDAO().getProcesos(id_rol);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_PROCESOS_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de fases (actividades) asociadas con un
     * <code>Proceso</code> y un Rol.
     *
     * @param rol el rol del cual se quiere obtener la <code>Fase</code>.
     * @param proceso el <code>proceso</code> del cual se quiere obtener la
     * <code>Fase</code>
     * @return la lista de fases asociadas al <code>Proceso</code> y la
     * estacion.
     * @see gov.sir.core.negocio.modelo.Fase
     * @see gov.sir.core.negocio.modelo.Proceso
     * @throws <code>HermodException</code>
     */
    public List getFases(Rol rol, Proceso proceso) throws HermodException {
        try {
            return factory.getFasesDAO().getFases(rol, proceso);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_FASES_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de turnos asociados a una estacion, un rol, un
     * <code>Usuario</code> una <code>Fase</code> y un <code>Proceso</code>.
     *
     * @param estacion Estacion sobre la cual se buscan los turnos.
     * @param rol <code>Rol</code> sobre el cual se buscan los turnos.
     * @param usuario <code>Usuario</code> sobre el cual se buscan los turnos.
     * @param proceso <code>Proceso</code> sobre el cual se buscan los turnos.
     * @param fase <code>Fase</code> sobre la cual se buscan los turnos.
     * @param circulo <code>Circulo</code> donde pertenecen los turnos.
     * @return una lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.Proceso
     * @see gov.sir.core.negocio.modelo.Fase
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws <code>HermodException</code>
     */
    public List getTurnos(Estacion estacion, Rol rol, Usuario usuario, Proceso proceso, Fase fase, Circulo circulo)
            throws HermodException {
        try {
            return factory.getTurnosDAO().getTurnos(estacion, rol, usuario, proceso, fase, circulo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TURNOS_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    /**
     * Obtiene la lista de turnos asociados a un rol, un
     * <code>Usuario</code> una <code>Fase</code> y un <code>Proceso</code>.
     * Filtra los turnos radicados por REL
     * @param estacion Estacion sobre la cual se buscan los turnos.
     * @param rol <code>Rol</code> sobre el cual se buscan los turnos.
     * @param usuario <code>Usuario</code> sobre el cual se buscan los turnos.
     * @param proceso <code>Proceso</code> sobre el cual se buscan los turnos.
     * @param fase <code>Fase</code> sobre la cual se buscan los turnos.
     * @param circulo <code>Circulo</code> donde pertenecen los turnos.
     * @return una lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.Proceso
     * @see gov.sir.core.negocio.modelo.Fase
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws <code>HermodException</code>
     */
    public List getTurnosPMY(Estacion estacion, Rol rol, Usuario usuario, Proceso proceso, Fase fase, Circulo circulo)
            throws HermodException {
        try {
            return factory.getTurnosDAO().getTurnosPMY(estacion, rol, usuario, proceso, fase, circulo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TURNOS_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    public Boolean isTurnoREL(String idWorkflow) throws HermodException{
        try{
            return factory.getTurnosDAO().isTurnoREL(idWorkflow);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al validar Turno REL", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    public String currentStateNotaNotificada(String idWorkflow) throws HermodException{
        try{
            return factory.getTurnosDAO().currentStateNotaNotificada(idWorkflow);
        } catch(DAOException e) {
            HermodException he = new HermodException("Error al consultar el estado actual de Nota Devolutiva Notificada", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    public String getFechaTurnoJuzgado(String idWorkflow) throws HermodException{
        try{
            return factory.getTurnosDAO().getFechaTurnoJuzgado(idWorkflow);
        } catch(DAOException e) {
            HermodException he = new HermodException("Error al consultar la fecha de Nota Devolutiva Notificada", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    public Boolean isTurnoDevuelto(String idWorkflow) throws HermodException{
        try{
            return factory.getTurnosDAO().isTurnoDevuelto(idWorkflow);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al validar Turno Devuelto", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    public int getNumNotasInformativas(Turno turno, String tipoNota) throws HermodException{
        try{
            return factory.getTurnosDAO().getNumNotasInformativas(turno, tipoNota);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener limite de reasignaciones", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
     public int getReasignacion() throws HermodException{
        try{
            return factory.getTurnosDAO().getReasignacion();
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener limite de reasignaciones", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
     
    public String getValorLookupCodes(String tipo, String idCodigo) throws HermodException{
        try{
            return factory.getLookupDAO().getValorLookupCodes(tipo, idCodigo);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener parametro", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    public String getStringByQuery(String sql) throws HermodException{
        try{
            return factory.getTurnosDAO().getStringByQuery(sql);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener parametro", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    public List getOPLookupCodesByTipo(String tipo) throws HermodException{
        try{
            return factory.getLookupDAO().getOPLookupCodesByTipo(tipo);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener parametro", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    public String getCopiaImp(String idCirculo) throws HermodException {
        try{
            return factory.getTurnosDAO().getCopiaImp(idCirculo);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener parametro", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    public int getLimiteReasignacion(Turno turno) throws HermodException{
        try{
            return factory.getTurnosDAO().getLimiteReasignacion(turno);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener limite de reasignaciones", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    public String getEstacionFromRevisor(Turno turno)throws HermodException{
        try{
            return factory.getTurnosDAO().getEstacionFromRevisor(turno);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener la estacion a avanzar desde Revisor", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    public String getEstacionFromRecursosNota(Turno turno) throws HermodException{
        try{
            return factory.getTurnosDAO().getEstacionFromRecursosNota(turno);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener la estacion a avanzar desde Revisor", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    public boolean isEstacionActivaCalificador(String estacionId) throws HermodException{
        try{
            return factory.getTurnosDAO().isEstacionActivaCalificador(estacionId);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener la estacion a avanzar desde Revisor", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    public List isMatriculaNotificacionDev(String idMatricula) throws HermodException{
        try{
            return factory.getTurnosDAO().isMatriculaNotificacionDev(idMatricula);
        } catch (DAOException e){
            HermodException he = new HermodException("Error al obtener matricula bloqueada", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    public int getNotasNecesarias(Turno turno) throws HermodException{
        try{
            return factory.getTurnosDAO().getNotasNecesarias(turno);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener las notas informativas a agregar", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    public List getControlMatriculaTurno(String turnoID) throws HermodException{
        try{
            return factory.getTurnosDAO().getControlMatriculaTurno(turnoID);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error el control de matriculas", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }    
    /**
     * Obtiene la lista de procesos que son iniciados por un rol.
     *
     * @param id_rol el identificador del rol del cual se buscan los procesos
     * que son iniciados.
     * @return una lista de objetos <code>Proceso</code>
     * @see gov.sir.core.negocio.modelo.Proceso
     * @throws <code>HermodException</code>
     */
    public List getProcesosQueInicia(String id_rol) throws HermodException {
        try {
            return factory.getProcesosDAO().getProcesosQueInicia(id_rol);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_PROCESOS_QUE_INICIA_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de fases (actividades) siguientes a partir de un
     * <code>Turno</code>
     *
     * @param turno el objeto <code>Turno</code> actual
     * @return la lista de fases siguientes respecto al <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Fase
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>HermodException</code>
     */
    public List getFasesSiguientes(Turno turno) throws HermodException {
        try {
            return factory.getFasesDAO().getFasesSiguientes(turno);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_FASES_SIGUIENTES_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de respuestas siguientes respecto a un
     * <code>Turno</code>
     *
     * @param turno el objeto <code>Turno</code> actual
     * @return la lista de respuestas (String) siguientes
     * @throws <code>HermodException</code>
     */
    public List getRespuestasSiguientes(Turno turno) throws HermodException {
        try {
            return factory.getFasesDAO().getRespuestasSiguientes(turno);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_RTAS_SIGUIENTES_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Valida el pago y completa la información correspondiente
     *
     * @param pago representa la información inicial del <code>Pago</code>
     * @return un objeto <code>Pago</code> con los detalles del mismo
     * @see gov.sir.core.negocio.modelo.Pago
     * @throws <code>HermodException</code>
     */
    public Pago validarPago(Pago pago) throws HermodException {
        try {
            return HermodDAOFactory.getFactory().getPagosDAO().validarPago(pago);
        } catch (DAOException e) {
            HermodException he = new HermodException(DAOException.VALIDACION_PAGO_INCORRECTA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Ejecuta el pago y obtiene la información para generar el recibo
     *
     * @param pago representa la información para realizar el <code>Pago</code>
     * @return un objeto <code>Turno</code> con los datos correspondientes
     * @see gov.sir.core.negocio.modelo.Pago
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     * @throws <code>HermodException</code>
     */
    public Turno procesarPago(Pago pago, String estacion) throws HermodException {
        try {
            return factory.getPagosDAO().procesarPago(pago, estacion, null, null, null, false);
        } catch (DAOException e) {
            HermodException he = new HermodException(DAOException.PAGO_INCORRECTO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el valor a liquidar de acuerdo al tipo de <code>Proceso</code>
     *
     * @param liquidacion representa la información de la
     * <code>Liquidacion</code> actual
     * @return un objeto <code>Liquidacion</code> con los detalles del valor
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @throws <code>HermodException</code>
     */
    public Liquidacion liquidar(Liquidacion liquidacion) throws HermodException {
        try {
            Liquidador liquidador = Liquidador.getLiquidador(liquidacion);

            if (liquidacion != null
                    && liquidacion.getSolicitud() != null
                    && liquidacion.getSolicitud().getCirculo() != null
                    && liquidacion.getSolicitud().getCirculo().getIdCirculo() != null) {
                liquidacion.setCirculo(liquidacion.getSolicitud().getCirculo().getIdCirculo());
            } else {
                Log.getInstance().debug(this.getClass(), "(LIQUIDACION)NO SE PUDO GUARDAR EL IDENTIFICADOR DEL CIRCULO " + (liquidacion != null ? liquidacion.getIdSolicitud() : ""));
            }

            return liquidador.liquidar(liquidacion);
        } catch (LiquidarException e) {//UN DAO ESTA FALLANDO PERO NO SE SI SEA POR ESO; puede que este bloqueada 
            HermodException he = new HermodException(LiquidarException.LIQUIDACION_NO_REALIZADA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        } catch (LiquidadorException e) {
            HermodException he = new HermodException(LiquidadorException.LIQUIDADOR_NO_OBTENIDO + e, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de objetos de tipo <code>Banco</code>
     *
     * @return una lista de objetos <code>Banco</code>
     * @see gov.sir.core.negocio.modelo.Banco
     * @throws <code>HermodException</code>
     */
    public List getBancos() throws HermodException {
        try {
            return factory.getBancosDAO().getBancos();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_BANCOS_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el objeto <code>Banco</code>
     *
     * @return <code>Banco</code>
     * @see gov.sir.core.negocio.modelo.Banco
     * @throws <code>HermodException</code>
     */
    public Banco getBancoByID(String idBanco) throws HermodException {
        try {
            return factory.getBancosDAO().getBancoByID(idBanco);
        } catch (DAOException e) {
            HermodException he = new HermodException("No fue posible obtener el Banco ", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene los <code>OPLookupCodes</code> asociados con el
     * <code>OPLookupTypse</code> pasado como parámetro.
     *
     * @param tipo LookupCode que representa el tipo de <code>Proceso</code>
     * @return Lista de objetos de tipo <code>OPLookupCodes</code>
     * @see gov.sir.core.negocio.modelo.Pago
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     * @throws <code>HermodException</code>
     */
    public List getOPLookupCodes(String tipo) throws HermodException {
        try {
            return factory.getLookupDAO().getLookupCodes(tipo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_LOOKUP_CODES_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el valor del rango de aceptación para un pago
     *
     * @param tipo String que representa el tipo de <code>Proceso</code>
     * @return un valor que representa el rango de aceptación.
     * @see gov.sir.core.negocio.modelo.Pago
     * @throws <code>HermodException</code>
     */
    public double getRangoAceptacionPago(String tipo) throws HermodException {
        String codigo = "DESVIACION";
        String valor = "";
        double rango = 0;

        if (tipo != null) {
            try {
                valor = factory.getLookupDAO().getValor(tipo, codigo);

                if ((valor != null) && !valor.equals("")) {
                    rango = Double.parseDouble(valor);
                }
            } catch (DAOException e) {
                HermodException he
                        = new HermodException(HermodException.RANGO_ACEPTACION_NO_OBTENIDO, e);
                ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
                Log.getInstance().error(this.getClass(), he.getMessage(), e);
                throw he;
            }
        }

        return rango;
    }

    /**
     * Obtiene la lista de tipos de certificados
     *
     * @return una lista de objetos <code>TipoCertificado</code>
     * @see gov.sir.core.negocio.modelo.TipoCertificado
     * @throws <code>HermodException</code>
     */
    public List getTiposCertificado() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTiposCertificado();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_BANCOS_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de tipos de certificados asociados con el proceso de
     * certificados individuales.
     *
     * @return una lista de objetos <code>TipoCertificado</code>
     * @see gov.sir.core.negocio.modelo.TipoCertificado
     * @throws <code>HermodException</code>
     */
    public List getTiposCertificadosIndividuales() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTiposCertificadosIndividuales();
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_CERTIFICADOS_INDIVIDUALES_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de tipos de documentos.
     *
     * @return una lista de objetos <code>TipoDocumento</code>
     * @see gov.sir.core.negocio.modelo.TipoDocumento
     * @throws <code>HermodException</code>
     */
    public List getTiposDocumento() throws HermodException {
        try {
            return factory.getLookupDAO().getTiposDocumento();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_BANCOS_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    /**
     * Obtiene la lista de tipos de documento para personas juridicas.
     *
     * @return una lista de tipos de documento para personas juridicas.
     * @throws <code>HermodException</code>
     */
    public List getTipoDocJuridico() throws HermodException {
        try {
            return factory.getLookupDAO().getTipoDocJuridico();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPOIDJURDICA_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    /**
     * Obtiene la lista de tipos de documento para personas naturales.
     *
     * @return una lista de tipos de documento para personas naturales.
     * @throws <code>HermodException</code>
     */
    public List getTipoDocNatural() throws HermodException {
        try {
            return factory.getLookupDAO().getTipoDocNatural();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPOSIDNATURAL_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    /**
     * Obtiene la lista de modalidades.
     *
     * @return una lista de modalidades
     * @throws <code>HermodException</code>
     */
    public List getModalidad() throws HermodException {
        try {
            return factory.getLookupDAO().getModalidad();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.MODALIDAD_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    /**
     * Obtiene la lista de determinaciones del inmueble.
     *
     * @return una lista de determinaciones del inmueble
     * @throws <code>HermodException</code>
     */
    public List getDeterminacionInm() throws HermodException {
        try {
            return factory.getLookupDAO().getDeterminacionInm();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.DETERMINACION_INMUEBLE_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    /**
     * Obtiene la lista de tipos de persona.
     *
     * @return una lista de tipos de persona
     * @throws <code>HermodException</code>
     */
    public List getTipoPersona() throws HermodException {
        try {
            return factory.getLookupDAO().getTipoPersona();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_PERSONA_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    /**
     * Obtiene la lista de tipos de sexo.
     *
     * @return una lista de tipos de sexo
     * @throws <code>HermodException</code>
     */
    public List getTipoSexo() throws HermodException {
        try {
            return factory.getLookupDAO().getTipoSexo();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_SEXO_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de causales de restitución.
     *
     * @return una lista de objetos <code>OPLookupCodes</code>con los causales
     * de reimpresión.
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     * @throws <code>HermodException</code>
     */
    public List getCausalesReimpresion() throws HermodException {
        try {
            return factory.getLookupDAO().getLookupCodes(COPLookupTypes.CAUSAL_REIMPRESION);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException("No fue posible obtener la lista de causales de impresión", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna la lista de validaciones que se deben hacer dependiendo del tipo
     * de solicitud
     *
     * @param solicitud Solicitud sobre la que se van a obtener las
     * validaciones.
     * @return Lista de objetos de tipo <code>Validacion</code>
     * @see gov.sir.core.negocio.modelo.Validacion
     * @throws <code>HermodException</code>
     */
    public List getValidacionesSolicitud(Solicitud solicitud) throws HermodException {
        List rta = new ArrayList();

        try {
            if (solicitud instanceof SolicitudCertificado) {
                List liquidaciones = solicitud.getLiquidaciones();

                //Si la lista de liquidaciones de la Solicitud es vacía
                //se genera una excepción.
                if (liquidaciones.size() == 0) {
                    throw new HermodException("La solicitud no tiene liquidaciones");
                }

                TipoCertificado tipo
                        = ((LiquidacionTurnoCertificado) liquidaciones.get(0)).getTipoCertificado();

                TipoCertificadoPk oid = new TipoCertificadoPk();
                oid.idTipoCertificado = tipo.getIdTipoCertificado();
                rta = factory.getVariablesOperativasDAO().getValidacionesByTipoCertificado(oid);
            } else if (solicitud instanceof SolicitudRegistro) {
                Validacion val = new Validacion();
                val.setIdValidacion(CValidacion.FOLIO_ANULADO);
                rta.add(val);

                //Bug:0003787
                //Validacion val2 = new Validacion();
                //val2.setIdValidacion(CValidacion.FOLIO_CERRADO);
                //rta.add(val2);
                Validacion val3 = new Validacion();
                val3.setIdValidacion(CValidacion.FOLIO_INCONSISTENTE);
                rta.add(val3);

            } else if (solicitud instanceof SolicitudCertificadoMasivo) {
                List liquidaciones = solicitud.getLiquidaciones();

                //Si la lista de liquidaciones de la Solicitud es vacía
                //se genera una excepción.
                if (liquidaciones.size() == 0) {
                    throw new HermodException("La solicitud no tiene liquidaciones");
                }

                TipoCertificado tipo
                        = ((LiquidacionTurnoCertificadoMasivo) liquidaciones.get(0)).getTipoCertificado();

                TipoCertificadoPk oid = new TipoCertificadoPk();
                oid.idTipoCertificado = tipo.getIdTipoCertificado();
                rta = factory.getVariablesOperativasDAO().getValidacionesByTipoCertificado(oid);

                /**
                 *
                 * @author: Fernando Padilla Velez, 02/07/2015
                 * @change: 1195.IMPRESION.CERTIFICADOS.EXENTOS.PDF, Se comenta
                 * este codigo para que no realice validacion sobre folios de
                 * mayor extension.
                 */
                //Validacion val = new Validacion();
                //val.setIdValidacion(CValidacion.FOLIO_MAYOR_EXT);
                //rta.add(val);
            }

        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_DE_VALIDACIONES_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        } catch (HermodException e) {
            ExceptionPrinter ep = new ExceptionPrinter(e.fillInStackTrace());
            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw e;
        }

        return rta;
    }

    /**
     * Retorna la lista de validaciones para registro
     *
     * @return Lista de objetos de tipo <code>Validacion</code>
     * @see gov.sir.core.negocio.modelo.Validacion
     * @throws <code>HermodException</code>
     */
    public List getValidacionesRegistro() throws HermodException {
        List rta = new ArrayList();

        Validacion val = new Validacion();
        val.setIdValidacion(CValidacion.FOLIO_ANULADO);
        rta.add(val);

        //Se quita la validacion de Folio Cerrado para liquidación Bug#4183
        /*Validacion val2 = new Validacion();
		val2.setIdValidacion(CValidacion.FOLIO_CERRADO);
		rta.add(val2);*/
        Validacion val3 = new Validacion();
        val3.setIdValidacion(CValidacion.FOLIO_INCONSISTENTE);
        rta.add(val3);

        Validacion val4 = new Validacion();
        val4.setIdValidacion(CValidacion.FOLIO_TRASLADADO);
        rta.add(val4);

        return rta;
    }

    /**
     * Retorna la lista de validaciones para registro de Turno Manual
     *
     * @return Lista de objetos de tipo <code>Validacion</code>
     * @see gov.sir.core.negocio.modelo.Validacion
     * @throws <code>HermodException</code>
     */
    public List getValidacionesRegistroTurnoManual() throws HermodException {
        List rta = new ArrayList();

        Validacion val = new Validacion();
        val.setIdValidacion(CValidacion.FOLIO_ANULADO);
        rta.add(val);

        Validacion val3 = new Validacion();
        val3.setIdValidacion(CValidacion.FOLIO_INCONSISTENTE);
        rta.add(val3);

        return rta;
    }

    /**
     * Obtiene la lista de tipos de alcances geograficos
     *
     * @return una lista de objetos de tipo <code>AlcanceGeografico</code>
     * @see gov.sir.core.negocio.modelo.AlcanceGeografico
     * @throws <code>HermodException</code>
     */
    public List getTiposAlcanceGeografico() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTiposAlcanceGeografico();
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_ALCANCES_GEOGRAFICOS_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        } catch (Throwable e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_ALCANCES_GEOGRAFICOS_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de tipos de consultas.
     *
     * @return una lista de objetos <code>TipoConsulta</code>
     * @see gov.sir.core.negocio.modelo.TipoConsulta
     * @throws <code>HermodException</code>
     */
    public List getTiposConsulta() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTiposConsulta();
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_TIPOS_CONSULTA_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega una búsqueda a una Solicitud de consultas
     *
     * @param solConsulta la <code>SolicitudConsulta</code> a la que se va a
     * asociar la <code>Busqueda</code>
     * @param busc la <code>Busqueda</code> que va a ser asociada a la
     * <code>SolicitudConsulta</code>
     * @return la <code>Solicitud</code> con la <code>Busqueda</code> asociada.
     * @see gov.sir.core.negocio.modelo.SolicitudConsulta
     * @see gov.sir.core.negocio.modelo.Busqueda
     * @throws <code>HermodException</code>
     */
    public Solicitud addBusquedaToSolicitudConsulta(SolicitudConsulta solConsulta, Busqueda busc)
            throws HermodException {
        try {
            return factory.getSolicitudesDAO().agregarBusqueda(solConsulta, busc);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException("No fue posible agregar la búsqueda a la solicitud de consulta.", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega una Solicitud de folios a una solicitud de Consultas
     *
     * @param solConsulta la <code>SolicitudConsulta</code> a la que se va a
     * asociar la <code>SolicitudFolio</code>
     * @param solFolio la <code>SolicitudFolio</code> que va a ser asociada a la
     * <code>SolicitudConsulta</code>
     * @return la <code>SolicitudConsulta</code> con la
     * <code>SolicitudFolio</code> asociada.
     * @see gov.sir.core.negocio.modelo.SolicitudConsulta
     * @see gov.sir.core.negocio.modelo.SolicitudFolio
     * @throws <code>HermodException</code>
     */
    public SolicitudConsulta addFolioToSolicitudConsulta(
            SolicitudConsulta solConsulta,
            SolicitudFolio solFolio)
            throws HermodException {
        try {
            return factory.getSolicitudesDAO().agregarSolicitudFolio(solConsulta, solFolio);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException("No fue posible agregar el folio a la solicitud de consulta.", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Modifica una búsqueda en una solicitud de consultas.
     *
     * @param solConsulta La <code>SolicitudConsulta</code> en la que se va a
     * modificar la <code>Busqueda</code>
     * @param b la <code>Busqueda</code> con los nuevos valores.
     * @return la <code>SolicitudConsulta</code> con la <code>Busqueda</code>
     * modificada.
     * @see gov.sir.core.negocio.modelo.SolicitudConsulta
     * @see gov.sir.core.negocio.modelo.Busqueda
     * @throws <code>HermodException</code>
     */
    public SolicitudConsulta updateBusquedaInSolicitudConsulta(
            SolicitudConsulta solConsulta,
            Busqueda b)
            throws HermodException {
        try {
            return factory.getSolicitudesDAO().modificarBusquedaConsulta(solConsulta, b);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException("No fue posible agregar la búsqueda a la solicitud de consulta.", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de tipos de recepción de petición
     *
     * @return una lista de objetos <code>TipoRececpcionPeticion</code>
     * @see gov.sir.core.negocio.modelo.TipoRecepcionPeticion
     * @throws <code>HermodException</code>
     */
    public List getTiposRecepcionPeticion() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTiposRecepcionPeticion();
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_TIPOS_RECEPCION_PETICION_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de tipos de Subtipos de Atencion
     *
     * @return una lista de objetos <code>SubtipoAtencion</code> con todos sus
     * atributos.
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     * @throws <code>HermodException</code>
     */
    public List getSubTiposAtencion() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getSubTiposAtencion();
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_SUB_TIPOS_ATENCION_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de tipos de Subtipos de Atencion completa (listado de
     * subtipos de solicitud y tipos acto)
     *
     * @return una lista de objetos <code>SubtipoAtencion</code> con todos sus
     * atributos.
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     * @throws <code>HermodException</code>
     */
    public List getSubTiposAtencionCompleto() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getSubTiposAtencionCompleto();
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_SUB_TIPOS_ATENCION_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de calificadores del circulo dado
     *
     * @return una lista de objetos <code>Usuario</code> con todos sus
     * atributos.
     * @see gov.sir.core.negocio.modelo.Usuario
     * @throws <code>Throwable</code>
     */
    public List getCalificadoresSubtipoAtencion(Circulo cir, SubtipoAtencion sub) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getCalificadoresSubtipoAtencion(cir, sub);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_SUB_TIPOS_ATENCION_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega el orden de un usuario en el subtipo solicitud
     *
     * @return true o false dependiendo del resultado de la adicion.
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     * @throws <code>Throwable</code>
     */
    public boolean addOrdenSubtipoAtencion(SubtipoAtencion sub, Usuario usu, String orden, Circulo cir) throws HermodException {
        try {
            UsuarioSubtipoAtencionPk usuSubId = factory.getVariablesOperativasDAO().addOrdenSubtipoAtencion(sub, usu, orden, cir);

            //Se realizó la inserción del tipo de acto.
            if (usuSubId != null) {
                return true;
            }

            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_ACTO_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Remueve el orden de un usuario en el subtipo solicitud
     *
     * @return true o false dependiendo del resultado de la operacion.
     * @see gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion
     * @throws <code>Throwable</code>
     */
    public boolean removeOrdenSubtipoAtencion(UsuarioSubtipoAtencion usuSubtipoAtencion, Circulo cir) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().removeOrdenSubtipoAtencion(usuSubtipoAtencion, cir);

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_ACTO_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de tipos de Subtipos de Solicitud
     *
     * @return una lista de objetos <code>SubtipoSolicitud</code> con todos sus
     * atributos.
     * @see gov.sir.core.negocio.modelo.SubtipoSolicitud
     * @throws <code>HermodException</code>
     */
    public List getSubTiposSolicitud() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getSubTiposSolicitud();
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_SUB_TIPOS_SOLICITUD_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de Tipos de Actos.
     *
     * @return una lista de objetos de tipo <code>TipoActo</code> con todos sus
     * atributos.
     * @see gov.sir.core.negocio.modelo.TipoActo
     * @throws <code>HermodException</code>
     */
    public List getTiposActo() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTiposActo();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TIPOS_ACTO_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene un objeto <code>TipoActo</code> a partir del idTipoActo
     * Solicitado
     *
     * @return Objeto <code>TipoActo</code>
     * @param TipoActo.ID tid
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.TipoActo
     */
    public TipoActo getTipoActo(TipoActoPk tid) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTipoActo(tid);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_ACTO_NO_OBTENIDO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /*
        * @autor          : JATENCIA 
        * @mantis        : 0015082 
        * @Requerimiento : 027_589_Acto_liquidación_copias 
        * @descripcion   : Se declara el metodo llamado atraves de las interfacez
        * 
     */
    /**
     * Obtiene la lista de Tipos de Actos.
     *
     * @return una lista de objetos de tipo <code>TipoActo</code> con todos sus
     * atributos.
     * @see gov.sir.core.negocio.modelo.TipoActo
     * @throws <code>HermodException</code>
     */
    public List getTiposActoDos() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTiposActoDos();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TIPOS_ACTO_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene un objeto <code>TipoActo</code> a partir del idTipoActo
     * Solicitado
     *
     * @return Objeto <code>TipoActo</code>
     * @param TipoActo.ID tid
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.TipoActo
     */
    public TipoActo getTipoActoDos(TipoActoPk tid) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTipoActoDos(tid);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_ACTO_NO_OBTENIDO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /* - Fin del bloque - */
    /**
     * Obtiene la lista de tipos de Impuestos.
     *
     * @return una lista de objetos <code>TipoImpuesto</code> con todos sus
     * atributos.
     * @see gov.sir.core.negocio.modelo.TipoImpuesto
     * @throws <code>HermodException</code>
     */
    public List getTiposImpuesto() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTiposImpuesto();
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_TIPOS_IMPUESTOS_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @author Fernando Padilla Velez Obtiene la lista de tipos de Impuestos por
     * circulos.
     * @return una lista de objetos <code>TipoImpuestoCirculo</code> con todos
     * sus atributos.
     * @see gov.sir.core.negocio.modelo.TipoImpuestoCirculo
     * @throws <code>HermodException</code>
     */
    public List getTiposImpuestoCirculo() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTiposImpuestoCirculo();
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_TIPOS_IMPUESTOS_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de tipos de Accion Notarial
     *
     * @return una lista de objetos <code>AccionNotarial</code> con todos sus
     * atributos.
     * @see gov.sir.core.negocio.modelo.AccionNotarial
     * @throws <code>HermodException</code>
     */
    public List getTiposAccionNotarial() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTiposAccionNotarial();
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_TIPOS_ACCION_NOTARIAL_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Crea una solicitud persistente, dependiendo del tipo de
     * <code>Solicitud</code> recibida como parámetro.
     *
     * @param sol <code>Solicitud</code> que se va a hacer persistente.
     * @return <code>Solicitud</code> persistente.
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudCertificado
     * @see gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo
     * @see gov.sir.core.negocio.modelo.SolicitudConsulta
     * @see gov.sir.core.negocio.modelo.SolicitudCorreccion
     * @see gov.sir.core.negocio.modelo.SolicitudDevolucion
     * @see gov.sir.core.negocio.modelo.SolicitudFotocopia
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     * @see gov.sir.core.negocio.modelo.SolicitudRepartoNotarial
     * @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
     * @throws <code>HermodException</code>
     */
    public Solicitud crearSolicitud(Solicitud sol) throws HermodException {
        try {
            //Solicitud de Consulta
            if (sol instanceof SolicitudConsulta) {
                return factory.getSolicitudesDAO().crearSolicitudConsulta(sol);
            }

            //Solicitud de Corrección
            if (sol instanceof SolicitudCorreccion) {
                return factory.getSolicitudesDAO().crearSolicitudCorreccion(sol);
            }

            //Solicitud de Reparto Notarial
            if (sol instanceof SolicitudRepartoNotarial) {
                return factory.getSolicitudesDAO().crearSolicitudRepartoNotarial(sol);
            }

            //Solicitud de Restitución Reparto Notarial
            if (sol instanceof SolicitudRestitucionReparto) {
                return factory.getSolicitudesDAO().crearSolicitudRestitucionReparto(sol);
            }

            //Solicitud de Fotocopias
            if (sol instanceof SolicitudFotocopia) {
                return factory.getSolicitudesDAO().crearSolicitudFotocopia(sol);
            }

            // Solicitud de Registro
            if (sol instanceof SolicitudRegistro) {
                SolicitudPk srID = factory.getSolicitudesDAO().crearSolicitudRegistro((SolicitudRegistro) sol);
                return (SolicitudRegistro) factory.getSolicitudesDAO().getSolicitudByID(srID);
            }

            // Solicitud de Certificado
            if (sol instanceof SolicitudCertificado) {
                SolicitudPk srID = factory.getSolicitudesDAO().crearSolicitudCertificado((SolicitudCertificado) sol);
                return (SolicitudCertificado) factory.getSolicitudesDAO().getSolicitudByID(srID);
            }

            // Solicitud de Antiguo Sistema
            if (sol instanceof SolicitudAntiguoSistema) {
                SolicitudPk solicitudId = factory.getSolicitudesDAO().crearSolicitudAntiguoSistema((SolicitudAntiguoSistema) sol);
                return factory.getSolicitudesDAO().getSolicitudByID(solicitudId);
            }

        } catch (DAOException e) {
            HermodException he
                    = new HermodException("No fue posible crear la solicitud " + e.getMessage(), e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }

        return null;
    }

    /**
     * Agrega un tipo de AlcanceGeografico en el sistema.
     *
     * @param alcance El tipo de alcance geografico que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.AlcanceGeografico
     * @throws <code>HermodException</code>
     */
    public boolean addTipoAlcanceGeografico(AlcanceGeografico alcance) throws HermodException {
        try {
            AlcanceGeograficoPk ag = factory.getVariablesOperativasDAO().addAlcanceGeografico(alcance);

            //Inserción Satisfactoria.
            if (ag != null) {
                return true;
            }

            return false;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ALCANCE_GEOGRAFICO_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega una AccionNotarial en el sistema.
     *
     * @param an La <code>AccionNotarial</code> que se va a agregar.
     * @param usuario que va adicionar la accion notarial
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.AccionNotarial
     * @throws <code>HermodException</code>
     */
    public boolean addAccionNotarial(AccionNotarial an, Usuario usuario) throws HermodException {
        try {
            AccionNotarialPk anId = factory.getVariablesOperativasDAO().addAccionNotarial(an, usuario);

            //Inserción Satisfactoria.
            if (anId != null) {
                return true;

                //Fallo en la inserción.
            }

            return false;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ACCION_NOTARIAL_NO_CREADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Editar una AccionNotarial en el sistema.
     *
     * @param an La <code>AccionNotarial</code> que se va a editar.
     * @param usuario que va editar la accion notarial
     * @return true o false dependiendo del resultado de la actualización.
     * @see gov.sir.core.negocio.modelo.AccionNotarial
     * @throws <code>Throwable</code>
     */
    public boolean editarAccionNotarial(AccionNotarial an, Usuario usuario) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().editarAccionNotarial(an, usuario);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ACCION_NOTARIAL_NO_CREADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un <code>Banco</code> en el sistema.
     *
     * @param banco El <code>Banco</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.Banco
     * @throws <code>HermodException</code>
     */
    public boolean addBanco(Banco banco) throws HermodException {
        try {
            BancoPk bancoId = factory.getBancosDAO().addBanco(banco);

            //Inserción Satisfactoria.
            if (bancoId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.BANCO_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista con los Causales de Restitución existentes en el
     * sistema.
     *
     * @return Lista con objetos de tipo <code>CausalRestitucion</code>
     * @see gov.sir.core.negocio.modelo.CausalRestitucion</code>
     * @throws <code>HermodException</code>
     */
    public List getCausalesRestitucion() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getCausalesRestitucion();
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_CAUSALES_RESTITUCION_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un Causal de Restitución en el sistema.
     *
     * @param causal El <code>CausalRestitucion</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.CausalRestitucion
     * @throws <code>HermodException</code>
     */
    public boolean addCausalRestitucion(CausalRestitucion causal) throws HermodException {
        try {
            CausalRestitucionPk causalId
                    = factory.getVariablesOperativasDAO().addCausalRestitucion(causal);

            //Se realizó la inserción.
            if (causalId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CAUSAL_RESTITUCION_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar una <code>CausalRestitucion</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param valNota la <code>CausalRestitucion</code> que va a ser eliminada.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.CausalRestitucion
     */
    public boolean eliminarCausalRestitucion(CausalRestitucion causal) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarCausalRestitucion(causal);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.ELIMINACION_CAUSALES_RESTITUCION_NO_EFECTUADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un LookupCode en el sistema.
     *
     * @param luc El <code>OPLookupCodes</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     * @throws <code>HermodException</code>
     */
    public boolean addOPLookupCode(OPLookupCodes luc) throws HermodException {
        try {
            OPLookupCodesPk lookId = factory.getLookupDAO().addLookupCode(luc);

            //Se realizó la inserción.
            if (lookId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LOOK_UP_CODE_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Edita un LookupCode en el sistema.
     *
     * @param datoAEditar El <code>OPLookupCodes</code> que se va a editar.
     * @param dato El <code>OPLookupCodes</code> con los nuevos datos.
     * @return true o false dependiendo del resultado de la edicion.
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     * @throws <code>HermodException</code>
     */
    public boolean updateOPLookupCode(OPLookupCodes datoAEditar, OPLookupCodes dato) throws HermodException {
        try {
            OPLookupCodesPk lookId = factory.getLookupDAO().updateLookupCode(datoAEditar, dato);

            //Se realizó la inserción.
            if (lookId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LOOK_UP_CODE_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista con los LookupTypes existentes en el sistema.
     *
     * @return Lista con objetos de tipo <code>OPLookupTypes</code>
     * @see gov.sir.core.negocio.modelo.OPLookupTypes</code>
     * @throws <code>HermodException</code>
     */
    public List getOPLookupTypes() throws HermodException {
        try {
            return factory.getLookupDAO().getLookupTypes();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_LOOKUP_TYPES_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un LookupType en el sistema.
     *
     * @param lut El <code>OPLookupTypes</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     * @throws <code>HermodException</code>
     */
    public boolean addOPLookupType(OPLookupTypes lut) throws HermodException {
        try {
            OPLookupTypesPk lookId = factory.getLookupDAO().addLookupType(lut);

            if (lookId != null) {
                return true;
            }

            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LOOK_UP_TYPE_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Edita un LookupType en el sistema.
     *
     * @param datoAEditar El <code>OPLookupTypes</code> que se va a editar.
     * @param dato El <code>OPLookupTypes</code> que tiene los nuevos datos.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     * @throws <code>Throwable</code>
     */
    public boolean updateOPLookupType(OPLookupTypes datoAEditar, OPLookupTypes dato) throws HermodException {
        try {
            OPLookupTypesPk lookId = factory.getLookupDAO().updateLookupType(datoAEditar, dato);

            if (lookId != null) {
                return true;
            }

            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LOOK_UP_TYPE_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un tipo de acto en el sistema.
     *
     * @param tac El <code>TipoActo</code> que se va a agregar.
     * @param Usuario usuario que va adicionar el tipo de acto
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.TipoActo
     * @throws <code>HermodException</code>
     */
    public boolean addTipoActo(TipoActo tac, Usuario usuario) throws HermodException {
        try {
            TipoActoPk tacId = factory.getVariablesOperativasDAO().addTipoActo(tac, usuario);

            //Se realizó la inserción del tipo de acto.
            if (tacId != null) {
                return true;
            }

            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_ACTO_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Modifica un tipo de acto en el sistema.
     *
     * @param tac El <code>TipoActo</code> que se va a agregar.
     * @param usuario que va a modificar el tipo de acto
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.TipoActo
     * @throws <code>HermodException</code>
     */
    public boolean updateTipoActo(TipoActo tac, Usuario usuario) throws HermodException {
        try {
            TipoActoPk tacId = factory.getVariablesOperativasDAO().updateTipoActo(tac, usuario);

            //Se realizó la inserción del tipo de acto.
            if (tacId != null) {
                return true;
            }

            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_ACTO_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista con los Tipos de Cálculos existentes en el sistema.
     *
     * @return Lista con objetos de tipo <code>TipoCalculo</code>
     * @see gov.sir.core.negocio.modelo.TipoCalculo</code>
     * @throws <code>HermodException</code>
     */
    public List getTiposCalculo() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTiposCalculo();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TIPOS_ACTO_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un tipo de cálculo en el sistema.
     *
     * @param tac El <code>TipoCalculo</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.TipoCalculo
     * @throws <code>HermodException</code>
     */
    public boolean addTipoCalculo(TipoCalculo tc) throws HermodException {
        try {
            TipoCalculoPk tcId = factory.getVariablesOperativasDAO().addTipoCalculo(tc);

            //Se realizó la inserción del tipo de cálculo.
            if (tcId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_CALCULO_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista con los Tipos de Fotocopias existentes en el sistema.
     *
     * @return Lista con objetos de tipo <code>TipoFotocopia</code>
     * @see gov.sir.core.negocio.modelo.TipoFotocopia</code>
     * @throws <code>HermodException</code>
     */
    public List getTiposFotocopia() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTiposFotocopia();
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_TIPOS_FOTOCOPIA_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    /**
     * Obtiene la la ultima notificacion de una nota devoluctiva
     *¨*/
    public List getNotaDevNotificada (String turnoWF) throws HermodException{
        try {
            return factory.getTurnosDAO().getNotaDevNotificada(turnoWF);
        } catch (DAOException e) {
            HermodException he = new HermodException("No fue posible obtener la Nota Devolutiva Notificada", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    public int diasHabiles(String idCirculo, String fecha) throws HermodException{
            try {
            return factory.getTurnosDAO().diasHabiles(idCirculo, fecha);
        } catch (DAOException e) {
            HermodException he = new HermodException("No fue posible obtener los dias habiles", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    /**
     * Agrega una notificación;
     * @see HermodServiceInterface#notificarNotaDevolutiva(gov.sir.core.negocio.modelo.NotificacionNota) 
     */
    public void notificarNotaDevolutiva(NotificacionNota notify) throws HermodException{
        try{
            factory.getTurnosDAO().notificarNotaDevolutiva(notify);
        } catch(DAOException e){
            HermodException he = new HermodException("NO FUE POSIBLE AGREGAR LA NOTIFICACION ", e);
            System.out.println("ERROR: " + he.getMessage());
        }
    }
    
    /**
     * Agrega una notificación;
     * @see HermodServiceInterface#updateRecurso(gov.sir.core.negocio.modelo.Recurso, java.lang.String) 
     */
        public void updateRecurso(Recurso recurso, String turnoWF) throws HermodException{
        try{
            factory.getTurnosDAO().updateRecurso(recurso, turnoWF);
        } catch(DAOException e){
            HermodException he = new HermodException("NO FUE POSIBLE ACTUALIZAR EL RECURSO ", e);
            System.out.println("ERROR: " + he.getMessage());
        }
    }
    
    /**
     * Agrega una notificación;
     * @see HermodServiceInterface#deleteRecurso(java.lang.String, java.lang.String)  
     */
    public void deleteRecurso(String idRecurso, String turnoWF) throws HermodException{
        try{
            factory.getTurnosDAO().deleteRecurso(idRecurso, turnoWF);
        } catch(DAOException e){
            HermodException he = new HermodException("NO FUE POSIBLE ELIMINAR EL RECURSO ", e);
            System.out.println("ERROR: " + he.getMessage());
        }
    }
    
    /**
     * Agrega una notificación;
     * @see HermodServiceInterface#executeDMLFromSQL(java.lang.String)  
     */
    public void executeDMLFromSQL(String sql) throws HermodException{
        try{
            factory.getTurnosDAO().executeDMLFromSQL(sql);
        } catch(DAOException e){
            HermodException he = new HermodException("NO FUE POSIBLE REALIZAR LA ACCION ", e);
            System.out.println("ERROR: " + he.getMessage());
        }
    }
    
    /**
         * Agrega una lista de notas informativas
     * @see HermodServiceInterface#addNotasInformativas(java.util.List) 
     */
    public void addNotasInformativas(List notasInformativas)
            throws HermodException {
        try {
            factory.getTurnosDAO().addNotasInformativas(notasInformativas);

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_NOTAS_INFORMATIVAS_NO_AGREGADAS, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    public List getValorLookupCodesByTipo(String tipo) throws HermodException{
        try{
            return factory.getLookupDAO().getValorLookupCodesByTipo(tipo);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener parametro", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    
       /**
         * Actualiza el estado de la nota devolutiva notificada
     * @see HermodServiceInterface#setStateNotaNotificada(java.lang.String, java.lang.String) 
     */
    public void setStateNotaNotificada(String state, String idWorkflow)
            throws HermodException {
        try {
            factory.getTurnosDAO().setStateNotaNotificada(state, idWorkflow);

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.STATUS_NO_ALMACENADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
     /**
         * Agrega el estado de un turno REL
     * @see HermodServiceInterface#setStatusREL(java.lang.String, java.lang.String, java.lang.String) 
     */
   public void setStatusREL(String status, String url, String idWorkflow)
            throws HermodException {
        try {
            factory.getTurnosDAO().setStatusREL(status, url, idWorkflow);

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.STATUS_NO_ALMACENADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
        /**
         * Agrega una registro en el control de matriculas.
     * @see HermodServiceInterface#addCtrlMatricula(java.lang.String, java.lang.String, java.lang.String, java.lang.String) 
     */
    public void addCtrlMatricula(String idMatricula, String accion, String rol, String idWorkflow)
            throws HermodException {
        try {
            factory.getTurnosDAO().addCtrlMatricula(idMatricula, accion, rol, idWorkflow);

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CONTROL_MATRICULA_NO_AGREGADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
            /**
         * Agrega una registro en el control de reasignación.
     * @see HermodServiceInterface#addCtrlReasignacion(gov.sir.core.negocio.modelo.Turno, java.lang.String, java.lang.String) 
     */
    public void addCtrlReasignacion(Turno turno, String usuarioOrigen, String usuarioDestino)
            throws HermodException {
        try {
            factory.getTurnosDAO().addCtrlReasignacion(turno, usuarioOrigen, usuarioDestino);

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CONTROL_REASIGNACION_NO_AGREGADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    /**
     * Agrega un tipo de fotocopia en el sistema.
     *
     * @param fot El <code>TipoFotocopia</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.TipoFotocopia
     * @throws <code>HermodException</code>
     */
    public boolean addTipoFotocopia(TipoFotocopia fot) throws HermodException {
        try {
            TipoFotocopiaPk tipoFotId = factory.getVariablesOperativasDAO().addTipoFotocopia(fot);

            //Se realizó la inserción del tipo de fotocopia.
            if (tipoFotId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_FOTOCOPIA_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un tipo de impuesto en el sistema.
     *
     * @param timp El <code>TipoImpuesto</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.TipoImpuesto
     * @throws <code>HermodException</code>
     */
    public boolean addTipoImpuesto(TipoImpuesto timp) throws HermodException {
        try {
            TipoImpuestoPk tcId = factory.getVariablesOperativasDAO().addTipoImpuesto(timp);

            //Se realizó la inserción del tipo de impusto.
            if (tcId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_IMPUESTO_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un subtipo de atención en el sistema.
     *
     * @param sat El <code>SubtipoAtencion</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     * @throws <code>HermodException</code>
     */
    public boolean addSubTipoAtencion(SubtipoAtencion sat) throws HermodException {
        try {
            SubtipoAtencionPk satId = factory.getVariablesOperativasDAO().addSubtipoAtencion(sat);

            //Se realizó la inserción del subtipo de atención.
            if (satId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SUBTIPO_ATENCION_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Edita un subtipo de atención en el sistema.
     *
     * @param sat El <code>SubtipoAtencion</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     * @throws <code>HermodException</code>
     */
    public boolean updateSubTipoAtencion(SubtipoAtencion sat) throws HermodException {
        try {
            SubtipoAtencionPk satId = factory.getVariablesOperativasDAO().updateSubtipoAtencion(sat);

            //Se realizó la inserción del subtipo de atención.
            if (satId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SUBTIPO_ATENCION_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un subtipo de solicitud en el sistema.
     *
     * @param sts El <code>SubtipoSolicitud</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.SubtipoSolicitud
     * @throws <code>HermodException</code>
     */
    public boolean addSubTipoSolicitud(SubtipoSolicitud sts) throws HermodException {
        try {
            SubtipoSolicitudPk satId = factory.getVariablesOperativasDAO().addSubtipoSolicitud(sts);

            //Se realizó la inserción del subtipo de solicitud.
            if (satId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SUBTIPO_SOLICITUD_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Edita un subtipo de solicitud en el sistema.
     *
     * @param sts El <code>SubtipoSolicitud</code> que se va a editar.
     * @return true o false dependiendo del resultado de la edicion.
     * @see gov.sir.core.negocio.modelo.SubtipoSolicitud
     * @throws <code>HermodException</code>
     */
    public boolean updateSubTipoSolicitud(SubtipoSolicitud sts) throws HermodException {
        try {
            SubtipoSolicitudPk satId = factory.getVariablesOperativasDAO().updateSubtipoSolicitud(sts);

            //Se realizó la inserción del subtipo de solicitud.
            if (satId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SUBTIPO_SOLICITUD_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un tipo de certificado en el sistema.
     *
     * @param tcert El <code>TipoCertificado</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.TipoCertificado
     * @throws <code>HermodException</code>
     */
    public boolean addTipoCertificado(TipoCertificado tcert) throws HermodException {
        try {
            TipoCertificadoPk tcId = factory.getVariablesOperativasDAO().addTipoCertificado(tcert);

            // Se realizó la inserción del tipo de certificado.
            if (tcId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_CERTIFICADO_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un tipo de consulta en el sistema.
     *
     * @param tcons El <code>TipoConsulta</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.TipoConsulta
     * @throws <code>HermodException</code>
     */
    public boolean addTipoConsulta(TipoConsulta tcons) throws HermodException {
        try {
            TipoConsultaPk tcId = factory.getVariablesOperativasDAO().addTipoConsulta(tcons);

            //Se realizó la inserción del tipo de consulta
            if (tcId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_CONSULTA_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un tipo de recepcion peticion en el sistema.
     *
     * @param trp El <code>TipoRecepcionPeticion</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.TipoRecepcionPeticion
     * @throws <code>HermodException</code>
     */
    public boolean addTipoRecepcionPeticion(TipoRecepcionPeticion trp) throws HermodException {
        try {
            TipoRecepcionPeticionPk tcId
                    = factory.getVariablesOperativasDAO().addTipoRecepcionPeticion(trp);

            //Se realizó la inserción del tipo de recepción petición.
            if (tcId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.TIPO_RECEPCION_PETICION_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista con los Tipos de Tarifas existentes en el sistema.
     *
     * @return Lista con objetos de tipo <code>TipoTarifa</code>
     * @see gov.sir.core.negocio.modelo.TipoTarifa</code>
     * @throws <code>HermodException</code>
     */
    public List getTiposTarifas() throws HermodException {
        try {
            return factory.getTarifasDAO().getTiposTarifas();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TIPOS_TARIFA_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un Tipo de Tarifa en el sistema.
     *
     * @param tar El <code>TipoTarifa</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.TipoTarifa
     * @throws <code>HermodException</code>
     */
    public boolean addTipoTarifa(Tarifa tar) throws HermodException {
        try {
            TipoTarifaPk tcId = factory.getTarifasDAO().addTipoTarifa(tar);

            //Se realizó la inserción del tipo de tarifa.
            if (tcId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_TARIFA_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista con las Tarifas existentes en el sistema.
     *
     * @return Lista con objetos de tipo <code>Tarifa</code>
     * @see gov.sir.core.negocio.modelo.Tarifa</code>
     * @throws <code>HermodException</code>
     */
    public List getTarifas(TipoTarifa tipoTar) throws HermodException {
        try {
            return factory.getTarifasDAO().getTarifas(tipoTar);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TARIFAS_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega una Tarifa en el sistema.
     *
     * @param tar La <code>Tarifa</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.Tarifa
     * @throws <code>HermodException</code>
     */
    public boolean addTarifa(Tarifa tar) throws HermodException {
        try {
            TarifaPk tcId = factory.getTarifasDAO().addTarifa(tar);

            //Se realizó la inserción de la tarifa
            if (tcId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TARIFA_NO_CREADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna una lista con los subtipos de atención y los círculos de los
     * usuarios recibidos como parámetros.
     *
     * @param logins Lista que contiene los usuarios que van a ser buscados.
     * @param Circulo <code>Circulo</code> al que pertenecen los usuarios.
     * @return Lista que contiene los usuarios recibidos como parámetros con sus
     * respectivos subtipos de atencion y sus circulos.
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     * @throws <code>HermodException</code>
     */
    public List getSubtiposAtencionByUsuarios(List logins, Circulo circulo) throws HermodException {
        try {
            return factory.getRepartoTurnosDAO().getSubTipoAtencionByUsuario(logins, circulo);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_SUBTIPOS_ATENCION_USUARIO_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna una lista con los subtipos de atención y los círculos de los
     * usuarios recibidos como parámetros.
     *
     * @param logins Lista que contiene los usuarios que van a ser buscados.
     * @param circuloId identificador del circulo al que pertenecen los
     * usuarios.
     * @return Lista que contiene los usuarios recibidos como parámetros con sus
     * respectivos subtipos de atencion y sus circulos.
     */

    /*public List getSubtiposAtencionUsuarios(List logins, String circuloId)
	        throws HermodException {
	        try {
	                return factory.getVariablesOperativasDAO()
	                                          .getSubTipoAtencionUsuario(logins, circuloId);
	        } catch (DAOException e) {
	                HermodException he = new HermodException(HermodException.LISTA_SUBTIPOS_ATENCION_USUARIO_NO_OBTENIDA);
	                Log.getInstance().error(this.getClass(),he.getMessage(), e);
	                throw he;
	        }
	}*/
    /**
     * Agrega una <code>Nota</code> a un <code>Turno</code>
     *
     * @return el Identificador de la <code>Nota</code> creada
     * @param nota <code>Nota</code>con todos los atributos excepto su
     * identificador
     * @param tId Identificador del <code>Turno</code> al que se le asocia la <code>
     * Nota</code>
     * @see gov.sir.core.negocio.modelo.Nota
     * @throws <code>HermodException</code>
     *
     */
    public NotaPk addNotaToTurno(Nota nota, TurnoPk tId) throws HermodException {
        try {
            return factory.getTurnosDAO().addNotaToTurno(nota, tId);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NOTA_NO_CREADO, e);
            //ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Crea un <code>Testamento</code> persistente y lo asocia a la solicitud de
     * un turno de registro de documentos.
     *
     * @param Turno El turno <code>Turno</code> al que se va a asociar el
     * <code>Testamento</code>.
     * @param testamento El <code>Testamento</code> que va a asociarse a la
     * solicitud de registro de documentos.
     * @return resultado<code>boolean</code> el resultado de el ingreso de el
     * Testamento.
     * @see gov.sir.core.negocio.modelo.Testamento
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>Throwable</code>
     */
    public boolean addTestamentoToSolicitudRegistro(TurnoPk tid, Testamento testamento) throws HermodException {
        try {
            return factory.getTurnosDAO().addTestamentoToSolicitudRegistro(tid, testamento);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TESTAMENTO_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
       /**
     * Captura Datos de Calificador para registrador
     *
     * @param idTurno El turno <code>Turno</code> Workflow
     * @param Circulo El <code>Circulo</code> Su id
     * @param Activo Si paso por registrador en numero
     * @return Lista de <code>ReproduccionSellos</code> 
     * @see gov.sir.core.negocio.modelo.ReproduccionSellos
     * @throws <code>Throwable</code>
     */
    public List getListReproduccionSellos(String idTurno, String Circulo, int Activo)throws HermodException{
        try {
            return factory.getTurnosDAO().getListReproduccionSellos( idTurno,  Circulo,  Activo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.REPRODUCCIONNOCREADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    /**
     * Crea un <code>ReproduccionSellos</code> persistente 
     *
     * @param reproduccion El objeto <code>ReproduccionSellos</code> el cual se va a guardar
     * @return resultado<code>boolean</code> el resultado de el ingreso
     * @see gov.sir.core.negocio.modelo.ReproduccionSellos
     * @throws <code>Throwable</code>
     */
    public boolean CreateReproduccionSellosReg(ReproduccionSellos reproduccion) throws HermodException {
        try {
            return factory.getTurnosDAO().CreateReproduccionSellosReg(reproduccion);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.REPRODUCCIONNOCREADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Dado un identificador de turno retorna el turno con todos sus atributos y
     * jerarquia.
     *
     * @return el <code>Turno</code> con todos sus atributos.
     * @param tId El Identificador del <code>Turno</code>que se quiere recuperar
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public Turno getTurno(TurnoPk tId) throws HermodException {
        try {
            return factory.getTurnosDAO().getTurnoByID(tId);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNO_NO_OBTENIDO, e);
            //ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un <code>ProcesoReparto</code> al sistema.
     *
     * @return el <code>ProcesoReparto</code> persistente con su identificador.
     * @param prR <code>ProcesoReparto</code> con todos los atributos excepto el
     * identificador.
     * @see gov.sir.core.negocio.modelo.ProcesoReparto
     * @throws <code>HermodException</code>
     */
    public ProcesoReparto addProcesoReparto(ProcesoReparto prR) throws HermodException {
        try {
            return factory.getRepartoTurnosDAO().addProcesoReparto(prR);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.PROCESO_REPARTO_NO_CREADO, e);
            //ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna el último reparto asociado a un <code>Turno</code>
     *
     * @param turnoId el identificador del <code>turno del cual se va a obtener
     * el último reparto.
     * @return el último <code>Reparto</code> asociado con el <code>Turno,
     * con id recibido como parámetro.
     * @see gov.sir.core.negocio.modelo.Reparto
     * @throws <code>HermodException</code>
     */
    public Reparto getLastReparto(TurnoPk turnoId) throws HermodException {
        try {
            return factory.getRepartoTurnosDAO().getLastReparto(turnoId);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.REPARTO_NO_OBTENIDO, e);
            //ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Adiciona un <code>Folio a un <code>Turno</code>.
     *
     * @param matricula matricula asociada al folio.
     * @param tID identificador del <code>Turno</code>
     * @param user El <code>Usuario</code> que generó la
     * <code>SolicitudFolio</code>
     * @return el identificador de la solicitud folio que se adicionó al
     * <code>Turno</code>.
     * @see gov.sir.core.negocio.modelo.SolicitudFolio
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws HermodException
     */
    public SolicitudFolioPk addFolioToTurno(String matricula, TurnoPk tID, Usuario user)
            throws HermodException {
        try {
            Folio f = new Folio();
            f.setIdMatricula(matricula);

            SolicitudFolioPk resultado = factory.getSolicitudesDAO().addFolioToTurno(f, tID, user);

            return resultado;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.FOLIO_NO_AGREGADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Remueve la asociación de un <code>Folio a un <code>Turno</code>.
     *
     * @param matricula matricula asociada al <code>folio</code>.
     * @param tID Identificador del <code>Turno</code>de registro.
     * @return true o false dependiendo del resultado de la eliminacion.
     * @see gov.sir.core.negocio.modelo.SolicitudFolio
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws HermodException
     */
    public boolean removeFolioFromTurno(String matricula, TurnoPk tID) throws HermodException {
        try {
            Folio f = new Folio();
            f.setIdMatricula(matricula);

            return factory.getSolicitudesDAO().removeFolioFromTurno(f, tID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.FOLIO_NO_ELIMINADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Registra la matrícula que fue desasociada en los datos básicos del turno
     *
     * @param folio
     * @param tID
     * @return
     * @throws DAOException
     */
    public boolean registrarMatriculaEliminadaTurno(String matricula, TurnoPk tID) throws HermodException {
        try {
            Folio f = new Folio();
            f.setIdMatricula(matricula);

            return factory.getSolicitudesDAO().registrarMatriculaEliminadaTurno(f, tID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.FOLIO_NO_ELIMINADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el listado de Categorías a las que puede pertenecer una Notaría.
     *
     * @return Lista de Categorías a las que puede pertenecer una Notaría.
     * @see gov.sir.core.negocio.modelo.CategoriaNotaria
     * @throws Throwable
     */
    public List getCategoriasNotarias() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getCategoriasNotarias();
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.CONSULTA_CATEGORIAS_NOTARIAS_NO_EFECTUADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Remueve la asociación de un folio a un turno
     *
     * @param matricula matricula asociada al folio.
     * @param tID Identificador del turno de registro.
     * @return true o false dependiendo del resultado de la eliminacion.
     * @throws HermodException
     */
    public boolean addCambioMatriculaAuditoria(String folioViejo, String folionuevo, TurnoPk tID, Usuario user) throws HermodException {
        try {
            return factory.getSolicitudesDAO().addCambioMatriculaAuditoria(folioViejo, folionuevo, tID, user);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.FOLIO_NO_CAMBIADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * verifica que a el turno se le realizo un cambio de matricula
     *
     * @param tID Identificador del turno.
     * @return true o false dependiendo de si se hizo cambio al turno.
     * @throws HermodException
     * @author : Julio Alcázar Rivas Caso Mantis : 02359
     */
    public boolean getCambioMatriculaAuditoria(TurnoPk tID) throws HermodException {
        try {
            return factory.getSolicitudesDAO().getCambioMatriculaAuditoria(tID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNO_NO_ENCONTRADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna un mapa con las listas de los usuarios clasificados por subtipo
     * de atencion
     *
     * @param List Lista con los usuarios que deben clasificar según su subtipo
     * de atención.
     * @return Map Mapa que contiene las asociaciones usuarios, subtipos de
     * atención.
     * @throws HermodException
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public Map getUsuariosBySubtipoAtencion(List usuarios) throws HermodException {
        try {
            return factory.getRepartoTurnosDAO().getUsuariosBySubtipoAtencion(usuarios);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_SUB_TIPOS_ATENCION_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega una sucursal a un <code>Banco</code> existente en el sistema.
     *
     * @param suc <code>SucursalBanco</code> que va a ser agregada.
     * @return true o false dependiendo del resultado de la inserción.
     * @see gov.sir.core.negocio.modelo.Banco
     * @see gov.sir.core.negocio.modelo.SucursalBanco
     * @throws <code>HermodException</code>
     */
    public boolean addSucursalBanco(SucursalBanco suc) throws HermodException {
        try {
            SucursalBancoPk sucursalId = factory.getBancosDAO().addSucursal(suc);

            //Se realizó la inserción de la sucursal.
            if (sucursalId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SUCURSAL_NO_CREADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna una lista con las sucursales asociadas a un <code>Banco</code>
     *
     * @param idBanco Identificador del <code>Banco</code> del cual se van a
     * buscar las <code>SucursalesBanco</code>
     * @return Lista que contiene las <code>SucursalesBanco</code> asociadas con
     * el <code>Banco</code> pasado como parámetro.
     * @see gov.sir.core.negocio.modelo.SucursalBanco
     * @see gov.sir.core.negocio.modelo.Banco
     * @throws <code>HermodException</code>
     */
    public List getSucursalesBanco(BancoPk idBanco) throws HermodException {
        try {
            return factory.getBancosDAO().getSucursalesBanco(idBanco);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SUCURSALES_NO_OBTENIDAS, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna una lista con los tipos de Derechos Registrales existentes en el
     * sistema.
     *
     * @return Lista que contiene objetos de tipo <code>TipoDerechoReg</code>
     * existentes en el sistema.
     * @see gov.sir.core.negocio.modelo.TipoDerechoReg
     * @throws <code>HermodException</code>
     */
    public List getTipoDerechoRegistral() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTiposDerechosRegistrales();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SUCURSALES_NO_OBTENIDAS, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un tipo de Derecho Registral a la configuración del sistema.
     *
     * @param tipo <code>TipoDerechoReg</code> que va a ser agregado.
     * @return true o false dependiendo del resultado de la inserción.
     * @see gov.sir.core.negocio.modelo.TipoDerechoReg
     * @throws <code>HermodException</code>
     */
    public boolean addTipoDerechoRegistral(TipoDerechoReg tipo) throws HermodException {
        try {
            TipoDerechoRegPk tipoId = factory.getVariablesOperativasDAO().addTipoDerechoRegistral(tipo);

            //Se realizó la inserción del tipo de derecho registral.
            if (tipoId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SUCURSAL_NO_CREADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna una lista con las Categorías de Reparto Notarial existentes en el
     * sistema.
     *
     * @return Lista que contiene objetos de tipo <code>Categoria</code>
     * existentes en el sistema.
     * @see gov.sir.core.negocio.modelo.Categoria
     * @see gov.sir.core.negocio.modelo.RepartoNotarial
     * @throws <code>HermodException</code>
     */
    public List getCategoriasReparto(String orden) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getCategoriasReparto(orden);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SUCURSALES_NO_OBTENIDAS, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega una Categoría de Reparto Notarial a la configuración del sistema.
     *
     * @param categoria <code>Categoria</code> que va a ser agregada.
     * @param usuario que adiciona la categoria al reparto
     * @return true o false dependiendo del resultado de la inserción.
     * @see gov.sir.core.negocio.modelo.Categoria
     * @see gov.sir.core.negocio.modelo.RepartoNotarial
     * @throws <code>HermodException</code>
     */
    public boolean addCategoriaReparto(Categoria categoria, Usuario usuario) throws HermodException {
        try {
            CategoriaPk categoriaId
                    = factory.getVariablesOperativasDAO().addCategoriaReparto(categoria, usuario);

            //Se realizó la inserción de la Categoría de Reparto Notarial.
            if (categoriaId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SUCURSAL_NO_CREADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Edita una Categoría de Reparto Notarial a la configuración del sistema.
     *
     * @param categoria <code>Categoria</code> que va a ser editada.
     * @return true o false dependiendo del resultado de la edición.
     * @return usuario que va a modificar la categoria del reparto
     * @see gov.sir.core.negocio.modelo.Categoria
     * @see gov.sir.core.negocio.modelo.RepartoNotarial
     * @throws <code>HermodException</code>
     */
    public boolean updateCategoriaReparto(Categoria categoria, Usuario usuario) throws HermodException {
        try {
            CategoriaPk categoriaId
                    = factory.getVariablesOperativasDAO().updateCategoriaReparto(categoria, usuario);

            //Se realizó la inserción de la Categoría de Reparto Notarial.
            if (categoriaId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SUCURSAL_NO_CREADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna la categoría en la cual se debe clasificar una
     * <code>Minuta</code> de acuerdo con el valor o el número de unidades.
     *
     * @param minuta la <code>Minuta</code> que se va a clasificar.
     * @return la <code>Categoria</code> en la cual se debe clasificar la
     * <code>minuta</code>.
     * @see gov.sir.core.negocio.modelo.RepartoNotarial
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.SolicitudRepartoNotarial
     * @throws <code>HermodException</code>
     */
    public Categoria getCategoriaClasificacionMinuta(Minuta minuta) throws HermodException {
        try {
            Categoria categoria = factory.getRepartosDAO().getCategoriaClasificacionMinuta(minuta);

            //Se obtuvo la categoría de clasificación de la minuta.
            if (categoria != null) {
                return categoria;
            }
            return null;

        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.CLASIFICACION_MINUTA_NO_REALIZADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Crea una <code>Minuta</code> persistente y la asocia a una solicitud de
     * reparto notarial de minutas.
     *
     * @param solicitud La <code>Solicitud</code> a la que se va a asociar la
     * <code>minuta</code>.
     * @param minuta La <code>Minuta</code> que va a asociarse a la solicitud de
     * reparto.
     * @return solicitud <code>SolicitudRepartoNotarial</code> con su
     * <code>Minuta</code> persistente asociada.
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.SolicitudRepartoNotarial
     * @throws <code>HermodException</code>
     */
    public SolicitudRepartoNotarial addMinutaToSolicitudReparto(Solicitud solicitud, Minuta minuta)
            throws HermodException {
        try {
            return factory.getRepartosDAO().addMinutaToSolicitudReparto(solicitud, minuta);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.MINUTA_NO_AGREGADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna el número máximo de impresiones autorizadas para un rol dentro de
     * un proceso dado.
     *
     * @param rol El rol sobre el que se hace la consulta.
     * @param p El proceso sobre el que se hace la consulta.
     * @return El número Máximo de impresiones autorizadas para un rol dentro de
     * un proceso dado.
     * @see gov.sir.core.negocio.modelo.Proceso
     * @throws <code>HermodException</code>
     */
    public int getNumeroMaximoImpresiones(Rol rol, Proceso p) throws HermodException {
        int numero = 0;

        try {
            numero = factory.getVariablesOperativasDAO().getMaximoImpresionesByRol(rol, p);

            return numero;
        } //Si se llega a esta excepción es porque fue arrojada dentro de la
        //consulta, al no encontrarse un valor para la asociación rol, proceso
        //dada.
        catch (DAOException e) {
            return 0;
        }
    }

    /**
     * Anula la minuta recibida como parámetro.
     *
     * @param min La minuta que va a ser anulada
     * @param usuario El usuario que anula la minuta
     * @return true o false dependiendo del resultado de la anulación de la
     * minuta.
     * @see gov.sir.core.negocio.modelo.Minuta
     * @throws <code>HermodException</code>
     */
    public boolean anularMinuta(Minuta min, Usuario usuario) throws HermodException {
        boolean result = false;

        try {
            result = factory.getRepartosDAO().anularMinutaRepartoNotarial(min, usuario);

            return result;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.MINUTA_NO_ANULADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Lista todos las tipos de notas existentes en el sistema.
     *
     * @return Lista con objetos <code>TipoNota</code> existentes en el sistema.
     * @see gov.sir.core.negocio.modelo.TipoNota
     * @throws <code>HermodException</code>
     */
    public List getTiposNotas() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTiposNota();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TIPOS_NOTA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Permite la modificación de una minuta de reparto notarial.
     *
     * @param min la minuta que se va a modificar.
     * @param generarAuditoria flag que indica si se debe generar auditoría de
     * la modificación.
     * @param usuario Usuario que realiza la modificaicion.
     * @return la minuta con las modificaciones persistentes.
     * @see gov.sir.core.negocio.modelo.Minuta
     * @throws <code>HermodException</code>
     */
    public Minuta modificarMinuta(Minuta min, boolean generarAuditoria, Usuario usuario) throws HermodException {
        try {
            return factory.getRepartosDAO().modificarMinuta(min, generarAuditoria, usuario);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.MINUTA_NO_MODIFICADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Lista todas las minutas que no tienen asignado un reparto notarial y que
     * están asociadas con una <code>Vereda</code> dada.
     *
     * @param vereda identificador de una <code>Vereda</code>
     * @return lista con todas las minutas asociadas a una <code>Vereda</code>
     * que no han sido repartidas.
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.Vereda
     * @throws <code>HermodException</code>
     *
     */
    public List getMinutasNoAsignadasByVereda(VeredaPk vereda) throws HermodException {
        try {
            VeredaPk veredaId = new VeredaPk();

            return factory.getRepartosDAO().getMinutasNoAsignadasByVereda(veredaId);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_MINUTAS_BY_VEREDA_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    /**
     * Guarda el Documento Pago al cual se le va a hacer la correccion
     *
     * @param idDocumento Id de documento Pago
     * @return <code>true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @throws <code>HermodException</code>
     */
    public boolean guardardocumentopagoantesdecorreccion(String idDocumento, String iduser)
            throws HermodException {
        try {
            return  factory.getPagosDAO().guardardocumentopagoantesdecorreccion(idDocumento, iduser);
        } catch (DAOException e) {
            HermodException he = new HermodException("ALMACENAR DOCUMENTO PAGO PARA CORRECCION NO EXITOSO", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    /**
     * Actualiza el Documento Pago al cual se le hizo la correccion
     *
     * @param idDocumento Id de documento Pago
     * @return <code>true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @throws <code>HermodException</code>
     */
    public boolean actualizarEstadoDocumento(DocumentoPago Documento)
            throws HermodException {
        try {
            return  factory.getPagosDAO().actualizarEstadoDocumento(Documento);
        } catch (DAOException e) {
            HermodException he = new HermodException("ACTUALIZAR DOCUMENTO PAGO PARA CORRECCION NO EXITOSO", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    /**
     * Agrega una Oficina Origen a una <code>Categoria</code>
     *
     * @param cat El identificador de la <code>Categoria</code> en la que se va
     * a adicionar la <code> OficinaOrigen </code>
     * @param oficina La <code> OficinaOrigen </code> que se va a asociar a la
     * <code>Categoria</code>
     * @param true o false dependiendo del resultado de la operación.
     * @see gov.sir.core.negocio.modelo.Categoria
     * @see gov.sir.core.negocio.modelo.OficinaOrigen
     * @throws <code>HermodException</code>
     */
    public boolean addOficinaOrigenToCategoria(CategoriaPk cat, OficinaOrigen oficina)
            throws HermodException {
        try {
            CategoriaPk categoriaId
                    = factory.getVariablesOperativasDAO().addOficinaOrigenToCategoria(cat, oficina);

            if (categoriaId != null) {
                return true;
            }

            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.OFICINA_NO_ASOCIADA_CATEGORIA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Actualiza la información de una <code>Categoria</code> existente.
     *
     * @param cat El identificador de la <code>Categoria</code> que se va a
     * modificar.
     * @param nuevosDatos la información modificada de la <code>Categoria</code>
     * @return true o false dependiendo del resultado de la operación.
     * @see gov.sir.core.negocio.modelo.Categoria
     * @throws <code>HermodException</code>
     */
    public boolean updateCategoria(CategoriaPk cat, Categoria nuevosDatos) throws HermodException {
        try {
            CategoriaPk categoriaId
                    = factory.getVariablesOperativasDAO().updateCategoria(cat, nuevosDatos);

            //Se realizó la actualización de la Categoría
            if (categoriaId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CATEGORIA_NO_MODIFICADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Lista las tipos de nota para el <code>Proceso</code> recibido
     *
     * @param proceso Proceso del que se deben obtener las notas.
     * @param proceso <code>Proceso</code> del cual se desean obtener los tipos
     * de notas.
     * @return Lista de objetos <code>TipoNota</code> que están asociados con el
     * <code>Proceso</code> recibido como parámetro.
     * @see gov.sir.core.negocio.modelo.Proceso
     * @see gov.sir.core.negocio.modelo.TipoNota
     * @throws <code>HermodException</code>
     *
     */
    public List getTiposNotasProceso(ProcesoPk proceso) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTiposNotaProceso(proceso);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TIPOS_NOTA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Lista las tipos de nota para el <code>Proceso</code> recibido
     *
     * @param proceso Proceso del que se deben obtener las notas.
     * @param proceso <code>Proceso</code> del cual se desean obtener los tipos
     * de notas.
     * @return Lista de objetos <code>TipoNota</code> que están asociados con el
     * <code>Proceso</code> recibido como parámetro.
     * @see gov.sir.core.negocio.modelo.Proceso
     * @see gov.sir.core.negocio.modelo.TipoNota
     * @throws <code>HermodException</code>
     *
     */
    public List getTiposNotaProcesoByTpnaDevolutiva(ProcesoPk proceso, boolean devolutiva) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTiposNotaProcesoByTpnaDevolutiva(proceso, devolutiva);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TIPOS_NOTA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Dado un identificador de <code>Turno</code> retorna el <code>Turno</code>
     * con todos sus atributos y jerarquia
     *
     * @return el <code>Turno</code> con todos sus atributos.
     * @param Identificador del <code>Turno</code> a recuperar
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>HermodException</code>
     */
    public Turno getTurnobyWF(String turnoIdWf) throws HermodException {
        try {
            Turno turno = factory.getTurnosDAO().getTurnoByWFId(turnoIdWf);
            return turno;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNO_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Dado un identificador de solicitud retorna el <code>DocumentoPago</code>
     * con todos sus atributos y jerarquia
     *
     * @return el <code>DocumentoPago</code> con todos sus atributos.
     * @param Identificador de la solicitud asociada al documento de pago a
     * recuperar
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @throws <code>HermodException</code>
     */
    public List getDocumentoPagoBySolicitud(String solicitud) throws HermodException {
        try {
            List docPago = factory.getPagosDAO().getDocumentoPagoBySolicitud(solicitud);
            return docPago;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.DATOS_PAGO_NO_OBTENIDOS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Dado un identificador de <code>DocumentoPago</code> retorna el
     * <code>DocPagoConsignacion</code> con todos sus atributos y jerarquia
     *
     * @return el <code>DocPagoConsignacion</code> con todos sus atributos.
     * @param Identificador del <code>DocumentoPago</code> a recuperar
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @throws <code>Throwable</code>
     */
    public DocPagoConsignacion getDocPagoConsignacion(DocumentoPago docPago) throws HermodException {
        try {
            DocPagoConsignacion docPagoCons = factory.getPagosDAO().getDocPagoConsignacion(docPago);
            return docPagoCons;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.DATOS_PAGO_NO_OBTENIDOS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Dado un identificador de <code>DocumentoPago</code> retorna el
     * <code>DocPagoCheque</code> con todos sus atributos y jerarquia
     *
     * @return el <code>DocPagoCheque</code> con todos sus atributos.
     * @param Identificador del <code>DocumentoPago</code> a recuperar
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @throws <code>Throwable</code>
     */
    public DocPagoCheque getDocPagoCheque(DocumentoPago docPago) throws HermodException {
        try {
            DocPagoCheque docPagoCheque = factory.getPagosDAO().getDocPagoCheque(docPago);
            return docPagoCheque;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.DATOS_PAGO_NO_OBTENIDOS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Dado un identificador de la consignacion retorna el
     * <code>DocPagoConsignacion</code> con todos sus atributos y jerarquia
     *
     * @return el <code>DocPagoConsignacion</code> con todos sus atributos.
     * @param Identificador de la consignacion a recuperar
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @throws <code>Throwable</code>
     */
    public DocPagoConsignacion getDocPagoConsignacion(String noConsignacion) throws HermodException {
        try {
            DocPagoConsignacion docPagoCons = factory.getPagosDAO().getDocPagoConsignacion(noConsignacion);
            return docPagoCons;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.DATOS_CONSIGNACION_NO_OBTENIDOS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Dado un identificador del cheque retorna el <code>DocPagoCheque</code>
     * con todos sus atributos y jerarquia
     *
     * @return el <code>DocPagoCheque</code> con todos sus atributos.
     * @param Identificador del cheque a recuperar
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @throws <code>Throwable</code>
     */
    public DocPagoCheque getDocPagoCheque(String noCheque) throws HermodException {
        try {
            DocPagoCheque docPagoCheque = factory.getPagosDAO().getDocPagoCheque(noCheque);
            return docPagoCheque;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.DATOS_CONSIGNACION_NO_OBTENIDOS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Dado un <code>DocumentoPago</code> lo crea en la base de datos con todos
     * sus atributos
     *
     * @return el <code>DocumentoPago</code> con todos sus atributos.
     * @param <code>DocumentoPago</code> a crear
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @throws <code>Throwable</code>
     */
    public DocumentoPago crearDocumentoPago(DocumentoPago dp) throws HermodException {
        try {
            DocumentoPago docPago = factory.getPagosDAO().crearDocumentoPago(dp);
            return docPago;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CREACION_DOCUMENTO_PAGO_NO_EFECTUADA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene una estación recibo dado su identificador.
     *
     * @param oid Identificador de la <code>EstacionRecibo</code>
     * @return <code>EstacionRecibo </code> con todos sus atributos.
     * @throws HermodException
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     * @throws <code>HermodException</code>
     */
    public EstacionRecibo getEstacionRecibo(EstacionReciboPk oid) throws HermodException {
        try {
            return factory.getRecibosDAO().getEstacionRecibo(oid);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ESTACION_RECIBO_NO_OBTENIDO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene una estación recibo dado su identificador.
     *
     * @param oid Identificador de la <code>EstacionRecibo</code>
     * @return <code>EstacionRecibo </code> con todos sus atributos.
     * @throws HermodException
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     * @throws <code>HermodException</code>
     */
    public EstacionRecibo getEstacionRecibo(EstacionReciboPk oid, long numeroProceso) throws HermodException {
        try {
            return factory.getRecibosDAO().getEstacionRecibo(oid, numeroProceso);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ESTACION_RECIBO_NO_OBTENIDO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see HermodServiceInterface#setEstacionRecibo(Circulo.CirculoPk,
     * EstacionRecibo, gov.sir.core.negocio.modelo.Usuario)
     */
    public void setEstacionRecibo(CirculoPk circuloID, EstacionRecibo eRecibo, Usuario user)
            throws HermodException {
        try {
            factory.getRecibosDAO().setEstacionRecibo(circuloID, eRecibo, user);

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ESTACION_RECIBO_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Resetea el último número del recibo de la estacion. Recibe los nueno
     * último número del recibos.
     *
     * @param oid Identificador de la <code>EstacionRecibo</code> cuyo
     * secuencial va a ser reseteado.
     * @param ultimoNumeroActualizado Nuevo valor para el último número de la
     * <code>EstacionRecibo</code>.
     * @return true o false dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    public boolean resetUltimoNumeroEstacionRecibo(EstacionReciboPk oid, long ultimoNumeroActualizado)
            throws HermodException {
        try {
            return factory.getRecibosDAO().resetUltimoNumeroEstacionRecibo(oid, ultimoNumeroActualizado);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.ESTACION_RECIBO_NO_RESET_ULT_NUM, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el siguiente número de recibo según la secuencia configurada
     *
     * @param oid Identificador de la <code>EstacionRecibo</code>
     * @return el siguiente número de la secuencia de recibos para la
     * <code>EstacionRecibo</code>
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     * @throws <code>HermodException</code>
     */
    public long getNextNumeroRecibo(EstacionReciboPk oid, Usuario user, long idProceso) throws HermodException {
        try {
            return factory.getRecibosDAO().getNextNumeroRecibo(oid, user, idProceso);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ESTACION_RECIBO_NO_NEXT, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene una minuta persistente, dado el identificador del turno de
     * workflow en el cual fue creada.
     *
     * @param turnoIdWf Identificador del <code> Turno</code> asociado a la
     * creación de la  <code>Minuta</code>
     * @return <code>Minuta</code> persistente creada en el <code>Turno</code>
     * con id dado.
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>HermodException</code>
     */
    public Minuta getMinutaByTurnoWF(String turnoIdWf) throws HermodException {
        try {
            Minuta minuta = factory.getRepartosDAO().getMinutaByWFId(turnoIdWf);

            return minuta;
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.MINUTA_ASOCIADA_TURNO_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene una minuta persistente, dado el identificador del turno de
     * workflow en el cual fue creada.
     *
     * @param turnoIdWf Identificador del <code> Turno</code> asociado a la
     * creación de la  <code>Minuta</code>
     * @return <code>Minuta</code> persistente creada en el <code>Turno</code>
     * con id dado.
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>HermodException</code>
     */
    public List getMinutasByTurnoWF(String turnoIdWf) throws HermodException {
        try {
            List minutas = factory.getRepartosDAO().getMinutasByWFId(turnoIdWf);
            return minutas;
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.MINUTA_ASOCIADA_TURNO_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el valor a liquidar para un acto de acuerdo al tipo de acto y la
     * cuantia
     *
     * @param acto objeto <code>Acto</code> con la información ingresada
     * @return un objeto <code>Acto</code> con los detalles del valor
     * @see gov.sir.core.negocio.modelo.Acto
     */
    public Acto getLiquidacionActo(Acto acto) throws HermodException {
        try {
            return factory.getLiquidacionesDAO().getLiquidacionActo(acto);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LIQUIDACION_ACTO_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    /**
     * Elimina los actos de una solicitud.
     * @param idSolicitud
     * @throws HermodException 
     */
    public void eliminarActos(String idSolicitud) throws HermodException {
        try{
            factory.getTurnosDAO().eliminarActos(idSolicitud);
        } catch (DAOException e){
            HermodException he = new HermodException(HermodException.ACTOS_NO_ELIMINADOS, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    /**
     * Valida la cuantia de un acto con respecto a lo que se edita.
     *
     * @param acto objeto <code>Acto</code> con la información ingresada
     * @param i <code>int</code> posicion del acto
     * @return true o false dependiendo de la validacion.
     * @see gov.sir.core.negocio.modelo.Acto
     */
    public boolean validacionActo(Acto acto, int i) throws HermodException {
        try {
            return factory.getLiquidacionesDAO().validacionActo(acto, i);

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LIQUIDACION_ACTO_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene una lista de las estaciones recibo del sistema.
     *
     * @return una lista de objetos <code>EstacionRecibo</code>
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     * @throws <code>HermodException</code>
     */
    public List getEstacionesRecibo() throws HermodException {
        try {
            return factory.getRecibosDAO().getEstacionesRecibo();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ESTACION_RECIBO_NO_OBTENIDO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un ciudadano a una Solicitud de consultas
     *
     * @param solConsulta la <code>SolicitudConsulta</code> a la que se va a
     * asociar el <code>Ciudadano</code>
     * @param ciud El <code>Ciudadano</code> que va a ser asociado a la
     * <code>SolicitudConsulta</code>
     * @return la <code>Solicitud</code> con el <code>Ciudadano</code> asociado.
     * @see gov.sir.core.negocio.modelo.SolicitudConsulta
     * @see gov.sir.core.negocio.modelo.Ciudadano
     * @throws <code>HermodException</code>
     */
    public Solicitud addCiudadanoToSolicitudConsulta(SolicitudConsulta solConsulta, Ciudadano ciud)
            throws HermodException {
        try {
            return factory.getSolicitudesDAO().agregarCiudadanoConsulta(solConsulta, ciud);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.CIUDADANO_NO_ASOCIADO_A_SOLICITUD_CONSULTA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Finaliza el DAO
     *
     * @throws <code>HermodException</code> cuando hay un error con la conexión
     */
    public void finalizar() throws HermodException {
        try {
            factory.getVariablesOperativasDAO().finalizar();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SHUTDOWN_NO_REALIZADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Realiza el Reparto de las Minutas que se encuentran disponibles dentro
     * del Círculo al que pertenece el <code>Usuario</code>.
     *
     * @param circ <code>Circulo</code> al que pertenece el <code>Usuario</code>
     * @param usuario <code>Usuario</code> que realiza el reparto notarial.
     * @param tipo indica si el reparto es normal (false) o extraordinario
     * (true)
     * @param idExtraordinario identificador del turno en el que se debe
     * realizar un reparto extraordinario.
     * @return Hashtable con las asociaciones, número de Turno y notaría
     * asignada, para cada uno de los Turnos que fueron repartidos.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.RepartoNotarial
     * @see gov.sir.core.negocio.modelo.Minuta
     */
    public Hashtable realizarRepartoCirculo(Circulo circ, Usuario usuario, boolean tipo, String idExtraordinario) throws HermodException {
        try {
            return factory.getRepartosDAO().realizarRepartoCirculo(circ, usuario, tipo, idExtraordinario);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.REPARTO_CIRCULO_NO_REALIZADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Realiza el <code>RepartoNotarial</code> de las Minutas pertenecientes a
     * un Circulo Notarial.
     *
     * @param circ El <code>CirculoNotarial</code> Notarial en el que se va a
     * realizar el Reparto
     * @param usuario <code>Usuario</code> que realiza el reparto notarial.
     * @param tipo indica si el reparto es normal (false) o extraordinario
     * (true)
     * @param idExtraordinario identificador del turno en el que se debe
     * realizar un reparto extraordinario.
     * @return Hashtable con las asociaciones, número de Turno y notaría
     * asignada, para cada uno de los Turnos que fueron repartidos.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.RepartoNotarial
     * @see gov.sir.core.negocio.modelo.Minuta
     */
    public Hashtable realizarRepartoCirculoNotarialOrdinario(CirculoNotarial circuloNotarial, Usuario usuario)
            throws HermodException {
        try {
            return factory.getRepartosDAO().realizarRepartoCirculoNotarialOrdinario(circuloNotarial, usuario);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.REPARTO_CIRCULO_NO_REALIZADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Realiza el consumo de secuencial si no se reparte nada
     *
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.RepartoNotarial
     * @see gov.sir.core.negocio.modelo.Minuta
     */
    public String consultarLastSecuencialCirculoNotarial(CirculoNotarial circuloNotarial, Usuario usuario, boolean tipo)
            throws HermodException {
        try {
            return factory.getRepartosDAO().consultarLastSecuencialCirculoNotarial(circuloNotarial, usuario, tipo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.REPARTO_CIRCULO_NO_REALIZADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Realiza el <code>RepartoNotarial</code> de las Minutas pertenecientes a
     * un Circulo Notarial.
     *
     * @param circ El <code>CirculoNotarial</code> Notarial en el que se va a
     * realizar el Reparto
     * @param usuario <code>Usuario</code> que realiza el reparto notarial.
     * @param tipo indica si el reparto es normal (false) o extraordinario
     * (true)
     * @param idExtraordinario identificador del turno en el que se debe
     * realizar un reparto extraordinario.
     * @return Hashtable con las asociaciones, número de Turno y notaría
     * asignada, para cada uno de los Turnos que fueron repartidos.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.RepartoNotarial
     * @see gov.sir.core.negocio.modelo.Minuta
     */
    public Hashtable realizarRepartoCirculoNotarialExtraordinario(CirculoNotarial circuloNotarial, Usuario usuario, boolean tipo, String[] idsExtraordinaro)
            throws HermodException {
        try {
            return factory.getRepartosDAO().realizarRepartoCirculoNotarialExtraOrdinario(circuloNotarial, usuario, tipo, idsExtraordinaro);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.REPARTO_CIRCULO_NO_REALIZADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un <code>numero de anotaciones de un folio<code> </code> a una
     * solicitud.
     *
     * @param numAnotaciones <code>long</code> que se va a adicionar.
     * @param solicitud <code>Solicitud<code> a la que se agregará
     * el <code>numAnotaciones</code>
     * @return <code>true</code> si se agrego el valor exitosamente
     * <code>false</code> en caso contrario.
     * @see gov.sir.core.negocio.modelo.SoliciutConsulta
     * @throws <code>HermodException</code>
     */
    public boolean setAnotacionestoSolicitud(long numAnotaciones, Solicitud solicitud)
            throws HermodException {
        try {
            return factory.getSolicitudesDAO().setAnotacionestoSolicitud(numAnotaciones, solicitud);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NUMERO_ANOTACIONES_NO_ASOCIADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }

    }

    /**
     * Retorna la lista de validaciones que se deben hacer dependiendo del tipo
     * de certificado
     *
     * @param solicitud Solicitud sobre la que se van a obtener las
     * validaciones.
     * @return Lista de objetos de tipo <code>Validacion</code>
     * @see gov.sir.core.negocio.modelo.Validacion
     * @throws <code>HermodException</code>
     */
    public List getValidacionesCertificado(TipoCertificadoPk tipoId) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getValidacionesByTipoCertificado(tipoId);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_DE_VALIDACIONES_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Realiza las validaciones a las matriculas especificadas dada una lista
     *
     * @param matricula
     * @return
     * @throws ForsetiException hay una lista de excepciones por matrícula
     */
    public boolean validarMatriculas(List validaciones, List matriculas) throws HermodException {
        Validacion val;
        List errores = new ArrayList();
        Hashtable ht = new Hashtable();
        HermodException he = new HermodException("Error en la validación de las matriculas.");
        String matricula;

        for (Iterator itr2 = matriculas.iterator(); itr2.hasNext();) {
            matricula = (String) itr2.next();
            errores = new ArrayList();

            for (Iterator itr = validaciones.iterator(); itr.hasNext();) {

                val = (Validacion) itr.next();

                if (val.getIdValidacion().equals(CValidacion.FOLIO_TRAMITE_SIRMIG)) {

                    Folio folioTemp = new Folio();
                    folioTemp.setIdMatricula(matricula);

                    boolean isTurnoTramiteSirMig = factory.getMigracionSirDAO().isFolioSirMigTurnoFolioTramite(folioTemp);

                    if (isTurnoTramiteSirMig) {
                        errores.add(CValidacion.FOLIO_TRAMITE_SIR_MIG_MSG);
                    }

                    continue;
                }

            }

            if (errores.size() > 0) {
                ht.put(matricula, errores);
            }
        }

        if (ht.size() > 0) {
            he.setHashErrores(ht);
            throw he;
        }

        return true;
    }

    /**
     * Dado un identificador de turno retorna el turno con todos sus atributos y
     * jerarquia exceptuando las anotaciones de los folios.
     *
     * @return el <code>Turno</code> con todos sus atributos exceptuando las
     * anotaciones de los folios.
     * @param tId El Identificador del <code>Turno</code>que se quiere recuperar
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public Turno getTurnoSinAnotaciones(TurnoPk tId) throws HermodException {
        try {
            return factory.getTurnosDAO().getTurnoByID(tId);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNO_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene una lista de los  <code>TipoPago</code> del sistema.
     *
     * @return una lista de objetos <code>TipoPago</code>
     * @see gov.sir.core.negocio.modelo.TipoPago
     * @throws <code>HermodException</code>
     */
    public List getTiposPago() throws HermodException {
        try {
            return factory.getPagosDAO().getTiposPago();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TIPOS_PAGO_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene un círculo de proceso dado su identificador
     *
     * @param cpID El identificador del círculo de proceso
     * @return El <code>CirculoProceso</code> correspondiente al identificador
     * suministrado
     * @throws <code>HermodException</code>
     */
    public CirculoProceso getCirculoProceso(CirculoProcesoPk cpID) throws HermodException {
        try {
            return factory.getProcesosDAO().getCirculoProcesoById(cpID);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.CIRCULO_PROCESO_NO_OBTENIDO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene una lista de los  <code>CirculoTipoPago</code> del sistema.
     *
     * @return una lista de objetos <code>CirculoTipoPago</code>
     * @see gov.sir.core.negocio.modelo.CirculoTipoPago
     * @throws <code>HermodException</code>
     */
    public List getCirculoTiposPago(CirculoPk cirID) throws HermodException {
        try {
            return factory.getPagosDAO().getCirculoTiposPago(cirID);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_CIRCULO_TIPOS_PAGO_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Adiciona un <code>CirculoTipoPago</code> a la configuración del sistema
     *
     * @param datos <code>CirculoTipoPago</code> que va a ser agregado a la
     * configuración del sistema.
     * @return identificador de la <code>EstacionRecibo</code>agregada a la
     * configuración del sistema.
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     * @throws <code>HermodException</code>
     */
    public CirculoTipoPagoPk addCirculoTipoPago(CirculoTipoPago dato) throws HermodException {
        try {
            return factory.getPagosDAO().addCirculoTipoPago(dato);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CIRCULO_TIPO_PAGO_NO_ADICIONADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Elimina un <code>CirculoTipoPago</code> de la configuración del sistema
     *
     * @param datos <code>CirculoTipoPago</code> que va a ser removido de la
     * configuración del sistema.
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     * @throws <code>HermodException</code>
     */
    public void removeCirculoTipoPago(CirculoTipoPago dato) throws HermodException {
        try {
            factory.getPagosDAO().removeCirculoTipoPago(dato);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CIRCULO_TIPO_PAGO_NO_ELIMINADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Realiza la Restitución de Reparto Notarial para la Notaría que la
     * solicita. El proceso de Restitución realiza las siguientes acciones: 1.
     * Marca la <code>Solicitud</code> como aceptada. 2. Anula la
     * <code>Minuta</code> asociada con la <code>Solicitud</code> 3. Coloca la
     * Notaría que realizó la solicitud como primera, dentro de la cola de
     * Notarías para la categoría a la que pertenecía la <code>Minuta</code>
     *
     * @param idSolicitud Identificador de la solicitud de restitución.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.SolicitudRestitucionRepartoNotarial
     * @see gov.sir.core.negocio.modelo.Minuta
     */
    public Hashtable realizarRestitucionRepartoNotarial(String idSolicitud) throws HermodException {
        try {
            return factory.getRepartosDAO().realizarRestitucionRepartoNotarial(idSolicitud);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.RESTITUCION_REPARTO_NO_REALIZADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite asignar a una <code>SolicitudConsulta</code>
     * persistente, el valor para el atributo numeroMaximoBusquedas recibido
     * como parámetro.
     *
     * @param numMaximo El número máximo de búsquedas permitido para la
     * solicitud.
     * @param solicitud La <code>SolicitudConsulta</code> a la que se va a
     * asignar el número máximo de búsquedas.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso),
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.SolicitudConsulta
     */
    public boolean setNumMaxBusquedasToSolicitudConsulta(SolicitudConsulta solicitud, int numMaximo)
            throws HermodException {

        try {
            return factory.getSolicitudesDAO().setNumMaxBusquedasToSolConsulta(solicitud, numMaximo);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.NUM_MAXIMO_BUSQUEDAS_NO_ASIGNADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite adicionar el texto correspondiente a una resolución
     * de restitución de reparto notarial a una
     * <code>SolicitudRestitucionReparto</code>
     *
     * @param resolucion El texto que va a ser asociado a la
     * <code>Solicitud</code>
     * @param solicitud La <code>SolicitudRestitucionReparto</code> a la que se
     * va a asignar la resolución.
     * @param observaciones Comentario que explica por qué fue aceptada o
     * rechazada una solicitud de restitución
     * @param fechaResolucion fecha en la que fue creada la resolución de
     * restitución.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
     */
    public boolean addResolucionToSolicitudRestitucion(
            SolicitudRestitucionReparto solicitud,
            String resolucion, String observaciones, Date fechaResolucion)
            throws HermodException {
        try {
            return factory.getRepartosDAO().addResolucionToSolicitudRestitucion(solicitud, resolucion, observaciones, fechaResolucion);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.RESOLUCION_NO_ASOCIADA_SOLICITUD_RESTITUCION, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite marcar como rechazada una
     * <code>SolicitudRestitucionReparto</code>
     *
     * @param solicitud La <code>SolicitudRestitucionReparto</code> que va a ser
     * marcada como rechazada.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
     */
    public boolean rechazarSolicitudRestitucion(SolicitudRestitucionReparto solicitud)
            throws HermodException {
        try {
            return factory.getRepartosDAO().rechazarSolicitudRestitucion(solicitud);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.SOLICITUD_RESTITUCION_NO_RECHAZADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar una <code>Categoría</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param categoria La <code>Categoria</code> que va a ser eliminada.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.Categoria
     */
    public boolean eliminarCategoria(Categoria categoria, Usuario usuario) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarCategoria(categoria, usuario);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CATEGORIA_NO_ELIMINADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar un <code>AlcanceGeografico</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param alcance El <code>AlcanceGeografico</code> que va a ser eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.AlcanceGeografico
     */
    public boolean eliminarAlcanceGeografico(AlcanceGeografico alcance) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarAlcanceGeografico(alcance);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ALCANCE_GEOGRAFICO_NO_ELIMINADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar un <code>TipoFotocopia</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param tipoFot El <code>TipoFotocopia</code> que va a ser eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.TipoFotocopia
     */
    public boolean eliminarTipoFotocopia(TipoFotocopia tipoFot) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarTipoFotocopia(tipoFot);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_FOTOCOPIA_NO_ELIMINADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar un <code>TipoCalculo</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param tipoCalc El <code>TipoCalculo</code> que va a ser eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.TipoCalculo
     */
    public boolean eliminarTipoCalculo(TipoCalculo tipoCalc) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarTipoCalculo(tipoCalc);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_CALCULO_NO_ELIMINADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar un <code>TipoDerechoReg</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param tipoDerecho El <code>TipoDerechoReg</code> que va a ser eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.TipoDerechoReg
     */
    public boolean eliminarTipoDerechoRegistral(TipoDerechoReg tipoDerecho) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarTipoDerechoRegistral(tipoDerecho);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.TIPO_DERECHO_REGISTRAL_NO_ELIMINADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar un Tipo de  <code>AccionNotarial</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param accion La <code>AccionNotarial</code> que va a ser eliminada.
     * @param usuario que va eliminar la accion notarial
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.AccionNotarial
     */
    public boolean eliminarAccionNotarial(AccionNotarial accion, Usuario usuario) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarAccionNotarial(accion, usuario);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.TIPO_ACCION_NOTARIAL_NO_ELIMINADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite asociar un rango de matrículas y las respectivas
     * solicitudes folio a una <code>SolicitudRegistro</code> El servicio
     * realiza la validación de los rangos y verifica la existencia de los
     * folios que va a asociar. En caso de que no exista alguno de los folios,
     * debe partir los rangos.
     *
     * @param matInicial Valor de la matrícula inicial del rango.
     * @param matFinal Valor de la matrícula final del rango.
     * @param solicitud <code>Solicitud</code> a la que se asociará el rango de
     * folios.
     * @param user El <code>Usuario</code> que generó la
     * <code>SolicitudFolio</code>
     * @return Lista con objetos de tipo <code>RangoFolio</code> creados a
     * partir de los valores de matrículas inicial y final recibidos como
     * parámetros.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     * @see gov.sir.core.negocio.modelo.RangoFolio
     * @see gov.sir.core.negocio.modelo.SolicitudFolio
     */
    public List addRangoFoliosToSolicitudRegistro(
            String matInicial,
            String matFinal,
            Solicitud solicitud,
            Usuario user,
            boolean validarAsociar)
            throws HermodException {

        try {
            return factory.getSolicitudesDAO().addRangoFoliosToSolicitud(
                    matInicial,
                    matFinal,
                    solicitud,
                    user,
                    validarAsociar);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.RANGO_FOLIO_NO_ASOCIADO_SOLICITUD, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Modifica el encabezado del documento en una solicitud de Registro.
     *
     * @author Cesar Ramírez
     * @change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO Se recibe el usuario
     * como parámetro.
     * @param solReg La <code>SolicitudRegistro</code> en la que se va a
     * modificar el <code>Documento</code>
     * @return la <code>Solicitudregistro</code> con el <code>Documento</code>
     * modificado.
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     * @see gov.sir.core.negocio.modelo.Documento
     * @throws <code>HermodException</code>
     */
    public SolicitudRegistro updateEncabezadoInSolicitud(SolicitudRegistro solReg, Usuario usuario)
            throws HermodException {
        try {
            return factory.getSolicitudesDAO().modificarEncabezadoRegistro(solReg, usuario);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ENCABEZADO_NO_MODIFICADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Consulta las <code>EstacionRecibo</code> para un círculo específico
     *
     * @return la lista de los objetos <code>EstacionRecibo</code> solicitados
     * @throws HermodException
     */
    public List consultarEstacionesReciboPorCirculo(Circulo circulo) throws HermodException {
        try {
            return factory.getRecibosDAO().consultarEstacionesReciboPorCirculo(circulo);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.CONSULTA_ESTACIONES_RECIBO_CIRCULO_NO_EFECTUADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }

    }

    /**
     * Servicio que permite eliminar un <code>Banco</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param banco El <code>Banco</code> que va a ser eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.Banco
     */
    public boolean eliminarBanco(Banco banco) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarBanco(banco);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.BANCO_NO_ELIMINADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar una <code>SucursalBanco</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param sucursal <code>SucursalBanco</code> que va a ser eliminada.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.SucursalBanco
     */
    public boolean eliminarSucursalBanco(SucursalBanco sucursal) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarSucursalBanco(sucursal);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SUCURSAL_BANCO_NO_ELIMINADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar una <code>Tarifa</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param tarifa <code>Tarifa</code> que va a ser eliminada.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.Tarifa
     */
    public boolean eliminarTarifa(Tarifa tarifa) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarTarifa(tarifa);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TARIFA_NO_ELIMINADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar un <code>TipoTarifa</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param tipoTarifa <code>TipoTarifa</code> que va a ser eliminada.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.TipoTarifa
     */
    public boolean eliminarTipoTarifa(TipoTarifa tipoTarifa) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarTipoTarifa(tipoTarifa);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_TARIFA_NO_ELIMINADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar un <code>SubtipoSolicitud</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param subtipoSolicitud <code>SubtipoSolicitud</code> que va a ser
     * eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.SubtipoSolicitud
     */
    public boolean eliminarSubtipoSolicitud(SubtipoSolicitud subtipoSolicitud) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarSubtipoSolicitud(subtipoSolicitud);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SUBTIPO_SOLICITUD_NO_ELIMINADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar un objeto <code>OPLookupTypes</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param type Objeto <code>OPLookupTypes</code> que va a ser eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     */
    public boolean eliminarLookupType(OPLookupTypes type) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarLookupType(type);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LOOKUP_TYPE_NO_ELIMINADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar un objeto <code>OPLookupCodes</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param code Objeto <code>OPLookupCodes</code> que va a ser eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     */
    public boolean eliminarLookupCode(OPLookupCodes code) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarLookupCode(code);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LOOKUP_CODE_NO_ELIMINADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar un objeto <code>TipoImpuesto</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param impuesto Objeto <code>TipoImpuesto</code> que va a ser eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.TipoImpuesto
     */
    public boolean eliminarTipoImpuesto(TipoImpuesto impuesto) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarTipoImpuesto(impuesto);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_IMPUESTO_NO_ELIMINADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar un objeto <code>SubtipoAtencion</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param atencion Objeto <code>SubtipoAtencion</code> que va a ser
     * eliminado.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     */
    public boolean eliminarSubtipoAtencion(SubtipoAtencion atencion) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarSubtipoAtencion(atencion);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SUBTIPO_ATENCION_NO_ELIMINADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar un objeto <code>TipoActo</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param usuario que va a eliminar el tipo de acto
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.TipoActo
     */
    public boolean eliminarTipoActo(TipoActo acto, Usuario usuario) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarTipoActo(acto, usuario);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_ACTO_NO_ELIMINADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar una <code>EstacionRecibo</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param categoria La <code>EstacionRecibo</code> que va a ser eliminada.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    public boolean eliminarEstacionRecibo(EstacionRecibo estacionRecibo) throws HermodException {
        try {
            return factory.getRecibosDAO().eliminarEstacionRecibo(estacionRecibo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ESTACION_RECIBO_NO_ELIMINADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un <code>ValidacionNota</code> en el sistema.
     *
     * @param valVota La <code>ValidacionNota</code> que se va a agregar.
     * @return true o false dependiendo del resultado de la insercion.
     * @see gov.sir.core.negocio.modelo.ValidacionNota
     * @throws <code>HermodException</code>
     */
    public boolean addValidacionNota(ValidacionNota valNota) throws HermodException {
        try {
            ValidacionNotaPk valNotaId = factory.getVariablesOperativasDAO().addValidacionNota(valNota);

            //Inserción Satisfactoria.
            if (valNotaId != null) {
                return true;
            }
            return false;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.VALIDACION_NOTA_NO_CREADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar una <code>ValidacionNota</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param valNota La <code>ValidacionNota</code> que va a ser eliminada.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.ValidacionNota
     */
    public boolean eliminarValidacionNota(ValidacionNota valNota) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarValidacionNota(valNota);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.VALIDACION_NOTA_NO_ELIMINADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de objetos de tipo <code>ValidacionNota</code>
     *
     * @return una lista de objetos <code>ValidacionNota</code>
     * @see gov.sir.core.negocio.modelo.ValidacionNota
     * @throws <code>HermodException</code>
     */
    public List getValidacionNotas() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getValidacionNotas();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_VALIDACION_NOTA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Actualiza los subtipos de atención de un <code>Usuario</code>
     * <p>
     * Utilizado desde las pantallas administrativas.</p>
     *
     * @param usuario  <code>Usuario</code> al que se le adicionarán los subtipos
     * @param listaUsuarios  <code>List</code> con los ids de usuario que son
     * calificadores
     * @param circulo  <code>Circulo</code> circulo al que pertenece el usuario
     * que se le esta configurando el suubtipo de atención.
     * @return <code>boolean</code> si puede actualizar el usuario
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.Usuario
     */
    public boolean updateSubtiposAtencionEnUsuario(Usuario usuario, List listaUsuarios, Circulo circulo) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().updateSubtiposAtencionEnUsuario(usuario, listaUsuarios, circulo);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.ERROR_ACTUALIZACION_SUBTIPOS_ATENCION_USUARIO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de procesos existentes en el sistema
     *
     * @return una lista de objetos <code>Proceso</code> que están disponibles
     * en el sistema
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.Proceso
     */
    public List getListaProcesos() throws HermodException {
        try {
            return factory.getProcesosDAO().getListaProcesos();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CONSULTA_LISTA_PROCESOS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de objetos de tipo <code>FaseProceso</code> con las
     * fases relacionadas al <code>Proceso</code> pasado como parámetro
     *
     * @return una lista de objetos <code>Procesos_V</code> que con las fases
     * del proceso pasado como parámetro
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.FaseProceso
     */
    public List getFasesDeProceso(Proceso proceso) throws HermodException {
        try {
            return factory.getProcesosDAO().getFasesDeProceso(proceso);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CONSULTA_FASES_PROCESO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Adiciona una <code>TipoCertificadoValidacion<code> a la configuración del sistema.<p>
     * <p>
     * Se lanza una excepción en el caso en el que ya exista en la base de
     * datos, una <code>TipoCertificadoValidacion</code> con el identificador
     * pasado dentro del parámetro.
     *
     * @param valNota objeto <code>TipoCertificadoValidacion</code> con sus
     * atributos, incluido el identificador.
     * @return identificador del TipoCertificadoValidacion generado.
     * @throws HermodException.
     * @see gov.sir.core.negocio.modelo.TipoCertificadoValidacion
     */
    public TipoCertificadoValidacionPk addTipoCertificadoValidacion(TipoCertificadoValidacion dato)
            throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().addTipoCertificadoValidacion(dato);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.ADICION_TIPO_CERTIFICADO_VALIDACION_NO_EFECTUADA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar una <code>TipoCertificadoValidacion</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param valNota la <code>TipoCertificadoValidacion</code> que va a ser
     * eliminada.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.TipoCertificadoValidacion
     */
    public boolean eliminarTipoCertificadoValidacion(TipoCertificadoValidacion dato) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarTipoCertificadoValidacion(dato);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(
                            HermodException.ELIMINACION_TIPO_CERTIFICADO_VALIDACION_NO_EFECTUADA,
                            e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene una lista de objetos tipo <code>TipoCertificadoValidacion </code>
     * filtrada por <code>TipoCertificado</code>
     *
     * @param tipoCertificado  <code>TipoCertificado</code> utilizado para el
     * filtro
     * @return una lista de objetos <code>TipoCertificadoValidacion</code>
     * @throws HermodException.
     * @see gov.sir.core.negocio.modelo.TipoCertificadoValidacion
     */
    public List getTiposCertificadosValidacionByTipoCertificado(TipoCertificado dato) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTiposCertificadosValidacionByTipoCertificado(
                    dato);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(
                            HermodException.CONSULTA_TIPO_CERTIFICADO_VALIDACION_NO_EFECTUADA,
                            e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite consultar la lista de objetos
     * <code>Validacion</code> existentes en el sistema.
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @return <code>List</code> con la lista de validaciones disponibles en el
     * sistema
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.Validacion
     */
    public List getValidaciones() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getValidaciones();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CONSULTA_VALIDACIONES, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Adiciona un <code>TipoNota<code> a la configuración del sistema.<p>
     * <p>
     * Se lanza una excepción en el caso en el que ya exista en la base de
     * datos, una <code>TipoNota</code> con el identificador pasado dentro del
     * parámetro.
     *
     * @param tipoNota objeto <code>TipoNota</code> con sus atributos, incluido
     * el identificador.
     * @param usuario que va adiconar el tipo de nota
     * @return identificador del TipoNota generado.
     * @throws HermodException.
     * @see gov.sir.core.negocio.modelo.TipoNota
     */
    public TipoNotaPk addTipoNota(TipoNota tipoNota, Usuario usuario) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().addTipoNota(tipoNota, usuario);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ADICION_TIPO_NOTA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar un objeto <code>TipoNota</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param valNota la <code>TipoNota</code> que va a ser eliminada.
     * @param usuario que va a eliminar el tipo de nota
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.TipoNota
     */
    public boolean eliminarTipoNota(TipoNota tipoNota, Usuario usuario) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarTipoNota(tipoNota, usuario);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ELIMINACION_TIPO_NOTA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Adiciona un <code>CheckItem<code> a la configuración del sistema.<p>
     * <p>
     * Se lanza una excepción en el caso en el que ya exista en la base de
     * datos, un <code>CheckItem</code> con el identificador pasado dentro del
     * parámetro.
     *
     * @param item objeto <code>CheckItem</code> con sus atributos, incluido el
     * identificador.
     * @return identificador del <code>CheckItem</code> generado.
     * @throws HermodException.
     * @see gov.sir.core.negocio.modelo.CheckItem
     */
    public CheckItemPk addCheckItem(CheckItem item) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().addCheckItem(item);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ADICION_CHECK_ITEM, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Edita un <code>CheckItem<code> a la configuración del sistema.<p>
     * <p>
     * Se lanza una excepción en el caso en el que no exista en la base de
     * datos, un <code>CheckItem</code> con el identificador pasado dentro del
     * parámetro.
     *
     * @param item objeto <code>CheckItem</code> con sus atributos, incluido el
     * identificador.
     * @return identificador del <code>CheckItem</code> generado.
     * @throws HermodException.
     * @see gov.sir.core.negocio.modelo.CheckItem
     */
    public CheckItemPk updateCheckItem(CheckItem item) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().updateCheckItem(item);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ADICION_CHECK_ITEM, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Servicio que permite eliminar un objeto <code>CheckItem</code>
     * <p>
     * Utilizado desde las pantallas administrativas.
     *
     * @param item la <code>CheckItem</code> que va a ser eliminada.
     * @return <code>true</code> (éxito) o <code>false</code> (fracaso)
     * dependiendo del resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.CheckItem
     */
    public boolean eliminarCheckItem(CheckItem item) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().eliminarCheckItem(item);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ELIMINACION_CHECK_ITEM, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene una lista de objetos tipo <code>CheckItem </code> filtrada por
     * <code>SubtipoSolicitud</code>
     *
     * @param subtipo  <code>SubtipoSolicitud</code> utilizado para el filtro
     * @return una lista de objetos <code>CheckItem</code>
     * @throws HermodException.
     * @see gov.sir.core.negocio.modelo.CheckItem
     * @see gov.sir.core.negocio.modelo.SubtipoSolicitud
     */
    public List getCheckItemsBySubtipoSolicitud(SubtipoSolicitud subtipo) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getCheckItemsBySubtipoSolicitud(subtipo);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.CONSULTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un <code>Usuario</code> a un <code>Turno</code>
     *
     * @param user  <code>Usuario</code> que será asignado al <code>Turnoz</code>
     * @param turno <code>Turno</code> al que será asignado el
     * <code>Usuario</code>
     * @return true o false dependiendo del resultado de la operación.
     * @throws HermodService
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public boolean setUsuarioToTurno(Usuario user, Turno turno) throws HermodException {

        try {
            return factory.getTurnosDAO().setUsuarioToTurno(user, turno);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.USUARIO_NO_ASOCIADO_TURNO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene una lista con los tipos de visibilidad para las notas
     * informativas, existentes en el sistema.
     *
     * @return Lista con los nombres de los tipos de visibilidad existentes en
     * el sistema.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.Nota
     */
    public List getTiposVisibilidad() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTiposVisibilidad();
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_TIPOS_VISIBILIDAD_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de turnos asociados a una <code>Fase</code>, un
     * <code>Proceso</code> y un <code>Circulo</code>.
     *
     * @param proceso <code>Proceso</code> sobre el cual se buscan los turnos.
     * @param fase <code>Fase</code> sobre la cual se buscan los turnos.
     * @param circulo <code>Circulo</code> sobre el cual se buscan los turnos.
     * @return una lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Proceso
     * @see gov.sir.core.negocio.modelo.Fase
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws <code>HermodException</code>
     */
    public List getTurnosFase(Proceso proceso, Fase fase, Circulo circulo) throws HermodException {
        try {
            return factory.getTurnosDAO().getTurnosFase(proceso, fase, circulo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TURNOS_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Modifica una solicitud de Certificado.
     *
     * @param solCer La <code>SolicitudCertificado</code> que se va a modificar.
     * @return la <code>SolicitudCertificado</code> actualizada.
     * @see gov.sir.core.negocio.modelo.SolicitudCerificado
     * @throws <code>HermodException</code>
     */
    public SolicitudCertificado updateSolicitudCertificado(SolicitudCertificado solCer)
            throws HermodException {
        try {
            return factory.getSolicitudesDAO().modificarSolicitudCertificado(solCer);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ENCABEZADO_NO_MODIFICADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Modifica una solicitud de Certificado o Consulta.
     *
     * @param solicitud La <code>Solicitud</code> que se va a modificar.
     * @return la <code>Solicitud</code> actualizada.
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @throws <code>HermodException</code>
     */
    public Solicitud updateSolicitud(Solicitud solicitud)
            throws HermodException {
        try {
            return factory.getSolicitudesDAO().actualizarSolicitud(solicitud);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ENCABEZADO_NO_MODIFICADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene una la lista de los Tipos de Notas existentes en la configuración
     * del sistema que están asociados con el <code>Proceso</code> pasado como
     * parámetro, la fase y si es devolutivo o informativa.
     * <p>
     * El método lanza una excepción si no se encuentra el <code>Proceso</code>
     * con el identificador pasado como parámetro.
     *
     * @return una lista de objetos <code>TipoNota</code> ordenados
     * alfabéticamente de acuerdo al nombre, y que están asociados con el
     * <code>Proceso</code> recibido como parámetro.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.TipoNota
     * @see gov.sir.core.negocio.modelo.Proceso
     */
    public List getTiposNotaProceso(ProcesoPk proceso, String fase, boolean devolutiva)
            throws HermodException {
        try {
            if (fase == null) {
                fase = factory.getProcesosDAO().getFaseInicialProceso(proceso).getID();
            }

            return factory.getVariablesOperativasDAO().getTiposNotaProceso(proceso, fase, devolutiva);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TIPOS_NOTA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de Tipos de Tarifas de Certificados
     *
     * @return una lista de objetos de tipo <code>OPLookupCodes</code> con todos
     * sus atributos.
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     * @throws <code>HermodException</code>
     */
    public List getTiposTarifaCertificados() throws HermodException {
        try {
            return factory.getLookupDAO().getLookupCodes(COPLookupTypes.TARIFA_CERTIFICADOS);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_TIPOS_TARIFAS_CERTIFICADOS_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene una la lista de los Tipos de Notas existentes en la configuración
     * del sistema que están asociados con el <code>Proceso</code> pasado como
     * parámetro y la fase
     * <p>
     * El método lanza una excepción si no se encuentra el <code>Proceso</code>
     * con el identificador pasado como parámetro.
     *
     * @return una lista de objetos <code>TipoNota</code> ordenados
     * alfabéticamente de acuerdo al nombre, y que están asociados con el
     * <code>Proceso</code> recibido como parámetro.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.TipoNota
     * @see gov.sir.core.negocio.modelo.Proceso
     */
    public List getTiposNotaProceso(ProcesoPk proceso, String fase) throws HermodException {
        try {
            if (fase == null) {
                fase = factory.getProcesosDAO().getFaseInicialProceso(proceso).getID();
            }

            return factory.getVariablesOperativasDAO().getTiposNotaProceso(proceso, fase);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TIPOS_NOTA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;

        }

    }

    /**
     * Actualiza el atributo orden para todas las relaciones Usuario - SubTipo
     * de Atención definidas dentro del Circulo recibido como parámetro.
     * <p>
     * El usuario que tenía el orden 1 pasa al final de la lista, y en los demás
     * casos, el orden se reduce en una unidad.
     *
     * @throws <code>HermodException</code>
     * @param circulo El <code>Circulo </code> en el que se debe actualizar el
     * orden para las relaciones Usuario - SubtipoAtencion.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * del proceso.
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     */
    public boolean actualizarRotacionReparto(Circulo circulo) throws HermodException {
        try {
            return factory.getRepartoTurnosDAO().actualizarRotacionReparto(circulo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ROTACION_REPARTO_NO_REALIZADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el siguiente número de recibo pero NO avanza la secuencia, si se
     * supera la secuencia devuelve -1
     *
     * @param oid
     * @return
     * @throws HermodException
     */
    public long getNextNumeroReciboSinAvanzar(EstacionReciboPk oid, Usuario user, long idProceso) throws HermodException {
        try {
            return factory.getRecibosDAO().getNextNumeroReciboSinAvanzar(oid, user, idProceso);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ESTACION_RECIBO_NO_NEXT, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Avanza la secuencia de recibos en el avance configurado, si se supera la
     * secuencia definida en la estación se lanza una excepción
     *
     * @param oid
     * @param avance
     * @return
     * @throws HermodException
     */
    public long avanzarNumeroRecibo(EstacionReciboPk oid, long avance, long idProceso) throws HermodException {
        try {
            return factory.getRecibosDAO().avanzarNumeroRecibo(oid, avance, idProceso);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ESTACION_RECIBO_NO_NEXT, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Traslada un turno a un usuario específico
     *
     * @param turno
     * @param usuario
     * @return
     * @throws HermodException
     */
    public boolean trasladarTurnoSAS(Turno turno, Estacion estacion) throws HermodException {
        try {
            return factory.getTrasladoTurnosDAO().trasladarTurnoSAS(turno, estacion);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ESTACION_RECIBO_NO_NEXT, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene una lista de objetos <code>Turno</code> que estan sociados con el
     * <code>Folio</code> correspondiente al numero de matricula ingresado.
     *
     * @param matricula <code>String</code>
     * @return una lista de objetos <code>Turno</code>.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Folio
     */
    public List getTurnosByMatricula(String matricula) throws HermodException {
        try {
            return factory.getTurnosDAO().getTurnosByMatricula(matricula);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TURNOS_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Ejecuta el pago y obtiene la información para generar el recibo
     *
     * @param pago representa la información para realizar el <code>Pago</code>
     * @param impresora Impresora en la que debe imprimirse el certificado
     * generado.
     * @return un objeto <code>Turno</code> con los datos correspondientes
     * @see gov.sir.core.negocio.modelo.Pago
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     * @throws <code>HermodException</code>
     */
    public Turno procesarPago(Pago pago, String estacion, String impresora, Usuario user, Rol rol) throws HermodException {

        try {
            return this.procesarPago(pago, estacion, impresora, user, rol, false);
        } catch (DAOException e) {
            HermodException he = new HermodException(DAOException.PAGO_INCORRECTO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }

    }

    /**
     * Hace persistente el motivo por el cual se incrementó el secuencial de un
     * recibo.
     *
     * @param usuario Usuario que incrementa el secuencial.
     * @param secuencial Valor al cual se incrementa el secuencial de recibos
     * @param motivo Motivo por el cual se incrementó el secuencial del recibo.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @throws HermodException
     */
    public boolean almacenarMotivoIncrementoSecuencial(Usuario usuario, long secuencial, String motivo)
            throws HermodException {

        try {
            return factory.getRecibosDAO().almacenarMotivoIncrementoSecuencial(usuario, secuencial, motivo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.MOTIVO_SECUENCIAL_RECIBOS_NO_ALMACENADO);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Dado un identificador de turno retorna el turno con todos sus atributos y
     * jerarquia.
     *
     * @return el <code>Turno</code> con todos sus atributos.
     * @param tId El Identificador del <code>Turno</code>que se quiere recuperar
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public Fase getFase(String idFase) throws HermodException {
        try {
            return factory.getFasesDAO().getFaseById(idFase);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.FASE_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Este servicio permite modificar el valor de la última liquidación
     * asociada a un turno (Utilizado en el proceso de devoluciones).
     *
     * @param turno El identificador del turno sobre el cual se va a modificar
     * la última liquidación.
     * @param liquidacion La <code>Liquidacion</code> con los valores que van a
     * ser modificados.
     * @return <code>true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.Liquidacion
     */
    public boolean updateUltimaLiquidacion(TurnoPk turnoId, Liquidacion liquidacion) throws HermodException {

        try {
            return factory.getLiquidacionesDAO().updateUltimaLiquidacion(turnoId, liquidacion);
        } catch (Throwable t) {

            HermodException he = new HermodException(HermodException.ULTIMA_LIQUIDACION_NO_ACTUALIZADA, t);
            throw he;
        }
    }

    /**
     * Actualiza un <code>TipoNota<code> en la configuración del sistema.<p>
     * <p>
     * @param tipoNota objeto <code>TipoNota</code> con sus atributos
     * @param usuario que va a modificar el tipo de nota
     * @return identificador del TipoNota generado.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.TipoNota
     */
    public TipoNotaPk updateTipoNota(TipoNota tipoNota, Usuario usuario) throws HermodException {

        try {
            TipoNotaPk tipoNotaId
                    = factory.getVariablesOperativasDAO().updateTipoNota(tipoNota, usuario);

            //Se realizó la actualización del Tipo de Nota
            if (tipoNotaId != null) {
                return tipoNotaId;
            }
            return null;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_NOTA_NO_ACTUALIZADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Valida que el turno pueda ser anulado.
     * <p>
     * @param idTurno objeto <code>Turno.ID</code> con sus atributos
     * @return true = Si se puede anular, false = Si no se puede anular.
     * @throws Throwable.
     * @see gov.sir.core.negocio.modelo.Turno.ID
     */
    public boolean validarAnulacionTurno(TurnoPk idTurno) throws HermodException {

        try {
            return factory.getTurnosDAO().validarAnulacionTurno(idTurno);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNO_NO_ANULADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Anular el turno
     * <p>
     * @param turno objeto <code>Turno</code> con sus atributos
     * @return void
     * @throws Throwable.
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public void anularTurno(Turno turno) throws HermodException {

        try {
            factory.getTurnosDAO().anularTurno(turno);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNO_NO_ANULADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Habilitar el turno
     * <p>
     * @param turno objeto <code>Turno</code> con sus atributos
     * @return void
     * @throws Throwable.
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public void habilitarTurno(Turno turno) throws HermodException {

        try {
            factory.getTurnosDAO().habilitarTurno(turno);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNO_NO_HABILITADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene los turnos anteriores asociados con el turno con identificador
     * ingresado como parámetro.
     *
     * @param idTurno Identificador del turno del cual se consultarán los turnos
     * anteriores.
     * @return lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>HermodException</code>
     */
    public List getTurnosAnteriores(String idTurno) throws HermodException {
        try {
            return factory.getTurnosDAO().getTurnosAnteriores(idTurno);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNOS_ANTERIORES_NO_OBTENIDOS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene los turnos siguientes asociados con el turno con identificador
     * ingresado como parámetro.
     *
     * @param idTurno Identificador del turno del cual se consultarán los turnos
     * siguientes.
     * @return lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>HermodException</code>
     */
    public List getTurnosSiguientes(String idTurno) throws HermodException {
        try {
            return factory.getTurnosDAO().getTurnosSiguientes(idTurno);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNOS_SIGUIENTES_NO_OBTENIDOS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        **/
    /**
     * Obtiene los turnos siguientes asociados con el turno con identificador
     * ingresado como parámetro.
     *
     * @param idTurno Identificador del turno del cual se consultarán los turnos
     * siguientes.
     * @return lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>HermodException</code>
     */
    public List getTurnosSiguientesTestamento(String idTurno) throws HermodException {
        try {
            return factory.getTurnosDAO().getTurnosSiguientesTestamento(idTurno);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNOS_SIGUIENTES_NO_OBTENIDOS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene los turnos siguientes asociados con el turno con identificador
     * ingresado como parámetro.
     *
     * @param idTurno Identificador del turno del cual se consultarán los turnos
     * siguientes.
     * @return lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>HermodException</code>
     */
    public List getTurnosSiguientesDevolucion(String idTurno) throws HermodException {
        try {
            return factory.getTurnosDAO().getTurnosSiguientesDevolucion(idTurno);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNOS_SIGUIENTES_NO_OBTENIDOS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Ejecuta el pago y obtiene la información para generar el recibo
     *
     * @param pago representa la información para realizar el <code>Pago</code>
     * @param parametros tabla con parametros para procesar una solicitud de
     * certificados masivos.
     * @return un objeto <code>Turno</code> con los datos correspondientes
     * @see gov.sir.core.negocio.modelo.Pago
     * @throws <code>HermodException</code>
     */
    public Turno procesarPagoMasivos(Pago pago, Hashtable parametros) throws HermodException {

        try {
            return factory.getPagosDAO().procesarPago(pago, parametros);
        } catch (DAOException e) {

            HermodException he = new HermodException(DAOException.PAGO_INCORRECTO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el valor de la variable probabilidad revisión calificación.
     *
     * @return El valor de la variable probabilidad revisión calificación.
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     * @throws <code>HermodException</code>
     */
    public String getProbabilidadRevisionCalificacion() throws HermodException {
        try {
            return factory.getLookupDAO().getValor(COPLookupTypes.REVISION_CALIFICACION_TYPE, COPLookupCodes.REVISION_CALIFICACION_CODE);
        } catch (DAOException e) {

            HermodException he = new HermodException(HermodException.PROBABILIDAD_REVISION_CALIFICACION_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el valor de la cantidad de impresiones de certificados permitidas
     * en correcciones.
     *
     * @return El valor de la variable numero impresiones de certificados en
     * correcciones.
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     * @throws <code>Throwable</code>
     */
    public String getNumeroImpresionesCertificadosEnCorrecciones() throws HermodException {
        try {
            return factory.getLookupDAO().getValor(COPLookupTypes.NUMERO_IMPRESIONES_CERTIFICADOS_CORRECCIONES_TYPE, COPLookupCodes.NUMERO_IMPRESIONES_CERTIFICADOS_CORRECCIONES_CODE);
        } catch (DAOException e) {

            HermodException he = new HermodException(HermodException.NUMERO_DE_IMPRESIONES_CERTIFICADOS_EN_CORRECCIONES_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Modifica el valor de la variable probabilidad revisión calificación.
     *
     * @param nuevoValor el valor que va a ser asignado a la variable
     * probabilidad revisión calificación.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     * @throws <code>HermodException</code>
     */
    public boolean updateProbabilidadRevisionCalificacion(String nuevoValor) throws HermodException {
        boolean respuesta = false;

        try {

            respuesta = factory.getLookupDAO().updateLookupCode(COPLookupTypes.REVISION_CALIFICACION_TYPE, COPLookupCodes.REVISION_CALIFICACION_CODE, nuevoValor);

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.PROBABILIDAD_REVISION_CALIFICACION_NO_MODIFICADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }

        return respuesta;
    }

    public Map getSolicitudesSinTurno(Circulo circulo, Date fechaInicial, Date fechaFinal, String cadena,
            int indiceInicial, int numeroResultados) throws HermodException {

        try {
            return factory.getLiquidacionesDAO().getSolicitudesSinTurno(circulo, fechaInicial, fechaFinal, cadena, indiceInicial, numeroResultados);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_SOLICITUDES_SIN_TURNOS_ASOCIADOS_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    public long getNumeroSolicitudesSinTurno(Circulo circulo, Date fechaInicial,
            Date fechaFinal) throws HermodException {

        try {
            return factory.getLiquidacionesDAO().getNumeroSolicitudesSinTurno(circulo, fechaInicial, fechaFinal);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_SOLICITUDES_SIN_TURNOS_ASOCIADOS_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Elimina la <code>Solicitud</code> recibida como parámetro.
     *
     * @param solicitud La <code>Solicitud</code> que va a ser eliminada.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @throws <code>HermodException</code>
     */
    public boolean deleteSolicitud(Solicitud solicitud) throws HermodException {
        try {
            return factory.getLiquidacionesDAO().deleteSolicitud(solicitud);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SOLICITUD_SIN_TURNOS_ASOCIADOS_NO_ELIMINADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Elimina la <code>Solicitud</code> recibida como parámetro.
     *
     * @param solicitud La <code>Solicitud</code> que va a ser eliminada.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @throws <code>Throwable</code>
     */
    public boolean deleteTurnoAnterior(SolicitudPk sid) throws HermodException {
        try {
            return factory.getSolicitudesDAO().deleteTurnoAnterior(sid);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SOLICITUD_SIN_TURNOS_ASOCIADOS_NO_ELIMINADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Elimina todas las solicitudes segun el rango dado
     *
     * @param solicitud La <code>Solicitud</code> que va a ser eliminada.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @throws <code>Throwable</code>
     */
    public boolean removeSolicitudesSinTurno(Circulo circulo, Date fechaInicial, Date fechaFinal, String cadena,
            int indiceInicial, int numeroResultados) throws HermodException {

        try {
            return factory.getLiquidacionesDAO().removeSolicitudesSinTurno(circulo, fechaInicial, fechaFinal, cadena, indiceInicial, numeroResultados);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_SOLICITUDES_SIN_TURNOS_ASOCIADOS_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene una lista de los objetos <code>TipoTarifa</code> existentes en la
     * Base de Datos que son configurables por círculo
     * <p>
     * Se establece como criterio de ordenamiento el id del tipo de tarifa.
     *
     * @return una lista de objetos <code>TipoTarifa</code>
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Tarifa
     * @see gov.sir.core.negocio.modelo.TipoTarifa
     */
    public List getTiposTarifasConfiguradasPorCirculo() throws HermodException {
        try {
            return factory.getTarifasDAO().getTiposTarifasConfiguradasPorCirculo();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TARIFAS_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene una lista de los objetos <code>Tarifa</code> existentes en la
     * Base de Datos del círculo indicado
     * <p>
     * @return una lista de objetos <code>Tarifa</code>
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.Tarifa
     * @see gov.sir.core.negocio.modelo.TipoTarifa
     */
    public List getTiposTarifasPorCirculo(Circulo c) throws HermodException {
        List rta = new ArrayList();
        try {
            Circulo circulo;
            CirculoPk cID = new CirculoPk();
            cID.idCirculo = c.getIdCirculo();
            circulo = factory.getSolicitudesDAO().getCirculoByID(cID);
            if (circulo == null) {
                throw new HermodException("El círculo NO existe: " + cID.idCirculo);
            }

            TipoTarifa aux;
            Tarifa tarifa;
            List tiposTarifaConfigurablesPorCirculo = factory.getTarifasDAO().getTiposTarifasConfiguradasPorCirculo();
            for (Iterator it = tiposTarifaConfigurablesPorCirculo.iterator(); it.hasNext();) {
                aux = (TipoTarifa) it.next();
                tarifa = factory.getTarifasDAO().getObjetoTarifa(aux.getIdTipo(), circulo.getNombre());
                if (tarifa != null) {
                    rta.add(tarifa);
                }
            }
            return rta;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TARIFAS_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el tipo de una fase.
     * <p>
     * El tipo de una fase puede ser Automático o Manual.
     *
     * @param fase_id el identificador de la fase de la cual se desea obtener su
     * tipo.
     * @return el tipo de fase (Automático o Manual) asociado con la fase.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.constantes.CFase;
     */
    public String getTipoFase(String fase_id) throws HermodException {
        try {
            return factory.getFasesDAO().getTipoFase(fase_id);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPO_FASE_NO_OBTENIDO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene una solicitud dado su identificador.
     * <p>
     * La solicitud debe incluir el listado de Liquidaciones asociadas.
     *
     * @param solicitud_id el identificador de la solicitud que se desea
     * obtener.
     * @return la solicitud con el id recibido como parámetro y su listado de
     * liquidaciones asociadas.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     */
    public Solicitud getSolicitudById(String solicitud_id) throws HermodException {
        try {
            return factory.getSolicitudesDAO().getSolicitudById(solicitud_id);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SOLICITUD_BY_ID_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene una lista de turnos dentro de un rango dado, que han pasado por
     * una fase dada.
     *
     * @param idFase identificador de la fase por la cual debe haber pasado el
     * turno.
     * @param turnoInicial identificador del turno inicial dentro del rango en
     * el que se va a realizar la consulta.
     * @param turnoFinal identificado del turno final dentro del ranqo en el que
     * se va a realizar la consulta
     * @return Lista de todos los turnos que han pasado por la fase dada y que
     * están comprendidos entre el rango de turnos dado.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public List getRangoTurnosByFase(String idFase, String turnoInicial, String turnoFinal) throws HermodException {
        try {
            return factory.getTurnosDAO().getRangoTurnosByFase(idFase, turnoInicial, turnoFinal);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.RANGO_TURNOS_BY_FASE_NO_OBTENIDO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Valida que la solicitud de certificado sea de tipo "ASOCIADO" y que tenga
     * un padre solicitud de registro que tenga como folio asociado el folio que
     * se pasa por parámetros
     *
     * @param solCer La <code>SolicitudCertificado</code> que se va a validar
     * @param folioID La <code>Folio.ID</code> Identificador del folio a validar
     * @see gov.sir.core.negocio.modelo.SolicitudCertificado
     * @throws <code>HermodException</code>
     */
    public void validarMatriculaMesaControl(SolicitudCertificado solCer, FolioPk folioID) throws HermodException {
        try {
            factory.getSolicitudesDAO().validarMatriculaMesaControl(solCer, folioID);
            return;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.VALIDAR_MATRICULA_MESA_CONTROL, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Modifica la categoría de clasificación de una solicitud de registro.
     *
     * @param clasificacion Valor que va a ser asignado al atributo categoría de
     * clasificación de la solicitud de registro.
     * @param turnoActualizado El turno que va a ser actualizado.
     * @return <code> true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @throws <code>HermodException </code>
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     */
    public boolean updateClasificacionSolicitudRegistro(String clasificacion, Turno turnoActualizado) throws HermodException {

        try {
            return factory.getTurnosDAO().updateClasificacionSolicitudRegistro(clasificacion, turnoActualizado);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CATEGORIA_CLASIFICACION_NO_MODIFICADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Hace persistente la información de un Pago, y lo asocia a una solicitud.
     * <p>
     * Método desarrollado para cumplir con los requerimientos específicos del
     * proceso de registro de documentos.
     *
     * @param pago El <code>Pago</code> que se va a hacer persistente.
     * @param estacion El identificador de la estación desde la cual se va a
     * asociar el <code>Pago</code>
     * @return <code> Pago </code> que se ha hecho persistente.
     * @throws <code>HermodException </code>
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     * @see gov.sir.core.negocio.modelo.Pago
     */
    public Pago registrarPago(Pago pago, String estacion) throws HermodException {
        try {
            return HermodDAOFactory.getFactory().getPagosDAO().registrarPago(pago, estacion);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.PAGO_NO_REGISTRADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el número de intentos permitidos para impresión.
     *
     * @param proceso El proceso para el cual se va a realizar la consulta.
     * @return El número de intentos permitidos para impresión.
     * @see gov.sir.core.negocio.modelo.constantes.CImpresion
     * @throws <code>HermodException</code>
     */
    public String getNumeroIntentosImpresion(String proceso) throws HermodException {
        try {
            if (proceso.equals(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES)) {
                return factory.getLookupDAO().getValor(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.NUMERO_INTENTOS_IMPRESION_CERTIFICADOS);
            }

            if (proceso.equals(CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION)) {
                return factory.getLookupDAO().getValor(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.NUMERO_INTENTOS_IMPRESION_CALIFICACION);
            }

            if (proceso.equals(CImpresion.IMPRIMIR_FOLIO)) {
                return factory.getLookupDAO().getValor(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.NUMERO_INTENTOS_IMPRESION_FOLIOS);
            }

            if (proceso.equals(CImpresion.IMPRIMIR_NOTA)) {
                return factory.getLookupDAO().getValor(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.NUMERO_INTENTOS_IMPRESION_NOTAS);
            }

            if (proceso.equals(CImpresion.IMPRIMIR_RECIBO)) {
                return factory.getLookupDAO().getValor(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.NUMERO_INTENTOS_IMPRESION_RECIBOS);
            }

        } catch (DAOException e) {

            HermodException he = new HermodException(HermodException.NUMERO_INTENTOS_IMPRESION_NO_OBTENIDO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }

        return null;

    }

    /**
     * Modifica el número de intentos permitidos para impresión.
     *
     * @param proceso El proceso para el cual se va a realizar la modificación.
     * @param valor El valor que va a ser asignado a la variable.
     * @return <code>true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @see gov.sir.core.negocio.modelo.constantes.CImpresion
     * @throws <code>HermodException</code>
     */
    public boolean updateNumeroIntentosImpresion(String proceso, String valor) throws HermodException {
        boolean respuesta = false;

        try {

            if (proceso == CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES) {
                respuesta = factory.getLookupDAO().updateLookupCode(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.NUMERO_INTENTOS_IMPRESION_CERTIFICADOS, valor);
            }

            if (proceso == CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION) {
                respuesta = factory.getLookupDAO().updateLookupCode(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.NUMERO_INTENTOS_IMPRESION_CALIFICACION, valor);
            }

            if (proceso == CImpresion.IMPRIMIR_FOLIO) {
                respuesta = factory.getLookupDAO().updateLookupCode(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.NUMERO_INTENTOS_IMPRESION_FOLIOS, valor);
            }

            if (proceso == CImpresion.IMPRIMIR_NOTA) {
                respuesta = factory.getLookupDAO().updateLookupCode(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.NUMERO_INTENTOS_IMPRESION_NOTAS, valor);
            }

            if (proceso == CImpresion.IMPRIMIR_RECIBO) {
                respuesta = factory.getLookupDAO().updateLookupCode(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.NUMERO_INTENTOS_IMPRESION_RECIBOS, valor);
            }

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NUMERO_INTENTOS_IMPRESION_NO_MODIFICADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }

        return respuesta;
    }

    /**
     * Obtiene el tiempo de espera configurado para el proceso de impresión.
     *
     * @param proceso El proceso para el cual se va a realizar la consulta.
     * @return el tiempo de espera configurado para el proceso de impresión.
     * @see gov.sir.core.negocio.modelo.constantes.CImpresion
     * @throws <code>HermodException</code>
     */
    public String getTiempoEsperaImpresion(String proceso) throws HermodException {
        try {
            if (proceso.equals(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES)) {
                return factory.getLookupDAO().getValor(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.TIEMPO_ESPERA_IMPRESION_CERTIFICADOS);
            }

            if (proceso.equals(CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION)) {
                return factory.getLookupDAO().getValor(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.TIEMPO_ESPERA_IMPRESION_CALIFICACION);
            }

            if (proceso.equals(CImpresion.IMPRIMIR_FOLIO)) {
                return factory.getLookupDAO().getValor(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.TIEMPO_ESPERA_IMPRESION_FOLIOS);
            }

            if (proceso.equals(CImpresion.IMPRIMIR_NOTA)) {
                return factory.getLookupDAO().getValor(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.TIEMPO_ESPERA_IMPRESION_NOTAS);
            }

            if (proceso.equals(CImpresion.IMPRIMIR_RECIBO)) {
                return factory.getLookupDAO().getValor(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.TIEMPO_ESPERA_IMPRESION_RECIBOS);
            }

        } catch (DAOException e) {

            HermodException he = new HermodException(HermodException.TIEMPO_ESPERA_IMPRESION_NO_OBTENIDO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }

        return null;

    }

    /**
     * Modifica el tiempo de espera configurado para el proceso de impresión.
     *
     * @param proceso El proceso para el cual se va a realizar la modificación.
     * @param valor El valor que va a ser asignado a la variable.
     * @return <code>true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @see gov.sir.core.negocio.modelo.constantes.CImpresion
     * @throws <code>HermodException</code>
     */
    public boolean updateTiempoEsperaImpresion(String proceso, String valor) throws HermodException {
        boolean respuesta = false;

        try {

            if (proceso == CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES) {
                respuesta = factory.getLookupDAO().updateLookupCode(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.TIEMPO_ESPERA_IMPRESION_CERTIFICADOS, valor);
            }

            if (proceso == CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION) {
                respuesta = factory.getLookupDAO().updateLookupCode(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.TIEMPO_ESPERA_IMPRESION_CALIFICACION, valor);
            }

            if (proceso == CImpresion.IMPRIMIR_FOLIO) {
                respuesta = factory.getLookupDAO().updateLookupCode(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.TIEMPO_ESPERA_IMPRESION_FOLIOS, valor);
            }

            if (proceso == CImpresion.IMPRIMIR_NOTA) {
                respuesta = factory.getLookupDAO().updateLookupCode(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.TIEMPO_ESPERA_IMPRESION_NOTAS, valor);
            }

            if (proceso == CImpresion.IMPRIMIR_RECIBO) {
                respuesta = factory.getLookupDAO().updateLookupCode(COPLookupTypes.VARIABLES_IMPRESION_HERMOD, COPLookupCodes.TIEMPO_ESPERA_IMPRESION_RECIBOS, valor);
            }

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIEMPO_ESPERA_IMPRESION_NO_MODIFICADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }

        return respuesta;
    }

    /**
     * Obtiene el estado de pago de una Solicitud.
     * <p>
     * Si la Solicitud tiene un <code>Pago </code> asociado, se retorna <code> true
     * </code>, en el caso contrario se retorna <code>false </code>
     *
     * @param solicitud La <code>Solicitud</code> en la cual se va a consultar
     * el estado de los pagos.
     * @return <code>true </code> si la solicitud ya tiene un <code>Pago</code>
     * o <code>false </code> en el caso contrario.
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.Pago
     * @throws <code>HermodException</code>
     */
    public boolean getEstadoPagoSolicitud(Solicitud solicitud) throws HermodException {
        try {
            return HermodDAOFactory.getFactory().getSolicitudesDAO().getEstadoPagoSolicitud(solicitud);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ESTADO_PAGO_SOLICITUD_NO_OBTENIDO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Crea un <code>Turno </code> de Registro y su respectiva instancia de
     * Workflow.
     * <p>
     * Si el turno tiene certificados asociados, también crea las instancias de
     * los turnos de certificados individuales.
     *
     * @param solicitud La <code>SolicitudRegistro</code> desde la cual se va a
     * generar el turno y su instancia de workflow.
     * @param usuarioSir <code>Usuario</code> que crea el turno.
     * @return El <code>Turno</code> que fue creado.
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>HermodException</code>
     */
    public Turno crearTurnoRegistro(SolicitudRegistro solicitud, Usuario usuarioSir) throws HermodException {
        try {
            return HermodDAOFactory.getFactory().getTurnosDAO().crearTurnoRegistro(solicitud, usuarioSir);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNO_REGISTRO_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Crea un <code>Turno</code> manual asociado a un pago. Este tipo de turnos
     * no genera instancia en Workflow.
     *
     * @param pPago El pago asociado al turno
     * @param sAnio El año del turno
     * @param sCirculo El círculo del turno
     * @param sProceso El proceso del turno
     * @param sIdTurno El identificador del turno
     * @param dFechaInicio La fecha de inicio para el turno
     * @param uUsuario El usuario que realiza el ingreso del turno
     * @return El <code>Turno</code> que fue creado.
     * @throws HermodException <code>HermodException</code>
     */
    public Turno crearTurnoManual(Pago pPago, String sAnio, String sCirculo, String sProceso,
            String sIdTurno, Date dFechaInicio, Usuario uUsuario) throws HermodException {

        try {
            return factory.getTurnosDAO().crearTurno(pPago, sAnio, sCirculo, sProceso, sIdTurno, dFechaInicio, uUsuario);
        } catch (DAOException deException) {
            HermodException he = new HermodException(HermodException.TURNO_MANUAL_NO_CREADO, deException);
            Log.getInstance().error(this.getClass(), he.getMessage(), deException);
            throw he;
        }
    }

    /**
     * Obtiene el listado de turnos calificados por un <code>Usuario</code> y
     * que no han pasado por la fase de firma del registrador.
     *
     * @param usuario El identificador del usuario del cual se están consultando
     * sus turnos calificados.
     * @param Circulo circulo del calificador
     * @return Lista con los turnos calificados por el <code>Usuario</code> dado
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>HermodException</code>
     */
    public List getTurnosByUsuarioCalificador(long usuario, Circulo circulo) throws HermodException {
        try {
            return HermodDAOFactory.getFactory().getTurnosDAO().getTurnosByUsuarioCalificador(usuario, circulo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNOS_CALIFICADOS_USUARIO_NO_OBTENIDOS, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el listado de turnos radicados en este dia y que se encuentren
     * actualmente en confrontación.
     *
     * @return Lista con los turnos validos
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>Throwable</code>
     */
    public List listarTurnosRegistroParaAgregarCertificadosAsociados(Circulo cir) throws HermodException {
        try {
            return HermodDAOFactory.getFactory().getTurnosDAO().listarTurnosRegistroParaAgregarCertificadosAsociados(cir);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNOS_VALIDOS_AGREGAR_CERTIFICADOS_ASOCIADOS_NO_OBTENIDOS, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega una <code>SolicitudFolio</code> a una SolicitudHija asociada a un
     * Turno.
     *
     * @param turno <code>Turno</code> que tiene asociada la Solicitud.
     * @param solicitud <code>Solicitud</code> a la cual se va a asociar la
     * <code>SolicitudFolio</code>
     * @param solFolio <code> <code>SolicitudFolio</code> que va a ser asociada.
     * @return Turno actualizado.
     * @see gov.sir.core.negocio.modelo.SolicitudFolio
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>HermodException</code>
     */
    public Turno addSolicitudFolioToSolicitudHija(Turno turno, Solicitud solcitud, SolicitudFolio solFolio) throws HermodException {
        return null;
    }

    /**
     * Agrega una <code>Solicitud</code> como SolicitudHija a la SolicitudPadre.
     *
     * @param solPadre <code>Solicitud</code> a la cual se va a asociar la
     * <code>Solicitud</code>
     * @param solHija <code> <code>Solicitud</code> que va a ser asociada.
     * @return Solicitud actualizada.
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @throws <code>HermodException</code>
     */
    public Solicitud addSolicitudHija(Solicitud solPadre, Solicitud solHija) throws HermodException {
        try {
            return factory.getSolicitudesDAO().addSolicitudHija(solPadre, solHija);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SOLICITUD_BY_ID_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Elimina una <code>SolicitudFolio</code> de una SolicitudHija asociada a
     * un Turno.
     *
     * @param turno <code>Turno</code> que tiene asociada la Solicitud.
     * @param solicitud <code>Solicitud</code> de la cual se va a eliminar la
     * <code>SolicitudFolio</code>
     * @param solFolio <code> <code>SolicitudFolio</code> que va a ser eliminada
     * @return Turno actualizado.
     * @see gov.sir.core.negocio.modelo.SolicitudFolio
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>HermodException</code>
     */
    public Turno removeSolicitudFolioFromSolicitudHija(Turno turno, Solicitud solcitud, SolicitudFolio solFolio) throws HermodException {
        return null;
    }

    /**
     * Modifica el atributo ajuste de una <code>SolicitudRegistro</code>
     *
     * @param turno <code>Turno</code> que tiene asociada la Solicitud.
     * @param valor el boolenao que va a ser asignado al atributo ajuste de la
     * <code>SolicitudRegistro</code>
     * @return <code>true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>HermodException</code>
     */
    public boolean updateAjusteInTurnoRegistro(Turno turno, boolean ajuste) throws HermodException {
        try {
            return factory.getTurnosDAO().updateAjusteInTurnoRegistro(turno, ajuste);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.AJUSTE_REGISTRO_NO_MODIFICADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorno un mapa en donde las llaves son los subtipos de atención y los
     * valores corresponden a la lista de los usuarios del círculo especificado
     * rotados
     *
     * @param circulo Identificador del círculo
     * @return Mapa [SubtipoAtencion, Lista usuarios en orden]
     * @throws HermodException
     */
    public Map getUsuariosPorSubtiposDeAtencionRotados(Circulo circulo) throws HermodException {
        try {
            return factory.getRepartoTurnosDAO().getUsuariosPorSubtiposDeAtencionRotados(circulo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ROTACION_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Este servicio permite agregar una liquidación, cuyo valor ha sido
     * ingresado por el usuario a una solicitud.
     *
     * @param solicitud La <code>Solicitud</code> a la cual se va a agregar la
     * liquidación.
     * @param liquidacion La <code>Liquidacion</code> que va a ser agregada a la
     * solicitud.
     * @return <code>true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.Liquidacion
     */
    public boolean addLiquidacionToSolicitud(Solicitud solicitud, Liquidacion liquidacion) throws HermodException {
        try {
            return factory.getLiquidacionesDAO().addLiquidacionToSolicitud(solicitud, liquidacion);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LIQUIDACION_NO_AGREGADA_TURNO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Este servicio permite agregar una liquidación, cuyo valor ha sido
     * ingresado por el usuario a una solicitud de certificados.
     *
     * @param solicitud La <code>SolicitudCertificado</code> a la cual se va a
     * agregar la liquidación.
     * @param liquidacion La <code>LiquidacionTurnoRegistro</code> que va a ser
     * agregada a la solicitud.
     * @return <code>LiquidacionTurnoCertificado</code> creada para la
     * solicitud.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudCertificado
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado
     */
    public LiquidacionTurnoCertificado addLiquidacionToSolicitudCertificado(SolicitudPk solicitudId, LiquidacionTurnoCertificado liquidacion) throws HermodException {
        try {
            return factory.getLiquidacionesDAO().addLiquidacionToSolicitudCertificado(liquidacion, solicitudId);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LIQUIDACION_NO_AGREGADA_TURNO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de turnos de fotocopias que se encuentren en la fase de
     * 'FOT_PAGO' durante más de n días (n es pasado por parámetros)
     *
     * @param circulo Círculo del cual se desea buscar los turnos
     * @param dias Número de dias
     * @return
     * @throws HermodException
     */
    public List getTurnosFotocopiasConPagoVencido(Circulo circulo, double dias)
            throws HermodException {
        try {
            return factory.getTurnosDAO().getTurnosFotocopiasConPagoVencido(circulo, dias);

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TURNOS_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el proceso asociado al id de la fase otorgado.
     *
     * @param idFase Id de la fase el cual se desea saber el proceso
     * @return proceso dado el id de la fase
     * @throws HermodException
     */
    public String getProcesoByIdFase(String idFase)
            throws HermodException {
        try {
            return factory.getFasesDAO().getProcesoByIdFase(idFase);

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.PROCESO_FASE_NO_ENCONTRADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#setRespuestaRecurso(gov.sir.core.negocio.modelo.Recurso)
     */
    public void setRespuestaRecurso(Recurso recurso) throws HermodException {
        try {

            factory.getTurnosDAO().setRespuestaRecurso(recurso);

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.PROCESO_FASE_NO_ENCONTRADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addRecurso(gov.sir.core.negocio.modelo.TurnoHistoria.TurnoHistoriaPk,
     * gov.sir.core.negocio.modelo.TipoRecurso.TipoRecursoPk, java.lang.String)
     */
    public RecursoPk addRecurso(Recurso recurso) throws HermodException {
        try {

            return factory.getTurnosDAO().crearRecurso(recurso);

        } catch (DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw e;
        }
    }

    public void updateRecurso(RecursoPk rid, String datoAmpliacion) throws HermodException {
        try {

            factory.getTurnosDAO().updateRecurso(rid, datoAmpliacion);

        } catch (DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#deleteActosBySolicitud(java.lang.String) 
     */
    public void deleteActosBySolicitud(String idSolicitud) throws HermodException {
        try{
            factory.getSolicitudesDAO().deleteActosBySolicitud(idSolicitud);
        } catch (DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw e;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#forceUnFolio(gov.sir.core.negocio.modelo.Solicitud,
     * gov.sir.core.negocio.modelo.Folio)
     */
    public void forceUnFolio(Solicitud solicitud, Folio folio) throws HermodException {
        try {

            factory.getSolicitudesDAO().forceUnFolio(solicitud, folio);

        } catch (DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Actualiza los documentos fotocopia asociados a la solicitud de fotocopia
     *
     * @param sol <code>SolicitudFotocopia</code> con identificador y sus
     * documentos fotocopia, cada uno con su nuevo tipo de fotocopia y su nuevo
     * número de hojas
     * @return La solicitud completa de fotocopia con sus documentos
     * actualizados
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudCertificado
     */
    public SolicitudFotocopia updateDocumentosFotocopia(SolicitudFotocopia sol) throws HermodException {
        try {
            return factory.getSolicitudesDAO().updateDocumentosFotocopia(sol);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_ACTUALIZACION, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Ejecuta el pago y obtiene la información para generar el recibo
     *
     * @param pago representa la información para realizar el <code>Pago</code>
     * @param impresora Impresora en la que debe imprimirse el certificado
     * generado.
     * @param delegarUsuario indica si el turno debe ser creado o no en la
     * estación recibida como parámetro.
     * @param estacion Estacion desde la cual se está creando el turno.
     * @param user Usuario que está creando el turno.
     * @param rol Rol del usuario que está creando el turno.
     * @return un objeto <code>Turno</code> con los datos correspondientes
     * @see gov.sir.core.negocio.modelo.Pago
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     * @throws <code>HermodException</code>
     */
    public Turno procesarPago(Pago pago, String estacion, String impresora, Usuario user, Rol rol, boolean delegarUsuario) throws HermodException {
        try {
            return factory.getPagosDAO().procesarPago(pago, estacion, impresora, user, rol, delegarUsuario);
        } catch (DAOException e) {

            HermodException he = new HermodException(DAOException.PAGO_INCORRECTO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Crea un <code>Turno </code> de Fotocopias y su respectiva instancia de
     * Workflow.
     *
     * @param solicitud La <code>SolicitudFotocopia</code> desde la cual se va a
     * generar el turno y su instancia de workflow.
     * @param usuarioSir <code>Usuario</code> que realiza el proceso.
     * @return El <code>Turno</code> que fue creado.
     * @see gov.sir.core.negocio.modelo.SolicitudFotocopia
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>HermodException</code>
     */
    public Turno crearTurnoFotocopias(SolicitudFotocopia solicitud, Usuario usuarioSir) throws HermodException {
        try {
            return HermodDAOFactory.getFactory().getTurnosDAO().crearTurnoFotocopias(solicitud, usuarioSir);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNO_FOTOCOPIAS_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Cambia el flag de aprobación de una solicitud de corrección o devolución
     *
     * @param solID Id de la solicitud
     * @param aprobada Nuevo estado del flag de aprobación
     * @return
     * @throws DAOException
     */
    public boolean setAprobacionSolicitud(SolicitudPk solID, boolean aprobada) throws HermodException {
        try {
            return factory.getSolicitudesDAO().setAprobacionSolicitud(solID, aprobada);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_ACTUALIZACION, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene un turno dado el identificador de su solicitud, si no tiene turno
     * asociado retorna null
     *
     * @param solID
     * @return
     * @throws HermodException
     */
    public Turno getTurnoBySolicitud(SolicitudPk solID) throws HermodException {
        try {
            return factory.getTurnosDAO().getTurnoBySolicitud(solID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNO_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
     /**
     * retorna verdadero si encontro algun dato de este documento pago en correccion forma pago
     * @param idDocumento
     * @return
     * @throws HermodException
     */
    public boolean validacionCorreccion(String idDocumento) throws HermodException {
        try {
            return factory.getPagosDAO().validacionCorreccion(idDocumento);
        } catch (DAOException e) {
            HermodException he = new HermodException("ERROR VALIDACION DOCUMENTO PAGO PARA CORRECCION FORMA PAGO", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el numero de recibo de un pago dado su identificador //Caso de
     * certificados asociados
     *
     * @param pID identificador del <code>Pago</code>
     * @return <code>String</code> con sus atributos
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.PagoPk
     */
    public String getNumReciboPagoByID(PagoPk pID) throws HermodException {
        try {
            return factory.getPagosDAO().getNumReciboPagoByID(pID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNO_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Crea un oficio, el oficio debe tener sus atributos básicos y una
     * asociación con un turno historia existente.
     *
     * @param oficio
     * @return
     * @throws HermodException
     */
    public OficioPk crearOficio(Oficio oficio) throws HermodException {
        try {
            return factory.getTurnosDAO().crearOficio(oficio);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_CREACION_OFICIO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Elimina oficios, el oficio debe tener sus atributos básicos y una
     * asociación con un turno historia existente.
     *
     * @param oficios
     * @return
     * @throws HermodException
     */
    public void eliminarOficios(List oficios) throws HermodException {
        try {
            factory.getTurnosDAO().eliminarOficios(oficios);
        } catch (DAOException e) {
            HermodException he = new HermodException("No se pudo eliminar la resolucion", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Actualiza la firma del oficio con el ID especificado en el flag indicado
     *
     * @param oficioID
     * @param firmado
     * @return
     * @throws HermodException
     */
    public boolean actualizarFirmaOficio(OficioPk oficioID, boolean firmado) throws HermodException {
        try {
            return factory.getTurnosDAO().actualizarFirmaOficio(oficioID, firmado);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_ACTUALIZACION, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Asocia un oficio respuesta a un recurso. Ambos deben existir en la base
     * de datos. El objeto queda en el atributo oficioRespuesta del recurso
     *
     * @param recursoID
     * @param oficioID
     * @return
     * @throws HermodException
     */
    public boolean setOficioRespuestaToRecurso(RecursoPk recursoID, OficioPk oficioID) throws HermodException {
        try {
            return factory.getTurnosDAO().setOficioRespuestaToRecurso(recursoID, oficioID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_ACTUALIZACION, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el valor de una variable, dado su lookupType y su LookupCode
     *
     * @param tipo El LookupType de la variable.
     * @param codigo El LookupCode de la variable.
     * @return El valor de la variable.
     * @throws HermodException
     */
    public String getValor(String tipo, String codigo) throws HermodException {
        try {
            return factory.getLookupDAO().getValor(tipo, codigo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_OBTENCION_DE_LOOKUPCODE, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene los oficios asociados al turno. Cada oficio tiene el turno
     * historia en el que fue creado
     *
     * @param oid
     * @return
     * @throws HermodException
     */
    public List getOficiosTurno(TurnoPk oid) throws HermodException {
        try {
            return factory.getTurnosDAO().getOficiosTurno(oid);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_OBTENCION_DE_OFICIOS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Actualiza el número del oficio con el ID especificado
     *
     * @param oficioID
     * @param firmado
     * @return
     * @throws HermodException
     */
    public boolean actualizarNumeroOficio(OficioPk oficioID, String nuevoNumero) throws HermodException {
        try {
            return factory.getTurnosDAO().actualizarNumeroOficio(oficioID, nuevoNumero);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_ACTUALIZACION, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega la fecha de firma al oficio con el ID especificado
     *
     * @param oficioID
     * @param fechaFirma
     * @return
     * @throws DAOException
     */
    public boolean agregarFechaFirmaOficio(OficioPk oficioID, Date fechaFirma) throws HermodException {
        try {
            return factory.getTurnosDAO().agregarFechaFirmaOficio(oficioID, fechaFirma);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_ACTUALIZACION, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de las estaciones a donde pasó un turno.
     *
     * @param fase Nombre de la fase a partir de donde se quiere saber.
     * @param idWF identificador del workflow del turno.
     * @return Lista de objetos de tipo String
     * @throws HermodException
     */
    public List getEstacionesActuales(String fase, String idWF) throws HermodException {
        try {
            return factory.getTurnosDAO().getEstacionesActuales(fase, idWF);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_ACTUALIZACION, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Devuelve el último usuario asignado a un turno en calificación que tenga
     * el folio con el ID folioID asociado
     *
     * @param folioID
     * @return
     * @throws HermodException
     */
    public Usuario getUsuarioConTurnoEnCalificacionConFolioAsociado(FolioPk folioID) throws HermodException {
        try {
            return factory.getTurnosDAO().getUsuarioConTurnoEnCalificacionConFolioAsociado(folioID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_OBTENCION_USUARIO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Verifica si un turno es válido entre los registros de ejecución de SAS
     *
     * @param idWorkflow
     * @return
     * @throws HermodException
     */
    public String lastAdministradorTurnoSAS(String idWorkflow) throws HermodException {
        try {
            return factory.getTurnosDAO().lastAdministradorTurnoSAS(idWorkflow);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNO_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Verifica si un turno es válido entre los registros de ejecución de SAS
     *
     * @param idWorkflow
     * @return
     * @throws HermodException
     */
    public boolean isValidTurnoSAS(String idWorkflow) throws HermodException {
        try {
            return factory.getTurnosDAO().isValidTurnoSAS(idWorkflow);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNO_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el listado de entidades públicas que intervienen como otorgantes,
     * en el proceso de reparto notarial
     *
     * @return Listado de entidades públicas que intervienen como otorgantes en
     * el proceso de reparto notarial.
     * @throws HermodException
     */
    public List getEntidadesReparto() throws HermodException {
        try {
            return factory.getRepartosDAO().getEntidadesReparto();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_ENTIDADES_PUBLICAS_REPARTO_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el listado de entidades públicas que intervienen como otorgantes,
     * en el proceso de reparto con una naturaleza juridica determinada.
     *
     * @return Listado de entidades públicas que intervienen como otorgantes en
     * el proceso de reparto notarial, que contienen la naturaleza jurídica
     * dada.
     * @throws Throwable
     */
    public List getEntidadesRepartoByNaturaleza(NaturalezaJuridicaEntidad naturalezaJuridicaReparto) throws HermodException {
        try {
            return factory.getRepartosDAO().getEntidadesRepartoByNaturaleza(naturalezaJuridicaReparto);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_ENTIDADES_PUBLICAS_REPARTO_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el listado de naturalezas jurídicas de las entidades públicas que
     * intervienen como otorgantes, en el proceso de reparto notarial
     *
     * @return Listado de entidades públicas que intervienen como otorgantes en
     * el proceso de reparto notarial.
     * @throws HermodException
     */
    public List getNaturalezasJuridicasEntidades() throws HermodException {
        try {
            return factory.getRepartosDAO().getNaturalezasJuridicasEntidades();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NATURALEZA_JURIDICA_ENTIDADES_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene los permisos de cerrección confirurados en el sistema
     *
     * @return Lista con objetos de tipo PermisoCorreccion
     * @throws HermodException
     */
    public List getPermisosCorreccion() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getPermisosCorreccion();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.PERMISOS_CORRECCION_NO_OBTENIDOS, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Setea el conjunto de permisos configurados de un turno
     *
     * @param turnoID
     * @param permisos
     * @return
     * @throws HermodException
     */
    public boolean asignarPermisosCorreccion(TurnoPk turnoID, List permisos) throws HermodException {
        try {
            return factory.getTurnosDAO().asignarPermisosCorreccion(turnoID, permisos);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.PERMISOS_CORRECCION_NO_ASIGNADOS, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Refresca el subtipo de atención del turno dependiendo de las nuevas
     * características de éste
     *
     * @param turnoID
     * @param permisos
     * @return
     * @throws HermodException
     */
    public boolean refrescarSubtipoAtencionTurno(TurnoPk turnoID) throws HermodException {
        try {
            return factory.getTurnosDAO().refrescarSubtipoAtencionTurno(turnoID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_REFRESCA_SUBTIPO_ATENCION, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el listado de Minutas pendientes por repartir dentro de un
     * Círculo Registral.
     *
     * @param circuloRegistral el <code>Circulo</code> en el cual se van a
     * buscar las minutas pendientes de reparto.
     * @return Lista de minutas por repartir dentro del <code>Círculo</code>
     * recibido como parámetro.
     * @throws <code>HermodException</code>
     */
    public List getMinutasNoRepartidasByCirculoRegistral(Circulo circuloRegistral) throws HermodException {

        try {
            return factory.getRepartosDAO().getMinutasNoRepartidasByCirculoRegistral(circuloRegistral);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.MINUTAS_NO_REPARTIDAS_NO_OBTENIDAS, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el listado de Minutas en las que aparece como otorgante una
     * persona natural.
     *
     * @param otorgante, nombre del otorgante que se quiere consultar dentro del
     * listado de minutas.
     * @param estado indica si la <code>Minuta</code> ha o no ha sido repartida.
     * @param circulo registral al cual pertenece el usuario en sesion
     * @return Lista de minutas en las que aparece el otorgante recibido como
     * parámetro.
     * @see gov.sir.core.negocio.modelo.constantes.CReparto
     * @throws <code>HermodException</code>
     */
    public List getMinutasByOtorganteNatural(String otorganteNatural, long estado, Circulo circuloRegistral) throws HermodException {

        try {
            return factory.getRepartosDAO().getMinutasByOtorganteNatural(otorganteNatural, estado, circuloRegistral);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.MINUTAS_POR_OTORGANTE_NO_OBTENIDAS, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el listado de Minutas en las que aparece como otorgante una
     * entidad pública.
     *
     * @param otorgantePublico, nombre del otorgante público que se quiere
     * consultar dentro del listado de minutas.
     * @param estado indica si la <code>Minuta</code> ha o no ha sido repartida.
     * @return Lista de minutas en las que aparece el otorgante recibido como
     * parámetro.
     * @see gov.sir.core.negocio.modelo.constantes.CReparto
     * @throws <code>HermodException</code>
     */
    public List getMinutasByOtorgantePublico(String otorgantePublico, long estado, Circulo circuloRegistral) throws HermodException {

        try {
            return factory.getRepartosDAO().getMinutasByOtorgantePublico(otorgantePublico, estado, circuloRegistral);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.MINUTAS_POR_OTORGANTE_NO_OBTENIDAS, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }

    }

    /**
     * Obtiene el listado de Minutas radicadas dentro de un rango de fechas
     * dado.
     *
     * @param fechaInicial fecha de inicio para el rango.
     * @param fechaFinal fecha de finalización para el rango.
     * @return Lista de minutas radicadas dentro del rango dado
     * @see gov.sir.core.negocio.modelo.constantes.CReparto
     * @throws <code>HermodException</code>
     */
    public List getMinutasRadicadasByRangoFecha(Date fechaInicial, Date fechaFinal) throws HermodException {
        try {
            return factory.getRepartosDAO().getMinutasRadicadasByRangoFecha(fechaInicial, fechaFinal);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.MINUTAS_RANGO_FECHAS_NO_OBTENIDAS, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el listado de Minutas repartidas dentro de un rango de fechas
     * dado.
     *
     * @param fechaInicial fecha de inicio para el rango.
     * @param fechaFinal fecha de finalización para el rango.
     * @param circulo registral del usuario
     * @return Lista de minutas repartidas dentro del rango dado
     * @throws <code>HermodException</code>
     */
    public List getMinutasRepartidasByRangoFecha(Date fechaInicial, Date fechaFinal, Circulo circuloRegistral) throws HermodException {
        try {
            return factory.getRepartosDAO().getMinutasRepartidasByRangoFecha(fechaInicial, fechaFinal, circuloRegistral);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.MINUTAS_RANGO_FECHAS_NO_OBTENIDAS, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el listado de Círculos Notariales, asociados con un Círculo
     * Registral.
     *
     * @param circulo <code>Circulo</code> del cual se van a obtener los
     * circulos notariales.
     * @return Lista de Círculos Notariales, asociados con un Círculo Registral.
     * @throws <code>HermodException</code>
     */
    public List getCirculosNotarialesByCirculoRegistral(Circulo circulo) throws HermodException {
        try {
            return factory.getRepartosDAO().getCirculosNotarialesByCirculoRegistral(circulo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CIRCULOS_NOTARIALES_CIRCULO_REGISTRAL_NO_OBTENIDOS, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Setea los datos de antiguo sistema a una solicitud de corrección. Si los
     * datos de antiguo sistema ya está seteados para la solicitud se reescriben
     *
     * @param sol Solicitud de corrección con el ID seteado
     * @param datosAntiguoSistema
     * @return
     * @throws HermodException
     */
    public boolean setDatosAntiguoSistemaToSolicitud(Solicitud sol, DatosAntiguoSistema datosAntiguoSistema) throws HermodException {
        try {
            return factory.getSolicitudesDAO().setDatosAntiguoSistemaToSolicitud(sol, datosAntiguoSistema);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.DATOS_ANTIGUO_SISTEMA_NO_SETEADOS, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene una hashtable con el estado de la cola de las notarías por
     * categoría. La llave es la categoría y el valor es la lista de oficinas
     * origen.
     *
     * @return
     * @throws HermodException
     */
    public Hashtable getColasRepartoByCategoria() throws HermodException {
        try {
            return factory.getRepartosDAO().getColasRepartoByCategoria();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.COLAS_REPARTO_NOTARIAL_NO_OBTENIDAS, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Permite agregar una Entidad Pública a la configuración del Sistema.
     *
     * @param entidadPublica <code>EntidadPublica</code> que va a ser
     * adicionada.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @throws <code>HermodException</code>
     */
    public boolean agregarEntidadPublica(EntidadPublica entidadPublica) throws HermodException {

        boolean respuesta = false;

        try {
            respuesta = factory.getRepartosDAO().addEntidadPublica(entidadPublica);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ENTIDAD_PUBLICA_NO_AGREGADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }

        return respuesta;

    }

    /**
     * Permite actualizar una Entidad Pública a la configuración del Sistema.
     *
     * @param entidadPublica <code>EntidadPublica</code> que va a ser
     * actualizado.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @throws <code>Throwable</code>
     */
    public boolean editarEntidadPublica(EntidadPublica entidadPublica) throws HermodException {

        boolean respuesta = false;

        try {
            respuesta = factory.getRepartosDAO().editarEntidadPublica(entidadPublica);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ENTIDAD_PUBLICA_NO_ACTUALIZADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }

        return respuesta;

    }

    /**
     * Permite eliminar una Entidad Pública de la configuración del Sistema.
     *
     * @param entidadPublica <code>EntidadPublica</code> que va a ser eliminada
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @throws <code>HermodException</code>
     */
    public boolean eliminarEntidadPublica(EntidadPublica entidadPublica) throws HermodException {
        try {
            return factory.getRepartosDAO().eliminarEntidadPublica(entidadPublica);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ENTIDAD_PUBLICA_NO_ELIMINADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Permite agregar una Naturaleza Jurídica de Entidad Pública a la
     * configuración del Sistema.
     *
     * @param naturaleza Naturaleza Jurídica de Entidad que va a ser adicionada.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @throws <code>HermodException</code>
     */
    public boolean agregarNaturalezaJuridicaEntidadPublica(NaturalezaJuridicaEntidad naturaleza) throws HermodException {

        boolean respuesta = false;

        try {
            respuesta = factory.getRepartosDAO().agregarNaturalezaJuridicaEntidadPublica(naturaleza);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NATURALEZA_JURIDICA_ENTIDAD_NO_AGREGADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
        return respuesta;
    }

    /**
     * Permite editar una Naturaleza Jurídica de Entidad Pública.
     *
     * @param naturaleza Naturaleza Jurídica de Entidad que va a ser editada.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @throws <code>HermodException</code>
     */
    public boolean editarNaturalezaJuridicaEntidadPublica(NaturalezaJuridicaEntidad naturaleza) throws HermodException {

        boolean respuesta = false;

        try {
            respuesta = factory.getRepartosDAO().editarNaturalezaJuridicaEntidadPublica(naturaleza);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NATURALEZA_JURIDICA_ENTIDAD_NO_EDITADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
        return respuesta;
    }
    
    /**
     * Retorna uun objeto tipo Documento Pago
     * de atencion
     *
     * @param String codigo de documento pago
     * @throws <code>HermodException</code>
     * @throws Throwable
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    public DocumentoPago getDocumentobyIdDocPago(String idDocpago) throws HermodException{
        try {
            return factory.getPagosDAO().getDocumentobyIdDocPago(idDocpago);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_DOCUMENTO_PAGO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    /**
     * Permite actualizar el estado de una Naturaleza Jurídica de Entidad
     * Pública para el reparto notarial.
     *
     * @param naturaleza Naturaleza Jurídica de Entidad que va a ser
     * actualizada.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @throws <code>Throwable</code>
     */
    public boolean actualizarEstadoNaturalezaJuridicaEntidadPublica(NaturalezaJuridicaEntidad naturaleza) throws HermodException {

        boolean respuesta = false;

        try {
            respuesta = factory.getRepartosDAO().actualizarEstadoNaturalezaJuridicaEntidadPublica(naturaleza);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NATURALEZA_JURIDICA_ENTIDAD_NO_EDITADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
        return respuesta;
    }

    /**
     * Permite eliminar una Naturaleza Jurídica de Entidad Pública de la
     * configuración del Sistema
     *
     * @param naturaleza Naturaleza Jurídica de Entidad que va a ser eliminada.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @throws <code>HermodException</code>
     */
    public boolean eliminarNaturalezaJuridicaEntidadPublica(NaturalezaJuridicaEntidad naturaleza) throws HermodException {
        try {
            return factory.getRepartosDAO().eliminarNaturalezaJuridicaEntidadPublica(naturaleza);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NATURALEZA_JURIDICA_ENTIDADES_NO_ELIMINADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Permite obtener el identificador de workflow de un turno asociado con una
     * minuta con id pasado como parámetro.
     *
     * @param idMinuta identificador de la minuta de la cual se desea obtener su
     * turno asociado.
     * @return identificador de workflow de un turno asociado con una minuta con
     * id pasado como parámetro.
     * @throws <code>HermodException </code>
     */
    public String getIdWorkflowByIdMinuta(String idMinuta) throws HermodException {
        String respuesta = null;

        try {
            respuesta = factory.getRepartosDAO().getIdWorkflowByIdMinuta(idMinuta);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ID_WORKFLOW_BY_ID_MINUTA_NO_OBTENIDO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
        return respuesta;
    }

    /**
     * Obtiene la lista con los actos que tienen un plazo para ser registrados
     * existentes en el sistema.
     *
     * @return Lista con objetos de tipo <code>OPLookupCodes</code> con actos
     * que tienen un plazo de vencimiento para ser registrados
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     * @throws <code>HermodException</code>
     */
    public List getActosQueVencen() throws HermodException {
        try {
            return factory.getLookupDAO().getLookupCodes(COPLookupCodes.ACTOS_QUE_VENCEN);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_OBTENCION_DE_LOOKUPCODE, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el plazo (dias) en el cual vencen los Actos para ser registrados
     *
     * @return String con la vigencua de los actos en dias.
     * @throws <code>HermodException</code>
     */
    public String getPlazoVencimientoRegistroActos() throws HermodException {
        try {
            return factory.getLookupDAO().getValor(COPLookupCodes.PLAZO_VENCIMIENTO_REGISTRO_ACTOS, COPLookupCodes.PLAZO_VENCIMIENTO_REGISTRO_ACTOS_DIAS);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_OBTENCION_DE_LOOKUPCODE, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Asigna un estado a la solicitud folio del turno y folio determinado.
     *
     * @param turnoID
     * @param folioID
     * @return
     * @throws HermodException
     */
    public boolean updateEstadoSolicitudFolio(SolicitudFolio solFolio) throws HermodException {
        try {
            return factory.getTurnosDAO().updateEstadoSolicitudFolio(solFolio);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SOLICITUD_FOLIO_ESTADO_NO_MODIFICADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Asigna una marca al folio dentro del turno especificado
     *
     * @param turnoID
     * @param folioID
     * @return
     * @throws HermodException
     */
    public boolean marcarFolioInTurno(TurnoPk turnoID, FolioPk folioID) throws HermodException {
        try {
            return factory.getTurnosDAO().marcarFolioInTurno(turnoID, folioID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_MARCA_FOLIO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Desmarca todos los folios asociados a un turno
     *
     * @param turnoID
     * @return
     * @throws DAOException
     */
    public boolean desmarcarFoliosInTurno(TurnoPk turnoID) throws HermodException {
        try {
            return factory.getTurnosDAO().desmarcarFoliosInTurno(turnoID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_MARCA_FOLIO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de alertas para la estación determinada. Si no se tiene
     * alertas se retorna una lista vacía. Si existen alertas se rebaja el
     * contador de alertas, si este llega a 0 se elimina la alerta.
     *
     * @param idEstacion
     * @return
     * @throws HermodException
     */
    public List getAlertas(String idEstacion) throws HermodException {
        try {
            return factory.getAlertasDAO().getAlertas(idEstacion);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_ALERTAS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Permite modificar los datos de una liquidación de registro de documentos.
     *
     * @author dlopez
     * @param nuevaLiquidacion Nueva liquidación que va a ser asociada a la
     * solicitud.
     * <p>
     * La nueva liquidación tiene asociada su respectiva solicitud.
     */
    public Liquidacion modificarLiquidacionRegistro(Liquidacion nuevaLiquidacion) throws HermodException {
        try {
            return factory.getLiquidacionesDAO().modificarLiquidacionRegistro(nuevaLiquidacion);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LIQUIDACION_REGISTRO_NO_MODIFICADA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
     /**
     * Retorna la cuenta destino enviandole el documenoPago.
     *
     * <p>
     * Retorna la cuenta destino enviandole el documenoPago
     */
    public String getNoCuentabyDocumentoPago(String idDocumentopago) throws HermodException {
        try {
            return factory.getPagosDAO().getNoCuentabyDocumentoPago(idDocumentopago);
        } catch (DAOException e) {
            HermodException he = new HermodException("NO TRAE LA CUENTA", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna un documento de pago registrado en la base de datos. El documento
     * de pago debe ser un cheque o una consignación. Si no encuentra un
     * documento de pago correspondiente retorna null
     *
     * @param doc
     * @return
     * @throws HermodException
     */
    public DocumentoPago getDocumentosPagoExistente(DocumentoPago doc) throws HermodException {
        try {
            return factory.getPagosDAO().getDocumentosPagoExistente(doc);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_DOCUMENTO_PAGO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Indica si el turno tiene por lo menos un acto del tipo indicado
     *
     * @param turnoID
     * @param tipoActoID
     * @return
     * @throws HermodException
     */
    public boolean hasActoTurnoRegistro(TurnoPk turnoID, TipoActoPk tipoActoID) throws HermodException {
        try {
            return factory.getTurnosDAO().hasActoTurnoRegistro(turnoID, tipoActoID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_HAS_ACTO_TURNO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Valida si la solicitud se puede asociar como solicitud vinculada
     *
     * @param solicitudID
     * @return
     * @throws HermodException
     */
    public boolean validarSolicitudVinculada(SolicitudPk solicitudID) throws HermodException {
        try {
            return factory.getSolicitudesDAO().validarSolicitudVinculada(solicitudID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_VALIDA_SOLICITUD_VINCULADA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Consulta las solicitudes que tengan el <code>Turno</code> como turno
     * anterior
     *
     * @param turnoID
     * @return
     * @throws HermodException
     */
    public List getSolicitudesByTurnoAnterior(Turno turno) throws HermodException {
        try {
            return factory.getSolicitudesDAO().getSolicitudesByTurnoAnterior(turno);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CONSULTA_SOLICITUDES_TURNO_ANTERIOR_NO_EFECTUADA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el listado de intereses jurídicos.
     *
     * @return Listado de intereses jurídicos.
     * @throws HermodException
     */
    public List getInteresesJuridicos() throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getInteresesJuridicos();
        } catch (DAOException e) {

            HermodException he = new HermodException(HermodException.LISTA_INTERESES_JURIDICOS_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la ruta física donde se encuentran las firmas de los
     * registradores.
     *
     * @return Ruta física donde se encuentra las firmas de los registradores.
     * @throws HermodException
     */
    public String getPathFirmasRegistradores() throws HermodException {
        try {
            HermodProperties hp = HermodProperties.getInstancia();
            String rutaFirmas = hp.getProperty(HermodProperties.HERMOD_RUTA_FIRMAS_REGISTRADORES);
            return rutaFirmas;
        } catch (Exception e) {

            HermodException he = new HermodException(HermodException.RUTA_FIRMAS_NO_ENCONTRADA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la ruta física donde se encuentran las firmas a asociar de los
     * registradores.
     *
     * @return Ruta física donde se encuentra las firmas de los registradores.
     * @throws HermodException
     */
    public String getPathFirmasRegistradoresAAsociar() throws HermodException {
        try {
            HermodProperties hp = HermodProperties.getInstancia();
            String rutaFirmas = hp.getProperty(HermodProperties.HERMOD_RUTA_FIRMAS_REGISTRADORES_A_ASOCIAR);
            return rutaFirmas;
        } catch (Exception e) {

            HermodException he = new HermodException(HermodException.RUTA_FIRMAS_NO_ENCONTRADA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el tipo de archivo que se usa para las impresiones
     *
     * @return Tipo de archivo para las impresiones
     * @throws HermodException
     */
    public String getFirmasContentType() throws HermodException {
        try {
            HermodProperties hp = HermodProperties.getInstancia();
            String contentType = hp.getProperty(HermodProperties.HERMOD_CONTENT_TYPE);
            return contentType;
        } catch (Exception e) {

            HermodException he = new HermodException(HermodException.CONTENT_TYPE_NO_ENCONTRADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el tipo de archivo que se usa para las impresiones
     *
     * @return Tipo de archivo para las impresiones
     * @throws HermodException
     */
    public String getUrlServletReportes() throws HermodException {
        try {
            HermodProperties hp = HermodProperties.getInstancia();
            String url = hp.getProperty(HermodProperties.URL_SERVLET_REPORTES);
            return url;
        } catch (Exception e) {

            HermodException he = new HermodException(HermodException.URL_SERVLET_REPORTES_NO_ENCONTRADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el número máximo de impresiones de certificados que se pueden
     * realizar
     *
     * @return Número máximo de impresiones de certificados
     * @throws HermodException
     */
    public String getNumeroMaximoImpresionesCertificados() throws HermodException {
        try {
            HermodProperties hp = HermodProperties.getInstancia();
            String numeroImpresiones = hp.getProperty(HermodProperties.HERMOD_NUMERO_IMPRESIONES_CERTIFICADOS);
            return numeroImpresiones;
        } catch (Exception e) {

            HermodException he = new HermodException(HermodException.NUMERO_IMPRESIONES_CERTIFICADOS_NO_ENCONTRADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el texto para los certificados Exentos
     *
     * @return texto para un certificado exento
     * @throws HermodException
     */
    public String getTextoExento() throws HermodException {
        try {
            HermodProperties hp = HermodProperties.getInstancia();
            String textoExento = hp.getProperty(HermodProperties.HERMOD_TEXTO_EXENTO);
            return textoExento;
        } catch (Exception e) {

            HermodException he = new HermodException(HermodException.TEXTO_EXENTO_NO_ENCONTRADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Elimina los modos de bloqueo y stack pos ingresados al avanzar push. De
     * acuerdo con el número de avances ingresado como parámetro.
     *
     * @return <code> true </code> o <code> false </code> dependiendo del
     * resultado de la operación.
     * @throws <code>HermodException</code>
     * @param turno El <code>Turno</code> sobre el cual se va a realizar la
     * actualización.
     * @param cantidad El número de operaciones avanzar push que debe
     * deshacerse.
     */
    public boolean deshacerAvancesPush(Turno turno, int cantidad) throws HermodException {
        try {
            return factory.getTurnosDAO().deshacerAvancesPush(turno, cantidad);
        } catch (DAOException e) {

            HermodException he = new HermodException(HermodException.DESHACER_AVANZAR_PUSH_NO_REALIZADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Asigna una marca al folio recién creado en antiguo sistema dentro del
     * turno especificado
     *
     * @param turnoID
     * @param folioID
     * @return
     * @throws HermodException
     */
    public boolean marcarFolioRecienCreadoASInTurno(TurnoPk turnoID, FolioPk folioID) throws HermodException {
        try {
            return factory.getTurnosDAO().marcarFolioRecienCreadoASInTurno(turnoID, folioID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_MARCA_FOLIO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Desmarca todos los folios recién creados en antiguo sistema asociados a
     * un turno
     *
     * @param turnoID
     * @return
     * @throws DAOException
     */
    public boolean desmarcarFoliosRecienCreadoASInTurno(TurnoPk turnoID) throws HermodException {
        try {
            return factory.getTurnosDAO().desmarcarFoliosRecienCreadoASInTurno(turnoID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_MARCA_FOLIO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Indica si la impresión de recibos asociados (Certificados) se imprimen en
     * la impresora local (Cajero) o se imprime en la impresora configurada para
     * los certificados por circulo
     *
     * @throws <code>HermodException</code>
     */
    public boolean isImpresionRecibosCertificadosImpresoraCajero(String nombreCirculo) throws HermodException {
        try {
            String valor = factory.getLookupDAO().getValor(COPLookupTypes.CONFIGURACION_IMPRESION, COPLookupCodes.IMP_RECIBOS_CERT_ASOC_LOCAL + "_" + nombreCirculo);
            return Boolean.valueOf(valor).booleanValue();
        } catch (DAOException e) {
            return false;
        }
    }

    /**
     * Valida previamente de avanzar el turno, si el turno tiene notas
     * informativas en la fase respectiva si está configurada la validación para
     * dicha fase
     *
     * @param fase
     * @param parametros
     * @param turno
     * @throws HermodException
     */
    public void validarNotaInformativaAvanceTurno(Fase fase, Hashtable parametros, Turno turno)
            throws HermodException {
        //Validacion sobre nota devolutiva para la fase ingresada
        String resultadoAvance = (String) parametros.get("RESULT");

        ValidacionNota validacionNota = factory.getVariablesOperativasDAO().getNotaDevolutiva(fase.getID(), resultadoAvance, turno.getIdProceso());

        //La combinación fase, proceso, resultado requiere nota informativa.
        //Obtener id del turno
        TurnoPk idTurno = new TurnoPk();
        idTurno.anio = turno.getAnio();
        idTurno.idCirculo = turno.getIdCirculo();
        idTurno.idProceso = turno.getIdProceso();
        idTurno.idTurno = turno.getIdTurno();

        if (validacionNota != null) { 
            if (!validacionNota.isValidaDetalle()) {
                boolean existeNota = factory.getTurnosDAO().hasNotaInLastFase(idTurno);
                if (!existeNota) {
                    throw new HermodException(HermodException.TURNO_SIN_NOTA);
                }
            } else {
                List notasTurno = factory.getTurnosDAO().getNotasInLastFase(idTurno);
                List tiposNota = validacionNota.getValidacionesNotaDetalle();
                boolean hasTipoNota = false;
                //no se valida si el turno tiene notas
                if (tiposNota == null) {
                    return;
                } else if (notasTurno != null) {
                    for (Iterator i = notasTurno.iterator(); i.hasNext();) {
                        Nota nota = (Nota) i.next();
                        for (Iterator j = tiposNota.iterator(); j.hasNext();) {
                            TipoNota tipoNota = ((ValidacionNotaDetalle) j.next()).getTipoNota();
                            if (nota.getTiponota() != null
                                    && tipoNota.getIdTipoNota() != null
                                    && tipoNota.getIdTipoNota().equals(nota.getTiponota().getIdTipoNota())) {
                                hasTipoNota = true;
                                continue;
                            }
                        }
                    }
                }
                if (!hasTipoNota) {
                    throw new HermodException(HermodException.TURNO_SIN_TIPO_NOTA);
                }
            }
        }
    }

    /**
     * Obtiene el listado de pantallas administrativas visibles para un
     * respectivo rol.
     *
     * @param roles Listado de roles asociados con el usuario.
     * @return Listado de pantallas administrativas visibles para el rol
     * recibido como parámetro.
     * @throws HermodException
     */
    public List obtenerAdministrativasPorRol(List roles) throws HermodException {
        try {
            List pantallasVisibles = factory.getVariablesOperativasDAO().obtenerAdministrativasPorRol(roles);
            return pantallasVisibles;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.PANTALLAS_ADMINISTRATIVAS_ROL_NO_OBTENIDAS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el listado de reportes visibles para un respectivo rol.
     *
     * @param roles Listado de roles asociados con el usuario.
     * @return Listado de pantallas administrativas visibles para el rol
     * recibido como parámetro.
     * @throws HermodException
     */
    public List obtenerPantallasPaginaReportesPorRol(List roles) throws HermodException {
        try {

            List pantallasVisibles
                    = factory.getVariablesOperativasDAO().obtenerPantallasPaginaReportesPorRol(roles);
            return pantallasVisibles;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.PANTALLAS_ADMINISTRATIVAS_ROL_NO_OBTENIDAS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        } // try

    } // end method

    /**
     * Obtiene el listado de tipos de pantallas administrativas existentes en la
     * aplicación.
     *
     * @return Listado de tipos de pantallas administrativas.
     * @throws HermodException
     */
    public List obtenerTiposPantallasAdministrativas() throws HermodException {
        try {
            List tiposPantalla = factory.getVariablesOperativasDAO().obtenerTiposPantallasAdministrativas();
            return tiposPantalla;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TIPOS_PANTALLAS_ADMINISTRATIVAS_NO_OBTENIDOS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /* (non-Javadoc)
         * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosByFechaYCirculo(gov.sir.core.negocio.modelo.Proceso, gov.sir.core.negocio.modelo.Fase, java.util.Date, gov.sir.core.negocio.modelo.Circulo)
     */
    public List getTurnosByFechaYCirculo(Proceso proceso, Fase fase, Date fecha, Circulo circulo) throws HermodException {
        try {
            return factory.getTurnosDAO().getTurnosByFechaYCirculo(proceso, fase, fecha, circulo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TURNOS_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }


    /* (non-Javadoc)
         * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosByFechaAndCirculoMinusMasivos(gov.sir.core.negocio.modelo.Proceso, gov.sir.core.negocio.modelo.Fase, java.util.Date, gov.sir.core.negocio.modelo.Circulo)
     */
    public List getTurnosByFechaAndCirculoMinusMasivos(Proceso proceso, Fase fase, Date fecha, Circulo circulo) throws HermodException {
        try {
            return factory.getTurnosDAO().getTurnosByFechaAndCirculoMinusMasivos(proceso, fase, fecha, circulo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TURNOS_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    public void setNumeroReciboPago(PagoPk pagoID, String numRecibo) throws HermodException {
        try {
            factory.getPagosDAO().setNumeroReciboPago(pagoID, numRecibo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NUMERO_RECIBO_PAGO_NO_ESTABLECIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de turnos que se encuentren en la fase de
     * 'PMY_REGISTRAR' durante más de 2 meses
     *
     * @param circulo Círculo del cual se desea buscar los turnos
     * @return
     * @throws HermodException
     */
    public List getTurnosConPagoMayorValorVencido(Circulo circulo)
            throws HermodException {
        try {
            return factory.getTurnosDAO().getTurnosConPagoMayorValorVencido(circulo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_LISTA_TURNOS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de turnos que se encuentren en la fase de
     * 'PMY_REGISTRAR', es decir, que están pendientes de pago
     *
     * @param circulo Círculo del cual se desea buscar los turnos
     * @return
     * @throws HermodException
     */
    public List getTurnosConPagoMayorValorPendiente(Circulo circulo)
            throws HermodException {

        try {
            return factory.getTurnosDAO().getTurnosConPagoMayorValorPendiente(circulo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_LISTA_TURNOS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /* (non-Javadoc)
         * @see gov.sir.hermod.interfaz.HermodServiceInterface#getPagoByNumeroRecibo(java.lang.String)
     */
    public Pago getPagoByNumeroRecibo(String numRecibo) throws HermodException {
        try {
            return factory.getPagosDAO().getUltimoPagoByNumeroRecibo(numRecibo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.PAGO_CON_NUMERO_RECIBO_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene un objeto <code>Pago</code> dado su identificador.
     *
     * @param pID identificador del <code>Pago</code>
     * @return <code>Pago</code> con sus atributos
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Pago
     */
    public Pago getPagoByID(PagoPk pID) throws HermodException {
        try {
            return factory.getPagosDAO().getPagoByID(pID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.PAGO_CON_NUMERO_RECIBO_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    public void addTurnoHistoriaToTurno(Turno turno, TurnoHistoria turnoHistoria) throws HermodException {
        try {
            factory.getTurnosDAO().addTurnoHistoria(turno, turnoHistoria, null);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNO_HISTORIA_NO_AGREGADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Actualiza en el modelo operativo la información del turno, el cuál debe
     * quedar en la fase de entrega.
     *
     * @param t
     * @param solicitud
     * @param user
     * @throws HermodException
     */
    public void actualizarTurnoCertificadoAsociado(Turno t, Hashtable solicitud, Usuario user) throws HermodException {
        try {
            factory.getTurnosDAO().actualizarTurnoCertificadoAsociado(t, solicitud, user);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ACTUALIZAR_TURNO_ASOCIADO_NO_EXITOSO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Método que permite actualizar el modo de bloqueo del turno al indicado en
     * la propiedad modoBloqueo del parámetro turno.
     *
     * @param turno
     * @throws HermodException
     */
    public void actualizarTurnoModoBloqueo(Turno turno) throws HermodException {
        try {
            factory.getTurnosDAO().actualizarTurnoModoBloqueo(turno);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ACTUALIZAR_TURNO_INFO_IMPRESION, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    public Proceso getProcesoById(gov.sir.core.negocio.modelo.ProcesoPk oid) throws HermodException {

        try {
            return factory.getProcesosDAO().getProcesoByID(oid);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.PROCESO_NO_ENCONTRADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el número de modificaciones permitidas en la edición de minutas
     * de reparto notarial
     *
     * @return el número de modificaciones permitidas en la edición de minutas
     * de reparto notarial
     * @throws <code>HermodException</code>
     */
    public int getNumModificacionesMinutas() throws HermodException {
        try {
            return factory.getRepartosDAO().getNumModificacionesMinutas();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NUMERO_MODIFICACIONES_MINUTA_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Modifica el atributo número de ediciones realizadas a una minuta
     *
     * @param minuta La minuta en la que se va a modificar el atributo.
     * @throws <code>HermodException</code>
     */
    public void updateNumModificacionesMinuta(Minuta minuta) throws HermodException {
        try {
            factory.getRepartosDAO().updateNumModificacionesMinuta(minuta);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NUMERO_MODIFICACIONES_MINUTA_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosCirculo(gov.sir.core.negocio.modelo.Proceso,
     * gov.sir.core.negocio.modelo.Circulo)
     */
    public List getTurnosCirculo(Proceso proceso, Circulo circulo, String idMatricula) throws HermodException {

        try {
            return factory.getTurnosDAO().getTurnosCirculo(proceso, circulo, idMatricula);
        } catch (DAOException de) {
            HermodException he = new HermodException(HermodException.LISTA_TURNOS_NO_OBTENIDA, de);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), de);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosCirculo(gov.sir.core.negocio.modelo.Circulo)
     */
    public List getTurnosCirculo(Circulo circulo, String idMatricula) throws HermodException {

        try {
            return factory.getTurnosDAO().getTurnosCirculo(circulo, idMatricula);
        } catch (DAOException de) {
            HermodException he = new HermodException(HermodException.LISTA_TURNOS_NO_OBTENIDA, de);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), de);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnos(java.lang.String)
     */
    public List getTurnos(String idMatricula) throws HermodException {

        try {
            return factory.getTurnosDAO().getTurnos(idMatricula);
        } catch (DAOException de) {
            HermodException he = new HermodException(HermodException.LISTA_TURNOS_NO_OBTENIDA, de);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(HermodService.class, he.getMessage());
            throw he;
        }
    }

    /**
     * Elimina del sistema las notas devolutivas asociadas con un turno.
     *
     * @param turno identificador del turno al cual se le van a eliminar las
     * notas devolutivas.
     * @throws Throwable
     */
    public void removeDevolutivasFromTurno(TurnoPk turnoID) throws HermodException {
        try {
            factory.getTurnosDAO().removeDevolutivasFromTurno(turnoID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.DEVOLUTIVAS_TURNO_NO_ELIMINADAS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Elimina del sistema las notas devolutivas asociadas con un turno.
     *
     * @param turno identificador del turno al cual se le van a eliminar las
     * notas devolutivas.
     * @throws Throwable
     */
    public double getValorOtroImpuestoTurnosAnteriores(TurnoPk turnoID) throws HermodException {
        try {
            return factory.getLiquidacionesDAO().getValorOtroImpuestoByTurno(turnoID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.OTRO_IMPUESTO_TURNOS_ANTERIORES, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addCertificadoAsociadoToTurno(gov.sir.core.modelo.Turno,
     * gov.sir.core.modelo.Turno)
     */
    public void addCertificadoAsociadoToTurno(Turno turnoRegistro, Turno turnoCertificado) throws HermodException {

        try {
            factory.getTurnosDAO().addCertificadoAsociado(turnoRegistro, turnoCertificado);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.OTRO_IMPUESTO_TURNOS_ANTERIORES, e);
            //ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#removeLiquidacionesSinPagoFromTurno(VeredaPk)
     */
    public void removeLiquidacionesSinPagoFromTurno(gov.sir.core.negocio.modelo.TurnoPk tid) throws HermodException {
        try {
            factory.getTurnosDAO().removeLiquidacionesSinPagoFromTurno(tid);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.PAGO_NO_REGISTRADO, e);
            //ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    public void eliminarRelacionTurno(gov.sir.core.negocio.modelo.TurnoPk tid) throws HermodException {
        try {
            factory.getTurnosDAO().eliminarRelacionTurno(tid);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.RELACION_NO_ELIMINADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTipoNota(gov.sir.core.negocio.modelo.TipoNota.TipoNotaPk)
     */
    public TipoNota getTipoNota(gov.sir.core.negocio.modelo.TipoNotaPk tid) throws HermodException {
        try {
            return factory.getTurnosDAO().getTipoNota(tid);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.PAGO_NO_REGISTRADO, e);
            //ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTiposRelacionesFase(String)
     */
    public List getTiposRelacionesFase(String idFase) throws HermodException {
        try {
            return factory.getRelacionesDAO().getTiposRelacionesFase(idFase);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener los tipos de relación disponibles para la fase", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTipoRelacion(gov.sir.core.negocio.modelo.TipoRelacion.TipoRelacionPk)
     */
    public TipoRelacion getTipoRelacion(gov.sir.core.negocio.modelo.TipoRelacionPk idTipoRelacion) throws HermodException {
        try {
            return factory.getRelacionesDAO().getTipoRelacion(idTipoRelacion);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener el tipo de relación", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosParaRelacion(Proceso,
     * Fase, Circulo, TipoRelacion)
     */
    public List getTurnosParaRelacion(Proceso proceso, Fase fase, Circulo circulo, TipoRelacion tipoRelacion) throws HermodException {
        try {
            return factory.getRelacionesDAO().getTurnosParaRelacion(proceso, fase, circulo, tipoRelacion);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener el tipo de relación", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosByRelacion(Proceso
     * proceso, Fase fase, Circulo circulo, String idRelacion)
     */
    public List getTurnosByRelacion(Proceso proceso, Fase fase, Circulo circulo, String idRelacion) throws HermodException {
        try {
            return factory.getRelacionesDAO().getTurnosByRelacion(proceso, fase, circulo, idRelacion);
        } catch (DAOException e) {
            HermodException he = new HermodException("No se pudieron consultar los turnos de la relación " + idRelacion, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosByRelacion(Proceso
     * proceso, Fase fase, Circulo circulo, String idRelacion)
     */
    public boolean validarFolioTurnoReanotacion(String idMatricula, Turno turno) throws HermodException {
        try {
            return factory.getTurnosDAO().validarFolioTurnoReanotacion(idMatricula, turno);
        } catch (DAOException e) {
            HermodException he = new HermodException("No se pudo hacer la validacion de reanotación a la matrícula " + idMatricula, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    public void reanotarTurno(Turno turno, Nota nota, Usuario calificador, Usuario usuario, Estacion estacion) throws HermodException {
        try {
            factory.getTurnosDAO().reanotarTurno(turno, nota, calificador, usuario, estacion);
        } catch (DAOException e) {
            HermodException he = new HermodException("No se pudo hacer la reanotación del turno " + turno.getIdWorkflow(), e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#crearRelacion(TipoRelacion,
     * Usuario)
     */
    public Relacion crearRelacion(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo) throws HermodException {
        try {
            return factory.getRelacionesDAO().crearRelacion(tipoRelacion, usuario, circulo);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener el tipo de relación", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#crearRelacion(TipoRelacion,
     * Usuario, List)
     */
    public Relacion crearRelacion(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos) throws HermodException {
        try {
            return factory.getRelacionesDAO().crearRelacion(tipoRelacion, usuario, circulo, turnos);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener el tipo de relación", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#crearRelacionNuevo(TipoRelacion,
     * Usuario, List, String)
     */
    public Relacion crearRelacionNuevo(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos, String respuesta) throws HermodException {
        try {
            return factory.getRelacionesDAO().crearRelacionNuevo(tipoRelacion, usuario, circulo, turnos, respuesta);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener el tipo de relación", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTurnoToRelacion(Relacion,
     * Turno)
     */
    public Relacion addTurnoToRelacion(Relacion relacion, Turno turno) throws HermodException {
        try {
            return factory.getRelacionesDAO().addTurnoToRelacion(relacion, turno);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener el tipo de relación", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#setNotaToRelacion(Relacion.RelacionPk,
     * String)
     */
    public void setNotaToRelacion(gov.sir.core.negocio.modelo.RelacionPk idRelacion, String nota) throws HermodException {
        try {
            factory.getRelacionesDAO().setNotaToRelacion(idRelacion, nota);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al establecer la nota en la relación", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getRelacion(gov.sir.core.negocio.modelo.Relacion.RelacionPk)
     */
    public Relacion getRelacion(gov.sir.core.negocio.modelo.RelacionPk idRelacion) throws HermodException {
        try {
            return factory.getRelacionesDAO().getRelacion(idRelacion);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener la relación", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#crearRelacion(TipoRelacion,
     * Usuario, Circulo, List, String)
     */
    public Relacion crearRelacion(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos, String idRelacion) throws HermodException {
        try {
            return factory.getRelacionesDAO().crearRelacion(tipoRelacion, usuario, circulo, turnos, idRelacion);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener la relación", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#crearRelacion(TipoRelacion,
     * Usuario, Circulo, List, String, String)
     */
    public Relacion crearRelacionNuevo(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos, String idRelacion, String respuesta) throws HermodException {
        try {
            return factory.getRelacionesDAO().crearRelacionNuevo(tipoRelacion, usuario, circulo, turnos, idRelacion, respuesta);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al obtener la relación", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * A partir de un identificador de relacion, busca el conjunto de
     * fasesProceso de las cuales tiene registros
     *
     * @return List< Fase >
     */
    public List buscarFasesRelacionadasPorRelacionId(String relacionId) throws HermodException {

        try {
            return factory.getRelacionesDAO().buscarFasesRelacionadasPorRelacionId(relacionId);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_PROCESOS_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#isValidarSesionImpresion()
     */
    public boolean isValidarSesionImpresion() throws HermodException {
        try {
            HermodProperties hp = HermodProperties.getInstancia();
            String validacion = hp.getProperty(HermodProperties.VALIDAR_SESION_IMPRESION);
            if (validacion == null || !validacion.equalsIgnoreCase("FALSE")) {
                return true;
            }
            return false;
        } catch (Exception e) {
            HermodException he = new HermodException("No se puedo obtener la propiedad de validación de sesiones de impresión", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosParaRelacion(Proceso,
     * Fase, Circulo, TipoRelacion, String)
     */
    public List getTurnosParaRelacion(Proceso proceso, Fase fase, Circulo circulo, TipoRelacion tipoRelacion, String idRelacion) throws HermodException {

        try {
            return factory.getRelacionesDAO().getTurnosParaRelacion(proceso, fase, circulo, tipoRelacion, idRelacion);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_PROCESOS_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnoDependiente(VeredaPk)
     */
    public Turno getTurnoDependiente(gov.sir.core.negocio.modelo.TurnoPk id) throws HermodException {

        try {
            return factory.getTurnosDAO().getTurnoDependiente(id);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_PROCESOS_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#crearTurnoDependiente(Turno,
     * Usuario, long)
     */
    public Turno crearTurnoDependiente(Turno turnoPadre, Usuario usuario, long idProceso) throws HermodException {

        try {
            return factory.getTurnosDAO().crearTurnoDependiente(turnoPadre, usuario, idProceso);
        } catch (DAOException e) {
            HermodException he = new HermodException("No fue posible crear un turno dependiente para el turno " + turnoPadre.getIdWorkflow() + ".", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene y avanza la secuencia de las constancias de devoluciones, de
     * acuerdo a los parametros recibidos.
     *
     * @param circuloId El identificador del <code>Circulo</code> en el que se
     * va a expedir la constancia de devolución.
     * @param year El año en el que se va a expedir la constancia de devolución.
     * @param pm Persistence Manager de la transacción.
     * @return El secuencial requerido.
     * @throws HermodException
     */
    public long getSecuencialDevolucion(String circuloId, String year)
            throws HermodException {

        try {
            return factory.getProcesosDAO().getSecuencialDevolucion(circuloId, year);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SECUENCIAL_DEVOLUCION_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addNotaActuacionToTurno(TurnoPk
     * turnoID, NotaActuacion notaActuacion)
     */
    public boolean addNotaActuacionToTurno(TurnoPk turnoID, NotaActuacion notaActuacion) throws HermodException {

        try {
            return factory.getTurnosDAO().agregarNotaActuacion(turnoID, notaActuacion);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ADICION_NOTAS_ACTUACION, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateNotaActuacionToTurno(TurnoPk
     * turnoID, NotaActuacion notaActuacion)
     */
    public boolean updateNotaActuacion(TurnoPk turnoID, NotaActuacion notaActuacion) throws HermodException {

        try {
            return factory.getTurnosDAO().actualizarNotaActuacion(turnoID, notaActuacion);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ADICION_NOTAS_ACTUACION, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene y avanza la secuencia de los recibos de certificados masivos, de
     * acuerdo a los parametros recibidos.
     *
     * @param circuloId El identificador del <code>Circulo</code> en el que se
     * va a expedir el recibo de certificados masivos.
     * @param year El año en el que se va a expedir el recibo de certificados
     * masivos.
     * @return El secuencial requerido.
     * @throws HermodException
     */
    public long getSecuencialMasivos(String circuloId, String year)
            throws HermodException {

        try {
            return factory.getProcesosDAO().getSecuencialMasivos(circuloId, year);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SECUENCIAL_MASIVOS_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @author : Diana Lora
     * @change : Obtiene la secuencia de los recibos de reparto notarial, de
     * acuerdo a los parametros recibidos.
     * @Caso Mantis : 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto
     * Notarial
     * @param year El año en el que se va a expedir el recibo de reparto
     * notarial.
     * @return El secuencial requerido.
     * @throws HermodException
     */
    public long getSecuencialRepartoNotarial(String circuloId, String year) throws HermodException {
        try {
            return factory.getProcesosDAO().getSecuencialRepartoNotarial(circuloId, year);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SECUENCIAL_REPARTO_NOTARIAL_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @author : Edgar Lora
     * @change : Obtiene la secuencia de los recibos de reparto notarial, de
     * acuerdo a los parametros recibidos.
     * @Caso Mantis : 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto
     * Notarial
     * @param year El año en el que se va a expedir el recibo de reparto
     * notarial.
     * @return El secuencial requerido.
     * @throws HermodException
     */
    @Override
    public long getSecuencialRepartoNotarialRecibo(String circuloId, String year) throws HermodException {
        try {
            return factory.getProcesosDAO().getSecuencialRepartoNotarialRecibo(circuloId, year);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SECUENCIAL_REPARTO_NOTARIAL_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @author : Julio Alcázar Rivas
     * @change : Obtiene y avanza la secuencia de los recibos de certificados
     * masivos, de acuerdo a los parametros recibidos.
     * @Caso Mantis : 000941
     * @param circuloId El identificador del <code>Circulo</code> en el que se
     * va a expedir el recibo de certificados masivos.
     * @param year El año en el que se va a expedir el recibo de certificados
     * masivos.
     * @return El secuencial requerido.
     * @throws HermodException
     */
    public long getSecuencialMasivosExento(String circuloId, String year)
            throws HermodException {

        try {
            return factory.getProcesosDAO().getSecuencialMasivosExento(circuloId, year);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SECUENCIAL_MASIVOS_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Modifica un objeto de tipo <code>CausalRestitucion</code> dentro de la
     * configuración del sistema.
     * <p>
     * El método genera una excepción si ya existe un causal de restitución con
     * el nombre del objeto pasado como parámetro.
     *
     * @param causal objeto de tipo <code>CausalRestitucion</code> con todos sus
     * atributos.
     * @return identificador del objeto <code>CausalRestitucion</code>
     * modificado.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.CausalRestitucion
     */
    public boolean editCausalRestitucion(CausalRestitucion causal) throws HermodException {

        try {

            CausalRestitucionPk causalId = factory.getVariablesOperativasDAO().editCausalRestitucion(causal);

            //Se realizó la modificación
            if (causalId != null) {
                return true;
            }
            return false;

        } catch (Throwable t) {
            HermodException he = new HermodException(HermodException.CAUSAL_RESTITUCION_NO_MODIFICADO, t);
            throw he;
        }
    }

    /**
     * Obtiene el listado de items de chequeo definidos en el sistema para las
     * solicitudes de devoluciones.
     *
     * @return Listado de items de chequeo definidos en el sistema para las
     * solicitudes de devoluciones.
     * @throws HermodException
     */
    public List getItemsChequeoDevoluciones()
            throws HermodException {

        try {
            return factory.getVariablesOperativasDAO().getItemsChequeoDevoluciones();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ITEMS_CHEQUEO_DEVOLUCIONES_NO_OBTENIDOS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el listado de turnos de restitución asociados con una minuta.
     *
     * @return listado de turnos de restitución asociados con una minuta.
     * @param idCir Círculo Registral asociado con la minuta
     * @param idMin Identificador de la minuta
     * @throws <code>HermodException</code>
     */
    public List getListadoTurnosRestitucionMinutas(String idCir, String idMin) throws HermodException {
        try {
            return factory.getRepartosDAO().getListadoTurnosRestitucionMinutas(idCir, idMin);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTADO_TURNOS_RESTITUCION_MINUTA_NO_OBTENIDO, e);
            throw he;
        }
    }

    /**
     * Obtiene el último turno generado por un usuario en un proceso especifico
     *
     * @param idUsuario
     * @param idProceso
     * @return
     * @throws HermodException
     */
    public Turno getUltimoTurnoProcesoUsuario(gov.sir.core.negocio.modelo.UsuarioPk idUsuario, gov.sir.core.negocio.modelo.ProcesoPk idProceso, CirculoPk idCirculo) throws HermodException {
        try {
            return factory.getTurnosDAO().getUltimoTurnoProcesoUsuario(idUsuario, idProceso, idCirculo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ULTIMO_TURNO_PROCESO_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    //getUltimaSolicitudLiquidacion
    
    /**
     * Obtiene el último turno generado por un usuario en un proceso especifico
     *
     * @param idUsuario
     * @param idProceso
     * @return
     * @throws HermodException
     */
    public String getUltimaSolicitudLiquidacion(gov.sir.core.negocio.modelo.UsuarioPk idUsuario, CirculoPk idCirculo) throws HermodException {
        try {
            return factory.getTurnosDAO().getUltimaSolicitudLiquidacion(idUsuario, idCirculo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ULTIMO_TURNO_PROCESO_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    
    /**
     * Obtiene el ultimo turno por usuario que haya registrado el pago de mayor
     * valor
     *
     * @param idUsuario
     * @param idProceso
     * @param idCirculo
     * @return
     * @throws HermodException
     */
    public Turno getUltimoTurnoMayorValorUsuario(gov.sir.core.negocio.modelo.UsuarioPk idUsuario, gov.sir.core.negocio.modelo.ProcesoPk idProceso, CirculoPk idCirculo) throws HermodException {
        try {
            return factory.getTurnosDAO().getUltimoTurnoMayorValorUsuario(idUsuario, idProceso, idCirculo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ULTIMO_TURNO_PROCESO_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Se incrementan los intentos de reimpresión de una solicitud específica
     *
     * @param idSolicitud
     * @throws HermodException
     */
    public void incrementarIntentoReimpresionRecibo(SolicitudPk idSolicitud) throws HermodException {
        try {
            factory.getSolicitudesDAO().incrementarIntentoReimpresionRecibo(idSolicitud);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ACTUALIZACION_INTENTO_REIMPRESION_NO_EFECTUADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega un circulo notarial
     *
     * @param circuloNotarial circulo notarial a agregar
     * @throws HermodException
     */
    public void agregarCirculoNotarial(CirculoNotarial circuloNotarial) throws HermodException {
        try {
            factory.getRepartosDAO().agregarCirculoNotarial(circuloNotarial);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.AGREGAR_CIRCULO_NOTARIAL_NO_EFECTUADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Elimina un circulo notarial
     *
     * @param circuloNotarial circulo notarial a eliminar
     * @throws HermodException
     */
    public void eliminarCirculoNotarial(CirculoNotarial circuloNotarial) throws HermodException {
        try {
            factory.getRepartosDAO().eliminarCirculoNotarial(circuloNotarial);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ELIMINAR_ZONA_NOTARIAL_NO_EFECTUADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Edita un circulo notarial
     *
     * @param circuloNotarial circulo notarial a editar
     * @throws Throwable
     */
    public void editarCirculoNotarial(CirculoNotarial circuloNotarial) throws HermodException {
        try {
            factory.getRepartosDAO().editarCirculoNotarial(circuloNotarial);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.EDITAR_CIRCULO_NOTARIAL_NO_EFECTUADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Consulta un circulo notarial
     *
     * @param circuloNotarial circulo notarial a consultar
     * @throws Throwable
     */
    public CirculoNotarial consultarCirculoNotarial(CirculoNotarialPk idCirculo) throws HermodException {
        try {
            return factory.getRepartosDAO().consultarCirculoNotarial(idCirculo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CIRCULO_NOTARIAL_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega una zona notarial
     *
     * @param zonaNotarial zona notarial a agregar
     * @throws Throwable
     */
    public void agregarZonaNotarial(ZonaNotarial zonaNotarial) throws HermodException {
        try {
            factory.getRepartosDAO().agregarZonaNotarial(zonaNotarial);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.AGREGAR_ZONA_NOTARIAL_NO_EFECTUADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Elimina una zona notarial
     *
     * @param zonaNotarial zona notarial a eliminar
     * @throws Throwable
     */
    public void eliminarZonaNotarial(ZonaNotarial zonaNotarial) throws HermodException {
        try {
            factory.getRepartosDAO().eliminarZonaNotarial(zonaNotarial);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ELIMINAR_ZONA_NOTARIAL_NO_EFECTUADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Crea una operación de englobe o segregación en la solicitud indicada
     *
     * @param operacion
     * @param solID
     * @return
     * @throws DAOException
     */
    public WebSegEngPk crearWebSegEng(WebSegEng operacion, SolicitudPk solID) throws HermodException {
        try {
            return factory.getWebSegEngDAO().crearWebSegEng(operacion, solID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.WEB_OPERACION_CREAR_NO_EFECTUADA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Elimina una operación de englobe o segregación dado su identificador
     *
     * @param operacionID
     * @return
     * @throws DAOException
     */
    public boolean eliminarWebSegEng(WebSegEngPk operacionID) throws HermodException {
        try {
            return factory.getWebSegEngDAO().eliminarWebSegEng(operacionID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.WEB_OPERACION_ELIMINAR_NO_EFECTUADA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Actualiza una operación de englobe o segregación dado su identificador y
     * nuevos datos
     *
     * @param operacionID
     * @param operacion
     * @return
     * @throws DAOException
     */
    public boolean actualizarWebSegEng(WebSegEngPk operacionID, WebSegEng operacion) throws HermodException {
        try {
            return factory.getWebSegEngDAO().actualizarWebSegEng(operacionID, operacion);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.WEB_OPERACION_ACTUALIZAR_NO_EFECTUADA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Consulta una operación de englobe o segregación dado su identificador
     *
     * @param operacionID
     * @return
     * @throws DAOException
     */
    public WebSegEng consultarWebSegEng(WebSegEngPk operacionID) throws HermodException {
        try {
            return factory.getWebSegEngDAO().consultarWebSegEng(operacionID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.WEB_OPERACION_CONSULTAR_NO_EFECTUADA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Consulta una operación de englobe o segregación dada su solicitud
     *
     * @param solicitudID
     * @return
     * @throws DAOException
     */
    public List getWebSegEngBySolicitud(SolicitudPk solicitudID) throws HermodException {
        try {
            return factory.getWebSegEngDAO().getWebSegEngBySolicitud(solicitudID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.WEB_OPERACION_CONSULTAR_NO_EFECTUADA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Agrega una notaria a la cola de reparto notarial
     *
     * @param notaria
     * @return
     * @throws Throwable
     */
    public void agregarNotariaReparto(OficinaOrigen notaria) throws HermodException {
        try {
            factory.getRepartosDAO().agregarNotariaReparto(notaria);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.AGREGAR_NOTARIA_REPARTO_NO_EFECTUADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obiene el texto imprimible
     *
     * @param idTexto identificador del texto
     * @return
     * @throws DAOException
     */
    public TextoImprimible getTextoImprimible(TextoImprimiblePk idTexto) throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getTextoImprimible(idTexto);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TEXTO_IMPRIMIBLE_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    public Turno getTurnoPadre(gov.sir.core.negocio.modelo.TurnoPk turnoID) throws HermodException {
        try {
            return factory.getTurnosDAO().getTurnoPadre(turnoID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_PROCESOS_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getImprimiblesPendientesByWfId(
     * java.lang.String, java.lang.String )
     */
    public List getImprimiblesPendientesByWfId(String turno_WfId, String tipoImprimibleId) throws HermodException {

        try {
            return factory.getTurnosDAO().getImprimiblesPendientesByWfId(turno_WfId, tipoImprimibleId);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_IMPRIMIBLESPENDIENTES_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }

    } // end-method

    public List getListaProcesosRelacion() throws HermodException {
        try {
            return factory.getProcesosDAO().getListaProcesosRelacion();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CONSULTA_LISTA_PROCESOS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Permite marcar o desmarcar el turno para indicar que al turno se le
     * interpuso un recurso o revocatoria directa.
     *
     * @param turno
     * @return
     * @throws Throwable
     */
    public boolean actualizarMarcaRevocatoriaTurno(Turno turno) throws HermodException {
        try {
            return factory.getTurnosDAO().actualizarMarcaRevocatoriaTurno(turno);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ACTUALIZAR_MARCA_REVOCATORIA_NO_EFECTUADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Realiza la consulta de los turnos que fueron bloqueados y que están para
     * ser reanotados.
     *
     * @param circulo
     * @return
     * @throws Throwable
     */
    public List consultarTurnosAReanotar(Circulo circulo) throws HermodException {
        try {
            return factory.getTurnosDAO().consultarTurnosAReanotar(circulo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.REANOTAR_TURNO_NO_EFECTUADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Permite colocar en la fase calificación, un turno que ya se encuentra
     * finalizado.
     *
     * @param turno
     * @param paramentros
     * @return
     * @throws Throwable
     */
    public boolean reanotarTurno(Turno turno, Hashtable parametros) throws HermodException {
        try {
            return factory.getTurnosDAO().reanotarTurno(turno, parametros);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.REANOTAR_TURNO_NO_EFECTUADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Método que permite actualizar el último turno historia de un turno para
     * su estado no sea activo..
     *
     * @param turno
     * @return
     * @throws Throwable
     */
    public void updateLastTurnoHistoria(Turno turno) throws HermodException {
        try {
            factory.getTurnosDAO().updateTurno(turno);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ACTUALIZAR_LAST_TURNO_HISTORIA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Método que permite actualizar el último turno historia para la
     * informacion de Reimpresion de Certificado
     *
     * @param turno
     * @return
     * @throws Throwable
     */
    public void updateTurnoReimpresionCertificado(Turno turno, Usuario usuario, Folio folio) throws HermodException {
        try {
            factory.getTurnosDAO().updateTurnoReimpresionCertificado(turno, usuario, folio);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ACTUALIZAR_TURNO_INFO_IMPRESION, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de turnos a partir de una estación y una fase. Este
     * método devuelve el turno con un turno historia en dónde se tiene la
     * información de la fase y la estación asignada.
     *
     * @param estacion <code>Estacion</code> sobre la cual se buscan los turnos.
     * @param fase <code>Fase</code> sobre la cual se buscan los turnos.
     * @return una lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Circulo
     * @see gov.sir.core.negocio.modelo.Usuario
     * @throws <code>Throwable</code>
     */
    public List getTurnosAReasignar(Estacion estacion, Fase fase) throws HermodException {
        try {
            return factory.getTurnosDAO().getTurnosAReasignar(estacion, fase);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TURNOS_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Actualiza la estación que tiene un turno por otra nueva estación.
     *
     * @param turno <code>Turno</code> sobre el cual se va a cambiar de estación
     * que lo tiene.
     * @param estacionDestino <code>Estacion</code> a la que va a quedar
     * asignado un turno.
     * @return una lista de objetos <code>Turno</code>
     * @throws <code>Throwable</code>
     */
    public boolean reasignarTurno(Turno turno, Estacion estacionDestino) throws HermodException {
        try {
            return factory.getTurnosDAO().reasignarTurno(turno, estacionDestino);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNOS_NO_REASIGNADOS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Actualiza la estación que tiene un turno por otra nueva estación.
     *
     * @param turno <code>Turno</code> sobre el cual se va a cambiar de estación
     * que lo tiene.
     * @param estacionDestino <code>Estacion</code> a la que va a quedar
     * asignado un turno.
     * @return una lista de objetos <code>Turno</code>
     * @throws <code>Throwable</code>
     */
    public boolean actualizarTurnoEjecucion(TurnoEjecucion turnoEjecucion) throws HermodException {
        try {
            return factory.getTurnosNuevosDAO().actualizarTurnoEjecucion(turnoEjecucion);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNO_EJECUCION_NO_ACTUALIZADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    public boolean getTurnoEjecucionTurnoIndividual(Estacion estacion, Fase fase, Circulo circulo, String idworkflow) throws HermodException {
        try {
            return factory.getTurnosNuevosDAO().getTurnoEjecucionTurnoIndividual(estacion, fase, circulo, idworkflow);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNO_EJECUCION_NO_ACTUALIZADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Método que permite actualizar la información en las tablas del modelo
     * operativo cuando se requiere ejecutar la reanotación de un turno de
     * registro de documentos.
     *
     * @param idTurno
     * @param notificationId
     * @param fase
     * @param resultado
     * @param estacionAsignada
     * @param usuarioSir
     * @return
     * @throws Throwable
     */
    public boolean reanotarTurnoModeloOperativo(String idTurno, String notificationId, String fase, String resultado, String estacionAsignada, Usuario usuarioSir)
            throws HermodException {
        try {
            return factory.getTurnosNuevosDAO().reanotarTurnoModeloOperativo(idTurno, notificationId, fase, resultado, estacionAsignada, usuarioSir);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.REANOTACION_NO_EJECUTADA_MODELO_OPERATIVO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Actualiza el usuario que atendió el último turno historia de un proceso
     * dado.
     *
     * @param turno <code>Turno</code> sobre el cuál se quiere actualizar el
     * turno historia.
     * @param nombreFase <code>String</code> sobre la cuál se quiere actualizar
     * el turno historia.
     * @param usuarioAtiende <code>Usuario</code> que atendió la fase dada.
     * @return <code>boolean</code> con la respuesta se se actualizó o nó el
     * turno.
     * @throws <code>Throwable</code>
     */
    public boolean actualizarUsuarioAtiendeTurnoHistoria(Turno turno, String nombreFase, Usuario usuarioAtiende) throws HermodException {
        try {
            return factory.getTurnosDAO().actualizarUsuarioAtiendeTurnoHistoria(turno, nombreFase, usuarioAtiende);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNOS_HISTORIA_NO_ACTUALIZADA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna un turno luego de haberlo creado en el modelo operativo.
     *
     * @param solicitud Solicitud asociada al turno.
     * @param usuarioSir <code>Usuario</code> que está creando el turno.
     * @param idEstacion Identificador de la estación desde la cual se está
     * creando el turno.
     * @return El <code>Turno</code> creado.
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Usuario
     * @throws <code>Throwable</code>
     */
    public Turno crearTurnoRepartoNotarial(SolicitudRepartoNotarial solicitud,
            String idEstacion, Usuario usuarioSir) throws HermodException {
        try {
            return factory.getTurnosNuevosDAO().crearTurnoRepartoNotarial(
                    solicitud, idEstacion, usuarioSir);
        } catch (DAOException e) {
            HermodException he = new HermodException(
                    HermodException.TURNO_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna una instancia de wf de un turno de reparto notarial luego de
     * haberlo creado en el modelo operativo.
     *
     * @param turno Turno asociado al turno.
     * @return El mensaje enviado por el WF luego de realizar la creación del
     * turno.
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Usuario
     * @throws <code>Throwable</code>
     */
    public Message crearWFTurno(Turno turno) throws HermodException {
        try {
            return factory.getTurnosNuevosDAO().crearWFTurno(turno);
        } catch (DAOException e) {
            HermodException he = new HermodException(
                    "Error creando instancia del WF para el turno", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la estación a la cual va a ser asignado un turno.
     *
     * @param m Message que contiene información del turno al cual se va a
     * asociar la estación.
     * @param idCirculo identificador del turno al cual se va a asociar la
     * estación.
     * @return El identificador de la estación a la cual debe ser asociado el
     * turno.
     */
    public String obtenerEstacionTurno(Hashtable m, String idCirculo)
            throws HermodException {
        try {
            return factory.getTurnosNuevosDAO().obtenerEstacionTurno(m,
                    idCirculo);
        } catch (DAOException e) {
            HermodException he = new HermodException(
                    "Error No se encontró una estación para asociar el turno",
                    e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Guarda la información de un turno en la tabla de Turno Ejecución.
     *
     * @param m Message que contiene información del turno del cual se va a
     * guardar la información.
     * @param idTurno identificador del turno del cual se va a guardar la
     * información.
     * @param usuarioSir el usuario responsable del avance o la creación del
     * turno.
     * @return El identificador de la estación a la cual debe ser asociado el
     * turno.
     */
    public void guardarInfoTurnoEjecucion(Hashtable m, String estacionAsignada,
            Turno turno, Usuario usuarioSir) throws HermodException {
        try {
            factory.getTurnosNuevosDAO().guardarInfoTurnoEjecucion(m,
                    estacionAsignada, turno, usuarioSir);
        } catch (DAOException e) {
            HermodException he = new HermodException(
                    "Error No fue posible guardar información del turno.", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Avanza un turno a la siguiente fase de acuerdo con los parámetros
     * recibidos.
     *
     * @param turno El <code>Turno</code> que va a ser avanzado.
     * @param fase La <code>Fase</code> en la que se encuentra el
     * <code>Turno</code>.
     * @param parametros Hashtable con los parametros necesarios para realizar
     * el avance.
     * @param usuario Usuario que está realizando el avance.
     * @return true o false dependiendo del resultado de la operación.
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.TurnoHistoria
     * @see gov.sir.core.negocio.modelo.constantes.CAvanzarTurno
     */
    public boolean avanzarTurnoNuevoNormal(Turno turno, Fase fase,
            Hashtable parametros, Usuario usuario) throws HermodException {

        // Validación notas informativasd
        String proceso;
        long idProceso;
        try {
            proceso = this.getProcesoByIdFase(fase.getID());
            idProceso = Long.parseLong(proceso);
        } catch (HermodException e) {
            throw new HermodException(e.getMessage(), e);
        } catch (NumberFormatException e1) {
            throw new HermodException(e1.getMessage(), e1);
        }

        // Validacion sobre nota devolutiva para la fase ingresada
        String resultadoAvance = (String) parametros.get("RESULT");

        boolean resultadoValidacion = factory.getVariablesOperativasDAO()
                .validarNotaDevolutiva(fase.getID(), resultadoAvance,
                        turno.getIdProceso());

        // La combinación fase, proceso, resultado requiere nota informativa.
        // Obtener id del turno
        TurnoPk idTurno = new TurnoPk();
        idTurno.anio = turno.getAnio();
        idTurno.idCirculo = turno.getIdCirculo();
        idTurno.idProceso = turno.getIdProceso();
        idTurno.idTurno = turno.getIdTurno();

        if (resultadoValidacion) {
            boolean existeNota = factory.getTurnosDAO().hasNotaInLastFase(
                    idTurno);

            if (!existeNota) {
                throw new HermodException(HermodException.TURNO_SIN_NOTA);
            }

        }

        try {
            return factory.getTurnosNuevosDAO().avanzarTurnoNuevoNormal(turno,
                    fase, parametros, usuario);
        } catch (DAOException e) {
            HermodException he = new HermodException(
                    HermodException.TURNO_NORMAL_NO_AVANZADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }

    }

    public List getTurnosNuevoByFechaYCirculo(Proceso proceso, Fase fase,
            Date fecha, Circulo circulo) throws HermodException {
        try {
            return factory.getTurnosNuevosDAO().getTurnosByFechaYCirculo(proceso,
                    fase, fecha, circulo);
        } catch (DAOException e) {
            HermodException he = new HermodException(
                    HermodException.LISTA_TURNOS_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    public boolean avanzarMasivoTurnosNuevo(List turnos, Fase fase, Hashtable parametros, Usuario usuario) throws HermodException {

        boolean rta = false;
        String idWF = null;
        try {
            Iterator it = turnos.iterator();
            while (it.hasNext()) {
                Turno t = (Turno) it.next();
                idWF = t.getIdWorkflow();
                t = this.getTurnobyWF(idWF);
                rta = this.avanzarTurnoNuevoNormal(t, fase, parametros, usuario);
            }
        } catch (DAOException e) {
            HermodException he = new HermodException(
                    HermodException.TURNO_CUALQUIERA_NO_AVANZADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
        return rta;

    }

    public boolean avanzarTurnoNuevoPush(Turno turno, Fase fase, Hashtable parametros, Usuario usuario) throws HermodException {

        try {
            return factory.getTurnosNuevosDAO().avanzarTurnoNuevoPush(turno, fase, parametros, usuario);
        } catch (DAOException e) {
            HermodException he = new HermodException(
                    HermodException.TURNO_PUSH_NO_AVANZADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }

    }

    public boolean avanzarTurnoNuevoPop(Turno turno, Fase fase, Hashtable parametros, Usuario usuario) throws HermodException {

        try {
            return factory.getTurnosNuevosDAO().avanzarTurnoNuevoPop(turno, fase, parametros, usuario);
        } catch (DAOException e) {
            HermodException he = new HermodException(
                    HermodException.TURNO_POP_NO_AVANZADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }

    }

    public boolean avanzarTurnoNuevoEliminandoPush(Turno turno, Fase fase, Hashtable parametros, Usuario usuario) throws HermodException {
        try {
            return factory.getTurnosNuevosDAO().avanzarTurnoNuevoEliminandoPush(turno, fase, parametros, usuario);
        } catch (DAOException e) {
            HermodException he = new HermodException(
                    HermodException.TURNO_ELIMINAR_PUSH_NO_AVANZADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    public boolean avanzarTurnoNuevoCualquiera(Turno turno, Fase fase, Hashtable parametros, Usuario usuario) throws HermodException {
        try {
            return factory.getTurnosNuevosDAO().avanzarTurnoNuevoCualquiera(turno, fase, parametros, usuario);
        } catch (DAOException e) {
            HermodException he = new HermodException(
                    HermodException.TURNO_CUALQUIERA_NO_AVANZADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Realiza el Reparto de las Minutas que se encuentran disponibles dentro
     * del Círculo al que pertenece el <code>Usuario</code>.
     *
     * @param circ <code>Circulo</code> al que pertenece el <code>Usuario</code>
     * @param usuario <code>Usuario</code> que realiza el reparto notarial.
     * @param tipo indica si el reparto es normal (false) o extraordinario
     * (true)
     * @param idExtraordinario identificador del turno en el que se debe
     * realizar un reparto extraordinario.
     * @return Hashtable con las asociaciones, número de Turno y notaría
     * asignada, para cada uno de los Turnos que fueron repartidos.
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.RepartoNotarial
     * @see gov.sir.core.negocio.modelo.Minuta
     */
    public Hashtable realizarRepartoExtraordinarioCirculo(Circulo circ, Usuario usuario, boolean tipo, String[] idsExtraordinarios) throws HermodException {
        try {
            return factory.getRepartosDAO().realizarRepartoCirculo(circ, usuario, tipo, idsExtraordinarios);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.REPARTO_CIRCULO_NO_REALIZADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    public RepartoNotarial getRepartoNotarialById(RepartoNotarialPk oid) throws HermodException {

        try {
            return factory.getRepartosDAO().getRepartoNotarialByID(oid);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.REPARTO_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna una lista de objetos<code>TurnoFolio</code> a partir de una
     * turno.
     *
     * @param turno Turno a partir del cuál se quiere consultar.
     * @return List de objetos<code>TurnoFolio</code>
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public List getTurnosFolioByTurno(Turno turno) throws HermodException {
        try {
            return factory.getMigracionSirDAO().getTurnosFolioByTurno(turno);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CONSULTA_TURNO_FOLIO_NO_EFECTUADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna una lista de objetos<code>SolicitudFolioMig</code> a partir de
     * una solicitud.
     *
     * @param solicitud Solicitud a partir del cuál se quiere consultar.
     * @return List de objetos<code>SolicitudFolioMig</code>
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     */
    public List getSolicitudFolioMigBySolicitud(Solicitud solicitud) throws HermodException {
        try {
            return factory.getMigracionSirDAO().getSolicitudFolioMigBySolicitud(solicitud);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CONSULTA_SOLICITUD_FOLIO_MIG_NO_EFECTUADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna una lista de objetos<code>TurnoFolio</code> a partir de un turno,
     * sólo se retornan si no existen registros en SIR_OP_SOLICITUD_FOLIO
     *
     * @param turno Turno a partir del cuál se quiere consultar.
     * @return List de objetos<code>TurnoFolio</code>
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public List getTurnosFolioNoMigrados(Turno turno) throws HermodException {
        try {
            return factory.getMigracionSirDAO().getTurnosFolioNoMigrados(turno);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CONSULTA_TRAMITE_TURNO_FOLIO_NO_EFECTUADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna una lista de objetos<code>TurnoFolioTramiteMig</code> a partir de
     * un turno, Estos registros representan los folios que estan en tramite en
     * un turno determinado en el sistema folio.
     *
     * @param turno Turno a partir del cuál se quiere consultar.
     * @return List de objetos<code>TurnoFolioTramiteMig</code>
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public List getTurnosFolioTramite(Turno turno) throws HermodException {
        try {
            return factory.getMigracionSirDAO().getTurnosFolioTramite(turno);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CONSULTA_TURNO_FOLIO_TRAMITE_NO_EFECTUADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna una lista de objetos<code>TurnoFolioTramiteMig</code> a partir de
     * un folio, Estos registros representan los folios que estan en tramite en
     * un turno determinado en el sistema folio.
     *
     * @param folio Folio a partir del cuál se quiere consultar.
     * @return List de objetos<code>TurnoFolioTramiteMig</code>
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Folio
     */
    public List getTurnosFolioTramite(Folio folio) throws HermodException {
        try {
            return factory.getMigracionSirDAO().getTurnosFolioTramite(folio);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CONSULTA_TURNO_FOLIO_TRAMITE_NO_EFECTUADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna un boolean con la información de si la creación de
     * TramiteTurnosFolioMig fue creada satisfactoriamente.
     *
     * @param tramiteTurnosFolioMig registros que se quieren insertar en la
     * tabla SIR_MIG_TRAMITE_TURNO_FOLIO.
     * @return boolean con información de si la operación fue exitosa
     * @throws <code>DAOException</code>
     */
    public boolean addTramiteTurnosFolioMigToTurno(List turnosFolioMig) throws HermodException {
        try {
            return factory.getMigracionSirDAO().addTramiteTurnosFolioMigToTurno(turnosFolioMig);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CREACION_TRAMITE_TURNO_FOLIO_MIG_NO_EFECTUADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna un boolean con la información de si la creación de TurnosFolioMig
     * fue creada satisfactoriamente.
     *
     * @param turno Turno a partir del cuál se quiere crear registros en la
     * tabla SIR_MIG_REL_TURNO_FOLIO.
     * @param turnosFolioMig registros que se quieren insertar en la tabla
     * SIR_MIG_REL_TURNO_FOLIO.
     * @return boolean con información de si la operación fue exitosa
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public boolean addTurnosFolioMigToTurno(Turno turno, List turnosFolioMig) throws HermodException {
        try {
            return factory.getMigracionSirDAO().addTurnosFolioMigToTurno(turno, turnosFolioMig);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CREACION_TURNO_FOLIO_MIG_NO_EFECTUADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna un boolean con la información de si la creación de
     * SolitudFolioMig fue creada satisfactoriamente.
     *
     * @param solicitud Solicitud a partir del cuál se quiere crear registros en
     * la tabla SIR_MIG_REL_SOLICITUD_FOLIO.
     * @param solicitudFolioMig registros que se quieren insertar en la tabla
     * SIR_MIG_REL_SOICITUD_FOLIO.
     * @return boolean con información de si la operación fue exitosa
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     */
    public boolean addSolicitudFolioMigToTurno(Solicitud solicitud, List solicitudFolioMig) throws HermodException {
        try {
            return factory.getMigracionSirDAO().addSolicitudFolioMigToTurno(solicitud, solicitudFolioMig);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ADICION_TURNO_FOLIO_NO_EFECTUADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna un boolean con la información de si la eliminación de
     * SolicitudFolioMig fue exitosa.
     *
     * @param solicitud Solicitud a partir del cuál se quiere crear registros en
     * la tabla SIR_MIG_REL_SOLICITUD_FOLIO.
     * @return boolean con información de si la operación fue exitosa
     * @throws <code>Throwable</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     */
    public boolean removeSolicitudFolioMig(Solicitud solicitud) throws HermodException {
        try {
            return factory.getMigracionSirDAO().removeSolicitudFolioMig(solicitud);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ELIMINACION_SOLICITUD_FOLIO_MIG_NO_EFECTUADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de turnos asociados a una estacion, un rol, un
     * <code>Usuario</code> una <code>Fase</code> y un <code>Proceso</code> si
     * estos tienen datos sin migrar como por ejemplo algún folio.
     *
     * @param estacion Estacion sobre la cual se buscan los turnos.
     * @param rol <code>Rol</code> sobre el cual se buscan los turnos.
     * @param usuario <code>Usuario</code> sobre el cual se buscan los turnos.
     * @param proceso <code>Proceso</code> sobre el cual se buscan los turnos.
     * @param fase <code>Fase</code> sobre la cual se buscan los turnos.
     * @param circulo <code>Circulo</code> donde pertenecen los turnos.
     * @return una lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.Proceso
     * @see gov.sir.core.negocio.modelo.Fase
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws <code>HermodException</code>
     */
    public List getTurnosSirMig(Estacion estacion, Rol rol, Usuario usuario, Proceso proceso, Fase fase, Circulo circulo)
            throws HermodException {
        try {
            return factory.getMigracionSirDAO().getTurnosSirMig(estacion, rol, usuario, proceso, fase, circulo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_TURNOS_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna un boolean con la información de si existe o no registros en la
     * tabla SIR_MIG_REL_TURNO_FOLIO a partir de un Folio.
     *
     * @param folio Folio a partir del cuál se quiere consultar registros en la
     * tabla SIR_MIG_REL_TURNO_FOLIO.
     * @return boolean con información de si existe o no registros
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.Folio
     */
    public boolean isFolioSirMigTurnoFolio(Folio folio) throws HermodException {
        try {
            return factory.getMigracionSirDAO().isFolioSirMigTurnoFolio(folio);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CONSULTA_TURNO_FOLIO_NO_EFECTUADA_BY_FOLIO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna un boolean con la información de si existe o no registros en la
     * tabla SIR_MIG_FOLIO_VALIDO a partir de un Folio.
     *
     * @param folio Folio a partir del cuál se quiere consultar registros en la
     * tabla SIR_MIG_REL_FOLIO_VALIDO.
     * @return boolean con información de si existe o no registros
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.Folio
     */
    public boolean isFolioValidoSirMig(Folio folio) throws HermodException {
        try {
            return factory.getMigracionSirDAO().isFolioValidoSirMig(folio);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CONSULTA_TURNO_FOLIO_NO_EFECTUADA_BY_FOLIO_MIG, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna un boolean con la información de si existe o no registros en la
     * tabla SIR_MIG_TRAMITE_TURNO_FOLIO a partir de un Folio.
     *
     * @param folio Folio a partir del cuál se quiere consultar registros en la
     * tabla SIR_MIG_TRAMITE_TURNO_FOLIO.
     * @return boolean con información de si existe o no registros
     * @throws <code>HermodException</code>
     * @see gov.sir.core.negocio.modelo.Folio
     */
    public boolean isFolioSirMigTurnoFolioTramite(Folio folio) throws HermodException {
        try {
            return factory.getMigracionSirDAO().isFolioSirMigTurnoFolioTramite(folio);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CONSULTA_TRAMITE_TURNO_FOLIO_NO_EFECTUADA_BY_FOLIO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el listado de <code>PantallaAdministrativa</code> cuyo atributo
     * pagina sea igual al parámetro pagina
     *
     * @param pagina
     * @return
     * @throws HermodException
     */
    public List getPantallasAdministrativasByPagina(String pagina)
            throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().getPantallasAdministrativasByPagina(pagina);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CONSULTA_PANTALLA_ADMINISTRATIVA_NO_EFECTUADA_BY_PAGINA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * inserta <code>RolPantalla</code> en la base de datos
     *
     * @param rolPantalla
     * @return
     * @throws HermodException
     */
    public RolPantalla addRolPantallasAdministrativa(RolPantalla rolPantalla)
            throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().addRolPantallasAdministrativa(rolPantalla);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.ADD_ROL_PANTALLA_ADMINISTRATIVA_NO_EFECTUADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * elimina <code>RolPantalla</code> de la base de datos
     *
     * @param rolPantalla
     * @return
     * @throws HermodException
     */
    public boolean deleteRolPantallasAdministrativa(RolPantalla rolPantalla)
            throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().deleteRolPantallasAdministrativa(rolPantalla);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.DELETE_ROL_PANTALLA_ADMINISTRATIVA_NO_EFECTUADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * inserta cada elemento <code>RolPantalla</code> de la lista rolesPantallas
     * en la base de datos
     *
     * @param rolesPantallas
     * @return
     * @throws DAOException
     */
    public void addRolesPantallasAdministrativas(List rolesPantallas)
            throws HermodException {
        try {
            factory.getVariablesOperativasDAO().addRolesPantallasAdministrativas(rolesPantallas);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.DELETE_ROL_PANTALLA_ADMINISTRATIVA_NO_EFECTUADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * inserta cada elemento <code>RolPantalla</code> de la lista rolesPantallas
     * en la base de datos
     *
     * @param rolesPantallas
     * @return
     * @throws DAOException
     */
    public boolean deleteRolPantallasAdministrativa(List rolesPantallas)
            throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().deleteRolPantallasAdministrativa(rolesPantallas);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.DELETE_ROL_PANTALLA_ADMINISTRATIVA_NO_EFECTUADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el listado de pantallas administrativas visibles para un
     * respectivo rol y una página de despligue de la pantalla administrativa.
     *
     * @param roles Listado de roles asociados con el usuario.
     * @param pagina pagina en la que se muestra la pantalla administrativa.
     * @return Listado de pantallas administrativas visibles para el rol
     * recibido como parámetro.
     * @throws HermodException
     */
    public List obtenerRolPantallasAdministrativasPorRolPagina(List roles, String pagina)
            throws HermodException {
        try {
            return factory.getVariablesOperativasDAO().obtenerRolPantallasAdministrativasPorRolPagina(roles, pagina);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CONSULTA_PANTALLA_ADMINISTRATIVA_NO_EFECTUADA_BY_PAGINA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Permite cambiar la respuesta en el historial de turnos del último turno
     * que tenga la fase especificada.
     *
     * @param turno
     * @fase fase del turno a cambiar
     * @respueta nuevo valor de la respuesta del turno
     * @return
     * @throws Throwable
     */
    public boolean modificarRespuestaUltimaFase(Turno turno, Fase fase, String respuesta)
            throws HermodException {
        try {
            return factory.getTurnosDAO().modificarRespuestaUltimaFase(turno, fase, respuesta);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.UPDATE_RESPUESTA_LAST_TURNO_HISTORIA_NO_EFECTUADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    public int getTamanioLista(String idListado, Object[] parametros)
            throws HermodException {
        try {
            return factory.getListadosPorPaginasDAO(idListado).consultarTamanioListado(idListado, parametros);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.UPDATE_RESPUESTA_LAST_TURNO_HISTORIA_NO_EFECTUADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    public List getListaPorRangos(String idListado, Object[] parametros, int rangoInf, int rangoSup)
            throws HermodException {
        try {
            return factory.getListadosPorPaginasDAO(idListado).consultarListadoPorRangos(idListado, parametros, rangoInf, rangoSup);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.UPDATE_RESPUESTA_LAST_TURNO_HISTORIA_NO_EFECTUADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    public boolean updateValorLiquidacionDevolucion(Liquidacion liquidacion) throws HermodException {
        try {
            return factory.getLiquidacionesDAO().updateValorLiquidacionDevolucion(liquidacion);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.VALOR_LIQUIDACION_DEVOLUCION_NO_ACTUALIZADA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Elimina las notas informativas asociadas al turno, solo elimina la última
     * nota de la fase en donde se encuentra el turno en estos momentos
     *
     * @param turnoID
     * @throws HermodException
     */
    public void eliminarNotasInformativasUltimaFaseTurno(TurnoPk turnoID) throws HermodException {
        try {
            factory.getTurnosDAO().eliminarNotasInformativasUltimaFaseTurno(turnoID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NOTAS_INFORMATIVAS_NO_ELIMINADAS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
     
    /**
     * Permite marcar o desmarcar el turno como turno de certificados nacional
     *
     * @param turno
     * @return
     * @throws Throwable
     */
    public boolean actualizarMarcaTurnoNacional(Turno turno) throws HermodException {
        try {
            return factory.getTurnosDAO().actualizarMarcaTurnoNacional(turno);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNO_NACIONAL_NO_ACTUALIZADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene el circulo configurado como circulo WEB
     *
     * @return Intacia de tipo gov.sir.core.negocio.modelo.Circulo.
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws <code>HermodException</code>
     */
    public Circulo getCirculoPortal() throws HermodException {
        Circulo circulo = null;
        try {
            List circulosLC = factory.getLookupDAO().getLookupCodes(COPLookupTypes.CIRCULO_PORTAL);
            if (circulosLC != null && !circulosLC.isEmpty()) {
                OPLookupCodes lc = (OPLookupCodes) circulosLC.iterator().next();

                circulo = new Circulo();
                circulo.setIdCirculo(lc.getValor());

            } else {
                throw new HermodException(HermodException.CIRCULO_PORTAL_NO_OBTENIDO);
            }
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.CIRCULO_PORTAL_NO_OBTENIDO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }

        return circulo;
    }

    public InicioProcesos obtenerFaseInicial(String idProceso) throws HermodException {
        InicioProcesos inicioProcesos = null;
        try {
            inicioProcesos = factory.getFasesDAO().obtenerFaseInicial(idProceso);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.INICIO_PROCESO_NO_OBTENIDO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
        return inicioProcesos;
    }

    public byte[] generarReporteJasper(String nombreReporte, String rutaReportes,
            HashMap parametrosJasper) throws HermodException {
        try {
            return factory.getReportesJasperDAO().generarReporteJasper(nombreReporte, rutaReportes, parametrosJasper);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.REPORTE_JASPER_NO_GENERADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(HermodService.class, he.getMessage(), e);
            throw he;
        }
    }

    public void addTurnoHistoriaToTurnoReasignacion(Turno turno, TurnoHistoria turnoHistoria) throws HermodException {
        try {
            factory.getTurnosDAO().addTurnoHistoriaReasignacion(turno, turnoHistoria);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNO_HISTORIA_NO_AGREGADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(HermodService.class, he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Modifica Pablo Quintana Junio 19 2008 Retorna un objeto Testamento según
     * el IdSolicitud
     */
    public Testamento getTestamentoByID(String idSolicitud) throws HermodException {
        try {
            return factory.getSolicitudesDAO().getTestamentoByID(idSolicitud);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TESTAMENTO_NO_ENCONTRADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(HermodService.class, he.getMessage(), e);
            throw he;
        }
    }

    /**
     * remueve un testadir de un testamento
     */
    public void removeTestadorFromTestamento(TestamentoCiudadanoPk testamentoCiudadanoID) throws HermodException {
        try {
            factory.getSolicitudesDAO().removeTestadorFromTestamento(testamentoCiudadanoID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TESTADOR_NO_ELIMINADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(HermodService.class, he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Retorna true si un pago se una consignacion pertenece a devoluciones
     *
     * @param idDocumentoPago
     * @return
     * @throws Throwable
     */
    public boolean existeConsignacionDevolucion(String idDocumentoPago) throws HermodException {
        try {
            return factory.getPagosDAO().existeConsignacionDevolucion(idDocumentoPago);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NO_DOCUMENTO_PAGO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Actualiza solicitud
     */
    public void actualizaSolicitud(Solicitud solicitud, List consignaciones) throws HermodException {
        try {
            factory.getSolicitudesDAO().actualizaSolicitud(solicitud, consignaciones);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.SOLICITUD_NO_ACTUALIZADA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Valida si un documento pago (consignacion o cheque) es valido en
     * devolución.
     */
    public boolean getDocsPagosDevolucion(DocumentoPago doc) throws HermodException {
        try {
            return factory.getPagosDAO().getDocsPagosDevolucion(doc);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.DOCUMENTO_PAGO_NO_VALIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Adiciona una relación banco-círculo (SIR_OP_BANCOS_X_CIRCULO)
     */
    public boolean addBancoCirculo(BancosXCirculo bancoXCirculo) throws HermodException {
        try {
            BancosXCirculoPk bancoXCirculoPk = factory.getBancosDAO().addBancoCirculo(bancoXCirculo);
            if (bancoXCirculoPk != null) {
                return true;
            }
            return false;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.BANCO_CIRCULO_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Consulta los bancos configurados para un determinado círculo
     */
    public List getBancosXCirculo(String idCirculo) throws HermodException {
        try {
            return factory.getBancosDAO().getBancosXCirculo(idCirculo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.BANCOS_X_CIRCULO_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Elimina un banco relacionado con un círculo (SIR_OP_BANCOS_X_CIRCULO)
     */
    public boolean eliminaBancoCirculo(BancosXCirculo bancoXCirculo) throws HermodException {
        try {
            if (factory.getBancosDAO().eliminaBancoCirculo(bancoXCirculo)) {
                return true;
            }
            return false;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.BANCO_CIRCULO_NO_ELIMINADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Método para activar un banco como principal (defecto) para una oficina
     */
    public boolean activarBancoPrincipal(BancosXCirculo bancoXCirculo) throws HermodException {
        try {
            if (factory.getBancosDAO().activarBancoPrincipal(bancoXCirculo)) {
                return true;
            }
            return false;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.BANCO_CIRCULO_NO_CREADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Consulta relación bancos círculo (SIR_OP_BANCOS_X_CIRCULO) para ser
     * cargada en contexto. Con ésta lista se cargan los bancos en las
     * diferentes modalidades de pago
     */
    public List getRelacionBancosCirculo() throws HermodException {
        try {
            return factory.getBancosDAO().getRelacionBancosCirculo();
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.BANCOS_X_CIRCULO_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Elimina una nota devolutiva de un turno
     *
     * @param turnoID
     * @param nota
     * @throws HermodException
     */
    public void removeNotaDevolutivaFromTurno(TurnoPk turnoID, Nota nota) throws HermodException {
        try {
            factory.getTurnosDAO().removeNotaDevolutivaFromTurno(turnoID, nota);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NOTA_DEVOLUTIVAS_TURNO_NO_ELIMINADS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * actualiza una nota devolutiva de un turno
     *
     * @param turnoID
     * @param nota
     * @throws HermodException
     */
    public void actualizaNotaDevolutiva(TurnoPk turnoID, Nota nota, Nota notaOld) throws HermodException {
        try {
            factory.getTurnosDAO().actualizaNotaDevolutiva(turnoID, nota, notaOld);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.NOTA_NO_CREADO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    public void updateSubtipoSolicitud(String idSubtipoSolicitud, String idSolicitud) throws HermodException {
        try {
            factory.getSolicitudesDAO().updateSubtipoSolicitud(idSubtipoSolicitud, idSolicitud);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error actualizando el subtipo de solicitud", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    public List getTurnosCertificadoPosteriores(String idMatricula, Turno turno) throws HermodException {
        List rta = null;
        try {
            rta = factory.getTurnosDAO().getTurnosCertificadoPosteriores(idMatricula, turno);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error actualizando el subtipo de solicitud", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        } catch (Exception e) {
            HermodException he = new HermodException("Error actualizando el subtipo de solicitud", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
        return rta;
    }

    /*Adiciona Funcionalidad Boton de Pago
        * Author: Ingeniero Diego Hernandez
        * Modificacion en: 2010/03/12 by jvenegas

	/**
	 * Metodo encargado de la creacion de turno de certificado masivo con tertificados individuales asociados de orden nacional
	 *
	 * @param transaccion
	 * @param solicitante
	 * @return Tabla con los turnos, el recibo de pago, y los identificadores de los certificados
	 * @throws HermodException
     */
    public Hashtable crearTurnoTransaccion(Transaccion transaccion, Ciudadano solicitante, String idUsuario, String idBanco) throws HermodException {
        Hashtable resultados = null;
        try {
            UsuarioPk uID = new UsuarioPk();
            uID.idUsuario = Long.parseLong(idUsuario);
            Usuario usuarioSIR = fenrir.getFenrirDAO().getUsuarioByID(uID);

            Rol rol = fenrir.getFenrirDAO().getRolByID(CRol.SIR_ROL_CAJERO_CERT_MASIVOS);

            /**
             * @author Daniel Forero
             * @change REQ 1156 - Filtro de estaciones activas por rol y
             * usuario.
             */
            List estaciones = fenrir.getFenrirDAO().getEstacionesUsuarioByEstadoRol(usuarioSIR, rol, true);
            Estacion estacion = (Estacion) estaciones.get(0);

            resultados = factory.getTurnosPortalDAO().crearTurnoTransaccion(transaccion, solicitante, rol, estacion, usuarioSIR,
                    this.getPathFirmasRegistradores(), idBanco);

        } catch (Exception e) {
            HermodException he = new HermodException("No se pudo crear el turno por internet", e);
            throw he;
        } catch (Throwable t) {
            HermodException he = new HermodException("No se pudo crear el turno por internet", t);
            throw he;
        }
        return resultados;
    }

    /*Adiciona Funcionalidad Boton de Pago
 * Author: Ingeniero Diego Hernandez
 * Modificacion en: 2010/03/12 by jvenegas


        /**
	 * Metodo encargado de procesar el pago por ventanilla unica de registro
	 *
	 * @param transaccion
	 * @param solicitante
	 * @param idBanco
	 * @return Tabla con los turnos, el recibo de pago, y los identificadores de los certificados
	 * @throws HermodException
     */
    public Hashtable procesarPagoVUR(Transaccion transaccion, Ciudadano solicitante, String idBanco, String idUsuarioSIR, String cedula) throws HermodException {
        Hashtable resultados = null;
        try {
            UsuarioPk uID = new UsuarioPk();
            uID.idUsuario = Long.parseLong(idUsuarioSIR);
            Usuario usuarioSIR = fenrir.getFenrirDAO().getUsuarioByID(uID);

            Rol rol = fenrir.getFenrirDAO().getRolByID(CRol.SIR_ROL_CAJERO_CERTIFICADOS);

            List estaciones = fenrir.getFenrirDAO().getEstaciones(usuarioSIR, rol);
            Estacion estacion = (Estacion) estaciones.get(0);

            resultados = factory.getTurnosPortalDAO().procesarPagoVUR(transaccion, solicitante, rol, estacion, usuarioSIR, this.getPathFirmasRegistradores(), idBanco, cedula);

        } catch (Exception e) {
            HermodException he = new HermodException("No se pudo crear el turno por internet", e);
            throw he;
        } catch (Throwable t) {
            HermodException he = new HermodException("No se pudo crear el turno por internet", t);
            throw he;
        }
        return resultados;
    }


    /*Adiciona Funcionalidad Boton de Pago
         * Author: Ingeniero Diego Hernandez
         * Modificacion en: 2010/03/12 by jvenegas

	/**
	 * @param idImprimible
	 * @return el imprimible del certificado
	 * @throws HermodException
     */
    public ImprimibleCertificado getImprimible(Integer idImprimible) throws HermodException {
        ImprimibleCertificado imprimible = null;
        try {
            ImprimiblePk id = new ImprimiblePk();
            id.idImprimible = idImprimible.intValue();
            Imprimible imp = impresion.getImpresionDAO().getImprimible(id);
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(imp.getDatosImprimible());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Bundle bundle = (Bundle) ois.readObject();
                imprimible = (ImprimibleCertificado) bundle.getImprimible();

            } catch (Exception e) {
            }
        } catch (Exception e) {
            throw new HermodException(e.getMessage(), e);
        }
        return imprimible;
    }

    /**
     * @param idImprimible
     * @return el imprimible del certificado
     * @throws HermodException
     */
    public ImprimibleRecibo getRecibo(Integer idImprimible) throws HermodException {
        ImprimibleRecibo imprimible = null;
        try {
            ImprimiblePk id = new ImprimiblePk();
            id.idImprimible = idImprimible.intValue();
            Imprimible imp = impresion.getImpresionDAO().getImprimible(id);
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(imp.getDatosImprimible());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Bundle bundle = (Bundle) ois.readObject();
                imprimible = (ImprimibleRecibo) bundle.getImprimible();
            } catch (Exception e) {
            }
        } catch (Exception e) {
            throw new HermodException(e.getMessage(), e);
        }
        return imprimible;
    }

    /*Adiciona Funcionalidad Boton de Pago
 * Author: Ingeniero Diego Hernandez
 * Modificacion en: 2010/02/23 by jvenegas
 * /
 /* Obtiene un turno dado el identificador de su solicitud, si no tiene turno
		* asociado retorna null
		* @param solID
		* @return
		* @throws HermodException
     */
    public Turno getTurnoBySolicitudPortal(SolicitudPk solID) throws HermodException {
        try {
            return factory.getTurnosDAO().getTurnoBySolicitudPortal(solID);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNO_NO_OBTENIDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }


    /*Adiciona Funcionalidad Boton de Pago
 * Author: Ingeniero Diego Hernandez
 * Modificacion en: 2010/03/12 by jvenegas
 	* Obtiene datos de impresion
		*  retorna arreglo caracter
		* @param solID
		* @return
		* @throws HermodException
     */

 /* Modificado 2010-03-16 by jvenegas para Boton de Pago */
    public byte[] getDatosImprimible(Integer idImprimible) throws HermodException {
        byte[] imprimible = null;

        try {
            Log.getInstance().debug(this.getClass(), "valor idImprimible =" + idImprimible);

            ImprimiblePk id = new ImprimiblePk();
            id.idImprimible = idImprimible.intValue();
            imprimible = impresion.getImpresionDAO().getImprimibleBytes(id);

        } catch (Exception e) {
            throw new HermodException(e.getMessage(), e);
        }
        return imprimible;
    }

    /**
     * Obtiene los turnos de devolución asociados con el turno ingresado como
     * parámetro.
     *
     * @author: Julio Alcazar
     * @change: 7393: Acta - Requerimiento No 215 - Ver Detalles de Turno -
     * Turnos Devolucion Negados
     * @param Turno Identificador del turno del cual se consultarán los turnos
     * siguientes.
     * @return lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>HermodException</code>
     */
    public List getTurnosDevolucion(Turno turno) throws HermodException {
        try {
            return factory.getTurnosDAO().getTurnosDevolucion(turno);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.TURNOS_DEVOLUCION_NO_OBTENIDOS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @author : Julio Alcazar
     * @change : Se consulta la informacion origen de una matricula trasladada
     * Caso Mantis : 0007676: Acta - Requerimiento No 247 - Traslado de Folios
     * V2
     */
    public Traslado getFolioOrigenTraslado(String matricula) throws HermodException {
        try {
            return factory.getTrasladoTurnosDAO().getFolioOrigenTraslado(matricula);
        } catch (DAOException e) {
            HermodException he = new HermodException("Fallo en el servicio para obtener la informacion de traslado: ", e);
            he.setHashErrores(e.getHashErrores());
            Log.getInstance().error(HermodService.class, he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @author : Julio Alcazar
     * @change : Se consulta la informacion destino de una matricula trasladada
     * Caso Mantis : 0007676: Acta - Requerimiento No 247 - Traslado de Folios
     * V2
     */
    public Traslado getFolioDestinoTraslado(String matricula) throws HermodException {
        try {
            return factory.getTrasladoTurnosDAO().getFolioDestinoTraslado(matricula);
        } catch (DAOException e) {
            HermodException he = new HermodException("Fallo en el servicio para obtener la informacion de traslado: ", e);
            he.setHashErrores(e.getHashErrores());
            Log.getInstance().error(HermodService.class, he.getMessage(), e);
            throw he;
        }
        /**
         * @author : Julio Alcazar
         * @change : Se consulta la informacion origen de una matricula
         * trasladada Caso Mantis : 0007676
         */
    }

    /**
     * @author : Carlos Mario Torre Urina
     * @casoMantis :11309: Acta - Requerimiento No 023_453 -
     * Traslado_Masivo_Folios
     * @change : Obtiene el objeto SolicitudFolio identificado sfid
     */
    public SolicitudFolio getSolicitudFolio(SolicitudFolioPk sfid) throws HermodException {
        try {
            SolicitudFolioEnhanced solf = factory.getSolicitudesDAO().getSolicitudFolio(sfid);
            Object resl = solf.toTransferObject();
            return (SolicitudFolio) resl;
        } catch (DAOException e) {
            HermodException he = new HermodException("Fallo en el servicio para optener SolicitudFolio: ", e);
            he.setHashErrores(e.getHashErrores());
            Log.getInstance().error(HermodService.class, he.getMessage(), e);
            throw he;

        }
    }

    /**
     * @author : Carlos Torres
     * @change : Se agrega fase en el historial del turno Caso Mantis : 0014376
     */
    public void addFaseRestitucionTurno(Turno turno, Usuario usuario) throws HermodException {
        try {
            factory.getTurnosDAO().addFaseRestitucionTurno(turno, usuario);
        } catch (DAOException e) {
            HermodException he = new HermodException("Fallo en el servicio para agregar face al turno: ", e);
            he.setHashErrores(e.getHashErrores());
            Log.getInstance().error(HermodService.class, he.getMessage(), e);
            throw he;

        }
    }

    /**
     * @author : Carlos Torres
     * @Caso Mantis : 14371: Acta - Requerimiento No
     * 021_589_Rol_Registrador_Inhabilitar_Pantallas_Administrativas Obtiene el
     * listado de pantallas administrativas visibles para un respectivo usuario.
     * @param Usuario
     * @return Listado de pantallas administrativas visibles para el Usuario
     * recibido como parámetro.
     * @throws HermodException
     */
    public List obtenerAdministrativasPorRol(Usuario usuario) throws HermodException {
        try {
            List pantallasVisibles = factory.getVariablesOperativasDAO().obtenerAdministrativasPorRol(usuario);
            return pantallasVisibles;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.PANTALLAS_ADMINISTRATIVAS_ROL_NO_OBTENIDAS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @author : Carlos Torres
     * @Caso Mantis : 14371: Acta - Requerimiento No
     * 021_589_Rol_Registrador_Inhabilitar_Pantallas_Administrativas Obtiene el
     * listado de reportes visibles para un respectivo rol.
     * @param roles Listado de roles asociados con el usuario.
     * @return Listado de pantallas administrativas visibles para el rol
     * recibido como parámetro.
     * @throws HermodException
     */
    public List obtenerPantallasPaginaReportesPorRol(Usuario usuario) throws HermodException {
        try {

            List pantallasVisibles = factory.getVariablesOperativasDAO().obtenerPantallasPaginaReportesPorRol(usuario);
            return pantallasVisibles;

        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.PANTALLAS_ADMINISTRATIVAS_ROL_NO_OBTENIDAS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        } // try

    } // end method

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#desbloquearFolios(gov.sir.core.negocio.modelo.Turno)
     * @author: Daniel Forero
     * @change: 1159.111.LIBERACION.FOLIOS.TURNOS.FINALIZADOS.
     */
    public int desbloquearFolios(Turno turno) throws HermodException {
        try {
            return factory.getTurnosNuevosDAO().desbloquearFolios(turno);
        } catch (DAOException e) {
            HermodException he = new HermodException("No fue posible liberar los folios del turno " + turno.getIdWorkflow(), e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * @Autor: Santiago Vásquez
     * @Change: 2062.TARIFAS.REGISTRALES.2017
     */
    public boolean isIncentivoRegistral(Date fechaDocumento) throws HermodException {
        try {
            return factory.getLiquidacionesDAO().isIncentivoRegistral(fechaDocumento);
        } catch (DAOException e) {
            HermodException he = new HermodException("Error al validar la fecha de encabezado de documento", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Adiciona una relación circulo-banco-cuenta (SIR_OP_CUENTAS_BANCARIAS)
     */
    public boolean addCuentaBancaria(CuentasBancarias cuentaBancaria) throws HermodException {
        try {
            CuentasBancariasPk cuentasBancariasPk = factory.getBancosDAO().addCuentaBancaria(cuentaBancaria);
            if (cuentasBancariasPk != null) {
                return true;
            }
            return false;
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CUENTA_BANCARIA_NO_CREADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Consulta las cuentas bancarias configurados para un círculo y banco en
     * específico
     */
    public List getCuentasBancarias(String idCirculo, String idBanco) throws HermodException {
        try {
            return factory.getBancosDAO().getCuentasBancarias(idCirculo, idBanco);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CUENTAS_BANCARIAS_NO_OBTENIDAS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Consulta las cuentas bancarias configurados para un círculo en específico
     */
    public List getCuentasBancariasXCirculo(Circulo circulo) throws HermodException {
        try {
            return factory.getBancosDAO().getCuentasBancariasXCirculo(circulo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CUENTAS_BANCARIAS_NO_OBTENIDAS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * actualiza el estado para una cuenta bancaria de un circulo y banco en
     * especifico
     */
    public void actualizarEstadoCtaBancaria(String idCirculo, String idBanco, String nroCuenta, boolean estado) throws HermodException {
        try {
            factory.getBancosDAO().actualizarEstadoCtaBancaria(idCirculo, idBanco, nroCuenta, estado);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.FALLO_ESTADO_CUENTA_BANCARIA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de objetos de tipo <code>CanalesRecaudo</code>
     *
     * @return una lista de objetos <code>CanalesRecaudo</code>
     * @see gov.sir.core.negocio.modelo.CanalesRecaudo
     * @throws <code>HermodException</code>
     */
    public List getCanalesRecaudo(boolean flag) throws HermodException {
        try {
            return factory.getBancosDAO().getCanalesRecaudo(flag);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_CANALES_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    /**
     * Obtiene una lista con datos de documento Corregido de un <code>Canal de Recaudo</code>
     *  Recibe IdDocumentoPago String que se corrrigio
     * @return List 
     * @see gov.sir.core.negocio.modelo.DocumentoPagoCorreccion
     * @throws <code>HermodException</code>
     */
    public List getDocumentoCorregido(String idDocumentoPago) throws HermodException {
        try {
            return factory.getPagosDAO().getDocumentoCorregido(idDocumentoPago);
        } catch (DAOException e) {
            HermodException he = new HermodException("NO ENCONTRADO", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene la lista de objetos de tipo <code>CanalesRecaudo</code>
     *
     * @return una lista de objetos <code>CanalesRecaudo</code>
     * @param circulo
     * @see gov.sir.core.negocio.modelo.CanalesRecaudo
     * @throws <code>HermodException</code>
     */
    public List getCanalesRecaudoXCirculo(Circulo circulo) throws HermodException {
        try {
            return factory.getBancosDAO().getCanalesRecaudoXCirculo(circulo);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_CANALES_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Adiciona un nuevo canal de recaudo <code>CanalesRecaudo</code>
     *
     * @return true o false dependiendo del resultado de la adicion.
     * @see gov.sir.core.negocio.modelo.CanalesRecaudo
     * @throws <code>HermodException</code>
     */
    public boolean addCanalRecaudo(CanalesRecaudo canalesRecaudo) throws HermodException {
        boolean estado = false;
        try {
            if (factory.getBancosDAO().addCanalRecaudo(canalesRecaudo)) {
                estado = true;
            }
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_CANALES_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }

        return estado;
    }

    /**
     * actualiza el estado para un canal de recaudo en especifico
     */
    public void actualizarEstadoCanalRecaudo(int idCanal, boolean estado) throws HermodException {
        try {
            factory.getBancosDAO().actualizarEstadoCanalRecaudo(idCanal, estado);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.FALLO_ESTADO_CANAL_RECAUDO, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Valida si para la forma de pago recibida el campo de banco/franquicia
     * esta activo o no
     *
     * @return true o false dependiendo del resultado de la adicion.
     * @see gov.sir.core.negocio.modelo.TipoPago
     * @throws <code>HermodException</code>
     */
    public boolean validaCampoBancoFranquicia(TipoPago tipoPago) throws HermodException {
        boolean estado = false;
        try {
            if (factory.getBancosDAO().validaCampoBancoFranquicia(tipoPago)) {
                estado = true;
            }
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LISTA_CANALES_NO_OBTENIDA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }

        return estado;
    }

    /**
     * Obtiene una lista de los  <code>CamposCaptura</code> activos para la forma
     * de pago recibida
     *
     * @return una lista de objetos <code>CamposCaptura</code>
     * @see gov.sir.core.negocio.modelo.CamposCaptura
     * @throws <code>HermodException</code>
     */
    public List camposCapturaXFormaPago(String formaPagoId) throws HermodException {
        try {
            return factory.getPagosDAO().camposCapturaXFormaPago(formaPagoId);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_CAMPOS_CAPTURA_X_FORMA_PAGO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene informacion de  <code>CanalesRecaudo</code> por el id especificado
     *
     * @return un objeto <code>CanalesRecaudo</code>
     * @see gov.sir.core.negocio.modelo.CanalesRecaudo
     * @throws <code>HermodException</code>
     */
    public CanalesRecaudo getCanalRecaudoByID(int canalRecaudoId) throws HermodException {
        try {
            return factory.getPagosDAO().getCanalRecaudoByID(canalRecaudoId);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.CANAL_RECAUDO_NO_ENCONTRADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene informacion de  <code>TipoPago</code> por el id especificado
     *
     * @return un objeto <code>TipoPago</code>
     * @see gov.sir.core.negocio.modelo.TipoPago
     * @throws <code>HermodException</code>
     */
    public TipoPago getTipoPagoByID(int tipoPagoId) throws HermodException {
        try {
            return factory.getPagosDAO().getTipoPagoByID(tipoPagoId);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.TIPO_PAGO_NO_ENCONTRADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Obtiene informacion de  <code>CuentasBancarias</code> por el id
     * especificado
     *
     * @return un objeto <code>CuentasBancarias</code>
     * @see gov.sir.core.negocio.modelo.CuentasBancarias
     * @throws <code>HermodException</code>
     */
    public CuentasBancarias getCuentasBancariasByID(int cuentaBancariaId) throws HermodException {
        try {
            return factory.getPagosDAO().getCuentasBancariasByID(cuentaBancariaId);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.CUENTA_BANCARIA_NO_ENCONTRADA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
//.................................................CARLOS TEST.......................................

    public String getIdCtpByParamenters(String formaPagoId, String idCirculo, int idCanal, String idCb) throws HermodException {
        try {
            return factory.getPagosDAO().getIdCtpByParamenters(formaPagoId, idCirculo, idCanal, idCb);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.LISTA_CIRCULO_TIPOS_PAGO_NO_OBTENIDA, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
//.................................................CARLOS TEST.......................................    

    public CirculoTipoPago getCirculoTipoPagoByID(int idCtp) throws HermodException {
        try {
            return factory.getPagosDAO().getCirculoTipoPagoByID(idCtp);
        } catch (DAOException e) {
            HermodException he
                    = new HermodException(HermodException.CIRCULO_TIPO_PAGO_NO_ENCONTRADO, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * Consulta las cuentas bancarias configurados para un círculo, canal y
     * forma pago
     */
    public List getCuentasBancariasXCirculoCanalForma(Circulo circulo, String idCanalRecaudo, String idFormaPago) throws HermodException {
        try {
            return factory.getBancosDAO().getCuentasBancariasXCirculoCanalForma(circulo, idCanalRecaudo, idFormaPago);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.CUENTAS_BANCARIAS_NO_OBTENIDAS, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

    /**
     * actualiza el estado para un registro en la tabla SIR_NE_CIRCULO_TIPO_PAGO
     */
    public void actualizarEstadoCtp(String idCtp, boolean estado) throws HermodException {
        try {
            factory.getBancosDAO().actualizarEstadoCtp(idCtp, estado);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.FALLO_ESTADO_CTP, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
        
    public void deleteLiquidacionTurnoRegistro(String idLiquidacion, String idSolicitud) throws HermodException {
        try {
             factory.getLiquidacionesDAO().deleteLiquidacionTurnoRegistro(idLiquidacion, idSolicitud);
        } catch (DAOException e) {
            HermodException he = new HermodException(HermodException.LIQUIDACION_SIN_ID_NO_ELIMINADA, e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    /**
     * @author: DNilson226 - Nilson Olaya Gómez - desarrollo3@tsg.net.co
     * retorna verdadero si encontro algun dato de este documento pago en correccion forma pago
     * @param idDocumento
     * @return
     * @throws HermodException
     */
    public boolean validarSiTurnoFueRadicadoXREL(String trnoIdWorkFlow) throws HermodException {
        try {
            return factory.getPagosDAO().validarSiTurnoFueRadicadoXREL(trnoIdWorkFlow);
        } catch (DAOException e) {
            HermodException he = new HermodException("ERROR VALIDACION EN DOCUMENTO PAGO DE TURNOS RADICOS POR REL", e);
            ExceptionPrinter ep = new ExceptionPrinter(he.fillInStackTrace());
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    /**
     * @param banco
     * @return idBanco
     * @throws HermodException 
     */
    public String getIdBanco(String banco) throws HermodException {
        try{
            return factory.getPagosDAO().getIdBanco(banco);
        } catch(DAOException e){
            HermodException he = new HermodException("EL ID DE BANCO NO PUDO SER CONSULTADO ", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    
    /**
     * @param canal
     * @return campCaptura
     * @throws HermodException 
     */
    public String getCanalPago (String canal) throws HermodException{
        try{
            return factory.getPagosDAO().getCanalPago(canal);
        } catch(DAOException e){
            HermodException he = new HermodException("EL ID DE BANCO NO PUDO SER CONSULTADO ", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    public boolean restringirAddPago(String idBanco, String canal, String numero) throws HermodException{
        try{
            return factory.getPagosDAO().restringirAddPago(idBanco, canal, numero);
        } catch(DAOException e){
            HermodException he = new HermodException("NO FUE POSIBLE CONSULTAR UNA RESTRICCION ", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    public boolean restringirAddPagoByFP(String formaPago, String canal, String numero) throws HermodException{
        try{
            return factory.getPagosDAO().restringirAddPagoByFP(formaPago, canal, numero);
        } catch(DAOException e){
            HermodException he = new HermodException("NO FUE POSIBLE CONSULTAR UNA RESTRICCION ", e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }
    
    /**
     * 
     * @author: julian rojas - desarrollo5@tsg.net.co
     * cambia el estado y url en la tabla SIR_OP_TURNO
     * <p>
     * @param turno objeto <code>Turno</code> con sus atributos
     * @return void
     * @throws Throwable.
     * @see gov.sir.core.negocio.modelo.Turno
     */
    
    public void updateStatusRel(Turno turno) throws HermodException {

        try {
            System.out.println("hermodService ok");
            factory.getTurnosDAO().updateStatusRel(turno);
        } catch (DAOException e) {
            System.out.println("hermodService ok");
            HermodException he = new HermodException(HermodException.TURNO_STATUS_REL, e);
            Log.getInstance().error(this.getClass(), he.getMessage(), e);
            throw he;
        }
    }

}

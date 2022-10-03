/*
 * Clase para el manejo de las solicitudes asociadas con los
 * diferentes procesos de la aplicación.
 */
package gov.sir.hermod.dao.impl.jdogenie;

import co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.constantes.CDocumento;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CSecuencias;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.constantes.CValidacion;

import gov.sir.hermod.HermodProperties;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.SolicitudesDAO;

import com.versant.core.jdo.VersantPersistenceManager;
import com.versant.core.jdo.VersantQuery;

import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.BloqueoFolio;
import gov.sir.core.negocio.modelo.Busqueda;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.CiudadanoPk;
import gov.sir.core.negocio.modelo.Consignacion;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.DocumentoFotocopia;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.LlaveBloqueo;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAntiguoSistema;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudDevolucion;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudFolioPk;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;
import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.TestamentoCiudadanoPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.UsuarioPk;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;

import gov.sir.core.negocio.modelo.jdogenie.ActoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.AplicacionPagoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.BancoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.BloqueoFolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.BusquedaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.BusquedaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CambioMatriculaCerEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CausalRestitucionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CausalRestitucionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CheckItemDevEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CheckItemDevEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ConsignacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DatosAntiguoSistemaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoFotocopiaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoFotocopiaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.FolioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoMasivoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoConsultaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoDevolucionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoFotocopiaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRegistroEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LlaveBloqueoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.PagoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.RangoFolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SolCheckedItemDevEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitanteEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudAntiguoSistemaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudAsociadaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoMasivoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudCheckedItemEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudCorreccionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudRepartoNotarialEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudRestitucionRepartoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudVinculadaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SubAtencionSubSolicitudEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionTipoActoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SubtipoSolicitudEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SubtipoSolicitudEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TestamentoCiudadanoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TestamentoCiudadanoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TestamentoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TestamentoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoConsultaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoConsultaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoDocumentoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoDocumentoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoFotocopiaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoFotocopiaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoRecepcionPeticionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoRecepcionPeticionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase para el manejo de las solicitudes asociadas con los diferentes
 * procesos de la aplicación.
 * @author mrios
 * @author mortiz
 * @author dlopez
 * @author dsalas
 */
public class JDOSolicitudesDAO implements SolicitudesDAO {
        
        private JDOAuditoriaDAO auditoria = new JDOAuditoriaDAO();
	/** Crea a nueva instancia de JDOSolicitudesDAO
	 *
	 */
	public JDOSolicitudesDAO() {
	}

	/**
	 * Crea una solicitud de certificados en el sistema
	 * @param  s <code>SolicitudCertificado</code> con sus atributos, exceptuando el
	 * identificador
	 * @return el identificador de la <code>SolicitudCertificado</code> persistente
	 * con su identificador.
	 * @throws DAOException
	 * @see gov.sir.core.negocio.modelo.Solicitud
	 * @see gov.sir.core.negocio.modelo.SolicitudCertificado
	 */
	public SolicitudPk crearSolicitudCertificado(
		SolicitudCertificado s) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		SolicitudCertificadoEnhanced sc = null;
		SolicitudEnhancedPk scId = new SolicitudEnhancedPk();

		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			sc = this.crearSolicitudCertificado(SolicitudCertificadoEnhanced.enhance(
						s), pm);
			pm.currentTransaction().commit();
			scId = (SolicitudEnhancedPk) pm.getObjectId(sc);
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			throw (e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return scId.getSolicitudID();
	}

	/**
	* Crea una solicitud de certificados en el sistema.
	* <p>Método utilizado por transacciones.
	* @param  s <code>SolicitudCertificadoEnhanced</code> con sus atributos, exceptuando el
	* identificador
	* @param pm Persistence Manager de la transacción.
	* @return <code>SolicitudCertificadoEnhanced</code> persistente
	* con su identificador.
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced
	* @see gov.sir.core.negocio.modelo.SolicitudCertificado
	* @see gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced
	*/
	protected SolicitudCertificadoEnhanced crearSolicitudCertificado(
		SolicitudCertificadoEnhanced s, PersistenceManager pm)
		throws DAOException {
		JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
		JDOProcesosDAO procesosDAO = new JDOProcesosDAO();
		JDOLiquidacionesDAO liquidacionesDAO = new JDOLiquidacionesDAO();
		JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
		ProcesoEnhanced pr = new ProcesoEnhanced();

		try {

		    //Se valida que la solicitud recibida no sea nula.
		    if (s == null) {
					throw new DAOException("La solicitud recibida es null");
			}
		    s.setIdSolicitud(this.getLlavePrimariaSolicitud(s, pm));

			CirculoEnhanced c = s.getCirculo();
			CirculoEnhancedPk cId = new CirculoEnhancedPk();
			cId.idCirculo = c.getIdCirculo();
			CirculoEnhanced cr = this.getCirculoByID(cId,pm);
			if (cr == null) {
				throw new DAOException("No encontró el circulo con el ID: " +
					cId.idCirculo);
			}

			pr = s.getProceso();
			ProcesoEnhancedPk prId = new ProcesoEnhancedPk();
			prId.idProceso = pr.getIdProceso();
			ProcesoEnhanced prr = procesosDAO.getProcesoByID(prId,pm);
			if (prr == null) {
				throw new DAOException("No encontró el circulo con el ID: " +
					cId.idCirculo);
			}

			if (s.getTurnoAnterior() != null)
			{

				TurnoEnhanced tant = s.getTurnoAnterior();
				TurnoEnhanced tantr = turnosDAO.getTurnoByWFId(tant.getIdWorkflow(),pm);
				if (tantr == null)
				{
					throw new DAOException(	"No encontró el turno con el ID: " +tant.getIdWorkflow());
				}
                SolicitudCertificadoEnhanced solAnt = (SolicitudCertificadoEnhanced) tantr.getSolicitud();
                s.setTurnoAnterior(tantr);

			}

			UsuarioEnhanced u = s.getUsuario();
			UsuarioEnhancedPk uId = new UsuarioEnhancedPk();
			uId.idUsuario = u.getIdUsuario();
			UsuarioEnhanced ur = this.getUsuarioByID(uId,pm);
			if (ur == null) {
				throw new DAOException("No encontró el usuario con el ID: " +
					uId.idUsuario);
			}

			CiudadanoEnhanced ciu = s.getCiudadano();
			if (ciu != null){
				String docCiudadano = ciu.getDocumento();
				String tipoDocCiudadano = ciu.getTipoDoc();
				CiudadanoEnhanced ciur = this.getCiudadanoByDocumento(tipoDocCiudadano,
						docCiudadano, pm, ciu.getIdCirculo());
				if (ciur == null) {
					ciur = this.crearCiudadano(ciu, pm);
				}

				else{
					//Actualizar datos de ciudadano:
					this.updateCiudadanoSolicitante(ciur, ciu, pm);
				}
				s.setCiudadano(ciur);
			}
			else{
				LiquidacionTurnoCertificadoEnhanced lc = (LiquidacionTurnoCertificadoEnhanced) s.getLiquidaciones().get(0);
				String tipo = lc.getTipoCertificado().getIdTipoCertificado();
				if (tipo.equals(CTipoCertificado.TIPO_PERTENENCIA_ID) || tipo.equals(CTipoCertificado.TIPO_ANTIGUO_SISTEMA_ID)){
					throw new DAOException(
						"Los datos del solicitante son obligatorios para este proceso");
				}
			}


			if (s.getDatosAntiguoSistema() != null) {
				DatosAntiguoSistemaEnhanced da = s.getDatosAntiguoSistema();
				da.setIdDatosAntiguoSistema(s.getIdSolicitud());
				da.setFechaCreacion(new Date());
			}

			if (s.getDocumento() != null) {
				long a = this.getSecuencial(CDocumento.TABLE_NAME,null);
				DocumentoEnhanced doc = s.getDocumento();
				doc.setIdDocumento(String.valueOf(a));
				TipoDocumentoEnhanced tipoDoc = doc.getTipoDocumento();
				TipoDocumentoEnhancedPk tipoDocId = new TipoDocumentoEnhancedPk();
				tipoDocId.idTipoDocumento =  tipoDoc.getIdTipoDocumento();
				TipoDocumentoEnhanced tipoDocr = variablesDAO.getTipoDocumentoById(tipoDocId,pm);
				if (tipoDocr == null) {
					throw new DAOException("No encontró el tipo de documento con el ID: " +
					tipoDocId.idTipoDocumento);
				}
				if (doc.getOficinaOrigen()!= null){
					OficinaOrigenEnhanced of = doc.getOficinaOrigen();
					OficinaOrigenEnhancedPk ofId = new OficinaOrigenEnhancedPk();
					ofId.idOficinaOrigen = of.getIdOficinaOrigen();
                                         /*
                                         *  @author Carlos Torres
                                         *  @chage   se agrega validacion de version diferente
                                         *  @mantis
                                         */
                                        ofId.version = of.getVersion();
					OficinaOrigenEnhanced ofr = variablesDAO.getOficinaOrigen(ofId, pm);
					if (ofr == null) {
						throw new DAOException("No encontró la ofica de origen del documento con el ID: " +
						ofId.idOficinaOrigen);
					}
					doc.setOficinaOrigen(ofr);

				}
				doc.setTipoDocumento(tipoDocr);
				doc.setCirculo(s.getCirculo()!=null?s.getCirculo().getIdCirculo():null);
				s.setDocumento(doc);
			}

			List folios = s.getSolicitudFolios();
			Iterator it = folios.iterator();

			while (it.hasNext()) {
				SolicitudFolioEnhanced sf = (SolicitudFolioEnhanced) it.next();
				FolioEnhanced f = sf.getFolio();
				FolioEnhanced fr = this.getFolioByMatricula(f.getIdMatricula(),pm);

				//Se elimina esta validación que se realiza a nivel de las otras capas
				//para permitir el funcionamiento de certificados masivos.
				/*
				if (fr == null) {
					throw new DAOException(
						"No encontró el folio con el ID: " +
						f.getIdMatricula());
				}
				*/

				//Se realizan las validaciones para Certificados Masivos.
				if (fr == null)
				{
					//logger.debug("MATRICULA INEXISTENTE DEBE GENERARSE NOTA DEVOLUTIVA");
				}

				else
				{
					sf.setFolio(fr);
					sf.setIdMatricula(fr.getIdMatricula());
					sf.setSolicitud(s);
					sf.setIdSolicitud(s.getIdSolicitud());
					sf.setCirculo(s.getCirculo()!=null?s.getCirculo().getIdCirculo():null);
				}

			}

			s.setLastIdLiquidacion(0);
			List liq = s.getLiquidaciones();
			Iterator itl = liq.iterator();
			while (itl.hasNext()) {
				LiquidacionTurnoCertificadoEnhanced l = (LiquidacionTurnoCertificadoEnhanced) itl.next();
				liquidacionesDAO.addLiquidacionToSolicitudCertificado(l, s, pm);
			}

			s.setCirculo(cr);
			s.setUsuario(ur);
			s.setProceso(prr);
			if (s.getFecha()== null){
				s.setFecha(new Date());
			}
			pm.makePersistent(s);
		} catch (DAOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			Log.getInstance().error(JDOSolicitudesDAO.class,e);
			throw e;
		} catch (JDOObjectNotFoundException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			Log.getInstance().error(JDOSolicitudesDAO.class,e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

		return s;
	}


	/**
	 * Crea una solicitud de registro en el sistema
	 * @param  s <code>SolicitudRegistro</code> con sus atributos, exceptuando el
	 * identificador
	 * @return el identificador de la <code>SolicitudRegistro</code> persistente
	 * con su identificador.
	 * @throws DAOException
	 * @see gov.sir.core.negocio.modelo.Solicitud
	 * @see gov.sir.core.negocio.modelo.SolicitudRegistro
	 */
	public SolicitudPk crearSolicitudRegistro(SolicitudRegistro s)
		throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		SolicitudRegistroEnhanced sc = null;
		SolicitudEnhancedPk scId = new SolicitudEnhancedPk();

		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			sc = this.crearSolicitudRegistro(SolicitudRegistroEnhanced.enhance(
						s), pm);
			pm.currentTransaction().commit();
			scId = (SolicitudEnhancedPk) pm.getObjectId(sc);
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			throw (e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return scId.getSolicitudID();
	}



	/**
	* Crea una solicitud de registro en el sistema.
	* <p>Método utilizado por transacciones.
	* @param  s <code>SolicitudRegistroEnhanced</code> con sus atributos, exceptuando el
	* identificador
	* @param pm Manager de la transacción.
	* @return <code>SolicitudRegistroEnhanced</code> persistente
	* con su identificador.
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced
	* @see gov.sir.core.negocio.modelo.SolicitudRegistro
	* @see gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced
	*/
	protected SolicitudRegistroEnhanced crearSolicitudRegistro(
		SolicitudRegistroEnhanced s, PersistenceManager pm)
		throws DAOException {
		JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
		JDOProcesosDAO procesosDAO = new JDOProcesosDAO();
		JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
		JDOLiquidacionesDAO liquidacionesDAO = new JDOLiquidacionesDAO();
		ProcesoEnhanced pr = new ProcesoEnhanced();

		try {

		    if (s == null){
				throw new DAOException("La solicitud recibida es null");
			}
			s.setIdSolicitud(this.getLlavePrimariaSolicitud(s, pm));

			CirculoEnhanced c = s.getCirculo();
			CirculoEnhancedPk cId = new CirculoEnhancedPk();
			cId.idCirculo = c.getIdCirculo();
			CirculoEnhanced cr = this.getCirculoByID(cId,pm);

			if (cr == null) {
				throw new DAOException("No encontró el circulo con el ID: " +
					cId.idCirculo);
			}

			pr = s.getProceso();
			ProcesoEnhancedPk prId = new ProcesoEnhancedPk();
			prId.idProceso = pr.getIdProceso();
			ProcesoEnhanced prr = procesosDAO.getProcesoByID(prId,pm);
			if (prr == null) {
				throw new DAOException("No encontró el circulo con el ID: " +
					cId.idCirculo);
			}

			if (s.getTurnoAnterior() != null) {
				TurnoEnhanced tant = s.getTurnoAnterior();
				TurnoEnhanced tantr = turnosDAO.getTurnoByWFId(tant.getIdWorkflow(),
						pm);
				if (tantr == null) {
					throw new DAOException(
						"No encontró el turno con el ID: " + tant.getIdTurno());
				}
				if(!CProceso.PROCESO_REGISTRO.equals(String.valueOf(tantr.getIdProceso()))){
					throw new DAOException(
						"El turno anterior con el ID: " +
						tant.getIdTurno()+ " no es de mismo proceso.");
				}
				s.setTurnoAnterior(tantr);
			}

			UsuarioEnhanced u = s.getUsuario();
			UsuarioEnhancedPk uId = new UsuarioEnhancedPk();
			uId.idUsuario = u.getIdUsuario();
			UsuarioEnhanced ur = this.getUsuarioByID(uId,pm);
			if (ur == null) {
				throw new DAOException("No encontró el usuario con el ID: " +
					uId.idUsuario);
			}

			CiudadanoEnhanced ciu = s.getCiudadano();
			if(ciu !=null){
				String docCiudadano = ciu.getDocumento();
				String tipoDocCiudadano = ciu.getTipoDoc();
				CiudadanoEnhanced ciur = this.getCiudadanoByDocumento(tipoDocCiudadano,
						docCiudadano, pm, ciu.getIdCirculo());
				if (ciur == null) {
					CiudadanoEnhanced ciuNuevo = this.crearCiudadano(ciu, pm);
					//ciur = this.getCiudadanoByDocumento(ciuNuevo.getTipoDoc(),ciuNuevo.getDocumento(), pm, ciuNuevo.getIdCirculo());
					if (ciuNuevo == null) {
						throw new DAOException(
							"No encontró el ciudadano con el documento: " +
							 ciu.getDocumento());
					}
					ciur = ciuNuevo;
				}
				s.setCiudadano(ciur);
			}
			else{
				throw new DAOException(
					"Los datos del solicitante son obligatorios para este proceso");
			}

			SubtipoSolicitudEnhanced so = s.getSubtipoSolicitud();
			SubtipoSolicitudEnhancedPk soId = new SubtipoSolicitudEnhancedPk();
			soId.idSubtipoSol = so.getIdSubtipoSol();
			SubtipoSolicitudEnhanced sor = variablesDAO.getSubtipoSolicitudByID(soId,pm);
			if (sor == null) {
				throw new DAOException("No encontró el subtipo de Solicitud con el ID: " +
				sor.getIdSubtipoSol());
			}


			DocumentoEnhanced d = s.getDocumento();
			if (d != null) {
				d.setCirculo(s.getCirculo()!=null?s.getCirculo().getIdCirculo():null);
				this.setDocumentoToSolicitudRegistro(s, d, pm);
			}

			List checks = s.getCheckedItems();
			Iterator it1 = checks.iterator();

			while (it1.hasNext()) {
				SolicitudCheckedItemEnhanced si = (SolicitudCheckedItemEnhanced) it1.next();
				CheckItemEnhanced ci = si.getCheckItem();
				CheckItemEnhancedPk ciId = new CheckItemEnhancedPk();
				ciId.idCheckItem = ci.getIdCheckItem();
				ciId.idSubtipoSol = ci.getIdSubtipoSol();
				CheckItemEnhanced cir = this.getCheckItemByID(ciId, pm);
				if (cir == null) {
					throw new DAOException(
						"No encontró el Check Item con el ID: " + ci.getIdCheckItem());
				}
				si.setCheckItem(cir);
				si.setSolicitud(s);
			}

			//Recuperar solicitudes vinculadas persistentes.
			List vinculadas = s.getSolicitudesVinculadas();
			Iterator itVinc = vinculadas.iterator();

			while (itVinc.hasNext()) {
			SolicitudVinculadaEnhanced svEnh = (SolicitudVinculadaEnhanced) itVinc.next();
			SolicitudEnhanced solV = svEnh.getSolicitudPadre();

			SolicitudEnhancedPk solEnhId = new SolicitudEnhancedPk();
			solEnhId.idSolicitud = solV.getIdSolicitud();

			SolicitudEnhanced solVincPers = (SolicitudEnhanced) pm.getObjectById(solEnhId, true);

			if (solVincPers == null)
			{
				throw new DAOException("No se encontró la solicitud vinculada con el id: "+solEnhId.idSolicitud);
			}

			svEnh.setSolicitudPadre(solVincPers);
			svEnh.setSolicitudHija(s);
			}

			if (s.getDatosAntiguoSistema() != null) {
				DatosAntiguoSistemaEnhanced da = s.getDatosAntiguoSistema();
				da.setIdDatosAntiguoSistema(s.getIdSolicitud());
				if(da.getDocumento()!=null){
					if(da!=null && da.getDocumento()!=null){
						da.getDocumento().setCirculo(s.getCirculo()!=null?s.getCirculo().getIdCirculo():null);	
					}					
					this.setDocumentoToDatosAntiguoSistema(da, da.getDocumento(), pm);
				}
			}

			List folios = s.getSolicitudFolios();
			Iterator it = folios.iterator();

			while (it.hasNext()) {
				SolicitudFolioEnhanced sf = (SolicitudFolioEnhanced) it.next();
				FolioEnhanced f = sf.getFolio();
				FolioEnhanced fr = this.getFolioByMatricula(f.getIdMatricula(),
						pm);
				if (fr == null) {
					throw new DAOException(
						"No encontró el folio con el ID: " +
						f.getIdMatricula());
				}
				sf.setFolio(fr);
				sf.setIdMatricula(fr.getIdMatricula());
				sf.setSolicitud(s);
				sf.setIdSolicitud(s.getIdSolicitud());
				sf.setCirculo(s.getCirculo()!=null?s.getCirculo().getIdCirculo():null);
			}

			List solAsocs = s.getSolicitudesHijas();
			Iterator it2 = solAsocs.iterator();

			while (it2.hasNext()) {
				SolicitudAsociadaEnhanced solAsoc = (SolicitudAsociadaEnhanced) it2.next();
				SolicitudCertificadoEnhanced solCer = (SolicitudCertificadoEnhanced) solAsoc.getSolicitudHija();
				SolicitudEnhancedPk solCerId = new SolicitudEnhancedPk();
				solCerId.idSolicitud = solCer.getIdSolicitud();
				SolicitudCertificadoEnhanced solCerr = (SolicitudCertificadoEnhanced) this.getSolicitudByID(solCerId, pm);
				if (solCerr == null) {
					throw new DAOException(
						"No encontró la Solicitud Asociada con el ID: " +
						solCer.getIdSolicitud());
				}
				LiquidacionTurnoCertificadoEnhanced liqCert= (LiquidacionTurnoCertificadoEnhanced) solCerr.getLiquidaciones().get(0);
				LiquidacionEnhancedPk liqCertId = new LiquidacionEnhancedPk();
				liqCertId.idLiquidacion = liqCert.getIdLiquidacion();
				liqCertId.idSolicitud = liqCert.getIdSolicitud();
				LiquidacionTurnoCertificadoEnhanced liqCertr = (LiquidacionTurnoCertificadoEnhanced) liquidacionesDAO.getLiquidacionByID(liqCertId, pm);

				solAsoc.setSolicitudHija(solCerr);
				solAsoc.setSolicitudPadre(s);
			}

			s.setLastIdLiquidacion(0);
			List liq = s.getLiquidaciones();

			if (liq.size() >0)
			{
				LiquidacionTurnoRegistroEnhanced l = (LiquidacionTurnoRegistroEnhanced) liq.get(0);
				liquidacionesDAO.addLiquidacionToSolicitudRegistro(l, s, pm);
			}

			s.setCirculo(cr);
			s.setUsuario(ur);
			s.setProceso(prr);
			s.setSubtipoSolicitud(sor);

			//Se escoge el subtipo de atención dependiendo de los datos de la solicitud

			SubtipoAtencionEnhanced sar = null;
			if (s.getSubtipoAtencion() != null){
				SubtipoAtencionEnhanced sa = s.getSubtipoAtencion();
				SubtipoAtencionEnhancedPk saId = new SubtipoAtencionEnhancedPk();
				saId.idSubtipoAtencion = sa.getIdSubtipoAtencion();
				sar = variablesDAO.getSubtipoAtencionByID(saId,pm);

			}
			else {
				sar = this.seleccionarSubtipoAtencion(s, pm);
			}

			if (sar == null) {
				throw new DAOException("No encontró el subtipo de Atencion con el ID: " +
					sar.getIdSubtipoAtencion());
			}

			s.setSubtipoAtencion(sar);
			pm.makePersistent(s);
		} catch (DAOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			Log.getInstance().error(JDOSolicitudesDAO.class,e);
			throw e;
		} catch (JDOObjectNotFoundException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			Log.getInstance().error(JDOSolicitudesDAO.class,e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

		return s;
	}


	/**
	 * Setea los datos de antiguo sistema a una solicitud de corrección. Si los datos de antiguo sistema ya
	 * está seteados para la solicitud se reescriben
	 * @param sol Solicitud de corrección con el ID seteado
	 * @param datosAntiguoSistema
	 * @return
	 * @throws DAOException
	 */
	public boolean setDatosAntiguoSistemaToSolicitud(Solicitud sol, DatosAntiguoSistema datosAntiguoSistema) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		try{

			pm.currentTransaction().begin();
			SolicitudEnhancedPk solID = new SolicitudEnhancedPk();
			solID.idSolicitud = sol.getIdSolicitud();

			SolicitudEnhanced solEnh = (SolicitudEnhanced)this.getSolicitudByID(solID, pm);

			if(!(solEnh instanceof SolicitudCorreccionEnhanced)&&!(solEnh instanceof SolicitudRegistroEnhanced)){
				throw new DAOException("La solicitud NO es de corrección ni de registro");
			}

			DatosAntiguoSistemaEnhanced datos = DatosAntiguoSistemaEnhanced.enhance(datosAntiguoSistema);
			datos.setIdDatosAntiguoSistema(solEnh.getIdSolicitud());

			if(datos.getDocumento()!=null){
				if(solEnh!=null&&solEnh.getCirculo()!=null&&datos!=null&&datos.getDocumento()!=null){
					datos.getDocumento().setCirculo(solEnh.getCirculo().getIdCirculo());
				}
				this.setDocumentoToDatosAntiguoSistema(datos, datos.getDocumento(), pm);
			}


			if(solEnh instanceof SolicitudCorreccionEnhanced){
				SolicitudCorreccionEnhanced solCorEnh = (SolicitudCorreccionEnhanced)solEnh;
				if(solCorEnh.getDatosAntiguoSistema()!=null){
					DatosAntiguoSistemaEnhanced datosToDelete = solCorEnh.getDatosAntiguoSistema();
					solCorEnh.setDatosAntiguoSistema(null);
					pm.deletePersistent(datosToDelete);
					//Ejecutamos el borrado
					VersantPersistenceManager pm2 = (VersantPersistenceManager) pm;
					pm2.flush();
				}
				pm.makePersistent(datos);
				solCorEnh.setDatosAntiguoSistema(datos);
			}
			else{
				SolicitudRegistroEnhanced solRegEnh = (SolicitudRegistroEnhanced)solEnh;
				if(solRegEnh.getDatosAntiguoSistema()!=null){
					DatosAntiguoSistemaEnhanced datosToDelete = solRegEnh.getDatosAntiguoSistema();
					solRegEnh.setDatosAntiguoSistema(null);
					pm.deletePersistent(datosToDelete);
					//Ejecutamos el borrado
					VersantPersistenceManager pm2 = (VersantPersistenceManager) pm;
					pm2.flush();
				}
				pm.makePersistent(datos);
				solRegEnh.setDatosAntiguoSistema(datos);
			}

			pm.currentTransaction().commit();

			return true;
		}
		catch (JDOException e) {
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}finally{
			pm.close();
		}

	}

	/**
	 * Escoge el subtipo de atención de la solicitud de registro dependiendo de las reglas de negocio definidas
	 * @param s
	 * @param pm
	 * @return
	 */
	protected SubtipoAtencionEnhanced seleccionarSubtipoAtencion(SolicitudRegistroEnhanced s, PersistenceManager pm) throws DAOException {
		SubtipoAtencionEnhanced subtipo = null;
		try {
			//1. Se selecciona los subtipos de atención por orden de prelación:
			Query query = pm.newQuery(SubtipoAtencionEnhanced.class);
			query.setOrdering("orden ascending");
			Collection col = (Collection)query.execute();

			boolean found = false;
			for (Iterator iter = col.iterator(); iter.hasNext() && !found; ) {
				//1.1 Para cada subtipo de atención se revisa si cumple con las reglas de negocio
				//    el subtipo de atención escogido es el primero que cumpla con todas las condiciones
				subtipo = (SubtipoAtencionEnhanced)iter.next();
				if(this.cumpleReglasNegocio(subtipo, s, pm)){
					found = true;
				}
			}

		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

		return subtipo;
	}

	/**
	 * @param subtipo
	 * @param s
	 * @return
	 */
	private boolean cumpleReglasNegocio(SubtipoAtencionEnhanced subtipo, SolicitudRegistroEnhanced s, PersistenceManager pm) {

		//1.1.1 Se revisa si el subtipo de solicitud está configurado para el subtipo de atención:
		if(!this.isSubtipoSolicitudParaSubtipoAtencion(s.getSubtipoSolicitud(), subtipo, pm)){
			return false;
		}

		//1.1.2 Pasa la validación. Se valida ahora el número de anotaciones de cada uno
		//      de los folios
		if (subtipo.getMinAnotacione()>0){
			if(!this.isAnyFolioConMinAnotaciones(s, subtipo.getMinAnotacione(), pm)){
				return false;
			}
		}

		//      con los tipos de actos configurados para el subtipo de atención");
		//1.1.3 Se revisa si existe un acto en la solicitud cuyo tipo esté asociado
		//      con los tipos de actos configurados para el subtipo de atención
		boolean found = false;
		List liquidaciones = s.getLiquidaciones();
		LiquidacionTurnoRegistroEnhanced liq;
		for(Iterator it = liquidaciones.iterator(); it.hasNext() && !found;){
			liq = (LiquidacionTurnoRegistroEnhanced)it.next();
			List actos = liq.getActos();
			ActoEnhanced acto;
			for(Iterator it2 = actos.iterator(); it2.hasNext() && !found;){
				acto = (ActoEnhanced) it2.next();
				if(this.isTipoActoParaSubtipoAtencion(acto.getTipoActo(), subtipo, pm)){
					found = true;
				}
			}
		}
		if(found==false){
			return false;
		}

		//1.1.4 Se valida el número de folios asociados a la solicitud
		/*
                 * @author     :   Julio Alcazar Rivas
                 * @change     :   Se modifica el if para que se return false solo cuando el numero de folios de la solicitud sea menor que
                 *                 el numero minimo de folios del subtipo evaluado.
                 * Caso Mantis :   0002593
                 */
                if(s.getSolicitudFolios().size()<subtipo.getMinFolios()){
			return false;
		}


		return true;
	}

	/**
	 * @param enhanced
	 * @param subtipo
	 * @param pm
	 * @return
	 */
	private boolean isTipoActoParaSubtipoAtencion(TipoActoEnhanced tipoAct, SubtipoAtencionEnhanced subtipo, PersistenceManager pm) {
		boolean rta = false;
		Query query = pm.newQuery(SubtipoAtencionTipoActoEnhanced.class);
		query.declareParameters("String idTipoAct, String idSubAtenc");
		query.setFilter("this.idTipoActo == idTipoAct &&\n"+
				"this.idSubtipoAtencion == idSubAtenc");
		Collection col = (Collection)query.execute(tipoAct.getIdTipoActo(), subtipo.getIdSubtipoAtencion());
		Iterator iter = col.iterator();
		if(iter.hasNext()) {
			rta = true;
		}

		return rta;
	}

	/**
	 * @param s
	 * @param l
	 * @return
	 */
	private boolean isAnyFolioConMinAnotaciones(SolicitudRegistroEnhanced s, long l, PersistenceManager pm) {
		SolicitudFolioEnhanced solFol;
		boolean found = false;
		for(Iterator it = s.getSolicitudFolios().iterator(); it.hasNext() && !found;){
			solFol = (SolicitudFolioEnhanced) it.next();
			if(this.getCountAnotacionesFolio(solFol.getIdMatricula(), pm)>l){
				found = true;
			}
		}

		return found;
	}


	/**
	 * Obtiene el número de anotaciones de un folio sin tener que recorrer
	 * la lista completa
	 * @param fol
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected long getCountAnotacionesFolio(String matricula,
		PersistenceManager pm) {
		long rta = 0;
		if (matricula != null) {
			//Se debe buscar por algún criterio las anotaciones
			VersantQuery query = (VersantQuery) pm.newQuery(AnotacionEnhanced.class);
			query.declareParameters("String matricula");
			query.setFilter("idMatricula==matricula");
			query.setResult("count(this)");
			rta = ((Long) query.execute(matricula)).longValue();
		}

		return rta;
	}

	/**
	 * @param enhanced
	 * @param subtipo
	 * @param pm
	 * @return
	 */
	private boolean isSubtipoSolicitudParaSubtipoAtencion(SubtipoSolicitudEnhanced subSol, SubtipoAtencionEnhanced subAtenc, PersistenceManager pm) {
		boolean rta = false;
		Query query = pm.newQuery(SubAtencionSubSolicitudEnhanced.class);
		query.declareParameters("String idSubAtenc, String idSubSol");
		query.setFilter("this.idSubtipoAtencion==idSubAtenc &&\n"+
				"this.idSubtipoSol==idSubSol");
		Collection col = (Collection)query.execute(subAtenc.getIdSubtipoAtencion(), subSol.getIdSubtipoSol());
		Iterator iter = col.iterator();
		if(iter.hasNext()) {
			rta = true;
		}
		return rta;
	}

	/**
	 * Crea un ciudadano en el sistema, asignándole su número identificador,
	 * y su número secuencial en caso de ser necesario.
	 * @param  c Ciudadano con sus atributos, exceptuando el identificador
	 * @return un Ciudadano persistente con su identificador.
	 * @throws DAOException
	 * @see gov.sir.core.negocio.modelo.Ciudadano
	 */
	public Ciudadano crearCiudadano(Ciudadano c) throws DAOException {
		CiudadanoEnhanced cr = null;
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			pm.currentTransaction().begin();
			cr = this.crearCiudadano(CiudadanoEnhanced.enhance(c), pm);
			pm.currentTransaction().commit();
		} catch (DAOException e) {
			cr = null;
			throw e;
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return (Ciudadano) cr.toTransferObject();
	}

	/**
	 * Crea un <code>Ciudadano</code> en el sistema
	 * <p>Método utilizado para transacciones.
	 * @param  c Ciudadano con sus atributos, exceptuando el identificador
	 * @param pm PersistenceManager de la transaccion
	 * @return un <code>CiudadanoEnhanced</code> persistente con su identificador.
	 * @throws DAOException
	 * @see gov.sir.core.negocio.modelo.Ciudadano
	 * @see gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced
	 */
	protected CiudadanoEnhanced crearCiudadano(CiudadanoEnhanced c,
		PersistenceManager pm) throws DAOException {
		CiudadanoEnhanced rta = null;

		try {
			//Se valida que el ciudadano tenga círculo
			if(c.getIdCirculo()==null){
				throw new DAOException("El ciudadano que se está intentando insertar no tiene círculo asociado");
			}

			//Generar el número de documento del ciudadano.

			long idCiudadano = this.getSecuencial("SIR_NE_CIUDADANO",null);

			Long idCiud = new Long (idCiudadano);
			String secuencial = new String(idCiud.toString());
			c.setIdCiudadano(secuencial);
			c.setSolicitante(true);

			if (c.getTipoDoc().equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD))
			{
				c.setDocumento(secuencial);

			}
			pm.makePersistent(c);
			rta = c;
		} catch (JDOObjectNotFoundException e) {
			rta = null;
		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

		return rta;
	}

	/**
	 * Setea el documento de la solicitud en una transacción.
	 * <p>
	 * Si idDocumento es nulo, se asigna un consecutivo y
	 * se crea el documento, si el idDocumento es diferente de nulo se
	 * valida que exista y se asocia.
	 * @param solicitud <code>SolicitudRegistroEnhanced</code>
	 * @param datos Documento con los objetos <code>OficinaOrigen</code> y <code>TipoOficina</code>
	 * @param pm PersistenceManager de la transacción
	 * @return true o false dependiendo del resultado de la operación.
	 * @throws DAOException
	 * @see gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced
	 * @see gov.sir.core.negocio.modelo.OficinaOrigen
	 * @see gov.sir.core.negocio.modelo.TipoOficina
	 *
	 */
	protected boolean setDocumentoToSolicitudRegistro(SolicitudRegistroEnhanced solicitud,
		DocumentoEnhanced datos, PersistenceManager pm)
		throws DAOException {
		DocumentoEnhanced doc = null;
		boolean rta;

		try {
			if (datos.getIdDocumento() != null) {
				DocumentoEnhancedPk did = new DocumentoEnhancedPk();
				did.idDocumento = datos.getIdDocumento();
				doc = this.getDocumentoByID(did, pm);

				if (doc == null) {
					throw new DAOException(
						"No encontró el documento con el ID: " +
						did.idDocumento);
				}
				if(doc!=null&&solicitud.getCirculo()!=null){
					doc.setCirculo(solicitud.getCirculo().getIdCirculo());
				}
				solicitud.setDocumento(doc);
			} else {
				
            	//Asignación de llave:
				datos.setIdDocumento(String.valueOf(this.getSecuencial(
						CDocumento.TABLE_NAME, null)));
				
				if (datos.getOficinaOrigen() == null) {
					if(datos.getOficinaInternacional() == null || datos.getOficinaInternacional().length()==0){
						throw new DAOException(
							"El documento debe tener oficina origen Nacional o Internacional");
					}
				}

				if (datos.getTipoDocumento() == null) {
					throw new DAOException(
						"El documento debe tener tipo de documento");
				}

				OficinaOrigenEnhanced oficina = null;
				//Si esta en null es porque viene la oficina origen internacional
				if(datos.getOficinaOrigen() != null){
					OficinaOrigenEnhancedPk ofid = new OficinaOrigenEnhancedPk();
					ofid.idOficinaOrigen = datos.getOficinaOrigen()
												.getIdOficinaOrigen();
                                        /*
                                          *  @author Carlos Torres
                                          *  @chage   se agrega validacion de version diferente
                                          *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                          */
                                        ofid.version = datos.getOficinaOrigen().getVersion();

					oficina = this.getOficinaOrigen(ofid,pm);

					if (oficina == null) {
						throw new DAOException(
							"La oficina origen no existe. IdOficina: " +
							ofid.idOficinaOrigen);
					}
				}

				TipoDocumentoEnhancedPk tdid = new TipoDocumentoEnhancedPk();
				tdid.idTipoDocumento = datos.getTipoDocumento()
											.getIdTipoDocumento();

				TipoDocumentoEnhanced tipoDoc = this.getTipoDocumento(tdid, pm);

				if (tipoDoc == null) {
					throw new DAOException(
						"El tipo de documento no existe. IdTipoDocumento: " +
						tdid.idTipoDocumento);
				}

				datos.setOficinaOrigen(oficina);
				datos.setTipoDocumento(tipoDoc);
				if(datos!=null&&solicitud.getCirculo()!=null){
					datos.setCirculo(solicitud.getCirculo().getIdCirculo());
				}
				solicitud.setDocumento(datos);
			}

			rta = true;
		} catch (DAOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw e;
		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

		return rta;
	}



	/**
	 * Setea el documento de los datos de antiguo sistema en una transacción.
	 * <p>
	 * Si idDocumento es nulo, se asigna un consecutivo y
	 * se crea el documento, si el idDocumento es diferente de nulo se
	 * valida que exista y se asocia.
	 * @param solicitud <code>SolicitudRegistroEnhanced</code>
	 * @param datos Documento con los objetos <code>OficinaOrigen</code> y <code>TipoOficina</code>
	 * @param pm PersistenceManager de la transacción
	 * @return true o false dependiendo del resultado de la operación.
	 * @throws DAOException
	 * @see gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced
	 * @see gov.sir.core.negocio.modelo.OficinaOrigen
	 * @see gov.sir.core.negocio.modelo.TipoOficina
	 *
	 */
	protected boolean setDocumentoToDatosAntiguoSistema(DatosAntiguoSistemaEnhanced datosAntiguoSistema,
		DocumentoEnhanced datos, PersistenceManager pm)
		throws DAOException {
		DocumentoEnhanced doc = null;
		boolean rta;

		try {
			if (datos.getIdDocumento() != null) {
				DocumentoEnhancedPk did = new DocumentoEnhancedPk();
				did.idDocumento = datos.getIdDocumento();
				doc = this.getDocumentoByID(did, pm);

				if (doc == null) {
					throw new DAOException(
						"No encontró el documento con el ID: " +
						did.idDocumento);
				}
				datosAntiguoSistema.setDocumento(doc);
			} else {
            	//Asignación de llave:
				datos.setIdDocumento(String.valueOf(this.getSecuencial(
						CDocumento.TABLE_NAME, null)));
				
				if (datos.getOficinaOrigen() != null) {

					OficinaOrigenEnhancedPk ofid = new OficinaOrigenEnhancedPk();
					ofid.idOficinaOrigen = datos.getOficinaOrigen()
												.getIdOficinaOrigen();
                                        /*
                                          *  @author Carlos Torres
                                          *  @chage   se agrega validacion de version diferente
                                          *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                          */
                                         ofid.version = datos.getOficinaOrigen().getVersion();
					OficinaOrigenEnhanced oficina = this.getOficinaOrigen(ofid,pm);

					if (oficina == null) {
						throw new DAOException(
							"La oficina origen no existe. IdOficina: " +
							ofid.idOficinaOrigen);
					}
					datos.setOficinaOrigen(oficina);
				}

				if (datos.getTipoDocumento() == null) {
					throw new DAOException(
						"El documento debe tener tipo de documento");
				}


				TipoDocumentoEnhancedPk tdid = new TipoDocumentoEnhancedPk();
				tdid.idTipoDocumento = datos.getTipoDocumento()
											.getIdTipoDocumento();

				TipoDocumentoEnhanced tipoDoc = this.getTipoDocumento(tdid, pm);

				if (tipoDoc == null) {
					throw new DAOException(
						"El tipo de documento no existe. IdTipoDocumento: " +
						tdid.idTipoDocumento);
				}


				datos.setTipoDocumento(tipoDoc);
				datosAntiguoSistema.setDocumento(datos);
			}

			rta = true;
		} catch (DAOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw e;
		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

		return rta;
	}



	/**
	 * Obtiene un ciudadano dado el número y el tipo del documento de
	 * identificacion.
	 * @param tipodoc Tipo del documento de Identificacion
	 * @param doc Número del documento de identificacion del <code>Ciudadano</code>
	 * @return <code>Ciudadano</code> con sus atributos
	 * @throws DAOException
	 * @gov.sir.core.negocio.modelo.Ciudadano
	 */
	public Ciudadano getCiudadanoByDocumento(String tipodoc, String doc, String idCirculo)
		throws DAOException {
		CiudadanoEnhanced cr = null;
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			cr = this.getCiudadanoByDocumento(tipodoc, doc, pm, idCirculo);
			if(cr == null){
				return null;
			}
			pm.makeTransient(cr);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw e;
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return (Ciudadano) cr.toTransferObject();
	}




	/**
	 * Obtiene un ciudadano dado el número y el tipo del documento de
	 * identificacion.
	 * <p>
	 * Método utilizado por transacciones.
	 * @param tipodoc Tipo del documento de Identificacion
	 * @param doc Número del documento de identificacion del <code>Ciudadano</code>
	 * @param pm Persistence Manager de la transacción.
	 * @param idCirculo TODO
	 * @return <code>Ciudadano</code> con sus atributos
	 * @throws DAOException
	 * @gov.sir.core.negocio.modelo.Ciudadano
	 */
	protected CiudadanoEnhanced getCiudadanoByDocumento(String tipodoc,
		String doc, PersistenceManager pm, String idCirculo) throws DAOException {
		CiudadanoEnhanced rta = null;

		if((tipodoc==null)&&(doc==null)){
			return null;
		}

		String Tipo = tipodoc;
		String Documento = doc;

		try {
			if(idCirculo==null){
				throw new DAOException("No se puede obtener el ciudadano puesto que no tiene un círculo asociado");
			}

			//Se establece como filtro de la consulta el tipo y número del
			//documento de identificación del ciudadano.
			Query query = pm.newQuery(CiudadanoEnhanced.class);
			query.declareParameters("String Tipo, String Documento, String idCir");
			query.setFilter("tipoDoc == Tipo && documento == Documento && idCirculo==idCir && solicitante==true");

			Collection col = (Collection) query.execute(Tipo, Documento, idCirculo);

			if (col.size() == 0) {
				rta = null;
			} else {
				for (Iterator iter = col.iterator(); iter.hasNext();) {
					rta = (CiudadanoEnhanced) iter.next();
				}
				query.closeAll();
			}
		} catch (JDOObjectNotFoundException e) {
			rta = null;
		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}

		return rta;
	}

	/**
	 * Obtiene un objeto Folio dado el número de matrícula
	 * @param matricula número de matrícula del folio
	 * @return Objeto Folio con sus objetos estado, complementacion, tipoPredio y ZonaRegistral,
	 * El objeto ZonaRegistral contiene la jerarquía circulo, vereda, municipio y departamento
	 * null si  no encuentra un folio que coincida con el identificador dado
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Folio
	 */
	public Folio getFolioByMatricula(String matricula)
		throws DAOException {
		FolioEnhanced rta = null;
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			rta = this.getFolioByMatricula(matricula, pm);

			if (rta != null) {

				//Se hace transiente cada uno de los objetos asociados
				//con el Folio.
				pm.makeTransient(rta.getEstado());
				pm.makeTransient(rta.getComplementacion());
				pm.makeTransient(rta.getTipoPredio());
				pm.makeTransient(rta.getZonaRegistral().getVereda()
									.getMunicipio().getDepartamento());
				pm.makeTransient(rta.getZonaRegistral().getVereda().getMunicipio());
				pm.makeTransient(rta.getZonaRegistral().getVereda());
				pm.makeTransient(rta.getZonaRegistral().getCirculo());
				pm.makeTransient(rta.getZonaRegistral());
				pm.makeTransient(rta);
			}
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw e;
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return (Folio) rta.toTransferObject();
	}

	/**
	 * Obtiene un objeto Folio dado el número de matrícula
	 * @param matricula número de matrícula del folio
	 * @return Objeto Folio con sus objetos estado, complementacion, tipoPredio y ZonaRegistral.
	 * El objeto ZonaRegistral contiene la jerarquía circulo, vereda, municipio y departamento
	 * null si  no encuentra un folio que coincida con el identificador dado
	 * @throws DAOException
	 */
	protected FolioEnhanced getFolioByMatricula(String matricula,
		PersistenceManager pm) throws DAOException {
		FolioEnhanced rta = null;

		try {

			//Se establece como criterio de búsqueda el identificador
			//de la Matrícula.
			Query query = pm.newQuery(FolioEnhanced.class);
			query.declareParameters("String matricula");
			query.setFilter("idMatricula == matricula");

			Collection col = (Collection) query.execute(matricula);

			Iterator iter = col.iterator();

			if (!iter.hasNext()) {
				rta = null;
			} else
			{
				rta = (FolioEnhanced) iter.next();
				query.closeAll();
			}
		} catch (JDOObjectNotFoundException e) {
			rta = null;
		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable t) {
			Log.getInstance().error(JDOSolicitudesDAO.class,t.getMessage());
			throw new DAOException(t.getMessage(), t);
		}

		return rta;
	}
	
	/**
     * Obtiene el bloqueo del folio en caso que se encuentre bloqueado
     * Si la matricula NO está bloqueada returna null, si está bloqueada
     * retorna un usuario con la llave de bloqueo en la lista de sus llaves,
     * y la llave con el BloqueoFolio en la lista de sus bloqueos de folio. En el
     * objeto BloqueoFolio está la fecha de bloqueo y el turno (idWorkflowBloqueo)
     * que lo bloqueó
     * @param fid
     * @return Hashtable
     * @throws DAOException
     */
    public Usuario getBloqueoFolio(FolioPk fid) throws DAOException {
        Hashtable hs = null;
        BloqueoFolio rta = null;
        BloqueoFolioEnhanced aux = null;
        LlaveBloqueo llave = null;
        LlaveBloqueoEnhanced llaveAux = null;
        UsuarioEnhanced usuAux = null;
        Usuario usuario = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            FolioEnhanced folio = this.getFolioByID(new FolioEnhancedPk(fid),
                    pm);

            if (folio == null) {
                throw new DAOException("El folio no existe");
            }

            aux = this.getBloqueoFolio(folio.getIdMatricula(), pm);

            if (aux != null) {
                llaveAux = aux.getLlaveBloqueo();
                usuAux = this.getUsuarioByLlaveBloqueo(llaveAux, pm);

                if (usuAux == null) {
                    throw new DAOException(
                        "Error en la obtención del usuario a partir de la llave de bloqueo");
                }

                pm.makeTransient(llaveAux);
                pm.makeTransient(usuAux);
                pm.makeTransient(aux);
            }

            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw e;
        } finally {
            pm.close();
        }

        if (aux != null) {
            hs = new Hashtable();
            rta = (BloqueoFolio) aux.toTransferObject();
            llave = (LlaveBloqueo) llaveAux.toTransferObject();
            usuario = (Usuario) usuAux.toTransferObject();

            llave.addBloqueoFolio(rta);
            usuario.addLlavesBloqueo(llave);
        }

        return usuario;
    }
    
    
    /**
     * Retorna el bloqueo de la matricula en caso que esté bloqueada, si no está bloqueada
     * retorna null
     * @param matricula
     * @param pm
     * @return
     * @throws DAOException
     */
     protected BloqueoFolioEnhanced getBloqueoFolio(String matricula,
         PersistenceManager pm) throws DAOException {
         BloqueoFolioEnhanced rta = null;

         if (matricula != null) {
             try {
                 Query query = pm.newQuery(BloqueoFolioEnhanced.class);
                 query.declareParameters("String matricula");
                 query.setFilter(
                     "this.idMatricula==matricula && this.fechaDesbloqueo==null");

                 Collection col = (Collection) query.execute(matricula);
                 Iterator iter = col.iterator();

                 if (iter.hasNext()) {
                     rta = (BloqueoFolioEnhanced) iter.next();
                 }
             } catch (JDOObjectNotFoundException e) {
                 rta = null;
             } catch (JDOException e) {
                 Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
                 throw new DAOException(e.getMessage(), e);
             }
         }
         return rta;
     }
     
     /**
      * Obtiene el usuario dueño de la llave de bloqueo,
      * @param llave Objeto Persistente
      * @param pm
      * @return
      * @throws DAOException
      */
      protected UsuarioEnhanced getUsuarioByLlaveBloqueo(
          LlaveBloqueoEnhanced llave, PersistenceManager pm)
          throws DAOException {
          UsuarioEnhanced rta = null;

          try {
              Query query = pm.newQuery(UsuarioEnhanced.class);
              query.declareParameters("LlaveBloqueoEnhanced llave");
              query.setFilter("this.llavesBloqueo.contains(llave)");

              Collection col = (Collection) query.execute(llave);
              Iterator iter = col.iterator();

              if (iter.hasNext()) {
                  rta = (UsuarioEnhanced) iter.next();
              }
          } catch (JDOObjectNotFoundException e) {
              rta = null;
          } catch (JDOException e) {
              Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
              throw new DAOException(e.getMessage(), e);
          }

          return rta;
      }

	/**
	 * Obtiene un <code>Ciudadano</code> dado su identificador.
	 * Genera una excepción si no se encuentra el ciudadano con el identificado
	 * dado.
	 * @param cID identificador del <code>Ciudadano</code>
	 * @return <code>Ciudadano</code> con sus atributos
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Ciudadano
	 */
	public Ciudadano getCiudadanoByID(CiudadanoPk cID)
		throws DAOException {
		CiudadanoEnhanced cr = null;
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			cr = this.getCiudadanoByID(new CiudadanoEnhancedPk(cID), pm);
			pm.makeTransient(cr);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw e;
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return (Ciudadano) cr.toTransferObject();
	}



	/**
	 * Obtiene un <code>CiudadanoEnhanced</code> dado su identificador
	 * @param cID identificador del <code>Ciudadano</code>
	 * @return <code>CiudadanoEnhanced</code> con sus atributos
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Ciudadano
	 * @see gov.sir.core.negocio.modelo.jdoGenie.CiudadanoEnhanced
	 */
	protected CiudadanoEnhanced getCiudadanoByID(CiudadanoEnhancedPk cID,
		PersistenceManager pm) throws DAOException {
		CiudadanoEnhanced rta = null;

		if (cID.idCiudadano != null) {
			try {
				rta = (CiudadanoEnhanced) pm.getObjectById(cID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}



	/**
	 * Obtiene un <code>Circulo</code> dado su identificador
	 * Genera una excepción si no se encuentra el Círculo con el identificador
	 * dado.
	 * @param cID identificador del <code>Circulo</code>
	 * @return <code>Circulo</code> con sus atributos y jerarquia: circulos proceso y zonas registrales.
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Circulo
	 */
	public Circulo getCirculoByID(CirculoPk cID) throws DAOException {
		CirculoEnhanced cr = null;
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			cr = this.getCirculoByID(new CirculoEnhancedPk(cID), pm);
			pm.makeTransient(cr);

			List cirpro = cr.getCirculoProcesos();
			Iterator itf = cirpro.iterator();

			//Se hacen transientes los Círculos Procesos asociados con el Círculo.
			/*
			while (itf.hasNext()) {
				CirculoProcesoEnhanced cp = (CirculoProcesoEnhanced) itf.next();
				pm.makeTransient(cp.getProceso());
				pm.makeTransient(cp.getCirculo());
				pm.makeTransient(cp);
			}*/

			List zonas = cr.getZonaRegistrales();
			Iterator itz = zonas.iterator();


            //Se hacen transientes las Zonas Registrales asociadas con el Círculo.
			while (itz.hasNext()) {
				ZonaRegistralEnhanced zr = (ZonaRegistralEnhanced) itz.next();
				pm.makeTransient(zr.getVereda());
				pm.makeTransient(zr.getCirculo());
				pm.makeTransient(zr);
			}

		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw e;
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return (Circulo) cr.toTransferObject();
	}




	/**
	 * Obtiene un <code>CirculoEnhanced</code> dado su identificador
	 * @param cID identificador del <code>Circulo</code>
	 * @param pm PersistenceManager de la transacción.
	 * @return <code>CirculoEnhanced</code> con sus atributos y jerarquia: circulos proceso y zonas registrales.
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.jdoGenie.CirculoEnhanced
	 */
	protected CirculoEnhanced getCirculoByID(CirculoEnhancedPk cID,
		PersistenceManager pm) throws DAOException {
		CirculoEnhanced rta = null;

		if (cID.idCirculo != null) {
			try {
				rta = (CirculoEnhanced) pm.getObjectById(cID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage(), e);
				Log.getInstance().debug(JDOSolicitudesDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}



	/**
	 * Obtiene un <code>DocumentoEnhanced</code> dado su identificador
	 * @param dID identificador del <code>Documento</code>
	 * @param pm PersistenceManager de la transacción.
	 * @return <code>DocumentoEnhanced</code> con sus atributos.
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.jdoGenie.DocumentoEnhanced
	 */
	protected DocumentoEnhanced getDocumentoByID(DocumentoEnhancedPk dID,
		PersistenceManager pm) throws DAOException {
			DocumentoEnhanced rta = null;

		if (dID.idDocumento != null) {
			try {
				rta = (DocumentoEnhanced) pm.getObjectById(dID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}


	/**
	 * Obtiene un <code>DocumentoFotocopiaEnhanced</code> dado su identificador
	 * @param dID identificador del <code>Documento</code>
	 * @param pm PersistenceManager de la transacción.
	 * @return <code>DocumentoFotocopiaEnhanced</code> con sus atributos.
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.jdoGenie.DocumentoFotocopiaEnhanced
	 */
	protected DocumentoFotocopiaEnhanced getDocumentoFotocopia(DocumentoFotocopiaEnhancedPk dID,
		PersistenceManager pm) throws DAOException {
		DocumentoFotocopiaEnhanced rta = null;

		if ((dID.idDocFotocopia != null)&&(dID.idSolicitud!=null)) {
			try {
				rta = (DocumentoFotocopiaEnhanced) pm.getObjectById(dID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}
		return rta;
	}



	/**
	 * Obtiene un check item dado su identificador, método utilizado para
	 * transacciones
	 * @param cID identificador del checkItem
	 * @param pm PersistenceManager de la transaccion
	 * @return <code>CheckItem</code> con sus atributos
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhanced
	 */
	protected CheckItemEnhanced getCheckItemByID(CheckItemEnhancedPk cID,
		PersistenceManager pm) throws DAOException {
			CheckItemEnhanced rta = null;

		if (cID.idCheckItem != null && cID.idSubtipoSol!= null) {
			try {
				rta = (CheckItemEnhanced) pm.getObjectById(cID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}


	/**
	 * Obtiene un objeto <code>Folio</code> dado su identificador.
	 * Genera una excepción si no se encuentra el Folio con el identificado
	 * dado.
	 * @param fID identificador del <code>Folio</code>
	 * @return <code>Folio</code> con sus atributos y jerarquia, Estado, Lindero, Zona Registral
	 * @throws DAOException
	 * @see gov.sir.core.negocio.modelo.Folio
	 */
	public Folio getFolioByID(FolioPk fID) throws DAOException {
		FolioEnhanced rta = null;
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			rta = this.getFolioByID(new FolioEnhancedPk(fID), pm);

			if (rta != null) {

				//Se hacen transientes cada uno de los objetos asociados
				//con el Folio.
				pm.makeTransient(rta.getEstado());
				pm.makeTransient(rta.getLindero());
				pm.makeTransient(rta.getTipoPredio());
				pm.makeTransient(rta.getZonaRegistral().getVereda()
									.getMunicipio().getDepartamento());
				pm.makeTransient(rta.getZonaRegistral().getVereda()
									.getMunicipio());
				pm.makeTransient(rta.getZonaRegistral().getVereda());
				pm.makeTransient(rta.getZonaRegistral().getCirculo());
				pm.makeTransient(rta.getZonaRegistral());
				pm.makeTransient(rta);
			}
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw e;
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return (Folio) rta.toTransferObject();
	}




	/**
	 * Obtiene un objeto <code>FolioEnhanced</code> dado su identificador.
	 * <p>
	 * Método utilizado por transacciones.
	 * @param fID identificador del <code>Folio</code>
	 * @return <code>FolioEnhanced</code> con sus atributos y jerarquia, Estado, Lindero, Zona Registral
	 * @throws DAOException
	 * @see gov.sir.core.negocio.modelo.jdogenie.Folio
	 */
	protected FolioEnhanced getFolioByID(FolioEnhancedPk fID,
		PersistenceManager pm) throws DAOException {
		FolioEnhanced rta = null;

		if ((fID.idMatricula != null)) {
			try {
				rta = (FolioEnhanced) pm.getObjectById(fID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOSolicitudesDAO.class,e);
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}



	/**
	 * Obtiene un objeto <code>Usuario</code> dado su identificador.
	 * <p>
	 * Genera una excepción si no se encuentra el usuario con el identificado
	 * dado.
	 * @param uID identificador del <code>Usuario</code>
	 * @return <code>Usuario</code> con sus atributos.
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Usuario
	 */
	public Usuario getUsuarioByID(UsuarioPk uID) throws DAOException {
		UsuarioEnhanced ur = null;
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			ur = this.getUsuarioByID(new UsuarioEnhancedPk(uID), pm);
			pm.makeTransient(ur);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return (Usuario) ur.toTransferObject();
	}




	/**
	 * Obtiene un objeto <code>UsuarioEnhanced</code> dado su identificador.
	 * <p>
	 * Genera una excepción si no se encuentra el usuario con el identificador
	 * dado.
	 * @param uID identificador del <code>Usuario</code>
	 * @return <code>UsuarioEnhanced</code> con sus atributos.
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Usuario
	 * @see gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced
	 */
	protected UsuarioEnhanced getUsuarioByID(UsuarioEnhancedPk uID,
		PersistenceManager pm) throws DAOException {
		UsuarioEnhanced rta = null;

		if (String.valueOf(uID.idUsuario) != null) {
			try {
				rta = (UsuarioEnhanced) pm.getObjectById(uID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}



	/**
	 * Obtiene un objeto <code>Solicitud</code> dado su identificador.
	 * <p>
	 * Genera una excepción si no se encuentra la <code>Solicitud</code>
	 * con el identificado dado.
	 * @param sID identificador de la <code>Solicitud</code>
	 * @return <code>Solicitud</code> con sus atributos y jerarquía:
	 * Circulo, Proceso, Usuario, TurnoAnterior, Folios, Liquidacones, Pagos
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Solicitud
	 */
	public Solicitud getSolicitudByID(SolicitudPk sID)throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		SolicitudEnhanced sr = null;
		try{
			sr = this.getSolicitudByID(new SolicitudEnhancedPk(sID), pm);
			if (sr == null)
			{
				throw new DAOException("No encontró la solicitud con el ID: "+sID.idSolicitud);
			}
			this.makeTransientSolicitud(sr, pm);
		}
		catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw e;
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		return (Solicitud) sr.toTransferObject();
	}




	/**
	 * Obtiene un objeto <code>Solicitud</code> dado su identificador.
	 * <p>
	 * Genera una excepción si no se encuentra la <code>Solicitud</code>
	 * con el identificado dado.
	 * @param sID identificador de la <code>Solicitud</code>
	 * @return <code>Solicitud</code> con sus atributos y jerarquía:
	 * Circulo, Proceso, Usuario, TurnoAnterior, Folios, Liquidacones, Pagos
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Solicitud
	 */
	protected SolicitudEnhanced makeTransientSolicitud(SolicitudEnhanced sr, PersistenceManager pm)
		throws DAOException {
		JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
		JDOPagosDAO pagosDAO = new JDOPagosDAO();
		try {
			if (sr == null)
			{
				return null;
			}
			//Se hacen transientes cada uno de los objetos asociados con
			//la solicitud.
			
			this.makeTransientOficinaOrigen(sr.getCirculo().getOficinaOrigen(), pm);

			pm.makeTransient(sr.getCirculo());
			pm.makeTransient(sr.getProceso());
			pm.makeTransient(sr.getUsuario());
			pm.makeTransient(sr.getTurnoAnterior());

			try
			{
				pm.makeTransient(sr.getTurno());
			}
			catch (JDOObjectNotFoundException e)
			{
			}


			List folios = sr.getSolicitudFolios();
			Iterator itf = folios.iterator();

			while (itf.hasNext()) {
				SolicitudFolioEnhanced sf = (SolicitudFolioEnhanced) itf.next();
				pm.makeTransient(sf.getFolio());
				pm.makeTransient(sf);
			}

			//Solicitudes Hijas
			List solHijs = sr.getSolicitudesHijas();
			Iterator ith = solHijs.iterator();
			SolicitudEnhanced solAux;
			LiquidacionEnhanced liqAux;
			SolicitudFolioEnhanced sfAux;

			while (ith.hasNext()) {
				SolicitudAsociadaEnhanced solAsoc = (SolicitudAsociadaEnhanced) ith.next();
				solAux = solAsoc.getSolicitudHija();
				for(Iterator itSF = solAux.getSolicitudFolios().iterator();itSF.hasNext();){
					sfAux = (SolicitudFolioEnhanced)itSF.next();
					pm.makeTransient(sfAux);
				}
				pm.makeTransientAll(solAux.getSolicitudFolios());

				for(Iterator itLiq = solAux.getLiquidaciones().iterator();itLiq.hasNext();){
					liqAux = (LiquidacionEnhanced)itLiq.next();
					pm.makeTransient(liqAux.getUsuario());
					pm.makeTransient(liqAux);
					liqAux.setSolicitud(solAux);
				}
				pm.makeTransientAll(solAux.getLiquidaciones());

				TurnoEnhanced turnoAsoc = turnosDAO.getTurnoBySolicitud(solAux,
                        pm);
				solAux.setTurno(turnoAsoc);
                pm.makeTransient(turnoAsoc);

				pm.makeTransient(solAux);
				pm.makeTransient(solAsoc);
			}

			//Solicitudes Padre
			List solPads = sr.getSolicitudesPadres();
			Iterator itp = solPads.iterator();

			while (itp.hasNext()) {
				SolicitudAsociadaEnhanced solAsoc = (SolicitudAsociadaEnhanced) itp.next();
				pm.makeTransient(solAsoc.getSolicitudPadre());
				pm.makeTransient(solAsoc);
			}


			//CASO: Solicitud de Certificados.
			if (sr instanceof SolicitudCertificadoEnhanced) {
				SolicitudCertificadoEnhanced sc = (SolicitudCertificadoEnhanced) sr;
				pm.makeTransient(sc.getCiudadano());
				pm.makeTransient(sc.getDocumento());

				try {
					DatosAntiguoSistemaEnhanced da = sc.getDatosAntiguoSistema();
					pm.makeTransient(da);
				} catch (JDOObjectNotFoundException e) {
					DatosAntiguoSistemaEnhanced da = null;
				}

				List liq = sr.getLiquidaciones();
				Iterator itl = liq.iterator();

				while (itl.hasNext()) {
					LiquidacionTurnoCertificadoEnhanced l = (LiquidacionTurnoCertificadoEnhanced) itl.next();
					pm.makeTransient(l.getTipoCertificado());
					pm.makeTransient(l.getSolicitud());
					pm.makeTransient(l);
				}
			}



			//CASO: Solicitud de Consultas.
			if (sr instanceof SolicitudConsultaEnhanced) {
				SolicitudConsultaEnhanced sCons = (SolicitudConsultaEnhanced) sr;
				pm.makeTransient(sCons.getTipoConsulta());
				pm.makeTransient(sCons.getCiudadano());

				List busc = sCons.getBusquedas();
				Iterator itl = busc.iterator();

				while (itl.hasNext()) {
					BusquedaEnhanced b = (BusquedaEnhanced) itl.next();
					pm.makeTransient(b);
				}

				//pm.makeTransientAll(busc);

                List liq = sr.getLiquidaciones();
                Iterator iter = liq.iterator();

                while (iter.hasNext()) {
                        LiquidacionTurnoConsultaEnhanced l = (LiquidacionTurnoConsultaEnhanced) iter.next();
                        pm.makeTransient(l.getAlcanceGeografico());
                        pm.makeTransient(l.getSolicitud());
                        pm.makeTransient(l);
                }
			}



			//CASO: Solicitud de Correcciones.
			if (sr instanceof SolicitudCorreccionEnhanced) {
				SolicitudCorreccionEnhanced sCons = (SolicitudCorreccionEnhanced) sr;
				pm.makeTransient(sCons.getTipoRecepcionPeticion());
				pm.makeTransient(sCons.getCiudadano());
			}



			//CASO: Solicitud de Fotocopias.
			if (sr instanceof SolicitudFotocopiaEnhanced) {
				SolicitudFotocopiaEnhanced sc = (SolicitudFotocopiaEnhanced) sr;
				pm.makeTransient(sc.getCiudadano());

				//Documentos de fotocopia
				DocumentoFotocopiaEnhanced docFot;
				for(Iterator it = sc.getDocumentoFotocopia().iterator();it.hasNext();){
					docFot = (DocumentoFotocopiaEnhanced) it.next();
					pm.makeTransient(docFot.getTipoDocumento());
					pm.makeTransient(docFot.getTipoFotocopia());
					pm.makeTransient(docFot);
				}


				List liq = sr.getLiquidaciones();
				Iterator itl = liq.iterator();

				while (itl.hasNext()) {
					LiquidacionTurnoFotocopiaEnhanced l = (LiquidacionTurnoFotocopiaEnhanced) itl.next();
					//pm.makeTransient(l.getTipoFotocopia());
					pm.makeTransient(l.getSolicitud());
					pm.makeTransient(l);
				}
			}


			//CASO: Solicitud de Devoluciones.
			if (sr instanceof SolicitudDevolucionEnhanced) {
				SolicitudDevolucionEnhanced sc = (SolicitudDevolucionEnhanced) sr;
				pm.makeTransient(sc.getCiudadano());

				List liq = sr.getLiquidaciones();
				Iterator itl = liq.iterator();

				while (itl.hasNext()) {
					LiquidacionTurnoDevolucionEnhanced l = (LiquidacionTurnoDevolucionEnhanced) itl.next();
					pm.makeTransient(l.getSolicitud());
					pm.makeTransient(l);
				}
				
				List solte = sc.getSolicitantes();
				Iterator its = solte.iterator();

				while (its.hasNext()) {
					SolicitanteEnhanced c = (SolicitanteEnhanced) its.next();
					pm.makeTransient(c.getSolicitud());
					pm.makeTransient(c);
				}
				
				List cons = sc.getConsignaciones();
				Iterator itc = cons.iterator();

				while (itc.hasNext()) {
					ConsignacionEnhanced co = (ConsignacionEnhanced) itc.next();
					pm.makeTransient(co.getSolicitud());
					pm.makeTransient(co);
				}
			}


			//CASO: Solicitud de Registro.
			if (sr instanceof SolicitudRegistroEnhanced) {
				SolicitudRegistroEnhanced sc = (SolicitudRegistroEnhanced) sr;
				pm.makeTransient(sc.getCiudadano());
				//pm.makeTransient(sc.getDocumento());
				pm.makeTransient(sc.getSubtipoAtencion());
				pm.makeTransient(sc.getSubtipoSolicitud());

				//Se hace transiente el documento asociado a la solicitud de registro
			  	 if (sc.getDocumento()!= null)
			  	 {
			  	 	pm.makeTransient (sc.getDocumento().getTipoDocumento());

			  	 	if (sc.getDocumento().getOficinaOrigen()!=null)
			  	 	{
						pm.makeTransient(sc.getDocumento().getOficinaOrigen().getVereda());
						pm.makeTransient(sc.getDocumento().getOficinaOrigen().getTipoOficina());
						pm.makeTransient(sc.getDocumento().getOficinaOrigen());
			  	 	}

					pm.makeTransient(sc.getDocumento());
			  	 }


				try {
					DatosAntiguoSistemaEnhanced da = sc.getDatosAntiguoSistema();
					if(da != null && da.getDocumento()!=null){
						if (da.getDocumento()!= null)
						{
						   pm.makeTransient (da.getDocumento().getTipoDocumento());

						   if (da.getDocumento().getOficinaOrigen()!=null)
						   {
							   pm.makeTransient(da.getDocumento().getOficinaOrigen().getVereda());
							   pm.makeTransient(da.getDocumento().getOficinaOrigen().getTipoOficina());
							   pm.makeTransient(da.getDocumento().getOficinaOrigen());
						   }

						   pm.makeTransient(da.getDocumento());
						}
					}
					pm.makeTransient(da);
				} catch (JDOObjectNotFoundException e) {
					sc.setDatosAntiguoSistema(null);
				}
				List checks = sc.getCheckedItems();
				Iterator itc = checks.iterator();
				while (itc.hasNext()) {
					SolicitudCheckedItemEnhanced si = (SolicitudCheckedItemEnhanced) itc.next();
					CheckItemEnhanced c = si.getCheckItem();
					pm.makeTransient(c);
					pm.makeTransient(si.getCheckItem());
					pm.makeTransient(si);
				}

				List liq = sr.getLiquidaciones();
				Iterator itl = liq.iterator();
				while (itl.hasNext()) {
					LiquidacionTurnoRegistroEnhanced l = (LiquidacionTurnoRegistroEnhanced) itl.next();
					List actos = l.getActos();
					Iterator ita = actos.iterator();
					while (ita.hasNext()) {
						ActoEnhanced a = (ActoEnhanced) ita.next();
						if (a.getTipoActo()!= null)
						{
							pm.makeTransient(a.getTipoActo().getTipoCalculo());

						}
						pm.makeTransient(a.getTipoActo());
						if (a.getTipoDerechoReg()!=null)
						{
							pm.makeTransient(a.getTipoDerechoReg().getTipoDerechoRegistral());
						}

						//Hacer transiente el circulo.
						if (a.getLiquidacion()!=null)
						{
							if (a.getLiquidacion().getSolicitud()!= null)
							{
							    pm.makeTransient(a.getLiquidacion().getSolicitud().getCirculo());
								pm.makeTransient(a.getLiquidacion().getSolicitud());
							}

						}

						pm.makeTransient(a.getTipoDerechoReg());

						pm.makeTransient(a.getTipoImpuesto());

						pm.makeTransient(a);
					}
					pm.makeTransient(l.getSolicitud());
					try {
							PagoEnhanced pago = l.getPago();


							if (pago!=null)
							{
								List listaAplicaciones = new ArrayList();
								listaAplicaciones = pago.getAplicacionPagos();
								if (listaAplicaciones !=null )
								{
									for(int i= 0; i <listaAplicaciones.size(); i++)
									{
										AplicacionPagoEnhanced ap = (AplicacionPagoEnhanced) listaAplicaciones.get(i);

										if (ap != null)
										{
											DocumentoPagoEnhanced docPago = ap.getDocumentoPago();
											if (docPago != null){
												pagosDAO.makeTransientDocumentoPago(ap.getDocumentoPago(), pm);
											}
										pm.makeTransient(ap);
										}



									}
								}
							}

						pm.makeTransient(pago);
					}
					catch (JDOObjectNotFoundException e)
					{
							PagoEnhanced pago = null;
					}
					pm.makeTransient(l);

				}

			}


			//CASO: Solicitud de Certificados Masivos.
			if (sr instanceof SolicitudCertificadoMasivoEnhanced) {
				SolicitudCertificadoMasivoEnhanced sc = (SolicitudCertificadoMasivoEnhanced) sr;
				pm.makeTransient(sc.getCiudadano());

                                try {
					DatosAntiguoSistemaEnhanced da = sc.getDatosAntiguoSistema();
					pm.makeTransient(da);
				} catch (JDOObjectNotFoundException e) {
					DatosAntiguoSistemaEnhanced da = null;
				}
                                /**
                                 * @author      :   Julio Alcázar Rivas
                                 * @change      :   makeTransient al documento de la solicitud
                                 * Caso Mantis  :   000941
                                 */
                                if (sc.getDocumento()!= null)
			  	 {
			  	 	pm.makeTransient (sc.getDocumento().getTipoDocumento());
                                        if (sc.getDocumento().getOficinaOrigen()!=null)
			  	 	{
						pm.makeTransient(sc.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getDepartamento());
                                                pm.makeTransient(sc.getDocumento().getOficinaOrigen().getVereda().getMunicipio());
                                                pm.makeTransient(sc.getDocumento().getOficinaOrigen().getVereda());
						pm.makeTransient(sc.getDocumento().getOficinaOrigen().getTipoOficina());
						pm.makeTransient(sc.getDocumento().getOficinaOrigen());
			  	 	}

					pm.makeTransient(sc.getDocumento());
			  	 }
				List liq = sr.getLiquidaciones();
				Iterator itl = liq.iterator();

				while (itl.hasNext()) {
					LiquidacionTurnoCertificadoMasivoEnhanced l = (LiquidacionTurnoCertificadoMasivoEnhanced) itl.next();
					pm.makeTransient(l.getTipoCertificado());
					pm.makeTransient(l.getSolicitud());
					pm.makeTransient(l);
				}
			}

			pm.makeTransient(sr);
		} catch (DAOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw e;
		} catch (Throwable e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		return sr;
	}



	/**
	 * Obtiene una solicitud dado su identificador.
	 * <p>Método utilizado para transacciones.
	 * <p>Retorna null, si no se encuentra la <code>Solicitud</code>
	 * con el identificador dado.
	 * @param sID identificador de la <code>Solicitud</code>
	 * @param pm PersistenceManager de la transacción
	 * @return <code>Solicitud</code> con sus atributos
	 * @throws DAOException
	 * @see gov.sir.core.negocio.modelo.Solicitud
	 */
	protected SolicitudEnhanced getSolicitudByID(SolicitudEnhancedPk sID,
		PersistenceManager pm) throws DAOException {
		SolicitudEnhanced rta = null;

		//Se coloca un tiempo de delay
		JDOVariablesOperativasDAO jdoDAO = new JDOVariablesOperativasDAO();
		boolean respuesta = jdoDAO.getObjectByLlavePrimaria(sID, 3, pm);

		if (sID.idSolicitud != null) {
			try {
				rta = (SolicitudEnhanced) pm.getObjectById(sID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().debug(JDOSolicitudesDAO.class,e);
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/*******************************************************************************/
	/*                                                                             */
	/*                METODOS UTILIZADOS EN EL PROCESO DE CONSULTAS                */
	/*                                                                             */
	/*******************************************************************************/


	/**
	* Crea una solicitud de consultas en el sistema
	* @param  s SolicitudConsulta con sus atributos, exceptuando el identificador
	* @return una <code>SolicitudConsulta</code>persistente con su identificador.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SolicitudConsulta
	*/
	public SolicitudConsulta crearSolicitudConsulta(Solicitud s)
		throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		SolicitudConsultaEnhanced sc = null;
		SolicitudConsulta solConsulta = (SolicitudConsulta) s;
		
		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
			sc = this.crearSolicitudConsulta(SolicitudConsultaEnhanced.enhance(
						solConsulta), pm);
			pm.currentTransaction().commit();

			this.makeTransientSolicitud(sc, pm);
		}
		 catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw (e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
		} finally {
			pm.close();
		}

		return (SolicitudConsulta) sc.toTransferObject();
	}

	/**
	* Crea una solicitud de consultas en el sistema.
	* <p>
	* Método utilizado para transacciones.
	* @param  s <code>SolicitudConsultaEnhanced</code> con sus atributos,
	* exceptuando el identificador.
	* @param pm PersistenceManager de la transacción
	* @return una <code>SolicitudConsulta</code> persistente con su identificador.
	* @throws <code>DAOException</code>
	*/
	protected SolicitudConsultaEnhanced crearSolicitudConsulta(
		SolicitudConsultaEnhanced s, PersistenceManager pm)
		throws DAOException {
		String table = "SIR_OP_SOLICITUD";
		JDOTurnosDAO TurnosDAO = new JDOTurnosDAO();
		JDOProcesosDAO ProcesosDAO = new JDOProcesosDAO();
		JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
		ProcesoEnhanced pr = new ProcesoEnhanced();

		try {
			//Se asigna a la solicitud de certificado el id de acuerdo con una secuencia
			//SecuenciasEnhanced.ID seqId = new SecuenciasEnhanced.ID();
			//seqId.tableName = table;
 		    // SecuenciasEnhanced seq = (SecuenciasEnhanced) this.getSecuenciaByID(seqId,pm);
			//seq.setLastUsedId(seq.getLastUsedId() + 1);
			// Se valida que la solicitud consulta recibida no sea nula.
			if (s == null) {
				throw new DAOException("La solicitud recibida es null");
			}
			s.setIdSolicitud(this.getLlavePrimariaSolicitud(s, pm));

			//Se valida que exista un Círculo con el identificador recibido
			CirculoEnhanced c = s.getCirculo();
			if (c == null) {
				throw new DAOException(
					"El Circulo asociado a la solicitud es null");
			}
            CirculoEnhancedPk cId = new CirculoEnhancedPk();
            cId.idCirculo = c.getIdCirculo();
            CirculoEnhanced cr = this.getCirculoByID(cId,
            		pm);
            if (cr == null) {
            	throw new DAOException("No encontró el circulo con el ID: " +
            		cId.idCirculo);
            }
            s.setCirculo(cr);

			//Se valida que exista un Proceso con el identificador recibido
			pr = s.getProceso();
			if (pr == null) {
				throw new DAOException(
					"El Proceso asociado a la solicitud es null");
			}
            ProcesoEnhancedPk prId = new ProcesoEnhancedPk();
            prId.idProceso = pr.getIdProceso();
            ProcesoEnhanced prr = ProcesosDAO.getProcesoByID(prId, pm);
            if (prr == null) {
            	throw new DAOException("No encontró el proceso con el ID: " +
            		prId.idProceso);
            }
            s.setProceso(prr);

			//Se valida (Si aplica) que exista un turno anterior con el id recibido.
			if (s.getTurnoAnterior() != null) {
				TurnoEnhanced tant = s.getTurnoAnterior();
				TurnoEnhanced tantr = TurnosDAO.getTurnoByWFId(tant.getIdWorkflow(),
						pm);
				if (tantr == null) {
					throw new DAOException(
						"No encontró el turno anterior con el ID: " +
						tant.getIdTurno());
				}
				s.setTurnoAnterior(tantr);
			}

			//Se valida que exista un usuario con  el id recibido.
			UsuarioEnhanced u = s.getUsuario();
			if (u == null) {
				throw new DAOException(
					"El Usuario asociado a la solicitud es null");
			}
            UsuarioEnhancedPk uId = new UsuarioEnhancedPk();
            uId.idUsuario = u.getIdUsuario();
            UsuarioEnhanced ur = this.getUsuarioByID(uId, pm);
            if (ur == null) {
            	throw new DAOException("No encontró el usuario con el ID: " +
            		uId.idUsuario);
            }
            s.setUsuario(ur);

			//Se valida que exista un tipoConsulta con el id recibido.
			TipoConsultaEnhanced tc = s.getTipoConsulta();
			if (tc == null) {
				throw new DAOException(
					"El tipo de consulta asociado a la solicitud es null");
			}
            TipoConsultaEnhancedPk tcId = new TipoConsultaEnhancedPk();
            tcId.idTipoConsulta = tc.getIdTipoConsulta();
            TipoConsultaEnhanced tcPers = variablesDAO.getTipoConsultaByID(tcId,
            		pm);
            if (tcPers == null) {
            	throw new DAOException(
            		"No encontró el tipoConsulta con id:  " +
            		tc.getIdTipoConsulta());
            }
            s.setTipoConsulta(tcPers);

			//Como el proceso es de creación, se asignan a los atributos lastIdLiquidación
			//y lastIdBusqueda el valor 0, pues aún la solicitud no los tiene asociados.
			s.setLastIdLiquidacion(0);
			s.setLastIdBusqueda(0);
			//Se intenta hacer la persistencia sobre la búsqueda.
			List busq = s.getBusquedas();
			if (busq == null) {
				throw new DAOException(
					"La Solicitud no tiene asociada ninguna busqueda");
			}
            Iterator it2 = busq.iterator();
            while (it2.hasNext()) {
            	BusquedaEnhanced bs = (BusquedaEnhanced) it2.next();
            	bs = this.addBusquedaToSolicitudConsulta(bs, s, pm);
            }
			List folios = s.getSolicitudFolios();
			Iterator it = folios.iterator();

			while (it.hasNext()) {
				SolicitudFolioEnhanced sf = (SolicitudFolioEnhanced) it.next();
				FolioEnhanced fr = this.getFolioByMatricula(sf.getIdMatricula(),pm);
				if (fr == null) {
					throw new DAOException("No encontró el folio con el ID: " +
						sf.getIdMatricula());
				}
				sf.setFolio(fr);
				sf.setIdMatricula(fr.getIdMatricula());
				sf.setSolicitud(s);
				pm.makeTransient(sf);
				sf.setIdSolicitud(s.getIdSolicitud());
			}

           //VALIDAR CIUDADANO
			CiudadanoEnhanced ciu = s.getCiudadano();
			if (ciu !=null){

			String docCiudadano = ciu.getDocumento();
			String tipoDocCiudadano = ciu.getTipoDoc();
			CiudadanoEnhanced ciur = this.getCiudadanoByDocumento(tipoDocCiudadano,
			  docCiudadano, pm, ciu.getIdCirculo());
              if (ciur == null) {
                  ciur = this.crearCiudadano(ciu, pm);
              }
			  s.setCiudadano(ciur);
			}

            //Se recuperan los objetos persistentes asociados con el Documento.
            if (s.getDocumento() != null)
            {
            	long a = this.getSecuencial(CDocumento.TABLE_NAME,null);
            	DocumentoEnhanced docEnhanced = s.getDocumento();
            	docEnhanced.setIdDocumento(String.valueOf (a));

				//Tipo de Documento
				TipoDocumentoEnhanced tipoDocEnh = docEnhanced.getTipoDocumento();
				if (tipoDocEnh != null)
				{
					TipoDocumentoEnhancedPk idTipoDocEnh = new TipoDocumentoEnhancedPk();
					idTipoDocEnh.idTipoDocumento = tipoDocEnh.getIdTipoDocumento();

					TipoDocumentoEnhanced tipoDocPers = (TipoDocumentoEnhanced) pm.getObjectById(idTipoDocEnh,true);
					if (tipoDocPers == null)
					{
						throw new DAOException ("No se encontró el tipo de documento con el identificador: "+idTipoDocEnh.idTipoDocumento);
					}
					docEnhanced.setTipoDocumento(tipoDocPers);
				}
				
				//Oficina Origen
				OficinaOrigenEnhanced ofOrigEnh = docEnhanced.getOficinaOrigen();
				if (ofOrigEnh != null)
				{
					OficinaOrigenEnhancedPk idOfOrigenEnh = new OficinaOrigenEnhancedPk();
					idOfOrigenEnh.idOficinaOrigen = ofOrigEnh.getIdOficinaOrigen();
                                         /*
                                          *  @author Carlos Torres
                                          *  @chage   se agrega validacion de version diferente
                                          *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                          */
                                        idOfOrigenEnh.version = ofOrigEnh.getVersion();

					OficinaOrigenEnhanced ofOrigPers = (OficinaOrigenEnhanced) pm.getObjectById(idOfOrigenEnh,true);
					if (ofOrigPers == null)
					{
						throw new DAOException ("No se encontró la oficina origen con el identificador: "+idOfOrigenEnh.idOficinaOrigen);
					}
					docEnhanced.setOficinaOrigen(ofOrigPers);
				}
				if(docEnhanced!=null&&s.getCirculo()!=null){
					docEnhanced.setCirculo(s.getCirculo().getIdCirculo());
				}
			s.setDocumento(docEnhanced);
            }

			//Se hace persistencia sobre la solicitud.
			pm.makePersistent(s);
		} catch (DAOException daoE) {
			Log.getInstance().error(JDOSolicitudesDAO.class,daoE.getMessage());
			Log.getInstance().error(JDOSolicitudesDAO.class,daoE);
			throw new DAOException(daoE.getMessage(), daoE);
		}
		 catch (JDOObjectNotFoundException ex) {
			Log.getInstance().error(JDOSolicitudesDAO.class,ex.getMessage());
			ex.printStackTrace();
			throw new DAOException(ex.getMessage(), ex);
		}
		 catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
		}

		return s;
	}

	/**
	* Agrega una busqueda en el sistema y la asocia a una solicitud, método
	* utilizado para transacciones
	* @param  b busqueda con sus atributos, exceptuando el identificador.
	* @param  s solicitud consulta a la que se debe asociar la busqueda.
	* @param  pm PersistenceManager de la transaccion
	* @return  Búsqueda generada
	* @throws DAOException
	*/
	protected BusquedaEnhanced addBusquedaToSolicitudConsulta(
		BusquedaEnhanced b, SolicitudConsultaEnhanced s, PersistenceManager pm)
		throws DAOException {
		BusquedaEnhanced rta = null;

		try {
			//Se valida que la solicitud consulta recibida no sea nula.
			if (s == null) {
				throw new DAOException("La solicitud recibida es null");
			}

			//Se valida que la busqueda recibida no sea nula.
			if (b == null) {
				throw new DAOException("La busqueda recibida es null");
			}

			b.setSolicitud(s);
			b.setIdSolicitud(s.getIdSolicitud());
			b.setIdBusqueda(String.valueOf(s.getLastIdBusqueda() + 1));
			b.setFechaCreacion(new Date());
			s.setLastIdBusqueda(s.getLastIdBusqueda() + 1);
			rta = b;

		} catch (DAOException daoE) {
			Log.getInstance().error(JDOSolicitudesDAO.class,daoE);
			Log.getInstance().error(JDOSolicitudesDAO.class,daoE.getMessage(), daoE);
			throw new DAOException(daoE.getMessage(), daoE);
		}
		 catch (JDOObjectNotFoundException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e);
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e);
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}

		return rta;
	}

	/**
	 * Agrega un documento fotocopia y la asocia a una solicitud, método
	 * utilizado para transacciones
	 * @param doc
	 * @param s
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected DocumentoFotocopiaEnhanced addDocumentoFotocopiaToSolicitud(
		DocumentoFotocopiaEnhanced doc, SolicitudFotocopiaEnhanced s, PersistenceManager pm)
		throws DAOException {
		DocumentoFotocopiaEnhanced rta = null;

		try {
			//Se valida que la solicitud consulta recibida no sea nula.
			if (s == null) {
				throw new DAOException("La solicitud recibida es null");
			}

			//Se valida que el.
			if (doc == null) {
				throw new DAOException("El documento recibido es null");
			}

			//Se valida que el documento fotocopia tenga tipo de fotocopia:

			if(doc.getTipoFotocopia()!=null){
				//Se valida la existencia del tipo de fotocopia
				TipoFotocopiaEnhancedPk  tfID = new TipoFotocopiaEnhancedPk();
				tfID.idTipoFotocopia = doc.getTipoFotocopia().getIdTipoFotocopia();
				TipoFotocopiaEnhanced tipo = this.getTipoFotocopia(tfID, pm);

				if(tipo==null){
					throw new DAOException("El tipo de fotocopia no existe: "+tfID.idTipoFotocopia);
				}
				doc.setTipoFotocopia(tipo);
			}

			if(doc.getTipoDocumento()==null){
				throw new DAOException("El documento fotocopia debe tener un tipo de documento asociado");
			}


			//Se valida la existencia del tipo de documento
			TipoDocumentoEnhancedPk  tdID = new TipoDocumentoEnhancedPk();
			tdID.idTipoDocumento= doc.getTipoDocumento().getIdTipoDocumento();
			TipoDocumentoEnhanced tipoDoc = this.getTipoDocumento(tdID, pm);

			if(tipoDoc==null){
				throw new DAOException("El tipo de documento no existe: "+tdID.idTipoDocumento);
			}


			doc.setSolicitud(s);
			doc.setTipoDocumento(tipoDoc);

			doc.setIdDocFotocopia(String.valueOf(s.getLastIdDocumentoFotocopia() + 1));
			s.setLastIdDocumentoFotocopia(s.getLastIdDocumentoFotocopia() + 1);
			rta = doc;

		} catch (DAOException daoE) {
			Log.getInstance().error(JDOSolicitudesDAO.class,daoE);
			Log.getInstance().error(JDOSolicitudesDAO.class,daoE.getMessage(), daoE);
			throw new DAOException(daoE.getMessage(), daoE);
		}
		 catch (JDOObjectNotFoundException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e);
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e);
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}

		return rta;
	}

	/**
	* Agrega una <code>SolicitudFolio</code> a una <code>Solicitud</code> en el sistema.
	* @param  s <code>SolicitudConsulta</code> a la que se agregará la <code>SolicitudFolio</code>
	* @param  sf <code>SolicitudFolio</code> que se va a adicionar.
	* @return una SolicitudConsulta persistente con su <code>SolicitudFolio</code> persistente.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SolicitudConsulta
	* @see gov.sir.core.negocio.modelo.SolicitudFolio
	*
	*/
	public SolicitudConsulta agregarSolicitudFolio(SolicitudConsulta s, SolicitudFolio sf)
		throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		SolicitudConsultaEnhanced sc = null;
		SolicitudFolioEnhanced sfe = null;

		try {
			pm.currentTransaction().begin();

			//Se obtiene la solicitud consulta persistente.
			SolicitudEnhancedPk scId = new SolicitudEnhancedPk();
			scId.idSolicitud = s.getIdSolicitud();
			SolicitudConsultaEnhanced solPers = (SolicitudConsultaEnhanced) this.getSolicitudByID(scId,
					pm);

			if (solPers == null) {
				throw new DAOException("No se encontro la solicitud consulta");
			}

			//Se obtiene la última búsqueda
			String idBusqCons = null;
			List busquedas = solPers.getBusquedas();
			if(!busquedas.isEmpty()){
				BusquedaEnhanced bus = (BusquedaEnhanced)busquedas.get(busquedas.size()-1);
				idBusqCons = bus.getIdBusqueda();
			}

			/*
			Ciudadano ciud = s.getCiudadano();
			if (ciud != null) {
				CiudadanoEnhanced ciudE = CiudadanoEnhanced.enhance(ciud);
				CiudadanoEnhanced ciudPers = this.getCiudadanoByDocumento(ciudE.getTipoDoc(), ciudE.getDocumento(), pm);
				if(ciudPers!=null) {
					solPers.setCiudadano(ciudPers);
				}
				else{
					CiudadanoEnhanced ciur = this.crearCiudadano(ciudE, pm);
					solPers.setCiudadano(ciur);
				}
			}*/

			sfe = SolicitudFolioEnhanced.enhance(sf);
			sfe.setIdSolicitud(solPers.getIdSolicitud());


            //Se obtiene el folio persistente.
            FolioEnhancedPk folioId = new FolioEnhancedPk();
            folioId.idMatricula = sfe.getIdMatricula();

            FolioEnhanced folioPers = getFolioByID(folioId,pm);
            if(folioPers == null){
            	throw new DAOException("El folio no existe");
            }

			sfe.setIdBusquedaConsulta(idBusqCons);
            sfe.setFolio(folioPers);
            sfe.setSolicitud(solPers);
            sfe.setCirculo(folioPers.getCirculo());
			pm.makePersistent(sfe);

			SolicitudEnhancedPk sid = new SolicitudEnhancedPk();
			sid.idSolicitud = solPers.getIdSolicitud();

			pm.currentTransaction().commit();
			sc = (SolicitudConsultaEnhanced)this.getSolicitudByID(sid, pm);
			this.makeTransientSolicitud(sc, pm);

		}
		 catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw (e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
		} finally {
			pm.close();
		}

		return (SolicitudConsulta) sc.toTransferObject();
	}




	/**
	* Agrega una <code>Busqueda</code> en el sistema y la asocia a una
	* <code>SolicitudConsulta</code>.
	* <p>
	* Método utilizado para transacciones
	* @param  b <code>Busqueda</code> con sus atributos, exceptuando el identificador.
	* @param  s <code>SolicitudConsulta</code> a la que se debe asociar la
	* <code>Busqueda</code>
	* @return  <code>SolicitudConsulta</code> con la <code>Busqueda</code> agregada.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SolicitudConsulta
	* @see gov.sir.core.negocio.modelo.Busqueda
	*/
	public SolicitudConsulta agregarBusqueda(SolicitudConsulta s, Busqueda b)
		throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		SolicitudConsultaEnhanced sc = null;
		BusquedaEnhanced busc = null;
		SolicitudConsulta rta = null;
		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//Se obtiene la solicitud consulta persistente.
			SolicitudEnhancedPk scId = new SolicitudEnhancedPk();
			scId.idSolicitud = s.getIdSolicitud();

			SolicitudConsultaEnhanced solPers = (SolicitudConsultaEnhanced) this.getSolicitudByID(scId,
					pm);
			if (solPers == null) {
				throw new DAOException("No se encontro la solicitud consulta");
			}

			/*
			Ciudadano ciud = s.getCiudadano();
			if (ciud != null){
				CiudadanoEnhanced ciudE = CiudadanoEnhanced.enhance(ciud);
				CiudadanoEnhanced ciudPers = this.getCiudadanoByDocumento(ciudE.getTipoDoc(), ciudE.getDocumento(), pm);
				if(ciudPers!=null) {
					solPers.setCiudadano(ciudPers);
				}
			    else{
					solPers.setCiudadano(ciudE);
				}
			}*/

			busc = BusquedaEnhanced.enhance(b);
			busc.setIdBusqueda(String.valueOf((solPers.getLastIdBusqueda()) + 1));
			busc.setIdSolicitud(solPers.getIdSolicitud());
			solPers.setLastIdBusqueda(solPers.getLastIdBusqueda() + 1);
			busc.setSolicitud(solPers);
			busc.setFechaCreacion(new Date());
			pm.makePersistent(busc);

			SolicitudEnhancedPk sid = new SolicitudEnhancedPk();
			sid.idSolicitud = solPers.getIdSolicitud();

			pm.currentTransaction().commit();
			sc = (SolicitudConsultaEnhanced)this.getSolicitudByID(sid, pm);
			this.makeTransientSolicitud(sc, pm);
		}
		 catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw (e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
		} finally {
			pm.close();
		}

		return (SolicitudConsulta)sc.toTransferObject();
	}




	/**
	* Modifica una <code>Busqueda</code>  asociada a una
	* <code>SolicitudConsulta</code>.
	* @param  busc <code>Busqueda</code> con sus atributos.
	* @param  solConsulta <code>SolicitudConsulta</code> que tiene asociada la
	* <code>Busqueda</code>
	* @return  <code>SolicitudConsulta</code> con su <code>Busqueda</code> modificada.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SolicitudConsulta
	* @see gov.sir.core.negocio.modelo.Busqueda
	*/
	public SolicitudConsulta modificarBusquedaConsulta(
		SolicitudConsulta solConsulta, Busqueda busc) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();

		BusquedaEnhancedPk busqId = null;
		BusquedaEnhanced busqPers = null;
		SolicitudEnhancedPk sID;
		SolicitudConsultaEnhanced solPers;

		sID = new SolicitudEnhancedPk();
		sID.idSolicitud = solConsulta.getIdSolicitud();

		busqId = new BusquedaEnhancedPk();
		busqId.idBusqueda = busc.getIdBusqueda();

		try {

			    pm.currentTransaction().setOptimistic(false);
				pm.currentTransaction().begin();
				//Se obtiene la solicitud consulta persistente.
				solPers = (SolicitudConsultaEnhanced) this.getSolicitudByID(sID, pm);
				if (solPers == null) {
					throw new DAOException(
						"No se encontro la solicitud consulta");
				}

				Ciudadano ciud = solConsulta.getCiudadano();
				if (ciud != null){
					CiudadanoEnhanced ciudE = CiudadanoEnhanced.enhance(ciud);
					CiudadanoEnhanced ciudPers = this.getCiudadanoByDocumento(ciudE.getTipoDoc(), ciudE.getDocumento(), pm, ciudE.getIdCirculo());

					if(ciudPers!=null) {
						solPers.setCiudadano(ciudPers);
					}
				  	else{
						solPers.setCiudadano(ciudE);
					}
				}

				//Se obtiene la búsqueda persistente.
				List list = solPers.getBusquedas();
				//Se obtiene la última búsqueda asociada a la solicitud.
				busqPers = (BusquedaEnhanced) list.get(list.size() - 1);
				if (busqPers == null) {
					throw new DAOException("No se encontro la busqueda");
				}
				BusquedaEnhanced nuevaBusqueda = BusquedaEnhanced.enhance(busc);
				//Se cambian los atributos de la búsqueda.
				busqPers.setApellido1Ciudadano(nuevaBusqueda.getApellido1Ciudadano());
				busqPers.setApellido2Ciudadano(nuevaBusqueda.getApellido2Ciudadano());
				busqPers.setDireccion(nuevaBusqueda.getDireccion());
				busqPers.setIdEje(nuevaBusqueda.getIdEje());
				busqPers.setIdTipoPredio(nuevaBusqueda.getIdTipoPredio());
				busqPers.setMatricula(nuevaBusqueda.getMatricula());
				busqPers.setNombreCiudadano(nuevaBusqueda.getNombreCiudadano());
				busqPers.setNombreNaturalezaJuridica(nuevaBusqueda.getNombreNaturalezaJuridica());
				busqPers.setNumeroCatastral(nuevaBusqueda.getNumeroCatastral());
				busqPers.setNumeroDocCiudadano(nuevaBusqueda.getNumeroDocCiudadano());
				busqPers.setNumeroIntento(busqPers.getNumeroIntento() + 1);
				busqPers.setNumeroResultados(0);
				busqPers.setTipoDocCiudadano(nuevaBusqueda.getTipoDocCiudadano());
				if(nuevaBusqueda.getIdCirculoBusqueda()!=null){
					busqPers.setIdCirculoBusqueda(nuevaBusqueda.getIdCirculoBusqueda());
				}
				
				pm.currentTransaction().commit();

				solPers = (SolicitudConsultaEnhanced) SolicitudConsultaEnhanced.enhance(this.getSolicitudByID(
							sID.getSolicitudID()));

                                List liq = solPers.getLiquidaciones();
                                Iterator itl = liq.iterator();

                                //Hacer transiente las liquidaciones, los pagos y los documentos de pago.
                                while (itl.hasNext()) {
                                    LiquidacionTurnoConsultaEnhanced l = (LiquidacionTurnoConsultaEnhanced) itl.next();
                                    pm.makeTransient(l.getAlcanceGeografico());
                                    pm.makeTransient(l.getUsuario());
                                    pm.makeTransient(l);
                                }


			    //pm.makeTransient(solPers.getCiudadano());

		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw e;
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return (SolicitudConsulta) solPers.toTransferObject();
	}




	/*******************************************************************************/
	/*                                                                             */
	/*              METODOS UTILIZADOS EN EL PROCESO DE CORRECCIONES               */
	/*                                                                             */
	/*******************************************************************************/




	/**
	* Crea una solicitud de corrección en el sistema
	* @param  s <code>Solicitud</code> con sus atributos, exceptuando el identificador
	* @return una <code>SolicitudCorreccion</code>persistente con su identificador.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SolicitudCorreccion
	*/
	public SolicitudCorreccion crearSolicitudCorreccion(Solicitud s)
		throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		SolicitudCorreccionEnhanced sc = null;
		SolicitudCorreccion solCorreccion = (SolicitudCorreccion) s;

		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			sc = this.crearSolicitudCorreccion(SolicitudCorreccionEnhanced.enhance(
						solCorreccion), pm);
			pm.currentTransaction().commit();

			SolicitudEnhancedPk sid = new SolicitudEnhancedPk();
			sid.idSolicitud = sc.getIdSolicitud();

			sc = (SolicitudCorreccionEnhanced) SolicitudCorreccionEnhanced.enhance(this.getSolicitudByID(
						sid.getSolicitudID()));
		}
		 catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw (e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
		} finally {
			pm.close();
		}

		return (SolicitudCorreccion) sc.toTransferObject();
	}

	/**
	* Crea una <code>Solicitud</code> de correcciones en el sistema.
	* <p> Método utilizado para transacciones
	* @param  s <code>SolicitudCorreccion</code> con sus atributos, exceptuando el identificador
	* @param pm PersistenceManager de la transaccion
	* @return una SolicitudCorreción persistente con su identificador.
	* @throws DAOException
	*/
	protected SolicitudCorreccionEnhanced crearSolicitudCorreccion(
		SolicitudCorreccionEnhanced s, PersistenceManager pm)
		throws DAOException {
		String table = "SIR_OP_SOLICITUD";
		JDOTurnosDAO TurnosDAO = new JDOTurnosDAO();
		JDOProcesosDAO ProcesosDAO = new JDOProcesosDAO();
		JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
		ProcesoEnhanced pr = new ProcesoEnhanced();

		try {

			// Se valida que la solicitud corrección recibida no sea nula.
		    if (s == null){
				throw new DAOException("La solicitud recibida es null");
			}
			s.setIdSolicitud(this.getLlavePrimariaSolicitud(s, pm));

			//Se valida que exista un Círculo con el identificador recibido
			CirculoEnhanced c = s.getCirculo();
			if (c == null) {
				throw new DAOException(
					"El Circulo asociado a la solicitud es null");
			}
            CirculoEnhancedPk cId = new CirculoEnhancedPk();
            cId.idCirculo = c.getIdCirculo();
            CirculoEnhanced cr = this.getCirculoByID(cId, pm);
            if (cr == null) {
            	throw new DAOException(
            		"No encontró el circulo con el ID: " +
            		cId.idCirculo);
            }
            s.setCirculo(cr);

			//Se valida que exista un Proceso con el identificador recibido
			pr = s.getProceso();
			if (pr == null) {
				throw new DAOException(
					"El Proceso asociado a la solicitud es null");
			}
            ProcesoEnhancedPk prId = new ProcesoEnhancedPk();
            prId.idProceso = pr.getIdProceso();
            ProcesoEnhanced prr = ProcesosDAO.getProcesoByID(prId, pm);
            if (prr == null) {
            	throw new DAOException(
            		"No encontró el proceso con el ID: " +
            		prId.idProceso);
            }
            s.setProceso(prr);

			//VALIDAR CIUDADANO
			CiudadanoEnhanced ciu = s.getCiudadano();
			String docCiudadano = ciu.getDocumento();
			String tipoDocCiudadano = ciu.getTipoDoc();
			CiudadanoEnhanced ciur = this.getCiudadanoByDocumento(tipoDocCiudadano,
				 docCiudadano, pm, ciu.getIdCirculo());
			if (ciur == null) {
			ciur = this.crearCiudadano(ciu, pm);
			ciur = s.getCiudadano();
			if (ciur == null) {
				throw new DAOException(
					 "No encontró el ciudadano con el documento: " +
					 docCiudadano);
				 }
			 }
			 s.setCiudadano(ciur);

			//Se valida (Si aplica) que exista un turno anterior con el id recibido.
			if (s.getTurnoAnterior() != null) {
				TurnoEnhanced tant = s.getTurnoAnterior();
				TurnoEnhanced tantr = TurnosDAO.getTurnoByWFId(tant.getIdWorkflow(),
						pm);
				if (tantr == null) {
					throw new DAOException(
						"No encontró el turno anterior con el ID: " +
						tant.getIdWorkflow());
				}
				s.setTurnoAnterior(tantr);

			}

			//Se valida que exista un usuario con  el id recibido.
			UsuarioEnhanced u = s.getUsuario();
			if (u == null) {
				throw new DAOException(
					"El Usuario asociado a la solicitud es null");
			}
            UsuarioEnhancedPk uId = new UsuarioEnhancedPk();
            uId.idUsuario = u.getIdUsuario();
            UsuarioEnhanced ur = this.getUsuarioByID(uId,pm);
            if (ur == null) {
            	throw new DAOException(
            		"No encontró el usuario con el ID: " +
            		uId.idUsuario);
            }
            s.setUsuario(ur);

			//Se valida que exista un tipoRecepcionPeticion con el id recibido.
			TipoRecepcionPeticionEnhanced trp = s.getTipoRecepcionPeticion();

			if (trp == null) {
				throw new DAOException(
					"El tipo de recepción petición asociado a la solicitud es null");
			}
            TipoRecepcionPeticionEnhancedPk tcId = new TipoRecepcionPeticionEnhancedPk();
            tcId.idTipoRecepcionPeticion = trp.getIdTipoRecepcion();
            TipoRecepcionPeticionEnhanced trpPers = variablesDAO.getTipoRecepcionPeticionByID(tcId,
            		pm);
            if (trpPers == null) {
            	throw new DAOException(
            		"No encontró el tipoRecepcionPeticion con id:  " +
            		trp.getIdTipoRecepcion());
            }
            s.setTipoRecepcionPeticion(trpPers);



            // TODO: Colocar datos de antiguo sistema
            DatosAntiguoSistemaEnhanced datosAntiguoSistemaEnhanced;
            datosAntiguoSistemaEnhanced = s.getDatosAntiguoSistema();
            if( datosAntiguoSistemaEnhanced != null ){

               // asociar id
               datosAntiguoSistemaEnhanced.setIdDatosAntiguoSistema( s.getIdSolicitud() );

               // documento
               if(datosAntiguoSistemaEnhanced.getDocumento()!=null){
            	   if(s!=null&&s.getCirculo()!=null){
            		   datosAntiguoSistemaEnhanced.getDocumento().setCirculo(s.getCirculo().getIdCirculo());
            	   }
                 this.setDocumentoToDatosAntiguoSistema(datosAntiguoSistemaEnhanced, datosAntiguoSistemaEnhanced.getDocumento(), pm);
               }



            }







			//Se valida que exista una lista con por lo menos una matrícula asociada.
			List matriculas = s.getSolicitudFolios();
			if (matriculas == null) {
				throw new DAOException(
					"No hay matrículas asociadas con la solicitud de correccion:");
			}

			List folios = s.getSolicitudFolios();
			Iterator it = folios.iterator();
			while (it.hasNext()) {
				SolicitudFolioEnhanced sf = (SolicitudFolioEnhanced) it.next();
				FolioEnhanced fr = this.getFolioByMatricula(sf.getIdMatricula(),
						pm);
				if (fr == null) {
					throw new DAOException(
						"No encontró el folio con el ID: " +
						sf.getIdMatricula());
				}

				sf.setFolio(fr);
				sf.setIdMatricula(fr.getIdMatricula());
				sf.setSolicitud(s);
				pm.makeTransient(sf);
				sf.setIdSolicitud(s.getIdSolicitud());
				sf.setCirculo(s.getCirculo()!=null?s.getCirculo().getIdCirculo():null);
			}

			//Como el proceso es de creación, se asigna al atributo lastIdLiquidación
			//el valor 0, pues aún la solicitud no tiene asociadas liquidaciones.
			s.setLastIdLiquidacion(0);

			//Se hace persistencia sobre la solicitud.
			pm.makePersistent(s);

		} catch (DAOException daoE) {
			Log.getInstance().error(JDOSolicitudesDAO.class,daoE.getMessage());
			Log.getInstance().error(JDOSolicitudesDAO.class,daoE);
			throw new DAOException(daoE.getMessage(), daoE);
		}
		 catch (JDOObjectNotFoundException ex) {
			Log.getInstance().error(JDOSolicitudesDAO.class,ex.getMessage());
			ex.printStackTrace();
			throw new DAOException(ex.getMessage(), ex);
		}
		 catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
		}
		return s;
	}

	/**
	 * Crea una solicitud de fotocopias en el sistema
	 * @param  sol <code>SolicitudCertificado</code> con sus atributos, exceptuando el
	 * identificador
	 * @return el identificador de la <code>SolicitudCertificado</code> persistente
	 * con su identificador.
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Solicitud
	 * @see gov.sir.core.negocio.modelo.SolicitudCertificado
	 */
	public SolicitudFotocopia crearSolicitudFotocopia(Solicitud sol)
		throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();

		SolicitudFotocopia s = (SolicitudFotocopia) sol;
		SolicitudFotocopiaEnhanced sf = null;
		SolicitudEnhancedPk scId = new SolicitudEnhancedPk();
		SolicitudEnhancedPk sid;

		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			sf = this.crearSolicitudFotocopia(SolicitudFotocopiaEnhanced.enhance(
						s), pm);
			pm.currentTransaction().commit();

			this.makeTransientSolicitud(sf, pm);

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw (e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return (SolicitudFotocopia) sf.toTransferObject();
	}



	/**
	 * Actualiza los documentos fotocopia asociados a la solicitud de fotocopia
	 * @param  sol <code>SolicitudFotocopia</code> con identificador y sus documentos fotocopia,
	 * cada uno con su nuevo tipo de fotocopia y su nuevo número de hojas
	 * @return La solicitud completa de fotocopia con sus documentos actualizados
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Solicitud
	 * @see gov.sir.core.negocio.modelo.SolicitudCertificado
	 */
	public SolicitudFotocopia updateDocumentosFotocopia(SolicitudFotocopia sol)
		throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();

		SolicitudFotocopiaEnhanced sf = null;
		SolicitudEnhancedPk scId = new SolicitudEnhancedPk();
		scId.idSolicitud = sol.getIdSolicitud();
		SolicitudEnhancedPk sid;


		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			sf = (SolicitudFotocopiaEnhanced)this.getSolicitudByID(scId, pm);
			if(sf==null){
				throw new DAOException("No encontró una solicitud de fotocopia con el id dado: "+sol.getIdSolicitud());
			}

			//Recorremos la lista de objetos que actualizan los documentos de fotocopia:
			DocumentoFotocopia docFot;
			DocumentoFotocopiaEnhancedPk docFotID;
			DocumentoFotocopiaEnhanced docFotPers;
			for(Iterator it = sol.getDocumentoFotocopia().iterator(); it.hasNext();){
				docFot = (DocumentoFotocopia)it.next();
				docFotID = new DocumentoFotocopiaEnhancedPk();
				docFotID.idSolicitud = sf.getIdSolicitud();
				docFotID.idDocFotocopia = docFot.getIdDocFotocopia();

				docFotPers = this.getDocumentoFotocopia(docFotID, pm);

				if(docFotPers == null){
					throw new DAOException("No se encontró el documento fotocopia a actualizar");
				}

				//Se actualizan: Tipo de fotocopia y número de hojas
				//Se valida la existencia del tipo de fotocopia
				if(docFot.getTipoFotocopia()==null){
					throw new DAOException("El documento no tiene tipo de fotocopia para actualizar: "+docFotPers.getIdDocFotocopia());
				}

				TipoFotocopiaEnhancedPk  tfID = new TipoFotocopiaEnhancedPk();
				tfID.idTipoFotocopia = docFot.getTipoFotocopia().getIdTipoFotocopia();
				TipoFotocopiaEnhanced tipo = this.getTipoFotocopia(tfID, pm);

				if(tipo==null){
					throw new DAOException("El tipo de fotocopia no existe: "+tfID.idTipoFotocopia);
				}

				docFotPers.setTipoFotocopia(tipo);
				docFotPers.setNumHojas(docFot.getNumHojas());
			}
			pm.currentTransaction().commit();

			sid = new SolicitudEnhancedPk();
			sid.idSolicitud = sf.getIdSolicitud();

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw (e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return (SolicitudFotocopia) this.getSolicitudByID(sid.getSolicitudID());
	}

	/**
	 * Obtiene un objeto <code>OficinaOrigen</code> dado su identificador
	 * en una transacción
	 * @param oid identificador de la <code>OficinaOrigen</code>
	 * @param pm PersistenceManager de la transacción
	 * @return OficinaOrigen persistente, null si no la encuentra
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.OficinaOrigen
	 */
	protected OficinaOrigenEnhanced getOficinaOrigen(
		OficinaOrigenEnhancedPk oid, PersistenceManager pm)
		throws DAOException {
		OficinaOrigenEnhanced rta = null;

		if (oid.idOficinaOrigen != null) {
			try {
				rta = (OficinaOrigenEnhanced) pm.getObjectById(oid, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	 * Obtiene un objeto <code>TipoDocumento</code> dado su identificador
	 * en una transacción.
	 * @param oid identificador del Tipo de Documento
	 * @param pm PersistenceManager con la transacción
	 * @return objeto <code>TipoDocumento</code> persistente
	 * @throws <code>DAOException</code>
	 */
	protected TipoDocumentoEnhanced getTipoDocumento(
		TipoDocumentoEnhancedPk oid, PersistenceManager pm)
		throws DAOException {
		TipoDocumentoEnhanced rta = null;

		if (oid.idTipoDocumento != null) {
			try {
				rta = (TipoDocumentoEnhanced) pm.getObjectById(oid, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}


	/**
	 * Obtiene un objeto <code>TipoFotocopia</code> dado su identificador
	 * en una transacción.
	 * @param oid identificador del Tipo de Documento
	 * @param pm PersistenceManager con la transacción
	 * @return objeto <code>TipoDocumento</code> persistente
	 * @throws <code>DAOException</code>
	 */
	protected TipoFotocopiaEnhanced getTipoFotocopia(
		TipoFotocopiaEnhancedPk oid, PersistenceManager pm)
		throws DAOException {
		TipoFotocopiaEnhanced rta = null;

		if (oid.idTipoFotocopia != null) {
			try {
				rta = (TipoFotocopiaEnhanced) pm.getObjectById(oid, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}






	protected DocumentoEnhanced crearDocumento(
		DocumentoEnhanced documentoEnhanced, PersistenceManager pm)
		throws DAOException {
		//VALIDAR TIPO DOCUMENTO
		TipoDocumentoEnhanced tde = documentoEnhanced.getTipoDocumento();

		if (tde == null) {
			throw new DAOException("No se tiene tipo de documento");
		}

		TipoDocumentoEnhancedPk tdeid = new TipoDocumentoEnhancedPk();
		tdeid.idTipoDocumento = tde.getIdTipoDocumento();

		TipoDocumentoEnhanced auxtde = this.getTipoDocumento(tdeid, pm);

		if (auxtde == null) {
			throw new DAOException("Tipo de documento no válido");
		}

		//ASOCIAR TIPO DOCUMENTO
		documentoEnhanced.setTipoDocumento(auxtde);

		//VALIDAR OFICINA DE ORIGEN
		OficinaOrigenEnhanced ooe = documentoEnhanced.getOficinaOrigen();

		if (ooe != null) {
			OficinaOrigenEnhancedPk ooeid = new OficinaOrigenEnhancedPk();
			ooeid.idOficinaOrigen = ooe.getIdOficinaOrigen();
                         /*
                          *  @author Carlos Torres
                          *  @chage   se agrega validacion de version diferente
                          *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                          */
                        ooeid.version = ooe.getVersion();
			OficinaOrigenEnhanced auxooe = this.getOficinaOrigen(ooeid, pm);

			if (auxooe == null) {
				throw new DAOException("Oficina de origen no válida");
			}

			//ASOCIAR OFICINA DE ORIGEN
			documentoEnhanced.setOficinaOrigen(auxooe);
		}

		return documentoEnhanced;
	}






	/**
	* Crea una solicitud de fotocopias en el sistema.
	* <p>Método utilizado por transacciones.
	* @param  s <code>SolicitudFotocopiaEnhanced</code> con sus atributos, exceptuando el
	* identificador
	* @param Persistence Manager de la transacción.
	* @return <code>SolicitudFotocopiaEnhanced</code> persistente
	* con su identificador.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced
	* @see gov.sir.core.negocio.modelo.SolicitudFotocopia
	* @see gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced
	*/
	protected SolicitudFotocopiaEnhanced crearSolicitudFotocopia(
		SolicitudFotocopiaEnhanced s, PersistenceManager pm)
		throws DAOException {
		String table = "SIR_OP_SOLICITUD";
		JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
		JDOProcesosDAO procesosDAO = new JDOProcesosDAO();

		try {

		    if (s == null){
				throw new DAOException("La solicitud recibida es null");
			}
			s.setIdSolicitud(this.getLlavePrimariaSolicitud(s, pm));

			//VALIDAR CIRCULO
			CirculoEnhanced c = s.getCirculo();
			CirculoEnhancedPk cId = new CirculoEnhancedPk();
			cId.idCirculo = c.getIdCirculo();
			CirculoEnhanced cr = this.getCirculoByID(cId, pm);
			if (cr == null) {
				throw new DAOException("No encontró el circulo con el ID: " +
					cId.idCirculo);
			}

			//VALIDAR PROCESO
			ProcesoEnhanced pr = s.getProceso();
			ProcesoEnhancedPk prId = new ProcesoEnhancedPk();
			prId.idProceso = pr.getIdProceso();
			ProcesoEnhanced prr = procesosDAO.getProcesoByID(prId, pm);
			if (prr == null) {
				throw new DAOException("No encontró el proceso con el ID: " +
					prId.idProceso);
			}

			//VALIDAR USUARIO
			UsuarioEnhanced u = s.getUsuario();
			UsuarioEnhancedPk uId = new UsuarioEnhancedPk();
			uId.idUsuario = u.getIdUsuario();
			UsuarioEnhanced ur = this.getUsuarioByID(uId,pm);
			if (ur == null) {
				throw new DAOException("No encontró el usuario con el ID: " +
					uId.idUsuario);
			}

			//VALIDAR CIUDADANO
			CiudadanoEnhanced ciu = s.getCiudadano();
			String docCiudadano = ciu.getDocumento();
			String tipoDocCiudadano = ciu.getTipoDoc();
			CiudadanoEnhanced ciur = this.getCiudadanoByDocumento(tipoDocCiudadano,
					docCiudadano, pm, ciu.getIdCirculo());
			if (ciur == null) {
				ciur = this.crearCiudadano(ciu, pm);
				ciur = s.getCiudadano();
				if (ciur == null) {
					throw new DAOException(
						"No encontró el ciudadano con el documento: " +
						docCiudadano);
				}
			}

			//DOCUMENTOS FOTOCOPIA:
			List documentosFotocopia = s.getDocumentoFotocopia();
			DocumentoFotocopiaEnhanced docFot;
			for(Iterator it =documentosFotocopia.iterator(); it.hasNext();){
				docFot = (DocumentoFotocopiaEnhanced)it.next();
				this.addDocumentoFotocopiaToSolicitud(docFot, s, pm);
			}

			//Como el proceso es de creación, se asigna al atributo lastIdLiquidación
			//el valor 0, pues aún la solicitud no tiene asociadas liquidaciones.
			//s.setLastIdLiquidacion(0);

			//ASOCIAR INFORMACION PERSISTENTE
			s.setCirculo(cr);
			s.setProceso(prr);
			s.setUsuario(ur);
			s.setCiudadano(ciur);
			//s.setDocumento(de);
			pm.makePersistent(s);

		} catch (JDOObjectNotFoundException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			Log.getInstance().error(JDOSolicitudesDAO.class,e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

		return s;
	}




	/**
	* Retorna la llave primaria de una <code>Solicitud</code>
	* <p>Método utilizado por transacciones.
	* @param  solicitud <code>SolicitudEnhanced</code> de la cual se
	* desea obtener la llave primaria.
	* @param Persistence Manager de la transacción.
	* @return <code>SolicitudCertificadoEnhanced</code> persistente
	* con su identificador.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced
	*/
	protected String getLlavePrimariaSolicitud(SolicitudEnhanced solicitud,
		PersistenceManager pm) throws DAOException {
		JDOProcesosDAO procesoDAO = new JDOProcesosDAO();
		String rta;
		CirculoEnhanced circulo = solicitud.getCirculo();
		ProcesoEnhanced proceso = solicitud.getProceso();

		CirculoProcesoEnhanced cp = null;

		if ((circulo == null) || (proceso == null)) {
			throw new DAOException("La solicitud no tiene circulo o proceso");
		}
		
		//Incidencia 10512.
		this.validarTipoTurno(solicitud,proceso);

		Calendar calendario = Calendar.getInstance();
		String anio = String.valueOf(calendario.get(Calendar.YEAR));

		try {
			CirculoProcesoEnhancedPk cpId = new CirculoProcesoEnhancedPk();
			cpId.idProceso = proceso.getIdProceso();
			cpId.idCirculo = circulo.getIdCirculo();
			cpId.anio = anio;
			cp = procesoDAO.getCirculoProcesoById(cpId, pm);

			long sec = cp.getLastIdSolicitud() + 1;
			cp.setLastIdSolicitud(sec);
			rta = anio + "-" + cpId.idCirculo + "-" + cpId.idProceso + "-" +
				sec;
		} catch (JDOObjectNotFoundException e) {
			rta = null;
		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw e;
		}

		return rta;
	}

	private void validarTipoTurno(SolicitudEnhanced solicitud, ProcesoEnhanced proceso) throws DAOException {
		
		//SE VALIDA QUE EL PROCESO SEA DE REGISTRO
		if(solicitud instanceof SolicitudRegistroEnhanced ){
			long idTurnosRegistro = new Long(CProceso.PROCESO_REGISTRO).longValue();
			if(proceso.getIdProceso() != idTurnosRegistro){
				throw new DAOException("El proceso asociado no corresponde a un turno de registro de documentos.");				
			}
		}

		//SE VALIDA QUE EL PROCESO SEA DE CERTIFICADOS
		if(solicitud instanceof SolicitudCertificadoEnhanced ){
			long idTurnosCertificado = new Long(CProceso.PROCESO_CERTIFICADOS).longValue();
			if(proceso.getIdProceso() != idTurnosCertificado){
				throw new DAOException("El proceso asociado no corresponde a un turno de certificados.");				
			}
		}
	}


	protected SolicitudDevolucionEnhanced crearDevolucionFotocopia(
		SolicitudDevolucionEnhanced s, PersistenceManager pm)
		throws DAOException {
		String table = "SIR_OP_SOLICITUD";
		JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
		JDOProcesosDAO procesosDAO = new JDOProcesosDAO();
		JDOLiquidacionesDAO liquidacionesDAO = new JDOLiquidacionesDAO();

		try {

			if (s == null){
				throw new DAOException("La solicitud recibida es null");
			}
			s.setIdSolicitud(this.getLlavePrimariaSolicitud(s, pm));

			//VALIDAR CIRCULO
			CirculoEnhanced c = s.getCirculo();
			CirculoEnhancedPk cId = new CirculoEnhancedPk();
			cId.idCirculo = c.getIdCirculo();
			CirculoEnhanced cr = this.getCirculoByID(cId,
					pm);
			if (cr == null) {
				throw new DAOException("No encontró el circulo con el ID: " +
					cId.idCirculo);
			}

			//VALIDAR PROCESO
			ProcesoEnhanced pr = s.getProceso();
			ProcesoEnhancedPk prId = new ProcesoEnhancedPk();
			prId.idProceso = pr.getIdProceso();
			ProcesoEnhanced prr = procesosDAO.getProcesoByID(prId, pm);
			if (prr == null) {
				throw new DAOException("No encontró el circulo con el ID: " +
					cId.idCirculo);
			}

			//VALIDAR USUARIO
			UsuarioEnhanced u = s.getUsuario();
			UsuarioEnhancedPk uId = new UsuarioEnhancedPk();
			uId.idUsuario = u.getIdUsuario();
			UsuarioEnhanced ur = this.getUsuarioByID(uId, pm);
			if (ur == null) {
				throw new DAOException("No encontró el usuario con el ID: " +
					uId.idUsuario);
			}

			//VALIDAR CIUDADANO
			CiudadanoEnhanced ciu = s.getCiudadano();
			String docCiudadano = ciu.getDocumento();
			String tipoDocCiudadano = ciu.getTipoDoc();
			CiudadanoEnhanced ciur = this.getCiudadanoByDocumento(tipoDocCiudadano,
					docCiudadano, pm, ciu.getIdCirculo());
			if (ciur == null) {
				ciur = this.crearCiudadano(ciu, pm);
				ciur = s.getCiudadano();
				if (ciur == null) {
					throw new DAOException(
						"No encontró el ciudadano con el documento: " +
						docCiudadano);
				}
			}

			//VALIDAR LIQUIDACIONES
			s.setLastIdLiquidacion(0);
			List liq = s.getLiquidaciones();
			Iterator itl = liq.iterator();
			while (itl.hasNext()) {
				LiquidacionTurnoDevolucionEnhanced l =
				(LiquidacionTurnoDevolucionEnhanced) itl.next();
				liquidacionesDAO.addLiquidacionToSolicitudDevolucion(l, s, pm);
			}

			//ASOCIAR INFORMACION PERSISTENTE
			s.setCirculo(cr);
			s.setProceso(prr);
			s.setUsuario(ur);
			s.setCiudadano(ciur);
			pm.makePersistent(s);

		} catch (JDOObjectNotFoundException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			Log.getInstance().error(JDOSolicitudesDAO.class,e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

		return s;
	}






	/**
	* Crea una solicitud de devoluciones en el sistema.
	* <p>Método utilizado por transacciones.
	* @param  s <code>SolicitudDevolucionEnhanced</code> con sus atributos, exceptuando el
	* identificador
	* @param pm Persistence Manager de la transacción.
	* @return <code>SolicitudCertificadoEnhanced</code> persistente
	* con su identificador.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced
	* @see gov.sir.core.negocio.modelo.SolicitudCertificado
	* @see gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced
	*/
	protected SolicitudDevolucionEnhanced crearSolicitudDevolucion(
		SolicitudDevolucionEnhanced s, PersistenceManager pm)
		throws DAOException {
		String table = "SIR_OP_SOLICITUD";
		JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
		JDOPagosDAO pagosDAO = new JDOPagosDAO();
		JDOProcesosDAO procesosDAO = new JDOProcesosDAO();
		JDOLiquidacionesDAO liquidacionesDAO = new JDOLiquidacionesDAO();

		try {
			if (s == null)
				throw new DAOException("La solicitud recibida es null");

			s.setIdSolicitud(this.getLlavePrimariaSolicitud(s, pm));

			//VALIDAR CIRCULO
			CirculoEnhanced c = s.getCirculo();
			CirculoEnhancedPk cId = new CirculoEnhancedPk();
			cId.idCirculo = c.getIdCirculo();
			CirculoEnhanced cr = this.getCirculoByID(cId, pm);
			if (cr == null) {
				throw new DAOException("No encontró el circulo con el ID: " +
					cId.idCirculo);
			}

			//VALIDAR PROCESO
			ProcesoEnhanced pr = s.getProceso();
			ProcesoEnhancedPk prId = new ProcesoEnhancedPk();
			prId.idProceso = pr.getIdProceso();
			ProcesoEnhanced prr = procesosDAO.getProcesoByID(prId, pm);
			if (prr == null) {
				throw new DAOException("No encontró el circulo con el ID: " +
					cId.idCirculo);
			}

			//VALIDAR USUARIO
			UsuarioEnhanced u = s.getUsuario();
			UsuarioEnhancedPk uId = new UsuarioEnhancedPk();
			uId.idUsuario = u.getIdUsuario();
			UsuarioEnhanced ur = this.getUsuarioByID(uId, pm);
			if (ur == null) {
				throw new DAOException("No encontró el usuario con el ID: " +
				uId.idUsuario);
			}

			//VALIDAR CIUDADANOS
			List solicitantes = s.getSolicitantes();
			if (solicitantes != null){
				for (int i=0; i < solicitantes.size(); i++){
					SolicitanteEnhanced solte = (SolicitanteEnhanced)solicitantes.get(i);
					CiudadanoEnhanced ciu = solte.getCiudadano();
					String docCiudadano = ciu.getDocumento();
					String tipoDocCiudadano = ciu.getTipoDoc();
					CiudadanoEnhanced ciur = this.getCiudadanoByDocumento(tipoDocCiudadano,
							docCiudadano, pm, ciu.getIdCirculo());
					if (ciur == null) {
						ciur = this.crearCiudadano(ciu, pm);
						if (ciur == null) {
							throw new DAOException(
									"No encontró el ciudadano con el documento: " +
									docCiudadano);
						}
					}
					solte.setSolicitud(s);
					solte.setCiudadano(ciur);
					solte.setIdCiudadano(ciur.getIdCiudadano());
				}
			}

			//VALIDAR TURNO ANTERIOR
			if (s.getTurnoAnterior() != null) {
				TurnoEnhanced tant = s.getTurnoAnterior();
				TurnoEnhanced tantr = turnosDAO.getTurnoByWFId(tant.getIdWorkflow(),
						pm);
				if (tantr == null) {
					throw new DAOException(
						"No encontró el turno con el ID: " +
						tant.getIdWorkflow());
				}
				//ASOCIAR TURNO ANTERIOR
				s.setTurnoAnterior(tantr);
			}
			
			//VALIDAR CONSIGNACIONES
			double valor = 0;
			List consignaciones = s.getConsignaciones();
			if (consignaciones != null){
				for (int i=0; i < consignaciones.size(); i++){
					ConsignacionEnhanced cons = (ConsignacionEnhanced)consignaciones.get(i);
					DocumentoPagoEnhanced docPago = null;
					if (cons.getDocPago() instanceof DocPagoChequeEnhanced){
						DocPagoChequeEnhanced docPagoCheque = (DocPagoChequeEnhanced)cons.getDocPago();
						docPago = pagosDAO.crearDocumentoPago(docPagoCheque, pm);
						valor += docPagoCheque.getValorDocumento();
					} 
					if (cons.getDocPago() instanceof DocPagoConsignacionEnhanced){
						DocPagoConsignacionEnhanced docPagoCons = (DocPagoConsignacionEnhanced)cons.getDocPago();
						docPago = pagosDAO.crearDocumentoPago(docPagoCons, pm);
						valor += docPagoCons.getValorDocumento();
					} 
					
					if (docPago == null) {
						throw new DAOException(
								"No se pudo crear la consignacion o cheque: " );
					} 
					cons.setDocPago(docPago);
					cons.setSolicitud(s);
				}
			}
			
			
			//VALIDAR LIQUIDACIONES
			s.setLastIdLiquidacion(0);
			List liq = s.getLiquidaciones();
			Iterator itl = liq.iterator();

			while (itl.hasNext()) {
				LiquidacionTurnoDevolucionEnhanced l = (LiquidacionTurnoDevolucionEnhanced) itl.next();
				l.setValor(valor);
				liquidacionesDAO.addLiquidacionToSolicitudDevolucion(l, s, pm);
			}
			
			//VALIDAR ITEMS DE CHEQUEO PARA DEVOLUCIONES.
			List listaItems = new ArrayList();
			listaItems = s.getCheckedItems();
			if (listaItems != null)
			{
				for (int it=0; it<listaItems.size(); it++)
				{
					SolCheckedItemDevEnhanced itemDev = (SolCheckedItemDevEnhanced)listaItems.get(it);
					CheckItemDevEnhanced itemAux = itemDev.getCheckItem();
					CheckItemDevEnhancedPk idItem = new CheckItemDevEnhancedPk();
					idItem.idCheckItemDev = itemAux.getIdCheckItemDev();
					CheckItemDevEnhanced itemAuxEnh = (CheckItemDevEnhanced) pm.getObjectById(idItem,true);
					if (itemAuxEnh == null)
					{
						throw new DAOException("No se encontró un ítem de Chequeo con el id: " +idItem.idCheckItemDev);
					}
					itemDev.setCheckItem(itemAuxEnh);
					itemDev.setSolicitud(s);
				}
				
			}
			

			//ASOCIAR INFORMACION PERSISTENTE
			s.setCirculo(cr);
			s.setProceso(prr);
			s.setUsuario(ur);
			//s.setCiudadano(ciur);
			pm.makePersistent(s);

		} catch (JDOObjectNotFoundException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			Log.getInstance().error(JDOSolicitudesDAO.class,e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

		return s;
	}

	/**
	 * @see gov.sir.hermod.dao.SolicitudesDAO#crearSolicitudDevolucion(gov.sir.core.negocio.modelo.SolicitudDevolucion)
	 */
	public SolicitudPk crearSolicitudDevolucion(SolicitudDevolucion s) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		SolicitudDevolucionEnhanced sd = null;
		SolicitudEnhancedPk sdId = new SolicitudEnhancedPk();

		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			sd = this.crearSolicitudDevolucion(SolicitudDevolucionEnhanced.enhance(s), pm);
			pm.currentTransaction().commit();
			sdId = (SolicitudEnhancedPk) pm.getObjectId(sd);
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw (e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return sdId.getSolicitudID();
	}

	/******************************************************************************/
	/*                                                                            */
	/*                  PROCESO DE REPARTO NOTARIAL (MINUTAS)                     */
	/*                                                                            */
	/******************************************************************************/

	/**
	* Crea una solicitud de reparto notarial en el sistema
	* @param  s SolicitudRepartoNotarial con sus atributos, exceptuando el identificador
	* @return una SolicitudRepartoNotarial persistente con su identificador.
	* @throws DAOException
	*/
	public SolicitudRepartoNotarial crearSolicitudRepartoNotarial(Solicitud s)
		throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		SolicitudRepartoNotarialEnhanced sc = null;
		SolicitudRepartoNotarial solReparto = (SolicitudRepartoNotarial) s;


		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			sc = this.crearSolicitudRepartoNotarial(SolicitudRepartoNotarialEnhanced.enhance(
						solReparto), pm);
			pm.currentTransaction().commit();
			this.makeTransientSolicitud(sc, pm);

		}
		 catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw (e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
		} finally {
			pm.close();
		}

		return (SolicitudRepartoNotarial) sc.toTransferObject();
	}

	protected SolicitudRepartoNotarialEnhanced crearSolicitudRepartoNotarial(
		SolicitudRepartoNotarialEnhanced s, PersistenceManager pm)
		throws DAOException {
		String table = "SIR_OP_SOLICITUD";
		JDOTurnosDAO TurnosDAO = new JDOTurnosDAO();
		JDOProcesosDAO ProcesosDAO = new JDOProcesosDAO();
		ProcesoEnhanced pr = new ProcesoEnhanced();

		try {
			if (s == null){
				throw new DAOException("La solicitud recibida es null");
			}

			s.setIdSolicitud(this.getLlavePrimariaSolicitud(s, pm));

			//Se valida que exista un Círculo con el identificador recibido
			CirculoEnhanced c = s.getCirculo();
			if (c == null) {
				throw new DAOException(
					"El Circulo asociado a la solicitud es null");
			}
            CirculoEnhancedPk cId = new CirculoEnhancedPk();
            cId.idCirculo = c.getIdCirculo();
            CirculoEnhanced cr = this.getCirculoByID(cId, pm);
            if (cr == null) {
            	throw new DAOException(
            		"No encontró el circulo con el ID: " +
            		cId.idCirculo);
            }
            s.setCirculo(cr);

			//Se valida que exista un Proceso con el identificador recibido
			pr = s.getProceso();
			if (pr == null) {
				throw new DAOException(
					"El Proceso asociado a la solicitud es null");
			}
            ProcesoEnhancedPk prId = new ProcesoEnhancedPk();
            prId.idProceso = pr.getIdProceso();
            ProcesoEnhanced prr = ProcesosDAO.getProcesoByID(prId, pm);
            if (prr == null) {
            	throw new DAOException(
            		"No encontró el proceso con el ID: " +
            		prId.idProceso);
            }
            s.setProceso(prr);

			//Se valida (Si aplica) que exista un turno anterior con el id recibido.
			if (s.getTurnoAnterior() != null) {
				TurnoEnhanced tant = s.getTurnoAnterior();
				TurnoEnhanced tantr = TurnosDAO.getTurnoByWFId(tant.getIdWorkflow(),
						pm);
				if (tantr == null) {
					throw new DAOException(
						"No encontró el turno anterior con el ID: " +
						tant.getIdTurno());
				}
				s.setTurnoAnterior(tantr);
			}

			//Se valida que exista un usuario con  el id recibido.
			UsuarioEnhanced u = s.getUsuario();
			if (u == null) {
				throw new DAOException(
					"El Usuario asociado a la solicitud es null");
			}
            UsuarioEnhancedPk uId = new UsuarioEnhancedPk();
            uId.idUsuario = u.getIdUsuario();
            UsuarioEnhanced ur = this.getUsuarioByID(uId, pm);
            if (ur == null) {
            	throw new DAOException(
            		"No encontró el usuario con el ID: " +
            		uId.idUsuario);
            }
            s.setUsuario(ur);

			//Como el proceso es de creación, se asigna al atributo lastIdLiquidación
			//el valor 0, pues aún la solicitud no tiene asociadas liquidaciones.
			s.setLastIdLiquidacion(0);

			//Se hace persistencia sobre la solicitud.
			pm.makePersistent(s);

		} catch (DAOException daoE) {
			Log.getInstance().error(JDOSolicitudesDAO.class,daoE.getMessage());
			Log.getInstance().error(JDOSolicitudesDAO.class,daoE);
			throw new DAOException(daoE.getMessage(), daoE);
		}
		 catch (JDOObjectNotFoundException ex) {
			Log.getInstance().error(JDOSolicitudesDAO.class,ex.getMessage());
			ex.printStackTrace();
			throw new DAOException(ex.getMessage(), ex);
		}
		 catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
		}
		return s;
	}

	/******************************************************************************/
	/*                                                                            */
	/*                  PROCESO DE REPARTO NOTARIAL (RESTITUCION)                 */
	/*                                                                            */
	/******************************************************************************/

	/**
	* Crea una solicitud de restitución de reparto notarial en el sistema
	* @param  s SolicitudRestitucionReparto con sus atributos, exceptuando el identificador
	* @return una SolicitudRestitucionReparto persistente con su identificador.
	* @throws DAOException
	*/
	public SolicitudRestitucionReparto crearSolicitudRestitucionReparto(Solicitud s)
		throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		SolicitudRestitucionRepartoEnhanced sc = null;
		SolicitudRestitucionReparto solRestitucion = (SolicitudRestitucionReparto)s;

		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			sc = this.crearSolicitudRestitucionRepartoNotarial (SolicitudRestitucionRepartoEnhanced.enhance(
						solRestitucion), pm);
			pm.currentTransaction().commit();

			SolicitudEnhancedPk sid = new SolicitudEnhancedPk();
			sid.idSolicitud = sc.getIdSolicitud();
			sc = (SolicitudRestitucionRepartoEnhanced) SolicitudRestitucionRepartoEnhanced.enhance(this.getSolicitudByID(
						sid.getSolicitudID()));
		}
		 catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw (e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
		} finally {
			pm.close();
		}

		return (SolicitudRestitucionReparto) sc.toTransferObject();
	}

	protected SolicitudRestitucionRepartoEnhanced crearSolicitudRestitucionRepartoNotarial(
		SolicitudRestitucionRepartoEnhanced s, PersistenceManager pm)
		throws DAOException {
		String table = "SIR_OP_SOLICITUD";
		JDOTurnosDAO TurnosDAO = new JDOTurnosDAO();
		JDOProcesosDAO ProcesosDAO = new JDOProcesosDAO();
		ProcesoEnhanced pr = new ProcesoEnhanced();
		CausalRestitucionEnhanced causalEnh=null;

		try {
			// Se valida que la solicitud de restitución recibida no sea nula.
			if (s == null) {
				throw new DAOException("La solicitud recibida es null");
			}

			s.setIdSolicitud(this.getLlavePrimariaSolicitud(s, pm));

			//Se valida que exista un Círculo con el identificador recibido
			CirculoEnhanced c = s.getCirculo();
			if (c == null) {
				throw new DAOException(
					"El Circulo asociado a la solicitud es null");
			}
            CirculoEnhancedPk cId = new CirculoEnhancedPk();
            cId.idCirculo = c.getIdCirculo();
            CirculoEnhanced cr = this.getCirculoByID(cId, pm);
            if (cr == null) {
            	throw new DAOException(
            		"No encontró el circulo con el ID: " +
            		cId.idCirculo);
            }
            s.setCirculo(cr);

			//Se valida que exista un Proceso con el identificador recibido
			pr = s.getProceso();
			if (pr == null) {
				throw new DAOException(
					"El Proceso asociado a la solicitud es null");
			}
            ProcesoEnhancedPk prId = new ProcesoEnhancedPk();
            prId.idProceso = pr.getIdProceso();
            ProcesoEnhanced prr = ProcesosDAO.getProcesoByID(prId, pm);
            if (prr == null) {
            	throw new DAOException(
            		"No encontró el proceso con el ID: " +
            		prId.idProceso);
            }
            s.setProceso(prr);

			//Se valida (Si aplica) que exista un turno anterior con el id recibido.
			if (s.getTurnoAnterior() != null) {
				TurnoEnhanced tant = s.getTurnoAnterior();
				TurnoEnhanced tantr = TurnosDAO.getTurnoByWFId(tant.getIdWorkflow(),
						pm);
				if (tantr == null) {
					throw new DAOException(
						"No encontró el turno anterior con el ID: " +
						s.getTurnoAnterior().getIdWorkflow());
				}
                Long idProceso = new Long (tantr.getIdProceso());
                String idProcesoString = idProceso.toString();
                if(idProcesoString.compareTo(CProceso.PROCESO_REPARTO_NOTARIAL_MINUTAS)!=0){
                	throw new DAOException("El turno anterior no corresponde a un proceso de Reparto Notarial" +tant.getIdTurno());
                }
				s.setTurnoAnterior(tantr);
			}

			//Se valida que exista un usuario con  el id recibido.
			UsuarioEnhanced u = s.getUsuario();
			if (u == null) {
				throw new DAOException(
					"El Usuario asociado a la solicitud es null");
			}
            UsuarioEnhancedPk uId = new UsuarioEnhancedPk();
            uId.idUsuario = u.getIdUsuario();
            UsuarioEnhanced ur = this.getUsuarioByID(uId, pm);
            if (ur == null) {
            	throw new DAOException(
            		"No encontró el usuario con el ID: " +
            		uId.idUsuario);
            }
            s.setUsuario(ur);


			//Se valida que exista un Causal de Restitución con el id
			//recibido.
			CausalRestitucionEnhanced causal = s.getCausalRestitucion();
			if (causal ==null){
				throw new DAOException ("El causal de restitución asociado a la solicitud es null");
			}
            CausalRestitucionEnhancedPk causalID = new CausalRestitucionEnhancedPk();
            causalID.idCausalRestitucion = causal.getIdCausalRestitucion();
            causalEnh = this.getCausalRestitucionByID(causalID,pm);
            if (causalEnh == null) {
            	throw new DAOException(
            	"No encontró el Causal de Restitución con el ID: " +
            						causalEnh.getIdCausalRestitucion());
            				}
			s.setCausalRestitucion(causalEnh);

			//Como el proceso es de creación, se asigna al atributo lastIdLiquidación
			//el valor 0, pues aún la solicitud no tiene asociadas liquidaciones.
			s.setLastIdLiquidacion(0);

			//Se hace persistencia sobre la solicitud.
			pm.makePersistent(s);

		} catch (DAOException daoE) {
			Log.getInstance().error(JDOSolicitudesDAO.class,daoE.getMessage());
			Log.getInstance().error(JDOSolicitudesDAO.class,daoE);
			throw new DAOException(daoE.getMessage(), daoE);
		}
		 catch (JDOObjectNotFoundException ex) {
			Log.getInstance().error(JDOSolicitudesDAO.class,ex.getMessage());
			ex.printStackTrace();
			throw new DAOException(ex.getMessage(), ex);
		}
		 catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
		}

		return s;
	}
	
	/**
	 * Crea una solicitud de antiguo sistema en el sistema
	 * @param  solicitud <code>Solicitud</code> con sus atributos, exceptuando el
	 * identificador
	 * @return el identificador de la <code>Solicitud</code> persistente
	 * con su identificador.
	 * @throws DAOException
	 * @see gov.sir.core.negocio.modelo.Solicitud
	 */
	public SolicitudPk crearSolicitudAntiguoSistema(SolicitudAntiguoSistema solicitud)
		throws DAOException {
		
		PersistenceManager pm = AdministradorPM.getPM();
		SolicitudAntiguoSistemaEnhanced solicitudEnh = null;
		SolicitudEnhancedPk solicitudId = new SolicitudEnhancedPk();

		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
			solicitudEnh = crearSolicitudAntiguoSistema(SolicitudAntiguoSistemaEnhanced.enhance(solicitud), pm);
			
			pm.currentTransaction().commit();
			
			solicitudId = (SolicitudEnhancedPk)pm.getObjectId(solicitudEnh);
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			throw (e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return solicitudId.getSolicitudID();
	}
	
	/**
	* Crea una solicitud de antiguo sistema en el sistema.
	* <p>Método utilizado por transacciones.
	* @param  solicitud <code>SolicitudEnhanced</code> con sus atributos, exceptuando el
	* identificador
	* @param pm Manager de la transacción.
	* @return <code>SolicitudEnhanced</code> persistente
	* con su identificador.
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced
	*/
	protected SolicitudAntiguoSistemaEnhanced crearSolicitudAntiguoSistema(SolicitudAntiguoSistemaEnhanced solicitud, PersistenceManager pm)
		throws DAOException {
		
		JDOProcesosDAO procesosDAO = new JDOProcesosDAO();

		try {

		    if (solicitud == null) {
				throw new DAOException("La solicitud recibida es nula");
			}
		    
			solicitud.setIdSolicitud(getLlavePrimariaSolicitud(solicitud, pm));

			CirculoEnhanced circuloEnh = solicitud.getCirculo();
			CirculoEnhancedPk circuloId = new CirculoEnhancedPk();
			circuloId.idCirculo = circuloEnh.getIdCirculo();
			circuloEnh = getCirculoByID(circuloId, pm);

			if (circuloEnh == null) {
				throw new DAOException("No encontró el circulo con el ID: " + circuloId.idCirculo);
			}

			ProcesoEnhanced procesoEnh = solicitud.getProceso();
			ProcesoEnhancedPk procesoId = new ProcesoEnhancedPk();
			procesoId.idProceso = procesoEnh.getIdProceso();
			procesoEnh = procesosDAO.getProcesoByID(procesoId, pm);
			
			if(procesoEnh == null) {
				throw new DAOException("No encontró el proceso con el ID: " + procesoId.idProceso);
			}

			UsuarioEnhanced usuarioEnh = solicitud.getUsuario();
			UsuarioEnhancedPk usuarioId = new UsuarioEnhancedPk();
			usuarioId.idUsuario = usuarioEnh.getIdUsuario();
			usuarioEnh = getUsuarioByID(usuarioId, pm);
			
			if(usuarioEnh == null) {
				throw new DAOException("No encontró el usuario con el ID: " + usuarioId.idUsuario);
			}

			if(solicitud.getDatosAntiguoSistema() != null) {
				DatosAntiguoSistemaEnhanced datosAntiguoSistema = solicitud.getDatosAntiguoSistema();
				datosAntiguoSistema.setIdDatosAntiguoSistema(solicitud.getIdSolicitud());
				if(datosAntiguoSistema.getDocumento()!= null) {
					if(solicitud.getCirculo()!=null){
						datosAntiguoSistema.getDocumento().setCirculo(solicitud.getCirculo().getIdCirculo());
					}
					setDocumentoToDatosAntiguoSistema(datosAntiguoSistema, datosAntiguoSistema.getDocumento(), pm);
				}
			}

			List folios = solicitud.getSolicitudFolios();

			for(Iterator iteradorFolios = folios.iterator(); iteradorFolios.hasNext();) {
				SolicitudFolioEnhanced solicitudFolioEnh = (SolicitudFolioEnhanced) iteradorFolios.next();
				FolioEnhanced folioEnh = solicitudFolioEnh.getFolio();
				folioEnh = this.getFolioByMatricula(folioEnh.getIdMatricula(), pm);
				
				if(folioEnh == null) {
					throw new DAOException("No encontró el folio con el ID: " + 
							solicitudFolioEnh.getFolio().getIdMatricula());
				}
				
				solicitudFolioEnh.setFolio(folioEnh);
				solicitudFolioEnh.setIdMatricula(folioEnh.getIdMatricula());
				solicitudFolioEnh.setSolicitud(solicitud);
				solicitudFolioEnh.setIdSolicitud(solicitud.getIdSolicitud());
				solicitudFolioEnh.setCirculo(solicitud.getCirculo()!=null?solicitud.getCirculo().getIdCirculo():null);
			}

			solicitud.setCirculo(circuloEnh);
			solicitud.setUsuario(usuarioEnh);
			solicitud.setProceso(procesoEnh);

			pm.makePersistent(solicitud);

		} catch (JDOObjectNotFoundException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			Log.getInstance().error(JDOSolicitudesDAO.class,e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

		return solicitud;
	}

	 /**
     * Adiciona un folio a un turno.
     * @param matricula matricula asociada al folio.
     * @param tID identificador del turno.
     * @param user El <code>Usuario</code> que generó la <code>SolicitudFolio</code>
     * @return true o false dependiendo del resultado de la adición.
     * @throws HermodException
     */
	public SolicitudFolioPk addFolioToTurno(Folio folio,
		TurnoPk tID, Usuario user) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		JDOTurnosDAO turnosDAO = new JDOTurnosDAO();

		FolioEnhanced f = FolioEnhanced.enhance(folio);
		//SolicitudRegistroEnhanced s = null;
		TurnoEnhanced tEnh = new TurnoEnhanced();

		TurnoEnhancedPk tEnhID = new TurnoEnhancedPk();
		tEnhID.anio = tID.anio;
		tEnhID.idCirculo = tID.idCirculo;
		tEnhID.idProceso = tID.idProceso;
		tEnhID.idTurno = tID.idTurno;

		SolicitudFolioEnhanced sf = new SolicitudFolioEnhanced();
		sf.setFolio(f);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			tEnh = turnosDAO.getTurnoByID(tEnhID,pm);

			if (tEnh == null) {
				throw new DAOException("No se encontro el turno con el Id dado");
			}

			SolicitudEnhanced s = tEnh.getSolicitud();

			if (s == null) {
				throw new DAOException("No se encontro la solicitud asociada al turno");
			}

			sf = this.addSolicitudFolioToSolicitud(sf, s, user, pm);
			sf.setCirculo(tEnhID.idCirculo);
			pm.makePersistent(sf);
			s.addSolicitudFolio(sf);
			pm.currentTransaction().commit();
			SolicitudFolioEnhancedPk rta = (SolicitudFolioEnhancedPk) pm.getObjectId(sf);
			return rta.getSolicitudFolioID();
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw e;
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().commit();
				pm.close();
			}
		}

	}

	/**
	 * Adiciona una solicitud folio trasiente a una solicitud persistente
	 * La solicitud folio debe tener un folio existente con un número de matrícula
	 * @param sf
	 * @param s
	 * @param pm
	 * @param user <code>Usuario</code> que generó la <code>SolicitudFolio</code>
	 * @return
	 * @throws DAOException
	 */
	protected SolicitudFolioEnhanced addSolicitudFolioToSolicitud(
		SolicitudFolioEnhanced sf, SolicitudEnhanced s, Usuario user, PersistenceManager pm)
		throws DAOException {
		SolicitudFolioEnhanced rta = null;

		try {
			//Se valida que la solicitud consulta recibida no sea nula.
			if (s == null) {
				throw new DAOException("La solicitud recibida es null");
			}

			//Se valida que la solicitud folio recibida no sea nula.
			if (sf == null) {
				throw new DAOException("La solicitud folio recibida es null");
			}


            //Agregar el Usuario a la solicitud
			UsuarioEnhancedPk userId = new UsuarioEnhancedPk();
			userId.idUsuario = user.getIdUsuario();
			//Obtener usuario persistente
			UsuarioEnhanced usuarioPers = (UsuarioEnhanced) pm.getObjectById(userId,true);
			if (usuarioPers == null)
			{
			  throw new DAOException ("El usuario que genera la solicitud no es válido");
			}
			sf.setUsuario(usuarioPers);


			FolioEnhanced f = this.getFolioByMatricula(sf.getIdMatricula(), pm);

			if (f == null) {
				throw new DAOException(
					"No encontró el folio con la matricula: " +
					sf.getIdMatricula());
			}

			String idSol = sf.getIdSolicitud();
			String matricula = f.getIdMatricula();
			Query query = pm.newQuery(SolicitudFolioEnhanced.class);
			query.declareParameters("String idSol, String matricula");
			query.setFilter("this.idSolicitud == idSol && this.idMatricula == matricula");

			Collection col = (Collection) query.execute(idSol, matricula);

			Iterator iter = col.iterator();

			if (iter.hasNext()) {

				throw new DAOException("Ya existe la solicitud folio que se intenta insertar.");
			}

			sf.setFolio(f);
			sf.setSolicitud(s);
			rta = sf;

		} catch (DAOException daoE) {
			Log.getInstance().error(JDOSolicitudesDAO.class,daoE);
			Log.getInstance().error(JDOSolicitudesDAO.class,daoE.getMessage(), daoE);
			throw new DAOException(daoE.getMessage(), daoE);
		} catch (JDOObjectNotFoundException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e);
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e);
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}

		return rta;
	}

	 /**
     * Remueve la asociación de un folio a un turno
     * @param matricula matricula asociada al folio.
     * @param tID Identificador del turno de registro.
     * @return true o false dependiendo del resultado de la eliminacion.
     * @throws HermodException
     */
	public boolean removeFolioFromTurno(Folio folio,
		TurnoPk tID) throws DAOException {
		boolean rta = false;
		PersistenceManager pm = AdministradorPM.getPM();
		SolicitudFolioEnhanced solicitudFolio = null;
		JDOTurnosDAO turnosDAO = new JDOTurnosDAO();

		FolioEnhanced f = FolioEnhanced.enhance(folio);
		SolicitudEnhanced s;

		TurnoEnhanced tEnh = new TurnoEnhanced();

		TurnoEnhancedPk tEnhID = new TurnoEnhancedPk();
		tEnhID.anio = tID.anio;
		tEnhID.idCirculo = tID.idCirculo;
		tEnhID.idProceso = tID.idProceso;
		tEnhID.idTurno = tID.idTurno;

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			tEnh = turnosDAO.getTurnoByID(tEnhID,pm);

			if (tEnh == null) {
				throw new DAOException("No se encontro el turno con el Id dado");
			}

			s = tEnh.getSolicitud();

			if (s == null) {
				throw new DAOException("No se encontro la solicitud asociada al turno");
			}

			String idSol = s.getIdSolicitud();
			String matricula = folio.getIdMatricula();
			Query query = pm.newQuery(SolicitudFolioEnhanced.class);
			query.declareParameters("String idSol, String matricula");
			query.setFilter(
				"this.idSolicitud == idSol && this.idMatricula == matricula");

			Collection col = (Collection) query.execute(idSol, matricula);

			Iterator iter = col.iterator();

			if (iter.hasNext()) {
				solicitudFolio = (SolicitudFolioEnhanced) iter.next();
				pm.deletePersistent(solicitudFolio);
			} else {
				throw new DAOException(
					"La matrícula no está asociada a la solicitud");
			}

			//s.removeSolicitudFolio(solicitudFolio);
			pm.currentTransaction().commit();
			rta = true;
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw e;
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		  finally {
			pm.close();
		}

		return rta;
	}
	
	/**
	 * Registra la matrícula que fue desasociada en los datos básicos del turno
	 * @param folio
	 * @param tID
	 * @return
	 * @throws DAOException
	 */
	public boolean registrarMatriculaEliminadaTurno(Folio folio,
			TurnoPk tID) throws DAOException {
		boolean rta = false;
		PersistenceManager pm = AdministradorPM.getPM();
		JDOTurnosDAO turnosDAO = new JDOTurnosDAO();

		TurnoEnhanced tEnh = new TurnoEnhanced();

		TurnoEnhancedPk tEnhID = new TurnoEnhancedPk();
		tEnhID.anio = tID.anio;
		tEnhID.idCirculo = tID.idCirculo;
		tEnhID.idProceso = tID.idProceso;
		tEnhID.idTurno = tID.idTurno;

		try {
			pm.currentTransaction().begin();

			tEnh = turnosDAO.getTurnoByID(tEnhID,pm);

			if (tEnh == null) {
				throw new DAOException("No se encontro el turno con el Id dado");
			}

			tEnh.setIdMatriculaUltima(folio.getIdMatricula());

			pm.currentTransaction().commit();
			rta = true;
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw e;
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		  finally {
			pm.close();
		}

		return rta;
	}
	
	 /**
     * Ingresa la auditoria del cambio de Matricula
     * @param folioViejo matricula original.
     * @param folionuevo matricula Nueva.
     * @param tID Identificador del turno.
     * @return user Usuario de la Operacion.
     * @throws HermodException
     */
	public boolean addCambioMatriculaAuditoria(String folioViejo, String folionuevo, TurnoPk tID,  Usuario user ) throws DAOException {
		boolean rta = false;
		PersistenceManager pm = AdministradorPM.getPM();
		
		try {
			String idWorkFlow = tID.anio + "-" + tID.idCirculo + "-" + tID.idProceso + "-" + tID.idTurno;
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			CambioMatriculaCerEnhanced cambioMatricula = new CambioMatriculaCerEnhanced();
			
			//Asignacion de llave primaria:
			cambioMatricula.setIdCambioMatricula((int)this.getSecuencial(CSecuencias.SIR_OP_CAMBIO_MATRICULA_CER, null));
			cambioMatricula.setIdWorkflow(idWorkFlow);
			cambioMatricula.setFechaCreacion(new Date());
			cambioMatricula.setIdCirculo(tID.idCirculo);
			cambioMatricula.setIdMatricula(folioViejo);
			cambioMatricula.setIdMatricula1(folionuevo);
			cambioMatricula.setUsuario(user.getIdUsuario());
			
			pm.makePersistent(cambioMatricula);
			pm.currentTransaction().commit();
			rta = true;
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw e;
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 finally {
			pm.close();
		}

		return rta;
	}


	/**
	 * Obtiene y avanza la secuencia de la tabla especificada 
	 * (TOMADO de FORSETTI)
	 * @param tableName Nombre de la tabla de la cual se quiere obtener
	 * el secuencial.
	 * @param pm Persistence Manager de la transacción.
	 * @return El secuencial requerido. 
	 * @throws DAOException
	 */
	protected long getSecuencial(String tableName, PersistenceManager pm) throws DAOException {
		long rta = -1;
		
		boolean transaccionIndependiente = false;
		VersantPersistenceManager pm2 = null;

        Connection con        = null;
        PreparedStatement pst = null;
        ResultSet rs          = null;

		HermodProperties hprop = HermodProperties.getInstancia();
        String secuencias = hprop.getProperty(
                    HermodProperties.SECUENCIALES_POR_SECUENCIA);
        boolean usarSecuencia = false;
        
        if (secuencias != null)
        {
        	String []secuenciasSplit = secuencias.split(";");
        	for (int i= 0; i< secuenciasSplit.length; i++)
        	{
        		if (secuenciasSplit[i].equals(tableName))
        		{
        			usarSecuencia = true;
        			break;
        		}
        	}
        }
        
		if(pm==null){
			transaccionIndependiente = true;
		}
		
		if (tableName != null) {
			try {
				if(transaccionIndependiente){
					pm = AdministradorPM.getPM();
					pm.currentTransaction().setOptimistic(false);					
					pm.currentTransaction().begin();
				}
				
				if (usarSecuencia)
		        {
					String sql = "SELECT SEC_" + (tableName.length() > 26 ? tableName.substring(0, 26) : tableName) + ".nextval FROM DUAL";
		        	
		        	pm2 = (VersantPersistenceManager) pm;

                    con = pm2.getJdbcConnection(null);
		        	pst = con.prepareStatement(sql);
		        	rs  = pst.executeQuery();

		        	if (rs.next())
		        	{
		        		rta = rs.getLong(1);
		        	} else
		        	{
		        		throw new DAOException("No se encontró la secuencia");
		        	}
		        	
		        } else 
		        {
	
					//Se hace el cambio de tipo de bloqueo pesimista para
					//que se bloquee la tabla la cual  nos
					//provee el secuencial
					pm2 = (VersantPersistenceManager) pm;
					pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_ALL);
					pm = pm2;
	
					SecuenciasEnhancedPk sid = new SecuenciasEnhancedPk();
					sid.tableName = tableName;
	
					SecuenciasEnhanced s = this.getSecuenciaByID(sid, pm);
					s.setLastUsedId(s.getLastUsedId() + 1);
	
					//Volvemos a setear el tipo de bloqueo pesimista
					//para que no nos bloquee los siquientes registros
					//consultados
					pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
	
					rta = s.getLastUsedId(); // + 1;
		        }
				
				if(transaccionIndependiente){
					pm.currentTransaction().commit();
				}
				
			} catch (JDOObjectNotFoundException e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				if(transaccionIndependiente){
					if((pm!=null)&&(pm.currentTransaction().isActive())){
						pm.currentTransaction().rollback();
					}	
				}
				throw new DAOException("No se encontró el registro de la secuencia", e);
			} catch (JDOException e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				if(transaccionIndependiente){
					if((pm!=null)&&(pm.currentTransaction().isActive())){
						pm.currentTransaction().rollback();
					}	
				}
				throw new DAOException("Error obteniendo el registro de la secuencia", e);
			} catch (DAOException e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				if(transaccionIndependiente){
					if((pm!=null)&&(pm.currentTransaction().isActive())){
						pm.currentTransaction().rollback();
					}	
				}
				throw new DAOException("Error obteniendo el registro de la secuencia", e);
			}
			catch (Exception e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				if(transaccionIndependiente){
					if((pm!=null)&&(pm.currentTransaction().isActive())){
						pm.currentTransaction().rollback();
					}	
				}
				throw new DAOException("Error obteniendo el registro de la secuencia", e);
			}
			finally{
                            pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
                try{
                    if(rs != null){
                        rs.close();
                    }
                    if(pst != null){
                        pst.close();
                    }
                    if(con != null){
                        con.close();
                    }
                }catch(SQLException e){
                }
                
				if(transaccionIndependiente){
					if(pm!=null){
						pm.close();
					}
				}
			}
		}

		return rta;
	}

	/**
	* Obtiene una secuencia dado su identificador, método utilizado para transacciones
	* TOMADO de FORSETTI
	* @param sID identificador de la secuencia
	* @param pm PersistenceManager de la transaccion
	* @return Secuencia con sus atributos
	* @throws DAOException
	*/
	protected SecuenciasEnhanced getSecuenciaByID(SecuenciasEnhancedPk sID,
		PersistenceManager pm) throws DAOException {
		SecuenciasEnhanced rta = null;

		if (sID.tableName != null) {
			try {
				rta = (SecuenciasEnhanced) pm.getObjectById(sID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}




	/**
	* Obtiene un Causal de Restitución dado su identificador.
	* <p>Método utilizado para transacciones.
	* @param causalID identificador del Causal de Restitución.
	* @param pm PersistenceManager de la transacción
	* @return <code>CausalRestitucionEnhanced</code> con sus atributos
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.CausalRestitucion
	* @see gov.sir.core.negocio.modelo.jdogenie.CausalRestitucionEnhanced
	*/
	protected CausalRestitucionEnhanced getCausalRestitucionByID(CausalRestitucionEnhancedPk causalID,
		PersistenceManager pm) throws DAOException {
			CausalRestitucionEnhanced rta=null;

		try {
				rta = (CausalRestitucionEnhanced) pm.getObjectById(causalID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}

		return rta;
	}




	/**
	* Crea una solicitud de Certificados Masivos en el sistema,
	* @param  s <code>SolicitudCertificadoMasivo</code> con sus atributos,
	* exceptuando el identificador.
	* @return El identificador de la <code>SolicitudCertificadoMasivo</code>
	* persistente.
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo
	* @see gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoMasivo
	*/
	public SolicitudPk crearSolicitudCertificadoMasivo(SolicitudCertificadoMasivo s)
		throws DAOException {
			PersistenceManager pm = AdministradorPM.getPM();
			SolicitudCertificadoMasivoEnhanced sc = null;
			SolicitudEnhancedPk scId = new SolicitudEnhancedPk();

			try {
				pm.currentTransaction().setOptimistic(false);
				pm.currentTransaction().begin();
				sc = this.crearSolicitudCertificadoMasivo(
					SolicitudCertificadoMasivoEnhanced.enhance(s), pm);
				pm.currentTransaction().commit();
				scId = (SolicitudEnhancedPk) pm.getObjectId(sc);
			} catch (JDOException e) {
				if (pm.currentTransaction().isActive()) {
					pm.currentTransaction().rollback();
				}

				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			} catch (DAOException e) {
				if (pm.currentTransaction().isActive()) {
					pm.currentTransaction().rollback();
				}

				throw (e);
			} catch (Throwable e) {
				if (pm.currentTransaction().isActive()) {
					pm.currentTransaction().rollback();
				}

				throw new DAOException(e.getMessage(), e);
			} finally {
				pm.close();
			}

			return scId.getSolicitudID();
	}

	/**
	* Crea una solicitud de Certificados Masivos en el sistema,
	* <p>Método utilizado para transacciones
	* @param  s <code>SolicitudCertificadoMasivoEnhanced</code> con sus atributos,
	* exceptuando el identificador.
	* @param pm PersistenceManager de la transaccion
	* @return una <code>SolicitudCertificadoMasivo</code> persistente con su identificador.
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo
	* @see gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoMasivo
	*/
	protected SolicitudCertificadoMasivoEnhanced crearSolicitudCertificadoMasivo(
		SolicitudCertificadoMasivoEnhanced s, PersistenceManager pm)
		throws DAOException {
		JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
		JDOLiquidacionesDAO liquidacionesDAO = new JDOLiquidacionesDAO();
		JDOProcesosDAO procesosDAO = new JDOProcesosDAO();
		JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
		ProcesoEnhanced pr = new ProcesoEnhanced();

		try {
			// Se valida que la solicitud de certificados masivos recibida no sea nula.
			if (s == null) {
				throw new DAOException("La solicitud recibida es null");
			}
			s.setIdSolicitud(this.getLlavePrimariaSolicitud(s, pm));

			//Se valida que exista un Círculo con el identificador recibido
			CirculoEnhanced c = s.getCirculo();
			if (c == null) {
				throw new DAOException(
					"El Circulo asociado a la solicitud es null");
			}
            CirculoEnhancedPk cId = new CirculoEnhancedPk();
            cId.idCirculo = c.getIdCirculo();
            CirculoEnhanced cr = this.getCirculoByID(cId,
            		pm);
            if (cr == null) {
            	throw new DAOException("No encontró el circulo con el ID: " +
            		cId.idCirculo);
            }
            s.setCirculo(cr);

			//Se valida que exista un Proceso con el identificador recibido
			pr = s.getProceso();
			if (pr == null) {
				throw new DAOException(
					"El Proceso asociado a la solicitud es null");
			}
            ProcesoEnhancedPk prId = new ProcesoEnhancedPk();
            prId.idProceso = pr.getIdProceso();
            ProcesoEnhanced prr = procesosDAO.getProcesoByID(prId, pm);
            if (prr == null) {
            	throw new DAOException("No encontró el proceso con el ID: " +
            		prId.idProceso);
            }
            s.setProceso(prr);

			//Se valida (Si aplica) que exista un turno anterior con el id recibido.
			if (s.getTurnoAnterior() != null) {
				TurnoEnhanced tant = s.getTurnoAnterior();
				TurnoEnhanced tantr = turnosDAO.getTurnoByWFId(tant.getIdWorkflow(),
						pm);
				if (tantr == null) {
					throw new DAOException(
						"No encontró el turno anterior con el ID: " +
						tant.getIdTurno());
				}
				s.setTurnoAnterior(tantr);
			}

			//Se valida que exista un usuario con  el id recibido.
			UsuarioEnhanced u = s.getUsuario();
			if (u == null) {
				throw new DAOException(
					"El Usuario asociado a la solicitud es null");
			}
            UsuarioEnhancedPk uId = new UsuarioEnhancedPk();
            uId.idUsuario = u.getIdUsuario();
            UsuarioEnhanced ur = this.getUsuarioByID(uId, pm);
            if (ur == null) {
            	throw new DAOException("No encontró el usuario con el ID: " +
            		uId.idUsuario);
            }
            s.setUsuario(ur);

			List solAsocs = s.getSolicitudesHijas();
			Iterator it2 = solAsocs.iterator();

			while (it2.hasNext()) {
				SolicitudAsociadaEnhanced solAsoc = (SolicitudAsociadaEnhanced) it2.next();
				SolicitudCertificadoEnhanced solCer = (SolicitudCertificadoEnhanced) solAsoc.getSolicitudHija();
				SolicitudEnhancedPk solCerId = new SolicitudEnhancedPk();
				solCerId.idSolicitud = solCer.getIdSolicitud();
				SolicitudCertificadoEnhanced solCerr = (SolicitudCertificadoEnhanced) this.getSolicitudByID(solCerId, pm);
				if (solCerr == null) {
					throw new DAOException(
						"No encontró la Solicitud Asociada con el ID: " +
						solCer.getIdSolicitud());
				}
				solAsoc.setSolicitudHija(solCerr);
				solAsoc.setSolicitudPadre(s);
			}

			s.setLastIdLiquidacion(0);
			List liq = s.getLiquidaciones();
			Iterator itl = liq.iterator();
			while (itl.hasNext()) {
				LiquidacionTurnoCertificadoMasivoEnhanced l = (LiquidacionTurnoCertificadoMasivoEnhanced) itl.next();
				liquidacionesDAO.addLiquidacionToSolicitudCertificadoMasivo(l, s, pm);
			}


	       //VALIDAR CIUDADANO
			CiudadanoEnhanced ciu = s.getCiudadano();
			if (ciu !=null){

			String docCiudadano = ciu.getDocumento();
			String tipoDocCiudadano = ciu.getTipoDoc();
			CiudadanoEnhanced ciur = this.getCiudadanoByDocumento(tipoDocCiudadano,
			  docCiudadano, pm, ciu.getIdCirculo());
              if (ciur == null) {
                  ciur = this.crearCiudadano(ciu, pm);
              }
			  s.setCiudadano(ciur);
			}

            /**
            * @author      :   Julio Alcázar Rivas
            * @change      :   Enhanced al documento de la solicitud
            * Caso Mantis  :   000941
            */
            if (s.getDocumento() != null)
            {
            	long a = this.getSecuencial(CDocumento.TABLE_NAME,null);
            	DocumentoEnhanced docEnhanced = s.getDocumento();
            	docEnhanced.setIdDocumento(String.valueOf (a));

				//Tipo de Documento
		TipoDocumentoEnhanced tipoDocEnh = docEnhanced.getTipoDocumento();
		if (tipoDocEnh != null)
                {
                    TipoDocumentoEnhancedPk idTipoDocEnh = new TipoDocumentoEnhancedPk();
		    idTipoDocEnh.idTipoDocumento = tipoDocEnh.getIdTipoDocumento();

		    TipoDocumentoEnhanced tipoDocPers = (TipoDocumentoEnhanced) pm.getObjectById(idTipoDocEnh,true);
		    if (tipoDocPers == null)
		    {
			throw new DAOException ("No se encontró el tipo de documento con el identificador: "+idTipoDocEnh.idTipoDocumento);
		    }
		    docEnhanced.setTipoDocumento(tipoDocPers);
		}

		//Oficina Origen
		OficinaOrigenEnhanced ofOrigEnh = docEnhanced.getOficinaOrigen();
		if (ofOrigEnh != null)
		{
		    OficinaOrigenEnhancedPk idOfOrigenEnh = new OficinaOrigenEnhancedPk();
		    idOfOrigenEnh.idOficinaOrigen = ofOrigEnh.getIdOficinaOrigen();
                    /*
                      *  @author Carlos Torres
                      *  @chage   se agrega validacion de version diferente
                      *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                      */
                    idOfOrigenEnh.version = ofOrigEnh.getVersion();
		    OficinaOrigenEnhanced ofOrigPers = (OficinaOrigenEnhanced) pm.getObjectById(idOfOrigenEnh,true);
		    if (ofOrigPers == null)
		    {
			throw new DAOException ("No se encontró la oficina origen con el identificador: "+idOfOrigenEnh.idOficinaOrigen);
		    }
		    docEnhanced.setOficinaOrigen(ofOrigPers);
		}
		if(docEnhanced!=null&&s.getCirculo()!=null){
		   docEnhanced.setCirculo(s.getCirculo().getIdCirculo());
		}
		s.setDocumento(docEnhanced);
            }
			//Se hace persistencia sobre la solicitud.
			pm.makePersistent(s);


		} catch (DAOException daoE) {
			Log.getInstance().error(JDOSolicitudesDAO.class,daoE.getMessage());
			Log.getInstance().error(JDOSolicitudesDAO.class,daoE);
			throw new DAOException(daoE.getMessage(), daoE);
		}
		 catch (JDOObjectNotFoundException ex) {
			Log.getInstance().error(JDOSolicitudesDAO.class,ex.getMessage());
			ex.printStackTrace();
			throw new DAOException(ex.getMessage(), ex);
		}
		 catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
		}

		return s;
	}

	/**
	* Agrega un <code>Ciudadano a una <code>SolicitudConsulta</code>
	* existente en el sistema.
	* @param  solconsulta <code>SolicitudConsulta<code> a la que se agregará
	* el <code>Ciudadano</code>
	* @param  ciud <code>Ciudadano</code> que se va a adicionar.
	* @return una <code>SolicitudConsulta</code> persistente con su
	* <code>Ciudadano</code> asociado.
	* @see gov.sir.core.negocio.modelo.Ciudadano
	* @see gov.sir.core.negocio.modelo.SoliciutConsulta
	* @throws DAOException
	*/
	public SolicitudConsulta agregarCiudadanoConsulta (SolicitudConsulta solConsulta, Ciudadano ciud) throws DAOException {
			PersistenceManager pm = AdministradorPM.getPM();
			SolicitudConsultaEnhanced sc = null;
			BusquedaEnhanced busc = null;

			try {
				pm.currentTransaction().setOptimistic(false);
				pm.currentTransaction().begin();

				//Se obtiene la solicitud consulta persistente.
				SolicitudEnhancedPk scId = new SolicitudEnhancedPk();
				scId.idSolicitud = solConsulta.getIdSolicitud();

				SolicitudConsultaEnhanced solPers = (SolicitudConsultaEnhanced) this.getSolicitudByID(scId,
						pm);
				if (solPers == null) {
					throw new DAOException("No se encontro la solicitud consulta");
				}

				//Hacer persistencia sobre el ciudadano o hacer la relacion con
				//la solicitud.
			    String docCiudadano = ciud.getDocumento();
				String tipoDocCiudadano = ciud.getTipoDoc();
				CiudadanoEnhanced ciur = this.getCiudadanoByDocumento(tipoDocCiudadano,
							   docCiudadano, pm, ciud.getIdCirculo());

				//No existe el ciudadano en la BD
				if (ciur == null) {

					CiudadanoEnhanced ciudEnh = CiudadanoEnhanced.enhance(ciud);
					ciur = this.crearCiudadano(ciudEnh, pm);

					if (ciur == null) {
						throw new DAOException(
								   "No pudo crearse el ciudadano con el documento: " +
								   docCiudadano);
							   }
						   }
				solPers.setCiudadano(ciur);


				SolicitudEnhancedPk sid = new SolicitudEnhancedPk();
				sid.idSolicitud = solPers.getIdSolicitud();

				pm.currentTransaction().commit();
				sc = (SolicitudConsultaEnhanced) SolicitudConsultaEnhanced.enhance(this.getSolicitudByID(
							sid.getSolicitudID()));
			}
			 catch (JDOException e) {
				if (pm.currentTransaction().isActive()) {
					pm.currentTransaction().rollback();
				}
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
			 catch (DAOException e) {
				if (pm.currentTransaction().isActive()) {
					pm.currentTransaction().rollback();
				}
				throw (e);
			} catch (Throwable e) {
				if (pm.currentTransaction().isActive()) {
					pm.currentTransaction().rollback();
				}
			} finally {
				pm.close();
			}

			return (SolicitudConsulta) sc.toTransferObject();
		}

	/**
	* Agrega un <code>numero de anotaciones de un folio<code> </code>
	* a una solicitud.
	* @param  numAnotaciones <code>long</code> que se va a adicionar.
	* @param  solicitud <code>Solicitud<code> a la que se agregará
	* el <code>numAnotaciones</code>
	* @return <code>true</code> si se agrego el valor exitosamente
	* <code>false</code> en caso contrario.
	* @see gov.sir.core.negocio.modelo.SoliciutConsulta
	* @throws DAOException
	*/
	public boolean setAnotacionestoSolicitud(long numAnotaciones, Solicitud solicitud) throws DAOException {
		boolean rta = false;
		PersistenceManager pm = AdministradorPM.getPM();
		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			if (solicitud instanceof SolicitudCertificado){
				SolicitudEnhancedPk solCerId = new SolicitudEnhancedPk();
				solCerId.idSolicitud = solicitud.getIdSolicitud();
				SolicitudCertificadoEnhanced solCer = (SolicitudCertificadoEnhanced) this.getSolicitudByID(solCerId, pm);
				if (solCer == null){
					throw new DAOException("No se encontro la solicitud");
				}
				solCer.setNumeroAnotaciones(numAnotaciones);
				pm.currentTransaction().commit();
			}
		} catch (JDOException e) {
		if (pm.currentTransaction().isActive()) {
			pm.currentTransaction().rollback();
		}
		Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
		throw new DAOException(e.getMessage(), e);
		}
		 catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw (e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
		} finally {
			pm.close();
		}

		return rta;
	}


	/**
	* Método que permite asignar a una <code>SolicitudConsulta</code> persistente,
	* el valor para el atributo numeroMaximoBusquedas recibido como parámetro.
	* @param numMaximo El número máximo de búsquedas permitido para la solicitud.
	* @param solicitud La <code>SolicitudConsulta</code> a la que se va  a
	* asignar el número máximo de búsquedas.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso),
	* dependiendo del resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SolicitudConsulta
	*/
	public boolean setNumMaxBusquedasToSolConsulta (SolicitudConsulta solicitud, int numMaximo)
		throws DAOException
		{
			PersistenceManager pm = AdministradorPM.getPM();
			SolicitudConsultaEnhanced solCons = null;
			SolicitudEnhancedPk solEnhId = new SolicitudEnhancedPk();
			solEnhId.idSolicitud = solicitud.getIdSolicitud();


			try
			{
				pm.currentTransaction().setOptimistic(false);
				pm.currentTransaction().begin();
				solCons = (SolicitudConsultaEnhanced) pm.getObjectById(solEnhId,true);

				if (solCons == null)
				{
					throw new DAOException("No se encontro la solicitud asociada");
				}

				solCons.setNumeroMaximoBusquedas(numMaximo);
				pm.currentTransaction().commit();

			}
			catch (Throwable e)
			{
				if (pm.currentTransaction().isActive())
				{
						pm.currentTransaction().rollback();
				}
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(),e);
			}

			finally
			{
				pm.close();
			}

			return true;
		}




		/**
		* Método que permite asociar un rango de matrículas y las respectivas
		* solicitudes folio a una <code>SolicitudRegistro</code>
		* El servicio realiza la validación de los rangos y verifica la existencia
		* de los folios que va a asociar. En caso de que no exista alguno
		* de los folios, debe partir los  rangos.
		* @param matInicial Valor de la matrícula inicial del rango.
		* @param matFinal Valor de  la matrícula final del rango.
		* @param solicitud <code>Solicitud</code> a la que se asociará el
		* rango de folios.
		* @param user El <code>Usuario</code> que generó la <code>SolicitudFolio</code>
		* @return Lista con objetos de tipo <code>RangoFolio</code> creados
		* a partir de los valores de matrículas inicial y final recibidos como
		* parámetros.
		* @throws DAOException
		* @see gov.sir.core.negocio.modelo.Solicitud
		* @see gov.sir.core.negocio.modelo.SolicitudRegistro
		* @see gov.sir.core.negocio.modelo.RangoFolio
		* @see gov.sir.core.negocio.modelo.SolicitudFolio
		*/
		public List addRangoFoliosToSolicitud (String matInicial, String matFinal, Solicitud solicitud, Usuario user, boolean validarAsociar)
		throws DAOException
		{

			PersistenceManager pm = AdministradorPM.getPM();
			SolicitudRegistroEnhanced solRegistro = null;
			SolicitudCorreccionEnhanced solCorreccion = null;
			SolicitudEnhancedPk solEnhId = new SolicitudEnhancedPk();
			solEnhId.idSolicitud = solicitud.getIdSolicitud();
			List listaSolicitudesFolio = new ArrayList();
			List listaRangos = new ArrayList();
			RangoFolioEnhanced rangoTemporal = new RangoFolioEnhanced();
			SolicitudEnhanced sol;
			JDOLookupDAO lookDAO = new JDOLookupDAO();

			try
			{
				pm.currentTransaction().begin();
				sol = this.getSolicitudByID(solEnhId, pm);
				if(sol==null){
					throw new DAOException("No se encontró una solicitud con el ID: "+solEnhId.idSolicitud);
				}
				//Se captura el número máximo de matrículas que se pueden asociar en un rango
				String maxRango;
				try{
					maxRango = lookDAO.getValor(COPLookupTypes.REGISTRO_TYPE, COPLookupCodes.NUMERO_MAXIMO_RANGO);
				}
				catch(DAOException e){
					//Si no encuentra el code se pone uno por defecto
					maxRango = "500";
				}

				int numMaxRango = Integer.parseInt(maxRango);

				//Se recorre cada una de las matrículas que pertenecen al rango.
				//Hasta que se encuentre una que no exista.
				//En ese caso se parte el rango.

				 //1. Validar que el número de matrícula Inicial < número matrícula final
				 StringTokenizer st = new StringTokenizer (matInicial,"-");
				 String circulo1 = st.nextToken();
				 StringTokenizer st2 = new StringTokenizer (matFinal,"-");
				 String circulo2 = st2.nextToken();
				 String rangoInicial =null;
				 String rangoFinal =null;

				 //1.1 Se tokenizan las matriculas para obtener los números.
				 //Se supone que una matrícula se compone
				 //del círculo, el separador - y el número
				 while (st.hasMoreTokens()) {
					rangoInicial = (st.nextToken());
				 }

				  while (st2.hasMoreTokens()) {
					 rangoFinal = (st2.nextToken());
				 }

			     Integer IntInicial;
			     Integer IntFinal;
			     int intInicial;
			     int intFinal;

			     try
			     {
				   IntInicial = new Integer (rangoInicial);
				   IntFinal = new Integer (rangoFinal);
				   intInicial = IntInicial.intValue();
				   intFinal = IntFinal.intValue();
				  }
			     catch(NumberFormatException nfe)
			     {
				   throw new DAOException("Los valores de las matrículas no son numéricos");
			     }

			     if (intInicial > intFinal){
				    throw new DAOException("La matrícula final debe ser mayor que la matrícula inicial");
			     }

			     if (intInicial == intFinal){
				   throw new DAOException("El valor ingresado no corresponde a un rango.  Debe incluir al menos dos matrículas.");
			     }

			     if (!circulo1.equals(circulo2)){
					throw new DAOException("El círculo debe ser el mismo para ambos rangos.");
				 }

				 if(intFinal-intInicial>numMaxRango){
					throw new DAOException("No puede asociar más de "+maxRango+" folios por rango");
				 }


				//1.2 Se obtiene una lista con los folios que existen en el rango dado.

			   //1.2.1 Se obtiene el valor del atributo LPAD para el rango inicial
			   //y el rango final.
			   FolioEnhanced folio1 = new FolioEnhanced ();
			   String lpadInicial=null;
			   String lpadFinal = null;

				 //1.2.1.1 Encontrar el primer lpad Valido (Inicial)

				 int k= intInicial;
				 int w= intFinal;
				 int continuar=0;

				 while (k<= intFinal && continuar <=0 )
				 {
					 folio1 = this.getFolioByMatricula(circulo1+"-"+k,pm);
					 k++;
					 if (folio1!=null)
					 {
						 continuar++;
					 }

				 }
				 if (folio1 != null)
				 {
					 lpadInicial  = folio1.getOrdenLPAD();
				 }
				 else{
					 throw new DAOException("No existen folios dentro del rango dado.");
				 }

				 //1.2.1.2 Encontrar el último lpad Valido (Final)
				 FolioEnhanced folio2 = new FolioEnhanced ();

				 continuar =0;
				 while (w>= intInicial && continuar <=0 )
				 {
					 folio2 = this.getFolioByMatricula(circulo2+"-"+w,pm);
					 w--;
					 if (folio2!=null)
					 {
						 continuar++;
					 }

				 }
				 if (folio2 != null)
				 {
					 lpadFinal  = folio2.getOrdenLPAD();
				 }
				 else{
					 throw new DAOException("No existen folios dentro del rango dado.");
				 }


				//Agregar el Usuario a la solicitud
				UsuarioEnhancedPk userId = new UsuarioEnhancedPk();
				userId.idUsuario = user.getIdUsuario();
				//Obtener usuario persistente
				UsuarioEnhanced usuarioPers = this.getUsuarioByID(userId, pm);
				if (usuarioPers == null){
					throw new DAOException ("El usuario que genera la solicitud no es válido");
				}

			    //1.2.2 Se obtiene la lista.
			    List foliosExistentes = new ArrayList();

			    foliosExistentes = this.getRangoFoliosByLpad(lpadInicial,lpadFinal,solicitud.getIdSolicitud(),pm);

				if(sol instanceof SolicitudCorreccionEnhanced){

                                        // bug 3593
                                        // no dejar insertar si existe folio que este cerrado
                                        // Se lanza excepcion si esta cerrado

                                        FolioEnhanced local_FolioEnhanced;
                                        // List < FolioEnhanced >
                                        List local_ListFoliosCerrados = new ArrayList();
                                        List local_ListFoliosInconsistente = new ArrayList();
                                        /**
    * @author              : Carlos Mario Torre Urina
    * @casoMantis          :11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
    * @change              : Variable para el manejo de folios trasladados
    */
                                        List local_ListFoliosTrasladados = new ArrayList();
                                        StringBuffer local_ListFoliosCerradosCsvRepresentation = new StringBuffer( 1024 );
                                        StringBuffer local_ListFoliosInconsistentesCsvRepresentation = new StringBuffer( 1024 );
                                                         /**
    * @author              : Carlos Mario Torre Urina
    * @casoMantis          :11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
    * @change              : Variable para el manejo de folios trasladados
    */
                                        StringBuffer local_ListFoliosTrasladadosCsvRepresentation = new StringBuffer( 1024 ); 
                                        final String SEPARATOR = ", ";

					for(Iterator it = foliosExistentes.iterator(); it.hasNext();){
                                                local_FolioEnhanced = (FolioEnhanced)it.next();


                                                // bug 3593
                                                // no dejar insertar si existe folio que este cerrado
                                                // Se lanza excepcion si esta cerrado
                                                if( "C".equals( local_FolioEnhanced.getEstado().getIdEstado() ) ) {
                                                   local_ListFoliosCerrados.add( local_FolioEnhanced );
                                                   local_ListFoliosCerradosCsvRepresentation.append( SEPARATOR + local_FolioEnhanced.getIdMatricula() );
                                                   continue;
                                                } // if
                                                
                                                //No deja ingresar el folio si es inconsistente
                                                if (local_FolioEnhanced.isInconsistente()){
                                                	local_ListFoliosInconsistente.add( local_FolioEnhanced );
                                                	local_ListFoliosInconsistentesCsvRepresentation.append( SEPARATOR + local_FolioEnhanced.getIdMatricula() );
                                                    continue;
                                                }
                                                                                                      /**
    * @author              : Carlos Mario Torre Urina
    * @casoMantis          :11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
    * @change              : No deja ingresar el folio si esta trasladado.*/
                                                if (local_FolioEnhanced.getEstado() != null && local_FolioEnhanced.getEstado().getIdEstado().equals(CEstadoFolio.TRASLADADO)){
                                                	local_ListFoliosTrasladados.add( local_FolioEnhanced );
                                                	local_ListFoliosTrasladadosCsvRepresentation.append( SEPARATOR + local_FolioEnhanced.getIdMatricula() );
                                                    continue;
                                                }

						SolicitudFolioEnhanced sf = new SolicitudFolioEnhanced();
						sf.setSolicitud(sol);
						sf.setFolio( local_FolioEnhanced );
						sf.setUsuario(usuarioPers);
						sf.setCirculo(solicitud.getCirculo()!=null?solicitud.getCirculo().getIdCirculo():null);
						pm.makePersistent(sf);
					}

                                        // raise si existen folios cerrados
                                        if( local_ListFoliosCerrados.size() > 0 ){
                                           throw new DAOException ("Existen folios en el rango que aparecen como cerrados " + local_ListFoliosCerradosCsvRepresentation.toString() );
                                        } // if
                                        
                                        // raise si existen folios Inconsistentes
                                        if( local_ListFoliosInconsistente.size() > 0 ){
                                           throw new DAOException ("Existen folios en el rango que aparecen como inconsistentes " + local_ListFoliosInconsistentesCsvRepresentation.toString() );
                                        } // if
                                        
                                                                                                       /**
    * @author              : Carlos Mario Torre Urina
    * @casoMantis          :11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
    * @change              :raise si existen folios Trasladados
    */                  
                                        if( local_ListFoliosTrasladados.size() > 0 ){
                                           throw new DAOException ("Existen folios en el rango que aparecen como trasladados " + local_ListFoliosTrasladadosCsvRepresentation.toString() );
                                        } // if

				}

				if(sol instanceof SolicitudRegistroEnhanced){
					
					List local_ListFoliosTramite = new ArrayList();
					List local_ListFoliosInconsistente = new ArrayList();
					
					StringBuffer local_ListFoliosTramiteCsvRepresentation = new StringBuffer( 1024 );
					StringBuffer local_ListFoliosInconsistentesCsvRepresentation = new StringBuffer( 1024 );
					final String SEPARATOR_SOL = ", ";
					
					for(Iterator it = foliosExistentes.iterator(); it.hasNext();){
						FolioEnhanced fol = (FolioEnhanced)it.next();
						//Bug 3787. Folios Cerrados pueden asocirase
						//if( !fol.getEstado().getIdEstado().equals(CEstadoFolio.ANULADO) && !fol.getEstado().getIdEstado().equals(CEstadoFolio.CERRADO) ){
						/*
                                                * @author      :   Julio Alcázar Rivas
                                                * @change      :   a) se cambia la comparación para que la realice con la constante del idEstado.
                                                                   b) se agrega la condición qe no asocie folio en estado Trasladado.
                                                * Caso Mantis  :   07123
                                                */
                                                if( !fol.getEstado().getIdEstado().equals(CEstadoFolio.ANULADO) && !fol.isInconsistente()
                                                        && !fol.getEstado().getIdEstado().equals(CEstadoFolio.TRASLADADO)){
							
							if (validarAsociar) {
								//SE VALIDA QUE LA MATRICULA NO ESTE BLOQUEADA
	            				FolioPk folioid = new FolioPk();
	            				folioid.idMatricula = fol.getIdMatricula();
	            				
	            				Usuario usuarioBloqueo = this.getBloqueoFolio(folioid);
	            				
	            				if (usuarioBloqueo != null){
	            					local_ListFoliosTramite.add( fol );
	            					local_ListFoliosTramiteCsvRepresentation.append( SEPARATOR_SOL + fol.getIdMatricula() );
	                                continue;
	            				}
	            				
//	            				
							}
							SolicitudFolioEnhanced sf = new SolicitudFolioEnhanced();
							sf.setSolicitud(sol);
							sf.setFolio(fol);
							sf.setUsuario(usuarioPers);
							sf.setCirculo(solicitud.getCirculo()!=null?solicitud.getCirculo().getIdCirculo():null);
							pm.makePersistent(sf);
						}
						
						//No deja ingresar el folio si es inconsistente
                        if (fol.isInconsistente()){
                        	local_ListFoliosInconsistente.add( fol );
                        	local_ListFoliosInconsistentesCsvRepresentation.append( SEPARATOR_SOL + fol.getIdMatricula() );
                        }

					}
					
					//raise si existen folios estan en tramite
                    if( local_ListFoliosTramite.size() > 0 ){
                       throw new DAOException ("Existen folios en el rango que aparecen en trámite " + local_ListFoliosTramiteCsvRepresentation.toString() );
                    } // if
                    
                    // raise si existen folios Inconsistentes
                    if( local_ListFoliosInconsistente.size() > 0 ){
                       throw new DAOException ("Existen folios en el rango que aparecen como inconsistentes " + local_ListFoliosInconsistentesCsvRepresentation.toString() );
                    } // if
                    }

				pm.currentTransaction().commit();
			}
			catch (DAOException e){
				if (pm.currentTransaction().isActive()){
					pm.currentTransaction().rollback();
				}
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw e;
			}
			catch (Throwable e){
				if (pm.currentTransaction().isActive()){
					pm.currentTransaction().rollback();
				}
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(),e);
			}

			finally{
				pm.close();
			}


			return TransferUtils.makeTransferAll(listaRangos);
		}


	/**
	* Método que permite asociar un rango de matrículas y las respectivas
	* solicitudes folio a una <code>SolicitudRegistro</code>
	* El servicio realiza la validación de los rangos y verifica la existencia
	* de los folios que va a asociar. En caso de que no exista alguno
	* de los folios, debe partir los  rangos.
	* @param matInicial Valor de la matrícula inicial del rango.
	* @param matFinal Valor de  la matrícula final del rango.
	* @param solicitud <code>Solicitud</code> a la que se asociará el
	* rango de folios.
	* @param user El <code>Usuario</code> que generó la <code>SolicitudFolio</code>
	* @return Lista con objetos de tipo <code>RangoFolio</code> creados
	* a partir de los valores de matrículas inicial y final recibidos como
	* parámetros.
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.SolicitudRegistro
	* @see gov.sir.core.negocio.modelo.RangoFolio
	* @see gov.sir.core.negocio.modelo.SolicitudFolio
	*/
	public List addRangoFoliosToSolicitudViejoConRangosEnBD(String matInicial, String matFinal, Solicitud solicitud, Usuario user)
	throws DAOException
	{

		PersistenceManager pm = AdministradorPM.getPM();
		SolicitudRegistroEnhanced solRegistro = null;
		SolicitudCorreccionEnhanced solCorreccion = null;
		SolicitudEnhancedPk solEnhId = new SolicitudEnhancedPk();
		solEnhId.idSolicitud = solicitud.getIdSolicitud();
		List listaSolicitudesFolio = new ArrayList();
		List listaRangos = new ArrayList();
		RangoFolioEnhanced rangoTemporal = new RangoFolioEnhanced();

		try
		{
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();


            //CASO: Solicitud de Registro.
			if (solicitud instanceof SolicitudRegistro) {

			solRegistro = (SolicitudRegistroEnhanced) pm.getObjectById(solEnhId,true);

			if (solRegistro == null)
				{
					throw new DAOException("No se encontro la solicitud asociada");
				}
			}


            //CASO: Solicitud de Correcciones
			if (solicitud instanceof SolicitudCorreccion) {

			   solCorreccion = (SolicitudCorreccionEnhanced) pm.getObjectById(solEnhId,true);

				if (solCorreccion == null)
				{
					throw new DAOException("No se encontro la solicitud asociada");
				}
			}



			//Se recorre cada una de las matrículas que pertenecen al rango.
			//Hasta que se encuentre una que no exista.
			//En ese caso se parte el rango.

			 //1. Validar que el número de matrícula Inicial < número matrícula final
			     StringTokenizer st = new StringTokenizer (matInicial,"-");
			     String circulo1 = st.nextToken();
			     StringTokenizer st2 = new StringTokenizer (matFinal,"-");
			     String circulo2 = st2.nextToken();
			     String rangoInicial =null;
			     String rangoFinal =null;

				 //1.1 Se tokenizan las matriculas para obtener los números.
				 //Se supone que una matrícula se compone
				 //del círculo, el separador - y el número
				 while (st.hasMoreTokens()) {
					rangoInicial = (st.nextToken());
				 }

			      while (st2.hasMoreTokens()) {
					 rangoFinal = (st2.nextToken());
				}

			   Integer IntInicial;
			   Integer IntFinal;
			   int intInicial;
			   int intFinal;

			   try
			   {
				   IntInicial = new Integer (rangoInicial);
				   IntFinal = new Integer (rangoFinal);
				   intInicial = IntInicial.intValue();
				   intFinal = IntFinal.intValue();
			    }
			   catch(NumberFormatException nfe)
			   {
				   throw new DAOException("Los valores de las matrículas no son numéricos");
			   }

			   if (intInicial > intFinal){
			      throw new DAOException("La matrícula final debe ser mayor que la matrícula inicial");
			   }

			   if (intInicial == intFinal){
				   throw new DAOException("El valor ingresado no corresponde a un rango.  Debe incluir al menos dos matrículas.");
			   }

			    if (circulo1.equals(circulo2)){
                    //;
                }
			    else{
			       throw new DAOException("El círculo debe ser el mismo para ambos rangos.");
			    }


               //1.2 Se obtiene una lista con los folios que existen en el rango dado.

                  //1.2.1 Se obtiene el valor del atributo LPAD para el rango inicial
                  //y el rango final.
                  FolioEnhanced folio1 = new FolioEnhanced ();
                  String lpadInicial=null;
                  String lpadFinal = null;

                    //1.2.1.1 Encontrar el primer lpad Valido (Inicial)

                    int k= intInicial;
			        int w= intFinal;
			        int continuar=0;

                    while (k<= intFinal && continuar <=0 )
                    {
						folio1 = this.getFolioByMatricula(circulo1+"-"+k,pm);
						k++;
						if (folio1!=null)
						{
							continuar++;
						}

                    }
			        if (folio1 != null)
			        {
						lpadInicial  = folio1.getOrdenLPAD();
			        }
			        else{
			            throw new DAOException("No existen folios dentro del rango dado.");
			        }

                    //1.2.1.2 Encontrar el último lpad Valido (Final)
			        FolioEnhanced folio2 = new FolioEnhanced ();

					continuar =0;
					while (w>= intInicial && continuar <=0 )
					{
						folio2 = this.getFolioByMatricula(circulo2+"-"+w,pm);
						w--;
						if (folio2!=null)
						{
							continuar++;
						}

					}
					if (folio2 != null)
					{
						lpadFinal  = folio2.getOrdenLPAD();
					}
					else{
						throw new DAOException("No existen folios dentro del rango dado.");
					}


                  //1.2.2 Se obtiene la lista.
                  List foliosExistentes = new ArrayList();


                  foliosExistentes = this.getRangoFoliosByLpadViejo(lpadInicial,lpadFinal,solicitud.getIdSolicitud(),pm);

                  if (foliosExistentes != null)
                  {
					int size = foliosExistentes.size();
					//Se recorre toda la lista de folios existentes y se crean las
					//solicitudes de folio y los rangos.
					FolioEnhanced folioTemporal;
					SolicitudFolioEnhanced solicitudFolioTemporal;
					int valorInicial=1;
					int valorFinal=0;
					int temporal =0;
					int valorFolio =0;
					String lastMatricula=null;

					for (int j = 0; j<size; j++)
					{
						folioTemporal = (FolioEnhanced)foliosExistentes.get(j);
						solicitudFolioTemporal = new SolicitudFolioEnhanced ();

						FolioEnhancedPk idFolio = new FolioEnhancedPk();
						idFolio.idMatricula = folioTemporal.getIdMatricula();

						//Obtener el folio persistente.
						try
						{
							folioTemporal = (FolioEnhanced) pm.getObjectById(idFolio,true);
						}
						catch (Throwable e)
						{
							if (pm.currentTransaction().isActive())
							{
								pm.currentTransaction().rollback();
							}
							Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
							throw new DAOException(e.getMessage(),e);
						}


						//Crear la solicitud Folio y setear sus atributos.
						solicitudFolioTemporal.setFolio(folioTemporal);
						solicitudFolioTemporal.setIdMatricula(folioTemporal.getIdMatricula());

						  //Agregar el Usuario a la solicitud
						  UsuarioEnhancedPk userId = new UsuarioEnhancedPk();
						  userId.idUsuario = user.getIdUsuario();
						    //Obtener usuario persistente
						    UsuarioEnhanced usuarioPers = (UsuarioEnhanced) pm.getObjectById(userId,true);
						    if (usuarioPers == null)
						    {
						    	throw new DAOException ("El usuario que genera la solicitud no es válido");
						    }
						    solicitudFolioTemporal.setUsuario(usuarioPers);


						//CASO: Solicitud de Registro.
						if (solicitud instanceof SolicitudRegistro) {
							solicitudFolioTemporal.setSolicitud(solRegistro);
						}

						//CASO: Solicitud de Correcciones
						if (solicitud instanceof SolicitudCorreccion) {
							solicitudFolioTemporal.setSolicitud(solCorreccion);
						}

						listaSolicitudesFolio.add(solicitudFolioTemporal);

						//Proceso para el manejo de los rangos.
                        //1. Obtener el número del folio.
						StringTokenizer st3 = new StringTokenizer (folioTemporal.getIdMatricula(),"-");
						String inicioRango =null;

						while (st3.hasMoreTokens()) {
						   inicioRango = (st3.nextToken());
						}

						Integer IntInicialRango;
						int intInicialRango=0;


						IntInicialRango = new Integer (inicioRango);
						intInicialRango = IntInicialRango.intValue();

						Long longRango;
						long idRango=0;
						String stringRango =null;


						if(valorInicial ==1)
						{

							rangoTemporal = new RangoFolioEnhanced();

							//CASO: Solicitud de Registro.
							if (solicitud instanceof SolicitudRegistro) {
								idRango = solRegistro.getLastIdRangoFolio();
								solRegistro.setLastIdRangoFolio(idRango +1);
								idRango = solRegistro.getLastIdRangoFolio();
							}

							//CASO: Solicitud de Correcciones
							if (solicitud instanceof SolicitudCorreccion) {
								idRango = solCorreccion.getLastIdRangoFolio();
								solCorreccion.setLastIdRangoFolio(idRango +1);
								idRango = solCorreccion.getLastIdRangoFolio();
							}


							longRango = new Long (idRango);
							stringRango = longRango.toString();
							rangoTemporal.setIdRangoFolio(stringRango);
							rangoTemporal.setFechaCreacion(new Date());
							rangoTemporal.setMatriculaInicial(folioTemporal.getIdMatricula());

						    //CASO: Solicitud de Registro.
							if (solicitud instanceof SolicitudRegistro) {
								rangoTemporal.setSolicitud(solRegistro);
													  }

							//CASO: Solicitud de Correcciones
							if (solicitud instanceof SolicitudCorreccion) {
								rangoTemporal.setSolicitud(solCorreccion);
							}


							valorInicial =0;
							temporal = intInicialRango;
							lastMatricula = folioTemporal.getIdMatricula();


							if (j==size-1)
							{

								rangoTemporal.setMatriculaFinal(lastMatricula);



								listaRangos.add(rangoTemporal);
							}

						}
						else if (valorInicial ==0)
						{
							//El siguiente folio pertenece al rango.
							if (temporal == intInicialRango -1)
							{
								temporal = intInicialRango;
								lastMatricula = folioTemporal.getIdMatricula();
							}
							else
							{
								rangoTemporal.setMatriculaFinal(lastMatricula);

								listaRangos.add(rangoTemporal);

								rangoTemporal = new RangoFolioEnhanced();

							    //CASO: Solicitud de Registro
								if (solicitud instanceof SolicitudRegistro) {
									idRango = solRegistro.getLastIdRangoFolio();
									solRegistro.setLastIdRangoFolio(idRango +1);
									idRango = solRegistro.getLastIdRangoFolio();
								}

								//CASO: Solicitud de Correcciones
								if (solicitud instanceof SolicitudCorreccion) {
									idRango = solCorreccion.getLastIdRangoFolio();
									solCorreccion.setLastIdRangoFolio(idRango +1);
									idRango = solCorreccion.getLastIdRangoFolio();
								}

								longRango = new Long (idRango);
								stringRango = longRango.toString();
								rangoTemporal.setIdRangoFolio(stringRango);

								rangoTemporal.setFechaCreacion(new Date());
								rangoTemporal.setMatriculaInicial(folioTemporal.getIdMatricula());


                                //CASO: Solicitud de Registro
								if (solicitud instanceof SolicitudRegistro) {
									rangoTemporal.setSolicitud(solRegistro);
								}

								//CASO: Solicitud de Correcciones
								if (solicitud instanceof SolicitudCorreccion) {
									rangoTemporal.setSolicitud(solCorreccion);
								}


								valorInicial =0;
								temporal = intInicialRango;
								lastMatricula = folioTemporal.getIdMatricula();



								if (j==size-1)
								{
										rangoTemporal.setMatriculaFinal(lastMatricula);

									    listaRangos.add(rangoTemporal);
								}

							}
						}


					}

                  //Hacer persistentes las solicitudes de folio.
                  if (listaSolicitudesFolio !=null)
                  {

                  for (int t=0; t<listaSolicitudesFolio.size();t++)
                  {
                  	SolicitudFolioEnhanced elemento = (SolicitudFolioEnhanced) listaSolicitudesFolio.get(t);
                  	pm.makePersistent(elemento);
                  }
                  }


				  //Hacer persistentes los rangos de folio.
				  if (listaRangos != null)
				  {

					for (int q=0; q<listaRangos.size();q++)
					{
						RangoFolioEnhanced rango = (RangoFolioEnhanced) listaRangos.get(q);
						pm.makePersistent(rango);
					}
				  }

					pm.currentTransaction().commit();

					//Hacer transientes los elementos de la lista.
					if (listaRangos != null)
					{
					int tamRetornado = listaRangos.size();
					for (int i=0; i<tamRetornado; i++)
					{
			             RangoFolioEnhanced rangoEnhanced = (RangoFolioEnhanced) listaRangos.get(i);
			             pm.makeTransient(rangoEnhanced);

					}
					}

                  }


                  //Se recibió una consulta nula.
                  //No hay rangos ni folios para asociar.
                  else
                  {
                  	 throw new DAOException ("No hay ninguna solicitud folio para agregar dentro del rango dado");
                  }


		}
		catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
            throw e;
        }
		catch (Throwable e)
		{
			if (pm.currentTransaction().isActive())
			{
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(),e);
		}

		finally
		{
			pm.close();
		}


		return TransferUtils.makeTransferAll(listaRangos);
	}

	/**
	* Modifica el encabezado del documento en una solicitud de Registro.
	 * @author Cesar Ramírez
	 * @change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
	 * Se envía el usuario como parámetro.
	 * @param solReg La <code>SolicitudRegistro</code> en la que se va a modificar el <code>Documento</code>
	 * @param usuario La <code>Usuario</code>
	* @return la <code>Solicitudregistro</code> con el <code>Documento</code> modificado.
	* @see gov.sir.core.negocio.modelo.SolicitudRegistro
	* @see gov.sir.core.negocio.modelo.Documento
	* @throws DAOException
	*/
	public SolicitudRegistro modificarEncabezadoRegistro(SolicitudRegistro solReg, Usuario usuario) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();

		SolicitudRegistroEnhanced solRegistro = SolicitudRegistroEnhanced.enhance(solReg);
		SolicitudEnhancedPk solRegId = new SolicitudEnhancedPk();
		solRegId.idSolicitud = solRegistro.getIdSolicitud();
		SolicitudRegistroEnhanced solRegistror = null;
                
		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			solRegistror = (SolicitudRegistroEnhanced) this.getSolicitudByID(solRegId, pm);
                        
			if (solRegistror == null) {
				throw new DAOException("No se encontro la Solicitud con el Id: "
				+ solReg.getIdSolicitud());
			}

			DocumentoEnhancedPk docId = new DocumentoEnhancedPk();
			docId.idDocumento = solRegistror.getDocumento().getIdDocumento();

			OficinaOrigenEnhancedPk ofiOrigId = new OficinaOrigenEnhancedPk();
			OficinaOrigenEnhanced ofiOrigr = null;
			if (solRegistro.getDocumento().getOficinaOrigen()!= null){

				solRegistror.getDocumento().setOficinaInternacional(null);
				ofiOrigId.idOficinaOrigen = solRegistro.getDocumento().getOficinaOrigen().getIdOficinaOrigen();
				/*
                                  *  @author Carlos Torres
                                  *  @chage   se agrega validacion de version diferente
                                  *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                  */
                                ofiOrigId.version = solRegistro.getDocumento().getOficinaOrigen().getVersion();
                                ofiOrigr = variablesDAO.getOficinaOrigen(ofiOrigId, pm);
				if (ofiOrigr == null){
					throw new DAOException("No se encontro la Oficina Origen con el Id: "
					+ solRegistro.getDocumento().getOficinaOrigen().getIdOficinaOrigen());
				}
			}

			TipoDocumentoEnhancedPk tipoDocId = new TipoDocumentoEnhancedPk();
			tipoDocId.idTipoDocumento = solRegistro.getDocumento().getTipoDocumento().getIdTipoDocumento();
			TipoDocumentoEnhanced tipoDocr = variablesDAO.getTipoDocumentoById(tipoDocId, pm);
			if (tipoDocr == null){
				throw new DAOException("No se encontro la Tipo Documento con el Id: "
				+ solRegistro.getDocumento().getTipoDocumento().getIdTipoDocumento());
			}


                        
			DocumentoEnhanced docr = variablesDAO.getDocumentoById(docId, pm);
                          /**
                            * @Author Carlos Torres
                            * @Mantis 13176
                            * @Chaged 
                            */
                        DocumentoEnhanced docrViejo = (DocumentoEnhanced)auditoria.clonarEnhanced(docr);
			docr.setFecha(solRegistro.getDocumento().getFecha());
			docr.setNumero(solRegistro.getDocumento().getNumero());
			docr.setTipoDocumento(tipoDocr);
			docr.setOficinaOrigen(ofiOrigr);

			if(solRegistro.getDocumento().getOficinaInternacional()!=null){
				docr.setOficinaInternacional( solRegistro.getDocumento().getOficinaInternacional() );
				docr.setOficinaOrigen(null);
			}
			
			if(docr!=null&&solRegistror!=null&&solRegistror.getCirculo()!=null){
				docr.setCirculo(solRegistror.getCirculo().getIdCirculo());
			}
			solRegistror.setDocumento(docr);
			//pm.currentTransaction().commit();
                        /**
                            * @Author Carlos Torres
                            * @Mantis 13176
                            * @Chaged 
                            */
                        co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoriaSir = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
                        
                        /**
                         * @author Cesar Ramírez
                         * @change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
                         * Se utiliza el UsuarioEnhanced para adicionarlo a la auditoría al igual que los datos que se cambiaron.
                         **/
                        if(usuario != null) {
                            UsuarioEnhanced us;
                            UsuarioEnhancedPk uid = new UsuarioEnhancedPk();
                            uid.idUsuario = usuario.getIdUsuario();
                            us = this.getUsuarioByID(uid, pm);
                            
                            if (us == null) {
                                throw new DAOException("El usuario especificado no existe: " + uid.idUsuario);
                            }
                            auditoria.addAuditoria(docr, docrViejo, us, pm);
                        }
                        
                        auditoriaSir.guardarAuditoriaDocumento(docr, docrViejo, solRegistro.getIdSolicitud());

		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw e;
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().commit();
			}
			pm.close();
		}
		if (solRegistror == null){
			return null;
		}
        return (SolicitudRegistro) solRegistror.toTransferObject();

	}




	/**
	 * Obtiene una lista de los objetos de tipo <code>FolioEnhanced</code>
	 * que existen dentro del rango pasado como parámetro.
	 * <p>
	 * Método utilizado por transacciones.
	 * @param lpadInicial Valor inicial del rango.
	 * @param lpadFinal Valor final del rango.
	 * @param solId Identificador de la solicitud.
	 * @param PersistenceManager pm
	 * @return Lista de objetos <code>FolioEnhanced</code> que existen dentro del
	 * rango dado.
	 * @throws <code>DAOException</code>
	 * @gov.sir.core.negocio.modelo.Folio
	 */
	protected List getRangoFoliosByLpadViejo (String lpadInicial, String lpadFinal, String solId,
	    PersistenceManager pm) throws DAOException

	    {

		List respuesta = new ArrayList();
		FolioEnhanced folio = new FolioEnhanced();
		String idSolicitud = solId;

		//Las validaciones a los parámetros ya se realizaron en el
		//proceso que invoca este método.

		try {

			//Se establece como filtro de la consulta el rango formado
			//por los parámetros lpadInicial y lpadFinal.
			Query query = pm.newQuery(FolioEnhanced.class);
			query.declareVariables("SolicitudFolioEnhanced sf");
			query.declareParameters("String lpadInicial, String lpadFinal, String idSolicitud");
			query.setFilter("ordenLPAD >= lpadInicial && \n"+
					"ordenLPAD <= lpadFinal &&\n"+
					"!(sf.idSolicitud==idSolicitud &&\n"+
					"sf.idMatricula==this.idMatricula)");

			query.setOrdering("ordenLPAD ascending");

			Collection col = (Collection) query.execute(lpadInicial, lpadFinal, idSolicitud);

			if (col.size() == 0) {
				respuesta = null;
			}



			else {


				Date dateInicial = new Date();
				long initialLong = dateInicial.getTime();
				for (Iterator iter = col.iterator(); iter.hasNext();) {
					folio = (FolioEnhanced) iter.next();
					pm.makeTransient(folio);
					respuesta.add(folio);
				}

				Date dateFinal = new Date();
				long finalLong = dateFinal.getTime();

				query.closeAll();
			}
		}

		catch (JDOObjectNotFoundException e) {
			respuesta = null;
		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}

		return respuesta;
	}



	/**
	 *
	 * @param lpadInicial
	 * @param lpadFinal
	 * @param solId
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected List getRangoFoliosByLpad (String lpadInicial, String lpadFinal, String solId,
		PersistenceManager pm) throws DAOException{
		List respuesta = new ArrayList();
		FolioEnhanced folio = new FolioEnhanced();
		String idSolicitud = solId;

		//Las validaciones a los parámetros ya se realizaron en el
		//proceso que invoca este método.

		try {

			//Se establece como filtro de la consulta el rango formado
			//por los parámetros lpadInicial y lpadFinal.
			Query query = pm.newQuery(FolioEnhanced.class);
			query.declareVariables("SolicitudFolioEnhanced sf");
			query.declareParameters("String lpadInicial, String lpadFinal, String idSolicitud");
			query.setFilter("ordenLPAD >= lpadInicial && \n"+
					"ordenLPAD <= lpadFinal &&\n"+
					"!(sf.idSolicitud==idSolicitud &&\n"+
					"sf.idMatricula==this.idMatricula)");

			query.setOrdering("ordenLPAD ascending");

			List rta = (List) query.execute(lpadInicial, lpadFinal, idSolicitud);

			return rta;
		}

		 catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	/**
	* Actualiza una solicitud de Certificado.
	* @param solCer La <code>SolicitudCertificado</code> que se va a modificar.
	* @return la <code>SolicitudCertificado</code> modificada.
	* @see gov.sir.core.negocio.modelo.SolicitudCertificado
	* @throws <code>DAOException</code>
	*/
	public SolicitudCertificado modificarSolicitudCertificado(SolicitudCertificado solCer) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();

		SolicitudCertificadoEnhanced solCertificado = SolicitudCertificadoEnhanced.enhance(solCer);
		SolicitudEnhancedPk solCerId = new SolicitudEnhancedPk();
		solCerId.idSolicitud = solCertificado.getIdSolicitud();
		SolicitudCertificadoEnhanced solCertr = null;

		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			solCertr = (SolicitudCertificadoEnhanced) this.getSolicitudByID(solCerId, pm);

			if (solCertr == null) {
				throw new DAOException("No se encontro la Solicitud con el Id: "
				+ solCer.getIdSolicitud());
			}

			solCertr.setNumImpresiones(solCertificado.getNumImpresiones());
			
			Ciudadano ciu = solCer.getCiudadano();
			if (solCertr.getCiudadano() == null && ciu != null)
			{
					String docCiudadano = ciu.getDocumento();
					String tipoDocCiudadano = ciu.getTipoDoc();
					CiudadanoEnhanced ciur = this.getCiudadanoByDocumento(tipoDocCiudadano,
							docCiudadano, pm, ciu.getIdCirculo());
					if (ciur == null) {
						ciur = this.crearCiudadano(CiudadanoEnhanced.enhance(ciu), pm);
					}

					else{
						//Actualizar datos de ciudadano:
						this.updateCiudadanoSolicitante(ciur, CiudadanoEnhanced.enhance(ciu), pm);
					}
					solCertr.setCiudadano(ciur);
			}


		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw e;
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().commit();
				pm.close();
			}
		}
		if (solCertr == null){
			return null;
		}
        return (SolicitudCertificado) solCertr.toTransferObject();

	}
	
	/**
	* Actualiza una solicitud de Certificado y Consultas.
	* @param solicitud La <code>Solicitud</code> que se va a modificar.
	* @return la <code>Solicitud</code> modificada.
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @throws <code>DAOException</code>
	*/
	public Solicitud actualizarSolicitud(Solicitud solicitud) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();

		SolicitudEnhanced solicitudEnh = null;
		
		if (solicitud instanceof SolicitudCertificado)
		{
			solicitudEnh = SolicitudCertificadoEnhanced.enhance(solicitud);
		} else if (solicitud instanceof SolicitudConsulta)
		{
			solicitudEnh = SolicitudConsultaEnhanced.enhance(solicitud);
		}
		
		SolicitudEnhancedPk solId = new SolicitudEnhancedPk();
		solId.idSolicitud = solicitudEnh.getIdSolicitud();
		SolicitudEnhanced solEnhPersistente = null;

		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			solEnhPersistente = (SolicitudEnhanced) this.getSolicitudByID(solId, pm);

			if (solEnhPersistente == null) {
				throw new DAOException("No se encontro la Solicitud con el Id: "
				+ solicitudEnh.getIdSolicitud());
			}

			Ciudadano ciu = solicitud.getCiudadano();
			if (solEnhPersistente.getCiudadano() == null && ciu != null)
			{
					String docCiudadano = ciu.getDocumento();
					String tipoDocCiudadano = ciu.getTipoDoc();
					CiudadanoEnhanced ciur = this.getCiudadanoByDocumento(tipoDocCiudadano,
							docCiudadano, pm, ciu.getIdCirculo());
					if (ciur == null) {
						ciur = this.crearCiudadano(CiudadanoEnhanced.enhance(ciu), pm);
					}

					else{
						//Actualizar datos de ciudadano:
						this.updateCiudadanoSolicitante(ciur, CiudadanoEnhanced.enhance(ciu), pm);
					}
					solEnhPersistente.setCiudadano(ciur);
			}


		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw e;
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().commit();
				pm.close();
			}
		}
		if (solEnhPersistente == null){
			return null;
		}
        return (Solicitud) solEnhPersistente.toTransferObject();

	}

	/**
	 * Actualiza la información de ciudadano con la información trasiente del
	 * ciudadano fromCiud
	 * @param toUpdate
	 * @param fromCiud
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected boolean updateCiudadanoSolicitante(CiudadanoEnhanced toUpdate,
		CiudadanoEnhanced fromCiud, PersistenceManager pm) throws DAOException {
		CiudadanoEnhanced rta = null;

		try {
			//Actualización de datos de ciudadano:
			if(fromCiud.getApellido1()!=null){
				toUpdate.setApellido1(fromCiud.getApellido1());
			}

			if(fromCiud.getApellido2()!=null){
				toUpdate.setApellido2(fromCiud.getApellido2());
			}

			if(fromCiud.getNombre()!=null){
				toUpdate.setNombre(fromCiud.getNombre());
			}

			if(fromCiud.getTelefono()!=null){
				toUpdate.setTelefono(fromCiud.getTelefono());
			}

		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

		return true;
	}


	/**
	 * Retorna una lista de objetos <code>Solicitud</code> asociados al
	 * <code>Folio</code> del número de matrícula dado.
	 * @param matricula número de matrícula del folio
	 * @param pm
	 * @return lista de objetos <code>Solicitud</code>
	 * @throws DAOException
	 */
	protected List getSolicitudesByMatricula(String matricula,
		PersistenceManager pm) throws DAOException {
		List rta = new ArrayList();

		try {

			//Se establece como criterio de búsqueda el identificador
			//de la Matrícula.
			Query query = pm.newQuery(SolicitudFolioEnhanced.class);
			query.declareParameters("String matricula");
			query.setFilter("idMatricula == matricula");
			query.setOrdering("solicitud.fecha descending, idSolicitud descending");

			Collection col = (Collection) query.execute(matricula);

			Iterator iter = col.iterator();

			while (iter.hasNext()){
				SolicitudFolioEnhanced solFol = (SolicitudFolioEnhanced) iter.next();
				SolicitudEnhanced sol = solFol.getSolicitud();
				if (sol!= null){
					rta.add(sol);
				}
			}
			query.closeAll();

		} catch (JDOObjectNotFoundException e) {
			rta = null;
		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable t) {
			Log.getInstance().error(JDOSolicitudesDAO.class,t.getMessage());
			throw new DAOException(t.getMessage(), t);
		}

		return rta;
	}




	/**
	 * Obtiene una solicitudFolio dado la solicitud y el folio persistentes, si no encuentra ninguna
	 * relación retorna null
	 * @param sol
	 * @param fol
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected SolicitudFolioEnhanced getSolicitudFolio(SolicitudEnhanced sol, FolioEnhanced fol,
		PersistenceManager pm) throws DAOException {
		SolicitudFolioEnhanced  rta = null;

		try {

			//Se establece como criterio de búsqueda el identificador
			//de la Matrícula.
			Query query = pm.newQuery(SolicitudFolioEnhanced.class);
			query.declareParameters("SolicitudEnhanced sol, FolioEnhanced fol");
			query.setFilter("solicitud==sol && folio==fol");
			query.setOrdering("solicitud.fecha descending, idSolicitud descending");

			Collection col = (Collection) query.execute(sol, fol);

			Iterator iter = col.iterator();

			if(iter.hasNext()){
				rta = (SolicitudFolioEnhanced) iter.next();
			}

		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable t) {
			Log.getInstance().error(JDOSolicitudesDAO.class,t.getMessage());
			throw new DAOException(t.getMessage(), t);
		}

		return rta;
	}
        
        /**
	 * Obtiene una solicitudFolio dado la solicitud y el folio persistentes, si no encuentra ninguna
	 * relación retorna null
	 * @param sol
	 * @author              : Carlos Mario Torre Urina
         * @casoMantis          :11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         * @return
	 * @throws DAOException
	 */
	public SolicitudFolioEnhanced getSolicitudFolio(SolicitudFolioPk sol) throws DAOException {
		SolicitudFolioEnhanced  rta = null;
                PersistenceManager pm = AdministradorPM.getPM();
                rta = this.getSolicitudFolio(new SolicitudFolioEnhancedPk(sol), pm);
		return rta;
	}

/**
	 * Obtiene una solicitudFolio dado la solicitud y el folio persistentes, si no encuentra ninguna
	 * relación retorna null
	 * @param sol
	 * @param fol
	 * @param pm
	 * @return
         * @author              : Carlos Mario Torre Urina
         * @casoMantis          :11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
	 * @throws DAOException
	 */
	protected SolicitudFolioEnhanced getSolicitudFolio(SolicitudFolioEnhancedPk sol,PersistenceManager pm) throws DAOException {
		SolicitudFolioEnhanced  rta = null;

		try {

                    if(sol.idMatricula!=null && sol.idSolicitud!=null)
                    {
                        rta = (SolicitudFolioEnhanced) pm.getObjectById(sol, true);
                    }

		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable t) {
			Log.getInstance().error(JDOSolicitudesDAO.class,t.getMessage());
			throw new DAOException(t.getMessage(), t);
		}

		return rta;
	}

	/**
	* Obtiene una solicitud dado su identificador.
	* <p>
	* La solicitud debe incluir el listado de Liquidaciones asociadas.
	* @param solicitud_id el identificador de la solicitud  que se desea obtener.
	* @return la solicitud con el id recibido como parámetro y su listado de liquidaciones
	* asociadas.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Solicitud
	*/
	public Solicitud getSolicitudById(String solicitud_id) throws DAOException
	{

		SolicitudPk idSolicitud = new SolicitudPk();
		idSolicitud.idSolicitud = solicitud_id;
		return this.getSolicitudByID(idSolicitud);
	}




	/**
	* Valida que la solicitud de certificado sea de tipo "ASOCIADO" y que tenga un
	* padre solicitud de registro que tenga como folio asociado el folio que se pasa por
	* parámetros
	* @param solCer La <code>SolicitudCertificado</code> que se va a validar
	* @param folioID La <code>Folio.ID</code> Identificador del folio a validar
	* @see gov.sir.core.negocio.modelo.SolicitudCertificado
	* @throws <code>DAOException</code>
	*/
	public void validarMatriculaMesaControl(SolicitudCertificado solCer, FolioPk folioID) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		boolean rta = true;
		SolicitudEnhancedPk solCerId = new SolicitudEnhancedPk();
		solCerId.idSolicitud = solCer.getIdSolicitud();
		SolicitudCertificadoEnhanced solCertr = null;

		try {
			pm.currentTransaction().begin();

			solCertr = (SolicitudCertificadoEnhanced) this.getSolicitudByID(solCerId, pm);

			if (solCertr == null) {
				throw new DAOException("No se encontro la Solicitud con el Id: "
				+ solCer.getIdSolicitud());
			}

			if(solCertr.getLiquidaciones().isEmpty()){
				throw new DAOException("La Solicitud con el Id: "
								+ solCer.getIdSolicitud()+" NO tiene liquidaciones");
			}

			/*
			LiquidacionTurnoCertificadoEnhanced liq = (LiquidacionTurnoCertificadoEnhanced)solCertr.getLiquidaciones().get(0);
			if(!liq.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_ASOCIADO_ID)){
				throw new DAOException("La Solicitud con el Id: "
								+ solCer.getIdSolicitud()+" NO es de tipo asociado");
			}*/

			if(solCertr.getSolicitudesPadres().isEmpty()){
				throw new DAOException("La Solicitud con el Id: "
				+ solCer.getIdSolicitud()+ " NO está asociada a ninguna solicitud de registro");
			}

			SolicitudAsociadaEnhanced solPadre = (SolicitudAsociadaEnhanced)solCertr.getSolicitudesPadres().get(0);
			if(solPadre.getSolicitudPadre().getProceso().getIdProceso()!=Integer.valueOf(CProceso.PROCESO_REGISTRO).intValue()){
				throw new DAOException("La Solicitud con el Id: "
								+ solCer.getIdSolicitud()+ " NO está asociada a una solicitud de registro");
			}

			FolioEnhanced fol = this.getFolioByID(new FolioEnhancedPk(folioID), pm);

			if(fol==null){
				throw new DAOException("El folio con matrícula "+folioID.idMatricula+" NO existe");
			}

			Query query = pm.newQuery(SolicitudFolioEnhanced.class);
			query.declareVariables("TurnoEnhanced t");
			query.declareParameters("String idMat,String idSol");
			query.setFilter("this.folio.idMatricula==idMat &&\n"+
					"this.solicitud.idSolicitud==idSol &&\n"+
					"t.solicitud==this.solicitud &&\n " +
					"t.idCirculo==this.solicitud.circulo &&\n "+
					"t.fechaFin==null");
			Collection col = (Collection)query.execute(fol.getIdMatricula(), solPadre.getSolicitudPadre().getIdSolicitud());

			Iterator iter = col.iterator();
			if(!iter.hasNext()) {
				throw new DAOException("El folio con matrícula "+folioID.idMatricula+" NO está asociado al turno de registro padre");
			}

			query.closeAll();

		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw e;
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().commit();
				pm.close();
			}
		}
		return;

	}
        
        public void deleteActosBySolicitud(String idSolicitud) throws DAOException{
        PersistenceManager pm = AdministradorPM.getPM();
        VersantPersistenceManager jdoPM = (VersantPersistenceManager) pm;
        Connection connection = null;
        connection = jdoPM.getJdbcConnection(null);

        String sql = "DELETE FROM SIR_OP_LIQUIDA_CONSERVA_DOCU WHERE ID_SOLICITUD = '"+ idSolicitud +"' ";
        Statement s = null;
        try {
            s = connection.createStatement();
            s.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DAOException("Error SQL: " + e, e);
        } finally {

            try {
                if (s != null) {
                    s.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            }

        }
    }

	/**
	 * @see gov.sir.hermod.dao.SolicitudesDAO#forceUnFolio(gov.sir.core.negocio.modelo.Solicitud, gov.sir.core.negocio.modelo.Folio)
	 */
	public void forceUnFolio(Solicitud solicitud, Folio folio) throws DAOException {
		PersistenceManager pm=AdministradorPM.getPM();

		try {
			//VALIDAR QUE TRAIGAN IDs
			if(solicitud.getIdSolicitud()==null)
				throw new DAOException("La solicitud debe llegar con ID");
			if(folio.getIdMatricula()==null)
				throw new DAOException("El folio debe llegar con matrícula");
			if(folio.getZonaRegistral()==null)
				throw new DAOException("El folio debe llegar con zona registral");

			//CREAR IDs
			SolicitudPk sid = new SolicitudPk();
			sid.idSolicitud=solicitud.getIdSolicitud();
			FolioPk fid = new FolioPk();
			fid.idMatricula=folio.getIdMatricula();

			//INICIAR TRANSACCION
			pm.currentTransaction().begin();

			//BUSCAR PARAMETROS
			SolicitudEnhanced solicitudE=getSolicitudByID(new SolicitudEnhancedPk(sid),pm);
			FolioEnhanced folioE=getFolioByID(new FolioEnhancedPk(fid),pm);

			//EXISTEN LOS PARAMETROS?
			if(solicitudE==null)
				throw new DAOException("La solicitud no existe "+solicitud.getIdSolicitud());
			if(folioE==null)
				throw new DAOException("El folio no existe "+folio.getIdMatricula());

			//BORRAR SOLICITUDES_FOLIO
			pm.deletePersistentAll(solicitudE.getSolicitudFolios());
			VersantPersistenceManager pm2 = (VersantPersistenceManager) pm;
			pm2.flush();
			//CREAR SOLICITUD_FOLIO
			SolicitudFolioEnhanced sfe=new SolicitudFolioEnhanced();
			sfe.setFolio(folioE);
			sfe.setSolicitud(solicitudE);
			sfe.setCirculo(solicitudE.getCirculo().getIdCirculo());
			pm.makePersistent(sfe);
			pm.currentTransaction().commit();

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

	}


	/**
	 * Valida si la solicitud se puede asociar como solicitud vinculada
	 * @param solicitudID
	 * @return
	 * @throws DAOException
	 */
	public boolean validarSolicitudVinculada(SolicitudPk solicitudID) throws DAOException {
		PersistenceManager pm=AdministradorPM.getPM();
		JDOTurnosDAO turDAO = new JDOTurnosDAO();
		try {
			//VALIDAR QUE TRAIGAN IDs
			if(solicitudID.idSolicitud==null)
				throw new DAOException("La solicitud debe llegar con ID");

			//BUSCAR PARAMETROS
			SolicitudEnhanced solicitudE=getSolicitudByID(new SolicitudEnhancedPk(solicitudID),pm);

			//EXISTEN LOS PARAMETROS?
			if(solicitudE==null)
				throw new DAOException("La solicitud vinculada no existe "+solicitudID.idSolicitud);

			//SE VALIDA QUE SEA DE REGSITRO:
			if(solicitudE.getProceso().getIdProceso()!=Long.parseLong(CProceso.PROCESO_REGISTRO)){
				throw new DAOException("La solicitud vinculada no es de registro: "+solicitudID.idSolicitud);
			}

			//SE QUITA LA VALIDACION DE TURNO
			/*
			if(turDAO.getTurnoBySolicitud(solicitudE, pm)!=null){
				throw new DAOException("La solicitud vinculada ya tiene un turno asociado");
			}*/


		} catch (Throwable e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		return true;
	}

	/**
	* Obtiene el estado de pago de una Solicitud.
	* <p> Si la Solicitud tiene un <code>Pago </code> asociado, se retorna <code> true </code>,
	* en el caso contrario se retorna <code>false </code>
	* @param solicitud La <code>Solicitud</code> en la cual se va a consultar el estado de
	* los pagos.
	* @return <code>true </code> si la solicitud ya tiene un <code>Pago</code> o <code>false </code>
	* en el caso contrario.
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.Pago
	* @throws <code>DAOException</code>
	*/
	public boolean getEstadoPagoSolicitud (Solicitud solicitud) throws DAOException
	{
		PersistenceManager pm = AdministradorPM.getPM();

		SolicitudEnhanced solEnh = null;
		SolicitudEnhancedPk idEnh = new SolicitudEnhancedPk();
		idEnh.idSolicitud = solicitud.getIdSolicitud();
		boolean result = false;

		try
		{
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			solEnh = (SolicitudEnhanced) pm.getObjectById(idEnh,true);

			if (solEnh == null) {
						throw new DAOException("No se encontro la Solicitud con el Id: "
						+ solicitud.getIdSolicitud());
			}


			List listaLiquidaciones = new ArrayList();
			listaLiquidaciones = solEnh.getLiquidaciones();
			if (listaLiquidaciones == null)
			{
				result = false;
			}
			else if (listaLiquidaciones.size()==0)
			{
				result = false;
			}
			else
			{
				LiquidacionEnhanced liq = (LiquidacionEnhanced)listaLiquidaciones.get(0);
				if (liq == null)
				{
					result = false;
				}
				if (liq != null)
				{

					try
					{
						if (liq.getPago()!=null)
						{
							result =  true;
						}
					}
					catch (Throwable t)
					{
						result = false;
					}

			     }
			}

			pm.currentTransaction().commit();


		}
		catch (DAOException e)
		{
			if (pm.currentTransaction().isActive())
			{
			pm.currentTransaction().rollback();
			}
			throw e;
		}

		catch (JDOException e)
		{
			if (pm.currentTransaction().isActive())
			{
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

		catch (Throwable e)
		{
			if (pm.currentTransaction().isActive())
			{
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

		finally
		{
			if (pm.currentTransaction().isActive())
			{
				pm.currentTransaction().commit();
				pm.close();
			}
		}

		return result;
	}


	/**
	 * Cambia el flag de aprobación de una solicitud de corrección o devolución
	 * @param solID Id de la solicitud
	 * @param aprobada Nuevo estado del flag de aprobación
	 * @return
	 * @throws DAOException
	 */
	public boolean setAprobacionSolicitud(SolicitudPk solID, boolean aprobada) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			SolicitudEnhanced sol = (SolicitudEnhanced) this.getSolicitudByID(new SolicitudEnhancedPk(solID), pm);

			if (sol == null) {
				throw new DAOException("No se encontro la Solicitud con el Id: "
				+ solID.idSolicitud);
			}

			if(sol instanceof SolicitudCorreccionEnhanced){
				SolicitudCorreccionEnhanced solCor = (SolicitudCorreccionEnhanced) sol;
				solCor.setAprobada(aprobada);
			}
			else if(sol instanceof SolicitudDevolucionEnhanced){
				SolicitudDevolucionEnhanced solDev = (SolicitudDevolucionEnhanced) sol;
				solDev.setAprobada(aprobada);
				if(!aprobada){
                                       /*
                                        * Se elimina la posibilidad de eliminar la relación de los turnos de devolución con su turno padre
                                        * @author: Julio Alcazar
                                        * @change: 7393: Acta - Requerimiento No 215 - Ver Detalles de Turno - Turnos Devolucion Negados
                                        */
					if(solDev.getTurnoAnterior()==null){
						List consignaciones = solDev.getConsignaciones();
						for(int i=0; i<consignaciones.size();i++){
							ConsignacionEnhanced conEnh = (ConsignacionEnhanced)consignaciones.get(i);
							pm.deletePersistent(conEnh.getDocPago());
							pm.deletePersistent(conEnh);
						}
						solDev.setConsignaciones(null);
					}
					for(int j=0;j<solDev.getLiquidaciones().size();j++){
						((LiquidacionTurnoDevolucionEnhanced)solDev.getLiquidaciones().get(j)).setValor(0);
					}
				}
			}else{
				throw new DAOException("El tipo de la solicitud NO es correción ni devolución");
			}

			pm.currentTransaction().commit();


		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw e;
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().commit();
				pm.close();
			}
		}

		return true;

	}

	/**
	 * @see gov.sir.hermod.dao.SolicitudesDAO#addSolicitudHija(gov.sir.core.negocio.modelo.Solicitud)
	 */
	public Solicitud addSolicitudHija(Solicitud solPadre, Solicitud solHija) throws DAOException {

		SolicitudEnhancedPk solHijaID = new SolicitudEnhancedPk();
		solHijaID.idSolicitud = solHija.getIdSolicitud();

		SolicitudEnhancedPk solPadreID = new SolicitudEnhancedPk();
		solPadreID.idSolicitud = solPadre.getIdSolicitud();

		SolicitudEnhanced solPadreEnh = null;

		PersistenceManager pm=AdministradorPM.getPM();
		try{
			try{

				//obtener la solicitud hija
				SolicitudEnhanced solHijaEnh =  this.getSolicitudByID(solHijaID, pm);
				if (solHijaEnh == null) {
					throw new DAOException(
						"No encontró la Solicitud Asociada con el ID: " +
						solHija.getIdSolicitud());
				}

				//obtener la solicitud padre
				solPadreEnh =  this.getSolicitudByID(solPadreID, pm);
				if (solPadreEnh == null) {
					throw new DAOException(
						"No encontró la Solicitud Asociada con el ID: " +
						solPadre.getIdSolicitud());
				}

				//crear la solicitud asociada y volverla persistente
				SolicitudAsociadaEnhanced solAsoc = new SolicitudAsociadaEnhanced();
				//solAsoc.setSolicitudPadre(solPadreEnh);
				//solAsoc.setSolicitudHija(solHijaEnh);
				solAsoc.setIdSolicitud(solPadreEnh.getIdSolicitud());
				solAsoc.setIdSolicitud1(solHijaEnh.getIdSolicitud());

				pm.currentTransaction().begin();
				pm.makePersistent(solAsoc);
				pm.currentTransaction().commit();

				//obtener la solicitud padre para retornarla
				this.makeTransientSolicitud(solPadreEnh, pm);


			} catch (JDOObjectNotFoundException e) {
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				Log.getInstance().error(JDOSolicitudesDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			} catch (JDOException e) {
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			} catch (Throwable e) {
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			throw (e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
			return (Solicitud) solPadreEnh.toTransferObject();

	}
	
	/**
	 * Se incrementan los intentos de reimpresión de una solicitud específica
	 * @param idSolicitud
	 * @throws Throwable
	 */
	public void incrementarIntentoReimpresionRecibo(SolicitudPk idSolicitud) throws DAOException{
		SolicitudEnhancedPk idEnh = new SolicitudEnhancedPk();
		idEnh.idSolicitud=idSolicitud.idSolicitud;
		PersistenceManager pm=AdministradorPM.getPM();
		try {
			pm.currentTransaction().begin();
			SolicitudEnhanced solicitud=(SolicitudEnhanced)pm.getObjectById(idEnh,true);
			int intentos=solicitud.getNumReimpresionesRecibo();
			solicitud.setNumReimpresionesRecibo(intentos+1);
			pm.currentTransaction().commit();
			
		}  catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().commit();
				pm.close();
			}
		}
	}
	


	/**
	 * Hace transientes las oficinas origen
	 * @param oficina
	 * @param pm
	 * @throws DAOException
	 */
	protected void makeTransientOficinaOrigen(OficinaOrigenEnhanced oficina,
		PersistenceManager pm) throws DAOException {
		if (oficina != null) {
			try {
				pm.makeTransient(oficina.getTipoOficina());

				if (oficina.getVereda() != null) {
					pm.makeTransient(oficina.getVereda().getMunicipio()
											.getDepartamento());
					pm.makeTransient(oficina.getVereda().getMunicipio());
					pm.makeTransient(oficina.getVereda());
				}

				for(Iterator it = oficina.getCategorias().iterator(); it.hasNext();){
					OficinaCategoriaEnhanced ofCat = (OficinaCategoriaEnhanced)it.next();
					pm.makeTransient(ofCat.getCategoria());
					pm.makeTransient(ofCat);
				}

				pm.makeTransient(oficina);
			} catch (JDOException e) {
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}
	}
	
	/**
	* Elimina la <code>Solicitud</code> recibida como parámetro.
	* @param solicitud La <code>Solicitud</code> que va a ser eliminada.
	* @return <code>true</code> o <code>false</code> dependiendo del resultado de la operación.
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @throws <code>Throwable</code>
	*/
	public boolean deleteTurnoAnterior(SolicitudPk idSolicitud) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		

		try {

			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
			//Traer objeto Persistente
			SolicitudEnhancedPk idSol = new SolicitudEnhancedPk();
			idSol.idSolicitud = idSolicitud.idSolicitud;

			SolicitudEnhanced sol = null;

			try {
				sol = (SolicitudEnhanced) pm.getObjectById(idSol, true);
			}
			catch (JDOObjectNotFoundException e) {
				sol = null;
			}
		
			if (sol == null) {
				throw new DAOException(
					"No existe la solicitud con el id " + idSol.getSolicitudID());
			}
			
			//Se mira si tiene turno anterior para eliminarlo hijas asociadas para eliminarlas
			TurnoEnhanced turnoAnterior = sol.getTurnoAnterior();
			if(turnoAnterior!=null){
				sol.setTurnoAnterior(null);
			}

			
					

			pm.currentTransaction().commit();
		}

		catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		
		return true;

	}
	
	/**
	 * Consulta las solicitudes que tengan el <code>Turno</code> como turno anterior
	 * @param turnoID
	 * @return
	 * @throws DAOEXception
	 */
	public List getSolicitudesByTurnoAnterior (Turno turno) throws DAOException
	{
		PersistenceManager pm = AdministradorPM.getPM();
		List rta = new ArrayList();

		try {

			//Se establece como criterio de búsqueda el identificador
			//de la Matrícula.
			Query query = pm.newQuery(SolicitudEnhanced.class);
			
			query.declareParameters("String idWorkflow");
			query.setFilter("this.turnoAnterior.idWorkflow == idWorkflow");

			Collection col = (Collection) query.execute(turno.getIdWorkflow());

			Iterator iter = col.iterator();

			while (iter.hasNext()){
				SolicitudEnhanced sol = (SolicitudEnhanced) iter.next();
				pm.makeTransient(sol.getTurno());
				this.makeTransientSolicitud(sol, pm);
				if (sol!= null){
					rta.add(sol.toTransferObject());
				}
			}
			query.closeAll();

		} catch (JDOObjectNotFoundException e) {
			rta = null;
		} catch (JDOException e) {
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable t) {
			Log.getInstance().error(JDOSolicitudesDAO.class,t.getMessage());
			throw new DAOException(t.getMessage(), t);
		}finally{
			pm.close();
		}

		return rta;
	}

	/** Pablo Quintana Junio 19 2008
	 *  Retorna un Testamento asociado a un turno según el IdSolicitud*/
	public Testamento getTestamentoByID(String idSolicitud) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		TestamentoEnhancedPk tID = new TestamentoEnhancedPk();
		tID.idTestamento = idSolicitud;
		TestamentoEnhanced testa = null;
		try{
			try {
				testa = (TestamentoEnhanced) pm.getObjectById(tID, true);
			} catch (JDOObjectNotFoundException e) {
				testa = null;
			} catch (JDOException e) {
				Log.getInstance().debug(JDOSolicitudesDAO.class, e);
				Log.getInstance().error(JDOSolicitudesDAO.class, e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			}
			if (testa == null){
				throw new DAOException("No encontró el testamento con el ID: "+tID.idTestamento);
			}
			for(int i=0;i<testa.getTestadores().size();i++){
				pm.makeTransient(((TestamentoCiudadanoEnhanced)testa.getTestadores().get(i)).getCiudadano());
			}
			pm.makeTransientAll(testa.getTestadores());
			pm.makeTransient(testa);
		}
		catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class, e.getMessage());
			throw e;
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		return (Testamento) testa.toTransferObject();
	}
	
	/**
	 * Elimina del sistema un testador de testamentos
	 * @param turno identificador del turno al cual se le van a eliminar las notas devolutivas.
	 * @throws DAOException
	 */
	 public void removeTestadorFromTestamento (TestamentoCiudadanoPk testamentoCiudadanoID) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		try{
			pm.currentTransaction().begin();
			//SE CAPTURA EL TURNO
			TestamentoCiudadanoEnhanced testamentoCiudadanoEnh=this.getTestamentoCiudadanoByID(new TestamentoCiudadanoEnhancedPk(testamentoCiudadanoID), pm);
			if(testamentoCiudadanoEnh == null){
				throw new DAOException("No encontró el turno con el ID indicado");
			}
			pm.deletePersistent(testamentoCiudadanoEnh);
			//TERMINAR TRANSACCION
			pm.currentTransaction().commit();
		}catch (JDOException e){
			if (pm.currentTransaction().isActive()){
			     pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}catch (DAOException e){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class, e.getMessage());
			throw e;
		}catch (Exception e){
			if (pm.currentTransaction().isActive()){
			     pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}finally{
			pm.close();
		}
	}
	 
	    /**
	     * Obtiene un TestamentoCiudadano dado su identificador, método utilizado para transacciones
	     * @param tID identificador del Turno
	     * @param pm PersistenceManager de la transaccion
	     * @return TestamentoCiudadano con sus atributos
	     * @throws DAOException
	     */
	    protected TestamentoCiudadanoEnhanced getTestamentoCiudadanoByID(TestamentoCiudadanoEnhancedPk tID,
	        PersistenceManager pm) throws DAOException {
	        TestamentoCiudadanoEnhanced rta = null;
			//Se coloca un tiempo de delay
			JDOVariablesOperativasDAO jdoDAO = new JDOVariablesOperativasDAO();
			jdoDAO.getObjectByLlavePrimaria(tID, 3, pm);
			if(tID!=null && tID.idCiudadano!=null && tID.idTestamento!=null
					&& String.valueOf(tID.idCiudadano)!=null  && String.valueOf(tID.idTestamento)!=null  ){
				try {
					rta=(TestamentoCiudadanoEnhanced)pm.getObjectById(tID, true);
	            } catch (JDOObjectNotFoundException e) {
	                rta = null;
	            } catch (JDOException e) {
	                Log.getInstance().error(JDOSolicitudesDAO.class, e.getMessage(), e);
	                throw new DAOException(e.getMessage(), e);
	            }
			}
			return rta;
	    }
	    
		 public void actualizaSolicitud (Solicitud solicitud,List consignaciones) throws DAOException{
				PersistenceManager pm=AdministradorPM.getPM();
				try{
					TurnoEnhancedPk turnoPK=new TurnoEnhancedPk();
					if(solicitud!=null && solicitud.getTurnoAnterior()!=null){
						turnoPK.anio=solicitud.getTurnoAnterior().getAnio();
						turnoPK.idCirculo=solicitud.getTurnoAnterior().getIdCirculo();
						turnoPK.idProceso=solicitud.getTurnoAnterior().getIdProceso();
						turnoPK.idTurno=solicitud.getTurnoAnterior().getIdTurno();
					}
					SolicitudEnhancedPk idSol = new SolicitudEnhancedPk();
					JDOPagosDAO pagosDAO = new JDOPagosDAO();//new
					idSol.idSolicitud = solicitud.getIdSolicitud();
					//solicitud=null;
					pm=AdministradorPM.getPM();
					pm.currentTransaction().setOptimistic(false);
					pm.currentTransaction().begin();
					/*SolicitudEnhanced sol = null;
					try {
						sol = (SolicitudEnhanced) pm.getObjectById(idSol, true);
					}catch (JDOObjectNotFoundException e) {
						sol = null;
					}
					TurnoEnhanced turnoAnterior = sol.getTurnoAnterior();
					if(turnoAnterior!=null){
						sol.setTurnoAnterior(null);
						TurnoEnhanced turnoAnt= (TurnoEnhanced) pm.getObjectById(turnoPK, true);
						sol.setTurnoAnterior(turnoAnt);
					} */
					SolicitudDevolucionEnhanced sol = null;
					try {
						sol = (SolicitudDevolucionEnhanced) pm.getObjectById(idSol, true);
					}catch (JDOObjectNotFoundException e) {
						sol = null;
					}
					TurnoEnhanced turnoAnterior = sol.getTurnoAnterior();
					if(solicitud!=null && solicitud.getTurnoAnterior()!=null){
						if(turnoAnterior!=null)
							sol.setTurnoAnterior(null);
						TurnoEnhanced turnoAnt= (TurnoEnhanced) pm.getObjectById(turnoPK, true);
						sol.setTurnoAnterior(turnoAnt);
					}
					if(consignaciones!=null && consignaciones.size()>0){
						boolean delete;
						for(int j=sol.getConsignaciones().size()-1;j>=0;j--){
							delete=false;
							for(int i=0;i<consignaciones.size();i++){
								if((ConsignacionEnhanced.enhance((Consignacion)consignaciones.get(i))).getIdDocPago()!=null
										&& (ConsignacionEnhanced.enhance((Consignacion)consignaciones.get(i))).getIdDocPago()
										.equals(((ConsignacionEnhanced)sol.getConsignaciones().get(j)).getIdDocPago())) {
									delete=false;
									break;
								}else{
									delete=true;
								}
							}
							if(delete){
								pm.deletePersistent((ConsignacionEnhanced)sol.getConsignaciones().get(j));
								sol.removeConsignacion((ConsignacionEnhanced)sol.getConsignaciones().get(j));
							}
						}
					}else if(sol.getConsignaciones()!=null && sol.getConsignaciones().size()>0){
						pm.deletePersistentAll(sol.getConsignaciones());
					}
					//Add
					boolean add;
					DocPagoConsignacion docPagoConsignacion;
					DocPagoCheque docPagoCheque; 
					Banco banco;
					double valor = 0;
					for(int i=consignaciones.size()-1;i>=0;i--){
					  docPagoConsignacion=null;
						docPagoCheque=null;
						banco=null;
						String idBanco = null; 
						add=true;
						for(int j=0;j<sol.getConsignaciones().size();j++){
							if((ConsignacionEnhanced.enhance((Consignacion)consignaciones.get(i))).getIdDocPago()!=null
									&& (ConsignacionEnhanced.enhance((Consignacion)consignaciones.get(i))).getIdDocPago()
									.equals(((ConsignacionEnhanced)sol.getConsignaciones().get(j)).getIdDocPago())) {
								add=false;
								break;
							}
						}
						if(add){
							((Consignacion)consignaciones.get(i)).getDocPago().setIdDocumentoPago(String.valueOf(getSecuencial(CSecuencias.SIR_OP_DOCUMENTO_PAGO, null)));
							if( ((Consignacion)consignaciones.get(i)).getDocPago() instanceof DocPagoCheque ){
								banco=((DocPagoCheque)((Consignacion)consignaciones.get(i)).getDocPago()).getBanco();
								idBanco = new String(banco.getIdBanco());
								valor += ((DocPagoCheque)((Consignacion)consignaciones.get(i)).getDocPago()).getValorDocumento();
								((DocPagoCheque)((Consignacion)consignaciones.get(i)).getDocPago()).setBanco(null);
							}else if( ((Consignacion)consignaciones.get(i)).getDocPago() instanceof DocPagoConsignacion  ){
								banco=((DocPagoConsignacion)((Consignacion)consignaciones.get(i)).getDocPago()).getBanco();
								idBanco = new String(banco.getIdBanco());
								valor += ((DocPagoConsignacion)((Consignacion)consignaciones.get(i)).getDocPago()).getValorDocumento();
								((DocPagoConsignacion)((Consignacion)consignaciones.get(i)).getDocPago()).setBanco(null);
							}
							ConsignacionEnhanced conEnh = ConsignacionEnhanced.enhance((Consignacion)consignaciones.get(i));
							pm.makePersistent(conEnh);
							
							BancoEnhanced banEnh = (BancoEnhanced)pm.getObjectById(new BancoEnhancedPk(idBanco), true);
							
							if( ((Consignacion)consignaciones.get(i)).getDocPago() instanceof DocPagoCheque ){
								((DocPagoCheque)((Consignacion)consignaciones.get(i)).getDocPago()).setBanco(banco);
								((DocPagoChequeEnhanced)conEnh.getDocPago()).setBanco(banEnh);
							}else if( ((Consignacion)consignaciones.get(i)).getDocPago() instanceof DocPagoConsignacion  ){
								((DocPagoConsignacion)((Consignacion)consignaciones.get(i)).getDocPago()).setBanco(banco);
								((DocPagoConsignacionEnhanced)conEnh.getDocPago()).setBanco(banEnh);
							}
						}else{
							if(pagosDAO!=null){
								if( ((Consignacion)consignaciones.get(i)).getDocPago() instanceof DocPagoConsignacion  ){
									docPagoConsignacion=pagosDAO.getDocPagoConsignacion(((DocPagoConsignacion)((Consignacion)consignaciones.get(i)).getDocPago()).getNoConsignacion());
									banco=docPagoConsignacion.getBanco();
									valor+=docPagoConsignacion.getValorDocumento();
									((DocPagoConsignacion)((Consignacion)consignaciones.get(i)).getDocPago()).setBanco(banco);
								}else if( ((Consignacion)consignaciones.get(i)).getDocPago() instanceof DocPagoCheque ){
									docPagoCheque=pagosDAO.getDocPagoCheque(((DocPagoCheque)((Consignacion)consignaciones.get(i)).getDocPago()).getNoCheque());
									banco=docPagoCheque.getBanco();
									valor += docPagoCheque.getValorDocumento();
									((DocPagoCheque)((Consignacion)consignaciones.get(i)).getDocPago()).setBanco(banco);
								}
							}
						}
					}
					
					((LiquidacionTurnoDevolucionEnhanced)sol.getLiquidaciones().get(0)).setValor(valor);

					consignaciones=null;
					pm.currentTransaction().commit();
				}catch (JDOException e){
					if (pm.currentTransaction().isActive()){
					     pm.currentTransaction().rollback();
					}
					Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
					throw new DAOException(e.getMessage(), e);
				}catch (Exception e){
					if (pm.currentTransaction().isActive()){
					     pm.currentTransaction().rollback();
					}
					Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
					throw new DAOException(e.getMessage(), e);
				}finally{
					pm.close();
				}
			}

		 public void updateSubtipoSolicitud(String idSubtipoSolicitud, String idSolicitud)throws DAOException{
			 PersistenceManager pm = AdministradorPM.getPM();
			 try{
				 pm.currentTransaction().setOptimistic(false);
				 pm.currentTransaction().begin();
				 
				 SolicitudEnhancedPk solID = new SolicitudEnhancedPk();
				 solID.idSolicitud = idSolicitud;
				 
				 SolicitudRegistroEnhanced solEnh= (SolicitudRegistroEnhanced)this.getSolicitudByID(solID, pm);
				 
				 SubtipoSolicitudEnhancedPk subID = new SubtipoSolicitudEnhancedPk();
				 subID.idSubtipoSol = idSubtipoSolicitud;
				 
				 SubtipoSolicitudEnhanced subEnh = (SubtipoSolicitudEnhanced)pm.getObjectById(subID, true);
				 
				 solEnh.setSubtipoSolicitud(subEnh);
				 pm.currentTransaction().commit();
			 }catch (JDOException e){
				if (pm.currentTransaction().isActive()){
					pm.currentTransaction().rollback();
				}
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}catch (Exception e){
				if (pm.currentTransaction().isActive()){
					pm.currentTransaction().rollback();
				}
				Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}finally{
				pm.close();
			}
		 }

   /**
     * @author      :   Julio Alcázar Rivas
     * Caso Mantis  :   02359
     * @return true si sobre el turno se realizo un cambio de matricula
     * @param tID Identificador del turno.
     * @throws HermodException
     */
	public boolean getCambioMatriculaAuditoria(TurnoPk tID ) throws DAOException {
		boolean rta = false;
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			String idWorkFlow = tID.anio + "-" + tID.idCirculo + "-" + tID.idProceso + "-" + tID.idTurno;

			pm.currentTransaction().begin();

			Query query = pm.newQuery(CambioMatriculaCerEnhanced.class);
			query.declareParameters("String idWorkFlow");
			query.setFilter( "this.idWorkflow == idWorkFlow ");

			Collection col = (Collection) query.execute(idWorkFlow);

			Iterator iter = col.iterator();

                        if (iter.hasNext()) {
                            rta = true;
			}

			pm.currentTransaction().commit();

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOSolicitudesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 finally {
			pm.close();
		}

		return rta;
	}

}

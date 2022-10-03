/*
 * JDOVariablesOperativasDAO.java
 * Clase que se encarga del manejo (Creación, Consulta y Modificación) de todas
 * las variables operativas que son utilizadas en el sistema.
 * Creado  04 de noviembre de 2004, 9:10
 */

package gov.sir.hermod.dao.impl.jdogenie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gov.sir.hermod.HermodProperties;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.constantes.CCategoria;
import gov.sir.core.negocio.modelo.constantes.CEstadoAnotacion;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.core.negocio.modelo.constantes.CImpresion;

import org.auriga.core.modelo.transferObjects.Rol;

import com.versant.core.jdo.VersantPersistenceManager;
import com.versant.core.jdo.VersantQuery;

import gov.sir.core.negocio.modelo.AccionNotarial;
import gov.sir.core.negocio.modelo.AccionNotarialPk;
import gov.sir.core.negocio.modelo.AlcanceGeografico;
import gov.sir.core.negocio.modelo.AlcanceGeograficoPk;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.Categoria;
import gov.sir.core.negocio.modelo.CategoriaPk;
import gov.sir.core.negocio.modelo.CausalRestitucion;
import gov.sir.core.negocio.modelo.CausalRestitucionPk;
import gov.sir.core.negocio.modelo.CheckItem;
import gov.sir.core.negocio.modelo.CheckItemPk;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.OPLookupTypes;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.ProcesoPk;
import gov.sir.core.negocio.modelo.RolPantalla;
import gov.sir.core.negocio.modelo.SubtipoAtencion;
import gov.sir.core.negocio.modelo.SubtipoAtencionPk;
import gov.sir.core.negocio.modelo.SubtipoSolicitud;
import gov.sir.core.negocio.modelo.SubtipoSolicitudPk;
import gov.sir.core.negocio.modelo.SucursalBanco;
import gov.sir.core.negocio.modelo.Tarifa;
import gov.sir.core.negocio.modelo.TextoImprimible;
import gov.sir.core.negocio.modelo.TextoImprimiblePk;
import gov.sir.core.negocio.modelo.TipoActo;
import gov.sir.core.negocio.modelo.TipoActoPk;
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
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoDocumentoPk;
import gov.sir.core.negocio.modelo.TipoFotocopia;
import gov.sir.core.negocio.modelo.TipoFotocopiaPk;
import gov.sir.core.negocio.modelo.TipoImpuesto;
import gov.sir.core.negocio.modelo.TipoImpuestoPk;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.TipoNotaPk;
import gov.sir.core.negocio.modelo.TipoOficina;
/**
    * @author      :  Carlos Torres
    * @change      :  Obtener pantallas administrativas del usuario
    * @Caso Mantis :  14371: Acta - Requerimiento No 070_453_Rol_Registrador_Inhabilitar_Pantallas_Administrativas
    */
import gov.sir.core.negocio.modelo.TipoPantalla;
import gov.sir.core.negocio.modelo.TipoRecepcionPeticion;
import gov.sir.core.negocio.modelo.TipoRecepcionPeticionPk;
import gov.sir.core.negocio.modelo.TipoTarifa;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion;
import gov.sir.core.negocio.modelo.UsuarioSubtipoAtencionPk;
import gov.sir.core.negocio.modelo.Validacion;
import gov.sir.core.negocio.modelo.ValidacionNota;
import gov.sir.core.negocio.modelo.ValidacionNotaDetalle;
import gov.sir.core.negocio.modelo.ValidacionNotaPk;
import gov.sir.core.negocio.modelo.jdogenie.AccionNotarialEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.AccionNotarialEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.AlcanceGeograficoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.AlcanceGeograficoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.BancoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CategoriaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CategoriaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CategoriaNotariaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CausalRestitucionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CausalRestitucionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CheckItemDevEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.OPLookupTypesEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.OPLookupTypesEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.PantallaAdministrativaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.PantallaAdministrativaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.PermisoCorreccionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.RolPantallaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.RolPantallaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.RolSIREnhanced;
import gov.sir.core.negocio.modelo.jdogenie.RolSIREnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SubAtencionSubSolicitudEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionTipoActoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SubtipoSolicitudEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SubtipoSolicitudEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SucursalBancoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SucursalBancoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TarifaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TarifaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TextoImprimibleEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TextoImprimibleEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoCalculoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoCalculoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoValidacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoValidacionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoConsultaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoConsultaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoDerechoRegEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoDerechoRegEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoDocumentoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoDocumentoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoFotocopiaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoFotocopiaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoCirculoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoNotaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoNotaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoOficinaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoOficinaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoPantallaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoRecepcionPeticionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoRecepcionPeticionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoTarifaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoTarifaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioSubtipoAtencionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioSubtipoAtencionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ValidacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ValidacionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ValidacionNotaDetalleEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ValidacionNotaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ValidacionNotaEnhancedPk;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.VariablesOperativasDAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que se encarga del manejo y administración (Creación, Consulta y Modificación) de todas
 * las variables operativas que son utilizadas en el sistema.
 * @author  dlopez, mortiz
 */
public class JDOVariablesOperativasDAO implements VariablesOperativasDAO {

	/**
	* Crea una nueva instacia de JDORepartosDAO
	*/
	public JDOVariablesOperativasDAO() {
	}



	/**
	* Obtiene la lista de los tipos de alcances geográficos existentes en
	* la configuración del sistema.
	* <p>
	* El ordenamiento de los resultados se realiza alfabéticamente, utilizando
	* como criterio de ordenamiento el nombre del <code>AlcanceGeografico</code>
	* @return una lista de objetos <code>AlcanceGeografico</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.AlcanceGeografico
	*/
	public List getTiposAlcanceGeografico() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {

			//El ordenamiento de los resultados se realiza alfabéticamente, utilizando
			//como criterio de ordenamiento el nombre del AlcanceGeografico
			pm.currentTransaction().begin();
			Query q = pm.newQuery(AlcanceGeograficoEnhanced.class);
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			while (it.hasNext()) {
				AlcanceGeograficoEnhanced obj = (AlcanceGeograficoEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}
			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	* Adiciona un Tipo de <code>AlcanceGeografico</code> a la configuración del sistema.
	* <p>
	* Se genera una excepción en el caso en el que ya exista un <code>AlcanceGeografico</code>
	* con el identificador recibido como parámetro.
	* @param datos El objeto de tipo <code>AlcanceGeografico</code> con sus atributos que va
	* a ser insertado a la configuración del sistema.
	* @return identificador del <code>AlcanceGeografico</code> generado.
	* @see gov.sir.core.negocio.modelo.AlcanceGeografico
	* @throws DAOException
	*/
	public AlcanceGeograficoPk addAlcanceGeografico(AlcanceGeografico datos) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		AlcanceGeograficoEnhanced alcance = AlcanceGeograficoEnhanced.enhance(datos);

		try {

			//Validación del identificador del Alcance Geográfico.
			AlcanceGeograficoEnhancedPk alcanceId = new AlcanceGeograficoEnhancedPk();
			alcanceId.idAlcanceGeografico = alcance.getIdAlcanceGeografico();

			AlcanceGeograficoEnhanced valid = this.getAlcanceGeografico(alcanceId, pm);

			//Ya existe en la Base de Datos un Alcance Geográfico con el identificador del objeto
			//recibido como parámetro.
			if (valid != null) {
				throw new DAOException(
					"Ya existe un alcance geografico con el identificador: "
						+ alcanceId.idAlcanceGeografico);
			}

			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			pm.makePersistent(alcance);
			pm.currentTransaction().commit();

			AlcanceGeograficoEnhancedPk rta = (AlcanceGeograficoEnhancedPk) pm.getObjectId(alcance);

			return rta.getAlcanceGeograficoID();
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		  finally {
			pm.close();
		}
	}

	/**
	* Obtiene un objeto de tipo <code>AlcanceGeograficoEnhanced</code> dado su identificador.
	* <p>
	* Método usado en transacciones.
	* <p>
	* El método retorna null en el caso en el que no encuentre el alcance Geográfico con
	* el identificador recibido como parámetro.
	* @param oid Identificador del objeto <code>AlcanceGeográfico</code> que se quiere
	* recuperar.
	* @param Persistence manager de la transacción.
	* @return objeto <code>AlcanceGeograficoEnhanced</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.AlcanceGeografico
	* @throws DAOException
	*/
	protected AlcanceGeograficoEnhanced getAlcanceGeografico(
		AlcanceGeograficoEnhancedPk oid,
		PersistenceManager pm)
		throws DAOException {
		AlcanceGeograficoEnhanced rta = null;

		if (oid != null) {
			try {
				rta = (AlcanceGeograficoEnhanced) pm.getObjectById(oid, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene la lista de los tipos de <code>AccionNotarial</code> existentes en
	* la configuración del sistema.
	* <p>
	* El ordenamiento de los resultados se realiza alfabéticamente, utilizando
	* como criterio de ordenamiento el nombre de la <code>AccionNotarial</code>
	* @return una lista de objetos <code>AccionNotarial</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.AccionNotarial
	*/
	public List getTiposAccionNotarial() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {

			//Se realiza la consulta utilizando como criterio de ordenamiento
			//el nombre de la Accion Notarial.
			pm.currentTransaction().begin();
			Query q = pm.newQuery(AccionNotarialEnhanced.class);
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			while (it.hasNext()) {
				AccionNotarialEnhanced obj = (AccionNotarialEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	* Adiciona una <code>AccionNotarial</code> a la configuración del sistema.
	* <p>
	* Se genera una excepción en el caso en el que ya exista una <code>AccionNotarial</code>
	* con el identificador recibido como parámetro.
	* @param datos El objeto de tipo <code>AccionNotarial</code> con sus atributos que va
	* a ser agregado a la configuración del sistema.
	* @param usuario que va adicionar la accion notarial
	* @return identificador de la <code>AccionNotarial</code> generada.
	* @see gov.sir.core.negocio.modelo.AccionNotarial
	* @throws DAOException
	*/
	public AccionNotarialPk addAccionNotarial(AccionNotarial datos, Usuario usuario) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		AccionNotarialEnhanced accion = AccionNotarialEnhanced.enhance(datos);
		Connection connection = null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		VersantPersistenceManager jdoPM=null;
		try {

			//Validación de identificador de la Acción Notarial
			AccionNotarialEnhancedPk anId = new AccionNotarialEnhancedPk();
			
			// se toma el id de la accion notarial, 
			String consulta="SELECT MAX(TO_NUMBER(ID_ACCION_NOTARIAL)) FROM SIR_OP_ACCION_NOTARIAL";
			jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
			jdoPM.currentTransaction().begin();
			int idactualaccionnotarial=0;
			connection=jdoPM.getJdbcConnection(null);
			ps=connection.prepareStatement(consulta);
			rs=ps.executeQuery();
			while(rs.next())
			{
				idactualaccionnotarial=rs.getInt(1);
			}

			// se aumenta en 1 el id de la acción notaril
		    idactualaccionnotarial=idactualaccionnotarial+1;
		    jdoPM.currentTransaction().commit();
			//anId.idAccionNotarial = accion.getIdAccionNotarial();
			anId.idAccionNotarial=String.valueOf(idactualaccionnotarial);
		    AccionNotarialEnhanced valid = this.getAccionNotarial(anId, pm);
			
			//Si ya existe una Acción Notarial con el identificador del objeto recibido
			//como parámetro, se genera una excepción.
			if (valid != null) {
				throw new DAOException(
					"Ya existe una Accion Notarial con el identificador: " + anId.idAccionNotarial);
			}
			if(getAccionNotarialByNombre(datos.getNombre(),pm)!=null)
				throw new DAOException(
						"Ya existe una Accion Notarial con el nombre: " + datos.getNombre());
				
			// Validación del identificador del Usuario.
			UsuarioEnhancedPk usuarioId = new UsuarioEnhancedPk();
			usuarioId.idUsuario = usuario.getIdUsuario();

			UsuarioEnhanced usuarioEnh = getUsuarioByID(usuarioId, pm);
			accion.setUsuario(usuarioEnh);
			accion.setIdAccionNotarial(String.valueOf(idactualaccionnotarial));
			pm.currentTransaction().begin();
			pm.makePersistent(accion);
			pm.currentTransaction().commit();
			AccionNotarialEnhancedPk rta = (AccionNotarialEnhancedPk) pm.getObjectId(accion);

			return rta.getAccionNotarialID();
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			if(jdoPM.currentTransaction().isActive())
			{
		      jdoPM.currentTransaction().rollback();		
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            if(jdoPM.currentTransaction().isActive())
            {
              jdoPM.currentTransaction().rollback();  	
            }

            Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
            throw e;
        }
		catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			if(jdoPM.currentTransaction().isActive())
			{
			jdoPM.currentTransaction().rollback();	
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 finally {
			 try
			 {	 
			 if(rs!=null)
			 {
				 rs.close();
			 }
			 if(ps!=null)
			 {
				ps.close(); 
			 }
             if(connection != null){
                 connection.close();
             }
			 if(jdoPM!=null)
			 {
			   jdoPM.close();	 
			 }
			//pm.close();
			 }
			 catch(SQLException e)
			 {
				 Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
			     Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			     throw new DAOException(e.getMessage(), e);
			 }
			
		}
	}
	
	/**
	* Obtiene un accion notarial dado su nombre
	* @param nombreAccion
	 * @param pm  PersistenceManager de la trnsaccion
	* @return Ciudadano con sus atributos
	* @throws DAOException
	*/
	protected AccionNotarialEnhanced getAccionNotarialByNombre(String nombreAccion, PersistenceManager pm) throws DAOException {
		AccionNotarialEnhanced rta = null;
		try {
			Query query = pm.newQuery(AccionNotarialEnhanced.class);
			query.declareParameters("String nombreAccion");
			query.setFilter("this.nombre==nombreAccion");
			Collection col = (Collection)query.executeWithArray(new Object[]{nombreAccion});
			if (col.size() == 0) {
				rta = null;
			} else {
				for (Iterator iter = col.iterator(); iter.hasNext();) {
					rta = (AccionNotarialEnhanced) iter.next();
				}
				query.closeAll();
			}
		} catch (JDOObjectNotFoundException e) {
			rta = null;
		} catch (JDOException e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
		return rta;
	}
	
	/**
	* Editar una AccionNotarial en el sistema.
	* @param an La <code>AccionNotarial</code> que se va a editar.
	* @param usuario que va editar la accion notarial
	* @return true o false dependiendo del resultado de la actualización.
	* @see gov.sir.core.negocio.modelo.AccionNotarial
	* @throws <code>Throwable</code>
	*/
	public boolean editarAccionNotarial(AccionNotarial datos, Usuario usuario) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		boolean respuesta = false;

		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//Recuperar el objeto naturaleza juridica de entidad pública persistente.
			String idAccion = datos.getIdAccionNotarial();
			if (idAccion == null) {
				throw new DAOException("No se ingresó un identificador válido para el Negocio Jurídico");
			}

			AccionNotarialEnhancedPk idAccionEnh = new AccionNotarialEnhancedPk();
			idAccionEnh.idAccionNotarial = idAccion;

			AccionNotarialEnhanced accionEnhanced = new AccionNotarialEnhanced();
			accionEnhanced = (AccionNotarialEnhanced) pm.getObjectById(idAccionEnh, true);

			if (accionEnhanced == null) {
				throw new DAOException("No se encontró el Negocio Jurídico que se desea actualizar");
			}
			
//			Validación del identificador del Usuario.
			UsuarioEnhancedPk usuarioId = new UsuarioEnhancedPk();
			usuarioId.idUsuario = usuario.getIdUsuario();

			UsuarioEnhanced usuarioEnh = getUsuarioByID(usuarioId, pm);
			
			accionEnhanced.setUsuario(usuarioEnh);
			accionEnhanced.setCuantia(datos.isCuantia());
			accionEnhanced.setNombre(datos.getNombre());
			pm.currentTransaction().commit();
			respuesta = true;
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
				respuesta = false;
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable t) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
				respuesta = false;
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,t.getMessage());
			throw new DAOException(t.getMessage(), t);
		} finally {
			pm.close();
		}

		return respuesta;

	}



	/**
	* Obtiene un objeto de tipo <code>AccionNotarialEnhanced</code> dado su identificador.
	* <p>
	* Método usado en transacciones.
	* <p>
	* El método retorna null en el caso en el que no encuentre la Accion Notarial con
	* el identificador recibido como parámetro.
	* @param oid Identificador del objeto <code>AccionNotarial</code> que se quiere
	* recuperar.
	* @param Persistence manager de la transacción.
	* @return objeto <code>AccionNotarialEnhanced</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.AccionNotarial
	* @throws DAOException
	*/
	protected AccionNotarialEnhanced getAccionNotarial(
		AccionNotarialEnhancedPk oid,
		PersistenceManager pm)
		throws DAOException {
		AccionNotarialEnhanced rta = null;

		if (oid.idAccionNotarial != null) {
			try {
				rta = (AccionNotarialEnhanced) pm.getObjectById(oid, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}



	/**
	* Obtiene un <code>CirculoEnhanced</code> dado su identificador.
	* <p> Método utilizado para transacciones.
	* <p> El método retorna <code>null</code> si no se encuentra el <code>Circulo</code>
	* con el identificador dado.
	* @param prID identificador del <code>Circulo</code> que se quiere recuperar.
	* @param pm PersistenceManager de la transaccion
	* @return <code>CirculoEnhanced</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.CirculoEnhanced
	* @throws DAOException
	*/
	protected CirculoEnhanced getCirculoById(CirculoEnhancedPk cID, PersistenceManager pm)
		throws DAOException {
		CirculoEnhanced rta = null;

		if (cID.idCirculo != null) {
			try {
				rta = (CirculoEnhanced) pm.getObjectById(cID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
				Log.getInstance().debug(JDOVariablesOperativasDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene una la lista de objetos <code>TipoFotocopia</code> existentes en
	* la configuración del sistema.
	* <p>
	* El ordenamiento de los resultados se realiza alfabéticamente, utilizando
	* como criterio de ordenamiento el nombre del <code>TipoFotocopia</code>
	* @return una lista de objetos <code>TipoFotocopia</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.TipoFotocopia
	*/
	public List getTiposFotocopia() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			pm.currentTransaction().begin();
			Query q = pm.newQuery(TipoFotocopiaEnhanced.class);

			//Se establece como criterio de ordenamiento el nombre del tipo de fotocopia
			//(Alfabéticamente)
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			//Se hacen transientes cada uno de los tipos de fotocopias.
			while (it.hasNext()) {
				TipoFotocopiaEnhanced obj = (TipoFotocopiaEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	
	
	
	/**
	* Obtiene una la lista de objetos <code>CategoriasNotaria</code> existentes en
	* la configuración del sistema.
	* <p>
	* El ordenamiento de los resultados se realiza alfabéticamente, utilizando
	* como criterio de ordenamiento el nombre de la <code>CategoriaNotaria</code>
	* @return una lista de objetos <code>CategoriaNotaria</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.CategoriaNotaria
	*/
	public List getCategoriasNotarias() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			pm.currentTransaction().begin();
			Query q = pm.newQuery(CategoriaNotariaEnhanced.class);

			//Se establece como criterio de ordenamiento el nombre del tipo de fotocopia
			//(Alfabéticamente)
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			//Se hacen transientes cada una de las Categorías de Notarías.
			while (it.hasNext()) {
				CategoriaNotariaEnhanced obj = (CategoriaNotariaEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	
	
	
	
	/**
	* Retorna un booleano que indica si el usuario con el login dado,
	* tiene asociado un subtipo de atención.
	* @param subtipo Subtipo de atención.
	* @param login  Login del usuario.
	* @param pm Persistence Manager de la transacción.
	* @return un booleano que indica si el <code>Usuario</code> con el login dado,
	* tiene asociado el <code>SubtipoAtencion</code> pasado como parámetro.
	* @see gov.sir.core.negocio.modelo.SubtipoAtencion
	* @see gov.sir.core.negocio.modelo.Usuario
	* @throws DAOException
	*/
	protected boolean isUsuarioInSubtipoAtencion(String subtipo, long login, PersistenceManager pm)
		throws DAOException {

		Long Username = new Long(login);
		String SubtipoId = subtipo;
		boolean respuesta;

		try {

			//Se establecen como filtros de la consulta, el identificador del usuario
			//y el Subtipo de Atención.
			Query query = pm.newQuery(UsuarioSubtipoAtencionEnhanced.class);
			query.declareParameters("long Username, String SubtipoId");
			query.setFilter("idUsuario == Username && idSubtipoAtencion ==SubtipoId ");
			Collection col = (Collection) query.execute(Username, SubtipoId);

			if (col.size() == 0) {
				respuesta = false;
			} else {
				respuesta = true;
			}

			query.closeAll();
		} catch (JDOObjectNotFoundException e) {
			respuesta = false;
		} catch (JDOException e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}

		return respuesta;
	}

	/**
	* Obtiene una la lista de objetos <code>TipoCertificado</code> existentes en
	* la configuración del sistema.
	* <p>
	* El ordenamiento de los resultados se realiza alfabéticamente, utilizando
	* como criterio de ordenamiento el nombre del <code>TipoCertificado</code>
	* @return una lista de objetos <code>TipoCertificado</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.TipoCertificado
	*/
	public List getTiposCertificado() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			pm.currentTransaction().begin();
			Query q = pm.newQuery(TipoCertificadoEnhanced.class);

			//Se establece como criterio de ordenamiento el nombre del tipo de certificado
			//(Alfabéticamente)
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			//Se hacen transientes cada uno de los objetos de la lista de tipos de certificados.
			while (it.hasNext()) {
				TipoCertificadoEnhanced obj = (TipoCertificadoEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}
			pm.currentTransaction().commit();
			q.close(results);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		lista = TransferUtils.makeTransferAll(lista);
		return lista;

	}



	/**
	* Obtiene una la lista de objetos <code>TipoCertificado</code> existentes en
	* la configuración del sistema, y que estén asociados con el proceso de
	* certificados individuales.
	* <p>
	* El ordenamiento de los resultados se realiza alfabéticamente, utilizando
	* como criterio de ordenamiento el nombre del <code>TipoCertificado</code>
	* @return una lista de objetos <code>TipoCertificado</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.TipoCertificado
	*/
	public List getTiposCertificadosIndividuales() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();
		boolean individual = true;

		try {
			pm.currentTransaction().begin();
			Query q = pm.newQuery(TipoCertificadoEnhanced.class);

			//Se establece como criterio de ordenamiento el nombre del tipo de certificado
			//(Alfabéticamente)
			q.setOrdering("nombre ascending");
			q.setFilter("flagIndividual == true ");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			//Se hacen transientes cada uno de los objetos de la lista de tipos de certificados.
			while (it.hasNext()) {
				TipoCertificadoEnhanced obj = (TipoCertificadoEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}
			pm.currentTransaction().commit();
			q.close(results);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		lista = TransferUtils.makeTransferAll(lista);
		return lista;

	}

	/**
	* Obtiene una la lista de objetos <code>TipoConsulta</code> existentes en
	* la configuración del sistema.
	* <p>
	* El ordenamiento de los resultados se realiza alfabéticamente, utilizando
	* como criterio de ordenamiento el nombre del <code>TipoConsulta</code>
	* @return una lista de objetos <code>TipoConsulta</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.TipoConsulta
	*/
	public List getTiposConsulta() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			Query q = pm.newQuery(TipoConsultaEnhanced.class);
			//Se establece como criterio de ordenamiento el nombre del tipo de consulta
			//(Alfabéticamente)
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			//	Se hacen transientes cada uno de los objetos de la lista de tipos de consultas.
			while (it.hasNext()) {
				TipoConsultaEnhanced obj = (TipoConsultaEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	* Obtiene una la lista de objetos <code>SubtipoAtencion</code> existentes en
	* la configuración del sistema.
	* <p>
	* El ordenamiento de los resultados se realiza alfabéticamente, utilizando
	* como criterio de ordenamiento el nombre del <code>SubtipoAtencion</code>
	* @return una lista de objetos <code>SubtipoAtencion</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.SubtipoAtencion
	*/
	public List getSubTiposAtencion() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			pm.currentTransaction().begin();
			Query q = pm.newQuery(SubtipoAtencionEnhanced.class);
			//Se establece como criterio de ordenamiento el nombre del subtipo de atención
			//(Alfabéticamente)
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			//Se hacen transientes cada uno de los objetos de la lista de subtipos de atencion.
			while (it.hasNext()) {
				SubtipoAtencionEnhanced obj = (SubtipoAtencionEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	* Obtiene una la lista de objetos <code>SubtipoAtencion</code> (los cuales incluyen subtipos de solicitud
	* y tipos de acto) existentes en la configuración del sistema.
	* <p>
	* El ordenamiento de los resultados se realiza alfabéticamente, utilizando
	* como criterio de ordenamiento el nombre del <code>SubtipoAtencion</code>
	* @return una lista de objetos <code>SubtipoAtencion</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.SubtipoAtencion
	*/
	public List getSubTiposAtencionCompleto
	() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			pm.currentTransaction().begin();
			Query q = pm.newQuery(SubtipoAtencionEnhanced.class);
			//Se establece como criterio de ordenamiento el nombre del subtipo de atención
			//(Alfabéticamente)
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			//Se hacen transientes cada uno de los objetos de la lista de subtipos de atencion.
			while (it.hasNext()) {
				SubtipoAtencionEnhanced obj = (SubtipoAtencionEnhanced) it.next();

				List subtipoSolicitudes = obj.getSubtipoSolicitudes();
				//se vuelven trasientes los subtipos de solicitud
				for(Iterator itSubSol = subtipoSolicitudes.iterator(); itSubSol.hasNext();){
					SubAtencionSubSolicitudEnhanced subtipoAtenSol = (SubAtencionSubSolicitudEnhanced)itSubSol.next();
					pm.makeTransient(subtipoAtenSol.getSubtipoSolicitud());
					pm.makeTransient(subtipoAtenSol);
				}

				List tipoActos = obj.getTiposActos();
				//se vuelven trasientes los tipos de acto
				for(Iterator itTipoActo = tipoActos.iterator(); itTipoActo.hasNext();){
					SubtipoAtencionTipoActoEnhanced subAtencTipoActo = (SubtipoAtencionTipoActoEnhanced)itTipoActo.next();
					pm.makeTransient(subAtencTipoActo.getTipoActo());
					pm.makeTransient(subAtencTipoActo);
				}

				pm.makeTransient(obj);
				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	* Obtiene una la lista de objetos <code>Usuario</code> .
	* @return una lista de objetos <code>Usuario</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.Usuario
	*/
	public List getCalificadoresSubtipoAtencion(Circulo cir, SubtipoAtencion sub) throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			pm.currentTransaction().begin();
			Query q = pm.newQuery(UsuarioSubtipoAtencionEnhanced.class);
			q.declareParameters("String SubtipoId, String idCir");
			q.declareVariables("UsuarioCirculoEnhanced usuCir");
			q.setFilter("idSubtipoAtencion == SubtipoId && " +
					"usuario.usuarioCirculos.contains(usuCir) &&" +
					"usuCir.idCirculo==idCir");
			q.setOrdering("orden ascending");

			Collection results = (Collection) q.execute(sub.getIdSubtipoAtencion(), cir.getIdCirculo());
			Iterator it = results.iterator();

			//añadir los usuarios
			while (it.hasNext()) {
				UsuarioSubtipoAtencionEnhanced obj = (UsuarioSubtipoAtencionEnhanced) it.next();
				pm.makeTransient(obj.getUsuario());
				pm.makeTransient(obj);

				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	* Agrega el orden de un usuario en el subtipo solicitud
	* @return true o false dependiendo del resultado de la adicion.
	* @see gov.sir.core.negocio.modelo.Usuario
	* @see gov.sir.core.negocio.modelo.SubtipoAtencion
	* @throws DAOException
	*/
	public UsuarioSubtipoAtencionPk addOrdenSubtipoAtencion(SubtipoAtencion sub, Usuario usu,  String orden, Circulo cir) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		UsuarioSubtipoAtencionEnhanced usuSubtipoAtencion = new UsuarioSubtipoAtencionEnhanced();

		try {

			//Se mira si existe el subtipo de atencion
			SubtipoAtencionEnhancedPk subtipoId = new SubtipoAtencionEnhancedPk();
			subtipoId.idSubtipoAtencion = sub.getIdSubtipoAtencion();
			SubtipoAtencionEnhanced subtipoBD = this.getSubtipoAtencionByID(subtipoId, pm);

			// Si no existe un ubtipo de atencion con el identificador del objeto
			// pasado como parámetro, se genera una excepción.
			if (subtipoBD == null) {
				throw new DAOException(
					"No existe un Subtipo de atencion con el identificador: " + sub.getIdSubtipoAtencion());
			}

			//Se mira si existe el usuario
			UsuarioEnhancedPk usuarioId = new UsuarioEnhancedPk();
			usuarioId.idUsuario = usu.getIdUsuario();
			UsuarioEnhanced usuarioBD = this.getUsuarioByID(usuarioId, pm);

			// Si no existe un ubtipo de atencion con el identificador del objeto
			// pasado como parámetro, se genera una excepción.
			if (usuarioBD == null) {
				throw new DAOException(
					"No existe un Usuario con el identificador: " + usu.getIdUsuario());
			}

			//se colocan valores al objeto usuSubtipoAtencion
			usuSubtipoAtencion.setFechaCreacion(new Date());
			usuSubtipoAtencion.setIdSubtipoAtencion(sub.getIdSubtipoAtencion());
			usuSubtipoAtencion.setIdUsuario(usu.getIdUsuario());
			usuSubtipoAtencion.setOrden(Long.parseLong(orden));
			usuSubtipoAtencion.setSubtipoatencion(subtipoBD);
			usuSubtipoAtencion.setUsuario(usuarioBD);


			pm.currentTransaction().begin();
			//Mirar los otros subtipos de atencion para organizar la lista
			Query q = pm.newQuery(UsuarioSubtipoAtencionEnhanced.class);
			q.declareParameters("String SubtipoId, String idCir, Long nOrden");
			q.declareVariables("UsuarioCirculoEnhanced usuCir");
			q.setFilter("idSubtipoAtencion == SubtipoId && " +
					"orden >= nOrden && " +
					"usuario.usuarioCirculos.contains(usuCir) &&" +
					"usuCir.idCirculo==idCir");
			q.setOrdering("orden ascending");

			Collection results = (Collection) q.execute(sub.getIdSubtipoAtencion(), cir.getIdCirculo(), new Long(orden));
			Iterator it = results.iterator();

			//añadir los usuarios
			while (it.hasNext()) {
				UsuarioSubtipoAtencionEnhanced obj = (UsuarioSubtipoAtencionEnhanced) it.next();
				obj.setOrden(obj.getOrden()+1);
			}


			//Hacer persistente y hacer commit

			pm.makePersistent(usuSubtipoAtencion);
			pm.currentTransaction().commit();
			UsuarioSubtipoAtencionEnhancedPk rtaEnhanced = (UsuarioSubtipoAtencionEnhancedPk) pm.getObjectId(usuSubtipoAtencion);

			UsuarioSubtipoAtencionPk rta = new UsuarioSubtipoAtencionPk();
			rta.idSubtipoAtencion =rtaEnhanced.idSubtipoAtencion;
			rta.idUsuario =rtaEnhanced.idUsuario;
			return rta;

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		  finally {
			pm.close();
		}
	}

	/**
	* Agrega el orden de un usuario en el subtipo solicitud
	* @return true o false dependiendo del resultado de la adicion.
	* @see gov.sir.core.negocio.modelo.Usuario
	* @see gov.sir.core.negocio.modelo.SubtipoAtencion
	* @throws DAOException
	*/
	public boolean removeOrdenSubtipoAtencion(UsuarioSubtipoAtencion usuSubtipoAtencion, Circulo cir) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();


		try {
			pm.currentTransaction().begin();

			//Se mira si existe el usuario subtipo de atencion
			UsuarioSubtipoAtencionEnhancedPk usuSubtipoId = new UsuarioSubtipoAtencionEnhancedPk();
			usuSubtipoId.idSubtipoAtencion = usuSubtipoAtencion.getIdSubtipoAtencion();
			usuSubtipoId.idUsuario = usuSubtipoAtencion.getIdUsuario();
			UsuarioSubtipoAtencionEnhanced usuarioSubtipoBD = this.getUsuarioSubtipoAtencionByID(usuSubtipoId, pm);

			// Si no existe un ubtipo de atencion con el identificador del objeto
			// pasado como parámetro, se genera una excepción.
			if (usuarioSubtipoBD == null) {
				throw new DAOException(
					"No existe un Subtipo de atencion con el identificador: " + usuSubtipoAtencion.getIdSubtipoAtencion());
			}


			//se elimina el objeto de la BD
			pm.deletePersistent(usuarioSubtipoBD);



			//Mirar los otros subtipos de atencion para organizar la lista
			Query q = pm.newQuery(UsuarioSubtipoAtencionEnhanced.class);
			q.setIgnoreCache(true);
			q.declareParameters("String SubtipoId, String idCir, Long nOrden");
			q.declareVariables("UsuarioCirculoEnhanced usuCir");
			q.setFilter("idSubtipoAtencion == SubtipoId && " +
					"orden > nOrden && " +
					"usuario.usuarioCirculos.contains(usuCir) &&" +
					"usuCir.idCirculo==idCir");
			q.setOrdering("orden ascending");

			Collection results = (Collection) q.execute(usuSubtipoAtencion.getIdSubtipoAtencion(), cir.getIdCirculo(), new Long(usuSubtipoAtencion.getOrden()));
			Iterator it = results.iterator();

			//Modificar el orden para que cuadre
			while (it.hasNext()) {
				UsuarioSubtipoAtencionEnhanced obj = (UsuarioSubtipoAtencionEnhanced) it.next();
				obj.setOrden(obj.getOrden()-1);
			}

			pm.currentTransaction().commit();
			UsuarioSubtipoAtencionEnhanced rta = this.getUsuarioSubtipoAtencionByID(usuSubtipoId, pm);
			if(rta==null){
				return true;
			}else{
				return false;
			}


		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		 finally {
			pm.close();
		}
	}

	/**
	* Obtiene una la lista de objetos <code>TipoActo</code> existentes en
	* la configuración del sistema.
	* <p>
	* El ordenamiento de los resultados se realiza alfabéticamente, utilizando
	* como criterio de ordenamiento el nombre del <code>TipoActo</code>
	* @return una lista de objetos <code>TipoActo</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.TipoActo
	*/
	public List getTiposActo() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			pm.currentTransaction().begin();
			Query q = pm.newQuery(TipoActoEnhanced.class);

			//Se establece como criterio de ordenamiento el nombre del tipo de acto
			//(Alfabéticamente)
                        
                        /*
                        * @autor          : JATENCIA
                        * @mantis        : 0015082 
                        * @Requerimiento : 027_589_Acto_liquidación_copias 
                        * @descripcion   : Se establece el criterio de filtro para que no se visualice el tipo de acto que
                        * se encuentra inactivo. 
                        */
                        q.setFilter("activo == true");
                        /* - Fin del bloque - */
                        
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			while (it.hasNext()) {
				TipoActoEnhanced obj = (TipoActoEnhanced) it.next();

				//Hacer transientes los elementos de la lista de derechos registrales.
				List listaDerechos = obj.getTiposDerechosRegistrales();

				for (int k = 0; k < obj.getTiposDerechosRegistrales().size(); k++) {
					TipoActoDerechoRegistralEnhanced tadr =
						(TipoActoDerechoRegistralEnhanced) listaDerechos.get(k);
					pm.makeTransient(tadr.getTipoDerechoRegistral());
					pm.makeTransient(tadr);
				}

				pm.makeTransient(obj.getTipoCalculo());
				pm.makeTransient(obj);
				lista.add(obj);

			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}
	
	
	/**
	* Obtiene un objeto <code>TipoActo</code> a partir del idTipoActo Solicitado
	* @return Objeto <code>TipoActo</code>
	* @param TipoActo.ID tid
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.TipoActo
	*/	
	public TipoActo getTipoActo(TipoActoPk tid) throws DAOException {

		TipoActo rta = null;
		PersistenceManager pm = AdministradorPM.getPM();
		TipoActoEnhancedPk tidEnh = new TipoActoEnhancedPk(tid);
		TipoActoEnhanced tr = null;
		try {
			tr = this.getTipoActoByID(tidEnh, pm);

			if (tr == null) {
				throw new DAOException("No encontró el TipoActo con el ID: " + tid.idTipoActo);
			}

			List listaDerechos = tr.getTiposDerechosRegistrales();

			for (int k = 0; k < tr.getTiposDerechosRegistrales().size(); k++) {
				TipoActoDerechoRegistralEnhanced tadr =
					(TipoActoDerechoRegistralEnhanced) listaDerechos.get(k);
				pm.makeTransient(tadr.getTipoDerechoRegistral());
				pm.makeTransient(tadr);
			}

			pm.makeTransient(tr.getTipoCalculo());			
			pm.makeTransient(tr);

			
		} catch (JDOObjectNotFoundException e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally{
			pm.close();
		}
		if (tr!=null){
			rta = (TipoActo)tr.toTransferObject();
		}

		return rta;
	}	

        /*
        * @autor          : JATENCIA
        * @mantis        : 0015082 
        * @Requerimiento : 027_589_Acto_liquidación_copias 
        * @descripcion   : Se crea una copia de los metodos y se le quita la 
        * opción de filtro.
        */
        
        
	/**
	* Obtiene una la lista de objetos <code>TipoActo</code> existentes en
	* la configuración del sistema.
	* <p>
	* El ordenamiento de los resultados se realiza alfabéticamente, utilizando
	* como criterio de ordenamiento el nombre del <code>TipoActo</code>
	* @return una lista de objetos <code>TipoActo</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.TipoActo
	*/
	public List getTiposActoDos() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			pm.currentTransaction().begin();
			Query q = pm.newQuery(TipoActoEnhanced.class);

			//Se establece como criterio de ordenamiento el nombre del tipo de acto
			//(Alfabéticamente)
                                                
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			while (it.hasNext()) {
				TipoActoEnhanced obj = (TipoActoEnhanced) it.next();

				//Hacer transientes los elementos de la lista de derechos registrales.
				List listaDerechos = obj.getTiposDerechosRegistrales();

				for (int k = 0; k < obj.getTiposDerechosRegistrales().size(); k++) {
					TipoActoDerechoRegistralEnhanced tadr =
						(TipoActoDerechoRegistralEnhanced) listaDerechos.get(k);
					pm.makeTransient(tadr.getTipoDerechoRegistral());
					pm.makeTransient(tadr);
				}

				pm.makeTransient(obj.getTipoCalculo());
				pm.makeTransient(obj); 
				lista.add(obj);

			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}
	
	
	/**
	* Obtiene un objeto <code>TipoActo</code> a partir del idTipoActo Solicitado
	* @return Objeto <code>TipoActo</code>
	* @param TipoActo.ID tid
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.TipoActo
	*/	
	public TipoActo getTipoActoDos(TipoActoPk tid) throws DAOException {

		TipoActo rta = null;
		PersistenceManager pm = AdministradorPM.getPM();
		TipoActoEnhancedPk tidEnh = new TipoActoEnhancedPk(tid);
		TipoActoEnhanced tr = null;
		try {
			tr = this.getTipoActoByID(tidEnh, pm);

			if (tr == null) {
				throw new DAOException("No encontró el TipoActo con el ID: " + tid.idTipoActo);
			}

			List listaDerechos = tr.getTiposDerechosRegistrales();

			for (int k = 0; k < tr.getTiposDerechosRegistrales().size(); k++) {
				TipoActoDerechoRegistralEnhanced tadr =
					(TipoActoDerechoRegistralEnhanced) listaDerechos.get(k);
				pm.makeTransient(tadr.getTipoDerechoRegistral());
				pm.makeTransient(tadr);
			}

			pm.makeTransient(tr.getTipoCalculo());			
			pm.makeTransient(tr);

			
		} catch (JDOObjectNotFoundException e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally{
			pm.close();
		}
		if (tr!=null){
			rta = (TipoActo)tr.toTransferObject();
		}

		return rta;
	}
	
        /* - Fin del bloque - */
        
	/**
	* Obtiene una la lista de objetos <code>SubtipoSolicitud</code> existentes en
	* la configuración del sistema.
	* <p>
	* El ordenamiento de los resultados se realiza alfabéticamente, utilizando
	* como criterio de ordenamiento el nombre del <code>SubtipoSolicitud</code>
	* @return una lista de objetos <code>SubtipoSolicitud</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.SubtipoSolicitud
	*/
	public List getSubTiposSolicitud() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {

			//Se establece como criterio de ordenamiento el nombre del subtipo
			//de solicitud.(Alfabéticamente)
			pm.currentTransaction().begin();
			Query q = pm.newQuery(SubtipoSolicitudEnhanced.class);
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			while (it.hasNext()) {
				SubtipoSolicitudEnhanced obj = (SubtipoSolicitudEnhanced) it.next();
				//Hacer transientes los elementos de la lista.
				List listaItems = obj.getCheckItems();
				for (int k = 0; k < obj.getCheckItems().size(); k++) {
					CheckItemEnhanced check = (CheckItemEnhanced) listaItems.get(k);
					pm.makeTransient(check);
				}
				pm.makeTransient(obj);
				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	* Obtiene una la lista de objetos <code>TipoImpuesto</code> existentes en
	* la configuración del sistema.
	* <p>
	* El ordenamiento de los resultados se realiza alfabéticamente, utilizando
	* como criterio de ordenamiento el nombre del <code>TipoImpuesto</code>
	* @return una lista de objetos <code>TipoImpuesto</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.TipoImpuesto
	*/
	public List getTiposImpuesto() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {

			//Se establece como criterio de ordenamiento el nombre del
			//Tipo de Impuesto(Alfabéticamente)
			pm.currentTransaction().begin();
			Query q = pm.newQuery(TipoImpuestoEnhanced.class);
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			//Se hacen transientes los elementos de la lista.
			while (it.hasNext()) {
				TipoImpuestoEnhanced obj = (TipoImpuestoEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

        /**
	* Obtiene una la lista de objetos <code>TipoImpuestoCirculo</code> existentes en
	* la configuración del sistema.
	* <p>
	* El ordenamiento de los resultados se realiza alfabéticamente, utilizando
	* como criterio de ordenamiento el nombre del <code>TipoImpuestoCirculo</code>
        * @author Fernando Padilla Velez
        * @change Modificado para el caso MANTIS 135_141_Impuesto Meta
	* @return una lista de objetos <code>TipoImpuestoCirculo</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.TipoImpuestoCirculo
	*/
	public List getTiposImpuestoCirculo() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {

			pm.currentTransaction().begin();
			Query q = pm.newQuery(TipoImpuestoCirculoEnhanced.class);
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			//Se hacen transientes los elementos de la lista.
			while (it.hasNext()) {
				TipoImpuestoCirculoEnhanced obj = (TipoImpuestoCirculoEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	* Obtiene una la lista de objetos <code>TipoRecepcionPeticion</code> existentes en
	* la configuración del sistema.
	* <p>
	* El ordenamiento de los resultados se realiza alfabéticamente, utilizando
	* como criterio de ordenamiento el nombre del <code>TipoRecepcionPeticion</code>
	* @return una lista de objetos <code>TipoRecepcionPeticion</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.TipoRecepcionPeticion
	*/
	public List getTiposRecepcionPeticion() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {

			//Se establece como criterio de ordenamiento el nombre del
			//Tipo de Recepción Petición (Alfabéticamente)
			pm.currentTransaction().begin();
			Query q = pm.newQuery(TipoRecepcionPeticionEnhanced.class);
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			//Se hacen transientes los elementos de la lista.
			while (it.hasNext()) {
				TipoRecepcionPeticionEnhanced obj = (TipoRecepcionPeticionEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	* Adiciona un objeto de tipo <code>TipoFotocopia</code> a la configuración del sistema.
	* <p>
	* El método genera una excepción si ya existe un tipo de fotocopia con el identificador
	* del objeto pasado como parámetro.
	* @param fot objeto de tipo <code>TipoFotocopia</code> con todos sus atributos.
	* @return identificador del objeto <code>TipoFotocopia</code> adicionado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoFotocopia
	*
	*/
	public TipoFotocopiaPk addTipoFotocopia(TipoFotocopia fot) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		TipoFotocopiaEnhanced tipoFot = TipoFotocopiaEnhanced.enhance(fot);

		try {
			//Validación del identificador del tipo de fotocopia
			TipoFotocopiaEnhancedPk fotId = new TipoFotocopiaEnhancedPk();
			fotId.idTipoFotocopia = tipoFot.getIdTipoFotocopia();
			TipoFotocopiaEnhanced valid = this.getTipoFotocopiaById(fotId, pm);

			if (valid != null) {
				throw new DAOException(
					"Ya existe un Tipo de Fotocopia con el identificador: " + fotId.idTipoFotocopia);
			}
			
			//Se settea la fecha de creacion.
			tipoFot.setFechaCreacion(new Date());
			
			pm.currentTransaction().begin();
			pm.makePersistent(tipoFot);
			pm.currentTransaction().commit();
			TipoFotocopiaEnhancedPk rta = (TipoFotocopiaEnhancedPk) pm.getObjectId(tipoFot);

			return rta.getTipoFotocopiaID();

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		 finally {
			pm.close();
		}
	}

	/**
	 * Obtiene un objeto <code>TipoFotocopia</code> dado su identificador.
	 * <p>Método utilizado para transacciones.
	 * <p>Si no existe un objeto con el identificador dado, el método retorna
	 * null.
	 * @param fotID identificador del tipo de fotocopia.
	 * @param pm PersistenceManager de la transacción
	 * @return objeto <code>TipoFotocopia<code> con sus atributos
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.TipoFotocopia
	 */
	protected TipoFotocopiaEnhanced getTipoFotocopiaById(
		TipoFotocopiaEnhancedPk fotID,
		PersistenceManager pm)
		throws DAOException {
		TipoFotocopiaEnhanced rta = null;

		if (fotID.idTipoFotocopia != null) {
			try {
				rta = (TipoFotocopiaEnhanced) pm.getObjectById(fotID, true);
			}

			//No se encontró el objeto con el identificador dado.  Se retorna null.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
				Log.getInstance().debug(JDOVariablesOperativasDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Adiciona un objeto de tipo <code>TipoDocumento</code> a la configuración del sistema.
	* <p>
	* El método genera una excepción si ya existe un tipo de documento con el identificador
	* del objeto pasado como parámetro.
	* @param tdoc objeto de tipo <code>TipoDocumento</code> con todos sus atributos.
	* @return identificador del objeto <code>TipoDocumento</code> adicionado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoDocumento
	*/
	public TipoDocumentoPk addTipoDocumento(TipoDocumento tdoc) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		TipoDocumentoEnhanced tipoDoc = TipoDocumentoEnhanced.enhance(tdoc);

		try {

			//Validación del identificador del tipo de documento.
			TipoDocumentoEnhancedPk docId = new TipoDocumentoEnhancedPk();
			docId.idTipoDocumento = tipoDoc.getIdTipoDocumento();
			TipoDocumentoEnhanced valid = this.getTipoDocumentoById(docId, pm);
			if (valid != null) {
				throw new DAOException(
					"Ya existe un Tipo de documento con el identificador: " + docId.idTipoDocumento);
			}

			pm.currentTransaction().begin();
			pm.makePersistent(tipoDoc);
			pm.currentTransaction().commit();
			TipoDocumentoEnhancedPk rta = (TipoDocumentoEnhancedPk) pm.getObjectId(tipoDoc);

			return rta.getTipoDocumentoID();
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		 finally {
			pm.close();
		}
	}

	/**
	 * Obtiene un objeto <code>TipoDocumento</code> dado su identificador.
	 * <p>Método utilizado para transacciones.
	 * <p>Si no existe un objeto con el identificador dado, el método retorna
	 * null.
	 * @param docID identificador del tipo de documento.
	 * @param pm PersistenceManager de la transacción
	 * @return objeto <code>TipoDocumento<code> con sus atributos
	 * <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.TipoDocumento
	 */
	protected TipoDocumentoEnhanced getTipoDocumentoById(
		TipoDocumentoEnhancedPk docID,
		PersistenceManager pm)
		throws DAOException {
		TipoDocumentoEnhanced rta = null;

		if (docID.idTipoDocumento != null) {
			try {
				rta = (TipoDocumentoEnhanced) pm.getObjectById(docID, true);
			}

			//No se encontró el objeto con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
				Log.getInstance().debug(JDOVariablesOperativasDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene una la lista de objetos <code>CausalRestitucion</code> existentes en
	* la configuración del sistema.
	* <p>
	* El ordenamiento de los resultados se realiza alfabéticamente, utilizando
	* como criterio de ordenamiento el nombre del <code>CausalRestitucion</code>
	* @return una lista de objetos <code>CausalRestitucion</code>
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.CausalRestitucion
	*/
	public List getCausalesRestitucion() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {

			//Se realiza la búsqueda de los causales de restitución.
			//Se establece como criterio de ordenamiento el nombre del causal de restitución.
			//(Alfabéticamente).
			pm.currentTransaction().begin();
			Query q = pm.newQuery(CausalRestitucionEnhanced.class);
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			//Se hacen transientes cada uno de los objetos de la lista de causales de restitución.
			while (it.hasNext()) {
				CausalRestitucionEnhanced obj = (CausalRestitucionEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	* Adiciona un objeto de tipo <code>CausalRestitucion</code> a la configuración del sistema.
	* <p>
	* El método genera una excepción si ya existe un causal de restitución con el identificador
	* del objeto pasado como parámetro.
	* @param causal objeto de tipo <code>CausalRestitucion</code> con todos sus atributos.
	* @return identificador del objeto <code>CausalRestitucion</code> adicionado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.CausalRestitucion
	*/
	public CausalRestitucionPk addCausalRestitucion(CausalRestitucion causal) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		CausalRestitucionEnhanced ca = CausalRestitucionEnhanced.enhance(causal);
		Connection connection = null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		VersantPersistenceManager jdoPM=null;

		try {

			//Se intenta insertar el causal de restitución.
			CausalRestitucionEnhancedPk causalId = new CausalRestitucionEnhancedPk();
			
			//Se realiza una consulta para sacar el id del causal de restitución
			String consulta="SELECT MAX(TO_NUMBER(ID_CAUSAL_RESTITUCION)) FROM SIR_OP_CAUSAL_RESTITUCION";
			jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
			jdoPM.currentTransaction().begin();
			int idactualrestitucionnotarial=0;
			connection=jdoPM.getJdbcConnection(null);
			ps=connection.prepareStatement(consulta);
			rs=ps.executeQuery();
			while(rs.next())
			{
				idactualrestitucionnotarial=rs.getInt(1);
			}
			idactualrestitucionnotarial=idactualrestitucionnotarial+1;
			jdoPM.currentTransaction().commit();
			//hasta aca la consulta para sacar el id de la restitucion de reparto notarial
		     
			causalId.idCausalRestitucion=String.valueOf(idactualrestitucionnotarial);
			//causalId.idCausalRestitucion = ca.getIdCausalRestitucion();
			CausalRestitucionEnhanced causalr = this.getCausalRestitucionById(causalId, pm);

			//Si ya existe un causal de restitución con el identificador del objeto
			// pasado como parámetro, se genera una excepción.
			if (causalr != null) {
				throw new DAOException(
					"Ya existe una Causal  con el identificador: " + causalId.idCausalRestitucion);
			}

			pm.currentTransaction().begin();
			ca.setIdCausalRestitucion(String.valueOf(idactualrestitucionnotarial));
			ca.setActivo(true);
			pm.makePersistent(ca);
			pm.currentTransaction().commit();
			CausalRestitucionEnhancedPk rta = (CausalRestitucionEnhancedPk) pm.getObjectId(ca);

			return rta.getCausalRestitucionID();

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			if(jdoPM.currentTransaction().isActive())
			{
		       jdoPM.currentTransaction().rollback();		
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			if(jdoPM.currentTransaction().isActive())
			{
		     jdoPM.currentTransaction().rollback();		
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		 finally 
		 {
             try{
                 if(rs != null){
                     rs.close();
                 }
                 if(ps != null){
                     ps.cancel();
                 }
                 if(connection != null){
                     connection.close();
                 }
             }catch(SQLException e){
             }
			jdoPM.close();
			pm.close();
		}
	}

	/**
	* Servicio que permite eliminar una <code>CausalRestitucion</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param valNota la <code>CausalRestitucion</code> que va a ser eliminada.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.CausalRestitucion
	*/
	public boolean eliminarCausalRestitucion(CausalRestitucion causal) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		CausalRestitucionEnhanced eliminado = CausalRestitucionEnhanced.enhance(causal);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer objeto Persistente
			CausalRestitucionEnhancedPk oid = new CausalRestitucionEnhancedPk();
			oid.idCausalRestitucion = eliminado.getIdCausalRestitucion();

			CausalRestitucionEnhanced causalEnh = null;

			try {
				causalEnh = (CausalRestitucionEnhanced) pm.getObjectById(oid, true);
			} catch (JDOObjectNotFoundException e) {
				causalEnh = null;
			}

			if (causalEnh == null) {
				throw new DAOException(
					"No existe el Causal Restitución con el id " + causalEnh.getIdCausalRestitucion());
			}

			//Cambiar el estado del causal de restitución.
			if (causalEnh.isActivo())
			{
				causalEnh.setActivo(false);
			}
			else
			{
				causalEnh.setActivo(true);
			}
			
			pm.currentTransaction().commit();
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}


	/**
	* Modifica un objeto de tipo <code>CausalRestitucion</code> dentro de la configuración del sistema.
	* <p>
	* El método genera una excepción si ya existe un causal de restitución con el nombre
	* del objeto pasado como parámetro.
	* @param causal objeto de tipo <code>CausalRestitucion</code> con todos sus atributos.
	* @return identificador del objeto <code>CausalRestitucion</code> modificado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.CausalRestitucion
	*/
	public CausalRestitucionPk editCausalRestitucion(CausalRestitucion causal) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		CausalRestitucionEnhanced ca = CausalRestitucionEnhanced.enhance(causal);
		CausalRestitucionPk rta = null;

		try {

			//Se intenta modificar el causal de restitución.
			CausalRestitucionEnhancedPk causalId = new CausalRestitucionEnhancedPk();
			causalId.idCausalRestitucion = ca.getIdCausalRestitucion();
			CausalRestitucionEnhanced causalr = this.getCausalRestitucionById(causalId, pm);

			//Si no existe un causal de restitución con el identificador del objeto
			// pasado como parámetro, se genera una excepción.
			if (causalr == null) {
				throw new DAOException(
					"No se encontró un Causal con el identificador: " + causalId.idCausalRestitucion);
			}

			pm.currentTransaction().begin();
			
			//Asociar los nuevos atributos al objeto.
			causalr.setDescripcion(ca.getDescripcion());
			causalr.setNombre(ca.getNombre());
			pm.currentTransaction().commit();
			


		} catch (Throwable t) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			throw new DAOException("Error de integridad de datos modificando un causal de restitución", t);
		} finally {
			pm.close();
		}
		
		if (rta!= null){
			rta.idCausalRestitucion = causal.getIdCausalRestitucion();
			return rta;
		}
		return null;

	}



	/**
	* Adiciona un objeto de tipo <code>TipoActo</code> a la configuración del sistema.
	* <p>
	* El método genera una excepción si ya existe un Tipo de Acto con el identificador
	* del objeto pasado como parámetro.
	* @param acto objeto de tipo <code>TipoActo</code> con todos sus atributos.
	* @param Usuario que va adicionar el tipo de acto
	* @return identificador del objeto <code>TipoActo</code> adicionado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoActo
	*/
	public TipoActoPk addTipoActo(TipoActo acto, Usuario usuario) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		TipoActoEnhanced ta = TipoActoEnhanced.enhance(acto);

		try {

			//Se intenta insertar el tipo de acto.
			TipoActoEnhancedPk actoId = new TipoActoEnhancedPk();
			actoId.idTipoActo = ta.getIdTipoActo();
			TipoActoEnhanced actor = this.getTipoActoByID(actoId, pm);
			// Obtener el usuario que va adicionar un tipo de acto
			UsuarioEnhancedPk usuarioEnhId=new UsuarioEnhancedPk();
			usuarioEnhId.idUsuario=usuario.getIdUsuario();
			UsuarioEnhanced usuarioEnh=(UsuarioEnhanced)pm.getObjectById(usuarioEnhId,true);
			// Si ya existe un tipo de acto con el identificador del objeto
			// pasado como parámetro, se genera una excepción.
			if (actor != null) {
				throw new DAOException(
					"Ya existe un Tipo de Acto con el identificador: " + actor.getIdTipoActo());
			}

			List derRegs = ta.getTiposDerechosRegistrales();
			Iterator it = derRegs.iterator();

			//Se intentan obtener cada uno de los tipos de derechos registrales,
			//asociados con el tipo de acto.
			while (it.hasNext()) {
				TipoActoDerechoRegistralEnhanced tipoActDer =
					(TipoActoDerechoRegistralEnhanced) it.next();
				TipoDerechoRegEnhanced tipoDer = tipoActDer.getTipoDerechoRegistral();
				TipoDerechoRegEnhancedPk tipoDerId = new TipoDerechoRegEnhancedPk();
				tipoDerId.idTipoDerechoReg = tipoDer.getIdTipoDerechoReg();
				TipoDerechoRegEnhanced tipoDerr = this.getTipoDerechoRegByID(tipoDerId, pm);

				// Si no existe un tipo de derecho registral con el identificador del objeto
				// pasado como parámetro, se genera una excepción.
				if (tipoDerr == null) {
					throw new DAOException(
						"No encontró el tipo de Derecho registral con el ID: "
							+ tipoDer.getIdTipoDerechoReg());
				}
				tipoActDer.setTipoDerechoRegistral(tipoDerr);
				tipoActDer.setTipoActo(ta);
			}

			//	Se intentan obtener cada uno de los tipos de cálculo,
			// asociados con el tipo de acto.
			TipoCalculoEnhanced tipoCal = ta.getTipoCalculo();
			TipoCalculoEnhancedPk tipoCalcId = new TipoCalculoEnhancedPk();
			tipoCalcId.idTipoCalculo = tipoCal.getIdTipoCalculo();
			TipoCalculoEnhanced tipoCalr = this.getTipoCalculoByID(tipoCalcId, pm);

			// Si no existe un tipo de cálculo con el identificador del objeto
			// pasado como parámetro, se genera una excepción.
			if (tipoCalr == null) {
				throw new DAOException(
					"No encontró el tipo de calculo con el ID: " + tipoCal.getIdTipoCalculo());
			}
			ta.setUsuario(usuarioEnh);
			ta.setTipoCalculo(tipoCalr);

			pm.currentTransaction().begin();
			pm.makePersistent(ta);
			pm.currentTransaction().commit();
			TipoActoEnhancedPk rta = (TipoActoEnhancedPk) pm.getObjectId(ta);

			return rta.getTipoActoDerechoRegistralID();

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		finally {
			pm.close();
		}
	}

	/**
	* Actualiza un objeto de tipo <code>TipoActo</code> a la configuración del sistema.
	* @param acto objeto de tipo <code>TipoActo</code> con todos sus atributos.
	* @param usuario que va a modificar el tipo de acto
	* @return identificador del objeto <code>TipoActo</code> modificado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoActo
	*/
	public TipoActoPk updateTipoActo(TipoActo acto, Usuario usuario) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		TipoActoEnhanced newData = TipoActoEnhanced.enhance(acto);

		try {
			pm.currentTransaction().begin();
			//Se intenta editar el tipo de acto.
			TipoActoEnhancedPk actoId = new TipoActoEnhancedPk();
			actoId.idTipoActo = newData.getIdTipoActo();
			TipoActoEnhanced actor = this.getTipoActoByID(actoId, pm);
			//Se obtiene el usuario que va amodificar el tipo de acto
			UsuarioEnhancedPk usuarioEnhId= new UsuarioEnhancedPk();
			usuarioEnhId.idUsuario=usuario.getIdUsuario();
			UsuarioEnhanced usuarioEhn=(UsuarioEnhanced)pm.getObjectById(usuarioEnhId, true);
			// Si no existe un tipo de acto con el identificador del objeto
			// pasado como parámetro, se genera una excepción.
			if (actor == null) {
				throw new DAOException(
					"No existe un Tipo de Acto con el identificador: " + actor.getIdTipoActo());
			}

                        /**
                         * @author Fernando Padilla
                         * @change caso mantis 2859, Se hace comentario sobre este codigo para que no
                         * borre los datos del tipo de acto en la base de datos.
                         */
			//se eliminan los datos del tipo de acto en la base de datos.
			//asociados con el tipo de acto.
			/*Iterator it2 = actor.getTiposDerechosRegistrales().iterator();
			while (it2.hasNext()) {
				TipoActoDerechoRegistralEnhanced tipoActDerToDel =
					(TipoActoDerechoRegistralEnhanced) it2.next();
				pm.deletePersistent(tipoActDerToDel);
			}*/

			VersantPersistenceManager pm2 = (VersantPersistenceManager) pm;
			pm2.flush();

			//se agregan los nuevos atributos del tipo de acto
			actor.setImpPorCuantia(newData.isImpPorCuantia());                        
                        /*
                         *@autor          : JATENCIA
                         * @mantis        : 0015082 
                         * @Requerimiento : 027_589_Acto_liquidación_copias 
                         * @descripcion   : Incluye la variable dentro del metodo.
                         */
                        actor.setActivo(newData.isActivo());
                        /* - Fin del bloque - */
			actor.setNombre(newData.getNombre());
			actor.setUsuario(usuarioEhn);
			
                        /**
                         * @author Fernando Padilla
                         * @change caso mantis 2859, Se hace comentario sobre este codigo para que no
                         * cree nuevos los datos del tipos de actos en la base de datos.
                         */
                        //List derRegs = newData.getTiposDerechosRegistrales();
                        //Iterator it = derRegs.iterator();

			//Se intentan obtener cada uno de los tipos de derechos registrales,
			//asociados con el tipo de acto.
			/*while (it.hasNext()) {
				TipoActoDerechoRegistralEnhanced tipoActDer =
					(TipoActoDerechoRegistralEnhanced) it.next();
				TipoDerechoRegEnhanced tipoDer = tipoActDer.getTipoDerechoRegistral();
				TipoDerechoRegEnhancedPk tipoDerId = new TipoDerechoRegEnhancedPk();
				tipoDerId.idTipoDerechoReg = tipoDer.getIdTipoDerechoReg();
				TipoDerechoRegEnhanced tipoDerr = this.getTipoDerechoRegByID(tipoDerId, pm);

				// Si no existe un tipo de derecho registral con el identificador del objeto
				// pasado como parámetro, se genera una excepción.
				if (tipoDerr == null) {
					throw new DAOException(
						"No encontró el tipo de Derecho registral con el ID: "
							+ tipoDer.getIdTipoDerechoReg());
				}
				tipoActDer.setTipoDerechoRegistral(tipoDerr);
				tipoActDer.setTipoActo(actor);
				pm.makePersistent(tipoActDer);
			}*/

			//	Se intentan obtener cada uno de los tipos de cálculo,
			// asociados con el tipo de acto.
			TipoCalculoEnhanced tipoCal = newData.getTipoCalculo();
			TipoCalculoEnhancedPk tipoCalcId = new TipoCalculoEnhancedPk();
			tipoCalcId.idTipoCalculo = tipoCal.getIdTipoCalculo();
			TipoCalculoEnhanced tipoCalr = this.getTipoCalculoByID(tipoCalcId, pm);

			// Si no existe un tipo de cálculo con el identificador del objeto
			// pasado como parámetro, se genera una excepción.
			if (tipoCalr == null) {
				throw new DAOException(
					"No encontró el tipo de calculo con el ID: " + tipoCal.getIdTipoCalculo());
			}
			actor.setTipoCalculo(tipoCalr);
			pm.currentTransaction().commit();
			TipoActoEnhancedPk rta = (TipoActoEnhancedPk) pm.getObjectId(actor);

			return rta.getTipoActoDerechoRegistralID();

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		 finally {
			pm.close();
		}
	}

	/**
	* Obtiene una la lista de objetos <code>TipoCalculo</code> existentes en
	* la configuración del sistema.
	* <p>
	* El ordenamiento de los resultados se realiza alfabéticamente, utilizando
	* como criterio de ordenamiento el nombre del <code>TipoCalculo</code>
	* @return una lista de objetos <code>TipoCalculo</code>
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoCalculo
	*/
	public List getTiposCalculo() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {

			//Se obtiene la lista de los objetos TipoCalculo.
			//Se establece como criterio de ordenamiento el nombre del tipo de calculo
			//(Alfabéticamente).
			pm.currentTransaction().begin();
			Query q = pm.newQuery(TipoCalculoEnhanced.class);
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			while (it.hasNext()) {
				TipoCalculoEnhanced obj = (TipoCalculoEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	* Adiciona un objeto de tipo <code>TipoCalculo</code> a la configuración del sistema.
	* <p>
	* El método genera una excepción si ya existe un Tipo de Cálculo con el identificador
	* del objeto pasado como parámetro.
	* @param tipoCalc objeto de tipo <code>TipoCalculo</code> con todos sus atributos.
	* @return identificador del objeto <code>TipoCalculo</code> adicionado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoCalculo
	*/
	public TipoCalculoPk addTipoCalculo(TipoCalculo tipoCalc) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		TipoCalculoEnhanced tc = TipoCalculoEnhanced.enhance(tipoCalc);

		try {

			//Se intenta insertar el tipo de cálculo.
			TipoCalculoEnhancedPk calcId = new TipoCalculoEnhancedPk();
			calcId.idTipoCalculo = tc.getIdTipoCalculo();
			TipoCalculoEnhanced calcr = this.getTipoCalculoByID(calcId, pm);

			// Si ya existe un tipo de cálculo con el identificador del objeto
			// pasado como parámetro, se genera una excepción.
			if (calcr != null) {
				throw new DAOException(
					"Ya existe un Tipo de Calculo con el identificador: " + calcr.getIdTipoCalculo());
			}

			pm.currentTransaction().begin();
			pm.makePersistent(tc);
			pm.currentTransaction().commit();
			TipoCalculoEnhancedPk rta = (TipoCalculoEnhancedPk) pm.getObjectId(tc);
			return rta.getTipoCalculoID();

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 finally {
			pm.close();
		}

	}

	/**
	* Adiciona un objeto de tipo <code>TipoImpuesto</code> a la configuración del sistema.
	* <p>
	* El método genera una excepción si ya existe un Tipo de Impuesto con el identificador
	* del objeto pasado como parámetro.
	* @param tImpuesto objeto de tipo <code>TipoImpuesto</code> con todos sus atributos.
	* @return identificador del objeto <code>TipoImpuesto</code> adicionado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoImpuesto
	*/
	public TipoImpuestoPk addTipoImpuesto(TipoImpuesto tImpuesto) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		TipoImpuestoEnhanced ti = TipoImpuestoEnhanced.enhance(tImpuesto);

		try {

			//Se intenta insertar el tipo de impuesto.
			TipoImpuestoEnhancedPk tImpId = new TipoImpuestoEnhancedPk();
			tImpId.idTipoImpuesto = ti.getIdTipoImpuesto();
			TipoImpuestoEnhanced tImpr = this.getTipoImpuestoByID(tImpId, pm);

			// Si ya existe un tipo de impuesto con el identificador del objeto
			// pasado como parámetro, se genera una excepción.
			if (tImpr != null) {
				throw new DAOException(
					"Ya existe un Tipo de Impuesto con el identificador: " + tImpId.idTipoImpuesto);
			}

			pm.currentTransaction().begin();
			pm.makePersistent(ti);
			pm.currentTransaction().commit();
			TipoImpuestoEnhancedPk rta = (TipoImpuestoEnhancedPk) pm.getObjectId(ti);
			return rta.getTipoImpuestoID();

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 finally {
			pm.close();
		}
	}

	/**
	* Adiciona un objeto de tipo <code>SubtipoAtencion</code> a la configuración del sistema.
	* <p>
	* El método genera una excepción si ya existe un Subtipo de Atención con el identificador
	* del objeto pasado como parámetro.
	* @param subAt objeto de tipo <code>SubtipoAtencion</code> con todos sus atributos.
	* @return identificador del objeto <code>SubtipoAtencion</code> adicionado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SubtipoAtencion
	*/
	public SubtipoAtencionPk addSubtipoAtencion(SubtipoAtencion subAt) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		SubtipoAtencionEnhanced subA = SubtipoAtencionEnhanced.enhance(subAt);

		try {

			//Se intenta insertar el subtipo de atención.
			SubtipoAtencionEnhancedPk subAtId = new SubtipoAtencionEnhancedPk();
			subAtId.idSubtipoAtencion = subA.getIdSubtipoAtencion();
			SubtipoAtencionEnhanced subAtr = this.getSubtipoAtencionByID(subAtId, pm);

			// Si ya existe un tipo de impuesto con el identificador del objeto
			// pasado como parámetro, se genera una excepción.
			if (subAtr != null) {
				throw new DAOException(
					"Ya existe un Subtipo Atencion con el identificador: " + subAtId.idSubtipoAtencion);
			}

			List subSols = subA.getSubtipoSolicitudes();
			Iterator it = subSols.iterator();

			//Se intentan obtener cada uno de los subtipos de solicitud,
			//asociados con el subtipo de atención.
			while (it.hasNext()) {
				SubAtencionSubSolicitudEnhanced subAtSol = (SubAtencionSubSolicitudEnhanced) it.next();
				SubtipoSolicitudEnhanced subSol = subAtSol.getSubtipoSolicitud();
				SubtipoSolicitudEnhancedPk subSolId = new SubtipoSolicitudEnhancedPk();
				subSolId.idSubtipoSol = subSol.getIdSubtipoSol();
				SubtipoSolicitudEnhanced subSolr = this.getSubtipoSolicitudByID(subSolId, pm);

				// Si no existe un subtipo de solicitud con el identificador del objeto
				// pasado como parámetro, se genera una excepción.
				if (subSolr == null) {
					throw new DAOException(
						"No encontró el subtipo Solicitud con el ID: " + subSol.getIdSubtipoSol());
				}
				subAtSol.setSubtipoSolicitud(subSolr);
				subAtSol.setSubtipoAtencion(subA);
			}

			List tipoActos = subA.getTiposActos();
			Iterator it2 = tipoActos.iterator();

			//Se intentan obtener cada uno de los subtipos de solicitud,
			//asociados con el subtipo de atención.
			while (it2.hasNext()) {
				SubtipoAtencionTipoActoEnhanced subAtActo = (SubtipoAtencionTipoActoEnhanced) it2.next();
				TipoActoEnhanced tipoActo = subAtActo.getTipoActo();
				TipoActoEnhancedPk tipoActId = new TipoActoEnhancedPk();
				tipoActId.idTipoActo = tipoActo.getIdTipoActo();
				TipoActoEnhanced tipoActor = this.getTipoActoByID(tipoActId, pm);

				// Si no existe un tipo de acto con el identificador del objeto
				// pasado como parámetro, se genera una excepción.
				if (tipoActor == null) {
					throw new DAOException(
						"No encontró el tipo de acto con el ID: " + tipoActo.getIdTipoActo());
				}
				subAtActo.setTipoActo(tipoActor);
				subAtActo.setSubtipoAtencion(subA);
			}

			pm.currentTransaction().begin();
			pm.makePersistent(subA);
			pm.currentTransaction().commit();

			SubtipoAtencionEnhancedPk rta = (SubtipoAtencionEnhancedPk) pm.getObjectId(subA);
			return rta.getSubtipoAtencionID();

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 finally {
			pm.close();
		}
	}

	/**
	* Edita un objeto de tipo <code>SubtipoAtencion</code> a la configuración del sistema.
	* <p>
	* El método genera una excepción si ya existe un Subtipo de Atención con el identificador
	* del objeto pasado como parámetro.
	* @param subAt objeto de tipo <code>SubtipoAtencion</code> con todos sus atributos.
	* @return identificador del objeto <code>SubtipoAtencion</code> adicionado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SubtipoAtencion
	*/
	public SubtipoAtencionPk updateSubtipoAtencion(SubtipoAtencion subAt) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		SubtipoAtencionEnhanced newData = SubtipoAtencionEnhanced.enhance(subAt);

		try {

			//Se intenta insertar el subtipo de atención.
			SubtipoAtencionEnhancedPk subAtId = new SubtipoAtencionEnhancedPk();
			subAtId.idSubtipoAtencion = newData.getIdSubtipoAtencion();
			SubtipoAtencionEnhanced subAtr = this.getSubtipoAtencionByID(subAtId, pm);

			pm.currentTransaction().begin();

			// Si ya existe un tipo de impuesto con el identificador del objeto
			// pasado como parámetro, se genera una excepción.
			if (subAtr == null) {
				throw new DAOException(
					"No existe un Subtipo Atencion con el identificador: " + subAtId.idSubtipoAtencion);
			}

			//Se agregan los nuevos datos
			subAtr.setNombre(newData.getNombre());
			subAtr.setMinAnotacione(newData.getMinAnotacione());


			//se eliminan las clases relaccion de subtipos de solicitud base de datos.
			Iterator it4 = subAtr.getSubtipoSolicitudes().iterator();
			while (it4.hasNext()) {
				SubAtencionSubSolicitudEnhanced relSubtipoSolicitudToDel =
					(SubAtencionSubSolicitudEnhanced) it4.next();
				pm.deletePersistent(relSubtipoSolicitudToDel);
			}

			//se eliminan las clases relaccion de subtipos de solicitud base de datos.
			Iterator it3 = subAtr.getTiposActos().iterator();
			while (it3.hasNext()) {
				SubtipoAtencionTipoActoEnhanced relTipoActoToDel =
					(SubtipoAtencionTipoActoEnhanced) it3.next();
				pm.deletePersistent(relTipoActoToDel);
			}

			VersantPersistenceManager pm2 = (VersantPersistenceManager) pm;
			pm2.flush();


			List subSols = newData.getSubtipoSolicitudes();
			Iterator it = subSols.iterator();

			//Se intentan obtener cada uno de los subtipos de solicitud,
			//asociados con el subtipo de atención.
			while (it.hasNext()) {
				SubAtencionSubSolicitudEnhanced subAtSol = (SubAtencionSubSolicitudEnhanced) it.next();
				SubtipoSolicitudEnhanced subSol = subAtSol.getSubtipoSolicitud();
				SubtipoSolicitudEnhancedPk subSolId = new SubtipoSolicitudEnhancedPk();
				subSolId.idSubtipoSol = subSol.getIdSubtipoSol();
				SubtipoSolicitudEnhanced subSolr = this.getSubtipoSolicitudByID(subSolId, pm);

				// Si no existe un subtipo de solicitud con el identificador del objeto
				// pasado como parámetro, se genera una excepción.
				if (subSolr == null) {
					throw new DAOException(
						"No encontró el subtipo Solicitud con el ID: " + subSol.getIdSubtipoSol());
				}
				subAtSol.setSubtipoSolicitud(subSolr);
				subAtSol.setSubtipoAtencion(subAtr);
				pm.makePersistent(subAtSol);
			}

			List tipoActos = newData.getTiposActos();
			Iterator it2 = tipoActos.iterator();

			//Se intentan obtener cada uno de los subtipos de solicitud,
			//asociados con el subtipo de atención.
			while (it2.hasNext()) {
				SubtipoAtencionTipoActoEnhanced subAtActo = (SubtipoAtencionTipoActoEnhanced) it2.next();
				TipoActoEnhanced tipoActo = subAtActo.getTipoActo();
				TipoActoEnhancedPk tipoActId = new TipoActoEnhancedPk();
				tipoActId.idTipoActo = tipoActo.getIdTipoActo();
				TipoActoEnhanced tipoActor = this.getTipoActoByID(tipoActId, pm);

				// Si no existe un tipo de acto con el identificador del objeto
				// pasado como parámetro, se genera una excepción.
				if (tipoActor == null) {
					throw new DAOException(
						"No encontró el tipo de acto con el ID: " + tipoActo.getIdTipoActo());
				}
				subAtActo.setTipoActo(tipoActor);
				subAtActo.setSubtipoAtencion(subAtr);
				pm.makePersistent(subAtActo);
			}


			pm.currentTransaction().commit();

			SubtipoAtencionEnhancedPk rta = (SubtipoAtencionEnhancedPk) pm.getObjectId(subAtr);
			return rta.getSubtipoAtencionID();

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		finally {
			pm.close();
		}
	}

	/**
	* Adiciona un objeto de tipo <code>SubtipoSolicitud</code> a la configuración del sistema.
	* <p>
	* El método genera una excepción si ya existe un Subtipo de Solicitud con el identificador
	* del objeto pasado como parámetro.
	* @param subSol objeto de tipo <code>SubtipoSolicitud</code> con todos sus atributos.
	* @return identificador del objeto <code>SubtipoSolicitud</code> adicionado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SubtipoSolicitud
	*/
	public SubtipoSolicitudPk addSubtipoSolicitud(SubtipoSolicitud subSol) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		SubtipoSolicitudEnhanced subS = SubtipoSolicitudEnhanced.enhance(subSol);

		try {

			//Se intenta insertar el subtipo de solicitud.
			SubtipoSolicitudEnhancedPk subSolId = new SubtipoSolicitudEnhancedPk();
			subSolId.idSubtipoSol = subS.getIdSubtipoSol();
			SubtipoSolicitudEnhanced subSolr = this.getSubtipoSolicitudByID(subSolId, pm);

			// Si ya existe un subtipo de solicitud con el identificador del objeto
			// pasado como parámetro, se genera una excepción.
			if (subSolr != null) {
				throw new DAOException(
					"Ya existe un Subtipo Solicitud con el identificador: " + subSolId.idSubtipoSol);
			}

			pm.currentTransaction().begin();
			pm.makePersistent(subS);
			pm.currentTransaction().commit();
			SubtipoSolicitudEnhancedPk rta = (SubtipoSolicitudEnhancedPk) pm.getObjectId(subS);
			return rta.getSubtipoSolicitudID();

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		finally {
			pm.close();
		}
	}

	/**
	* Edita un objeto de tipo <code>SubtipoSolicitud</code> a la configuración del sistema.
	* @param subSol objeto de tipo <code>SubtipoSolicitud</code> con todos sus atributos.
	* @return identificador del objeto <code>SubtipoSolicitud</code> editado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SubtipoSolicitud
	*/
	public SubtipoSolicitudPk updateSubtipoSolicitud(SubtipoSolicitud subSol) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		SubtipoSolicitudEnhanced newData = SubtipoSolicitudEnhanced.enhance(subSol);

		try {
			pm.currentTransaction().begin();
			//Se intenta insertar el subtipo de solicitud.
			SubtipoSolicitudEnhancedPk subSolId = new SubtipoSolicitudEnhancedPk();
			subSolId.idSubtipoSol = newData.getIdSubtipoSol();
			SubtipoSolicitudEnhanced subSolr = this.getSubtipoSolicitudByID(subSolId, pm);

			// Si ya existe un subtipo de solicitud con el identificador del objeto
			// pasado como parámetro, se genera una excepción.
			if (subSolr == null) {
				throw new DAOException(
					"No existe un Subtipo Solicitud con el identificador: " + subSolId.idSubtipoSol);
			}

			// Se ingresan los nuevos datos del subtipo de solicitud
			subSolr.setNombre(newData.getNombre());


			// Se hace commit al cambio
			pm.currentTransaction().commit();
			SubtipoSolicitudEnhancedPk rta = (SubtipoSolicitudEnhancedPk) pm.getObjectId(subSolr);
			return rta.getSubtipoSolicitudID();

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		finally {
			pm.close();
		}
	}

	/**
	* Adiciona un objeto de tipo <code>TipoCertificado</code> a la configuración del sistema.
	* <p>
	* El método genera una excepción si ya existe un Tipo de Certificado con el identificador
	* del objeto pasado como parámetro.
	* @param tipoCert objeto de tipo <code>TipoCertificado</code> con todos sus atributos.
	* @return identificador del objeto <code>TipoCertificado</code> adicionado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoCertificado
	*/
	public TipoCertificadoPk addTipoCertificado(TipoCertificado tipoCert) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		TipoCertificadoEnhanced tc = TipoCertificadoEnhanced.enhance(tipoCert);

		try {

			//Se intenta insertar el tipo de certificado.
			TipoCertificadoEnhancedPk tipoId = new TipoCertificadoEnhancedPk();
			tipoId.idTipoCertificado = tc.getIdTipoCertificado();
			TipoCertificadoEnhanced tipor = this.getTipoCertificadoByID(tipoId, pm);

			// Si ya existe un tipo de certificado con el identificador del objeto
			// pasado como parámetro, se genera una excepción.
			if (tipor != null) {
				throw new DAOException(
					"Ya existe un Tipo de Certificado con el identificador: "
						+ tipor.getIdTipoCertificado());
			}

			pm.currentTransaction().begin();
			pm.makePersistent(tc);
			pm.currentTransaction().commit();
			TipoCertificadoEnhancedPk rta = (TipoCertificadoEnhancedPk) pm.getObjectId(tc);
			return rta.getTipoCertificadoID();

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		finally {
			pm.close();
		}
	}

	/**
	* Adiciona un objeto de tipo <code>TipoConsulta</code> a la configuración del sistema.
	* <p>
	* El método genera una excepción si ya existe un Tipo de Consulta con el identificador
	* del objeto pasado como parámetro.
	* @param tipoCons objeto de tipo <code>TipoCertificado</code> con todos sus atributos.
	* @return identificador del objeto <code>TipoCertificado</code> adicionado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoCertificado
	*/
	public TipoConsultaPk addTipoConsulta(TipoConsulta tipoCons) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		TipoConsultaEnhanced tc = TipoConsultaEnhanced.enhance(tipoCons);

		try {

			//Se intenta insertar el tipo de consulta.
			TipoConsultaEnhancedPk tipoId = new TipoConsultaEnhancedPk();
			tipoId.idTipoConsulta = tc.getIdTipoConsulta();
			TipoConsultaEnhanced tipor = this.getTipoConsultaByID(tipoId, pm);

			// Si ya existe un tipo de consulta con el identificador del objeto
			// pasado como parámetro, se genera una excepción.
			if (tipor != null) {
				throw new DAOException(
					"Ya existe un Tipo de Consultas con el identificador: " + tipor.getIdTipoConsulta());
			}

			pm.currentTransaction().begin();
			pm.makePersistent(tc);
			pm.currentTransaction().commit();
			TipoConsultaEnhancedPk rta = (TipoConsultaEnhancedPk) pm.getObjectId(tc);
			return rta.getTipoConsultaID();

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		finally {
			pm.close();
		}
	}

	/**
	* Adiciona un objeto de tipo <code>TipoRecepcionPeticion</code> a la configuración del sistema.
	* <p>
	* El método genera una excepción si ya existe un Tipo de Recepcion Peticion con el identificador
	* del objeto pasado como parámetro.
	* @param tipoRep objeto de tipo <code>TipoRecepcionPeticion</code> con todos sus atributos.
	* @return identificador del objeto <code>TipoRecepcionPeticion</code> adicionado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoRecepcionPeticion
	*/
	public TipoRecepcionPeticionPk addTipoRecepcionPeticion(TipoRecepcionPeticion tipoRep)
		throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		TipoRecepcionPeticionEnhanced tr = TipoRecepcionPeticionEnhanced.enhance(tipoRep);

		try {

			//Se intenta adicionar el tipo de recepción petición.
			TipoRecepcionPeticionEnhancedPk tipoId = new TipoRecepcionPeticionEnhancedPk();
			tipoId.idTipoRecepcionPeticion = tr.getIdTipoRecepcion();
			TipoRecepcionPeticionEnhanced tipor = this.getTipoRecepcionPeticionByID(tipoId, pm);

			// Si ya existe un tipo de recepción petición con el identificador del objeto
			// pasado como parámetro, se genera una excepción.
			if (tipor != null) {
				throw new DAOException(
					"Ya existe un Tipo de Recepcion con el identificador: "
						+ tipor.getIdTipoRecepcion());
			}

			pm.currentTransaction().begin();
			pm.makePersistent(tr);
			pm.currentTransaction().commit();
			TipoRecepcionPeticionEnhancedPk rta = (TipoRecepcionPeticionEnhancedPk) pm.getObjectId(tr);
			return rta.getTipoRecepcionPeticionID();

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		 finally {
			pm.close();
		}
	}

	/**
	* Obtiene un objeto de tipo <code>SubtipoAtencionEnhanced</code> dado su identificador.
	* <p>
	* Método usado en transacciones.
	* <p>
	* El método retorna null en el caso en el que no encuentre el Subtipo de Atencion con
	* el identificador recibido como parámetro.
	* @param saID Identificador del objeto <code>SubtipoAtencion</code> que se quiere
	* recuperar.
	* @param pm Persistence manager de la transacción.
	* @return objeto <code>SubtipoAtencionEnhanced</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.SubtipoAtencion
	* @see gov.sir.core.negocio.modelo.SubtipoAtencionEnhanced
	* @throws DAOException
	*/
	protected SubtipoAtencionEnhanced getSubtipoAtencionByID(
		SubtipoAtencionEnhancedPk saID,
		PersistenceManager pm)
		throws DAOException {
		SubtipoAtencionEnhanced rta = null;

		if (saID.idSubtipoAtencion != null) {
			try {
				rta = (SubtipoAtencionEnhanced) pm.getObjectById(saID, true);
			}

			//No se encontró el subtipo de atención con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene un objeto de tipo <code>SubtipoSolicitudEnhanced</code> dado su identificador.
	* <p>
	* Método usado en transacciones.
	* <p>
	* El método retorna null en el caso en el que no encuentre el Subtipo de Solicitud con
	* el identificador recibido como parámetro.
	* @param soID Identificador del objeto <code>SubtipoSolicitud</code> que se quiere
	* recuperar.
	* @param pm Persistence manager de la transacción.
	* @return objeto <code>SubtipoSolicitudEnhanced</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.SubtipoSolicitud
	* @see gov.sir.core.negocio.modelo.SubtipoSolicitudEnhanced
	* @throws DAOException
	*/
	protected SubtipoSolicitudEnhanced getSubtipoSolicitudByID(
		SubtipoSolicitudEnhancedPk soID,
		PersistenceManager pm)
		throws DAOException {
		SubtipoSolicitudEnhanced rta = null;

		if (soID.idSubtipoSol != null) {
			try {
				rta = (SubtipoSolicitudEnhanced) pm.getObjectById(soID, true);
			}

			//No se encontró el subtipo de solicitud con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene un objeto de tipo <code>TipoActoEnhanced</code> dado su identificador.
	* <p>
	* Método usado en transacciones.
	* <p>
	* El método retorna null en el caso en el que no encuentre el Tipo de Acto con
	* el identificador recibido como parámetro.
	* @param taID Identificador del objeto <code>TipoActo</code> que se quiere
	* recuperar.
	* @param pm Persistence manager de la transacción.
	* @return objeto <code>TipoActoEnhanced</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.TipoActo
	* @see gov.sir.core.negocio.modelo.TipoActoEnhanced
	* @throws DAOException
	*/
	protected TipoActoEnhanced getTipoActoByID(TipoActoEnhancedPk taID, PersistenceManager pm)
		throws DAOException {
		TipoActoEnhanced rta = null;

		if (taID.idTipoActo != null) {
			try {
				rta = (TipoActoEnhanced) pm.getObjectById(taID, true);
			}

			//No se encontró el tipo de acto con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
				Log.getInstance().debug(JDOVariablesOperativasDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene un objeto de tipo <code>TipoActoDerechoRegistralEnhanced</code> dado su identificador.
	* <p>
	* Método usado en transacciones.
	* <p>
	* El método retorna null en el caso en el que no encuentre el Tipo de Acto de
	* Derecho Registral, con el identificador recibido como parámetro.
	* @param taID Identificador del objeto <code>TipoActoDerechoRegistral</code> que se quiere
	* recuperar.
	* @param pm Persistence manager de la transacción.
	* @return objeto <code>TipoActoDerechoRegistralEnhanced</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.TipoActoDerechoRegistral
	* @see gov.sir.core.negocio.modelo.TipoActoDerechoRegistralEnhanced
	* @throws DAOException
	*/
	protected TipoActoDerechoRegistralEnhanced getTipoActoDerechoByID(
		TipoActoDerechoRegistralEnhancedPk taID,
		PersistenceManager pm)
		throws DAOException {
		TipoActoDerechoRegistralEnhanced rta = null;

		if ((taID.idTipoActo != null) && (taID.idTipoDerechoReg != null)) {
			try {
				rta = (TipoActoDerechoRegistralEnhanced) pm.getObjectById(taID, true);
			}

			//No se encontró el tipo de acto derecho registral con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
				Log.getInstance().debug(JDOVariablesOperativasDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene un objeto de tipo <code>TipoImpuestoEnhanced</code> dado su identificador.
	* <p>
	* Método usado en transacciones.
	* <p>
	* El método retorna null en el caso en el que no encuentre el Tipo de Impuesto, con el identificador recibido como parámetro.
	* @param taID Identificador del objeto <code>TipoImpuesto</code> que se quiere
	* recuperar.
	* @param pm Persistence manager de la transacción.
	* @return objeto <code>TipoImpuestoEnhanced</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.TipoImpuesto
	* @see gov.sir.core.negocio.modelo.TipoImpuestoEnhanced
	* @throws DAOException
	*/
	protected TipoImpuestoEnhanced getTipoImpuestoByID(
		TipoImpuestoEnhancedPk taID,
		PersistenceManager pm)
		throws DAOException {
		TipoImpuestoEnhanced rta = null;

		if (taID.idTipoImpuesto != null) {
			try {
				rta = (TipoImpuestoEnhanced) pm.getObjectById(taID, true);
			}

			//No se encontró el tipo de impuesto con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
				Log.getInstance().debug(JDOVariablesOperativasDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene un objeto de tipo <code>TipoCalculoEnhanced</code> dado su identificador.
	* <p>
	* Método usado en transacciones.
	* <p>
	* El método retorna null en el caso en el que no encuentre el Tipo de Calculo, con el
	* identificador recibido como parámetro.
	* @param taID Identificador del objeto <code>TipoCalculo</code> que se quiere
	* recuperar.
	* @param pm Persistence manager de la transacción.
	* @return objeto <code>TipoCalculoEnhanced</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.TipoCalculo
	* @see gov.sir.core.negocio.modelo.TipoCalculoEnhanced
	* @throws DAOException
	*/
	protected TipoCalculoEnhanced getTipoCalculoByID(TipoCalculoEnhancedPk taID, PersistenceManager pm)
		throws DAOException {
		TipoCalculoEnhanced rta = null;

		if (taID.idTipoCalculo != null) {
			try {
				rta = (TipoCalculoEnhanced) pm.getObjectById(taID, true);
			}

			//No se encontró el tipo de cálculo con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
				Log.getInstance().debug(JDOVariablesOperativasDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene un objeto de tipo <code>AlcanceGeograficoEnhanced</code> dado su identificador.
	* <p>
	* Método usado en transacciones.
	* <p>
	* El método retorna null en el caso en el que no encuentre el Tipo de Alcance
	* Geográfico, con el identificador recibido como parámetro.
	* @param alcance Identificador del objeto <code>AlcanceGeografico</code> que se quiere
	* recuperar.
	* @param Persistence manager de la transacción.
	* @return objeto <code>AlcanceGeograficoEnhanced</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.AlcanceGeografico
	* @see gov.sir.core.negocio.modelo.AlcanceGeograficoEnhanced
	* @throws DAOException
	*/
	protected AlcanceGeograficoEnhanced getTipoAlcanceGeograficoByID(
		AlcanceGeograficoEnhancedPk alcance,
		PersistenceManager pm)
		throws DAOException {
		AlcanceGeograficoEnhanced rta = null;

		if (alcance.idAlcanceGeografico != null) {
			try {
				rta = (AlcanceGeograficoEnhanced) pm.getObjectById(alcance, true);
			}

			//No se encontró el tipo de alcance geográfico con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene un objeto de tipo <code>TipoCertificado</code> dado su identificador.
	* <p>
	* El método retorna null en el caso en el que no encuentre el Tipo de Certificado
	* con el identificador recibido como parámetro.
	* @param tipoCert Identificador del objeto <code>TipoCertificado</code> que se quiere
	* recuperar.
	* @return objeto <code>TipoCertificado</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.TipoCertificado
	* @throws DAOException
	*/
	public TipoCertificado getTipoCertificadoByID(TipoCertificadoPk tipoCert) throws DAOException {
		TipoCertificadoEnhanced tr = null;
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			tr = this.getTipoCertificadoByID(new TipoCertificadoEnhancedPk(tipoCert), pm);
			pm.makeTransient(tr);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw e;
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return (TipoCertificado) tr.toTransferObject();
	}

	/**
	* Obtiene un objeto de tipo <code>TipoCertificadoEnhanced</code> dado su identificador.
	* <p>
	* El método retorna null en el caso en el que no encuentre el Tipo de Certificado
	* con el identificador recibido como parámetro.
	* @param tipoCert Identificador del objeto <code>TipoCertificado</code> que se quiere
	* recuperar.
	* @param pm PersistenceManager de la transacción.
	* @return objeto <code>TipoCertificadoEnhanced</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.TipoCertificado
	* @see gov.sir.core.negocio.modelo.TipoCertificadoEnhanced
	* @throws DAOException
	*/
	protected TipoCertificadoEnhanced getTipoCertificadoByID(
		TipoCertificadoEnhancedPk tipoCert,
		PersistenceManager pm)
		throws DAOException {
		TipoCertificadoEnhanced rta = null;

		if (tipoCert.idTipoCertificado != null) {
			try {
				rta = (TipoCertificadoEnhanced) pm.getObjectById(tipoCert, true);
			}

			//No se encontró el tipo de certificado con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene un objeto de tipo <code>TipoConsultaEnhanced</code> dado su identificador.
	* <p>
	* El método retorna null en el caso en el que no encuentre el Tipo de Consulta
	* con el identificador recibido como parámetro.
	* @param tipoCons Identificador del objeto <code>TipoConsulta</code> que se quiere
	* recuperar.
	* @param pm PersistenceManager de la transacción.
	* @return objeto <code>TipoConsultaEnhanced</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.TipoConsulta
	* @see gov.sir.core.negocio.modelo.TipoConsultaEnhanced
	* @throws DAOException
	*/
	protected TipoConsultaEnhanced getTipoConsultaByID(
		TipoConsultaEnhancedPk tipoCons,
		PersistenceManager pm)
		throws DAOException {
		TipoConsultaEnhanced rta = null;

		if (tipoCons.idTipoConsulta != null) {
			try {
				rta = (TipoConsultaEnhanced) pm.getObjectById(tipoCons, true);
			}

			//No se encontró el tipo de consulta con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene un objeto de tipo <code>TipoFotocopiaEnhanced</code> dado su identificador.
	* <p>
	* El método retorna null en el caso en el que no encuentre el Tipo de Fotocopia
	* con el identificador recibido como parámetro.
	* @param oid Identificador del objeto <code>TipoFotocopia</code> que se quiere
	* recuperar.
	* @param pm PersistenceManager de la transacción.
	* @return objeto <code>TipoFotocopiaEnhanced</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.TipoFotocopia
	* @see gov.sir.core.negocio.modelo.TipoFotocopiaEnhanced
	* @throws DAOException
	*/
	protected TipoFotocopiaEnhanced getTipoFotocopia(
		TipoFotocopiaEnhancedPk oid,
		PersistenceManager pm)
		throws DAOException {
		TipoFotocopiaEnhanced rta = null;

		if (oid.idTipoFotocopia != null) {
			try {
				rta = (TipoFotocopiaEnhanced) pm.getObjectById(oid, true);
			}

			//No se encontró el tipo de fotocopia con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene un objeto de tipo <code>TipoRecepcionPeticionEnhanced</code> dado su identificador.
	* <p>
	* El método retorna null en el caso en el que no encuentre el Tipo de
	* RecepcionPeticion con el identificador recibido como parámetro.
	* @param tipoRecPet Identificador del objeto <code>TipoRecepcionPeticion</code> que se quiere
	* recuperar.
	* @param pm PersistenceManager de la transacción.
	* @return objeto <code>TipoRecepcionPeticionEnhanced</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.TipoRecepcionPeticion
	* @see gov.sir.core.negocio.modelo.TipoRecepcionPeticionEnhanced
	* @throws DAOException
	*/
	protected TipoRecepcionPeticionEnhanced getTipoRecepcionPeticionByID(
		TipoRecepcionPeticionEnhancedPk tipoRecPet,
		PersistenceManager pm)
		throws DAOException {
		TipoRecepcionPeticionEnhanced rta = null;

		if (tipoRecPet.idTipoRecepcionPeticion != null) {
			try {
				rta = (TipoRecepcionPeticionEnhanced) pm.getObjectById(tipoRecPet, true);
			}

			//No se encontró el tipo de recepción petición con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene un objeto de tipo <code>CausalRestitucionEnhanced</code> dado su identificador.
	* <p>
	* El método retorna null en el caso en el que no encuentre el
	* CausalRestitucion con el identificador recibido como parámetro.
	* @param causalID Identificador del objeto <code>CausalRestitucion</code> que se quiere
	* recuperar.
	* @param pm PersistenceManager de la transacción.
	* @return objeto <code>CausalRestitucionEnhanced</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.CausalRestitucion
	* @see gov.sir.core.negocio.modelo.CausalRestitucionEnhanced
	* @throws DAOException
	*/
	protected CausalRestitucionEnhanced getCausalRestitucionById(
		CausalRestitucionEnhancedPk causalID,
		PersistenceManager pm)
		throws DAOException {
		CausalRestitucionEnhanced rta = null;

		if (causalID.idCausalRestitucion != null) {
			try {
				rta = (CausalRestitucionEnhanced) pm.getObjectById(causalID, true);
			}

			//No se encontró el causal de restitución con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
				Log.getInstance().debug(JDOVariablesOperativasDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene un objeto de tipo <code>TipoFotocopia</code> dado su identificador.
	* <p>
	* El método retorna null en el caso en el que no encuentre el Tipo de Fotocopia
	* con el identificador recibido como parámetro.
	* @param oid Identificador del objeto <code>TipoFotocopia</code> que se quiere
	* recuperar.
	* @return objeto <code>TipoFotocopia</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.TipoFotocopia
	* @throws DAOException
	*/
	public TipoFotocopia getTipoFotocopiaByID(TipoFotocopiaPk oid) throws DAOException {
		TipoFotocopiaEnhanced rta = null;
		TipoFotocopia aux = null;
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			rta = this.getTipoFotocopiaByID(new TipoFotocopiaEnhancedPk(oid), pm);
			if (rta != null) {
				pm.makeTransient(rta);
			}
		} catch (DAOException e) {
			throw e;
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			pm.close();
		}
		if (rta != null) {
			aux = (TipoFotocopia) rta.toTransferObject();
		}

		return aux;
	}

	/**
	* Obtiene un objeto de tipo <code>TipoFotocopiaEnhanced</code> dado su identificador.
	* <p>
	* El método retorna null en el caso en el que no encuentre el
	* TipoFotocopia con el identificador recibido como parámetro.
	* @param oid Identificador del objeto <code>TipoFotocopia</code> que se quiere
	* recuperar.
	* @param pm PersistenceManager de la transacción.
	* @return objeto <code>TipoFotocopiaEnhanced</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.TipoFotocopia
	* @see gov.sir.core.negocio.modelo.TipoFotocopiaEnhanced
	* @throws DAOException
	*/
	protected TipoFotocopiaEnhanced getTipoFotocopiaByID(
		TipoFotocopiaEnhancedPk oid,
		PersistenceManager pm)
		throws DAOException {
		TipoFotocopiaEnhanced rta = null;

		if (oid.idTipoFotocopia != null) {
			try {
				rta = (TipoFotocopiaEnhanced) pm.getObjectById(oid, true);
			}

			//No se encontró el tipo de fotocopia con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Adiciona un objeto de tipo <code>TipoDerechoReg</code> a la configuración del sistema.
	* <p>
	* El método genera una excepción si ya existe un Tipo de Derecho Registral con el identificador
	* del objeto pasado como parámetro.
	* @param tipoDer objeto de tipo <code>TipoDerechoReg</code> con todos sus atributos.
	* @return identificador del objeto <code>TipoDerechoReg</code> adicionado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoDerechoReg
	*/
	public TipoDerechoRegPk addTipoDerechoRegistral(TipoDerechoReg tipoDer) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		TipoDerechoRegEnhanced tr = TipoDerechoRegEnhanced.enhance(tipoDer);

		try {

			//Se intenta insertar el tipo de derecho registral.
			TipoDerechoRegEnhancedPk tipoId = new TipoDerechoRegEnhancedPk();
			tipoId.idTipoDerechoReg = tr.getIdTipoDerechoReg();
			TipoDerechoRegEnhanced tipor = this.getTipoDerechoRegByID(tipoId, pm);

			//Si ya existe un tipo de derecho registral con el identificador pasado como
			//parámetro, se genera una excepción.
			if (tipor != null) {
				throw new DAOException(
					"Ya existe un Tipo de Derecho Registral con el identificador: "
						+ tipor.getIdTipoDerechoReg());
			}

			pm.currentTransaction().begin();
			pm.makePersistent(tr);
			pm.currentTransaction().commit();
			TipoDerechoRegEnhancedPk rta = (TipoDerechoRegEnhancedPk) pm.getObjectId(tr);
			return rta.getTipoDerechoRegID();

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		finally {
			pm.close();
		}
	}

	/**
	* Adiciona un objeto de tipo <code>Categoria</code> a la configuración del sistema.
	* <p>
	* El método genera una excepción si ya existe una Categoria con el identificador
	* del objeto pasado como parámetro.
	* @param categoria objeto de tipo <code>Categoria</code> con todos sus atributos.
	* @param usuario que adiciona la categoria de reparto
	* @return identificador del objeto <code>Categoria</code> adicionado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Categoria
	*/
	public CategoriaPk addCategoriaReparto(Categoria categoria, Usuario usuario) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		CategoriaEnhanced categoriar = CategoriaEnhanced.enhance(categoria);

		try {

			//Se intenta agregar una Categoría
			CategoriaEnhancedPk tipoId = new CategoriaEnhancedPk();
			tipoId.idCategoria = categoriar.getIdCategoria();
			CategoriaEnhanced tipor = this.getCategoriaByID(tipoId, pm);

			//Obtener el usuario que adiciona la categoria de reparto
			UsuarioEnhancedPk usuarioEnhId= new UsuarioEnhancedPk();
			usuarioEnhId.idUsuario=usuario.getIdUsuario();
			UsuarioEnhanced usuarioEhn=(UsuarioEnhanced)pm.getObjectById(usuarioEnhId, true);
			
			//Si ya existe una Categoría con el identificador pasado como parámetro
			//se genera una excepción.
			if (tipor != null) {
				throw new DAOException(
					"Ya existe una categoria con el identificador: " + tipor.getIdCategoria());
			}
			
			//Validar que no haya cruce de valores ni de unidades.
			this.validarCruceValoresCategoria(categoria);
			categoriar.setUsuario(usuarioEhn);
			

			pm.currentTransaction().begin();
			pm.makePersistent(categoriar);
			pm.currentTransaction().commit();
			CategoriaEnhancedPk rta = (CategoriaEnhancedPk) pm.getObjectId(categoriar);
			return rta.getCategoriaID();

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		finally {
			pm.close();
		}
	}
	
	/**
	* Edita un objeto de tipo <code>Categoria</code> a la configuración del sistema.
	* <p>
	* El método genera una excepción si no existe una Categoria con el identificador
	* del objeto pasado como parámetro.
	* @param categoria objeto de tipo <code>Categoria</code> con todos sus atributos.
	* @return identificador del objeto <code>Categoria</code> editado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Categoria
	*/
	public CategoriaPk updateCategoriaReparto(Categoria categoria, Usuario usuario) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		CategoriaEnhanced categoriar = CategoriaEnhanced.enhance(categoria);

		try {
			pm.currentTransaction().begin();

			//Se intenta agregar una Categoría
			CategoriaEnhancedPk tipoId = new CategoriaEnhancedPk();
			tipoId.idCategoria = categoriar.getIdCategoria();
			CategoriaEnhanced tipor = this.getCategoriaByID(tipoId, pm);

			//Tener el usuario que va a modificar la categoria
			UsuarioEnhancedPk usuarioEnhId=new UsuarioEnhancedPk();
			usuarioEnhId.idUsuario=usuario.getIdUsuario();
			UsuarioEnhanced usuarioEnh=(UsuarioEnhanced)pm.getObjectById(usuarioEnhId,true);
			
			//Si ya existe una Categoría con el identificador pasado como parámetro
			//se genera una excepción.
			if (tipor == null) {
				throw new DAOException(
					"No existe una categoria con el identificador: " + categoriar.getIdCategoria());
			}
			
			//Validar que no haya cruce de valores ni de unidades.
			this.validarCruceValoresCategoria(categoria);

			tipor.setUsuario(usuarioEnh);
			tipor.setNombre(categoriar.getNombre());
			tipor.setUnidadMax(categoriar.getUnidadMax());
			tipor.setUnidadMin(categoriar.getUnidadMin());
			tipor.setValorMax(categoriar.getValorMax());
			tipor.setValorMin(categoriar.getValorMin());
			tipor.setActivo(categoriar.isActivo());

			pm.currentTransaction().commit();
			
			CategoriaEnhancedPk tipoIdRta = new CategoriaEnhancedPk();
			tipoIdRta.idCategoria = categoriar.getIdCategoria();
			CategoriaEnhanced tiporrta = this.getCategoriaByID(tipoIdRta, pm);
			
			CategoriaPk rta = new CategoriaPk();
			rta.idCategoria = tiporrta.getIdCategoria();
			return rta;
		}catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw e;
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		finally {
			pm.close();
		}
	}
	
	/**
     * @param c
     */
    protected void validarCruceValoresCategoria(Categoria c) throws DAOException {
        try {
        	Query query = null;
        	Query query2 = null;
        	PersistenceManager pm = AdministradorPM.getPM();
        	
            Double valorMaximo = new Double(c.getValorMax());
            Double valorMinimo =  new Double(c.getValorMin());
            Double unidadMaximo =  new Double(c.getUnidadMax());
            Double unidadMinimo =  new Double(c.getUnidadMin());
            
            List categorias = this.getCategoriasReparto("nombre");
            
            boolean hayInterseccion= false;
            
            CategoriaEnhanced conCategoria = null;
            Categoria catTemp= null;
            String idCat = null;

            if ( !c.getIdCategoria().equals(CCategoria.CATEGORIA_QUINTA) && !c.getIdCategoria().equals(CCategoria.CATEGORIA_SEXTA)){ 
            
	            for (Iterator iter = categorias.iterator(); iter.hasNext() && !hayInterseccion;) {
	                catTemp = (Categoria) iter.next();
	                idCat = catTemp.getIdCategoria();
	
	                if (!idCat.equals(c.getIdCategoria())) {
	                    //consulta para ver si se traslapan los valores
	                	query = pm.newQuery(CategoriaEnhanced.class);
	                    query.declareParameters(
	                        "String idCat, double valorMinimo, double valorMaximo");
	                    query.setFilter("this.idCategoria!=idCat &&\n" +
	                    	"this.idCategoria!=\""+ CCategoria.CATEGORIA_QUINTA + "\" &&\n" +	
	                    	"this.idCategoria!=\""+ CCategoria.CATEGORIA_SEXTA + "\" &&\n" +
	                        "((this.valorMin<=valorMinimo &&\n" +
	                        "valorMinimo<=this.valorMax) ||\n" +
	                        "(this.valorMin<=valorMaximo && \n" +
	                        "valorMaximo<=this.valorMax) ||\n" +
	                        "(this.valorMin>=valorMinimo &&\n" +
	                        "this.valorMax<=valorMaximo))");
	
	                    List col = (List) query.execute(c.getIdCategoria(), valorMinimo, valorMaximo);
	                    
	                    if (col.size() > 0) {
	                    	hayInterseccion = true;
	                        conCategoria = (CategoriaEnhanced) col.get(0);
	                    }
	                    
	                    //consulta para ver si se traslapan las unidades
	                    
	                    query2 = pm.newQuery(CategoriaEnhanced.class);
	                    query2.declareParameters(
	                        "String idCat, double unidadMinimo, double unidadMaximo");
	                    query2.setFilter("this.idCategoria!=idCat &&\n" +
		                    "this.idCategoria!=\""+ CCategoria.CATEGORIA_QUINTA + "\" &&\n" +	
		                    "this.idCategoria!=\""+ CCategoria.CATEGORIA_SEXTA + "\" &&\n" +
	                        "((this.unidadMin<=unidadMinimo &&\n" +
	                        "unidadMinimo<=this.unidadMax) ||\n" +
	                        "(this.unidadMin<=unidadMaximo && \n" +
	                        "unidadMaximo<=this.unidadMax) ||\n" +
	                        "(this.unidadMin>=unidadMinimo &&\n" +
	                        "this.unidadMax<=unidadMaximo))");
	
	                    List col2 = (List) query2.execute(c.getIdCategoria(), unidadMinimo,
	                    		unidadMaximo);
	                    
	                    if (col2.size() > 0) {
	                    	hayInterseccion = true;
	                        conCategoria = (CategoriaEnhanced) col2.get(0);
	                    }
	                    
	                }
	            }
            }

            if (query != null) {
                query.closeAll();
            }
            
            pm.close();

            if (hayInterseccion) {
					throw new DAOException("La categoria " + conCategoria.getIdCategoria() +
										" tiene unos valores o unidades que se traslapan con la categoria insertada");                
            }
        } catch (JDOException e) {
            Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

	/**
	* Obtiene una la lista de las Categorías de Reparto Notarial existentes en
	* la configuración del sistema, utilizando como criterio de ordenamiento la
	* cadena pasada como parámetro.
	* @return una lista de objetos <code>Categoria</code> ordenados de acuerdo
	* con el criterio pasado como parámetro.
	* @param orden El criterio de ordenamiento que se utilizará para ordenar la lista
	* de Categorías de Reparto Notarial.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Categoria
	*/
	public List getCategoriasReparto(String orden) throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {

			//Se intenta obtener el listado de Categorías.
			pm.currentTransaction().begin();
			Query q = pm.newQuery(CategoriaEnhanced.class);

			//Se establece como criterio de ordenamiento el campo pasado como parámetro

			q.setOrdering(orden + " ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			while (it.hasNext()) {
				CategoriaEnhanced obj = (CategoriaEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	* Obtiene una la lista de los Tipos de Derechos Registrales existentes en
	* la configuración del sistema, utilizando como criterio de ordenamiento
	* el nombre del objeto.
	* @return una lista de objetos <code>TipoDerechoReg</code> ordenados
	* alfabéticamente de acuerdo al nombre.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoDerechoReg
	*/
	public List getTiposDerechosRegistrales() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {

			//Se intenta obtener el listado de Tipos de Derechos Registrales.
			pm.currentTransaction().begin();
			Query q = pm.newQuery(TipoDerechoRegEnhanced.class);

			//Se establece como criterio de ordenamiento el nombre del tipo de Derecho Registral
			//(Alfabéticamente).
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			//Se adiciona cada uno de los objetos a la lista y se hacen transientes.
			while (it.hasNext()) {
				TipoDerechoRegEnhanced obj = (TipoDerechoRegEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;

	}

	/**
	* Obtiene un objeto de tipo <code>TipoDerechoRegEnhanced</code> dado su identificador.
	* <p>
	* El método retorna null en el caso en el que no encuentre el
	* Tipo de Derecho Registral con el identificador recibido como parámetro.
	* @param taID Identificador del objeto <code>TipoDerechoReg</code> que se quiere
	* recuperar.
	* @param pm PersistenceManager de la transacción.
	* @return objeto <code>TipoDerechoRegEnhanced</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.TipoDerechoReg
	* @see gov.sir.core.negocio.modelo.TipoDerechoRegEnhanced
	* @throws DAOException
	*/
	protected TipoDerechoRegEnhanced getTipoDerechoRegByID(
		TipoDerechoRegEnhancedPk taID,
		PersistenceManager pm)
		throws DAOException {
		TipoDerechoRegEnhanced rta = null;

		if (taID.idTipoDerechoReg != null) {
			try {
				rta = (TipoDerechoRegEnhanced) pm.getObjectById(taID, true);
			}

			//Se retorna null, si no existe el tipo de derecho registral con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
				Log.getInstance().debug(JDOVariablesOperativasDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene un objeto de tipo <code>CategoriaEnhanced</code> dado su identificador.
	* <p>
	* El método retorna null en el caso en el que no encuentre la
	* Categoría con el identificador recibido como parámetro.
	* @param catID Identificador del objeto <code>Categoria</code> que se quiere
	* recuperar.
	* @param pm PersistenceManager de la transacción.
	* @return objeto <code>Categoria</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.Categoria
	* @see gov.sir.core.negocio.modelo.CategoriaEnhanced
	* @throws DAOException
	*/
	protected CategoriaEnhanced getCategoriaByID(CategoriaEnhancedPk catID, PersistenceManager pm)
		throws DAOException {
		CategoriaEnhanced rta = null;

		if (catID.idCategoria != null) {
			try {
				rta = (CategoriaEnhanced) pm.getObjectById(catID, true);
			}

			//Se retorna null, si no existe la Categoría con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
				Log.getInstance().debug(JDOVariablesOperativasDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
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
        
        Connection        con = null;
        PreparedStatement pst = null;
        ResultSet          rs = null;

        boolean transaccionIndependiente = false;
		VersantPersistenceManager pm2 = null;
		
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
			} catch (Exception e) {
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
	* El método retorna null, si no encuentra el secuencial con el identificador dado.
	* @param sID identificador de la secuencia
	* @param pm PersistenceManager de la transaccion
	* @return Secuencia con sus atributos
	* @throws DAOException
	*/
	protected SecuenciasEnhanced getSecuenciaByID(SecuenciasEnhancedPk sID, PersistenceManager pm)
		throws DAOException {
		SecuenciasEnhanced rta = null;

		if (sID.tableName != null) {
			try {
				rta = (SecuenciasEnhanced) pm.getObjectById(sID, true);
			}

			//Se retorna null, si no existe la Secuencia con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	 * Retorna el número máximo de impresiones que son permitidas para un Rol.
	 * @param rol El Rol del usuario del cual se desea obtener el número máximo de impresiones
	 * permitidas.
	 * @param p El proceso asociado al usuario y del cual se desea obtener el número máximo
	 * de impresiones permitidas.
	 * @return el número máximo de impresiones permitidas para la asociación usuario - rol,
	 * recibida como parámetro  o -1 si el rol o el proceso no es válido.
	 * @throws <code>DAOException</code>
	 */
	public int getMaximoImpresionesByRol(Rol rol, Proceso p) throws DAOException {

		String nombre = rol.getRolId();
		String proceso = p.getNombre();
		PersistenceManager pm = AdministradorPM.getPM();

		try {

			//Se intenta realizar la búsqueda sobre la tabla OPLookupCodes, en la cual
			//el type corresponde al Rol y el Code al proceso.
			pm.currentTransaction().begin();
			Query query = pm.newQuery(OPLookupCodesEnhanced.class);
			String Type = proceso;
			String Code;
			int numeroImp = -1;

			//Número de Impresiones Cajero Certificados.
			/*
                         * @author      : Julio Alcázar Rivas
                         * @change      : se agrega al if CRoles.SIR_ROL_REIMPRESION_CERTIFICADOS para obtenes el maximo numero 
                                          de impresiones del rol
                         * Caso Mantis  :   02359
                         */
                        if (nombre.equals(CRoles.CAJERO_CERTIFICADOS) || nombre.equals(CRoles.SIR_ROL_REIMPRESION_CERTIFICADOS)) {

				Code = CImpresion.MAXIMO_IMPRESIONES_CAJERO;
			}

			//Número de impresiones Supervisor (Reimpresión Especial).
			else if (nombre.equals(CRoles.REIMPRESION_ESPECIAL)) {
				Code = CImpresion.MAXIMO_IMPRESIONES_SUPERVISOR;
			}

			//Número de impresiones Rol Entrega (Entrega).
			else if (nombre.equals(CRoles.ENTEGA_CERTIFICADOS)) {
				Code = CImpresion.MAXIMO_IMPRESIONES_ENTREGA;
			} else {
				throw new DAOException("No se pudo obtener el numero de impresiones permitidas para el rol");
			}

			query.declareParameters("String Type, String Code");
			query.setFilter("codigo == Code && tipo == Type");

			Collection results = (Collection) query.execute(Type, Code);

			Iterator it = results.iterator();

			while (it.hasNext()) {

				OPLookupCodesEnhanced obj = (OPLookupCodesEnhanced) it.next();
				String value = obj.getValor();
				Integer valInt = new Integer(value);
				numeroImp = valInt.intValue();

			}
			query.close(results);
			pm.currentTransaction().commit();
			return numeroImp;

		} catch (DAOException de) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,de.getMessage());
			throw new DAOException(de.getMessage(), de);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
	}

	/**
	* Obtiene una la lista de los Tipos de Notas existentes en
	* la configuración del sistema, utilizando como criterio de ordenamiento
	* el nombre del objeto.
	* @return una lista de objetos <code>TipoNota</code> ordenados
	* alfabéticamente de acuerdo al nombre.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoNota
	*/
	public List getTiposNota() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {

			//Se intenta obtener el listado de los Tipos de Notas.
			pm.currentTransaction().begin();
			Query q = pm.newQuery(TipoNotaEnhanced.class);

			//Se establece como criterio de ordenamiento el Tipo de Nota
			//(Alfabéticamente).
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			//Se hacen transientes cada uno de los elementos de la lista.
			while (it.hasNext()) {
				TipoNotaEnhanced obj = (TipoNotaEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	* Agrega una <code>OficinaOrigen</code> a una <code>Categoria</code>
	* @param cat El identificador de la <code>Categoria</code> en la que se va a adicionar
	* la <code>OficinaOrigen</code>
	* <p>
	* El método lanza una excepción si no se encontró la Categoría o la Oficina
	* Origen  con el id recibido como parámetro.
	* @param oficina La <code>OficinaOrigen</code> que se va a asociar a la <code>Categoria</code>
	* @param true o false dependiendo del resultado de la operación.
	* @trhows <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Categoria
	* @see gov.sir.core.negocio.modelo.OficinaOrigen
	*/
	public CategoriaPk addOficinaOrigenToCategoria(CategoriaPk cat, OficinaOrigen oficina)
		throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		CategoriaEnhanced catInicialEnh = null;
		OficinaOrigenEnhanced OficinaOrigenEnh = null;
		CategoriaEnhancedPk catId = new CategoriaEnhancedPk();

		try {
			pm.currentTransaction().begin();

			//Se obtiene la categoría Persistente.

			catId.idCategoria = cat.idCategoria;
			catInicialEnh = this.getCategoriaByID(catId, pm);

			//Se valida la existencia de la categoría en la BD.
			if (catInicialEnh == null) {
				throw new DAOException("No se encontró la Categoría con el ID dado.");
			}

			//Se obtien el Tipo de Oficina.
			TipoOficina tipoOficina = oficina.getTipoOficina();
			TipoOficinaEnhancedPk ofID = new TipoOficinaEnhancedPk();
			ofID.idTipoOficina = tipoOficina.getIdTipoOficina();
			TipoOficinaEnhanced ofEnh = this.getTipoOficinaById(ofID, pm);

			//Se valida la existencia del tipo de oficina en la BD
			if (ofEnh == null) {
				throw new DAOException("No se encontró la Oficina Origen con el ID dado.");
			}

			OficinaOrigenEnhanced oficinaEnh = OficinaOrigenEnhanced.enhance(oficina);
			OficinaCategoriaEnhanced ofOrigenEnh = new OficinaCategoriaEnhanced();
			ofOrigenEnh.setCategoria(catInicialEnh);
			ofOrigenEnh.setOficinaOrigen(oficinaEnh);

			pm.makePersistent(ofOrigenEnh);
			pm.currentTransaction().commit();

			CategoriaEnhanced catFinalEnh = this.getCategoriaByID(catId, pm);
			catId = (CategoriaEnhancedPk) pm.getObjectId(catFinalEnh);
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
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
		} finally {
			pm.close();
		}

		return catId.getCategoriaID();

	}

	/**
	* Actualiza la información de una Categoría de Reparto Notarial.
	* <p>
	* El método genera una excepción si no se encuentra una categoría con el identificador
	* pasado como parámetro.
	* @param cat El identificador de la <code>Categoria</code> que se va a modificar.
	* @param newInfo Un objeto de tipo <code>Categoria</code> con la nueva información
	* que va a ser modificada en la <code>Categoria</code>
	* @return true o false dependiendo del resultado de la operación.
	* @see gov.sir.core.negocio.modelo.Categoria
	*/
	public CategoriaPk updateCategoria(CategoriaPk cat, Categoria newInfo) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		CategoriaEnhanced catInicialEnh = null;
		CategoriaEnhanced catFinalEnh = null;
		CategoriaEnhancedPk catId = new CategoriaEnhancedPk();

		try {

			pm.currentTransaction().begin();

			//Se obtiene la categoría inicial Persistente.

			catId.idCategoria = cat.idCategoria;
			catInicialEnh = this.getCategoriaByID(catId, pm);

			//Se valida la existencia de la categoría en la BD.
			if (catInicialEnh == null) {
				throw new DAOException("No se encontró la Categoría con el ID dado.");
			}

			catFinalEnh = CategoriaEnhanced.enhance(newInfo);

			//Se remplaza la información de la Categoría con los nuevos valores.
			catInicialEnh.setNombre(catFinalEnh.getNombre());
			catInicialEnh.setUnidadMax(catFinalEnh.getUnidadMax());
			catInicialEnh.setValorMax(catFinalEnh.getValorMax());

			pm.currentTransaction().commit();
			catFinalEnh = this.getCategoriaByID(catId, pm);
			catId = (CategoriaEnhancedPk) pm.getObjectId(catFinalEnh);
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
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
		} finally {
			pm.close();
		}

		return catId.getCategoriaID();

	}

	/**
	* Obtiene un objeto de tipo <code>TipoOficinaEnhanced</code> dado su identificador.
	* <p>
	* El método retorna null en el caso en el que no encuentre el
	* Tipo de Oficina con el identificador recibido como parámetro.
	* @param ofID Identificador del objeto <code>TipoOficina</code> que se quiere
	* recuperar.
	* @param pm PersistenceManager de la transacción.
	* @return objeto <code>TipoOficinaEnhanced</code> con todos sus atributos.
	* @see gov.sir.core.negocio.modelo.TipoOficina
	* @see gov.sir.core.negocio.modelo.TipoOficinaEnhanced
	* @throws DAOException
	*/
	protected TipoOficinaEnhanced getTipoOficinaById(TipoOficinaEnhancedPk ofID, PersistenceManager pm)
		throws DAOException {
		TipoOficinaEnhanced rta = null;

		if (ofID.idTipoOficina != null) {
			try {
				rta = (TipoOficinaEnhanced) pm.getObjectById(ofID, true);
			}

			//Se retorna null, si no existe el Tipo de Oficina con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
				Log.getInstance().debug(JDOVariablesOperativasDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene una la lista de los Tipos de Notas existentes en
	* la configuración del sistema que están asociados con el <code>Proceso</code> pasado
	* como parámetro, utilizando como criterio de ordenamiento
	* el nombre del <code>TipoNota</code>
	* <p>
	* El método lanza una excepción si no se encuentra el <code>Proceso</code> con el identificador
	* pasado como parámetro.
	* @return una lista de objetos <code>TipoNota</code> ordenados
	* alfabéticamente de acuerdo al nombre, y que están asociados con el <code>Proceso</code>
	* recibido como parámetro.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoNota
	* @see gov.sir.core.negocio.modelo.Proceso
	*/
	public List getTiposNotaProceso(ProcesoPk proceso) throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();
		JDOProcesosDAO procesosDAO = new JDOProcesosDAO();

		try {

			pm.currentTransaction().begin();
			ProcesoEnhanced procesoPer = procesosDAO.getProcesoByID(new ProcesoEnhancedPk(proceso), pm);

			//Se lanza una excepción si no se encuentra el Proceso con el identificador
			//pasado como parámetro.
			if (procesoPer == null) {
				throw new DAOException("No se encontró el Proceso con el ID dado.");
			}

			//Se realiza la búsqueda utilizando como criterio de ordenamiento el nombre
			//del tipo de nota (Alfabéticamente).
			Query q = pm.newQuery(TipoNotaEnhanced.class);
			q.declareParameters("ProcesoEnhanced proc");
			q.setFilter("this.proceso == proc"); // && devolutiva ==" + String.valueOf(dev));
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute(procesoPer);
			Iterator it = results.iterator();

			while (it.hasNext()) {
				TipoNotaEnhanced obj = (TipoNotaEnhanced) it.next();
				pm.makeTransient(obj.getProceso());
				pm.makeTransient(obj);
				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	* Obtiene una la lista de los Tipos de Notas existentes en
	* la configuración del sistema que están asociados con el <code>Proceso</code> pasado
	* como parámetro, utilizando como filtro el tipo de nota (devolutiva o informativa)
	* <p>
	* El método lanza una excepción si no se encuentra el <code>Proceso</code> con el identificador
	* pasado como parámetro.
	* @return una lista de objetos <code>TipoNota</code> ordenados
	* alfabéticamente de acuerdo al nombre, y que están asociados con el <code>Proceso</code>
	* recibido como parámetro.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoNota
	* @see gov.sir.core.negocio.modelo.Proceso
	*/
	public List getTiposNotaProcesoByTpnaDevolutiva(ProcesoPk proceso, boolean devolutiva) throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();
		JDOProcesosDAO procesosDAO = new JDOProcesosDAO();

		try {

			pm.currentTransaction().begin();
			ProcesoEnhanced procesoPer = procesosDAO.getProcesoByID(new ProcesoEnhancedPk(proceso), pm);

			//Se lanza una excepción si no se encuentra el Proceso con el identificador
			//pasado como parámetro.
			if (procesoPer == null) {
				throw new DAOException("No se encontró el Proceso con el ID dado.");
			}

			//Se realiza la búsqueda utilizando como criterio de ordenamiento el nombre
			//del tipo de nota (Alfabéticamente).
			Query q = pm.newQuery(TipoNotaEnhanced.class);
			q.declareParameters("ProcesoEnhanced proc");
			q.setFilter("this.proceso == proc"); // && devolutiva ==" + String.valueOf(dev));
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute(procesoPer);
			Iterator it = results.iterator();

			while (it.hasNext()) {
				TipoNotaEnhanced obj = (TipoNotaEnhanced) it.next();
				pm.makeTransient(obj.getProceso());
				pm.makeTransient(obj);
				if(obj.isDevolutiva() && devolutiva)
				{
					lista.add(obj);
				}

				if(!obj.isDevolutiva() && !devolutiva)
				{
					lista.add(obj);
				}
			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	* Obtiene el <code>SubtipoAtencion</code> asociado con el <code>SubtipoSolicitud</code>
	* recibido como parámetro.
	* @param subSol  <code>SubtipoSolicitud</code> del cual se desea obtener el <code>SubtipoAtencion</code>
	* asociado.
	* @param pm PersistenceManager de la transacción.
	* @return un objeto de tipo <code>SubtipoAtencion</code> asociado con el
	* <code>SubtipoSolicitud</code> pasado como parámetro.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SubtipoAtencion
	* @see gov.sir.core.negocio.modelo.SubtipoSolicitud
	*/
	protected SubtipoAtencionEnhanced getSubTipoAtencionbySolicitud(
		SubtipoSolicitudEnhanced subSol,
		PersistenceManager pm)
		throws DAOException {
		SubtipoAtencionEnhanced rta = new SubtipoAtencionEnhanced();
		SubtipoAtencionEnhancedPk subAtId = new SubtipoAtencionEnhancedPk();
		List lista = new ArrayList();

		try {
			VersantQuery q = (VersantQuery) pm.newQuery(SubAtencionSubSolicitudEnhanced.class);
			q.setFilter("idSubtipoSol == '" + subSol.getIdSubtipoSol() + "'");
			q.setResult("min(idSubtipoAtencion)");
			String orden = (String) q.execute();

			if (orden == null) {
				throw new DAOException(
					"No se encontró el Subtipo de Atención "
						+ " asociada al Subtipo de Solicitud con el ID dado.");
			}
			subAtId.idSubtipoAtencion = orden;
			rta = this.getSubtipoAtencionByID(subAtId, pm);
			if (rta == null) {
				throw new DAOException("No se encontró el Subtipo de Atención " + " con el ID " + orden);
			}
			q.closeAll();

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		return rta;
	}

	/**
	* Servicio que permite eliminar una <code>Categoría</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param categoria La <code>Categoria</code> que va a ser eliminada.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Categoria
	*/
	public boolean eliminarCategoria(Categoria categoria, Usuario usuario) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		CategoriaEnhanced eliminada = CategoriaEnhanced.enhance(categoria);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer Categoría Persistente
			CategoriaEnhancedPk idCatEnh = new CategoriaEnhancedPk();
			idCatEnh.idCategoria = eliminada.getIdCategoria();

			CategoriaEnhanced catPers = (CategoriaEnhanced) pm.getObjectById(idCatEnh, true);
			if (catPers == null) {
				throw new DAOException("No existe la Categoría con el id " + categoria.getIdCategoria());
			}

			pm.deletePersistent(catPers);
			pm.currentTransaction().commit();
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;

	}

	/**
	* Servicio que permite eliminar un <code>AlcanceGeografico</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param alcance El <code>AlcanceGeografico</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.AlcanceGeografico
	*/
	public boolean eliminarAlcanceGeografico(AlcanceGeografico alcance) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		AlcanceGeograficoEnhanced eliminado = AlcanceGeograficoEnhanced.enhance(alcance);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer Alcance Geográfico Persistente
			AlcanceGeograficoEnhancedPk idAlcEnh = new AlcanceGeograficoEnhancedPk();
			idAlcEnh.idAlcanceGeografico = eliminado.getIdAlcanceGeografico();

			AlcanceGeograficoEnhanced alcPers =
				(AlcanceGeograficoEnhanced) pm.getObjectById(idAlcEnh, true);
			if (alcPers == null) {
				throw new DAOException(
					"No existe el Alcance Geográfico con el id " + alcance.getIdAlcanceGeografico());
			}

			pm.deletePersistent(alcPers);
			pm.currentTransaction().commit();
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}

	/**
	* Servicio que permite eliminar un <code>TipoFotocopia</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param tipoFot El <code>TipoFotocopia</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoFotocopia
	*/
	public boolean eliminarTipoFotocopia(TipoFotocopia tipoFot) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		TipoFotocopiaEnhanced eliminado = TipoFotocopiaEnhanced.enhance(tipoFot);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer Tipo Fotocopia Persistente
			TipoFotocopiaEnhancedPk idFotEnh = new TipoFotocopiaEnhancedPk();
			idFotEnh.idTipoFotocopia = eliminado.getIdTipoFotocopia();

			TipoFotocopiaEnhanced fotPers = (TipoFotocopiaEnhanced) pm.getObjectById(idFotEnh, true);

			if (fotPers == null) {
				throw new DAOException(
					"No existe el Tipo de Fotocopia con el id " + tipoFot.getIdTipoFotocopia());
			}

			pm.deletePersistent(fotPers);
			pm.currentTransaction().commit();
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}

	/**
	* Servicio que permite eliminar un <code>TipoCalculo</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param tipoCalc El <code>TipoCalculo</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoCalculo
	*/
	public boolean eliminarTipoCalculo(TipoCalculo tipoCalc) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		TipoCalculoEnhanced eliminado = TipoCalculoEnhanced.enhance(tipoCalc);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer Tipo Fotocopia Persistente
			TipoCalculoEnhancedPk idCalcEnh = new TipoCalculoEnhancedPk();
			idCalcEnh.idTipoCalculo = eliminado.getIdTipoCalculo();

			TipoCalculoEnhanced calcPers = (TipoCalculoEnhanced) pm.getObjectById(idCalcEnh, true);

			if (calcPers == null) {
				throw new DAOException(
					"No existe el Tipo de Cálculo con el id " + tipoCalc.getIdTipoCalculo());
			}
			pm.deletePersistent(calcPers);
			pm.currentTransaction().commit();
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}

	/**
	* Servicio que permite eliminar un <code>TipoDerechoReg</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param tipoDerecho El <code>TipoDerechoReg</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoDerechoReg
	*/
	public boolean eliminarTipoDerechoRegistral(TipoDerechoReg tipoDerecho) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		TipoDerechoRegEnhanced eliminado = TipoDerechoRegEnhanced.enhance(tipoDerecho);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer Tipo Derecho Registral Persistente
			TipoDerechoRegEnhancedPk idDerEnh = new TipoDerechoRegEnhancedPk();
			idDerEnh.idTipoDerechoReg = eliminado.getIdTipoDerechoReg();

			TipoDerechoRegEnhanced derPers = (TipoDerechoRegEnhanced) pm.getObjectById(idDerEnh, true);

			if (derPers == null) {
				throw new DAOException(
					"No existe el Tipo de Derecho Registral con el id "
						+ tipoDerecho.getIdTipoDerechoReg());
			}

			pm.deletePersistent(derPers);
			pm.currentTransaction().commit();
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}

	/**
	* Servicio que permite eliminar un Tipo de  <code>AccionNotarial</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param accion La <code>AccionNotarial</code> que va a ser eliminada.
	* @param usuario que va eliminar la accion notarial
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.AccionNotarial
	*/
	public boolean eliminarAccionNotarial(AccionNotarial accion, Usuario usuario) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		AccionNotarialEnhanced eliminado = AccionNotarialEnhanced.enhance(accion);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer Acción Notarial Persistente
			AccionNotarialEnhancedPk idAccionEnh = new AccionNotarialEnhancedPk();
			idAccionEnh.idAccionNotarial = eliminado.getIdAccionNotarial();

			AccionNotarialEnhanced accPers =
				(AccionNotarialEnhanced) pm.getObjectById(idAccionEnh, true);

			if (accPers == null) {
				throw new DAOException(
					"No existe el Tipo de Acción Notarial con el id " + accion.getIdAccionNotarial());
			}
			pm.deletePersistent(accPers);
			pm.currentTransaction().commit();
		} catch (Throwable e) {

			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}

	/**
	 * Obtiene un objeto <code>Documento</code> dado su identificador.
	 * <p>Método utilizado para transacciones.
	 * <p>Si no existe un objeto con el identificador dado, el método retorna
	 * null.
	 * @param docID identificador del documento.
	 * @param pm PersistenceManager de la transacción
	 * @return objeto <code>Documento<code> con sus atributos
	 * <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Documento
	 */
	protected DocumentoEnhanced getDocumentoById(DocumentoEnhancedPk docID, PersistenceManager pm)
		throws DAOException {
		DocumentoEnhanced rta = null;

		if (docID.idDocumento != null) {
			try {
				rta = (DocumentoEnhanced) pm.getObjectById(docID, true);
			}

			//No se encontró el objeto con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
				Log.getInstance().debug(JDOVariablesOperativasDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene un TipoPredio dado su identificador, usado para transacciones
	* se debe dar el PersistenceManager
	* @param oid identificador del tipoPredio
	* @return objeto TipoPredio
	* @throws DAOException
	*/
	protected OficinaOrigenEnhanced getOficinaOrigen(
		OficinaOrigenEnhancedPk oid,
		PersistenceManager pm)
		throws DAOException {
		OficinaOrigenEnhanced rta = null;

		if (oid.idOficinaOrigen != null) {
			try {
				rta = (OficinaOrigenEnhanced) pm.getObjectById(oid, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Servicio que permite eliminar un <code>Banco</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param banco El <code>Banco</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Banco
	*/
	public boolean eliminarBanco(Banco banco) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		BancoEnhanced eliminado = BancoEnhanced.enhance(banco);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer Banco Persistente
			BancoEnhancedPk idBancoEnh = new BancoEnhancedPk();
			idBancoEnh.idBanco = eliminado.getIdBanco();

			BancoEnhanced bancoPers = (BancoEnhanced) pm.getObjectById(idBancoEnh, true);

			if (bancoPers == null) {
				throw new DAOException("No existe el Banco con el id " + banco.getIdBanco());
			}
			pm.deletePersistent(bancoPers);
			pm.currentTransaction().commit();
		} catch (Throwable e) {

			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}

	/**
	* Servicio que permite eliminar una <code>SucursalBanco</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param sucursal <code>SucursalBanco</code> que va a ser eliminada.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SucursalBanco
	*/
	public boolean eliminarSucursalBanco(SucursalBanco sucursal) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		SucursalBancoEnhanced eliminado = SucursalBancoEnhanced.enhance(sucursal);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer Sucursal Banco Persistente
			SucursalBancoEnhancedPk idSucursalEnh = new SucursalBancoEnhancedPk();
			idSucursalEnh.idBanco = eliminado.getIdBanco();
			idSucursalEnh.idSucursal = eliminado.getIdSucursal();

			SucursalBancoEnhanced sucursalPers =
				(SucursalBancoEnhanced) pm.getObjectById(idSucursalEnh, true);

			if (sucursalPers == null) {
				throw new DAOException(
					"No existe la Sucursal de Banco con el id de Banco"
						+ ""
						+ sucursal.getIdBanco()
						+ "y el id de sucursal "
						+ sucursal.getIdSucursal());
			}
			pm.deletePersistent(sucursalPers);
			pm.currentTransaction().commit();
		} catch (Throwable e) {

			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}

	/**
	* Servicio que permite eliminar una <code>Tarifa</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param tarifa <code>Tarifa</code> que va a ser eliminada.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Tarifa
	*/
	public boolean eliminarTarifa(Tarifa tarifa) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		TarifaEnhanced eliminado = TarifaEnhanced.enhance(tarifa);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer tarifa Persistente
			TarifaEnhancedPk idTarifaEnh = new TarifaEnhancedPk();
			idTarifaEnh.idCodigo = eliminado.getIdCodigo();
			idTarifaEnh.idTipo = eliminado.getIdTipo();
			idTarifaEnh.idVersion = eliminado.getIdVersion();

			TarifaEnhanced tarifaPers = (TarifaEnhanced) pm.getObjectById(idTarifaEnh, true);

			if (tarifaPers == null) {
				throw new DAOException(
					"No existe la Tarifa con el id de Codigo"
						+ ""
						+ tarifa.getIdCodigo()
						+ " y el id de Tipo "
						+ tarifa.getIdTipo()
						+ "y el id de Version "
						+ tarifa.getIdVersion());
			}
			pm.deletePersistent(tarifaPers);
			pm.currentTransaction().commit();
		} catch (Throwable e) {

			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}

	/**
	* Servicio que permite eliminar un <code>TipoTarifa</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param tipoTarifa <code>TipoTarifa</code> que va a ser eliminada.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoTarifa
	*/
	public boolean eliminarTipoTarifa(TipoTarifa tipoTarifa) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		TipoTarifaEnhanced eliminado = TipoTarifaEnhanced.enhance(tipoTarifa);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer Tipo tarifa Persistente
			TipoTarifaEnhancedPk idTipoTarifaEnh = new TipoTarifaEnhancedPk();
			idTipoTarifaEnh.idTipo = eliminado.getIdTipo();

			TipoTarifaEnhanced tipoTarifaPers =
				(TipoTarifaEnhanced) pm.getObjectById(idTipoTarifaEnh, true);

			if (tipoTarifaPers == null) {
				throw new DAOException(
					"No existe el Tipo de Tarifa con el id " + tipoTarifa.getIdTipo());
			}
			pm.deletePersistent(tipoTarifaPers);
			pm.currentTransaction().commit();
		} catch (Throwable e) {

			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}

	/**
	* Servicio que permite eliminar un <code>SubtipoSolicitud</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param subtipoSolicitud <code>SubtipoSolicitud</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SubtipoSolicitud
	*/
	public boolean eliminarSubtipoSolicitud(SubtipoSolicitud subtipoSolicitud) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		SubtipoSolicitudEnhanced eliminado = SubtipoSolicitudEnhanced.enhance(subtipoSolicitud);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer SubtipoSolicitud Persistente
			SubtipoSolicitudEnhancedPk idSubtipoSolicitudEnh = new SubtipoSolicitudEnhancedPk();
			idSubtipoSolicitudEnh.idSubtipoSol = eliminado.getIdSubtipoSol();

			SubtipoSolicitudEnhanced subtipoPers =
				(SubtipoSolicitudEnhanced) pm.getObjectById(idSubtipoSolicitudEnh, true);

			if (subtipoPers == null) {
				throw new DAOException(
					"No existe el Subtipo de Solicitud con el id " + subtipoSolicitud.getIdSubtipoSol());
			}
			pm.deletePersistent(subtipoPers);
			pm.currentTransaction().commit();
		} catch (Throwable e) {

			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}

	/**
	* Servicio que permite eliminar un objeto <code>OPLookupTypes</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param type Objeto <code>OPLookupTypes</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.OPLookupTypes
	*/
	public boolean eliminarLookupType(OPLookupTypes type) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		OPLookupTypesEnhanced eliminado = OPLookupTypesEnhanced.enhance(type);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer LookupType Persistente
			OPLookupTypesEnhancedPk idLookupTypeEnh = new OPLookupTypesEnhancedPk();
			idLookupTypeEnh.tipo = eliminado.getTipo();

			OPLookupTypesEnhanced typePers =
				(OPLookupTypesEnhanced) pm.getObjectById(idLookupTypeEnh, true);

			if (typePers == null) {
				throw new DAOException("No existe el LookupType con el id " + type.getTipo());
			}
			pm.deletePersistent(typePers);
			pm.currentTransaction().commit();
		} catch (Throwable e) {

			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}

	/**
	* Servicio que permite eliminar un objeto <code>OPLookupCodes</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param code Objeto <code>OPLookupCodes</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.OPLookupCodes
	*/
	public boolean eliminarLookupCode(OPLookupCodes code) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		OPLookupCodesEnhanced eliminado = OPLookupCodesEnhanced.enhance(code);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer LookupCode Persistente
			OPLookupCodesEnhancedPk idLookupCodeEnh = new OPLookupCodesEnhancedPk();
			idLookupCodeEnh.codigo = eliminado.getCodigo();
			idLookupCodeEnh.tipo = eliminado.getTipo();

			OPLookupCodesEnhanced codePers =
				(OPLookupCodesEnhanced) pm.getObjectById(idLookupCodeEnh, true);

			if (codePers == null) {
				throw new DAOException(
					"No existe el LookupCode con el id de Tipo "
						+ code.getTipo()
						+ " y el id de Código "
						+ code.getCodigo());
			}
			pm.deletePersistent(codePers);
			pm.currentTransaction().commit();
		} catch (Throwable e) {

			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}

	/**
	* Servicio que permite eliminar un objeto <code>TipoImpuesto</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param impuesto Objeto <code>TipoImpuesto</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoImpuesto
	*/
	public boolean eliminarTipoImpuesto(TipoImpuesto impuesto) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		TipoImpuestoEnhanced eliminado = TipoImpuestoEnhanced.enhance(impuesto);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer TipoImpuesto Persistente
			TipoImpuestoEnhancedPk idImpuestoEnh = new TipoImpuestoEnhancedPk();
			idImpuestoEnh.idTipoImpuesto = eliminado.getIdTipoImpuesto();

			TipoImpuestoEnhanced impuestoPers =
				(TipoImpuestoEnhanced) pm.getObjectById(idImpuestoEnh, true);

			if (impuestoPers == null) {
				throw new DAOException(
					"No existe el TipoImpuesto con el id" + impuesto.getIdTipoImpuesto());
			}
			pm.deletePersistent(impuestoPers);
			pm.currentTransaction().commit();
		} catch (Throwable e) {

			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}

	/**
	* Servicio que permite eliminar un objeto <code>SubtipoAtencion</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param atencion Objeto <code>SubtipoAtencion</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SubtipoAtencion
	*/
	public boolean eliminarSubtipoAtencion(SubtipoAtencion atencion) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		SubtipoAtencionEnhanced eliminado = SubtipoAtencionEnhanced.enhance(atencion);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer SubtipoAtencion Persistente
			SubtipoAtencionEnhancedPk idAtencionEnh = new SubtipoAtencionEnhancedPk();
			idAtencionEnh.idSubtipoAtencion = eliminado.getIdSubtipoAtencion();

			SubtipoAtencionEnhanced atencionPers =
				(SubtipoAtencionEnhanced) pm.getObjectById(idAtencionEnh, true);

			if (atencionPers == null) {
				throw new DAOException(
					"No existe el Subtipo de Atencion con el id" + atencion.getIdSubtipoAtencion());
			}
			pm.deletePersistent(atencionPers);
			pm.currentTransaction().commit();
		} catch (Throwable e) {

			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}

	/**
	* Servicio que permite eliminar un objeto <code>TipoActo</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param acto Objeto <code>TipoActo</code> que va a ser eliminado.
	* @param usuario que va a modificar el tipo de acto
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoActo
	*/
	public boolean eliminarTipoActo(TipoActo acto, Usuario usuario) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		TipoActoEnhanced eliminado = TipoActoEnhanced.enhance(acto);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer TipoActo Persistente
			TipoActoEnhancedPk idActoEnh = new TipoActoEnhancedPk();
			idActoEnh.idTipoActo = eliminado.getIdTipoActo();

			TipoActoEnhanced actoPers = (TipoActoEnhanced) pm.getObjectById(idActoEnh, true);

			if (actoPers == null) {
				throw new DAOException("No existe el Tipo de Acto con el id" + acto.getIdTipoActo());
			}
			pm.deletePersistent(actoPers);
			pm.currentTransaction().commit();
		} catch (Throwable e) {

			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}

	
	/**
	* Servicio que permite validar si una fase, con una respuesta especifica
	* requiere agregar una nota devolutiva para poder avanzar
	* @param faseId <code>String</code> identificador de la fase.
	* @param respuesta <code>String</code> respuesta con que se desea avanzar la fase.
	* @param procesoId <code>Long</code> identificador del proceso al que pertenece la fase.
	* @return <code>true</code> (si es necesaria la nota) o <code>false</code>
	* (en caso contrario) dependiendo del resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.ValidacionNota
	*/
	public boolean validarNotaDevolutiva(String faseId, String respuesta, long procesoId)
		throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		boolean rta = false;

		try {

			ValidacionNotaEnhancedPk valNotaId = new ValidacionNotaEnhancedPk();
			valNotaId.idFase = faseId;
			valNotaId.idProceso = procesoId;
			valNotaId.idRespuesta = respuesta;

			ValidacionNotaEnhanced valNota = this.getValidacionNotaById(valNotaId, pm);

			if (valNota != null) {
				rta = true;
			}
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return rta;
	}
	
	/**
	* Servicio que obtiene la validacion de notas
	* según si una fase, con una respuesta especifica
	* requiere agregar una nota devolutiva para poder avanzar
	* @param faseId <code>String</code> identificador de la fase.
	* @param respuesta <code>String</code> respuesta con que se desea avanzar la fase.
	* @param procesoId <code>Long</code> identificador del proceso al que pertenece la fase.
	* @return <code>true</code> (si es necesaria la nota) o <code>false</code>
	* (en caso contrario) dependiendo del resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.ValidacionNota
	*/
	public ValidacionNota getNotaDevolutiva(String faseId, String respuesta, long procesoId)
		throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		ValidacionNotaEnhanced valNota = null;
		ValidacionNota valNotaAux = null;

		try {

			ValidacionNotaEnhancedPk valNotaId = new ValidacionNotaEnhancedPk();
			valNotaId.idFase = faseId;
			valNotaId.idProceso = procesoId;
			valNotaId.idRespuesta = respuesta;

			valNota = this.getValidacionNotaById(valNotaId, pm);
			if (valNota != null)
			{
				this.makeTransientValidacionNota(valNota, pm);
				valNotaAux = (ValidacionNota)valNota.toTransferObject();
				if (valNota.getValidacionesNotaDetalle() != null && valNota.getValidacionesNotaDetalle().size() > 0)
				{
					List validacionesNotaDetalle = new ArrayList();
					for (Iterator i = valNota.getValidacionesNotaDetalle().iterator(); i.hasNext();)
					{
						ValidacionNotaDetalleEnhanced valNotaDetTmpEnh = (ValidacionNotaDetalleEnhanced)i.next();
						ValidacionNotaDetalle valNotaDetTmp = (ValidacionNotaDetalle)valNotaDetTmpEnh.toTransferObject();
						TipoNota tipoNota = new TipoNota();
						tipoNota.setIdTipoNota(valNotaDetTmpEnh.getIdTipoNota());
						valNotaDetTmp.setTipoNota(tipoNota);
						validacionesNotaDetalle.add(valNotaDetTmp);
					}
					valNotaAux.setValidacionesNotaDetalle(validacionesNotaDetalle);
				}
			}
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return valNotaAux;
	}

	/**
	 * Obtiene un objeto <code>ValidacionNota</code> dado su identificador.
	 * <p>Método utilizado para transacciones.
	 * <p>Si no existe un objeto con el identificador dado, el método retorna
	 * null.
	 * @param notaValID identificador de la ValidacionNota.
	 * @param pm PersistenceManager de la transacción
	 * @return objeto <code>ValidacionNota<code> con sus atributos
	 * <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.ValidacionNota
	 */
	protected ValidacionNotaEnhanced getValidacionNotaById(
		ValidacionNotaEnhancedPk notaValId,
		PersistenceManager pm)
		throws DAOException {
		ValidacionNotaEnhanced rta = null;

		if (notaValId.idFase != null && notaValId.idRespuesta != null) {
			try {
				rta = (ValidacionNotaEnhanced) pm.getObjectById(notaValId, true);
			}

			//No se encontró el objeto con el identificador dado.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
				Log.getInstance().debug(JDOVariablesOperativasDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Adiciona una <code>ValidacionNota<code> a la configuración del sistema.<p>
	* <p>
	* Se lanza una excepción en el caso en el que ya exista en
	* la base de datos, una <code>ValidacionNota</code> con el identificador pasado dentro
	* del parámetro.
	* @param valNota objeto <code>ValidacionNota</code> con sus atributos, incluido el identificador.
	* @return identificador del ValidacionNota generado.
	* @throws DAOException.
	* @see gov.sir.core.negocio.modelo.ValidacionNota
	*/
	public ValidacionNotaPk addValidacionNota(ValidacionNota valNota) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		ValidacionNotaEnhanced validNota = ValidacionNotaEnhanced.enhance(valNota);

		try {

			//Validación del identificador de la ValidacionNota.
			ValidacionNotaEnhancedPk valNotaId = new ValidacionNotaEnhancedPk();
			valNotaId.idFase = validNota.getIdFase();
			valNotaId.idProceso = validNota.getIdProceso();
			valNotaId.idRespuesta = validNota.getIdRespuesta();

			ValidacionNotaEnhanced valid = this.getValidacionNotaById(valNotaId, pm);

			//Se lanza una excepción en el caso en el que ya exista en
			//la base de datos, una ValidacionNota con el identificador pasado dentro
			//del parámetro.
			if (valid != null) {
				throw new DAOException(
					"Ya existe una ValidacionNota con el identificador: "
						+ valNotaId.idFase
						+ ", "
						+ valNotaId.idProceso
						+ ", "
						+ valNotaId.idRespuesta);
			}

			pm.currentTransaction().begin();
			pm.makePersistent(validNota);
			pm.currentTransaction().commit();

			ValidacionNotaEnhancedPk rta = (ValidacionNotaEnhancedPk) pm.getObjectId(validNota);

			return rta.getValidacionNotaID();
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
	}

	/**
	* Servicio que permite eliminar una <code>ValidacionNota</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param valNota la <code>ValidacionNota</code> que va a ser eliminada.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.ValidacionNota
	*/
	public boolean eliminarValidacionNota(ValidacionNota valNota) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		ValidacionNotaEnhanced val = ValidacionNotaEnhanced.enhance(valNota);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer ValidacionNota Persistente
			ValidacionNotaEnhancedPk valNotaId = new ValidacionNotaEnhancedPk();
			valNotaId.idFase = val.getIdFase();
			valNotaId.idProceso = val.getIdProceso();
			valNotaId.idRespuesta = val.getIdRespuesta();

			ValidacionNotaEnhanced valPers = (ValidacionNotaEnhanced) pm.getObjectById(valNotaId, true);

			if (valPers == null) {
				throw new DAOException(
					"No existe el Banco con el id "
						+ valNotaId.idFase
						+ ", "
						+ valNotaId.idProceso
						+ ", "
						+ valNotaId.idRespuesta);
			}
			pm.deletePersistent(valPers);
			pm.currentTransaction().commit();
		} catch (Throwable e) {

			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}

	/**
	* Obtiene una lista de objetos tipo <code>ValidacionNota </code>, organizada ascendentemente
	* según el id de la fase de la ValidacionNota.
	* @return una lista de objetos <code>ValidacionNota</code>
	* @throws DAOException.
	* @see gov.sir.core.negocio.modelo.ValidacionNota
	*/
	public List getValidacionNotas() throws DAOException {
		List lista = new ArrayList();

		try {
			PersistenceManager pm = AdministradorPM.getPM();

			Query q = pm.newQuery(ValidacionNotaEnhanced.class);

			//Se establece como criterio de ordenamiento el identificador
			//del banco. (Orden ascendente).
			q.setOrdering("proceso.nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			while (it.hasNext()) {
				ValidacionNotaEnhanced obj = (ValidacionNotaEnhanced) it.next();
				pm.makeTransient(obj.getProceso());
				pm.makeTransient(obj);
				lista.add(obj);
			}

			q.close(results);
			pm.close();
		} catch (Exception e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	* Finaliza el servicio Hermod
	*/
	public void finalizar() throws DAOException {
		AdministradorPM.shutdown();
	}


	/* (non-Javadoc)
	 * @see gov.sir.hermod.dao.VariablesOperativasDAO#updateSubtiposAtencionEnUsuario(gov.sir.core.negocio.modelo.Usuario, java.util.List, gov.sir.core.negocio.modelo.Circulo)
	 */
	public boolean updateSubtiposAtencionEnUsuario(Usuario usuario, List listaUsuarios, Circulo circulo ) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			pm.currentTransaction().begin();
			//Validación del identificador del Usuario.
			UsuarioEnhancedPk usuarioId = new UsuarioEnhancedPk();
			usuarioId.idUsuario = usuario.getIdUsuario();

			UsuarioEnhanced usuarioEnh = getUsuarioByID(usuarioId, pm);

			//Se lanza una excepción en el caso que el usuario no exista
			if (usuarioEnh == null) {
				throw new DAOException(
					"No existe un usuario identificado con el id " + usuarioEnh.getIdUsuario());
			}

			List subTiposAnteriores = usuarioEnh.getSubtiposAtencions();

			List nuevosSubtipos = usuario.getSubtiposAtencions();
			List nuevosSubtiposEnh = new ArrayList();

			Set setIDsNuevos = new HashSet();

			for (Iterator iter = nuevosSubtipos.iterator(); iter.hasNext();) {
				UsuarioSubtipoAtencion uSubTip = (UsuarioSubtipoAtencion) iter.next();

				UsuarioSubtipoAtencionEnhancedPk uSubTipID = new UsuarioSubtipoAtencionEnhancedPk();
				uSubTipID.idSubtipoAtencion = uSubTip.getIdSubtipoAtencion();
				uSubTipID.idUsuario = uSubTip.getIdUsuario();
				setIDsNuevos.add(uSubTipID);

				//busca únicamente los nuevos para ser creados
				if (getUsuarioSubtipoAtencionByID(uSubTipID, pm) == null) {
					UsuarioSubtipoAtencionEnhanced uSubTipEnh =
						UsuarioSubtipoAtencionEnhanced.enhance(uSubTip);
					uSubTipEnh.setOrden(determinarOrdenSubtipoAtencion(  uSubTip , circulo, listaUsuarios));
					//dar fecha de creacion					
					uSubTipEnh.setFechaCreacion(new Date());
					nuevosSubtiposEnh.add(uSubTipEnh);
				}
			}

			List borrarSubtiposEnh = new ArrayList();

			if (setIDsNuevos.isEmpty()) {
				//se eliminaron todos los subtipos del usuario
				if (subTiposAnteriores != null && !subTiposAnteriores.isEmpty()) {
					borrarSubtiposEnh.addAll(subTiposAnteriores);
				}
			} else {
				//Se buscan los subtipos que se eliminaron del usuario
				for (Iterator iter = subTiposAnteriores.iterator(); iter.hasNext();) {
					UsuarioSubtipoAtencionEnhanced uSubTip =
						(UsuarioSubtipoAtencionEnhanced) iter.next();

					UsuarioSubtipoAtencionEnhancedPk uSubTipID =
						new UsuarioSubtipoAtencionEnhancedPk();
					uSubTipID.idSubtipoAtencion = uSubTip.getIdSubtipoAtencion();
					uSubTipID.idUsuario = uSubTip.getIdUsuario();

					if (!setIDsNuevos.contains(uSubTipID)) {
						borrarSubtiposEnh.add(uSubTip);
					}
				}
			}


			pm.makePersistentAll(nuevosSubtiposEnh);
			pm.deletePersistentAll(borrarSubtiposEnh);
			usuarioEnh.addListaSubtiposAtencion(nuevosSubtiposEnh);
			usuarioEnh.removeListaSubtiposAtencion(borrarSubtiposEnh);
			pm.currentTransaction().commit();
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		return true;
	}
	
	
	/**
	 * Obtiene un orden en la cola de reparto para un nuevo calificador en un suptipo de atenci'on.
	 * @param usuarioSubtipoAtencion
	 * @param circulo
	 * @param listaUsuarios
	 * @return
	 * @throws DAOException
	 */
	private long determinarOrdenSubtipoAtencion(UsuarioSubtipoAtencion usuarioSubtipoAtencion, Circulo circulo , List listaUsuarios)
		throws DAOException {
		
		long rta = 0;
		if(usuarioSubtipoAtencion!=null){
			
			JDORepartoTurnosDAO turnosDAO = new JDORepartoTurnosDAO();
			
			List subtiposCirculo = turnosDAO.getSubTipoAtencionByUsuario(listaUsuarios, circulo);
			
			if(subtiposCirculo!=null && subtiposCirculo.size()>0){
				List ordenSuptipo = new ArrayList();
				

				
				/*Iterator it = subtiposCirculo.iterator();
				while(it.hasNext()){
		    		UsuarioSubtipoAtencion u = (UsuarioSubtipoAtencion) it.next();
		    		
		    		if(u.getIdSubtipoAtencion()== usuarioSubtipoAtencion.getIdSubtipoAtencion()){
		    			Log.getInstance().debug(JDOVariablesOperativasDAO.class,"Para el subtipo de atencion" + u.getIdSubtipoAtencion() + " esta el orden " + u.getOrden() + " para el usuario " + u.getIdUsuario());
		    			ordenSuptipo.add(new Long(u.getOrden()));
		    		}
		    		
				}*/
				
	            for(Iterator iter = subtiposCirculo.iterator(); iter.hasNext();){
	            	Usuario dato = (Usuario)iter.next();
	            	
	            	List subtiposUsuarioAtencion = dato.getSubtiposAtencions();
	            	//List subtiposList = new ArrayList();
	           		
	            	Iterator it = subtiposUsuarioAtencion.iterator();
					while(it.hasNext()){
			    		UsuarioSubtipoAtencion u = (UsuarioSubtipoAtencion) it.next();
			    		
			    		if(u.getIdSubtipoAtencion().equals(usuarioSubtipoAtencion.getIdSubtipoAtencion())){
			    			ordenSuptipo.add(new Long(u.getOrden()));
			    		}
			    		
					}	            	
	            	
	            	
	            	/*for(Iterator itSubUser = subtiposUsuarioAtencion.iterator(); itSubUser.hasNext();){
	            		UsuarioSubtipoAtencion u = (UsuarioSubtipoAtencion) itSubUser.next();
	            		SubtipoAtencion s = new SubtipoAtencion();
	            		s.setIdSubtipoAtencion(u.getIdSubtipoAtencion());
		           		subtiposList.add(s);
		            	}	
	           		}*/	
	            }
				
				
				
				
	            long fin = 0;
				Collections.sort(ordenSuptipo);
				if(ordenSuptipo.size() > 0 ){
				    long inicio = ((Long)ordenSuptipo.get(0)).longValue();
				    fin = ((Long)ordenSuptipo.get((ordenSuptipo.size()-1))).longValue();
				    long recorreLista = inicio;  
				    
				    for (int x=0;x<ordenSuptipo.size();x++){
				    	Long temp = (Long)ordenSuptipo.get(x);
				    	
				    	if(recorreLista != temp.longValue() && rta==0){
				    		rta = recorreLista;
				    		break;
				    	}		    		
				    	recorreLista++;	
				    }
				}
			    
			    if(rta == 0 || rta == fin ){
			    	rta = fin+1;		    	
			    }			    
			    
			}
		}

		return rta;
	}

	/**
	 * Obtiene un <code>Usuario</code> dado su identificador, método utilizado para transacciones
	 * <p>
	 * En caso de que no se encuentre en la Base de Datos una sucursal con el identificador pasado
	 * como parámetro, se retorna <code>null</code>.
	 * @param sucID identificador de la <code>SucursalBanco</code>.
	 * @param pm PersistenceManager de la transaccion
	 * @return <code>SucursalBanco</code> con sus atributos.
	 * @throws DAOException
	 * @see gov.sir.core.negocio.modelo.Usuario
	 */
	private UsuarioEnhanced getUsuarioByID(UsuarioEnhancedPk usuarioId, PersistenceManager pm)
		throws DAOException {
		UsuarioEnhanced rta = null;

		//if (usuarioId.idUsuario != null) {
		try {
			rta = (UsuarioEnhanced) pm.getObjectById(usuarioId, true);
		} catch (JDOObjectNotFoundException e) {
			rta = null;
		} catch (JDOException e) {
			Log.getInstance().debug(JDOVariablesOperativasDAO.class,e);
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
		//}
		return rta;
	}

	/**
	 * Obtiene un <code>Usuario</code> dado su identificador, método utilizado para transacciones
	 * <p>
	 * En caso de que no se encuentre en la Base de Datos una sucursal con el identificador pasado
	 * como parámetro, se retorna <code>null</code>.
	 * @param sucID identificador de la <code>SucursalBanco</code>.
	 * @param pm PersistenceManager de la transaccion
	 * @return <code>SucursalBanco</code> con sus atributos.
	 * @throws DAOException
	 * @see gov.sir.core.negocio.modelo.Usuario
	 */
	private UsuarioSubtipoAtencionEnhanced getUsuarioSubtipoAtencionByID(
		UsuarioSubtipoAtencionEnhancedPk usuarioId,
		PersistenceManager pm)
		throws DAOException {
		UsuarioSubtipoAtencionEnhanced rta = null;

		try {
			rta = (UsuarioSubtipoAtencionEnhanced) pm.getObjectById(usuarioId, true);
		} catch (JDOObjectNotFoundException e) {
			rta = null;
		} catch (JDOException e) {
			Log.getInstance().debug(JDOVariablesOperativasDAO.class,e);
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
		//}
		return rta;
	}

	/**
	* Adiciona una <code>TipoCertificadoValidacion<code> a la configuración del sistema.<p>
	* <p>
	* Se lanza una excepción en el caso en el que ya exista en
	* la base de datos, una <code>TipoCertificadoValidacion</code> con el identificador pasado dentro
	* del parámetro.
	* @param valNota objeto <code>TipoCertificadoValidacion</code> con sus atributos, incluido el identificador.
	* @return identificador del TipoCertificadoValidacion generado.
	* @throws DAOException.
	* @see gov.sir.core.negocio.modelo.TipoCertificadoValidacion
	*/
	public TipoCertificadoValidacionPk addTipoCertificadoValidacion(TipoCertificadoValidacion tipoCert)
		throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		TipoCertificadoValidacionEnhanced tipoEnh = TipoCertificadoValidacionEnhanced.enhance(tipoCert);

		try {
			//	Traer TipoCertificadoValidacion Persistente
			TipoCertificadoValidacionEnhancedPk oid = new TipoCertificadoValidacionEnhancedPk();
			oid.idTipoCertificado = tipoEnh.getIdTipoCertificado();
			oid.idValidacion = tipoEnh.getIdValidacion();

			TipoCertificadoValidacionEnhanced valid = null;
			try {
				valid = (TipoCertificadoValidacionEnhanced) pm.getObjectById(oid, true);
			} catch (JDOObjectNotFoundException e) {
				valid = null;
			}

			//	Se lanza una excepción en el caso en el que ya exista en
			//la base de datos, una ValidacionNota con el identificador pasado dentro
			//del parámetro.
			if (valid != null) {
				throw new DAOException(
					"Ya existe un Tipo de Certificado Validación con el id "
						+ oid.idTipoCertificado
						+ ", "
						+ oid.idValidacion);
			}

			TipoCertificadoEnhancedPk tcOid = new TipoCertificadoEnhancedPk();
			tcOid.idTipoCertificado = tipoCert.getIdTipoCertificado();
			TipoCertificadoEnhanced tipoCertEnh = null;
			try {
				tipoCertEnh = (TipoCertificadoEnhanced) pm.getObjectById(tcOid, true);
			} catch (JDOObjectNotFoundException e) {
				tipoCertEnh = null;
			}
			if (tipoCertEnh == null) {
				throw new DAOException(
					"No se encontró un Tipo de Certificado con el id " + tcOid.idTipoCertificado);
			}

			ValidacionEnhancedPk vOid = new ValidacionEnhancedPk();
			vOid.idValidacion = tipoCert.getIdValidacion();
			ValidacionEnhanced validacionEnh = null;
			try {
				validacionEnh = (ValidacionEnhanced) pm.getObjectById(vOid, true);
			} catch (JDOObjectNotFoundException e) {
				validacionEnh = null;
			}
			if (validacionEnh == null) {
				throw new DAOException(
					"No se encontró una Validación con el id " + vOid.idValidacion);
			}

			tipoEnh.setTipoCertificado(tipoCertEnh);
			tipoEnh.setValidacion(validacionEnh);

			pm.currentTransaction().begin();
			pm.makePersistent(tipoEnh);
			pm.currentTransaction().commit();

			TipoCertificadoValidacionEnhancedPk rta =
				(TipoCertificadoValidacionEnhancedPk) pm.getObjectId(tipoEnh);

			return rta.getTipoCertificadoValidacionID();
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
	}

	/**
	* Servicio que permite eliminar una <code>TipoCertificadoValidacion</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param valNota la <code>TipoCertificadoValidacion</code> que va a ser eliminada.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoCertificadoValidacion
	*/
	public boolean eliminarTipoCertificadoValidacion(TipoCertificadoValidacion valNota)
		throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		TipoCertificadoValidacionEnhanced val = TipoCertificadoValidacionEnhanced.enhance(valNota);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer TipoCertificadoValidacion Persistente
			TipoCertificadoValidacionEnhancedPk oid = new TipoCertificadoValidacionEnhancedPk();
			oid.idTipoCertificado = val.getIdTipoCertificado();
			oid.idValidacion = val.getIdValidacion();

			TipoCertificadoValidacionEnhanced tipEnh = null;

			try {
				tipEnh = (TipoCertificadoValidacionEnhanced) pm.getObjectById(oid, true);
			} catch (JDOObjectNotFoundException e) {
				tipEnh = null;
			}

			if (tipEnh == null) {
				throw new DAOException(
					"No existe un Tipo de Certificado Validación con el id "
						+ oid.idTipoCertificado
						+ ", "
						+ oid.idValidacion);
			}
			pm.deletePersistent(tipEnh);
			pm.currentTransaction().commit();
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		return true;
	}

	/**
	* Obtiene una lista de objetos tipo <code>TipoCertificadoValidacion </code> filtrada por <code>TipoCertificado</code>
	* @param tipoCertificado  <code>TipoCertificado</code> utilizado para el filtro
	* @return una lista de objetos <code>TipoCertificadoValidacion</code>
	* @throws DAOException.
	* @see gov.sir.core.negocio.modelo.TipoCertificadoValidacion
	*/
	public List getTiposCertificadosValidacionByTipoCertificado(TipoCertificado tipoCertificado)
		throws DAOException {

		List lista = new ArrayList();

		try {
			PersistenceManager pm = AdministradorPM.getPM();

			if (tipoCertificado == null || tipoCertificado.getIdTipoCertificado() == null) {
				throw new DAOException(
					"No existe un Tipo de Certificado identificado con el id "
						+ tipoCertificado.getIdTipoCertificado());
			}

			Query q = pm.newQuery(TipoCertificadoValidacionEnhanced.class);
			q.setFilter("idTipoCertificado == '" + tipoCertificado.getIdTipoCertificado() + "'");
			//Se establece como criterio de ordenamiento el nombre del tipo de certificado
			q.setOrdering("tipoCertificado.nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			while (it.hasNext()) {
				TipoCertificadoValidacionEnhanced obj = (TipoCertificadoValidacionEnhanced) it.next();
				pm.makeTransient(obj.getTipoCertificado());
				pm.makeTransient(obj.getValidacion());
				pm.makeTransient(obj);
				lista.add(obj);
			}

			q.close(results);
			pm.close();
		} catch (Exception e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	* Obtiene una la lista de objetos de tipo <code>Validacion</code> existentes en
	* la configuración del sistema y que estén asociados con el tipo de certificado
	* cuyo id fue pasado como parámetro.
	* <p>
	* El método lanza una excepción si no se encuentra el tipo de certificado con
	* identificador pasado como parámetro.
	* @return una lista de objetos <code>Validacion</code> que estén asociados con
	* el tipo de certificado con identificador pasado como parámetro.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Validacion
	* @see gov.sir.core.negocio.modelo.TipoCertificado
	*/
	public List getValidacionesByTipoCertificado(TipoCertificadoPk oid) throws DAOException {
		TipoCertificadoEnhanced tc = null;
		PersistenceManager pm = AdministradorPM.getPM();
		List rta = new ArrayList();
		ValidacionEnhanced v;

		try {
			tc = this.getTipoCertificadoByID(new TipoCertificadoEnhancedPk(oid), pm);

			// Se lanza una excepción si no se encuentra el tipo de certificado con
			// el identificador pasado como parámetro.
			if (tc == null) {
				throw new DAOException("No se encontró el Tipo de Certificado");
			}

			//Se hace transiente cada una de las validaciones obtenidas.
			for (Iterator itr = tc.getValidaciones().iterator(); itr.hasNext();) {
				v = ((TipoCertificadoValidacionEnhanced) itr.next()).getValidacion();
				pm.makeTransient(v);
				rta.add((Validacion) v.toTransferObject());
			}
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw e;
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return rta;
	}

	/* (non-Javadoc)
	 * @see gov.sir.hermod.dao.VariablesOperativasDAO#getValidaciones()
	 */
	public List getValidaciones() throws DAOException {
		List lista = new ArrayList();

		try {
			PersistenceManager pm = AdministradorPM.getPM();

			Query q = pm.newQuery(ValidacionEnhanced.class);
			//Se establece como criterio de ordenamiento el nombre
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			while (it.hasNext()) {
				ValidacionEnhanced obj = (ValidacionEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			q.close(results);
			pm.close();
		} catch (Exception e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	* Adiciona un <code>TipoNota<code> a la configuración del sistema.<p>
	* <p>
	* Se lanza una excepción en el caso en el que ya exista en
	* la base de datos, una <code>TipoNota</code> con el identificador pasado dentro
	* del parámetro.
	* @param tipoNota objeto <code>TipoNota</code> con sus atributos, incluido el identificador.
	* @param Usuario que va adicionar el tipo de nota
	* @return identificador del TipoNota generado.
	* @throws DAOException.
	* @see gov.sir.core.negocio.modelo.TipoNota
	*/
	public TipoNotaPk  addTipoNota(TipoNota tipoNota, Usuario usuario) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		TipoNotaEnhanced tipoEnh = TipoNotaEnhanced.enhance(tipoNota);

		try {

			pm.currentTransaction().begin();
			
			/*
			long sec = this.getSecuencial(CTipoNota.TABLE_NAME, pm);

			tipoEnh.setIdTipoNota(String.valueOf(sec));
			*/
			if(tipoEnh.getProceso() == null){
				throw new DAOException("Debe proporcionar un proceso");
			}
            
			//Obtener el usuario que adiciona el tipo de nota
			UsuarioEnhancedPk usuarioEnhId= new UsuarioEnhancedPk();
			usuarioEnhId.idUsuario=usuario.getIdUsuario();
			UsuarioEnhanced usuarioEnh=(UsuarioEnhanced)pm.getObjectById(usuarioEnhId, true);
			
			ProcesoEnhancedPk prOid = new ProcesoEnhancedPk();
			prOid.idProceso = tipoNota.getProceso().getIdProceso();
			ProcesoEnhanced procEnh = null;
			try {
				procEnh = (ProcesoEnhanced) pm.getObjectById(prOid, true);
			} catch (JDOObjectNotFoundException e) {
				procEnh = null;
			}
			if (procEnh == null) {
				throw new DAOException(
					"No se encontró un Proceso con el id " + prOid.idProceso );
			}
			tipoEnh.setUsuario(usuarioEnh);
			tipoEnh.setProceso(procEnh);
			pm.makePersistent(tipoEnh);

            TipoNotaEnhancedPk rta =
                (TipoNotaEnhancedPk) pm.getObjectId(tipoEnh);
            TipoNotaPk tipoNotaID = rta.getTipoNotaID();

            pm.currentTransaction().commit();

			return tipoNotaID;
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
			pm.close();
		}
	}

	/**
	* Servicio que permite eliminar un objeto <code>TipoNota</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param valNota la <code>TipoNota</code> que va a ser eliminada.
	* @param usuario que va a eliminar el tipo de nota
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoNota
	*/
	public boolean eliminarTipoNota(TipoNota valNota, Usuario usuario) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		TipoNotaEnhanced val = TipoNotaEnhanced.enhance(valNota);

		try {
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer TipoCertificadoValidacion Persistente
			TipoNotaEnhancedPk oid = new TipoNotaEnhancedPk();
			oid.idTipoNota = val.getIdTipoNota();
                         /*
                         *  @fecha 30/10/2012
                         *  @author Carlos Torres
                         *  @chage   se asigna el atributo version
                         *  @mantis 12621: Acta - Requerimiento No 055_453_Notas_Devolutivas_Causales_Texto_e_Histórico
                        */
                        oid.version = val.getVersion();

			TipoNotaEnhanced tipEnh = null;

			try {
				tipEnh = (TipoNotaEnhanced) pm.getObjectById(oid, true);
			} catch (JDOObjectNotFoundException e) {
				tipEnh = null;
			}

			if (tipEnh == null) {
				throw new DAOException(
					"No existe un Tipo de Nota con el id "	+ oid.idTipoNota);
			}
			pm.deletePersistent(tipEnh);
			pm.currentTransaction().commit();
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		return true;
	}

	/**
	* Adiciona un <code>CheckItem<code> a la configuración del sistema.<p>
	* <p>
	* Se lanza una excepción en el caso en el que ya exista en
	* la base de datos, un <code>CheckItem</code> con el identificador pasado dentro
	* del parámetro.
	* @param item objeto <code>CheckItem</code> con sus atributos, incluido el identificador.
	* @return identificador del <code>CheckItem</code> generado.
	* @throws DAOException.
	* @see gov.sir.core.negocio.modelo.CheckItem
	*/
	public CheckItemPk addCheckItem(CheckItem item) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		CheckItemEnhanced itemEnh = CheckItemEnhanced.enhance(item);

		try {
			//	Traer Objeto Persistente
			CheckItemEnhancedPk oid = new CheckItemEnhancedPk();
			oid.idCheckItem = item.getIdCheckItem();
			oid.idSubtipoSol = item.getIdSubtipoSol();

			CheckItemEnhanced valid = null;
			try {
				valid = (CheckItemEnhanced) pm.getObjectById(oid, true);
			} catch (JDOObjectNotFoundException e) {
				valid = null;
			}

			//	Se lanza una excepción en el caso en el que ya exista en
			//la base de datos, una objeto con el identificador pasado dentro
			//del parámetro.
			if (valid != null) {
				throw new DAOException(
					"Ya existe un Tipo de Nota con el id "	+ oid.idCheckItem + " - "+oid.idSubtipoSol );
			}

			if(item.getSubtipoSolicitud() == null){
				throw new DAOException("Debe proporcionar un Subtipo de Solicitud");
			}

			SubtipoSolicitudEnhancedPk solOid = new SubtipoSolicitudEnhancedPk();
			solOid.idSubtipoSol = item.getSubtipoSolicitud().getIdSubtipoSol();
			SubtipoSolicitudEnhanced subEnh = null;
			try {
				subEnh = (SubtipoSolicitudEnhanced) pm.getObjectById(solOid, true);
			} catch (JDOObjectNotFoundException e) {
				subEnh = null;
			}
			if (solOid == null) {
				throw new DAOException(
					"No se encontró un Subtipo de solicitud con el id " + solOid.idSubtipoSol );
			}

			itemEnh.setSubtipoSolicitud(subEnh);
			pm.currentTransaction().begin();
			pm.makePersistent(itemEnh);
			pm.currentTransaction().commit();

			CheckItemEnhancedPk rta =
				(CheckItemEnhancedPk) pm.getObjectId(itemEnh);

			return rta.getCheckItemID();
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
	}

	/**
	* Edita un <code>CheckItem<code> a la configuración del sistema.<p>
	* <p>
	* Se lanza una excepción en el caso en el que no exista en
	* la base de datos, un <code>CheckItem</code> con el identificador pasado dentro
	* del parámetro.
	* @param item objeto <code>CheckItem</code> con sus atributos, incluido el identificador.
	* @return identificador del <code>CheckItem</code> generado.
	* @throws DAOException.
	* @see gov.sir.core.negocio.modelo.CheckItem
	*/
	public CheckItemPk updateCheckItem(CheckItem item) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		CheckItemEnhanced newData = CheckItemEnhanced.enhance(item);

		try {

			pm.currentTransaction().begin();
			//	Traer Objeto Persistente
			CheckItemEnhancedPk oid = new CheckItemEnhancedPk();
			oid.idCheckItem = item.getIdCheckItem();
			oid.idSubtipoSol = item.getIdSubtipoSol();

			CheckItemEnhanced valid = null;
			try {
				valid = (CheckItemEnhanced) pm.getObjectById(oid, true);
			} catch (JDOObjectNotFoundException e) {
				valid = null;
			}

			//	Se lanza una excepción en el caso en el no ya exista en
			//la base de datos, una objeto con el identificador pasado dentro
			//del parámetro.
			if (valid == null) {
				throw new DAOException(
					"No existe un Tipo de Nota con el id "	+ oid.idCheckItem + " - "+oid.idSubtipoSol );
			}

			//agregar nuevos datos
			valid.setNombre(newData.getNombre());
			valid.setObligatorio(newData.isObligatorio());

			//buscar el subtipo de solicitud y asociarla
			if(newData.getSubtipoSolicitud() == null){
				throw new DAOException("Debe proporcionar un Subtipo de Solicitud");
			}
			SubtipoSolicitudEnhancedPk solOid = new SubtipoSolicitudEnhancedPk();
			solOid.idSubtipoSol = item.getSubtipoSolicitud().getIdSubtipoSol();
			SubtipoSolicitudEnhanced subEnh = null;
			try {
				subEnh = (SubtipoSolicitudEnhanced) pm.getObjectById(solOid, true);
			} catch (JDOObjectNotFoundException e) {
				subEnh = null;
			}
			if (solOid == null) {
				throw new DAOException(
					"No se encontró un Subtipo de solicitud con el id " + solOid.idSubtipoSol );
			}

			valid.setSubtipoSolicitud(subEnh);

			pm.makePersistent(valid);
			pm.currentTransaction().commit();

			CheckItemEnhancedPk rta =
				(CheckItemEnhancedPk) pm.getObjectId(valid);

			return rta.getCheckItemID();
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		finally {
			pm.close();
		}
	}

	/**
	* Servicio que permite eliminar un objeto <code>CheckItem</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param item la <code>CheckItem</code> que va a ser eliminada.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.CheckItem
	*/
	public boolean eliminarCheckItem(CheckItem item) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		CheckItemEnhanced itemEnh = CheckItemEnhanced.enhance(item);

		try {
			//	Traer Objeto Persistente
			CheckItemEnhancedPk oid = new CheckItemEnhancedPk();
			oid.idCheckItem = item.getIdCheckItem();
			oid.idSubtipoSol = item.getIdSubtipoSol();

			CheckItemEnhanced valid = null;
			try {
				valid = (CheckItemEnhanced) pm.getObjectById(oid, true);
			} catch (JDOObjectNotFoundException e) {
				valid = null;
			}

			//	Se lanza una excepción en el caso en el que ya exista en
			//la base de datos, una objeto con el identificador pasado dentro
			//del parámetro.
			if (valid == null) {
				throw new DAOException(
					"No existe un Tipo de Nota con el id "	+ oid.idCheckItem + " - "+oid.idSubtipoSol );
			}

			pm.currentTransaction().begin();
			pm.deletePersistent(valid);
			pm.currentTransaction().commit();

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		return true;
	}

	/**
	* Obtiene una lista de objetos tipo <code>CheckItem </code> filtrada por <code>SubtipoSolicitud</code>
	* @param subtipo  <code>SubtipoSolicitud</code> utilizado para el filtro
	* @return una lista de objetos <code>CheckItem</code>
	* @throws DAOException.
	* @see gov.sir.core.negocio.modelo.CheckItem
	* @see gov.sir.core.negocio.modelo.SubtipoSolicitud
	*/
	public List getCheckItemsBySubtipoSolicitud(SubtipoSolicitud subtipo) throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();
		try {

			if (subtipo == null || subtipo.getIdSubtipoSol() == null) {
				throw new DAOException(	"Debe proporcionar un subtipo de solicitud ");
			}

			Query q = pm.newQuery(CheckItemEnhanced.class);
			q.setFilter("idSubtipoSol == '" + subtipo.getIdSubtipoSol() + "'");
			//Se establece como criterio de ordenamiento el nombre
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			while (it.hasNext()) {
				CheckItemEnhanced obj = (CheckItemEnhanced) it.next();
				pm.makeTransient(obj.getSubtipoSolicitud());
				pm.makeTransient(obj);
				lista.add(obj);
			}
			q.close(results);

		} catch (Exception e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
		finally{
			pm.close();
		}
		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	  * Obtiene un <code>Usuario</code> con todos sus atributos, dado su nombre_de_usuario
	  * y el <code>Circulo</code> al que pertenece.
	  * <p>
	  * Método utilizado para transacciones.
	  * <p>
	  * El método retorna null en el caso en el que no se encuentre el <code>Usuario en la
	  * Base de Datos.
	  * @param username nombre_de_usuario
	  * @param pm  PersistenceManager de la transacción
	  * @param circuloId <code>Circulo</code> asociado al <code>Usuario</code>.
	  * @return <code>Usuario</code> con todos sus atributos.
	  * @see gov.sir.core.negocio.modelo.Circulo
	  * @see gov.sir.core.negocio.modelo.Usuario
	  * @throws DAOException
	  */
	  protected UsuarioEnhanced getUsuarioByLogin(String username, String circuloId, PersistenceManager pm) throws DAOException {

		  UsuarioEnhanced rta = null;
		  String Username = username;
		  String CirculoId = circuloId;

		  //Se realiza la consulta y se establecen como filtros el nombre de usuario
		  //y el Círculo al que pertenece el Usuario.
		  try {
			  Query query = pm.newQuery(UsuarioEnhanced.class);
			  query.declareParameters("String Username, String CirculoId");
			  query.declareVariables("UsuarioCirculoEnhanced c");
			  query.setFilter("username == Username && this.usuarioCirculos.contains(c) && c.idCirculo==CirculoId ");
			  Collection col = (Collection)query.execute(Username, CirculoId);

			  //Si no se obtienen resultados se retorna null.
			  if (col.size() == 0) {
				  rta = null;
			  }
			  else {
				  for (Iterator iter = col.iterator(); iter.hasNext();) {
					  rta = (UsuarioEnhanced)iter.next();
				  }

				  query.closeAll();
			  }
		  }
		  catch (JDOObjectNotFoundException e) {
			  rta = null;
		  }
		  catch (JDOException e) {
			  Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			  throw new DAOException(e.getMessage(), e);
		  }
		  return rta;
	  }

	  /**
	  * Obtiene un <code>Usuario</code> con todos sus atributos, dado su nombre_de_usuario
	  * <p>
	  * Método utilizado para transacciones.
	  * <p>
	  * El método retorna null en el caso en el que no se encuentre el <code>Usuario en la
	  * Base de Datos.
	  * @param username nombre_de_usuario
	  * @param pm  PersistenceManager de la transacción
	  * @return <code>Usuario</code> con todos sus atributos.
	  * @see gov.sir.core.negocio.modelo.Circulo
	  * @see gov.sir.core.negocio.modelo.Usuario
	  * @throws DAOException
	  */
	  protected UsuarioEnhanced getUsuarioByLogin(String username, PersistenceManager pm) throws DAOException {

		  UsuarioEnhanced rta = null;
		  String Username = username;

		  try {

			  //Se realiza la consulta estableciendo como filtro el username
			  //del usuario recibido como parámetro.
			  Query query = pm.newQuery(UsuarioEnhanced.class);
			  query.declareParameters("String Username");
			  query.setFilter("username == Username");

			  Collection col = (Collection)query.execute(Username);

			  //Si no se obtienen resultados se retorna null.
			  if (col.size() == 0) {
				  rta = null;
			  }
			  else {
				  for (Iterator iter = col.iterator(); iter.hasNext();) {
					  rta = (UsuarioEnhanced)iter.next();
				  }

				  query.closeAll();
			  }
		  }
		  catch (JDOObjectNotFoundException e) {
			  rta = null;
		  }
		  catch (JDOException e) {
			  Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			  throw new DAOException(e.getMessage(), e);
		  }

		  return rta;
	  }

	/**
	* Obtiene una lista con los nombres de los tipos de visibilidad asociados con las notas
	* informativas existentes en el sistema.
	* @return Lista con los nombres de los tipos de visibilidad existentes en el sistema.
	* @throws DAOException.
	*/
	public List getTiposVisibilidad() throws DAOException
	{

		List lista = new ArrayList();

		try {
			PersistenceManager pm = AdministradorPM.getPM();

			//Se obtiene la constante para determinar la visibilidad en la tabla
			//de lookupTypes.
			String visibilidad = COPLookupTypes.VISIBILIDAD;

			Query q = pm.newQuery(OPLookupCodesEnhanced.class);
			q.setFilter("tipo == '" + visibilidad + "'");
			//Se establece como criterio de ordenamiento el nombre del tipo de visibilidad
			q.setOrdering("codigo ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			while (it.hasNext()) {
				OPLookupCodesEnhanced obj = (OPLookupCodesEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			q.close(results);
			pm.close();
		} catch (Exception e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

	/**
	* Devuelve el número de anotaciones del folio indicado,
	* si el folio no existe retorna 0
	* Tomado de Forsetti
	* @param matricula
	* @return
	* @throws DAOException
	*/
	protected long getCountAnotacionesFolio(String matricula)
		throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		 long rta = 0;

		 try {
			 pm.currentTransaction().begin();
			 FolioEnhanced fol = this.getFolioByMatricula(matricula, pm);
			 if (fol != null) {
				 //Se debe buscar por algún criterio las anotaciones
				 VersantQuery query = (VersantQuery) pm.newQuery(AnotacionEnhanced.class);
				 query.declareParameters("String matricula");

				 String filtro = "this.idMatricula==matricula && this.estado.idEstadoAn!='" +
					 CEstadoAnotacion.OBSOLETA + "' ";
				 query.setFilter(filtro);
				 query.setResult("count(this)");
				 rta = ((Long) query.execute(matricula)).longValue();
			 }
		 } catch (JDOException e) {
			 if (pm.currentTransaction().isActive()) {
					pm.currentTransaction().rollback();
			 }
			 Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			 throw new DAOException(e.getMessage(), e);
		 } catch (Exception e) {
				if (pm.currentTransaction().isActive()) {
					pm.currentTransaction().rollback();
				}

				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		 finally {
			 if(pm.currentTransaction().isActive()){
				 pm.currentTransaction().commit();
			 }
			 pm.close();
		 }

		 return rta;
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
			Query query = pm.newQuery(FolioEnhanced.class);
			query.declareParameters("String matricula");
			query.setFilter("idMatricula == matricula");

			Collection col = (Collection) query.execute(matricula);

			Iterator iter = col.iterator();

			if (!iter.hasNext()) {
				rta = null;
			} else {
				rta = (FolioEnhanced) iter.next();

				//Los folios en estado OBSOLETO no existen, están en la BD por auditoría
				if (rta.getEstado().getIdEstado().equals(CEstadoFolio.OBSOLETO)) {
					rta = null;
				}

				query.closeAll();
			}
		} catch (JDOException e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

		return rta;
	}

    /**
     * Obtiene una la lista de los Tipos de Notas existentes en la configuración
     * del sistema que están asociados con el
     * <code>Proceso</code> pasado como parámetro, la fase y si es devolutivo o
     * informativa. <p> El método lanza una excepción si no se encuentra el
     * <code>Proceso</code> con el identificador pasado como parámetro.
     *
     * @return una lista de objetos
     * <code>TipoNota</code> ordenados alfabéticamente de acuerdo al nombre, y
     * que están asociados con el
     * <code>Proceso</code> recibido como parámetro.
     * @throws
     * <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.TipoNota
     * @see gov.sir.core.negocio.modelo.Proceso
     */
    public List getTiposNotaProceso(ProcesoPk proceso, String fase, boolean devolutiva) throws DAOException {
        
        List<TipoNotaEnhanced> lista          = new ArrayList<TipoNotaEnhanced>();
        List<TipoNotaEnhanced> listaRespuesta = new ArrayList<TipoNotaEnhanced>();
        
        PersistenceManager pm = AdministradorPM.getPM();
        JDOProcesosDAO procesosDAO = new JDOProcesosDAO();
        Boolean dev = new Boolean(devolutiva);
        try {

			pm.currentTransaction().begin();
			ProcesoEnhanced procesoPer = procesosDAO.getProcesoByID(new ProcesoEnhancedPk(proceso), pm);

			//Se lanza una excepción si no se encuentra el Proceso con el identificador
			//pasado como parámetro.
			if (procesoPer == null) {
				throw new DAOException("No se encontró el Proceso con el ID dado.");
			}
                        /*** @author : HGOMEZ, FPADILLA
                         *** @change : Se valida que solo se desplieguen notas debolutivas
                         *** correspondientes a la mayor version de cada una de ellas.
                         *** Caso Mantis : 12621
                         */
			//Se realiza la búsqueda utilizando como criterio de ordenamiento el nombre
			//del tipo de nota (Alfabéticamente).
			Query q = pm.newQuery(TipoNotaEnhanced.class);
			q.declareParameters("ProcesoEnhanced proc, String fase, Boolean dev");
			q.setFilter("this.proceso == proc && this.fase==fase && this.devolutiva==dev"); // && devolutiva ==" + String.valueOf(dev));
			q.setOrdering("idTipoNota ascending, version descending");
			Collection results = (Collection) q.execute(procesoPer, fase, dev);
			Iterator it = results.iterator();

            String idTipoNota = "";
            
            while (it.hasNext()) {
                TipoNotaEnhanced obj = (TipoNotaEnhanced) it.next();
                if (!idTipoNota.equals(obj.getIdTipoNota())) {
                    lista.add(obj);
                    idTipoNota = obj.getIdTipoNota();
                }
            }

            for(TipoNotaEnhanced tn : lista) {
                if (tn.getActivo() == 1) {
                    pm.makeTransient(tn.getProceso());
                    pm.makeTransient(tn);
                    listaRespuesta.add(tn);
                }
            }            
            
            pm.currentTransaction().commit();
            q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

        listaRespuesta = TransferUtils.makeTransferAll(listaRespuesta);
        return listaRespuesta;
    }

    /**
     * * @author : HGOMEZ, FPADILLA ** @change : Obtiene una la lista de los
     * Tipos de Notas existentes ordenados ** por idTipoNota en forma ascendente
     * y por version en forma descendente. ** Caso Mantis : 12621
     */
    public List<gov.sir.core.negocio.modelo.constantes.TipoNotaModel> getListadoTiposNotaVersion() throws DAOException {

        List<gov.sir.core.negocio.modelo.constantes.TipoNotaModel> listaNotas = new ArrayList();
        List<gov.sir.core.negocio.modelo.constantes.TipoNotaModel> listaRespuesta = new ArrayList<gov.sir.core.negocio.modelo.constantes.TipoNotaModel>();
        
        PersistenceManager pm = AdministradorPM.getPM();
        try {

			pm.currentTransaction().begin();

			//Se realiza la búsqueda utilizando como criterio de ordenamiento el nombre
			//del tipo de nota (Alfabéticamente).
			Query q = pm.newQuery(TipoNotaEnhanced.class);
			q.setOrdering("idTipoNota ascending, version descending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

            String idTipoNota = "";
            while (it.hasNext()) {
                TipoNotaEnhanced obj = (TipoNotaEnhanced) it.next();
                if (!idTipoNota.equals(obj.getIdTipoNota())) {
                    gov.sir.core.negocio.modelo.constantes.TipoNotaModel nota = new gov.sir.core.negocio.modelo.constantes.TipoNotaModel();
                    nota.setIdTipoNota(obj.getIdTipoNota());
                    nota.setVersion(obj.getVersion());
                    nota.setActivo(obj.getActivo());
                    listaNotas.add(nota);
                    idTipoNota = obj.getIdTipoNota();
                }
            }

            for(gov.sir.core.negocio.modelo.constantes.TipoNotaModel tn : listaNotas) {
                if (tn.getActivo() == 1) {
                    listaRespuesta.add(tn);
                }
            }      
            
            pm.currentTransaction().commit();
            q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

        return listaRespuesta;
    }


	/**
	* Obtiene una la lista de los Tipos de Notas existentes en
	* la configuración del sistema que están asociados con el <code>Proceso</code> pasado
	* como parámetro y la fase
	* <p>
	* El método lanza una excepción si no se encuentra el <code>Proceso</code> con el identificador
	* pasado como parámetro.
	* @return una lista de objetos <code>TipoNota</code> ordenados
	* alfabéticamente de acuerdo al nombre, y que están asociados con el <code>Proceso</code>
	* recibido como parámetro.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoNota
	* @see gov.sir.core.negocio.modelo.Proceso
	*/
	public List getTiposNotaProceso(ProcesoPk proceso, String fase) throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();
		JDOProcesosDAO procesosDAO = new JDOProcesosDAO();
		try {

			pm.currentTransaction().begin();
			ProcesoEnhanced procesoPer = procesosDAO.getProcesoByID(new ProcesoEnhancedPk(proceso), pm);

			//Se lanza una excepción si no se encuentra el Proceso con el identificador
			//pasado como parámetro.
			if (procesoPer == null) {
				throw new DAOException("No se encontró el Proceso con el ID dado.");
			}

			//Se realiza la búsqueda utilizando como criterio de ordenamiento el nombre
			//del tipo de nota (Alfabéticamente).
			Query q = pm.newQuery(TipoNotaEnhanced.class);
			q.declareParameters("ProcesoEnhanced proc, String fase");
			q.setFilter("this.proceso == proc && this.fase==fase"); // && devolutiva ==" + String.valueOf(dev));
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute(procesoPer, fase);
			Iterator it = results.iterator();

			while (it.hasNext()) {
				TipoNotaEnhanced obj = (TipoNotaEnhanced) it.next();
				pm.makeTransient(obj.getProceso());
				pm.makeTransient(obj);
				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
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
	public TipoNotaPk updateTipoNota (TipoNota tipoNota,Usuario usuario) throws DAOException
	{

		PersistenceManager pm = AdministradorPM.getPM();
		TipoNotaEnhanced notaInicialEnh = null;
		TipoNotaEnhanced notaFinalEnh = null;
		TipoNotaEnhancedPk notaId = new TipoNotaEnhancedPk();
		TipoNotaPk idFinal = new TipoNotaPk();

		try {

			pm.currentTransaction().begin();

			//Se obtiene el tipo de nota inicial Persistente.

			notaId.idTipoNota = tipoNota.getIdTipoNota();
                        /*
                         *  @fecha 30/10/2012
                         *  @author Carlos Torres
                         *  @chage   se asigna el atributo version
                         *  @mantis 12621: Acta - Requerimiento No 055_453_Notas_Devolutivas_Causales_Texto_e_Histórico
                        */
                        notaId.version = tipoNota.getVersion();
			notaInicialEnh = (TipoNotaEnhanced) pm.getObjectById(notaId,true);

//			Obtener el usuario que adiciona el tipo de nota
			UsuarioEnhancedPk usuarioEnhId= new UsuarioEnhancedPk();
			usuarioEnhId.idUsuario=usuario.getIdUsuario();
			UsuarioEnhanced usuarioEnh=(UsuarioEnhanced)pm.getObjectById(usuarioEnhId, true);
			
			//Se valida la existencia del Tipo de Nota en la BD.
			if (notaInicialEnh == null) {
				throw new DAOException("No se encontró el Tipo de Nota con el ID dado.");
			}

			//Se obtiene el proceso persistente.
			ProcesoEnhancedPk procesoId = new ProcesoEnhancedPk();
			procesoId.idProceso = tipoNota.getProceso().getIdProceso();
			ProcesoEnhanced procesoEnh = (ProcesoEnhanced) pm.getObjectById(procesoId,true);
			if (procesoEnh == null) {
				throw new DAOException("No se encontró el Proceso con el ID dado.");
			}

			notaFinalEnh = TipoNotaEnhanced.enhance(tipoNota);

			//Se remplaza la información del Tipo de Nota con los nuevos valores.
			notaInicialEnh.setDescripcion(notaFinalEnh.getDescripcion());
			notaInicialEnh.setDevolutiva(notaFinalEnh.isDevolutiva());
			notaInicialEnh.setFase(notaFinalEnh.getFase());
			notaInicialEnh.setNombre(notaFinalEnh.getNombre());
			notaInicialEnh.setProceso(procesoEnh);
			notaInicialEnh.setVisibilidad(notaFinalEnh.getVisibilidad());
			notaInicialEnh.setUsuario(usuarioEnh);
			pm.currentTransaction().commit();
			idFinal.idTipoNota = notaId.idTipoNota;
			/** @author : HGOMEZ, FPADILLA
                	*** @change : Ajustes respectivos para setear versión del Tipo de Nota.
                	*** Caso Mantis : 12621
                	*/

                        idFinal.version = notaId.version;

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
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
		} finally {
			pm.close();
		}

		return idFinal;

	}



	/**
	 * Obtiene los permisos de cerrección confirurados en el sistema
	 * @return Lista con objetos de tipo PermisoCorreccion
	 * @throws DAOException
	 */
	public List getPermisosCorreccion() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			//Se establece como criterio de ordenamiento el nombre del causal de restitución.
			//(Alfabéticamente).
			pm.currentTransaction().begin();
			Query q = pm.newQuery(PermisoCorreccionEnhanced.class);
			q.setOrdering("idPermiso ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			//Se hacen transientes cada uno de los objetos de la lista de causales de restitución.
			while (it.hasNext()) {
				PermisoCorreccionEnhanced obj = (PermisoCorreccionEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			q.close(results);
			pm.currentTransaction().commit();

		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}


	/**
	 * Servicio que realiza una espera hasta que un objeto de la base de datos
	 * sea recuperado según su identificador.
	 * El tiempo límite de espera en segundos es configurable.
	 * @param objectId identificador del objeto que va a ser recuperado.
	 * @param segundos Segundos máximos que deben esperarse en la búsqueda.
	 *
	 */
	 public boolean getObjectByLlavePrimaria (Object objectId, int segundos, PersistenceManager pm) throws DAOException
	 {
	 	boolean respuesta = false;
	 	boolean condicion = true;
	 	boolean condicionFecha = true;
	 	Object objetoRecuperado = null;

	 	//Se obtiene la fecha inicial de la operacion en milisegundos.
	 	Date fechaInicio = new Date();
	 	long milisegundosInicio = fechaInicio.getTime();

	 	while (condicion)
	 	{

	 	    Date fechaIntermedia = new Date();
	 	    long milisegundosFinal = fechaIntermedia.getTime();

	 	    //Verificar que no se haya sobrepasado el tiempo de espera.
	 	    long diferencia = milisegundosFinal - milisegundosInicio;
	 	    if (diferencia > 1000 * segundos)
	 	    {
	 	    	condicionFecha = false;
	 	    	condicion = false;
	 	    }

	 	    if (condicionFecha)
	 	    {
	 	    	try
	 	    	{
                     //Se hace una espera
	 	    		objetoRecuperado = (Object) pm.getObjectById(objectId,true);
	 	    		//Se encontró el objeto.
	 	    		if (objetoRecuperado != null)
	 	    		{
	 	    			condicion = false;
	 	    		}
	 		 	}

	 	        catch (JDOObjectNotFoundException jdoException)
	 	       {
	 				try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
					}
	 	       }
			   catch (Throwable t)
			   {
	 				t.printStackTrace();
			   }
	 	    }
	 	}

	 	//Si el método terminó porque se agotó el tiempo de espera, se retorna false.
	 	if (condicionFecha == false)
	 	{
	 		return condicionFecha;
	 	}

	 	//Si el método terminó porque se encontró el objeto, se retorna true;
	 	else
	 	{
			return true;
	 	}

	 }




	/**
	* Obtiene el listado de intereses jurídicos.
	* @return Listado de intereses jurídicos.
	* @throws Throwable
	*/
	public List getInteresesJuridicos () throws DAOException
	{
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try
		{



		    //Se obtiene la constante para determinar los intereses jurídicos en la tabla
			//de lookupTypes.
			String interesJuridico = COPLookupTypes.INTERES_JURIDICO;

			Query q = pm.newQuery(OPLookupCodesEnhanced.class);
			q.setFilter("tipo == '" + interesJuridico + "'");

			//Se establece como criterio de ordenamiento el nombre del interés jurídico.
			q.setOrdering("codigo ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			while (it.hasNext())
			{
				OPLookupCodesEnhanced obj = (OPLookupCodesEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			q.close(results);

		}

		catch (Exception e)
		{
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}

		finally
		{
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}


	/**
	* Obtiene el listado de pantallas administrativas visibles para un respectivo rol.
	* @param roles Listado de roles asociados con el usuario.
	* @return Listado de pantallas administrativas visibles para el rol recibido como parámetro.
	* @throws DAOException
	*/
	public List obtenerAdministrativasPorRol (List roles)throws DAOException
	{
		List listaPantallasEnh = new ArrayList();
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try
		{


			String p_PaginaId = "PG_ADMINISTRATIVA";

			String filtro = "p_roles.contains(this.idRol)"
				+ "&& this.pantalla.pagina == p_PaginaId";
			String orden = "this.pantalla.idPantallaAdministrativa ascending";
			String parametros = "String p_PaginaId, Collection p_roles";

			Collection rolesString = new ArrayList();
			for (Iterator rolesArItera = roles.iterator(); rolesArItera.hasNext();)
			{
				rolesString.add(((Rol)rolesArItera.next()).getRolId());
			}

			Query query = pm.newQuery(RolPantallaEnhanced.class);
			query.setOrdering(orden);
			query.declareParameters(parametros);
			query.setFilter(filtro);
			
			listaPantallasEnh = (List)query.execute(p_PaginaId, rolesString);
			List listaPantallasTmp = new ArrayList();
			
			if (listaPantallasEnh != null)
			{
				for (Iterator pantallasEnhItera = listaPantallasEnh.iterator(); 
						pantallasEnhItera.hasNext(); )
				{
					RolPantallaEnhanced rolPantallaEnh = (RolPantallaEnhanced)pantallasEnhItera.next();
					PantallaAdministrativaEnhanced pantallaEnh = rolPantallaEnh.getPantalla();
					pm.makeTransient(pantallaEnh.getTipoPantalla());
                    pm.makeTransient (pantallaEnh);
					listaPantallasTmp.add(pantallaEnh);
				}
			}
			lista = TransferUtils.makeTransferAll(listaPantallasTmp);
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			Log.getInstance().error(JDOVariablesOperativasDAO.class,t.getMessage(), t);
			throw new DAOException(t.getMessage(), t);
		}
		finally
		{
			pm.close();
		}
		
		return lista;
	}
	
	
	/**
	* Obtiene el listado de pantallas administrativas visibles para un respectivo rol.
	* @param roles Listado de roles asociados con el usuario.
	* @return Listado de pantallas administrativas visibles para el rol recibido como parámetro.
	* @throws DAOException
	*/
	public List obtenerRolPantallasAdministrativasPorRolPagina (List roles, String Pagina)throws DAOException
	{
		List listaPantallasEnh = new ArrayList();
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();
		RolSIREnhanced rolEnh;

		try
		{

                  String p_PaginaId = Pagina;
                  String p_RolId = "";


                  Query query = pm.newQuery(RolPantallaEnhanced.class);
                  query.setOrdering("this.pantalla.idPantallaAdministrativa ascending");
                  query.declareParameters("String p_PaginaId, String p_RolId");
                  query.setFilter(
                        "this.idRol == p_RolId"
                      + "&& this.pantalla.pagina == p_PaginaId"
                  );


			for (int j=0; j<roles.size(); j++)
			{
				Rol rolAuriga = (Rol) roles.get(j);
				RolSIREnhancedPk idRol = new RolSIREnhancedPk();
				idRol.idRol = rolAuriga.getRolId();
				try
				{
					rolEnh = (RolSIREnhanced) pm.getObjectById(idRol,true);
                    p_RolId = rolEnh.getIdRol();
                    Collection col = (Collection)query.execute(p_PaginaId, p_RolId);

                    Iterator iterator = col.iterator();
                    for( ; iterator.hasNext(); ) {

                      RolPantallaEnhanced rolPantEnh = (RolPantallaEnhanced)iterator.next();
                      PantallaAdministrativaEnhanced pantEnh = rolPantEnh.getPantalla();
                      pm.makeTransient(pantEnh.getTipoPantalla());
                      pm.makeTransient (pantEnh);
                      pm.makeTransient(rolPantEnh.getRol());
                      pm.makeTransient (rolPantEnh);
                      listaPantallasEnh.add(rolPantEnh);
                    }

				}
                                catch( Throwable t ) {

				}


			}
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			Log.getInstance().error(JDOVariablesOperativasDAO.class,t.getMessage(), t);
			throw new DAOException(t.getMessage(), t);
		}
		finally
		{
			pm.close();
		}


		lista = TransferUtils.makeTransferAll(listaPantallasEnh);
		return lista;
	}
	
	

	/**
	* Obtiene el listado de tipos de pantallas administrativas existentes en la aplicación.
	* @return Listado de tipos de pantallas administrativas.
	* @throws DAOException
	*/
	public List obtenerTiposPantallasAdministrativas ()throws DAOException
	{
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();


		try
		{
			Query q = pm.newQuery(TipoPantallaEnhanced.class);

            // Se establece como criterio de ordenamiento el nombre del tipo de pantalla
			q.setOrdering("idTipoPantalla ascending");

			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			while (it.hasNext())
			{
				TipoPantallaEnhanced obj = (TipoPantallaEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			q.close(results);

		}

		catch (Throwable t)
		{
			t.printStackTrace();
			Log.getInstance().error(JDOVariablesOperativasDAO.class,t.getMessage(), t);
			throw new DAOException(t.getMessage(), t);
		}

		finally
		{
			pm.close();
		}



		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}


        /**
        * Obtiene el listado de pantallas administrativas visibles para un respectivo rol.
        * @param roles Listado de roles asociados con el usuario.
        * @return Listado de pantallas administrativas visibles para el rol recibido como parámetro.
        * @throws DAOException
        */
        public List obtenerPantallasPaginaReportesPorRol( List roles ) throws DAOException {

          // List< PantallAdministrativa >
          List local_Result = new ArrayList();

          // PersistenceManager (already initialized)
          PersistenceManager pm = AdministradorPM.getPM();

          // not repeated / ordering according id.
          // Map < PantallaAdministrativaEnhanced.ID, PantallaAdministrativaEnhanced >
          Map local_MapRepresentation;
          // local_MapRepresentation = new java.util.HashMap();
          local_MapRepresentation = new java.util.TreeMap(
            new java.util.Comparator() {
      public boolean equals(Object obj) {
        return false;
      }

      public int compare(Object o1, Object o2) {
        if( !( ( o1 instanceof PantallaAdministrativaEnhancedPk )
            &&( o2 instanceof PantallaAdministrativaEnhancedPk ) ) ){
          return -1;
        }

        PantallaAdministrativaEnhancedPk p1 = (PantallaAdministrativaEnhancedPk)o1;
        PantallaAdministrativaEnhancedPk p2 = (PantallaAdministrativaEnhancedPk)o2;

        return( (int) ( p1.idPantallaAdministrativa - p2.idPantallaAdministrativa ) );

      }

    }

          );

          /*
          try {

            PantallaAdministrativaEnhanced pantallaadministrativaenhanced;

            String p_PaginaId = "PG_REPORTES";
            String p_RolId = "";
            RolSIREnhanced rolsirenhanced;

            // pm.currentTransaction().begin();
            VersantQuery query = (VersantQuery)pm.newQuery(RolPantallaEnhanced.class);
            query.declareVariables("PantallaAdministrativaEnhanced pantallaadministrativaenhanced;");
            query.declareParameters("String p_PaginaId, String p_RolId");
            query.setUnique(false);
            query.setFilter(
                  "pantallaadministrativaenhanced.pagina == p_PaginaId "
              +   "&& this.pantalla == pantallaadministrativaenhanced "
              +   "&& this.rol.idRol == p_RolId"
            );
            query.setResult("this.pantalla");
            query.setGrouping("this.pantalla");


            org.auriga.core.modelo.transferObjects.Rol local_RolAuriga;
            RolSIREnhanced.ID local_RolSirID;
            PantallaAdministrativaEnhanced.ID local_PantallaAdministrativaID;

            for( Iterator iterator = roles.iterator(); iterator.hasNext(); ) {

              local_RolAuriga = (org.auriga.core.modelo.transferObjects.Rol)iterator.next();
              local_RolSirID = new RolSIREnhanced.ID();
              local_RolSirID.idRol = local_RolAuriga.getRolId();

              try {
                rolsirenhanced = (RolSIREnhanced) pm.getObjectById(local_RolSirID, true);
                p_RolId = rolsirenhanced.getIdRol();
              }
              catch( Exception e ){
                continue;
              }
              // put parameters + execute

              Collection col = (Collection)query.execute( p_PaginaId, p_RolId );
              for (Iterator iter = col.iterator(); iter.hasNext();) {
                  //    Log.getInstance().debug(JDOVariablesOperativasDAO.class,objects);
                  pantallaadministrativaenhanced = (PantallaAdministrativaEnhanced)iter.next();
                  local_PantallaAdministrativaID = new PantallaAdministrativaEnhanced.ID();
                  local_PantallaAdministrativaID.idPantallaAdministrativa = pantallaadministrativaenhanced.getIdPantallaAdministrativa();

                  pantallaadministrativaenhanced = (PantallaAdministrativaEnhanced) pm.getObjectById(local_PantallaAdministrativaID, true);
                  pm.makeTransient( pantallaadministrativaenhanced );
                  local_Result.add( pantallaadministrativaenhanced );
              }




            } // for



            query.closeAll();

            // pm.currentTransaction().commit();


          }
          catch( Throwable t ) {
              t.printStackTrace();
              Log.getInstance().error(JDOVariablesOperativasDAO.class,t.getMessage(), t);
              throw new DAOException(t.getMessage(), t);
          }
          finally {

            if( pm != null )
              pm.close();

          } // try

          local_Result = TransferUtils.makeTransferAll(local_Result);
          return local_Result;

          */
          // version 2-------------------------------------------------

          Iterator iterator;

          try {

            PantallaAdministrativaEnhanced pantallaadministrativaenhanced;

            String p_PaginaId = "PG_REPORTES";
            String p_RolId = "";
            RolSIREnhanced rolsirenhanced;
            RolPantallaEnhanced rolpantallaenhanced;

            // pm.currentTransaction().begin();
            Query query = pm.newQuery(RolPantallaEnhanced.class);;
            query.setOrdering("this.pantalla.idPantallaAdministrativa ascending");
            query.declareParameters("String p_PaginaId, String p_RolId");
            query.setFilter(
      "this.idRol == p_RolId"
    + "&& this.pantalla.pagina == p_PaginaId"
            );

            // iterate over the list of roles
            org.auriga.core.modelo.transferObjects.Rol local_RolAuriga;
            RolSIREnhancedPk local_RolSirID;
            PantallaAdministrativaEnhancedPk local_PantallaAdministrativaID;

            iterator = roles.iterator();
            for( ; iterator.hasNext(); ) {

              local_RolAuriga = (org.auriga.core.modelo.transferObjects.Rol)iterator.next();
              local_RolSirID = new RolSIREnhancedPk();
              local_RolSirID.idRol = local_RolAuriga.getRolId();

              try {
                rolsirenhanced = (RolSIREnhanced) pm.getObjectById(local_RolSirID, true);
                p_RolId = rolsirenhanced.getIdRol();
              }
              catch( Exception e ){
                continue;
              }
              // put parameters + execute

              Collection col = (Collection)query.execute( p_PaginaId, p_RolId );
              for (Iterator iter = col.iterator(); iter.hasNext();) {
                  //    Log.getInstance().debug(JDOVariablesOperativasDAO.class,objects);
                  rolpantallaenhanced = (RolPantallaEnhanced)iter.next();
                  local_PantallaAdministrativaID = new PantallaAdministrativaEnhancedPk();
                  local_PantallaAdministrativaID.idPantallaAdministrativa = rolpantallaenhanced.getIdPantallaAdministrativa();

                  pantallaadministrativaenhanced = (PantallaAdministrativaEnhanced) rolpantallaenhanced.getPantalla();
                  pm.makeTransient( pantallaadministrativaenhanced );
                  local_MapRepresentation.put( local_PantallaAdministrativaID, pantallaadministrativaenhanced );
              }




            } // for



            query.closeAll();

            // pm.currentTransaction().commit();


          }
          catch( Throwable t ) {
              t.printStackTrace();
              Log.getInstance().error(JDOVariablesOperativasDAO.class,t.getMessage(), t);
              throw new DAOException(t.getMessage(), t);
          }
          finally {

            if( pm != null )
              pm.close();

          } // try

          // transform the map & return
          Map.Entry mapEntry; // to iterate through the map

          iterator = local_MapRepresentation.entrySet().iterator();
          for(;iterator.hasNext();) {
            mapEntry =  (Map.Entry) iterator.next();

            local_Result.add( mapEntry.getValue() );

          } // for

          local_MapRepresentation = null;

          local_Result = TransferUtils.makeTransferAll(local_Result);
          return local_Result;

          // ----------------------------------------------------------


        } // end method






	/**
	* Obtiene el listado de items de chequeo para turnos de Devoluciones.
	* @return Listado de items de chequeo para turnos de devoluciones.
	* @throws DAOException
	*/
	public List getItemsChequeoDevoluciones () throws DAOException
	{
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();


		try
		{
			Query q = pm.newQuery(CheckItemDevEnhanced.class);

			// Se establece como criterio de ordenamiento el codigo del item de chequeo.
			q.setOrdering("idCheckItemDev ascending");

			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			while (it.hasNext())
			{
				CheckItemDevEnhanced obj = (CheckItemDevEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			q.close(results);

		}

		catch (Throwable t)
		{
			t.printStackTrace();
			Log.getInstance().error(JDOVariablesOperativasDAO.class,t.getMessage(), t);
			throw new DAOException(t.getMessage(), t);
		}

		finally
		{
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}
	
	/**
     * Obiene el texto imprimible
     * @param idTexto identificador del texto
     * @return
     * @throws DAOException
     */
    public TextoImprimible getTextoImprimible(TextoImprimiblePk idTexto) throws DAOException{
    	TextoImprimibleEnhanced textoImp=null;
		TextoImprimibleEnhancedPk idEnh=new TextoImprimibleEnhancedPk();
		idEnh.idTexto=idTexto.idTexto;
		PersistenceManager pm = AdministradorPM.getPM();
		try{
			textoImp=(TextoImprimibleEnhanced)pm.getObjectById(idEnh,true);
		}

		catch (Throwable t){
			t.printStackTrace();
			Log.getInstance().error(JDOVariablesOperativasDAO.class,t.getMessage(), t);
			throw new DAOException(t.getMessage(), t);
		}finally{
			pm.close();
		}
		return (TextoImprimible)textoImp.toTransferObject();
    }

    protected void makeTransientValidacionNota(ValidacionNotaEnhanced validacionNota,
            PersistenceManager pm) throws DAOException {
        if (validacionNota != null) {
            try {
                for (Iterator itr = validacionNota.getValidacionesNotaDetalle().iterator();
                        itr.hasNext();) {
                    pm.makeTransient((ValidacionNotaDetalleEnhanced) itr.next());
                }
                
                pm.makeTransient(validacionNota);
            } catch (JDOException e) {
                Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

    }

    /**
     * Obtiene el listado de <code>PantallaAdministrativa</code> cuyo atributo
     * pagina sea igual al parámetro pagina
     * @param pagina
     * @return
     * @throws DAOException
     */
    public  List getPantallasAdministrativasByPagina (String pagina)
   throws DAOException
   {
    	List pantallas = new ArrayList();
    	PersistenceManager pm = AdministradorPM.getPM();
		try{
			Query query = pm.newQuery(PantallaAdministrativaEnhanced.class);

			query.setOrdering("nombre ascending");
			query.declareParameters("String pagina");
			query.setFilter("this.pagina == pagina");

			Collection results = (Collection) query.execute(pagina);
			Iterator it = results.iterator();

			while (it.hasNext())
			{
				PantallaAdministrativaEnhanced obj = (PantallaAdministrativaEnhanced) it.next();
				pm.makeTransient(obj);
				pantallas.add(obj.toTransferObject());
			}

			query.close(results);

		}

		catch (Throwable t){
			t.printStackTrace();
			Log.getInstance().error(JDOVariablesOperativasDAO.class,t.getMessage(), t);
			throw new DAOException(t.getMessage(), t);
		}finally{
			pm.close();
		}
    	return pantallas;
   }
    
    protected void addRolPantallasAdministrativa (RolPantallaEnhanced rolPantalla, PersistenceManager pm)
    throws DAOException
    {
 		try{
 			String idRolPantalla = rolPantalla.getIdPantallaAdministrativa() + "-" + rolPantalla.getIdRol();
 			RolPantallaEnhancedPk rolPantallaID = new RolPantallaEnhancedPk(idRolPantalla);
 			RolPantallaEnhanced rolPantallaExiste = this.getPantallaAdministrativaByID(rolPantallaID, pm);
 			
 			if (rolPantallaExiste == null)
 			{
 				pm.makePersistent(rolPantalla);
 			} else
 			{
 				throw new DAOException("RolPantalla ya existe");
 			}

	    } catch (JDOException e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
	 }
    
    /**
     * inserta <code>RolPantalla</code> en la base de datos
     * 
     * @param rolPantalla
     * @return
     * @throws DAOException
     */
    public  RolPantalla addRolPantallasAdministrativa (RolPantalla rolPantalla)
    throws DAOException
    {
     	PersistenceManager pm = AdministradorPM.getPM();
     	RolPantallaEnhanced rolPantallaE = RolPantallaEnhanced.enhance(rolPantalla);
     	
 		try{
 			pm.currentTransaction().begin();
 			
 			this.addRolPantallasAdministrativa(rolPantallaE, pm);
 			
 			pm.currentTransaction().commit();

	    } catch (JDOException e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}		
	     	return rolPantalla;
	 }
    
    /**
     * inserta cada elemento <code>RolPantalla</code> de la lista
     * rolesPantallas en la base de datos
     * 
     * @param rolesPantallas
     * @return
     * @throws DAOException
     */
    public  void addRolesPantallasAdministrativas (List rolesPantallas)
    throws DAOException
    {
     	PersistenceManager pm = AdministradorPM.getPM();
     	
     	if (rolesPantallas != null)
     	{
	 		try{
	 			pm.currentTransaction().begin();
	 			
	 			for (Iterator i = rolesPantallas.iterator(); i.hasNext() ;)
	 			{
	 				RolPantalla rolPantalla = (RolPantalla)i.next();
		 			RolPantallaEnhanced rolPantallaE = RolPantallaEnhanced.enhance(rolPantalla);
		 			
		 			this.addRolPantallasAdministrativa(rolPantallaE, pm);
	 			}
	 			
	 			pm.currentTransaction().commit();
	
		    } catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
				if (pm.currentTransaction().isActive()) {
					pm.currentTransaction().rollback();
				}
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			} catch (Throwable e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
				if (pm.currentTransaction().isActive()){
					pm.currentTransaction().rollback();
				}
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			} finally {
				pm.close();
			}
     	}
     	
	 }
    
    protected  boolean deleteRolPantallasAdministrativa (RolPantalla rolPantalla, PersistenceManager pm)
    throws DAOException
    {
     	boolean rta = false;
     	
 		try{
 			
 			String idRolPantalla = rolPantalla.getIdPantallaAdministrativa() + "-" + rolPantalla.getIdRol();
 			RolPantallaEnhancedPk rolPantallaID = new RolPantallaEnhancedPk(idRolPantalla);
 			RolPantallaEnhanced rolPantallaExiste = this.getPantallaAdministrativaByID(rolPantallaID, pm);
 			
 			if (rolPantallaExiste != null)
 			{
 				pm.deletePersistent(rolPantallaExiste);
 				rta = true;
 			}

	    } catch (JDOException e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} 	
	    return rta;
	 }
    
    /**
     * elimina <code>RolPantalla</code> de la base de datos
     * 
     * @param rolPantalla
     * @return
     * @throws DAOException
     */
    public  boolean deleteRolPantallasAdministrativa (RolPantalla rolPantalla)
    throws DAOException
    {
     	PersistenceManager pm = AdministradorPM.getPM();
     	boolean rta = false;
     	
 		try{
 			pm.currentTransaction().begin();
 			
 			rta = this.deleteRolPantallasAdministrativa(rolPantalla, pm);
			
 			pm.currentTransaction().commit();

	    } catch (JDOException e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}		
	     	return rta;
	 }
    
    /**
     * elimina cada elemento <code>RolPantalla</code> de la lista rolesPantalas
     * de la base de datos
     * 
     * @param rolesPantallas
     * @return
     * @throws DAOException
     */
    public  boolean deleteRolPantallasAdministrativa (List rolesPantallas)
    throws DAOException
    {
     	PersistenceManager pm = AdministradorPM.getPM();
     	boolean rta = true;
     	
     	if (rolesPantallas != null)
     	{
	 		try{
	 			pm.currentTransaction().begin();
	 			
	 			
	 			for (Iterator i = rolesPantallas.iterator(); i.hasNext() && rta;)
	 			{
	 				RolPantalla rolPantalla = (RolPantalla)i.next();
	 				rta = this.deleteRolPantallasAdministrativa(rolPantalla, pm);
	 			}
				
	 			if (rta)
	 			{
	 				pm.currentTransaction().commit();
	 			} else
	 			{
	 				pm.currentTransaction().rollback();
	 			}
	
		    } catch (JDOException e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
				if (pm.currentTransaction().isActive()){
					pm.currentTransaction().rollback();
				}
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			} catch (Throwable e) {
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e);
				if (pm.currentTransaction().isActive()){
					pm.currentTransaction().rollback();
				}
				Log.getInstance().error(JDOVariablesOperativasDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			} finally {
				pm.close();
			}	
     	}
	    return rta;
	 }
    
    protected RolPantallaEnhanced getPantallaAdministrativaByID (RolPantallaEnhancedPk rolPantallaID, PersistenceManager pm)
	throws DAOException 
	{
    	RolPantallaEnhanced rta = null;

        try {
            rta = (RolPantallaEnhanced) pm.getObjectById(rolPantallaID, true);
        } catch (JDOObjectNotFoundException e) {
            rta = null;
        } catch (JDOException e) {
            Log.getInstance().debug(JDOVariablesOperativasDAO.class, e);
            Log.getInstance().error(JDOVariablesOperativasDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
        return rta;
    }
     /**
     * Obtiene el listado de pantallas administrativas visibles para un
     * respectivo rol.
     *
     * @author      :  Carlos Torres
     * @change      :  Obtener pantallas administrativas del usuario
     * @Caso Mantis :  14371: Acta - Requerimiento No 070_453_Rol_Registrador_Inhabilitar_Pantallas_Administrativas
     * @param roles Listado de roles asociados con el usuario.
     * @return Listado de pantallas administrativas visibles para el rol
     * recibido como parámetro.
     * @throws DAOException
     */
    public List obtenerAdministrativasPorRol(Usuario usuario) throws DAOException {
        List listaPantallasEnh = new ArrayList();
        List lista = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String p_PaginaId = "PG_ADMINISTRATIVA";
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();
            connection = ((VersantPersistenceManager)pm).getJdbcConnection(null);

            StringBuilder consulta = new StringBuilder();
            consulta.append("select  distinct tp.id_tipo_pantalla,tp.tppa_nombre, pa.id_pantalla_administrativa,pa.ptdm_nombre,pa.ptdm_vista,pa.ptdm_pagina ");
            consulta.append("from AURIGA.cmn_rel_rol_estacion rre ");
            consulta.append("join AURIGA.cmn_rel_usu_rol_est ure on rre.estacion_id = ure.estacion_id and rre.rol_id = ure.rol_id ");
            consulta.append("join SIR.sir_op_rol_pantalla rp on rp.id_rol = rre.rol_id ");
            consulta.append("join SIR.sir_op_pantalla_administrativa pa on rp.id_pantalla_administrativa = pa.id_pantalla_administrativa ");
            consulta.append("join SIR.sir_op_tipo_pantalla tp on tp.id_tipo_pantalla = pa.id_tipo_pantalla ");
            consulta.append("WHERE ure.usuario_id = ? ");
            consulta.append("and rre.activa = 1 ");
            consulta.append("and pa.ptdm_pagina = ? ");
            consulta.append("order by 3");

            ps = connection.prepareStatement(consulta.toString());
            ps.setString(1, usuario.getUsername());
            ps.setString(2, p_PaginaId);

            rs = ps.executeQuery();

            while(rs.next())
            {
                TipoPantallaEnhanced tipo = new TipoPantallaEnhanced();
                tipo.setIdTipoPantalla(rs.getLong(1));
                tipo.setNombre(rs.getString(2));

                PantallaAdministrativaEnhanced pantalla = new PantallaAdministrativaEnhanced();
                pantalla.setIdPantallaAdministrativa(rs.getLong(3));
                pantalla.setTipoPantalla(tipo);
                pantalla.setNombre(rs.getString(4));
                pantalla.setVista(rs.getString(5));
                pantalla.setPagina(rs.getString(6));

                listaPantallasEnh.add(pantalla);

            }
            pm.currentTransaction().commit();

            lista = TransferUtils.makeTransferAll(listaPantallasEnh);
        } catch (Throwable t) {
            t.printStackTrace();
            Log.getInstance().error(JDOVariablesOperativasDAO.class, t.getMessage(), t);
            throw new DAOException(t.getMessage(), t);
        } finally {
            pm.close();
        }

        return lista;
    }

    /**
     * Obtiene el listado de pantallas administrativas visibles para un
     * respectivo rol.
     * @author      :  Carlos Torres
     * @change      :  Obtener pantallas administrativas del usuario
     * @Caso Mantis :  14371: Acta - Requerimiento No 070_453_Rol_Registrador_Inhabilitar_Pantallas_Administrativas
     * @param roles Listado de roles asociados con el usuario.
     * @return Listado de pantallas administrativas visibles para el rol
     * recibido como parámetro.
     * @throws DAOException
     */
    public List obtenerPantallasPaginaReportesPorRol(Usuario usuario) throws DAOException {
         List listaPantallasEnh = new ArrayList();
        List lista = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String p_PaginaId = "PG_REPORTES";
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();
            connection = ((VersantPersistenceManager)pm).getJdbcConnection(null);

            StringBuilder consulta = new StringBuilder();
            consulta.append("select  distinct tp.id_tipo_pantalla,tp.tppa_nombre, pa.id_pantalla_administrativa,pa.ptdm_nombre,pa.ptdm_vista,pa.ptdm_pagina ");
            consulta.append("from AURIGA.cmn_rel_rol_estacion rre ");
            consulta.append("join AURIGA.cmn_rel_usu_rol_est ure on rre.estacion_id = ure.estacion_id and rre.rol_id = ure.rol_id ");
            consulta.append("join SIR.sir_op_rol_pantalla rp on rp.id_rol = rre.rol_id ");
            consulta.append("join SIR.sir_op_pantalla_administrativa pa on rp.id_pantalla_administrativa = pa.id_pantalla_administrativa ");
            consulta.append("join SIR.sir_op_tipo_pantalla tp on tp.id_tipo_pantalla = pa.id_tipo_pantalla ");
            consulta.append("WHERE ure.usuario_id = ? ");
            consulta.append("and rre.activa = 1 ");
            consulta.append("and pa.ptdm_pagina = ? ");
            consulta.append("order by 3");

            ps = connection.prepareStatement(consulta.toString());
            ps.setString(1, usuario.getUsername());
            ps.setString(2, p_PaginaId);

            rs = ps.executeQuery();

            while(rs.next())
            {
                TipoPantallaEnhanced tipo = new TipoPantallaEnhanced();
                tipo.setIdTipoPantalla(rs.getLong(1));
                tipo.setNombre(rs.getString(2));

                PantallaAdministrativaEnhanced pantalla = new PantallaAdministrativaEnhanced();
                pantalla.setIdPantallaAdministrativa(rs.getLong(3));
                pantalla.setTipoPantalla(tipo);
                pantalla.setNombre(rs.getString(4));
                pantalla.setVista(rs.getString(5));
                pantalla.setPagina(rs.getString(6));

                listaPantallasEnh.add(pantalla);

            }
            pm.currentTransaction().commit();

            lista = TransferUtils.makeTransferAll(listaPantallasEnh);
        } catch (Throwable t) {
            t.printStackTrace();
            Log.getInstance().error(JDOVariablesOperativasDAO.class, t.getMessage(), t);
            throw new DAOException(t.getMessage(), t);
        } finally {
            pm.close();
        }

        return lista;

    } // end method
}

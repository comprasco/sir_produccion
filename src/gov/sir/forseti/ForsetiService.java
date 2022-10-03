/*
 * Created on 15-jul-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.forseti;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.auriga.core.modelo.Servicio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

import gov.sir.core.negocio.acciones.comun.ANPago;
import gov.sir.core.negocio.modelo.ActoresRepartoNotarial;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.AnotacionPk;
import gov.sir.core.negocio.modelo.Busqueda;
import gov.sir.core.negocio.modelo.BusquedaPk;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoFestivo;
import gov.sir.core.negocio.modelo.CirculoFestivoPk;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.CiudadanoPk;
import gov.sir.core.negocio.modelo.CiudadanoProhibicionPk;
import gov.sir.core.negocio.modelo.Complementacion;
import gov.sir.core.negocio.modelo.DeltaFolio;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.DepartamentoPk;
import gov.sir.core.negocio.modelo.Direccion;
import gov.sir.core.negocio.modelo.DireccionPk;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.DocumentoPk;
import gov.sir.core.negocio.modelo.DominioNaturalezaJuridica;
import gov.sir.core.negocio.modelo.DominioNaturalezaJuridicaPk;
import gov.sir.core.negocio.modelo.Eje;
import gov.sir.core.negocio.modelo.EjePk;
import gov.sir.core.negocio.modelo.EstadoAnotacion;
import gov.sir.core.negocio.modelo.EstadoAnotacionPk;
import gov.sir.core.negocio.modelo.EstadoFolio;
import gov.sir.core.negocio.modelo.EstadoFolioPk;
import gov.sir.core.negocio.modelo.FirmaRegistrador;
import gov.sir.core.negocio.modelo.FirmaRegistradorPk;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioDerivado;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.GrupoNaturalezaJuridicaPk;
import gov.sir.core.negocio.modelo.LlaveBloqueo;
import gov.sir.core.negocio.modelo.LlaveBloqueoPk;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.MunicipioPk;
import gov.sir.core.negocio.modelo.NaturalezaJuridica;
import gov.sir.core.negocio.modelo.NaturalezaJuridicaPk;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.OficinaOrigenPk;
import gov.sir.core.negocio.modelo.PlantillaPertenencia;
import gov.sir.core.negocio.modelo.ProhibicionPk;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.Texto;
import gov.sir.core.negocio.modelo.TextoPk;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoDocumentoPk;
import gov.sir.core.negocio.modelo.TipoOficina;
import gov.sir.core.negocio.modelo.TipoOficinaPk;
import gov.sir.core.negocio.modelo.TipoPredio;
import gov.sir.core.negocio.modelo.TipoPredioPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Validacion;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.VeredaPk;
import gov.sir.core.negocio.modelo.ZonaRegistral;
import gov.sir.core.negocio.modelo.ZonaRegistralPk;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;
import gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador;
import gov.sir.core.negocio.modelo.constantes.CValidacion;
import gov.sir.forseti.dao.DAOException;
import gov.sir.forseti.dao.FactoryException;
import gov.sir.forseti.dao.ForsetiFactory;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.print.server.dao.PrintFactory;
import gov.sir.core.negocio.modelo.Prohibicion;
import gov.sir.core.negocio.modelo.SalvedadAnotacion;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionTMP;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionTMPPk;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoTMP;
import gov.sir.core.negocio.modelo.jdogenie.FolioDatosTMP;
import gov.sir.core.negocio.modelo.jdogenie.FolioDatosTMPPk;
/**
     * @Autor: Edgar Lora
     * @Mantis: 13176
     */
import gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced;
import gov.sir.core.negocio.modelo.util.Log;


/**
 * Fachada del servicio Forseti
 * @author fceballos
 *
 */
public class ForsetiService implements Servicio, ForsetiServiceInterface {
    private static ForsetiService instancia = null;
    private ForsetiFactory forseti;
    
    /**
	 * Instancia del service locator
	 */
	private ServiceLocator service = null;
	
    /**
	 * Instancia de Hermod
	 */
	private HermodServiceInterface hermod;

	private PrintFactory impresion;
	
    /**
    * Constructor por defecto
    */
    private ForsetiService() throws ForsetiException {
        try {
            forseti = ForsetiFactory.getFactory();
        }
        catch (FactoryException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener la f�brica concreta", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
        
        try {
        	service = ServiceLocator.getInstancia();
        	
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");

			impresion = PrintFactory.getFactory();
			
		} catch (ServiceLocatorException e) {
			throw new ForsetiException("Error instanciando el servicio.", e);
		} catch (gov.sir.print.server.dao.FactoryException e) {
			throw new ForsetiException("Error instanciando el servicio.", e);
		} 
    }

    /**
    * M�todo que obtiene la instancia del servicio forseti
    * @return
    */
    public static ForsetiService getInstance() throws ForsetiException {
        if (instancia == null) {
            instancia = new ForsetiService();
        }

        return instancia;
    }
    
    /**
     * 
     * @param idMatricula
     * @return
     * @throws ForsetiException 
     */
    public List getHistorialArea(String idMatricula) throws ForsetiException{
        try {
            return forseti.getFolioDAO().getHistorialArea(idMatricula);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n del historial de areas", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    /**
    * Obtiene un objeto Folio dado su identificador
    * @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
    * @return Objeto Folio con sus objetos estado, lindero, tipoPredio y ZonaRegistral.
    * El objeto ZonaRegistral contiene la jerarqu�a circulo, vereda, municipio y departamento
    * null si  no encuentra un folio que coincida con el identificador dado
    * @throws ForsetiException
    */
    public Folio getFolioByID(FolioPk oid) throws ForsetiException {
        Folio rta = null;

        try {
            rta = forseti.getFolioDAO().getFolioByID(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener el folio", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
     * Obtiene un objeto Folio dado su identificador (Devuelve folios temporales y definitivos)
     * @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
     * @return Objeto Folio con sus objetos estado, lindero, tipoPredio y ZonaRegistral.
     * El objeto ZonaRegistral contiene la jerarqu�a circulo, vereda, municipio y departamento
     * null si  no encuentra un folio que coincida con el identificador dado
     * @throws ForsetiException
     */
     public Folio getFolioByID(String matricula) throws ForsetiException {
         Folio rta = null;

         try {
             rta = forseti.getFolioDAO().getFolioByID(matricula);
         }
         catch (DAOException e) {
             ForsetiException fe = new ForsetiException("No fue posible obtener el folio", e);
             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
             throw fe;
         }

         return rta;
     }
    
    /**
    * Obtiene un objeto Folio dado el n�mero de matr�cula
    * @param matricula n�mero de matr�cula del folio
    * @return Objeto Folio con sus objetos estado, lindero, tipoPredio y ZonaRegistral.
    * El objeto ZonaRegistral contiene la jerarqu�a circulo, vereda, municipio y departamento
    * null si  no encuentra un folio que coincida con el identificador dado
    * @throws ForsetiException
    */
    public Folio getFolioByMatricula(String matricula) throws ForsetiException {
        Folio rta = null;

        try {
            rta = forseti.getFolioDAO().getFolioByMatricula(matricula);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener el folio", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Obtiene una lista con las anotaciones del folio especificado por su ID
    * @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
    * @return lista con las anotaciones del folio, cada anotaci�n contiene los objetos
    * tipoAnotacion y naturalezaJuridica
    * @see gov.sir.core.negocio.modelo.Anotacion
    * @throws ForsetiException cuando no encuentra un folio con el id dado
    */
    public List getAnotacionesFolio(FolioPk oid) throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getFolioDAO().getAnotacionesFolio(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener las anotaciones", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }
    
    /**
     * Obtiene una lista con las anotaciones del folio (aun temporal) especificado por su ID
     */
    public List getAnotacionesFolioTMP(FolioPk oid) throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getFolioDAO().getAnotacionesFolioTMP(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener las anotaciones", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }
    
    
    
    /**
     * Obtiene una lista con las anotaciones del folio (aun temporal) especificado por su ID con los folios derivados Padre
     */
    public List getAnotacionesFolioTMPFD(FolioPk oid) throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getFolioDAO().getAnotacionesFolioTMPFD(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener las anotaciones", e);
            Log.getInstance().error(ForsetiService.class,fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
     * Obtiene una lista con las anotaciones del folio (aun temporal) especificado por su ID
     */
    public int getAnotacionesFolioTMPCount(FolioPk oid) throws ForsetiException {
        int rta = 0;

        try {
            rta = forseti.getFolioDAO().getAnotacionesFolioTMPCount(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener las anotaciones", e);
            Log.getInstance().error(ForsetiService.class,fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Adiciona un EstadoFolio a la configuraci�n del sistema
    * @param datos objeto EstadoFolio con sus atributos exceptuando su identificador
    * @param usuario que adiciona el estado del folio
    * el cual es generado por el sistema
    * @return identificador de estadoFolio generado
    */
    public EstadoFolioPk addEstadoFolio(EstadoFolio datos, Usuario usuario) throws ForsetiException {
        EstadoFolioPk rta = null;

        try {
            rta = forseti.getFolioDAO().addEstadoFolio(datos, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible crear el estado folio", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Adiciona un departamento a la configuraci�n del sistema
    * @param datos objeto Departamento con sus atributos exceptuando su identificador
    * el cual es generado por el sistema y el usuario que adiciona el departamento
    * @return identificador de departamento generado
    * @throws ForsetiException
    */
    public DepartamentoPk addDepartamento(Departamento datos, Usuario usuario) throws ForsetiException {
        DepartamentoPk rta = null;

        try {
            rta = forseti.getZonaRegistralDAO().addDepartamento(datos, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible crear el departamnento", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Obtiene un objeto Departamento dado su identificador
    * @param oid identificador del departamento
    * @return objeto Departamento con sus atributos b�sicos y municipios
    * @throws DAOException
    */
    public Departamento getDepartamento(DepartamentoPk oid) throws ForsetiException {
        Departamento rta = null;

        try {
            rta = forseti.getZonaRegistralDAO().getDepartamento(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener el departamento", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Adiciona un municipio a un departamento dado
    * @param datos objeto municipio con sus atributos exceptuando su identificaci�n
    * el cual es generado por el sistema
    * @param oid identificador del departamento
    * @param usuario que adiciona el municipio
    * @return identificaci�n del municipio generada
    * @throws ForsetiException
    */
    public MunicipioPk addMunicipioToDepartamento(Municipio datos, DepartamentoPk oid, Usuario usuario) throws ForsetiException {
        MunicipioPk rta = null;

        try {
            rta = forseti.getZonaRegistralDAO().addMunicipioToDepartamento(datos, oid, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible crear el municipio", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Servicio utilizado para establecer si el folio con el n�mero
    * de matr�cula especificado existe en el sistema
    * @param matricula
    * @return true: existe, false: no existe
    * @throws ForsetiException
    */
    public boolean existeFolio(String matricula) throws ForsetiException {
        boolean rta;

        try {
            rta = forseti.getFolioDAO().existeMatricula(matricula);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible conocer si la matr�cula existe", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }
    
    /**
     * Servicio utilizado para establecer si existe un matricula como no grabada
     * @param circulo, idMatricula
     * @return true: existe, false: no existe
     * @throws ForsetiException
     */
    public boolean matriculaNoGrabadaExistente(String circulo, long idMatricula) throws ForsetiException{
    	try {
    		return forseti.getFolioDAO().matriculaNoGrabadaExistente(circulo, idMatricula);
    	}
    	catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio para obtener el folio", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    
    /**
     * Servicio utilizado para establecer si el folio con el n�mero
     * de matr�cula especificado existe en el sistema, a difencia del m�todo 
     * existeFolio, �ste tiene en cuenta los datos temporales
     * @param matricula
     * @return true: existe, false: no existe
     * @throws ForsetiException
     */
     public boolean existeFolioIncluyendoTemporales(String matricula) throws ForsetiException {
         boolean rta;

         try {
             rta = forseti.getFolioDAO().existeMatriculaIncluyendoTemporales(matricula);
         }
         catch (DAOException e) {
             ForsetiException fe = new ForsetiException("No fue posible conocer si la matr�cula existe", e);
             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
             throw fe;
         }

         return rta;
     }

    /**
    * Servicio utilizado para establecer si el folio con el n�mero
    * de matr�cula se encuentra actualmente bloqueado
    * @param matricula
    * @return true: folio bloqueado,   false: folio no est� bloqueado
    * @throws ForsetiException
    */
    public boolean bloqueadoFolio(String matricula) throws ForsetiException {
        boolean rta;

        try {
            rta = forseti.getFolioDAO().estaBloqueado(matricula);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible establecer si el folio est� bloqueado", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }
   
     /**
    * Servicio utilizado para establecer si el folio con el n�mero
    * de matr�cula se encuentra actualmente bloqueado y en actuacion
    * administrativa
    * @param idMatricula
    * @return true: folio bloqueado,   false: folio no est� bloqueado
    * @throws ForsetiException
    */
    public boolean isActuacionAdministrativa(String idMatricula) throws ForsetiException {
        boolean rta;

        try {
            rta = forseti.getFolioDAO().isActuacion(idMatricula);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible establecer si el folio est� bloqueado", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }
    /**
     * Indica si el ciudadano esta en alguna anotacionCiudadano
     * @param idCiudadano
     * @return
     * @throws ForsetiException
     */
     public boolean estaCiudadanoEnAnotacionCiudadano(String idCiudadano) throws ForsetiException {
         boolean rta;

         try {
             rta = forseti.getFolioDAO().estaCiudadanoEnAnotacionCiudadano(idCiudadano);
         }
         catch (DAOException e) {
             ForsetiException fe = new ForsetiException("No fue posible establecer si el ciudadano esta relacionado en una anotacion", e);
             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
             throw fe;
         }

         return rta;
     }

    /**
    * Servicio utilizado para establecer si el folio con el n�mero
    * de matr�cula se encuentra en tr�mite
    * @param matricula
    * @return true: folio en tr�mite,   false: folio no se encuentra en tr�mite
    * @throws ForsetiException
    */
    public boolean enTramiteFolio(String matricula) throws ForsetiException {
        boolean rta;

        try {
            rta = forseti.getFolioDAO().enTramiteFolio(matricula);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible establecer si el folio est� en tr�mite", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Servicio utilizado para establecer si el folio con el n�mero
    * de matr�cula se encuentra en custodia
    * @param matricula
    * @return true: folio en custodia, false: folio no est� en custodia (o no existe)
    * @throws ForsetiException
    */
    public boolean enCustodiaFolio(String matricula) throws ForsetiException {
        boolean rta = false;
        EstadoFolio ef = null;

        try {
            ef = forseti.getFolioDAO().getEstadoFolioByMatricula(matricula);

            if (ef != null) {
                if (ef.getIdEstado().equals(CEstadoFolio.EN_CUSTODIA)) {
                    rta = true;
                }
            }
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible establecer si el folio est� en custodia", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
	* Actualiza los datos temporales de un folio.
	* @param matricula, datosTMP, eliminarDireccion
	* @return rta:  true si se realizo la operacion correctamente
	* 				false si hubo algun error
	* @throws ForsetiException
	*/
	
	public boolean actualizarFolioDatosTMP(String matricula, FolioDatosTMP datos, boolean eliminarDireccion)
	throws ForsetiException {	
		boolean rta = false;
	
		try {
			rta = forseti.getFolioDAO().actualizarFolioDatosTMP(matricula, datos, eliminarDireccion);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("No fue posible establecer si el folio es inconsistente", e);
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}

		return rta;
	}
    /**
    * Servicio utilizado para establecer si el folio con el n�mero
    * de matr�cula se encuentra anulado
    * @param matricula
    * @return true: folio anulado, false: folio no est� anulado (o no existe)
    * @throws ForsetiException
    */
    public boolean anuladoFolio(String matricula) throws ForsetiException {
        boolean rta = false;
        EstadoFolio ef = null;

        try {
            ef = forseti.getFolioDAO().getEstadoFolioByMatricula(matricula);

            if (ef != null) {
                if (ef.getIdEstado().equals(CEstadoFolio.ANULADO)) {
                    rta = true;
                }
            }
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible establecer si el folio est� anulado", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Servicio utilizado para establecer si el folio con el n�mero
    * de matr�cula se encuentra cerrado
    * @param matricula
    * @return true: folio cerrado, false: folio no est� cerrado (o no existe)
    * @throws ForsetiException
    */
    public boolean cerradoFolio(String matricula) throws ForsetiException {
        boolean rta = false;
        EstadoFolio ef = null;

        try {
            ef = forseti.getFolioDAO().getEstadoFolioByMatricula(matricula);

            if (ef != null) {
                if (ef.getIdEstado().equals(CEstadoFolio.CERRADO)) {
                	ef = forseti.getFolioDAO().getEstadoFolioByMatriculaTMP(matricula);
                	if (ef != null) {
                		 if (ef.getIdEstado().equals(CEstadoFolio.CERRADO)) {
                			 rta = true;
                		 }
                	}else{
                		rta = true;
                	}
                }
            }
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible establecer si el folio est� cerrado", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }



	/**
	* Servicio utilizado para establecer si el folio con el n�mero
	* de matr�cula se encuentra cerrado
	* @param matricula
	* @return true: folio cerrado, false: folio no est� cerrado (o no existe)
	* @throws ForsetiException
	*/
	public boolean inconsistenteFolio(String matricula) throws ForsetiException {
		boolean rta = false;
		EstadoFolio ef = null;

		try {
			rta = forseti.getFolioDAO().inconsistenteMatricula(matricula);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("No fue posible establecer si el folio es inconsistente", e);
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}

		return rta;
	}



	/**
	* Servicio utilizado para establecer si el folio con el n�mero
	* de matr�cula se encuentra cerrado
	* @param matricula
	* @return true: folio cerrado, false: folio no est� cerrado (o no existe)
	* @throws ForsetiException
	*/
	public boolean cerradoFolio(String matricula, Usuario usuario) throws ForsetiException {
		boolean rta = false;
		EstadoFolio ef = null;

		try {
			ef = forseti.getFolioDAO().getEstadoFolioByMatricula(matricula, usuario);

			if (ef != null) {
				if (ef.getIdEstado().equals(CEstadoFolio.CERRADO)) {
					rta = true;
				}
			}
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("No fue posible establecer si el folio est� cerrado", e);
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}

		return rta;
	}

    /**
    * Servicio utilizado para establecer si el folio con el n�mero
    * de matr�cula posee alguna deuda
    * @param matricula
    * @return true: folio debe dinero, false: folio libre de deudas
    * @throws ForsetiException
    */
    public boolean tieneDeudaFolio(String matricula) throws ForsetiException {
		try {
			return forseti.getFolioDAO().tieneDeudaFolio(matricula);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de validaci�n de deudas de folio", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
    }

    /**
    * Servicio utilizado para establecer si el folio con el n�mero
    * de matr�cula es de mayor extensi�n
    * @param matricula
    * @return true: folio de mayor extensi�n, false: folio no es de mayor extensi�n
    * @throws ForsetiException
    */
    public boolean mayorExtensionFolio(String matricula) throws ForsetiException {
        boolean rta;
        ForsetiProperties prop = ForsetiProperties.getInstancia();
        int mayorExt = Integer.parseInt(prop.getProperty(ForsetiProperties.MAYOR_EXTENSION));

        try {
            long anotaciones = forseti.getFolioDAO().getCountAnotacionesFolio(matricula);

            if (anotaciones > mayorExt) {
                rta = true;
            }
            else {
                rta = false;
            }
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible establecer si el folio es de mayor extensi�n", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Adiciona un c�rculo a la configuraci�n del sistema
    * @param datos objeto Circulo con sus atributos exceptuando su identificaci�n
    * el cual es generado por el sistema
    * @return identificaci�n del c�rculo generado por el sistema
    * @throws DAOException
    */
    public CirculoPk addCirculo(Circulo datos, Usuario usuario) throws ForsetiException {
        CirculoPk rta = null;

        try {
            rta = forseti.getZonaRegistralDAO().addCirculo(datos, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible crear el circulo", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Crea un folio y asigna un n�mero de matr�cula
    * @param datos objeto Folio que debe contener codCatastral y sus objetos
    * ZonaRegistral, estado, lindero y tipoPredio
    * @return objeto Folio con el n�mero de matr�cula asignado
    * @throws DAOException
    */
    public Folio crearFolio(Folio datos, Usuario us, TurnoPk oid, boolean validarTurno) throws ForsetiException {
        Folio rta = null;

        try {
            rta = forseti.getFolioDAO().crearFolio(datos, us, oid, validarTurno);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible crear el folio", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }
    
    
    /**
     * Crea un folio y sus anotaciones completas y se asocia el turno actual
     * @param datos objeto Folio que debe contener codCatastral y sus objetos
     * ZonaRegistral, estado, lindero y tipoPredio
     * @return objeto Folio con el n�mero de matr�cula asignado
     * @throws DAOException
     */
     public Folio crearFolioSegeng(Folio datos, Usuario us, TurnoPk oid, boolean validarTurno, Folio folioB) throws ForsetiException {
         Folio rta = null;

         try {
             rta = forseti.getFolioDAO().crearFolioSegeng(datos, us, oid, validarTurno, folioB);
         }
         catch (DAOException e) {
             ForsetiException fe = new ForsetiException("No fue posible crear el folio", e);
             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
             throw fe;
         }

         return rta;
     }
    
    public boolean validarActualizacionCiudadanoTurno(Turno turno) throws ForsetiException {
        boolean rta = false;

        try {
            rta = forseti.getFolioDAO().validarActualizacionCiudadanoTurno(turno);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible validar la informaci�n el folio", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }
    
    public List getFolioDerivePadre(String IdMatricula,String IdAnotacion) throws ForsetiException {
        List lstFolioDerive;

        try {
        	lstFolioDerive = forseti.getFolioDAO().getFolioDerivePadre(IdMatricula, IdAnotacion);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible validar la informaci�n el folio", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return lstFolioDerive;
    }
    

    /**
    * Obtiene una lista con los BloqueoFolio correspondientes a la llave especificada
    * @param oid identificador de la llave
    * @return lista con BloqueoFolio, cada bloqueoFolio contiene el objeto Folio
    * @see gov.sir.core.negocio.modelo.BloqueoFolio
    * @throws ForsetiException
    */
    public List getBloqueoFolios(LlaveBloqueoPk oid) throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getFolioDAO().getBloqueoFolios(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener los bloqueos de la llave especificada", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Obtiene una lista con las direcciones del folio especificado por su ID
    * @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
    * @return lista con las direcciones del folio, cada direccion contiene los objetos
    * eje y eje1
    * @see gov.sir.core.negocio.modelo.Direccion
    * @throws ForsetiException
    */
    public List getDireccionesFolio(FolioPk oid) throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getFolioDAO().getDireccionesFolio(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener las direcciones del folio especificado", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Obtiene una lista con las salvedades del folio especificado por su ID
    * @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
    * @return lista con las salvedades del folio
    * @see gov.sir.core.negocio.modelo.SalvedadFolio
    * @throws ForsetiException
    */
    public List getSalvedadesFolio(FolioPk oid) throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getFolioDAO().getSalvedadesFolio(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener las salvedades del folio especificado", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Obtiene el documento asociado con la anotacion
    * @param oid identificador de la anotaci�n conformado por el idAnotacion, IdMatricula
    * y IdZonaRegistral
    * @return objeto Documento con toda su jerarqu�a de objetos
    * @throws ForsetiException
    */
    public Documento getDocumentoAnotacion(AnotacionPk oid) throws ForsetiException {
        Documento rta = null;

        try {
            rta = forseti.getFolioDAO().getDocumentoAnotacion(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener el documento de la anotaci�n", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Obtiene una lista con las salvedades la anotaci�n especificada por su ID
    * @param oid identificador de la anotaci�n conformado por el idAnotacion, IdMatricula
    * y IdZonaRegistral
    * @return lista con las salvedades de la anotaci�n
    * @see gov.sir.core.negocio.modelo.SalvedadAnotacion
    * @throws ForsetiException
    */
    public List getSalvedadesAnotacion(AnotacionPk oid) throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getFolioDAO().getSalvedadesAnotacion(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener las salvedades de la anotacion", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Obtiene una lista de objetos tipo AnotacionCiudadano de la anotaci�n especificada
    * por su identificador
    * @param oid identificador de la anotaci�n conformado por el idAnotacion, IdMatricula
    * y IdZonaRegistral
    * @return lista de objetos tipo AnotacionCiudadano, cada uno contiene el objeto
    * ciudadano
    * @see gov.sir.core.negocio.modelo.AnotacionCiudadano
    * @see gov.sir.core.negocio.modelo.Ciudadano
    * @throws ForsetiException
    */
    public List getAnotacionesCiudadano(AnotacionPk oid) throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getFolioDAO().getAnotacionesCiudadano(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener los ciudadanos de la anotacion", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Obtiene las anotaciones hijas de una anotacion
    * @param oid identificador de la anotaci�n conformado por el idAnotacion, IdMatricula
    * y IdZonaRegistral
    * @return lista de objetos FolioDerivado, cada uno contiene el objeto hijo
    * que indica la anotaci�n derivada
    * @see gov.sir.core.negocio.modelo.FolioDerivado
    * @throws ForsetiException
    */
    public List getAnotacionesHijas(AnotacionPk oid) throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getFolioDAO().getAnotacionesHijas(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener las anotaciones hijas", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Obtiene las anotaciones padre de una anotacion
    * @param oid identificador de la anotaci�n conformado por el idAnotacion, IdMatricula
    * y IdZonaRegistral
    * @return lista de objetos FolioDerivado, cada uno contiene el objeto padre
    * que indica la anotaci�n derivada
    * @see gov.sir.core.negocio.modelo.FolioDerivado
    * @throws ForsetiException
    */
    public List getAnotacionesPadre(AnotacionPk oid) throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getFolioDAO().getAnotacionesPadre(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener las anotaciones padre", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Agrega una direccion a un folio
    * @param oid Identificador del folio
    * @param datos Direcci�n a agregar, debe contener el objeto eje. El eje1 puede ser nulo
    * @param pm PersistenceManager
    * @return identificador de la direcci�n asignada por el sistema
    * @throws ForsetiException
    */
    public DireccionPk addDireccionToFolio(FolioPk oid, Direccion datos) throws ForsetiException {
        DireccionPk rta = null;

        try {
            rta = forseti.getFolioDAO().addDireccionToFolio(oid, datos);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible agregar la direcci�n al folio", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
     * Retorna la direcci�n con Mayor ID, de manera trasiente y sin dependencias
     * @param direcciones
     * @return
     */
    public Direccion getUltimaDireccion(FolioPk oid) throws ForsetiException {
        Direccion rta = null;
        try {
            rta = forseti.getFolioDAO().getUltimaDireccion(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible consultar la �ltima direcci�n" , e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
        return rta;
    }

    /**
    * Setea el documento de una anotacion. Si idDocumento es nulo, se asigna un consecutivo y
    * se crea el documento, si el idDocumento es diferente de nulo se valida que exista y se asocia
    * @param anotacion
    * @param datos
    * @return
    * @throws ForsetiException
    */
    public DocumentoPk setDocumentoToAnotacion(AnotacionPk oid, Documento datos) throws ForsetiException {
        DocumentoPk rta = null;

        try {
            rta = forseti.getFolioDAO().setDocumentoToAnotacion(oid, datos);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible setear el documento a la anotaci�n", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * A�ade una anotaci�n a un folio
    * @param folio
    * @param datos La anotaci�n debe contener los objetos existentes: estado, naturalezaJuridica y
    * tipoAnotacion. Si el idDocumento de la anotacion es direfente de null se valida que exista y
    * se asocia, si el documento de la anotaci�n es null se crea
    * @return
    * @throws ForsetiException
    */
    public AnotacionPk addAnotacionToFolio(FolioPk oid, Anotacion datos) throws ForsetiException {
        AnotacionPk rta = null;

        try {
            rta = forseti.getFolioDAO().addAnotacionToFolio(oid, datos);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible agregar la anotaci�n al folio", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Obtiene una lista con los departamentos registrados en el sistema. Si el circulo.id es null
    * se obtienen todos los departamentos a nivel pa�s, si el c�rculo.id es distinto de null se obtienen
    * s�lamente los departamentos, de los municipios de las veredas del circulo dado
    * @return lista con objetos tipo Departamento, cada departamento tiene su jerarqu�a de objetos de acuerdo
    * al c�rculo dado
    * @see gov.sir.core.negocio.modelo.Departamento
    * @throws ForsetiException
    */
    public List getDepartamentos(CirculoPk oid) throws ForsetiException {
        List rta = new ArrayList();
        List zonas = null;
        Vereda vereda = null;
        int posDepto;
        int posMun;
        Departamento depto;
        Municipio mun;

        try {
            if (oid == null) {
                rta = forseti.getZonaRegistralDAO().getDepartamentos();
            }
            else {
                zonas = forseti.getZonaRegistralDAO().getZonaRegistralesCirculo(oid);

                for (Iterator itr = zonas.iterator(); itr.hasNext();) {
                    vereda = ((ZonaRegistral)itr.next()).getVereda();

                    if (!rta.contains(vereda.getMunicipio().getDepartamento())) {
                        rta.add(vereda.getMunicipio().getDepartamento());
                    }

                    posDepto = rta.indexOf(vereda.getMunicipio().getDepartamento());
                    depto = (Departamento)rta.get(posDepto);

                    if (!depto.getMunicipios().contains(vereda.getMunicipio())) {
                        depto.addMunicipio(vereda.getMunicipio());
                    }

                    posMun = depto.getMunicipios().indexOf(vereda.getMunicipio());
                    mun = (Municipio)depto.getMunicipios().get(posMun);
                    mun.addVereda(vereda);
                }
            }
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener los departamentos", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Obtiene una lista con todos los ejes configurados en el sistema
    * @return lista de objetos Eje
    * @see gov.sir.core.negocio.modelo.Eje
    * @throws ForsetiException
    */
    public List getEjes() throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getZonaRegistralDAO().getEjes();
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener los ejes", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Obtiene una lista con todos los posibles tipos de predio
    * configurados en el sistema
    * @return lista de objetos tipo TipoPredio
    * @see gov.sir.core.negocio.modelo.TipoPredio
    * @throws ForsetiException
    */
    public List getTiposPredio() throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getFolioDAO().getTiposPredio();
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener los tipos de predio", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Atiende una b�squeda previamente definida en la base de datos. Retorna un objeto busqueda en donde se
    * encuentra toda la jerarqu�a de las respuestas con folios y anotaciones respuesta. Aumenta en uno el n�mero de
    * intentos de la b�squeda
    * @param busqueda
    * @return
    * @throws ForsetiException
    */
    public Busqueda atenderConsulta(BusquedaPk bid) throws ForsetiException {
        Busqueda b = null;

        try {
            b = forseti.getFolioDAO().ejecutarConsulta(bid);

            return b;
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible realizar la consulta", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    
    /**
     * Atiende una b�squeda  Retorna un objeto busqueda en donde se
     * encuentra toda la jerarqu�a de las respuestas con folios y anotaciones respuesta. 
     * @param busqueda
     * @return
     * @throws ForsetiException
     */
     public Busqueda atenderConsultaAdministracion(Busqueda busqueda) throws ForsetiException {
         Busqueda b = null;

         try {
             b = forseti.getFolioDAO().ejecutarConsultaAdministracion(busqueda);

             return b;
         }
         catch (DAOException e) {
             ForsetiException fe = new ForsetiException("No fue posible realizar la consulta", e);
             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
             throw fe;
         }
     }


    /**
    * Obtiene una lista con todos los posibles tipos de documentos
    * configurados en el sistema
    * @return lista de objetos TipoDocumento
    * @see gov.sir.core.negocio.modelo.TipoDocumento
    * @throws ForsetiException
    */
    public List getTiposDocumento() throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getFolioDAO().getTiposDocumento();
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener los tipos de documento", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Retorna los grupos de naturalezas jur�dicas, cada uno con su lista de naturalezas jur�dicas
    * @return Lista de GrupoNaturalezaJuridica
    * @see gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica
    * @throws ForsetiException
    */
    public List getGruposNaturalezaJuridica() throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getFolioDAO().getGruposNaturalezaJuridica();
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener los grupos de naturalezas juridicas", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }
    /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiaci�n_de_Naturaleza_Jur�dica
        */
    /**
    * Retorna los grupos de naturalezas jur�dicas, cada uno con su lista de naturalezas jur�dicas
    * @return Lista de GrupoNaturalezaJuridica
    * @see gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica
    * @throws ForsetiException
    */
    public List getGruposNaturalezaJuridicaAll() throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getFolioDAO().getGruposNaturalezaJuridicaAll();
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener los grupos de naturalezas juridicas", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Obtiene la lista de dominios de naturalezas jur�dicas
    * @return
    * @throws ForsetiException
    */
    public List getDominiosNaturalezaJuridica() throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getFolioDAO().getDominiosNaturalezaJuridica();
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener los dominios de naturalezas juridicas", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Retorna una lista de las oficinas origen de la vereda especificada, las oficinas origen tienen el objeto
    * TipoOficina
    * @param oid Identificador de la vereda
    * @return Lista de OficinaOrigen
    * @throws ForsetiException
    */
    public List getOficinasOrigenByVereda(VeredaPk oid) throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getFolioDAO().getOficinasOrigenByVereda(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener las oficinas de origen de la vereda especificada", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Retorna una Hashtable en donde la llave es el tipo de oficina y el valor es una lista
    * de las oficinas origen dentro de la vereda dada
    * @param vid Identificador de la Vereda
    * @return Hashtable
    * @throws ForsetiException
    */
    public Hashtable getTiposOficinaByVereda(VeredaPk vid) throws ForsetiException {
        List rta;
        Hashtable ht = new Hashtable();
        OficinaOrigen oficina;
        List aux;

        try {
            rta = forseti.getFolioDAO().getOficinasOrigenByVereda(vid);

            for (Iterator itr = rta.iterator(); itr.hasNext();) {
                oficina = (OficinaOrigen)itr.next();

                if (ht.containsKey(oficina.getTipoOficina())) {
                    aux = (List)ht.get(oficina.getTipoOficina());
                }
                else {
                    aux = new ArrayList();
                }

                aux.add(oficina);
                ht.put(oficina.getTipoOficina(), aux);
            }
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener las oficinas de origen de la vereda especificada", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return ht;
    }
    
    /**
    * Realiza las validaciones a las matriculas especificadas dada una lista
    * @param matricula
    * @return
    * @throws ForsetiException hay una lista de excepciones por matr�cula
    */
    public boolean validarMatriculas(List validaciones, List matriculas) throws ForsetiException {
        Validacion val;
        List errores = new ArrayList();
        Hashtable ht = new Hashtable();
        ForsetiException fe = new ForsetiException("Error en la validaci�n de las matriculas");
        String matricula;

        for (Iterator itr = validaciones.iterator(); itr.hasNext();) {
            val = (Validacion)itr.next();

            if (val.getIdValidacion().equals(CValidacion.FOLIO_REQUERIDO)) {
                if (matriculas.size() == 0) {
                    errores.add("Se debe suministrar por lo menos un folio");
                    ht.put("NO_SUMINISTRADA", errores);
                }
            }
        }

        for (Iterator itr2 = matriculas.iterator(); itr2.hasNext();) {
            matricula = (String)itr2.next();
            errores = new ArrayList();

            for (Iterator itr = validaciones.iterator(); itr.hasNext();) {
                val = (Validacion)itr.next();

                if (val.getIdValidacion().equals(CValidacion.FOLIO_EXISTE)) {
                    if (!this.existeFolio(matricula)) {
                        errores.add(CValidacion.FOLIO_EXISTE_MSG);
                    }
                    continue;
                }
            }
            if (errores.size() > 0) {
                ht.put(matricula, errores);
            }
        }
        if(errores.isEmpty() || !(errores.size() > 0)){
            for (Iterator itr2 = matriculas.iterator(); itr2.hasNext();) {
                matricula = (String)itr2.next();
                errores = new ArrayList();

                for (Iterator itr = validaciones.iterator(); itr.hasNext();) {
                    val = (Validacion)itr.next();

                    if (val.getIdValidacion().equals(CValidacion.FOLIO_BLOQUEADO)) {
                        if (this.bloqueadoFolio(matricula)) {
                            errores.add(CValidacion.FOLIO_BLOQUEADO_MSG);
                        }
                        continue;
                    }

                    if (val.getIdValidacion().equals(CValidacion.FOLIO_DEBE_DINERO)) {
                        if (this.tieneDeudaFolio(matricula)) {
                            errores.add(CValidacion.FOLIO_DEBE_DINERO_MSG);
                        }
                        continue;
                    }

                    if (val.getIdValidacion().equals(CValidacion.FOLIO_EN_CUSTODIA)) {
                        if (this.enCustodiaFolio(matricula)) {
                            errores.add(CValidacion.FOLIO_EN_CUSTODIA_MSG);
                        }
                        continue;
                    }

                    if (val.getIdValidacion().equals(CValidacion.FOLIO_EN_TRAMITE)) {
                        if (this.enTramiteFolio(matricula)) {
                            errores.add(CValidacion.FOLIO_EN_TRAMITE_MSG);
                        }
                        continue;
                    }

                    if (val.getIdValidacion().equals(CValidacion.FOLIO_MAYOR_EXT)) {
                        if (this.mayorExtensionFolio(matricula)) {
                            errores.add(CValidacion.FOLIO_MAYOR_EXT_MSG);
                        }
                        continue;
                    }

                    if (val.getIdValidacion().equals(CValidacion.FOLIO_ANULADO)) {
                        if (this.anuladoFolio(matricula)) {
                            errores.add(CValidacion.FOLIO_ANULADO_MSG);
                        }
                        continue;
                    }

                    if (val.getIdValidacion().equals(CValidacion.FOLIO_CERRADO)) {
                        if (this.cerradoFolio(matricula)) {
                            errores.add(CValidacion.FOLIO_CERRADO_MSG);
                        }
                        continue;
                    }

                                    if (val.getIdValidacion().equals(CValidacion.FOLIO_INCONSISTENTE)) {
                                            if (this.inconsistenteFolio(matricula)) {
                                                    errores.add(CValidacion.FOLIO_INCONSISTENTE_MSG);
                                            }
                        continue;
                                    }

                    if(val.getIdValidacion().equals(CValidacion.FOLIO_TRASLADADO)) {
                        if(this.trasladadoFolio(matricula)) {
                            errores.add(CValidacion.FOLIO_TRASLADADO_MSG);
                        }
                        continue;
                    }

                    if(val.getIdValidacion().equals(CValidacion.FIRMA_REG_EXISTE)) {
                            CirculoPk cir = new CirculoPk();
                            int iCirculo = matricula.indexOf("-"); 
                            cir.idCirculo = matricula.substring(0,iCirculo);
                        if(!this.firmaRegistradorExiste(cir)) {
                            errores.add(CValidacion.FIRMA_REG_EXISTE_MSG);
                        }
                        continue;
                    }
                    /**
                    * @Autor: Fernando Padilla Velez, 30/06/2015
                    * @change:1209.AJUSTE.IMPRIMIBLE.CERTIFICADO.BP.SIR,
                    *         Se agrega la validacion para el folio bloqueado.
                    * */
                    if(val.getIdValidacion().equals(CValidacion.FOLIO_ESTADO_BLOQUEADO)) {
                        if(this.getFolioByID(matricula).getEstado().getIdEstado().equals(CEstadoFolio.BLOQUEADO)) {
                            errores.add(CValidacion.FOLIO_ESTADO_BLOQUEADO_MSG);
                        }
                        continue;
                    }
                }



                if (errores.size() > 0) {
                    ht.put(matricula, errores);
                }
            }
        }
        if (ht.size() > 0) {
            fe.setHashErrores(ht);
            throw fe;
        }

        return true;
    }
    
    /**
    * Obtiene las validaciones a las matriculas especificadas dada una lista
    * @param matricula
    * @return
    * @throws ForsetiException hay una lista de excepciones por matr�cula
    */
    public List<String> validarCertificadoEspecial(List validaciones, List matriculas) throws ForsetiException {
        Validacion val;
        List<String> errores = new ArrayList();
        ForsetiException fe = new ForsetiException("Error en la validaci�n de las matriculas");
        String matricula;

        if(errores.isEmpty() || !(errores.size() > 0)){
            for (Iterator itr2 = matriculas.iterator(); itr2.hasNext();) {
                matricula = (String)itr2.next();
                errores = new ArrayList();

                for (Iterator itr = validaciones.iterator(); itr.hasNext();) {
                    val = (Validacion)itr.next();

                    if (val.getIdValidacion().equals(CValidacion.FOLIO_BLOQUEADO)) {
                        if (this.bloqueadoFolio(matricula)) {
                            errores.add(CValidacion.FOLIO_BLOQUEADO_MSG);
                        }
                        continue;
                    }

                    if (val.getIdValidacion().equals(CValidacion.FOLIO_DEBE_DINERO)) {
                        if (this.tieneDeudaFolio(matricula)) {
                            errores.add(CValidacion.FOLIO_DEBE_DINERO_MSG);
                        }
                        continue;
                    }

                    if (val.getIdValidacion().equals(CValidacion.FOLIO_EN_CUSTODIA)) {
                        if (this.enCustodiaFolio(matricula)) {
                            errores.add(CValidacion.FOLIO_EN_CUSTODIA_MSG);
                        }
                        continue;
                    }

                    if (val.getIdValidacion().equals(CValidacion.FOLIO_EN_TRAMITE)) {
                        if (this.enTramiteFolio(matricula)) {
                            errores.add(CValidacion.FOLIO_EN_TRAMITE_MSG);
                        }
                        continue;
                    }

                    if (val.getIdValidacion().equals(CValidacion.FOLIO_MAYOR_EXT)) {
                        if (this.mayorExtensionFolio(matricula)) {
                            errores.add(CValidacion.FOLIO_MAYOR_EXT_MSG);
                        }
                        continue;
                    }

                    if (val.getIdValidacion().equals(CValidacion.FOLIO_ANULADO)) {
                        if (this.anuladoFolio(matricula)) {
                            errores.add(CValidacion.FOLIO_ANULADO_MSG);
                        }
                        continue;
                    }

                    if (val.getIdValidacion().equals(CValidacion.FOLIO_CERRADO)) {
                        if (this.cerradoFolio(matricula)) {
                            errores.add(CValidacion.FOLIO_CERRADO_MSG);
                        }
                        continue;
                    }

                                    if (val.getIdValidacion().equals(CValidacion.FOLIO_INCONSISTENTE)) {
                                            if (this.inconsistenteFolio(matricula)) {
                                                    errores.add(CValidacion.FOLIO_INCONSISTENTE_MSG);
                                            }
                        continue;
                                    }

                    if(val.getIdValidacion().equals(CValidacion.FOLIO_TRASLADADO)) {
                        if(this.trasladadoFolio(matricula)) {
                            errores.add(CValidacion.FOLIO_TRASLADADO_MSG);
                        }
                        continue;
                    }

                    if(val.getIdValidacion().equals(CValidacion.FIRMA_REG_EXISTE)) {
                            CirculoPk cir = new CirculoPk();
                            int iCirculo = matricula.indexOf("-"); 
                            cir.idCirculo = matricula.substring(0,iCirculo);
                        if(!this.firmaRegistradorExiste(cir)) {
                            errores.add(CValidacion.FIRMA_REG_EXISTE_MSG);
                        }
                        continue;
                    }
                    /**
                    * @Autor: Fernando Padilla Velez, 30/06/2015
                    * @change:1209.AJUSTE.IMPRIMIBLE.CERTIFICADO.BP.SIR,
                    *         Se agrega la validacion para el folio bloqueado.
                    * */
                    if(val.getIdValidacion().equals(CValidacion.FOLIO_ESTADO_BLOQUEADO)) {
                        if(this.getFolioByID(matricula).getEstado().getIdEstado().equals(CEstadoFolio.BLOQUEADO)) {
                            errores.add(CValidacion.FOLIO_ESTADO_BLOQUEADO_MSG);
                        }
                        continue;
                    }
                }



                if (errores.size() > 0) {
                    return errores;
                }
            }
        }
        return null;
    }

    public boolean firmaRegistradorExiste(CirculoPk circulo) throws ForsetiException {

    	boolean rta = false;

        FirmaRegistrador firmaRegistrador = null;
        
        try {
        	String cargo = CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;
        	firmaRegistrador =  this.getFirmaRegistradorActiva(circulo,cargo);

			if(firmaRegistrador==null){
				cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO;
				firmaRegistrador =  this.getFirmaRegistradorActiva(circulo,cargo);
			}
			if(firmaRegistrador==null){
				cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL;
				firmaRegistrador =  this.getFirmaRegistradorActiva(circulo,cargo);
			}
			if(firmaRegistrador==null){
				cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO;
				firmaRegistrador =  this.getFirmaRegistradorActiva(circulo,cargo);
			}
			
			if (firmaRegistrador == null)
			{
				return false;
			}
			
			String rutaFisica =  hermod.getPathFirmasRegistradores();

			String sNombre = firmaRegistrador.getNombreRegistrador();
			String archivo = firmaRegistrador.getIdArchivo();
			
			if (rutaFisica == null || rutaFisica.equals(""))
			{
				return false;
			}
			
			if (sNombre == null || sNombre.equals(""))
			{
				return false;
			}
			
			if (archivo == null || archivo.equals(""))
			{
				return false;
			}
			
			String nombreCompleto = ANPago.getNombreCompleto(rutaFisica,archivo);
			
			File file = new File(nombreCompleto);
			
			if (!file.canRead())
			{
				return false;
			}
			
			rta = true;
			
			
        } catch (ForsetiException e) {
            ForsetiException fe = new ForsetiException("No fue posible establecer si la firma del registrador existe", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable e) {
        	ForsetiException fe = new ForsetiException("No fue posible establecer si la firma del registrador existe", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
		}
        
        return rta;
	}

	/**
     * @param matricula
     * @return
     */
    public boolean trasladadoFolio(String matricula)
        throws ForsetiException {

        boolean rta = false;
        EstadoFolio ef = null;

        try {
            rta = forseti.getFolioDAO().trasladadoMatricula(matricula);
        } catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible establecer si el folio fue trasladado", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Valida la informaci�n del folio antes de crearlo. Si existe por lo menos un error lanza un DAOException
    * Los errores son listados en DAOException
    * @param datos
    * @throws ForsetiException
    */
    public void validarCrearFolio(Folio datos, boolean validarTurno) throws ForsetiException {
        List rta = null;

        try {
            forseti.getFolioDAO().validarCrearFolio(datos, validarTurno);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en la validaci�n del folio para creaci�n", e);
            fe.setErrores(e.getErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    
    /**
    * Finaliza el DAO
    * @throws ForsetiException cuando hay un error con la conexi�n
    */
    public void finalizar() throws ForsetiException {
        try {
            forseti.getFolioDAO().finalizar();
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el shutdown del servicio ", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
    * Bloquea los folios especificados en una lista de matriculas a un
    * usuario determinado. Si en el bloqueo de alg�n folio se produce
    * una excepci�n, esta quedar� registrada en la hastable de ForsetiException
    * en donde la llave es la matricula y el valor el mensaje de excepci�n.
    * Si no se produce ning�n error no se lanza ninguna excepci�n y se crea
    * una llave de bloqueo al usuario y se le asocian los folios dados. Finalmente
    * la LlaveBloquedo es retornada
    * @param matriculas
    * @param usuario
    * @return
    * @throws ForsetiException
    */
    public LlaveBloqueo bloquearFolios(List matriculas, Usuario usuario, TurnoPk turnoID) throws ForsetiException {
        try {
            return forseti.getFolioDAO().bloquearFolios(matriculas, usuario, turnoID);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de bloqueo de folios ", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
    * Agrega una direccion a un folio de manera temporal
    * @param oid Identificador del folio
    * @param datos Direcci�n a agregar, debe contener el objeto eje. El eje1 puede ser nulo
    * @param pm PersistenceManager
    * @return identificador de la direcci�n asignada por el sistema
    * @throws ForsetiException
    */
    public DireccionPk addDireccionToFolio(FolioPk oid, Direccion datos, Usuario us) throws ForsetiException {
        /*
        try {
        // rta = forseti.getFolioDAO().addAnotacionToFolio(oid, datos, usuario);

        } catch (DAOException e) {
        ForsetiException fe = new ForsetiException(
        "No fue posible agregar la anotaci�n al folio");
        Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
        throw fe;
        }*/
        return null;
    }

    /**
    * Obtiene una lista de c�rculos de la configuraci�n del sistema
    * @return lista de objetos Circulo
    * @see gov.sir.core.negocio.modelo.Circulo
    * @throws ForsetiException
    */
    public List getCirculos() throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getZonaRegistralDAO().getCirculos();
            
            Circulo circuloPortal = hermod.getCirculoPortal();
            
            String idCirculo = circuloPortal.getIdCirculo();
            	
        	for (Iterator circItera = rta.iterator();
        			circItera.hasNext();)
        	{
        		Circulo circulo = (Circulo)circItera.next();
        		if (circulo.getIdCirculo().equals(idCirculo))
        		{
        			circItera.remove();
        			break;
        		}
        	}
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener los c�rculos", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable e) {
        	ForsetiException fe = new ForsetiException("No fue posible obtener la configuraci�n del c�rculo de portal", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
		}

        return rta;
    }
    
    /**
     * Obtiene una lista de c�rculos de la configuraci�n del sistema
     * Solo los circulos que operan en SIR y tienen firma activa
     * @return lista de objetos Circulo
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws ForsetiException
     */
     public List getCirculosFechaProd() throws ForsetiException {
         List rta = null;

         try {
             rta = forseti.getZonaRegistralDAO().getCirculosFechaProd();
             
             Circulo circuloPortal = hermod.getCirculoPortal();
             
             String idCirculo = circuloPortal.getIdCirculo();
             	
         	for (Iterator circItera = rta.iterator();
         			circItera.hasNext();)
         	{
         		Circulo circulo = (Circulo)circItera.next();
         		if (circulo.getIdCirculo().equals(idCirculo))
         		{
         			circItera.remove();
         			break;
         		}
         	}
         }
         catch (DAOException e) {
             ForsetiException fe = new ForsetiException("No fue posible obtener los c�rculos", e);
             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
             throw fe;
         } catch (Throwable e) {
         	ForsetiException fe = new ForsetiException("No fue posible obtener la configuraci�n del c�rculo de portal", e);
         	Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
             throw fe;
 		}

         return rta;
     }
    
    /**
     * Obtiene una lista de c�rculos que tienen configurada
     * la firma del  registrador
     * @return lista de objetos Circulo
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws ForsetiException
     */
     public List getCirculosFirmaRegistrador() throws ForsetiException {
         List rta = null;

         try {
             rta = forseti.getZonaRegistralDAO().getCirculosFirmaRegistrador();
         }
         catch (DAOException e) {
             ForsetiException fe = new ForsetiException("No fue posible obtener los circulos con Firma de Registrador", e);
             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
             throw fe;
         }

         return rta;
     }

    /**
    * Obtiene un objeto Circulo dado su identificador
    * @param oid identificador del circulo
    * @return objeto Circulo
    * @throws ForsetiException
    */
    public Circulo getCirculo(CirculoPk oid) throws ForsetiException {
        Circulo rta = null;

        try {
            rta = forseti.getZonaRegistralDAO().getCirculo(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener el circulo", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Adiciona un eje a la configuraci�n del sistema
    * @param datos objeto Eje con sus atributos exceptuando su identificador
    * el cual es generado por el sistema
    * @param usuario que adiciona el eje
    * @return identificador de Eje generado
    * @throws ForsetiException
    */
    public EjePk addEje(Eje datos, Usuario usuario) throws ForsetiException {
        EjePk rta = null;

        try {
            rta = forseti.getZonaRegistralDAO().addEje(datos, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible crear el eje", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Adiciona un EstadoAnotacion a la configuraci�n del sistema
    * @param datos objeto EstadoAnotacion con sus atributos exceptuando su identificador
    * el cual es generado por el sistema
    * @return identificador de estadoFolio generado
    */
    public EstadoAnotacionPk addEstadoAnotacion(EstadoAnotacion datos) throws ForsetiException {
        EstadoAnotacionPk rta = null;

        try {
            rta = forseti.getFolioDAO().addEstadoAnotacion(datos);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible crear el estado de la anotaci�n", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Obtiene una lista de estados anotacion de la configuraci�n del sistema
    * @return lista de objetos EstadoAnotacion
    * @see gov.sir.core.negocio.modelo.EstadoAnotacion
    * @throws ForsetiException
    */
    public List getEstadosAnotacion() throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getFolioDAO().getEstadosAnotacion();
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener los estados de anotacion", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Obtiene una lista con todos los posibles estados de un folio
    * configurados en el sistema
    * @return lista de objetos EstadoFolio
    * @see gov.sir.core.negocio.modelo.EstadoFolio
    * @throws ForsetiException
    */
    public List getEstadosFolio() throws ForsetiException {
        List rta = null;

        try {
            rta = forseti.getFolioDAO().getEstadosFolio();
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener los estados de folio", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Agrega un grupo de naturaleza juridica la configuraci�n del sistema
    * @param naturaleza GrupoNaturalezaJuridica con su nombre
    * @throws ForsetiException
    */
    public GrupoNaturalezaJuridicaPk addGrupoNaturalezaJuridica(GrupoNaturalezaJuridica naturaleza) throws ForsetiException {
        GrupoNaturalezaJuridicaPk rta = null;

        try {
            rta = forseti.getFolioDAO().addGrupoNaturalezaJuridica(naturaleza);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible crear el grupo de naturaleza jur�dica", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Retorna un GrupoNaturalezaJuridica existente en el sistema
    * @param  GrupoNaturalezaJuridica.ID  identificador del objeto
    * @return GrupoNaturalezaJuridica buscado
    * @throws ForsetiException
    */
    public GrupoNaturalezaJuridica getGrupoNaturalezaJuridica(GrupoNaturalezaJuridicaPk oid) throws ForsetiException {
        GrupoNaturalezaJuridica rta = null;

        try {
            rta = forseti.getFolioDAO().getGrupoNaturalezaJuridica(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener el grupo de naturaleza juridica", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    *
    * @param oid
    * @return
    * @throws ForsetiException
    */
    public DominioNaturalezaJuridica getDominioNaturalezaJuridica(DominioNaturalezaJuridicaPk oid) throws ForsetiException {
        DominioNaturalezaJuridica rta = null;

        try {
            rta = forseti.getFolioDAO().getDominioNaturalezaJuridica(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener el dominio de naturaleza juridica", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * @param notaria con su tipo, nombre, numero, departamento, municipio
    * @return
    * @throws ForsetiException
    */
    public OficinaOrigenPk addOficinaOrigen(OficinaOrigen oficina) throws ForsetiException {
        OficinaOrigenPk rta = null;

        try {
            rta = forseti.getFolioDAO().addOficinaOrigen(oficina);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible crear la oficina origen", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Adiciona una vereda a un municipio dado
    * @param datos objeto municipio con sus atributos exceptuando su identificaci�n
    * el cual es generado por el sistema
    * @param oid identificador del municipio
    * @return identificaci�n de la vereda generada
    * @throws ForsetiException
    */
    public VeredaPk addVeredaToMunicipio(Vereda ver, MunicipioPk oid) throws ForsetiException {
        VeredaPk rta = null;

        try {
            rta = forseti.getZonaRegistralDAO().addVeredaToMunicipio(ver, oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible crear la vereda", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    *
    * @param oid
    * @param datos
    * @return
    * @throws ForsetiException
    */
    public ZonaRegistralPk addZonaRegistralToCirculo(CirculoPk oid, ZonaRegistral datos) throws ForsetiException {
        ZonaRegistralPk rta = null;

        try {
            rta = forseti.getZonaRegistralDAO().addZonaRegistralToCirculo(oid, datos);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible crear la zona registral", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    *
    * @param datos
    * @param gid
    * @param usuario que adiciona la naturaleza juridica
    * @return
    * @throws ForsetiException
    */
    public NaturalezaJuridicaPk addNaturalezaJuridicaToGrupo(NaturalezaJuridica datos, GrupoNaturalezaJuridicaPk gid, Usuario usuario) throws ForsetiException {
        NaturalezaJuridicaPk rta = null;

        try {
            rta = forseti.getFolioDAO().addNaturalezaJuridicaToGrupo(datos, gid, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible crear la naturaleza juridica", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Adiciona un tipo de oficina a la configuraci�n del sistema
    * @param datos objeto TipoOficina con sus atributos exceptuando su identificador
    * el cual es generado por el sistema
    * @param usuario que adiciona el tipo de oficina
    * @return identificador de TipoOficina generado
    * @throws ForsetiException
    */
    public TipoOficinaPk addTipoOficina(TipoOficina datos, Usuario usuario) throws ForsetiException {
        TipoOficinaPk rta = null;

        try {
            rta = forseti.getFolioDAO().addTipoOficina(datos, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible crear el tipo de oficina", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Adiciona un TipoPredio a la configuraci�n del sistema
    * @param datos objeto TipoPredio con sus atributos exceptuando su identificador
    * el cual es generado por el sistema
    * @return identificador de estadoFolio generado
    * @throws ForsetiException
    */
    public TipoPredioPk addTipoPredio(TipoPredio datos) throws ForsetiException {
        TipoPredioPk rta = null;

        try {
            rta = forseti.getFolioDAO().addTipoPredio(datos);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible crear el tipo de predio", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }

    /**
    * Adiciona un tipo de documento a la configuraci�n del sistema
    * @param datos objeto TipoDocumento con sus atributos exceptuando su identificador
    * el cual es generado por el sistema
    * @return identificador de TipoAnotacion generado
    * @throws ForsetiException
    */
    public TipoDocumentoPk addTipoDocumento(TipoDocumento datos, Usuario usuario) throws ForsetiException {
        TipoDocumentoPk rta = null;

        try {
            rta = forseti.getFolioDAO().addTipoDocumento(datos, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible crear el tipo de documento", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }

        return rta;
    }


    /*
    * Desbloquea los folios especificados de una lista de matriculas a un
    * usuario determinado. Si en el desbloqueo de alg�n folio se produce
    * una excepci�n, esta quedar� registrada en la hastable de ForsetiException
    * en donde la llave es la matricula y el valor el mensaje de excepci�n.
    * Si no se produce ning�n error no se lanza ninguna excepci�n
    * @param matriculas
    * @param usuario
    * @return
    * @throws ForsetiException
    *
    public boolean desbloquearFolios(List matriculas, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getFolioDAO().desbloquearFolios(matriculas, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de desbloqueo de folios ", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
*/

    /**
    * Actualiza la informaci�n de un folio dependiendo de los cambios que vienen
    * en el objeto. El usuario debe tener bloqueado el folio para poder
    * afectar la informaci�n de �ste, de lo contrario se genera un ForsetiException.
    * Los cambios quedan registrados en el esquema temporal.
    * Los cambios o actualizaciones de los objetos dependientes del folio se comportan
    * de la siguiente manera: (Se actualizan los que vienen diferentes a null)
    *
    * Lindero: folio.getLindero()
    * El lindero puede ser actualizado.
    *
    * C�digo catastral: folio.getCodCatastral()
    * El c�digo catastral puede ser actualizado.
    *
    * C�digo catastral anterior: folio.getCodCatastralAnterior()
    * El c�digo catastral anterior puede ser actualizado.
    *
    * Complementaci�n: folio.getComplementacion()
    * La complementaci�n puede ser actualizada. Si se afecta la complementaci�n de un
    * folio, se afectan la de todos los folios que hagan referencia a la misma cuando
    * se vuelva definitivo
    *
    * Tipo de predio: folio.getTipoPredio()
    * El tipo de predio puede ser actualizado
    *
    * Estado de predio: folio.getEstado()
    * El estado del predio puede ser modificado
    *
    * Salvedades folio: folio.getSalvedades()
    * Las salvedades s�lo pueden ser a�adidas, por lo tanto no necesitan IdSalvedad
    *
    * Direcciones: (folio.getDirecciones())
    * Las direcciones nunca son actualizadas, s�lamente son insertadas. Por lo tanto
    * toda direcci�n que llegue ser� insertada dentro de las direcciones temporales
    * del folio. Cada direcci�n debe tener el primer eje asociado.
    *
    * PARA ANOTACIONES:
    *
    * Anotaciones: (folio.getAnotaciones())
    * Las anotaciones pueden ser actualizadas o a�adidas.
    *
    * 1. Adici�n de anotaciones:
    *
    * Una anotaci�n siempre es a�adida en las anotaciones temporales. Para agregar una anotaci�n,
    * se debe crear un objeto Anotacion sin setearle IdAnotacion y se tienen que asociar sus objetos
    * b�sicos: NaturalezaJuridica, TipoAnotacion y Estado. La anotaci�n puede incluir ciudadanos a
    * trav�s de la lista de getAnotacionesCiudadanos(). Cada anotaci�nCiudadano debe tener seteado
    * el objeto ciudadano con el n�mero y tipo de documento del ciudadano. Si el ciudadano existe
    * se asocia, si no existe se crea y se asocia. La anotaci�n tambi�n puede incluir salvedades,
    * las salvedades se agregan a trav�s de la lista getSalvedadesAnotacion(). Tambi�n puede incluir
    * cancelaciones a trav�s de la lista getAnotacionesCancelacion(). Cada Cancelaci�n debe tener
    * asociado el objeto "cancelada" que es una anotaci�n existente con el ID seteado.
    *
    * 2. Borrar anotaciones temporales
    *
    * Una anotaci�n temporal puede ser eliminada con todas sus dependencias (salvedades, ciudadanos y cancelaciones).
    * No se pueden eliminar anotaciones que est�n encadenando folios, es decir las de tipo "GENERADORA" o "DERIVADA"
    * Tampoco se pueden eliminar anotaciones temporales canceladas por otra anotaci�n.
    * Para eliminar una anotaci�n temporal se debe pasar un objeto anotaci�n dentro de la lista de anotaciones
    * de folio con el IdAnotacion seteado que se quiere eliminar y con el atributo toDelete de anotaci�n en true.
    *
    * 3. Actualizar anotaciones temporales
    *
    * Una anotaci�n temporal puede ser actualizada. Se pueden actualizar datos b�sicos como:
    * comentario, especificaci�n, orden, naturaleza jur�dica y estado. Se pueden eliminar y a�adir
    * ciudadanos de la anotaci�n, eliminar, actualizar y a�adir salvedades.
    *
    * Para actualizar una anotaci�n, se debe incluir en la lista de anotaciones de folio un
    * objeto anotaci�n con el idAnotacion seteado, no se debe setear el toDelete. Los datos b�sicos
    * que se quieren actualizar deben ser seteados en el objeto anotaci�n.
    *
    * Los ciudadanos pueden ser a�adidos de la misma manera en que se agregan cuando se inserta
    * una anotaci�n. Tambi�n pueden ser eliminados seteando en la AnotacionCiudadano el rol,
    * la propiedad toDelete a true, y el objeto ciudadano con el idCiudadano seteado.
    *
    * Las salvedades pueden ser a�adidas de la misma manera en que se agregan cuando se inserta
    * una anotaci�n. Tambi�n pueden ser eliminados seteando el idSalvedad y la propiedad toDelete
    * a true. Las salvedades pueden ser actualizadas seteando el idSalvedad y seteando la nueva
    * descripci�n.
    *
    * Las cancelaciones pueden ser a�adidas de la misma manera en que se agregan cuando se inserta
    * una anotaci�n. Tambi�n pueden ser eliminadas seteando el flag toDelete de la cancelaci�n en true
    * y asociando la anotaci�n cancelada con su respectivo idAnotacion
    *
    *
    * 4. Actualizar anotaciones definitivas (Correccion)
    *
    * Una anotaci�n definitiva puede ser actualizada. Se pueden actualizar datos b�sicos como:
    * comentario, especificaci�n, orden, naturaleza jur�dica y estado.  Se pueden eliminar y a�adir
    * ciudadanos de la anotaci�n, eliminar, actualizar y a�adir salvedades.
    *
    * Para actualizar una anotaci�n definitiva, se debe incluir en la lista de anotaciones de folio un
    * objeto anotaci�n con el idAnotacion seteado. Los datos b�sicos que se quieren actualizar deben ser
    * seteados en el objeto anotaci�n.
    *
    * Los ciudadanos pueden ser a�adidos de la misma manera en que se agregan cuando se inserta
    * una anotaci�n. Tambi�n pueden ser eliminados seteando en la AnotacionCiudadano el rol,
    * la propiedad toDelete a true, y el objeto ciudadano con el idCiudadano seteado.
    *
    * Las salvedades pueden ser a�adidas de la misma manera en que se agregan cuando se inserta
    * una anotaci�n. Tambi�n pueden ser eliminados seteando el idSalvedad y la propiedad toDelete
    * a true. Las salvedades pueden ser actualizadas seteando el idSalvedad y seteando la nueva
    * descripci�n.
    *
    * Cada vez que se actualice una anotaci�n definitiva se guarda el registro de su estado
    * actual creando unanueva anotaci�n y dej�ndola con estado "ACTUALIZADA"
    *
    *
    * @param folio
    * @param usuario
    * @return
    * @throws DAOException
    */
    public boolean updateFolio(Folio folio, Usuario usuario, TurnoPk tid, boolean validarTurno) throws ForsetiException {
        try {
            return forseti.getFolioDAO().updateFolio(folio, usuario, tid, validarTurno);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException( e.getMessage() + ". (Fallo en el servicio de actualizaci�n de folios)." , e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    
    /**
     * Alternativa para guardar los folios derivados
     * @param datos
     * @param usuario
     * @return
     * @throws ForsetiException
     */
    
    
    public boolean updateFolioFD(Folio folio, Usuario usuario, TurnoPk tid, boolean validarTurno,List lstAnotaFolioHijo, List lstAnotaFolioPadre, String anotacionUpdate, String matriculaUpdate, List lstHijosRemove) throws ForsetiException {
        try {
            return forseti.getFolioDAO().updateFolioFD(folio, usuario, tid, validarTurno, lstAnotaFolioHijo, lstAnotaFolioPadre, anotacionUpdate, matriculaUpdate, lstHijosRemove);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException( e.getMessage() + ". (Fallo en el servicio de actualizaci�n de folios)." , e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class,fe.getMessage(), e);
            throw fe;
        }
    }

    /**
    * Hace definitivos los cambios temporales aplicados al folio, y desbloquea el folio
    * @param datos
    * @param usuario
    * @return
    * @throws ForsetiException
    */
    public boolean hacerDefinitivoFolio(Folio folio, Usuario usuario, TurnoPk tid, boolean validarTurno) throws ForsetiException {
        ImprimibleCertificado imprimible = null;
        
        try {
            boolean rta = forseti.getFolioDAO().hacerDefinitivoFolio(folio, usuario, tid, validarTurno);         
            if (! this.mayorExtensionFolio(folio.getIdMatricula()) )
            {	
            	// obtenemos los valores configurados a nivel del sistema para la impresion de certificados prefabricados      
	    		try {
	    			boolean usePrefabricado;
	    			ForsetiProperties prop = ForsetiProperties.getInstancia();
	    			
	    			/* verificamos si la aplicacion esta configurada para el uso de prefabricados */
	    			usePrefabricado = Boolean.valueOf(prop.getProperty(ForsetiProperties.USAR_PREFABRICADO)).booleanValue();
	    			
	    			if (usePrefabricado) {
	    				/* verificamos si el circulo asociado a la solicitud del certificado, se encuentra habilitado
	    				 * para el uso del proceso de prefabricados */
	    				final String circulos_activos = prop.getProperty(ForsetiProperties.CIRCULOS_ACTIVOS);
	    				
	    				if (circulos_activos != null) 
	    					usePrefabricado = circulos_activos == "ALL" || circulos_activos.indexOf(folio.getCirculo()) != -1? true : false;
	    				
	    				/* verificamos que el folio no sea de mayor extension */
	    				if (usePrefabricado ) {
	    					try {
		    					imprimible = forseti.getFolioDAO().getImprimibleCertificadoByMatricula(folio.getIdMatricula());
		    					impresion.getImpresionDAO().guardarPrefabricado(imprimible);  
		    				} catch (Exception e) {
		    					impresion.getImpresionDAO().registrarObsoleto(folio.getCirculo(), folio.getIdMatricula());
		    					e.printStackTrace();
		    				}
	    				}
	    	            
	    			}
	    		} catch (Exception e) {
	    			Log.getInstance().error(ForsetiService.class,"Fallo el proceso de generacion de prefabricados", e);
	    		}
            }
    		
            if(validarTurno){
				List matriculas = new ArrayList();
				matriculas.add(folio.getIdMatricula());
				forseti.getFolioDAO().desbloquearFolios(matriculas, usuario);
            }
            return rta;

        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio para hacer definitivos los datos temporales", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
    * Deshace los cambios que est�n siendo aplicados al folio, borra toda la informaci�n temporal. Finalmente
    * desbloquea el folio
    * @param datos
    * @param usuario
    * @return
    * @throws ForsetiException
    */
    public boolean deshacerCambiosFolio(Folio datos, Usuario usuario) throws ForsetiException {
        try {
            boolean rta =  forseti.getFolioDAO().deshacerCambiosFolio(datos, usuario);
            List matriculas = new ArrayList();
            matriculas.add(datos.getIdMatricula());
            forseti.getFolioDAO().desbloquearFolios(matriculas, usuario);
            return rta;
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio para deshacer los datos temporales del folio", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    
    /**
	* Deshace los cambios que est�n siendo aplicados a los ciudadanos, borra toda la informaci�n temporal
	* @param datos
	* @param usuario
	* @return
	* @throws ForsetiException
	*/
	public boolean deshacerCambiosCiudadanosTurno(TurnoPk turnoID, Usuario usuario, boolean desbloquear) throws ForsetiException{
	try {
		forseti.getFolioDAO().deshacerCambiosCiudadanosTurno(turnoID, usuario);
		
		if (desbloquear) {
			forseti.getFolioDAO().desbloquearFolios(turnoID, usuario);
		}
		
		return true;
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de deshacer los cambios temporales de un turno", e);
		fe.setHashErrores(e.getHashErrores());
		fe.setErrores(e.getErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}

    /**
    * Retorna la informaci�n del folio dependiendo del usuario solicitante,
    * si el usuario tiene bloqueado el folio se retorna la informaci�n definitiva
    * y temporal, si el usuario no tiene el bloqueo del folio se lanza una excepcion
    * indicando lo sucedido
    * @param oid
    * @param us
    * @return
    * @throws ForsetiException
    */
    public Folio getFolioByID(FolioPk oid, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getFolioByID(oid, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio para obtener el folio", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Retorna la informaci�n del folio dependiendo del usuario solicitante,
     * si el usuario tiene bloqueado el folio se retorna la informaci�n definitiva
     * y temporal, si el usuario no tiene el bloqueo del folio se lanza una excepcion
     * indicando lo sucedido. Evalua si el folio es definitivo
     * @param oid
     * @param us
     * @return
     * @throws ForsetiException
     */
     public Folio loadFolioByID(FolioPk oid, Usuario usuario) throws ForsetiException {
         try {
             return forseti.getFolioDAO().loadFolioByID(oid, usuario);
         }
         catch (DAOException e) {
             ForsetiException fe = new ForsetiException("Fallo en el servicio para obtener el folio", e);
             fe.setHashErrores(e.getHashErrores());
             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
             throw fe;
         }
     }
    
    
    /**
     * Segrega un folio
     * @param datos Tiene los IDs del folio a segregar y los IDs de la anotaciones a heredar
     * @param foliosDerivados Lista de folios derivados
     * @param usuario Usuario que realiza la segregaci�n, debe tener el folio bloqueado
     * @param nuevaAnotacion Anotaci�n que se crea en el padre y en los hijos para encadenarlos
     * @param copiarComentarioSegregacion Permite determinar si se debe o no copiar el comentario de la anotaci�n origen a las
     *        nuevas anotaciones de los nuevos folios.
     * @param copiarComentarioHeredadas Permite determinar si se debe o no copiar el comentario de las anotaciones a heredar en los nuevos folios
     * @return
     * @throws Throwable
     */
    public List segregarFolio(Folio datos, List foliosDerivados, Usuario usuario, Anotacion nuevaAnotacion,
    						  boolean copiarComentarioSegregacion, boolean copiarComentarioHeredadas, TurnoPk oid) throws ForsetiException {
        try {
            return forseti.getFolioDAO().segregarFolio(datos, foliosDerivados, usuario, nuevaAnotacion, copiarComentarioSegregacion, copiarComentarioHeredadas, oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de segregar folio", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    
    /**
     * Segrega un folio a trav�s de un procedimiento almacenado en PL/SLQ
     * @param datos Datos del folio a segregar
     * @param oid ID el turno a segregar
     * @return List con los folios segregados
     * @author      :   Julio Alc�zar Rivas
     * @change      :   Se modifico el metodo segregarFolioPLSQL agregandole el param SalvedadAnotacion salvedad
     * Caso Mantis  :   04131
     * @throws Throwable
     */
    public List segregarFolioPLSQL(Folio datos, TurnoPk oid, SalvedadAnotacion salvedad, String IdAnotacionDef) throws ForsetiException {
    	try {            return forseti.getFolioDAO().segregarFolioPLSQL(datos, oid, salvedad,IdAnotacionDef);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de segregar folio", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /* (non-Javadoc)
    * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#englobarFolio(java.util.List, gov.sir.core.negocio.modelo.Folio, gov.sir.core.negocio.modelo.Usuario, java.util.List)
    */
    public Folio englobarFolio(List foliosFuentes, Folio nuevoFolio, Usuario usuario, List tabla, Anotacion anotacion, FolioDerivado informacionAdicional,
    					       boolean copiarComentarioEnglobe, boolean copiarComentarioHeredadas, TurnoPk oid) throws ForsetiException {
        try {
            return forseti.getFolioDAO().englobarFolio(foliosFuentes, nuevoFolio, usuario, tabla,  anotacion,  informacionAdicional, copiarComentarioEnglobe, copiarComentarioHeredadas, oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fall� en el servicio de englobar folio", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
    * Delega el bloqueo de todos los folios asociados al turno
    * Si alg�n folio no est� bloqueado se genera una excepcion guardada
    * en un hashtable donde la llave es el n�mero de matr�cula y el valor
    * es la descripci�n del error
    * @param oid
    * @param usuario
    * @return
    * @throws ForsetiException
    */
    public LlaveBloqueo delegarBloqueoFolios(TurnoPk oid, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getFolioDAO().delegarBloqueoFolios(oid, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de delegar bloqueo de folio", e);
            fe.setHashErrores(e.getHashErrores());
            /**
             * @author Cesar Ram�rez
             * @change: 1156.111.USUARIOS.ROLES.INACTIVOS
             * Env�a el listado los errores generados.
             **/
            fe.setErrores(e.getErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    
    /**
     * Delega el bloqueo de todos los folios asociados al turno
     * @param oid
     * @param usuario
     * @return
     * @throws ForsetiException
     */
     public LlaveBloqueo delegarBloqueoFoliosObligatorio(TurnoPk oid, Usuario usuario) throws ForsetiException {
         try {
             return forseti.getFolioDAO().delegarBloqueoFoliosObligatorio(oid, usuario);
         }
         catch (DAOException e) {
             ForsetiException fe = new ForsetiException("Fallo en el servicio de delegar bloqueo de folio", e);
             fe.setHashErrores(e.getHashErrores());
             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
             throw fe;
         }
     }

    /**
    * Determina si una matricula tiene por lo menos una anotaci�n temporal
    * @param matricula
    * @return
    * @throws ForsetiException
    */
    public boolean hasAnotacionesTMP(FolioPk oid) throws ForsetiException {
        try {
            return forseti.getFolioDAO().hasAnotacionesTMP(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de delegar bloqueo de folio", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

/*
    /**
    * Bloquea los todos los folios asociados a un turno
    * Si alg�n folio est� bloqueado se genera una excepcion guardada
    * en un hashtable donde la llave es el n�mero de matr�cula y el valor
    * es la descripci�n del error
    * @param oid
    * @param usuario
    * @return
    * @throws ForsetiException
    *
    public LlaveBloqueo bloquearFolios(Turno.ID oid, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getFolioDAO().bloquearFolios(oid, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de bloqueo de folios", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }


    /**
    * Desbloquea todos los folios asociados a un turno, si en el turno existe alg�n folio que no
    * est� bloqueado se produce una excepci�n
    * @param oid
    * @param usuario
    * @return
    * @throws ForsetiException
    *
    public boolean desbloquearFolios(Turno.ID oid, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getFolioDAO().desbloquearFolios(oid, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de desbloqueo de folios", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
*/
    
    /**
     * Desbloquea todos los folios asociados a un turno, si en el turno existe alg�n folio que no
     * est� bloqueado se produce una excepci�n no valida que el bloqueo lo tenga un usuario.
     * @param oid
     * @param usuario
     * @return
     * @throws DAOException
     */
     public  boolean desbloquearFoliosTurno(TurnoPk oid, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getFolioDAO().desbloquearFoliosTurno(oid, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de desbloqueo de folios", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

     /**
     * Desbloquea un folios si no esta bloquedado
     * se produce una excepci�n
     * @param folio
     * @return
     * @throws ForsetiException
     */
     public boolean desbloquearFolio(Folio folio) throws ForsetiException {
         try {
             return forseti.getFolioDAO().desbloquearFolio(folio);
         }
         catch (DAOException e) {
             ForsetiException fe = new ForsetiException("Fallo en el servicio de desbloqueo de folio", e);
             fe.setHashErrores(e.getHashErrores());
             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
             throw fe;
         }
     }

    /**
    * Acualiza la informaci�n de las matr�culas dadas con los datos
    * del folio.
    * @param datos
    * @param ids Listas de objetos tipo Folio.ID
    * @param usuario
    * @return
    * @throws ForsetiException
    */
    public boolean updateFolios(Folio datos, List ids, Usuario usuario, boolean validarBloqueo) throws ForsetiException {
        try {
            return forseti.getFolioDAO().updateFolios(datos, ids, usuario, validarBloqueo);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de actualizaci�n de folios", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
    * Obtiene una lista con todos los posibles tipos de oficina
    * configurados en el sistema
    * @return lista de objetos TipoOficina
    * @see gov.sir.core.negocio.modelo.TipoOficina
    * @throws DAOException
    */
    public List getTiposOficina() throws ForsetiException {
        try {
            return forseti.getFolioDAO().getTiposOficina();
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de tipos de oficina", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
    * Agrega un dominio de naturaleza jur�dica
    * @param naturaleza
    * @return
    * @throws ForsetiException
    */
    public DominioNaturalezaJuridicaPk addDominioNaturalezaJuridica(DominioNaturalezaJuridica naturaleza) throws ForsetiException {
        try {
            return forseti.getFolioDAO().addDominioNaturalezaJuridica(naturaleza);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de agregaci�n de dominio de naturaleza jur�dica", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
    * Calcula el n�mero de anotaciones del folio con el criterio dado. El criterio
    * es tomado del conjunto de constantes de CCriterio
    * @param oid
    * @param criterio
    * @param valor
    * @return
    * @throws DAOException
    */
    public long getCountAnotacionesFolio(FolioPk oid, String criterio, String valor) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getCountAnotacionesFolio(oid, criterio, valor, false);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de c�lculo de n�mero de anotaciones", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
    * Adiciona un festivo a un circulo dado
    * @param datos objeto circulofestivo con sus atributos
    * @param oid identificador del circulo
    * @return identificaci�n del circulofestivo generado
    * @throws DAOException
    */
    public CirculoFestivoPk addCirculoFestivo(CirculoPk oid, CirculoFestivo datos) throws ForsetiException {
        try {
            return forseti.getZonaRegistralDAO().addCirculoFestivo(oid, datos);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de adicci�n de festivos", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
    * Obtiene los festivo configurados en el sistema para un c�rculo determinado
    * @return lista de objetos tipo CirculoFestivo
    * @see gov.sir.core.negocio.modelo.CirculoFestivo
    * @throws DAOException
    */
    public List getFestivosCirculo(CirculoPk oid) throws ForsetiException {
        try {
            return forseti.getZonaRegistralDAO().getFestivosCirculo(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de festivos", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
    * Determina si la fecha ingresada esta configurada en el sistema como un
    * festivo para un c�rculo dado
    * @return boolean
    * @see gov.sir.core.negocio.modelo.CirculoFestivo
    * @throws DAOException
    */
    public boolean isFestivo(Date fecha, CirculoPk oid) throws ForsetiException {
        try {
            return forseti.getZonaRegistralDAO().isFestivo(fecha, oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de festivos", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
    * Retorna las anotaciones de un folio con el criterio dado. Se puede especificar
    * la posici�n inicial y el n�mero de anotaciones que se quieren obtener a partir
    * de dicha posici�n.
    * @param oid
    * @param criterio
    * @param valor
    * @param posicionInicial
    * @param cantidad
    * @return
    * @throws ForsetiException
    */
    public List getAnotacionesFolio(FolioPk oid, String criterio, String valor, int posicionInicial, int cantidad, boolean vigente) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getAnotacionesFolio(oid, criterio, valor, posicionInicial, cantidad, vigente);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de anotaciones", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * @author     : Ellery David Robles G.
     * @casoMantis : 08469.
     * @actaReq    : 024_151 - Error en anotaci�n de medida cautelar.
     * @change     : Retorna las anotaciones definitivas y temporales de un folio segun el parametro naturaleza jurudica.
     * @param      : oid.
     * @param      : naturalezaJuridica.
     * @throws     : ForsetiException.
     */
    @Override
    public List getAnotacionesFolioNJ(FolioPk oid, String naturalezaJuridica) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getAnotacionesFolioNJ(oid, naturalezaJuridica);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de anotaciones", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
    * Obtiene un objeto Folio dado su identificador
    * @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
    * @return Objeto Folio con sus objetos estado, complementacion, tipoPredio y ZonaRegistral,
    * y listas de salvedades folio y direcciones. No se incluyen las anotaciones
    * El objeto ZonaRegistral contiene la jerarqu�a circulo, vereda, municipio y departamento
    * null si  no encuentra un folio que coincida con el identificador dado
    * @throws DAOException
    */
    public Folio getFolioByIDSinAnotaciones(FolioPk oid) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getFolioByIDSinAnotaciones(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folio sin anotaciones", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    
    /**
     * Obtiene un objeto Folio Temporal dado su identificador
     * @param oid identificador del Folio conformado por Nro matricula
     * @return Objeto Folio temporal
     * @throws DAOException
     */
     public FolioDatosTMP getFolioDatosTMP(FolioDatosTMPPk oid) throws ForsetiException {
         try {
             return forseti.getFolioDAO().getFolioDatosTMP(oid);
         }
         catch (DAOException e) {
             ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folio sin anotaciones", e);
             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
             throw fe;
         }
     }
    
    /**
     * Obtiene un objeto Folio dado su identificador aun si es temporal
     * @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
     * @return Objeto Folio con sus objetos estado, complementacion, tipoPredio y ZonaRegistral,
     * y listas de salvedades folio y direcciones. No se incluyen las anotaciones
     * El objeto ZonaRegistral contiene la jerarqu�a circulo, vereda, municipio y departamento
     * null si  no encuentra un folio que coincida con el identificador dado
     * @throws DAOException
     */
     public Folio getFolioEvenTempByIDSinAnotaciones(FolioPk oid) throws ForsetiException {
         try {
             return forseti.getFolioDAO().getFolioEvenTempByIDSinAnotaciones(oid);
         }
         catch (DAOException e) {
             ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folio sin anotaciones", e);
             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
             throw fe;
         }
     }

    /**
    * Obtiene un objeto Folio dado su identificador
    * @param matricula n�mero de matr�cula del folio
    * @return Objeto Folio con sus objetos estado, complementacion, tipoPredio y ZonaRegistral,
    * y listas de salvedades folio y direcciones. No se incluyen las anotaciones
    * El objeto ZonaRegistral contiene la jerarqu�a circulo, vereda, municipio y departamento
    * null si  no encuentra un folio que coincida con el identificador dado
    * @throws DAOException
    */
    public Folio getFolioByMatriculaSinAnotaciones(String matricula) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getFolioByMatriculaSinAnotaciones(matricula);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folio sin anotaciones por matricula", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
        * Obtiene las ZonaRegistrales configuradas en el sistema para un c�rculo determinado
        * @return lista de objetos tipo ZonaRegistral, cada uno tiene sus objetos vereda y circulo
        * @see gov.sir.core.negocio.modelo.ZonaRegistral
        * @throws ForsetiException
        */
    public List getZonaRegistralesCirculo(CirculoPk oid) throws ForsetiException {
        try {
            return forseti.getZonaRegistralDAO().getZonaRegistralesCirculo(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folio sin anotaciones por matricula", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
    * Obtiene un turno de correcciones activo que tenga asociado
    * el folio dado, si no existe un turno de correci�n activo con
    * el folio asociado retorna null
    * @param oid
    * @return
    * @throws ForsetiException
    */
    public Turno getTurnoCorreccionActivoFolio(FolioPk oid) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getTurnoCorreccionActivoFolio(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de turno de correcci�n activo dado el folio", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Si el turno va a deshacer los cambios temporales del folio
     * valida si existe otros turnos que tengan fecha
     * de inicio anterior y que no hayan pasado por la fase de firmar
     * que es la fase posterior a calificaci�n y que ya hayan sido calificados.
     * Ademas valida si existen turnos de correciones entre la fase
     * de correccion simple y revisar y aprobar.
     * Si hay alg�n error en la validaci�n se lanzar� una excepci�n
     * en donde la hastable interna tiene como llave la matricula y como
     * valor la lista de turnos que tienen el folio
     * @param oid
     * @throws DAOException
     */
    public void validarPrincipioPrioridadDeshacerCambiosTemporales(TurnoPk oid) throws ForsetiException {
    try {
        forseti.getFolioDAO().validarPrincipioPrioridadDeshacerCambiosTemporales(oid);
    }
    catch (DAOException e) {
        ForsetiException fe = new ForsetiException("Fallo en el servicio de validaci�n de principio de prioridad DeshacerCambios", e);
        fe.setHashErrores(e.getHashErrores());
        Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
        throw fe;
    }	
    }
    
    /**
    * Valida el principio de prioridad seg�n Art. 22 Dto 1250/70
    * Si el turno va a entrar a calificaci�n este servicio se puede
    * utilizar para validar si existe otros turnos que tengan fecha
    * de inicio anterior y que no hayan pasado por la fase de firmar
    * que es la fase posterior a calificaci�n
    * Si hay alg�n error en la validaci�n se lanzar� una excepci�n
    * en donde la hastable interna tiene como llave la matricula y como
    * valor la lista de turnos que tienen el folio
    * @param oid
    * @throws ForsetiException
    */
    public void validarPrincipioPrioridadCalificacion(TurnoPk oid, Usuario usuario) throws ForsetiException {
        try {
            forseti.getFolioDAO().validarPrincipioPrioridadCalificacion(oid, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de validaci�n de principio de prioridad Calificaci�n", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    
    /**
     * Valida el principio de prioridad Digitacion
     * Si el turno va a entrar a Digitaci�n este servicio se puede
     * utilizar para validar si existe otros turnos que tengan fecha
     * de inicio anterior y que esten en esta fase
     * Si hay alg�n error en la validaci�n se lanzar� una excepci�n
     * en donde la hastable interna tiene como llave la matricula y como
     * valor la lista de turnos que tienen el folio
     * @param oid
     * @throws DAOException
     */
    public void validarPrincipioPrioridadDigitacion(TurnoPk oid, Usuario usuario) throws ForsetiException {
        try {
            forseti.getFolioDAO().validarPrincipioPrioridadDigitacion(oid, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de validaci�n de principio de prioridad Calificaci�n", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    

    /**
     * Valida el principio de prioridad seg�n Art. 22 Dto 1250/70
     * Si el turno va a entrar a calificaci�n este servicio se puede
     * utilizar para validar si existe otros turnos que tengan fecha
     * de inicio anterior y que no hayan pasado de la fase de calificaci�n
     * Se retorna true si no hay turnos que hayan sido creado previamente o false si los hay.
     * @param oid
     * @param usuario
     * @throws Throwable
     */
     public boolean isTurnoValidoCalificacion(TurnoPk oid, Usuario usuario) throws ForsetiException {
         try {
             return forseti.getFolioDAO().isTurnoValidoCalificacion(oid, usuario);
         }
         catch (DAOException e) {
             ForsetiException fe = new ForsetiException("Fallo en el servicio de validaci�n de principio de prioridad Calificaci�n", e);
             fe.setHashErrores(e.getHashErrores());
             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
             throw fe;
         }
     }
     
     /**
      * Valida el principio de prioridad para salir de Calificaci�n
      * Si el turno va a entrar a calificaci�n este servicio se puede
      * utilizar para validar si existe otros turnos que tengan fecha
      * de inicio anterior y que no hayan pasado de la fase de calificaci�n
      * Se retorna true si no hay turnos que hayan sido creado previamente o false si los hay.
      * @param oid
      * @param usuario
      * @throws Throwable
      */
      public void isTurnoValidoSalidaCalificacion(TurnoPk oid, Usuario usuario) throws ForsetiException {
          try {
              forseti.getFolioDAO().isTurnoValidoSalidaCalificacion(oid, usuario);
          }
          catch (DAOException e) {
              ForsetiException fe = new ForsetiException("Fallo en el servicio de validaci�n de principio de prioridad Calificaci�n", e);
              fe.setHashErrores(e.getHashErrores());
              Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
              throw fe;
          }
      }
    

    /**
    * Devuelve el n�mero de anotaciones del folio indicado, si el folio no existe retorna 0
    * @param matricula
    * @return
    * @throws ForsetiException
    */
    public long getCountAnotacionesFolio(String matricula) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getCountAnotacionesFolio(matricula);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de n�mero de anotaciones del folio", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * A�ade una firma
     * @param archivo
     * @param oid
     * @return
     * @throws ForsetiException
     */
    public boolean addFirmaRegistradorToCirculo(FirmaRegistrador firmaReg, CirculoPk oid) throws ForsetiException {
        try {
            return forseti.getZonaRegistralDAO().addFirmaRegistradorToCirculo(firmaReg, oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de adici�n de firma de registrador a c�rculo", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Setea el flag activa a una firma registrador
     * ACTIVA -> la firma esta activa
     * INACTIVA -> la firma esta inactiva, pero puede volverse a activar
     * INACTIVA_DEFINITIVA -> la firma esta inactiva y no se puede volver a activar.
     * @param oid
     * @param activa 0 -> INACTIVA , 1 -> ACTIVA, 2 -> INACTIVA_DEFINITIVA
     * @return
     * @throws ForsetiException
     */
    public boolean setActivoFirmaRegistrador(FirmaRegistradorPk oid, int activa) throws ForsetiException {
        try {
            return forseti.getZonaRegistralDAO().setActivoFirmaRegistrador(oid, activa);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de adici�n de cambio de flag activo a la firma", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Valida el principio de prioridad para un turno. Se valida que para cada
     * matr�cula relacionada no exista un turno de correcci�n activo con la misma
     * matr�cula asociada. Si alg�n folio asociado al turno presenta esta situaci�n,
     * se lanza una excepci�n con la hashtable donde la llave es la matr�cula y el
     * valor es la lista de turnos de calificaci�n que tienen el folio
     * @param oid
     * @throws ForsetiException
     */
    public void validarPrincipioPrioridadCorreccion(TurnoPk oid) throws ForsetiException {
        try {
            forseti.getFolioDAO().validarPrincipioPrioridadCorreccion(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de validaci�n de principio de prioridad Correcci�n", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Retorna la lista con los folios hijos del folio especificado (Folio)
     * Si el folio no tiene hijos o no existe retorna una lista vac�a
     * @param oid
     * @return
     * @throws ForsetiException
     */
    public List getFoliosHijos(FolioPk oid) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getFoliosHijos(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folios hijos", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    
    /**
     * Retorna la lista con los folios hijos del folio especificado (Folio)
     * Si el folio no tiene hijos o no existe retorna una lista vac�a
     * @param oid
     * @return
     * @throws ForsetiException
     */
    public List getFoliosHijosOrdenAnotacion(FolioPk oid) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getFoliosHijosOrdenAnotacion(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folios hijos", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    

    /**
     * Retorna la lista con los los folios padre del folio especificado (Folio)
     * Si el folio no tiene padres o no existe retorna una lista vac�a
     * @param oid
     * @return
     * @throws ForsetiException
     */
    public List getFoliosPadre(FolioPk oid) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getFoliosPadre(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folios padre", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    
    /**
     * Retorna la lista con los folios Derivado hijos del folio especificado (Folio)
     * Si el folio no tiene hijos o no existe retorna una lista vac�a
     * @param oid
     * @return
     * @throws ForsetiException
     */
    public List getFoliosDerivadoHijos(FolioPk oid) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getFoliosDerivadoHijos(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folios Derivado hijos", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Retorna la lista con los los folios Derivado padre del folio especificado (Folio)
     * Si el folio no tiene padres o no existe retorna una lista vac�a
     * @param oid
     * @return
     * @throws ForsetiException
     */
    public List getFoliosDerivadoPadre(FolioPk oid) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getFoliosDerivadoPadre(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folios Derivado padre", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    
    /**
     * Retorna la lista con los folios Derivado hijos del folio especificado (Folio)
     * Si el folio no tiene hijos o no existe retorna una lista vac�a el usuario debe tener el bloqueo
     * @param oid
     * @return
     * @throws ForsetiException
     */
    public List getFoliosDerivadoHijos(FolioPk oid, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getFoliosDerivadoHijos(oid, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folios Derivado hijos", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Retorna la lista con los los folios Derivado padre del folio especificado (Folio)
     * Si el folio no tiene padres o no existe retorna una lista vac�a el usuario debe tener el bloqueo
     * @param oid
     * @return
     * @throws ForsetiException
     */
    public List getFoliosDerivadoPadre(FolioPk oid, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getFoliosDerivadoPadre(oid, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folios Derivado padre", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarDepartamento(gov.sir.core.negocio.modelo.Departamento)
     */
    public boolean eliminarDepartamento(Departamento dato, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getZonaRegistralDAO().eliminarDepartamento(dato, usuario);
        }


        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de eliminaci�n de Departamentos.", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarMunicipio(gov.sir.core.negocio.modelo.Municipio)
     */
    public boolean eliminarMunicipio(Municipio dato) throws ForsetiException {
        try {
            return forseti.getZonaRegistralDAO().eliminarMunicipio(dato);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de eliminaci�n de Municipios.", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarVereda(gov.sir.core.negocio.modelo.Vereda)
     */
    public boolean eliminarVereda(Vereda dato) throws ForsetiException {
        try {
            return forseti.getZonaRegistralDAO().eliminarVereda(dato);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de eliminaci�n de Veredas.", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarCirculo(gov.sir.core.negocio.modelo.Circulo)
     */
    public boolean eliminarCirculo(Circulo dato, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getZonaRegistralDAO().eliminarCirculo(dato, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de eliminaci�n de C�rculos.", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarZonaRegistral(gov.sir.core.negocio.modelo.ZonaRegistral)
     */
    public boolean eliminarZonaRegistral(ZonaRegistral dato) throws ForsetiException {
        try {
            return forseti.getZonaRegistralDAO().eliminarZonaRegistral(dato);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de eliminaci�n de Zonas Registrales.", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarEje(gov.sir.core.negocio.modelo.Eje, Usuario)
     */
    public boolean eliminarEje(Eje dato, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getZonaRegistralDAO().eliminarEje(dato, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de eliminaci�n de Ejes.", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarCirculoFestivo(gov.sir.core.negocio.modelo.CirculoFestivo)
     */
    public boolean eliminarCirculoFestivo(CirculoFestivo dato) throws ForsetiException {
        try {
            return forseti.getZonaRegistralDAO().eliminarCirculoFestivo(dato);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de eliminaci�n de C�rculos / Festivo.", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarEstadoFolio(gov.sir.core.negocio.modelo.EstadoFolio, Usuario usuario)
     */
    public boolean eliminarEstadoFolio(EstadoFolio dato, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getFolioDAO().eliminarEstadoFolio(dato, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de eliminaci�n de estados de folio.", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarTipoOficina(gov.sir.core.negocio.modelo.TipoOficina, Usuario usuario)
     */
    public boolean eliminarTipoOficina(TipoOficina dato, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getFolioDAO().eliminarTipoOficina(dato, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio eliminaci�n de tipos de oficina.", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarTipoDocumento(gov.sir.core.negocio.modelo.TipoDocumento, Usuario usuario)
     */
    public boolean eliminarTipoDocumento(TipoDocumento dato, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getFolioDAO().eliminarTipoDocumento(dato, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio eliminaci�n de tipos de documento.", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarTipoPredio(gov.sir.core.negocio.modelo.TipoPredio)
     */
    public boolean eliminarTipoPredio(TipoPredio dato) throws ForsetiException {
        try {
            return forseti.getFolioDAO().eliminarTipoPredio(dato);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio eliminaci�n de tipos de predio.", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarGrupoNaturalezaJuridica(gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica)
     */
    public boolean eliminarGrupoNaturalezaJuridica(GrupoNaturalezaJuridica dato) throws ForsetiException {
        try {
            return forseti.getFolioDAO().eliminarGrupoNaturalezaJuridica(dato);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio eliminaci�n de grupos de Naturaleza jur�dica.", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarNaturalezaJuridica(gov.sir.core.negocio.modelo.NaturalezaJuridica, Usuario usuario)
     */
    public boolean eliminarNaturalezaJuridica(NaturalezaJuridica dato, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getFolioDAO().eliminarNaturalezaJuridica(dato, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio eliminaci�n de Naturalezas jur�dicas.", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarEstadoAnotacion(gov.sir.core.negocio.modelo.EstadoAnotacion)
     */
    public boolean eliminarEstadoAnotacion(EstadoAnotacion dato) throws ForsetiException {
        try {
            return forseti.getFolioDAO().eliminarEstadoAnotacion(dato);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio eliminaci�n de estados de anotaci�n.", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarDominioNaturalezaJuridica(gov.sir.core.negocio.modelo.DominioNaturalezaJuridica)
     */
    public boolean eliminarDominioNaturalezaJuridica(DominioNaturalezaJuridica dato) throws ForsetiException {
        try {
            return forseti.getFolioDAO().eliminarDominioNaturalezaJuridica(dato);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio eliminaci�n de dominios de naturaleza jur�dica.", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#trasladarFolio(gov.sir.core.negocio.modelo.Folio.ID,gov.sir.core.negocio.modelo.Folio.ID,gov.sir.core.negocio.modelo.ZonaRegistral,gov.sir.core.negorio.modelo.Usuario)
     */
    public Folio trasladarFolio(Folio folioOrigen, Folio folioDestino, Usuario us, String resolucion) throws ForsetiException {
        try {
            return forseti.getTrasladoDAO().trasladarFolio(folioOrigen, folioDestino, us, resolucion);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en traslado de matr�cula.", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#consultarTrasladosMatricula(gov.sir.core.negocio.modelo.Folio.ID)
     */
    public List consultarTrasladosMatricula(FolioPk idFolio) throws ForsetiException {
        try {
            return forseti.getTrasladoDAO().consultarTrasladosMatricula(idFolio);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en consulta de traslados de matr�cula.", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Obtiene el ciudadano dado su tipo y numero de documento.
     * Si el ciudadano no existe en el sistema retorna null
     * @param tipodoc
     * @param doc
     * @return
     * @throws DAOException
     */
    public Ciudadano getCiudadanoByDocumento(String tipodoc, String doc, String idCirculo) throws ForsetiException {
        try {
            return forseti.getCiudadanoDAO().getCiudadanoByDocumento(tipodoc, doc, idCirculo);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de ciudadano", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    
    /**
     * Obtiene el ciudadanoTMP dado su tipo y numero de documento.
     * Si el ciudadano no existe en el sistema retorna null
     * @param tipodoc
     * @param doc
     * @return
     * @throws DAOException
     */
    public CiudadanoTMP getCiudadanoTmpByDocumento(String tipodoc, String doc, String idCirculo) throws ForsetiException {
        try {
            return forseti.getCiudadanoDAO().getCiudadanoTmpByDocumento(tipodoc, doc, idCirculo);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de ciudadano", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    /**
     * Obtiene un ciudadano por medio del identificador
     * @param oid identificador del ciudadno
     * */
    public Ciudadano getCiudadanoById( CiudadanoPk oid )   throws ForsetiException {
      try {
          return forseti.getCiudadanoDAO().getCiudadano( oid );
      }
      catch (DAOException e) {
          ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de ciudadano", e);
          fe.setHashErrores(e.getHashErrores());
          Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
          throw fe;
      }

    }


	/**
	 * Obtiene un ciudadano por medio del identificador, si el ciudadano tiene cambios temporales devuelve
	 * estos cambios, si no, devuelve el ciudadano definitivo, si el ciudadano no existe retorna null
	 * @param oid identificador del ciudadno
	 * */
	public Ciudadano getCiudadanoByIdTMP( CiudadanoPk oid )   throws ForsetiException {
	  try {
		  return forseti.getCiudadanoDAO().getCiudadanoByIdTMP( oid );
	  }
	  catch (DAOException e) {
		  ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de ciudadano", e);
		  fe.setHashErrores(e.getHashErrores());
		  Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		  throw fe;
	  }

	}

    /**
     * Devuelve un listado de anotaciones en las cuales
     * el ciudadano se encuentra relacionado
     * @return List<Anotacion>
     * */
    public List getAnotacionesQueRelacionanCiudadano( CiudadanoPk oid )   throws ForsetiException {
      try {
          return forseti.getFolioDAO().getAnotacionesQueRelacionanCiudadano( oid );
      }
      catch (DAOException e) {
          ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de ciudadano", e);
          fe.setHashErrores(e.getHashErrores());
          Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
          throw fe;
      }
    }


    /**
     * Valida la terminaci�n de calificaci�n dependiendo del tipo de
     * validaci�n que se pasa
     * @param oid Identificador del turno
     * @param tipoValidacion Tipo de validaci�n: T
     * TODOS_FOLIOS_UNA_ANOTACION: Valida que todos los folios asociados al turno
     * tengan por lo menos una anotacion temporal
     * UN_FOLIO_UNA_ANOTACION: Valida que por lo menos un folio del turno contenga
     * una anotaci�n
     * @see gov.sir.core.negocio.modelo.constantes.CTipoRevisionCalificacion
     * @return true: Se cumple la validaci�n, false: NO se cumple la validaci�n
     * @throws ForsetiException
     */
    public boolean validarTerminacionCalificacion(TurnoPk oid, String tipoValidacion) throws ForsetiException {
        try {
            return forseti.getFolioDAO().validarTerminacionCalificacion(oid, tipoValidacion);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de validaci�n de terminaci�n calificaci�n", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Retorna la lista con los IDs (Folio.ID) de los folios hijos del folio especificado
     * tanto definitivos como temporales
     * Si el folio no tiene hijos o no existe retorna una lista vac�a
     * @param oid
     * @return
     * @throws ForsetiException
     */
    public List getFoliosHijos(FolioPk oid, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getFoliosHijos(oid, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folios hijos", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * @author      :   Henry G�mez Rocha
     * @change      :   Evita que cuando el proceso pasa del role Calificador al role
     *                  Digitador se verifique el usuario que tiene el bloqueo.
     * Caso Mantis  :   0004967
     */
    public List getFoliosHijos(FolioPk oid, Usuario usuario, boolean validarTurno) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getFoliosHijos(oid, usuario, validarTurno);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folios hijos", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    
    /**
     * Retorna la lista con los IDs (Folio.ID) de los folios hijos del folio especificado
     * tanto definitivos como temporales
     * Si el folio no tiene hijos o no existe retorna una lista vac�a
     * @param oid
     * @return
     * @throws ForsetiException
     */
    public List getFoliosHijosOrdenAnotacion(FolioPk oid, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getFoliosHijosOrdenAnotacion(oid, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folios hijos", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Retorna la lista con los IDs (Folio.ID) de los folios padre del folio especificado
     * tanto definitivos como temporales
     * Si el folio no tiene padres o no existe retorna una lista vac�a
     * @param oid
     * @return
     * @throws ForsetiException
     */
    public List getFoliosPadre(FolioPk oid, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getFoliosPadre(oid, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folios padre", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * @author      :   Henry G�mez Rocha
     * @change      :   Evita que cuando el proceso pasa del role Calificador al role
     *                  Digitador se verifique el usuario que tiene el bloqueo.
     * Caso Mantis  :   0004967
     */
    public List getFoliosPadre(FolioPk oid, Usuario usuario, boolean validarTurno) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getFoliosPadre(oid, usuario, validarTurno);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folios padre", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Obtiene la zona registral de un folio dada la matricula
     * @param matricula
     * @return
     * @throws ForsetiException
     */
    public String getZonaRegistral(String matricula) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getZonaRegistral(matricula);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de zona registral", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Obtiene una oficina origen por ID
     * @param oid
     * @return
     * @throws DAOException
     */
    public OficinaOrigen getOficinaOrigen(OficinaOrigenPk oid) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getOficinaOrigen(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de oficina origen por ID", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    /**
     * @author Carlos Torres
     * @mantis 0013414: Acta - Requerimiento No 069_453_C�digo_Notaria_NC
     * Obtiene una oficina origen por ID
     * @param oid
     * @return
     * @throws DAOException
     */
    public OficinaOrigen getOficinaOrigen(String idOficina) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getOficinaOrigen(idOficina);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de oficina origen por idOficina", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Retorna la lista de las anotaciones que tienen salvedades en un folio
     * tanto definitivos como temporales
     * Si el folio no tiene anotaciones con salvedades retorna una lista vacia
     * @param oid
     * @return
     * @throws ForsetiException
     */
    public List getAnotacionesConSalvedades(FolioPk oid, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getAnotacionesConSalvedades(oid, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de anotaciones con salvedades", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    /**
     * @author      :   Henry G�mez Rocha
     * @change      :   Evita que cuando el proceso pasa del role Calificador al role
     *                  Digitador se verifique el usuario que tiene el bloqueo.
     * Caso Mantis  :   0004967
     */
    public List getAnotacionesConSalvedades(FolioPk oid, Usuario usuario, boolean validarTurno) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getAnotacionesConSalvedades(oid, usuario, validarTurno);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de anotaciones con salvedades", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
        
    /**
     * retorna una anotacion temporal con los folios hijos y folio pabre (ambos folios derivados)
     * @param oid
     * @return
     * @throws ForsetiException
     */
    public AnotacionTMP getAnotacionTMPByIDSinPersitence(AnotacionTMPPk oid) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getAnotacionTMPByIDSinPersitence(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de anotaciones con folios temporales", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class,fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Retorna la lista de las anotaciones que tienen salvedades en un folio
     * Si el folio no tiene anotaciones con salvedades retorna una lista vacia
     * @param oid
     * @return
     * @throws ForsetiException
     */
    public List getAnotacionesConSalvedades(FolioPk oid) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getAnotacionesConSalvedades(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de anotaciones con salvedades", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Retorna la lista de las anotaciones que tienen cancelaciones en un folio
     * tanto definitivos como temporales
     * Si el folio no tiene anotaciones con salvedades retorna una lista vacia
     * @param oid
     * @return
     * @throws DAOException
     */
    public List getAnotacionesConCancelaciones(FolioPk oid, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getAnotacionesConCancelaciones(oid, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de anotaciones con cancelaciones", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * @author      :   Henry G�mez Rocha
     * @change      :   Evita que cuando el proceso pasa del role Calificador al role
     *                  Digitador se verifique el usuario que tiene el bloqueo.
     * Caso Mantis  :   0004967
     */
    public List getAnotacionesConCancelaciones(FolioPk oid, Usuario usuario, boolean validarTurno) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getAnotacionesConCancelaciones(oid, usuario, validarTurno);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de anotaciones con cancelaciones", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Retorna la lista de las anotaciones que tienen cancelaciones en un folio
     * Si el folio no tiene anotaciones con cancelaciones retorna una lista vacia
     * @param oid
     * @return
     * @throws DAOException
     */
    public List getAnotacionesConCancelaciones(FolioPk oid) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getAnotacionesConCancelaciones(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de anotaciones con cancelaciones", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

	/**
	 * Retorna la lista de las anotaciones inv�lidas en un folio
	 * tanto definitivos como temporales
	 * Si el folio no tiene anotaciones inv�lidas retorna una lista vacia
	 * @param oid
	 * @return
	 * @throws Throwable
	 */
	public List getAnotacionesInvalidas(FolioPk oid, Usuario usuario) throws ForsetiException {
		try {
			return forseti.getFolioDAO().getAnotacionesInvalidas(oid, usuario);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de anotaciones inv�lidas", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}

        /**
         * @author      :   Henry G�mez Rocha
         * @change      :   Evita que cuando el proceso pasa del role Calificador al role
         *                  Digitador se verifique el usuario que tiene el bloqueo.
         * Caso Mantis  :   0004967
         */
	public List getAnotacionesInvalidas(FolioPk oid, Usuario usuario, boolean validarTurno) throws ForsetiException {
		try {
			return forseti.getFolioDAO().getAnotacionesInvalidas(oid, usuario, validarTurno);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de anotaciones inv�lidas", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}

	/**
	 * Retorna la lista de las anotaciones inv�lidas en un folio
	 * Si el folio no tiene anotaciones inv�lidas retorna una lista vacia
	 * @param oid
	 * @return
	 * @throws Throwable
	 */
	public List getAnotacionesInvalidas(FolioPk oid) throws ForsetiException {
		try {
			return forseti.getFolioDAO().getAnotacionesInvalidas(oid);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de anotaciones inv�lidas", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}

    /**
     * Retorna la lista con las anotaciones de los folios hijos del folio especificado,
     * cada anotacion tiene el objeto folio asociado y la �ltima direccion en la lista
     * de direcciones de este folio
     * Si el folio no tiene hijos o no existe retorna una lista vac�a
     * @param oid
     * @return
     * @throws ForsetiException
     */
    public List getFolioHijosEnAnotacionesConDireccion(FolioPk oid) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getFolioHijosEnAnotacionesConDireccion(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folios hijos en anotaciones", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    
    /**
     * Retorna la lista con las anotaciones de los folios hijos del folio especificado,
     * cada anotacion tiene el objeto folio asociado y la �ltima direccion en la lista
     * de direcciones de este folio, ordenado por anotacion
     * Si el folio no tiene hijos o no existe retorna una lista vac�a
     * @param oid
     * @return
     * @throws ForsetiException
     */
    public List getFolioHijosEnAnotacionesConDireccionOrdenadoAnotaciones(FolioPk oid) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getFolioHijosEnAnotacionesConDireccionOrdenadoAnotaciones(oid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folios hijos en anotaciones", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Obtiene el n�mero actual de anotaciones temporales del folio, el m�todo
     * debe recibir el usuario que tiene bloqueado el folio
     * @param oid
     * @param criterio
     * @param valor
     * @return
     * @throws ForsetiException
     */
    public long getCountAnotacionesTMPFolio(FolioPk oid, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getCountAnotacionesTMPFolio(oid, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de n�mero de anotaciones temporales", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * @author      :   Henry G�mez Rocha
     * @change      :   Evita que cuando el proceso pasa del role Calificador al role
     *                  Digitador se verifique el usuario que tiene el bloqueo.
     * Caso Mantis  :   0004967
     */
    public long getCountAnotacionesTMPFolio(FolioPk oid, Usuario usuario, boolean validarTurno) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getCountAnotacionesTMPFolio(oid, usuario, validarTurno);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de n�mero de anotaciones temporales", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Obtiene el siguiente orden de anotaci�n con base en el tama�o
     * de las anotaciones definitivas y temporales
     * @param fid
     * @return
     * @throws DAOException
     */
    public long getNextOrdenAnotacion(FolioPk fid) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getNextOrdenAnotacion(fid);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n del siguiente orden de anotaci�n del folio", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     *  Compara el folio actual con el folio en temporal y devuelve en un
     *  objeto folio, las diferencias encontradas.
     *  La comparaci�n incluye los objetos dependientes de folio: folio, anotacion,
     *  ciudadano, salvedad y cancelacion.
     * @param fid identificador del folio
     * @param usuario usuario que esta efectuando los cambios
     * @return un objeto delta folio con las diferencias.
     */
    public DeltaFolio getCambiosPropuestos(FolioPk fid,Usuario usuario) throws ForsetiException {
    	return getCambiosPropuestos( fid, usuario, true, false );
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCambiosPropuestos( gov.sir.core.negocio.modelo.Folio.FolioPk, gov.sir.core.negocio.modelo.Usuario, boolean, boolean )
     */
    public DeltaFolio getCambiosPropuestos(FolioPk fid,Usuario usuario, boolean procesarDelta, boolean incluirSalvedades ) throws ForsetiException {
    	
    	Folio definitivo = null;
        Folio temporal = null;

        try {

			long tiempoInicial =  System.currentTimeMillis();
			Log.getInstance().debug(ForsetiService.class, "\n*******************************************************");
			Log.getInstance().debug(ForsetiService.class, "((FORSETI) 1 LLAMADO cargarCambiosPropuestos) > "+fid.idMatricula+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
			Log.getInstance().debug(ForsetiService.class, "\n*******************************************************\n");
        	
            definitivo = forseti.getFolioDAO().getDatosDefinitivosDeDatosTemporales(fid, usuario);
            
			Log.getInstance().debug(ForsetiService.class, "\n*******************************************************");
			Log.getInstance().debug(ForsetiService.class, "((FORSETI) 2 LLAMADO cargarCambiosPropuestos)(Despues consulta definitivo) > "+fid.idMatricula+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
			Log.getInstance().debug(ForsetiService.class, "\n*******************************************************\n");            
            
            temporal = forseti.getFolioDAO().getDeltaFolio(fid, usuario);
            
			Log.getInstance().debug(ForsetiService.class, "\n*******************************************************");
			Log.getInstance().debug(ForsetiService.class, "((FORSETI) 3 LLAMADO cargarCambiosPropuestos)(Despues consulta temporal) > "+fid.idMatricula+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
			Log.getInstance().debug(ForsetiService.class, "\n*******************************************************\n");            
            
            DeltaFolio result;
            result = new DeltaFolio(definitivo, temporal, procesarDelta, incluirSalvedades );
            
			Log.getInstance().debug(ForsetiService.class, "\n*******************************************************");
			Log.getInstance().debug(ForsetiService.class, "((FORSETI) 4 LLAMADO cargarCambiosPropuestos)(Despues comparacion) > "+fid.idMatricula+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
			Log.getInstance().debug(ForsetiService.class, "\n*******************************************************\n");
            
            return result;
            
        } 
        catch( DAOException e ) {
        	
            ForsetiException fe = new ForsetiException(e.getMessage(), e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
            
        } // try
        
    } // end-method: getCambiosPropuestos

    
    
    


	/**
	* Adiciona una Prohibicion a la configuraci�n del sistema
	* @param datos objeto Prohibicion con sus atributos exceptuando su identificador
	* el cual es generado por el sistema
	* @return identificador de Prohibicion generado
	*/
	public ProhibicionPk addProhibicion(Prohibicion datos)
		throws ForsetiException {
		try {
			return forseti.getCiudadanoDAO().addProhibicion(datos);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de adici�n de prohibici�n", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}


	/**
	* Edita una Prohibicion de la configuraci�n del sistema
	* @param datos objeto Prohibicion con sus atributos incluido su identificador
	* @return identificador de Prohibicion generado
	*/
	public ProhibicionPk editarProhibicion(Prohibicion datos)
		throws ForsetiException {
		try {
			return forseti.getCiudadanoDAO().editarProhibicion(datos);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de edici�n de prohibiciones", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}


	/**
	* Agrega una prohibici�n a un ciudadano
	* @param oid Identificador del ciudadano
	* @param pid Identificador de la prohibici�n
	* @param pm PersistenceManager
	* @return identificador del CiudadanoProhibicion
	* @throws ForsetiException
	*/
	public CiudadanoProhibicionPk addProhibicionToCiudadano(CiudadanoPk oid, ProhibicionPk pid, CirculoPk cirid, String comentario, Usuario usuario)
		throws ForsetiException {
		try {
			return forseti.getCiudadanoDAO().addProhibicionToCiudadano(oid, pid, cirid, comentario, usuario);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de adici�n de prohibici�n a ciudadano", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}



	/**
	* Agrega una prohibici�n a un ciudadano
	* @param oid Identificador del ciudadano
	* @param pid Identificador de la prohibici�n
	* @param pm PersistenceManager
	* @return identificador del CiudadanoProhibicion
	* @throws ForsetiException
	*/
	public boolean desactivarProhibicionToCiudadano(CiudadanoProhibicionPk pid, Usuario usuario,String comentarioAnulacion)
		throws ForsetiException {
		try {
			return forseti.getCiudadanoDAO().desactivarProhibicionToCiudadano(pid, usuario,comentarioAnulacion);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de desactivaci�n de prohibici�n", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}



	/**
	 * Elimina una prohibici�n configurada en el sistema
	 * @param dato
	 * @return
	 * @throws ForsetiException
	 */
	public boolean eliminarProhibicion(Prohibicion dato)
		throws ForsetiException {
		try {
			return forseti.getCiudadanoDAO().eliminarProhibicion(dato);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de eliminaci�n de prohibici�n", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}




	/**
	* Obtiene una lista con todos las prohibiciones de ciudadano
	* configurados en el sistema
	* @return lista de objetos EstadoFolio
	* @see gov.sir.core.negocio.modelo.EstadoFolio
	* @throws ForsetiException
	*/
	public List getProhibiciones() throws ForsetiException {
	try {
		return forseti.getCiudadanoDAO().getProhibiciones();
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de lista de prohibiciones", e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
	}
}




	/**
	* Obtiene el usuario que tiene bloqueado el folio. El folio debe estar bloqueado
	* de lo contrario genera un DAOException
	* @param oid identificador del folio
	* @return objeto Usuario que tiene bloqueado el folio
	* @throws ForsetiException
	*/
	public Usuario getUsuarioBloqueoFolio(FolioPk oid)
		throws ForsetiException {
		try {
			return forseti.getFolioDAO().getUsuarioBloqueoFolio(oid);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de usuario bloqueo", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}



	/**
	 * Retorna la informaci�n temporal del folio que se encuentra lista
	 * para hacerse definitiva. En los datos del folio retornado
	 * s�lo van los cambios que se est�n aplicando:
	 * A nivel de Folio:
	 * codCatastral
	 * codCatastralAnterior
	 * estado
	 * lindero
	 * tipoPredio
	 * complementacion
	 *
	 * A nivel de Direcciones de folio, van las nuevas direcciones
	 *
	 * A nivel de Salvedades de folio, van las nuevas salvedades
	 *
	 * A nivel de Anotaciones, van las nuevas anotaciones y las
	 * anotaciones definitivas a las cuales se est� aplicando
	 * alg�n cambio:
	 *
	 * Si el flag de anotacion temporal es true, la anotaci�n est�
	 * siendo insertada, todos los datos de esta anotaci�n son nuevos.
	 *
	 * Si el flag de anotaci�n temporal es false, la anotaci�n corresponde
	 * a los cambios de una anotaci�n definitiva, es decir, los campos
	 * diferentes de null corresponden a los cambios aplicados:
	 *
	 * comentario
	 * especificacion
	 * estado
	 * orden
	 * tipoAnotacion
	 * naturalezaJuridica
	 * toUpdateValor == true : valor
	 *
	 * Lista de anotaciones ciudadano:
	 * Si el flag toDelete es true, la anotacionCiudadano correspondiente
	 * est� marcada para eliminaci�n
	 * Si el flag toDelete es false, la anotacionCiudadano correspondiente
	 * est� marcada para inserci�n
	 *
	 * Lista de salvedades de anotaci�n:
	 * Si el flag toDelete es true, la salvedad correspondiente
	 * est� marcada para eliminaci�n
	 * Si el flag toDelete es false, la salvedad correspondiente
	 * est� marcada para inserci�n o actualizaci�n. Si el idSalvedad
	 * est� seteado la salvedad es una actualizaci�n de una definitiva,
	 * si el idSalvedad no est� seteado, es una nueva salvedad de la anotaci�n
	 *
	 *
	 * @param oid
	 * @param us
	 * @return
	 * @throws ForsetiException
	 */
	public Folio getDeltaFolio(FolioPk oid, Usuario usuario)
		throws ForsetiException {
		try {
			return forseti.getFolioDAO().getDeltaFolio(oid, usuario);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de delta de folio", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}


	/**
	 * Cuenta el n�mero total de anotaciones que tiene un folio incluyendo
	 * las anotaciones temporales, con el criterio dado
	 * @param oid
	 * @param criterio
	 * @param valor
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public long getCountAnotacionesFolio(FolioPk oid, String criterio,
		String valor, Usuario usuario) throws ForsetiException {
		try {
			return forseti.getFolioDAO().getCountAnotacionesFolio(oid, criterio, valor, usuario);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de n�mero total de anotaciones de folio", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}

        /**
         * @author      :   Henry G�mez Rocha
         * @change      :   Evita que cuando el proceso pasa del role Calificador al role
         *                  Digitador se verifique el usuario que tiene el bloqueo.
         * Caso Mantis  :   0004967
         */
	public long getCountAnotacionesFolio(FolioPk oid, String criterio,
		String valor, Usuario usuario, boolean validarTurno) throws ForsetiException {
		try {
			return forseti.getFolioDAO().getCountAnotacionesFolio(oid, criterio, valor, usuario, validarTurno);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de n�mero total de anotaciones de folio", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}


	/**
	 * Retorna las anotaciones temporales de un folio con el criterio dado.
	 * @param oid
	 * @param criterio
	 * @param valor
	 * @param usuario
	 * @return
	 * @throws ForsetiException
	 */
	public List getAnotacionesTMPFolioToInsert(FolioPk oid, String criterio,
		String valor, Usuario usuario)
		throws ForsetiException {
		try {
			return forseti.getFolioDAO().getAnotacionesTMPFolioToInsert(oid, criterio, valor, usuario);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de n�mero total de anotaciones de folio", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}


        /**
         * @author      :   Henry G�mez Rocha
         * @change      :   Evita que cuando el proceso pasa del role Calificador al role
         *                  Digitador se verifique el usuario que tiene el bloqueo.
         * Caso Mantis  :   0004967
         */
	public List getAnotacionesTMPFolioToInsert(FolioPk oid, String criterio,
		String valor, Usuario usuario, boolean validarTurno)
		throws ForsetiException {
		try {
			return forseti.getFolioDAO().getAnotacionesTMPFolioToInsert(oid, criterio, valor, usuario, validarTurno);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de n�mero total de anotaciones de folio", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}

	/**
	 * Retorna las anotaciones definitivas de un folio con el criterio dado
     * con el delta aplicado (Anotaciones temporales actualizando definitivas),
     * y las anotaciones temporales nuevas
	 *
	 * @param oid
	 * @param criterio
	 * @param valor
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public List getAnotacionesFolio(FolioPk oid, String criterio,
		String valor, int posicionInicial, int cantidad, Usuario usuario, boolean vigente)
		throws ForsetiException {
		try {
			return forseti.getFolioDAO().getAnotacionesFolio(oid, criterio, valor,posicionInicial, cantidad, usuario, vigente);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de anotaciones de folio", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}


        /**
         * @author      :   Henry G�mez Rocha
         * @change      :   Evita que cuando el proceso pasa del role Calificador al role
         *                  Digitador se verifique el usuario que tiene el bloqueo.
         * Caso Mantis  :   0004967
         */
	public List getAnotacionesFolio(FolioPk oid, String criterio,
		String valor, int posicionInicial, int cantidad, Usuario usuario, boolean vigente, boolean validarTurno)
		throws ForsetiException {
		try {
			return forseti.getFolioDAO().getAnotacionesFolio(oid, criterio, valor,posicionInicial, cantidad, usuario, vigente, validarTurno);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de anotaciones de folio", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}
        
	/**
	 * Obtiene los datos definitivos de los datos temporales que est�n modificando
	 * al folio
	 * @param oid
	 * @param usuario
	 * @return
	 * @throws ForsetiException
	 */
	public Folio getDatosDefinitivosDeDatosTemporales(FolioPk oid, Usuario usuario)
		throws ForsetiException {
		try {
			return forseti.getFolioDAO().getDatosDefinitivosDeDatosTemporales(oid, usuario);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de datos definitivos de informaci�n temporal", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}




	/**
	 * Retorna una lista de objetos SolicitudFolio con los folios que tengan
	 * alg�n cambio temporal a ser aplicado dentro del turno especificado.
	 * Cada Folio retornado tiene los deltas que est�n pendientes por aplicar,
	 * si alg�n folio no se encuentra bloqueado por el usuario especificado
	 * se lanza una excepcion, si ning�n folio tiene deltas retorna una lista vac�a
	 * @param oid
	 * @param usuario
	 * @return
	 * @throws ForsetiException
	 */
	public List getDeltaFolios(TurnoPk oid, Usuario usuario)
		throws ForsetiException {
		try {
			return forseti.getFolioDAO().getDeltaFolios(oid, usuario);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de deltas de folios asociados al turno", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}


	/**
	 * Retorna el archivo de la firma activa del c�rculo especificado
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public FirmaRegistrador getFirmaRegistradorActiva(CirculoPk oid, String cargo) throws ForsetiException {
	try {
		return forseti.getZonaRegistralDAO().getFirmaRegistradorActiva(oid, cargo);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de firma activa del circulo", e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
	}
}


	/**
	 * Actualiza los datos del c�rculo.
	 * @param cid
	 * @param dato
	 * @return
	 * @throws DAOException
	 */
	public boolean updateCirculo(CirculoPk cid, Circulo dato, Usuario usuario) throws ForsetiException {
		try {
			return forseti.getZonaRegistralDAO().updateCirculo(cid, dato, usuario);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de actualizaci�n de circulo", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}

	/**
	 * Actualizar la descripci�n de una complementaci�n cuando esta tiene conflictos con la 
	 * complementaci�n del sistema FOLIO.
	 * @param complementacion
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public boolean updateComplementacionConflictiva(Complementacion complementacion, Usuario usuario) throws ForsetiException {
		try {
			return forseti.getFolioDAO().updateComplementacionConflictiva(complementacion, usuario);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de actualizaci�n de complementaciones conflictivas", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}	

	/**
	* Obtiene un objeto Folio dado su identificador
	* @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
	* @return Objeto Folio con sus objetos estado, complementacion, tipoPredio y ZonaRegistral,
	* y listas de salvedades folio y direcciones. No se incluyen las anotaciones
	* El objeto ZonaRegistral contiene la jerarqu�a circulo, vereda, municipio y departamento
	* null si  no encuentra un folio que coincida con el identificador dado
	* @throws DAOException
	*/
	public Folio getFolioByIDSinAnotaciones(FolioPk oid, Usuario usuario)
		throws ForsetiException {
		try {
			return forseti.getFolioDAO().getFolioByIDSinAnotaciones(oid, usuario);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folio sin anotaciones", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}


        /**
         * @author      :   Henry G�mez Rocha
         * @change      :   Evita que cuando el proceso pasa del role Calificador al role
         *                  Digitador se verifique el usuario que tiene el bloqueo.
         * Caso Mantis  :   0004967
         */
	public Folio getFolioByIDSinAnotaciones(FolioPk oid, Usuario usuario, boolean validarTurno)
		throws ForsetiException {
		try {
			return forseti.getFolioDAO().getFolioByIDSinAnotaciones(oid, usuario, validarTurno);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folio sin anotaciones", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}

	/**
	 * Obtiene el ciudadano dado su tipo y numero de documento.
	 * Si el ciudadano no existe en el sistema retorna null
	 * @param tipodoc
	 * @param doc
	 * @return
	 * @throws ForsetiException
	 */
	public Ciudadano getCiudadanoByDocumentoSolicitante(String tipodoc, String doc, String idCirculo)
		throws ForsetiException {
		try {
			return forseti.getCiudadanoDAO().getCiudadanoByDocumentoSolicitante(tipodoc, doc, idCirculo);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de ciudadano solicitante", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}

    /**
     * Servicio que permite ejecutar el proceso de catastro
     * @param fechIni Fecha inicial del proceso
     * @param fechfin Fecha final del proceso
     * @param circulo Objeto que representa el c�rculo registral sobre el cual se deje ejecutar el proceso
     * @param us Usuario que realiza la operaci�n
     * @return True: proceso exitoso; False: proceso no exitoso
     * @throws <code>ForsetiException</code>
     */
    public boolean catastro(Date fechIni, Date fechFin, Circulo circulo, Usuario us) throws ForsetiException {
        try {
            return forseti.getCatastroDAO().catastro(fechIni, fechFin, circulo, us);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en Proceso de Catastro.", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }



	/**
	* Servicio utilizado para establecer si el folio con el n�mero
	* de matr�cula se encuentra en tr�mite. Si retorna null el folio NO est� en tr�mite,
	* si returna un objeto Turno el folio se encuentra en tr�mite por el turno dado
	* @param matricula
	* @return Turno
	* @throws DAOException
	*/
	public Turno getTurnoTramiteFolio(String matricula) throws ForsetiException {
	try {
		return forseti.getFolioDAO().getTurnoTramiteFolio(matricula);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de decisi�n si el folio se encuentra en tr�mite", e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
	}
}
        
        /**
	* Servicio utilizado para establecer si el folio con el n�mero
	* de matr�cula se encuentra en tr�mite. Si retorna null el folio NO est� en tr�mite,
	* si returna una lista de turnos el folio se encuentra en tr�mite por el turno dado
	* @param matricula
	* @return Turno
	* @throws DAOException
	*/
	public List getTurnosTramiteFolio(String matricula) throws ForsetiException {
	try {
		return forseti.getFolioDAO().getTurnosTramiteFolio(matricula);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de decisi�n si el folio se encuentra en tr�mite", e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
	}
}
        
        
	/**
	 * Obtiene el bloqueo del folio en caso que se encuentre bloqueado
	 * Si la matricula NO est� bloqueada returna null, si est� bloqueada
	 * retorna un usuario con la llave de bloqueo en la lista de sus llaves,
	 * y la llave con el BloqueoFolio en la lista de sus bloqueos de folio. En el
	 * objeto BloqueoFolio est� la fecha de bloqueo y el turno (idWorkflowBloqueo)
	 * que lo bloque�
	 * @param fid
	 * @return Hashtable
	 * @throws ForsetiException
	 */
	public Usuario getBloqueoFolio(FolioPk fid)
		throws ForsetiException{
		try {
			return forseti.getFolioDAO().getBloqueoFolio(fid);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n del bloqueo del folio", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}



	/**
	* Servicio utilizado para establecer si el folio con el n�mero
	* de matr�cula posee alguna deuda. Si No tiene deuda retorna null,
	* si tiene deuda retorna un Turno, con una solicitud, y con la liquidaci�n
	* la cual NO tiene registrado el pago
	*
	* @param matricula
	* @return Turno
	* @throws ForsetiException
	*/
	public Turno getTurnoDeudaFolio(FolioPk fid) throws ForsetiException{
	try {
		return forseti.getFolioDAO().getTurnoDeudaFolio(fid);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de deuda de folio", e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
	}
}


	/**
	 * Obtiene la plantilla de pertenencia asociada a la respuesta especificada
	 * @param respuesta
	 * @return
	 * @throws ForsetiException
	 */
	public PlantillaPertenencia getPlantillaPertenenciaByRespuesta(String respuesta)
		throws ForsetiException{
		try {
			return forseti.getFolioDAO().getPlantillaPertenenciaByRespuesta(respuesta);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de plantilla de pertenencia", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}

	/**
	 * Actualiza la despcripci�n de un oficio de pertenencia dada su respuesta
	 * @param respuesta
	 * @return
	 * @throws ForsetiException
	 */
	public boolean updatePlantillaPertenenciaByRespuesta(String respuesta, String nuevaDescripcion)
		throws ForsetiException{
		try {
			return forseti.getFolioDAO().updatePlantillaPertenenciaByRespuesta(respuesta, nuevaDescripcion);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de actualizaci�n de plantilla de pertenencia", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}


	/**
	 * Obtiene la lista de las platilla de pertenencia configuradas
	 * en el sistema
	 * @return
	 * @throws DAOException
	 */
	public List getPlantillaPertenencias() throws ForsetiException{
		try {
			return forseti.getFolioDAO().getPlantillaPertenencias();
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de platillas de pertenencia", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
			}
	}

	/**
	 * Retorna el turno asociado a la solicitud, si no existe el turno
	 * retorna null
	 * @param sol
	 * @return
	 * @throws DAOException
	 */
	public Turno getTurnoBySolicitud(SolicitudPk sol) throws ForsetiException{
		try {
			return forseti.getFolioDAO().getTurnoBySolicitud(sol);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de Turnos de solicitud", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}


	/**
	 * Obtiene un objeto NaturalezaJuridica dado su identificador
	 * @param oid identificador de NaturalezaJuridica
	 * @return objeto NaturalezaJuridica
	 * @throws DAOException
	 */
	public NaturalezaJuridica getNaturalezaJuridica(NaturalezaJuridicaPk oid)
		throws ForsetiException{
		try {
			return forseti.getFolioDAO().getNaturalezaJuridica(oid);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de naturaleza jur�dica", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
			}
	}



	/**
	 * Obtiene una lista con todas las impresoras del c�rculo
	 * @return lista de objetos CirculoImpresora
	 * @see gov.sir.core.negocio.modelo.CirculoImpresora
	 * @throws ForsetiException
	 */
	public List getCirculoImpresoras(String idCirculo) throws ForsetiException{
	try {
		return forseti.getZonaRegistralDAO().getCirculoImpresoras(idCirculo);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de impresoras", e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
}


	/**
	 * Elimina la lista de impresoras configuradas para el c�rculo
	 * @param idCirculo
	 * @return
	 * @throws ForsetiException
	 */
	public boolean eliminarCirculoImpresoras(String idCirculo) throws ForsetiException{
	try {
		return forseti.getZonaRegistralDAO().eliminarCirculoImpresoras(idCirculo);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de eliminaci�n de impresoras del c�rculo", e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
}

	/**
	 * Adiciona una lista de impresoras a la lista actual de impresoras del c�rculo
	 * la lista que recibe es de objetos String
	 * @return lista de objetos CirculoImpresora con la lista total de impresoras
	 * @see gov.sir.core.negocio.modelo.CirculoImpresora
	 * @throws ForsetiException
	 */
	public List addListaCirculoImpresoras(String idCirculo, List impresoras) throws ForsetiException{
	try {
		return forseti.getZonaRegistralDAO().addListaCirculoImpresoras(idCirculo, impresoras);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de adici�n de impresoras", e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
}

	/**
	 * Actualiza los datos de una naturaleza jur�dica.
	 * @param cid
	 * @param dato
	 * @param Usuario que actualiza la naturaleza juridica
	 * @return
	 * @throws ForsetiException
	 */
	public boolean updateNaturalezaJuridica(NaturalezaJuridica naturaleza, Usuario usuario)
		throws ForsetiException{
		try {
			return forseti.getFolioDAO().updateNaturalezaJuridica(naturaleza, usuario);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de actualizaci�n de naturaleza jur�dica", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
			}
	}


	/**
	* Retorna los grupos de naturalezas jur�dicas, cada uno con su lista de naturalezas jur�dicas
	* asociadas s�lamente a calificaci�n
	* @return Lista de GrupoNaturalezaJuridica
	* @see gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica
	* @throws ForsetiException
	*/
	public List getGruposNaturalezaJuridicaCalificacion() throws ForsetiException{
	try {
		List l = forseti.getFolioDAO().getGruposNaturalezaJuridica();
		List rta = new ArrayList();
		GrupoNaturalezaJuridica grupo;
		NaturalezaJuridica nat;
		List naturalezas;

		for(Iterator it= l.iterator();it.hasNext();){
			grupo = (GrupoNaturalezaJuridica)it.next();
			naturalezas = new ArrayList();
			for(Iterator it2 = grupo.getNaturalezaJuridicas().iterator(); it2.hasNext();){
				nat = (NaturalezaJuridica)it2.next();
				if(nat.isHabilitadoCalificacion()){
					naturalezas.add(nat);
				}
			}
			if(!naturalezas.isEmpty()){
				grupo.setNaturalezaJuridicas(naturalezas);
				rta.add(grupo);
			}

		}
		return rta;
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de obtener naturalezas jur�dicas de calificaci�n", e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
}



	/**
	 * Copia la anotaci�n indicada con el ID idAnotacion (definitiva o temporal) al
	 * folio indicado con el ID idFolioDestino. El usuario debe tener el bloqueo del
	 * folio.
	 * CONDICIONES DEPENDIENDO DE LA ANOTACION A COPIAR:
	 * Cancelada: Se copia junto con su canceladora
	 * Canceladora: No se copia (Se lanza excepci�n, en el la lista de errores del ForsetiException)
	 * Derivada o generadora: Solo copia la anotaci�n, sin relaciones a otros folios
	 * Normal: Se copia normalmente
	 * @param idAnotacion
	 * @param foliosID lista de objetos Folio.ID
	 * @param usuario Usuario que esta realizando la copia de anotaciones
	 * @param copiarComentario Booleano que determina si se debe copiar o no el comentario de la anotaci�n origen
	 * @return la anotaci�n generada
	 * @throws ForsetiException
	 */
	public boolean copiarAnotacion(AnotacionPk idAnotacion,List foliosID, Usuario usuario, boolean copiarComentario)
		throws ForsetiException{
			try {
				return forseti.getFolioDAO().copiarAnotacion(idAnotacion, foliosID, usuario, copiarComentario);
			}
			catch (DAOException e) {
				ForsetiException fe = new ForsetiException("Fallo en el servicio de copia de anotaci�n", e);
				fe.setHashErrores(e.getHashErrores());
				fe.setErrores(e.getErrores());
				Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
				throw fe;
				}
		}



	/**
	 * Cuenta los d�as NO h�biles configurados en el sistema desde la fecha inicial incluy�ndola
	 * hasta la fecha final excluy�ndola. N�mero D�as no h�biles entre: [fehainicial, fechaFinal)
	 * @return long
	 * @throws ForsetiException
	 */
	public long countDiasNoHabiles(CirculoPk cirID, Date fechaInicial, Date fechaFinal) throws ForsetiException{
		try {
			return forseti.getZonaRegistralDAO().countDiasNoHabiles(cirID, fechaInicial, fechaFinal);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de d�as NO h�biles", e);
			fe.setHashErrores(e.getHashErrores());
			fe.setErrores(e.getErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
			}
	}



	/**
	 * Obtiene una anotaci�n definitiva o temporal por el orden. Obtiene los datos b�sicos de
	 * la anotaci�n sin dependencias.
	 * @param oid
	 * @param orden
	 * @param usuario
	 * @return
	 * @throws ForsetiException
	 */
	public Anotacion getAnotacionByOrden(FolioPk oid, String orden, Usuario usuario)  throws ForsetiException{
	try {
		return forseti.getFolioDAO().getAnotacionByOrden(oid,  orden, usuario);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de anotaci�n a partir del orden", e);
		fe.setHashErrores(e.getHashErrores());
		fe.setErrores(e.getErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
    }


	/**
	 * Obtiene una lista de anotaciones temporales a partir de dos rangos por el orden.
	 * Obtiene los datos b�sicos de la anotaci�n sin dependencias.
	 * @param oid
	 * @param ordenInicial
	 * @param ordenFinal
	 * @param usuario
	 * @return
	 * @throws ForsetiException
	 */
	public List getAnotacionesTemporalesByRangoOrden(FolioPk oid, String ordenInicial, String ordenFinal, Usuario usuario)  throws ForsetiException{
	try {
		return forseti.getFolioDAO().getAnotacionesTemporalesByRangoOrden(oid,  ordenInicial, ordenFinal, usuario);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de anotaciones a partir de una rango de ordenes", e);
		fe.setHashErrores(e.getHashErrores());
		fe.setErrores(e.getErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}


	/**
	 * Copia una anotaci�n canceladora a otros folios, cada folio debe tener un objeto anotaci�n con el
	 * identificador de la anotaci�n que se quiere cancelar
	 * @param idAnotacion
	 * @param folios
	 * @param usuario Usuario que esta realizando la copia de anotaciones
	 * @param copiarComentario Booleano que determina si se debe copiar o no el comentario de la anotaci�n origen
	 * @return
	 * @throws ForsetiException
	 */
	public boolean copiarAnotacionCanceladora(AnotacionPk idAnotacion, List folios, Usuario usuario, boolean copiarComentario) throws ForsetiException{
	try {
		return forseti.getFolioDAO().copiarAnotacionCanceladora(idAnotacion, folios, usuario, copiarComentario);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de copia de anotaci�n canceladora", e);
		fe.setHashErrores(e.getHashErrores());
		fe.setErrores(e.getErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}


	/**
	 * Devuelve una hashtable en donde para cada matr�cula (key)
	 * especifica si tiene o no nuevas anotaciones temporales Boolean (valor)
	 * @param turnoID
	 * @return
	 * @throws ForsetiException
	 */
	public Hashtable validarNuevasAnotacionesTurno(TurnoPk turnoID) throws ForsetiException{
	try {
		return forseti.getFolioDAO().validarNuevasAnotacionesTurno(turnoID);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de copia de validaci�n de nuevas anotaciones del turno", e);
		fe.setHashErrores(e.getHashErrores());
		fe.setErrores(e.getErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}


	/**
	 * Indica si un folio se encuentra asociado a un turno activo que se encuentre en calificaci�n
	 * @param folioID
	 * @return
	 * @throws ForsetiException
	 */
	public boolean isFolioInTurnoCalificacion(FolioPk folioID)throws ForsetiException{
	try {
		return forseti.getFolioDAO().isFolioInTurnoCalificacion(folioID);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de consulta de folio en turno de calificaci�n", e);
		fe.setHashErrores(e.getHashErrores());
		fe.setErrores(e.getErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}

	/**
	* Deshace los cambios que est�n siendo aplicados a los folios del turno, borra toda la informaci�n temporal
	* @param datos
	* @param usuario
	* @return
	* @throws ForsetiException
	*/
	public boolean deshacerCambiosTurno(TurnoPk turnoID, Usuario usuario, boolean desbloquear) throws ForsetiException{
	try {
		forseti.getFolioDAO().deshacerCambiosTurno(turnoID, usuario);

		if (desbloquear) {
			forseti.getFolioDAO().desbloquearFolios(turnoID, usuario);
		}

		return true;
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de deshacer los cambios temporales de un turno", e);
		fe.setHashErrores(e.getHashErrores());
		fe.setErrores(e.getErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}

	/**
	* Deshace los cambios que est�n siendo aplicados a los folios del turno, borra toda la informaci�n temporal
	* @param turnoID
	* @param usuario
	* @return
	* @throws ForsetiException
	*/
	public boolean deshacerCambiosFolioByTurno(TurnoPk turnoID, Usuario usuario, boolean desbloquear) throws ForsetiException{
	try {
		forseti.getFolioDAO().deshacerCambiosFolioByTurno(turnoID, usuario);
		
		if (desbloquear) {
			forseti.getFolioDAO().desbloquearFolios(turnoID, usuario);
		}
		
		return true;
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de deshacer los cambios temporales de un turno", e);
		fe.setHashErrores(e.getHashErrores());
		fe.setErrores(e.getErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}

	/**
	 * Actualiza una oficina origen con TODOS los datos que llegan el en objeto.
	 * El objeto debe tener su identificador.
	 * @param oficina
	 * @return
	 * @throws ForsetiException
	 */
	public boolean updateOficinaOrigen(OficinaOrigen oficina) throws ForsetiException{
	try {
		return forseti.getFolioDAO().updateOficinaOrigen(oficina);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de actualizar oficina origen. " + e.getMessage(), e);
		fe.setHashErrores(e.getHashErrores());
		fe.setErrores(e.getErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}



	/**
	 * Indica si un turno tiene datos temporales realizados por el turno indicado
	 * @param turnoID
	 * @param folioID
	 * @return true: El turno est� realizando cambios sobre el folio
	 *         false: El turno NO est� realizando cambios sobre el folio
	 * @throws ForsetiException
	 */
	public boolean hasDatosTemporalesTurno(TurnoPk turnoID, FolioPk folioID)throws ForsetiException{
	try {
		return forseti.getFolioDAO().hasDatosTemporalesTurno(turnoID, folioID);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de decisi�n de datos temporales de folio en turno", e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}
	
	/**
	 * Indica si un folio tiene datos temporales
	 * @param turnoID
	 * @param folioID
	 * @return true: El folio tiene informaci�n temporal
	 *         false: El turno NO tiene informaci�n temporal
	 * @throws ForsetiException
	 */
	public boolean hasDatosTemporalesFolio(FolioPk folioID)throws ForsetiException{
	try {
		return forseti.getFolioDAO().hasDatosTemporalesFolio(folioID);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de decisi�n de datos temporales de folio", e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}

	/**
	 * Indica si el folio se encuentra bloqueado en el turno
	 * @param turnoID
	 * @param folioID
	 * @returntrue: true: El folio es bloqueado por el turno
	 *         		false: El folio NO esta bloqueadopor el turno
	 */
	public boolean isBloqueadoByTurno(TurnoPk turnoID, FolioPk folioID) throws ForsetiException{
		try {
			return forseti.getFolioDAO().isBloqueadoByTurno(turnoID, folioID);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de decisi�n de datos temporales de folio", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
			}
	}
	
	/**
	 * Elimina un folio temporal dado su ID, y concepto de eliminacion. No se valida el bloqueo del folio
	 * @param folioID, String concepto
	 * @return
	 * @throws DAOException
	 */
	public boolean deleteFolio(FolioPk folioID, String concepto) throws ForsetiException{
		try {
			return forseti.getFolioDAO().deleteFolio(folioID, concepto);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de eliminaci�n de folio", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
			}
	}


	/**
	 * Elimina un folio temporal dado su ID, el folio es desasociado del turno al que se encuentre asociado
	 * @param folioID
	 * @return
	 * @throws ForsetiException
	 */
	public boolean deleteFolio(FolioPk folioID, Usuario usuario) throws ForsetiException{
		try {
			return forseti.getFolioDAO().deleteFolio(folioID, usuario);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de eliminaci�n de folio", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
			}
	}



	/**
	 * Desasocia dos folios que se encuentran encadenados a trav�s de cada una de sus anotaciones
	 * @param anotaID1
	 * @param anotaID2
	 * @return
	 * @throws ForsetiException
	 */
	public boolean desasociarFolios(AnotacionPk anotaGeneradoraID, AnotacionPk anotaDerivadaID, Usuario usuario) throws ForsetiException{
	try {
		return forseti.getFolioDerivadoDAO().desasociarFolios(anotaGeneradoraID, anotaDerivadaID, usuario);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de desasociar folios", e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}


	/**
	 * Asocia dos folios a trav�s de sus anotaciones generadora y derivada
	 * @param anotaGeneradoraID
	 * @param anotaDerivadaID
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public boolean asociarFolios(AnotacionPk anotaGeneradoraID, AnotacionPk anotaDerivadaID, Usuario usuario, Turno turno) throws ForsetiException{
	try {
		return forseti.getFolioDerivadoDAO().asociarFolios(anotaGeneradoraID, anotaDerivadaID, usuario, turno);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de asociar folios", e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}
	
	/**
	 * Asocia dos folios a trav�s de sus anotaciones generadora y derivada
	 * @param anotaGeneradoraID
	 * @param anotaDerivadaID
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public boolean asociarFoliosAdministrativa(AnotacionPk anotaGeneradoraID, AnotacionPk anotaDerivadaID, Usuario usuario, int tipo) throws ForsetiException{
	try {
		return forseti.getFolioDerivadoDAO().asociarFoliosAdministrativa(anotaGeneradoraID, anotaDerivadaID, usuario, tipo);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de asociar folios", e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}
	
	/**
	 * DesAsocia dos folios a trav�s de sus anotaciones generadora y derivada
	 * @param anotaGeneradoraID
	 * @param anotaDerivadaID
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public boolean desasociarFoliosAdministrativa(AnotacionPk anotaGeneradoraID, AnotacionPk anotaDerivadaID, Usuario usuario, int tipo) throws ForsetiException{
	try {
		return forseti.getFolioDerivadoDAO().desasociarFoliosAdministrativa(anotaGeneradoraID, anotaDerivadaID, usuario, tipo);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de desasociar folios", e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}



	/**
	 * Devuelve la relaci�n entre un folio padre y un folio hijo, si no existe retorna null.
	 * @param folioIDPadre
	 * @param folioIDHijo
	 * @param usuario
	 * @return
	 * @throws ForsetiException
	 */
	public FolioDerivado getFolioDerivadoEnlace(FolioPk folioIDPadre, FolioPk folioIDHijo, Usuario usuario) throws ForsetiException{
	try {
		return forseti.getFolioDerivadoDAO().getFolioDerivadoEnlace(folioIDPadre, folioIDHijo, usuario);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folio derivado enlace", e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}


	/**
	 * Devuelve el folio derivado a partir del folio derivado hijo entre un folio padre y un folio hijo, si no existe retorna null.
	 * @param folioIDHijo
	 * @param usuario
	 * @return
	 * @throws ForsetiException
	 */
	public FolioDerivado getFolioDerivadoHijo(FolioPk folioIDHijo, Usuario usuario) throws ForsetiException{
	try {
		return forseti.getFolioDerivadoDAO().getFolioDerivadoHijo(folioIDHijo, usuario);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folio derivado hijo", e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}



	/**
	 * Obtiene las anotaciones agrupadas por folio que fueron agregadas en un turno espec�fico
	 * @param oid
	 * @return
	 * @throws ForsetiException
	 */
	public List getCalificacionTurno(TurnoPk oid) throws ForsetiException{
	try {
		return forseti.getFolioDAO().getCalificacionTurno(oid);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de folio derivado enlace", e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}

	/**
	 * Delega el bloqueo de un folio dentro de un turno al usuario especificado
	 * @param oid
	 * @param usuario
	 * @param fid
	 * @return
	 * @throws ForsetiException
	 */
	public LlaveBloqueo delegarBloqueoFolio(TurnoPk oid, Usuario usuario, FolioPk fid)
		throws ForsetiException{
		try {
			return forseti.getFolioDAO().delegarBloqueoFolio(oid, usuario, fid);
		}
		catch (DAOException e) {
			ForsetiException fe = new ForsetiException("Fallo en el servicio de delegaci�n de bloqueo de folio", e);
			fe.setHashErrores(e.getHashErrores());
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
			}
		}



	/**
	 * Actualiza un ciudadano en el modelo temporal
	 * @param ciud
	 * @return
	 * @throws ForsetiException
	 */
	public boolean updateCiudadano(Ciudadano ciud, Usuario usuario, String numRadicacion)
	throws ForsetiException{
	try {
		return forseti.getCiudadanoDAO().updateCiudadano(ciud, usuario, numRadicacion);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException(e.getMessage(), e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}

	/**
	 * Actualiza un ciudadano en el modelo
	 * @param ciud
	 * @return
	 * @throws ForsetiException
	 */
	public boolean updateCiudadanoAdministrativa(Ciudadano ciud, Usuario usuario)
	throws ForsetiException{
	try {
		return forseti.getCiudadanoDAO().updateCiudadanoAdministrativa(ciud, usuario);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException(e.getMessage(), e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}

	/**
	 * Indica si el ciudadano se encuentra asociado a una anotaci�n definitiva
	 * @param turno
	 * @param folio
	 * @param pm
	 * @return
	 */
	public boolean isCiudadanoInAnotacionDefinitiva(CiudadanoPk ciudID) throws ForsetiException{
	try {
		return forseti.getFolioDAO().isCiudadanoInAnotacionDefinitiva(ciudID);
	}
	catch (DAOException e) {
		ForsetiException fe = new ForsetiException("Fallo en el servicio de definici�n de ciudadano en anotaci�n definitiva", e);
		fe.setHashErrores(e.getHashErrores());
		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		throw fe;
		}
	}


    /**
     * Obtiene un objeto Texto dado su identificador
     * @param oid identificador del circulo
     * @return objeto Circulo
     * @throws DAOException
     */
    public Texto getTexto(TextoPk oid) throws ForsetiException{
    	try {
    		return forseti.getZonaRegistralDAO().getTexto(oid);
    	}
    	catch (DAOException e) {
    		ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de Texto", e);
    		fe.setHashErrores(e.getHashErrores());
    		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
    		throw fe;
    		}
    	}



    /**
     * Adiciona un texto a la configuraci�n del sistema
     * @param datos objeto Circulo con sus atributos exceptuando su identificaci�n
     * el cual es generado por el sistema
     * @param datos
     * @return identificaci�n del texto generado por el sistema
     * @throws DAOException
     */
    public TextoPk addTexto(Texto texto) throws ForsetiException{
    	try {
    		return forseti.getZonaRegistralDAO().addTexto(texto);
    	}
    	catch (DAOException e) {
    		ForsetiException fe = new ForsetiException("Fallo en el servicio de adici�n de Texto", e);
    		fe.setHashErrores(e.getHashErrores());
    		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
    		throw fe;
    		}
    	}


    /**
     * Actualiza los datos del texto.
     * @param cid
     * @param dato
     * @return
     * @throws DAOException
     */
    public boolean updateTexto(TextoPk cid, String nuevoTexto) throws ForsetiException{
    	try {
    		return forseti.getZonaRegistralDAO().updateTexto(cid, nuevoTexto);
    	}
    	catch (DAOException e) {
    		ForsetiException fe = new ForsetiException("Fallo en el servicio de actualizaci�n de Texto", e);
    		fe.setHashErrores(e.getHashErrores());
    		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
    		throw fe;
    		}
    	}

    /**
	 * Obtiene una lista con todas los tipos de imprimibles
	 * @return lista de objetos TipoImprimible
	 * @see gov.sir.core.negocio.modelo.TipoImprimible
	 * @throws ForsetiException
	 */
	public List getTiposImprimible() throws ForsetiException {
    	try {
    		return forseti.getZonaRegistralDAO().getTiposImprimible();
    	}
    	catch (DAOException e) {
    		ForsetiException fe = new ForsetiException("Fallo en el servicio para obtener los tipos de imprimibles", e);
    		fe.setHashErrores(e.getHashErrores());
    		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
    		throw fe;
    		}
    	}

	/**
	 * Retorna la lista de impresoras por c�rculo y tipo de imprimible
	 * @param idCirculo
	 * @param idTipoImprimible
	 * @param pm
	 * @return
	 * @throws ForsetiException
	 */
	public List getCirculoImpresorasXTipoImprimible(String idCirculo, String idTipoImprimible) throws ForsetiException {
    	try {
    		return forseti.getZonaRegistralDAO().getCirculoImpresorasXTipoImprimible(idCirculo,idTipoImprimible);
    	}
    	catch (DAOException e) {
    		ForsetiException fe = new ForsetiException("Fallo en el servicio para obtener las impresoras del c�rculo por tipo de imprimible", e);
    		fe.setHashErrores(e.getHashErrores());
    		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
    		throw fe;
    		}
    	}

	/**
	 * Obtiene la configuracion de impresoras del c�rculo por tipo de imprimible
	 * @return lista de objetos CirculoImpresora
	 * @see gov.sir.core.negocio.modelo.CirculoImpresora
	 * @throws ForsetiException
	 */
	public Hashtable getConfiguracionImpresoras(String idCirculo) throws ForsetiException {
    	try {
    		return forseti.getZonaRegistralDAO().getConfiguracionImpresoras(idCirculo);
    	}
    	catch (DAOException e) {
    		ForsetiException fe = new ForsetiException("Fallo en el servicio para obtener la configuraci�n de impresoras por c�rculo", e);
    		fe.setHashErrores(e.getHashErrores());
    		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
    		throw fe;
    		}
    	}

	/**
	 * Cambia la configuracion de impresoras del c�rculo por tipo de imprimible
	 * @return lista de objetos CirculoImpresora
	 * @see gov.sir.core.negocio.modelo.CirculoImpresora
	 * @throws ForsetiException
	 */
	public boolean setConfiguracionImpresoras(Hashtable nuevaConfiguracion, String idCirculo) throws ForsetiException {
    	try {
    		return forseti.getZonaRegistralDAO().setConfiguracionImpresoras(nuevaConfiguracion,idCirculo);
    	}
    	catch (DAOException e) {
    		ForsetiException fe = new ForsetiException("Fallo en el servicio para actualizar la configuraci�n de impresoras por c�rculo", e);
    		fe.setHashErrores(e.getHashErrores());
    		Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
    		throw fe;
    		}
    	}
       /**
        * @see
        */
       public boolean validarFolioTieneAnotacionesconOrdenRepetido( FolioPk folioId ) throws ForsetiException {

          try {
            return forseti.getFolioDAO().validarFolioTieneAnotacionesconOrdenRepetido( folioId );
          }
          catch (DAOException e) {
            ForsetiException fe = new ForsetiException("(ServicioNegocio) Orden de anotaciones " + e.getMessage() , e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
          }

       } // end-method: validarFolioTieneAnotacionesconOrdenRepetido

       /**
        *
        */
       public boolean eliminarOficinaOrigen(OficinaOrigenPk oficinaID)	throws ForsetiException {

		try {
			return forseti.getFolioDAO().eliminarOficinaOrigen(oficinaID);
		} catch (DAOException e) {
			ForsetiException fe = new ForsetiException("No fue posible eliminar la oficina origen", e);
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}
       
       /**
       * Devuelve un listado de los ciudadanos que estan relacionados a un folio
       */
      public List getCiudadanoUltimosFolio(String idMatricula)	throws ForsetiException {

		try {
			return forseti.getFolioDAO().getCiudadanoUltimosFolio(idMatricula);
		} catch (DAOException e) {
			ForsetiException fe = new ForsetiException("No fue posible obtener los ultimos ciudadadanos del Folio", e);
			Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
			throw fe;
		}
	}

       /**
        * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#countSalvedadesFolio(java.lang.String, java.lang.String, gov.sir.core.negocio.modelo.Usuario)
        */
       public long countSalvedadesFolio( String idMatricula, String idWorkflow, Usuario usuario ) throws ForsetiException {
           try {
               return forseti.getFolioDAO().countSalvedadesFolio( idMatricula, idWorkflow, usuario );
           }
           catch (DAOException e) {
               ForsetiException fe = new ForsetiException("Fallo en el servicio countSalvedadesFolio", e);
               fe.setHashErrores(e.getHashErrores());
               Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
               throw fe;
           }
       } // end-method: countSalvedadesFolio

       /**
        * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#countSalvedadesAnotacion(java.lang.String, java.lang.String, gov.sir.core.negocio.modelo.Usuario)
        */
       public long countSalvedadesAnotacion( String idMatricula, String idWorkflow, Usuario usuario ) throws ForsetiException {
           try {
               return forseti.getFolioDAO().countSalvedadesAnotacion( idMatricula, idWorkflow, usuario );
           }
           catch (DAOException e) {
               ForsetiException fe = new ForsetiException("Fallo en el servicio countSalvedadesFolio", e);
               fe.setHashErrores(e.getHashErrores());
               Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
               throw fe;
           }
       } // end-method: countSalvedadesAnotacion


       /**
        * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getOficinasOrigenByMunicipio( gov.sir.core.negocio.modelo.Municipio.MunicipioPk )
       */
       public List getOficinasOrigenByMunicipio( MunicipioPk oid ) throws ForsetiException {
           List rta = null;

           try {
               rta = forseti.getFolioDAO().getOficinasOrigenByMunicipio(oid);
           }
           catch (DAOException e) {
               ForsetiException fe = new ForsetiException("No fue posible obtener las oficinas de origen del municipio especificado", e);
               Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
               throw fe;
           }

           return rta;
       } // end-method:getOficinasOrigenByMunicipio

	public Ciudadano crearCiudadano(Ciudadano ciudadano) throws ForsetiException {

        try {
            return forseti.getCiudadanoDAO().crearCiudadano(ciudadano);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("No fue posible obtener las oficinas de origen del municipio especificado", e);
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getCirculosByUsuario( gov.sir.core.negocio.modelo.Usuario.UsuarioPk )
    */
    public List getCirculosByUsuario(gov.sir.core.negocio.modelo.UsuarioPk oid) throws ForsetiException {

	  List rta = null;

	  try {
		 rta = forseti.getZonaRegistralDAO().getCirculosByUsuario( oid );
	  }
	  catch (DAOException e) {
		 ForsetiException fe = new ForsetiException(
			  "No fue posible obtener los circulos del usuario especificado",
			  e);
		 Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
		 throw fe;
	  }

	  return rta;
	}

   /**
    * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionById( gov.sir.core.negocio.modelo.Anotacion.AnotacionPk )
   */
  public Anotacion getAnotacionById( gov.sir.core.negocio.modelo.AnotacionPk oid ) throws ForsetiException {
     try {
        return forseti.getFolioDAO().getAnotacionById( oid );
     }
     catch (DAOException e) {
             ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de anotaci�n a partir del orden", e);
             fe.setHashErrores(e.getHashErrores());
             fe.setErrores(e.getErrores());
             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
             throw fe;
    } // try
  } // end-method: getAnotacionById
  
  /**
   * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacion( gov.sir.core.negocio.modelo.Anotacion.AnotacionPk )
  */
 public Anotacion getAnotacion( gov.sir.core.negocio.modelo.AnotacionPk oid ) throws ForsetiException {
    try {
       return forseti.getFolioDAO().getAnotacion( oid );
    }
    catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de anotaci�n a partir del orden", e);
            fe.setHashErrores(e.getHashErrores());
            fe.setErrores(e.getErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
   } // try
 } // end-method: getAnotacion
 
  
  /**
    * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarDeltaSegunAnotacionDefinitiva( gov.sir.core.negocio.modelo.Folio, gov.sir.core.negocio.modelo.Anotacion, gov.sir.core.negocio.modelo.Usuario )
    */

  
  /**
   * Valida el principio de prioridad seg�n Incidencia 5063
   * Si los turnos van a salir de firma y existen otros turnos anteriores (con fechaInicio anterior)
   * que no hayan salido de firma y comparten alg�n folio se genera una excepcion
   * @param oid
   * @throws DAOException
   */
  public void validarPrincipioPrioridadFirma(List turnos)
  	throws ForsetiException {
	     try {
	          forseti.getFolioDAO().validarPrincipioPrioridadFirma(turnos);
	     }
	     catch (DAOException e) {
	             ForsetiException fe = new ForsetiException("Fallo en el servicio de prioridad de firma", e);
	             fe.setHashErrores(e.getHashErrores());
	             fe.setErrores(e.getErrores());
	             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
	             throw fe;
	    } 
	  }
  
  /**
   * Valida el principio de prioridad Devolucion
   * Si los turnos van a ser devueltos y existen otros turnos posteriores
   * que no hayan salido de firma y comparten alg�n folio se genera una excepcion
   * @param oid
   * @throws DAOException
   */
  public void validarPrincipioPrioridadDevolucion(List turnos)
  	throws ForsetiException {
	     try {
	          forseti.getFolioDAO().validarPrincipioPrioridadDevolucion(turnos);
	     }
	     catch (DAOException e) {
	             ForsetiException fe = new ForsetiException("Fallo en el servicio de prioridad de Devolucion", e);
	             fe.setHashErrores(e.getHashErrores());
	             fe.setErrores(e.getErrores());
	             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
	             throw fe;
	    } 
	  }
  
  /**
   * Valida el principio de prioridad
   * Si los turnos van a salir de firma y existen otros turnos anteriores (con fechaInicio anterior)
   * que no hayan salido de firma y comparten alg�n folio los agrega a una lista
   * @param oid
   * @throws DAOException
   */
  public Hashtable validarPrincipioPrioridadFirmaRelacion(List turnos)
  	throws ForsetiException {
	     try {
	          return forseti.getFolioDAO().validarPrincipioPrioridadFirmaRelacion(turnos);
	     }
	     catch (DAOException e) {
	             ForsetiException fe = new ForsetiException("Fallo en el servicio de prioridad de firma", e);
	             fe.setHashErrores(e.getHashErrores());
	             fe.setErrores(e.getErrores());
	             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
	             throw fe;
	    } 
	  }
  
  /**
   * Valida que el turno sea el primero entre turnos que comparten las mismas matricula
   * Si los turnos se van a tomar en firma y existen otros turnos anteriores (con fechaInicio anterior)
   * que no hayan salido de firma y comparten alg�n folio se genera false
   * @param oid
   * @throws DAOException
   */
  public boolean validarTurnoFirmaPrincipioPrioridad(List turnos)
  	throws ForsetiException {
	     try {
	          return forseti.getFolioDAO().validarTurnoFirmaPrincipioPrioridad(turnos);
	     }
	     catch (DAOException e) {
	             ForsetiException fe = new ForsetiException("Fallo en el servicio de prioridad de firma", e);
	             fe.setHashErrores(e.getHashErrores());
	             fe.setErrores(e.getErrores());
	             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
	             throw fe;
	    } 
	  }
  
  public void inhabilitarCirculo(Circulo circuloOrigen, Circulo circuloDestino, List zonasRegistrales, Map usuariosCrear, List usuariosTrasladar, List usuariosRolConsulta, Usuario usuarioInhabilita) throws ForsetiException {
	try {
		forseti.getZonaRegistralDAO().inhabilitarCirculo(circuloOrigen,circuloDestino, zonasRegistrales, usuariosCrear, usuariosTrasladar, usuariosRolConsulta, usuarioInhabilita);
     }
     catch (DAOException e) {
             ForsetiException fe = new ForsetiException("Fallo en el servicio de inhabilitar circulos", e);
             fe.setHashErrores(e.getHashErrores());
             fe.setErrores(e.getErrores());
             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
             throw fe;
    }
  }
  
  
  /**
	 * Traslada el rango de folios de un c�rculo a otro
	 * @param circuloOrigen c�rculo del cual se van a trasladar los folios
	 * @param circuloDestino c�rculo al que se van a trasladar los folios
	 * @param trasladoMasivoInicio indica desde que n�mero de matr�cula se van a trasladar
	 * @param trasladoMasivoFin indica hasta que n�mero de matr�cula se van a trasladar
	 */
	public void trasladarFolios(Circulo circuloOrigen, Circulo circuloDestino, int trasladoMasivoInicio, int trasladoMasivoFin) throws ForsetiException {
	     try {
	          forseti.getZonaRegistralDAO().trasladarFolios(circuloOrigen, circuloDestino, trasladoMasivoInicio, trasladoMasivoFin);
	     }
	     catch (DAOException e) {
	             ForsetiException fe = new ForsetiException("Fallo en el servicio de traslado masivo de folios", e);
	             fe.setHashErrores(e.getHashErrores());
	             fe.setErrores(e.getErrores());
	             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
	             throw fe;
	    } 
	  } 
	
	
	public List getCirculosInhabilitados(Circulo circuloDestino) throws ForsetiException {
		try {
			return forseti.getZonaRegistralDAO().getCirculosInhabilitados(circuloDestino);
	     }
	     catch (DAOException e) {
	             ForsetiException fe = new ForsetiException("Fallo en el servicio de inhabilitar circulos", e);
	             fe.setHashErrores(e.getHashErrores());
	             fe.setErrores(e.getErrores());
	             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
	             throw fe;
	    }
	}

	public void validarMatriculaCrearFolio(Folio datos) throws ForsetiException {
		try {
			forseti.getFolioDAO().validarMatriculaCrearFolio(datos);
	     }
	     catch (DAOException e) {
	             ForsetiException fe = new ForsetiException("Fallo en el servicio de validar matricula", e);
	             fe.setHashErrores(e.getHashErrores());
	             fe.setErrores(e.getErrores());
	             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
	             throw fe;
	    }	
	}
	
	 /**
     * Valida la prohibicion de enajencion de una lista de anotaciones ciudadano
     * @param anotaciones
     * @return 
     * @throws DAOException
     */
    public boolean validarAnotacionesCiudadano(List anotacionesCiudadano)throws ForsetiException {
    	try {
			return forseti.getFolioDAO().validarAnotacionesCiudadano(anotacionesCiudadano);
	     }
	     catch (DAOException e) {
	             ForsetiException fe = new ForsetiException("Fallo en el servicio de validar enajenacion", e);
	             fe.setHashErrores(e.getHashErrores());
	             fe.setErrores(e.getErrores());
	             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
	             throw fe;
	    }
    }
    /**
    * @param folio
    * @param usuario
    * @return
    * @throws DAOException
    */
    public boolean updateFolioCreacionDirecta(Folio folio, Usuario usuario) throws ForsetiException {
        try {
            return forseti.getFolioDAO().updateFolioCreacionDirecta(folio, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException( e.getMessage() + ". (Fallo en el servicio de actualizaci�n de folios)." , e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * @param anotacion
     * @param usuario
     * @return
     * @throws DAOException
     */
     public boolean updateAnotacionesCreacionDirecta(Anotacion anotacion, Usuario usuario) throws ForsetiException {
         try {
             return forseti.getFolioDAO().updateAnotacionesCreacionDirecta(anotacion, usuario);
         }
         catch (DAOException e) {
             ForsetiException fe = new ForsetiException( e.getMessage() + ". (Fallo en el servicio de actualizaci�n de la anotacion del folio)." , e);
             fe.setHashErrores(e.getHashErrores());
             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
             throw fe;
         }
     }
     
	public String migrarFolioByMatricula(String matricula, Usuario usuario) throws ForsetiException {
		try {
			return forseti.getMigracionIncrementalDAO().migrarFolioByMatricula(matricula,usuario);
	     }
	     catch (DAOException e) {
	             ForsetiException fe = new ForsetiException("Fallo en el servicio de migracion incremental de Folio", e);
	             fe.setHashErrores(e.getHashErrores());
	             fe.setErrores(e.getErrores());
	             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
	             throw fe;
	    }
	}

	public Turno migrarTurnoNumero(String numTurno) throws ForsetiException {
		try {
			return forseti.getMigracionIncrementalDAO().migrarTurnoNumero(numTurno);
	     }
	     catch (DAOException e) {
	             ForsetiException fe = new ForsetiException("Fallo en el servicio de migracion incremental de Turno", e);
	             fe.setHashErrores(e.getHashErrores());
	             fe.setErrores(e.getErrores());
	             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
	             throw fe;
	    }
	}
	
	public ActoresRepartoNotarial getActoresRepartoNotarial(gov.sir.core.negocio.modelo.ActoresRepartoNotarialPk oid) throws ForsetiException {
		try {
			return forseti.getZonaRegistralDAO().getActoresRepartoNotarial(oid);
	     }
	     catch (DAOException e) {
	             ForsetiException fe = new ForsetiException("Fallo en el servicio de migracion incremental de Turno", e);
	             fe.setHashErrores(e.getHashErrores());
	             fe.setErrores(e.getErrores());
	             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
	             throw fe;
	    }
	}

	public List getFoliosAnotaActNomenclaturaSinActualizarBySolicitud(gov.sir.core.negocio.modelo.SolicitudPk oid, Usuario usuario) throws ForsetiException {
		try {
			return forseti.getFolioDAO().getFoliosAnotaActNomenclaturaSinActualizarBySolicitud(oid, usuario);
	     }
	     catch (DAOException e) {
	             ForsetiException fe = new ForsetiException("Fallo en el servicio de anotaciones de actualizacion de nomenclatura", e);
	             fe.setHashErrores(e.getHashErrores());
	             fe.setErrores(e.getErrores());
	             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
	             throw fe;
	    }
	}
        
        public List getTurnsShareFolioNotaDev(String idMatricula) throws ForsetiException{
		try {
			return forseti.getFolioDAO().getTurnsShareFolioNotaDev(idMatricula);
	     }
	     catch (DAOException e) {
	             ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de turnos", e);
	             fe.setHashErrores(e.getHashErrores());
	             fe.setErrores(e.getErrores());
	             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
	             throw fe;
	    }
	}
        
         public List validarPrincipioPrioridadNotNotaDev(String idMatricula) throws ForsetiException {
		try {
			return forseti.getFolioDAO().validarPrincipioPrioridadNotNotaDev(idMatricula);
	     }
	     catch (DAOException e) {
	             ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de turnos", e);
	             fe.setHashErrores(e.getHashErrores());
	             fe.setErrores(e.getErrores());
	             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
	             throw fe;
	    }
	}
         
         public List validarPrincipioPrioridadNotNotaNot(String idMatricula) throws ForsetiException {
		try {
			return forseti.getFolioDAO().validarPrincipioPrioridadNotNotaNot(idMatricula);
	     }
	     catch (DAOException e) {
	             ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de turnos", e);
	             fe.setHashErrores(e.getHashErrores());
	             fe.setErrores(e.getErrores());
	             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
	             throw fe;
	    }
	}
         
         public List validarPrincipioPrioridadRecNot(String idMatricula) throws ForsetiException {
	 	try {
			return forseti.getFolioDAO().validarPrincipioPrioridadRecNot(idMatricula);
	     }
	     catch (DAOException e) {
	             ForsetiException fe = new ForsetiException("Fallo en el servicio de obtenci�n de turnos", e);
	             fe.setHashErrores(e.getHashErrores());
	             fe.setErrores(e.getErrores());
	             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
	             throw fe;
	    }
	}
	
	/**
	 * Actualiza las salvedades del folio. Por cada salvedad si existe, la actualiza, de lo contrario
	 * la inserta
	 * @param folio
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public boolean updateFolioSalvedades(Folio folio, Usuario usuario) throws ForsetiException
	{
		try {
			return forseti.getFolioDAO().updateFolioSalvedades(folio, usuario);
	     }
	     catch (DAOException e) {
	             ForsetiException fe = new ForsetiException("Fallo en el servicio de folio de actualizacion de salvedades", e);
	             fe.setHashErrores(e.getHashErrores());
	             fe.setErrores(e.getErrores());
	             Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
	             throw fe;
	    }
	}
	
	public List getTurnosCorreccionActivosFolio(Folio fol, Turno turnoNoValidar) throws ForsetiException{
		try{
			return forseti.getFolioDAO().getTurnosCorreccionActivosFolio(fol, turnoNoValidar);
		}catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en la busqueda de turnos de Correccion", e);
            fe.setHashErrores(e.getHashErrores());
            fe.setErrores(e.getErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
		}
	}
	
	public boolean isUltimoPropietario(AnotacionCiudadano anota)throws ForsetiException{
		try{
			return forseti.getFolioDAO().isUltimoPropietario(anota);
		}catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en la obtencion de propietario", e);
            fe.setHashErrores(e.getHashErrores());
            fe.setErrores(e.getErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
		}
	}
	public void eliminarFolioCreacionDirecta(Folio datos, Usuario usuario) throws ForsetiException{
		try {
            forseti.getFolioDAO().eliminarFolioCreacionDirecta(datos, usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio para deshacer los datos temporales del folio", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class,fe.getMessage(), e);
            throw fe;
        }
	}
	
	
	public boolean desbloquearFoliosEntradaCalificacion(List lstMatricula, Usuario usuario) throws ForsetiException {
        try {
            forseti.getFolioDAO().desbloquearFoliosEntradaCalificacion(lstMatricula, usuario);
            return true;
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio para deshacer los datos temporales del folio", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class,fe.getMessage(), e);
            throw fe;
        }
    }
	
	/**
	 * Actualiza folio derivado
	 */
	public void updateFolioDerivado(List foliosDerivadosModificados,Folio folioPadre,Usuario usuario) throws ForsetiException{
		try {
            forseti.getFolioDAO().updateFolioDerivado(foliosDerivadosModificados,folioPadre,usuario);
        }
        catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Error Actualizando Folio Derivado", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class,fe.getMessage(), e);
            throw fe;
        }
	}
	
	/**
	 * Retorna una lista de folios derivados temporales para una matr�cula padre
	 */
	public List getFoliosDerivadosTMPByMatricula(String matriculaPadre) throws ForsetiException{
		try {
            return forseti.getFolioDerivadoDAO().getFoliosDerivadosTMPByMatricula(matriculaPadre);
        }catch (DAOException e) {
	        ForsetiException fe = new ForsetiException("Fallo en la busqueda de folios derivados temporales", e);
	        fe.setHashErrores(e.getHashErrores());
	        fe.setErrores(e.getErrores());
	        Log.getInstance().error(ForsetiService.class,fe.getMessage(), e);
	        throw fe;
		} 
	}

/*Adiciona Funcionalidad Boton de Pago
 * Author: Ingeniero Diego Hernandez
 * Modificacion en: 2010/02/23 by jvenegas
 */

    public List getAlertasMatriculas(String idMatricula) throws ForsetiException{
        try {

            return forseti.getFolioDAO().getAlertasMatriculas(idMatricula);
        } catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en la consulta de alertas del folio", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class,fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * @author      :   Henry G�mez Rocha y Fernando Padilla
     * @change      :   Evita que cuando el proceso pasa del role Calificador al role
     *                  Digitador se verifique el usuario que tiene el bloqueo.
     * Caso Mantis  :   0004967
     */
    public Folio getFolioByID(FolioPk oid, Usuario usuario, boolean validarBloqueo) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getFolioByID(oid, usuario,validarBloqueo);
        } catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio para obtener el folio", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    /**
     * @author      :   Carlos Mario Torres Urina
     * @change      :   Retorna anotacion temporal
     * Caso Mantis  :   11767
     */
    public Anotacion getAnotacionTMP(FolioPk oid, String idanotacion) throws ForsetiException {
        try {
            return forseti.getFolioDAO().getAnotacionTMP(oid, idanotacion);
        } catch (DAOException e) {
            ForsetiException fe = new ForsetiException("Fallo en el servicio para obtener el folio", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
    
    /**
     * @Autor: Edgar Lora
     * @Mantis: 13176
     */
    public NaturalezaJuridicaEnhanced getNaturalezaJuridicaById(NaturalezaJuridicaEnhancedPk pk) throws ForsetiException {
        try{
            return forseti.getFolioDAO().getNaturalezaJuridicaById(pk);
        } catch(DAOException e){
            ForsetiException fe = new ForsetiException("Fallo en el servicio para obtener el folio", e);
            fe.setHashErrores(e.getHashErrores());
            Log.getInstance().error(ForsetiService.class, fe.getMessage(), e);
            throw fe;
        }
    }
}

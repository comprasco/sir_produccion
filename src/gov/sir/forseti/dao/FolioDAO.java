/*
 * Created on 22-jul-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.forseti.dao;

import java.util.Hashtable;
import java.util.List;

import java.io.IOException;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.AnotacionPk;
import gov.sir.core.negocio.modelo.Busqueda;
import gov.sir.core.negocio.modelo.BusquedaPk;
import gov.sir.core.negocio.modelo.CiudadanoPk;
import gov.sir.core.negocio.modelo.Complementacion;
import gov.sir.core.negocio.modelo.Direccion;
import gov.sir.core.negocio.modelo.DireccionPk;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.DocumentoPk;
import gov.sir.core.negocio.modelo.DominioNaturalezaJuridica;
import gov.sir.core.negocio.modelo.DominioNaturalezaJuridicaPk;
import gov.sir.core.negocio.modelo.EstadoAnotacion;
import gov.sir.core.negocio.modelo.EstadoAnotacionPk;
import gov.sir.core.negocio.modelo.EstadoFolio;
import gov.sir.core.negocio.modelo.EstadoFolioPk;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioDerivado;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.GrupoNaturalezaJuridicaPk;
import gov.sir.core.negocio.modelo.LlaveBloqueo;
import gov.sir.core.negocio.modelo.LlaveBloqueoPk;
import gov.sir.core.negocio.modelo.MunicipioPk;
import gov.sir.core.negocio.modelo.NaturalezaJuridica;
import gov.sir.core.negocio.modelo.NaturalezaJuridicaPk;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.OficinaOrigenPk;
import gov.sir.core.negocio.modelo.PlantillaPertenencia;
import gov.sir.core.negocio.modelo.SalvedadAnotacion;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.TipoAnotacion;
import gov.sir.core.negocio.modelo.TipoAnotacionPk;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoDocumentoPk;
import gov.sir.core.negocio.modelo.TipoOficina;
import gov.sir.core.negocio.modelo.TipoOficinaPk;
import gov.sir.core.negocio.modelo.TipoPredio;
import gov.sir.core.negocio.modelo.TipoPredioPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.UsuarioPk;
import gov.sir.core.negocio.modelo.VeredaPk;

import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;

import gov.sir.core.negocio.modelo.jdogenie.AnotacionTMP;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionTMPPk;
import gov.sir.core.negocio.modelo.jdogenie.FolioDatosTMP;
import gov.sir.core.negocio.modelo.jdogenie.FolioDatosTMPPk;
/**
     * @Autor: Edgar Lora
     * @Mantis: 13176
     */
import gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced;

import gov.sir.forseti.ForsetiException;
import java.util.Map;


/**
 * Interface que administra los servicios de administración de Folios y anotaciones
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface FolioDAO {
    
    public List getTurnsShareFolioNotaDev(String idMatricula) throws DAOException;
    public List validarPrincipioPrioridadNotNotaDev(String idMatricula) throws DAOException;
    public List validarPrincipioPrioridadNotNotaNot(String idMatricula) throws DAOException;
    public List validarPrincipioPrioridadRecNot(String idMatricula) throws DAOException;
    
    /**
     * 
     * @param idMatricula
     * @return
     * @throws DAOException 
     */
    public List getHistorialArea(String idMatricula) throws DAOException;
    /**
     * Obtiene un objeto Folio dado su identificador
     * @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
     * @return Objeto Folio con sus objetos estado, complementacion, tipoPredio y ZonaRegistral.
     * El objeto ZonaRegistral contiene la jerarquía circulo, vereda, municipio y departamento
     * null si  no encuentra un folio que coincida con el identificador dado
     * @throws DAOException
     */
    public Folio getFolioByID(FolioPk oid) throws DAOException;

    /**
     * Obtiene un objeto Folio dado su identificador (Devuelve folios temporales y definitivos)
     * @param String identificador del Folio
     * @return Objeto Folio con sus objetos estado, complementacion, tipoPredio y ZonaRegistral.
     * El objeto ZonaRegistral contiene la jerarquía circulo, vereda, municipio y departamento
     * null si  no encuentra un folio que coincida con el identificador dado
     * @throws DAOException
     */
    public Folio getFolioByID(String matricula) throws DAOException;
    
    
    /**
     * Obtiene un objeto Folio dado el número de matrícula
     * @param matricula número de matrícula del folio
     * @return Objeto Folio con sus objetos estado, complementacion, tipoPredio y ZonaRegistral.
     * El objeto ZonaRegistral contiene la jerarquía circulo, vereda, municipio y departamento
     * null si  no encuentra un folio que coincida con el identificador dado
     * @throws DAOException
     */
    public Folio getFolioByMatricula(String matricula)
    	throws DAOException;

    
    /**
     * Obtiene un objeto Folio con toda su informacion, dado el número de matrícula
     * @param matricula número de matrícula del folio
     * @return Objeto Folio con sus objetos estado, complementacion, tipoPredio y ZonaRegistral.
     * El objeto ZonaRegistral contiene la jerarquía circulo, vereda, municipio y departamento
     * null si  no encuentra un folio que coincida con el identificador dado
     * @throws DAOException
     * @throws IOException 
     */
     public ImprimibleCertificado getImprimibleCertificadoByMatricula(String idMatricula) 
     	 throws DAOException, IOException;
     
    /**
     * Obtiene una lista con las anotaciones del folio especificado por su ID
     * @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
     * @return lista con las anotaciones del folio, cada anotación contiene los objetos
     * tipoAnotacion, naturalezaJuridica y estado
     * @see gov.sir.core.negocio.modelo.Anotacion
     * @throws DAOException
     */
    public List getAnotacionesFolio(FolioPk oid) throws DAOException;

    /**
     * Obtiene una lista con las anotaciones del folio (aun temporal) especificado por su ID
     * @param oid
     * @return
     * @throws DAOException
     */
    public List getAnotacionesFolioTMP(FolioPk oid) throws DAOException;
    
    
    /**
     * Obtiene una lista con las anotaciones del folio (aun temporal) especificado por su ID con los flios derivados si los tiene
     * @param oid
     * @return
     * @throws DAOException
     */
    public List getAnotacionesFolioTMPFD(FolioPk oid) throws DAOException;
    
    
    /**
     * Obtiene una anotacion con sus anotaciones hijas y padre especificado por su ID
     * @param oid
     * @return
     * @throws DAOException
     */
    public AnotacionTMP getAnotacionTMPByIDSinPersitence(AnotacionTMPPk oid)throws DAOException;
    
    
    /**
     * Obtiene una lista con las anotaciones del folio (aun temporal) especificado por su ID
     * @param oid
     * @return
     * @throws DAOException
     */
    public int getAnotacionesFolioTMPCount(FolioPk oid) throws DAOException;

	/**
	 * Obtiene una lista con las anotaciones del folio especificado por
	 * su matricula
	 * @param matricula Nro de matrícula
	 * @return lista con las anotaciones del folio, cada anotación contiene los objetos
	 * tipoAnotacion, naturalezaJuridica y estado
	 * @throws DAOException
	 */
	public List getAnotacionesFolio(String matricula) throws DAOException;


    /**
     * Obtiene una lista con las direcciones del folio especificado por su ID
     * @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
     * @return lista con las direcciones del folio, cada direccion contiene los objetos
     * eje y eje1
     * @see gov.sir.core.negocio.modelo.Direccion
     * @throws DAOException
     */
    public List getDireccionesFolio(FolioPk oid) throws DAOException;

    /**
     * Obtiene una lista con las salvedades del folio especificado por su ID
     * @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
     * @return lista con las salvedades del folio
     * @see gov.sir.core.negocio.modelo.SalvedadFolio
     * @throws DAOException
     */
    public List getSalvedadesFolio(FolioPk oid) throws DAOException;

    /**
     * Indica si el folio se encuentra bloqueado dado su identificador
     * @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
     * @return true está bloqueado, false no está bloqueado
     * @throws DAOException
     */
    public boolean estaBloqueado(FolioPk oid) throws DAOException;

    /**
     * Indica si el folio se encuentra bloqueado dado el número de matrícula
     * @param matricula
     * @return
     * @throws DAOException
     */
    public boolean estaBloqueado(String matricula) throws DAOException;
    
     /**
     * Indica si el folio se encuentra bloqueado y en actuacion administrativa
     * @param idMatricula
     * @return
     * @throws DAOException
     */
    public boolean isActuacion (String idMatricula) throws DAOException;
    
    /**
     * Indica si el ciudadano esta en alguna anotacionCiudadano
     * @param idCiudadano
     * @return
     * @throws DAOException
     */
     public boolean estaCiudadanoEnAnotacionCiudadano(String idCiudadano) throws DAOException;

    /**
     * Obtiene el usuario que tiene bloqueado el folio. El folio debe estar bloqueado
     * de lo contrario genera un DAOException
     * @param oid identificador del folio
     * @return objeto Usuario que tiene bloqueado el folio
     * @throws DAOException
     */
    public Usuario getUsuarioBloqueoFolio(FolioPk oid)
        throws DAOException;

    /**
     * Obtiene una lista con las llaves actuales de bloqueo del usuario
     * @param oid identificador del usuario
     * @return lista de LlaveBloqueo
     * @see gov.sir.core.negocio.modelo.LlaveBloqueo
     * @throws DAOException
     */
    public List getLlavesBloqueo(UsuarioPk oid) throws DAOException;

    /**
     * Obtiene una lista con los BloqueoFolio correspondientes a la llave especificada
     * @param oid identificador de la llave
     * @return lista con BloqueoFolio, cada bloqueoFolio contiene el objeto Folio
     * @see gov.sir.core.negocio.modelo.BloqueoFolio
     * @throws DAOException
     */
    public List getBloqueoFolios(LlaveBloqueoPk oid) throws DAOException;

    /**
     * Obtiene una anotacion dado su identificador
     * @param oid identificador de la anotación conformado por el idAnotacion, IdMatricula
     * y IdZonaRegistral
     * @return objeto Anotacion con los objetos naturalezaJuridica, tipoAnotacion y estado
     * @throws DAOException
     */
    public Anotacion getAnotacionByID(AnotacionPk oid)
        throws DAOException;

    /**
     * Obtiene una lista con las salvedades la anotación especificada por su ID
     * @param oid identificador de la anotación conformado por el idAnotacion, IdMatricula
     * y IdZonaRegistral
     * @return lista con las salvedades de la anotación
     * @see gov.sir.core.negocio.modelo.SalvedadAnotacion
     * @throws DAOException
     */
    public List getSalvedadesAnotacion(AnotacionPk oid)
        throws DAOException;

    /**
     * Obtiene el documento asociado con la anotacion
     * @param oid identificador de la anotación conformado por el idAnotacion, IdMatricula
     * y IdZonaRegistral
     * @return objeto Documento con toda su jerarquía de objetos
     * @throws DAOException
     */
    public Documento getDocumentoAnotacion(AnotacionPk oid)
        throws DAOException;

    /**
     * Obtiene una lista de objetos tipo Cancelacion de la anotación especificada
     * por su identificador
     * @param oid identificador de la anotación conformado por el idAnotacion, IdMatricula
     * y IdZonaRegistral
     * @return lista de objetos tipo Cancelacion, cada una contiene el objeto cancelada que
     * indica la anotación cancelada
     * @see gov.sir.core.negocio.modelo.Cancelacion
     * @throws DAOException
     */
    public List getAnotacionesCanceladas(AnotacionPk oid)
        throws DAOException;

    /**
     * Obtiene una lista de objetos tipo AnotacionCiudadano de la anotación especificada
     * por su identificador
     * @param oid identificador de la anotación conformado por el idAnotacion, IdMatricula
     * y IdZonaRegistral
     * @return lista de objetos tipo AnotacionCiudadano, cada uno contiene el objeto
     * ciudadano
     * @see gov.sir.core.negocio.modelo.AnotacionCiudadano
     * @see gov.sir.core.negocio.modelo.Ciudadano
     * @throws DAOException
     */
    public List getAnotacionesCiudadano(AnotacionPk oid)
        throws DAOException;

    /**
     * Obtiene las anotaciones hijas de una anotacion
     * @param oid identificador de la anotación conformado por el idAnotacion, IdMatricula
     * y IdZonaRegistral
     * @return lista de objetos FolioDerivado, cada uno contiene el objeto hijo
     * que indica la anotación derivada
     * @see gov.sir.core.negocio.modelo.FolioDerivado
     * @throws DAOException
     */
    public List getAnotacionesHijas(AnotacionPk oid) throws DAOException;


	/**
	 * Obtiene las anotaciones padre de una anotacion
	 * @param oid identificador de la anotación conformado por el idAnotacion, IdMatricula
	 * y IdZonaRegistral
	 * @return lista de objetos FolioDerivado, cada uno contiene el objeto hijo
	 * que indica la anotación derivada
	 * @see gov.sir.core.negocio.modelo.FolioDerivado
	 * @throws DAOException
	 */
	public List getAnotacionesPadre(AnotacionPk oid) throws DAOException;


    /**
     * Obtiene el folio asociado a una anotación
     * @param oid identificador de la anotación conformado por el idAnotacion, IdMatricula
     * y IdZonaRegistral
     * @return Objeto Folio con sus objetos estado, lindero, tipoPredio y ZonaRegistral.
     * El objeto ZonaRegistral contiene la jerarquía circulo, vereda, municipio y departamento
     * null si  no encuentra un folio que coincida con el identificador dado
     * @throws DAOException
     */
    public Folio getFolioAnotacion(AnotacionPk oid) throws DAOException;

    /**
     * Crea un folio y asigna un número de matrícula
     * @param datos objeto Folio que debe contener codCatastral y sus objetos
     * ZonaRegistral, estado, complementacion y tipoPredio. Si la complementación
     * existe se asocia, si no se crea
     * @return objeto Folio con el número de matrícula asignado
     * @throws DAOException
     */
    public Folio crearFolio(Folio datos, Usuario us, TurnoPk oid, boolean velidarTurno) throws DAOException;
    
    public Folio crearFolioSegeng(Folio datos, Usuario us, TurnoPk oid, boolean velidarTurno, Folio folioB) throws DAOException;


    public boolean validarActualizacionCiudadanoTurno(Turno turno) throws DAOException;
    
    
    public List getFolioDerivePadre(String IdMatricula,String IdAnotacion) throws DAOException;
    
	/**
	 * Obtiene una lista con todos los posibles estados de un folio
	 * configurados en el sistema
	 * @return lista de objetos EstadoFolio
	 * @see gov.sir.core.negocio.modelo.EstadoFolio
	 * @throws DAOException
	 */
	public List getEstadosFolio() throws DAOException;



	/**
	 * Obtiene un EstadoFolio dado su identificador
	 * @param oid
	 * @return objeto EstadoFolio
	 * @throws DAOException
	 */
	public EstadoFolio getEstadoFolio(EstadoFolioPk oid)
		throws DAOException;

	/**
	 * Adiciona un EstadoFolio a la configuración del sistema
	 * @param datos objeto EstadoFolio con sus atributos exceptuando su identificador
	 * @param usuario que adiciona el estado del folio
	 * el cual es generado por el sistema
	 * @return identificador de estadoFolio generado
	 */
	public EstadoFolioPk addEstadoFolio(EstadoFolio datos, Usuario usuario)
		throws DAOException;

	/**
	 * Obtiene una lista con todos los posibles tipos de predio
	 * configurados en el sistema
	 * @return lista de objetos tipo TipoPredio
	 * @see gov.sir.core.negocio.modelo.TipoPredio
	 * @throws DAOException
	 */
	public List getTiposPredio() throws DAOException;

	/**
	 * Obtiene un TipoPredio dado su identificador
	 * @param oid identificador del tipoPredio
	 * @return objeto TipoPredio
	 * @throws DAOException
	 */
	public TipoPredio getTipoPredio(TipoPredioPk oid)
		throws DAOException;

	/**
	 * Adiciona un TipoPredio a la configuración del sistema
	 * @param datos objeto TipoPredio con sus atributos exceptuando su identificador
	 * el cual es generado por el sistema
	 * @return identificador de estadoFolio generado
	 * @throws DAOException
	 */
	public TipoPredioPk addTipoPredio(TipoPredio datos)
		throws DAOException;



	/**
	 * Obtiene una lista con todos los posibles tipos de naturaleza
	 * jurídica configurados en el sistema
	 * @return lista de objetos NaturalezaJuridica
	 * @see gov.sir.core.negocio.modelo.NaturalezaJuridica
	 * @throws DAOException
	 */
	public List getNaturalezasJuridicas() throws DAOException;

	/**
	 * Obtiene un objeto NaturalezaJuridica dado su identificador
	 * @param oid identificador de NaturalezaJuridica
	 * @return objeto NaturalezaJuridica
	 * @throws DAOException
	 */
	public NaturalezaJuridica getNaturalezaJuridica(NaturalezaJuridicaPk oid)
		throws DAOException;

	/**
	 * Adiciona un tipo de naturaleza jurídica a la configuración del sistema
	 * @param datos objeto NaturalezaJuridica con sus atributos exceptuando su identificador
	 * el cual es generado por el sistema, usuario que adiciona la naturaleza juridica
	 * @return identificador de NaturalezaJuridica generado
	 * @throws DAOException
	 */
	public NaturalezaJuridicaPk addNaturalezaJuridicaToGrupo(
	   NaturalezaJuridica datos, GrupoNaturalezaJuridicaPk gid, Usuario usuario)
	   throws DAOException;

	/**
	 * Obtiene una lista con todos los posibles tipos de anotacion
	 * configurados en el sistema
	 * @return lista de objetos TipoAnotacion
	 * @see gov.sir.core.negocio.modelo.TipoAnotacion
	 * @throws DAOException
	 */
	public List getTiposAnotacion() throws DAOException;

	/**
	 * Obtiene un objeto TipoAnotacion dado su identificador
	 * @param oid identificador del tipo de anotación
	 * @return objeto TipoAnotación
	 * @throws DAOException
	 */
	public TipoAnotacion getTipoAnotacion(TipoAnotacionPk oid)
		throws DAOException;

	/**
	 * Adiciona un tipo de anotación a la configuración del sistema
	 * @param datos objeto TipoAnotacion con sus atributos exceptuando su identificador
	 * el cual es generado por el sistema
	 * @return identificador de TipoAnotacion generado
	 * @throws DAOException
	 */
	public TipoAnotacionPk addTipoAnotacion(TipoAnotacion datos)
		throws DAOException;

	/**
	 * Obtiene una lista con todos los posibles tipos de documentos
	 * configurados en el sistema
	 * @return lista de objetos TipoDocumento
	 * @see gov.sir.core.negocio.modelo.TipoDocumento
	 * @throws DAOException
	 */
	public List getTiposDocumento() throws DAOException;

	/**
	 * Obtiene un objeto TipoDocumento dado su identificador
	 * @param oid identificador del tipo de documento
	 * @return objeto TipoDocumento
	 * @throws DAOException
	 */
	public TipoDocumento getTipoDocumento(TipoDocumentoPk oid)
		throws DAOException;

	/**
	 * Adiciona un tipo de documento a la configuración del sistema
	 * @param datos objeto TipoDocumento con sus atributos exceptuando su identificador
	 * el cual es generado por el sistema
	 * @param usuario que adiciona el tipo de documento
	 * @return identificador de TipoAnotacion generado
	 * @throws DAOException
	 */
	public TipoDocumentoPk addTipoDocumento(TipoDocumento datos, Usuario usuario)
		throws DAOException;

	/**
	 * Obtiene una lista con todos los posibles tipos de oficina
	 * configurados en el sistema
	 * @return lista de objetos TipoOficina
	 * @see gov.sir.core.negocio.modelo.TipoOficina
	 * @throws DAOException
	 */
	public List getTiposOficina() throws DAOException;

	/**
	 * Obtiene un objeto TipoOficina dado su identificador
	 * @param oid identificador del tipo de oficina
	 * @return objeto TipoOficina
	 * @throws DAOException
	 */
	public TipoOficina getTipoOficina(TipoOficinaPk oid)
		throws DAOException;

	/**
	 * Adiciona un tipo de oficina a la configuración del sistema
	 * @param datos objeto TipoOficina con sus atributos exceptuando su identificador
	 * el cual es generado por el sistema
	 * @param Usuario que adiciona el tipo de oficina
	 * @return identificador de TipoOficina generado
	 * @throws DAOException
	 */
	public TipoOficinaPk addTipoOficina(TipoOficina datos, Usuario usuario)
		throws DAOException;



	/**
	 * Agrega una direccion a un folio
	 * @param oid Identificador del folio
	 * @param datos Dirección a agregar, debe contener el objeto eje. El eje1 puede ser nulo
	 * @param pm PersistenceManager
	 * @return identificador de la dirección asignada por el sistema
	 * @throws DAOException
	 */
	public DireccionPk addDireccionToFolio(FolioPk oid, Direccion datos)
		throws DAOException;


        /**
         * Retorna la dirección con Mayor ID, de manera trasiente y sin dependencias
         * @param direcciones
         * @return
         */
        public Direccion getUltimaDireccion(FolioPk oid)
                throws DAOException;

	/**
	 * Setea el documento de una anotacion. Si idDocumento es nulo, se asigna un consecutivo y
	 * se crea el documento, si el idDocumento es diferente de nulo se valida que exista y se asocia
	 * @param anotacion
	 * @param datos
	 * @return
	 * @throws DAOException
	 */
	public DocumentoPk setDocumentoToAnotacion(AnotacionPk oid,
		Documento datos) throws DAOException;


	/**
	 * Añade una anotación a un folio
	 * @param folio
	 * @param datos La anotación debe contener los objetos existentes: estado, naturalezaJuridica y
	 * tipoAnotacion. Si el idDocumento de la anotacion es direfente de null se valida que exista y
	 * se asocia, si el documento de la anotación es null se crea
	 * @return
	 * @throws DAOException
	 */
	public AnotacionPk addAnotacionToFolio(FolioPk oid, Anotacion datos)
		throws DAOException;



	/**
	 * Servicio utilizado para establecer si el folio con el número
	 * de matrícula se encuentra en trámite
	 * @param matricula
	 * @return true: folio en trámite,   false: folio no se encuentra en trámite
	 * @throws DAOException
	 */
	public boolean enTramiteFolio(String matricula) throws DAOException;

	/**
	 * Servicio utilizado para atender una consulta
	 * @param busqueda
	 * @return Objeto Busqueda con sus objetos respuesta
	 * @throws DAOException
	 */
	public Busqueda ejecutarConsulta(BusquedaPk bid) throws DAOException;
	
	/**
	 * Servicio utilizado para atender una consulta
	 * @param busqueda
	 * @return Objeto Busqueda con sus objetos respuesta
	 * @throws DAOException
	 */
	public Busqueda ejecutarConsultaAdministracion(Busqueda busqueda) throws DAOException;


	/**
	 * Retorna los grupos de naturalezas jurídicas, cada uno con su lista de naturalezas jurídicas
	 * @return Lista de GrupoNaturalezaJuridica
	 * @see gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica
	 * @throws DAOException
	 */
	public List getGruposNaturalezaJuridica() throws DAOException;


	/**
	 * Retorna la lista de dominios de naturalezas jurídicas
	 * @return
	 * @throws DAOException
	 */
	public List getDominiosNaturalezaJuridica() throws DAOException;


	/**
	 * Retorna una lista de las oficinas origen de la vereda especificada, las oficinas origen el objeto
	 * TipoOficina
	 * @param oid
	 * @return Lista de OficinaOrigen
	 * @throws DAOException
	 */
	public List getOficinasOrigenByVereda(VeredaPk oid) throws DAOException;



	/**
	 * Devuelve el número de anotaciones del folio indicado, si el folio no existe retorna 0
	 * @param matricula
	 * @return
	 * @throws DAOException
	 */
	public long getCountAnotacionesFolio(String matricula) throws DAOException;



	/**
	 * Valida la información del folio antes de crearlo. Si existe por lo menos un error lanza un DAOException
	 * Los errores son listados en DAOException
	 * @param datos
	 * @throws DAOException
	 */
	public void validarCrearFolio(Folio datos, boolean validarTurno) throws DAOException;


	/**
	 * Finaliza el servicio Forseti
	 */
	public void finalizar() throws DAOException;


	/**
	 * Especifica si existe una matrícula en el sistema
	 * @param matricula
	 * @return true: existe, false: No existe
	 * @throws DAOException
	 */
	public boolean existeMatricula(String matricula)throws DAOException;
	
	
	/**
     * Servicio utilizado para establecer si el folio con el número
     * de matrícula especificado existe en el sistema, a difencia del método 
     * existeFolio, éste tiene en cuenta los datos temporales
	 * @param matricula
	 * @return true: existe, false: No existe
	 * @throws DAOException
	 */
	public boolean existeMatriculaIncluyendoTemporales(String matricula)throws DAOException;


	/**
	 * Especifica el estado de un folio, si el folio no existe retorna null
	 * @param matricula
	 * @return EstadoFolio
	 * @throws DAOException
	 */
	public EstadoFolio getEstadoFolioByMatricula(String matricula) throws DAOException;
	
	/**
	 * Especifica el estado de un folio segun los datos temporales, si el folio no existe retorna null
	 * @param matricula
	 * @return EstadoFolio
	 * @throws DAOException
	 */
	public EstadoFolio getEstadoFolioByMatriculaTMP(String matricula) throws DAOException;



	/**
	* Actualiza los datos temporales de un folio.
	* @param matricula, datosTMP, eliminarDireccion
	* @return rta:  true si se realizo la operacion correctamente
	* 				false si hubo algun error
	* @throws DAOException
	*/
	
	public boolean actualizarFolioDatosTMP(String matricula, FolioDatosTMP datos, boolean eliminarDireccion) throws DAOException;
	
	/**
	 * Bloquea los folios especificados en una lista de matriculas a un
	 * usuario determinado. Si en el bloqueo de algún folio se produce
	 * una excepción, esta quedará registrada en la hastable de DAOException
	 * en donde la llave es la matricula y el valor el mensaje de excepción.
	 * Si no se produce ningún error no se lanza ninguna excepción y se crea
	 * una llave de bloqueo al usuario y se le asocian los folios dados. Finalmente
	 * la LlaveBloquedo es retornada
	 * @param matriculas
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public LlaveBloqueo bloquearFolios(List matriculas, Usuario usuario, TurnoPk turnoID) throws DAOException;




	/**
	 * Adiciona un EstadoAnotacion a la configuración del sistema
	 * @param datos objeto EstadoAnotacion con sus atributos exceptuando su identificador
	 * el cual es generado por el sistema
	 * @return identificador de estadoFolio generado
	 */
	public EstadoAnotacionPk addEstadoAnotacion(EstadoAnotacion datos)
		throws DAOException;



	/**
	 * Obtiene una lista de estados anotacion de la configuración del sistema
	 * @return lista de objetos EstadoAnotacion
	 * @see gov.sir.core.negocio.modelo.EstadoAnotacion
	 * @throws DAOException
	 */
	 public List getEstadosAnotacion() throws DAOException;


	/**
	 * Agrega un grupo de naturaleza juridica la configuración del sistema
	 * @param naturaleza GrupoNaturalezaJuridica con su nombre
	 * @throws DAOException
	 */
	public GrupoNaturalezaJuridicaPk addGrupoNaturalezaJuridica(
		GrupoNaturalezaJuridica naturaleza) throws DAOException;


	/**
	 * Obtiene un EstadoFolio dado su identificador
	 * @param oid
	 * @return objeto EstadoFolio
	 * @throws DAOException
	 */
	public GrupoNaturalezaJuridica getGrupoNaturalezaJuridica(
		GrupoNaturalezaJuridicaPk oid) throws DAOException;


	/**
	 *
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public DominioNaturalezaJuridica getDominioNaturalezaJuridica(
		DominioNaturalezaJuridicaPk oid) throws DAOException;


	/**
	 * @param notaria con su tipo, nombre, numero, departamento, municipio
	 * @return
	 * @throws DAOException
	 */
	public OficinaOrigenPk addOficinaOrigen(OficinaOrigen oficina)
		throws DAOException;



	/**
	 * Desbloquea los folios especificados de una lista de matriculas a un
	 * usuario determinado. Si en el desbloqueo de algún folio se produce
	 * una excepción, esta quedará registrada en la hastable de DAOException
	 * en donde la llave es la matricula y el valor el mensaje de excepción.
	 * Si no se produce ningún error no se lanza ninguna excepción
	 * @param matriculas
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public boolean desbloquearFolios(List matriculas, Usuario usuario)
		throws DAOException;


	/**
	 * Desbloquea un folio. Si el desbloqueo no e produce produce
	 * una excepción, esta quedará registrada en la hastable de DAOException
	 * en donde la llave es la matricula y el valor el mensaje de excepción.
	 * Si no se produce ningún error no se lanza ninguna excepción
	 * @param folio
	 * @return
	 * @throws DAOException
	 */
	public boolean desbloquearFolio(Folio folio)
     throws DAOException;

	/**
	* Actualiza la información de un folio dependiendo de los cambios que vienen
	* en el objeto. El usuario debe tener bloqueado el folio para poder
	* afectar la información de éste, de lo contrario se genera un ForsetiException.
	* Los cambios quedan registrados en el esquema temporal.
	* Los cambios o actualizaciones de los objetos dependientes del folio se comportan
	* de la siguiente manera: (Se actualizan los que vienen diferentes a null)
	*
	* Lindero: folio.getLindero()
	* El lindero puede ser actualizado.
	*
	* Código catastral: folio.getCodCatastral()
	* El código catastral puede ser actualizado.
	*
	* Código catastral anterior: folio.getCodCatastralAnterior()
	* El código catastral anterior puede ser actualizado.
	*
	* Complementación: folio.getComplementacion()
	* La complementación puede ser actualizada. Si se afecta la complementación de un
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
	* Las salvedades sólo pueden ser añadidas, por lo tanto no necesitan IdSalvedad
	*
	* Direcciones: (folio.getDirecciones())
	* Las direcciones nunca son actualizadas, sólamente son insertadas. Por lo tanto
	* toda dirección que llegue será insertada dentro de las direcciones temporales
	* del folio. Cada dirección debe tener el primer eje asociado.
	*
	* PARA ANOTACIONES:
	*
	* Anotaciones: (folio.getAnotaciones())
	* Las anotaciones pueden ser actualizadas o añadidas.
	*
	* 1. Adición de anotaciones:
	*
	* Una anotación siempre es añadida en las anotaciones temporales. Para agregar una anotación,
	* se debe crear un objeto Anotacion sin setearle IdAnotacion y se tienen que asociar sus objetos
	* básicos: NaturalezaJuridica, TipoAnotacion y Estado. La anotación puede incluir ciudadanos a
	* través de la lista de getAnotacionesCiudadanos(). Cada anotaciónCiudadano debe tener seteado
	* el objeto ciudadano con el número y tipo de documento del ciudadano. Si el ciudadano existe
	* se asocia, si no existe se crea y se asocia. La anotación también puede incluir salvedades,
	* las salvedades se agregan a través de la lista getSalvedadesAnotacion(). También puede incluir
	* cancelaciones a través de la lista getAnotacionesCancelacion(). Cada Cancelación debe tener
	* asociado el objeto "cancelada" que es una anotación existente con el ID seteado.
	*
	* 2. Borrar anotaciones temporales
	*
	* Una anotación temporal puede ser eliminada con todas sus dependencias (salvedades, ciudadanos y cancelaciones).
	* No se pueden eliminar anotaciones que estén encadenando folios, es decir las de tipo "GENERADORA" o "DERIVADA"
	* Tampoco se pueden eliminar anotaciones temporales canceladas por otra anotación.
	* Para eliminar una anotación temporal se debe pasar un objeto anotación dentro de la lista de anotaciones
	* de folio con el IdAnotacion seteado que se quiere eliminar y con el atributo toDelete de anotación en true.
	*
	* 3. Actualizar anotaciones temporales
	*
	* Una anotación temporal puede ser actualizada. Se pueden actualizar datos básicos como:
	* comentario, especificación, orden, naturaleza jurídica y estado. Se pueden eliminar y añadir
	* ciudadanos de la anotación, eliminar, actualizar y añadir salvedades.
	*
	* Para actualizar una anotación, se debe incluir en la lista de anotaciones de folio un
	* objeto anotación con el idAnotacion seteado, no se debe setear el toDelete. Los datos básicos
	* que se quieren actualizar deben ser seteados en el objeto anotación.
	*
	* Los ciudadanos pueden ser añadidos de la misma manera en que se agregan cuando se inserta
	* una anotación. También pueden ser eliminados seteando en la AnotacionCiudadano el rol,
	* la propiedad toDelete a true, y el objeto ciudadano con el idCiudadano seteado.
	*
	* Las salvedades pueden ser añadidas de la misma manera en que se agregan cuando se inserta
	* una anotación. También pueden ser eliminados seteando el idSalvedad y la propiedad toDelete
	* a true. Las salvedades pueden ser actualizadas seteando el idSalvedad y seteando la nueva
	* descripción.
	*
    * Las cancelaciones pueden ser añadidas de la misma manera en que se agregan cuando se inserta
    * una anotación. También pueden ser eliminadas seteando el flag toDelete de la cancelación en true
    * y asociando la anotación cancelada con su respectivo idAnotacion
    *
	*
	* 4. Actualizar anotaciones definitivas (Correccion)
	*
	* Una anotación definitiva puede ser actualizada. Se pueden actualizar datos básicos como:
	* comentario, especificación, orden, naturaleza jurídica y estado.  Se pueden eliminar y añadir
	* ciudadanos de la anotación, eliminar, actualizar y añadir salvedades.
	*
	* Para actualizar una anotación definitiva, se debe incluir en la lista de anotaciones de folio un
	* objeto anotación con el idAnotacion seteado. Los datos básicos que se quieren actualizar deben ser
	* seteados en el objeto anotación.
	*
	* Los ciudadanos pueden ser añadidos de la misma manera en que se agregan cuando se inserta
	* una anotación. También pueden ser eliminados seteando en la AnotacionCiudadano el rol,
	* la propiedad toDelete a true, y el objeto ciudadano con el idCiudadano seteado.
	*
	* Las salvedades pueden ser añadidas de la misma manera en que se agregan cuando se inserta
	* una anotación. También pueden ser eliminados seteando el idSalvedad y la propiedad toDelete
	* a true. Las salvedades pueden ser actualizadas seteando el idSalvedad y seteando la nueva
	* descripción.
	*
	* Cada vez que se actualice una anotación definitiva se guarda el registro de su estado
	* actual creando unanueva anotación y dejándola con estado "OBSOLETA"
	*
	*
	* @param folio
	* @param usuario
	* @return
	* @throws DAOException
	*/
	public boolean updateFolio(Folio folio, Usuario usuario, TurnoPk tid, boolean validarTurno) throws DAOException;
	
	
	/**
	 * Alternativa para guardar los folios derivados
	 * @param datos
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	
	public boolean updateFolioFD(Folio folio, Usuario usuario, TurnoPk tid, boolean validarTurno, List LstAnotaFolioHijo, List LstAnotaFolioPadre, String anotacionUpdate, String matriculaUpdate, List lstHijosRemove) throws DAOException;

	/**
	 * Hace definitivos los cambios temporales aplicados al folio
	 * @param datos
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public boolean hacerDefinitivoFolio(Folio folio, Usuario usuario, TurnoPk tid, boolean validarTurno) throws DAOException;



	/**
	* Deshace los cambios temporales realizados al folio
	* @param datos
	* @param usuario
	* @return
	* @throws DAOException
	*/
	public boolean deshacerCambiosFolio(Folio datos, Usuario usuario)
		throws DAOException;

	/**
	* Deshace los cambios temporales realizados a los ciudadadanos 
	* @param datos
	* @param usuario
	* @return
	* @throws DAOException
	*/

/**
	* Deshace los cambios que están siendo aplicados a los folios del turno, borra toda la información temporal
	* @param turnoID
	* @param usuario
	* @return
	* @throws DAOException
	*/
	public boolean deshacerCambiosFolioByTurno(TurnoPk turnoID, Usuario usuario)
		throws DAOException;
        
	public boolean deshacerCambiosCiudadanosTurno(TurnoPk turnoID, Usuario usuario) 
		throws DAOException;

	/**
	 * Retorna la información del folio dependiendo del usuario solicitante,
	 * si el usuario tiene bloqueado el folio se retorna la información definitiva
	 * y temporal, si el usuario no tiene el bloqueo del folio se lanza una excepcion
	 * indicando lo sucedido
	 * @param oid
	 * @param us
	 * @return
	 * @throws DAOException
	 */
	public Folio getFolioByID(FolioPk oid, Usuario usuario)
		throws DAOException;

	/**
	 * Retorna la información del folio dependiendo del usuario solicitante,
	 * si el usuario tiene bloqueado el folio se retorna la información definitiva
	 * y temporal, si el usuario no tiene el bloqueo del folio se lanza una excepcion
	 * indicando lo sucedido. Se valida que el folio no sea definitivo
	 * @param oid
	 * @param us
	 * @return
	 * @throws DAOException
	 */
	public Folio loadFolioByID(FolioPk oid, Usuario usuario)
		throws DAOException;

	/**
	 * Retorna la validacion de la existencia de una matricula no grabada
	 * @param String circulo
	 * @param long idMatricula
	 * @return
	 * @throws DAOException
	 */
	 public boolean matriculaNoGrabadaExistente(String circulo, long idMatricula) 
	 throws DAOException;
	 
	/**
	 * Segrega un folio
	 * @param datos Tiene los IDs del folio a segregar y los IDs de la anotaciones a heredar
	 * @param foliosDerivados Lista de folios derivados
	 * @param usuario Usuario que realiza la segregación, debe tener el folio bloqueado
	 * @param nuevaAnotacion Anotación que se crea en el padre y en los hijos para encadenarlos
     * @param copiarComentarioSegregacion Permite determinar si se debe o no copiar el comentario de la anotación origen a las
     *        nuevas anotaciones de los nuevos folios.
     * @param copiarComentarioHeredadas Permite determinar si se debe o no copiar el comentario de las anotaciones a heredar en los nuevos folios
	 * @return
	 * @throws DAOException
	 */
	public List segregarFolio(Folio datos, List foliosDerivados,
		Usuario usuario, Anotacion nuevaAnotacion, boolean copiarComentarioSegregacion, boolean copiarComentarioHeredadas, TurnoPk oid) throws DAOException;

	
	/**
     * Segrega un folio a través de un procedimiento almacenado en PL/SLQ
     * @param datos Datos del folio a segregar
     * @param oid ID el turno a segregar
     * @return List con los folios segregados
     * @throws Throwable
     * @author      :   Julio Alcázar Rivas
     * @change      :   Se modifico el metodo segregarFolioPLSQL agregandole el param SalvedadAnotacion salvedad
     * Caso Mantis  :   04131
     */
    public List segregarFolioPLSQL(Folio datos, TurnoPk oid,SalvedadAnotacion salvedad, String IdAnotacionDef) throws DAOException;


	/**
	 * Engloba un folio a partir de unos folios Fuentes y una tabla con anotaciones
	 * heredadas
	 * @param foliosFuentes folios a englobar
	 * @param nuevoFolio folio a partir del cual se sacará la información geográfica y contendrá la dirección a añadir al nuevo folio
	 * @param usuario usuario actual
	 * @param tabla tabla con la lista de anotaciones a heredar de cada folio fuente
	 * @param anotacion anotación nueva o existente de algún folio padre (con IDs seteados) la cuál va a encadenar los folios padres con el hijo
	 * @param informacionAdicional información adicional del englobe: LOTE, PORCENTAJE, DESCRIPCION Y AREA
     * @param copiarComentarioEnglobe Permite determinar si se debe o no copiar el comentario de la anotación origen a las
     *        nuevas anotaciones de los nuevos folios.
     * @param copiarComentarioHeredadas Permite determinar si se debe o no copiar el comentario de las anotaciones a heredar en los nuevos folios
	 * @return
	 * @throws Throwable
	 */
	public Folio englobarFolio(List foliosFuentes, Folio folio,
	Usuario usuario, List tabla, Anotacion anotacion, FolioDerivado informacionAdicional, boolean copiarComentarioEnglobe, boolean copiarComentarioHeredadas , TurnoPk oid) throws DAOException;



	/**
	 * Delega el bloqueo de todos los folios asociados al turno
	 * Si algún folio no está bloqueado se genera una excepcion guardada
	 * en un hashtable donde la llave es el número de matrícula y el valor
	 * es la descripción del error
	 * @param oid
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public LlaveBloqueo delegarBloqueoFolios(TurnoPk oid, Usuario usuario)
		throws DAOException;
	
	/**
	 * Delega el bloqueo de todos los folios asociados al turno
	 * @param oid
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public LlaveBloqueo delegarBloqueoFoliosObligatorio(TurnoPk oid, Usuario usuario)
		throws DAOException;

	/**
	 * Determina si una matricula tiene por lo menos una anotación temporal
	 * @param matricula
	 * @return
	 * @throws DAOException
	 */
	public boolean hasAnotacionesTMP(FolioPk oid) throws DAOException;




	/**
	 * Bloquea los todos los folios asociados a un turno
	 * @param oid
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public LlaveBloqueo bloquearFolios(TurnoPk oid, Usuario usuario)
		throws DAOException;


	/**
	 * Desbloquea todos los folios asociados a un turno, si en el turno existe algún folio que no
	 * esté bloqueado se produce una excepción
	 * @param oid
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public boolean desbloquearFolios(TurnoPk oid, Usuario usuario)
		throws DAOException;

	/**
     * Desbloquea todos los folios asociados a un turno, si en el turno existe algún folio que no
     * esté bloqueado se produce una excepción no valida que el bloqueo lo tenga un usuario.
     * @param oid
     * @param usuario
     * @return
     * @throws DAOException
     */
	public boolean desbloquearFoliosTurno(TurnoPk oid, Usuario usuario)
		throws DAOException;

	

	/**
	 * Acualiza la información de las matrículas dadas con los datos
	 * del folio.
	 * @param datos
	 * @param ids Listas de objetos tipo Folio.ID
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public boolean updateFolios(Folio datos, List ids, Usuario usuario, boolean validarBloqueo)
		throws DAOException;


	/**
	 * Agrega un dominio de naturaleza jurídica
	 * @param naturaleza
	 * @return
	 * @throws DAOException
	 */
	public DominioNaturalezaJuridicaPk addDominioNaturalezaJuridica(
	DominioNaturalezaJuridica naturaleza) throws DAOException;


	/**
	 * Calcula el número de anotaciones del folio con el criterio dado. El criterio
	 * es tomado del conjunto de constantes de CCriterio
	 * @param oid
	 * @param criterio
	 * @param valor
	 * @return
	 * @throws DAOException
	 */
	public long getCountAnotacionesFolio(FolioPk oid, String criterio,
		   String valor, boolean vigente) throws DAOException;



	/**
	 * Retorna las anotaciones de un folio con el criterio dado. Se puede especificar
	 * la posición inicial y el número de anotaciones que se quieren obtener a partir
	 * de dicha posición.
	 * @param oid
	 * @param criterio
	 * @param valor
	 * @param posicionInicial
	 * @param cantidad
	 * @return
	 * @throws DAOException
	 */
	public List getAnotacionesFolio(FolioPk oid, String criterio,
		String valor, int posicionInicial, int cantidad, boolean vigente)
		throws DAOException;

        /**
         * @author     : Ellery David Robles G.
         * @casoMantis : 08469.
         * @actaReq    : 024_151 - Error en anotación de medida cautelar.
         * @change     : Retorna las anotaciones definitivas y temporales de un folio segun el parametro naturaleza jurudica.
	 * @param      : oid.
	 * @param      : naturalezaJuridica.
	 * @throws     : DAOException.
	 */
	public List getAnotacionesFolioNJ(FolioPk oid, String naturalezaJuridica) throws DAOException;


	/**
	* Obtiene un objeto Folio dado su identificador
	* @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
	* @return Objeto Folio con sus objetos estado, complementacion, tipoPredio y ZonaRegistral,
	* y listas de salvedades folio y direcciones. No se incluyen las anotaciones
	* El objeto ZonaRegistral contiene la jerarquía circulo, vereda, municipio y departamento
	* null si  no encuentra un folio que coincida con el identificador dado
	* @throws DAOException
	*/
	public Folio getFolioByIDSinAnotaciones(FolioPk oid)
		throws DAOException;
	
	/**
	* Obtiene un objeto Folio dado su identificador
	* @param oid identificador del Folio conformado por Nro matricula.
	* @return Objeto Folio Temporal
	* @throws DAOException
	*/
	public FolioDatosTMP getFolioDatosTMP(FolioDatosTMPPk oid)
		throws DAOException;
	
	/**
	 * Actualiza las salvedades del folio. Por cada salvedad si existe, la actualiza, de lo contrario
	 * la inserta
	 * @param folio
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public boolean updateFolioSalvedades(Folio folio, Usuario usuario) throws DAOException;
	
	
	/**
	* Obtiene un objeto Folio dado su identificador aun si el folio es temporal
	* @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
	* @return Objeto Folio con sus objetos estado, complementacion, tipoPredio y ZonaRegistral,
	* y listas de salvedades folio y direcciones. No se incluyen las anotaciones
	* El objeto ZonaRegistral contiene la jerarquía circulo, vereda, municipio y departamento
	* null si  no encuentra un folio que coincida con el identificador dado
	* @throws DAOException
	*/
	public Folio getFolioEvenTempByIDSinAnotaciones(FolioPk oid)
		throws DAOException;



	/**
	* Obtiene un objeto Folio dado su identificador
	* @param matricula número de matrícula del folio
	* @return Objeto Folio con sus objetos estado, complementacion, tipoPredio y ZonaRegistral,
	* y listas de salvedades folio y direcciones. No se incluyen las anotaciones
	* El objeto ZonaRegistral contiene la jerarquía circulo, vereda, municipio y departamento
	* null si  no encuentra un folio que coincida con el identificador dado
	* @throws DAOException
	*/
	public Folio getFolioByMatriculaSinAnotaciones(String matricula)
		throws DAOException;


	/**
	 * Obtiene un turno de correcciones activo que tenga asociado
	 * el folio dado, si no existe un turno de correción activo con
	 * el folio asociado retorna null
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public Turno getTurnoCorreccionActivoFolio(FolioPk oid)
		throws DAOException;


	 /**
     * Si el turno va a deshacer los cambios temporales del folio
     * valida si existe otros turnos que tengan fecha
     * de inicio anterior y que no hayan pasado por la fase de firmar
     * que es la fase posterior a calificación y que ya hayan sido calificados.
     * Ademas valida si existen turnos de correciones entre la fase
     * de correccion simple y revisar y aprobar.
     * Si hay algún error en la validación se lanzará una excepción
     * en donde la hastable interna tiene como llave la matricula y como
     * valor la lista de turnos que tienen el folio
     * @param oid
     * @throws DAOException
     */
    public void validarPrincipioPrioridadDeshacerCambiosTemporales(TurnoPk oid)
    	throws DAOException;

	/**
	 * Valida el principio de prioridad según Art. 22 Dto 1250/70
	 * Si el turno va a entrar a calificación este servicio se puede
	 * utilizar para validar si existe otros turnos que tengan fecha
	 * de inicio anterior y que no hayan pasado por la fase de firmar
	 * que es la fase posterior a calificación
	 * Si hay algún error en la validación se lanzará una excepción
	 * en donde la hastable interna tiene como llave la matricula y como
	 * valor la lista de turnos que tienen el folio
	 * @param oid
	 * @throws DAOException
	 */
	public void validarPrincipioPrioridadCalificacion(TurnoPk oid, Usuario usuario)
		throws DAOException;
	
	/**
     * Valida el principio de prioridad Digitacion
     * Si el turno va a entrar a Digitación este servicio se puede
     * utilizar para validar si existe otros turnos que tengan fecha
     * de inicio anterior y que esten en esta fase
     * Si hay algún error en la validación se lanzará una excepción
     * en donde la hastable interna tiene como llave la matricula y como
     * valor la lista de turnos que tienen el folio
     * @param oid
     * @throws DAOException
     */
	public void validarPrincipioPrioridadDigitacion(TurnoPk oid, Usuario usuario)
		throws DAOException;
	
    /**
     * Valida el principio de prioridad según Art. 22 Dto 1250/70
     * Si el turno va a entrar a calificación este servicio se puede
     * utilizar para validar si existe otros turnos que tengan fecha
     * de inicio anterior y que no hayan pasado de la fase de calificación
     * Se retorna true si no hay turnos que hayan sido creado previamente o false si los hay.
     * @param oid
     * @param usuario
     * @throws Throwable
     */
	public boolean isTurnoValidoCalificacion(TurnoPk oid, Usuario usuario)
		throws DAOException;	
	
	/**
     * Valida el principio de prioridad para salir de Calificación
     * Si el turno va a entrar a calificación este servicio se puede
     * utilizar para validar si existe otros turnos que tengan fecha
     * de inicio anterior y que no hayan pasado de la fase de calificación
     * Se retorna true si no hay turnos que hayan sido creado previamente o false si los hay.
     * @param oid
     * @param usuario
     * @throws Throwable
     */
	public void isTurnoValidoSalidaCalificacion(TurnoPk oid, Usuario usuario)
		throws DAOException;


	/**
	 * Valida el principio de prioridad para un turno. Se valida que para cada
	 * matrícula relacionada no exista un turno de corrección activo con la misma
	 * matrícula asociada. Si algún folio asociado al turno presenta esta situación,
	 * se lanza una excepción con la hashtable donde la llave es la matrícula y el
	 * valor es la lista de turnos de calificación que tienen el folio
	 * @param oid
	 * @throws DAOException
	 */
	public void validarPrincipioPrioridadCorreccion(TurnoPk oid)
		throws DAOException;



	/**
	 * Retorna la lista con los folios hijos del folio especificado (Folio)
     * Si el folio no tiene hijos o no existe retorna una lista vacía
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public List getFoliosHijos(FolioPk oid)
		throws DAOException;
	
	/**
	 * Retorna la lista con los folios hijos del folio especificado (Folio)
     * Si el folio no tiene hijos o no existe retorna una lista vacía
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public List getFoliosHijosOrdenAnotacion(FolioPk oid)
		throws DAOException;


	/**
	 * Retorna la lista con los los folios padre del folio especificado (Folio)
     * Si el folio no tiene padres o no existe retorna una lista vacía
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public List getFoliosPadre(FolioPk oid)
		throws DAOException;
	
	/**
	 * Retorna la lista con los folios Derivado hijos del folio especificado (Folio)
     * Si el folio no tiene hijos o no existe retorna una lista vacía
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public List getFoliosDerivadoHijos(FolioPk oid)
		throws DAOException;


	/**
	 * Retorna la lista con los los folios Derivado padre del folio especificado (Folio)
     * Si el folio no tiene padres o no existe retorna una lista vacía
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public List getFoliosDerivadoPadre(FolioPk oid)
		throws DAOException;
	
	/**
	 * Retorna la lista con los folios Derivado hijos del folio especificado (Folio)
     * Si el folio no tiene hijos o no existe retorna una lista vacía el usuario debe tener el bloqueo
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public List getFoliosDerivadoHijos(FolioPk oid, Usuario usuario)
		throws DAOException;


	/**
	 * Retorna la lista con los los folios Derivado padre del folio especificado (Folio)
     * Si el folio no tiene padres o no existe retorna una lista vacía el usuario debe tener el bloqueo
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public List getFoliosDerivadoPadre(FolioPk oid, Usuario usuario)
		throws DAOException;


	/**
	* Servicio que permite eliminar un objeto <code>EstadoFolio</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param acto Objeto <code>EstadoFolio</code> que va a ser eliminado.
	* @param usuario que elimina el estado del folio
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.EstadoFolio
	*/
	public boolean eliminarEstadoFolio (EstadoFolio dato, Usuario usuario) throws DAOException;

	/**
	* Servicio que permite eliminar un objeto <code>TipoOficina</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param acto Objeto <code>TipoOficina</code> que va a ser eliminado.
	* @param Usuario que elimina el tipo de oficina
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoOficina
	*/
	public boolean eliminarTipoOficina (TipoOficina dato, Usuario usuario) throws DAOException;

	/**
	* Servicio que permite eliminar un objeto <code>TipoDocumento</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param acto Objeto <code>TipoDocumento</code> que va a ser eliminado.
	* @param Usuario que elimina el tipo de documento
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoDocumento
	*/
	public boolean eliminarTipoDocumento (TipoDocumento dato, Usuario usuario) throws DAOException;


	/**
	* Servicio que permite eliminar un objeto <code>TipoPredio</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param acto Objeto <code>TipoPredio</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoPredio
	*/
	public boolean eliminarTipoPredio (TipoPredio dato) throws DAOException;



	/**
	* Servicio que permite eliminar un objeto <code>GrupoNaturalezaJuridica</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param acto Objeto <code>GrupoNaturalezaJuridica</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica
	*/
	public boolean eliminarGrupoNaturalezaJuridica(GrupoNaturalezaJuridica dato) throws DAOException;


	/**
	* Servicio que permite eliminar un objeto <code>NaturalezaJuridica</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param acto Objeto <code>NaturalezaJuridica</code> que va a ser eliminado.
	* @param usuario que va a eliminar la naturaleza juridica
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.NaturalezaJuridica
	*/
	public boolean eliminarNaturalezaJuridica(NaturalezaJuridica dato, Usuario usuario) throws DAOException;


	/**
	* Servicio que permite eliminar un objeto <code>EstadoAnotacion</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param acto Objeto <code>EstadoAnotacion</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.EstadoAnotacion
	*/
	public boolean eliminarEstadoAnotacion(EstadoAnotacion dato) throws DAOException;


	/**
	* Servicio que permite eliminar un objeto <code>DominioNaturalezaJuridica</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param acto Objeto <code>DominioNaturalezaJuridica</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.DominioNaturalezaJuridica
	*/
	public boolean eliminarDominioNaturalezaJuridica(DominioNaturalezaJuridica dato) throws DAOException;






	/**
	 * Valida la terminación de calificación dependiendo del tipo de
	 * validación que se pasa
	 * @param oid Identificador del turno
	 * @param tipoValidacion Tipo de validación: T
	 * TODOS_FOLIOS_UNA_ANOTACION: Valida que todos los folios asociados al turno
	 * tengan por lo menos una anotacion temporal
	 * UN_FOLIO_UNA_ANOTACION: Valida que por lo menos un folio del turno contenga
	 * una anotación
	 * @see gov.sir.core.negocio.modelo.constantes.CTipoRevisionCalificacion
	 * @return true: Se cumple la validación, false: NO se cumple la validación
	 * @throws DAOException
	 */
	public boolean validarTerminacionCalificacion(TurnoPk oid, String tipoValidacion)  throws DAOException;



	/**
	 * Retorna la lista con los IDs (Folio.ID) de los folios hijos del folio especificado
	 * tanto definitivos como temporales
	 * Si el folio no tiene hijos o no existe retorna una lista vacía
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public List getFoliosHijos(FolioPk oid, Usuario usuario) throws DAOException;

        /**
         * @author      :   Henry Gómez Rocha
         * @change      :   Evita que al momento de pasar del role Calificador al role
         *                  Digitador se verifique el usuario que tiene el bloqueo.
         * Caso Mantis  :   0004967
         */
	public List getFoliosHijos(FolioPk oid, Usuario usuario, boolean validarTurno) throws DAOException;
	
	/**
	 * Retorna la lista con los IDs (Folio.ID) de los folios hijos del folio especificado
	 * tanto definitivos como temporales
	 * Si el folio no tiene hijos o no existe retorna una lista vacía
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public List getFoliosHijosOrdenAnotacion(FolioPk oid, Usuario usuario) throws DAOException;


	/**
	 * Retorna la lista con los IDs (Folio.ID) de los folios padre del folio especificado
	 * tanto definitivos como temporales
	 * Si el folio no tiene padres o no existe retorna una lista vacía
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public List getFoliosPadre(FolioPk oid, Usuario usuario) throws DAOException;


         /**
         * @author      :   Henry Gómez Rocha
         * @change      :   Evita que al momento de pasar del role Calificador al role
         *                  Digitador se verifique el usuario que tiene el bloqueo.
         * Caso Mantis  :   0004967
         */
	public List getFoliosPadre(FolioPk oid, Usuario usuario, boolean validarTurno) throws DAOException;


	/**
	 * Obtiene la zona registral de un folio dada la matricula
	 * @param matricula
	 * @return
	 * @throws DAOException
	 */
	public String getZonaRegistral(String matricula)
		throws DAOException;


	/**
	 * Obtiene una oficina origen por ID
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public OficinaOrigen getOficinaOrigen(OficinaOrigenPk oid)
		throws DAOException;


	/**
	 * Retorna la lista de las anotaciones que tienen salvedades en un folio
	 * tanto definitivos como temporales
	 * Si el folio no tiene anotaciones con salvedades retorna una lista vacia
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public List getAnotacionesConSalvedades(FolioPk oid, Usuario usuario)
		throws DAOException;

         /**
         * @author      :   Henry Gómez Rocha
         * @change      :   Evita que al momento de pasar del role Calificador al role
         *                  Digitador se verifique el usuario que tiene el bloqueo.
         * Caso Mantis  :   0004967
         */
	public List getAnotacionesConSalvedades(FolioPk oid, Usuario usuario, boolean validarTurno)
		throws DAOException;

	/**
	 * Retorna la lista de las anotaciones que tienen salvedades en un folio
	 * Si el folio no tiene anotaciones con salvedades retorna una lista vacia
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public List getAnotacionesConSalvedades(FolioPk oid)
		throws DAOException;


	/**
	 * Retorna la lista de las anotaciones que tienen cancelaciones en un folio
	 * tanto definitivos como temporales
	 * Si el folio no tiene anotaciones con salvedades retorna una lista vacia
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public List getAnotacionesConCancelaciones(FolioPk oid, Usuario usuario)
		throws DAOException;

         /**
         * @author      :   Henry Gómez Rocha
         * @change      :   Evita que al momento de pasar del role Calificador al role
         *                  Digitador se verifique el usuario que tiene el bloqueo.
         * Caso Mantis  :   0004967
         */
	public List getAnotacionesConCancelaciones(FolioPk oid, Usuario usuario, boolean validarTurno)
		throws DAOException;
        
	/**
	 * Retorna la lista de las anotaciones que tienen cancelaciones en un folio
	 * Si el folio no tiene anotaciones con cancelaciones retorna una lista vacia
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public List getAnotacionesConCancelaciones(FolioPk oid)
		throws DAOException;


	/**
	 * Retorna la lista de las anotaciones inválidas en un folio
	 * tanto definitivos como temporales
	 * Si el folio no tiene anotaciones inválidas retorna una lista vacia
	 * @param oid
	 * @return
	 * @throws Throwable
	 */
	public List getAnotacionesInvalidas(FolioPk oid, Usuario usuario)
		throws DAOException;

         /**
         * @author      :   Henry Gómez Rocha
         * @change      :   Evita que al momento de pasar del role Calificador al role
         *                  Digitador se verifique el usuario que tiene el bloqueo.
         * Caso Mantis  :   0004967
         */
	public List getAnotacionesInvalidas(FolioPk oid, Usuario usuario, boolean validarTurno)
		throws DAOException;

	/**
	 * Retorna la lista de las anotaciones inválidas en un folio
	 * Si el folio no tiene anotaciones inválidas retorna una lista vacia
	 * @param oid
	 * @return
	 * @throws Throwable
	 */
	public List getAnotacionesInvalidas(FolioPk oid)
		throws DAOException;


	/**
	 * Retorna la lista con las anotaciones de los folios hijos del folio especificado,
	 * cada anotacion tiene el objeto folio asociado y la última direccion en la lista
	 * de direcciones de este folio
	 * Si el folio no tiene hijos o no existe retorna una lista vacía
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public List getFolioHijosEnAnotacionesConDireccion(FolioPk oid) throws DAOException;

	/**
	 * Retorna la lista con las anotaciones de los folios hijos del folio especificado,
	 * cada anotacion tiene el objeto folio asociado y la última direccion en la lista
	 * de direcciones de este folio ordenado por la anotacion
	 * Si el folio no tiene hijos o no existe retorna una lista vacía
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public List getFolioHijosEnAnotacionesConDireccionOrdenadoAnotaciones(FolioPk oid) throws DAOException;

	
	/**
	 * Obtiene el número actual de anotaciones temporales del folio, el método
	 * debe recibir el usuario que tiene bloqueado el folio
	 * @param oid
	 * @param criterio
	 * @param valor
	 * @return
	 * @throws DAOException
	 */
	public long getCountAnotacionesTMPFolio(FolioPk oid, Usuario usuario) throws DAOException;

         /**
         * @author      :   Henry Gómez Rocha
         * @change      :   Evita que al momento de pasar del role Calificador al role
         *                  Digitador se verifique el usuario que tiene el bloqueo.
         * Caso Mantis  :   0004967
         */
	public long getCountAnotacionesTMPFolio(FolioPk oid, Usuario usuario, boolean validarTurno) throws DAOException;

	/**
	 * Obtiene el siguiente orden de anotación con base en el tamaño
	 * de las anotaciones definitivas y temporales
	 * @param fid
	 * @return
	 * @throws DAOException
	 */
	public long getNextOrdenAnotacion(FolioPk fid)
		throws DAOException;


	/**
	 * Retorna la información temporal del folio que se encuentra lista
	 * para hacerse definitiva. En los datos del folio retornado
	 * sólo van los cambios que se están aplicando:
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
	 * anotaciones definitivas a las cuales se está aplicando
	 * algún cambio:
	 *
	 * Si el flag de anotacion temporal es true, la anotación está
	 * siendo insertada, todos los datos de esta anotación son nuevos.
	 *
	 * Si el flag de anotación temporal es false, la anotación corresponde
	 * a los cambios de una anotación definitiva, es decir, los campos
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
	 * está marcada para eliminación
	 * Si el flag toDelete es false, la anotacionCiudadano correspondiente
	 * está marcada para inserción
	 *
	 * Lista de salvedades de anotación:
	 * Si el flag toDelete es true, la salvedad correspondiente
	 * está marcada para eliminación
	 * Si el flag toDelete es false, la salvedad correspondiente
	 * está marcada para inserción o actualización. Si el idSalvedad
	 * está seteado la salvedad es una actualización de una definitiva,
	 * si el idSalvedad no está seteado, es una nueva salvedad de la anotación
	 *
	 *
	 * @param oid
	 * @param us
	 * @return
	 * @throws DAOException
	 */
	public Folio getDeltaFolio(FolioPk oid, Usuario usuario)
		throws DAOException;



	/**
	 * Cuenta el número total de anotaciones que tiene un folio incluyendo
	 * las anotaciones temporales
	 * @param oid
	 * @param criterio
	 * @param valor
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public long getCountAnotacionesFolio(FolioPk oid, String criterio,
		String valor, Usuario usuario) throws DAOException;


         /**
         * @author      :   Henry Gómez Rocha
         * @change      :   Evita que al momento de pasar del role Calificador al role
         *                  Digitador se verifique el usuario que tiene el bloqueo.
         * Caso Mantis  :   0004967
         */
	public long getCountAnotacionesFolio(FolioPk oid, String criterio,
		String valor, Usuario usuario, boolean validarTurno) throws DAOException;

	/**
	 * Retorna las anotaciones temporales de un folio con el criterio dado.
	 * @param oid
	 * @param criterio
	 * @param valor
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public List getAnotacionesTMPFolioToInsert(FolioPk oid, String criterio,
		String valor, Usuario usuario)
		throws DAOException;

         /**
         * @author      :   Henry Gómez Rocha
         * @change      :   Evita que al momento de pasar del role Calificador al role
         *                  Digitador se verifique el usuario que tiene el bloqueo.
         * Caso Mantis  :   0004967
         */
	public List getAnotacionesTMPFolioToInsert(FolioPk oid, String criterio,
		String valor, Usuario usuario, boolean validarTurno)
		throws DAOException;

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
		throws DAOException;

        /**
         * @author      :   Henry Gómez Rocha
         * @change      :   Evita que al momento de pasar del role Calificador al role
         *                  Digitador se verifique el usuario que tiene el bloqueo.
         * Caso Mantis  :   0004967
         */
	public List getAnotacionesFolio(FolioPk oid, String criterio,
		String valor, int posicionInicial, int cantidad, Usuario usuario, boolean vigente, boolean validarTurno)
		throws DAOException;

	/**
	 * Obtiene los datos definitivos de los datos temporales que estén modificando
	 * al folio
	 * @param oid
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public Folio getDatosDefinitivosDeDatosTemporales(FolioPk oid, Usuario usuario)
		throws DAOException;



	/**
	 * Retorna una lista de objetos SolicitudFolio con los folios que tengan
	 * algún cambio temporal a ser aplicado dentro del turno especificado.
	 * Cada Folio retornado tiene los deltas que están pendientes por aplicar,
	 * si algún folio no se encuentra bloqueado por el usuario especificado
	 * se lanza una excepcion, si ningún folio tiene deltas retorna una lista vacía
	 * @param oid
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public List getDeltaFolios(TurnoPk oid, Usuario usuario)
		throws DAOException;


	/**
	* Obtiene un objeto Folio dado su identificador
	* @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
	* @return Objeto Folio con sus objetos estado, complementacion, tipoPredio y ZonaRegistral,
	* y listas de salvedades folio y direcciones. No se incluyen las anotaciones
	* El objeto ZonaRegistral contiene la jerarquía circulo, vereda, municipio y departamento
	* null si  no encuentra un folio que coincida con el identificador dado
	* @throws DAOException
	*/
	public Folio getFolioByIDSinAnotaciones(FolioPk oid, Usuario usuario)
		throws DAOException;


         /**
         * @author      :   Henry Gómez Rocha
         * @change      :   Evita que al momento de pasar del role Calificador al role
         *                  Digitador se verifique el usuario que tiene el bloqueo.
         * Caso Mantis  :   0004967
         */
	public Folio getFolioByIDSinAnotaciones(FolioPk oid, Usuario usuario, boolean validarTurno)
		throws DAOException;

	/**
	* Servicio utilizado para establecer si el folio con el número
	* de matrícula posee alguna deuda
	* @param matricula
	* @return true: folio debe dinero, false: folio libre de deudas
	* @throws ForsetiException
	*/
	public boolean tieneDeudaFolio(String matricula) throws DAOException;



	/**
	* Servicio utilizado para establecer si el folio con el número
	* de matrícula se encuentra en trámite. Si retorna null el folio NO está en trámite,
	* si returna un objeto Turno el folio se encuentra en trámite por el turno dado
	* @param matricula
	* @return Turno
	* @throws DAOException
	*/
	public Turno getTurnoTramiteFolio(String matricula) throws DAOException;

        
        /**
	* Servicio utilizado para establecer si el folio con el número
	* de matrícula se encuentra en trámite. Si retorna null el folio NO está en trámite,
	* si returna una lista de Turnos el folio se encuentra en trámite por el turno dado
	* @param matricula
	* @return Turno
	* @throws DAOException
	*/
	public List getTurnosTramiteFolio(String matricula) throws DAOException;
        

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
	public Usuario getBloqueoFolio(FolioPk fid)
		throws DAOException;


	/**
	* Servicio utilizado para establecer si el folio con el número
	* de matrícula posee alguna deuda. Si No tiene deuda retorna null,
	* si tiene deuda retorna un Turno, con una solicitud, y con la liquidación
	* la cual NO tiene registrado el pago
	*
	* @param matricula
	* @return Turno
	* @throws DAOException
	*/
	public Turno getTurnoDeudaFolio(FolioPk fid) throws DAOException;



	/**
	 * Obtiene la plantilla de pertenencia asociada a la respuesta especificada
	 * @param respuesta
	 * @return
	 * @throws DAOException
	 */
	public PlantillaPertenencia getPlantillaPertenenciaByRespuesta(String respuesta)
		throws DAOException;

	/**
	 * Actualiza la despcripción de un oficio de pertenencia dada su respuesta
	 * @param respuesta
	 * @return
	 * @throws DAOException
	 */
	public boolean updatePlantillaPertenenciaByRespuesta(String respuesta, String nuevaDescripcion)
		throws DAOException;
	
	
	/**
	 * Actualizar la descripción de una complementación cuando esta tiene conflictos con la 
	 * complementación del sistema FOLIO.
	 * @param complementacion
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public boolean updateComplementacionConflictiva(Complementacion complementacion, Usuario usuario)
		throws DAOException;


	/**
	 * Obtiene la lista de las platilla de pertenencia configuradas
	 * en el sistema
	 * @return
	 * @throws DAOException
	 */
	public List getPlantillaPertenencias() throws DAOException;

	/**
	 * Retorna el turno asociado a la solicitud, si no existe el turno
	 * retorna null
	 * @param sol
	 * @return
	 * @throws DAOException
	 */
	public Turno getTurnoBySolicitud(SolicitudPk sol) throws DAOException;


	/**
	 * Actualiza los datos de una naturaleza jurídica.
	 * @param cid
	 * @param dato
	 * @param Usuario que modifica la naturaleza juridica
	 * @return
	 * @throws DAOException
	 */
	public boolean updateNaturalezaJuridica(NaturalezaJuridica naturaleza, Usuario usuario)
		throws DAOException;


	/**
	 * Copia la anotación indicada con el ID idAnotacion (definitiva o temporal) al
	 * folio indicado con el ID idFolioDestino. El usuario debe tener el bloqueo del
	 * folio.
	 * CONDICIONES DEPENDIENDO DE LA ANOTACION A COPIAR:
	 * Cancelada: Se copia junto con su canceladora
	 * Canceladora: No se copia (Se lanza excepción, en el la lista de errores del DAOException)
	 * Derivada o generadora: Solo copia la anotación, sin relaciones a otros folios
	 * Normal: Se copia normalmente
	 * @param idAnotacion
	 * @param foliosID lista de objetos Folio.ID
	 * @param usuario Usuario que esta realizando la copia de anotaciones
	 * @param copiarComentario Booleano que determina si se debe copiar o no el comentario de la anotación origen
	 * @return la anotación generada
	 * @throws DAOException
	 */
	public boolean copiarAnotacion(AnotacionPk idAnotacion,List foliosID, Usuario usuario, boolean copiarComentario )
		throws DAOException;



	/**
	 * Obtiene una anotación definitiva o temporal por el orden. Obtiene los datos básicos de
	 * la anotación sin dependencias.
	 * @param oid
	 * @param orden
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public Anotacion getAnotacionByOrden(FolioPk oid, String orden, Usuario usuario)  throws DAOException;


	/**
	 * Obtiene una lista de anotaciones temporales a partir de dos rangos por el orden.
	 * Obtiene los datos básicos de la anotación sin dependencias.
	 * @param oid
	 * @param ordenInicial
	 * @param ordenFinal
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public List getAnotacionesTemporalesByRangoOrden(FolioPk oid, String ordenInicial, String ordenFinal, Usuario usuario)  throws DAOException;



	/**
	 * Copia una anotación canceladora a otros folios, cada folio debe tener un objeto anotación con el
	 * identificador de la anotación que se quiere cancelar
	 * @param idAnotacion
	 * @param folios
	 * @param usuario Usuario que esta realizando la copia de anotaciones
	 * @param copiarComentario Booleano que determina si se debe copiar o no el comentario de la anotación origen
	 * @return
	 * @throws DAOException
	 */
	public boolean copiarAnotacionCanceladora(AnotacionPk idAnotacion, List folios, Usuario usuario, boolean copiarComentario ) throws DAOException;



	/**
	 * Devuelve una hashtable en donde para cada matrícula (key)
	 * especifica si tiene o no nuevas anotaciones temporales Boolean (valor)
	 * @param turnoID
	 * @return
	 * @throws DAOException
	 */
	public Hashtable validarNuevasAnotacionesTurno(TurnoPk turnoID) throws DAOException;


	/**
	 * Indica si un folio se encuentra asociado a un turno activo que se encuentre en calificación
	 * @param folioID
	 * @return
	 * @throws DAOException
	 */
	public boolean isFolioInTurnoCalificacion(FolioPk folioID)throws DAOException;


	/**
	* Deshace los cambios que están siendo aplicados a los folios del turno, borra toda la información temporal
	* @param datos
	* @param usuario
	* @return
	* @throws DAOException
	*/
	public boolean deshacerCambiosTurno(TurnoPk turnoID, Usuario usuario) throws DAOException;


	/**
	 * Actualiza una oficina origen con TODOS los datos que llegan el en objeto.
	 * El objeto debe tener su identificador.
	 * @param oficina
	 * @return
	 * @throws DAOException
	 */
	public boolean updateOficinaOrigen(OficinaOrigen oficina) throws DAOException;


	/**
	 * Indica si un turno tiene datos temporales realizados por el turno indicado
	 * @param turnoID
	 * @param folioID
	 * @return true: El turno está realizando cambios sobre el folio
	 *         false: El turno NO está realizando cambios sobre el folio
	 * @throws DAOException
	 */
	public boolean hasDatosTemporalesTurno(TurnoPk turnoID, FolioPk folioID)throws DAOException;
	
	/**
	 * Indica si un folio tiene datos temporales 
	 * @param turnoID
	 * @param folioID
	 * @return true: El folio tiene información temporal
	 *         false: El folio NO tiene inforamción temporal
	 * @throws DAOException
	 */
	public boolean hasDatosTemporalesFolio(FolioPk folioID)throws DAOException;
	

	/**
	 * Indica si el folio se encuentra bloqueado en el turno
	 * @param turnoID
	 * @param folioID
	 * @returntrue: true: El folio es bloqueado por el turno
	 *         		false: El folio NO esta bloqueadopor el turno
	 */
	public boolean isBloqueadoByTurno(TurnoPk turnoID, FolioPk folioID) throws DAOException;

	/**
	* Especifica el estado de un folio, si el folio no existe retorna null
	* @param matricula
	* @return EstadoFolio
	* @throws DAOException
	*/
	public EstadoFolio getEstadoFolioByMatricula(String matricula, Usuario usuario)throws DAOException;

	/**
	 * Elimina un folio temporal dado su ID, y concepto de eliminacion. No se valida el bloqueo del folio
	 * @param folioID
	 * @return
	 * @throws DAOException
	 */
	public boolean deleteFolio(FolioPk folioID, String concepto) throws DAOException;

	/**
	 * Elimina un folio temporal dado su ID, el folio es desasociado del turno al que se encuentre asociado
	 * @param folioID
	 * @return
	 * @throws DAOException
	 */
	public boolean deleteFolio(FolioPk folioID, Usuario usuario) throws DAOException;



	/**
	 * Obtiene las anotaciones agrupadas por folio que fueron agregadas en un turno específico
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public List getCalificacionTurno(TurnoPk oid) throws DAOException;

	/**
	 * Delega el bloqueo de un folio dentro de un turno al usuario especificado
	 * @param oid
	 * @param usuario
	 * @param fid
	 * @return
	 * @throws DAOException
	 */
	public LlaveBloqueo delegarBloqueoFolio(TurnoPk oid, Usuario usuario, FolioPk fid) throws DAOException;



	/**
	* Especifica si existe una matrícula está marcada como inconsistente
	* @param matricula
	* @return true: inconsistente, false: No inconsistente
	* @throws DAOException
	*/
	public boolean inconsistenteMatricula(String matricula) throws DAOException;


	/**
	 * Devuelve un listado de anotaciones en las cuales
	 * el ciudadano se encuentra relacionado
	 * @return List<Anotacion>
	 * */
	public List getAnotacionesQueRelacionanCiudadano( CiudadanoPk oid ) throws DAOException;


	/**
	 * Devuelve un listado de los ciudadanos que estan relacionados a un folio
	 * @return List<Ciudadanos>
	 * */
	public List getCiudadanoUltimosFolio( String idMatricula ) throws DAOException;
		
	/**
	 * Indica si el ciudadano se encuentra asociado a una anotación definitiva
	 * @param folio
	 * @param pm
	 * @return
	 */
	public boolean isCiudadanoInAnotacionDefinitiva(CiudadanoPk ciudID) throws DAOException;

    /**
     * @param matricula
     * @return
     */
    public boolean trasladadoMatricula(String matricula) throws DAOException;


   /**
    * Revisar si las anotaciones con cambios temporales
    * dentro de un folio dado como parametro tienen
    * orden repetido
    * @param matricula
    * @return
    */
   public boolean validarFolioTieneAnotacionesconOrdenRepetido( FolioPk folioId ) throws DAOException;

	/**
	 * @param oficinaID
	 * @return
	 */
	public boolean eliminarOficinaOrigen(OficinaOrigenPk oficinaID) throws DAOException;
   
	/**
    * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#countSalvedadesFolio(java.lang.String, java.lang.String, gov.sir.core.negocio.modelo.Usuario)
   */
   public long countSalvedadesFolio( String idMatricula, String idWorkflow, Usuario usuario ) throws DAOException;

   /**
    * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#countSalvedadesAnotacion(java.lang.String, java.lang.String, gov.sir.core.negocio.modelo.Usuario)
   */
   public long countSalvedadesAnotacion( String idMatricula, String idWorkflow, Usuario usuario ) throws DAOException;

   /**
    * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getOficinasOrigenByMunicipio( gov.sir.core.negocio.modelo.Municipio.MunicipioPk )
   */
   public List getOficinasOrigenByMunicipio(MunicipioPk oid) throws DAOException;

   /**
    * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacionById( gov.sir.core.negocio.modelo.Anotacion.AnotacionPk )
   */
  public gov.sir.core.negocio.modelo.Anotacion getAnotacionById( AnotacionPk oid )  throws DAOException;
  
  /**
   * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#getAnotacion( gov.sir.core.negocio.modelo.Anotacion.AnotacionPk )
  */
  public gov.sir.core.negocio.modelo.Anotacion getAnotacion( AnotacionPk oid )  throws DAOException;

	/**
    * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarDeltaSegunAnotacionDefinitiva( gov.sir.core.negocio.modelo.Folio, gov.sir.core.negocio.modelo.Anotacion, gov.sir.core.negocio.modelo.Usuario )
    */
  
  
   /**
   * Valida el principio de prioridad según Incidencia 5063
   * Si los turnos van a salir de firma y existen otros turnos anteriores (con fechaInicio anterior)
   * que no hayan salido de firma y comparten algún folio se genera una excepcion
   * @param oid
   * @throws DAOException
   */
   public void validarPrincipioPrioridadFirma(List turnos) throws DAOException;
   
   /**
    * Valida el principio de prioridad Devolucion
    * Si los turnos van a ser devueltos y existen otros turnos posteriores
    * que no hayan salido de firma y comparten algún folio se genera una excepcion
    * @param oid
    * @throws DAOException
    */
   public void validarPrincipioPrioridadDevolucion(List turnos) throws DAOException;
   
   /**
    * Valida el principio de prioridad
    * Si los turnos van a salir de firma y existen otros turnos anteriores (con fechaInicio anterior)
    * que no hayan salido de firma y comparten algún folio los agrega a una lista
    * @param oid
    * @throws DAOException
    */
    public Hashtable validarPrincipioPrioridadFirmaRelacion(List turnos) throws DAOException;
   /**
    * Valida el principio de prioridad 
    * Si los turnos van a salir de firma y existen otros turnos anteriores (con fechaInicio anterior)
    * que no hayan salido de firma y comparten algún folio se genera una excepcion
    * @param oid
    * @throws DAOException
    */
    public boolean validarTurnoFirmaPrincipioPrioridad(List turnos) throws DAOException;
    
   /**
    * Valida la matrícula del folio a crear
    * @param datos
    * @throws DAOException
    */
   public void validarMatriculaCrearFolio(Folio datos) throws DAOException;

   /**
    * Valida la prohibicion de enajencion de una lista de anotaciones ciudadano
    * @param anotaciones
    * @throws DAOException
    */
    public boolean validarAnotacionesCiudadano(List anotacionesCiudadano)throws DAOException;
   
	/**
	* @param folio
	* @param usuario
	* @return
	* @throws DAOException
	*/
	public boolean updateFolioCreacionDirecta(Folio folio, Usuario usuario) throws DAOException;
	
	/**
     * Metodo para actualizar una anotacion en Creacion Directa
     * @param anotacion
     * @param usuario
     * @return
     * @throws DAOException
     */
     public boolean updateAnotacionesCreacionDirecta(Anotacion anotacion, Usuario usuario)  throws DAOException;
     
	/**
	* @param oid
	* @param usuario
	* @return
	* @throws DAOException
	*/
	public List getFoliosAnotaActNomenclaturaSinActualizarBySolicitud(SolicitudPk oid,Usuario usuario) throws DAOException;
	
	public boolean isUltimoPropietario(AnotacionCiudadano anota) throws DAOException;
	
	public List getTurnosCorreccionActivosFolio(Folio fol, Turno turnoNoValidar) throws DAOException;
	
	public void eliminarFolioCreacionDirecta(Folio datos, Usuario usuario) throws DAOException;
	
	public boolean desbloquearFoliosEntradaCalificacion(List lstMatricula, Usuario usuario) throws DAOException;
	
	/**
	 * Actualiza folio derivado
	 * @param folioDerivado
	 * @throws DAOException
	 */
	public void updateFolioDerivado(List foliosDerivadosModificados,Folio folioPadre, Usuario usuario) throws DAOException;

/*Adiciona Funcionalidad Boton de Pago
 * Author: Ingeniero Diego Hernandez
 * Modificacion en: 2010/02/23 by jvenegas
 */

    public List getAlertasMatriculas(String idMatricula) throws DAOException;

    /**
     * @author      :   Henry Gómez Rocha y Fernando Padilla
     * @change      :   Evita que al momento de pasar del role Calificador al role
     *                  Digitador se verifique el usuario que tiene el bloqueo.
     * Caso Mantis  :   0004967
     */
    public Folio getFolioByID(FolioPk oid, Usuario usuario, boolean validarBloqueo) throws DAOException;
 /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 11767.
         * @actaReq    : Requerimiento No 089_151_Proceso_correcciones_permitir_varias_correcciones.
         * @change     : Retorna Anotacion temporar.
	 * @param      : oid.
	 * @param      : idanotacion.
	 * @throws     : DAOException.
	 */
   public Anotacion getAnotacionTMP(FolioPk oid, String idanotacion) throws DAOException;

    
    /**
     * @Autor: Edgar Lora
     * @Mantis: 13176
     */
    public NaturalezaJuridicaEnhanced getNaturalezaJuridicaById(NaturalezaJuridicaEnhancedPk pk) throws DAOException;
	
	        /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change     : Definicion del metodo
	 */
    public List getGruposNaturalezaJuridicaAll() throws DAOException;
    
     /**
     * * @author Carlos Torres
     * @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
     * Obtiene una oficina origen por ID
     * @param oid
     * @return
     * @throws DAOException
     */
    public OficinaOrigen getOficinaOrigen(String idOficina) throws DAOException;

}

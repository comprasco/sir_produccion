/*
 * Created on 20-oct-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.forseti.interfaz;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import gov.sir.core.negocio.modelo.ActoresRepartoNotarial;
import gov.sir.core.negocio.modelo.ActoresRepartoNotarialPk;
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
import gov.sir.core.negocio.modelo.Prohibicion;
import gov.sir.core.negocio.modelo.ProhibicionPk;
import gov.sir.core.negocio.modelo.SalvedadAnotacion;
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
import gov.sir.core.negocio.modelo.UsuarioPk;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.VeredaPk;
import gov.sir.core.negocio.modelo.ZonaRegistral;
import gov.sir.core.negocio.modelo.ZonaRegistralPk;
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

import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.dao.DAOException;


/**
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface ForsetiServiceInterface {
    public Folio getFolioByID(FolioPk oid) throws Throwable;
    public Folio getFolioByID(String matricula) throws Throwable;
    public Folio getFolioByMatricula(String matricula) throws Throwable;
    public List getAnotacionesFolio(FolioPk oid) throws Throwable;
    public AnotacionTMP getAnotacionTMPByIDSinPersitence(AnotacionTMPPk oid) throws ForsetiException;
    public List getAnotacionesFolioTMP(FolioPk oid) throws Throwable;
    public List getAnotacionesFolioTMPFD(FolioPk oid) throws Throwable;
    public int getAnotacionesFolioTMPCount(FolioPk oid) throws Throwable;
    public EstadoFolioPk addEstadoFolio(EstadoFolio datos, Usuario usuario) throws Throwable;
    public DepartamentoPk addDepartamento(Departamento datos, Usuario usuario) throws Throwable;
    public Departamento getDepartamento(DepartamentoPk oid) throws Throwable;
    public MunicipioPk addMunicipioToDepartamento(Municipio datos, DepartamentoPk oid, Usuario usuario) throws Throwable;
    public boolean existeFolio(String matricula) throws Throwable;
    public boolean existeFolioIncluyendoTemporales(String matricula) throws Throwable;
    public boolean bloqueadoFolio(String matricula) throws Throwable;
    public boolean isActuacionAdministrativa(String idMatricula) throws Throwable;
    public boolean estaCiudadanoEnAnotacionCiudadano(String idCiudadano) throws Throwable;
    public boolean enTramiteFolio(String matricula) throws Throwable;
    public boolean enCustodiaFolio(String matricula) throws Throwable;
    public boolean actualizarFolioDatosTMP(String matricula, FolioDatosTMP datos, boolean eliminarDireccion) throws Throwable;
    public boolean tieneDeudaFolio(String matricula) throws Throwable;
    public boolean mayorExtensionFolio(String matricula) throws Throwable;
    public CirculoPk addCirculo(Circulo datos, Usuario usuario) throws Throwable;
    public Folio crearFolio(Folio datos, Usuario us, TurnoPk oid, boolean validarTurno) throws Throwable;
    public Folio crearFolioSegeng(Folio datos, Usuario us, TurnoPk oid, boolean validarTurno, Folio folioB) throws Throwable;
    public boolean validarActualizacionCiudadanoTurno(Turno turno) throws Throwable;
    public List getFolioDerivePadre(String IdMatricula,String IdAnotacion) throws Throwable;
    public List getTurnsShareFolioNotaDev(String idMatricula) throws Throwable;
    public List validarPrincipioPrioridadNotNotaDev(String idMatricula) throws Throwable;
    public List validarPrincipioPrioridadNotNotaNot(String idMatricula) throws Throwable;
    public List validarPrincipioPrioridadRecNot(String idMatricula) throws Throwable;
    public List getBloqueoFolios(LlaveBloqueoPk oid) throws Throwable;
    public List getDireccionesFolio(FolioPk oid) throws Throwable;
    public List getSalvedadesFolio(FolioPk oid) throws Throwable;
    public Documento getDocumentoAnotacion(AnotacionPk oid) throws Throwable;
    public List getSalvedadesAnotacion(AnotacionPk oid) throws Throwable;
    public List getAnotacionesCiudadano(AnotacionPk oid) throws Throwable;
    public List getAnotacionesHijas(AnotacionPk oid) throws Throwable;
    public List getAnotacionesPadre(AnotacionPk oid) throws Throwable;
    public DireccionPk addDireccionToFolio(FolioPk oid, Direccion datos) throws Throwable;
    public Direccion getUltimaDireccion(FolioPk oid) throws Throwable;
    public DocumentoPk setDocumentoToAnotacion(AnotacionPk oid, Documento datos) throws Throwable;
    public AnotacionPk addAnotacionToFolio(FolioPk oid, Anotacion datos) throws Throwable;
    public List getDepartamentos(CirculoPk oid) throws Throwable;
    public List getEjes() throws Throwable;
    public List getTiposPredio() throws Throwable;
    public Busqueda atenderConsulta(BusquedaPk bid) throws Throwable;
    public Busqueda atenderConsultaAdministracion(Busqueda busqueda) throws Throwable;
    public List getTiposDocumento() throws Throwable;
    public List getGruposNaturalezaJuridica() throws Throwable;
    public List getOficinasOrigenByVereda(VeredaPk oid) throws Throwable;
    public Hashtable getTiposOficinaByVereda(VeredaPk vid) throws Throwable;
    public List getEstadosFolio() throws Throwable;
    public boolean validarMatriculas(List validaciones, List matriculas) throws Throwable;
    public void validarCrearFolio(Folio datos, boolean validarTurno ) throws Throwable;
    public void finalizar() throws Throwable;

    public LlaveBloqueo bloquearFolios(List matriculas, Usuario usuario, TurnoPk turnoID) throws Throwable;
    public EstadoAnotacionPk addEstadoAnotacion(EstadoAnotacion datos) throws Throwable;
    public List getEstadosAnotacion() throws Throwable;
    public GrupoNaturalezaJuridicaPk addGrupoNaturalezaJuridica(GrupoNaturalezaJuridica naturaleza) throws Throwable;
    public GrupoNaturalezaJuridica getGrupoNaturalezaJuridica(GrupoNaturalezaJuridicaPk oid) throws Throwable;
    public OficinaOrigenPk addOficinaOrigen(OficinaOrigen oficina) throws Throwable;

    //public boolean desbloquearFolios(List matriculas, Usuario usuario) throws Throwable;

    public boolean desbloquearFolio(Folio folio) throws Throwable;

    public TipoDocumentoPk addTipoDocumento(TipoDocumento datos, Usuario usuario) throws Throwable;

    public TipoOficinaPk addTipoOficina(TipoOficina datos, Usuario usuario) throws Throwable;

    public TipoPredioPk addTipoPredio(TipoPredio datos) throws Throwable;

    public NaturalezaJuridicaPk addNaturalezaJuridicaToGrupo(NaturalezaJuridica datos, GrupoNaturalezaJuridicaPk gid, Usuario usuario) throws Throwable;

    public VeredaPk addVeredaToMunicipio(Vereda ver, MunicipioPk oid) throws Throwable;

    public List getCirculos() throws Throwable;
    
    public List getCirculosFechaProd() throws Throwable;
    
    /**
     * Obtiene una lista de círculos que tienen configurada
     * la firma del  registrador
     * @return lista de objetos Circulo
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws ForsetiException
     */
    public List getCirculosFirmaRegistrador() throws Throwable;
    

    public EjePk addEje(Eje datos, Usuario usuario) throws Throwable;

    /**
    * Actualiza la información de un folio dependiendo de los cambios que vienen
    * en el objeto. El usuario debe tener bloqueado el folio para poder
    * afectar la información de éste, de lo contrario se genera un Throwable.
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
    * actual creando unanueva anotación y dejándola con estado "ACTUALIZADA"
    *
    *
    * @param folio
    * @param usuario
    * @return
    * @throws DAOException
    */
    public boolean updateFolio(Folio folio, Usuario usuario, TurnoPk tid, boolean validarTurno) throws Throwable;
    
    /**
    * @param idMatricula
    * @return
    * @throws DAOException
    */
    public List getHistorialArea(String idMatricula) throws Throwable;
    
    /**
     * @param folio
     * @param usuario
     * @param Turno.ID
     * @param validarTurno
     * @return
     * @throws Throwable
     */
     
    public boolean updateFolioFD(Folio folio, Usuario usuario, TurnoPk tid, boolean validarTurno, List LstAnotaFolioHijo, List LstAnotaFolioPadre, String anotacionUpdate, String matriculaUpdate,List lstHijoRemove) throws Throwable;

    /**
     * Hace definitivos los cambios temporales aplicados al folio, y desbloquea el folio
     * @param folio
     * @param usuario
     * @param Turno.ID
     * @param validarTurno
     * @return
     * @throws Throwable
     */
    public boolean hacerDefinitivoFolio(Folio folio, Usuario usuario, TurnoPk tid, boolean validarTurno) throws Throwable;

    /**
    * Deshace los cambios que están siendo aplicados al folio, borra toda la información temporal. Finalmente
    * desbloquea el folio
    * @param datos
    * @param usuario
    * @return
    * @throws Throwable
    */
    public boolean deshacerCambiosFolio(Folio datos, Usuario usuario) throws Throwable;

    /**
	* Deshace los cambios que están siendo aplicados a los ciudadanos, borra toda la información temporal
	* @param datos
	* @param usuario
	* @return
	* @throws Throwable
	*/

/**
	* Deshace los cambios que están siendo aplicados a los folios del turno, borra toda la información temporal
	* @param turnoID
	* @param usuario
	* @return
	* @throws DAOException
	*/
	public boolean deshacerCambiosFolioByTurno(TurnoPk turnoID, Usuario usuario, boolean desbloquear) throws Throwable;

	public boolean deshacerCambiosCiudadanosTurno(TurnoPk turnoID, Usuario usuario, boolean desbloquear) throws Throwable;
	
    /**
     * Retorna la información del folio dependiendo del usuario solicitante,
     * si el usuario tiene bloqueado el folio se retorna la información definitiva
     * y temporal, si el usuario no tiene el bloqueo del folio se lanza una excepcion
     * indicando lo sucedido
     * @param oid
     * @param us
     * @return
     * @throws ThrowableException
     */
    public Folio getFolioByID(FolioPk oid, Usuario usuario) throws Throwable;

     /**
      * @author      :   Henry Gómez Rocha y Fernando Padilla
      * @change      :   Evita que al momento de pasar del role Calificador al role
      *                  Digitador se verifique el usuario que tiene el bloqueo..
      * Caso Mantis  :   0005307
      * @param oid
      * @param usuario
      * @param validarBloqueo
      * @return
      * @throws ThrowableException
      */
    public Folio getFolioByID(FolioPk oid, Usuario usuario, boolean validarBloqueo) throws Throwable;

    /**
     * Retorna la información del folio dependiendo del usuario solicitante,
     * si el usuario tiene bloqueado el folio se retorna la información definitiva
     * y temporal, si el usuario no tiene el bloqueo del folio se lanza una excepcion
     * indicando lo sucedido. Evalua si el folio es definitivo
     * @param oid
     * @param us
     * @return
     * @throws ThrowableException
     */
    public Folio loadFolioByID(FolioPk oid, Usuario usuario) throws Throwable;
    
    
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
     * @throws Throwable
     */
    public List segregarFolio(Folio datos, List foliosDerivados, Usuario usuario, Anotacion nuevaAnotacion, boolean copiarComentarioSegregacion, boolean copiarComentarioHeredadas, TurnoPk oid) throws Throwable;
    
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
    public List segregarFolioPLSQL(Folio datos, TurnoPk oid, SalvedadAnotacion salvedad, String IdAnotacionDef) throws Throwable;

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
    public Folio englobarFolio(List foliosFuentes, Folio folio, Usuario usuario, List tabla, Anotacion anotacion, FolioDerivado informacionAdicional, boolean copiarComentarioEnglobe, boolean copiarComentarioHeredadas, TurnoPk oid) throws Throwable;

    /**
     * Delega el bloqueo de todos los folios asociados al turno
     * Si algún folio no está bloqueado se genera una excepcion guardada
     * en un hashtable donde la llave es el número de matrícula y el valor
     * es la descripción del error
     * @param oid
     * @param usuario
     * @return
     * @throws Throwable
     */
    public LlaveBloqueo delegarBloqueoFolios(TurnoPk oid, Usuario usuario) throws Throwable;
    
    /**
     * Delega el bloqueo de todos los folios asociados al turno
     * @param oid
     * @param usuario
     * @return
     * @throws Throwable
     */
    public LlaveBloqueo delegarBloqueoFoliosObligatorio(TurnoPk oid, Usuario usuario) throws Throwable;

    /**
     * Determina si una matricula tiene por lo menos una anotación temporal
     * @param matricula
     * @return
     * @throws Throwable
     */
    public boolean hasAnotacionesTMP(FolioPk oid) throws Throwable;

/*
     * Bloquea los todos los folios asociados a un turno
     * Si algún folio está bloqueado se genera una excepcion guardada
     * en un hashtable donde la llave es el número de matrícula y el valor
     * es la descripción del error
     * @param oid
     * @param usuario
     * @return
     * @throws Throwable
    public LlaveBloqueo bloquearFolios(Turno.ID oid, Usuario usuario) throws Throwable;



     * Desbloquea todos los folios asociados a un turno, si en el turno existe algún folio que no
     * esté bloqueado se produce una excepción
     * @param oid
     * @param usuario
     * @return
     * @throws Throwable
     *
     *
    public boolean desbloquearFolios(Turno.ID oid, Usuario usuario) throws Throwable;
*/

    /**
     * Desbloquea todos los folios asociados a un turno, si en el turno existe algún folio que no
     * esté bloqueado se produce una excepción no valida que el bloqueo lo tenga un usuario.
     * @param oid
     * @param usuario
     * @return
     * @throws DAOException
     */
	public boolean desbloquearFoliosTurno(TurnoPk oid, Usuario usuario) throws Throwable;

    /**
     * Acualiza la información de las matrículas dadas con los datos
     * del folio.
     * @param datos
     * @param ids Listas de objetos tipo Folio.ID
     * @param usuario
     * @return
     * @throws Throwable
     */
    public boolean updateFolios(Folio datos, List ids, Usuario usuario, boolean validarBloqueo) throws Throwable;

    public DominioNaturalezaJuridica getDominioNaturalezaJuridica(DominioNaturalezaJuridicaPk oid) throws Throwable;

    /**
     * Obtiene la lista de dominios de naturalezas jurídicas
     * @return
     * @throws Throwable
     */
    public List getDominiosNaturalezaJuridica() throws Throwable;

    /**
     * Obtiene una lista con todos los posibles tipos de oficina
     * configurados en el sistema
     * @return lista de objetos TipoOficina
     * @see gov.sir.core.negocio.modelo.TipoOficina
     * @throws DAOException
     */
    public List getTiposOficina() throws Throwable;

    /**
     * Agrega un dominio de naturaleza jurídica
     * @param naturaleza
     * @return
     * @throws Throwable
     */
    public DominioNaturalezaJuridicaPk addDominioNaturalezaJuridica(DominioNaturalezaJuridica naturaleza) throws Throwable;

    /**
     * Adiciona un festivo a un circulo dado
     * @param datos objeto circulofestivo con sus atributos
     * @param oid identificador del circulo
     * @return identificación del circulofestivo generado
     * @throws DAOException
     */
    public CirculoFestivoPk addCirculoFestivo(CirculoPk oid, CirculoFestivo datos) throws Throwable;

    /**
     * Obtiene los festivo configurados en el sistema para un círculo determinado
     * @return lista de objetos tipo CirculoFestivo
     * @see gov.sir.core.negocio.modelo.CirculoFestivo
     * @throws DAOException
     */
    public List getFestivosCirculo(CirculoPk oid) throws Throwable;

    /**
     * Determina si la fecha ingresada esta configurada en el sistema como un
     * festivo para un círculo dado
     * @return boolean
     * @see gov.sir.core.negocio.modelo.CirculoFestivo
     * @throws DAOException
     */
    public boolean isFestivo(Date fecha, CirculoPk oid) throws Throwable;

    /**
    * Obtiene un objeto Folio dado su identificador
    * @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
    * @return Objeto Folio con sus objetos estado, complementacion, tipoPredio y ZonaRegistral,
    * y listas de salvedades folio y direcciones. No se incluyen las anotaciones
    * El objeto ZonaRegistral contiene la jerarquía circulo, vereda, municipio y departamento
    * null si  no encuentra un folio que coincida con el identificador dado
    * @throws DAOException
    */
    public Folio getFolioByIDSinAnotaciones(FolioPk oid) throws Throwable;
    
    /**
     * Obtiene un objeto Folio Temporal dado su identificador
     * @param oid identificador del Folio conformado por Nro matricula.
     * @return Objeto Folio Temporal
     * @throws DAOException
     */
     public FolioDatosTMP getFolioDatosTMP(FolioDatosTMPPk oid) throws Throwable;
    
    
    
    /**
     * Obtiene un objeto Folio dado su identificador
     * @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
     * @return Objeto Folio con sus objetos estado, complementacion, tipoPredio y ZonaRegistral,
     * y listas de salvedades folio y direcciones. No se incluyen las anotaciones
     * El objeto ZonaRegistral contiene la jerarquía circulo, vereda, municipio y departamento
     * null si  no encuentra un folio que coincida con el identificador dado
     * @throws DAOException
     */
     public Folio getFolioEvenTempByIDSinAnotaciones(FolioPk oid) throws Throwable;

    /**
    * Obtiene un objeto Folio dado su identificador
    * @param matricula número de matrícula del folio
    * @return Objeto Folio con sus objetos estado, complementacion, tipoPredio y ZonaRegistral,
    * y listas de salvedades folio y direcciones. No se incluyen las anotaciones
    * El objeto ZonaRegistral contiene la jerarquía circulo, vereda, municipio y departamento
    * null si  no encuentra un folio que coincida con el identificador dado
    * @throws DAOException
    */
    public Folio getFolioByMatriculaSinAnotaciones(String matricula) throws Throwable;

    /**
     *
     * @param oid
     * @param datos
     * @return
     * @throws Throwable
     */
    public ZonaRegistralPk addZonaRegistralToCirculo(CirculoPk oid, ZonaRegistral datos) throws Throwable;

    /**
    * Obtiene las ZonaRegistrales configuradas en el sistema para un círculo determinado
    * @return lista de objetos tipo ZonaRegistral, cada uno tiene sus objetos vereda y circulo
    * @see gov.sir.core.negocio.modelo.ZonaRegistral
    * @throws Throwable
    */
    public List getZonaRegistralesCirculo(CirculoPk oid) throws Throwable;

    /**
    * Calcula el número de anotaciones del folio con el criterio dado. El criterio
    * es tomado del conjunto de constantes de CCriterio
    * @param oid
    * @param criterio
    * @param valor
    * @return
    * @throws DAOException
    */
    public long getCountAnotacionesFolio(FolioPk oid, String criterio, String valor) throws Throwable;

    /**
     * Obtiene un turno de correcciones activo que tenga asociado
     * el folio dado, si no existe un turno de correción activo con
     * el folio asociado retorna null
     * @param oid
     * @return
     * @throws Throwable
     */
    public Turno getTurnoCorreccionActivoFolio(FolioPk oid) throws Throwable;

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
    * @throws Throwable
    */
    public List getAnotacionesFolio(FolioPk oid, String criterio, String valor, int posicionInicial, int cantidad, boolean vigente) throws Throwable;
    
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
    public void validarPrincipioPrioridadDeshacerCambiosTemporales(TurnoPk oid) throws Throwable;
    
    
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
     * @throws Throwable
     */
    public void validarPrincipioPrioridadCalificacion(TurnoPk oid, Usuario usuario) throws Throwable;
    
    /**
     * Valida el principio de prioridad Digitaciono
     * Si el turno va a entrar a Digitación este servicio se puede
     * utilizar para validar si existe otros turnos que tengan fecha
     * de inicio anterior y que esten en esta fase
     * Si hay algún error en la validación se lanzará una excepción
     * en donde la hastable interna tiene como llave la matricula y como
     * valor la lista de turnos que tienen el folio
     * @param oid
     * @throws DAOException
     */
    public void validarPrincipioPrioridadDigitacion(TurnoPk oid, Usuario usuario) throws Throwable;
    
    
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
    public boolean isTurnoValidoCalificacion(TurnoPk oid, Usuario usuario) throws Throwable; 
    
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
    public void isTurnoValidoSalidaCalificacion(TurnoPk oid, Usuario usuario) throws Throwable;  

    
    /**
    * Devuelve el número de anotaciones del folio indicado, si el folio no existe retorna 0
    * @param matricula
    * @return
    * @throws Throwable
    */
    public long getCountAnotacionesFolio(String matricula) throws Throwable;

    /**
     * Añade una firma
     * @param archivo
     * @param oid
     * @return
     * @throws Throwable
     */
    public boolean addFirmaRegistradorToCirculo(FirmaRegistrador firmaReg, CirculoPk oid) throws Throwable;

    /**
    * Obtiene un objeto Circulo dado su identificador
    * @param oid identificador del circulo
    * @return objeto Circulo
    * @throws Throwable
    */
    public Circulo getCirculo(CirculoPk oid) throws Throwable;

    /**
     * Setea el estado de una firma de registrador este puede ser
     * ACTIVA -> la firma esta activa
     * INACTIVA -> la firma esta inactiva, pero puede volverse a activar
     * INACTIVA_DEFINITIVA -> la firma esta inactiva y no se puede volver a activar.
     * @param oid
     * @param activa 0 -> INACTIVA , 1 -> ACTIVA, 2 -> INACTIVA_DEFINITIVA
     * @return
     * @throws Throwable
     */
    public boolean setActivoFirmaRegistrador(FirmaRegistradorPk oid, int activa) throws Throwable;

    /**
     * Valida el principio de prioridad para un turno. Se valida que para cada
     * matrícula relacionada no exista un turno de corrección activo con la misma
     * matrícula asociada. Si algún folio asociado al turno presenta esta situación,
     * se lanza una excepción con la hashtable donde la llave es la matrícula y el
     * valor es la lista de turnos de calificación que tienen el folio
     * @param oid
     * @throws Throwable
     */
    public void validarPrincipioPrioridadCorreccion(TurnoPk oid) throws Throwable;

    /**
     * Retorna la lista con los folios hijos del folio especificado (Folio)
     * Si el folio no tiene hijos o no existe retorna una lista vacía
     * @param oid
     * @return
     * @throws Throwable
     */
    public List getFoliosHijos(FolioPk oid) throws Throwable;
    
    /**
     * Retorna la lista con los folios hijos del folio especificado (Folio)
     * Si el folio no tiene hijos o no existe retorna una lista vacía
     * @param oid
     * @return
     * @throws Throwable
     */
    public List getFoliosHijosOrdenAnotacion(FolioPk oid) throws Throwable;

    /**
     * Retorna la lista con los los folios padre del folio especificado (Folio)
     * Si el folio no tiene padres o no existe retorna una lista vacía
     * @param oid
     * @return
     * @throws Throwable
     */
    public List getFoliosPadre(FolioPk oid) throws Throwable;
    
    /**
     * Retorna la lista con los folios Derivado hijos del folio especificado (Folio)
     * Si el folio no tiene hijos o no existe retorna una lista vacía
     * @param oid
     * @return
     * @throws Throwable
     */
    public List getFoliosDerivadoHijos(FolioPk oid) throws Throwable;

    /**
     * Retorna la lista con los los folios Derivado padre del folio especificado (Folio)
     * Si el folio no tiene padres o no existe retorna una lista vacía
     * @param oid
     * @return
     * @throws Throwable
     */
    public List getFoliosDerivadoPadre(FolioPk oid) throws Throwable;
    
    /**
     * Retorna la lista con los folios Derivado hijos del folio especificado (Folio)
     * Si el folio no tiene hijos o no existe retorna una lista vacía el usuario debe tener el bloqueo
     * @param oid
     * @return
     * @throws Throwable
     */
    public List getFoliosDerivadoHijos(FolioPk oid, Usuario usuario) throws Throwable;

    /**
     * Retorna la lista con los los folios Derivado padre del folio especificado (Folio)
     * Si el folio no tiene padres o no existe retorna una lista vacía el usuario debe tener el bloqueo
     * @param oid
     * @return
     * @throws Throwable
     */
    public List getFoliosDerivadoPadre(FolioPk oid, Usuario usuario) throws Throwable;

    /**
    * Servicio que permite eliminar un objeto <code>Departamento</code>
    * <p>Utilizado desde las pantallas administrativas.
    * @param acto Objeto <code>Departamento</code> que va a ser eliminado.
    * @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
    * resultado de la operación.
    * @throws <code>Throwable</code>
    * @see gov.sir.core.negocio.modelo.Departamento
    */
    public boolean eliminarDepartamento(Departamento dato, Usuario usuario) throws Throwable;

    /**
    * Servicio que permite eliminar un objeto <code>Municipio</code>
    * <p>Utilizado desde las pantallas administrativas.
    * @param acto Objeto <code>Municipio</code> que va a ser eliminado.
    * @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
    * resultado de la operación.
    * @throws <code>Throwable</code>
    * @see gov.sir.core.negocio.modelo.Municipio
    */
    public boolean eliminarMunicipio(Municipio dato) throws Throwable;

    /**
    * Servicio que permite eliminar un objeto <code>Vereda</code>
    * <p>Utilizado desde las pantallas administrativas.
    * @param acto Objeto <code>Vereda</code> que va a ser eliminado.
    * @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
    * resultado de la operación.
    * @throws <code>Throwable</code>
    * @see gov.sir.core.negocio.modelo.Vereda
    */
    public boolean eliminarVereda(Vereda dato) throws Throwable;

    /**
    * Servicio que permite eliminar un objeto <code>Circulo</code>
    * <p>Utilizado desde las pantallas administrativas.
    * @param acto Objeto <code>Circulo</code> que va a ser eliminado.
    * @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
    * resultado de la operación.
    * @throws <code>Throwable</code>
    * @see gov.sir.core.negocio.modelo.Circulo
    */
    public boolean eliminarCirculo(Circulo dato, Usuario usuario) throws Throwable;

    /**
    * Servicio que permite eliminar un objeto <code>ZonaRegistral</code>
    * <p>Utilizado desde las pantallas administrativas.
    * @param acto Objeto <code>ZonaRegistral</code> que va a ser eliminado.
    * @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
    * resultado de la operación.
    * @throws <code>Throwable</code>
    * @see gov.sir.core.negocio.modelo.ZonaRegistral
    */
    public boolean eliminarZonaRegistral(ZonaRegistral dato) throws Throwable;

    /**
    * Servicio que permite eliminar un objeto <code>Eje</code>
    * <p>Utilizado desde las pantallas administrativas.
    * @param acto Objeto <code>Eje</code> que va a ser eliminado.
    * @param usuario que elimina el eje
    * @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
    * resultado de la operación.
    * @throws <code>Throwable</code>
    * @see gov.sir.core.negocio.modelo.Eje
    */
    public boolean eliminarEje(Eje dato, Usuario usuario) throws Throwable;

    /**
    * Servicio que permite eliminar un objeto <code>CirculoFestivo</code>
    * <p>Utilizado desde las pantallas administrativas.
    * @param acto Objeto <code>CirculoFestivo</code> que va a ser eliminado.
    * @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
    * resultado de la operación.
    * @throws <code>Throwable</code>
    * @see gov.sir.core.negocio.modelo.CirculoFestivo
    */
    public boolean eliminarCirculoFestivo(CirculoFestivo dato) throws Throwable;

    /**
    * Servicio que permite eliminar un objeto <code>EstadoFolio</code>
    * <p>Utilizado desde las pantallas administrativas.
    * @param acto Objeto <code>EstadoFolio</code> que va a ser eliminado.
    * @param usuario que elimina el estado del folio
    * @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
    * resultado de la operación.
    * @throws <code>Throwable</code>
    * @see gov.sir.core.negocio.modelo.EstadoFolio
    */
    public boolean eliminarEstadoFolio(EstadoFolio dato, Usuario usuario) throws Throwable;

    /**
    * Servicio que permite eliminar un objeto <code>TipoOficina</code>
    * <p>Utilizado desde las pantallas administrativas.
    * @param acto Objeto <code>TipoOficina</code> que va a ser eliminado.
    * @param Usuario que elimina el tipo de oficina
    * @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
    * resultado de la operación.
    * @throws <code>Throwable</code>
    * @see gov.sir.core.negocio.modelo.TipoOficina
    */
    public boolean eliminarTipoOficina(TipoOficina dato, Usuario usuario) throws Throwable;

    /**
    * Servicio que permite eliminar un objeto <code>TipoDocumento</code>
    * <p>Utilizado desde las pantallas administrativas.
    * @param acto Objeto <code>TipoDocumento</code> que va a ser eliminado.
    * @param Usuario que elimina el tipo de documento
    * @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
    * resultado de la operación.
    * @throws <code>Throwable</code>
    * @see gov.sir.core.negocio.modelo.TipoDocumento
    */
    public boolean eliminarTipoDocumento(TipoDocumento dato, Usuario usuario) throws Throwable;

    /**
    * Servicio que permite eliminar un objeto <code>TipoPredio</code>
    * <p>Utilizado desde las pantallas administrativas.
    * @param acto Objeto <code>TipoPredio</code> que va a ser eliminado.
    * @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
    * resultado de la operación.
    * @throws <code>Throwable</code>
    * @see gov.sir.core.negocio.modelo.TipoPredio
    */
    public boolean eliminarTipoPredio(TipoPredio dato) throws Throwable;

    /**
    * Servicio que permite eliminar un objeto <code>GrupoNaturalezaJuridica</code>
    * <p>Utilizado desde las pantallas administrativas.
    * @param acto Objeto <code>GrupoNaturalezaJuridica</code> que va a ser eliminado.
    * @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
    * resultado de la operación.
    * @throws <code>Throwable</code>
    * @see gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica
    */
    public boolean eliminarGrupoNaturalezaJuridica(GrupoNaturalezaJuridica dato) throws Throwable;

    /**
    * Servicio que permite eliminar un objeto <code>NaturalezaJuridica</code>
    * <p>Utilizado desde las pantallas administrativas.
    * @param acto Objeto <code>NaturalezaJuridica</code> que va a ser eliminado.
    * @param usuario que elimina la naturaleza juridica
    * @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
    * resultado de la operación.
    * @throws <code>Throwable</code>
    * @see gov.sir.core.negocio.modelo.NaturalezaJuridica
    */
    public boolean eliminarNaturalezaJuridica(NaturalezaJuridica dato, Usuario usuario) throws Throwable;

    /**
    * Servicio que permite eliminar un objeto <code>EstadoAnotacion</code>
    * <p>Utilizado desde las pantallas administrativas.
    * @param acto Objeto <code>EstadoAnotacion</code> que va a ser eliminado.
    * @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
    * resultado de la operación.
    * @throws <code>Throwable</code>
    * @see gov.sir.core.negocio.modelo.EstadoAnotacion
    */
    public boolean eliminarEstadoAnotacion(EstadoAnotacion dato) throws Throwable;

    /**
    * Servicio que permite eliminar un objeto <code>DominioNaturalezaJuridica</code>
    * <p>Utilizado desde las pantallas administrativas.
    * @param acto Objeto <code>DominioNaturalezaJuridica</code> que va a ser eliminado.
    * @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
    * resultado de la operación.
    * @throws <code>Throwable</code>
    * @see gov.sir.core.negocio.modelo.DominioNaturalezaJuridica
    */
    public boolean eliminarDominioNaturalezaJuridica(DominioNaturalezaJuridica dato) throws Throwable;

    /* Declaración de métodos de traslados */

    /**
     * Servicio que permite trasladar un folio a una nueva zona registral
     * @param idFolioOrigen Objeto de tipo <code>Folio.ID</code> que represena folio a trasladar
     * @param idFolioDestino Objeto de tipo <code>Folio.ID</code> que represena folio trasladado
     * @param zonaDestino <code>ZonaRegistral</code> a donde se debe trasladar el folio
     * @param us Objeto que representa el usuario que realiza el traslado
     * @return Objeto de tipo <code>Folio</code> que representa el folio trasladado
     */
    public Folio trasladarFolio(Folio folioOrigen, Folio folioDestino, Usuario us, String resolucion) throws Throwable;

    /**
     * Servicio que permite ejecutar el proceso de catastro
     * @param fechIni Fecha inicial del proceso
     * @param fechfin Fecha final del proceso
     * @param circulo Objeto que representa el círculo registral sobre el cual se deje ejecutar el proceso
     * @param us Usuario que realiza la operación
     * @return True: proceso exitoso; False: proceso no exitoso
     * @throws <code>Throwable</code>
     */
    public boolean catastro(Date fechIni, Date fechFin, Circulo circulo, Usuario us) throws Throwable;

    /**
     * Servicio que permite consultar la información de traslados de un folio
     * @param idFolio Objeto que representa la llave de acceso al folio a consultar
     * @return Lista histórica de traslados de una matrícula (folio)
     */
    public List consultarTrasladosMatricula(FolioPk idFolio) throws Throwable;

    /**
     * Permite consultar la información de un ciudadano
     * @param oid Identificador interno del ciudadano
     */
    public Ciudadano getCiudadanoById( CiudadanoPk oid )   throws Throwable;


    /**
     * Devuelve un listado de anotaciones en las cuales
     * el ciudadano se encuentra relacionado
     * @return List<Anotacion>
     * */
    public List getAnotacionesQueRelacionanCiudadano( CiudadanoPk oid )   throws Throwable;

    /**
     * Obtiene el ciudadano dado su tipo y numero de documento.
     * Si el ciudadano no existe en el sistema retorna null
     * @param tipodoc
     * @param doc
     * @param idCirculo TODO
     * @return
     * @throws DAOException
     */
    public Ciudadano getCiudadanoByDocumento(String tipodoc, String doc, String idCirculo) throws Throwable;
    
    /**
     * Obtiene el ciudadanoTMP dado su tipo y numero de documento.
     * Si el ciudadano no existe en el sistema retorna null
     * @param tipodoc
     * @param doc
     * @param idCirculo TODO
     * @return
     * @throws DAOException
     */
    public CiudadanoTMP getCiudadanoTmpByDocumento(String tipodoc, String doc, String idCirculo) throws Throwable;

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
     * @throws Throwable
     */
    public boolean validarTerminacionCalificacion(TurnoPk oid, String tipoValidacion) throws Throwable;

    /**
     * Retorna la lista con los IDs (Folio.ID) de los folios hijos del folio especificado
     * tanto definitivos como temporales
     * Si el folio no tiene hijos o no existe retorna una lista vacía
     * @param oid
     * @return
     * @throws Throwable
     */
    public List getFoliosHijos(FolioPk oid, Usuario usuario) throws Throwable;

    /**
     * @author      :   Henry Gómez Rocha
     * @change      :   Evita que al momento de pasar del role Calificador al role
     *                  Digitador se verifique el usuario que tiene el bloqueo..
     * Caso Mantis  :   0004967
     */
    public List getFoliosHijos(FolioPk oid, Usuario usuario, boolean validarTurno) throws Throwable;
    
    /**
     * Retorna la lista con los IDs (Folio.ID) de los folios hijos del folio especificado
     * tanto definitivos como temporales
     * Si el folio no tiene hijos o no existe retorna una lista vacía
     * @param oid
     * @return
     * @throws Throwable
     */
    public List getFoliosHijosOrdenAnotacion(FolioPk oid, Usuario usuario) throws Throwable;

    /**
     * Retorna la lista con los IDs (Folio.ID) de los folios padre del folio especificado
     * tanto definitivos como temporales
     * Si el folio no tiene padres o no existe retorna una lista vacía
     * @param oid
     * @return
     * @throws Throwable
     */
    public List getFoliosPadre(FolioPk oid, Usuario usuario) throws Throwable;

    /**
     * @author      :   Henry Gómez Rocha
     * @change      :   Evita que al momento de pasar del role Calificador al role
     *                  Digitador se verifique el usuario que tiene el bloqueo..
     * Caso Mantis  :   0004967
     */
    public List getFoliosPadre(FolioPk oid, Usuario usuario, boolean validarTurno) throws Throwable;

    /**
     * Obtiene la zona registral de un folio dada la matricula
     * @param matricula
     * @return
     * @throws Throwable
     */
    public String getZonaRegistral(String matricula) throws Throwable;

    /**
     * Obtiene una oficina origen por ID
     * @param oid
     * @return
     * @throws Throwable
     */
    public OficinaOrigen getOficinaOrigen(OficinaOrigenPk oid) throws Throwable;

    /**
     * Retorna la lista de las anotaciones que tienen salvedades en un folio
     * tanto definitivos como temporales
     * Si el folio no tiene anotaciones con salvedades retorna una lista vacia
     * @param oid
     * @return
     * @throws Throwable
     */
    public List getAnotacionesConSalvedades(FolioPk oid, Usuario usuario) throws Throwable;

    /**
     * @author      :   Henry Gómez Rocha
     * @change      :   Evita que al momento de pasar del role Calificador al role
     *                  Digitador se verifique el usuario que tiene el bloqueo..
     * Caso Mantis  :   0004967
     */
    public List getAnotacionesConSalvedades(FolioPk oid, Usuario usuario, boolean validarTurno) throws Throwable;

    /**
     * Retorna la lista de las anotaciones que tienen salvedades en un folio
     * Si el folio no tiene anotaciones con salvedades retorna una lista vacia
     * @param oid
     * @return
     * @throws Throwable
     */
    public List getAnotacionesConSalvedades(FolioPk oid) throws Throwable;

    /**
     * Retorna la lista de las anotaciones que tienen cancelaciones en un folio
     * tanto definitivos como temporales
     * Si el folio no tiene anotaciones con salvedades retorna una lista vacia
     * @param oid
     * @return
     * @throws Throwable
     */
    public List getAnotacionesConCancelaciones(FolioPk oid, Usuario usuario) throws Throwable;

    /**
     * @author      :   Henry Gómez Rocha
     * @change      :   Evita que al momento de pasar del role Calificador al role
     *                  Digitador se verifique el usuario que tiene el bloqueo..
     * Caso Mantis  :   0004967
     */
    public List getAnotacionesConCancelaciones(FolioPk oid, Usuario usuario, boolean validarTurno) throws Throwable;

    /**
     * Retorna la lista de las anotaciones que tienen cancelaciones en un folio
     * Si el folio no tiene anotaciones con cancelaciones retorna una lista vacia
     * @param oid
     * @return
     * @throws Throwable
     */
    public List getAnotacionesConCancelaciones(FolioPk oid) throws Throwable;

	/**
	 * Retorna la lista de las anotaciones inválidas en un folio
	 * tanto definitivos como temporales
	 * Si el folio no tiene anotaciones inválidas retorna una lista vacia
	 * @param oid
	 * @return
	 * @throws Throwable
	 */
	public List getAnotacionesInvalidas(FolioPk oid, Usuario usuario) throws Throwable;

        /**
         * @author      :   Henry Gómez Rocha
         * @change      :   Evita que al momento de pasar del role Calificador al role
         *                  Digitador se verifique el usuario que tiene el bloqueo..
         * Caso Mantis  :   0004967
         */
	public List getAnotacionesInvalidas(FolioPk oid, Usuario usuario, boolean validarTurno) throws Throwable;

	/**
	 * Retorna la lista de las anotaciones inválidas en un folio
	 * Si el folio no tiene anotaciones inválidas retorna una lista vacia
	 * @param oid
	 * @return
	 * @throws Throwable
	 */
	public List getAnotacionesInvalidas(FolioPk oid) throws Throwable;



    /**
     * Retorna la lista con las anotaciones de los folios hijos del folio especificado,
     * cada anotacion tiene el objeto folio asociado y la última direccion en la lista
     * de direcciones de este folio
     * Si el folio no tiene hijos o no existe retorna una lista vacía
     * @param oid
     * @return
     * @throws Throwable
     */
    public List getFolioHijosEnAnotacionesConDireccion(FolioPk oid) throws Throwable;
    
    /**
     * Retorna la lista con las anotaciones de los folios hijos del folio especificado,
     * cada anotacion tiene el objeto folio asociado y la última direccion en la lista
     * de direcciones de este folio, ordenado por anotacion
     * Si el folio no tiene hijos o no existe retorna una lista vacía
     * @param oid
     * @return
     * @throws Throwable
     */
    public List getFolioHijosEnAnotacionesConDireccionOrdenadoAnotaciones(FolioPk oid) throws Throwable;
    
    /**
     * Obtiene el número actual de anotaciones temporales del folio, el método
     * debe recibir el usuario que tiene bloqueado el folio
     * @param oid
     * @param criterio
     * @param valor
     * @return
     * @throws Throwable
     */
    public long getCountAnotacionesTMPFolio(FolioPk oid, Usuario usuario) throws Throwable;

    /**
     * @author      :   Henry Gómez Rocha
     * @change      :   Evita que al momento de pasar del role Calificador al role
     *                  Digitador se verifique el usuario que tiene el bloqueo..
     * Caso Mantis  :   0004967
     */
    public long getCountAnotacionesTMPFolio(FolioPk oid, Usuario usuario, boolean validarTurno) throws Throwable;

    /**
     * Obtiene el siguiente orden de anotación con base en el tamaño
     * de las anotaciones definitivas y temporales
     * @param fid
     * @return
     * @throws Throwable
     */
    public long getNextOrdenAnotacion(FolioPk fid) throws Throwable;

    /**
     *  Compara el folio actual con el folio en temporal y devuelve en un
     *  objeto folio, las diferencias encontradas.
     *  La comparación incluye los objetos dependientes de folio: folio, anotacion,
     *  ciudadano, salvedad y cancelacion.
     * @param fid identificador del folio
     * @param usuario usuario que esta efectuando los cambios
     * @return un objeto delta folio con las diferencias.
     */
    public DeltaFolio getCambiosPropuestos(FolioPk fid, Usuario usuario) throws Throwable;

    /**
     *  Igual que getCambiosPropuestos(Folio.ID, Usuario)
     *  excepto que se puede escoger el procesar las diferencias
     *  e incluir las salvedades
     *  
     *  @see #getCambiosPropuestos(Folio.FolioPk, Usuario)
     */
    public DeltaFolio getCambiosPropuestos(FolioPk fid,Usuario usuario, boolean procesarDelta, boolean incluirSalvedades ) throws Throwable;

	/**
	* Adiciona una Prohibicion a la configuración del sistema
	* @param datos objeto Prohibicion con sus atributos incluido su identificador
	* @return identificador de Prohibicion generado
	*/
	public ProhibicionPk addProhibicion(Prohibicion datos)
		throws Throwable;


	/**
	* Edita una Prohibicion de la configuración del sistema
	* @param datos objeto Prohibicion con sus atributos exceptuando su identificador
	* el cual es generado por el sistema
	* @return identificador de Prohibicion generado
	*/
	public ProhibicionPk editarProhibicion(Prohibicion datos)
		throws Throwable;




	/**
	* Agrega una prohibición a un ciudadano
	* @param oid Identificador del ciudadano
	* @param pid Identificador de la prohibición
	* @param pm PersistenceManager
	* @return identificador del CiudadanoProhibicion
	* @throws Throwable
	*/
	public CiudadanoProhibicionPk addProhibicionToCiudadano(CiudadanoPk oid, ProhibicionPk pid, CirculoPk cirid, String comentario, Usuario usuario)
		throws Throwable;


	/**
	* Agrega una prohibición a un ciudadano
	* @param oid Identificador del ciudadano
	* @param pid Identificador de la prohibición
	* @param pm PersistenceManager
	* @return identificador del CiudadanoProhibicion
	* @throws Throwable
	*/
	public boolean desactivarProhibicionToCiudadano(CiudadanoProhibicionPk pid, Usuario usuario,String comentarioAnulacion)
		throws Throwable;


	/**
	 * Elimina una prohibición configurada en el sistema
	 * @param dato
	 * @return
	 * @throws Throwable
	 */
	public boolean eliminarProhibicion(Prohibicion dato)
		throws Throwable;



	/**
	* Obtiene una lista con todos las prohibiciones de ciudadano
	* configurados en el sistema
	* @return lista de objetos EstadoFolio
	* @see gov.sir.core.negocio.modelo.EstadoFolio
	* @throws Throwable
	*/
	public List getProhibiciones() throws Throwable;



	/**
	* Obtiene el usuario que tiene bloqueado el folio. El folio debe estar bloqueado
	* de lo contrario genera un DAOException
	* @param oid identificador del folio
	* @return objeto Usuario que tiene bloqueado el folio
	* @throws Throwable
	*/
	public Usuario getUsuarioBloqueoFolio(FolioPk oid)
		throws Throwable;



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
	 * @throws Throwable
	 */
	public Folio getDeltaFolio(FolioPk oid, Usuario usuario)
		throws Throwable;



	/**
	 * Cuenta el número total de anotaciones que tiene un folio incluyendo
	 * las anotaciones temporales, con el criterio dado
	 * @param oid
	 * @param criterio
	 * @param valor
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public long getCountAnotacionesFolio(FolioPk oid, String criterio,
		String valor, Usuario usuario) throws Throwable;

        /**
        * @author      :   Henry Gómez Rocha
        * @change      :   Evita que al momento de pasar del role Calificador al role
        *                  Digitador se verifique el usuario que tiene el bloqueo..
        * Caso Mantis  :   0004967
        */
	public long getCountAnotacionesFolio(FolioPk oid, String criterio,
		String valor, Usuario usuario, boolean validarTurno) throws Throwable;



	/**
	 * Retorna las anotaciones temporales de un folio con el criterio dado.
	 * @param oid
	 * @param criterio
	 * @param valor
	 * @param usuario
	 * @return
	 * @throws Throwable
	 */
	public List getAnotacionesTMPFolioToInsert(FolioPk oid, String criterio,
		String valor, Usuario usuario)
		throws Throwable;

        /**
         * @author      :   Henry Gómez Rocha
         * @change      :   Evita que al momento de pasar del role Calificador al role
         *                  Digitador se verifique el usuario que tiene el bloqueo..
         * Caso Mantis  :   0004967
         */
	public List getAnotacionesTMPFolioToInsert(FolioPk oid, String criterio,
		String valor, Usuario usuario, boolean validarTurno)
		throws Throwable;


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
	 * @throws Throwable
	 */
	public List getAnotacionesFolio(FolioPk oid, String criterio,
		String valor, int posicionInicial, int cantidad, Usuario usuario, boolean vigente)
		throws Throwable;

        /**
         * @author     : Ellery David Robles G.
         * @casoMantis : 08469.
         * @actaReq    : 024_151 - Error en anotación de medida cautelar.
         * @change     : Retorna las anotaciones definitivas y temporales de un folio segun el parametro naturaleza jurudica.
	 * @param      : oid.
	 * @param      : naturalezaJuridica.
	 * @throws     : Throwable.
	 */
	public List getAnotacionesFolioNJ(FolioPk oid, String naturalezaJuridica) throws Throwable;

        /**
         * @author      :   Henry Gómez Rocha
         * @change      :   Evita que al momento de pasar del role Calificador al role
         *                  Digitador se verifique el usuario que tiene el bloqueo..
         * Caso Mantis  :   0004967
         */
	public List getAnotacionesFolio(FolioPk oid, String criterio,
		String valor, int posicionInicial, int cantidad, Usuario usuario, boolean vigente, boolean validarTurno)
		throws Throwable;

	/**
	 * Obtiene los datos definitivos de los datos temporales que estén modificando
	 * al folio
	 * @param oid
	 * @param usuario
	 * @return
	 * @throws Throwable
	 */
	public Folio getDatosDefinitivosDeDatosTemporales(FolioPk oid, Usuario usuario)
		throws Throwable;


	/**
	 * Retorna una lista de objetos SolicitudFolio con los folios que tengan
	 * algún cambio temporal a ser aplicado dentro del turno especificado.
	 * Cada Folio retornado tiene los deltas que están pendientes por aplicar,
	 * si algún folio no se encuentra bloqueado por el usuario especificado
	 * se lanza una excepcion, si ningún folio tiene deltas retorna una lista vacía
	 * @param oid
	 * @param usuario
	 * @return
	 * @throws Throwable;
	 */
	public List getDeltaFolios(TurnoPk oid, Usuario usuario)
		throws Throwable;



	/**
	 * Retorna el archivo de la firma activa del círculo especificado
	 * @param oid
	 * @return
	 * @throws Throwable
	 */
	public FirmaRegistrador getFirmaRegistradorActiva(CirculoPk oid, String cargo) throws Throwable;


	/**
	 * Actualiza los datos del círculo.
	 * @param cid
	 * @param dato
	 * @return
	 * @throws Throwable
	 */
	public boolean updateCirculo(CirculoPk cid, Circulo dato, Usuario usuario) throws Throwable;
	
	
	/**
	 * Actualizar la descripción de una complementación cuando esta tiene conflictos con la 
	 * complementación del sistema FOLIO.
	 * @param complementacion
	 * @param usuario
	 * @return
	 * @throws Throwable
	 */
	public boolean updateComplementacionConflictiva(Complementacion complementacion, Usuario usuario) throws Throwable;


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
		throws Throwable;


        /**
        * @author      :   Henry Gómez Rocha
        * @change      :   Evita que al momento de pasar del role Calificador al role
        *                  Digitador se verifique el usuario que tiene el bloqueo..
        * Caso Mantis  :   0004967
        */
	public Folio getFolioByIDSinAnotaciones(FolioPk oid, Usuario usuario, boolean validarTurno)
		throws Throwable;


	/**
	 * Obtiene el ciudadano dado su tipo y numero de documento.
	 * Si el ciudadano no existe en el sistema retorna null
	 * @param tipodoc
	 * @param doc
	 * @param idCirculo TODO
	 * @return
	 * @throws Throwable
	 */
	public Ciudadano getCiudadanoByDocumentoSolicitante(String tipodoc, String doc, String idCirculo)
		throws Throwable;



	/**
	* Servicio utilizado para establecer si el folio con el número
	* de matrícula se encuentra en trámite. Si retorna null el folio NO está en trámite,
	* si returna un objeto Turno el folio se encuentra en trámite por el turno dado
	* @param matricula
	* @return Turno
	* @throws Throwable
	*/
	public Turno getTurnoTramiteFolio(String matricula) throws Throwable;
        
        /**
	* Servicio utilizado para establecer si el folio con el número
	* de matrícula se encuentra en trámite. Si retorna null el folio NO está en trámite,
	* si returna una lista de turnos el folio se encuentra en trámite por el turno dado
	* @param matricula
	* @return Turno
	* @throws Throwable
	*/
	public List getTurnosTramiteFolio(String matricula) throws Throwable;


	/**
	 * Obtiene el bloqueo del folio en caso que se encuentre bloqueado
	 * Si la matricula NO está bloqueada returna null, si está bloqueada
	 * retorna un usuario con la llave de bloqueo en la lista de sus llaves,
	 * y la llave con el BloqueoFolio en la lista de sus bloqueos de folio. En el
	 * objeto BloqueoFolio está la fecha de bloqueo y el turno (idWorkflowBloqueo)
	 * que lo bloqueó
	 * @param fid
	 * @return Hashtable
	 * @throws Throwable
	 */
	public Usuario getBloqueoFolio(FolioPk fid)
		throws Throwable;




	/**
	* Servicio utilizado para establecer si el folio con el número
	* de matrícula posee alguna deuda. Si No tiene deuda retorna null,
	* si tiene deuda retorna un Turno, con una solicitud, y con la liquidación
	* la cual NO tiene registrado el pago
	*
	* @param matricula
	* @return Turno
	* @throws Throwable
	*/
	public Turno getTurnoDeudaFolio(FolioPk fid) throws Throwable;


	/**
	 * Obtiene la plantilla de pertenencia asociada a la respuesta especificada
	 * @param respuesta
	 * @return
	 * @throws Throwable
	 */
	public PlantillaPertenencia getPlantillaPertenenciaByRespuesta(String respuesta)
		throws Throwable;

	/**
	 * Actualiza la despcripción de un oficio de pertenencia dada su respuesta
	 * @param respuesta
	 * @return
	 * @throws Throwable
	 */
	public boolean updatePlantillaPertenenciaByRespuesta(String respuesta, String nuevaDescripcion)
		throws Throwable;


	/**
	 * Obtiene la lista de las platilla de pertenencia configuradas
	 * en el sistema
	 * @return
	 * @throws Throwable
	 */
	public List getPlantillaPertenencias() throws Throwable;


	/**
	 * Retorna el turno asociado a la solicitud, si no existe el turno
	 * retorna null
	 * @param sol
	 * @return
	 * @throws DAOException
	 */
	public Turno getTurnoBySolicitud(SolicitudPk sol) throws Throwable;


	/**
	 * Obtiene un objeto NaturalezaJuridica dado su identificador
	 * @param oid identificador de NaturalezaJuridica
	 * @return objeto NaturalezaJuridica
	 * @throws Throwable
	 */
	public NaturalezaJuridica getNaturalezaJuridica(NaturalezaJuridicaPk oid)
		throws Throwable;


	/**
	 * Obtiene una lista con todas las impresoras del círculo
	 * @return lista de objetos CirculoImpresora
	 * @see gov.sir.core.negocio.modelo.CirculoImpresora
	 * @throws Throwable
	 */
	public List getCirculoImpresoras(String idCirculo) throws Throwable;


	/**
	 * Elimina la lista de impresoras configuradas para el círculo
	 * @param idCirculo
	 * @return
	 * @throws Throwable
	 */
	public boolean eliminarCirculoImpresoras(String idCirculo) throws Throwable;

	/**
	 * Adiciona una lista de impresoras a la lista actual de impresoras del círculo
	 * @return lista de objetos CirculoImpresora con la lista total de impresoras
	 * @see gov.sir.core.negocio.modelo.CirculoImpresora
	 * @throws Throwable
	 */
	public List addListaCirculoImpresoras(String idCirculo, List impresoras) throws Throwable;


	/**
	 * Actualiza los datos de una naturaleza jurídica.
	 * @param cid
	 * @param dato
	 * @return
	 * @throws Throwable
	 */
	public boolean updateNaturalezaJuridica(NaturalezaJuridica naturaleza, Usuario usuario)
		throws Throwable;


	/**
	* Retorna los grupos de naturalezas jurídicas, cada uno con su lista de naturalezas jurídicas
	* asociadas sólamente a calificación
	* @return Lista de GrupoNaturalezaJuridica
	* @see gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica
	* @throws Throwable
	*/
	public List getGruposNaturalezaJuridicaCalificacion() throws Throwable;



	/**
	 * Copia la anotación indicada con el ID idAnotacion (definitiva o temporal) al
	 * folio indicado con el ID idFolioDestino. El usuario debe tener el bloqueo del
	 * folio.
	 * CONDICIONES DEPENDIENDO DE LA ANOTACION A COPIAR:
	 * Cancelada: Se copia junto con su canceladora
	 * Canceladora: No se copia (Se lanza excepción, en el la lista de errores del Throwable)
	 * Derivada o generadora: Solo copia la anotación, sin relaciones a otros folios
	 * Normal: Se copia normalmente
	 * @param idAnotacion
	 * @param foliosID lista de objetos Folio.ID
	 * @param usuario Usuario que esta realizando la copia de anotaciones
	 * @param copiarComentario Booleano que determina si se debe copiar o no el comentario de la anotación origen
	 * @return la anotación generada
	 * @throws Throwable
	 */
	public boolean copiarAnotacion(AnotacionPk idAnotacion,List foliosID, Usuario usuario, boolean copiarComentario)
		throws Throwable;



	/**
	 * Cuenta los días NO hábiles configurados en el sistema desde la fecha inicial incluyéndola
	 * hasta la fecha final excluyéndola. Número Días no hábiles entre: [fehainicial, fechaFinal)
	 * @return long
	 * @throws Throwable
	 */
	public long countDiasNoHabiles(CirculoPk cirID, Date fechaInicial, Date fechaFinal) throws Throwable;


	/**
	 * Obtiene una anotación definitiva o temporal por el orden. Obtiene los datos básicos de
	 * la anotación sin dependencias.
	 * @param oid
	 * @param orden
	 * @param usuario
	 * @return
	 * @throws Throwable
	 */
	public Anotacion getAnotacionByOrden(FolioPk oid, String orden, Usuario usuario)  throws Throwable;


	/**
	 * Obtiene una lista de anotaciones temporales a partir de dos rangos por el orden.
	 * El servicio no retorna las anotaciones temporales producto de modificaciones en el orden de las anotaciones definitivas.
	 * Obtiene los datos básicos de la anotación sin dependencias.
	 * @param oid
	 * @param ordenInicial
	 * @param ordenFinal
	 * @param usuario
	 * @return
	 * @throws Throwable
	 */
	public List getAnotacionesTemporalesByRangoOrden(FolioPk oid, String ordenInicial, String ordenFinal, Usuario usuario)  throws Throwable;


	/**
	 * Copia una anotación canceladora a otros folios, cada folio debe tener un objeto anotación con el
	 * identificador de la anotación que se quiere cancelar
	 * @param idAnotacion
	 * @param folios
	 * @param usuario Usuario que esta realizando la copia de anotaciones
	 * @param copiarComentario Booleano que determina si se debe copiar o no el comentario de la anotación origen
	 * @return
	 * @throws Throwable
	 */
	public boolean copiarAnotacionCanceladora(AnotacionPk idAnotacion, List folios, Usuario usuario, boolean copiarComentario) throws Throwable;


	/**
	 * Devuelve una hashtable en donde para cada matrícula (key)
	 * especifica si tiene o no nuevas anotaciones temporales Boolean (valor)
	 * @param turnoID
	 * @return
	 * @throws Throwable
	 */
	public Hashtable validarNuevasAnotacionesTurno(TurnoPk turnoID) throws Throwable;



	/**
	 * Indica si un folio se encuentra asociado a un turno activo que se encuentre en calificación
	 * @param folioID
	 * @return
	 * @throws Throwable;
	 */
	public boolean isFolioInTurnoCalificacion(FolioPk folioID)throws Throwable;


	/**
	* Servicio utilizado para establecer si el folio con el número
	* de matrícula se encuentra cerrado
	* @param matricula
	* @return true: folio cerrado, false: folio no está cerrado (o no existe)
	* @throws Throwable
	*/
	public boolean cerradoFolio(String matricula) throws Throwable;


	/**
	* Servicio utilizado para establecer si el folio con el número
	* de matrícula se encuentra cerrado
	* @param matricula
	* @return true: folio cerrado, false: folio no está cerrado (o no existe)
	* @throws Throwable
	*/
	public boolean cerradoFolio(String matricula, Usuario usuario) throws Throwable;


	/**
	* Servicio utilizado para establecer si el folio con el número
	* de matrícula se encuentra inconsistente
	* @param matricula
	* @return true: folio inconsistente, false: folio no está inconsistente (o no existe)
	* @throws Throwable
	*/
	public boolean inconsistenteFolio(String matricula) throws Throwable;
	
	
	/**
	* Deshace los cambios que están siendo aplicados a los folios del turno, borra toda la información temporal
	* @param datos
	* @param usuario
	* @return
	* @throws Throwable
	*/
	public boolean deshacerCambiosTurno(TurnoPk turnoID, Usuario usuario, boolean desbloquear) throws Throwable;



	/**
	 * Actualiza una oficina origen con TODOS los datos que llegan el en objeto.
	 * El objeto debe tener su identificador.
	 * @param oficina
	 * @return
	 * @throws Throwable
	 */
	public boolean updateOficinaOrigen(OficinaOrigen oficina) throws Throwable;


	/**
	 * Indica si un turno tiene datos temporales realizados por el turno indicado
	 * @param turnoID
	 * @param folioID
	 * @return true: El turno está realizando cambios sobre el folio
	 *         false: El turno NO está realizando cambios sobre el folio
	 * @throws Throwable
	 */
	public boolean hasDatosTemporalesTurno(TurnoPk turnoID, FolioPk folioID)throws Throwable;
	
	/**
	 * Indica si un folio tiene datos temporales
	 * @param turnoID
	 * @param folioID
	 * @return true: El folio tiene información temporal
	 *         false: El folio NO tiene información temporal
	 * @throws Throwable
	 */
	public boolean hasDatosTemporalesFolio(FolioPk folioID)throws Throwable;


	/**
	 * Indica si el folio se encuentra bloqueado en el turno
	 * @param turnoID
	 * @param folioID
	 * @returntrue: true: El folio es bloqueado por el turno
	 *         		false: El folio NO esta bloqueadopor el turno
	 * @throws Throwable
	 */
	public boolean isBloqueadoByTurno(TurnoPk turnoID, FolioPk folioID) throws Throwable;
	
	/**
	 * Elimina un folio temporal dado su ID, y concepto de eliminacion. No se valida el bloqueo del folio
	 * @param folioID, String concepto
	 * @return
	 * @throws DAOException
	 */
	public boolean deleteFolio(FolioPk folioID, String concepto) throws Throwable;
	
	
	/**
	 * Elimina un folio temporal dado su ID, el folio es desasociado del turno al que se encuentre asociado
	 * @param folioID
	 * @return
	 * @throws Throwable
	 */
	public boolean deleteFolio(FolioPk folioID, Usuario usuario) throws Throwable;

	/**
	 * Desasocia dos folios que se encuentran encadenados a través de cada una de sus anotaciones
	 * @param anotaID1
	 * @param anotaID2
	 * @return
	 * @throws Throwable
	 */
	public boolean desasociarFolios(AnotacionPk anotaGeneradoraID, AnotacionPk anotaDerivadaID, Usuario usuario) throws Throwable;


	/**
	 * Asocia dos folios a través de sus anotaciones generadora y derivada
	 * @param anotaGeneradoraID
	 * @param anotaDerivadaID
	 * @param usuario
	 * @return
	 * @throws Throwable
	 */
	public boolean asociarFolios(AnotacionPk anotaGeneradoraID, AnotacionPk anotaDerivadaID, Usuario usuario, Turno turno) throws Throwable;

	/**
	 * Asocia dos folios a través de sus anotaciones generadora y derivada
	 * @param anotaGeneradoraID
	 * @param anotaDerivadaID
	 * @param usuario
	 * @return
	 * @throws Throwable
	 */
	public boolean asociarFoliosAdministrativa(AnotacionPk anotaGeneradoraID, AnotacionPk anotaDerivadaID, Usuario usuario, int tipo) throws Throwable;

	/**
	 * DesAsocia dos folios a través de sus anotaciones generadora y derivada
	 * @param anotaGeneradoraID
	 * @param anotaDerivadaID
	 * @param usuario
	 * @return
	 * @throws Throwable
	 */
	public boolean desasociarFoliosAdministrativa(AnotacionPk anotaGeneradoraID, AnotacionPk anotaDerivadaID, Usuario usuario, int tipo) throws Throwable;


	/**
	 * Devuelve la relación entre un folio padre y un folio hijo, si no existe retorna null.
	 * @param folioIDPadre
	 * @param folioIDHijo
	 * @param usuario
	 * @return
	 * @throws Throwable
	 */
	public FolioDerivado getFolioDerivadoEnlace(FolioPk folioIDPadre, FolioPk folioIDHijo, Usuario usuario) throws Throwable;


	/**
	 * Devuelve el folio derivado a partir del folio derivado hijo entre un folio padre y un folio hijo, si no existe retorna null.
	 * @param folioIDHijo
	 * @param usuario
	 * @return
	 * @throws Throwable
	 */
	public FolioDerivado getFolioDerivadoHijo(FolioPk folioIDHijo, Usuario usuario) throws Throwable;


	/**
	 * Obtiene las anotaciones agrupadas por folio que fueron agregadas en un turno específico
	 * @param oid
	 * @return
	 * @throws Throwable
	 */
	public List getCalificacionTurno(TurnoPk oid) throws Throwable;



	/**
	 * Delega el bloqueo de un folio dentro de un turno al usuario especificado
	 * @param oid
	 * @param usuario
	 * @param fid
	 * @return
	 * @throws Throwable
	 */
	public LlaveBloqueo delegarBloqueoFolio(TurnoPk oid, Usuario usuario, FolioPk fid) throws Throwable;


	/**
	 * Actualiza un ciudadano en el modelo temporal
	 * @param ciud
	 * @return
	 * @throws Throwable
	 */
	public boolean updateCiudadano(Ciudadano ciud, Usuario usuario, String numRadicacion)  throws Throwable;
	
	/**
	 * Actualiza un ciudadano
	 * @param ciud
	 * @return
	 * @throws Throwable
	 */
	public boolean updateCiudadanoAdministrativa(Ciudadano ciud, Usuario usuario)  throws Throwable;



	/**
	 * Indica si el ciudadano se encuentra asociado a una anotación definitiva
	 * @param turno
	 * @param folio
	 * @param pm
	 * @return
	 */
	public boolean isCiudadanoInAnotacionDefinitiva(CiudadanoPk ciudID) throws Throwable;



	/**
	 * Obtiene un ciudadano por medio del identificador, si el ciudadano tiene cambios temporales devuelve
	 * estos cambios, si no, devuelve el ciudadano definitivo, si el ciudadano no existe retorna null
	 * @param oid identificador del ciudadno
	 * */
	public Ciudadano getCiudadanoByIdTMP( CiudadanoPk oid )   throws Throwable;



    /**
     * Obtiene un objeto Texto dado su identificador
     * @param oid identificador del circulo
     * @return objeto Circulo
     * @throws DAOException
     */
    public Texto getTexto(TextoPk oid) throws Throwable;



    /**
     * Adiciona un texto a la configuración del sistema
     * @param datos objeto Circulo con sus atributos exceptuando su identificación
     * el cual es generado por el sistema
     * @param datos
     * @return identificación del texto generado por el sistema
     * @throws DAOException
     */
    public TextoPk addTexto(Texto texto) throws Throwable;



    /**
     * Actualiza los datos del texto.
     * @param cid
     * @param dato
     * @return
     * @throws DAOException
     */
    public boolean updateTexto(TextoPk cid, String nuevoTexto) throws Throwable;

    /**
	 * Obtiene una lista con todas los tipos de imprimibles
	 * @return lista de objetos TipoImprimible
	 * @see gov.sir.core.negocio.modelo.TipoImprimible
	 * @throws Throwable
	 */
	public List getTiposImprimible() throws Throwable;


	/**
	 * Retorna la lista de impresoras por círculo y tipo de imprimible
	 * @param idCirculo
	 * @param idTipoImprimible
	 * @param pm
	 * @return
	 * @throws Throwable
	 */
	public List getCirculoImpresorasXTipoImprimible(String idCirculo, String idTipoImprimible) throws Throwable;


	/**
	 * Obtiene la configuracion de impresoras del círculo por tipo de imprimible
	 * @return lista de objetos CirculoImpresora
	 * @see gov.sir.core.negocio.modelo.CirculoImpresora
	 * @throws Throwable
	 */
	public Hashtable getConfiguracionImpresoras(String idCirculo) throws Throwable;



	/**
	 * Cambia la configuracion de impresoras del círculo por tipo de imprimible
	 * @return lista de objetos CirculoImpresora
	 * @see gov.sir.core.negocio.modelo.CirculoImpresora
	 * @throws Throwable
	 */
	public boolean setConfiguracionImpresoras(Hashtable nuevaConfiguracion, String idCirculo) throws Throwable;



       /**
        * Revisar si las anotaciones con cambios temporales
        * dentro de un folio dado como parametro tienen
        * orden repetido
        * @param matricula
        * @return
        */
       public boolean validarFolioTieneAnotacionesconOrdenRepetido( FolioPk folioId ) throws Throwable;


     /**
   	 * Devuelve un listado de los ciudadanos que estan relacionados a un folio
   	 * @return List
   	 * */
   	 public List getCiudadanoUltimosFolio( String idMatricula ) throws Throwable;
   		
   	
       /**
   	 * Elimina una oficina origen
   	 * @param oficinaID
   	 * @return
   	 * @throws Throwable
   	 */
   	public boolean eliminarOficinaOrigen(OficinaOrigenPk oficinaID) throws Throwable;
       /**
        * cuenta el numero de salvedades-folio que un folio de
        * determinado tu8rno tiene
        * @param idMatricula
        * @param idWorkflow
        * @param usuario
        * @return
        */
       public long countSalvedadesFolio( String idMatricula, String idWorkflow, Usuario usuario ) throws Throwable;

       /**
        * cuenta el numero de salvedades-anotacion que un folio de
        * determinado tu8rno tiene
        * @param idMatricula
        * @param idWorkflow
        * @param usuario
        * @return
        */
       public long countSalvedadesAnotacion( String idMatricula, String idWorkflow, Usuario usuario ) throws Throwable;

      /**
       * Regresa la lista de oficinas segun el municipio especificado como parametro
       * @param oid el identificador del municipio
      */
      public List getOficinasOrigenByMunicipio( MunicipioPk oid ) throws Throwable;

      /**
       * Crea un nuevo ciudadano en la base de datos
       * @param ciudadano Objeto <code>Ciudadano</code> con los datos del ciudadano nuevo a crear
       * @return
       * @throws Throwable
       */
      public Ciudadano crearCiudadano(Ciudadano ciudadano) throws Throwable;
      /**
       * Regresa la lista de circulos dado un identificador de usuario-sir
       * @param oid el identificador del usuario
      */
      public List getCirculosByUsuario( UsuarioPk oid ) throws Throwable;

      /**
       * Regresa una anotacion sin tener en cuenta el modelo temporal
       * @param oid el identificador de anotacion
      */
      public Anotacion getAnotacionById( AnotacionPk oid ) throws Throwable;
      
      /**
       * Regresa una anotacion teniendo en cuenta el modelo temporal
       * @param oid el identificador de anotacion
      */
      public Anotacion getAnotacion( AnotacionPk oid ) throws Throwable;
      
      /**
       * Determina si un folio tiene el estado de TRASLADADO
       * @param matricula El folio para el que se quiere determinar si ha sido o no TRASLADADO
       * @return true si el folio ha sido trasladado, false de lo contrario
       * @throws Throwable
       */
      public boolean trasladadoFolio(String matricula) throws Throwable;
      
      /**
       * inhabilita un círculo
       * @param circuloOrigen círculo que se quiere inhabilitar
       * @param circuloDestino círculo donde se va a asociar la información del círculo origen
       * @param zonasRegistrales nuevas zonas registrales a asociar
       * @param usuariosCrear usuarios que se tienen que crear
       * @param usuariosTrasladar usuarios que se tienen que trasladar
       * @param usuariosRolConsulta usuarios a los que se le van a crear los roles
       * @param usuarioInhabilita usuario que inhabilita el circulo y crea el otro
       * @throws Throwable
       */
      public void inhabilitarCirculo(Circulo circuloOrigen, Circulo circuloDestino, List zonasRegistrales, Map usuariosCrear, List usuariosTrasladar, List usuariosRolConsulta, Usuario usuarioInhabilita) throws Throwable;
            
      /**
       * Regresa la lista de los círculos origen que fueron inhabilitados y asociados al circulo destino
       * @param circuloDestino círculo al que fue asociado el círculo inhabilitado
       * @return
       * @throws Throwable
       */
      public List getCirculosInhabilitados(Circulo circuloDestino) throws Throwable;
      
      /**
       * Valida el principio de prioridad según Incidencia 5063
       * Si los turnos van a salir de firma y existen otros turnos anteriores (con fechaInicio anterior)
       * que no hayan salido de firma y comparten algún folio se genera una excepcion
       * @param oid
       * @throws DAOException
       */
      public void validarPrincipioPrioridadFirma(List turnos) throws Throwable;
      
      /**
       * Valida el principio de prioridad Devolucion
       * Si los turnos van a ser devueltos y existen otros turnos posteriores
       * que no hayan salido de firma y comparten algún folio se genera una excepcion
       * @param oid
       * @throws DAOException
       */
      public void validarPrincipioPrioridadDevolucion(List turnos) throws Throwable;
      
      /**
       * Valida el principio de prioridad
       * Si los turnos van a salir de firma y existen otros turnos anteriores (con fechaInicio anterior)
       * que no hayan salido de firma y comparten algún folio los agrega a una lista
       * @param oid
       * @throws DAOException
       */
      public Hashtable validarPrincipioPrioridadFirmaRelacion(List turnos) throws Throwable;
      
      /**
       * Valida que el turno sea el primero entre turnos que comparten las mismas matricula
       * Si los turnos se van a tomar en firma y existen otros turnos anteriores (con fechaInicio anterior)
       * que no hayan salido de firma y comparten algún folio se genera false
       * @param oid
       * @throws DAOException
       */
      public boolean validarTurnoFirmaPrincipioPrioridad(List turnos) throws Throwable;
      
	/**
	 * Traslada el rango de folios de un círculo a otro
	 * @param circuloOrigen círculo del cual se van a trasladar los folios
	 * @param circuloDestino círculo al que se van a trasladar los folios
	 * @param trasladoMasivoInicio indica desde que número de matrícula se van a trasladar
	 * @param trasladoMasivoFin indica hasta que número de matrícula se van a trasladar
	 */
	public void trasladarFolios(Circulo circuloOrigen, Circulo circuloDestino, int trasladoMasivoInicio, int trasladoMasivoFin) throws Throwable;

	/**
	 * Valida la matricula del folio a crear 
	 * @param datos folio que se va a crear
	 */
    public void validarMatriculaCrearFolio(Folio datos) throws Throwable;
    
    /**
     * Valida la prohibicion de enajencion de una lista de anotaciones ciudadano
     * @param anotaciones
     * @return 
     * @throws DAOException
     */
    public boolean validarAnotacionesCiudadano(List anotacionesCiudadano)throws Throwable;
     
    /**
	 * Valida si existe una matricula como no grabada 
	 * @param datos del id de la matricula no grabada
	 */
    public boolean matriculaNoGrabadaExistente(String circulo, long idMatricula) throws Throwable;
    
    /**
    * @param folio
    * @param usuario
    * @return
    * @throws DAOException
    */
    public boolean updateFolioCreacionDirecta(Folio folio, Usuario usuario) throws Throwable;
    
    /**
	* @param anotacion
	* @param usuario
	* @return
	* @throws DAOException
	*/
	public boolean updateAnotacionesCreacionDirecta(Anotacion anotacion, Usuario usuario) throws Throwable;
	
    /**
     * Realiza la migración incremental de un Folio dado el número de Matrícula.
     * @param matricula
     * @return
     * @throws Throwable
     */
	public String migrarFolioByMatricula(String matricula, Usuario usuario) throws Throwable;

	/**
	 * Realiza la migración incremental de un Turno dado el número del turno.
	 * @param numTurno
	 * @return
	 * @throws Throwable
	 */
	public Turno migrarTurnoNumero(String numTurno) throws Throwable ;
    
	/**
     * Obtiene un objeto Texto dado su identificador
     * @param oid identificador del circulo
     * @return objeto Circulo
     * @throws DAOException
     */
    public ActoresRepartoNotarial getActoresRepartoNotarial(ActoresRepartoNotarialPk oid) throws Throwable;
    
	/**
	* @param oid
	* @param usuario
	* @return
	* @throws DAOException
	*/
	public List getFoliosAnotaActNomenclaturaSinActualizarBySolicitud(SolicitudPk oid,Usuario usuario) throws Throwable;
	
	/**
	 * Actualiza las salvedades del folio. Por cada salvedad si existe, la actualiza, de lo contrario
	 * la inserta
	 * @param folio
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public boolean updateFolioSalvedades(Folio folio, Usuario usuario) throws Throwable;
	
	public boolean isUltimoPropietario(AnotacionCiudadano anota)throws Throwable;
	
	public List getTurnosCorreccionActivosFolio(Folio fol, Turno turnoNoValidar) throws Throwable;
	
	public void eliminarFolioCreacionDirecta(Folio datos, Usuario usuario) throws Throwable;
	
	public boolean desbloquearFoliosEntradaCalificacion(List lstMatricula, Usuario usuario) throws Throwable;
	
	/**
	 * Actualiza folio derivado
	 * @param datos
	 * @param usuario
	 * @throws Throwable
	 */
	public void updateFolioDerivado(List foliosDerivadosModificados,Folio folioPadre,Usuario usuario) throws Throwable;
	
	/**
	 * Retorna una lista de folios derivados temporales para una matrícula padre
	 * @param oid
	 * @return
	 * @throws Throwable
	 */
	public List getFoliosDerivadosTMPByMatricula(String matriculaPadre) throws Throwable;

/*Adiciona Funcionalidad Boton de Pago
 * Author: Ingeniero Diego Hernandez
 * Modificacion en: 2010/02/23 by jvenegas
	 * Retorna una lista de matrículas
	 * @param idMatricula
	 * @return
	 * @throws Throwable
	 */

    public List getAlertasMatriculas(String idMatricula) throws Throwable;
   /**
    * @author      :   Julio Alcazar
    * @change      :   Se declara el método anuladoFolio(), el cual verifica si un folio ha sido anulado
    * Caso Mantis  :   0007123
    */
    public boolean anuladoFolio(String matricula) throws Throwable;
     /**
    * @author      :   Carlos Mario Torres Urina
    * @change      :   Se declara el metodo getAnotacionTMP()
    * Caso Mantis  :   11767
    */
    public Anotacion getAnotacionTMP(FolioPk oid,String idanotacion) throws Throwable;
    /**
    * @author      :   Carlos Mario Torres Urina
    * @change      :   getGruposNaturalezaJuridicaAll()
    * Caso Mantis  :   12705
    */
    public List getGruposNaturalezaJuridicaAll() throws Throwable;
    
    /**
     * @Autor: Edgar Lora
     * @Mantis: 13176
     */
    public NaturalezaJuridicaEnhanced getNaturalezaJuridicaById(NaturalezaJuridicaEnhancedPk pk) throws Throwable;
    /*
     *  @author Carlos Torres
     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
     */
    public OficinaOrigen getOficinaOrigen(String idOficina) throws Throwable;
}

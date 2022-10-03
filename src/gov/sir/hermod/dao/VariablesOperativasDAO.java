/*
 * VariablesOperativasDAO.java
 * Interfaz que se encarga del manejo (Creación, Consulta y Modificación) de todas
 * las variables operativas que son utilizadas en el sistema.
 * Creado  04 de noviembre de 2004, 9:10
 */

package gov.sir.hermod.dao;

import java.util.List;

import org.auriga.core.modelo.transferObjects.Rol;


import gov.sir.core.negocio.modelo.AccionNotarial;
import gov.sir.core.negocio.modelo.AccionNotarialPk;
import gov.sir.core.negocio.modelo.AlcanceGeograficoPk;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.Categoria;
import gov.sir.core.negocio.modelo.CategoriaPk;
import gov.sir.core.negocio.modelo.CausalRestitucion;
import gov.sir.core.negocio.modelo.CausalRestitucionPk;
import gov.sir.core.negocio.modelo.CheckItemPk;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.AlcanceGeografico;
import gov.sir.core.negocio.modelo.CheckItem;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.OPLookupTypes;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.ProcesoPk;
import gov.sir.core.negocio.modelo.RolPantalla;
import gov.sir.core.negocio.modelo.SubtipoAtencionPk;
import gov.sir.core.negocio.modelo.SubtipoSolicitud;
import gov.sir.core.negocio.modelo.SubtipoSolicitudPk;
import gov.sir.core.negocio.modelo.SucursalBanco;
import gov.sir.core.negocio.modelo.Tarifa;
import gov.sir.core.negocio.modelo.TextoImprimible;
import gov.sir.core.negocio.modelo.TextoImprimiblePk;
import gov.sir.core.negocio.modelo.TipoActoPk;
import gov.sir.core.negocio.modelo.TipoCalculoPk;
import gov.sir.core.negocio.modelo.TipoCertificadoPk;
import gov.sir.core.negocio.modelo.TipoCertificadoValidacion;
import gov.sir.core.negocio.modelo.TipoCertificadoValidacionPk;
import gov.sir.core.negocio.modelo.TipoConsultaPk;
import gov.sir.core.negocio.modelo.TipoDerechoReg;
import gov.sir.core.negocio.modelo.TipoDerechoRegPk;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoDocumentoPk;
import gov.sir.core.negocio.modelo.TipoFotocopia;
import gov.sir.core.negocio.modelo.TipoActo;
import gov.sir.core.negocio.modelo.TipoCalculo;
import gov.sir.core.negocio.modelo.TipoFotocopiaPk;
import gov.sir.core.negocio.modelo.TipoImpuesto;
import gov.sir.core.negocio.modelo.SubtipoAtencion;
import gov.sir.core.negocio.modelo.TipoCertificado;
import gov.sir.core.negocio.modelo.TipoConsulta;
import gov.sir.core.negocio.modelo.TipoImpuestoPk;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.TipoNotaPk;
import gov.sir.core.negocio.modelo.TipoRecepcionPeticion;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.TipoRecepcionPeticionPk;
import gov.sir.core.negocio.modelo.TipoTarifa;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion;
import gov.sir.core.negocio.modelo.UsuarioSubtipoAtencionPk;
import gov.sir.core.negocio.modelo.ValidacionNota;
import gov.sir.core.negocio.modelo.ValidacionNotaPk;



/**
 * Interfaz que se encarga del manejo y administración (Creación, Consulta y Modificación)
 * de todas  las variables operativas que son utilizadas en el sistema.
 * @author  dlopez, mortiz
 */
public interface VariablesOperativasDAO
{

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
	public AlcanceGeograficoPk addAlcanceGeografico (AlcanceGeografico datos) throws DAOException;


	/**
	* Adiciona una <code>AccionNotarial</code> a la configuración del sistema.
	* <p>
	* Se genera una excepción en el caso en el que ya exista una <code>AccionNotarial</code>
	* con el identificador recibido como parámetro.
	* @param datos El objeto de tipo <code>AccionNotarial</code> con sus atributos que va
	* @param usuario que va adicionar la accion notarial
	* a ser agregado a la configuración del sistema.
	* @return identificador de la <code>AccionNotarial</code> generada.
	* @see gov.sir.core.negocio.modelo.AccionNotarial
	* @throws DAOException
	*/
	public AccionNotarialPk addAccionNotarial (AccionNotarial datos, Usuario usuario) throws DAOException;


	/**
	* Editar una AccionNotarial en el sistema.
	* @param an La <code>AccionNotarial</code> que se va a editar.
	* @param usuario que va editar la accion notarial
	* @return true o false dependiendo del resultado de la actualización.
	* @see gov.sir.core.negocio.modelo.AccionNotarial
	* @throws <code>Throwable</code>
	*/
	public boolean editarAccionNotarial (AccionNotarial datos, Usuario usuario) throws DAOException;


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
	public CausalRestitucionPk addCausalRestitucion (CausalRestitucion causal) throws DAOException;


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
	public CausalRestitucionPk editCausalRestitucion(CausalRestitucion causal) throws DAOException;


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
	public TipoDocumentoPk addTipoDocumento (TipoDocumento tdoc) throws DAOException;




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
	public SubtipoAtencionPk addSubtipoAtencion (SubtipoAtencion subAt)throws DAOException;

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
	public SubtipoAtencionPk updateSubtipoAtencion (SubtipoAtencion subAt)throws DAOException;



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
	public SubtipoSolicitudPk addSubtipoSolicitud (SubtipoSolicitud subSol)throws DAOException;

	/**
	* Edita un objeto de tipo <code>SubtipoSolicitud</code> a la configuración del sistema.
	* @param subSol objeto de tipo <code>SubtipoSolicitud</code> con todos sus atributos.
	* @return identificador del objeto <code>SubtipoSolicitud</code> editado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SubtipoSolicitud
	*/
	public SubtipoSolicitudPk updateSubtipoSolicitud (SubtipoSolicitud subSol)throws DAOException;



	/**
	* Adiciona un objeto de tipo <code>TipoActo</code> a la configuración del sistema.
	* <p>
	* El método genera una excepción si ya existe un Tipo de Acto con el identificador
	* del objeto pasado como parámetro.
	* @param acto objeto de tipo <code>TipoActo</code> con todos sus atributos.
	* @param usuario que va adicionar el tipo de acto
	* @return identificador del objeto <code>TipoActo</code> adicionado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoActo
	*/
	public TipoActoPk addTipoActo (TipoActo actom, Usuario usuario)throws DAOException;

	/**
	* Actualiza un objeto de tipo <code>TipoActo</code> a la configuración del sistema.
	* @param acto objeto de tipo <code>TipoActo</code> con todos sus atributos.
	* @param usuario que va a modificar el tipo de acto
	* @return identificador del objeto <code>TipoActo</code> modificado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoActo
	*/
	public TipoActoPk updateTipoActo (TipoActo acto, Usuario usuario)throws DAOException;


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
	public TipoFotocopiaPk addTipoFotocopia (TipoFotocopia fot) throws DAOException;



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
	public TipoCalculoPk addTipoCalculo (TipoCalculo tipoCalc) throws DAOException;



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
	public TipoImpuestoPk addTipoImpuesto (TipoImpuesto tImpuesto) throws DAOException;



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
	public TipoCertificadoPk addTipoCertificado (TipoCertificado tipoCert)throws DAOException;



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
	public TipoConsultaPk addTipoConsulta (TipoConsulta tipoCons) throws DAOException;




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
	public TipoRecepcionPeticionPk addTipoRecepcionPeticion(TipoRecepcionPeticion tipoRec) throws DAOException;



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
	public TipoDerechoRegPk addTipoDerechoRegistral(TipoDerechoReg tipoDer) throws DAOException;



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
	public CategoriaPk addCategoriaReparto(Categoria categoria, Usuario usuario) throws DAOException;
	
	/**
	* Edita un objeto de tipo <code>Categoria</code> a la configuración del sistema.
	* <p>
	* El método genera una excepción si no existe una Categoria con el identificador
	* del objeto pasado como parámetro.
	* @param categoria objeto de tipo <code>Categoria</code> con todos sus atributos.
	* @param usuario que va a modificar la categoria del reparto
	* @return identificador del objeto <code>Categoria</code> editado.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Categoria
	*/
	public CategoriaPk updateCategoriaReparto(Categoria categoria, Usuario usuario) throws DAOException;



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
	public TipoCertificado getTipoCertificadoByID(TipoCertificadoPk tipoCert) throws DAOException;




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
	public List getTiposFotocopia () throws DAOException;



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
	public List getTiposCertificado () throws DAOException;




	/**
	* Obtiene una la lista de objetos <code>TipoCertificado</code> existentes en
	* la configuración del sistema y que están asociados con el proceso
	* de certificados individuales.
	* <p>
	* El ordenamiento de los resultados se realiza alfabéticamente, utilizando
	* como criterio de ordenamiento el nombre del <code>TipoCertificado</code>
	* @return una lista de objetos <code>TipoCertificado</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.TipoCertificado
	*/
	public List getTiposCertificadosIndividuales () throws DAOException;



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
	public List getTiposConsulta ()throws DAOException;



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
	public List getSubTiposAtencion () throws DAOException;



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
	public List getSubTiposAtencionCompleto () throws DAOException;


	/**
	* Agrega el orden de un usuario en el subtipo solicitud
	* @return true o false dependiendo del resultado de la adicion.
	* @see gov.sir.core.negocio.modelo.Usuario
	* @see gov.sir.core.negocio.modelo.SubtipoAtencion
	* @throws DAOException
	*/
	public  UsuarioSubtipoAtencionPk addOrdenSubtipoAtencion(SubtipoAtencion sub, Usuario usu,  String orden, Circulo cir) throws DAOException;

	/**
	* Remueve el orden de un usuario en el subtipo solicitud
	* @return true o false dependiendo del resultado de la operacion.
	* @see gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion
	* @throws DAOException
	*/
	public  boolean removeOrdenSubtipoAtencion(UsuarioSubtipoAtencion usuSubtipoAtencion, Circulo cir) throws DAOException;

	/**
	* Obtiene una la lista de objetos <code>Usuario</code> .
	* @return una lista de objetos <code>Usuario</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.Usuario
	*/
	public List getCalificadoresSubtipoAtencion (Circulo cir, SubtipoAtencion sub) throws DAOException;


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
	public List getTiposActo () throws DAOException;
	
	
	/**
	* Obtiene un objeto <code>TipoActo</code> a partir del idTipoActo Solicitado
	* @return Objeto <code>TipoActo</code>
	* @param TipoActo.ID tid
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.TipoActo
	*/		
	public TipoActo getTipoActo (TipoActoPk tid) throws DAOException;
        
        /*
        * @autor          : JATENCIA 
        * @mantis        : 0015082 
        * @Requerimiento : 027_589_Acto_liquidación_copias 
        * @descripcion   : Se declara el metodo en interfacez
        * 
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
	public List getTiposActoDos () throws DAOException;
	
	
	/**
	* Obtiene un objeto <code>TipoActo</code> a partir del idTipoActo Solicitado
	* @return Objeto <code>TipoActo</code>
	* @param TipoActo.ID tid
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.TipoActo
	*/		
	public TipoActo getTipoActoDos (TipoActoPk tid) throws DAOException;
        /* - Fin del bloque - */

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
	public List getTiposCalculo () throws DAOException;



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
	public List getSubTiposSolicitud () throws DAOException;



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
	public List getTiposImpuesto () throws DAOException;

        /**
	*
        * @author Fernando Padilla Velez
        * @change Modificado para el caso MANTIS 135_141_Impuesto Meta, Obtiene
        *         una la lista de objetos <code>TipoImpuestoCirculo</code> existentes en
	*         la configuración del sistema.
	* @return una lista de objetos <code>TipoImpuestoCirculo</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.TipoImpuestoCirculo
	*/
	public List getTiposImpuestoCirculo() throws DAOException;



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
	public List getTiposRecepcionPeticion () throws DAOException;



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
	public List getCausalesRestitucion () throws DAOException;


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
	public List getTiposAccionNotarial () throws DAOException;


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
	public List getTiposAlcanceGeografico ( ) throws DAOException;



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
	public List getCategoriasReparto (String orden) throws DAOException;



	/**
	* Obtiene una la lista de los Tipos de Derechos Registrales existentes en
	* la configuración del sistema, utilizando como criterio de ordenamiento
	* el nombre del objeto.
	* @return una lista de objetos <code>TipoDerechoReg</code> ordenados
	* alfabéticamente de acuerdo al nombre.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoDerechoReg
	*/
	public List getTiposDerechosRegistrales( ) throws DAOException;



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
	public int getMaximoImpresionesByRol(Rol rol, Proceso p) throws DAOException;



	/**
	* Obtiene una la lista de los Tipos de Notas existentes en
	* la configuración del sistema, utilizando como criterio de ordenamiento
	* el nombre del objeto.
	* @return una lista de objetos <code>TipoNota</code> ordenados
	* alfabéticamente de acuerdo al nombre.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoNota
	*/
	public List getTiposNota() throws DAOException;



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
	public List getTiposNotaProceso(ProcesoPk proceso) throws DAOException;



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
	public CategoriaPk addOficinaOrigenToCategoria (CategoriaPk cat, OficinaOrigen oficina) throws DAOException;



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
	public CategoriaPk updateCategoria (CategoriaPk cat, Categoria newInfo) throws DAOException;



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
	public TipoFotocopia getTipoFotocopiaByID(TipoFotocopiaPk oid) throws DAOException;


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
	public List getValidacionesByTipoCertificado(TipoCertificadoPk oid) throws DAOException;




	/**
	* Servicio que permite eliminar una <code>Categoría</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param categoria La <code>Categoria</code> que va a ser eliminada.
	* @param usuario que va a eliminar la categoria
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Categoria
	*/
	public boolean eliminarCategoria (Categoria categoria, Usuario usuario)
		throws DAOException;



	/**
	* Servicio que permite eliminar un <code>AlcanceGeografico</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param alcance El <code>AlcanceGeografico</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.AlcanceGeografico
	*/
	public boolean eliminarAlcanceGeografico (AlcanceGeografico alcance)
		throws DAOException;



	/**
	* Servicio que permite eliminar un <code>TipoFotocopia</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param tipoFot El <code>TipoFotocopia</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoFotocopia
	*/
	public boolean eliminarTipoFotocopia (TipoFotocopia tipoFot)
		throws DAOException;



	/**
	* Servicio que permite eliminar un <code>TipoCalculo</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param tipoCalc El <code>TipoCalculo</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoCalculo
	*/
	public boolean eliminarTipoCalculo (TipoCalculo tipoCalc)
			throws DAOException;



	/**
	* Servicio que permite eliminar un <code>TipoDerechoReg</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param tipoDerecho El <code>TipoDerechoReg</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoDerechoReg
	*/
	public boolean eliminarTipoDerechoRegistral (TipoDerechoReg tipoDerecho)
			throws DAOException;



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
	public boolean eliminarAccionNotarial (AccionNotarial accion, Usuario usuario)
			throws DAOException;




	/**
	* Servicio que permite eliminar un <code>Banco</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param banco El <code>Banco</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Banco
	*/
	public boolean eliminarBanco (Banco banco) throws DAOException;


	/**
	 * Obtiene el listado de Categorías a las que puede pertenecer una Notaría.
	 * @return Lista de Categorías a las que puede pertenecer una Notaría.
	 * @see gov.sir.core.negocio.modelo.CategoriaNotaria
	 * @throws Throwable
	 */
	 public List getCategoriasNotarias () throws DAOException;
	

	/**
	* Servicio que permite eliminar una <code>SucursalBanco</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param sucursal <code>SucursalBanco</code> que va a ser eliminada.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SucursalBanco
	*/
	public boolean eliminarSucursalBanco (SucursalBanco sucursal) throws DAOException;



	/**
	* Servicio que permite eliminar una <code>Tarifa</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param tarifa <code>Tarifa</code> que va a ser eliminada.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Tarifa
	*/
	public boolean eliminarTarifa (Tarifa tarifa) throws DAOException;



	/**
	* Servicio que permite eliminar un <code>TipoTarifa</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param tipoTarifa <code>TipoTarifa</code> que va a ser eliminada.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoTarifa
	*/
	public boolean eliminarTipoTarifa (TipoTarifa tipoTarifa) throws DAOException;



	/**
	* Servicio que permite eliminar un <code>SubtipoSolicitud</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param subtipoSolicitud <code>SubtipoSolicitud</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SubtipoSolicitud
	*/
	public boolean eliminarSubtipoSolicitud (SubtipoSolicitud subtipoSolicitud) throws DAOException;



	/**
	* Servicio que permite eliminar un objeto <code>OPLookupTypes</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param type Objeto <code>OPLookupTypes</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.OPLookupTypes
	*/
	public boolean eliminarLookupType (OPLookupTypes type) throws DAOException;




	/**
	* Servicio que permite eliminar un objeto <code>OPLookupCodes</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param code Objeto <code>OPLookupCodes</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.OPLookupCodes
	*/
	public boolean eliminarLookupCode (OPLookupCodes code) throws DAOException;



	/**
	* Servicio que permite eliminar un objeto <code>TipoImpuesto</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param impuesto Objeto <code>TipoImpuesto</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoImpuesto
	*/
	public boolean eliminarTipoImpuesto (TipoImpuesto impuesto) throws DAOException;



	/**
	* Servicio que permite eliminar un objeto <code>SubtipoAtencion</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param atencion Objeto <code>SubtipoAtencion</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SubtipoAtencion
	*/
	public boolean eliminarSubtipoAtencion (SubtipoAtencion atencion) throws DAOException;



	/**
	* Servicio que permite eliminar un objeto <code>TipoActo</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param acto Objeto <code>TipoActo</code> que va a ser eliminado.
	* @param usuario que va a eliminar
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoActo
	*/
	public boolean eliminarTipoActo (TipoActo acto, Usuario usuario) throws DAOException;



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
	public boolean validarNotaDevolutiva (String faseId, String respuesta, long procesoId) throws DAOException;

	
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
	public ValidacionNota getNotaDevolutiva(String faseId, String respuesta, long procesoId) throws DAOException;

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
	public ValidacionNotaPk addValidacionNota (ValidacionNota valNota) throws DAOException;



	/**
	* Servicio que permite eliminar una <code>ValidacionNota</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param valNota la <code>ValidacionNota</code> que va a ser eliminada.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.ValidacionNota
	*/
	public boolean eliminarValidacionNota (ValidacionNota valNota) throws DAOException;


	/**
	* Obtiene una lista de objetos tipo <code>ValidacionNota </code>, organizada ascendentemente
	* según el id de la fase de la ValidacionNota.
	* @return una lista de objetos <code>ValidacionNota</code>
	* @throws DAOException.
	* @see gov.sir.core.negocio.modelo.ValidacionNota
	*/
	List getValidacionNotas() throws DAOException;


   /**
	* Adiciona subtipos de atención a un <code>Usuario</code>
	* <p>Utilizado desde las pantallas administrativas.</p>
	* @param usuario  <code>Usuario</code> al que se le adicionarán los subtipos
	* @param listaUsuarios  <code>List</code> con los ids de usuario que son calificadores
	* @param circulo  <code>Circulo</code> circulo al que pertenece el usuario que se le esta configurando el suubtipo de atención.
	* @return <code>boolean</code> si puede actualizar el usuario
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Usuario
	*/
	public boolean updateSubtiposAtencionEnUsuario (Usuario usuario, List listaUsuarios, Circulo circulo) throws DAOException;


	/**
	* Servicio que permite eliminar una <code>CausalRestitucion</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param valNota la <code>CausalRestitucion</code> que va a ser eliminada.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.CausalRestitucion
	*/
	public boolean eliminarCausalRestitucion(CausalRestitucion valNota) throws DAOException;



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
	public TipoCertificadoValidacionPk addTipoCertificadoValidacion (TipoCertificadoValidacion valNota)
		throws DAOException;


	/**
	* Servicio que permite eliminar una <code>TipoCertificadoValidacion</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param valNota la <code>TipoCertificadoValidacion</code> que va a ser eliminada.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.TipoCertificadoValidacion
	*/
	public boolean eliminarTipoCertificadoValidacion (TipoCertificadoValidacion valNota)
		throws DAOException;


	/**
	* Obtiene una lista de objetos tipo <code>TipoCertificadoValidacion </code> filtrada por <code>TipoCertificado</code>
	* @param tipoCertificado  <code>TipoCertificado</code> utilizado para el filtro
	* @return una lista de objetos <code>TipoCertificadoValidacion</code>
	* @throws DAOException.
	* @see gov.sir.core.negocio.modelo.TipoCertificadoValidacion
	*/
	public List getTiposCertificadosValidacionByTipoCertificado(TipoCertificado tipoCertificado)
		throws DAOException;

	/**
	* Servicio que permite consultar la lista de objetos <code>Validacion</code> existentes en el sistema.
	* <p>Utilizado desde las pantallas administrativas.
	* @return <code>List</code> con la lista de validaciones disponibles en el sistema
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Validacion
	*/
	public List getValidaciones () throws DAOException;


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
	public TipoNotaPk addTipoNota (TipoNota tipoNota, Usuario usuario) throws DAOException;


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
	public boolean eliminarTipoNota(TipoNota valNota, Usuario usuario) throws DAOException;


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
	public CheckItemPk addCheckItem (CheckItem item) throws DAOException;

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
	public CheckItemPk updateCheckItem (CheckItem item) throws DAOException;


	/**
	* Servicio que permite eliminar un objeto <code>CheckItem</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param item la <code>CheckItem</code> que va a ser eliminada.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.CheckItem
	*/
	public boolean eliminarCheckItem(CheckItem item) throws DAOException;


	/**
	* Obtiene una lista de objetos tipo <code>CheckItem </code> filtrada por <code>SubtipoSolicitud</code>
	* @param subtipo  <code>SubtipoSolicitud</code> utilizado para el filtro
	* @return una lista de objetos <code>CheckItem</code>
	* @throws DAOException.
	* @see gov.sir.core.negocio.modelo.CheckItem
	* @see gov.sir.core.negocio.modelo.SubtipoSolicitud
	*/
	public List getCheckItemsBySubtipoSolicitud(SubtipoSolicitud subtipo) throws DAOException;


	/**
	* Obtiene una lista con los nombres de los tipos de visibilidad asociados con las notas
	* informativas existentes en el sistema.
	* @return Lista con los nombres de los tipos de visibilidad existentes en el sistema.
	* @throws DAOException.
	*/
	public List getTiposVisibilidad() throws DAOException;



	/**
	 * Finaliza el servicio Hermod
	 */
	public void finalizar() throws DAOException;




	/**
	* Obtiene una la lista de los Tipos de Notas existentes en
	* la configuración del sistema que están asociados con el <code>Proceso</code> pasado
	* como parámetro, la fase y si es devolutivo o informativa.
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
	public List getTiposNotaProceso(ProcesoPk proceso, String fase, boolean devolutiva) throws DAOException;

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
	public List getTiposNotaProcesoByTpnaDevolutiva(ProcesoPk proceso, boolean devolutiva) throws DAOException;

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
	public List getTiposNotaProceso(ProcesoPk proceso, String fase) throws DAOException;



	/**
	* Actualiza un <code>TipoNota<code> en la configuración del sistema.<p>
	* <p>
	* @param tipoNota objeto <code>TipoNota</code> con sus atributos
	* @param usuario que va a modificar el ti[po de nota
	* @return identificador del TipoNota generado.
	* @throws <code>HermodException</code>
	* @see gov.sir.core.negocio.modelo.TipoNota
	*/
	public TipoNotaPk updateTipoNota (TipoNota tipoNota, Usuario usuario) throws DAOException;


	/**
	 * Obtiene los permisos de cerrección confirurados en el sistema
	 * @return Lista con objetos de tipo PermisoCorreccion
	 * @throws DAOException
	 */
	public List getPermisosCorreccion() throws DAOException;



	/**
	* Obtiene el listado de intereses jurídicos.
	* @return Listado de intereses jurídicos.
	* @throws DAOException
	*/
	public List getInteresesJuridicos () throws DAOException;



	/**
	* Obtiene el listado de pantallas administrativas visibles para un respectivo rol.
	* @param roles Listado de roles asociados con el usuario.
	* @return Listado de pantallas administrativas visibles para el rol recibido como parámetro.
	* @throws DAOException
	*/
	public List obtenerAdministrativasPorRol (List roles)throws DAOException;


	/**
	* Obtiene el listado de pantallas administrativas visibles para un respectivo rol y una
	* página.
	* @param roles Listado de roles asociados con el usuario.
	* @param pagina Pagina de la pantalla administrativa
	* @return Listado de pantallas administrativas visibles para el rol y página recibidos como parámetro.
	* @throws DAOException
	*/
	public List obtenerRolPantallasAdministrativasPorRolPagina (List roles, String pagina)throws DAOException;
	

	/**
	* Obtiene el listado de tipos de pantallas administrativas existentes en la aplicación.
	* @return Listado de tipos de pantallas administrativas.
	* @throws DAOException
	*/
	public List obtenerTiposPantallasAdministrativas ()throws DAOException;


    /**
    * Obtiene el listado de pantallas administrativas visibles para un respectivo rol.
    * @param roles Listado de roles asociados con el usuario.
    * @return Listado de pantallas administrativas visibles para el rol recibido como parámetro.
    * @throws DAOException
    */
    public List obtenerPantallasPaginaReportesPorRol( List roles ) throws DAOException;
    
    
    /**
    * Obtiene el listado de items de chequeo para turnos de Devoluciones.
    * @return Listado de items de chequeo para turnos de devoluciones.
    * @throws DAOException
    */
    public List getItemsChequeoDevoluciones () throws DAOException;
    
    /**
     * Obiene el texto imprimible
     * @param idTexto identificador del texto
     * @return
     * @throws DAOException
     */
    public TextoImprimible getTextoImprimible(TextoImprimiblePk idTexto) throws DAOException;
    
    /**
     * Obtiene el listado de <code>PantallaAdministrativa</code> cuyo atributo
     * pagina sea igual al parámetro pagina
     * @param pagina
     * @return
     * @throws DAOException
     */
    public  List getPantallasAdministrativasByPagina (String pagina)
    	throws DAOException;
    
    
    /**
     * inserta <code>RolPantalla</code> en la base de datos
     * 
     * @param rolPantalla
     * @return
     * @throws DAOException
     */
    public  RolPantalla addRolPantallasAdministrativa (RolPantalla rolPantalla)
    throws DAOException;
    
    
    /**
     * inserta cada elemento <code>RolPantalla</code> de la lista
     * rolesPantallas en la base de datos
     * 
     * @param rolesPantallas
     * @return
     * @throws DAOException
     */
    public  void addRolesPantallasAdministrativas (List rolesPantallas)
    throws DAOException;
    
    
    /**
     * elimina <code>RolPantalla</code> de la base de datos
     * 
     * @param rolPantalla
     * @return
     * @throws DAOException
     */
    public  boolean deleteRolPantallasAdministrativa (RolPantalla rolPantalla)
    throws DAOException;
    
    /**
     * elimina cada elemento <code>RolPantalla</code> de la lista rolesPantalas
     * de la base de datos
     * 
     * @param rolesPantallas
     * @return
     * @throws DAOException
     */
    public  boolean deleteRolPantallasAdministrativa (List rolesPantallas)
    throws DAOException;

    /**
    * @author      :   Carlos Torres
    * @Caso Mantis :   14371: Acta - Requerimiento No 021_589_Rol_Registrador_Inhabilitar_Pantallas_Administrativas
	* Obtiene el listado de pantallas administrativas visibles para un respectivo usuario.
    * @param Usuario.
    * @return Listado de pantallas administrativas visibles para el Usuario recibido como parámetro.
    * @throws DAOException
    */
    public List obtenerAdministrativasPorRol (Usuario usuario)throws DAOException;
   /**
    * @author      :   Carlos Torres
    * @Caso Mantis :   14371: Acta - Requerimiento No 021_589_Rol_Registrador_Inhabilitar_Pantallas_Administrativas
    * Obtiene el listado de pantallas administrativas visibles para un respectivo rol.
    * @param roles Listado de roles asociados con el usuario.
    * @return Listado de pantallas administrativas visibles para el rol recibido como parámetro.
    * @throws DAOException
    */
    public List obtenerPantallasPaginaReportesPorRol( Usuario usuario ) throws DAOException;
}

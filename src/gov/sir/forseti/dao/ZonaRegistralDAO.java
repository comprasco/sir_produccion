/*
 * Created on 27-jul-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.forseti.dao;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import gov.sir.core.negocio.modelo.ActoresRepartoNotarial;
import gov.sir.core.negocio.modelo.ActoresRepartoNotarialPk;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoFestivo;
import gov.sir.core.negocio.modelo.CirculoFestivoPk;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.DepartamentoPk;
import gov.sir.core.negocio.modelo.Eje;
import gov.sir.core.negocio.modelo.EjePk;
import gov.sir.core.negocio.modelo.FirmaRegistrador;
import gov.sir.core.negocio.modelo.FirmaRegistradorPk;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.MunicipioPk;
import gov.sir.core.negocio.modelo.Texto;
import gov.sir.core.negocio.modelo.TextoPk;
import gov.sir.core.negocio.modelo.Usuario;

import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.VeredaPk;
import gov.sir.core.negocio.modelo.ZonaRegistral;
import gov.sir.core.negocio.modelo.ZonaRegistralPk;

/**
 * Interface que administra los datos geográficos del servicio Forseti:
 * Departamentos, Municipios, Veredas, Círculos y ZonaRegistrales
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface ZonaRegistralDAO {

    /**
     * Obtiene una lista con los departamentos registrados en el sistema
     * @return lista con objetos tipo Departamento, cada departamento tiene
     * sus atributos básicos
     * @see gov.sir.core.negocio.modelo.Departamento
     * @throws DAOException
     */
    public List getDepartamentos() throws DAOException;

    /**
     * Obtiene un objeto Departamento dado su identificador
     * @param oid identificador del departamento
     * @return objeto Departamento con sus atributos básicos y municipios
     * @throws DAOException
     */
    public Departamento getDepartamento(DepartamentoPk oid)
        throws DAOException;

    /**
     * Adiciona un departamento a la configuración del sistema
     * @param datos objeto Departamento con sus atributos exceptuando su identificador
     * el cual es generado por el sistema y el usuario que crea el departamento
     * @return identificador de departamento generado
     * @throws DAOException
     */
    public DepartamentoPk addDepartamento(Departamento datos, Usuario usuario)
        throws DAOException;


	/**
	* Servicio que permite eliminar un objeto <code>Departamento</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param acto Objeto <code>Departamento</code> que va a ser eliminado y el usuario que lo elimina.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Departamento
	*/
	public boolean eliminarDepartamento (Departamento dato, Usuario usuario) throws DAOException;

    /**
     * Obtiene la lista de municipios de un departamento
     * @param oid identificador del departamento
     * @return lista de objetos Municipio
     * @see gov.sir.core.negocio.modelo.Municipio
     * @throws DAOException
     */
    public List getMunicipiosDepartamento(DepartamentoPk oid)
        throws DAOException;

    /**
     * Obtiene un objeto municipio dado su identificador
     * @param oid identificador del municipio
     * @return objeto Municipio con sus atributos básicos y veredas
     * @throws DAOException
     */
    public Municipio getMunicipio(MunicipioPk oid) throws DAOException;

    /**
     * Adiciona un municipio a un departamento dado
     * @param datos objeto municipio con sus atributos exceptuando su identificación
     * el cual es generado por el sistema
     * @param oid identificador del departamento
     * @param usuario que adiciona el municipio
     * @return identificación del municipio generada
     * @throws DAOException
     */
    public MunicipioPk addMunicipioToDepartamento(Municipio datos,
        DepartamentoPk oid, Usuario usuario) throws DAOException;


	/**
	* Servicio que permite eliminar un objeto <code>Municipio</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param acto Objeto <code>Municipio</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Municipio
	*/
	public boolean eliminarMunicipio (Municipio dato) throws DAOException;


    /**
     * Obtiene la lista de veredas del municipio dado
     * @param oid identificación del municipio
     * @return lista de objetos tipo Vereda
     * @see gov.sir.core.negocio.modelo.Vereda
     * @throws DAOException
     */
    public List getVeredasMunicipio(MunicipioPk oid) throws DAOException;

    /**
     * Obtiene un objeto Vereda dado su identificador
     * @param oid identificador de la vereda
     * @return objeto Vereda
     * @throws DAOException
     */
    public Vereda getVereda(VeredaPk oid) throws DAOException;

	/**
	* Servicio que permite eliminar un objeto <code>Vereda</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param acto Objeto <code>Vereda</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Vereda
	*/
	public boolean eliminarVereda (Vereda dato) throws DAOException;

    /**
     * Obtiene una lista de círculos de la configuración del sistema
     * @return lista de objetos Circulo
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws DAOException
     */
    public List getCirculos() throws DAOException;
    
    /**
     * Obtiene una lista de círculos de la configuración del sistema
     * Solo los circulos que operan en SIR y tienen firma activa
     * @return lista de objetos Circulo
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws DAOException
     */
    public List getCirculosFechaProd() throws DAOException;
    
    /**
     * Obtiene una lista de círculos que tienen configurada
     * la firma del  registrador
     * @return lista de objetos Circulo
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws DAOException
     */
    public List getCirculosFirmaRegistrador() throws DAOException;

    /**
     * Obtiene un objeto Circulo dado su identificador
     * @param oid identificador del circulo
     * @return objeto Circulo
     * @throws DAOException
     */
    public Circulo getCirculo(CirculoPk oid) throws DAOException;


	/**
	 * Adiciona un círculo a la configuración del sistema
	 * @param datos objeto Circulo con sus atributos exceptuando su identificación
	 * el cual es generado por el sistema
	 * @param datos
	 * @return identificación del círculo generado por el sistema
	 * @throws DAOException
	 */
    public CirculoPk addCirculo(Circulo datos, Usuario usuario) throws DAOException;


	/**
	* Servicio que permite eliminar un objeto <code>Circulo</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param acto Objeto <code>Circulo</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Circulo
	*/
	public boolean eliminarCirculo(Circulo dato, Usuario usuario) throws DAOException;

    /**
     * Obtiene las ZonaRegistrales configuradas en el sistema
     * @return lista de objetos tipo ZonaRegistral, cada uno tiene sus objetos vereda y circulo
     * @see gov.sir.core.negocio.modelo.ZonaRegistral
     * @throws DAOException
     */
    public List getZonaRegistrales() throws DAOException;


	/**
	 * Obtiene las ZonaRegistrales configuradas en el sistema para un círculo determinado
	 * @return lista de objetos tipo ZonaRegistral, cada uno tiene sus objetos vereda y circulo
	 * @see gov.sir.core.negocio.modelo.ZonaRegistral
	 * @throws DAOException
	 */
	public List getZonaRegistralesCirculo(CirculoPk oid) throws DAOException;



	/**
	 * Obtiene una zona registral dado su identificador
	 * @param oid identificador de la zona registral
	 * @return zona registral con su jerarquía completa: circulo, vereda, municipio y departamento
	 * @throws DAOException
	 */
	public ZonaRegistral getZonaRegistral(ZonaRegistralPk oid) throws DAOException;


    /**
     * Adiciona una ubicación a la configuración del sistema, la ZonaRegistral
     * debe tener asociado una vereda y un círculo
     * @param datos objeto ZonaRegistral con objetos Vereda y Circulo
     * @return identificador de la ubicación generada
     * @throws DAOException
     */
    public ZonaRegistralPk addZonaRegistral(ZonaRegistral datos) throws DAOException;


	/**
	* Servicio que permite eliminar un objeto <code>ZonaRegistral</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param acto Objeto <code>ZonaRegistral</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.ZonaRegistral
	*/
	public boolean eliminarZonaRegistral(ZonaRegistral dato) throws DAOException;


	/**
	 * Obtiene una lista con todos los ejes configurados en el sistema
	 * @return lista de objetos Eje
	 * @see gov.sir.core.negocio.modelo.Eje
	 * @throws DAOException
	 */
	public List getEjes() throws DAOException;

	/**
	 * Obtiene un objeto Eje dado su identificador
	 * @param oid identificador del eje
	 * @return objeto Eje
	 * @throws DAOException
	 */
	public Eje getEje(EjePk oid) throws DAOException;

	/**
	 * Adiciona un eje a la configuración del sistema
	 * @param datos objeto Eje con sus atributos exceptuando su identificador
	 * el cual es generado por el sistema
	 * @param usuario que adiciona el eje
	 * @return identificador de Eje generado
	 * @throws DAOException
	 */
	public EjePk addEje(Eje datos, Usuario usuario) throws DAOException;


	/**
	* Servicio que permite eliminar un objeto <code>Eje</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param acto Objeto <code>Eje</code> que va a ser eliminado.
	* @param usuario que elimina el eje
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Eje
	*/
	public boolean eliminarEje(Eje dato, Usuario usuario) throws DAOException;

	/**
	 * Adiciona una vereda a un municipio dado
	 * @param datos objeto municipio con sus atributos exceptuando su identificación
	 * el cual es generado por el sistema
	 * @param oid identificador del municipio
	 * @return identificación de la vereda generada
	 * @throws DAOException
	 */
	public VeredaPk addVeredaToMunicipio(Vereda ver,
		MunicipioPk oid) throws DAOException;

	/**
	 *
	 * @param circulo
	 * @param zona
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	public ZonaRegistralPk addZonaRegistralToCirculo(CirculoPk oid,
		ZonaRegistral datos) throws DAOException;

	/**
	 * Adiciona un festivo a un circulo dado
	 * @param datos objeto circulofestivo con sus atributos
	 * @param oid identificador del circulo
	 * @return identificación del circulofestivo generado
	 * @throws DAOException
	 */
	public CirculoFestivoPk addCirculoFestivo(CirculoPk oid,
		CirculoFestivo datos) throws DAOException;

	/**
	 * Obtiene los festivo configurados en el sistema para un círculo determinado
	 * @return lista de objetos tipo CirculoFestivo
	 * @see gov.sir.core.negocio.modelo.CirculoFestivo
	 * @throws DAOException
	 */
	public List getFestivosCirculo(CirculoPk oid) throws DAOException;

	/**
	 * Determina si la fecha ingresada esta configurada en el sistema como un
	 * festivo para un círculo dado
	 * @return boolean
	 * @see gov.sir.core.negocio.modelo.CirculoFestivo
	 * @throws DAOException
	 */
	public boolean isFestivo(Date fecha, CirculoPk oid) throws DAOException;


	/**
	 * Añade una firma
	 * @param archivo
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public boolean addFirmaRegistradorToCirculo(FirmaRegistrador firmaReg, CirculoPk oid)
		throws DAOException;



	/**
	 * Setea el flag activa a una firma registrador
	 * ACTIVA -> la firma esta activa
     * INACTIVA -> la firma esta inactiva, pero puede volverse a activar
     * INACTIVA_DEFINITIVA -> la firma esta inactiva y no se puede volver a activar.
     * @param oid
     * @param activa 0 -> INACTIVA , 1 -> ACTIVA, 2 -> INACTIVA_DEFINITIVA
	 * @return
	 * @throws DAOException
	 */
	public boolean setActivoFirmaRegistrador(FirmaRegistradorPk oid, int activa)
		throws DAOException;

	/**
	* Servicio que permite eliminar un objeto <code>CirculoFestivo</code>
	* <p>Utilizado desde las pantallas administrativas.
	* @param acto Objeto <code>CirculoFestivo</code> que va a ser eliminado.
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.CirculoFestivo
	*/
	public boolean eliminarCirculoFestivo(CirculoFestivo dato) throws DAOException;



	/**
	 * Retorna el archivo de la firma activa del círculo especificado
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public FirmaRegistrador getFirmaRegistradorActiva(CirculoPk oid, String cargo) throws DAOException;


	/**
	 * Actualiza los datos del círculo.
	 * @param cid
	 * @param dato
	 * @return
	 * @throws DAOException
	 */
	public boolean updateCirculo(CirculoPk cid, Circulo dato, Usuario usuario) throws DAOException;


	/**
	 * Obtiene una lista con todas las impresoras del círculo
	 * @return lista de objetos CirculoImpresora
	 * @see gov.sir.core.negocio.modelo.CirculoImpresora
	 * @throws DAOException
	 */
	public List getCirculoImpresoras(String idCirculo) throws DAOException;


	/**
	 * Elimina la lista de impresoras configuradas para el círculo
	 * @param idCirculo
	 * @return
	 * @throws DAOException
	 */
	public boolean eliminarCirculoImpresoras(String idCirculo) throws DAOException;

	/**
	 * Adiciona una lista de impresoras a la lista actual de impresoras del círculo
	 * @return lista de objetos CirculoImpresora con la lista total de impresoras
	 * @see gov.sir.core.negocio.modelo.CirculoImpresora
	 * @throws DAOException
	 */
	public List addListaCirculoImpresoras(String idCirculo, List impresoras) throws DAOException;


	/**
	 * Cuenta los días NO hábiles configurados en el sistema desde la fecha inicial incluyéndola
	 * hasta la fecha final excluyéndola. Número Días no hábiles entre: [fehainicial, fechaFinal)
	 * @return long
	 * @throws DAOException
	 */
	public long countDiasNoHabiles(CirculoPk cirID, Date fechaInicial, Date fechaFinal) throws DAOException;



    /**
     * Obtiene un objeto Texto dado su identificador
     * @param oid identificador del circulo
     * @return objeto Circulo
     * @throws DAOException
     */
    public Texto getTexto(TextoPk oid) throws DAOException;



    /**
     * Adiciona un texto a la configuración del sistema
     * @param datos objeto Circulo con sus atributos exceptuando su identificación
     * el cual es generado por el sistema
     * @param datos
     * @return identificación del texto generado por el sistema
     * @throws DAOException
     */
    public TextoPk addTexto(Texto texto) throws DAOException;



    /**
     * Actualiza los datos del texto.
     * @param cid
     * @param dato
     * @return
     * @throws DAOException
     */
    public boolean updateTexto(TextoPk cid, String nuevoTexto) throws DAOException;


	/**
	 * Obtiene una lista con todas los tipos de imprimibles
	 * @return lista de objetos TipoImprimible
	 * @see gov.sir.core.negocio.modelo.TipoImprimible
	 * @throws DAOException
	 */
	public List getTiposImprimible() throws DAOException;


	/**
	 * Retorna la lista de impresoras por círculo y tipo de imprimible
	 * @param idCirculo
	 * @param idTipoImprimible
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	public List getCirculoImpresorasXTipoImprimible(String idCirculo, String idTipoImprimible) throws DAOException;


	/**
	 * Obtiene la configuracion de impresoras del círculo por tipo de imprimible
	 * @return lista de objetos CirculoImpresora
	 * @see gov.sir.core.negocio.modelo.CirculoImpresora
	 * @throws DAOException
	 */
	public Hashtable getConfiguracionImpresoras(String idCirculo) throws DAOException;



	/**
	 * Cambia la configuracion de impresoras del círculo por tipo de imprimible
	 * @return lista de objetos CirculoImpresora
	 * @see gov.sir.core.negocio.modelo.CirculoImpresora
	 * @throws DAOException
	 */
	public boolean setConfiguracionImpresoras(Hashtable nuevaConfiguracion, String idCirculo) throws DAOException;

	/**
	 * getCirculosByUsuario
         * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarDeltaSegunAnotacionDefinitiva( gov.sir.core.negocio.modelo.Usuario.UsuarioPk )
	 * @param oid ID
	 * @return List
	 */
	public List getCirculosByUsuario( gov.sir.core.negocio.modelo.UsuarioPk oid ) throws DAOException;

	/**
     * inhabilita un círculo
     * @param circuloOrigen círculo que se quiere inhabilitar
     * @param circuloDestino círculo donde se va a asociar la información del círculo origen
     * @param zonasRegistrales nuevas zonas registrales a asociar
     * @param usuariosCrear usuarios que se tienen que crear
     * @param usuariosTrasladar usuarios que se tienen que trasladar
     * @param usuariosRolConsulta usuarios a los que se le van a crear los roles
     * @param usuarioInhabilita usuario que inhabilita el circulo
     * @throws DAOException
     */
	public void inhabilitarCirculo (Circulo circuloOrigen, Circulo circuloDestino, List zonasRegistrales, Map usuariosCrear, List usuariosTrasladar, List usuariosRolConsulta, Usuario usuarioInhabilita) throws DAOException;

	
	/**
     * Regresa la lista de los círculos origen que fueron inhabilitados y asociados al circulo destino
     * @param circuloDestino círculo al que fue asociado el círculo inhabilitado
     * @return
     * @throws DAOException
     */
    public List getCirculosInhabilitados(Circulo circuloDestino) throws DAOException;

	/**
	 * @param circuloOrigen
	 * @param circuloDestino
	 * @param trasladoMasivoInicio
	 * @param trasladoMasivoFin
	 */
	public void trasladarFolios(Circulo circuloOrigen, Circulo circuloDestino, int trasladoMasivoInicio, int trasladoMasivoFin)throws DAOException;
	
    /**
     * Obtiene un objeto Texto dado su identificador
     * @param oid identificador del circulo
     * @return objeto Circulo
     * @throws DAOException
     */
    public ActoresRepartoNotarial getActoresRepartoNotarial(ActoresRepartoNotarialPk oid) throws DAOException;

}

/*
 * Interfaz para el manejo de los recibos que son impresos para
 * registrar los pagos realizados por los usuarios del sistema. 
 */
package gov.sir.hermod.dao;

import java.util.List;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.EstacionRecibo;
import gov.sir.core.negocio.modelo.EstacionReciboPk;
import gov.sir.core.negocio.modelo.Usuario;

/**
 * Interfaz para el manejo de los recibos que son impresos para
 * registrar los pagos realizados por los usuarios del sistema. 
 * @author  fceballos, dlopez
 */
public interface RecibosDAO {

	/**
	 * Obtiene un objeto <code>EstacionRecibo</code> estaci�n recibo dado su
	 * identificador.
	 * @param oid Identificador de la <code>EstacionRecibo</code> que quiere ser
	 * recuperada.
	 * @return La <code>EstacionRecibo</code> asociada con el identificador pasado
	 * como par�metro.
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.EstacionRecibo
	 */
	public EstacionRecibo getEstacionRecibo(EstacionReciboPk oid)
		throws DAOException;
	
	/**
	 * Obtiene un objeto <code>EstacionRecibo</code> estaci�n recibo dado su
	 * identificador.
	 * @param oid Identificador de la <code>EstacionRecibo</code> que quiere ser
	 * recuperada.
	 * @return La <code>EstacionRecibo</code> asociada con el identificador pasado
	 * como par�metro.
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.EstacionRecibo
	 */
	public EstacionRecibo getEstacionRecibo(EstacionReciboPk oid, long numeroProceso)
		throws DAOException;



	/**
	 * Setea una <code>EstacionRecibo</code> a la configuraci�n del sistema.
	 * <p>
	 * @param datos La <code>EstacionRecibo</code> que va a ser insertada.
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.EstacionRecibo
	 */
	public void setEstacionRecibo(CirculoPk circuloID, EstacionRecibo datos, Usuario user)
		throws DAOException;

	/**
	* Resetea el �ltimo n�mero del recibo de la estacion. Recibe los nueno �ltimo n�mero del recibos. 
	* @param oid Identificador de la <code>EstacionRecibo</code> cuyo secuencial va a
	* ser reseteado.
	* @param ultimoNumeroActualizado Nuevo valor para el �ltimo n�mero de la <code>EstacionRecibo</code>.
	* @return true o false dependiendo del resultado de la operaci�n. 
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.EstacionRecibo
	*/
	public boolean resetUltimoNumeroEstacionRecibo(EstacionReciboPk oid,
		long ultimoNumeroActualizado) throws DAOException;
 
	/**
	* Obtiene el siguiente n�mero de recibo seg�n la secuencia configurada para
	* una estaci�n en particular.
	* @param oid Identificador de la <code>EstacionRecibo</code> de la cual se desea
	* obtener el siguiente n�mero de recibo.
	* @return El siguiente n�mero de recibo asociado con la estaci�n cuyo identificador
	* fue recibido como par�metro. 
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.EstacionRecibo
	*/
	public long getNextNumeroRecibo(EstacionReciboPk oid, Usuario user, long idProceso)
		throws DAOException;
	
     /**
	 * Obtiene una lista de los objetos <code>EstacionRecibo</code>
	 * existentens en el sistema.
	 * @return una lista de objetos <code>EstacionRecibo</code> existentes en el sistema.
	 * @throws DAOException
	 * @see gov.sir.core.negocio.modelo.EstacionRecibo
	 */
	public List getEstacionesRecibo() throws DAOException;
	
	
	/**
	 * Consulta las <code>EstacionRecibo</code> para un c�rculo espec�fico
	 * @return la lista de los objetos <code>EstacionRecibo</code> solicitados
	 * @throws DAOException
	 */
	public List consultarEstacionesReciboPorCirculo(Circulo circulo) throws DAOException;
	
	/**
	* Servicio que permite eliminar una <code>EstacionRecibo</code>
	* <p>Utilizado desde las pantallas administrativas. 
	* @param categoria La <code>EstacionRecibo</code> que va a ser eliminada. 
	* @return <code>true</code> (�xito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operaci�n. 
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.EstacionRecibo
	*/
	public boolean eliminarEstacionRecibo (EstacionRecibo estacionRecibo)
		throws DAOException; 
		

	/**
	 * Obtiene el siguiente n�mero de recibo pero NO avanza la secuencia, si se 
	 * supera la secuencia devuelve -1
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public long getNextNumeroReciboSinAvanzar(EstacionReciboPk oid, Usuario user, long idProceso)
		throws DAOException;
		

	/**
	 * Avanza la secuencia de recibos en el avance configurado, si se supera la
	 * secuencia definida en la estaci�n se lanza una excepci�n
	 * @param oid
	 * @param avance
	 * @return
	 * @throws DAOException
	 */
	public long avanzarNumeroRecibo(EstacionReciboPk oid, long avance, long idProceso)
		throws DAOException;
		



	/**
	 * Hace persistente el motivo por el cual se increment� el secuencial
	 * de un recibo. 
	 * @param usuario Usuario que incrementa el secuencial.
	 * @param secuencial Valor al cual se incrementa el secuencial de recibos
	 * @param motivo Motivo por el cual se increment� el secuencial del recibo.
	 * @return <code>true</code> o <code>false</code> dependiendo del resultado
	 * de la operaci�n. 
	 * @throws DAOException
	 */
	public boolean almacenarMotivoIncrementoSecuencial(Usuario usuario, long secuencial, String motivo)
		throws DAOException;	
}

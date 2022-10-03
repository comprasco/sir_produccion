/*
 * Created on 19-ene-2005
 *
 */
package gov.sir.print.interfaz;

import java.util.Date;
import java.util.List;

import gov.sir.core.negocio.modelo.Imprimible;
import gov.sir.core.negocio.modelo.ImprimiblePdf;
import gov.sir.print.server.OrdenImpresion;
import gov.sir.print.server.PrintJobsException;

/**
 * @author dcantor
 *
 */
public interface PrintJobsInterface {
	
	
	/**
	 * @param UID
	 * @param b
	 * @param intentos
	 * @param espera
	 * @throws Throwable
	 */
	public void enviar(String UID, gov.sir.print.common.Bundle b, int intentos, int tiempoespera) throws Throwable;
	
	/**
	 * @param UID
	 * @param b
	 * @param intentos
	 * @param espera
	 * @param ordenImpresion
	 * @param ordenActual
	 * @throws Throwable
	 */
	public void enviar(String UID, gov.sir.print.common.Bundle b, int intentos, int tiempoespera, OrdenImpresion ordenImpresion, int ordenActual) throws Throwable;	
	
	/**
	 * @throws Throwable
	 */
	public void start() throws Throwable;
	
	/**
	 * @throws Throwable
	 */
	public void stop() throws Throwable;
	
	/**
	* Obtiene el valor del tiempo de vida de un Socket para la impresión.
	* @param tipo El LookupType de la variable. 
	* @param codigo El LookupCode de la variable. 
	* @return El valor de la variable.
	* @throws Throwable
	*/
	public String getTimeOutSocket(String codigo) throws Throwable;
	
	
	/**
	 * Guarda el imprimiblepdf en la base de datos.
	 * @param imprimible, que contiene el imprimible y el número de impresiones.
	 * @return
	 * @throws DAOException
	 */
	public int agregarImprimiblePDF (gov.sir.print.common.Bundle b, boolean guardarPDF) throws PrintJobsException;
	
	/**
	 * Guarda el imprimible en la base de datos.
	 * @param imprimible, que contiene el imprimible y el número de impresiones.
	 * @return
	 * @throws DAOException
	 */
	public int agregarImprimible (Imprimible imprimible) throws PrintJobsException;
	
	/**
	 * Elimina el imprimible de la base de datos
	 * @param id
	 * @return
	 * @throws DAOException
	 */

	public boolean eliminarImprimible (int id) throws PrintJobsException;
	
	/**
	 * Obtiene el imprimible de la base de datos a partir del id.
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public Imprimible getImprimible (int id) throws PrintJobsException;
	
	/**
	 * Obtiene el imprimible si este no ha sido impreso previamente
	 * @param id
	 * @return
	 * @throws PrintJobsException
	 */
	public Imprimible getImprimibleNoImpreso(int id) throws PrintJobsException;
	
	/**
	 * Obtiene el imprimible de la base de datos a partir del id.
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public ImprimiblePdf getImprimiblePdf (String idWorkflow) throws PrintJobsException;
	
	/**
	 * Actualiza el imprimible en la base de datos.
	 * @param imprimible, que contiene los cambios que quieren actualizarse.
	 * @return
	 * @throws DAOException
	 */
	public boolean updateImprimible (Imprimible imprimible) throws PrintJobsException;
	
	/**
	 * Obtiene la Lista con los Identificadores de las impresiones fallidas para un UID.
	 * @param List. Lista con los Identificadores de las impresiones fallidas
	 * @return
	 * @throws DAOException
	 */
	public List getImpresionesFallidas(Imprimible imprimible, boolean isAdministrador) throws PrintJobsException;	

	/**
	* Obtiene el valor del tiempo de retardo de la consulta de reimpresión de fallidos
	* por parte del administrador de impresión
	* @param tipo El LookupType de la variable. 
	* @param codigo El LookupCode de la variable. 
	* @return El valor de la variable.
	* @throws Throwable
	*/
	public int getRetardoConsultaReimpresion () throws PrintJobsException;
	
	/**
	 * Verifica si el administrado de impresión identificado con UID
	 * está activo. Esta verificación se hace comparando la fecha actual
	 * contra la última consulta de impresiones fallidas por parte del
	 * administrador de impresion
	 * @param UID
	 * @return
	 * @throws PrintJobsException
	 */
	public boolean estaActivoAdministrador (String UID) throws PrintJobsException;
	
	/**
	 * Consulta fecha de la última verificación del administrador de impresiones
	 * identificado con UID de impresiones fallidas
	 * @param UID
	 * @return
	 * @throws PrintJobsException
	 */
	public Date getUltimaConsultaReimpresión(String UID) throws PrintJobsException;
	
	/**
	* Obtiene la lista de roles que verán la alerta de administrado de impresión caido
	* @return Lista de Objetos String con el id del rol
	* @throws PrintJobsException
	*/
	public List getRolesAlertaAdmImpInactivo () throws PrintJobsException;
}

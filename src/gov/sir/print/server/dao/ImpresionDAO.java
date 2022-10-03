/*
 * Created on 14-jul-2005
 *
 */
package gov.sir.print.server.dao;

import java.util.Date;
import java.util.List;

import gov.sir.core.negocio.modelo.Impresion;
import gov.sir.core.negocio.modelo.ImpresionPk;
import gov.sir.core.negocio.modelo.Imprimible;
import gov.sir.core.negocio.modelo.ImprimiblePdf;
import gov.sir.core.negocio.modelo.ImprimiblePk;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.jdogenie.PrefabricadoPk;

import gov.sir.forseti.ForsetiException;

/**
 * @author fceballos, ppabon
 *
 */
public interface ImpresionDAO {
	
	
	/**
	 * Añade una sesión de impresión.
	 * @param sesionImpresion
	 * @return
	 * @throws DAOException
	 */
	public boolean addSesionImpresion(Impresion sesionImpresion) throws DAOException;
	
	
	/**
	 * Borra una sesión de impresión.
	 * @param sesionImpresion
	 * @return
	 * @throws DAOException
	 */
	public boolean deleteSesionImpresion(Impresion sesionImpresion) throws DAOException;	
    
    /**
	 * Author: Ingeniero Diego Hernandez
     * Modificado en 2010/02/23 by jvenegas
	 */
      public byte[] getImprimibleBytes(ImprimiblePk id) throws DAOException;
	
	/**
	 * Retorna la sesión de impresión dado su uid. Si no existe retorna null
	 * @param uid
	 * @return
	 * @throws DAOException
	 */
	public Impresion getSesionImpresion(ImpresionPk uid) throws DAOException;
	
	/**
	 * Guarda el imprimible en la base de datos.
	 * @param imprimible, que contiene el imprimible y el número de impresiones.
	 * @return
	 * @throws DAOException
	 */
	public int guardarImprimible(Imprimible imprimible) throws DAOException;
	
	/**
	 * Guarda el imprimible certificado en la base de datos.
	 * @param imprimible, que contiene el imprimible con la informacion del folio.
	 * @return
	 * @throws DAOException
	 */
	public void guardarPrefabricado(ImprimibleCertificado imprimible) throws DAOException;
		 
	/**
	 * Registra la obsolecencia de un certificado de tradicion.
	 * @param imprimible.
	 * @return
	 * @throws DAOException
	 */
	public void registrarObsoleto(String idCirculo, String idMatricula);
	
	/**
	 * Guarda el imprimiblePDF en la base de datos.
	 * @param imprimible, que contiene el imprimible y el número de impresiones.
	 * @return
	 * @throws DAOException
	 */
	public int guardarImprimiblePDF(Imprimible imprimible,gov.sir.print.common.Imprimible imprimibleTemp, boolean guardarPDF, byte[] bytesFormularioPdf) throws DAOException;
	
	/**
	 * Elimina el imprimible de la base de datos
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public boolean eliminarImprimible(int id) throws DAOException;
	
	/**
	 * Obtiene el imprimible de la base de datos a partir del id.
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public Imprimible getImprimible(ImprimiblePk id) throws DAOException;
	
    /**
     * Obtiene una lista con las matriculas que han sido marcadas como obsoletas
     * @param circulo circulo a actualizar
     * @param circulo numero de la matricula minimo
     * @param circulo numero de la matricula maximo
     *  
     * @return lista con las matriculas obsoletas
     * @see gov.sir.core.negocio.modelo.jdogenie.ObsoletoEnhanced
     * @throws ForsetiException cuando ocurre un error al consultar las matriculas obsoletas.
     */
	public List getObsoletos(String circulo, int cantidad) throws DAOException;
	/**
	 * Obtiene el certificado de la base de datos a partir del id.
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public Imprimible getPrefabricadoById(PrefabricadoPk id) throws DAOException;
	
	/**
	 * Obtiene el imprimible si este no ha sido impreso previamente
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public Imprimible getImprimibleNoImpreso(ImprimiblePk id) throws DAOException;
	
	/**
	 * Obtiene el imprimible de la base de datos a partir del id.
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public ImprimiblePdf getImprimiblePdf(String idWorkflow) throws DAOException;
	
	/**
	 * Actualiza el imprimible de la base de datos a partir del id.
	 * @param imprimible
	 * @return
	 * @throws DAOException
	 */
	public boolean updateImprimible(Imprimible imprimible) throws DAOException;	
	
	/**
	 * Obtiene la Lista con los Identificadores de las impresiones fallidas para un UID.
	 * @param List. Lista con los Identificadores de las impresiones fallidas
	 * @return
	 * @throws DAOException
	 */
	public List getImpresionesFallidas(Imprimible imprimible, boolean isAdministrador) throws DAOException;
		
	
	/**
	 * Obtiene el valor de una variable, dado su lookupType y su LookupCode
	 * @param tipo El LookupType de la variable. 
	 * @param codigo El LookupCode de la variable. 
	 * @return El valor de la variable.
	 * @throws DAOException
	 */
	 String getValor(String tipo, String codigo) throws DAOException;	
	
	/**
	 * Finaliza ImpresionDAO
	 * @throws DAOException
	 */
	public void finalizar() throws DAOException;
	
	
	/**
	 * Consulta la fecha de la última reimpresión del administrado de impresión
	 * identificado con UID
	 * @param UID
	 * @return
	 * @throws DAOException
	 */
	public Date getUltimaConsultaReimpresión(String UID) throws DAOException;
	
	
	/**
	* Obtiene la lista de variables, dado su lookupType
	* @param tipo El LookupType de la variable. 
	* @return Lista de objetos OPLookupCcodes.
	* @throws DAOException
	*/
	public List getValor(String tipo) throws DAOException;
	

}

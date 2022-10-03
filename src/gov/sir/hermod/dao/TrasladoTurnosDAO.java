package gov.sir.hermod.dao;

import gov.sir.core.negocio.modelo.Traslado;
import org.auriga.core.modelo.transferObjects.Estacion;

import gov.sir.core.negocio.modelo.Turno;


/**
 *
 * @author jmendez
 */
public interface TrasladoTurnosDAO {
    
	/**
	 * Traslada un turno a un usuario específico
	 * @param turno
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	boolean trasladarTurnoSAS(Turno turno, Estacion estacion) throws DAOException;

        /**
     * Servicio que permite conocer la historia de traslados de un folio
     * @param matricula Identificación del folio del cual se desea conocer la historia de traslados
     * @return Objeto <code>TrasladoEnhanced</code> que contiene la historia de traslados del folio
     * @throws <code>DAOException</code>
     * @author      :  Julio Alcazar
     * @change      :  nuevos metodos de traslado folios
     * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
     */
    public Traslado getFolioOrigenTraslado(String matricula) throws DAOException;
     /**
     * Servicio que permite conocer la historia de traslados de un folio
     * @param matricula Identificación del folio del cual se desea conocer la historia de traslados
     * @return Objeto <code>TrasladoEnhanced</code> que contiene la historia de traslados del folio
     * @throws <code>DAOException</code>
     * @author      :  Julio Alcazar
     * @change      :  nuevos metodos de traslado folios
     * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
     */
    public Traslado getFolioDestinoTraslado(String matricula) throws DAOException;


}
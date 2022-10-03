/*
 * CatastroDAO.java
 *
 * Created on 14 de marzo de 2005, 12:13
 */

package gov.sir.forseti.dao;

import java.util.Date;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Usuario;

/**
 *
 * @author  jalvarez
 */
public interface CatastroDAO {
    
    /**
     * Servicio que permite ejecutar el proceso de catastro para una 
     * oficina (círculo) específica
     * @param fechIni Fecha de inicio del proceso
     * @param fechFin Fecha de finalización del proceso
     * @param circulo Círculo registral (oficina) del proceso
     * @param us Usuario que solicita la ejecución del proceso
     * @return True: ejecución exitosa; False: ejecución no exitosa
     * @throws <code>DAOException</code>
     */
    public boolean catastro(Date fechIni, Date fechFin, Circulo circulo, Usuario us) throws DAOException;
}
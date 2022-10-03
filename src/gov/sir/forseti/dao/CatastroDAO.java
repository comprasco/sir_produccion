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
     * oficina (c�rculo) espec�fica
     * @param fechIni Fecha de inicio del proceso
     * @param fechFin Fecha de finalizaci�n del proceso
     * @param circulo C�rculo registral (oficina) del proceso
     * @param us Usuario que solicita la ejecuci�n del proceso
     * @return True: ejecuci�n exitosa; False: ejecuci�n no exitosa
     * @throws <code>DAOException</code>
     */
    public boolean catastro(Date fechIni, Date fechFin, Circulo circulo, Usuario us) throws DAOException;
}
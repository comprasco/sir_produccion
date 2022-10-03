package gov.sir.forseti.dao;

import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;

/**
 * Interface que administra los servicios de migraci�n incremental de Folios y Turnos.
 * @author gvillalba
 * Germ�n Villalba F.
 * gvillalba@1.com
 */
public interface MigracionIncrementalDAO {
	
    /**
     * Realiza la migraci�n incremental de un Folio dado el n�mero de matr�cula.
     * @param matricula n�mero de matr�cula del folio
     * @throws DAOException
     */
    public String migrarFolioByMatricula(String matricula, Usuario usuario) throws DAOException;
    
    /**
     * Realiza la migraci�n incremental de un Turno dado el n�mero del turno.
     * @param numero del turno
     * @throws DAOException
     */
    public Turno migrarTurnoNumero(String numTurno) throws DAOException;

}

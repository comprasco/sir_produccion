package gov.sir.forseti.dao;

import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;

/**
 * Interface que administra los servicios de migración incremental de Folios y Turnos.
 * @author gvillalba
 * Germán Villalba F.
 * gvillalba@1.com
 */
public interface MigracionIncrementalDAO {
	
    /**
     * Realiza la migración incremental de un Folio dado el número de matrícula.
     * @param matricula número de matrícula del folio
     * @throws DAOException
     */
    public String migrarFolioByMatricula(String matricula, Usuario usuario) throws DAOException;
    
    /**
     * Realiza la migración incremental de un Turno dado el número del turno.
     * @param numero del turno
     * @throws DAOException
     */
    public Turno migrarTurnoNumero(String numTurno) throws DAOException;

}

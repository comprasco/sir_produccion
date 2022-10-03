/*
 * Created on 14-jul-2005
 *
 */
package gov.sir.forseti.dao;

import java.util.List;

import gov.sir.core.negocio.modelo.AnotacionPk;
import gov.sir.core.negocio.modelo.FolioDerivado;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;

/**
 * @author fceballos
 *
 */
public interface FolioDerivadoDAO {
	
	
	/**
	 * Desasocia dos folios que se encuentran encadenados a través de cada una de sus anotaciones
	 * @param anotaID1
	 * @param anotaID2
	 * @return
	 * @throws DAOException
	 */
	public boolean desasociarFolios(AnotacionPk anotaID1, AnotacionPk anotaID2, Usuario usuario) throws DAOException;
	

	
	/**
	 * Asocia dos folios a través de sus anotaciones generadora y derivada
	 * @param anotaGeneradoraID
	 * @param anotaDerivadaID
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public boolean asociarFolios(AnotacionPk anotaGeneradoraID, AnotacionPk anotaDerivadaID, Usuario usuario,Turno turno) throws DAOException;
	
	/**
	 * Asociar dos folios que se encuentran encadenados a través de cada una de sus anotaciones
	 * @param anotaID1
	 * @param anotaID2
	 * @return
	 * @throws DAOException
	 */
	public boolean asociarFoliosAdministrativa(AnotacionPk anotaID1, AnotacionPk anotaID2, Usuario usuario, int tipo) throws DAOException;

	/**
	 * DesAsociar dos folios que se encuentran encadenados a través de cada una de sus anotaciones
	 * @param anotaID1
	 * @param anotaID2
	 * @return
	 * @throws DAOException
	 */
	public boolean desasociarFoliosAdministrativa(AnotacionPk anotaID1, AnotacionPk anotaID2, Usuario usuario, int tipo) throws DAOException;


	/**
	 * Devuelve la relación entre un folio padre y un folio hijo, si no existe retorna null.
	 * @param folioIDPadre
	 * @param folioIDHijo
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public FolioDerivado getFolioDerivadoEnlace(FolioPk folioIDPadre, FolioPk folioIDHijo, Usuario usuario) throws DAOException;
	
	/**
	 * Devuelve el folio derivado a partir del folio derivado hijo entre un folio padre y un folio hijo, si no existe retorna null.
	 * @param folioIDHijo
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public FolioDerivado getFolioDerivadoHijo(FolioPk folioIDHijo, Usuario usuario) throws DAOException;
	
	/**
	 * Retorna una lista de folios derivados temporales para una matrícula padre
	 * @param oid
	 * @return
	 * @throws Throwable
	 */
	public List getFoliosDerivadosTMPByMatricula(String matriculaPadre) throws DAOException;
	
	
}

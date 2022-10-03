/*
 * TrasladoDAO.java
 *
 * Created on 21 de enero de 2005, 16:15
 */

package gov.sir.forseti.dao;

import java.util.List;

import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Usuario;

/**
 *
 * @author  Administrador
 */
public interface TrasladoDAO {
    
    /**
     * Servicio que permite el traslado de folios
     * @param folioOrigen Información del folio original
     * @param folioDestino Información del folio destino: id y zona registral
     * @param us Usuario que realiza el traslado
     * @return Objeto <code>Folio</code> con la información del folio trasladado
     * @throws <code>DAOException</code>
     */
    public Folio trasladarFolio(Folio folioOrigen, Folio folioDestino, Usuario us, String resolucion) throws DAOException;
    
    /**
     * Servicio que permite conocer la historia de traslados de un folio
     * @param idFolio Identificación del folio del cual se desea conocer la historia de traslados
     * @return Objeto <code>List</code> que contiene la historia de traslados del folio
     * @throws <code>DAOException</code>
     */
    public List consultarTrasladosMatricula(FolioPk idFolio) throws DAOException;
}
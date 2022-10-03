/*
 * Clase para el manejo de las fases (actividades) asociadas con los flujos
 * de trabajo de los procesos de la aplicación. 
 * Created on 12 de julio de 2004, 9:10
 */
 
package gov.sir.hermod.dao;

import java.util.HashMap;

/**
 * Clase para el manejo de las fases (actividades) asociadas con los flujos
 * de trabajo de los procesos de la aplicación. 
 * @author  mrios, mortiz, dlopez
 */
public interface ReportesJasperDAO {
    
	public byte[] generarReporteJasper(String nombreReporte,
			String rutaReportes, HashMap parametrosJasper) throws DAOException;	 
	
}
package gov.sir.hermod.dao.impl.jdogenie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


import gov.sir.core.negocio.modelo.constantes.CListadosPorRangos;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced;
import gov.sir.hermod.dao.DAOException;
import javax.jdo.PersistenceManager;

public class JDOListadosPorPaginasDAO extends AbstractJDOListadosPorPaginasDAO {
	
	public List consultarListadoPorRangos (String idListado, Object[] parametros, int rangoInf, int rangoSup)
	throws DAOException
	{
		List listado = null;
		if (idListado.equals(CListadosPorRangos.LISTADO_TURNOS_EJECUCION))
		{
			listado = consultarListadoTurnosEjecucion(idListado, parametros, rangoInf, rangoSup);
		}
		return listado;
	}
	
	public int consultarTamanioListado (String idListado, Object[] parametros)
	throws DAOException
	{
		int tamanio = 0;
		if (idListado.equals(CListadosPorRangos.LISTADO_TURNOS_EJECUCION))
		{
			tamanio = consultarTamanioTurnosEjecucion(idListado, parametros);
		}
		return tamanio;
	}

	
	private int consultarTamanioTurnosEjecucion(String idListado, Object[] parametros) 
	throws DAOException{
		String variables = "TurnoEjecucionEnhanced turnoEjecucion";
		String parametrosDeclarados = "String idCir, String fase, String estacion";
		String filtro = "this.idCirculo==idCir && " +
				"turnoEjecucion.idWorkflow == this.idWorkflow && " +
 				"turnoEjecucion.fase == fase && " +
 				"turnoEjecucion.estacion == estacion";
		return this.consultarTamanioConsulta(TurnoEnhanced.class, 
							variables, 
							parametrosDeclarados, 
							filtro, 
							parametros);
	}

	private List consultarListadoTurnosEjecucion(String idListado, Object[] parametros, int rangoInf, int rangoSup) 
	throws DAOException
	{
		List turnos = new ArrayList();
		String variables = "TurnoEjecucionEnhanced turnoEjecucion";
		String parametrosDeclarados = "String idCir, String fase, String estacion";
		String filtro = "this.idCirculo==idCir && " +
				"turnoEjecucion.idWorkflow == this.idWorkflow && " +
 				"turnoEjecucion.fase == fase && " +
 				"turnoEjecucion.estacion == estacion";
		String orden = "fechaInicio ascending";
		
		try {
			Collection turnosEnh = this.consultarPorRangos(
										TurnoEnhanced.class, 
										variables, 
										parametrosDeclarados, 
										filtro, 
										orden, 
										parametros,
										"TRNO_FECHA_INICIO", 
										"this.fechaInicio", 
										rangoInf+1,rangoSup);
			if (turnosEnh != null)
			{
				Iterator iter = turnosEnh.iterator();
		        TurnoEnhanced turno=null;
		        
		        while (iter.hasNext()) {
		            turno = (TurnoEnhanced) iter.next();
                                     /**
                                      * @author      :   Carlos Torres
                                      * @Caso Mantis :   11604: Acta - Requerimiento No 030_453_Funcionario_Fase_ Entregado
                                      */
                            if("REG_ENTREGA".equals(parametros[1]) || "COS_ENTREGAR_ASOCIADOS".equals(parametros[1]) || "REG_ENTREGA_EXTERNO".equals(parametros[1])){
                                PersistenceManager pm = AdministradorPM.getPM();
                                pm.makeTransient(turno);
                            }else{
                                query.getPersistenceManager().makeTransient(turno);
                            }
		            turnos.add(turno);
		        }
			}
		} catch(Throwable e)
		{
                             /**
                              * @author      :   Carlos Torres
                              * @Caso Mantis :   11604: Acta - Requerimiento No 030_453_Funcionario_Fase_ Entregado
                              */
			if(query!=null && query.getPersistenceManager().currentTransaction().isActive())
			{
				query.getPersistenceManager().currentTransaction().rollback();
			}
		}finally {
                             /**
                              * @author      :   Carlos Torres
                              * @Caso Mantis :   11604: Acta - Requerimiento No 030_453_Funcionario_Fase_ Entregado
                              */
			if(query!=null && query.getPersistenceManager().currentTransaction().isActive())
			{
				query.getPersistenceManager().currentTransaction().commit();
			}
		}
		
		turnos = TransferUtils.makeTransferAll(turnos);
		
		return turnos;
	}

}

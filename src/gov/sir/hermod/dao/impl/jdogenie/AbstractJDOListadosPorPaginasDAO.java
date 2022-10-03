package gov.sir.hermod.dao.impl.jdogenie;

import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.hermod.dao.DAOException;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.jdo.PersistenceManager;


import com.versant.core.jdo.VersantPersistenceManager;
import com.versant.core.jdo.VersantQuery;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhancedPk;

/**
 * Dao que facilita la paginación de datos
 * @author gruiz
 *
 */
public abstract class AbstractJDOListadosPorPaginasDAO implements Serializable{

	private String sqlRangos;
	private String claseDeObjeto;
	protected VersantQuery query;
	/**
	 * retorna la lista de objetos encontrados entre rangoInf y rangoSup
	 * de la consulta JDO ejecutada sobre la clasePrincipal y con variables
	 * declaradas variables, declaración de parametros declaredParameters, 
	 * filro de consulta filter, parametros de ejecución parameters. 
	 * sqlColumn indica cual es la columna en BD sobre la cual se hace el ordenamiento.
	 * jdoField indica el campo JDO sobre el cual se hace el ordenamiento. 
	 * Éste método modifica filter para hacer un filtrado en el que jdoFiled > el valor
	 * retornado en sqlColumn en la posición rangoInf y jdoFiled <= el valor
	 * retornado en sqlColumn en la posición rangoSup. 
	 * @param clasePrincipal clase Enhanced JDO sobre la cual se ejecutará la consulta.
	 * 			El método invocará pm.newQuery(clasePrincipal); 
	 * @param variables Declaración de variables para el filtro.
	 * 			El método invocará query.declareVariables(variables)
	 * @param declaredParameters Declaración de parámetros para el filtro.
	 * 			El método invocará query.declareParameters(declaredParameters)
	 * @param filter Filtro con el que se ejecutará la consulta.
	 * 			El método invocará query.setFilter(filter)
	 * @param ordering Ordenamiento con el que se ejecutará la consulta.
	 * 			El método invocará query.setOrdering(ordering)
	 * @param parameters Parámetros para la ejecución de la consulta.
	 * 			El método invocará query.executeWithArray(parameters)
	 * @param sqlColumn Columna sql con la que se puede hacer la búsqueda por rangos.
	 * 			El orden de esta columna debe coincidir con el de la consulta
	 * @param jdoField Expreción JDO que indica cual es el campo sobre el cual se 
	 * 			puede ejecutar el filtrado por rangos. El método añadirá a filter
	 * 			la expreción " && " + jdoField + " &gt; " + objInf +" && jdoField &lt;= " + objSup; 
	 * @param rangoInf Posición inferior de la sublista a obtener
	 * @param rangoSup Posición Superior de la sublista a obtener
	 * @return Collección de elementos retornados por la consulta entre el rango definido
	 * 		por [rangoInf, rangoSup).
	 * @throws DAOException
	 */
	protected Collection consultarPorRangos (Class clasePrincipal, String variables,  String declaredParameters, String filter, String ordering, Object []parameters, String sqlColumn, String jdoField, int rangoInf, int rangoSup) throws DAOException
	{
		Collection result = null;
		
		PersistenceManager pm = AdministradorPM.getPM();
		pm.currentTransaction().begin();
		
		VersantQuery query = (VersantQuery) pm.newQuery(clasePrincipal);
		query.declareVariables(variables);
		query.declareParameters(declaredParameters);
		query.setFilter(filter);
		query.setOrdering(ordering);
		
		result = consultarPorRangos(query, parameters, sqlColumn, jdoField, rangoInf, rangoSup);
		
		return result;
		
	}

	
	/**
	 * retorna la lista de objetos encontrados entre rangoInf y rangoSup
	 * de la consulta JDO ejecutada. 
	 * sqlColumn indica cual es la columna en BD sobre la cual se hace el ordenamiento.
	 * jdoField indica el campo JDO sobre el cual se hace el ordenamiento. 
	 * Éste método modifica filter para hacer un filtrado en el que jdoFiled > el valor
	 * retornado en sqlColumn en la posición rangoInf y jdoFiled <= el valor
	 * retornado en sqlColumn en la posición rangoSup. 
	 * @param query Instancia de VersantQuery previamente preparada.
	 * @param parameters Parámetros para la ejecución de la consulta.
	 * 			El método invocará query.executeWithArray(parameters)
	 * @param sqlColumn Columna sql con la que se puede hacer la búsqueda por rangos.
	 * 			El orden de esta columna debe coincidir con el de la consulta
	 * @param jdoField Expreción JDO que indica cual es el campo sobre el cual se 
	 * 			puede ejecutar el filtrado por rangos. El método añadirá a filter
	 * 			la expreción " && " + jdoField + " &gt;> " + objInf +" && jdoField &lt;= " + objSup; 
	 * @param rangoInf Posición inferior de la sublista a obtener
	 * @param rangoSup Posición Superior de la sublista a obtener
	 * @return Collección de elementos retornados por la consulta entre el rango definido
	 * 		por [rangoInf, rangoSup).
	 * @throws DAOException
	 */
	protected Collection consultarPorRangos (VersantQuery query, Object []parameters, String sqlColumn, String jdoField, int rangoInf, int rangoSup) throws DAOException
	{
		if (query.getOrdering() == null && this.query == null)
		{
			throw new DAOException("Para consultar por rangos se debe hacer ordenamiento");
		}
		if (this.query == null && query.getOrdering().equals(""))
		{
			throw new DAOException("Para consultar por rangos se debe hacer ordenamiento");
		}
		
		HashMap clavesPorRango = new HashMap();
		Collection result = null;
		if (sqlRangos == null)
		{
			String sql = query.getPlan(parameters).getDatastoreQuery();
			int lastIdxOfOrder = sql.lastIndexOf("ORDER BY");
			int idxFrom = sql.indexOf("FROM");
			String sqlOrder = sql.substring(lastIdxOfOrder);
			String sqlRowNumberPre1 = "SELECT A." + sqlColumn + " FROM (";
			String sqlRowNumberPre2 = ", ROW_NUMBER() OVER( ORDER BY ";
			String sqlRowNumberPos = ") AS ROW_NUMBER ";
			String sqlRowNumberFin = ") A WHERE ROW_NUMBER IN(?,?)";
			sqlRangos = sqlRowNumberPre1 + sql.substring(0,idxFrom)+ sqlRowNumberPre2 + sqlColumn + sqlRowNumberPos + sql.substring(idxFrom) + sqlRowNumberFin;
		}	
		
		Connection con = ((VersantPersistenceManager)query.getPersistenceManager()).getJdbcConnection(null);
		PreparedStatement pst = null;
                ResultSet          rs = null;
                      /**
                        * @author      :   Carlos Torres
                        * @Caso Mantis :   11604: Acta - Requerimiento No 030_453_Funcionario_Fase_ Entregado
                        */                      

		if(!("REG_ENTREGA".equals(parameters[1]) || "COS_ENTREGAR_ASOCIADOS".equals(parameters[1]) || "REG_ENTREGA_EXTERNO".equals(parameters[1]))){
                try {
			pst = con.prepareStatement(sqlRangos);
			int i= 0;
			for (; i < parameters.length; i++)
			{
				pst.setObject(i+1, parameters[i]);
			}
			pst.setInt(++i, rangoInf);
			pst.setInt(++i, rangoSup);
			rs = pst.executeQuery();
			
			claseDeObjeto = rs.getMetaData().getColumnClassName(1);
			 
			if(rs.next())
			{
				Object objeto = null;
				if( claseDeObjeto.equals(Timestamp.class.getName()))
				{
					objeto = rs.getTimestamp(1);
				}else {
					objeto = rs.getObject(1);
				}
				clavesPorRango.put(new Integer(rangoInf),objeto);
			} else {
				return new ArrayList();
			}
			
			if(rs.next())
			{
				Object objeto = null;
				if( claseDeObjeto.equals(Timestamp.class.getName()))
				{
					objeto = rs.getTimestamp(1);
				}else {
					objeto = rs.getObject(1);
				}
				clavesPorRango.put(new Integer(rangoSup),objeto);
			} else {
				if (rangoInf != rangoSup)
				{
					return new ArrayList();
				}
			}
			
			
		} catch (Exception e) {
			Log.getInstance().error(AbstractJDOListadosPorPaginasDAO.class,e);
		}  finally
		{
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }
			if (pst != null)
			{
				try {
					pst.close();
				} catch (SQLException e) {
				}
			}
		}
                }
		try
		{
                       /**
                        * @author      :   Carlos Torres
                        * @Caso Mantis :   11604: Acta - Requerimiento No 030_453_Funcionario_Fase_ Entregado
                        */                      
			if("REG_ENTREGA".equals(parameters[1]) || "COS_ENTREGAR_ASOCIADOS".equals(parameters[1]) || "REG_ENTREGA_EXTERNO".equals(parameters[1])){
                               String sql = "SELECT A.RLCN_FECHA,a.ID_TURNO, a.ID_PROCESO, a.ID_CIRCULO, a.ANIO, a.TRNO_ANULADO, a.ID_CIRCULO, a.TRNO_CONSISTENCIA_WF, a.TRNO_DESCRIPCION, a.TRNO_ERROR, a.TRNO_FECHA_FIN, a.TRNO_FECHA_INICIO, a.TRNO_ID_FASE, a.TRNO_ID_FASE_RELACION, a.ID_MATRICULA_ULTIMA, a.TRNO_RELACION, a.TRNO_ID_WORKFLOW, a.TRNO_LAST_ID_HISTORIA, a.TRNO_LAST_ID_NOTA, a.TRNO_MODO_BLOQUEO, a.TRNO_NACIONAL, a.TRNO_OBSERVACIONES_ANULACION, a.TRNO_OBSERVACIONES_HABILITAR, a.TRNO_REVOCATORIA, a.ID_SOLICITUD, a.TRNO_ULTIMA_RESPUESTA, a.ID_USUARIO_ANULACION, a.ID_USUARIO,a.ROW_NUMBER "+
                                            "FROM (SELECT distinct r.rlcn_fecha ,tn.ID_TURNO, tn.ID_PROCESO, tn.ID_CIRCULO, tn.ANIO, tn.TRNO_ANULADO, tn.TRNO_CONSISTENCIA_WF, tn.TRNO_DESCRIPCION, tn.TRNO_ERROR, tn.TRNO_FECHA_FIN, tn.TRNO_FECHA_INICIO, tn.TRNO_ID_FASE, tn.TRNO_ID_FASE_RELACION, tn.ID_MATRICULA_ULTIMA, "+
                                            "tn.TRNO_RELACION, tn.TRNO_ID_WORKFLOW, tn.TRNO_LAST_ID_HISTORIA, tn.TRNO_LAST_ID_NOTA, tn.TRNO_MODO_BLOQUEO, tn.TRNO_NACIONAL, tn.TRNO_OBSERVACIONES_ANULACION, tn.TRNO_OBSERVACIONES_HABILITAR, tn.TRNO_REVOCATORIA, tn.ID_SOLICITUD, tn.TRNO_ULTIMA_RESPUESTA, tn.ID_USUARIO_ANULACION, tn.ID_USUARIO, ROW_NUMBER() OVER( ORDER BY to_number(substr(tn.TRNO_RELACION,0,4)),to_number(substr(tn.TRNO_RELACION,10,LENGTH(tn.TRNO_RELACION)))) AS ROW_NUMBER "+
                                            "FROM sir_op_turno tn "+
                                            "JOIN sir_op_turno_ejecucion te on tn.trno_id_workflow = te.trej_item_key "+
                                            "JOIN sir_op_relacion r on tn.trno_id_fase_relacion = r.id_fase and tn.trno_relacion = r.id_relacion "+
                                            "WHERE tn.id_circulo = ? and te.trej_fase = ? and te.trej_estacion=? ORDER BY to_number(substr(tn.TRNO_RELACION,0,4)),to_number(substr(tn.TRNO_RELACION,10,LENGTH(tn.TRNO_RELACION))) ) A "+
                                            "WHERE ROW_NUMBER between ? and ? "+
                                            "ORDER BY a.ROW_NUMBER ";
                               
                               pst = con.prepareStatement(sql);
                               int i= 0;
                               for (; i < parameters.length; i++)
                               {
                                        pst.setObject(i+1, parameters[i]);
                               }
                               pst.setInt(++i, rangoInf);
                               pst.setInt(++i, rangoSup);
                               rs = pst.executeQuery();
                               result= new ArrayList();
                               while(rs.next()){
                                   TurnoEnhanced turno = new TurnoEnhanced();
                                   turno.setIdWorkflow(rs.getString("trno_id_workflow"));
                                   turno.setIdCirculo(rs.getString("id_circulo"));
                                   turno.setAnio(rs.getString("anio"));
                                   turno.setIdProceso(rs.getLong("id_proceso"));
                                   turno.setIdTurno(rs.getString("id_turno"));
                                   turno.setIdRelacionActual(rs.getString("TRNO_RELACION"));
                                   turno.setIdFaseRelacionActual(rs.getString("TRNO_ID_FASE_RELACION"));
                                   turno.setIdFase(rs.getString("TRNO_ID_FASE"));
                                   result.add(turno);
                               }
                           }else{
                        if (this.query == null)
			{                         
				String params = claseDeObjeto + " rangoInf";
				params += ", " + claseDeObjeto + " rangoSup";
				String paramsOri = query.getParameters();
				if (paramsOri != null && !paramsOri.equals(""))
				{
					params += ", " + paramsOri;
				}
				
				query.declareParameters(params);
				
				String filter = jdoField +">= rangoInf";
				filter += " && " + jdoField +" <= rangoSup";;
				
				String filterOri = query.getFilter();
				if (filterOri != null && !filterOri.equals(""))
				{
					filter += " && " + filterOri;
				}
				
				query.setFilter(filter);
				this.query = query;
			}
			
			Object []newParams = new Object[parameters.length +2];
			
			newParams[0] = clavesPorRango.get(new Integer(rangoInf));
			newParams[1] = clavesPorRango.get(new Integer(rangoSup));
			
			for (int j=0; j< parameters.length; j++)
			{
				newParams[j+2] = parameters[j];
			}
			result = (Collection) this.query.executeWithArray(newParams);                 
                    }
            } catch (Exception e) {
                Log.getInstance().error(AbstractJDOListadosPorPaginasDAO.class, e);
            } finally {
            /**
            * @author      :   Carlos Torres
            * @Caso Mantis :   11604: Acta - Requerimiento No 030_453_Funcionario_Fase_ Entregado
            */
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
                if (pst != null) {
                    try {
                        pst.close();
                    } catch (SQLException e) {
                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                    }
                }
            }
		
		return result;
		
	}
	
	
	/**
	 * retorna el número de objetos encontrados entre rangoInf y rangoSup
	 * de la consulta JDO ejecutada sobre la clasePrincipal y con variables
	 * declaradas variables, declaración de parametros declaredParameters, 
	 * filro de consulta filter, parametros de ejecución parameters. 
	 * sqlColumn indica cual es la columna en BD sobre la cual se hace el ordenamiento.
	 * jdoField indica el campo JDO sobre el cual se hace el ordenamiento. 
	 * Éste método modifica filter para hacer un filtrado en el que jdoFiled > el valor
	 * retornado en sqlColumn en la posición rangoInf y jdoFiled <= el valor
	 * retornado en sqlColumn en la posición rangoSup. 
	 * @param clasePrincipal clase Enhanced JDO sobre la cual se ejecutará la consulta.
	 * 			El método invocará pm.newQuery(clasePrincipal); 
	 * @param variables Declaración de variables para el filtro.
	 * 			El método invocará query.declareVariables(variables)
	 * @param declaredParameters Declaración de parámetros para el filtro.
	 * 			El método invocará query.declareParameters(declaredParameters)
	 * @param filter Filtro con el que se ejecutará la consulta.
	 * 			El método invocará query.setFilter(filter)
	 * @param parameters Parámetros para la ejecución de la consulta.
	 * 			El método invocará query.executeWithArray(parameters) 
	 * @return número de elementos de la consulta
	 * @throws DAOException
	 */

	protected int consultarTamanioConsulta (Class clasePrincipal, String variables,  String declaredParameters, String filter, Object []parameters) throws DAOException
	{
		int result = 0;
		PersistenceManager pm = AdministradorPM.getPM();

		pm.currentTransaction().begin();
		
		VersantQuery query = (VersantQuery) pm.newQuery(clasePrincipal);
		query.declareVariables(variables);
		query.declareParameters(declaredParameters);
		query.setFilter(filter);
		
		result = consultarTamanioConsulta(query, parameters);
		pm.currentTransaction().commit();
		
		return result;
		
	}

	
	/**
	 * retorna el número de objetos encontrados entre rangoInf y rangoSup
	 * de la consulta JDO ejecutada.   
	 * @param query instancia de VersantQuery previamente preparada.
	 * @param parameters Parámetros para la ejecución de la consulta.
	 * 			El método invocará query.executeWithArray(parameters) 
	 * @return número de elementos de la consulta
	 * @throws DAOException
	 */
	protected int consultarTamanioConsulta (VersantQuery query, Object []parameters) throws DAOException
	{

		int tamanioConsulta = 0;
                         /**
            * @author      :   Carlos Torres
            * @Caso Mantis :   11604: Acta - Requerimiento No 030_453_Funcionario_Fase_ Entregado
            */
		String sql = "";
                if("REG_ENTREGA".equals(parameters[1]) || "COS_ENTREGAR_ASOCIADOS".equals(parameters[1]) || "REG_ENTREGA_EXTERNO".equals(parameters[1])){
                    sql = "SELECT distinct r.rlcn_fecha ,tn.ID_TURNO, tn.ID_PROCESO, tn.ID_CIRCULO, tn.ANIO, tn.TRNO_ANULADO, tn.TRNO_CONSISTENCIA_WF, tn.TRNO_DESCRIPCION, tn.TRNO_ERROR, tn.TRNO_FECHA_FIN, tn.TRNO_FECHA_INICIO, tn.TRNO_ID_FASE, tn.TRNO_ID_FASE_RELACION, tn.ID_MATRICULA_ULTIMA, "+
                          "tn.TRNO_RELACION, tn.TRNO_ID_WORKFLOW, tn.TRNO_LAST_ID_HISTORIA, tn.TRNO_LAST_ID_NOTA, tn.TRNO_MODO_BLOQUEO, tn.TRNO_NACIONAL, tn.TRNO_OBSERVACIONES_ANULACION, tn.TRNO_OBSERVACIONES_HABILITAR, tn.TRNO_REVOCATORIA, tn.ID_SOLICITUD, tn.TRNO_ULTIMA_RESPUESTA, tn.ID_USUARIO_ANULACION, tn.ID_USUARIO, ROW_NUMBER() OVER( ORDER BY RLCN_FECHA) AS ROW_NUMBER "+
                          "FROM sir_op_turno tn "+
                          "JOIN sir_op_turno_ejecucion te on tn.trno_id_workflow = te.trej_item_key "+
                          "JOIN sir_op_relacion r on tn.trno_id_fase_relacion = r.id_fase and tn.trno_relacion = r.id_relacion "+
                          "WHERE tn.id_circulo = ? and te.trej_fase = ? and te.trej_estacion=? ORDER BY 1";
                }else{
                    sql = query.getPlan(parameters).getDatastoreQuery();
                }
                
		int idxFrom = sql.indexOf("FROM");
		String sql1 = sql.substring(idxFrom);
		String sql2 = sql1.substring(0,sql1.lastIndexOf("ORDER BY"));
		String sqlRangos = "SELECT COUNT(1) " + sql2;
		
		Connection con = ((VersantPersistenceManager)query.getPersistenceManager()).getJdbcConnection(null);
		PreparedStatement pst = null;
        ResultSet rs = null;
		try {
			pst = con.prepareStatement(sqlRangos);
			int i= 0;
			for (; i < parameters.length; i++)
			{
				pst.setObject(i+1, parameters[i]);
			}
			rs = pst.executeQuery();
			
			for (int j =0;rs.next(); j++)
			{
				tamanioConsulta = rs.getInt(1);
			}
		} catch (Exception e) {
			Log.getInstance().error(AbstractJDOListadosPorPaginasDAO.class,e);
		} finally
		{
            if(rs != null)
            {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }
			if (pst != null)
			{
				try {
					pst.close();
				} catch (SQLException e) {
				}
			}
			if (con != null)
			{
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return tamanioConsulta;
		
	}
	
	public abstract List consultarListadoPorRangos (String idListado, Object[] parametros, int rangoInf, int rangoSup)
	throws DAOException;
	
	public abstract int consultarTamanioListado (String idListado, Object[] parametros)
	throws DAOException;
	
}

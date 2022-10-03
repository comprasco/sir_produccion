/*
 * Created on 15-oct-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.hermod.pagos;

import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion;
import gov.sir.core.negocio.modelo.SolicitudDevolucion;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.HermodDAOFactory;

/**
 * @author dsalas
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LiquidadorDevoluciones extends Liquidador {

	/**
	 * Crea una nueva instancia de Liquidador devoluciones
	 */
	public LiquidadorDevoluciones() {}

	/**
	 * @see gov.sir.hermod.pagos.Liquidador#liquidar(gov.sir.core.negocio.modelo.Liquidacion)
	 */
	public Liquidacion liquidar(Liquidacion liquidacion)
		throws LiquidarException {
			
			LiquidacionTurnoDevolucion ltd= (LiquidacionTurnoDevolucion)liquidacion;
			SolicitudDevolucion sd = (SolicitudDevolucion)ltd.getSolicitud();
			
			try {

				ltd.setValor(0);
		
				HermodDAOFactory factory = HermodDAOFactory.getFactory();
		
				if (sd.getIdSolicitud() == null){
					SolicitudPk srID = factory.getSolicitudesDAO().
					crearSolicitudDevolucion(sd);
					/*
					//Se coloca un tiemp de delay 
					JDOVariablesOperativasDAO jdoDAO = new JDOVariablesOperativasDAO();
					boolean respuesta = jdoDAO.getObjectByLlavePrimaria(new SolicitudEnhanced.ID(srID), 5);
					*/
					sd = (SolicitudDevolucion) factory.getSolicitudesDAO().getSolicitudByID(srID);
					if (sd.getLiquidaciones()!= null && sd.getLiquidaciones().size()>0){
						ltd = (LiquidacionTurnoDevolucion) sd.getLiquidaciones().get(0);
					}
				}
				else{
					SolicitudPk sdID = new SolicitudPk();
					sdID.idSolicitud = sd.getIdSolicitud();
					ltd.setSolicitud(sd);
					ltd = factory.getLiquidacionesDAO().addLiquidacionToSolicitudDevolucion(ltd, sdID);
				}

				return ltd;
				
			} catch (DAOException e) {
				throw new LiquidarException("No se pudo liquidar", e);
			} catch (Exception e) {
				Log.getInstance().error(LiquidadorDevoluciones.class,e.getMessage(),e);
				throw new LiquidarException("No se pudo obtener el valor de la liquidacion", e);
			}
		
	}

}

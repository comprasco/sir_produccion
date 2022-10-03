/*
 * Clase que se encarga de obteber el valor que debe ser liquidado
 * por concepto de una solicitud de consultas.
 * LiquidadorCorrecciones.java
 * Created on 21 de septiembre de 2004, 17:14
 */
      
package gov.sir.hermod.pagos;

import java.util.ArrayList;
import java.util.List;

import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudPk;

import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.hermod.dao.HermodDAOFactory;
import gov.sir.hermod.interfaz.HermodServiceInterface;

import org.auriga.smart.servicio.ServiceLocator;


/**
 * Clase que se encarga de obtener el valor que debe ser liquidado por
 * una solicitud de correcciones.
 * @author  dlopez
 */
public class LiquidadorCorrecciones extends Liquidador {
    
	/** 
	*Crea una nueva instancia de LiquidadorCorrecciones
	*/
	public LiquidadorCorrecciones() 
	{
	}
    
    
	/**
	* Obtiene el valor que debe ser liquidado por concepto de una solicitud de
	* correcciones.
	* @param liquidacion Liquidacion antes de obtener el valor a pagar.
	* @return La liquidación con el valor que debe pagarse. 
	* @see gov.sir.core.negocio.modelo.Liquidacion
	* @throws <code>LiquidarException</code>
	*/
	public Liquidacion liquidar(Liquidacion liquidacion) throws LiquidarException 
	{		
		LiquidacionTurnoCorreccion liqCor = (LiquidacionTurnoCorreccion) liquidacion;
		SolicitudCorreccion s = (SolicitudCorreccion) liqCor.getSolicitud();
		LiquidacionTurnoCorreccion liqPersistente = null;
		double valorTotal = 0;
	
	   try 
	   {
          
		    HermodDAOFactory factory = HermodDAOFactory.getFactory();
			ServiceLocator sl = ServiceLocator.getInstancia();
			HermodServiceInterface hermod = (HermodServiceInterface) sl.getServicio("gov.sir.hermod");
    					    
			SolicitudPk sid = new SolicitudPk();
			sid.idSolicitud = s.getIdSolicitud();
			
             //La solicitud no tiene asociada una liquidación
			 //persistente.
			 List listaLiquidaciones = new ArrayList(5);
			 listaLiquidaciones = s.getLiquidaciones();
			 int tamLiq = listaLiquidaciones.size();
		     
		     
		     //Si se trata de una corrección simple, el valor liquidado es cero.
		     if (tamLiq ==0){
				double valorLiq = 0;
				liqCor.setValor(valorLiq);
			/*
				//Se coloca un tiemp de delay 
				JDOVariablesOperativasDAO jdoDAO = new JDOVariablesOperativasDAO();
				boolean respuesta = jdoDAO.getObjectByLlavePrimaria(new SolicitudEnhanced.ID(sid), 5);
			*/
				liqPersistente = factory.getLiquidacionesDAO().addLiquidacionToSolicitudCorreccion(liqCor,s);
				
				if (liqPersistente==null)
				throw new LiquidarException("No pudo hacerse persistente la liquidación.");
			
				return liqPersistente;
		     }
		     
		     
		     //Si el proceso incluye pagos de mayor valor, se obtienen las tarifas desde
		     //las tablas de OPLookupCodes y OPLookupTypes
		     else if (tamLiq >0){
		     	
				List actos = liqCor.getActos();
				return null;   	
		     }
		     else
		      throw new LiquidarException("No pudo hacerse persistente la liquidación.");

		} catch (Exception e) {
			Log.getInstance().error(LiquidadorCorrecciones.class,e.getMessage(),e);
			throw new LiquidarException("No pudo hacerse persistente la liquidación", e);
		} catch (Throwable e) {
			Log.getInstance().error(LiquidadorCorrecciones.class,e.getMessage(),e);
			throw new LiquidarException("No se pudo avanzar el turno después de la liquidación", e);
			
		}        
	}


	/**
	 * Redondea el valor obtenido a la centena.
	 */
	private static double roundValue (double valor)
	{
		double diferencia = valor%10;
    	
		//Redondeo hacia la decena anterior.
		if (diferencia < 5)
		{
			valor -= diferencia;
		}
    	
		//Redondeo hacia la centena siguiente.
		else
		{
			valor -= diferencia;
			valor +=10;
		}
    	
		return valor;
	}


	
}
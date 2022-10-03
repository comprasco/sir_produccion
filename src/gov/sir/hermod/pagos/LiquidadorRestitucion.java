/*
 * LiquidadorRestitucion.java
 * Clase que se encarga de obteber el valor que debe ser liquidado
 * por concepto de una solicitud de restitucion de reparto notarial.
 * LiquidadorRestitucion.java
 * Created on 23 de noviembre de 2004, 12:14
 */
      
package gov.sir.hermod.pagos;

import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;
import gov.sir.core.negocio.modelo.Tarifa;
import gov.sir.core.negocio.modelo.constantes.CTipoTarifa;
import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.HermodDAOFactory;


/**
 * Clase que se encarga de obtener el valor que debe ser liquidado por
 * una solicitud de restituci�n de reparto notarial.
 * @author  dlopez
 */
public class LiquidadorRestitucion extends Liquidador {
    
	/** 
	*Crea una nueva instancia de LiquidadorRestitucion
	*/
	public LiquidadorRestitucion() 
	{
		
	}
    
    
	/**
	* Obtiene el valor que debe ser liquidado por concepto de una solicitud de
	* restituci�n de reparto notarial.
	* @param liquidacion Liquidacion antes de obtener el valor a pagar.
	* @return La liquidaci�n con el valor que debe pagarse. 
	* @see gov.sir.core.negocio.modelo.Liquidacion
	* @throws <code>LiquidarException</code>
	*/
	public Liquidacion liquidar(Liquidacion liquidacion) throws LiquidarException 
	{

				
		LiquidacionTurnoRestitucionReparto liqReparto = (LiquidacionTurnoRestitucionReparto) liquidacion;
		SolicitudRestitucionReparto s = (SolicitudRestitucionReparto) liqReparto.getSolicitud();
	
	   try 
	   {
			HermodDAOFactory factory = HermodDAOFactory.getFactory();
    
           //Se obtiene el valor que debe ser liquidado por concepto
		   //de la solicitud de restituci�n de reparto notarial.
		   double valorLiq;
		   String code = LiquidacionTurnoRestitucionReparto.CODE_LIQUIDACION;
           
		   //Se hace la consulta sobre la tabla OPLookupCodes.
           //SE  OBTIENE EL VALOR DE la tarifa
			Tarifa tarifaRestitucion = getTarifa(CTipoTarifa.REPARTO_NOTARIAL, code);	
			if (tarifaRestitucion == null)
			{
				throw new DAOException ("No se encontr� valor de tarifa de restituci�n para la llave: "+CTipoTarifa.REPARTO_NOTARIAL+" - "+code);
			}
			valorLiq = tarifaRestitucion.getValor();
			
			valorLiq = roundValue (valorLiq);
		
		   //Se asigna el valor obtenido a la liquidaci�n. 
		   liqReparto.setValor(valorLiq);
						    
			SolicitudPk sid = new SolicitudPk();
			sid.idSolicitud = s.getIdSolicitud();
			/*
			//Se coloca un tiemp de delay 
			JDOVariablesOperativasDAO jdoDAO = new JDOVariablesOperativasDAO();
			boolean respuesta = jdoDAO.getObjectByLlavePrimaria(new SolicitudEnhanced.ID(sid), 5);
			*/   
			LiquidacionTurnoRestitucionReparto liqPersistente = factory.getLiquidacionesDAO().addLiquidacionToSolicitudRestitucionReparto(liqReparto,s);
		 
			if (liqPersistente==null)
			   throw new LiquidarException("No pudo hacerse persistente la liquidaci�n.");
			
			return liqPersistente;

		}
		   
		catch (Exception e) 
		{
			Log.getInstance().error(LiquidadorRestitucion.class,e.getMessage(),e);
			throw new LiquidarException("No pudo hacerse persistente la liquidaci�n", e);
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
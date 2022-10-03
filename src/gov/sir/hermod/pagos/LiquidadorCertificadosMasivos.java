/*
 * LiquidadorCertificados.java
 *
 * Created on 6 de agosto de 2004, 17:14
 */

package gov.sir.hermod.pagos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo;
import gov.sir.core.negocio.modelo.SolicitudPk;

import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.hermod.dao.HermodDAOFactory;

/**
 *
 * @author  mortiz, dlopez
 */
public class LiquidadorCertificadosMasivos extends Liquidador {
    
    /**
    *  Crea una nueva instancia del liquidador de Certificados Masivos
    */
    public LiquidadorCertificadosMasivos() {
    }
    
    
    /**
    * Calcula el valor que debe ser cancelado por concepto de una liquidación de
    * certificados masivos.   
    */
    public Liquidacion liquidar(Liquidacion liquidacion) throws LiquidarException {
	
		LiquidacionTurnoCertificado liqIndividual;
		LiquidacionTurnoCertificadoMasivo liqCer = (LiquidacionTurnoCertificadoMasivo) liquidacion;
		SolicitudCertificadoMasivo s = (SolicitudCertificadoMasivo) liqCer.getSolicitud();
		double valorLiquidado = 0;
		List listaLiquidaciones = new ArrayList(5);
		Liquidacion liqFinal;
		
		
		try
		{
			HermodDAOFactory factory = HermodDAOFactory.getFactory();
			
            //Lista con las solicitudes asociadas. (Solicitudes de Certificados Individuales).
			List certAsocs = s.getSolicitudesHijas();
			
			//Se recorre la lista, para obtener una liquidación total. 
			//Sumando los valores parciales de las liquidaciones de certificados. 
			Iterator it2 = certAsocs.iterator();
			
			while(it2.hasNext())
			{
				SolicitudAsociada solAsoc = (SolicitudAsociada) it2.next();
				SolicitudCertificado solCert = (SolicitudCertificado) solAsoc.getSolicitudHija();
				
				//Se valida que la solicitud de certificados no sea nula. 
				if (solCert == null)
				{
					throw new LiquidarException("No se pudo realizar la liquidacion, no existe la solicitud asociada");
				}
				
				
				listaLiquidaciones = solCert.getLiquidaciones();
                // 	Se valida que la lista de liquidaciones no sea nula y contenga al menos un elemento. 
				if (listaLiquidaciones == null)
				{ 
					throw new LiquidarException("No se pudo realizar la liquidacion de Certificados Masivos" +
						" no existen las liquidaciones para los certificados individuales");
				}
				else if (listaLiquidaciones.size()==0)
				{
					throw new LiquidarException("No se pudo realizar la liquidacion de Certificados Masivos" +
						" no existen las liquidaciones para los certificados individuales");
				}
				else
				{
					liqIndividual = (LiquidacionTurnoCertificado) listaLiquidaciones.get(0);
					valorLiquidado += liqIndividual.getValor();
				}
				
			}
			
			valorLiquidado = roundValue (valorLiquidado);
			
			liqCer.setValor(valorLiquidado);
			
			SolicitudPk srID = factory.getSolicitudesDAO().crearSolicitudCertificadoMasivo(s);
			/*
			//Se coloca un tiemp de delay 
			JDOVariablesOperativasDAO jdoDAO = new JDOVariablesOperativasDAO();
			boolean respuesta = jdoDAO.getObjectByLlavePrimaria(new SolicitudEnhanced.ID(srID), 5);
			*/
			
			s = (SolicitudCertificadoMasivo) factory.getSolicitudesDAO().getSolicitudByID(srID);
			List listaLiquidacionesMasivos = s.getLiquidaciones();
			
			if (listaLiquidacionesMasivos.size()==0)
			{
				  throw new LiquidarException("No se pudo realizar la liquidacion de Certificados Masivos" +
							   " no existen la liquidación");
			}
			liqFinal = (Liquidacion) listaLiquidacionesMasivos.get(0);
			liqFinal.setSolicitud(s);
			
			
			

			
		}
	catch (Exception e) {
				Log.getInstance().error(LiquidadorCertificadosMasivos.class,e.getMessage(),e);	
				throw new LiquidarException("No se pudo obtener el valor de la liquidacion", e);
	}
	catch (Throwable e) {
				Log.getInstance().error(LiquidadorCertificadosMasivos.class,e.getMessage(),e);	
				throw new LiquidarException("No se pudo obtener el valor de la liquidacion", e);
	}      
	
	return liqFinal;
		
			
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